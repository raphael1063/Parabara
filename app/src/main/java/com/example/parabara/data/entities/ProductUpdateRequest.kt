/*상품 업데이트 요청 모델*/
package com.example.parabara.data.entities

data class ProductUpdateRequest(

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
    val price: Long
)
