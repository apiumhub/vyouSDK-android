package com.apiumhub.vyou_ui.edit_profile.presentation

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.apiumhub.vyou_ui.R
import com.apiumhub.vyou_ui.components.exception.ValidationException
import com.apiumhub.vyou_ui.databinding.VyouEditProfileFragmentBinding
import com.google.android.material.snackbar.Snackbar
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

internal class VYouEditProfileFragment : Fragment(R.layout.vyou_edit_profile_fragment) {

    lateinit var tenantCompliant: TenantCompliant
    private val viewModel: VYouEditProfileViewModel by viewModel { parametersOf(listOf(
        context?.getString(R.string.gender_male),
        context?.getString(R.string.gender_female),
        context?.getString(R.string.gender_other)
    ))}
    private val binding: VyouEditProfileFragmentBinding by viewBinding(VyouEditProfileFragmentBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let { bundle ->
            bundle.getParcelable<TenantCompliant>("tenantCompliant")?.let {
                 tenantCompliant = it
            }
        }

        viewModel.tenant.observe(viewLifecycleOwner) {
            binding.checkBoxesDynamicForm.isVisible = !tenantCompliant.isBothCompliant
            binding.saveBtn.isVisible = true
            binding.profileDynamicForm.isVisible = true
            binding.profileLoadingPb.isVisible = false
            binding.profileDynamicForm.render(it.fields)
            binding.profileDynamicForm.observe {
                binding.saveBtn.isEnabled = binding.profileDynamicForm.isValid && binding.checkBoxesDynamicForm.isValid
            }
            binding.checkBoxesDynamicForm.render(it.checkBoxes, tenantCompliant.tenantConsentCompliant)
            binding.checkBoxesDynamicForm.observe {
                binding.saveBtn.isEnabled = binding.profileDynamicForm.isValid && binding.checkBoxesDynamicForm.isValid
            }
        }

        viewModel.profile.observe(viewLifecycleOwner) {
            binding.profileDynamicForm.fillWithProfile(it)
        }

        viewModel.saved.observe(viewLifecycleOwner) {
            requireActivity().onBackPressed()
        }

        viewModel.error.observe(viewLifecycleOwner) {
            Log.e("Vyou", "Profile error", it)
            Snackbar.make(binding.root, "There was an unexpected error when updating profile.", Snackbar.LENGTH_LONG).show()
        }

        binding.saveBtn.text = context?.getString(R.string.button_save)
        binding.saveBtn.setOnClickListener {
            runCatching {
                viewModel.saveData(
                    binding.profileDynamicForm.getResponses().groupBy { it.fieldType },
                    binding.checkBoxesDynamicForm.getResponses()
                )
            }.onFailure {
                (it as ValidationException).view.requestFocus()
            }
        }
    }
}