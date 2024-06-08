package org.chatapp.chatapp.common;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class UploadService {

    //외부폴더
    public static String UPLOAD_PATH = "file:///C:/Users/kimad/Desktop/JavaPortpolio/coctail_library_imgs/";
    private String uploadPath = "C:/Users/kimad/Desktop/JavaPortpolio/coctail_library_imgs/";

    public static String createFileName(MultipartFile file){
        return UUID.randomUUID() + "_" + file.getOriginalFilename();
    }

    public void saveFile(MultipartFile file,String fileName) {
        try {
            file.transferTo(new File(uploadPath, fileName));
        } catch (IOException e) {
            log.error("Error occurred while saving file: {}", e.getMessage(), e);
        }
    }

    // 파일 삭제 메서드
    public void deleteFile(String fileName) {
        try {
            File file = new File(uploadPath, fileName);
            if (file.delete()) {
                log.info("{} 파일 삭제 성공", fileName);
            } else {
                log.warn("{} 파일 삭제 실패", fileName);
            }
        } catch (Exception e) {
            log.error("Error occurred while deleting file: {}", e.getMessage(), e);
        }
    }

    public void updateFile(String oldFileName,MultipartFile newFile,String newFileName){
        if(!newFile.isEmpty()){
            //새로운 이미지가 업데이트 되는경우

            //기존이미지를 삭제
            deleteFile(oldFileName);
            //새로운 이미지를 추가
            saveFile(newFile,newFileName);
        }
    }
}
