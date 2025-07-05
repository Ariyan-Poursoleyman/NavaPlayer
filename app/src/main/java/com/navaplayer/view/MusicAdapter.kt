package com.navaplayer.view

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.navaplayer.R
import com.navaplayer.databinding.ItemMusicBinding
import com.navaplayer.model.ONLINEMP3

class MusicAdapter(
    private var musicList: List<ONLINEMP3> = listOf(),
    private var context: Context,
    private val navController: NavController,
    private var currentFragment: String
) : RecyclerView.Adapter<MusicAdapter.MusicViewHolder>() {

    inner class MusicViewHolder(val binding: ItemMusicBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicViewHolder {
        val binding = ItemMusicBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MusicViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MusicViewHolder, position: Int) {
        val song = musicList[position]
        Log.d("🟨NAVADEBUG", "✅ Binding song: ${song.mp3_title}")
        holder.binding.song = song
        holder.binding.executePendingBindings()

        holder.binding.root.setOnClickListener {
            when (currentFragment) {
                "home" -> {
                    val action = HomeFragmentDirections
                        .actionHomeFragmentToPlayerFragment(song)
                    navController.navigate(action)
                }
                "search" -> {
                    val action = SearchFragmentDirections
                        .actionSearchFragmentToPlayerFragment(song)
                    navController.navigate(action)
                }
                "favorite" ->{
                    val action = FavoriteFragmentDirections.actionFavoriteFragmentToPlayerFragment(song)
                    navController.navigate(action)
                }
            }
        }


    }

    override fun getItemCount(): Int = musicList.size

    fun updateList(newList: List<ONLINEMP3>) {
        this.musicList = newList
        notifyDataSetChanged()
    }
}
