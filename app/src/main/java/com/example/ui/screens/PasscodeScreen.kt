package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Backspace
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun PasscodeScreen(
    onCorrectPasscode: () -> Unit,
    modifier: Modifier = Modifier
) {
    val correctCode = "1301"
    var enteredPin by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isSuccess by remember { mutableStateOf(false) }
    var showHint by remember { mutableStateOf(false) }

    val shakeOffset = remember { Animatable(0f) }
    val coroutineScope = rememberCoroutineScope()

    fun handleDigit(digit: String) {
        if (isSuccess || enteredPin.length >= 4) return
        errorMessage = null
        val newPin = enteredPin + digit
        enteredPin = newPin

        if (newPin.length == 4) {
            if (newPin == correctCode) {
                isSuccess = true
                coroutineScope.launch {
                    delay(400)
                    onCorrectPasscode()
                }
            } else {
                errorMessage = "Wrong Passcode! Access Denied 🔒"
                coroutineScope.launch {
                    shakeOffset.animateTo(
                        targetValue = 0f,
                        animationSpec = keyframes {
                            durationMillis = 400
                            0f at 0 with FastOutSlowInEasing
                            -24f at 80 with FastOutSlowInEasing
                            24f at 160 with FastOutSlowInEasing
                            -16f at 240 with FastOutSlowInEasing
                            16f at 320 with FastOutSlowInEasing
                            0f at 400 with FastOutSlowInEasing
                        }
                    )
                    delay(300)
                    enteredPin = ""
                }
            }
        }
    }

    fun handleBackspace() {
        if (enteredPin.isNotEmpty() && !isSuccess) {
            enteredPin = enteredPin.dropLast(1)
            errorMessage = null
        }
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
                .offset { IntOffset(shakeOffset.value.roundToInt(), 0) }
                .testTag("passcode_card"),
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
                // Lock icon badge
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(
                            if (isSuccess) Color(0xFFE8F5E9) else Color(0xFFF3E5F5)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (isSuccess) Icons.Filled.LockOpen else Icons.Filled.Lock,
                        contentDescription = "Lock status",
                        tint = if (isSuccess) Color(0xFF2E7D32) else Color(0xFF7B1FA2),
                        modifier = Modifier.size(32.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Secret Message 🔒",
                    fontFamily = FontFamily.Serif,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF38233D)
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Sayani, please enter the 4-digit secret passcode to unlock your message",
                    fontSize = 13.sp,
                    lineHeight = 18.sp,
                    color = Color(0xFF7A687D),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(24.dp))

                // PIN indicator circles
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    for (i in 0 until 4) {
                        val isFilled = i < enteredPin.length
                        val dotColor = when {
                            isSuccess -> Color(0xFF4CAF50)
                            errorMessage != null -> Color(0xFFE53935)
                            isFilled -> Color(0xFF7B1FA2)
                            else -> Color(0xFFE0E0E0)
                        }

                        Box(
                            modifier = Modifier
                                .size(20.dp)
                                .clip(CircleShape)
                                .background(dotColor)
                                .border(2.dp, dotColor.copy(alpha = 0.5f), CircleShape)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Error or success message
                Box(modifier = Modifier.height(24.dp), contentAlignment = Alignment.Center) {
                    if (isSuccess) {
                        Text(
                            text = "Passcode Accepted! Unlocking... ✨",
                            color = Color(0xFF2E7D32),
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                    } else if (errorMessage != null) {
                        Text(
                            text = errorMessage ?: "",
                            color = Color(0xFFD32F2F),
                            fontSize = 12.5.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Numeric Keypad
                val keypad = listOf(
                    listOf("1", "2", "3"),
                    listOf("4", "5", "6"),
                    listOf("7", "8", "9"),
                    listOf("C", "0", "⌫")
                )

                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    keypad.forEach { row ->
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            row.forEach { key ->
                                when (key) {
                                    "C" -> {
                                        Box(
                                            modifier = Modifier
                                                .size(56.dp)
                                                .clip(CircleShape)
                                                .background(Color(0xFFFFF0F5))
                                                .clickable {
                                                    if (!isSuccess) {
                                                        enteredPin = ""
                                                        errorMessage = null
                                                    }
                                                }
                                                .testTag("key_clear"),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                text = "C",
                                                fontSize = 17.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = Color(0xFFC2185B)
                                            )
                                        }
                                    }
                                    "⌫" -> {
                                        Box(
                                            modifier = Modifier
                                                .size(56.dp)
                                                .clip(CircleShape)
                                                .background(Color(0xFFF3E5F5))
                                                .clickable { handleBackspace() }
                                                .testTag("key_backspace"),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Icon(
                                                imageVector = Icons.AutoMirrored.Filled.Backspace,
                                                contentDescription = "Backspace",
                                                tint = Color(0xFF6A1B9A),
                                                modifier = Modifier.size(20.dp)
                                            )
                                        }
                                    }
                                    else -> {
                                        Box(
                                            modifier = Modifier
                                                .size(56.dp)
                                                .clip(CircleShape)
                                                .background(Color(0xFFFAF5FC))
                                                .border(1.dp, Color(0xFFE1BEE7), CircleShape)
                                                .clickable { handleDigit(key) }
                                                .testTag("key_$key"),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                text = key,
                                                fontSize = 21.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = Color(0xFF38233D)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Passcode Hint
                TextButton(
                    onClick = { showHint = !showHint },
                    modifier = Modifier.testTag("hint_button")
                ) {
                    Text(
                        text = if (showHint) "Hint: 1301 🤫" else "Need a hint? 💡",
                        fontSize = 12.5.sp,
                        color = Color(0xFF7B1FA2),
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}
