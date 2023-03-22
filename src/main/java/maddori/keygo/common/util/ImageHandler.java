package maddori.keygo.common.util;

import maddori.keygo.common.exception.CustomException;
import maddori.keygo.common.response.ResponseCode;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static maddori.keygo.common.response.ResponseCode.*;

public class ImageHandler {
    public static String imageUpload(MultipartFile profileImage) throws IOException {
        // 이미지 파일인지 체크
        String contentType = profileImage.getContentType();
        if (!contentType.contains("image")) throw new CustomException(PROFILE_IMAGE_FORMAT_ERROR);

        // Todo: S3 업로드로 변경
        // project root path/src/images/ 에 사진 저장, 디렉토리 없다면 자동 생성
        String absolutePath = new File("").getAbsolutePath() + "/src/main/resources/static/";
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

        return path;
    }

}
