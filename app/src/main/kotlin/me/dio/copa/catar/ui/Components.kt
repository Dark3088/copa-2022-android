package me.dio.copa.catar.ui


import androidx.compose.animation.*
import androidx.compose.animation.core.*
import me.dio.copa.catar.R
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.*
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import kotlinx.coroutines.launch
import me.dio.copa.catar.core.MatchNotifier
import me.dio.copa.catar.domain.extensions.getDate
import me.dio.copa.catar.domain.model.MatchDomain
import me.dio.copa.catar.domain.model.Stadium
import me.dio.copa.catar.ui.theme.MatchTextStyle

/*
TODO:
1. Criar uma lista com elementos que se expandem ao toque
2. Implementar um ícone de notificação que, quando tocado, cria um agendamento (work manager) para o usuário
3. Mostrar informações do estádio, como nome e magem junto a data e hora
 */
@Composable
fun MatchList(
    matchList: List<MatchDomain>,
    mViewModel: MainViewModel = viewModel()
) {
    val mIdsPlaceholder by mViewModel.expandedCardIdsList.collectAsState()
    val context = LocalContext.current
    val coroutine = rememberCoroutineScope()

    Scaffold { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top

        ) {
            items(matchList, key = { it.id }) { matchInfo ->

                val rotateAndExpand = mIdsPlaceholder.contains(matchInfo.id)

                val rotationDegrees = animateFloatAsState(
                    targetValue = if (rotateAndExpand) 180.0f else 0.0f, animationSpec = spring(
                        Spring.DampingRatioMediumBouncy,
                        Spring.StiffnessLow
                    )
                )

                RowItemInfo(description = matchInfo.name)
                PlayersInfo(
                    teamsInfo = matchInfo,
                    showMoreInfo = { mViewModel.onCardArrowClicked(matchInfo.id) },
                    cardArrowDegrees = rotationDegrees.value
                )

                StadiumInfo(
                    stadium = matchInfo.stadium,
                    shouldExpand = rotateAndExpand,
                    shouldSetNotification = { itShouldBeSet ->
                        if (itShouldBeSet) {
                            mViewModel.enableNotificationFor(matchInfo.id)
                            coroutine.launch {
                                MatchNotifier.start(
                                    context, matchInfo.name, matchInfo.date.getDate()
                                )
                            }
                        } else {
                            mViewModel.disableNotificationFor(matchInfo.id)
                            coroutine.launch {
                                MatchNotifier.cancel(context)
                            }
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun RowItemInfo(description: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = description,
            style = MaterialTheme.typography.caption
        )
    }
}

@Composable
fun PlayersInfo(
    teamsInfo: MatchDomain,
    cardArrowDegrees: Float,
    showMoreInfo: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        val textStyle = MatchTextStyle.titleSmall
        Text(text = teamsInfo.team1.flag, style = textStyle)
        Text(text = teamsInfo.team1.displayName, style = textStyle)

        Text(text = "X", style = textStyle)

        Text(text = teamsInfo.team2.flag, style = textStyle)
        Text(text = teamsInfo.team2.displayName, style = textStyle)

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            CardArrow(degrees = cardArrowDegrees, onClick = showMoreInfo)
        }
    }
}

@Composable
fun CardArrow(
    degrees: Float,
    onClick: () -> Unit
) {
    IconButton(
        modifier = Modifier
            .size(24.dp),
        onClick = onClick,
        content = {
            Icon(
                painter = painterResource(id = R.drawable.baseline_expand_more_24),
                contentDescription = "Expandable Arrow",
                modifier = Modifier.rotate(degrees),
            )
        }
    )
}

@Composable
fun StadiumInfo(
    stadium: Stadium,
    shouldExpand: Boolean,
    shouldSetNotification: (Boolean) -> Unit
) {
    val density = LocalDensity.current

    AnimatedVisibility(
        visible = shouldExpand,
        enter = slideInVertically {
            with(density) { -10.dp.roundToPx() }
        }
                + expandVertically(expandFrom = Alignment.Top)
                + fadeIn(
            initialAlpha = 0.1f, animationSpec = tween(durationMillis = 700)
        ),

        exit = fadeOut(
            targetAlpha = 0f,
            animationSpec = tween(durationMillis = 700, easing = LinearOutSlowInEasing)
        ) + shrinkVertically()

    ) {
        Box {
            Row(
                verticalAlignment = Alignment.Top,
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth()
            ) {
                AsyncImage(
                    modifier = Modifier.fillMaxWidth(),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(stadium.image)
                        .crossfade(true)
                        .fallback(R.drawable.not_found)
                        .build(),

                    contentDescription = ""
                )
            }
            StadiumDetails(
                shouldSetNotification = { forThisMatch -> shouldSetNotification(forThisMatch) }
            )
        }
    }
}

@Composable
fun StadiumDetails(
    shouldSetNotification: (Boolean) -> Unit
) {

    var isNotificationActive by rememberSaveable { mutableStateOf(false) }

    val onNotificationClick = {
        isNotificationActive = !isNotificationActive
        shouldSetNotification(isNotificationActive)
    }

    val notificationIcon =
        if (isNotificationActive) R.drawable.ic_notifications_active
        else R.drawable.ic_notifications

    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        IconButton(
            onClick = onNotificationClick,
            modifier = Modifier.size(24.dp)
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(notificationIcon),
                contentDescription = "Notification Icon",
                tint = Color.White
            )
        }
    }
}




















