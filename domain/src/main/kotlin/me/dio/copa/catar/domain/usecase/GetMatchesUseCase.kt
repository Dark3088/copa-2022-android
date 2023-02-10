package me.dio.copa.catar.domain.usecase

import kotlinx.coroutines.flow.Flow
import me.dio.copa.catar.domain.model.Match
import me.dio.copa.catar.domain.repositories.MatchesRepository

class GetMatchesUseCase (
    private val matchesRepository: MatchesRepository
    ){
    suspend operator fun invoke() = matchesRepository.getMatches()
}