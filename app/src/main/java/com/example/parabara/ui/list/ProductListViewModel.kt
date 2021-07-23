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
class ProductListViewModel @Inject constructor(private val repository: Repository) :
    BaseViewModel() {

    //총 아이템 갯수
    private val _totalRecords = MutableLiveData<Int>()
    val totalRecords: LiveData<Int> = _totalRecords

    private val _currentPage = MutableLiveData<Int>()
    val currentPage: LiveData<Int> = _currentPage

    private val _list = MutableLiveData<List<Row>?>()
    val list: LiveData<List<Row>?> = _list

    private val _actionProductItemClicked = MutableLiveData<Event<Long>>()
    val actionProductItemClicked: LiveData<Event<Long>> = _actionProductItemClicked

    private val _actionApplyButtonClicked = MutableLiveData<Event<Unit>>()
    val actionApplyButtonClicked: LiveData<Event<Unit>> = _actionApplyButtonClicked

    private val productList = mutableListOf<Row>()


    init {
        getList(isRefresh = false, isLoadMore = false, page = 1)
    }

    private fun getList(isRefresh: Boolean, isLoadMore: Boolean, page: Int) {
        repository.getProductList(page, size)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                if (!isRefresh) {
                    showLoading(true)
                }
            }
            .doFinally {
                if (isRefresh) {
                    hideRefreshing()
                } else {
                    showLoading(false)
                }
            }
            .subscribe({ response ->
                response.onResult {
                    response.data?.let { data ->
                        _totalRecords.value = data.records
                        _currentPage.value = data.page
                        if(isLoadMore) {
                            productList.addAll(data.rows)
                        } else {
                            productList.clear()
                            productList.addAll(data.rows)
                        }
                        _list.value = productList.map { it.copy() }
                    } ?: run {
                        showToast(R.string.empty_list_message)
                    }
                }
            }, {

            }).addTo(compositeDisposable)
    }

    fun refresh() {
        getList(isRefresh = true, isLoadMore = false, page = 1)
    }

    fun loadMore(page: Int) {
        getList(isRefresh = false, isLoadMore = true, page = page)
    }

    fun onProductItemClicked(id: Long) {
        _actionProductItemClicked.value = Event(id)
    }

    fun onApplyButtonClicked() {
        _actionApplyButtonClicked.value = Event(Unit)
    }

    companion object {
        private const val size = 30
    }
}