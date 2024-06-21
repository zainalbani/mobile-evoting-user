package com.zain.e_voting.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zain.e_voting.data.response.DataItem
import com.zain.e_voting.databinding.ListKandidatBinding

class KandidatAdapter(private var itemClick: ListKandidatInterface) :
    RecyclerView.Adapter<KandidatAdapter.ViewHolder>() {

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

            itemView.setOnClickListener {
                itemClick.kandidat(item.paslonId.toString())

            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ListKandidatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: KandidatAdapter.ViewHolder, position: Int) {
        val result = differ.currentList[position]
        holder.bind(result, position)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun setData(data: List<DataItem?>) {
        differ.submitList(data)
    }

    interface ListKandidatInterface {
        fun kandidat(id: String)
    }

}