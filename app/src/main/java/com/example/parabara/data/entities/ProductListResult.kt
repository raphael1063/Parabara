/*상품 리스트 조회 결과 모델*/
package com.example.parabara.data.entities

data class ProductListResult(

    /**
     * 현재 페이지
     */
    val page: Int,

    /**
     * 총 페이지 수
     */
    val total: Int,

    /**
     * 총 레코드 수
     */
    val records: Int,

    /**
     * 상품정목 목록
     */
    val rows: List<Row>
)