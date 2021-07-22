/*상품상세조회 결과 모델*/
package com.example.parabara.data.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductDetailResult(

    /**
     * 상품 아이디
     */
    val id: Long,

    /**
     * 상품 제목
     */
    val title: String,

    /**
     * 상품 내용
     */
    val content: String,

    /**
     * 상품 가격
     */
    val price: Int,

    /**
     * 상품 이미지 목록
     */
    val images: List<String>
): Parcelable