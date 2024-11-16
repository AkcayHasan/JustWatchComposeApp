package com.akcay.justwatch.domain.usecase

import com.akcay.justwatch.data.remote.model.response.movie.moviemodel.AuthUser
import com.akcay.justwatch.domain.repository.AccountRepository
import com.akcay.justwatch.internal.util.NetworkResult
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
  private val accountRepository: AccountRepository
) {

  suspend operator fun invoke(email: String, password: String): NetworkResult<AuthUser> {
    return accountRepository.register(email, password)
  }
}