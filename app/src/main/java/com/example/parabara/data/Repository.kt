package com.example.parabara.data

import com.example.parabara.data.entities.*
import io.reactivex.Single
import okhttp3.MultipartBody

interface Repository {

    /**
     * 이미지 업로드
     */
    fun uploadImage(image: MultipartBody.Part): Single<ResponseData<ImageUploadResult>>

    /**
     * 상품 등록
     */
    fun applyProduct(model: ProductApplyRequest): Single<ResponseData<ProductApplyResult>>

    /**
     * 상품 업데이트
     */
    fun updateProduct(model: ProductUpdateRequest): Single<ResponseData<ProductUpdateResult>>

    /**
     * 상품 삭제
     */
    fun removeProduct(id: Long): Single<ResponseData<Boolean>>

    /**
     * 상품 리스트 조회
     */
    fun getProductList(page: Int, size: Int): Single<ResponseData<ProductListResult>>

    /**
     * 상품 상세 조회
     */
    fun getProductDetail(id: Long): Single<ResponseData<ProductDetailResult>>

}