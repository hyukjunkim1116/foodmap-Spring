package foodmap.V2.util.AmazonS3;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class S3Service {
    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    public String saveFile(MultipartFile multipartFile) throws IOException {
        String uuid = String.valueOf(UUID.randomUUID());
        String originalFilename = multipartFile.getOriginalFilename();
        String fileUrl = originalFilename + uuid;
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getSize());
        metadata.setContentType(multipartFile.getContentType());
        amazonS3.putObject(bucket, fileUrl, multipartFile.getInputStream(), metadata);
        return amazonS3.getUrl(bucket, fileUrl).toString();
    }
    public void deleteImage(String fileUrl) throws SdkClientException {
        String[] parts = fileUrl.split("/");
        String originalFilename = parts[parts.length - 1];
        amazonS3.deleteObject(bucket, originalFilename);
    }
    public Long getFileCountInBucket() {
        // 버킷 내 객체 목록 가져오기
        ListObjectsV2Request request = new ListObjectsV2Request().withBucketName(bucket);
        ListObjectsV2Result result;
        var count=0L;
        do {
            result = amazonS3.listObjectsV2(request);
            count += result.getObjectSummaries().size();
            request.setContinuationToken(result.getNextContinuationToken());
        } while (result.isTruncated());
        return count;
    }
}