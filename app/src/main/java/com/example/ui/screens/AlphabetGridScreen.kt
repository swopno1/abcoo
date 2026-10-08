package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ads.KidSafeAdBanner
import com.example.audio.SpeechManager
import com.example.data.AlphabetItem

/**
 * 26-Letter interactive grid allowing toddlers to freely browse and tap any letter.
 * Responsive adaptive grid layout supporting compact phones and expanded tablets.
 */
@Composable
fun AlphabetGridScreen(
    items: List<AlphabetItem>,
    isBanglaEnabled: Boolean,
    speechManager: SpeechManager,
    onSelectLetter: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAFC))
            .testTag("alphabet_grid_screen")
    ) {
        // Cheerful Header Banner
        Surface(
            color = Color.White,
            shadowElevation = 2.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Alphabet A to Z",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF1E293B)
                )
                Text(
                    text = "Tap any letter to hear its sound and discover its friend!",
                    fontSize = 13.sp,
                    color = Color(0xFF64748B),
                    textAlign = TextAlign.Center
                )
            }
        }

        // Adaptive Alphabet Grid (3 columns on phones, 4-6 on tablets)
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 100.dp),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .testTag("alphabet_grid_list")
        ) {
            itemsIndexed(items) { index, item ->
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = item.backgroundColor
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(2.dp, item.secondaryColor, RoundedCornerShape(20.dp))
                        .clickable {
                            speechManager.speakLetter(item.uppercase, item.lowercase)
                            onSelectLetter(index)
                        }
                        .testTag("grid_letter_${item.uppercase}")
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 14.dp, horizontal = 8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        // Letter Pair: "A a"
                        Row(
                            verticalAlignment = Alignment.Bottom,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = item.uppercase,
                                fontSize = 34.sp,
                                fontWeight = FontWeight.Black,
                                color = item.primaryColor
                            )
                            Text(
                                text = item.lowercase,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = item.primaryColor.copy(alpha = 0.8f),
                                modifier = Modifier.padding(start = 4.dp, bottom = 2.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        // Word Badge
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .background(item.secondaryColor.copy(alpha = 0.6f))
                                .padding(horizontal = 8.dp, vertical = 2.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = item.word,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = item.primaryColor
                            )
                        }

                        if (isBanglaEnabled) {
                            Text(
                                text = item.banglaWord.substringBefore(" ("),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color(0xFF546E7A),
                                modifier = Modifier.padding(top = 2.dp)
                            )
                        }
                    }
                }
            }
        }

        // Child-Safe Test Ad Banner at bottom
        KidSafeAdBanner(modifier = Modifier.padding(bottom = 8.dp))
    }
}
