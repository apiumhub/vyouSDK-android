package com.apiumhub.vyou.ui.authenticated

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.apiumhub.vyou.R
import com.apiumhub.vyou.databinding.AuthenticatedUserFragmentBinding
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding

class AuthenticatedUserFragment : Fragment(R.layout.authenticated_user_fragment) {

    private val binding: AuthenticatedUserFragmentBinding by viewBinding(AuthenticatedUserFragmentBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.authLoadTenantBtn.setOnClickListener {

        }
    }

    companion object {
        fun newInstance() = AuthenticatedUserFragment()
    }

}