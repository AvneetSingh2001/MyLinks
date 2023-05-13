package com.avicodes.mylinks.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.avicodes.mylinks.domain.repository.LinkRepository

class MainActivityViewModelFactory(
    private val linkRepository: LinkRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainActivityViewModel(
            linkRepository = linkRepository
        ) as T
    }
}