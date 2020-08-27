package com.servo.utils;

import java.util.Random;

public final class RandomChooser {

    public final static String choose_image(){
        Random random = new Random(System.currentTimeMillis());
        return Constants.image_options[random.nextInt(6)];
    }
}
