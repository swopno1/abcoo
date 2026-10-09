package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FamilyRestroom
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ads.AdManager
import com.example.audio.SpeechManager
import com.example.data.AlphabetRepository
import com.example.ui.screens.AlphabetGridScreen
import com.example.ui.screens.LetterDetailScreen
import com.example.ui.screens.LetterQuizScreen
import com.example.ui.screens.ParentCornerDialog
import com.example.ui.theme.MyApplicationTheme

enum class MainTab(val title: String, val icon: ImageVector) {
    LEARN("Learn", Icons.Filled.School),
    GRID("A–Z Grid", Icons.Filled.GridView),
    PLAY("Play Quiz", Icons.Filled.SportsEsports)
}

class MainActivity : ComponentActivity() {

    private lateinit var speechManager: SpeechManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        speechManager = SpeechManager(this)
        AdManager.initialize(this)
        enableEdgeToEdge()

        setContent {
            MyApplicationTheme {
                ABCooApp(
                    speechManager = speechManager,
                    onTriggerInterstitial = { AdManager.showInterstitial(this@MainActivity) }
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        speechManager.shutdown()
    }
}

@Composable
fun ABCooApp(
    speechManager: SpeechManager,
    onTriggerInterstitial: () -> Unit = {}
) {
    var selectedTab by rememberSaveable { mutableStateOf(MainTab.LEARN) }
    var currentLetterIndex by rememberSaveable { mutableIntStateOf(0) }
    var isAutoPlay by rememberSaveable { mutableStateOf(false) }
    var isBanglaEnabled by rememberSaveable { mutableStateOf(true) }
    var showParentCorner by remember { mutableStateOf(false) }

    val alphabetItems = remember { AlphabetRepository.items }

    // If on subscreen and user presses system back button, navigate back to Learn tab
    if (selectedTab != MainTab.LEARN) {
        BackHandler {
            selectedTab = MainTab.LEARN
        }
    }

    if (showParentCorner) {
        ParentCornerDialog(onDismiss = { showParentCorner = false })
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.statusBars),
        topBar = {
            // Cheerful Top Application Bar
            Surface(
                color = Color.White,
                shadowElevation = 3.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Logo and Branding
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(38.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color(0xFFE53935)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "A",
                                color = Color.White,
                                fontWeight = FontWeight.Black,
                                fontSize = 24.sp
                            )
                        }
                        Column {
                            Text(
                                text = "ABCoo",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Black,
                                color = Color(0xFF1E293B)
                            )
                            Text(
                                text = "Fun Alphabet Learning",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color(0xFF64748B)
                            )
                        }
                    }

                    // Parents Corner Icon Button (Guarded with Parental Gate)
                    IconButton(
                        onClick = { showParentCorner = true },
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(Color(0xFFF1F5F9))
                            .testTag("button_parent_corner")
                    ) {
                        Icon(
                            imageVector = Icons.Filled.FamilyRestroom,
                            contentDescription = "Parents Corner",
                            tint = Color(0xFF475569)
                        )
                    }
                }
            }
        },
        bottomBar = {
            // Bottom Navigation with Large Child-Friendly Tabs
            NavigationBar(
                containerColor = Color.White,
                tonalElevation = 6.dp,
                modifier = Modifier
                    .windowInsetsPadding(WindowInsets.navigationBars)
                    .height(64.dp)
                    .testTag("main_bottom_nav")
            ) {
                MainTab.entries.forEach { tab ->
                    val isSelected = selectedTab == tab
                    NavigationBarItem(
                        selected = isSelected,
                        onClick = {
                            selectedTab = tab
                            speechManager.playPopSound()
                        },
                        icon = {
                            Icon(
                                imageVector = tab.icon,
                                contentDescription = tab.title,
                                modifier = Modifier.size(24.dp)
                            )
                        },
                        label = {
                            Text(
                                text = tab.title,
                                fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Medium,
                                fontSize = 12.sp
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color(0xFF1E88E5),
                            selectedTextColor = Color(0xFF1E88E5),
                            indicatorColor = Color(0xFFE3F2FD),
                            unselectedIconColor = Color(0xFF94A3B8),
                            unselectedTextColor = Color(0xFF94A3B8)
                        ),
                        modifier = Modifier.testTag("nav_tab_${tab.name.lowercase()}")
                    )
                }
            }
        }
    ) { innerPadding ->
        Crossfade(
            targetState = selectedTab,
            label = "tabCrossfade",
            modifier = Modifier.padding(innerPadding)
        ) { tab ->
            when (tab) {
                MainTab.LEARN -> {
                    LetterDetailScreen(
                        items = alphabetItems,
                        currentIndex = currentLetterIndex,
                        isAutoPlay = isAutoPlay,
                        isBanglaEnabled = isBanglaEnabled,
                        speechManager = speechManager,
                        onIndexChanged = { newIndex ->
                            currentLetterIndex = newIndex
                        },
                        onToggleAutoPlay = {
                            isAutoPlay = !isAutoPlay
                        },
                        onToggleBangla = {
                            isBanglaEnabled = !isBanglaEnabled
                        }
                    )
                }
                MainTab.GRID -> {
                    AlphabetGridScreen(
                        items = alphabetItems,
                        isBanglaEnabled = isBanglaEnabled,
                        speechManager = speechManager,
                        onSelectLetter = { selectedIndex ->
                            currentLetterIndex = selectedIndex
                            selectedTab = MainTab.LEARN
                        }
                    )
                }
                MainTab.PLAY -> {
                    LetterQuizScreen(
                        items = alphabetItems,
                        speechManager = speechManager,
                        onMilestoneReached = onTriggerInterstitial
                    )
                }
            }
        }
    }
}
