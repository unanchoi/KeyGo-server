package maddori.keygo.common.util;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import maddori.keygo.common.exception.CustomException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import static maddori.keygo.common.response.ResponseCode.*;

@Component
@RequiredArgsConstructor
public class ImageHandler {

    // 이미지가 저장되는 서버 경로
    private String absolutePath = new File("").getAbsolutePath() + "/src/main/resources/static/";

    // s3 관련
    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    // 로컬에 이미지 업로드
    public File imageUpload(MultipartFile profileImage) throws IOException {
        // 이미지 파일인지 체크
        String contentType = profileImage.getContentType();
        if (!contentType.contains("image")) throw new CustomException(PROFILE_IMAGE_FORMAT_ERROR);

        // project root path/src/images/ 에 사진 저장, 디렉토리 없다면 자동 생성
        File directory = new File(absolutePath + "images/");
        if (!directory.exists()) directory.mkdirs();

        // 확장자 구하기
        String extension;
        switch (contentType) {
            case "image/png" :
                extension = ".png";
                break;
            case "image/jpg" :
                extension = ".jpg";
                break;
            case "image/jpeg" :
                extension = ".jpeg";
                break;
            default:
                throw new CustomException(PROFILE_IMAGE_FORMAT_ERROR);
        }

        // '이미지 파일 명 + 현재 시간 + .확장자'로 파일명 설정
        // 현재 시간 string으로 변환(milliseconds까지 표시)
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddhhmmssSSS");
        String currentDate = simpleDateFormat.format(new Date());

        String path = "images/" + profileImage.getOriginalFilename() + currentDate + extension;
        File file = new File(absolutePath + path);
        profileImage.transferTo(file);

        return file;
    }

    // 로컬의 이미지 삭제
    public void imageDelete(File file) {
        if(!file.delete()) throw new RuntimeException();
    }

    // S3에 이미지 업로드
    public String imageUploadS3(MultipartFile profileImage) throws IOException {
        File localFile = imageUpload(profileImage); // 로컬에 파일 업로드
        String fileName = localFile.getName();

        // S3에 업로드
        amazonS3Client.putObject(
                new PutObjectRequest(bucket, fileName, localFile)
        );

        // 로컬의 이미지 삭제
        imageDelete(localFile);

        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    // S3 이미지 삭제
    public void imageDeleteS3(String path) {
        if (path == null) return;
        String key = URLDecoder.decode(path.split("/")[3]);
        amazonS3Client.deleteObject(bucket, key);
    }


}
