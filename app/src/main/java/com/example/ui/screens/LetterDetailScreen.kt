package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ads.KidSafeAdBanner
import com.example.audio.SpeechManager
import com.example.data.AlphabetItem
import com.example.ui.components.AlphabetRibbon
import com.example.ui.illustrations.AlphabetIllustration
import kotlinx.coroutines.delay

/**
 * Primary Learning Screen: Showcases letter, phonics sound, word, Bangla translation,
 * and animated 2D illustration with large child-friendly controls.
 */
@Composable
fun LetterDetailScreen(
    items: List<AlphabetItem>,
    currentIndex: Int,
    isAutoPlay: Boolean,
    isBanglaEnabled: Boolean,
    speechManager: SpeechManager,
    onIndexChanged: (Int) -> Unit,
    onToggleAutoPlay: () -> Unit,
    onToggleBangla: () -> Unit,
    modifier: Modifier = Modifier
) {
    val currentItem = items.getOrElse(currentIndex) { items.first() }

    // Automatic guided walkthrough timer if auto-play is enabled
    LaunchedEffect(currentIndex, isAutoPlay) {
        if (isAutoPlay) {
            speechManager.speakPhonicsSequence(currentItem, isBanglaEnabled)
            delay(4200)
            val nextIndex = (currentIndex + 1) % items.size
            onIndexChanged(nextIndex)
        }
    }

    // Pronounce initial letter on manual selection
    LaunchedEffect(currentIndex) {
        if (!isAutoPlay) {
            speechManager.speakPhonicsSequence(currentItem, isBanglaEnabled)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        currentItem.backgroundColor,
                        Color.White
                    )
                )
            )
            .testTag("letter_detail_screen")
    ) {
        // Quick A-Z letter ribbon
        AlphabetRibbon(
            items = items,
            selectedIndex = currentIndex,
            onSelectLetter = onIndexChanged,
            modifier = Modifier.padding(top = 4.dp, bottom = 2.dp)
        )

        // Main Center Learning Stage (Scrollable for smaller screens/foldables)
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(6.dp))

            // Large Upper & Lowercase Letter Header Card
            Card(
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(3.dp, currentItem.secondaryColor, RoundedCornerShape(28.dp))
                    .clickable {
                        speechManager.speakLetter(currentItem.uppercase, currentItem.lowercase)
                    }
                    .testTag("letter_card_main")
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp, horizontal = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Big Letters: "A a"
                    Row(
                        verticalAlignment = Alignment.Bottom,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(
                            text = currentItem.uppercase,
                            fontSize = 64.sp,
                            fontWeight = FontWeight.Black,
                            color = currentItem.primaryColor,
                            modifier = Modifier.testTag("text_uppercase")
                        )
                        Text(
                            text = currentItem.lowercase,
                            fontSize = 44.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = currentItem.primaryColor.copy(alpha = 0.85f),
                            modifier = Modifier
                                .padding(bottom = 6.dp)
                                .testTag("text_lowercase")
                        )
                    }

                    // Phonics Sound Badge
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = currentItem.secondaryColor.copy(alpha = 0.6f),
                        modifier = Modifier
                            .clickable {
                                speechManager.speakPhonicsSequence(currentItem, isBanglaEnabled)
                            }
                            .testTag("phonics_sound_badge")
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                                contentDescription = "Hear phonics sound",
                                tint = currentItem.primaryColor,
                                modifier = Modifier.size(22.dp)
                            )
                            Text(
                                text = currentItem.phonics,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = currentItem.primaryColor
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Word Title & Bilingual translation
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        speechManager.speakWord(currentItem, isBanglaEnabled)
                    }
                    .testTag("word_banner")
            ) {
                Text(
                    text = currentItem.word,
                    fontSize = 38.sp,
                    fontWeight = FontWeight.Black,
                    color = currentItem.primaryColor,
                    letterSpacing = 1.sp,
                    textAlign = TextAlign.Center
                )

                // Bangla Bilingual Context
                AnimatedVisibility(
                    visible = isBanglaEnabled,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Text(
                        text = currentItem.banglaWord,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF455A64),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }

                Text(
                    text = currentItem.funFact,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF78909C),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Animated 2D Illustration Stage (Touch to trigger bounce & narration)
            Box(
                modifier = Modifier
                    .size(240.dp)
                    .clip(RoundedCornerShape(32.dp))
                    .background(currentItem.secondaryColor.copy(alpha = 0.35f))
                    .border(2.dp, currentItem.secondaryColor.copy(alpha = 0.6f), RoundedCornerShape(32.dp))
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                AlphabetIllustration(
                    letter = currentItem.uppercase,
                    modifier = Modifier.size(200.dp),
                    onTap = {
                        speechManager.speakWord(currentItem, isBanglaEnabled)
                    }
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Gentle encouragement hint
            Text(
                text = "Tap the letter or picture to hear its sound!",
                fontSize = 13.sp,
                color = Color(0xFF90A4AE),
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(12.dp))
        }

        // Child-Friendly Large Navigation Controls Bar
        Surface(
            color = Color.White,
            shadowElevation = 8.dp,
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Previous Letter Button (Min 56dp)
                    IconButton(
                        onClick = {
                            val prevIndex = if (currentIndex - 1 < 0) items.size - 1 else currentIndex - 1
                            onIndexChanged(prevIndex)
                        },
                        modifier = Modifier
                            .size(56.dp)
                            .shadow(2.dp, CircleShape)
                            .background(Color(0xFFF1F5F9), CircleShape)
                            .testTag("button_prev_letter")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Previous Letter",
                            tint = Color(0xFF334155),
                            modifier = Modifier.size(32.dp)
                        )
                    }

                    // Main Speak / Repeat Button (68dp Big Floating Center Button)
                    Box(
                        modifier = Modifier
                            .size(68.dp)
                            .shadow(6.dp, CircleShape)
                            .background(currentItem.primaryColor, CircleShape)
                            .clickable {
                                speechManager.speakPhonicsSequence(currentItem, isBanglaEnabled)
                            }
                            .testTag("button_speak_repeat"),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.RecordVoiceOver,
                            contentDescription = "Pronounce Letter and Word",
                            tint = Color.White,
                            modifier = Modifier.size(36.dp)
                        )
                    }

                    // Next Letter Button (Min 56dp)
                    IconButton(
                        onClick = {
                            val nextIndex = (currentIndex + 1) % items.size
                            onIndexChanged(nextIndex)
                        },
                        modifier = Modifier
                            .size(56.dp)
                            .shadow(2.dp, CircleShape)
                            .background(Color(0xFFF1F5F9), CircleShape)
                            .testTag("button_next_letter")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = "Next Letter",
                            tint = Color(0xFF334155),
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Bottom feature toggles: Auto-Play slideshow & Bangla toggle
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Auto-Play Slideshow Pill
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = if (isAutoPlay) Color(0xFFE8F5E9) else Color(0xFFF8FAFC),
                        border = androidx.compose.foundation.BorderStroke(
                            1.dp,
                            if (isAutoPlay) Color(0xFF81C784) else Color(0xFFE2E8F0)
                        ),
                        modifier = Modifier
                            .clickable { onToggleAutoPlay() }
                            .testTag("toggle_autoplay")
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = if (isAutoPlay) Icons.Filled.Pause else Icons.Filled.PlayArrow,
                                contentDescription = "Auto-Play Slideshow",
                                tint = if (isAutoPlay) Color(0xFF2E7D32) else Color(0xFF64748B),
                                modifier = Modifier.size(18.dp)
                            )
                            Text(
                                text = if (isAutoPlay) "Auto-Play: ON" else "Auto-Play: OFF",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isAutoPlay) Color(0xFF2E7D32) else Color(0xFF64748B)
                            )
                        }
                    }

                    // Language / Bangla context toggle
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = if (isBanglaEnabled) Color(0xFFFFF3E0) else Color(0xFFF8FAFC),
                        border = androidx.compose.foundation.BorderStroke(
                            1.dp,
                            if (isBanglaEnabled) Color(0xFFFFB74D) else Color(0xFFE2E8F0)
                        ),
                        modifier = Modifier
                            .clickable { onToggleBangla() }
                            .testTag("toggle_bangla")
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Translate,
                                contentDescription = "Bilingual Context",
                                tint = if (isBanglaEnabled) Color(0xFFE65100) else Color(0xFF64748B),
                                modifier = Modifier.size(18.dp)
                            )
                            Text(
                                text = if (isBanglaEnabled) "English + বাংলা" else "English Only",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isBanglaEnabled) Color(0xFFE65100) else Color(0xFF64748B)
                            )
                        }
                    }
                }

                // Child-safe Ad banner
                KidSafeAdBanner(modifier = Modifier.padding(top = 4.dp))
            }
        }
    }
}
