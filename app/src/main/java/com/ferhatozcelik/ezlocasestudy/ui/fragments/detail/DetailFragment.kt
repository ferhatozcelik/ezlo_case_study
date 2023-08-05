package com.ferhatozcelik.ezlocasestudy.ui.fragments.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.ferhatozcelik.ezlocasestudy.R
import com.ferhatozcelik.ezlocasestudy.data.entity.DeviceEntity
import com.ferhatozcelik.ezlocasestudy.databinding.FragmentDetailBinding
import com.ferhatozcelik.ezlocasestudy.ui.base.BaseFragment
import com.ferhatozcelik.ezlocasestudy.util.IMAGE_1
import com.ferhatozcelik.ezlocasestudy.util.NAME_1
import com.ferhatozcelik.ezlocasestudy.util.generatorImage
import com.ferhatozcelik.ezlocasestudy.util.gone
import com.ferhatozcelik.ezlocasestudy.util.show
import com.ferhatozcelik.ezlocasestudy.util.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>(FragmentDetailBinding::inflate) {
    private val TAG = DetailFragment::class.java.simpleName

    private val viewModel: DetailViewModel by viewModels()
    private var deviceEntity: DeviceEntity? = null

    private var isEdit = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        deviceEntity = arguments?.getParcelable<DeviceEntity>("item") as DeviceEntity

        if (deviceEntity == null) {
            activity?.findNavController(R.id.nav_host_fragment)?.popBackStack()
            context?.toast("Object Not Found")
            Log.e(TAG,  "Object Not Found")
        } else {
            Log.d(TAG,  "Object Found: " + deviceEntity.toString())
            binding.apply {
                initToolbar()
                initView()
                toolbar.imageButton1.setOnClickListener {
                    editAction()
                }
            }
        }
    }

    private fun FragmentDetailBinding.editAction() {
        // Code block executed when in edit mode

        Log.d(TAG, "editAction: $isEdit")
        if (isEdit) {

            // Extract the text from the EditText widget for the device name
            val name = edittextNameValue.text.toString()

            // Call the ViewModel's updateDevice() method to update the device's name in the database
            viewModel.updateDevice(name, deviceEntity!!.id!!)

            // Disable edit mode
            isEdit = false
            edittextNameValue.isEnabled = false
            toolbar.imageButton1.setImageResource(R.drawable.baseline_mode_edit_outline_black_24dp)
        } else {
            // Enable edit mode
            toolbar.imageButton1.setImageResource(R.drawable.baseline_save_black_24dp)
            isEdit = true
            edittextNameValue.isEnabled = true

            // Request focus on the EditText
            edittextNameValue.requestFocus()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun FragmentDetailBinding.initView() {
        edittextNameValue.setText(deviceEntity!!.deviceName.toString())
        textViewIdValue.setText("SN: " + deviceEntity!!.pkDevice.toString())
        textViewMacValue.setText("Mac: " + deviceEntity!!.macAddress.toString())
        textViewFirmwareValue.setText("Firmware: " + deviceEntity!!.firmware.toString())
        textViewModelValue.setText("Model: " + deviceEntity!!.platform.toString())
        imageviewItemImage.setBackgroundResource(deviceEntity!!.platform.generatorImage())
    }

    private fun FragmentDetailBinding.initToolbar() {
        toolbar.imageButton1.setImageResource(R.drawable.baseline_mode_edit_outline_black_24dp)
        toolbar.imageButton1.show()
        toolbar.imageButton2.gone()
        edittextNameValue.isEnabled = false
        textviewProfileName.setText(NAME_1)
        imageviewProfileImage.setImageResource(IMAGE_1)
    }
}