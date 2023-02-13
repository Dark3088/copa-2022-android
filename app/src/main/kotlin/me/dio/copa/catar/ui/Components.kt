package me.dio.copa.catar.ui


import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.*
import me.dio.copa.catar.R
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.*
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
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
    val testLisIds by mViewModel.expandedCardIdsList.collectAsState()

/*        val mMatchList = listOf(
            MatchDomain(
            id = "1",
            name = "1ª RODADA",
            stadium = Stadium(name = "LUSAIL", image = "https://digitalinnovationone.github.io/copa-2022-android/statics/lusali-stadium.png"),
            team1 = Team(displayName = "BR", flag = ""),
            team2 = Team(displayName = "RS", flag = ""),
            date = LocalDateTime.now()
            ),
            MatchDomain(
                id = "2",
                name = "2ª RODADA",
                stadium = Stadium(name = "ESTÁDIO 974", image = "https://digitalinnovationone.github.io/copa-2022-android/statics/974-stadium.png"),
                team1 = Team(displayName = "BR", flag = ""),
                team2 = Team(displayName = "CH", flag = ""),
                date = LocalDateTime.now()
            ),
        )*/

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

                val rotateAndExpand = testLisIds.contains(matchInfo.id)

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
                    shouldExpand = rotateAndExpand
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
fun StadiumInfo(stadium: Stadium, shouldExpand: Boolean) {

    Row(
        verticalAlignment = Alignment.Top,
        modifier = Modifier
            .animateContentSize(
                animationSpec = tween(
                    700, easing = FastOutSlowInEasing
                )
            )
            .padding(vertical = 8.dp)
            .fillMaxWidth()
    ) {
        AsyncImage(
            modifier = Modifier.fillMaxWidth(),
            model = if (shouldExpand) {
                ImageRequest.Builder(LocalContext.current)
                    .data(stadium.image)
                    .crossfade(true)
                    .fallback(R.drawable.ic_notifications_active)
                    .build()
            } else null,
            contentDescription = ""
        )
    }
}




















