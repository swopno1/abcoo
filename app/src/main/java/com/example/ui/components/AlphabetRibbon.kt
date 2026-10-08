package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.AlphabetItem

/**
 * Child-friendly horizontal alphabet ribbon for instant letter selection.
 * Features large 48dp touch targets, smooth scrolling, and bouncy active states.
 */
@Composable
fun AlphabetRibbon(
    items: List<AlphabetItem>,
    selectedIndex: Int,
    onSelectLetter: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val listState = rememberLazyListState()

    // Smoothly scroll to selected letter whenever index changes
    LaunchedEffect(selectedIndex) {
        if (selectedIndex in items.indices) {
            val targetOffset = (selectedIndex - 2).coerceAtLeast(0)
            listState.animateScrollToItem(targetOffset)
        }
    }

    LazyRow(
        state = listState,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .testTag("alphabet_ribbon"),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        itemsIndexed(items) { index, item ->
            val isSelected = index == selectedIndex
            val scale by animateFloatAsState(
                targetValue = if (isSelected) 1.18f else 1.0f,
                animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
                label = "ribbonScale"
            )
            val bgColor by animateColorAsState(
                targetValue = if (isSelected) item.primaryColor else item.secondaryColor.copy(alpha = 0.5f),
                label = "ribbonBg"
            )
            val textColor by animateColorAsState(
                targetValue = if (isSelected) Color.White else item.primaryColor,
                label = "ribbonText"
            )

            Box(
                modifier = Modifier
                    .size(46.dp)
                    .scale(scale)
                    .clip(CircleShape)
                    .background(bgColor)
                    .clickable { onSelectLetter(index) }
                    .testTag("ribbon_item_${item.uppercase}"),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = item.uppercase,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 20.sp,
                    color = textColor
                )
            }
        }
    }
}
