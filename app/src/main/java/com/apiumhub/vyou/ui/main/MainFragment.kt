package com.apiumhub.vyou.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.apiumhub.vyou.R
import com.apiumhub.vyou.databinding.MainFragmentBinding
import com.apiumhub.vyou_core.VyouCore
import com.apiumhub.vyou_core.VyouCredentials
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment(R.layout.main_fragment) {

    private val vyouCore = VyouCore(this)

    private val viewModel: MainViewModel by viewModel()
    private val binding: MainFragmentBinding by viewBinding(MainFragmentBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mainAuthBtn.setOnClickListener {
            launchLogin { vyouCore.signInWithAuth() }
        }
        binding.mainGoogleSingInBtn.setOnClickListener {
            launchLogin { vyouCore.signInWithGoogle() }
        }
        binding.mainFbSignInBtn.setOnClickListener {
            launchLogin { vyouCore.signInWithFacebook(this@MainFragment) }
        }
        binding.mainLogoutBtn.setOnClickListener {
            vyouCore.signOut()
            refreshLogoutVisibility()
        }
    }

    private fun launchLogin(function: suspend () -> VyouCredentials) {
        lifecycleScope.launch {
            function()
            refreshLogoutVisibility()
        }
    }

    private fun refreshLogoutVisibility() {
        binding.mainLogoutBtn.isVisible = vyouCore.loggedIn
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        vyouCore.onActivityResult(requestCode, resultCode, data)
    }

    companion object {
        fun newInstance(): MainFragment = MainFragment()
    }
}