package com.apiumhub.vyou_ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class VyouProfileFragment : Fragment() {

    companion object {
        fun newInstance() = VyouProfileFragment()
    }

    private lateinit var viewModel: VyouProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.vyou_profile_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(VyouProfileViewModel::class.java)
        // TODO: Use the ViewModel
    }

}