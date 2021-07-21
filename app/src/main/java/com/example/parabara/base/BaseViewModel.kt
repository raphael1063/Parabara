package com.example.parabara.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.parabara.Event
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {

    private val _showServerErrorMessage = MutableLiveData<Event<Int>>()
    val showServerErrorMessage: LiveData<Event<Int>> = _showServerErrorMessage

    private val _showToast = MutableLiveData<Event<String>>()
    val showToast: LiveData<Event<String>> = _showToast

    private val _showToastInt = MutableLiveData<Event<Int>>()
    val showToastInt: LiveData<Event<Int>> = _showToastInt

    protected val compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        compositeDisposable.dispose()
    }

    private fun showServerErrorMessage() {

    }

    fun showToast(message: String) {
        _showToast.value = Event(message)
    }

    fun showToast(message: Int) {
        _showToastInt.value = Event(message)
    }
}