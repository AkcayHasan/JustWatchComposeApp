package com.akcay.justwatch.internal.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun JWAuthRequiredDialog(
    onDismiss: () -> Unit,
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit,
    title: String = "Giriş Gerekli",
    message: String = "Bu özelliği kullanmak için giriş yapmanız veya kayıt olmanız gerekiyor.",
    icon: ImageVector = Icons.Default.Person
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = title)
        },
        text = {
            Text(text = message)
        },
        icon = {
            Icon(
                imageVector = icon,
                contentDescription = null
            )
        },
        confirmButton = {
            TextButton(onClick = onLoginClick) {
                Text("Giriş Yap")
            }
        },
        dismissButton = {
            TextButton(onClick = onRegisterClick) {
                Text("Kayıt Ol")
            }
        }
    )
}
