# ABCoo — Early Learning Alphabet Adventure

[![Platform](https://img.shields.io/badge/Platform-Android-green.svg)](https://android.com)
[![Kotlin](https://img.shields.io/badge/Kotlin-2.2.10-blue.svg)](https://kotlinlang.org)
[![Compose](https://img.shields.io/badge/Jetpack%20Compose-Material%203-brightgreen.svg)](https://developer.android.com/jetpack/compose)
[![MinSDK](https://img.shields.io/badge/Min%20SDK-24-orange.svg)](https://developer.android.com/about/dashboards)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

**ABCoo** is a colorful, playful, early-learning Android application designed for toddlers and young children who are beginning to recognize and pronounce the English alphabet. Published by **ViveScript Solutions LLC**, the app combines large, easy-to-read letters, clear native voice narration, purposeful 2D animated illustrations, and interactive repetition to make early literacy intuitive and joyful.

---

## 📱 Screenshots & Store Assets

All production-ready Google Play graphics are located in the `/store_assets/` folder:
- **App Icon (512x512):** `store_assets/app_icon/app_icon_512x512.png`
- **Feature Graphic (1024x500):** `store_assets/feature_graphic/feature_graphic_1024x500.png`
- **Phone Screenshots:** `store_assets/screenshots/phone/`
- **Tablet Screenshots:** `store_assets/screenshots/tablet/`

---

## ✨ Features

- **Large A–Z Letter Cards:** Displays uppercase and lowercase letters side-by-side with high-contrast, toddler-friendly typography.
- **Native Voice & Phonics Narration:** Tap any letter or word to hear clear English pronunciation and natural phonics sounds (e.g., *"/æ/ as in Apple"*).
- **26 Custom Animated 2D Friends:** Every letter features a charming illustration with gentle floating motion and bouncy tap responses.
- **Bilingual Context (English & Bengali / বাংলা):** Toggle dual vocabulary support to learn words in both English and Bangla (e.g., *Apple / আপেল*).
- **Hands-Free Auto-Play Mode:** Automatically advances through the alphabet at a calm, toddler-friendly pace with narration.
- **Interactive 26-Letter Explorer Grid:** Adaptive grid allowing children to freely explore letters and jump to any character.
- **"Listen & Find" Quiz:** Pressure-free auditory recognition game that rewards toddlers with joyful star praise.
- **Child-Safe & Family-Compliant:** Protected by an adult parental gate challenge; zero runtime permissions; 100% offline-ready.
- **Isolated AdMob Architecture:** Configured with Google's official test IDs, COPPA compliance (`tagForChildDirectedTreatment = true`), and max content rating `G`.

---

## 🛠 Technology Stack

- **Language:** Kotlin 2.2.10
- **UI Framework:** Jetpack Compose (Material Design 3)
- **Audio Engine:** Android Native `TextToSpeech` & `ToneGenerator`
- **Build System:** Gradle Kotlin DSL (`build.gradle.kts`) with Version Catalog (`libs.versions.toml`)
- **Testing:** JUnit 4 & Robolectric 4.16

---

## 📋 Requirements

- **Minimum SDK:** Android 7.0 (API Level 24)
- **Target / Compile SDK:** Android 16 / API Level 36
- **JDK:** Java Development Kit 11 or higher
- **Android Studio:** Ladybug or newer recommended

---

## 🚀 Getting Started

### 1. Clone the Repository
```bash
git clone https://github.com/vivescriptsolutions/abcoo.git
cd abcoo
```

### 2. Build the Project
To compile the debug APK:
```bash
gradle assembleDebug
```

To run all unit and Robolectric tests:
```bash
gradle :app:testDebugUnitTest
```

### 3. Generate Release App Bundle (AAB)
To generate the production bundle for Google Play submission:
```bash
gradle :app:bundleRelease
```
The output `.aab` will be generated in `app/build/outputs/bundle/release/`.

---

## 📂 Project Architecture

```text
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── AndroidManifest.xml
│   │   │   ├── java/com/example/
│   │   │   │   ├── MainActivity.kt                  # Root activity, tabs, and navigation
│   │   │   │   ├── ads/
│   │   │   │   │   └── AdConfig.kt                  # AdMob test IDs & COPPA configuration
│   │   │   │   ├── audio/
│   │   │   │   │   └── SpeechManager.kt             # TTS pronunciation & audio synthesis
│   │   │   │   ├── data/
│   │   │   │   │   └── AlphabetData.kt              # Complete 26-letter curriculum repository
│   │   │   │   ├── ui/
│   │   │   │   │   ├── components/
│   │   │   │   │   │   └── AlphabetRibbon.kt        # Quick-jump alphabet scroll ribbon
│   │   │   │   │   ├── illustrations/
│   │   │   │   │   │   └── AlphabetIllustrations.kt # 26 animated 2D vector canvas drawings
│   │   │   │   │   ├── screens/
│   │   │   │   │   │   ├── LetterDetailScreen.kt    # Main learning stage & auto-play
│   │   │   │   │   │   ├── AlphabetGridScreen.kt    # 26-letter interactive explorer grid
│   │   │   │   │   │   ├── LetterQuizScreen.kt      # "Listen & Find" audio game
│   │   │   │   │   │   └── ParentCornerDialog.kt    # Parental gate & settings dialog
│   │   │   │   │   └── theme/
│   │   │   │   │       ├── Color.kt                 # Material 3 child-friendly colors
│   │   │   │   │       ├── Theme.kt                 # Centralized M3 theming
│   │   │   │   │       └── Type.kt                  # Typography definitions
│   │   │   └── res/                                 # Drawables, mipmaps, and string resources
│   │   └── test/                                    # Unit & Robolectric test suite
├── store_assets/                                    # Official 512x512 icon, 1024x500 banner, screenshots
├── CHANGELOG.md                                     # Release version history
├── DESIGN_ASSETS.md                                 # Graphics and dimensions specifications
├── LICENSE                                          # MIT License + Proprietary branding notice
├── PLAY_STORE_DATA_SAFETY.md                        # Google Play Data Safety answers
├── PLAY_STORE_METADATA.md                           # ASO description and store listing
├── PRIVACY_POLICY.md                                # Full COPPA and Families privacy policy
├── TERMS_OF_SERVICE.md                              # Legal Terms of Service
└── settings.gradle.kts
```

---

## 🔒 Privacy & Safety

- **Zero Personal Data:** No names, emails, identifiers, or tracking telemetry.
- **Offline-First:** All content is packaged locally; no server dependency.
- **COPPA & Google Play Families Compliant:** Meets all child privacy and advertising restrictions.

---

## 📄 License & Proprietary Branding

The original source code of ABCoo is licensed under the **MIT License**. See the `LICENSE` file for details.

**Proprietary Branding Notice:**  
The application name ("ABCoo"), logo, custom adaptive icons, store graphics, screenshots, marketing artwork, trade dress, and trademarks are the proprietary property of **ViveScript Solutions LLC** and are **NOT** licensed under the MIT License.

---

## 🏢 Copyright

**© 2026 ViveScript Solutions LLC.** All rights reserved.  
Website: [https://www.vivescriptsolutions.com/](https://www.vivescriptsolutions.com/)
