package com.example.nousdigitaltestbyhamza.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.nousdigitaltestbyhamza.viewModel.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
open class FragmentBase(@LayoutRes layout:Int) :Fragment(layout)  {
    open val shareViewModel by activityViewModels<SharedViewModel> ()
    private var attachObvers=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObserver()

    }

    open fun initViews(){

    }

    open fun initObserver(){

    }
}