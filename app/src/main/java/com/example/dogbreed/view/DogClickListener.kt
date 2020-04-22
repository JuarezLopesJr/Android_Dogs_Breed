package com.example.dogbreed.view

import android.view.View

/* also pass the function to the layout i wanted to be clickable using this parameter
* android:onClick="@{listener::onDogClicked}, pass this to the parent layout, in this case it was
* <LinearLayout> with has the <TextView> and <ImageView> children, that way the onClick will spread to all
*  children
* */
interface DogClickListener {
    fun onDogClicked(v: View)
}