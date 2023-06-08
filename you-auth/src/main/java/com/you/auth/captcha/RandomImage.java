package com.you.auth.captcha;

import java.awt.image.BufferedImage;

public interface RandomImage {

    BufferedImage getValidateImage();

    // 获取生成的验证字符串
    String getValidateString();

}