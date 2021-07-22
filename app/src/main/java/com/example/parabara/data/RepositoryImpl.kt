package com.example.parabara.data

import com.example.parabara.data.api.ApiService
import com.example.parabara.data.entities.*
import io.reactivex.Single
import okhttp3.MultipartBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryImpl @Inject constructor(private val apiService: ApiService) : Repository {

    /**
     * 이미지 업로드
     */
    override fun uploadImage(image: MultipartBody.Part): Single<ResponseData<ImageUploadResult>> =
        apiService.uploadImage(image)

    /**
     * 상품 등록
     */
    override fun applyProduct(model: ProductApplyRequest): Single<ResponseData<ProductApplyResult>> =
        apiService.applyProduct(model.title, model.price, model.content, model.images)

    /**
     * 상품 업데이트
     */
    override fun updateProduct(model: ProductUpdateRequest): Single<ResponseData<ProductUpdateResult>> =
        apiService.updateProduct(model.id, model.title, model.price, model.content)

    /**
     * 상품 삭제
     */
    override fun removeProduct(id: Long): Single<ResponseData<ProductRemoveResult>> =
        apiService.removeProduct(id)

    /**
     * 상품 리스트 조회
     */
    override fun getProductList(page: Int, size: Int): Single<ResponseData<ProductListResult>> =
        apiService.getProductList(page, size)

    /**
     * 상품 상세 조회
     */
    override fun getProductDetail(id: Long): Single<ResponseData<ProductDetailResult>> =
        apiService.getProductDetail(id)
}