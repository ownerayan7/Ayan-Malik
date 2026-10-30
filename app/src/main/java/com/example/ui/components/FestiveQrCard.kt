package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FestiveQrCard(
    contentUrl: String,
    modifier: Modifier = Modifier
) {
    val matrix = remember(contentUrl) {
        try {
            QrCodeEncoder().generateMatrix(contentUrl)
        } catch (e: Exception) {
            QrCodeEncoder().generateMatrix("https://ais-pre-kxxmipas342lfzri4osaev-570580243731.asia-southeast1.run.app")
        }
    }

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(top = 22.dp, bottom = 14.dp, start = 20.dp, end = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // QR Canvas with red/burgundy rounded modules
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .background(Color(0xFFFFF9FA), RoundedCornerShape(16.dp))
                    .padding(10.dp),
                contentAlignment = Alignment.Center
            ) {
                Canvas(modifier = Modifier.size(180.dp)) {
                    val count = matrix.size
                    val cellSize = size.width / count
                    val primaryColor = Color(0xFFD32F2F) // Festive bright red
                    val darkColor = Color(0xFFB71C1C)

                    for (r in 0 until count) {
                        for (c in 0 until count) {
                            if (matrix[r][c]) {
                                // Draw rounded cell
                                val isCorner = (r < 7 && c < 7) || (r < 7 && c >= count - 7) || (r >= count - 7 && c < 7)
                                val cellColor = if (isCorner) darkColor else primaryColor

                                drawRoundRect(
                                    color = cellColor,
                                    topLeft = Offset(c * cellSize + 0.5f, r * cellSize + 0.5f),
                                    size = Size(cellSize - 1f, cellSize - 1f),
                                    cornerRadius = CornerRadius(cellSize * 0.35f, cellSize * 0.35f)
                                )
                            }
                        }
                    }

                    // Little golden star in the very center
                    val centerPx = size.width / 2f
                    val starRadius = cellSize * 2.2f
                    drawCircle(
                        color = Color.White,
                        radius = starRadius + 2f,
                        center = Offset(centerPx, centerPx)
                    )
                    drawCircle(
                        color = Color(0xFFFFB300),
                        radius = starRadius,
                        center = Offset(centerPx, centerPx)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // SCAN ME Festive Ribbon Banner
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                // Festive Leaves canvas
                Canvas(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(36.dp)
                ) {
                    val leafGreen = Color(0xFF2E7D32)
                    val berryRed = Color(0xFFD32F2F)

                    // Left leaves
                    drawCircle(color = leafGreen, radius = 5.dp.toPx(), center = Offset(size.width * 0.16f, size.height * 0.65f))
                    drawCircle(color = leafGreen, radius = 6.dp.toPx(), center = Offset(size.width * 0.20f, size.height * 0.72f))
                    drawCircle(color = berryRed, radius = 3.5f.dp.toPx(), center = Offset(size.width * 0.18f, size.height * 0.60f))

                    // Right leaves
                    drawCircle(color = leafGreen, radius = 5.dp.toPx(), center = Offset(size.width * 0.84f, size.height * 0.65f))
                    drawCircle(color = leafGreen, radius = 6.dp.toPx(), center = Offset(size.width * 0.80f, size.height * 0.72f))
                    drawCircle(color = berryRed, radius = 3.5f.dp.toPx(), center = Offset(size.width * 0.82f, size.height * 0.60f))
                }

                // Ribbon badge
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color(0xFFE53935))
                        .padding(horizontal = 24.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "SCAN ME",
                        color = Color.White,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 1.8.sp,
                        fontFamily = FontFamily.Default
                    )
                }
            }
        }
    }
}
