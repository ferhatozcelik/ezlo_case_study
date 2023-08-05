package com.ferhatozcelik.ezlocasestudy.ui.fragments.home

import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.ContextMenu.ContextMenuInfo
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.ferhatozcelik.ezlocasestudy.R
import com.ferhatozcelik.ezlocasestudy.data.entity.DeviceEntity
import com.ferhatozcelik.ezlocasestudy.data.model.DeviceModel
import com.ferhatozcelik.ezlocasestudy.data.model.Resource
import com.ferhatozcelik.ezlocasestudy.databinding.FragmentHomeBinding
import com.ferhatozcelik.ezlocasestudy.interfaces.ItemClickListener
import com.ferhatozcelik.ezlocasestudy.interfaces.ItemLongClickListener
import com.ferhatozcelik.ezlocasestudy.ui.adapters.DevicesAdapter
import com.ferhatozcelik.ezlocasestudy.ui.base.BaseFragment
import com.ferhatozcelik.ezlocasestudy.util.IMAGE_1
import com.ferhatozcelik.ezlocasestudy.util.NAME_1
import com.ferhatozcelik.ezlocasestudy.util.NetworkUtil
import com.ferhatozcelik.ezlocasestudy.util.ProgressDialog
import com.ferhatozcelik.ezlocasestudy.util.show
import com.ferhatozcelik.ezlocasestudy.util.toast
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private val TAG = HomeFragment::class.java.simpleName

    private val viewModel: HomeViewModel by viewModels()

    private lateinit var progressDialog: ProgressDialog
    private lateinit var adapter: DevicesAdapter

    private var tempDeleteItem: DeviceEntity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Check if there is an internet connection on the device
        if (NetworkUtil.hasInternetConnection(requireContext())) {
            Log.e(TAG, "Internet Connected")
            // Call the 'getDeviceList()' method in the ViewModel to fetch the list of devices
            viewModel.getDeviceList()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Create a ProgressDialog using the activity context, if available
        progressDialog = activity?.let { ProgressDialog(it) }!!

        // Create an instance of DevicesAdapter with an empty list and a historyItemClickListener
        adapter = DevicesAdapter(emptyList(), deviceItemClickListener, deviceLongItemClickListener)

        // Call 'getLocalList()' method on the ViewModel to fetch a local list of devices
        viewModel.getLocalList()

        // Call initialization methods for Toolbar, User, and Device List
        initToolbar()
        initUser()
        deviceList()
    }

    private fun initToolbar() {
        binding.apply {
            recyclerviewDeviceList.adapter = adapter
            toolbar.imageButton1.setImageResource(R.drawable.baseline_clear_all_black_24dp)
            toolbar.imageButton2.setImageResource(R.drawable.baseline_autorenew_black_24dp)

            toolbar.imageButton1.show()
            toolbar.imageButton2.show()

            toolbar.imageButton1.setOnClickListener {
                // Method to clear the list of devices
                viewModel.clearList()
            }
            toolbar.imageButton2.setOnClickListener {
                // Method to fetch the list of devices from the api
                viewModel.getDeviceList()
            }
        }
    }

    private fun initUser() {
        binding.apply {
            textviewProfileName.text = NAME_1
            cardviewProfileImage.setBackgroundResource(IMAGE_1)
        }
    }

    private fun deviceList() {
        Log.d(TAG, "Called Device List")
        viewModel.deviceList.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Resource.Success<List<DeviceEntity>> -> {
                    progressDialog.cancelDialog(true)
                    adapter.setData(result.data ?: emptyList())
                    Log.d(TAG, "Device List Success")
                }

                is Resource.Error -> {
                    Log.d(TAG, "Device List Error: " + result.message)
                    context?.toast(result.message.toString())
                    progressDialog.cancelDialog(true)
                }

                is Resource.Loading -> {
                    Log.d(TAG, "Device List Loading")
                    progressDialog.createProgressDialog()
                }
            }
        }
    }

    private val deviceItemClickListener = object : ItemClickListener {
        override fun onClick(objects: Any?) {
            val item = objects as DeviceEntity
            val bundle = Bundle()
            bundle.putParcelable("item", item)
            activity?.findNavController(R.id.nav_host_fragment)?.navigate(R.id.action_homeFragment_to_myDeviceFragment, bundle)
        }
    }

    private val deviceLongItemClickListener = object : ItemLongClickListener {
        override fun onClick(objects: Any?, view: View?) {
            val item = objects as DeviceEntity
            tempDeleteItem = item
            if (view != null) {
                registerForContextMenu(view)
            }
        }
    }

    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menu.add(0, v.id, 0, "Delete")
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        if (item.title === "Delete") {
            val alertDialog: AlertDialog.Builder = context?.let { AlertDialog.Builder(it) }!!
            alertDialog.setTitle("Are you sure about deleting?")
            alertDialog.setPositiveButton("Delete") { _, i -> //Delete
                tempDeleteItem?.id?.let { viewModel.deleteByDevice(it) }
            }
            alertDialog.setNeutralButton("Cancel") { _, i ->

            }
            val alertBuilder = alertDialog.create()
            alertBuilder.show()
        }
        return true
    }
}