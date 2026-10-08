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
    fun adConfig_isProperlyConfiguredForTestAndFamilies() {
        assertTrue("Test mode should be enabled for development", AdConfig.IS_TEST_MODE)
        assertTrue("Child directed treatment must be enabled", AdConfig.CHILD_DIRECTED_TREATMENT)
        assertEquals("Max ad content rating must be G for families", "G", AdConfig.MAX_AD_CONTENT_RATING)
        assertTrue("Test App ID must be official Google test ID", AdConfig.TEST_APP_ID.contains("ca-app-pub-3940256099942544"))
    }
}
