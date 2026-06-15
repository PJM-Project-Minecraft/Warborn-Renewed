#version 150

uniform sampler2D DiffuseSampler;

uniform vec2 InSize;
uniform float Time;

// Параметры линз
uniform float TubeRadius;      // Радиус линзы
uniform float TubeSoftness;    // Мягкость края
uniform float TubeFisheye;     // Сила fisheye искажения
uniform float TubeSpacing;     // Расстояние между центрами линз

// Эффекты
uniform float VignetteStrength;
uniform float EdgeDarkening;
uniform float Brightness;
uniform float ChromaticAberration; // Сила хроматической аберрации

in vec2 texCoord;
in vec2 oneTexel;

out vec4 fragColor;

// Fisheye искажение от ЦЕНТРА ЭКРАНА (единое изображение)
vec2 fisheyeWarp(vec2 uv, float k, float aspect) {
    vec2 center = vec2(0.5, 0.5);
    vec2 p = uv - center;
    p.x *= aspect;
    float r = length(p);
    if (r < 1e-5) return uv;
    float rn = r + k * r * r;
    vec2 dir = p / r;
    vec2 warped = dir * rn;
    warped.x /= aspect;
    return center + warped;
}

// Маска круглой линзы
float tubeMask(vec2 uv, vec2 center, float radius, float softness, float aspect) {
    vec2 p = uv - center;
    p.x *= aspect;
    float d = length(p);
    return 1.0 - smoothstep(radius - softness, radius, d);
}

// Расстояние до края маски (для хром. аберрации)
float edgeDistance(vec2 uv, vec2 centerL, vec2 centerR, float radius, float aspect) {
    vec2 pL = uv - centerL;
    pL.x *= aspect;
    float dL = length(pL);
    
    vec2 pR = uv - centerR;
    pR.x *= aspect;
    float dR = length(pR);
    
    // Минимальное расстояние до центра любой из линз
    float minD = min(dL, dR);
    
    // Нормализуем: 0 = центр, 1 = край
    return smoothstep(radius * 0.3, radius * 0.95, minD);
}

// Виньетка
float vignette(vec2 uv, vec2 centerL, vec2 centerR, float radius, float strength, float aspect) {
    vec2 pL = uv - centerL;
    pL.x *= aspect;
    float dL = length(pL) / radius;
    
    vec2 pR = uv - centerR;
    pR.x *= aspect;
    float dR = length(pR) / radius;
    
    // Берём минимальное расстояние (ближайшая линза)
    float d = min(dL, dR);
    return 1.0 - pow(d, 2.0) * strength;
}

vec3 sampleRGB(vec2 uv) {
    return texture(DiffuseSampler, clamp(uv, 0.0, 1.0)).rgb;
}

// Хроматическая аберрация - смещение R/G/B каналов
vec3 sampleWithChromaAberration(vec2 uv, float strength, float aspect) {
    vec2 center = vec2(0.5, 0.5);
    vec2 dir = uv - center;
    dir.x *= aspect;
    float dist = length(dir);
    if (dist < 1e-5) return sampleRGB(uv);
    
    dir = normalize(dir);
    dir.x /= aspect;
    
    // Смещение пропорционально расстоянию от центра
    float shift = strength * dist * 0.015;
    
    float r = sampleRGB(uv + dir * shift).r;
    float g = sampleRGB(uv).g;
    float b = sampleRGB(uv - dir * shift).b;
    
    return vec3(r, g, b);
}

void main() {
    vec2 uv = texCoord;
    float aspect = InSize.x / max(1.0, InSize.y);
    
    // Центры двух линз
    float spacing = TubeSpacing / aspect;
    vec2 centerL = vec2(0.5 - spacing, 0.5);
    vec2 centerR = vec2(0.5 + spacing, 0.5);
    
    // Маски линз
    float maskL = tubeMask(uv, centerL, TubeRadius, TubeSoftness, aspect);
    float maskR = tubeMask(uv, centerR, TubeRadius, TubeSoftness, aspect);
    
    // Объединённая маска (форма "восьмёрки")
    float mask = clamp(maskL + maskR, 0.0, 1.0);
    
    // Если вне маски - чёрный
    if (mask < 0.01) {
        fragColor = vec4(0.0, 0.0, 0.0, 1.0);
        return;
    }
    
    // Fisheye от ЦЕНТРА ЭКРАНА (единое изображение!)
    vec2 warpedUV = fisheyeWarp(uv, TubeFisheye, aspect);
    
    // Расстояние до края для эффектов
    float edgeFactor = edgeDistance(uv, centerL, centerR, TubeRadius, aspect);
    
    // Сэмплируем с хроматической аберрацией по краям
    float chromaStrength = ChromaticAberration * edgeFactor;
    vec3 color = sampleWithChromaAberration(warpedUV, chromaStrength, aspect);
    
    // Яркость
    color *= Brightness;
    
    // Виньетка
    float vig = vignette(uv, centerL, centerR, TubeRadius, VignetteStrength, aspect);
    color *= clamp(vig, 0.0, 1.0);
    
    // Затемнение к краям
    color *= mix(1.0, 1.0 - EdgeDarkening, edgeFactor);
    
    // Размытие на самых краях
    if (edgeFactor > 0.7) {
        vec3 blur = (sampleRGB(warpedUV + oneTexel * vec2(2.0, 0.0)) +
                     sampleRGB(warpedUV - oneTexel * vec2(2.0, 0.0)) +
                     sampleRGB(warpedUV + oneTexel * vec2(0.0, 2.0)) +
                     sampleRGB(warpedUV - oneTexel * vec2(0.0, 2.0))) * 0.25 * Brightness;
        float blurMix = smoothstep(0.7, 1.0, edgeFactor) * 0.4;
        color = mix(color, blur, blurMix);
    }
    
    fragColor = vec4(color * mask, 1.0);
}
