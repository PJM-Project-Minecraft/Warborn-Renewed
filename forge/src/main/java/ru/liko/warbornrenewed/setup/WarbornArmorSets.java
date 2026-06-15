package ru.liko.warbornrenewed.setup;

import net.minecraft.world.item.Rarity;
import ru.liko.warbornrenewed.content.armorset.ArmorAttributeSpec;
import ru.liko.warbornrenewed.content.armorset.WarbornArmorRegistry;
import ru.liko.warbornrenewed.content.armorset.WarbornArmorSet;
import ru.liko.warbornrenewed.registry.ModArmorMaterials;

/**
 * ========================================
 * Р Р•Р“РРЎРўР РђР¦РРЇ РќРђР‘РћР РћР’ Р‘Р РћРќР WARBORN RENEWED
 * ========================================
 * 
 * РЎС‚СЂСѓРєС‚СѓСЂР°:
 * - РЁР›Р•РњР« (Р±РµР· Р·Р°С‰РёС‚С‹ С‚РµР»Р°)
 * - Р‘Р РћРќР•Р–РР›Р•РўР« (Р±РµР· С€Р»РµРјРѕРІ)
 * - РќРђР‘РћР Р« РЎ NVG (С€Р»РµРјС‹ СЃ РїСЂРёР±РѕСЂР°РјРё РЅРѕС‡РЅРѕРіРѕ РІРёРґРµРЅРёСЏ)
 * - РџР РћР§РР• РќРђР‘РћР Р«
 */
public class WarbornArmorSets {
    
    public static void bootstrap() {
        // ==================== РЁР›Р•РњР« ====================
        register6B47Helmets();      // Р РѕСЃСЃРёР№СЃРєРёР№ С€Р»РµРј 6Р‘47
        registerOpscoreHelmets();   // Ops-Core С€Р»РµРјС‹
        registerPanamaHelmets();    // РџР°РЅР°РјС‹
        registerPASTGTHelmets();    // PASGT С€Р»РµРјС‹
        
        // ==================== Р‘Р РћРќР•Р–РР›Р•РўР« ====================
        register6B45Vests();        // Р РѕСЃСЃРёР№СЃРєРёР№ Р±СЂРѕРЅРµР¶РёР»РµС‚ 6Р‘45
        registerIOTVVests();        // IOTV Р±СЂРѕРЅРµР¶РёР»РµС‚С‹
        registerWarmorVests();      // Warmor Р±СЂРѕРЅРµР¶РёР»РµС‚С‹
        registerJPCVests();         // JPC Р±СЂРѕРЅРµР¶РёР»РµС‚С‹
        registerUWINVests();        // UWIN Р±СЂРѕРЅРµР¶РёР»РµС‚С‹
        registerNatoArmor();        // NATO С€Р»РµРјС‹/Р¶РёР»РµС‚С‹
        
        // ==================== РЁР›Р•РњР« РЎ NVG ====================
        registerNVGHelmets();
        
        // ==================== РџР РћР§РР• РќРђР‘РћР Р« ====================
        registerPressSet();
        registerBandageSet();
        registerGhillieSet();
    }

    // ==================================================================================
    //                                    РЁР›Р•РњР«
    // ==================================================================================

    /**
     * Р РѕСЃСЃРёР№СЃРєРёР№ С€Р»РµРј 6Р‘47 - СЂР°Р·Р»РёС‡РЅС‹Рµ РєР°РјСѓС„Р»СЏР¶Рё
     */
    private static void register6B47Helmets() {
        WarbornArmorRegistry.registerSet(
                WarbornArmorSet.builder("6b47-helmets")
                        // ATACS-FG
                        .helmet(piece -> piece
                                .registryName("6b47-atacsfg")
                                .material(type -> ModArmorMaterials.KEVLAR)
                                .visuals(spec -> spec
                                        .model("warbornrenewed:geo/6b47.geo.json")
                                        .texture("warbornrenewed:textures/6b47/6b47-atacsfg.png"))
                                .properties(props -> props.stacksTo(1).rarity(Rarity.COMMON))
                                .bulletResistance(0.35D)
                                .attribute(ArmorAttributeSpec.protectionClass(1))
                                .attribute(ArmorAttributeSpec.blastResistance(0.02D))
                                .attribute(ArmorAttributeSpec.movementSpeed(-0.02D)))
                        // Desert
                        .helmet(piece -> piece
                                .registryName("6b47-desert")
                                .material(type -> ModArmorMaterials.KEVLAR)
                                .visuals(spec -> spec
                                        .model("warbornrenewed:geo/6b47.geo.json")
                                        .texture("warbornrenewed:textures/6b47/6b47-desert.png"))
                                .properties(props -> props.stacksTo(1).rarity(Rarity.COMMON))
                                .bulletResistance(0.35D)
                                .attribute(ArmorAttributeSpec.protectionClass(1))
                                .attribute(ArmorAttributeSpec.blastResistance(0.02D))
                                .attribute(ArmorAttributeSpec.movementSpeed(-0.02D)))
                        // EMR
                        .helmet(piece -> piece
                                .registryName("6b47-emr")
                                .material(type -> ModArmorMaterials.KEVLAR)
                                .visuals(spec -> spec
                                        .model("warbornrenewed:geo/6b47.geo.json")
                                        .texture("warbornrenewed:textures/6b47/6b47-emr.png"))
                                .properties(props -> props.stacksTo(1).rarity(Rarity.COMMON))
                                .bulletResistance(0.35D)
                                .attribute(ArmorAttributeSpec.protectionClass(1))
                                .attribute(ArmorAttributeSpec.blastResistance(0.02D))
                                .attribute(ArmorAttributeSpec.movementSpeed(-0.02D)))
                        // Green
                        .helmet(piece -> piece
                                .registryName("6b47-green")
                                .material(type -> ModArmorMaterials.KEVLAR)
                                .visuals(spec -> spec
                                        .model("warbornrenewed:geo/6b47.geo.json")
                                        .texture("warbornrenewed:textures/6b47/6b47-green.png"))
                                .properties(props -> props.stacksTo(1).rarity(Rarity.COMMON))
                                .bulletResistance(0.35D)
                                .attribute(ArmorAttributeSpec.protectionClass(1))
                                .attribute(ArmorAttributeSpec.blastResistance(0.02D))
                                .attribute(ArmorAttributeSpec.movementSpeed(-0.02D)))
                        // Winter EMR
                        .helmet(piece -> piece
                                .registryName("6b47-winteremr")
                                .material(type -> ModArmorMaterials.KEVLAR)
                                .visuals(spec -> spec
                                        .model("warbornrenewed:geo/6b47.geo.json")
                                        .texture("warbornrenewed:textures/6b47/6b47-winteremr.png"))
                                .properties(props -> props.stacksTo(1).rarity(Rarity.COMMON))
                                .bulletResistance(0.35D)
                                .attribute(ArmorAttributeSpec.protectionClass(1))
                                .attribute(ArmorAttributeSpec.blastResistance(0.02D))
                                .attribute(ArmorAttributeSpec.movementSpeed(-0.02D)))
        );
    }

