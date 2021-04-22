package com.apiumhub.vyou_ui.register.presentation

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.apiumhub.vyou_ui.R
import com.apiumhub.vyou_ui.components.exception.ValidationException
import com.apiumhub.vyou_ui.databinding.VyouProfileFragmentBinding
import com.google.android.material.snackbar.Snackbar
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class VyouRegisterFragment : Fragment(R.layout.vyou_profile_fragment) {

    private val binding: VyouProfileFragmentBinding by viewBinding(VyouProfileFragmentBinding::bind)
    private val viewModel: VyouRegisterViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            //TODO
        }
        viewModel.dynamicForm.observe(viewLifecycleOwner) {
            binding.checkBoxesDynamicForm.isVisible = true
            binding.saveBtn.isVisible = true
            binding.profileDynamicForm.isVisible = true
            binding.profileLoadingPb.isVisible = false
            binding.profileDynamicForm.render(it.fields)
            binding.checkBoxesDynamicForm.render(it.checkBoxes)
        }

        viewModel.userRegistered.observe(viewLifecycleOwner) {
            //TODO requireActivity().setResult()
        }

        binding.saveBtn.text = "Register user"
        binding.saveBtn.setOnClickListener {
            runCatching {
                viewModel.sendDataToRegister(
                    binding.profileDynamicForm.getResponses().groupBy { it.fieldType },
                    binding.checkBoxesDynamicForm.getResponses()
                )
            }.onFailure {
                (it as ValidationException).view.requestFocus()
            }
        }
    }
}