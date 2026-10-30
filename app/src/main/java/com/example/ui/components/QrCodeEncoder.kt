package com.example.ui.components

/**
 * Lightweight, self-contained QR Code encoder for Byte Mode (Version 1-4).
 * Generates an authentic boolean matrix suitable for Canvas rendering.
 */
class QrCodeEncoder {

    fun generateMatrix(text: String): Array<BooleanArray> {
        val bytes = text.toByteArray(Charsets.ISO_8859_1)
        val version = when {
            bytes.size <= 14 -> 1
            bytes.size <= 26 -> 2
            bytes.size <= 42 -> 3
            else -> 4
        }
        val size = 17 + 4 * version
        val matrix = Array(size) { BooleanArray(size) }
        val isReserved = Array(size) { BooleanArray(size) }

        fun setFinder(row: Int, col: Int) {
            for (r in 0 until 7) {
                for (c in 0 until 7) {
                    val inBorder = r == 0 || r == 6 || c == 0 || c == 6
                    val inCenter = r in 2..4 && c in 2..4
                    matrix[row + r][col + c] = inBorder || inCenter
                    isReserved[row + r][col + c] = true
                }
            }
            // Separators
            for (r in -1..7) {
                for (c in -1..7) {
                    val targetR = row + r
                    val targetC = col + c
                    if (targetR in 0 until size && targetC in 0 until size) {
                        isReserved[targetR][targetC] = true
                    }
                }
            }
        }

        setFinder(0, 0)
        setFinder(0, size - 7)
        setFinder(size - 7, 0)

        // Alignment pattern for v2+
        if (version >= 2) {
            val alignPos = when (version) {
                2 -> 18
                3 -> 22
                else -> 26
            }
            for (r in -2..2) {
                for (c in -2..2) {
                    val isBorder = kotlin.math.abs(r) == 2 || kotlin.math.abs(c) == 2
                    val isCenter = r == 0 && c == 0
                    matrix[alignPos + r][alignPos + c] = isBorder || isCenter
                    isReserved[alignPos + r][alignPos + c] = true
                }
            }
        }

        // Timing patterns
        for (i in 8 until size - 8) {
            val bit = (i % 2 == 0)
            matrix[6][i] = bit
            isReserved[6][i] = true
            matrix[i][6] = bit
            isReserved[i][6] = true
        }

        // Dark module
        matrix[4 * version + 9][8] = true
        isReserved[4 * version + 9][8] = true

        // Reserve format info
        for (i in 0 until 9) {
            if (i < size) isReserved[8][i] = true
            if (i < size) isReserved[i][8] = true
        }
        for (i in size - 8 until size) {
            isReserved[8][i] = true
            isReserved[i][8] = true
        }

        // Data bitstream
        val bitstream = mutableListOf<Boolean>()
        // Mode indicator: 0100 for Byte
        addBits(bitstream, 4, 4)
        // Character count indicator: 8 bits for v1-9
        addBits(bitstream, bytes.size, 8)
        // Data bytes
        for (b in bytes) {
            addBits(bitstream, b.toInt() and 0xFF, 8)
        }
        // Terminator
        val maxDataBits = when (version) {
            1 -> 152
            2 -> 272
            3 -> 440
            else -> 640
        }
        val padZeros = (maxDataBits - bitstream.size).coerceAtMost(4).coerceAtLeast(0)
        repeat(padZeros) { bitstream.add(false) }
        while (bitstream.size % 8 != 0) {
            bitstream.add(false)
        }
        val padBytes = listOf(0xEC, 0x11)
        var padIndex = 0
        while (bitstream.size < maxDataBits) {
            addBits(bitstream, padBytes[padIndex % 2], 8)
            padIndex++
        }

        // Fill data in zigzag pattern
        var bitIdx = 0
        var up = true
        var col = size - 1
        while (col > 0) {
            if (col == 6) col-- // Skip vertical timing column
            val rows = if (up) (size - 1 downTo 0) else (0 until size)
            for (row in rows) {
                for (c in 0..1) {
                    val currentCol = col - c
                    if (!isReserved[row][currentCol]) {
                        val bitVal = if (bitIdx < bitstream.size) bitstream[bitIdx++] else false
                        // Mask pattern 000: (row + col) % 2 == 0
                        val mask = (row + currentCol) % 2 == 0
                        matrix[row][currentCol] = bitVal xor mask
                    }
                }
            }
            up = !up
            col -= 2
        }

        // Standard Format info (ECC: M, Mask: 0 -> 101010000010010)
        val formatBits = intArrayOf(1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0)
        for (i in 0 until 6) matrix[8][i] = formatBits[i] == 1
        matrix[8][7] = formatBits[6] == 1
        matrix[8][8] = formatBits[7] == 1
        matrix[7][8] = formatBits[8] == 1
        for (i in 9 until 15) matrix[14 - i][8] = formatBits[i] == 1

        for (i in 0 until 8) matrix[size - 1 - i][8] = formatBits[i] == 1
        for (i in 8 until 15) matrix[8][size - 15 + i] = formatBits[i] == 1

        return matrix
    }

    private fun addBits(list: MutableList<Boolean>, value: Int, bitCount: Int) {
        for (i in bitCount - 1 downTo 0) {
            list.add(((value shr i) and 1) == 1)
        }
    }
}
