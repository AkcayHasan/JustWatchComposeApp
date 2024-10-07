package com.akcay.justwatch.domain.usecase

import com.akcay.justwatch.firebase.service.AccountRepository
import com.akcay.justwatch.internal.util.NetworkResult
import javax.inject.Inject

class CreateAnonymousUseCase @Inject constructor(
  private val accountRepository: AccountRepository
) {

  suspend operator fun invoke(): NetworkResult<Unit> {
    return accountRepository.createAnonymousUser()
  }
}