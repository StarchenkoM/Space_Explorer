package com.rprd.space_explorer.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.navArgs
import com.rprd.space_explorer.R
import com.rprd.space_explorer.databinding.FragmentDescriprionDialogBinding
import com.rprd.space_explorer.utils.ToastUtil
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.properties.Delegates

@AndroidEntryPoint
class DescriptionDialogFragment : DialogFragment() {

    private var binding by Delegates.notNull<FragmentDescriprionDialogBinding>()
    private val args: DescriptionDialogFragmentArgs by navArgs()

    @Inject
    lateinit var toastUtil: ToastUtil

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = FragmentDescriprionDialogBinding.inflate(inflater, container, false)
        setupUI()
        closeDialog()
        return binding.root
    }

    private fun setupUI() {
        val description = args.description ?: getString(R.string.lost_image_description_message)
        binding.descriptionTv.text = description
    }

    private fun closeDialog() {
        binding.closeBtn.setOnClickListener {
            dismiss()
        }
    }

}