package com.smartcontact.services;

import org.springframework.web.multipart.MultipartFile;

public interface ImageServices {

    String uploadImage(MultipartFile picture , String filename);

    String getUrlFromPublicId(String publicID);

}
