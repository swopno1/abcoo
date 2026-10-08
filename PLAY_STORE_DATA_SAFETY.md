# Google Play Data Safety Declaration for ABCoo

**Publisher:** ViveScript Solutions LLC  
**Application ID:** `com.vivescriptsolutions.abcoo`  
**Google Play Console Guide:** Follow these precise selections when completing the Data Safety section.

---

## 1. Overview of Data Safety Answers

| Section | Question | Answer | Details / Justification |
| :--- | :--- | :--- | :--- |
| **Data Collection** | Does your app collect or share any user data? | **No** | ABCoo functions completely on-device without collecting personal identifiers. |
| **Data Sharing** | Is data shared with third parties? | **No** | No user data is transferred or shared externally. |
| **Security Practices** | Is data encrypted in transit? | **Yes / N/A** | No user data is transmitted over the network. |
| **Data Deletion** | Do users have a way to request deletion? | **N/A** | No user accounts or personal data are stored externally; uninstalling the app purges all local app data. |
| **Target Age & Families** | Is the app directed at children under 13? | **Yes** | Fully participates in the Google Play Families program and complies with COPPA. |

---

## 2. Detailed Data Type Evaluation

| Data Type Category | Field | Collected? | Shared? | Purpose |
| :--- | :--- | :--- | :--- | :--- |
| **Personal Info** | Name, Email, Address, Phone, User ID | **No** | **No** | Not requested or accessed |
| **Financial Info** | Credit card, Bank info, Purchase history | **No** | **No** | No in-app purchases |
| **Location** | Approximate or Precise Location | **No** | **No** | Zero location permissions |
| **Photos & Videos** | Photos, Videos, Files | **No** | **No** | Zero storage/media permissions |
| **Audio Files** | Voice recordings, Sound files | **No** | **No** | Uses device-internal TTS only |
| **Health & Fitness** | Fitness/Health info | **No** | **No** | None |
| **Messages & Contacts** | SMS, Emails, Contacts | **No** | **No** | None |
| **App Activity** | Page views, Clicks, Interaction history | **No** | **No** | No analytics trackers |
| **App Performance** | Crash logs, Diagnostics | **No** | **No** | No third-party crash reporting SDKs |
| **Device Identifiers** | IMEI, MAC, Advertising ID | **No** | **No** | Advertising ID is NOT used for tracking or profiling (Child-directed mode enabled) |

---

## 3. Advertising & Child-Directed Declaration

- In Google Play Console under **Ads**:
  - Select **"Yes, my app contains ads"** (reflecting the AdMob integration).
- Under **Target Audience and Content**:
  - Target age group: **5 and under**, **6–8**.
  - Appeal to children: **Yes**.
  - All ad networks certified for Families: **Yes** (Google AdMob with `TAG_FOR_CHILD_DIRECTED_TREATMENT = TRUE` and rating `G`).
- Zero tracking or behavioral profiling is permitted or conducted.