    /**
     * Ops-Core С€Р»РµРјС‹ - СЂР°Р·Р»РёС‡РЅС‹Рµ РєР°РјСѓС„Р»СЏР¶Рё
     */
    private static void registerOpscoreHelmets() {
        WarbornArmorRegistry.registerSet(
                WarbornArmorSet.builder("opscore-helmets")
                        // Standard
                        .helmet(piece -> piece
                                .registryName("opscore-standard")
                                .material(type -> ModArmorMaterials.KEVLAR)
                                .visuals(spec -> spec
                                        .model("warbornrenewed:geo/opscore.geo.json")
                                        .texture("warbornrenewed:textures/opscore/opscore.png"))
                                .properties(props -> props.stacksTo(1).rarity(Rarity.UNCOMMON))
                                .bulletResistance(0.40D)
                                .attribute(ArmorAttributeSpec.protectionClass(2))
                                .attribute(ArmorAttributeSpec.blastResistance(0.03D))
                                .attribute(ArmorAttributeSpec.movementSpeed(-0.02D)))
                        // ATACS-FG
                        .helmet(piece -> piece
                                .registryName("opscore-atacsfg")
                                .material(type -> ModArmorMaterials.KEVLAR)
                                .visuals(spec -> spec
                                        .model("warbornrenewed:geo/opscore.geo.json")
                                        .texture("warbornrenewed:textures/opscore/opscore-atacsfg.png"))
                                .properties(props -> props.stacksTo(1).rarity(Rarity.UNCOMMON))
                                .bulletResistance(0.40D)
                                .attribute(ArmorAttributeSpec.protectionClass(2))
                                .attribute(ArmorAttributeSpec.blastResistance(0.03D))
                                .attribute(ArmorAttributeSpec.movementSpeed(-0.02D)))
                        // Black
                        .helmet(piece -> piece
                                .registryName("opscore-black")
                                .material(type -> ModArmorMaterials.KEVLAR)
                                .visuals(spec -> spec
                                        .model("warbornrenewed:geo/opscore.geo.json")
                                        .texture("warbornrenewed:textures/opscore/opscore-black.png"))
                                .properties(props -> props.stacksTo(1).rarity(Rarity.UNCOMMON))
                                .bulletResistance(0.40D)
                                .attribute(ArmorAttributeSpec.protectionClass(2))
                                .attribute(ArmorAttributeSpec.blastResistance(0.03D))
                                .attribute(ArmorAttributeSpec.movementSpeed(-0.02D)))
                        // Desert
                        .helmet(piece -> piece
                                .registryName("opscore-desert")
                                .material(type -> ModArmorMaterials.KEVLAR)
                                .visuals(spec -> spec
                                        .model("warbornrenewed:geo/opscore.geo.json")
                                        .texture("warbornrenewed:textures/opscore/opscore-desert.png"))
                                .properties(props -> props.stacksTo(1).rarity(Rarity.UNCOMMON))
                                .bulletResistance(0.40D)
                                .attribute(ArmorAttributeSpec.protectionClass(2))
                                .attribute(ArmorAttributeSpec.blastResistance(0.03D))
                                .attribute(ArmorAttributeSpec.movementSpeed(-0.02D)))
                        // EMR
                        .helmet(piece -> piece
                                .registryName("opscore-emr")
                                .material(type -> ModArmorMaterials.KEVLAR)
                                .visuals(spec -> spec
                                        .model("warbornrenewed:geo/opscore.geo.json")
                                        .texture("warbornrenewed:textures/opscore/opscore-emr.png"))
                                .properties(props -> props.stacksTo(1).rarity(Rarity.UNCOMMON))
                                .bulletResistance(0.40D)
                                .attribute(ArmorAttributeSpec.protectionClass(2))
                                .attribute(ArmorAttributeSpec.blastResistance(0.03D))
                                .attribute(ArmorAttributeSpec.movementSpeed(-0.02D)))
                        // Green
                        .helmet(piece -> piece
                                .registryName("opscore-green")
                                .material(type -> ModArmorMaterials.KEVLAR)
                                .visuals(spec -> spec
                                        .model("warbornrenewed:geo/opscore.geo.json")
                                        .texture("warbornrenewed:textures/opscore/opscore-green.png"))
                                .properties(props -> props.stacksTo(1).rarity(Rarity.UNCOMMON))
                                .bulletResistance(0.40D)
                                .attribute(ArmorAttributeSpec.protectionClass(2))
                                .attribute(ArmorAttributeSpec.blastResistance(0.03D))
                                .attribute(ArmorAttributeSpec.movementSpeed(-0.02D)))
                        // MM14
                        .helmet(piece -> piece
                                .registryName("opscore-mm14")
                                .material(type -> ModArmorMaterials.KEVLAR)
                                .visuals(spec -> spec
                                        .model("warbornrenewed:geo/opscore.geo.json")
                                        .texture("warbornrenewed:textures/opscore/opscore-mm14.png"))
                                .properties(props -> props.stacksTo(1).rarity(Rarity.UNCOMMON))
                                .bulletResistance(0.40D)
                                .attribute(ArmorAttributeSpec.protectionClass(2))
                                .attribute(ArmorAttributeSpec.blastResistance(0.03D))
                                .attribute(ArmorAttributeSpec.movementSpeed(-0.02D)))
                        // Multicam
                        .helmet(piece -> piece
                                .registryName("opscore-multicam")
                                .material(type -> ModArmorMaterials.KEVLAR)
                                .visuals(spec -> spec
                                        .model("warbornrenewed:geo/opscore.geo.json")
                                        .texture("warbornrenewed:textures/opscore/opscore-multicam.png"))
                                .properties(props -> props.stacksTo(1).rarity(Rarity.UNCOMMON))
                                .bulletResistance(0.40D)
                                .attribute(ArmorAttributeSpec.protectionClass(2))
                                .attribute(ArmorAttributeSpec.blastResistance(0.03D))
                                .attribute(ArmorAttributeSpec.movementSpeed(-0.02D)))
                        // White
                        .helmet(piece -> piece
                                .registryName("opscore-white")
                                .material(type -> ModArmorMaterials.KEVLAR)
                                .visuals(spec -> spec
                                        .model("warbornrenewed:geo/opscore.geo.json")
                                        .texture("warbornrenewed:textures/opscore/opscore-white.png"))
                                .properties(props -> props.stacksTo(1).rarity(Rarity.UNCOMMON))
                                .bulletResistance(0.40D)
                                .attribute(ArmorAttributeSpec.protectionClass(2))
                                .attribute(ArmorAttributeSpec.blastResistance(0.03D))
                                .attribute(ArmorAttributeSpec.movementSpeed(-0.02D)))
        );
    }

    /**
     * РџР°РЅР°РјС‹ - СЂР°Р·Р»РёС‡РЅС‹Рµ РєР°РјСѓС„Р»СЏР¶Рё (Р±РµР· Р±Р°Р»Р»РёСЃС‚РёС‡РµСЃРєРѕР№ Р·Р°С‰РёС‚С‹)
     */
    private static void registerPanamaHelmets() {
        WarbornArmorRegistry.registerSet(
                WarbornArmorSet.builder("panama-helmets")
                        // ATACS-FG
                        .helmet(piece -> piece
                                .registryName("panama-atacsfg")
                                .material(type -> ModArmorMaterials.LEATHER)
                                .visuals(spec -> spec
                                        .model("warbornrenewed:geo/panama.geo.json")
                                        .texture("warbornrenewed:textures/panama/panama-atacsfg.png"))
                                .properties(props -> props.stacksTo(1).rarity(Rarity.COMMON))
                                .bulletResistance(0.0D)
                                .attribute(ArmorAttributeSpec.protectionClass(0))
                                .attribute(ArmorAttributeSpec.movementSpeed(0.0D)))
                        // Desert
                        .helmet(piece -> piece
                                .registryName("panama-desert")
                                .material(type -> ModArmorMaterials.LEATHER)
                                .visuals(spec -> spec
                                        .model("warbornrenewed:geo/panama.geo.json")
                                        .texture("warbornrenewed:textures/panama/panama-desert.png"))
                                .properties(props -> props.stacksTo(1).rarity(Rarity.COMMON))
                                .bulletResistance(0.0D)
                                .attribute(ArmorAttributeSpec.protectionClass(0))
                                .attribute(ArmorAttributeSpec.movementSpeed(0.0D)))
                        // EMR
                        .helmet(piece -> piece
                                .registryName("panama-emr")
                                .material(type -> ModArmorMaterials.LEATHER)
                                .visuals(spec -> spec
                                        .model("warbornrenewed:geo/panama.geo.json")
                                        .texture("warbornrenewed:textures/panama/panama-emr.png"))
                                .properties(props -> props.stacksTo(1).rarity(Rarity.COMMON))
                                .bulletResistance(0.0D)
                                .attribute(ArmorAttributeSpec.protectionClass(0))
                                .attribute(ArmorAttributeSpec.movementSpeed(0.0D)))
                        // Green
                        .helmet(piece -> piece
                                .registryName("panama-green")
                                .material(type -> ModArmorMaterials.LEATHER)
                                .visuals(spec -> spec
                                        .model("warbornrenewed:geo/panama.geo.json")
                                        .texture("warbornrenewed:textures/panama/panama-green.png"))
                                .properties(props -> props.stacksTo(1).rarity(Rarity.COMMON))
                                .bulletResistance(0.0D)
                                .attribute(ArmorAttributeSpec.protectionClass(0))
                                .attribute(ArmorAttributeSpec.movementSpeed(0.0D)))
                        // Multicam
                        .helmet(piece -> piece
                                .registryName("panama-multicam")
                                .material(type -> ModArmorMaterials.LEATHER)
                                .visuals(spec -> spec
                                        .model("warbornrenewed:geo/panama.geo.json")
                                        .texture("warbornrenewed:textures/panama/panama-multicam.png"))
                                .properties(props -> props.stacksTo(1).rarity(Rarity.COMMON))
                                .bulletResistance(0.0D)
                                .attribute(ArmorAttributeSpec.protectionClass(0))
                                .attribute(ArmorAttributeSpec.movementSpeed(0.0D)))
                        // UCP
                        .helmet(piece -> piece
                                .registryName("panama-ucp")
                                .material(type -> ModArmorMaterials.LEATHER)
                                .visuals(spec -> spec
                                        .model("warbornrenewed:geo/panama.geo.json")
                                        .texture("warbornrenewed:textures/panama/panama-ucp.png"))
                                .properties(props -> props.stacksTo(1).rarity(Rarity.COMMON))
                                .bulletResistance(0.0D)
                                .attribute(ArmorAttributeSpec.protectionClass(0))
                                .attribute(ArmorAttributeSpec.movementSpeed(0.0D)))
                        // White
                        .helmet(piece -> piece
                                .registryName("panama-white")
                                .material(type -> ModArmorMaterials.LEATHER)
                                .visuals(spec -> spec
                                        .model("warbornrenewed:geo/panama.geo.json")
                                        .texture("warbornrenewed:textures/panama/panama-white.png"))
                                .properties(props -> props.stacksTo(1).rarity(Rarity.COMMON))
                                .bulletResistance(0.0D)
                                .attribute(ArmorAttributeSpec.protectionClass(0))
                                .attribute(ArmorAttributeSpec.movementSpeed(0.0D)))
        );
    }

