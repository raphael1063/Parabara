package com.example.parabara.ui.apply

import com.example.parabara.base.BaseViewModel
import com.example.parabara.data.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductApplyViewModel @Inject constructor(private val repository: Repository): BaseViewModel() {

}