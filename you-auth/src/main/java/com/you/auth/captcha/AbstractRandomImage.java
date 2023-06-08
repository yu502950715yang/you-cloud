package com.you.auth.captcha;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public abstract class AbstractRandomImage implements RandomImage {

    protected static final String randomString = "0123456789"; // 图片上的字符串
    protected String validateString; // 生成的验证字符串
    protected BufferedImage validateImage; // 生成的验证图片
    protected int length; // 图片上字符的个数
    protected int width; // 图片的宽度
    protected int height; // 图片的高度

    protected AbstractRandomImage() {
        //生成100px*22px的包含6个字符的验证码
        this.width = 100;
        this.height = 22;
        this.length = 6;
    }

    /**
     * 生成验证码图片
     *
     * @param length 图片上字符的个数
     * @param width  图片的宽度
     * @param height 图片的高度
     */
    public AbstractRandomImage(int length, int width, int height) {
        this.length = length;
        this.width = width;
        this.height = height;
    }

    // 获取生成的验证字符串
    @Override
    public String getValidateString() {
        if (validateString == null) {
            createVerificationCodeImage();
        }
        return validateString;
    }

    @Override
    public BufferedImage getValidateImage() {
        if (validateImage == null) {
            createVerificationCodeImage();
        }
        return validateImage;
    }

    protected abstract void createVerificationCodeImage();

    protected Color getRandomColor(int frontColor, int backColor) { // 给定范围获得随机颜色
        Random random = new Random();
        if (frontColor > 255) {
            frontColor = 255;
        }
        if (backColor > 255) {
            backColor = 255;
        }
        int red = frontColor + random.nextInt(backColor - frontColor);
        int green = frontColor + random.nextInt(backColor - frontColor);
        int blue = frontColor + random.nextInt(backColor - frontColor);
        return new Color(red, green, blue);
    }

}