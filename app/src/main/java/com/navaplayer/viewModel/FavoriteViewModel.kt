package com.navaplayer.viewModel

import androidx.lifecycle.*
import com.navaplayer.model.ONLINEMP3
import com.navaplayer.repository.FavoriteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val repository: FavoriteRepository
) : ViewModel() {

    private val _allFavorites = MutableLiveData<List<ONLINEMP3>>()
    val allFavorites: LiveData<List<ONLINEMP3>> get() = _allFavorites

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> get() = _isFavorite

    fun loadFavorites() {
        viewModelScope.launch {
            _allFavorites.value = repository.getAllFavorites()
        }
    }

    fun checkIsFavorite(songID: String) {
        viewModelScope.launch {
            _isFavorite.value = repository.isFavorite(songID)
        }
    }

    fun addToFavorite(song: ONLINEMP3) {
        viewModelScope.launch {
            repository.addToFavorite(song)
            checkIsFavorite(song.id)
        }
    }

    fun removeFromFavorite(song: ONLINEMP3) {
        viewModelScope.launch {
            repository.removeFromFavorite(song)
            checkIsFavorite(song.id)
        }
    }
}
