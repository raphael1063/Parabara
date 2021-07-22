/*상품 업데이트 결과 모델*/
package com.example.parabara.data.entities

data class ProductUpdateResult(

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
)