package com.apiumhub.vyou_ui.register.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.apiumhub.vyou_ui.R
import com.apiumhub.vyou_ui.databinding.VyouRegisterFragmentBinding
import com.apiumhub.vyou_ui.register.ui.exception.ValidationException
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class VyouRegisterFragment : Fragment(R.layout.vyou_register_fragment) {

    private val binding: VyouRegisterFragmentBinding by viewBinding(VyouRegisterFragmentBinding::bind)
    private val viewModel: VyouRegisterViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.dynamicForm.observe(viewLifecycleOwner) {
            binding.registerDynamicForm.render(it.fields)
            binding.checkBoxesDynamicForm.render(it.checkBoxes)
        }

        binding.registerCustomerBtn.setOnClickListener {
            runCatching {
                viewModel.sendDataToRegister(
                    binding.registerDynamicForm.getResponses().groupBy { it.fieldType },
                    binding.checkBoxesDynamicForm.getResponses()
                )
            }.onFailure {
                (it as ValidationException).view.requestFocus()
            }
        }
    }

    companion object {
        fun newInstance() = VyouRegisterFragment()
    }
}