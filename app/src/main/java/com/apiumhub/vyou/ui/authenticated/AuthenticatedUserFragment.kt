package com.apiumhub.vyou.ui.authenticated

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.apiumhub.vyou.R
import com.apiumhub.vyou.databinding.AuthenticatedUserFragmentBinding
import com.apiumhub.vyou_core.VYou
import com.apiumhub.vyou_core.domain.VYouResult
import com.apiumhub.vyou_core.session.domain.VYouProfile
import com.apiumhub.vyou_ui.VYouUI
import com.google.android.material.snackbar.Snackbar
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import kotlinx.coroutines.launch

class AuthenticatedUserFragment : Fragment(R.layout.authenticated_user_fragment) {

    private val session = VYou.session
    private val vyouUi = VYouUI(this)

    private val binding: AuthenticatedUserFragmentBinding by viewBinding(AuthenticatedUserFragmentBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.authLogoutBtn.setOnClickListener {
            lifecycleScope.launch {
                when (val result = session?.signOut()) {
                    is VYouResult.Success -> requireActivity().onBackPressed()
                    is VYouResult.Failure -> Snackbar.make(binding.root, "Unexpected error: ${result.error.message}", Snackbar.LENGTH_LONG).show()
                }
            }
        }

        binding.authEditProgfileBtn.setOnClickListener {
            lifecycleScope.launch {
                session?.credentials?.let {
                    vyouUi.startEditProfile(it)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        session?.let {
            lifecycleScope.launch {
                when (val result = it.tenantProfile()) {
                    is VYouResult.Success -> renderProfile(result.value)
                    is VYouResult.Failure -> Snackbar.make(binding.root, "Unexpected error: ${result.error.message}", Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun renderProfile(profile: VYouProfile) {
        binding.userNameTv.text = "Hello ${profile.mandatoryFields["name"]}"
    }
}