    /**
     * PASGT С€Р»РµРјС‹ - СЂР°Р·Р»РёС‡РЅС‹Рµ РєР°РјСѓС„Р»СЏР¶Рё
     */
    private static void registerPASTGTHelmets() {
        WarbornArmorRegistry.registerSet(
                WarbornArmorSet.builder("pastgt-helmets")
                        // Black
                        .helmet(piece -> piece
                                .registryName("pastgt-black")
                                .material(type -> ModArmorMaterials.KEVLAR)
                                .visuals(spec -> spec
                                        .model("warbornrenewed:geo/pastgt.geo.json")
                                        .texture("warbornrenewed:textures/pastgt/pastgt-black.png"))
                                .properties(props -> props.stacksTo(1).rarity(Rarity.COMMON))
                                .bulletResistance(0.30D)
                                .attribute(ArmorAttributeSpec.protectionClass(1))
                                .attribute(ArmorAttributeSpec.blastResistance(0.02D))
                                .attribute(ArmorAttributeSpec.movementSpeed(-0.02D)))
                        // Blue
                        .helmet(piece -> piece
                                .registryName("pastgt-blue")
                                .material(type -> ModArmorMaterials.KEVLAR)
                                .visuals(spec -> spec
                                        .model("warbornrenewed:geo/pastgt.geo.json")
                                        .texture("warbornrenewed:textures/pastgt/pastgt-blue.png"))
                                .properties(props -> props.stacksTo(1).rarity(Rarity.COMMON))
                                .bulletResistance(0.30D)
                                .attribute(ArmorAttributeSpec.protectionClass(1))
                                .attribute(ArmorAttributeSpec.blastResistance(0.02D))
                                .attribute(ArmorAttributeSpec.movementSpeed(-0.02D)))
                        // Desert
                        .helmet(piece -> piece
                                .registryName("pastgt-desert")
                                .material(type -> ModArmorMaterials.KEVLAR)
                                .visuals(spec -> spec
                                        .model("warbornrenewed:geo/pastgt.geo.json")
                                        .texture("warbornrenewed:textures/pastgt/pastgt-desert.png"))
                                .properties(props -> props.stacksTo(1).rarity(Rarity.COMMON))
                                .bulletResistance(0.30D)
                                .attribute(ArmorAttributeSpec.protectionClass(1))
                                .attribute(ArmorAttributeSpec.blastResistance(0.02D))
                                .attribute(ArmorAttributeSpec.movementSpeed(-0.02D)))
                        // Green
                        .helmet(piece -> piece
                                .registryName("pastgt-green")
                                .material(type -> ModArmorMaterials.KEVLAR)
                                .visuals(spec -> spec
                                        .model("warbornrenewed:geo/pastgt.geo.json")
                                        .texture("warbornrenewed:textures/pastgt/pastgt-green.png"))
                                .properties(props -> props.stacksTo(1).rarity(Rarity.COMMON))
                                .bulletResistance(0.30D)
                                .attribute(ArmorAttributeSpec.protectionClass(1))
                                .attribute(ArmorAttributeSpec.blastResistance(0.02D))
                                .attribute(ArmorAttributeSpec.movementSpeed(-0.02D)))
                        // MM14
                        .helmet(piece -> piece
                                .registryName("pastgt-mm14")
                                .material(type -> ModArmorMaterials.KEVLAR)
                                .visuals(spec -> spec
                                        .model("warbornrenewed:geo/pastgt.geo.json")
                                        .texture("warbornrenewed:textures/pastgt/pastgt-mm14.png"))
                                .properties(props -> props.stacksTo(1).rarity(Rarity.COMMON))
                                .bulletResistance(0.30D)
                                .attribute(ArmorAttributeSpec.protectionClass(1))
                                .attribute(ArmorAttributeSpec.blastResistance(0.02D))
                                .attribute(ArmorAttributeSpec.movementSpeed(-0.02D)))
                        // Multicam
                        .helmet(piece -> piece
                                .registryName("pastgt-multicam")
                                .material(type -> ModArmorMaterials.KEVLAR)
                                .visuals(spec -> spec
                                        .model("warbornrenewed:geo/pastgt.geo.json")
                                        .texture("warbornrenewed:textures/pastgt/pastgt-multicam.png"))
                                .properties(props -> props.stacksTo(1).rarity(Rarity.COMMON))
                                .bulletResistance(0.30D)
                                .attribute(ArmorAttributeSpec.protectionClass(1))
                                .attribute(ArmorAttributeSpec.blastResistance(0.02D))
                                .attribute(ArmorAttributeSpec.movementSpeed(-0.02D)))
                        // White
                        .helmet(piece -> piece
                                .registryName("pastgt-white")
                                .material(type -> ModArmorMaterials.KEVLAR)
                                .visuals(spec -> spec
                                        .model("warbornrenewed:geo/pastgt.geo.json")
                                        .texture("warbornrenewed:textures/pastgt/pastgt-white.png"))
                                .properties(props -> props.stacksTo(1).rarity(Rarity.COMMON))
                                .bulletResistance(0.30D)
                                .attribute(ArmorAttributeSpec.protectionClass(1))
                                .attribute(ArmorAttributeSpec.blastResistance(0.02D))
                                .attribute(ArmorAttributeSpec.movementSpeed(-0.02D)))
        );
    }

    // ==================================================================================
    //                                 Р‘Р РћРќР•Р–РР›Р•РўР«
    // ==================================================================================

