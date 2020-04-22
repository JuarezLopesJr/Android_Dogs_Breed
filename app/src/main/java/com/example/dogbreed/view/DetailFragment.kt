package com.example.dogbreed.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.dogbreed.R
import com.example.dogbreed.util.getProgressDrawable
import com.example.dogbreed.util.loadImage
import com.example.dogbreed.viewmodel.DetailViewModel
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.item_dog.*

class DetailFragment : Fragment() {
    private var dogUuid = 0
    private lateinit var viewModel: DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
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
            dog?.let {
                dogName.text = dog.dogBreed
                dogPurpose.text = dog.bredFor
                dogTemperament.text = dog.temperament
                dogLifeSpan.text = dog.lifeSpan
                context?.let { dogImage.loadImage(dog.imageUrl, getProgressDrawable(it)) }
            }
        })
    }
}
