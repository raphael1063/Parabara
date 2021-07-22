package com.example.parabara.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    private var currentId: Long = 0L

    fun loadData(id: Long) {
        currentId = id
        getProductDetail(id)
    }

    private fun getProductDetail(id: Long) {
        repository.getProductDetail(id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                response.onResult {
                    response.data?.let {
                        _productInfo.value = it
                    }
                }

            }, {

            }).addTo(compositeDisposable)
    }

}