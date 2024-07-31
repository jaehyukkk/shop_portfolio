package com.bubaum.pairing_server.review.service

import com.bubaum.pairing_server.amazon.service.S3Service
import com.bubaum.pairing_server.reviewoptiongroup.domain.dto.ReviewOptionGroupListResponseDto
import com.bubaum.pairing_server.file.domain.entity.File
import com.bubaum.pairing_server.member.service.MemberService
import com.bubaum.pairing_server.file.domain.repository.FileRepository
import com.bubaum.pairing_server.order.service.OrderService
import com.bubaum.pairing_server.product.service.ProductService
import com.bubaum.pairing_server.review.domain.repository.ReviewRepository
import com.bubaum.pairing_server.reviewoptionvaluereview.domain.repository.ReviewOptionValueReviewRepository
import com.bubaum.pairing_server.review.domain.dto.*
import com.bubaum.pairing_server.review.domain.entity.Review
import com.bubaum.pairing_server.reviewoptionvaluereview.domain.entity.ReviewOptionValueReview
import com.bubaum.pairing_server.reviewoptiongroup.service.ReviewOptionGroupService
import com.bubaum.pairing_server.reviewoptionvalue.service.ReviewOptionValueService
import com.bubaum.pairing_server.utils.global.FileUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.security.NoSuchAlgorithmException
import kotlin.math.roundToInt

@Service
class ReviewService(
    private val reviewRepository: ReviewRepository,
    private val reviewOptionValueReviewRepository: ReviewOptionValueReviewRepository,
    private val fileRepository: FileRepository,
    private val memberService: MemberService,
    private val orderService: OrderService,
    private val productService: ProductService,
    private val reviewOptionValueService: ReviewOptionValueService,
    private val reviewOptionGroupService: ReviewOptionGroupService,
    private val s3Service: S3Service
) {

    @Transactional
    fun create(reviewRequestDto: ReviewRequestDto, images : List<MultipartFile>, memberIdx: Long) {
        reviewRequestDto.member = memberService.getMember(memberIdx)
        reviewRequestDto.product = productService.getProduct(reviewRequestDto.productIdx)
        reviewRequestDto.orderItem = orderService.getReviewOrderItem(reviewRequestDto.orderItemIdx, memberIdx)
        val review = reviewRepository.save(reviewRequestDto.toEntity())
        saveFile(images, review)
        for(idx in reviewRequestDto.reviewOptionValueIdxs.orEmpty()){
            reviewOptionValueReviewRepository.save(
                ReviewOptionValueReview(
                   review = review
                    , reviewOptionValue = reviewOptionValueService.getReviewOptionValue(idx))
            )
        }
    }

    fun getList(productIdx: Long): List<ReviewResponseDto> {
        return reviewRepository.getReviewList(productIdx)
    }

    @Throws(IOException::class, NoSuchAlgorithmException::class)
    private fun saveFile(multipartFiles: List<MultipartFile>, review: Review) {
        for (multipartFile in multipartFiles) {
            val file: File = s3Service.s3Upload(multipartFile)
            file.review = review
            val createFile = fileRepository.save(file)
            review.addFile(createFile)
        }
    }

    fun getReviewOptionStatistics(productIdx: Long): List<ReviewOptionStatisticsResponseDto> {
        val reviewOptionStatisticsList : List<ReviewOptionStatisticsQuerydslDto> =
            reviewRepository.getReviewOptionStatistics(productIdx)
        val reviewOptions : List<ReviewOptionGroupListResponseDto> = reviewOptionGroupService.getReviewOptionGroupList(productIdx)
        val reviewOptionStatisticsResponseDtoList: MutableList<ReviewOptionStatisticsResponseDto> =
            reviewOptionStatisticsList
                .groupBy { it.reviewOption }
                .map { (key, value) ->
                    val totalCount = value.stream().mapToLong { it.count }.sum()
                    var totalPercentage = 0

                    val reviewOptionList = value.map { op ->
                        val percentage = ((op.count.toDouble() / totalCount.toDouble()) * 100).roundToInt()
                        totalPercentage += percentage
                        ReviewOptionStatisticsResponseDto.Companion.ReviewOption(
                            op.reviewOptionValue,
                            op.count,
                            percentage
                        )
                    }

                    reviewOptionList.lastOrNull()?.let { lastOption ->
                        val diff = 100 - totalPercentage
                        lastOption.percentage = lastOption.percentage?.plus(diff)
                    }

                    ReviewOptionStatisticsResponseDto(key, reviewOptionList.toMutableList())
                }.toMutableList()

        for (reviewOption in reviewOptions) {
            if(reviewOptionStatisticsResponseDtoList.none { it.reviewOptionGroupName == reviewOption.name }){
                reviewOptionStatisticsResponseDtoList.add(
                    ReviewOptionStatisticsResponseDto(
                        reviewOption.name,
                        reviewOption.reviewOptionValues.map {
                            ReviewOptionStatisticsResponseDto.Companion.ReviewOption(
                                it.name,
                                0,
                                0
                            )
                        }.toMutableList()
                    )
                )
            }
            for (reviewOptionStatisticsResponseDto in reviewOptionStatisticsResponseDtoList) {
                if (reviewOptionStatisticsResponseDto.reviewOptionGroupName == reviewOption.name) {
                    for (reviewOptionValue in reviewOption.reviewOptionValues) {
                        if (reviewOptionStatisticsResponseDto.reviewOptionValues?.none { it.reviewOptionValueName == reviewOptionValue.name } == true) {
                            reviewOptionStatisticsResponseDto.reviewOptionValues.add(
                                ReviewOptionStatisticsResponseDto.Companion.ReviewOption(
                                    reviewOptionValue.name,
                                    0,
                                    0
                                )
                            )
                        }
                    }
                }
            }
        }

        return reviewOptionStatisticsResponseDtoList

    }

    fun ratingAvg(productIdx: Long): Double {
        return reviewRepository.getRatingList(productIdx)
    }

    fun getRatingPercentage(productIdx: Long): List<ReviewPercentageResponseDto> {
        return reviewRepository.getRatingPercentage(productIdx)
    }

}
