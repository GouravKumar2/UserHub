package com.example.userhub.ui.detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.userhub.ui.common.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDetailScreen(
    onBack: () -> Unit,
    viewModel: UserDetailViewModel = viewModel()
) {
    val state by viewModel.detailState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("User Details") },
                navigationIcon = {
                    TextButton(onClick = onBack) {
                        Text("Back")
                    }
                }
            )
        }
    ) { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {

            when (val currentState = state) {

                is UiState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                is UiState.Error -> {
                    Column(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(currentState.message)

                        Button(onClick = { viewModel.loadUserDetails() }) {
                            Text("Retry")
                        }
                    }
                }

                is UiState.Success -> {
                    val data = currentState.data

                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        item {
                            Text(
                                text = data.user.name,
                                style = MaterialTheme.typography.headlineSmall
                            )

                            Text("Email: ${data.user.email}")
                            Text("Phone: ${data.user.phone}")
                            Text("Website: ${data.user.website}")
                            Text("Company: ${data.user.company.name}")

                            HorizontalDivider(
                                modifier = Modifier.padding(vertical = 16.dp)
                            )

                            Text(
                                text = "Posts",
                                style = MaterialTheme.typography.titleLarge
                            )
                        }

                        items(data.posts) { post ->
                            Card(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(
                                    modifier = Modifier.padding(16.dp)
                                ) {
                                    Text(
                                        text = post.title,
                                        style = MaterialTheme.typography.titleMedium
                                    )

                                    Spacer(modifier = Modifier.height(8.dp))

                                    Text(post.body)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}