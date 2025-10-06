package com.akcay.justwatch.domain.usecase

import com.akcay.justwatch.data.remote.model.User
import com.akcay.justwatch.domain.repository.AccountRepository
import com.akcay.justwatch.internal.util.NetworkResult
import javax.inject.Inject

class GetUserProfileUseCase @Inject constructor(
    private val accountRepository: AccountRepository
) {
    suspend operator fun invoke(): NetworkResult<User> {
        return accountRepository.getUserProfile()
    }
}
