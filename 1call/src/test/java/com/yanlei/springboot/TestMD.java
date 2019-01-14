package com.yanlei.springboot;

import com.yanlei.springboot.util.MD5;
import com.yanlei.springboot.util.Start;
import org.junit.Test;

/**
 * @Author: x
 * @Date: Created in 16:25 2018/11/23
 */

public class TestMD {

    @Test
    public void test(){
        String s = MD5.MD5Encode("xc123456").toUpperCase();
        System.out.println(s);
        String value = Start.SchemeStart.STARTFOUR.getValue();
        System.out.println(value);

        String details = "$$60岁以上至16岁以下**16岁以下小于16岁以下**$$16岁以下大于16岁以下**1是16岁以下**$$11小于2**33大于2**22非2**";
        StringBuffer sbf = new StringBuffer();
        if (details.contains("$$") ){
            String s1 = details.replace("$$", " ");
            System.out.println(s1);
            if (s1.contains("**")){
                String s2 = s1.replace("**", " ");
                sbf.append(s2);
            }
        }

        System.out.println(sbf);
    }



}
