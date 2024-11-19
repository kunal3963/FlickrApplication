package com.practice.flickrapplication.presentation

import android.os.Build
import android.text.Html
import android.text.Spanned
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.practice.flickrapplication.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageDetailScreen(
    viewModel: MainViewModel,
    innerPadding: PaddingValues,
    navController: NavHostController
) {
    val selectedImage by viewModel.selectedImage.collectAsState()

    selectedImage?.let {

        val spanned: Spanned = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(it.description, Html.FROM_HTML_MODE_LEGACY)
        } else {
            Html.fromHtml(it.description)
        }

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text(text = "Details") },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                navController.popBackStack()
                            }
                        ) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                        }
                    }
                )
            }
        )
        { innerPadding ->
            Column(
                Modifier
                    .padding(16.dp)
                    .padding(innerPadding)
            ) {
                Image(
                    painter = rememberImagePainter(it.media.m),
                    contentDescription = it.title,
                    modifier = Modifier.fillMaxWidth()
                )
                Text("Title: ${it.title}", fontWeight = FontWeight.Bold)
                Text("Description: ${spanned.toString()}")
                Text("Author: ${it.author}")
                Text("Published: ${it.date_taken}")
            }
        } ?: run {
            // You could show a placeholder or error message if the image is not available
            Text(
                "No image selected",
                modifier = Modifier.fillMaxSize(),
                textAlign = TextAlign.Center
            )
        }
    }

}
