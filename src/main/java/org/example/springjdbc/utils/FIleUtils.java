package org.example.springjdbc.utils;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Getter
@Setter
public class FIleUtils {
    private final String FOLDER = "static/images";

    public String uploadFile(MultipartFile image) throws IOException {

        String fileName = image.getOriginalFilename();

        File uploadDir = new File(FOLDER + File.separator + fileName);
        if (!uploadDir.exists()) {
            if (uploadDir.mkdirs()) {
                System.out.println("Directory created successfully: " + FOLDER);
            } else {
                System.out.println("Failed to create directory: " + FOLDER);
            }
        }
        image.transferTo(uploadDir);
        return "/resources/static/" + fileName;
    }
}
