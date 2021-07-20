/*상품 리스트 조회 요청 모델*/
package com.example.parabara.data.entities

data class ProductListRequest(

    /**
     * 현재 페이지
     */
    val page: Int,

    /**
     * 페이징 사이즈
     */
    val size: Int
)
