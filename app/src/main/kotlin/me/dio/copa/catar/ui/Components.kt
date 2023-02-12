package me.dio.copa.catar.ui

import me.dio.copa.catar.R
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import me.dio.copa.catar.domain.model.MatchDomain
import me.dio.copa.catar.domain.model.Stadium
import me.dio.copa.catar.domain.model.Team
import me.dio.copa.catar.ui.theme.MatchTextStyle

/*
TODO:
1. Criar uma lista com elementos que se expandem ao toque
2. Implementar um ícone de notificação que, quando tocado, cria um agendamento (work manager) para o usuário
3. Mostrar informações do estádio, como nome e magem junto a data e hora
 */
@Composable
fun MatchList(
    matchesList: List<MatchDomain>
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)

    ) {
        items(matchesList, key = { it.id }) { matchInfo ->
            RowItemInfo(description = matchInfo.name)
            PlayersInfo(
                team1Info = matchInfo.team1,
                team2Info = matchInfo.team2
            )
            StadiumInfo(stadium = matchInfo.stadium)
        }
    }
}

@Composable
fun RowItemInfo(description: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = description,
            style = MaterialTheme.typography.caption
        )
    }
}

@Composable
fun PlayersInfo(team1Info: Team, team2Info: Team) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        var isExpanded by remember { mutableStateOf(false) }
        val icon = if (isExpanded)
            R.drawable.baseline_expand_less_24 else R.drawable.baseline_expand_more_24

        val textStyle = MatchTextStyle.titleSmall
        Text(text = team1Info.displayName, style = textStyle)
        Text(text = team1Info.flag, style = textStyle)

        Text(text = "X", style = textStyle)

        Text(text = team2Info.flag, style = textStyle)
        Text(text = team2Info.displayName, style = textStyle)

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(
                onClick = { isExpanded = !isExpanded },
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.Top)
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(icon),
                    contentDescription = ""
                )
            }
        }
    }
}

@Composable
fun StadiumInfo(stadium: Stadium) {
    Row(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth()
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(stadium.image)
                .crossfade(true)
                .build(),
            contentDescription = ""
        )
    }
}




















