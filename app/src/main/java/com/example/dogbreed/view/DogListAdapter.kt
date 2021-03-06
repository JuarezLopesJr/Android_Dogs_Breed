package com.example.dogbreed.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.dogbreed.R
import com.example.dogbreed.databinding.ItemDogBinding
import com.example.dogbreed.model.DogBreed
import kotlinx.android.synthetic.main.item_dog.view.*

/* this class will populate and display all content to the user, based on the API response, the layout is
*  configured in item_dog.xml
*  */
class DogListAdapter(val dogList: ArrayList<DogBreed>) : RecyclerView.Adapter<DogListAdapter.DogViewHolder>(),
    DogClickListener {

    fun updateDogList(newDogsList: List<DogBreed>) {
        dogList.clear()
        dogList.addAll(newDogsList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogViewHolder {
        val inflater = LayoutInflater.from(parent.context)
//        val view = inflater.inflate(R.layout.item_dog, parent, false)
        val view = DataBindingUtil.inflate<ItemDogBinding>(inflater, R.layout.item_dog, parent, false)
        return DogViewHolder(view)
    }

    override fun getItemCount() = dogList.size

    /* each value from the layout is set in this method */
    override fun onBindViewHolder(holder: DogViewHolder, position: Int) {
        // dog comes from item_dog.xml <variable name="dog"> tag
        holder.view.dog = dogList[position]
        holder.view.listener = this // enabling the listener clickable, this is DogClickListener

        /*holder.view.dogName.text = dogList[position].dogBreed
        holder.view.lifespan.text = dogList[position].lifeSpan
        holder.view.setOnClickListener {
            val action = ListFragmentDirections.actionDetailFragment()
            action.dogUuid = dogList[position].uuid
            Navigation.findNavController(it).navigate(action)*/
    }

    /* this method loadImage is from the ImageView class extension function created at the
     util/Util.kt. This imageView, points to the ImageView component in the item_dog.xml
     the second parameter getProgressDrawable() is the function also declared inside Util.kt
    */
    /*holder.view.imageView.loadImage(
        dogList[position].imageUrl,
        getProgressDrawable(holder.view.imageView.context)
    )*/
    //}
    /* implementing the DogClickListener interface */
    override fun onDogClicked(v: View) {
        val uuid = v.dogId.text.toString().toInt()
        val action = ListFragmentDirections.actionDetailFragment()
        action.dogUuid = uuid
        Navigation.findNavController(v).navigate(action)
    }

    /* binding the elements inside item_dog.xml (binds provided by the <layout> tag */
    class DogViewHolder(var view: ItemDogBinding) : RecyclerView.ViewHolder(view.root)

}