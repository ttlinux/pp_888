package com.a8android888.bocforandroid.util;

import com.a8android888.bocforandroid.Bean.BKBean_Guoguan;
import com.a8android888.bocforandroid.Bean.BKBean_Normal;
import com.a8android888.bocforandroid.Bean.FootBallHq;
import com.a8android888.bocforandroid.Bean.FootBallRuQiu;
import com.a8android888.bocforandroid.Bean.FootBallbodan;
import com.a8android888.bocforandroid.Bean.LotterycomitorderBean;
import com.a8android888.bocforandroid.Bean.SportsFootBallBean_Guoguan;
import com.a8android888.bocforandroid.Bean.SportsFootBallBean_Normal;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Administrator on 2017/5/6.
 */
public class SortMethod {

    static final int GB_SP_DIFF = 160;
    // 存放国标一级汉字不同读音的起始区位码
    static final int[] secPosValueList = { 1601, 1637, 1833, 2078, 2274, 2302,
            2433, 2594, 2787, 3106, 3212, 3472, 3635, 3722, 3730, 3858, 4027,
            4086, 4390, 4558, 4684, 4925, 5249, 5600 };
    // 存放国标一级汉字不同读音的起始区位码对应读音
    static final char[] firstLetter = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
            'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'w', 'x',
            'y', 'z' };

    private static String getSpells(String characters) {

        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < characters.length(); i++) {

            char ch = characters.charAt(i);
            if ((ch >> 7) == 0) {
                // 判断是否为汉字，如果左移7为为0就不是汉字，否则是汉字
            } else {
                char spell = getFirstLetter(ch);
                buffer.append(String.valueOf(spell));
            }
        }
        return buffer.toString();
    }

    private static char getSpellsforchar(String characters) {
        if(characters==null || characters.length()<1)return 'a';
        char ch=characters.toLowerCase().charAt(0);
        if ((ch >> 7) == 0) {
            return ch;
        } else {
            char spell = getFirstLetter(ch);
            return spell;
        }
    }

    // 获取一个汉字的首字母
    private static Character getFirstLetter(char ch) {

        byte[] uniCode = null;
        try {
            uniCode = String.valueOf(ch).getBytes("GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
        if (uniCode[0] < 128 && uniCode[0] > 0) { // 非汉字
            return null;
        } else {
            return convert(uniCode);
        }
    }


    /**
     * 获取一个汉字的拼音首字母。 GB码两个字节分别减去160，转换成10进制码组合就可以得到区位码
     * 例如汉字“你”的GB码是0xC4/0xE3，分别减去0xA0（160）就是0x24/0x43
     * 0x24转成10进制就是36，0x43是67，那么它的区位码就是3667，在对照表中读音为‘n’
     */
    private static char convert(byte[] bytes) {
        char result = '-';
        int secPosValue = 0;
        int i;
        for (i = 0; i < bytes.length; i++) {
            bytes[i] -= GB_SP_DIFF;
        }
        secPosValue = bytes[0] * 100 + bytes[1];
        for (i = 0; i < 23; i++) {
            if (secPosValue >= secPosValueList[i]
                    && secPosValue < secPosValueList[i + 1]) {
                result = firstLetter[i];
                break;
            }
        }
        return result;
    }

    public static void sortFor_Sports(ArrayList<?> normals)
    {
        StepComparator comparator = new StepComparator();
        Collections.sort(normals, comparator);
    }


    public static  class StepComparator<T> implements Comparator<T> {

        /**
         * 如果o1小于o2,返回一个负数;如果o1大于o2，返回一个正数;如果他们相等，则返回0;
         */

        @Override
        public int compare(T lhs, T rhs) {
            if(lhs instanceof SportsFootBallBean_Normal)
            {
                if (Integer.valueOf(getSpellsforchar(((SportsFootBallBean_Normal)lhs).getTitle())) > Integer.valueOf(getSpellsforchar(((SportsFootBallBean_Normal)rhs).getTitle()))) {
                    return 1;
                }
            }
            //1111111111111

            if(lhs instanceof SportsFootBallBean_Guoguan)
            {
                if (Integer.valueOf(getSpellsforchar(((SportsFootBallBean_Guoguan)lhs).getTitle())) > Integer.valueOf(getSpellsforchar(((SportsFootBallBean_Guoguan) rhs).getTitle()))) {
                    return 1;
                }
            }
            //2222222222222222222222222222222222

            if(lhs instanceof BKBean_Guoguan)
            {
                if (Integer.valueOf(getSpellsforchar(((BKBean_Guoguan)lhs).getTitle())) > Integer.valueOf(getSpellsforchar(((BKBean_Guoguan)rhs).getTitle()))) {
                    return 1;
                }
            }
            //33333333333333333333333333

            if(lhs instanceof BKBean_Normal)
            {
                if (Integer.valueOf(getSpellsforchar(((BKBean_Normal)lhs).getTitle())) > Integer.valueOf(getSpellsforchar(((BKBean_Normal)rhs).getTitle()))) {
                    return 1;
                }
            }
            //44444444444444444444444444

            if(lhs instanceof FootBallbodan)
            {
                if (Integer.valueOf(getSpellsforchar(((FootBallbodan)lhs).getTitle())) > Integer.valueOf(getSpellsforchar(((FootBallbodan)rhs).getTitle()))) {
                    return 1;
                }
            }
            //55555555555555555555555

            if(lhs instanceof FootBallHq)
            {
                if (Integer.valueOf(getSpellsforchar(((FootBallHq)lhs).getTitle())) > Integer.valueOf(getSpellsforchar(((FootBallHq)rhs).getTitle()))) {
                    return 1;
                }
            }
            //6666666666666666666666666

            if(lhs instanceof FootBallRuQiu)
            {
                if (Integer.valueOf(getSpellsforchar(((FootBallRuQiu)lhs).getTitle())) > Integer.valueOf(getSpellsforchar(((FootBallRuQiu)rhs).getTitle()))) {
                    return 1;
                }
            }
            //77777777777777777777777777777
            return -1;
        }
    }
}
