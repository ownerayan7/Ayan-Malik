package com.example.ui.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.LocalFlorist
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.ApologyData
import com.example.model.GradientType
import com.example.model.LittleThing

@Composable
fun LittleThingsScreen(
    onNavigateToMessage: () -> Unit,
    modifier: Modifier = Modifier
) {
    val expandedStates = remember {
        mutableStateMapOf<Int, Boolean>()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card(
            modifier = Modifier
                .width(340.dp)
                .testTag("little_things_card"),
            shape = RoundedCornerShape(36.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 14.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 26.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Title
                Text(
                    text = "Little things I want to tell you...",
                    fontFamily = FontFamily.Serif,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF38233D),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Tap each one 👇",
                    fontSize = 13.sp,
                    color = Color(0xFF86728B),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(20.dp))

                // The 3 friendly pill cards
                ApologyData.littleThings.forEachIndexed { index, item ->
                    val isExpanded = expandedStates[item.id] == true
                    LittleThingPill(
                        item = item,
                        isExpanded = isExpanded,
                        onToggle = {
                            expandedStates[item.id] = !isExpanded
                        },
                        tagIndex = index + 1
                    )
                    Spacer(modifier = Modifier.height(14.dp))
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Next: My message button
                Button(
                    onClick = onNavigateToMessage,
                    modifier = Modifier
                        .fillMaxWidth(0.75f)
                        .height(48.dp)
                        .testTag("my_message_button"),
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFFE0B2)
                    )
                ) {
                    Text(
                        text = "My message ➔",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFFE65100)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        Text(
            text = "Every word is sincere and honest ✨",
            fontSize = 12.sp,
            color = Color(0xFF9C8A9E),
            fontFamily = FontFamily.Serif
        )
    }
}

@Composable
private fun LittleThingPill(
    item: LittleThing,
    isExpanded: Boolean,
    onToggle: () -> Unit,
    tagIndex: Int
) {
    val gradientBrush = when (item.gradientType) {
        GradientType.PEACH -> Brush.horizontalGradient(
            listOf(Color(0xFFFFE0B2), Color(0xFFFFCC80))
        )
        GradientType.PINK -> Brush.horizontalGradient(
            listOf(Color(0xFFFFF9C4), Color(0xFFFFE082))
        )
        GradientType.LAVENDER -> Brush.horizontalGradient(
            listOf(Color(0xFFE1BEE7), Color(0xFFCE93D8))
        )
    }

    val iconVector = when (item.id) {
        1 -> Icons.Filled.Star
        2 -> Icons.Filled.LocalFlorist
        else -> Icons.Filled.AutoAwesome
    }

    val iconColor = when (item.gradientType) {
        GradientType.PEACH -> Color(0xFFE65100)
        GradientType.PINK -> Color(0xFFF57F17)
        GradientType.LAVENDER -> Color(0xFF6A1B9A)
    }

    val textColor = when (item.gradientType) {
        GradientType.PEACH -> Color(0xFF4E2600)
        GradientType.PINK -> Color(0xFF422B00)
        GradientType.LAVENDER -> Color(0xFF29114D)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(gradientBrush)
            .clickable { onToggle() }
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
            .padding(horizontal = 18.dp, vertical = if (isExpanded) 14.dp else 16.dp)
            .testTag("pill_$tagIndex"),
        contentAlignment = Alignment.Center
    ) {
        if (!isExpanded) {
            // Closed state: Shows centered star/flower icon (non-romantic friendly symbols)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = iconVector,
                    contentDescription = "Tap to open note",
                    tint = iconColor.copy(alpha = 0.85f),
                    modifier = Modifier.size(24.dp)
                )
            }
        } else {
            // Opened state: Shows summary and friendly remorseful note
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = item.summary,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = textColor,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = item.iconEmoji,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(start = 6.dp)
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = item.detailMessage,
                    fontSize = 12.5.sp,
                    lineHeight = 18.sp,
                    color = textColor.copy(alpha = 0.85f)
                )
            }
        }
    }
}
