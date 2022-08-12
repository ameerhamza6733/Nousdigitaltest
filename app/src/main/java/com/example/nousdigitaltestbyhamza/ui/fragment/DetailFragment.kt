package com.example.nousdigitaltestbyhamza.ui.fragment

import android.view.View
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.nousdigitaltestbyhamza.R
import com.example.nousdigitaltestbyhamza.Resource
import com.example.nousdigitaltestbyhamza.Utils
import com.example.nousdigitaltestbyhamza.databinding.FragmentDetailBinding
import com.example.nousdigitaltestbyhamza.model.Post
import com.example.nousdigitaltestbyhamza.viewModel.DetailFragmentViewModel
import com.google.android.material.snackbar.Snackbar
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding


class DetailFragment : FragmentBase(R.layout.fragment_detail) {

    private val binding by viewBinding (FragmentDetailBinding::bind)
    private val viewModel by viewModels<DetailFragmentViewModel> ()
    private var post:Post?=null

    override fun initViews() {
        super.initViews()
        binding.btOpenEmail.setOnClickListener {
            viewModel.preparTheImageForEmail(post?.postImageUrl.toString())
        }
    }

    override fun initObserver() {
        super.initObserver()
        viewModel.liveDataEmailImage.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    binding.progressCircular.visibility = View.INVISIBLE
                    binding.btOpenEmail.isEnabled = true
                    Utils.composeEmail(
                        requireActivity().applicationContext,
                        arrayOf("ameer.hamza6733@gmail.com"),
                        post?.title.toString(),
                        post?.postDescription.toString(),
                        it.data
                    )
                }
                is Resource.Error -> {
                    binding.progressCircular.visibility = View.INVISIBLE
                    binding.btOpenEmail.isEnabled = true
                    Snackbar.make(binding.btOpenEmail, "${it.errorMessage}", Snackbar.LENGTH_LONG)
                        .show()
                }
                is Resource.Loading -> {
                    binding.progressCircular.visibility = View.VISIBLE
                    binding.btOpenEmail.isEnabled = false
                }
            }
        }
        shareViewModel.dataPostListFragmentToDetailFragment.observe(viewLifecycleOwner) {
            it?.let {
                post=it
                binding.tvPostTitle.text = it.title
                binding.tvPostDes.text = it.postDescription
                Glide.with(binding.root).asBitmap()
                    .apply(RequestOptions().error(R.drawable.ic_baseline_image_24))
                    .load(it.postImageUrl)
                    .into(binding.postImage)
            }
        }
    }

}