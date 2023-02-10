package me.dio.copa.catar.domain.usecase

import me.dio.copa.catar.domain.repositories.MatchesRepository

class DisableNotificationUseCase(
    private val matchesRepository: MatchesRepository
) {
    suspend operator fun invoke(id: String) = matchesRepository.disableNotificationFor(id)
}