package me.dio.copa.catar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dagger.hilt.android.AndroidEntryPoint
import me.dio.copa.catar.extensions.rememberFlow
import me.dio.copa.catar.ui.MainViewModel
import me.dio.copa.catar.ui.MatchList
import me.dio.copa.catar.ui.theme.Copa2022Theme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Copa2022Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val lifeCycleAware = rememberFlow(flow = viewModel.matchListState)
                    val list by lifeCycleAware.collectAsState(initial = emptyList())

                    Scaffold(
                        topBar = {
                            TopAppBar(title = { Text(text = "Match Notifier") })
                        }
                    ) { paddingValues ->

                        Column(
                            modifier = Modifier.padding(paddingValues)
                        ) {
                            ListHeader("Lista de Partidas")
                            MatchList(list)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ListHeader(title: String) {
    Row(
        modifier = Modifier
            .padding(top = 24.dp, start = 16.dp, end = 16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(text = title)
    }
}

@Composable
@Preview(showBackground = true)
fun DefaultPreview() {
    Copa2022Theme {
        ListHeader(title = "Matches List")
    }
}