    /**
     * Р РѕСЃСЃРёР№СЃРєРёР№ Р±СЂРѕРЅРµР¶РёР»РµС‚ 6Р‘45 - СЂР°Р·Р»РёС‡РЅС‹Рµ РєР°РјСѓС„Р»СЏР¶Рё
     */
    private static void register6B45Vests() {
        WarbornArmorRegistry.registerSet(
                WarbornArmorSet.builder("6b45-vests")
                        // Wood (EMR)
                        .chestplate(piece -> piece
                                .registryName("6b45-wood")
                                .material(type -> ModArmorMaterials.UHMWPE)
                                .visuals(spec -> spec
                                        .model("warbornrenewed:geo/ratnik-chest.geo.json")
                                        .texture("warbornrenewed:textures/ratnik-wood.png"))
                                .bones(bones -> bones
                                        .body("armorBody")
                                        .rightArm("armorRightArm")
                                        .leftArm("armorLeftArm"))
                                .properties(props -> props.stacksTo(1).rarity(Rarity.UNCOMMON))
                                .bulletResistance(0.55D)
                                .attribute(ArmorAttributeSpec.protectionClass(5))
                                .attribute(ArmorAttributeSpec.blastResistance(0.50D))
                                .attribute(ArmorAttributeSpec.movementSpeed(-0.08D)))
                        // Desert
                        .chestplate(piece -> piece
                                .registryName("6b45-desert")
                                .material(type -> ModArmorMaterials.UHMWPE)
                                .visuals(spec -> spec
                                        .model("warbornrenewed:geo/ratnik-chest-desert.geo.json")
                                        .texture("warbornrenewed:textures/ratnik-desert.png"))
                                .bones(bones -> bones
                                        .body("armorBody")
                                        .rightArm("armorRightArm")
                                        .leftArm("armorLeftArm"))
                                .properties(props -> props.stacksTo(1).rarity(Rarity.UNCOMMON))
                                .bulletResistance(0.55D)
                                .attribute(ArmorAttributeSpec.protectionClass(5))
                                .attribute(ArmorAttributeSpec.blastResistance(0.50D))
                                .attribute(ArmorAttributeSpec.movementSpeed(-0.08D)))
        );
    }

    /**
     * IOTV Р±СЂРѕРЅРµР¶РёР»РµС‚С‹ - СЂР°Р·Р»РёС‡РЅС‹Рµ РєР°РјСѓС„Р»СЏР¶Рё
     */
    private static void registerIOTVVests() {
        WarbornArmorRegistry.registerSet(
                WarbornArmorSet.builder("iotv-vests")
                        // Black
                        .chestplate(piece -> piece
                                .registryName("iotv-black")
                                .material(type -> ModArmorMaterials.CERAMIC)
                                .visuals(spec -> spec
                                        .model("warbornrenewed:geo/iotv.geo.json")
                                        .texture("warbornrenewed:textures/iotv/iotv-black.png"))
                                .bones(bones -> bones
                                        .body("armorBody"))
                                .properties(props -> props.stacksTo(1).rarity(Rarity.UNCOMMON))
                                .bulletResistance(0.50D)
                                .attribute(ArmorAttributeSpec.protectionClass(4))
                                .attribute(ArmorAttributeSpec.blastResistance(0.40D))
                                .attribute(ArmorAttributeSpec.movementSpeed(-0.07D)))
                        // Desert
                        .chestplate(piece -> piece
                                .registryName("iotv-desert")
                                .material(type -> ModArmorMaterials.CERAMIC)
                                .visuals(spec -> spec
                                        .model("warbornrenewed:geo/iotv.geo.json")
                                        .texture("warbornrenewed:textures/iotv/iotv-desert.png"))
                                .bones(bones -> bones
                                        .body("armorBody"))
                                .properties(props -> props.stacksTo(1).rarity(Rarity.UNCOMMON))
                                .bulletResistance(0.50D)
                                .attribute(ArmorAttributeSpec.protectionClass(4))
                                .attribute(ArmorAttributeSpec.blastResistance(0.40D))
                                .attribute(ArmorAttributeSpec.movementSpeed(-0.07D)))
                        // Green
                        .chestplate(piece -> piece
                                .registryName("iotv-green")
                                .material(type -> ModArmorMaterials.CERAMIC)
                                .visuals(spec -> spec
                                        .model("warbornrenewed:geo/iotv.geo.json")
                                        .texture("warbornrenewed:textures/iotv/iotv-green.png"))
                                .bones(bones -> bones
                                        .body("armorBody"))
                                .properties(props -> props.stacksTo(1).rarity(Rarity.UNCOMMON))
                                .bulletResistance(0.50D)
                                .attribute(ArmorAttributeSpec.protectionClass(4))
                                .attribute(ArmorAttributeSpec.blastResistance(0.40D))
                                .attribute(ArmorAttributeSpec.movementSpeed(-0.07D)))
                        // Multicam
                        .chestplate(piece -> piece
                                .registryName("iotv-multicam")
                                .material(type -> ModArmorMaterials.CERAMIC)
                                .visuals(spec -> spec
                                        .model("warbornrenewed:geo/iotv.geo.json")
                                        .texture("warbornrenewed:textures/iotv/iotv-multicam.png"))
                                .bones(bones -> bones
                                        .body("armorBody"))
                                .properties(props -> props.stacksTo(1).rarity(Rarity.UNCOMMON))
                                .bulletResistance(0.50D)
                                .attribute(ArmorAttributeSpec.protectionClass(4))
                                .attribute(ArmorAttributeSpec.blastResistance(0.40D))
                                .attribute(ArmorAttributeSpec.movementSpeed(-0.07D)))
                        // UCP
                        .chestplate(piece -> piece
                                .registryName("iotv-ucp")
                                .material(type -> ModArmorMaterials.CERAMIC)
                                .visuals(spec -> spec
                                        .model("warbornrenewed:geo/iotv.geo.json")
                                        .texture("warbornrenewed:textures/iotv/iotv-ucp.png"))
                                .bones(bones -> bones
                                        .body("armorBody"))
                                .properties(props -> props.stacksTo(1).rarity(Rarity.UNCOMMON))
                                .bulletResistance(0.50D)
                                .attribute(ArmorAttributeSpec.protectionClass(4))
                                .attribute(ArmorAttributeSpec.blastResistance(0.40D))
                                .attribute(ArmorAttributeSpec.movementSpeed(-0.07D)))
                        // White
                        .chestplate(piece -> piece
                                .registryName("iotv-white")
                                .material(type -> ModArmorMaterials.CERAMIC)
                                .visuals(spec -> spec
                                        .model("warbornrenewed:geo/iotv.geo.json")
                                        .texture("warbornrenewed:textures/iotv/iotv-white.png"))
                                .bones(bones -> bones
                                        .body("armorBody"))
                                .properties(props -> props.stacksTo(1).rarity(Rarity.UNCOMMON))
                                .bulletResistance(0.50D)
                                .attribute(ArmorAttributeSpec.protectionClass(4))
                                .attribute(ArmorAttributeSpec.blastResistance(0.40D))
                                .attribute(ArmorAttributeSpec.movementSpeed(-0.07D)))
        );
    }

