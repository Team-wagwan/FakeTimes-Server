package com.wagwan.faketimes.global.common.s3

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.regions.Regions
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.multipart.MultipartFile
import java.util.*

@Configuration
@Service
class S3(
    private val s3Properties: S3Properties
) {
    @Bean
    fun amazonS3Client(): AmazonS3 {
        return AmazonS3ClientBuilder.standard()
            .withCredentials(
                AWSStaticCredentialsProvider(BasicAWSCredentials(s3Properties.accessKey, s3Properties.secretKey))
            )
            .withRegion(Regions.AP_NORTHEAST_2)
            .build()
    }

    fun uploadMultipleFile(
        @RequestPart file: MultipartFile,
    ): String {

        if (file.isEmpty || file.equals("")) {
            throw RuntimeException("file can not be empty")
        }

        val fileName = createFileName(file.originalFilename ?: file.name)

        val objectMetadata = ObjectMetadata().apply {
            this.contentType = file.contentType
            this.contentLength = file.size
        }

        try {
            val putObjectRequest = PutObjectRequest(
                s3Properties.bucket,
                fileName,
                file.inputStream,
                objectMetadata
            ).withCannedAcl(CannedAccessControlList.PublicRead)  // 퍼블릭 읽기 권한 설정

            amazonS3Client().putObject(putObjectRequest)
        } catch (e: Exception) {
            throw RuntimeException("file upload failed", e)
        }

        return amazonS3Client().getUrl(s3Properties.bucket, fileName).toString()
    }

    private fun createFileName(originalName: String): String {
        return "/" + UUID.randomUUID() + "-" + originalName
    }
}