package com.servo.utils;

import java.util.ArrayList;
import java.util.List;

public final class StringManupilation {

    public static final String combine_chips(List<Boolean> list, int size){
        String ret = new String();
        for(int i=0; i<size; i++){
            if(list.get(i)){
                ret+=Constants.chips_default_tags[i];
                ret+=",";
            }
        }
        return ret.substring(0,ret.length()-1);
    }
}
