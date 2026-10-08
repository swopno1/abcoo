package com.example.ads

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Centralized AdMob & Google Play Families Advertising Configuration.
 *
 * GOOGLE PLAY FAMILIES POLICY COMPLIANCE:
 * - ABCoo is an early childhood educational application designed for young children under 13.
 * - Under Google Play Families Policy and COPPA, all advertisements MUST be served with:
 *     1. TAG_FOR_CHILD_DIRECTED_TREATMENT = TRUE
 *     2. MAX_AD_CONTENT_RATING = "G"
 *     3. Personalized advertising (interest-based targeting) DISABLED.
 *
 * PRODUCTION DEPLOYMENT:
 * To switch to production ads once approved by ViveScript Solutions LLC:
 * 1. Replace [TEST_APP_ID] with your production AdMob App ID.
 * 2. Replace [TEST_BANNER_ID] with your production AdMob Banner ID.
 * 3. Replace [TEST_INTERSTITIAL_ID] with your production AdMob Interstitial ID.
 */
object AdConfig {
    // Official Google AdMob Test App ID
    const val TEST_APP_ID = "ca-app-pub-3940256099942544~3347511713"

    // Official Google AdMob Test Ad Unit IDs
    const val TEST_BANNER_ID = "ca-app-pub-3940256099942544/6300978111"
    const val TEST_INTERSTITIAL_ID = "ca-app-pub-3940256099942544/1033173712"

    // Set to true to display the child-safe test ad banner in development
    const val IS_TEST_MODE = true

    // Set to false if ads are disabled (e.g. ad-free preschool experience)
    const val ADS_ENABLED = true

    // Designed for Families COPPA configuration constants
    const val CHILD_DIRECTED_TREATMENT = true
    const val MAX_AD_CONTENT_RATING = "G"
}

/**
 * Child-safe, unobtrusive Ad banner placeholder adhering to Designed for Families guidelines.
 * Displays clear labeling so it cannot be mistaken for game elements or accidental clicks.
 */
@Composable
fun KidSafeAdBanner(modifier: Modifier = Modifier) {
    if (!AdConfig.ADS_ENABLED) return

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFF1F5F9))
            .border(1.dp, Color(0xFFCBD5E1), RoundedCornerShape(12.dp))
            .testTag("ad_banner_container"),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.Info,
                contentDescription = null,
                tint = Color(0xFF64748B),
                modifier = Modifier.size(16.dp)
            )
            Text(
                text = " ABCoo Kid-Safe Partner Area (Test Ad Configured) ",
                fontSize = 11.sp,
                color = Color(0xFF64748B),
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}
