package com.navaplayer.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.navaplayer.databinding.FragmentHomeBinding
import com.navaplayer.model.ONLINEMP3X
import com.navaplayer.viewModel.MusicViewModel
import com.navaplayer.viewModel.ArtistViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val artistViewModel: ArtistViewModel by viewModels()
    private val musicViewModel: MusicViewModel by viewModels()

    private lateinit var artistAdapter: ArtistsAdapter
    private lateinit var musicAdapter: MusicAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        setupMusicRecycler()
        observeMusic()
        musicViewModel.fetchLatestSongs()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupArtistRecycler()
        observeArtists()
        artistViewModel.fetchHotArtists()
    }

    private fun setupArtistRecycler() {
        artistAdapter = ArtistsAdapter(emptyList(), requireContext())
        binding.artistRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = artistAdapter
        }
    }

    private fun observeArtists() {
        artistViewModel.hotArtists.observe(viewLifecycleOwner) { artistResponse ->
            val artistsList: List<ONLINEMP3X> = artistResponse.ONLINE_MP3
            if (artistsList.isNotEmpty()) {
                artistAdapter = ArtistsAdapter(artistsList, requireContext())
                binding.artistRecyclerView.adapter = artistAdapter
            }
        }
    }

    private fun setupMusicRecycler() {
        musicAdapter = MusicAdapter(emptyList(), requireContext(),findNavController(),"home")
        binding.latestSongsRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = musicAdapter
        }
    }

    private fun observeMusic() {
        musicViewModel.songs.observe(viewLifecycleOwner) { songs ->
            if (songs.isNotEmpty()) {
                musicAdapter = MusicAdapter(songs, requireContext(),findNavController(),"home")
                binding.latestSongsRecyclerView.adapter = musicAdapter
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
