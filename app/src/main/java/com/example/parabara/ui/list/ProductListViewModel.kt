package com.example.parabara.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.parabara.Event
import com.example.parabara.R
import com.example.parabara.base.*
import com.example.parabara.data.Repository
import com.example.parabara.data.entities.Row
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(private val repository: Repository) : BaseViewModel() {

    private val _list = MutableLiveData<List<Row>?>()
    val list: LiveData<List<Row>?> = _list

    private val _actionApplyButtonClicked = MutableLiveData<Event<Unit>>()
    val actionApplyButtonClicked: LiveData<Event<Unit>> = _actionApplyButtonClicked

    init {
        getList()
    }

    private fun getList() {
        repository.getProductList(page, size)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                response.onResult {
                    response.data?.let {
                        _list.value = it.rows
                    } ?: run {
                        showToast(R.string.empty_list_message)
                    }
                }
            }, {

            }).addTo(compositeDisposable)
    }

    fun refresh() {
        getList()
    }

    fun onApplyButtonClicked() {
        _actionApplyButtonClicked.value = Event(Unit)
    }

    companion object {
        private const val page = 1
        private const val size = 30
    }
}