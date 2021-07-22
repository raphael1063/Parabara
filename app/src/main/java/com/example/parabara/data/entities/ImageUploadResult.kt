/*이미지 업로드 결과 모델*/
package com.example.parabara.data.entities

data class ImageUploadResult(

    /**
     * 이미지 아이디
     */
    val id: Long,

    /**
     * 이미지 경로
     */
    val url: String
)