package com.example.dogbreed.view

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dogbreed.R
import com.example.dogbreed.viewmodel.ListViewModel
import kotlinx.android.synthetic.main.fragment_list.*


class ListFragment : Fragment() {
    private lateinit var viewModel: ListViewModel
    private val dogsListAdapter = DogListAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /* Instantiating the ListViewModel class from ListViewModel using ViewModel lifecycle method and calling the refresh method
        * */
        viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)
        viewModel.refresh()

        dogsList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = dogsListAdapter
        }
        // Swipe to refresh method implementation, starting the refresh process
        refreshLayout.setOnRefreshListener {
            dogsList.visibility = View.GONE
            listError.visibility = View.GONE
            loadingView.visibility = View.VISIBLE // my spinner implementation from the fragment_list.xml
            viewModel.refreshBypassCache() // starting the refresh spinner and updating from the API
            refreshLayout.isRefreshing = false // remove the little "swipe to refresh" spinner
        }

        observeViewModel()
    }

    /* This function is responsible for displaying the various states declared inside fragment_list.xml
    * it'll "observe" each state change and display the content accordingly
    *   */
    private fun observeViewModel() {
        viewModel.dogs.observe(this, Observer { dogs ->
            dogs?.let {
                dogsList.visibility = View.VISIBLE
                dogsListAdapter.updateDogList(dogs)
            }
        })
        // must handle each case, true or false in order to correct display the state
        viewModel.dogsLoadError.observe(this, Observer { isError ->
            isError?.let {
                listError.visibility = if (it) View.VISIBLE else View.GONE
            }
        })
        /* this statement is more complex because the spinner can be shown or not depending on the state
        * of the app, and when it's refreshing, the other views states must be hided, hence the second
        * if statement
        * */
        viewModel.loading.observe(this, Observer { isLoading ->
            isLoading?.let {
                loadingView.visibility = if (it) View.VISIBLE else View.GONE

                if (it) {
                    dogsList.visibility = View.GONE
                    listError.visibility = View.GONE
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.list_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.actionSettings -> {
                view?.let {
                    Navigation.findNavController(it)
                        .navigate(ListFragmentDirections.actionSettingsFragment())
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    // Remember to ALWAYS put the actions inside this method, NEVER inside onCreateView
    /*override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnDetails.setOnClickListener {
            // Navigating to actionDetailFragment - id created by me in the navigation xml
            val action = ListFragmentDirections.actionDetailFragment()
            action.dogUuid = 5
            Navigation.findNavController(it).navigate(action)

        }
    }*/
}
