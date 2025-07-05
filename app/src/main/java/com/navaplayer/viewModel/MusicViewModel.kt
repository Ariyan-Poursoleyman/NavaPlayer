package com.navaplayer.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.navaplayer.model.ONLINEMP3
import com.navaplayer.model.ONLINEMP3X
import com.navaplayer.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MusicViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    private val _songs = MutableLiveData<List<ONLINEMP3>>()
    val songs: LiveData<List<ONLINEMP3>> = _songs
    private val _artists = MutableLiveData<List<ONLINEMP3X>>()
    val artists: LiveData<List<ONLINEMP3X>> = _artists
    private val _songSearchResult = MutableLiveData<List<ONLINEMP3>>()
    val songSearchResult: LiveData<List<ONLINEMP3>> get() = _songSearchResult

    fun fetchLatestSongs() {
        _isLoading.value = true
        _error.value = null

        viewModelScope.launch {
            try {
                val music = repository.getLatestMusic()
                _songs.value = music.ONLINE_MP3
            } catch (e: Exception) {
                _error.value = "Something went wrong : ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun searchMusic(text: String) {
        _error.value = null
        viewModelScope.launch {
            try {
                val result = repository.searchSongs(text)
                _songSearchResult.value = result.ONLINE_MP3
            } catch (e: Exception) {
                _error.value = "Something went wrong : ${e.message}"
            }
        }
    }
}
