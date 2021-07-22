package com.example.parabara.ui.detail

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.parabara.Event
import com.example.parabara.base.BaseViewModel
import com.example.parabara.data.Repository
import com.example.parabara.data.entities.ProductDetailResult
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(private val repository: Repository) : BaseViewModel() {

    private val _productImageList = MutableLiveData<List<String>>()
    val productImageList: LiveData<List<String>> = _productImageList

    private val _productInfo = MutableLiveData<ProductDetailResult>()
    val productInfo: LiveData<ProductDetailResult> = _productInfo

    private val _actionEditButtonClicked = MutableLiveData<Event<ProductDetailResult>>()
    val actionEditButtonClicked: LiveData<Event<ProductDetailResult>> = _actionEditButtonClicked

    private val _actionRemoveButtonClicked = MutableLiveData<Event<Long>>()
    val actionRemoveButtonClicked: LiveData<Event<Long>> = _actionRemoveButtonClicked

    private var currentId: Long = 0L

    fun loadData(id: Long) {
        currentId = id
        getProductDetail(id)
    }

    private fun getProductDetail(id: Long) {
        repository.getProductDetail(id)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { showLoading(true) }
            .doFinally { showLoading(false) }
            .subscribe({ response ->
                response.onResult {
                    response.data?.let {
                        _productInfo.value = it
                        _productImageList.value = it.images
                    }
                }
            }, {

            }).addTo(compositeDisposable)
    }

    private fun removeProduct(id: Long) {
        repository.removeProduct(id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                response.onResult {
                    finishActivity(Activity.RESULT_OK)
                }
            }, {
                finishActivity(Activity.RESULT_CANCELED)
            }).addTo(compositeDisposable)
    }

    fun refresh() {
        getProductDetail(currentId)
    }

    fun onDeleteButtonClicked() {
        removeProduct(currentId)
    }

    fun onEditButtonClicked(productInfo: ProductDetailResult) {
        _actionEditButtonClicked.value = Event(productInfo)
    }

    fun onRemoveButtonClicked(id: Long) {
        _actionRemoveButtonClicked.value = Event(id)
    }

}