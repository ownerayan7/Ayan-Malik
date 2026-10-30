package com.example.ui

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.ApologyData
import com.example.model.ApologyScreenState
import com.example.ui.components.FloatingHeartsBackground
import com.example.ui.screens.FromMyHeartScreen
import com.example.ui.screens.HeyCutieScreen
import com.example.ui.screens.HurtScreen
import com.example.ui.screens.LittleThingsScreen
import com.example.ui.screens.PasscodeScreen
import com.example.ui.screens.QrInviteScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApologyApp() {
    var currentScreen by remember { mutableStateOf(ApologyScreenState.QR_INVITE) }

    // Android predictive/system BackHandler
    BackHandler(enabled = currentScreen != ApologyScreenState.QR_INVITE) {
        currentScreen = when (currentScreen) {
            ApologyScreenState.FROM_MY_HEART -> ApologyScreenState.LITTLE_THINGS
            ApologyScreenState.LITTLE_THINGS -> ApologyScreenState.I_HURT_YOU
            ApologyScreenState.I_HURT_YOU -> ApologyScreenState.HEY_CUTIE
            ApologyScreenState.HEY_CUTIE -> ApologyScreenState.PASSCODE_LOCK
            ApologyScreenState.PASSCODE_LOCK -> ApologyScreenState.QR_INVITE
            ApologyScreenState.QR_INVITE -> ApologyScreenState.QR_INVITE
        }
    }

    FloatingHeartsBackground {
        Scaffold(
            containerColor = Color.Transparent,
            contentWindowInsets = WindowInsets.safeDrawing,
            topBar = {
                // Gentle subtle header
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .statusBarsPadding()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    if (currentScreen != ApologyScreenState.QR_INVITE) {
                        IconButton(
                            onClick = {
                                currentScreen = when (currentScreen) {
                                    ApologyScreenState.FROM_MY_HEART -> ApologyScreenState.LITTLE_THINGS
                                    ApologyScreenState.LITTLE_THINGS -> ApologyScreenState.I_HURT_YOU
                                    ApologyScreenState.I_HURT_YOU -> ApologyScreenState.HEY_CUTIE
                                    ApologyScreenState.HEY_CUTIE -> ApologyScreenState.PASSCODE_LOCK
                                    else -> ApologyScreenState.QR_INVITE
                                }
                            },
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(Color.White.copy(alpha = 0.8f))
                                .testTag("top_back_button")
                        ) {
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = Color(0xFF6B2D46)
                            )
                        }
                    } else {
                        Spacer(modifier = Modifier.size(40.dp))
                    }

                    // Mode Pill indicator
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color.White.copy(alpha = 0.85f))
                            .padding(horizontal = 14.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = when (currentScreen) {
                                ApologyScreenState.QR_INVITE -> "💌 QR Invite"
                                ApologyScreenState.PASSCODE_LOCK -> "🔒 Secret Code"
                                ApologyScreenState.HEY_CUTIE -> "Step 1/4 • Hey Sayani"
                                ApologyScreenState.I_HURT_YOU -> "Step 2/4 • I Hurt You"
                                ApologyScreenState.LITTLE_THINGS -> "Step 3/4 • Little Things"
                                ApologyScreenState.FROM_MY_HEART -> "Step 4/4 • My Message"
                            },
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF6B2D46)
                        )
                    }

                    // Toggle button between QR and Story/Lock
                    IconButton(
                        onClick = {
                            currentScreen = if (currentScreen == ApologyScreenState.QR_INVITE) {
                                ApologyScreenState.PASSCODE_LOCK
                            } else {
                                ApologyScreenState.QR_INVITE
                            }
                        },
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.8f))
                            .testTag("toggle_qr_button")
                    ) {
                        Icon(
                            if (currentScreen == ApologyScreenState.QR_INVITE) Icons.Filled.Star else Icons.Filled.QrCode,
                            contentDescription = "Toggle QR / Passcode",
                            tint = Color(0xFF6B2D46)
                        )
                    }
                }
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                AnimatedContent(
                    targetState = currentScreen,
                    transitionSpec = {
                        if (targetState.ordinal > initialState.ordinal) {
                            (slideInHorizontally(
                                animationSpec = tween(380),
                                initialOffsetX = { width -> width }
                            ) + fadeIn(animationSpec = tween(380))).togetherWith(
                                slideOutHorizontally(
                                    animationSpec = tween(380),
                                    targetOffsetX = { width -> -width }
                                ) + fadeOut(animationSpec = tween(380))
                            )
                        } else {
                            (slideInHorizontally(
                                animationSpec = tween(380),
                                initialOffsetX = { width -> -width }
                            ) + fadeIn(animationSpec = tween(380))).togetherWith(
                                slideOutHorizontally(
                                    animationSpec = tween(380),
                                    targetOffsetX = { width -> width }
                                ) + fadeOut(animationSpec = tween(380))
                            )
                        }
                    },
                    label = "screen_transition"
                ) { screen ->
                    when (screen) {
                        ApologyScreenState.QR_INVITE -> {
                            QrInviteScreen(
                                onOpenApologyStory = {
                                    currentScreen = ApologyScreenState.PASSCODE_LOCK
                                }
                            )
                        }

                        ApologyScreenState.PASSCODE_LOCK -> {
                            PasscodeScreen(
                                onCorrectPasscode = {
                                    currentScreen = ApologyScreenState.HEY_CUTIE
                                }
                            )
                        }

                        ApologyScreenState.HEY_CUTIE -> {
                            HeyCutieScreen(
                                onContinue = {
                                    currentScreen = ApologyScreenState.I_HURT_YOU
                                }
                            )
                        }

                        ApologyScreenState.I_HURT_YOU -> {
                            HurtScreen(
                                onNext = {
                                    currentScreen = ApologyScreenState.LITTLE_THINGS
                                }
                            )
                        }

                        ApologyScreenState.LITTLE_THINGS -> {
                            LittleThingsScreen(
                                onNavigateToMessage = {
                                    currentScreen = ApologyScreenState.FROM_MY_HEART
                                }
                            )
                        }

                        ApologyScreenState.FROM_MY_HEART -> {
                            FromMyHeartScreen(
                                onRestart = {
                                    currentScreen = ApologyScreenState.PASSCODE_LOCK
                                },
                                onShowQr = {
                                    currentScreen = ApologyScreenState.QR_INVITE
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
