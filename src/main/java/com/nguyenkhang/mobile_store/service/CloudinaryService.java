package com.nguyenkhang.mobile_store.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.nguyenkhang.mobile_store.dto.response.UploadFileCloudinaryResponse;
import com.nguyenkhang.mobile_store.exception.AppException;
import com.nguyenkhang.mobile_store.exception.ErrorCode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CloudinaryService {
    Cloudinary cloudinary;

    public UploadFileCloudinaryResponse uploadFile(MultipartFile file, String folderName) throws IOException {
        String publicId = System.currentTimeMillis() + "_" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        //  Cấu hình các tùy chọn upload
        var uploadParams = ObjectUtils.asMap(
                "public_id", publicId,
                "resource_type", "auto", // Tự động nhận diện (image, video, raw)
                "folder", folderName
        );

        var uploadResults = cloudinary.uploader().upload(file.getBytes(), uploadParams);

        String url = uploadResults.get("secure_url").toString();// lấy link https
        String effectivePublicId = uploadParams.get("public_id").toString();


        return UploadFileCloudinaryResponse.builder()
                .publicUrl(url)
                .publicId(effectivePublicId)
                .build();
    }


    public void deleteFile(String publicId, String folderName) throws IOException {
        var options = ObjectUtils.asMap(
                "resource_file", "image"// Hoặc "video", "raw".
        );

        var result = cloudinary.uploader().destroy(folderName + "/" + publicId, options);

        log.info("Delete image file result: {}", result);
    }


    public UploadFileCloudinaryResponse safeUpload(MultipartFile file, String folder, ErrorCode errorCode) {
        try {

            return uploadFile(file, folder);
        } catch (IOException e) {
            throw new AppException(errorCode);
        }
    }

    public void safeDelete(String publicId, String folder, ErrorCode errorCode) {
        try {
            deleteFile(publicId, folder);
        } catch (IOException e) {
            throw new AppException(errorCode);
        }
    }
}