    /**
     * Warmor Р±СЂРѕРЅРµР¶РёР»РµС‚С‹ Gen 3 - СЂР°Р·Р»РёС‡РЅС‹Рµ РєР°РјСѓС„Р»СЏР¶Рё
     */
    private static void registerWarmorVests() {
        WarbornArmorRegistry.registerSet(
                WarbornArmorSet.builder("warmor-vests")
                        // Black
                        .chestplate(piece -> piece
                                .registryName("warmor-black")
                                .material(type -> ModArmorMaterials.COMPOSITE)
                                .visuals(spec -> spec
                                        .model("warbornrenewed:geo/warmor-gen.3.geo.json")
                                        .texture("warbornrenewed:textures/warmor/warmor-black.png"))
                                .bones(bones -> bones
                                        .body("armorBody"))
                                .properties(props -> props.stacksTo(1).rarity(Rarity.RARE))
                                .bulletResistance(0.60D)
                                .attribute(ArmorAttributeSpec.protectionClass(5))
                                .attribute(ArmorAttributeSpec.blastResistance(0.45D))
                                .attribute(ArmorAttributeSpec.movementSpeed(-0.06D)))
                        // Desert
                        .chestplate(piece -> piece
                                .registryName("warmor-desert")
                                .material(type -> ModArmorMaterials.COMPOSITE)
                                .visuals(spec -> spec
                                        .model("warbornrenewed:geo/warmor-gen.3.geo.json")
                                        .texture("warbornrenewed:textures/warmor/warmor-desert.png"))
                                .bones(bones -> bones
                                        .body("armorBody"))
                                .properties(props -> props.stacksTo(1).rarity(Rarity.RARE))
                                .bulletResistance(0.60D)
                                .attribute(ArmorAttributeSpec.protectionClass(5))
                                .attribute(ArmorAttributeSpec.blastResistance(0.45D))
                                .attribute(ArmorAttributeSpec.movementSpeed(-0.06D)))
                        // Green
                        .chestplate(piece -> piece
                                .registryName("warmor-green")
                                .material(type -> ModArmorMaterials.COMPOSITE)
                                .visuals(spec -> spec
                                        .model("warbornrenewed:geo/warmor-gen.3.geo.json")
                                        .texture("warbornrenewed:textures/warmor/warmor-green.png"))
                                .bones(bones -> bones
                                        .body("armorBody"))
                                .properties(props -> props.stacksTo(1).rarity(Rarity.RARE))
                                .bulletResistance(0.60D)
                                .attribute(ArmorAttributeSpec.protectionClass(5))
                                .attribute(ArmorAttributeSpec.blastResistance(0.45D))
                                .attribute(ArmorAttributeSpec.movementSpeed(-0.06D)))
                        // MM14
                        .chestplate(piece -> piece
                                .registryName("warmor-mm14")
                                .material(type -> ModArmorMaterials.COMPOSITE)
                                .visuals(spec -> spec
                                        .model("warbornrenewed:geo/warmor-gen.3.geo.json")
                                        .texture("warbornrenewed:textures/warmor/warmor-mm14.png"))
                                .bones(bones -> bones
                                        .body("armorBody"))
                                .properties(props -> props.stacksTo(1).rarity(Rarity.RARE))
                                .bulletResistance(0.60D)
                                .attribute(ArmorAttributeSpec.protectionClass(5))
                                .attribute(ArmorAttributeSpec.blastResistance(0.45D))
                                .attribute(ArmorAttributeSpec.movementSpeed(-0.06D)))
                        // Multicam
                        .chestplate(piece -> piece
                                .registryName("warmor-multicam")
                                .material(type -> ModArmorMaterials.COMPOSITE)
                                .visuals(spec -> spec
                                        .model("warbornrenewed:geo/warmor-gen.3.geo.json")
                                        .texture("warbornrenewed:textures/warmor/warmor-multicam.png"))
                                .bones(bones -> bones
                                        .body("armorBody"))
                                .properties(props -> props.stacksTo(1).rarity(Rarity.RARE))
                                .bulletResistance(0.60D)
                                .attribute(ArmorAttributeSpec.protectionClass(5))
                                .attribute(ArmorAttributeSpec.blastResistance(0.45D))
                                .attribute(ArmorAttributeSpec.movementSpeed(-0.06D)))
                        // UCP
                        .chestplate(piece -> piece
                                .registryName("warmor-ucp")
                                .material(type -> ModArmorMaterials.COMPOSITE)
                                .visuals(spec -> spec
                                        .model("warbornrenewed:geo/warmor-gen.3.geo.json")
                                        .texture("warbornrenewed:textures/warmor/warmor-ucp.png"))
                                .bones(bones -> bones
                                        .body("armorBody"))
                                .properties(props -> props.stacksTo(1).rarity(Rarity.RARE))
                                .bulletResistance(0.60D)
                                .attribute(ArmorAttributeSpec.protectionClass(5))
                                .attribute(ArmorAttributeSpec.blastResistance(0.45D))
                                .attribute(ArmorAttributeSpec.movementSpeed(-0.06D)))
                        // White
                        .chestplate(piece -> piece
                                .registryName("warmor-white")
                                .material(type -> ModArmorMaterials.COMPOSITE)
                                .visuals(spec -> spec
                                        .model("warbornrenewed:geo/warmor-gen.3.geo.json")
                                        .texture("warbornrenewed:textures/warmor/warmor-white.png"))
                                .bones(bones -> bones
                                        .body("armorBody"))
                                .properties(props -> props.stacksTo(1).rarity(Rarity.RARE))
                                .bulletResistance(0.60D)
                                .attribute(ArmorAttributeSpec.protectionClass(5))
                                .attribute(ArmorAttributeSpec.blastResistance(0.45D))
                                .attribute(ArmorAttributeSpec.movementSpeed(-0.06D)))
        );
    }

    /**
     * JPC Р±СЂРѕРЅРµР¶РёР»РµС‚С‹
     */
    private static void registerJPCVests() {
        WarbornArmorRegistry.registerSet(
                WarbornArmorSet.builder("jpc-vests")
                        // Standard
                        .chestplate(piece -> piece
                                .registryName("jpc")
                                .material(type -> ModArmorMaterials.AR500_STEEL)
                                .visuals(spec -> spec
                                        .model("warbornrenewed:geo/jpc.geo.json")
                                        .texture("warbornrenewed:textures/jpc-fma.png"))
                                .bones(bones -> bones
                                        .body("armorBody")
                                        .rightArm("armorRightArm")
                                        .leftArm("armorLeftArm"))
                                .properties(props -> props.stacksTo(1).rarity(Rarity.RARE))
                                .bulletResistance(0.55D)
                                .attribute(ArmorAttributeSpec.protectionClass(4))
                                .attribute(ArmorAttributeSpec.blastResistance(0.20D))
                                .attribute(ArmorAttributeSpec.movementSpeed(-0.05D)))
                        // Desert
                        .chestplate(piece -> piece
                                .registryName("jpc-desert")
                                .material(type -> ModArmorMaterials.AR500_STEEL)
                                .visuals(spec -> spec
                                        .model("warbornrenewed:geo/jpc.geo.json")
                                        .texture("warbornrenewed:textures/jpc-fma-desert.png"))
                                .bones(bones -> bones
                                        .body("armorBody")
                                        .rightArm("armorRightArm")
                                        .leftArm("armorLeftArm"))
                                .properties(props -> props.stacksTo(1).rarity(Rarity.RARE))
                                .bulletResistance(0.55D)
                                .attribute(ArmorAttributeSpec.protectionClass(4))
                                .attribute(ArmorAttributeSpec.blastResistance(0.20D))
                                .attribute(ArmorAttributeSpec.movementSpeed(-0.05D)))
        );
    }

    /**
     * UWIN Р±СЂРѕРЅРµР¶РёР»РµС‚С‹
     */
    private static void registerUWINVests() {
        WarbornArmorRegistry.registerSet(
                WarbornArmorSet.builder("uwin-vests")
                        // Standard
                        .chestplate(piece -> piece
                                .registryName("uwin")
                                .material(type -> ModArmorMaterials.CERAMIC)
                                .visuals(spec -> spec
                                        .model("warbornrenewed:geo/uwin.geo.json")
                                        .texture("warbornrenewed:textures/opscore/opscore-uwin.png"))
                                .bones(bones -> bones
                                        .body("armorBody")
                                        .rightArm("armorRightArm")
                                        .leftArm("armorLeftArm"))
                                .properties(props -> props.stacksTo(1).rarity(Rarity.EPIC))
                                .bulletResistance(0.60D)
                                .attribute(ArmorAttributeSpec.protectionClass(5))
                                .attribute(ArmorAttributeSpec.blastResistance(0.10D))
                                .attribute(ArmorAttributeSpec.movementSpeed(-0.04D)))
                        // Desert
                        .chestplate(piece -> piece
                                .registryName("uwin-desert")
                                .material(type -> ModArmorMaterials.CERAMIC)
                                .visuals(spec -> spec
                                        .model("warbornrenewed:geo/uwin.geo.json")
                                        .texture("warbornrenewed:textures/opscore/opscore-uwin-desert.png"))
                                .bones(bones -> bones
                                        .body("armorBody")
                                        .rightArm("armorRightArm")
                                        .leftArm("armorLeftArm"))
                                .properties(props -> props.stacksTo(1).rarity(Rarity.EPIC))
                                .bulletResistance(0.60D)
                                .attribute(ArmorAttributeSpec.protectionClass(5))
                                .attribute(ArmorAttributeSpec.blastResistance(0.10D))
                                .attribute(ArmorAttributeSpec.movementSpeed(-0.04D)))
        );
    }

