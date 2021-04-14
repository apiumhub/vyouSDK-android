package com.apiumhub.vyou.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.apiumhub.vyou.R
import com.apiumhub.vyou.databinding.MainFragmentBinding
import com.apiumhub.vyou.ui.authenticated.AuthenticatedUserFragment
import com.apiumhub.vyou_core.Vyou
import com.apiumhub.vyou_core.session.domain.VyouSession
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import kotlinx.coroutines.launch

class MainFragment : Fragment(R.layout.main_fragment) {

    private val vyouLogin = Vyou.getLogin(this)

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
            launchLogin {
                vyouLogin.signInWithFacebook(this@MainFragment)
            }
        }
        binding.mainLoadTenantBtn.setOnClickListener {
            lifecycleScope.launch {
                Vyou.tenantManager.getTenant()
            }
        }
    }

    private fun launchLogin(function: suspend () -> VyouSession) {
        lifecycleScope.launch {
            function()
            parentFragmentManager.beginTransaction()
                .add(R.id.container, AuthenticatedUserFragment.newInstance())
                .commitNow()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        vyouLogin.onActivityResult(requestCode, resultCode, data)
    }

    companion object {
        fun newInstance(): MainFragment = MainFragment()
    }
}