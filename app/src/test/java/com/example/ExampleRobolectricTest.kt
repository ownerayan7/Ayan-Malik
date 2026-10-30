package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.model.ApologyData
import com.example.ui.components.QrCodeEncoder
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class ExampleRobolectricTest {

    @Test
    fun `read string from context`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("I'm Sorry", appName)
    }

    @Test
    fun `verify apology data content`() {
        assertEquals("Sayani", ApologyData.RECIPIENT_NAME)
        assertEquals("1301", ApologyData.SECRET_PASSCODE)
        assertEquals(3, ApologyData.littleThings.size)
        assertTrue(ApologyData.HEARTFELT_LETTER.contains("Sayani"))
    }

    @Test
    fun `verify qr code matrix generator`() {
        val matrix = QrCodeEncoder().generateMatrix(ApologyData.WEB_URL)
        assertNotNull(matrix)
        assertTrue(matrix.isNotEmpty())
        assertTrue(matrix[0].isNotEmpty())
    }
}