    /**
     * NATO РЅР°Р±РѕСЂ (С€Р»РµРј + Р±СЂРѕРЅРµР¶РёР»РµС‚) вЂ” РІР°СЂРёР°РЅС‚С‹ Wood/Sand.
     * Р РµСЃСѓСЂСЃС‹ СѓР¶Рµ РµСЃС‚СЊ РІ assets: geo/nato-helmet-*.geo.json Рё geo/nato-chest*.geo.json.
     */
    private static void registerNatoArmor() {
        // Wood
        WarbornArmorRegistry.registerSet(
                WarbornArmorSet.builder("nato-wood")
                        .helmet(piece -> piece
                                .registryName("nato-wood-helmet")
                                .material(type -> ModArmorMaterials.KEVLAR)
                                .visuals(spec -> spec
                                        .model("warbornrenewed:geo/nato-helmet-wood.geo.json")
                                        .texture("warbornrenewed:textures/nato-wood.png"))
                                .properties(props -> props.stacksTo(1).rarity(Rarity.UNCOMMON))
                                .bulletResistance(0.40D)
                                .attribute(ArmorAttributeSpec.protectionClass(3))
                                .attribute(ArmorAttributeSpec.blastResistance(0.02D))
                                .attribute(ArmorAttributeSpec.movementSpeed(-0.03D)))
                        .chestplate(piece -> piece
                                .registryName("nato-wood-chestplate")
                                .material(type -> ModArmorMaterials.CERAMIC)
                                .visuals(spec -> spec
                                        .model("warbornrenewed:geo/nato-chest.geo.json")
                                        .texture("warbornrenewed:textures/nato-wood.png"))
                                .bones(bones -> bones.body("armorBody"))
                                .properties(props -> props.stacksTo(1).rarity(Rarity.RARE))
                                .bulletResistance(0.50D)
                                .attribute(ArmorAttributeSpec.protectionClass(4))
                                .attribute(ArmorAttributeSpec.blastResistance(0.25D))
                                .attribute(ArmorAttributeSpec.movementSpeed(-0.06D)))
        );

        // Sand
        WarbornArmorRegistry.registerSet(
                WarbornArmorSet.builder("nato-sand")
                        .helmet(piece -> piece
                                .registryName("nato-sand-helmet")
                                .material(type -> ModArmorMaterials.KEVLAR)
                                .visuals(spec -> spec
                                        .model("warbornrenewed:geo/nato-helmet-sand.geo.json")
                                        .texture("warbornrenewed:textures/nato-sand.png"))
                                .properties(props -> props.stacksTo(1).rarity(Rarity.UNCOMMON))
                                .bulletResistance(0.40D)
                                .attribute(ArmorAttributeSpec.protectionClass(3))
                                .attribute(ArmorAttributeSpec.blastResistance(0.02D))
                                .attribute(ArmorAttributeSpec.movementSpeed(-0.03D)))
                        .chestplate(piece -> piece
                                .registryName("nato-sand-chestplate")
                                .material(type -> ModArmorMaterials.CERAMIC)
                                .visuals(spec -> spec
                                        .model("warbornrenewed:geo/nato-chest-sand.geo.json")
                                        .texture("warbornrenewed:textures/nato-sand.png"))
                                .bones(bones -> bones.body("armorBody"))
                                .properties(props -> props.stacksTo(1).rarity(Rarity.RARE))
                                .bulletResistance(0.50D)
                                .attribute(ArmorAttributeSpec.protectionClass(4))
                                .attribute(ArmorAttributeSpec.blastResistance(0.25D))
                                .attribute(ArmorAttributeSpec.movementSpeed(-0.06D)))
        );
    }

    // ==================================================================================
    //                              РЁР›Р•РњР« РЎ NVG
    // ==================================================================================

    /**
     * РЁР»РµРјС‹ СЃ РїСЂРёР±РѕСЂР°РјРё РЅРѕС‡РЅРѕРіРѕ РІРёРґРµРЅРёСЏ
     */
    private static void registerNVGHelmets() {
        WarbornArmorRegistry.registerSet(
                WarbornArmorSet.builder("nvg-helmets")
                        // NATO GPNVG Wood
                        .helmet(piece -> piece
                                .registryName("gpngv-nato-wood")
                                .withNVG()
                                .material(type -> ModArmorMaterials.KEVLAR)
                                .visuals(spec -> spec
                                        .model("warbornrenewed:geo/gpngv-nato-helmet-woodland.geo.json")
                                        .texture("warbornrenewed:textures/nato-wood.png")
                                        .animation("warbornrenewed:animations/gpngv-nato-helmet-woodland.animation.json")
                                        .nvgShader("warbornrenewed:shaders/post/gpngv.json"))
                                .properties(props -> props.stacksTo(1).rarity(Rarity.EPIC))
                                .bulletResistance(0.40D)
                                .attribute(ArmorAttributeSpec.protectionClass(3))
                                .attribute(ArmorAttributeSpec.blastResistance(0.02D))
                                .attribute(ArmorAttributeSpec.movementSpeed(-0.03D)))
                        // NATO GPNVG Desert
                        .helmet(piece -> piece
                                .registryName("gpngv-nato-desert")
                                .withNVG()
                                .material(type -> ModArmorMaterials.KEVLAR)
                                .visuals(spec -> spec
                                        .model("warbornrenewed:geo/gpngv-nato-helmet-woodland.geo.json")
                                        .texture("warbornrenewed:textures/nato-desert.png")
                                        .animation("warbornrenewed:animations/gpngv-nato-helmet-woodland.animation.json")
                                        .nvgShader("warbornrenewed:shaders/post/gpngv.json"))
                                .properties(props -> props.stacksTo(1).rarity(Rarity.EPIC))
                                .bulletResistance(0.40D)
                                .attribute(ArmorAttributeSpec.protectionClass(3))
                                .attribute(ArmorAttributeSpec.blastResistance(0.02D))
                                .attribute(ArmorAttributeSpec.movementSpeed(-0.03D)))
                        // Ratnik 10T Wood
                        .helmet(piece -> piece
                                .registryName("ratnik-10t-wood")
                                .withNVG()
                                .material(type -> ModArmorMaterials.KEVLAR)
                                .visuals(spec -> spec
                                        .model("warbornrenewed:geo/ratnik10t.geo.json")
                                        .texture("warbornrenewed:textures/ratnik-wood.png")
                                        .animation("warbornrenewed:animations/ratnik10t.animation.json")
                                        .nvgShader("warbornrenewed:shaders/post/pnv10t.json"))
                                .properties(props -> props.stacksTo(1).rarity(Rarity.EPIC))
                                .bulletResistance(0.35D)
                                .attribute(ArmorAttributeSpec.protectionClass(2))
                                .attribute(ArmorAttributeSpec.blastResistance(0.02D))
                                .attribute(ArmorAttributeSpec.movementSpeed(-0.03D)))
                        // Ratnik 10T Desert
                        .helmet(piece -> piece
                                .registryName("ratnik-10t-desert")
                                .withNVG()
                                .material(type -> ModArmorMaterials.KEVLAR)
                                .visuals(spec -> spec
                                        .model("warbornrenewed:geo/ratnik10tdesert.geo.json")
                                        .texture("warbornrenewed:textures/ratnik-desert.png")
                                        .animation("warbornrenewed:animations/ratnik10t.animation.json")
                                        .nvgShader("warbornrenewed:shaders/post/pnv10t.json"))
                                .properties(props -> props.stacksTo(1).rarity(Rarity.EPIC))
                                .bulletResistance(0.35D)
                                .attribute(ArmorAttributeSpec.protectionClass(2))
                                .attribute(ArmorAttributeSpec.blastResistance(0.02D))
                                .attribute(ArmorAttributeSpec.movementSpeed(-0.03D)))
                        // Opscore FC-B2200 Voevoda
                        .helmet(piece -> piece
                                .registryName("opscore-fc-b2200-voevoda")
                                .withNVG()
                                .material(type -> ModArmorMaterials.KEVLAR)
                                .visuals(spec -> spec
                                        .model("warbornrenewed:geo/opscore-fc-b2200.geo.json")
                                        .texture("warbornrenewed:textures/opscore/opscore-voevoda.png")
                                        .animation("warbornrenewed:animations/fc-b2200.animation.json")
                                        .nvgShader("warbornrenewed:shaders/post/fc-b2200.json"))
                                .properties(props -> props.stacksTo(1).rarity(Rarity.EPIC))
                                .bulletResistance(0.45D)
                                .attribute(ArmorAttributeSpec.protectionClass(3))
                                .attribute(ArmorAttributeSpec.blastResistance(0.03D))
                                .attribute(ArmorAttributeSpec.movementSpeed(-0.02D)))
                        // 6B47 FC-B2200 SSO
                        .helmet(piece -> piece
                                .registryName("6b47-fc-b2200-sso")
                                .withNVG()
                                .material(type -> ModArmorMaterials.KEVLAR)
                                .visuals(spec -> spec
                                        .model("warbornrenewed:geo/6b47-fc-b2200.geo.json")
                                        .texture("warbornrenewed:textures/sso.png")
                                        .animation("warbornrenewed:animations/6b47-fc-b2200.animation.json")
                                        .nvgShader("warbornrenewed:shaders/post/fc-b2200.json"))
                                .properties(props -> props.stacksTo(1).rarity(Rarity.EPIC))
                                .bulletResistance(0.40D)
                                .attribute(ArmorAttributeSpec.protectionClass(2))
                                .attribute(ArmorAttributeSpec.blastResistance(0.02D))
                                .attribute(ArmorAttributeSpec.movementSpeed(-0.02D)))
        );
    }

