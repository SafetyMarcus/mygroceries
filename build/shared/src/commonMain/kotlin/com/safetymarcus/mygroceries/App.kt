package com.safetymarcus.mygroceries

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.safetymarcus.mygroceries.theme.AppTheme
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

// --- Shared Model ---
@Serializable //TODO move server models out to shared module, reference across server and shared UIs
data class Item(
    val name: String = "",
    val brand: String = "",
    val description: String = "",
    val size: String = "",
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemsListScreen(viewModel: ItemsViewModel) {
    val items by viewModel.items.collectAsState()
    val scope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        scope.launch { viewModel.onResume() }
    }
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("All products") })
        }
    ) { padding ->
        if (items.isEmpty()) {
            Box(
                Modifier.fillMaxSize().padding(padding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                items(items) { item ->
                    ListItem(
                        headlineContent = { Text(item.name) },
                        supportingContent = {
                            Text(
                                listOfNotNull(
                                    item.brand.takeIf { it.isNotBlank() },
                                    item.size.takeIf { it.isNotBlank() }).joinToString(", ")
                            )
                        },
                        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
                    )
                    HorizontalDivider()
                }
            }
        }
    }
}

@Composable
fun App() = AppTheme {
    ItemsListScreen(viewModel = remember { ItemsViewModel(KtorItemsRepository()) })
}