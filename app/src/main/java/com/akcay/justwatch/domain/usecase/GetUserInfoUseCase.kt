package com.akcay.justwatch.domain.usecase

import com.akcay.justwatch.data.remote.model.response.movie.moviemodel.User
import com.akcay.justwatch.domain.repository.AccountRepository
import com.akcay.justwatch.internal.util.NetworkResult
import javax.inject.Inject

class GetUserInfoUseCase @Inject constructor(
  private val accountRepository: AccountRepository
) {
  suspend operator fun invoke(userId: String): NetworkResult<User> {
    return accountRepository.getUserInfo(userId)
  }
}