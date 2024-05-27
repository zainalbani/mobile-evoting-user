package com.zain.e_voting.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zain.e_voting.R
import com.zain.e_voting.data.response.DataItem
import com.zain.e_voting.databinding.ListKandidatBinding

class VotingAdapter(private var itemClick: ListPlaceInterface) :
    RecyclerView.Adapter<VotingAdapter.ViewHolder>() {
    private var selectedPosition = -1

    private val differCallback = object : DiffUtil.ItemCallback<DataItem>() {
        override fun areItemsTheSame(
            oldItem: DataItem,
            newItem: DataItem
        ): Boolean {
            return oldItem.paslonId == newItem.paslonId
        }

        override fun areContentsTheSame(
            oldItem: DataItem,
            newItem: DataItem
        ): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, differCallback)

    inner class ViewHolder(private val binding: ListKandidatBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(item: DataItem, position: Int) {

            binding.tvKetuaVoting.text = item.namaKetua
            binding.tvWakilVoting.text = item.namaWakilKetua
            Glide.with(itemView.context)
                .load(item.imageUrl)
                .into(binding.ivKandidatVoting)

            val backgroundDrawable = if (position == selectedPosition){
                R.drawable.custom_radio_selected
            } else{
                R.drawable.custom_radio_normal
            }
            binding.cardKandidatVoting.setBackgroundResource(backgroundDrawable)

            itemView.setOnClickListener{
                val previousSelectedPosition = selectedPosition
                selectedPosition = adapterPosition
                notifyItemChanged(previousSelectedPosition)
                notifyItemChanged(selectedPosition)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ListKandidatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VotingAdapter.ViewHolder, position: Int) {
        val result = differ.currentList[position]
        holder.bind(result, position)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun setData(data: List<DataItem?>) {
        differ.submitList(data)
    }
    fun getSelectedPosition(): Int {
        return selectedPosition
    }

    interface ListPlaceInterface {
        fun place(id: String)
    }
}