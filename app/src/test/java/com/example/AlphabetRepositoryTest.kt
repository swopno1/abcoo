package com.example

import com.example.ads.AdConfig
import com.example.data.AlphabetRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class AlphabetRepositoryTest {

    @Test
    fun alphabetRepository_containsAll26Letters() {
        val items = AlphabetRepository.items
        assertEquals("Repository must have exactly 26 alphabet letters", 26, items.size)
    }

    @Test
    fun alphabetRepository_lettersAreStrictlyOrderedFromAToZ() {
        val items = AlphabetRepository.items
        val expectedLetters = ('A'..'Z').map { it.toString() }
        val actualLetters = items.map { it.uppercase }
        assertEquals(expectedLetters, actualLetters)
    }

    @Test
    fun alphabetRepository_noEmptyWordsOrPhonics() {
        val items = AlphabetRepository.items
        items.forEach { item ->
            assertTrue("Item ${item.uppercase} must have non-empty word", item.word.isNotBlank())
            assertTrue("Item ${item.uppercase} must have non-empty phonics", item.phonics.isNotBlank())
            assertTrue("Item ${item.uppercase} must have non-empty bangla word", item.banglaWord.isNotBlank())
            assertEquals(item.uppercase.lowercase(), item.lowercase)
        }
    }

    @Test
    fun adConfig_isProperlyConfiguredWithUserIds() {
        assertEquals("ca-app-pub-5222053984568989~6084120595", AdConfig.PRODUCTION_APP_ID)
        assertEquals("ca-app-pub-5222053984568989/7294395957", AdConfig.PRODUCTION_BANNER_ID)
        assertEquals("ca-app-pub-5222053984568989/6630296959", AdConfig.PRODUCTION_INTERSTITIAL_ID)
        assertTrue("Ads must be enabled", AdConfig.ADS_ENABLED)
        assertEquals(AdConfig.PRODUCTION_BANNER_ID, AdConfig.getBannerAdUnitId())
        assertEquals(AdConfig.PRODUCTION_INTERSTITIAL_ID, AdConfig.getInterstitialAdUnitId())
    }
}
