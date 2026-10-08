# Design Assets & Store Graphics Specification for ABCoo

**Publisher:** ViveScript Solutions LLC  
**Application ID:** `com.vivescriptsolutions.abcoo`  
**Assets Directory:** `/store_assets/`

This document details all visual identity elements, adaptive icons, promotional banners, and store screenshot assets created for **ABCoo** in compliance with Google Play Store asset requirements.

---

## 1. Store Assets Directory Structure

```text
store_assets/
├── app_icon/
│   ├── app_icon_512x512.png        # Official Google Play icon (512x512 PNG, 32-bit, max 1MB)
│   └── app_icon_1024x1024.png      # High-resolution master icon (1024x1024 PNG)
├── feature_graphic/
│   └── feature_graphic_1024x500.png# Official Google Play feature banner (1024x500 PNG)
└── screenshots/
    ├── phone/
    │   ├── phone_screenshot_1_letter_view.png     # 1080x1920 (Letter Detail Showcase)
    │   └── phone_screenshot_2_alphabet_grid.png   # 1080x1920 (A-Z Alphabet Grid Explorer)
    └── tablet/
        └── tablet_screenshot_1_tablet_view.png    # 1920x1440 (Widescreen Tablet Experience)
```

---

## 2. Graphic Asset Specifications

### 2.1 Google Play Store App Icon
- **File Path:** `store_assets/app_icon/app_icon_512x512.png`
- **Dimensions:** 512 x 512 px
- **Format:** 32-bit PNG with alpha
- **Max File Size:** Under 1024 KB
- **Design Composition:** Playful 3D toy alphabet block featuring the letter 'A' and a smiling, friendly animal character face on a solid `#3F51B5` vibrant background. Meets modern child-friendly preschool aesthetics.

### 2.2 Google Play Feature Graphic
- **File Path:** `store_assets/feature_graphic/feature_graphic_1024x500.png`
- **Dimensions:** 1024 x 500 px
- **Format:** 24-bit / 32-bit PNG (No transparency)
- **Composition:** Cheerful panoramic scene featuring large 3D letters A, B, C, smiling cartoon animal companions (bear, cat, lion), sunny rolling green hills, and a gentle rainbow. Conveys high-quality preschool educational value immediately.

### 2.3 Phone Screenshots (Vertical 9:16)
- **Dimensions:** 1080 x 1920 px (Complies with Google Play 16:9 / 9:16 aspect ratio rules)
- **Screenshot 1 (`phone_screenshot_1_letter_view.png`):** 
  - *Headline Focus:* Step-by-Step Letter Learning
  - *Content:* Large letter A card, "Apple" word title, pronunciation badge, and animated apple friend.
- **Screenshot 2 (`phone_screenshot_2_alphabet_grid.png`):**
  - *Headline Focus:* 26-Letter Interactive Alphabet Grid
  - *Content:* Clean, cheerful A–Z explorer tiles with dual English and Bangla vocabulary badges.

### 2.4 Tablet Screenshots (Landscape 4:3 / 16:9)
- **Dimensions:** 1920 x 1440 px
- **Screenshot 1 (`tablet_screenshot_1_tablet_view.png`):**
  - *Headline Focus:* Responsive Tablet Learning
  - *Content:* Spacious widescreen layout with full alphabet ribbon, animated companion, and audio controls.

---

## 3. Android Adaptive Launcher Icon

Configured strictly under Material Design / Android 8.0+ (API 26+) adaptive icon standards:

1. **Background Layer (`app/src/main/res/drawable/ic_launcher_background.xml`):**
   - Solid pinned color: `#3F51B5` (Deep Royal Indigo)
2. **Foreground Layer (`app/src/main/res/drawable/ic_launcher_foreground.xml`):**
   - `<layer-list>` centering the high-resolution foreground bitmap `ic_launcher_fg_img.png` strictly inside the **66dp safe zone** (within the 108dp canvas).
3. **Adaptive Wrappers:**
   - `app/src/main/res/mipmap-anydpi-v26/ic_launcher.xml`
   - `app/src/main/res/mipmap-anydpi-v26/ic_launcher_round.xml`
4. **Raster Legacy Fallbacks:**
   - Generated PNGs across all density buckets (`mdpi`: 48px, `hdpi`: 72px, `xhdpi`: 96px, `xxhdpi`: 144px, `xxxhdpi`: 192px).
   - Circular inscribed mask applied to all `ic_launcher_round.png` resources.
   - All old template `.webp` launcher files purged to prevent AAPT2 resource conflicts.

---

## 4. Brand Color Palette

| Name | Hex Code | Purpose |
| :--- | :--- | :--- |
| **Primary Indigo** | `#1E88E5` / `#3F51B5` | Primary brand accent, navigation, app icon background |
| **Playful Coral Red** | `#E53935` | Letter A, apple, primary attention focus |
| **Sunny Gold** | `#FFB300` | Stars, sun, encouraging praise, score badges |
| **Soft Meadow Green** | `#43A047` | Leaf accents, success banners, auto-play indicator |
| **Warm Tangerine** | `#FB8C00` | Secondary buttons, cat, giraffe, lion accents |
| **Soft Cloud Surface** | `#F8FAFC` | Calm, non-glaring reading background for toddler eye comfort |
| **Deep Charcoal Text** | `#1E293B` | High-contrast, easily legible typography for pre-readers |
