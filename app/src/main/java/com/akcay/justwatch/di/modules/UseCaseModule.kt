package com.akcay.justwatch.di.modules

import com.akcay.justwatch.domain.usecase.CreateAnonymousUseCase
import com.akcay.justwatch.domain.usecase.GetAllFavoritesUseCase
import com.akcay.justwatch.domain.usecase.GetUserInfoUseCase
import com.akcay.justwatch.domain.usecase.GetUserProfileUseCase
import com.akcay.justwatch.domain.usecase.IsFavoriteUseCase
import com.akcay.justwatch.domain.usecase.RegisterUseCase
import com.akcay.justwatch.domain.usecase.SaveUserInfoUseCase
import com.akcay.justwatch.domain.usecase.SignInUseCase
import com.akcay.justwatch.domain.usecase.SignOutUseCase
import com.akcay.justwatch.domain.usecase.ToggleFavoriteUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { SignInUseCase(get()) }
    single { RegisterUseCase(get()) }
    single { SignOutUseCase(get()) }
    single { CreateAnonymousUseCase(get()) }
    single { GetUserInfoUseCase(get()) }
    single { GetUserProfileUseCase(get()) }
    single { SaveUserInfoUseCase(get()) }
    single { ToggleFavoriteUseCase(get()) }
    single { IsFavoriteUseCase(get()) }
    single { GetAllFavoritesUseCase(get()) }
}
