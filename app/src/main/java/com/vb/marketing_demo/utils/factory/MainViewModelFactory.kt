package com.vb.marketing_demo.utils.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vb.marketing_demo.repository.MainRepository


class MainViewModelFactory(private val repo: MainRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(repo::class.java).newInstance(repo)
    }

}