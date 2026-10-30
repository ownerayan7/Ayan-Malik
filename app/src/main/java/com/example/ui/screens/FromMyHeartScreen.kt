package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.Handshake
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
fun FromMyHeartScreen(
    onRestart: () -> Unit,
    onShowQr: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showForgiveDialog by remember { mutableStateOf(false) }
    var showHugDialog by remember { mutableStateOf(false) }
    var isForgiven by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 22.dp, vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card(
            modifier = Modifier
                .width(340.dp)
                .testTag("from_my_heart_card"),
            shape = RoundedCornerShape(36.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 14.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Header
                Text(
                    text = "From my heart...",
                    fontFamily = FontFamily.Serif,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF38233D)
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Best friend kittens illustration with yellow daisy flower
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                        .clip(RoundedCornerShape(24.dp))
                        .background(
                            Brush.linearGradient(
                                listOf(Color(0xFFFFF8E7), Color(0xFFFFECB3), Color(0xFFE8EAF6))
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.best_friend_kittens_1793376529637),
                        contentDescription = "Best friend kittens with flower",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "↓ Tap & scroll to read message ↓",
                    fontSize = 11.5.sp,
                    color = Color(0xFF9E8A9E),
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Scrollable letter box with soft warm border
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(190.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color(0xFFFFFDF9))
                        .border(1.dp, Color(0xFFFFE0B2), RoundedCornerShape(20.dp))
                        .padding(14.dp)
                        .verticalScroll(rememberScrollState())
                        .testTag("apology_letter_scroll")
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "🌼 I'm Sincere & Sorry 🌼",
                            fontFamily = FontFamily.Serif,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFE65100)
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = ApologyData.HEARTFELT_LETTER,
                            fontSize = 13.sp,
                            lineHeight = 20.sp,
                            color = Color(0xFF554458),
                            textAlign = TextAlign.Start
                        )
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                // Actions for Sayani
                if (!isForgiven) {
                    Button(
                        onClick = {
                            isForgiven = true
                            showForgiveDialog = true
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("forgive_button"),
                        shape = RoundedCornerShape(24.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF7B1FA2)
                        )
                    ) {
                        Icon(Icons.Filled.Handshake, contentDescription = null, tint = Color.White)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Friends Again? 🤝",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(20.dp))
                            .background(Color(0xFFE8F5E9))
                            .padding(vertical = 10.dp, horizontal = 14.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Thank you Sayani! Friends always 😊",
                            color = Color(0xFF2E7D32),
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 13.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = { showHugDialog = true },
                        modifier = Modifier
                            .weight(1f)
                            .height(44.dp)
                            .testTag("hug_button"),
                        shape = RoundedCornerShape(22.dp)
                    ) {
                        Text(text = "Send Hug 🤗", fontSize = 12.sp, color = Color(0xFF5E35B1))
                    }

                    OutlinedButton(
                        onClick = onRestart,
                        modifier = Modifier
                            .weight(1f)
                            .height(44.dp)
                            .testTag("restart_button"),
                        shape = RoundedCornerShape(22.dp)
                    ) {
                        Icon(Icons.Filled.Refresh, contentDescription = null, modifier = Modifier.size(14.dp), tint = Color(0xFF5E35B1))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "↺ Restart", fontSize = 12.sp, color = Color(0xFF5E35B1))
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                TextButton(
                    onClick = onShowQr,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "📲 View QR Code Card",
                        fontSize = 12.5.sp,
                        color = Color(0xFF7B1FA2)
                    )
                }
            }
        }
    }

    // Forgiveness celebration dialog
    if (showForgiveDialog) {
        AlertDialog(
            onDismissRequest = { showForgiveDialog = false },
            title = {
                Text(
                    text = "Thank You, Sayani! 😊",
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF6A1B9A),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            text = {
                Text(
                    text = "Your friendship means the absolute world! I promise to always listen and never leave your chat on read again. I'm so glad we can talk and smile again! ✨🌼",
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp,
                    lineHeight = 21.sp,
                    color = Color(0xFF4A344D)
                )
            },
            confirmButton = {
                Button(
                    onClick = { showForgiveDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7B1FA2)),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text("Always Friends 😊")
                }
            }
        )
    }

    // Virtual hug dialog
    if (showHugDialog) {
        AlertDialog(
            onDismissRequest = { showHugDialog = false },
            title = {
                Text(
                    text = "Friendly Hug Sent! 🫂",
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF7B1FA2),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            text = {
                Text(
                    text = "Sending a warm, comforting hug to you! Thank you for understanding and for being such a wonderful person. 🌼",
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp,
                    lineHeight = 21.sp,
                    color = Color(0xFF4A344D)
                )
            },
            confirmButton = {
                Button(
                    onClick = { showHugDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFAB47BC)),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text("Thanks! 😊")
                }
            }
        )
    }
}
