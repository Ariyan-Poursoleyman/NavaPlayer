package com.navaplayer.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.navaplayer.databinding.FragmentFavoriteBinding
import com.navaplayer.viewModel.FavoriteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var musicAdapter: MusicAdapter
    private val favoriteViewModel: FavoriteViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        musicAdapter = MusicAdapter(emptyList(), requireContext(), findNavController(), "favorite")
        binding.recyclerFavorite.adapter = musicAdapter
        binding.recyclerFavorite.layoutManager = LinearLayoutManager(requireContext())

        favoriteViewModel.allFavorites.observe(viewLifecycleOwner) { musicList ->
            musicAdapter.updateList(musicList)
        }

        val navBackStackEntry = findNavController().currentBackStackEntry
        navBackStackEntry?.savedStateHandle?.getLiveData<Boolean>("favorites_updated")
            ?.observe(viewLifecycleOwner) { updated ->
                if (updated == true) {
                    favoriteViewModel.loadFavorites()
                    navBackStackEntry.savedStateHandle.set("favorites_updated", false)
                }
            }
    }


    override fun onResume() {
        super.onResume()
        favoriteViewModel.loadFavorites()
    }
}
