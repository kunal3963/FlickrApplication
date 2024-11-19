package com.practice.flickrapplication.network

// Flickr API Data Model
data class FlickrResponse(
    val items: List<ImageItem>
)

data class ImageItem(
    val title: String,
    val description: String,
    val author: String,
    val date_taken: String,
    val media: Media,
    val link: String
)

data class Media(
    val m: String  // The URL for the image thumbnail
)