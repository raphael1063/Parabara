/*상품등록 요청 모델*/
package com.example.parabara.data.entities

data class ProductApplyRequest(

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
    val price: Long,

    /**
     * 상품 이미지 아이디 목록
     */
    val images: List<Long>?
)
