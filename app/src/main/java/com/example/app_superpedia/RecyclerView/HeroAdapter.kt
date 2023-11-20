package com.example.app_superpedia.RecyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.app_superpedia.API.SuperheroItemResponse
import com.example.app_superpedia.R

//on Item Selected es una funcion Lambda

class HeroAdapter(
    var heroList: List<SuperheroItemResponse> = emptyList(),private val onItemSelected: (String) -> Unit
) : RecyclerView.Adapter<HeroViewHolder>() {

    fun updateList(list: List<SuperheroItemResponse>) {
        heroList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeroViewHolder {
        return HeroViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_hero, parent, false))
    }

    override fun onBindViewHolder(viewholder: HeroViewHolder, position: Int) {
        viewholder.bind(heroList[position],onItemSelected)
    //
    }

    override fun getItemCount() = heroList.size


}