    // ==================================================================================
    //                              РџР РћР§РР• РќРђР‘РћР Р«
    // ==================================================================================

    /**
     * РџСЂРµСЃСЃ РЅР°Р±РѕСЂ (РґР»СЏ Р¶СѓСЂРЅР°Р»РёСЃС‚РѕРІ)
     */
    private static void registerPressSet() {
        WarbornArmorRegistry.registerSet(
                WarbornArmorSet.builder("press")
                        .helmet(piece -> piece
                                .registryName("press-helmet")
                                .material(type -> ModArmorMaterials.KEVLAR)
                                .visuals(spec -> spec
                                        .model("warbornrenewed:geo/press-helmet.geo.json")
                                        .texture("warbornrenewed:textures/press.png"))
                                .properties(props -> props.stacksTo(1).rarity(Rarity.COMMON))
                                .bulletResistance(0.30D)
                                .attribute(ArmorAttributeSpec.protectionClass(1))
                                .attribute(ArmorAttributeSpec.blastResistance(0.02D))
                                .attribute(ArmorAttributeSpec.movementSpeed(-0.01D)))
                        .chestplate(piece -> piece
                                .registryName("press-vest")
                                .material(type -> ModArmorMaterials.CERAMIC)
                                .visuals(spec -> spec
                                        .model("warbornrenewed:geo/press-chest.geo.json")
                                        .texture("warbornrenewed:textures/press.png"))
                                .bones(bones -> bones
                                        .body("armorBody")
                                        .rightArm("armorRightArm")
                                        .leftArm("armorLeftArm"))
                                .properties(props -> props.stacksTo(1).rarity(Rarity.RARE))
                                .bulletResistance(0.50D)
                                .attribute(ArmorAttributeSpec.protectionClass(4))
                                .attribute(ArmorAttributeSpec.blastResistance(0.30D))
                                .attribute(ArmorAttributeSpec.movementSpeed(-0.02D)))
        );
    }

    /**
     * Р‘РёРЅС‚С‹
     */
    private static void registerBandageSet() {
        WarbornArmorRegistry.registerSet(
                WarbornArmorSet.builder("bandage")
                        .chestplate(piece -> piece
                                .registryName("arm_bandage")
                                .material(type -> ModArmorMaterials.LEATHER)
                                .visuals(spec -> spec
                                        .model("warbornrenewed:geo/bandage_arm.geo.json")
                                        .texture("warbornrenewed:textures/bandage.png"))
                                .bones(bones -> bones
                                        .body("armorBody")
                                        .rightArm("armorRightArm")
                                        .leftArm("armorLeftArm"))
                                .properties(props -> props.stacksTo(1).rarity(Rarity.COMMON)))
                        .leggings(piece -> piece
                                .registryName("leg_bandage")
                                .material(type -> ModArmorMaterials.LEATHER)
                                .visuals(spec -> spec
                                        .model("warbornrenewed:geo/leg_bandage.geo.json")
                                        .texture("warbornrenewed:textures/bandage.png"))
                                .bones(bones -> bones
                                        .body("armorBody")
                                        .rightLeg("armorRightLeg")
                                        .leftLeg("armorLeftLeg"))
                                .properties(props -> props.stacksTo(1).rarity(Rarity.COMMON)))
        );
    }

