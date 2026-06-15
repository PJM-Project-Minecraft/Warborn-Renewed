package ru.liko.warbornrenewed.content.armorset;

import net.minecraft.world.item.ArmorItem;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

import javax.annotation.Nullable;

public final class ArmorBonesSpec {
    private final String head;
    private final String body;
    private final String rightArm;
    private final String leftArm;
    private final String rightLeg;
    private final String leftLeg;
    private final String rightBoot;
    private final String leftBoot;

    private ArmorBonesSpec(Builder builder) {
        this.head = builder.head;
        this.body = builder.body;
        this.rightArm = builder.rightArm;
        this.leftArm = builder.leftArm;
        this.rightLeg = builder.rightLeg;
        this.leftLeg = builder.leftLeg;
        this.rightBoot = builder.rightBoot;
        this.leftBoot = builder.leftBoot;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static ArmorBonesSpec defaults(ArmorItem.Type type) {
        return switch (type) {
            case HELMET -> builder().head("armorHead").build();
            case CHESTPLATE -> builder().body("armorBody").rightArm("armorRightArm").leftArm("armorLeftArm").build();
            case LEGGINGS -> builder().body("armorBody").rightLeg("armorRightLeg").leftLeg("armorLeftLeg").build();
            case BOOTS -> builder().rightBoot("armorRightBoot").leftBoot("armorLeftBoot").build();
            default -> builder().build(); // For any future armor types
        };
    }

    public void apply(GeoArmorRenderer<?> renderer) {
        try {
            setField(renderer, "head", create(head));
            setField(renderer, "body", create(body));
            setField(renderer, "rightArm", create(rightArm));
            setField(renderer, "leftArm", create(leftArm));
            setField(renderer, "rightLeg", create(rightLeg));
            setField(renderer, "leftLeg", create(leftLeg));
            setField(renderer, "rightBoot", create(rightBoot));
            setField(renderer, "leftBoot", create(leftBoot));
        } catch (Exception e) {
            throw new RuntimeException("Failed to apply armor bones to renderer", e);
        }
    }

    private static void setField(GeoArmorRenderer<?> renderer, String fieldName, GeoBone bone) throws Exception {
        java.lang.reflect.Field field = GeoArmorRenderer.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(renderer, bone);
    }

    private static GeoBone create(@Nullable String boneName) {
        if (boneName == null || boneName.isBlank()) {
            return null;
        }
        return new GeoBone(null, boneName, false, 0.0D, false, false);
    }

    public static final class Builder {
        private String head;
        private String body;
        private String rightArm;
        private String leftArm;
        private String rightLeg;
        private String leftLeg;
        private String rightBoot;
        private String leftBoot;

        private Builder() {
        }

        public Builder head(@Nullable String head) {
            this.head = head;
            return this;
        }

        public Builder body(@Nullable String body) {
            this.body = body;
            return this;
        }

        public Builder rightArm(@Nullable String rightArm) {
            this.rightArm = rightArm;
            return this;
        }

        public Builder leftArm(@Nullable String leftArm) {
            this.leftArm = leftArm;
            return this;
        }

        public Builder rightLeg(@Nullable String rightLeg) {
            this.rightLeg = rightLeg;
            return this;
        }

        public Builder leftLeg(@Nullable String leftLeg) {
            this.leftLeg = leftLeg;
            return this;
        }

        public Builder rightBoot(@Nullable String rightBoot) {
            this.rightBoot = rightBoot;
            return this;
        }

        public Builder leftBoot(@Nullable String leftBoot) {
            this.leftBoot = leftBoot;
            return this;
        }

        public ArmorBonesSpec build() {
            return new ArmorBonesSpec(this);
        }
    }
}
