package com.akcay.justwatch.domain.usecase

import com.akcay.justwatch.data.remote.model.response.movie.moviemodel.AuthUser
import com.akcay.justwatch.domain.repository.AccountRepository
import com.akcay.justwatch.internal.util.NetworkResult

class SignInUseCase(
    private val accountRepository: AccountRepository,
) {

    suspend operator fun invoke(email: String, password: String): NetworkResult<AuthUser> {
        return accountRepository.signIn(email, password)
    }
}
