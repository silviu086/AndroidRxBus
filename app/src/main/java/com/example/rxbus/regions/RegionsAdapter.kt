package com.example.rxbus.regions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rxbus.R
import com.example.rxbus.reactivex.RxBus
import com.example.rxbus.reactivex.RxEvent
import kotlinx.android.synthetic.main.regions_list_item.view.*

class RegionsAdapter constructor(var regionsArray: MutableList<String>) :
    RecyclerView.Adapter<RegionsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.regions_list_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return regionsArray.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val region = regionsArray[position]
        holder.regionName.text = region

        if (isLastItemReached(position)) {
            RxBus.publish(RxEvent.EventBottomOfRegionsListReached(position))
        }
    }

    fun addItems(items: MutableList<String>) {
        regionsArray.addAll(items)
        notifyDataSetChanged()
    }

    private fun isLastItemReached(position: Int) = itemCount - 1 == position

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val regionName = view.regionTv
    }
}