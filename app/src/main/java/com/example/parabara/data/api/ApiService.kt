package com.example.parabara.data.api

import com.example.parabara.data.entities.*
import io.reactivex.Single
import okhttp3.MultipartBody
import retrofit2.http.*

interface ApiService {

    /**
     * 이미지 업로드
     */
    @Multipart
    @POST("/api/product/upload")
    fun uploadImage(@Part image: MultipartBody.Part): Single<ResponseData<ImageUploadResult>>

    /**
     * 상품 등록
     */
    @FormUrlEncoded
    @POST("/api/product")
    fun applyProduct(
        @Field("title") title: String,
        @Field("price") price: Long,
        @Field("content") content: String,
        @Field("images") images: List<Long>?
    ): Single<ResponseData<ProductApplyResult>>

    /**
     * 상품 업데이트
     */
    @FormUrlEncoded
    @PUT("/api/product")
    fun updateProduct(
        @Field("id") id: Long,
        @Field("title") title: String,
        @Field("price") price: Long,
        @Field("content") content: String
    ): Single<ResponseData<ProductUpdateResult>>

    /**
     * 상품 삭제
     */
    @DELETE("/api/product/{id}")
    fun removeProduct(@Path("id") id: Long): Single<ResponseData<Boolean>>

    /**
     * 상품 리스트 조회
     */
    @GET("/api/product")
    fun getProductList(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Single<ResponseData<ProductListResult>>

    /**
     * 상품 상세 조회
     */
    @GET("/api/product/{id}")
    fun getProductDetail(@Path("id") id: Long): Single<ResponseData<ProductDetailResult>>
}