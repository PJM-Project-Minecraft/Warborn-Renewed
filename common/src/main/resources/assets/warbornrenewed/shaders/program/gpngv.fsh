#version 150

uniform sampler2D DiffuseSampler;
uniform sampler2D NoiseSampler;

uniform float Time;
uniform float Brightness;
uniform float IntensityAdjust;
uniform float NoiseAmplification;

uniform float TubeRadius;
uniform float TubeSoftness;
uniform float TubeFisheye;
uniform float EdgeBlurStrength;
uniform float ChromaticAberration;
uniform vec3  PhosphorTint;

uniform vec2 InSize;

uniform float Pixels;
uniform float NoiseTimeScale;
uniform float HighNoiseExp;
uniform float BaseNoiseGain;
uniform float HighNoiseGain;

in vec2 texCoord;
in vec2 oneTexel;

out vec4 fragColor;

vec2 fisheyeWarp(vec2 uv, vec2 center, float k, float aspect){
    vec2 p = uv - center;
    p.x *= aspect;
    float r = length(p);
    if(r < 1e-5) return uv;
    float rn = r + k * r * r;
    vec2 dir = p / r;
    vec2 warped = dir * rn;
    warped.x /= aspect;
    return center + warped;
}

float tubeMask(vec2 uv, vec2 center, float radius, float softness, float aspect){
    vec2 p = uv - center;
    p.x *= aspect;
    float d = length(p);
    return 1.0 - smoothstep(radius - softness, radius, d);
}

float tubeEdgeFactor(vec2 uv, vec2 center, float radius, float aspect){
    vec2 p = uv - center;
    p.x *= aspect;
    float d = length(p);
    return smoothstep(radius * 0.65, radius * 0.95, d);
}

vec3 sampleRGB(vec2 uv){
    return texture(DiffuseSampler, clamp(uv, 0.0, 1.0)).rgb;
}

// WHITE PHOSPHOR тонемап с засветом при ярком свете
vec3 whitePhosphorTonemap(vec3 rgb, float gain, vec3 tint){
    float lum = dot(rgb, vec3(0.299, 0.587, 0.114));

    // Эффект засвета: при ярком свете усиливаем белый цвет для ослепления днем
    float bloomThreshold = 0.4;
    float bloom = smoothstep(bloomThreshold, 0.75, lum) * 4.0;

    float x = clamp(lum * gain, 0.0, 64.0);
    x = x / (1.0 + x);
    x = pow(x, 0.85);

    // Белофосфорный оттенок: серо-голубоватый
    vec3 result = tint * x;

    // Добавляем засвет
    result += vec3(bloom);

    return result;
}

float rnd(vec2 st){
    return fract(sin(dot(st, vec2(12.9898, 78.233))) * 43758.5453123);
}

float thermalNoiseAt(vec2 uv, vec2 fragCoord, float sceneGrey){
    float resScale = 1440.0 / max(1.0, InSize.y);
    float noisePx = (Pixels < 1.0 ? Pixels : Pixels * 0.2) * resScale;
    float t = mod(Time * NoiseTimeScale, 10.0);
    float baseN = rnd(uv / resScale + t);
    vec2 nUv = floor(fragCoord * noisePx) / InSize / noisePx;
    float highN = pow(rnd(nUv + t), HighNoiseExp) * HighNoiseGain;
    float vign = pow(1.0 - dot(uv - vec2(0.5), uv - vec2(0.5)), 3.0);
    float darkF = smoothstep(0.0, 0.3, sceneGrey);
    float n = (baseN * BaseNoiseGain + highN) * darkF;
    n *= (0.2 + 0.8 * vign);
    return n * NoiseAmplification;
}

vec3 sampleWithChromaticAberration(vec2 warpedUV, vec2 center, float edgeFactor, float aspect){
    vec2 dir = normalize((warpedUV - center) * vec2(aspect, 1.0));
    float shift = ChromaticAberration * edgeFactor * 0.003;

    float r = sampleRGB(warpedUV + dir * shift * 1.0).r;
    float g = sampleRGB(warpedUV).g;
    float b = sampleRGB(warpedUV - dir * shift * 1.0).b;

    return vec3(r, g, b);
}

