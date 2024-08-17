package com.akcay.justwatch.screens.splash

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import androidx.lifecycle.ViewModel
import com.akcay.justwatch.component.JWDialogBox
import com.akcay.justwatch.component.JWDialogBoxModel
import com.akcay.justwatch.security.JWSecurityUtil
import com.akcay.justwatch.ui.theme.Red
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor() : ViewModel() {

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun setLoadingStatus(loading: Boolean) {
        _isLoading.value = loading
    }

    fun checkDeviceIsRooted(): Boolean = JWSecurityUtil.isDeviceRooted()

    @Composable
    fun ErrorDialog(
        clickAction: () -> Unit
    ) {
        JWDialogBox(
            showDialog = true,
            onDismissRequest = {},
            content = JWDialogBoxModel(
                mainColor = Red,
                title = "Hata!",
                description = "Rootlu cihaz ile giriş yapıyorsunuz!",
                positiveButtonText = "Çık!",
                negativeButtonText = null
            ),
            positiveButtonClickAction = {
                clickAction.invoke()
            }
        )
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @Composable
    fun ShowNotificationPermission(onPermissionGranted: () -> Unit) {
        val context = LocalContext.current

        val launcher =
            rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) { isGranted ->
                onPermissionGranted.invoke()
            }

        LaunchedEffect(key1 = Unit) {
            if (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PermissionChecker.PERMISSION_GRANTED
            ) {
                // Permission is already granted, proceed
                onPermissionGranted.invoke()
            } else {
                // Launch the native permission dialog
                launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }
}