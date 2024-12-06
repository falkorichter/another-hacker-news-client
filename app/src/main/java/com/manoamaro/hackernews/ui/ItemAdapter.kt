package com.manoamaro.hackernews.ui

import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.manoamaro.hackernews.databinding.CardItemBinding
import com.manoamaro.hackernews.db.entity.Item
import java.time.ZoneId

class ItemAdapter(private val onClickListener: (Item?) -> Unit): PagedListAdapter<Item, ItemViewHolder>(ITEM_DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(CardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bindTo(getItem(position), onClickListener)
    }
}

class ItemViewHolder(val binding: CardItemBinding): RecyclerView.ViewHolder(binding.root) {
    fun bindTo(item: Item?, onClickListener: (Item?) -> Unit) {
        binding.cardItemTitle.text = item?.title
        binding.cardItemComments.text = item?.descendants?.toString()
        binding.cardItemScore.text = item?.score?.toString()
        item?.dateTime?.let { dateTime ->
            ZoneId.systemDefault().id
            val instant = dateTime.atZone(ZoneId.systemDefault()).toInstant()
            binding.cardItemDatetime.text = DateUtils.getRelativeTimeSpanString(instant.toEpochMilli())
        }
        binding.cardItemTitle.setOnClickListener { onClickListener(item) }
    }
}

val ITEM_DIFF_CALLBACK = object: DiffUtil.ItemCallback<Item>() {
    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean = oldItem == newItem
}