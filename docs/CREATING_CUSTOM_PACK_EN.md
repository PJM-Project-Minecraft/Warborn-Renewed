# 🛡️ Creating a Custom Pack for Warborn Renewed

> This guide describes how to create your own content pack (armor set) for the **Warborn Renewed** mod.

---

## 📋 Table of Contents

- [Pack System Overview](#-pack-system-overview)
- [Required Tools](#-required-tools)
- [Pack Structure](#-pack-structure)
- [Step-by-Step Pack Creation](#-step-by-step-pack-creation)
  - [Step 1: Create Pack Base](#step-1-create-pack-base)
  - [Step 2: Create Resources (Assets)](#step-2-create-resources-assets)
  - [Step 3: Create Configuration (JSON)](#step-3-create-configuration-json)
  - [Step 4: Animations (optional)](#step-4-animations-optional)
  - [Step 5: Localization](#step-5-localization-item-name)
- [Bone Name Reference](#-bone-name-reference)
- [Armor Stats Reference](#-armor-stats-reference)
- [Testing Your Pack](#-testing-your-pack)
- [Examples](#-examples)
- [FAQ](#-faq)

---

## 🔍 Pack System Overview

Warborn Renewed supports a **content pack system** — a way to add new armor to the mod without writing any Java code. Each pack is a set of files including:

- **JSON configuration** — armor parameters (defense, durability, etc.)
- **3D model** — a `.geo.json` file in Bedrock/GeckoLib format
- **Texture** — a `.png` file for rendering the model
- **Animation** (optional) — an `.animation.json` file for GeckoLib animations

Packs are loaded from the `warbornrenewed/packs/` folder in the game's root directory (next to the `mods` folder).

---

## 🛠️ Required Tools

| Tool | Description | Link |
|---|---|---|
| **Blockbench** | Creating 3D models in Bedrock/GeckoLib format | [blockbench.net](https://www.blockbench.net/) |
| **GeckoLib Plugin** | Blockbench plugin — export models and animations for GeckoLib | [blockbench.net/plugins/geckolib](https://www.blockbench.net/plugins/geckolib) |
| **Image Editor** | Any graphics editor (Photoshop, GIMP, Aseprite, paint.net) | — |
| **Text Editor** | For editing JSON files (VS Code, Notepad++, etc.) | — |

---

## 📁 Pack Structure

Each pack is placed in a separate folder inside `packs/` in the repository. The pack structure must follow the Minecraft resource pack standard, with the addition of a `json` folder for mod configurations.

Here is the correct structure:

```
packs/
└── my_cool_armor/             ← Your pack name (snake_case)
    ├── pack.mcmeta            ← Mandatory metadata file
    ├── pack.json              ← Creative tab configuration
    ├── assets/
    │   └── my_cool_armor/     ← Namespace (usually matches pack name)
    │       ├── geo/
    │       │   └── armor/
    │       │       └── my_helmet.geo.json     ← 3D helmet model
    │       ├── textures/
    │       │   └── armor/
    │       │       └── my_helmet.png          ← Helmet texture
    │       └── models/
    │           └── item/
    │               └── my_helmet.json         ← Item model for inventory
    ├── json/
    │   └── armor/
    │       └── my_helmet.json         ← Helmet stats configuration
    └── lang/                          ← Localization
        ├── en_us.json
        └── ru_ru.json
```

> [!IMPORTANT]
> The pack folder name **must be unique** and use `snake_case` format.
> Inside the `assets` folder, there must be a folder with a unique `namespace` (usually matching the pack name).

---

## 🚀 Step-by-Step Pack Creation

### Step 1: Create Pack Base

1. Create a folder with a unique name for your pack inside `packs/`.
2. Create a `pack.mcmeta` file in the pack root:

```json
{
  "pack": {
    "description": "Your pack description",
    "pack_format": 15
  }
}
```

3. Create a `pack.json` file in the pack root for creative tab settings:

```json
{
    "tab_name": "My Custom Pack",
    "icon_item": "my_cool_armor:my_helmet"
}
```
* `icon_item` — Item ID to be used as the tab icon (`namespace:item_id`).

### Step 2: Create Resources (Assets)

Create the folder structure for resources: `assets/<namespace>/`.
Inside `namespace`, create folders:
- `geo/armor/` — for 3D armor models
- `textures/armor/` — for armor textures
- `models/item/` — for item models (inventory icons)

#### 3D Model (Geo)
Open **Blockbench**, create a model (Bedrock Entity/GeckoLib).

#### Bone Hierarchy in Blockbench

For correct armor positioning, it is recommended to use the following bone hierarchy (matching the mod's standard models):

Example hierarchy for a helmet:

```
📦 root
└── 🦴 bipedHead (pivot: 0, 24, 0)
    └── 🦴 armorHead (pivot: 0, 24, 0)
        ├── 🟦 helmet_base (cube)
        ├── 🟦 helmet_visor (cube)
        └── 🟦 helmet_strap (cube)
```

Example hierarchy for a chestplate:

```
📦 root
├── 🦴 bipedBody (pivot: 0, 24, 0)
│   └── 🦴 armorBody (pivot: 0, 24, 0)
│       ├── 🟦 vest_front (cube)
│       ├── 🟦 vest_back (cube)
│       └── 🟦 vest_collar (cube)
├── 🦴 bipedRightArm (pivot: -5, 22, 0)
│   └── 🦴 armorRightArm (pivot: -5, 22, 0)
│       └── 🟦 shoulder_pad_right (cube)
└── 🦴 bipedLeftArm (pivot: 5, 22, 0)
    └── 🦴 armorLeftArm (pivot: 5, 22, 0)
        └── 🟦 shoulder_pad_left (cube)
```

> [!TIP]
> Using parent `biped...` bones helps avoid "broken center" issues (model misalignment) and ensures compatibility with player animations.

#### Pivot Points

Use the following pivot points for correct positioning:

| Bone | Pivot Point (X, Y, Z) |
|---|---|
| `bipedHead` / `armorHead` | `[0, 24, 0]` |
| `bipedBody` / `armorBody` | `[0, 24, 0]` |
| `bipedRightArm` / `armorRightArm` | `[-5, 22, 0]` |
| `bipedLeftArm` / `armorLeftArm` | `[5, 22, 0]` |
| `bipedRightLeg` / `armorRightLeg` | `[-1.9, 12, 0]` |
| `bipedLeftLeg` / `armorLeftLeg` | `[1.9, 12, 0]` |
| `bipedRightBoot` / `armorRightBoot` | `[-1.9, 12, 0]` |
| `bipedLeftBoot` / `armorLeftBoot` | `[1.9, 12, 0]` |

3. Export the model as **Bedrock Geometry** (`.geo.json`).
4. Save the file to `assets/<namespace>/geo/armor/<name>.geo.json`.

#### Texture
Save the model texture to `assets/<namespace>/textures/armor/<name>.png`.

#### Item Model
To display the item in the inventory, create `assets/<namespace>/models/item/<name>.json`:

```json
{
  "parent": "item/generated",
  "textures": {
    "layer0": "my_cool_armor:armor/my_helmet"
  }
}
```

### Step 3: Create Configuration (JSON)

Create a file `json/armor/<name>.json`. This file links all resources and defines stats.

```json
{
  "id": "my_cool_armor:my_helmet",
  "type": "helmet",
  "model_id": "my_cool_armor:armor/my_helmet",
  "texture_id": "my_cool_armor:armor/my_helmet",
  "name": "My Custom Helmet",
  "names": {
    "ru_ru": "Мой крутой шлем",
    "en_us": "My Cool Helmet"
  },
  "material": "kevlar",
  "defense": 3,
  "toughness": 1.0,
  "knockback_resistance": 0.0,
  "durability": 150
}
```

- `id`: Unique item identifier (`namespace:item_name`).
- `model_id`: Path to model. `namespace:armor/name` looks for `assets/namespace/geo/armor/name.geo.json`.
- `texture_id`: Path to texture. `namespace:armor/name` looks for `assets/namespace/textures/armor/name.png`.

| Property | Type | Required | Description |
|---|---|---|---|
| `name` | `string` | ❌ | **Display name** of the item. Used as default name for all languages if `names` is not specified |
| `names` | `object` | ❌ | **Multilingual names**. Key — locale code (`ru_ru`, `en_us`, etc.), value — name in that language |
| `material` | `string` | ❌ | **Armor material**. Defines the displayed material name in tooltip. Default: `kevlar`. [Material List](#-available-materials-list) |
| `defense` | `int` | ✅ | **Defense points** (like vanilla armor). 0–20 |
| `toughness` | `float` | ✅ | **Armor toughness** (reduces damage from strong attacks). 0.0–4.0 |

### Step 4: Animations (optional)


To add animations, create an `.animation.json` file alongside the model:

```
packs/<your_pack>/models/armor/<name>.animation.json
```

Animations are created in **Blockbench** using the **GeckoLib Animation Utils** plugin:

1. Install the GeckoLib plugin in Blockbench.
2. Create animations for your model.
3. Export as `.animation.json`.

The system will automatically pick up the animation file at the path matching `model_id` (with `.animation.json` extension).

### Step 5: Localization (Item Name)

There are **two ways** to set the item name — choose whichever is more convenient:

---

#### Method 1: Directly in JSON Configuration (recommended ✅)

The simplest way — add `name` and/or `names` fields directly to the armor JSON file:

```json
{
  "id": "my_pack:tactical_helmet",
  "model_id": "my_pack:geo/armor/tactical_helmet",
  "name": "Tactical Helmet",
  "names": {
    "ru_ru": "Тактический шлем",
    "en_us": "Tactical Helmet"
  },
  "defense": 4,
  "toughness": 1.5,
  "knockback_resistance": 0.05,
  "durability": 250
}
```

**Display priority:**
1. `names` → name in the current game language
2. `name` → general name (if language not found in `names`)
3. `id` → last resort fallback

> [!TIP]
> If your pack targets only one language — just specify `name` without `names`.

---

#### Method 2: Via lang files in the pack folder

For advanced users or packs with many items — create a `lang/` folder inside the pack:

```
packs/my_pack/
└── lang/
    ├── en_us.json
    └── ru_ru.json
```

**Key format:**
```
item.warbornrenewed.pack.<pack_name>.<item_id>
```

**`lang/en_us.json`:**
```json
{
  "item.warbornrenewed.pack.my_pack.tactical_helmet": "Tactical Helmet",
  "item.warbornrenewed.pack.my_pack.tactical_vest": "Tactical Vest"
}
```

**`lang/ru_ru.json`:**
```json
{
  "item.warbornrenewed.pack.my_pack.tactical_helmet": "Тактический шлем",
  "item.warbornrenewed.pack.my_pack.tactical_vest": "Тактический жилет"
}
```

> [!NOTE]
> Lang files have the **highest priority** — if a translation is found in a lang file, it will be used instead of `name`/`names` from the JSON config.

---

## 🦴 Bone Name Reference

Complete list of all bones used by the GeckoLib rendering system:

| Bone Name | Body Part | Used In |
|---|---|---|
| `armorHead` | Head | Helmets |
| `armorBody` | Torso | Chestplates, Leggings |
| `armorRightArm` | Right Arm | Chestplates (shoulder pads) |
| `armorLeftArm` | Left Arm | Chestplates (shoulder pads) |
| `armorRightLeg` | Right Leg | Leggings |
| `armorLeftLeg` | Left Leg | Leggings |
| `armorRightBoot` | Right Foot | Boots |
| `armorLeftBoot` | Left Foot | Boots |

---

## 📊 Armor Stats Reference

### Available Materials List

For the `material` field, use the following values (lowercase):

| JSON Value (`material`) | Display Name | Stats Reference |
|---|---|---|
| `leather` | Leather | Weak protection |
| `kevlar` | Kevlar | Standard military armor |
| `ceramic` | Ceramic | Heavy protection |
| `ar500_steel` | AR500 Steel | Durable steel |
| `uhmwpe` | UHMWPE | High-tech polyethylene |
| `composite` | Composite | Composite armor |
| `titanium` | Titanium | Maximum protection |

> [!NOTE]
> The material choice affects the tooltip display name and base equip sounds. Defense stats (`defense`, `toughness`) are still set manually in the JSON fields.

### Recommended Stat Values

For reference — stats of existing mod armor:

| Type | Item | Defense | Toughness | Bullet Res. | Durability |
|---|---|---|---|---|---|
| 🪖 Helmet (light) | Panama | 0 | 0.0 | 0% | Low |
| 🪖 Helmet (medium) | PASGT | 2 | 0.5 | 30% | 200 |
| 🪖 Helmet (heavy) | 6B47 | 3 | 1.0 | 35% | 300 |
| 🪖 Helmet (advanced) | Ops-Core | 3 | 1.0 | 40% | 350 |
| 🦺 Vest (medium) | IOTV | 6 | 2.0 | 50% | 400 |
| 🦺 Vest (heavy) | 6B45 | 7 | 2.5 | 55% | 450 |
| 🦺 Vest (elite) | Warmor | 8 | 3.0 | 60% | 500 |

> [!WARNING]
> Do not make stats **excessively high** — this will break the mod's balance!

---

## ✅ Testing Your Pack

Before using, test the pack locally:

1. **Copy the pack** to the game folder:
   ```
   .minecraft/warbornrenewed/packs/<your_pack>/
   ```

2. **Launch Minecraft** with the Warborn Renewed mod installed.

3. **Verify:**
   - ✅ Armor loads without errors in the logs
   - ✅ Model renders correctly on the player character
   - ✅ Texture displays properly (no artifacts or "pink-black squares")
   - ✅ Stats (defense, durability) work as expected
   - ✅ Item name displays correctly (not as `item.warbornrenewed.pack...`)
   - ✅ Animations work (if used)

4. **Check logs** for errors:
   ```
   logs/latest.log
   ```
   Look for lines containing `Failed to load armor def` or `WarbornPackManager`.

---

## 📌 Examples

### Example: The `example_pack`

The repository already contains an example pack at `packs/example_pack/`:

**Structure:**
```
packs/example_pack/
├── json/
│   └── armor/
│       └── alfa_helmet.json
├── models/
│   └── armor/
│       └── alfa_helmet.geo.json
└── textures/
    └── armor/
        └── alfa_helmet.png
```

**Configuration (`alfa_helmet.json`):**
```json
{
  "id": "example_pack:alfa_helmet",
  "model_id": "example_pack:geo/armor/alfa_helmet",
  "name": "Alfa Helmet",
  "names": {
    "ru_ru": "Шлем Альфа",
    "en_us": "Alfa Helmet"
  },
  "defense": 3,
  "toughness": 1.0,
  "knockback_resistance": 0.0,
  "durability": 150
}
```

### Loading Packs (for users)

For client-side use (not for development), packs are placed in:

```
.minecraft/warbornrenewed/packs/<pack_name>/
```

The mod automatically scans this directory at startup and loads all packs found.

---

## ❓ FAQ

### My pack won't load. What should I do?

1. Check the folder structure — it should match the format above
2. Check JSON for syntax errors (extra commas, unclosed brackets)
3. Make sure the `id` is unique and doesn't conflict with other packs
4. Check the logs: `logs/latest.log` — look for `Failed to load armor def`

### What model format is needed?

Use the **Bedrock Entity Geometry** `.geo.json` format (the same used for Bedrock Edition models and GeckoLib).

### The model renders incorrectly (offset, rotated)

Check the **pivot points** of root bones — they should match the table in the ["Pivot Points"](#pivot-points) section.

### Texture shows as pink-black squares

This means the `model_id` path points to a non-existent texture file. Make sure:
- The `.png` file exists alongside the `.geo.json`
- The path in `model_id` is correct

### Can I add multiple items to one pack?

Yes! Create one `.json`, `.geo.json`, and `.png` file per armor item, all within the same pack folder.

### What texture size should I use?

Recommended: **64×64** for simple models and **128×128** or **256×256** for more detailed ones. Textures should be square with dimensions that are a power of two (64, 128, 256). The texture size **must match** what's specified in the `.geo.json` file (`texture_width` / `texture_height`).

---

> **Good luck creating content!** 🎮
