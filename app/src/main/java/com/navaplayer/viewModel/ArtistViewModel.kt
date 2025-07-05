package com.navaplayer.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.navaplayer.model.Artist
import com.navaplayer.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel

class ArtistViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _hotArtists = MutableLiveData<Artist>()
    val hotArtists: LiveData<Artist> get() = _hotArtists

    fun fetchHotArtists() {
        viewModelScope.launch {
            try {
                val result = repository.getHotArtists()
                _hotArtists.value = result
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

