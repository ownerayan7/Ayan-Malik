package com.example.model

data class LittleThing(
    val id: Int,
    val iconEmoji: String,
    val summary: String,
    val detailMessage: String,
    val gradientType: GradientType
)

enum class GradientType {
    PEACH,
    PINK,
    LAVENDER
}

enum class ApologyScreenState {
    QR_INVITE,
    PASSCODE_LOCK,
    HEY_CUTIE,
    I_HURT_YOU,
    LITTLE_THINGS,
    FROM_MY_HEART
}

object ApologyData {
    const val SECRET_PASSCODE = "1301"
    const val RECIPIENT_NAME = "Sayani"
    const val WEB_URL = "https://ais-pre-kxxmipas342lfzri4osaev-570580243731.asia-southeast1.run.app"

    val littleThings = listOf(
        LittleThing(
            id = 1,
            iconEmoji = "⭐",
            summary = "Ignoring your message was a big mistake.",
            detailMessage = "I messed up and I am really sorry for that. Seeing your message and leaving it on read was completely thoughtless of me. As your friend, I should have replied right away instead of making you feel ignored.",
            gradientType = GradientType.PEACH
        ),
        LittleThing(
            id = 2,
            iconEmoji = "🌼",
            summary = "Your friendship means so much to me.",
            detailMessage = "You are such an important person in my life, and hurting your feelings is the absolute last thing I ever wanted to do. I truly value our connection and promise to be a much better friend to you.",
            gradientType = GradientType.PINK
        ),
        LittleThing(
            id = 3,
            iconEmoji = "✨",
            summary = "Please forgive me...",
            detailMessage = "I promise I will never leave your chat on read again. Please don't stay mad or distant. I really miss talking to you, Sayani... Can we please be good friends again? 🥺",
            gradientType = GradientType.LAVENDER
        )
    )

    const val HEARTFELT_LETTER = """Dear Sayani,

For hurting your feelings and making you feel ignored, I am truly and deeply sorry.

I never wanted to be the reason behind your sadness, your silence, or your disappointment. Seeing your message and failing to reply right away was completely thoughtless and wrong on my part.

You matter to me far more than I always manage to express, and our friendship is something I treasure deeply. Knowing that I made you feel left out breaks my heart, and I hate that I let you down.

I promise to listen more closely, to always prioritize your messages, and to be more attentive. Thank you for being such an amazing person and for all the memories we share.

I am truly trying my best to make amends, and I hope with all my heart that you can forgive me this time.

Please don't stay distant or upset with me. I really value having you in my life, Sayani.

Warmly,
From someone who values you deeply ✨"""
}
