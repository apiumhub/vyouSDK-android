package com.apiumhub.vyou_ui.profile.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.apiumhub.vyou_ui.R
import com.apiumhub.vyou_ui.components.exception.ValidationException
import com.apiumhub.vyou_ui.databinding.VyouProfileFragmentBinding
import com.google.android.material.snackbar.Snackbar
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class VyouProfileFragment : Fragment(R.layout.vyou_profile_fragment) {

    lateinit var tenantCompliant: TenantCompliant
    private val viewModel: ProfileViewModel by viewModel()
    private val binding: VyouProfileFragmentBinding by viewBinding(VyouProfileFragmentBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.tenant.observe(viewLifecycleOwner) {
            binding.checkBoxesDynamicForm.isVisible = true
            binding.saveBtn.isVisible = true
            binding.profileDynamicForm.isVisible = true
            binding.profileLoadingPb.isVisible = false
            binding.profileDynamicForm.render(it.fields)
            binding.checkBoxesDynamicForm.render(it.checkBoxes, tenantCompliant.tenantConsentCompliant)
        }

        viewModel.profile.observe(viewLifecycleOwner) {
            binding.profileDynamicForm.fillWithProfile(it)
        }

        viewModel.saved.observe(viewLifecycleOwner) {
//            Log.d("Saved", "Saved")
            //TODO
        }

        viewModel.error.observe(viewLifecycleOwner) {
//            Snackbar.make(binding.root, "There was an unexpected error", Snackbar.LENGTH_LONG).show()
            //TODO
        }

        binding.saveBtn.text = "Save"
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