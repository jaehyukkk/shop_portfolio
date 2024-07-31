package com.bubaum.pairing_server.reviewoptiontemplate.domain.dto

import com.bubaum.pairing_server.reviewoptiontemplate.domain.entity.ReviewOptionTemplate
import io.swagger.v3.oas.annotations.media.Schema

class ReviewOptionTemplateRequestDto(
    @Schema(description = "템플릿 이름", example = "기본템플릿")
    val name: String,
    @Schema(description = "템플릿 요소", example = """[
    {
      "name": "핏",
      "question": "핏은 어땠나요?",
      "reviewOptionValues": [
        {
          "name": "슬림핏이에요"
        },
        {
          "name": "적당해요"
        },
        {
          "name": "오버핏이에요"
        }
      ]
    },
    {
      "name": "배송",
      "question": "배송은 어땠나요?",
      "reviewOptionValues": [
        {
          "name": "느려요"
        },
        {
          "name": "보통이에요"
        },
        {
          "name": "빨라요"
        }
      ]
    }
  ]""")
    val content: String,
) {

    fun toEntity() : ReviewOptionTemplate{
        return ReviewOptionTemplate(
            name = name,
            content = content,
        )
    }
}