package com.akcay.justwatch.di.modules

import androidx.lifecycle.SavedStateHandle
import com.akcay.justwatch.JustWatchViewModel
import com.akcay.justwatch.screens.account.AccountViewModel
import com.akcay.justwatch.screens.detail.MovieDetailViewModel
import com.akcay.justwatch.screens.favourite.FavouriteViewModel
import com.akcay.justwatch.screens.forgotpassword.ForgotPasswordViewModel
import com.akcay.justwatch.screens.login.LoginViewModel
import com.akcay.justwatch.screens.movies.ui.MoviesViewModel
import com.akcay.justwatch.screens.onboarding.OnBoardingViewModel
import com.akcay.justwatch.screens.register.RegisterViewModel
import com.akcay.justwatch.screens.splash.SplashScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { 
        JustWatchViewModel(
            storeManager = get()
        )
    }
    viewModel { 
        SplashScreenViewModel(
            storeManager = get(),
            themeManager = get(),
            accountRepository = get()
        )
    }
    viewModel { 
        LoginViewModel(
            logRepository = get(),
            dataStoreManager = get(),
            signInUseCase = get(),
            registerUseCase = get()
        )
    }
    viewModel { 
        RegisterViewModel(
            accountRepository = get(),
            dataStoreManager = get(),
            registerUseCase = get()
        )
    }
    viewModel { 
        MoviesViewModel(
            logRepository = get(),
            movieRepository = get()
        )
    }
    viewModel { 
        FavouriteViewModel(
            accountRepository = get(),
            getAllFavoritesUseCase = get(),
            toggleFavoriteUseCase = get()
        )
    }
    viewModel { (handle: SavedStateHandle) -> 
        MovieDetailViewModel(
            savedStateHandle = handle,
            repo = get(),
            accountRepository = get(),
            isFavoriteUseCase = get(),
            toggleFavoriteUseCase = get()
        )
    }
    viewModel { 
        AccountViewModel(
            dataStoreManager = get(),
            themeManager = get(),
            accountRepository = get(),
            getUserProfileUseCase = get()
        )
    }
    viewModel { ForgotPasswordViewModel() }
    viewModel { 
        OnBoardingViewModel(
            dataStoreManager = get()
        )
    }
}
