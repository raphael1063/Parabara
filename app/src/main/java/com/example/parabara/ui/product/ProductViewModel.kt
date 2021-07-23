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
import com.example.parabara.data.entities.ProductDetailResult
import com.example.parabara.data.entities.ProductUpdateRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import okhttp3.MultipartBody
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(private val repository: Repository) : BaseViewModel() {

    //상품 이미지 리스트
    private val _productImageList = MutableLiveData<List<String>>()
    val productImageList: LiveData<List<String>> = _productImageList

    private var imageList = mutableListOf<ImageUploadResult>()

    //상품명
    val productTitle = MutableLiveData<String>()

    //판매가격
    val productPrice = MutableLiveData<String>()

    //상품 설명
    val productContent = MutableLiveData<String>()

    //이미지 선택버튼 클릭
    private val _actionImageChooserClicked = MutableLiveData<Event<Int>>()
    val actionImageChooserClicked: LiveData<Event<Int>> = _actionImageChooserClicked

    //모드(Apply: 상품 등록, Edit: 상품 수정)
    private val _mode = MutableLiveData(Mode.APPLY)
    val mode: LiveData<Mode> = _mode

    //현재 상품 ID (상품 수정 시에만 사용)
    private var currentId = -1L

    //초기 데이터 호출
    fun loadData(productInfo: ProductDetailResult) {
        productInfo.also {
            currentId = it.id
            productTitle.value = it.title
            productPrice.value = it.price.toString()
            productContent.value = it.content
            _productImageList.value = it.images
        }
        _mode.value = Mode.EDIT
    }

    //이미지 업로드 API
    private fun uploadImages(image: MultipartBody.Part) {
        repository.uploadImage(image)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                response.onResult {
                    response.data?.let { result ->
                        imageList.add(result)
                        _productImageList.value = imageList.map { it.copy().url }
                    }
                }
            }, {
                Timber.d("Failed to Image upload : ${it.message}")
                showToast(R.string.image_upload_error_message)
            }).addTo(compositeDisposable)
    }

    //상품 업데이트 API
    private fun updateProduct(productUpdateRequest: ProductUpdateRequest) {
        repository.updateProduct(productUpdateRequest)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { showLoading(true) }
            .doFinally { showLoading(false) }
            .subscribe({ response ->
                response.onResult {
                    showToast(R.string.product_update_success_message)
                    finishActivity(RESULT_OK)
                }
            }, {
                finishActivity(RESULT_CANCELED)
            }).addTo(compositeDisposable)
    }

    //상품 등록 API
    private fun applyProduct(productApplyRequest: ProductApplyRequest) {
        repository.applyProduct(productApplyRequest)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { showLoading(true) }
            .doFinally { showLoading(false) }
            .subscribe({ response ->
                response.onResult {
                    showToast(R.string.product_apply_success_message)
                    finishActivity(RESULT_OK)
                }
            }, {
                finishActivity(RESULT_CANCELED)
            }).addTo(compositeDisposable)
    }

    //이미지 삭제
    fun removeImage(position: Int) {
        imageList.removeAt(position)
        _productImageList.value = imageList.map { it.copy().url }
    }

    //이미지 선택 버튼 클릭
    fun onImageChooserClicked() {
        if (imageList.size < 10) {
            _actionImageChooserClicked.value = Event(10 - imageList.size)
        }
    }

    //제품 등록 or 제품 수정 버튼 클릭
    fun onApplyEditButtonClicked() {
        var title = ""
        var price = 0L
        var content = ""
        var imageList = listOf<Long>()
        if(!productTitle.value?.trim().isNullOrEmpty()) {
            title = productTitle.value!!
        } else {
            showToast(R.string.no_title_message)
            return
        }
        if(!productPrice.value?.trim().isNullOrEmpty()) {
            price = productPrice.value!!.toLong()
        } else {
            showToast(R.string.no_price_message)
            return
        }
        if(!productContent.value?.trim().isNullOrEmpty()) {
            content = productContent.value!!
        } else {
            showToast(R.string.no_content_message)
            return
        }
        productImageList.value?.let {
            imageList = this.imageList.map { it.id }
        }
        if (_mode.value == Mode.APPLY) {
            applyProduct(ProductApplyRequest(title, content, price, imageList))
        } else {
            updateProduct(ProductUpdateRequest(currentId, title, content, price))
        }
    }

    //이미지 업로드
    fun uploadImage(multipartList: MutableList<MultipartBody.Part>) {
        if (imageList.size + multipartList.size <= 10) {
            for (i in multipartList.indices) {
                uploadImages(multipartList[i])
            }
        } else {
            for (i in 0 until 10 - imageList.size) {
                showToast(R.string.image_upload_limit_message)
                uploadImages(multipartList[i])
            }
        }
    }

    enum class Mode {
        APPLY, EDIT
    }
}