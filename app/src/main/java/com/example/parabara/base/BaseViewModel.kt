package com.example.parabara.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.parabara.Event
import com.example.parabara.data.entities.ResponseData
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

abstract class BaseViewModel : ViewModel() {

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _hideRefreshing = MutableLiveData<Unit>()
    val hideRefreshing: LiveData<Unit> = _hideRefreshing

    private val _showServerErrorMessage = MutableLiveData<Event<Int>>()
    val showServerErrorMessage: LiveData<Event<Int>> = _showServerErrorMessage

    private val _showToast = MutableLiveData<Event<String>>()
    val showToast: LiveData<Event<String>> = _showToast

    private val _showToastInt = MutableLiveData<Event<Int>>()
    val showToastInt: LiveData<Event<Int>> = _showToastInt

    private val _finishActivity = MutableLiveData<Event<Int>>()
    val finishActivity: LiveData<Event<Int>> = _finishActivity


    protected val compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        compositeDisposable.dispose()
    }

    fun showLoading(isLoading: Boolean) {
        _loading.value = isLoading
    }

    fun hideRefreshing() {
        _hideRefreshing.value = Unit
    }

    fun showToast(message: String) {
        _showToast.value = Event(message)
    }

    fun showToast(message: Int) {
        _showToastInt.value = Event(message)
    }

    fun finishActivity(result: Int) {
        _finishActivity.value = Event(result)
    }

    fun <T> ResponseData<T>.onResult(doOnSuccess: () -> Unit) {
        when (this.status) {
            STATUS_SUCCESS -> doOnSuccess()
            STATUS_INVALID -> Timber.d("STATUS_INVALID : ${this.status}")
            STATUS_SERVER_ERROR -> Timber.d("STATUS_SERVER_ERROR : ${this.status}")
        }
        showToast(this.message)
    }
}