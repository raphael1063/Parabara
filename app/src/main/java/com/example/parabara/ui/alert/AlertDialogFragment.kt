package com.example.parabara.ui.alert

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.parabara.databinding.DialogAlertBinding

class AlertDialogFragment : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = true
    }

    private lateinit var binding: DialogAlertBinding

    private var callback: AlertDialogResultListener? = null

    fun setListener(listener: AlertDialogResultListener) {
        callback = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogAlertBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            tvAlertDialogCancel.setOnClickListener {
                dismiss()
            }
            tvAlertDialogDelete.setOnClickListener {
                callback?.onDeleteClicked()
                dismiss()
            }
        }
    }

    interface AlertDialogResultListener {
        fun onDeleteClicked()
    }

}