void main(){
    vec2 uv = texCoord;
    float aspect = InSize.x / max(1.0, InSize.y);

    vec2 cL = vec2(0.25, 0.50);
    vec2 cC = vec2(0.50, 0.50);
    vec2 cR = vec2(0.75, 0.50);

    vec2 wL = fisheyeWarp(uv, cL, TubeFisheye, aspect);
    vec2 wC = fisheyeWarp(uv, cC, TubeFisheye, aspect);
    vec2 wR = fisheyeWarp(uv, cR, TubeFisheye, aspect);

    float mL = tubeMask(uv, cL, TubeRadius, TubeSoftness, aspect);
    float mC = tubeMask(uv, cC, TubeRadius, TubeSoftness, aspect);
    float mR = tubeMask(uv, cR, TubeRadius, TubeSoftness, aspect);

    float eL = tubeEdgeFactor(uv, cL, TubeRadius, aspect);
    float eC = tubeEdgeFactor(uv, cC, TubeRadius, aspect);
    float eR = tubeEdgeFactor(uv, cR, TubeRadius, aspect);

    float sceneGrey = dot(sampleRGB(uv), vec3(0.299, 0.587, 0.114));
    float n = thermalNoiseAt(uv, uv * InSize, sceneGrey);
    vec3 noise = vec3(n);

    vec3 bL = sampleWithChromaticAberration(wL, cL, eL, aspect) * Brightness + noise;
    vec3 bC = sampleWithChromaticAberration(wC, cC, eC, aspect) * Brightness + noise;
    vec3 bR = sampleWithChromaticAberration(wR, cR, eR, aspect) * Brightness + noise;

    vec3 cL_col = whitePhosphorTonemap(bL, IntensityAdjust, PhosphorTint);
    vec3 cC_col = whitePhosphorTonemap(bC, IntensityAdjust, PhosphorTint);
    vec3 cR_col = whitePhosphorTonemap(bR, IntensityAdjust, PhosphorTint);

    vec3 blurL = (sampleRGB(wL + oneTexel * vec2(1.5, 0.0)) +
    sampleRGB(wL - oneTexel * vec2(1.5, 0.0)) +
    sampleRGB(wL + oneTexel * vec2(0.0, 1.5)) +
    sampleRGB(wL - oneTexel * vec2(0.0, 1.5))) * 0.25;
    vec3 blurC = (sampleRGB(wC + oneTexel * vec2(1.5, 0.0)) +
    sampleRGB(wC - oneTexel * vec2(1.5, 0.0)) +
    sampleRGB(wC + oneTexel * vec2(0.0, 1.5)) +
    sampleRGB(wC - oneTexel * vec2(0.0, 1.5))) * 0.25;
    vec3 blurR = (sampleRGB(wR + oneTexel * vec2(1.5, 0.0)) +
    sampleRGB(wR - oneTexel * vec2(1.5, 0.0)) +
    sampleRGB(wR + oneTexel * vec2(0.0, 1.5)) +
    sampleRGB(wR - oneTexel * vec2(0.0, 1.5))) * 0.25;

    blurL = whitePhosphorTonemap(blurL * Brightness, IntensityAdjust, PhosphorTint);
    blurC = whitePhosphorTonemap(blurC * Brightness, IntensityAdjust, PhosphorTint);
    blurR = whitePhosphorTonemap(blurR * Brightness, IntensityAdjust, PhosphorTint);

    cL_col = mix(cL_col, blurL, eL * EdgeBlurStrength);
    cC_col = mix(cC_col, blurC, eC * EdgeBlurStrength);
    cR_col = mix(cR_col, blurR, eR * EdgeBlurStrength);

    float sumM = mL + mC + mR + 1e-6;
    vec3 col = (cL_col * mL + cC_col * mC + cR_col * mR) / sumM;
    float mask = clamp(sumM, 0.0, 1.0);

    fragColor = vec4(col * mask, mask);
}
