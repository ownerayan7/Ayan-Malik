package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.ApologyData
import com.example.ui.components.FestiveQrCard

@Composable
fun QrInviteScreen(
    onOpenApologyStory: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var isGiftUnwrapped by remember { mutableStateOf(false) }
    val scaleAnim by animateFloatAsState(
        targetValue = if (isGiftUnwrapped) 1.0f else 0.98f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "gift_scale"
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Chat-style bubble header (matches video intro at 00:01)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            contentAlignment = Alignment.CenterEnd
        ) {
            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp, bottomStart = 20.dp, bottomEnd = 4.dp))
                        .background(
                            Brush.horizontalGradient(
                                listOf(Color(0xFF8E24AA), Color(0xFF5E35B1))
                            )
                        )
                        .padding(horizontal = 16.dp, vertical = 10.dp)
                ) {
                    Text(
                        text = "I know I messed up... so I made this 🥺",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF261028)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "A", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

        // Tap gift to unwrap or view QR
        AnimatedVisibility(visible = !isGiftUnwrapped) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(Color.White)
                    .clickable { isGiftUnwrapped = true }
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "🎁", fontSize = 52.sp)
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Tap to open Sayani's Card",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF6A1B9A)
                    )
                    Text(
                        text = "Contains a scannable QR card & apology note",
                        fontSize = 12.sp,
                        color = Color(0xFF7A687D)
                    )
                }
            }
        }

        // The Scannable Festive QR Card (matches 00:02 in video)
        Box(
            modifier = Modifier
                .scale(scaleAnim)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    onOpenApologyStory()
                }
        ) {
            FestiveQrCard(
                contentUrl = ApologyData.WEB_URL,
                modifier = Modifier
                    .width(280.dp)
                    .testTag("festive_qr_card")
            )
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Explanatory prompt for Sayani
        Text(
            text = "Sayani, scan the QR code with your camera or tap below to open your message! 🔒\n(Secret Passcode: 1301 required to open)",
            color = Color(0xFF4A344D),
            fontSize = 13.sp,
            textAlign = TextAlign.Center,
            lineHeight = 19.sp,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Main CTA button to enter the interactive story
        Button(
            onClick = onOpenApologyStory,
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp)
                .shadow(10.dp, RoundedCornerShape(27.dp), spotColor = Color(0xFFAB47BC))
                .testTag("scan_qr_button"),
            shape = RoundedCornerShape(27.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF8E24AA)
            )
        ) {
            Icon(Icons.Filled.MailOutline, contentDescription = null, tint = Color.White)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Open Message For Sayani ✨",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Secondary actions: Share & Copy
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            OutlinedButton(
                onClick = {
                    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    clipboard.setPrimaryClip(ClipData.newPlainText("Apology Link", ApologyData.WEB_URL))
                    Toast.makeText(context, "Link copied! Share it with Sayani ✨", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier
                    .weight(1f)
                    .height(46.dp),
                shape = RoundedCornerShape(23.dp)
            ) {
                Icon(Icons.Default.ContentCopy, contentDescription = null, modifier = Modifier.size(16.dp), tint = Color(0xFF6A1B9A))
                Spacer(modifier = Modifier.width(6.dp))
                Text(text = "Copy Link", fontSize = 13.sp, color = Color(0xFF6A1B9A))
            }

            OutlinedButton(
                onClick = {
                    val sendIntent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, "Sayani, please open this special message: ${ApologyData.WEB_URL} ✨")
                        type = "text/plain"
                    }
                    val shareIntent = Intent.createChooser(sendIntent, "Send to Sayani")
                    context.startActivity(shareIntent)
                },
                modifier = Modifier
                    .weight(1f)
                    .height(46.dp),
                shape = RoundedCornerShape(23.dp)
            ) {
                Icon(Icons.Default.Share, contentDescription = null, modifier = Modifier.size(16.dp), tint = Color(0xFF6A1B9A))
                Spacer(modifier = Modifier.width(6.dp))
                Text(text = "Share", fontSize = 13.sp, color = Color(0xFF6A1B9A))
            }
        }
    }
}
