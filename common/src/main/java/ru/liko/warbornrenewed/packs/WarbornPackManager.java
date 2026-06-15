package ru.liko.warbornrenewed.packs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import ru.liko.warbornrenewed.platform.Services;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class WarbornPackManager {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static volatile Map<String, ArmorDef> ARMOR_DEFS = new HashMap<>();
    private static volatile Map<String, List<ArmorDef>> PACK_DEFS = new LinkedHashMap<>();
    private static volatile Map<String, PackDef> PACK_INFOS = new HashMap<>();

    /**
     * Хранилище переводов из lang-файлов паков.
     * Структура: locale (например "ru_ru") -> translationKey -> значение
     */
    private static volatile Map<String, Map<String, String>> PACK_TRANSLATIONS = new HashMap<>();

    public static synchronized void loadPacks() {
        // Create new maps to populate
        Map<String, ArmorDef> newArmorDefs = new HashMap<>();
        Map<String, List<ArmorDef>> newPackDefs = new LinkedHashMap<>();
        Map<String, PackDef> newPackInfos = new HashMap<>();
        Map<String, Map<String, String>> newPackTranslations = new HashMap<>();

        Path gameDir = Services.PLATFORM.getGameDir();
        File packsDir = new File(gameDir.toFile(), "warbornrenewed/packs");

        if (!packsDir.exists()) {
            if (packsDir.mkdirs()) {
                System.out.println("[WarbornPacks] Created packs directory: " + packsDir.getAbsolutePath());
            } else {
                System.err.println("[WarbornPacks] Failed to create packs directory: " + packsDir.getAbsolutePath());
            }
            // Assign empty maps (already default) but good practice to reset if failed
            ARMOR_DEFS = newArmorDefs;
            PACK_DEFS = newPackDefs;
            PACK_INFOS = newPackInfos;
            PACK_TRANSLATIONS = newPackTranslations;
            return;
        }

        if (!packsDir.isDirectory()) {
            return;
        }

        File[] packDirs = packsDir.listFiles(File::isDirectory);
        if (packDirs == null)
            return;

        for (File packDir : packDirs) {
            // Проверка на валидность имени папки (namespace)
            String dirName = packDir.getName();
            if (!dirName.equals(dirName.toLowerCase()) || !dirName.matches("[a-z0-9_.-]+")) {
                System.err.println("[WarbornPacks] Skipping invalid pack directory name (must be lowercase a-z0-9_.-): " + dirName);
                continue;
            }
            loadPack(packDir, newArmorDefs, newPackDefs, newPackInfos, newPackTranslations);
        }

        // Atomically swap the maps
        ARMOR_DEFS = newArmorDefs;
        PACK_DEFS = newPackDefs;
        PACK_INFOS = newPackInfos;
        PACK_TRANSLATIONS = newPackTranslations;
    }

    private static boolean isValidResourceLocation(String string) {
        if (string == null || string.isEmpty()) return false;
        String[] parts = string.split(":", 2);
        String namespace = parts[0];
        String path = parts.length > 1 ? parts[1] : "";
        return namespace.matches("[a-z0-9_.-]+") && path.matches("[a-z0-9_./-]+");
    }

    private static void loadPack(File packDir, Map<String, ArmorDef> armorDefs, Map<String, List<ArmorDef>> packDefs, Map<String, PackDef> packInfos, Map<String, Map<String, String>> packTranslations) {
        String packName = packDir.getName();
        List<ArmorDef> packArmorDefs = new ArrayList<>();

        // Загрузка pack.json (информация о паке)
        File packJsonFile = new File(packDir, "pack.json");
        if (packJsonFile.exists() && packJsonFile.isFile()) {
            try (Reader reader = new FileReader(packJsonFile)) {
                PackDef packDef = GSON.fromJson(reader, PackDef.class);
                if (packDef != null) {
                    packInfos.put(packName, packDef);
                }
            } catch (Exception e) {
                System.err.println("[WarbornPacks] Failed to load pack.json for: " + packName);
                e.printStackTrace();
            }
        }

        // Загрузка JSON-конфигураций брони
        File armorDir = new File(packDir, "json/armor");
        if (armorDir.exists() && armorDir.isDirectory()) {
            File[] jsonFiles = armorDir.listFiles((dir, name) -> name.endsWith(".json"));
            if (jsonFiles != null) {
                for (File file : jsonFiles) {
                    try (Reader reader = new FileReader(file)) {
                        ArmorDef def = GSON.fromJson(reader, ArmorDef.class);
                        if (def != null && def.getId() != null) {
                            if (!isValidResourceLocation(def.getId())) {
                                System.err.println("[WarbornPacks] Skipping armor def with invalid ID (must be lowercase a-z0-9_.-): " + def.getId() + " in file " + file.getAbsolutePath());
                                continue;
                            }
                            if (def.getModelId() != null && !isValidResourceLocation(def.getModelId())) {
                                System.err.println("[WarbornPacks] Skipping armor def with invalid Model ID: " + def.getModelId() + " in file " + file.getAbsolutePath());
                                continue;
                            }
                            armorDefs.put(def.getId(), def);
                            packArmorDefs.add(def);
                        }
                    } catch (Exception e) {
                        System.err.println("[WarbornPacks] Failed to load armor def: " + file.getAbsolutePath());
                        e.printStackTrace();
                    }
                }
            }
        }

        if (!packArmorDefs.isEmpty()) {
            packDefs.put(packName, packArmorDefs);
            System.out.println(
                    "[WarbornPacks] Loaded pack '" + packName + "' with " + packArmorDefs.size() + " armor def(s)");
        }

        // Загрузка lang-файлов (опционально)
        File langDir = new File(packDir, "lang");
        if (langDir.exists() && langDir.isDirectory()) {
            File[] langFiles = langDir.listFiles((dir, name) -> name.endsWith(".json"));
            if (langFiles != null) {
                Type mapType = new TypeToken<Map<String, String>>() {
                }.getType();
                for (File langFile : langFiles) {
                    // Имя файла = код локали (например "ru_ru.json" -> "ru_ru")
                    String locale = langFile.getName().replace(".json", "").toLowerCase();
                    try (Reader reader = new FileReader(langFile)) {
                        Map<String, String> translations = GSON.fromJson(reader, mapType);
                        if (translations != null) {
                            packTranslations.computeIfAbsent(locale, k -> new HashMap<>())
                                    .putAll(translations);
                        }
                    } catch (Exception e) {
                        System.err.println("[WarbornPacks] Failed to load lang file: " + langFile.getAbsolutePath());
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static ArmorDef getArmorDef(String id) {
        return ARMOR_DEFS.get(id);
    }

    public static Map<String, ArmorDef> getAllArmorDefs() {
        return ARMOR_DEFS;
    }

    public static Set<String> getPackNames() {
        return PACK_DEFS.keySet();
    }

    public static List<ArmorDef> getPackDefs(String packName) {
        return PACK_DEFS.getOrDefault(packName, List.of());
    }

    public static PackDef getPackInfo(String packName) {
        return PACK_INFOS.get(packName);
    }

    /**
     * Получить перевод по ключу для указанной локали.
     * Ищет сначала в lang-файлах паков, затем возвращает null если не найдено.
     */
    public static String getTranslation(String key, String locale) {
        Map<String, String> localeMap = PACK_TRANSLATIONS.get(locale != null ? locale.toLowerCase() : "en_us");
        if (localeMap != null) {
            String value = localeMap.get(key);
            if (value != null)
                return value;
        }
        // Fallback на en_us если запрошенная локаль не найдена
        if (locale != null && !locale.equalsIgnoreCase("en_us")) {
            Map<String, String> fallback = PACK_TRANSLATIONS.get("en_us");
            if (fallback != null) {
                return fallback.get(key);
            }
        }
        return null;
    }

    /**
     * Получить отображаемое имя предмета брони с учётом локали.
     * Приоритет:
     * 1. lang-файл пака (translationKey для текущей локали)
     * 2. ArmorDef.names[locale]
     * 3. ArmorDef.name
     * 4. ArmorDef.id
     */
    public static String getDisplayName(String armorId, String locale) {
        // 1. Ищем в lang-файлах пака
        String translationKey = "item.warbornrenewed.pack." + armorId.replace(":", ".");
        String fromLang = getTranslation(translationKey, locale);
        if (fromLang != null && !fromLang.isEmpty()) {
            return fromLang;
        }

        // 2-4. Используем ArmorDef (name/names/id)
        ArmorDef def = getArmorDef(armorId);
        if (def != null) {
            return def.getDisplayName(locale);
        }

        // Крайний fallback
        return armorId;
    }
}
