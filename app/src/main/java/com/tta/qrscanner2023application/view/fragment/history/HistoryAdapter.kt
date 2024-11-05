package com.tta.qrscanner2023application.view.fragment.history

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tta.qrscanner2023application.data.model.QrCodeEntity
import com.tta.qrscanner2023application.databinding.ItemHistoryBinding

class HistoryAdapter :
    RecyclerView.Adapter<HistoryAdapter.ItemTodayMealViewHolder>() {
    private var listHistory: List<QrCodeEntity> = listOf()
    private lateinit var context: Context

    fun setListHistory(listHistory: List<QrCodeEntity>, context: Context) {
        this.listHistory = listHistory
        this.context = context
        notifyDataSetChanged()
    }

    private var onClickDeleteData: ((i: Int) -> Unit)? = null
    fun deleteItem(position: ((i: Int) -> Unit)) {
        onClickDeleteData = position
    }

    private var onClickShowData: ((i: Int) -> Unit)? = null
    fun showInfo(position: ((i: Int) -> Unit)) {
        onClickShowData = position
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemTodayMealViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemHistoryBinding.inflate(inflater, parent, false)
        return ItemTodayMealViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemTodayMealViewHolder, position: Int) {
        var someThingToEat = listHistory[position]

        holder.binding.code.text = someThingToEat.code
        holder.binding.date.text = someThingToEat.createdTime.toString()
        holder.binding.imageView2.setOnClickListener {
            onClickDeleteData?.let {
                it(position)
            }
        }
        holder.binding.clItem.setOnClickListener {
            onClickShowData?.let {
                it(position)
            }
        }
    }

    override fun getItemCount(): Int {
        return listHistory.size
    }

    class ItemTodayMealViewHolder(
        val binding: ItemHistoryBinding
    ) : RecyclerView.ViewHolder(binding.root)
}