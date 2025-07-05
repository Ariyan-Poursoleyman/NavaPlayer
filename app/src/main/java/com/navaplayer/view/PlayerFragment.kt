package com.navaplayer.view

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.navaplayer.R
import com.navaplayer.databinding.FragmentPlayerBinding
import com.navaplayer.viewModel.FavoriteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlayerFragment : Fragment() {
    private var _binding: FragmentPlayerBinding? = null
    private val binding get() = _binding!!
    private val args: PlayerFragmentArgs by navArgs()
    private var player: ExoPlayer? = null
    private val viewModel: FavoriteViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlayerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val song = args.song
        binding.song = song

        Glide.with(requireContext())
            .load(song.mp3_thumbnail_b)
            .into(binding.songImageView)

        // ساخت پلیر
        player = ExoPlayer.Builder(requireContext()).build().also { exoPlayer ->
            val mediaItem = MediaItem.fromUri(song.mp3_url)
            exoPlayer.setMediaItem(mediaItem)
            exoPlayer.prepare()
            exoPlayer.play()

            // تنظیم کنترل‌ویو
            val controlView = binding.playerControlView  // یا findViewById
            controlView.player = exoPlayer
        }

        // دکمه بازگشت
        binding.backIV.setOnClickListener {
            exitToMainFrag()
        }

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    exitToMainFrag()
                }
            })

        // دانلود آهنگ
        binding.downloadIV.setOnClickListener {
            val url = song.mp3_url
            val fileName = "${song.mp3_title}-${song.mp3_artist}.mp3"

            val request = DownloadManager.Request(Uri.parse(url)).apply {
                setTitle("در حال دانلود آهنگ از Nava Player")
                setDescription(fileName)
                setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)
                setAllowedOverMetered(true)
                setAllowedOverRoaming(true)
            }

            val downloadManager =
                requireContext().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            downloadManager.enqueue(request)

            Toast.makeText(requireContext(), "دانلود آغاز شد 🎵", Toast.LENGTH_SHORT).show()
        }


        viewModel.checkIsFavorite(song.id)
        viewModel.isFavorite.observe(viewLifecycleOwner) { isFav ->
            if (isFav) {
                binding.favoriteIV.setImageResource(R.drawable.baseline_favorite_24)
            } else {
                binding.favoriteIV.setImageResource(R.drawable.baseline_favorite_border_24)
            }
        }


        binding.favoriteIV.setOnClickListener {
            if (viewModel.isFavorite.value == true) {
                viewModel.removeFromFavorite(song)
                findNavController().previousBackStackEntry
                    ?.savedStateHandle
                    ?.set("favorites_updated", true)
            } else {
                viewModel.addToFavorite(song)
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        player?.release()
        player = null
        _binding = null
    }

    private fun exitToMainFrag() {
        player?.release()
        player = null
        findNavController().navigateUp()
    }
}
