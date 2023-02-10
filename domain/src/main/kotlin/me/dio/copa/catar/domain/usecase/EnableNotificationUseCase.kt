package me.dio.copa.catar.domain.usecase

import me.dio.copa.catar.domain.repositories.MatchesRepository

class EnableNotificationUseCase(
    private val matchesRepository: MatchesRepository
) {
    suspend operator fun invoke(id: String) = matchesRepository.enableNotificationFor(id)
}