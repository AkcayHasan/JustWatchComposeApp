package com.akcay.justwatch.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akcay.justwatch.domain.repository.AccountRepository
import com.akcay.justwatch.internal.navigation.AppDestination
import com.akcay.justwatch.internal.navigation.MainDestination
import com.akcay.justwatch.internal.util.DataStoreManager
import com.akcay.justwatch.internal.util.JWSecurityUtil
import com.akcay.justwatch.internal.util.ThemeManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
class SplashScreenViewModel(
    private val storeManager: DataStoreManager,
    private val themeManager: ThemeManager,
    private val accountRepository: AccountRepository
) : ViewModel() {
    private val _isRooted = MutableStateFlow(false)
    val isRooted = _isRooted.asStateFlow()

    val navigationDestination = combine(
        storeManager.shouldOnBoardingVisible(),
        storeManager.getDarkThemeEnabled(),
        accountRepository.currentAuthUser
    ) { onBoardingVisibility, darkThemEnabled, authUser ->
        themeManager.toggle(darkThemEnabled)
        when {
            onBoardingVisibility -> AppDestination.OnBoarding
            authUser != null && (authUser.isAnonymous == false) && !authUser.email.isNullOrEmpty() -> AppDestination.Main
            else -> AppDestination.Login
        }
    }.onStart {
        _isRooted.value = JWSecurityUtil.isDeviceRooted()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = null
    )
}
