package com.apiumhub.vyou.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.apiumhub.vyou.R
import com.apiumhub.vyou.databinding.MainFragmentBinding
import com.apiumhub.vyou_core.Vyou
import com.apiumhub.vyou_core.domain.VyouResult
import com.apiumhub.vyou_core.domain.VyouResult.*
import com.apiumhub.vyou_core.login.domain.VyouCredentials
import com.apiumhub.vyou_core.session.domain.VyouSession
import com.apiumhub.vyou_ui.VyouUI
import com.google.android.material.snackbar.Snackbar
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import kotlinx.coroutines.launch

class MainFragment : Fragment(R.layout.main_fragment) {

    private val navController: NavController by lazy { findNavController() }

    private val vyouLogin = Vyou.getLogin(this)
    private val vyouUi = VyouUI(this)
    private val binding: MainFragmentBinding by viewBinding(MainFragmentBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mainAuthBtn.setOnClickListener {
            launchLogin { vyouLogin.signInWithAuth() }
        }
        binding.mainGoogleSingInBtn.setOnClickListener {
            launchLogin { vyouLogin.signInWithGoogle() }
        }
        binding.mainFbSignInBtn.setOnClickListener {
            launchLogin { vyouLogin.signInWithFacebook(this@MainFragment) }
        }
        binding.mainRegisterBtn.setOnClickListener {
            lifecycleScope.launch {
                navigateToRegister()
            }
        }
    }

    private fun launchLogin(function: suspend () -> VyouResult<VyouSession>) {
        lifecycleScope.launch {
            when (val result = function()) {
                is Failure -> Snackbar.make(binding.root, "There was an unexpected error", Snackbar.LENGTH_LONG).show()
                is Success -> {
                    val credentials = result.value.credentials
                    if (credentials.tenantCompliant && credentials.tenantConsentCompliant)
                        launch {
                            Vyou.session?.credentials?.let {
                                vyouUi.startProfile(it)
                            }
                        }
                    else {
                        navigateToProfile(credentials)
                    }
                }
            }
        }
    }

    private fun navigateToProfile(credentials: VyouCredentials) {
        lifecycleScope.launch {
            when (vyouUi.startProfile(credentials)) {
                is Success -> launch {
                    Vyou.session?.credentials?.let {
                        vyouUi.startProfile(it)
                    }
                }
                is Failure -> Snackbar.make(binding.root, "An unexpected error occured", Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun navigateToRegister() {
        lifecycleScope.launch {
            when (vyouUi.startRegister()) {
                is Success -> Snackbar.make(binding.root, "User registered successfully.", Snackbar.LENGTH_LONG).show()
                is Failure -> Snackbar.make(binding.root, "An unexpected error occured", Snackbar.LENGTH_LONG).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        vyouLogin.onActivityResult(requestCode, resultCode, data)
    }
}