package com.navaplayer.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.navaplayer.databinding.FragmentSearchBinding
import com.navaplayer.viewModel.MusicViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var musicAdapter: MusicAdapter
    private val musicViewModel: MusicViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        // ✅ فقط یک بار Observer
        musicViewModel.songSearchResult.observe(viewLifecycleOwner) { songList ->
            Log.d("🟨NAVADEBUG", "✅ UI received: ${songList.size} items")
            songList?.let {
                musicAdapter.updateList(it)
            }
        }

        // 🎯 دکمه جستجو
        binding.searchIV.setOnClickListener {
            performSearch()

        }

        // 🎯 جستجو با Enter
        binding.searchEDT.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                performSearch()
                true
            } else {
                false
            }
        }
    }

    private fun setupRecyclerView() {
        musicAdapter = MusicAdapter(emptyList(), requireContext(),findNavController(),"search")
        binding.searchSongsRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = musicAdapter
        }
    }

    private fun performSearch() {
        val searchedText = binding.searchEDT.text.toString().trim()
        Log.d("🟨NAVADEBUG", "🔍 Search clicked: $searchedText")
        if (searchedText.isNotEmpty()) {
            musicViewModel.searchMusic(searchedText)
            binding.searchEDT.clearFocus()

            // بستن کیبورد
            val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(binding.searchEDT.windowToken, 0)
        }
    }
}
