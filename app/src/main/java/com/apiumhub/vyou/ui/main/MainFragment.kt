package com.apiumhub.vyou.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.ComponentActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.apiumhub.vyou.R
import com.apiumhub.vyou.databinding.MainFragmentBinding
import com.apiumhub.vyou_core.VyouCore
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment(R.layout.main_fragment) {

    private val vyouCore = VyouCore(this)

    private val viewModel: MainViewModel by viewModel()
    private val binding: MainFragmentBinding by viewBinding(MainFragmentBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mainAuthBtn.setOnClickListener {
            lifecycleScope.launch {
                vyouCore.signInWithAuth().let {
                    Log.d("Credentials", it.toString())
                }
            }
        }
        binding.mainGoogleSingInBtn.setOnClickListener {
            lifecycleScope.launch {
                vyouCore.signInWithGoogle().let {
                    Log.d("Credentials", it.toString())
                }
            }
        }
        binding.mainFbSignInBtn.setOnClickListener {
            lifecycleScope.launch {
                vyouCore.signInWithFacebook(this@MainFragment).let {
                    Log.d("Credentials", it.toString())
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        vyouCore.onActivityResult(requestCode, resultCode, data)
    }

    companion object {
        fun newInstance(): MainFragment = MainFragment()
    }
}