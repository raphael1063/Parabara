package com.example.parabara.ui.list

import com.example.parabara.base.BaseViewModel
import com.example.parabara.data.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(repository: Repository) : BaseViewModel() {

}