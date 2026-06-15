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
uniform vec3 PhosphorTint;
uniform vec2 InSize;
uniform float PixelNoiseSize;
uniform float BloomThreshold;
uniform float BloomIntensity;

in vec2 texCoord;
in vec2 oneTexel;

out vec4 fragColor;

// Константы
const float CONTRAST = 0.8;
const float NOISE_AMOUNT = 0.05;

// Функция искажения рыбьего глаза - сэмплирует из центра экрана
vec2 fishEyeDistort(vec2 uv, vec2 tubeCenter, float strength, float radius, float aspect) {
    // Позиция относительно центра трубки (для расчёта искажения)
    vec2 coordFromTube = uv - tubeCenter;
    coordFromTube.x *= aspect;
    
    float dist = length(coordFromTube);
    float maxRadius = radius * aspect;
    
    // Позиция относительно центра экрана (для сэмплирования текстуры)
    vec2 screenCenter = vec2(0.5, 0.5);
    vec2 coordFromScreen = uv - screenCenter;
    coordFromScreen.x *= aspect;
    
    if(dist < maxRadius && dist > 0.0) {
        float normalizedDist = dist / maxRadius;
        float distortionFactor = 1.0 + strength * normalizedDist * normalizedDist;
        coordFromScreen *= distortionFactor;
    }
    
    coordFromScreen.x /= aspect;
    return screenCenter + coordFromScreen;
}

// Круглая маска с мягкими краями
float tubeMask(vec2 uv, vec2 center, float radius, float softness, float aspect) {
    vec2 p = uv - center;
    p.x *= aspect;
    float d = length(p);
    float adjustedRadius = radius * aspect;
    return smoothstep(adjustedRadius, adjustedRadius - softness, d);
}

vec3 sampleRGB(vec2 uv) {
    return texture(DiffuseSampler, clamp(uv, 0.0, 1.0)).rgb;
}

float rnd(vec2 st) {
    return fract(sin(dot(st, vec2(12.9898, 78.233))) * 43758.5453123);
}

float grainNoise(vec2 uv) {
    vec2 pixelUV = floor(uv * InSize / PixelNoiseSize) * PixelNoiseSize / InSize;
    float t = mod(Time * 2.0, 100.0);
    float baseNoise = (rnd(pixelUV * t) - 0.5);
    float detailNoise = (rnd(uv * t * 3.7) - 0.5) * 0.3;
    return (baseNoise + detailNoise) * NoiseAmplification;
}

vec3 applyBloom(vec3 color) {
    float lum = dot(color, vec3(0.299, 0.587, 0.114));
    if (lum > BloomThreshold) {
        float bloomAmount = (lum - BloomThreshold) * BloomIntensity;
        color += vec3(bloomAmount) * PhosphorTint * 0.8;
    }
    return color;
}

vec3 overexposure(vec3 color, float amount) {
    float lum = dot(color, vec3(0.299, 0.587, 0.114));
    if (lum > 0.6) {
        float excess = (lum - 0.6) * amount;
        color += PhosphorTint * excess * 1.5;
    }
    return color;
}

// Обработка одного круга (как в pnv10t с пересветом)
vec3 processCircle(vec2 uv, vec2 center, float aspect) {
    // Применяем эффект рыбьего глаза
    vec2 distortedCoord = fishEyeDistort(uv, center, TubeFisheye, TubeRadius, aspect);
    distortedCoord = clamp(distortedCoord, 0.0, 1.0);
    
    vec4 texColor = texture(DiffuseSampler, distortedCoord);
    
    // Gamma коррекция для поднятия теней (как в pnv10t)
    texColor.rgb = pow(texColor.rgb, vec3(0.5)) * Brightness;
    
    // Добавляем шум с анимацией
    vec2 noiseUV;
    noiseUV.x = 0.35 * sin(Time * 10.0);
    noiseUV.y = 0.35 * cos(Time * 10.0);
    vec3 noise = texture(NoiseSampler, distortedCoord + noiseUV).rgb * NoiseAmplification;
    texColor.xy += noise.xy * NOISE_AMOUNT;
    
    // Конвертируем в ночное видение (как в pnv10t)
    const vec3 lumvec = vec3(0.30, 0.59, 0.11);
    float intensity = dot(lumvec, texColor.rgb);
    intensity = clamp(CONTRAST * (intensity - 0.5) + 0.5, 0.0, 1.0);
    
    float colorIntensity = clamp(intensity / 0.59, 0.0, 1.0) * IntensityAdjust;
    vec3 visionColor = PhosphorTint * colorIntensity;
    
    float gray = dot(texColor.rgb, vec3(0.299, 0.587, 0.114));
    vec3 result = vec3(gray) * visionColor;
    
    // Применяем bloom и overexposure для пересвета днём
    result = applyBloom(result);
    result = overexposure(result, 2.0);
    
    // Добавляем пиксельный шум
    float n = grainNoise(uv);
    result += n;
    
    return result;
}

void main() {
    vec2 uv = texCoord;
    float aspect = InSize.x / max(1.0, InSize.y);
    
    // Центры двух кругов (левый и правый)
    vec2 centerL = vec2(0.33, 0.50);
    vec2 centerR = vec2(0.67, 0.50);
    
    // Маски для кругов
    float maskL = tubeMask(uv, centerL, TubeRadius, TubeSoftness, aspect);
    float maskR = tubeMask(uv, centerR, TubeRadius, TubeSoftness, aspect);
    
    // Обрабатываем каждый круг
    vec3 colorL = processCircle(uv, centerL, aspect);
    vec3 colorR = processCircle(uv, centerR, aspect);
    
    // Комбинируем результаты - используем max для избежания перекрытия
    float mask = max(maskL, maskR);
    vec3 finalColor = mix(colorR, colorL, step(maskR, maskL));
    
    fragColor = vec4(finalColor * mask, mask);
}