    /**
     * РњР°СЃРєРёСЂРѕРІРѕС‡РЅС‹Р№ РєРѕСЃС‚СЋРј (Ghillie) - СЂР°Р·Р»РёС‡РЅС‹Рµ РєР°РјСѓС„Р»СЏР¶Рё
     */
    private static void registerGhillieSet() {
        // Desert
        WarbornArmorRegistry.registerSet(
                WarbornArmorSet.builder("ghillie-desert")
                        .helmet(piece -> piece
                                .registryName("ghillie-helmet-desert")
                                .material(type -> ModArmorMaterials.LEATHER)
                                .visuals(spec -> spec
                                        .model("warbornrenewed:geo/ghillie-helmet.geo.json")
                                        .texture("warbornrenewed:textures/ghille-desert.png"))
                                .properties(props -> props.stacksTo(1).rarity(Rarity.UNCOMMON))
                                .bulletResistance(0.0D)
                                .attribute(ArmorAttributeSpec.protectionClass(0))
                                .attribute(ArmorAttributeSpec.movementSpeed(0.0D)))
                        .chestplate(piece -> piece
                                .registryName("ghillie-body-desert")
                                .material(type -> ModArmorMaterials.LEATHER)
                                .visuals(spec -> spec
                                        .model("warbornrenewed:geo/ghillie-body.geo.json")
                                        .texture("warbornrenewed:textures/ghille-desert.png"))
                                .bones(bones -> bones
                                        .body("armorBody")
                                        .rightArm("armorRightArm")
                                        .leftArm("armorLeftArm"))
                                .properties(props -> props.stacksTo(1).rarity(Rarity.UNCOMMON))
                                .bulletResistance(0.0D)
                                .attribute(ArmorAttributeSpec.protectionClass(0))
                                .attribute(ArmorAttributeSpec.movementSpeed(0.0D)))
                        .leggings(piece -> piece
                                .registryName("ghillie-legs-desert")
                                .material(type -> ModArmorMaterials.LEATHER)
                                .visuals(spec -> spec
                                        .model("warbornrenewed:geo/ghillie-legs.geo.json")
                                        .texture("warbornrenewed:textures/ghille-desert.png"))
                                .bones(bones -> bones
                                        .body("armorBody")
                                        .rightLeg("armorRightLeg")
                                        .leftLeg("armorLeftLeg"))
                                .properties(props -> props.stacksTo(1).rarity(Rarity.UNCOMMON))
                                .bulletResistance(0.0D)
                                .attribute(ArmorAttributeSpec.protectionClass(0))
                                .attribute(ArmorAttributeSpec.movementSpeed(0.0D)))
        );

        // Jungle
        WarbornArmorRegistry.registerSet(
                WarbornArmorSet.builder("ghillie-jungle")
                        .helmet(piece -> piece
                                .registryName("ghillie-helmet-jungle")
                                .material(type -> ModArmorMaterials.LEATHER)
                                .visuals(spec -> spec
                                        .model("warbornrenewed:geo/ghillie-helmet.geo.json")
                                        .texture("warbornrenewed:textures/ghille-jungle.png"))
                                .properties(props -> props.stacksTo(1).rarity(Rarity.UNCOMMON))
                                .bulletResistance(0.0D)
                                .attribute(ArmorAttributeSpec.protectionClass(0))
                                .attribute(ArmorAttributeSpec.movementSpeed(0.0D)))
                        .chestplate(piece -> piece
                                .registryName("ghillie-body-jungle")
                                .material(type -> ModArmorMaterials.LEATHER)
                                .visuals(spec -> spec
                                        .model("warbornrenewed:geo/ghillie-body.geo.json")
                                        .texture("warbornrenewed:textures/ghille-jungle.png"))
                                .bones(bones -> bones
                                        .body("armorBody")
                                        .rightArm("armorRightArm")
                                        .leftArm("armorLeftArm"))
                                .properties(props -> props.stacksTo(1).rarity(Rarity.UNCOMMON))
                                .bulletResistance(0.0D)
                                .attribute(ArmorAttributeSpec.protectionClass(0))
                                .attribute(ArmorAttributeSpec.movementSpeed(0.0D)))
                        .leggings(piece -> piece
                                .registryName("ghillie-legs-jungle")
                                .material(type -> ModArmorMaterials.LEATHER)
                                .visuals(spec -> spec
                                        .model("warbornrenewed:geo/ghillie-legs.geo.json")
                                        .texture("warbornrenewed:textures/ghille-jungle.png"))
                                .bones(bones -> bones
                                        .body("armorBody")
                                        .rightLeg("armorRightLeg")
                                        .leftLeg("armorLeftLeg"))
                                .properties(props -> props.stacksTo(1).rarity(Rarity.UNCOMMON))
                                .bulletResistance(0.0D)
                                .attribute(ArmorAttributeSpec.protectionClass(0))
                                .attribute(ArmorAttributeSpec.movementSpeed(0.0D)))
        );

        // Winter
        WarbornArmorRegistry.registerSet(
                WarbornArmorSet.builder("ghillie-winter")
                        .helmet(piece -> piece
                                .registryName("ghillie-helmet-winter")
                                .material(type -> ModArmorMaterials.LEATHER)
                                .visuals(spec -> spec
                                        .model("warbornrenewed:geo/ghillie-helmet.geo.json")
                                        .texture("warbornrenewed:textures/ghille-winter.png"))
                                .properties(props -> props.stacksTo(1).rarity(Rarity.UNCOMMON))
                                .bulletResistance(0.0D)
                                .attribute(ArmorAttributeSpec.protectionClass(0))
                                .attribute(ArmorAttributeSpec.movementSpeed(0.0D)))
                        .chestplate(piece -> piece
                                .registryName("ghillie-body-winter")
                                .material(type -> ModArmorMaterials.LEATHER)
                                .visuals(spec -> spec
                                        .model("warbornrenewed:geo/ghillie-body.geo.json")
                                        .texture("warbornrenewed:textures/ghille-winter.png"))
                                .bones(bones -> bones
                                        .body("armorBody")
                                        .rightArm("armorRightArm")
                                        .leftArm("armorLeftArm"))
                                .properties(props -> props.stacksTo(1).rarity(Rarity.UNCOMMON))
                                .bulletResistance(0.0D)
                                .attribute(ArmorAttributeSpec.protectionClass(0))
                                .attribute(ArmorAttributeSpec.movementSpeed(0.0D)))
                        .leggings(piece -> piece
                                .registryName("ghillie-legs-winter")
                                .material(type -> ModArmorMaterials.LEATHER)
                                .visuals(spec -> spec
                                        .model("warbornrenewed:geo/ghillie-legs.geo.json")
                                        .texture("warbornrenewed:textures/ghille-winter.png"))
                                .bones(bones -> bones
                                        .body("armorBody")
                                        .rightLeg("armorRightLeg")
                                        .leftLeg("armorLeftLeg"))
                                .properties(props -> props.stacksTo(1).rarity(Rarity.UNCOMMON))
                                .bulletResistance(0.0D)
                                .attribute(ArmorAttributeSpec.protectionClass(0))
                                .attribute(ArmorAttributeSpec.movementSpeed(0.0D)))
        );

        // Sosna (Forest / Pine)
        WarbornArmorRegistry.registerSet(
                WarbornArmorSet.builder("ghillie-sosna")
                        .helmet(piece -> piece
                                .registryName("ghillie-helmet-sosna")
                                .material(type -> ModArmorMaterials.LEATHER)
                                .visuals(spec -> spec
                                        .model("warbornrenewed:geo/ghillie-helmet.geo.json")
                                        .texture("warbornrenewed:textures/ghille_sosna.png"))
                                .properties(props -> props.stacksTo(1).rarity(Rarity.UNCOMMON))
                                .bulletResistance(0.0D)
                                .attribute(ArmorAttributeSpec.protectionClass(0))
                                .attribute(ArmorAttributeSpec.movementSpeed(0.0D)))
                        .chestplate(piece -> piece
                                .registryName("ghillie-body-sosna")
                                .material(type -> ModArmorMaterials.LEATHER)
                                .visuals(spec -> spec
                                        .model("warbornrenewed:geo/ghillie-body.geo.json")
                                        .texture("warbornrenewed:textures/ghille_sosna.png"))
                                .bones(bones -> bones
                                        .body("armorBody")
                                        .rightArm("armorRightArm")
                                        .leftArm("armorLeftArm"))
                                .properties(props -> props.stacksTo(1).rarity(Rarity.UNCOMMON))
                                .bulletResistance(0.0D)
                                .attribute(ArmorAttributeSpec.protectionClass(0))
                                .attribute(ArmorAttributeSpec.movementSpeed(0.0D)))
                        .leggings(piece -> piece
                                .registryName("ghillie-legs-sosna")
                                .material(type -> ModArmorMaterials.LEATHER)
                                .visuals(spec -> spec
                                        .model("warbornrenewed:geo/ghillie-legs.geo.json")
                                        .texture("warbornrenewed:textures/ghille_sosna.png"))
                                .bones(bones -> bones
                                        .body("armorBody")
                                        .rightLeg("armorRightLeg")
                                        .leftLeg("armorLeftLeg"))
                                .properties(props -> props.stacksTo(1).rarity(Rarity.UNCOMMON))
                                .bulletResistance(0.0D)
                                .attribute(ArmorAttributeSpec.protectionClass(0))
                                .attribute(ArmorAttributeSpec.movementSpeed(0.0D)))
        );

        // Autumn
        WarbornArmorRegistry.registerSet(
                WarbornArmorSet.builder("ghillie-autumn")
                        .helmet(piece -> piece
                                .registryName("ghillie-helmet-autumn")
                                .material(type -> ModArmorMaterials.LEATHER)
                                .visuals(spec -> spec
                                        .model("warbornrenewed:geo/ghillie-helmet.geo.json")
                                        .texture("warbornrenewed:textures/ghille_autmn.png"))
                                .properties(props -> props.stacksTo(1).rarity(Rarity.UNCOMMON))
                                .bulletResistance(0.0D)
                                .attribute(ArmorAttributeSpec.protectionClass(0))
                                .attribute(ArmorAttributeSpec.movementSpeed(0.0D)))
                        .chestplate(piece -> piece
                                .registryName("ghillie-body-autumn")
                                .material(type -> ModArmorMaterials.LEATHER)
                                .visuals(spec -> spec
                                        .model("warbornrenewed:geo/ghillie-body.geo.json")
                                        .texture("warbornrenewed:textures/ghille_autmn.png"))
                                .bones(bones -> bones
                                        .body("armorBody")
                                        .rightArm("armorRightArm")
                                        .leftArm("armorLeftArm"))
                                .properties(props -> props.stacksTo(1).rarity(Rarity.UNCOMMON))
                                .bulletResistance(0.0D)
                                .attribute(ArmorAttributeSpec.protectionClass(0))
                                .attribute(ArmorAttributeSpec.movementSpeed(0.0D)))
                        .leggings(piece -> piece
                                .registryName("ghillie-legs-autumn")
                                .material(type -> ModArmorMaterials.LEATHER)
                                .visuals(spec -> spec
                                        .model("warbornrenewed:geo/ghillie-legs.geo.json")
                                        .texture("warbornrenewed:textures/ghille_autmn.png"))
                                .bones(bones -> bones
                                        .body("armorBody")
                                        .rightLeg("armorRightLeg")
                                        .leftLeg("armorLeftLeg"))
                                .properties(props -> props.stacksTo(1).rarity(Rarity.UNCOMMON))
                                .bulletResistance(0.0D)
                                .attribute(ArmorAttributeSpec.protectionClass(0))
                                .attribute(ArmorAttributeSpec.movementSpeed(0.0D)))
        );
    }
}



