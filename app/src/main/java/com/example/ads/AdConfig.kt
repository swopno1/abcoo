package com.example.ads

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

/**
 * Centralized AdMob & Google Play Families Advertising Configuration.
 *
 * Configured with user-provided production AdMob IDs:
 * - App ID: ca-app-pub-5222053984568989~6084120595
 * - Banner 1 ID: ca-app-pub-5222053984568989/7294395957
 * - Interstitial 1 ID: ca-app-pub-5222053984568989/6630296959
 */
object AdConfig {
    // User Provided AdMob Production App ID
    const val PRODUCTION_APP_ID = "ca-app-pub-5222053984568989~6084120595"

    // User Provided AdMob Production Ad Unit IDs
    const val PRODUCTION_BANNER_ID = "ca-app-pub-5222053984568989/7294395957"
    const val PRODUCTION_INTERSTITIAL_ID = "ca-app-pub-5222053984568989/6630296959"

    // Google Official Test Ad Unit IDs (for testing on emulators/devices)
    const val TEST_BANNER_ID = "ca-app-pub-3940256099942544/6300978111"
    const val TEST_INTERSTITIAL_ID = "ca-app-pub-3940256099942544/1033173712"

    // Set to false for production release builds with live user ads
    var useTestAds: Boolean = false

    // Master toggle for ads
    const val ADS_ENABLED = true

    // Minimum cooldown in milliseconds between interstitial displays (policy compliance)
    const val INTERSTITIAL_COOLDOWN_MS = 60_000L

    fun getBannerAdUnitId(): String {
        return if (useTestAds) TEST_BANNER_ID else PRODUCTION_BANNER_ID
    }

    fun getInterstitialAdUnitId(): String {
        return if (useTestAds) TEST_INTERSTITIAL_ID else PRODUCTION_INTERSTITIAL_ID
    }
}

/**
 * Singleton managing Google Mobile Ads initialization and Interstitial ads.
 */
object AdManager {
    private const val TAG = "AdManager"
    private var isInitialized = false
    private var interstitialAd: InterstitialAd? = null
    private var isInterstitialLoading = false
    private var lastInterstitialShowTime = 0L

    /**
     * Initializes Google Mobile Ads SDK with COPPA & Google Play Families compliance.
     */
    fun initialize(context: Context) {
        if (isInitialized) return

        try {
            // Strictly enforce Google Play Families & COPPA child-directed treatment
            val requestConfiguration = RequestConfiguration.Builder()
                .setTagForChildDirectedTreatment(RequestConfiguration.TAG_FOR_CHILD_DIRECTED_TREATMENT_TRUE)
                .setMaxAdContentRating(RequestConfiguration.MAX_AD_CONTENT_RATING_G)
                .build()

            MobileAds.setRequestConfiguration(requestConfiguration)

            MobileAds.initialize(context) { status ->
                isInitialized = true
                Log.d(TAG, "Google Mobile Ads initialized successfully: $status")
                // Preload an interstitial ad for natural transition moments
                loadInterstitial(context)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Failed to initialize Google Mobile Ads", e)
        }
    }

    /**
     * Loads an Interstitial Ad using child-directed request parameters.
     */
    fun loadInterstitial(context: Context) {
        if (!AdConfig.ADS_ENABLED || isInterstitialLoading || interstitialAd != null) return

        isInterstitialLoading = true
        val adRequest = AdRequest.Builder().build()
        val adUnitId = AdConfig.getInterstitialAdUnitId()

        InterstitialAd.load(
            context,
            adUnitId,
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(ad: InterstitialAd) {
                    interstitialAd = ad
                    isInterstitialLoading = false
                    Log.d(TAG, "Interstitial ad loaded successfully.")
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    interstitialAd = null
                    isInterstitialLoading = false
                    Log.w(TAG, "Interstitial ad failed to load: ${loadAdError.message}")
                }
            }
        )
    }

    /**
     * Shows the interstitial ad if loaded and cooldown period has elapsed.
     */
    fun showInterstitial(activity: Activity, onComplete: () -> Unit = {}) {
        val now = System.currentTimeMillis()
        val ad = interstitialAd

        if (!AdConfig.ADS_ENABLED || ad == null || (now - lastInterstitialShowTime < AdConfig.INTERSTITIAL_COOLDOWN_MS)) {
            // Cooldown not met, or ad not ready - proceed smoothly without interrupting user
            onComplete()
            if (ad == null && !isInterstitialLoading) {
                loadInterstitial(activity)
            }
            return
        }

        ad.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                interstitialAd = null
                lastInterstitialShowTime = System.currentTimeMillis()
                Log.d(TAG, "Interstitial dismissed.")
                onComplete()
                loadInterstitial(activity)
            }

            override fun onAdFailedToShowFullScreenContent(adError: com.google.android.gms.ads.AdError) {
                interstitialAd = null
                Log.w(TAG, "Interstitial failed to show: ${adError.message}")
                onComplete()
                loadInterstitial(activity)
            }

            override fun onAdShowedFullScreenContent() {
                Log.d(TAG, "Interstitial displayed.")
            }
        }

        ad.show(activity)
    }
}

/**
 * Child-safe, banner ad component embedded in Jetpack Compose using AndroidView.
 */
@Composable
fun KidSafeAdBanner(
    modifier: Modifier = Modifier,
    adUnitId: String = AdConfig.getBannerAdUnitId()
) {
    if (!AdConfig.ADS_ENABLED) return

    val context = LocalContext.current
    var isAdLoaded by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp)
            .padding(horizontal = 16.dp, vertical = 2.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFF8FAFC))
            .border(1.dp, Color(0xFFE2E8F0), RoundedCornerShape(12.dp))
            .testTag("ad_banner_container"),
        contentAlignment = Alignment.Center
    ) {
        AndroidView(
            modifier = Modifier.fillMaxWidth(),
            factory = { ctx ->
                AdView(ctx).apply {
                    setAdSize(AdSize.BANNER)
                    this.adUnitId = adUnitId
                    adListener = object : AdListener() {
                        override fun onAdLoaded() {
                            isAdLoaded = true
                            Log.d("KidSafeAdBanner", "Banner ad loaded successfully.")
                        }

                        override fun onAdFailedToLoad(error: LoadAdError) {
                            isAdLoaded = false
                            Log.w("KidSafeAdBanner", "Banner ad failed to load: ${error.message}")
                        }
                    }
                    val adRequest = AdRequest.Builder().build()
                    loadAd(adRequest)
                }
            },
            update = { adView ->
                // Maintain active adView lifecycle
            }
        )
    }
}
