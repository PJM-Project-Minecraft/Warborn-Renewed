package ru.liko.warbornrenewed.content.armorset;

import net.minecraft.resources.ResourceLocation;
import ru.liko.warbornrenewed.WarbornRenewed;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public record ArmorVisualSpec(ResourceLocation model,
                              ResourceLocation texture,
                              @Nullable ResourceLocation animation,
                              @Nullable ResourceLocation nvgShader,
                              Map<String, ResourceLocation> variants) {
    public ArmorVisualSpec {
        Objects.requireNonNull(model, "model");
        Objects.requireNonNull(texture, "texture");
        variants = Map.copyOf(variants); // Immutable copy
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private ResourceLocation model;
        private ResourceLocation texture;
        private ResourceLocation animation;
        private ResourceLocation nvgShader;
        private final Map<String, ResourceLocation> variants = new HashMap<>();

        private Builder() {
        }

        public Builder model(ResourceLocation model) {
            this.model = Objects.requireNonNull(model, "model");
            return this;
        }

        public Builder model(String path) {
            return model(resolve(path));
        }

        public Builder texture(ResourceLocation texture) {
            this.texture = Objects.requireNonNull(texture, "texture");
            return this;
        }

        public Builder texture(String path) {
            return texture(resolve(path));
        }

        public Builder animation(@Nullable ResourceLocation animation) {
            this.animation = animation;
            return this;
        }

        public Builder animation(String path) {
            return animation(resolve(path));
        }

        public Builder nvgShader(@Nullable ResourceLocation shader) {
            this.nvgShader = shader;
            return this;
        }

        public Builder nvgShader(String path) {
            return nvgShader(resolve(path));
        }

        public Builder variant(String name, ResourceLocation texture) {
            this.variants.put(name, Objects.requireNonNull(texture, "texture"));
            return this;
        }

        public Builder variant(String name, String path) {
            return variant(name, resolve(path));
        }

        public ArmorVisualSpec build() {
            Objects.requireNonNull(model, "model");
            Objects.requireNonNull(texture, "texture");
            return new ArmorVisualSpec(model, texture, animation, nvgShader, variants);
        }

        private static ResourceLocation resolve(String path) {
            if (path.contains(":")) {
                int idx = path.indexOf(':');
                String namespace = path.substring(0, idx);
                String resourcePath = path.substring(idx + 1);
                return new ResourceLocation(namespace, resourcePath);
            }
            return WarbornRenewed.id(path);
        }
    }
}


