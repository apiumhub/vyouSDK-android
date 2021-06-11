package com.apiumhub.vyou_ui.register.presentation

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.apiumhub.vyou_ui.R
import com.apiumhub.vyou_ui.components.exception.ValidationException
import com.apiumhub.vyou_ui.databinding.VyouRegisterFragmentBinding
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

internal class VYouRegisterFragment : Fragment(R.layout.vyou_register_fragment) {

    private val binding: VyouRegisterFragmentBinding by viewBinding(VyouRegisterFragmentBinding::bind)
    private val viewModel: VYouRegisterViewModel by viewModel {
        parametersOf(
            listOf(
                context?.getString(R.string.gender_male),
                context?.getString(R.string.gender_female),
                context?.getString(R.string.gender_other)
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            Log.e("VYou", "Register error", it)
            requireActivity().setResult(Activity.RESULT_CANCELED)
            requireActivity().finish()
        }
        viewModel.dynamicForm.observe(viewLifecycleOwner) {
            binding.checkBoxesDynamicForm.isVisible = true
            binding.saveBtn.isVisible = true
            binding.profileDynamicForm.isVisible = true
            binding.profileLoadingPb.isVisible = false
            binding.profileDynamicForm.render(it.fields)
            binding.profileDynamicForm.observe {
                binding.saveBtn.isEnabled = binding.profileDynamicForm.isValid && binding.checkBoxesDynamicForm.isValid
            }
            binding.checkBoxesDynamicForm.render(it.checkBoxes)
            binding.checkBoxesDynamicForm.observe {
                binding.saveBtn.isEnabled = binding.profileDynamicForm.isValid && binding.checkBoxesDynamicForm.isValid
            }
        }

        viewModel.userRegistered.observe(viewLifecycleOwner) {
            requireActivity().setResult(Activity.RESULT_OK)
            requireActivity().finish()
        }

        binding.saveBtn.text = context?.getString(R.string.button_register)
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