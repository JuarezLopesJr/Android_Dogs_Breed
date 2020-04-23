package com.example.dogbreed.view

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.dogbreed.R
import com.example.dogbreed.databinding.FragmentDetailBinding
import com.example.dogbreed.model.DogPalette
import com.example.dogbreed.viewmodel.DetailViewModel

/* all code related to the view that it's commented, refers to the "old way" of populate the layout
 without using DataBinding library
* */
class DetailFragment : Fragment() {
    private var dogUuid = 0
    private lateinit var viewModel: DetailViewModel
    private lateinit var dataBinding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        return dataBinding.root
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            dogUuid = DetailFragmentArgs.fromBundle(it).dogUuid
        }

        viewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)
        viewModel.fetch(dogUuid)



        observeViewModel()

        /*btnList.setOnClickListener {
            // Navigating to actionListFragment - id created by me in the navigation xml
            val action = DetailFragmentDirections.actionListFragment()
            Navigation.findNavController(it).navigate(action)
        }*/
    }

    /* This function is responsible for displaying the various states declared inside fragment_detail.xml
    * it'll "observe" each state change and display the content accordingly
    *   */
    private fun observeViewModel() {
        viewModel.dogLiveData.observe(this, Observer { dog ->
            /* passing explicit "it" parameter (breed) to not shadow the inner it */
            dog?.let { breed ->
                dataBinding.dog = dog

                breed.imageUrl?.let {
                    setupBackgroundColor(it)
                }
            }

            /*dog?.let {
                dogName.text = dog.dogBreed
                dogPurpose.text = dog.bredFor
                dogTemperament.text = dog.temperament
                dogLifeSpan.text = dog.lifeSpan
                context?.let { dogImage.loadImage(dog.imageUrl, getProgressDrawable(it)) }
            }*/
        })
    }

    private fun setupBackgroundColor(url: String) {
        Glide.with(this)
            .asBitmap()
            .load(url)
            .into(object : CustomTarget<Bitmap>() {
                override fun onLoadCleared(placeholder: Drawable?) {}

                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    Palette.from(resource)
                        .generate { palette ->
                            val intColor = palette?.darkMutedSwatch?.rgb ?: 0
                            val myPalette = DogPalette(intColor)
                            dataBinding.palette = myPalette
                        }
                }
            })
    }
}
