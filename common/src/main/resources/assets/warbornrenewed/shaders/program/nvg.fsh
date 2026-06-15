#version 150

uniform sampler2D DiffuseSampler;
uniform sampler2D NoiseSampler;
uniform float Time;
uniform float IntensityAdjust;
uniform vec2 InSize;

in vec2 texCoord;
in vec2 oneTexel;

out vec4 fragColor;

const float CIRCLE_RADIUS = 0.35;
const float CIRCLE_SOFTNESS = 0.25;
const float FISHEYE_STRENGTH = 0.3;
const float BRIGHTNESS = 0.6;
const float NOISE_AMOUNT = 0.05;
const float contrast = 0.8;
const float NOISE_AMPLIFICATION = 0.6;

const float RED_VALUE = 0.2;
const float GREEN_VALUE = 1.0;
const float BLUE_VALUE = 0.2;

const vec3 SEPIA = vec3(1.2, 1.0, 0.8);

vec2 fishEyeDistort(vec2 uv, float strength, float radius) {
    vec2 center = vec2(0.5, 0.5);
    vec2 coord = uv - center;

    float aspectRatio = InSize.x / InSize.y;
    coord.x *= aspectRatio;

    float dist = length(coord);
    float maxRadius = radius * aspectRatio;

    if(dist < maxRadius && dist > 0.0) {
        float normalizedDist = dist / maxRadius;
        float distortionFactor = 1.0 + strength * normalizedDist * normalizedDist;
        coord *= distortionFactor;
    }

    coord.x /= aspectRatio;
    return center + coord;
}

void main() {
    vec2 centeredCoord = texCoord - vec2(0.5, 0.5);
    float aspectRatio = InSize.x / InSize.y;
    centeredCoord.x *= aspectRatio;
    float distFromCenter = length(centeredCoord);
    float adjustedRadius = CIRCLE_RADIUS * aspectRatio;

    float vignette = smoothstep(adjustedRadius, adjustedRadius - CIRCLE_SOFTNESS, distFromCenter);

    vec2 distortedCoord = texCoord;
    if(distFromCenter < adjustedRadius) {
        distortedCoord = fishEyeDistort(texCoord, FISHEYE_STRENGTH, CIRCLE_RADIUS);
        distortedCoord = clamp(distortedCoord, 0.0, 1.0);
    }

    vec4 texColor = texture(DiffuseSampler, distortedCoord);

    texColor.rgb = pow(texColor.rgb, vec3(0.5)) * BRIGHTNESS;

    vec2 uv;
    uv.x = 0.35 * sin(Time * 10.0);
    uv.y = 0.35 * cos(Time * 10.0);
    vec3 noise = texture(NoiseSampler, distortedCoord + uv).rgb * NOISE_AMPLIFICATION;
    texColor.xy += noise.xy * NOISE_AMOUNT;

    texColor.rgb *= vignette;
    texColor.a = 1.0;

    if(distFromCenter <= adjustedRadius) {
        const vec3 lumvec = vec3(0.30, 0.59, 0.11);
        float intensity = dot(lumvec, texColor.rgb);
        intensity = clamp(contrast * (intensity - 0.5) + 0.5, 0.0, 1.0);

        float color = clamp(intensity / 0.59, 0.0, 1.0) * IntensityAdjust;
        vec4 visionColor = vec4(RED_VALUE * color, GREEN_VALUE * color, BLUE_VALUE * color, 1.0);

        float gray = dot(texColor.rgb, vec3(0.299, 0.587, 0.114));
        vec4 grayColor = vec4(gray, gray, gray, 1.0);
        texColor = grayColor * visionColor;
    } else {
        texColor = vec4(0.0, 0.0, 0.0, 1.0);
    }

    fragColor = vec4(texColor.rgb, 1.0);
}
