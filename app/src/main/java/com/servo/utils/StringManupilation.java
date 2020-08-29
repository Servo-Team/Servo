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

    public static final String[] explode_chips(String chips){
        String[] list = new String[100];
        for(int i=0, j=0; i<chips.length();i++,j++){
            String curr = new String();
            while(chips.charAt(i) != ','){
                curr+=chips.charAt(i);
                i++;
                if(i>=chips.length()) break;
            }
            list[j] = curr;
        }

        int num_tags=0;
        for(int i=0; list[i]!=null; i++) num_tags++;

        String[] finalized = new String[num_tags];
        System.arraycopy(list, 0, finalized, 0, num_tags);
        return finalized;
    }
}
