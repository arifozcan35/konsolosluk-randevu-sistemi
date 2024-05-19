package com.arifozcan.consulateapplication

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arifozcan.consulateapplication.databinding.RecyclerRowBinding

class VisaAdapter(val visaList: ArrayList<Visa>) : RecyclerView.Adapter<VisaAdapter.VisaHolder>() {
    class VisaHolder(val binding : RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VisaHolder {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VisaHolder(binding)
    }

    override fun getItemCount(): Int {
        return visaList.size
    }

    override fun onBindViewHolder(holder: VisaHolder, position: Int) {
        holder.binding.recyclerViewTextView.text = visaList.get(position).name
        holder.itemView.setOnClickListener{
            val intent = Intent(holder.itemView.context, VizeBasvurusu::class.java)
            intent.putExtra("info", "old")
            intent.putExtra("id", visaList.get(position).id)
            holder.itemView.context.startActivity(intent)
        }
    }
}