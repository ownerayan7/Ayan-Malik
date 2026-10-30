package com.example.ui.screens

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.ApologyData

@Composable
fun HeyCutieScreen(
    onContinue: () -> Unit,
    modifier: Modifier = Modifier
) {
    var animateIn by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        animateIn = true
    }
    val cardScale by animateFloatAsState(
        targetValue = if (animateIn) 1f else 0.92f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioLowBouncy),
        label = "card_scale"
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Main floating card
        Card(
            modifier = Modifier
                .width(340.dp)
                .scale(cardScale)
                .testTag("hey_cutie_card"),
            shape = RoundedCornerShape(36.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 14.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Image frame with soft pastel gradient border
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(190.dp)
                        .clip(RoundedCornerShape(26.dp))
                        .background(
                            Brush.linearGradient(
                                listOf(
                                    Color(0xFFFFF8E7),
                                    Color(0xFFFFECB3),
                                    Color(0xFFF3E5F5)
                                )
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.cute_bunny_intro_1793375107097),
                        contentDescription = "Cute bunny with flowers",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Title
                Text(
                    text = "Hey Sayani ✨",
                    fontFamily = FontFamily.Serif,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF38233D)
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Sweet, calming words for Sayani acknowledging friendship
                Text(
                    text = "Can we talk for a moment? There's something really important I want to tell you.\nYou are such a valued friend to me, and I feel really awful for making you feel upset or neglected. Please don't stay mad at me... 🥺",
                    fontSize = 14.sp,
                    lineHeight = 22.sp,
                    color = Color(0xFF67566C),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 6.dp)
                )

                Spacer(modifier = Modifier.height(22.dp))

                // Continue button
                Button(
                    onClick = onContinue,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .testTag("continue_button"),
                    shape = RoundedCornerShape(25.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFE1BEE7)
                    )
                ) {
                    Text(
                        text = "Continue",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF4A148C)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Icon(
                        Icons.Filled.Star,
                        contentDescription = null,
                        tint = Color(0xFF4A148C),
                        modifier = Modifier.padding(start = 2.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Friendly watermark
        Text(
            text = "for my dear friend ${ApologyData.RECIPIENT_NAME} 🌸",
            fontSize = 12.sp,
            color = Color(0xFF9C8A9E),
            fontFamily = FontFamily.Serif
        )
    }
}
