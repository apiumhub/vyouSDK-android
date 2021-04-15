package com.apiumhub.vyou_ui

import androidx.fragment.app.Fragment
import com.apiumhub.vyou_ui.databinding.VyouRegisterFragmentBinding
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class VyouRegisterFragment : Fragment(R.layout.vyou_register_fragment) {
    
    private val binding: VyouRegisterFragmentBinding by viewBinding(VyouRegisterFragmentBinding::bind)
    private val viewModel: VyouRegisterViewModel by viewModel()



    companion object {
        fun newInstance() = VyouRegisterFragment()
    }
}