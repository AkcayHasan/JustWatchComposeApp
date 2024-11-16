package com.akcay.justwatch.domain.usecase

import com.akcay.justwatch.domain.repository.AccountRepository
import com.akcay.justwatch.internal.util.NetworkResult
import javax.inject.Inject

class SaveUserInfoUseCase @Inject constructor(
  private val accountRepository: AccountRepository
) {

  suspend operator fun invoke(userId: String, firstName: String, lastName: String): NetworkResult<Boolean> {
    return accountRepository.saveUserInfo(userId = userId, name = firstName, lastName = lastName)
  }
}