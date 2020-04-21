package com.bikram.smart.service;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by Bikram Regmi
 * Created on 4/20/20.
 */
@Service
public class ImageViewService {

    public void imageViewService(String fileName, File imagePath, HttpServletResponse response) throws IOException {

        if (imagePath.exists()) {
            String ext = FilenameUtils.getExtension(fileName);
            if (ext.equals("png") || ext.equals("jpg") || ext.equals("jpeg")) {
                response.setContentType("image/" + ext);
            } else if (ext.equals("pdf")) {
                response.setContentType("application/" + ext);
            }
//                response.setHeader("Content-Disposition", "attachment;filename=" + fileName);

            FileCopyUtils.copy(new FileInputStream(imagePath), response.getOutputStream());

        }

    }
}
