package com.akcay.justwatch.internal.ext

import android.util.Patterns
import androidx.compose.ui.text.AnnotatedString
import java.util.regex.Pattern

private const val MIN_PASS_LENGTH = 6
private const val PASS_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{4,}$"

fun String.isValidEmail(): Boolean {
    return this.isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun String.isValidPassword(): Boolean {
    return this.isNotBlank() &&
            this.length >= MIN_PASS_LENGTH &&
            Pattern.compile(PASS_PATTERN).matcher(this).matches()
}

fun String.passwordMatches(repeated: String): Boolean {
    return this == repeated
}

fun AnnotatedString.mutate(mutator: AnnotatedString.Builder.(AnnotatedString) -> Unit): AnnotatedString {
    val builder = AnnotatedString.Builder(this)
    builder.mutator(this)
    return builder.toAnnotatedString()
}
