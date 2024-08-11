package com.akcay.justwatch.screens.splash

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
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
}