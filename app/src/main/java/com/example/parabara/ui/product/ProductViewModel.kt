package com.example.parabara.ui.product

import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.parabara.Event
import com.example.parabara.R
import com.example.parabara.base.*
import com.example.parabara.data.Repository
import com.example.parabara.data.entities.ImageUploadResult
import com.example.parabara.data.entities.ProductApplyRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import okhttp3.MultipartBody
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(private val repository: Repository) : BaseViewModel() {

    private val _productImageList = MutableLiveData<List<ImageUploadResult>>()
    val productImageList: LiveData<List<ImageUploadResult>> = _productImageList

    val productTitle = MutableLiveData<String>()

    val productPrice = MutableLiveData<String>()

    val productContent = MutableLiveData<String>()

    private val imageList = mutableListOf<ImageUploadResult>()

    private val _actionImageChooserClicked = MutableLiveData<Event<Int>>()
    val actionImageChooserClicked: LiveData<Event<Int>> = _actionImageChooserClicked

    private val _finishActivity = MutableLiveData<Event<Int>>()
    val finishActivity: LiveData<Event<Int>> = _finishActivity

    private fun uploadImages(image: MultipartBody.Part) {
        repository.uploadImage(image)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                response.onResult {
                    response.data?.let { result ->
                        imageList.add(result)
                        _productImageList.value = imageList.map { it.copy() }
                    }
                }
            }, {
                Timber.d("Failed to Image upload : ${it.message}")
                showToast(R.string.image_upload_error_message)
            }).addTo(compositeDisposable)
    }

    fun removeImage(position: Int) {
        imageList.removeAt(position)
        _productImageList.value = imageList.map { it.copy() }
    }

    private fun applyProduct(productApplyRequest: ProductApplyRequest) {
        repository.applyProduct(productApplyRequest)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                response.onResult {
                    _finishActivity.value = Event(RESULT_OK)
                }
            }, {
                    _finishActivity.value = Event(RESULT_CANCELED)
            }).addTo(compositeDisposable)
    }

    //이미지 선택 버튼 클릭
    fun onImageChooserClicked() {
        if (imageList.size < 10) {
            _actionImageChooserClicked.value = Event(10 - imageList.size)
        }
    }

    //제품 등록 or 제품 수정 버튼 클릭
    fun onApplyButtonClicked() {
        var title = ""
        var price = 0L
        var content = ""
        var imageList = listOf<Long>()
        productTitle.value?.let {
            title = it
        } ?: run {
            showToast(R.string.no_title_message)
            return
        }
        productPrice.value?.let {
            price = it.toLong()
        } ?: run {
            showToast(R.string.no_price_message)
            return
        }
        productContent.value?.let {
            content = it
        } ?: run {
            showToast(R.string.no_content_message)
            return
        }
        productImageList.value?.let {
            imageList = this.imageList.map { it.id }
        }
        applyProduct(ProductApplyRequest(title, content, price, imageList))
    }

    fun uploadImage(multipartList: MutableList<MultipartBody.Part>) {
        if (imageList.size + multipartList.size <= 10) {
            for (i in multipartList.indices) {
                uploadImages(multipartList[i])
            }
        } else {
            for (i in 0 until 10 - imageList.size) {
                uploadImages(multipartList[i])
            }
        }
    }
}