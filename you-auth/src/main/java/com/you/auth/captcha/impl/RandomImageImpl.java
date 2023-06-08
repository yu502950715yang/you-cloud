package com.you.auth.captcha.impl;


import com.you.auth.captcha.AbstractRandomImage;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class RandomImageImpl extends AbstractRandomImage {

    public RandomImageImpl(int length, int width, int height) {
        //生成65px*20px的包含4个字符的验证码
        super(length, width, height);
    }

    @Override
    protected void createVerificationCodeImage() {
        //建立图形缓冲区。
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();//获得   Graphics 对象。
        g.setColor(getRandomColor(180, 250));//设置背景色。
        g.fillRect(0, 0, width, height);//填充背景。
        StringBuilder validationCode = new StringBuilder();//用于保存最后的验证码
        String[] fontNames = {"Times New Roman", "Arial"};//用于随机的字体的集合
        Random r = new Random();
        //随机生成4个验证码
        for (int i = 0; i < this.length; i++) {
            g.setFont(new Font(fontNames[r.nextInt(2)], Font.BOLD, height));
            char codeChar = randomString.charAt(r.nextInt(randomString.length()));
            validationCode.append(codeChar);
            g.setColor(getRandomColor(10, 100));
            g.drawString(String.valueOf(codeChar), 25 * i + r.nextInt(7), height - r.nextInt(6));//在图形上输出验证码
        }
        validateString = validationCode.toString();
        //随机生干扰码
        for (int i = 0; i < 30; i++) {
            g.setColor(getRandomColor(90, 200));
            int x = r.nextInt(width);
            int y = r.nextInt(height);
            g.drawLine(x, y, x + r.nextInt(10), y + r.nextInt(5));
        }
        g.dispose(); // 图像生效
        validateImage = image;
    }
}
