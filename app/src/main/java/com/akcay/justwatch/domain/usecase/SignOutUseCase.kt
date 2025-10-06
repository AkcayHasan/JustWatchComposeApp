package com.akcay.justwatch.domain.usecase

import com.akcay.justwatch.domain.repository.AccountRepository
import com.akcay.justwatch.internal.util.NetworkResult
class SignOutUseCase(
    private val accountRepository: AccountRepository
) {

    suspend operator fun invoke(): NetworkResult<Unit> {
        return accountRepository.signOut()
    }
}