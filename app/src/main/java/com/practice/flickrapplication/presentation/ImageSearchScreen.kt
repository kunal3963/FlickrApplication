package com.practice.flickrapplication.presentation

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.practice.flickrapplication.network.ImageItem
import com.practice.flickrapplication.viewmodel.MainViewModel

@Composable
fun ImageSearchScreen(
    navController: NavHostController,
    viewModel: MainViewModel,
    innerPadding: PaddingValues
) {
    val images by viewModel.images.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val searchQuery by viewModel.searchText.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        // Search bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = {
                viewModel.searchText.value = it
                viewModel.searchImages(it)
            },
            label = { Text("Search images") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        // Loading Indicator
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp)
            )
        }

        // Grid of images
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(images) { item ->
                ImageThumbnail(item = item, onClick = {
                    viewModel.selectImage(item)
                    navController.navigate("imageDetail")
                })
            }
        }
    }
}

@Composable
fun ImageThumbnail(item: ImageItem, onClick: () -> Unit) {
    val painter = rememberImagePainter(item.media.m)
    Card(modifier = Modifier
        .padding(8.dp)
        .clickable(onClick = onClick)) {
        Log.d("ImageThumbnail", "Item: ${item.media.m}")
        Image(painter = painter, contentDescription = item.title, modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),  // You can adjust the height as per your requirements
            contentScale = ContentScale.Crop)
    }
}
