package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.Celebration
import androidx.compose.material.icons.filled.Refresh
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import com.example.ui.illustrations.AlphabetIllustration
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Pressure-free interactive "Listen & Find" game for toddlers.
 * Prompts with sound and celebrates every correct identification.
 */
@Composable
fun LetterQuizScreen(
    items: List<AlphabetItem>,
    speechManager: SpeechManager,
    modifier: Modifier = Modifier
) {
    var targetIndex by remember { mutableIntStateOf((0 until items.size).random()) }
    val targetItem = items[targetIndex]

    // Create 3 choices (1 target + 2 random distractors)
    val choices = remember(targetIndex) {
        val distractors = items.filter { it.id != targetItem.id }.shuffled().take(2)
        (listOf(targetItem) + distractors).shuffled()
    }

    var showSuccessCelebration by remember { mutableStateOf(false) }
    var scoreCount by remember { mutableIntStateOf(0) }
    val coroutineScope = rememberCoroutineScope()

    // Speak initial prompt whenever target letter changes
    LaunchedEffect(targetIndex) {
        showSuccessCelebration = false
        delay(300)
        speechManager.speakQuizPrompt(targetItem)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF1F8E9))
            .verticalScroll(rememberScrollState())
            .testTag("letter_quiz_screen"),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Top Game Header
        Surface(
            color = Color.White,
            shadowElevation = 2.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Listen & Find!",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFF2E7D32)
                    )
                    Text(
                        text = "Can you find the matching letter?",
                        fontSize = 12.sp,
                        color = Color(0xFF64748B)
                    )
                }

                // Star Score Pill
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = Color(0xFFFFF9C4),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFFFD54F))
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(text = "⭐", fontSize = 16.sp)
                        Text(
                            text = "$scoreCount Stars",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFF57F17)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Center Target Clue Box (shows illustration + sound button)
        Card(
            shape = RoundedCornerShape(28.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .fillMaxWidth()
                .border(3.dp, targetItem.secondaryColor, RoundedCornerShape(28.dp))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Find the letter for:",
                    fontSize = 14.sp,
                    color = Color(0xFF78909C),
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = targetItem.word,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Black,
                    color = targetItem.primaryColor
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Small clue illustration
                Box(
                    modifier = Modifier
                        .size(140.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(targetItem.secondaryColor.copy(alpha = 0.35f))
                        .padding(10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    AlphabetIllustration(
                        letter = targetItem.uppercase,
                        modifier = Modifier.size(120.dp),
                        onTap = {
                            speechManager.speakQuizPrompt(targetItem)
                        }
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Hear Question Again Button
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = targetItem.primaryColor,
                    modifier = Modifier
                        .clickable { speechManager.speakQuizPrompt(targetItem) }
                        .testTag("button_repeat_prompt")
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                            contentDescription = "Hear prompt again",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = "Hear Sound Again",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Success Celebration Banner
        AnimatedVisibility(
            visible = showSuccessCelebration,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFC8E6C9)),
                modifier = Modifier
                    .padding(horizontal = 24.dp, vertical = 6.dp)
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Celebration,
                        contentDescription = null,
                        tint = Color(0xFF2E7D32),
                        modifier = Modifier.size(28.dp)
                    )
                    Text(
                        text = " Yay! That's ${targetItem.uppercase} for ${targetItem.word}! 🎉",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 16.sp,
                        color = Color(0xFF1B5E20)
                    )
                }
            }
        }

        // Choice Options Cards (3 large cards side by side)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            choices.forEach { choice ->
                val isCorrect = choice.id == targetItem.id
                Card(
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = choice.backgroundColor
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    modifier = Modifier
                        .weight(1f)
                        .height(110.dp)
                        .border(3.dp, choice.secondaryColor, RoundedCornerShape(24.dp))
                        .clickable {
                            if (isCorrect) {
                                showSuccessCelebration = true
                                scoreCount++
                                speechManager.speakEncouragement()
                                coroutineScope.launch {
                                    delay(2000)
                                    targetIndex = (0 until items.size).random()
                                }
                            } else {
                                speechManager.speakLetter(choice.uppercase, choice.lowercase)
                            }
                        }
                        .testTag("quiz_choice_${choice.uppercase}")
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = choice.uppercase,
                            fontSize = 48.sp,
                            fontWeight = FontWeight.Black,
                            color = choice.primaryColor
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Next Question Button
        Surface(
            shape = RoundedCornerShape(24.dp),
            color = Color.White,
            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0)),
            modifier = Modifier
                .clickable {
                    targetIndex = (0 until items.size).random()
                }
                .testTag("button_skip_quiz")
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 18.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Refresh,
                    contentDescription = "Next Question",
                    tint = Color(0xFF64748B),
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = "Next Letter",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF334155)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Child-safe test ad banner
        KidSafeAdBanner(modifier = Modifier.padding(bottom = 8.dp))
    }
}
