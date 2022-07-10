package com.example.nousdigitaltestbyhamza.ui.fragment

import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.nousdigitaltestbyhamza.Log
import com.example.nousdigitaltestbyhamza.R
import com.example.nousdigitaltestbyhamza.Resource
import com.example.nousdigitaltestbyhamza.databinding.FragmentPostListBinding
import com.example.nousdigitaltestbyhamza.isInternetError
import com.example.nousdigitaltestbyhamza.model.Post
import com.example.nousdigitaltestbyhamza.ui.adupters.PostAdupter
import com.example.nousdigitaltestbyhamza.viewModel.PostListFragmentViewModel
import com.google.android.material.snackbar.Snackbar
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PostListFragment : FragmentBase(R.layout.fragment_post_list) {
    private lateinit var searchView: SearchView
    private val binding by viewBinding (FragmentPostListBinding::bind)
    private val viewModel by viewModels<PostListFragmentViewModel> ()
    private var postAdupter: PostAdupter?=null
    private var layoutManager:GridLayoutManager?=null
    private var observerPost: Observer<Resource<List<Post>>>?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log("onViewCreated")
    }

    override fun initViews() {
        super.initViews()

    }
   @Deprecated("Deprecated in Java")
   override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
       Log("onCreateOptionsMenu")
        inflater.inflate(R.menu.options_menu, menu)
       // Associate searchable configuration with the SearchView
       val searchViewMenuItem = menu.findItem(R.id.search)
       searchView= searchViewMenuItem.actionView as SearchView

      searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
           override fun onQueryTextSubmit(p0: String?): Boolean {
               return false
           }

           override fun onQueryTextChange(p0: String?): Boolean {
               viewModel.search(p0)
              return false
           }

       })





       searchView. post( ) {

           viewModel.searchText?.let {

               searchView.setQuery(it,false)

           }

       }
       searchView.postDelayed(Runnable {
           searchView.setIconified(false);
       },5000)
       super.onCreateOptionsMenu(menu!!, inflater)
   }

    override fun initObserver() {
        super.initObserver()

        observerPost?.let { viewModel.postLiveData.removeObserver(it) }
        observerPost= Observer {
            when (it) {
                is Resource.Success -> {
                    Log("postLiveData onSuceess ${it.message}")
                    binding.progressCircular.visibility=View.INVISIBLE
                   if (it.data.isEmpty()){
                       binding.noSearchResult.visibility=View.VISIBLE
                       binding.postRecylerView.visibility=View.INVISIBLE

                   }else{
                       binding.noSearchResult.visibility=View.INVISIBLE
                       binding.postRecylerView.visibility=View.VISIBLE

                       postAdupter= PostAdupter(it.data){post,adupterPostionClick->
                           viewModel.postListItemClick=adupterPostionClick
                           openDetail(post)
                       }
                       layoutManager= GridLayoutManager(requireContext(),2)
                       binding.postRecylerView.layoutManager=layoutManager
                       binding.postRecylerView.adapter=postAdupter
                       if (viewModel.postListItemClick!=-1){
                           binding.postRecylerView.scrollToPosition(viewModel.postListItemClick)
                       }
                   }

                }
                is Resource.Error -> {
                    binding.progressCircular.visibility=View.INVISIBLE

                    if (it.error?.isInternetError()==true){
                        Snackbar.make(binding.progressCircular,"NetWork error",Snackbar.LENGTH_INDEFINITE).setAction("tryAgain",{
                            viewModel.downloadFileFromNetwork()
                        })
                            .show()

                    }else{
                        Snackbar.make(binding.progressCircular,"error ${it.errorMessage}",Snackbar.LENGTH_LONG)
                            .show()

                    }

                }
                is Resource.Loading -> {
                    binding.progressCircular.visibility=View.VISIBLE
                }
            }
        }
        viewModel.postLiveData.observe(this,observerPost!!)
    }

    private fun openDetail(post:Post){

        shareViewModel.dataPostListFragmentToDetailFragment.value=post
        findNavController().navigate(R.id.action_postListFragment_to_detailFragment)
    }

}