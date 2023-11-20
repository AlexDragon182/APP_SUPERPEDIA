package com.example.app_superpedia.RecyclerView

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.app_superpedia.API.SuperheroItemResponse
import com.example.app_superpedia.databinding.ItemHeroBinding
import com.squareup.picasso.Picasso


class HeroViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemHeroBinding.bind(view)

    fun bind(superheroItemResponse: SuperheroItemResponse, onItemSelected: (String) -> Unit) {
        binding.tvheroName.text = superheroItemResponse.name
        Picasso.get().load(superheroItemResponse.superheroImage.url).into(binding.ivhero)
        binding.root.setOnClickListener { onItemSelected(superheroItemResponse.superheroId) }
    }

}