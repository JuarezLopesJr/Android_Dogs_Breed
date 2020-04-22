package com.example.dogbreed.util

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.dogbreed.R

fun getProgressDrawable(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        // test those values to see what works best
        strokeWidth = 10f // f is for float
        centerRadius = 50f
        start()
    }
}

// ImageView extension function loadImage()
fun ImageView.loadImage(uri: String?, progressDrawable: CircularProgressDrawable) {
    val options = RequestOptions()
        .placeholder(progressDrawable)
        .error(R.mipmap.ic_dog_icon) // treating the exception by displaying a backup image

    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(uri)
        .into(this) // "this" refers to the ImageView class
}

/* this value android:imageUrl will be available in the <ImageView> tag inside item_dog.xml and it'll
 be the url source for the image to be populated
*  */
@BindingAdapter("android:imageUrl")
fun bindingLoadImage(view: ImageView, url: String?) {
    view.loadImage(url, getProgressDrawable(view.context))
}