package com.ferhatozcelik.ezlocasestudy.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ferhatozcelik.ezlocasestudy.data.entity.DeviceEntity
import com.ferhatozcelik.ezlocasestudy.databinding.DeviceItemBinding
import com.ferhatozcelik.ezlocasestudy.interfaces.ItemClickListener
import com.ferhatozcelik.ezlocasestudy.interfaces.ItemLongClickListener
import com.ferhatozcelik.ezlocasestudy.util.generatorImage

class DevicesAdapter(
    var list: List<DeviceEntity>,
    var itemClickListener: ItemClickListener,
    var itemLongClickListener: ItemLongClickListener
) : RecyclerView.Adapter<DevicesAdapter.HistoryViewHolder>() {
    private val TAG = DevicesAdapter::class.java.simpleName
    lateinit var itemGoalsPreviewBinding: DeviceItemBinding

    class HistoryViewHolder(binding: DeviceItemBinding) : RecyclerView.ViewHolder(binding.root) {
        var binding: DeviceItemBinding

        init {
            this.binding = binding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding = DeviceItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val item = list[position]

        itemGoalsPreviewBinding = holder.binding

        holder.binding.apply {
            textViewNameValue.text = item.deviceName.toString()
            textViewIdValue.text = "SN: ${item.pkDevice.toString()}"
            imageviewItemImage.setImageResource(item.platform.generatorImage())
        }

        holder.itemView.setOnClickListener {
            itemClickListener.onClick(item)
        }

        holder.itemView.setOnLongClickListener {
            itemLongClickListener.onClick(item, holder.itemView.rootView)
            return@setOnLongClickListener false
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<DeviceEntity>){
        this.list = list
        notifyDataSetChanged()
    }

}
