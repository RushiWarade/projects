package com.smartcontact.services.impl;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.smartcontact.helpers.AppConstaints;
import com.smartcontact.services.ImageServices;

@Service
public class ImageServiceImpl implements ImageServices {

    @Autowired
    private Cloudinary cloudinary;

    @Override
    public String uploadImage(MultipartFile contactImage, String filename) {

        // String filename = UUID.randomUUID().toString();

        // code to upload an imaage on server

        try {
            byte[] data = new byte[contactImage.getInputStream().available()];
            contactImage.getInputStream().read(data);

            cloudinary.uploader().upload(data, ObjectUtils.asMap(
                    "public_id", filename));
            return this.getUrlFromPublicId(filename);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }

        // return url
    }

    @Override
    public String getUrlFromPublicId(String publicID) {

        return cloudinary
                .url()
                .transformation(
                        new Transformation<>()
                                .width(AppConstaints.CONTACT_IMAGE_WIDTH)
                                .height(AppConstaints.CONTACT_IMAGE_HEIGHT)
                                .crop(AppConstaints.CONTACT_IMAGE_CROP))
                .generate(publicID);
    }

}
