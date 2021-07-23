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

    //상풍 상세 호출 API
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

    //상품 삭제 API
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

    //새로고침
    fun refresh() {
        getProductDetail(currentId)
    }

    //삭제 버튼 클릭(다이얼로그)
    fun onDialogRemoveButtonClicked() {
        removeProduct(currentId)
    }

    //수정 버튼 클릭
    fun onEditButtonClicked(productInfo: ProductDetailResult) {
        _actionEditButtonClicked.value = Event(productInfo)
    }

    //삭제 버튼 클릭(상품 상세 화면)
    fun onRemoveButtonClicked(id: Long) {
        _actionRemoveButtonClicked.value = Event(id)
    }

}