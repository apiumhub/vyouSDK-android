package com.apiumhub.vyou_ui.profile.presentation

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.apiumhub.vyou_core.VYou
import com.apiumhub.vyou_ui.R
import com.apiumhub.vyou_ui.VYouUI
import com.apiumhub.vyou_ui.components.exception.ValidationException
import com.apiumhub.vyou_ui.databinding.VyouProfileFragmentBinding
import com.apiumhub.vyou_ui.edit_profile.presentation.TenantCompliant
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

internal class VYouProfileFragment : Fragment(R.layout.vyou_profile_fragment) {
    lateinit var tenantCompliant: TenantCompliant
    private val viewModel: VYouProfileViewModel by viewModel { parametersOf(listOf(
        context?.getString(R.string.gender_male),
        context?.getString(R.string.gender_female),
        context?.getString(R.string.gender_other)
    ))}
    private val binding: VyouProfileFragmentBinding by viewBinding(VyouProfileFragmentBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.onViewCreated()

        arguments?.let { bundle ->
            bundle.getParcelable<TenantCompliant>("tenantCompliant")?.let {
                tenantCompliant = it
            }
        }

        viewModel.tenant.observe(viewLifecycleOwner) {
            binding.tvLogOut.isVisible = true
            binding.profileDynamicList.isVisible = true
            binding.profileLoadingPb.isVisible = false
            binding.profileDynamicList.render(it.fields)
        }

        viewModel.profile.observe(viewLifecycleOwner) {
            binding.profileLoadingPb.isVisible = false
            binding.tvName.text = it.mandatoryFields.entries.find { entry -> entry.key == "name" }?.value
            binding.profileDynamicList.fillWithProfile(it)
        }

        viewModel.logOut.observe(viewLifecycleOwner) {
            requireActivity().onBackPressed()
        }

        viewModel.error.observe(viewLifecycleOwner) {
            Log.e("Vyou", "Profile error", it)
            requireActivity().setResult(Activity.RESULT_CANCELED)
            requireActivity().finish()
        }

        binding.tvEditProfile.setOnClickListener {
            findNavController().navigate(VYouProfileFragmentDirections.profileToEditProfile(tenantCompliant))
        }

        binding.tvLogOut.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setMessage(R.string.profile_dialog_log_out_message)
                .setPositiveButton(R.string.profile_dialog_log_out_confirm) { _, _ ->
                    viewModel.signOut()
                }
                .setNegativeButton(R.string.profile_dialog_log_out_cancel) { _, _ ->

                }
                .show()
        }

    }
}