package me.dio.copa.catar.ui

import android.util.Log
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

import kotlinx.coroutines.launch

import me.dio.copa.catar.domain.model.Match
import me.dio.copa.catar.domain.model.MatchDomain

import me.dio.copa.catar.domain.repositories.MatchesRepository

import me.dio.copa.catar.domain.usecase.DisableNotificationUseCase
import me.dio.copa.catar.domain.usecase.EnableNotificationUseCase
import me.dio.copa.catar.domain.usecase.GetMatchesUseCase
import me.dio.copa.catar.extensions.observe
import me.dio.copa.catar.remote.model.MatchRemote

import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val matchesRepo: GetMatchesUseCase,
    private val disableNotificationUseCase: DisableNotificationUseCase,
    private val enableNotificationUseCase: EnableNotificationUseCase
) : ViewModel() {

    init {
        getAllMatches()
    }

    private val _matchListState = MutableStateFlow(emptyList<MatchDomain>())
    val matchListState : StateFlow<List<MatchDomain>> = _matchListState

    private val _expandedCardIdsList = MutableStateFlow(listOf<String>())
    val expandedCardIdsList: StateFlow<List<String>> get() = _expandedCardIdsList

    fun updateNotificationState(newMatchNotificationState: Match) {
        var matchNotificationState = newMatchNotificationState.copy(
            notificationEnabled = newMatchNotificationState.notificationEnabled
        )
    }

    private fun getAllMatches() = viewModelScope.launch {
        runCatching {
            matchesRepo.invoke().collect {
                _matchListState.value = it
            }
        }
    }

    fun onCardArrowClicked(cardId: String) {
        _expandedCardIdsList.value = _expandedCardIdsList.value.toMutableList().also { list ->
            if (list.contains(cardId)) list.remove(cardId) else list.add(cardId)
        }
    }
}