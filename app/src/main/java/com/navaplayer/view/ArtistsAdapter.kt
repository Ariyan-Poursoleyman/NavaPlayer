package com.navaplayer.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.navaplayer.databinding.ItemArtistBinding
import com.navaplayer.model.ONLINEMP3X

class ArtistsAdapter(
    private val artists: List<ONLINEMP3X>,
    context: Context
) : RecyclerView.Adapter<ArtistsAdapter.ArtistsViewHolder>() {

    class ArtistsViewHolder(private val binding: ItemArtistBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(artist: ONLINEMP3X) {
            binding.artist = artist
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemArtistBinding.inflate(inflater, parent, false)
        return ArtistsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArtistsViewHolder, position: Int) {
        holder.bind(artists[position])
    }

    override fun getItemCount(): Int = artists.size
}
