package com.lmk.ms.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;

/**
 * 校验工具类
 * @author laomake@hotmail.com
 * @version 1.0
 * @date 2021/10/04
 */
public class ValidateUtils {

	public static boolean regex(String str, String regex) {
		if(StringUtils.isBlank(str) || StringUtils.isBlank(regex)) {
			return false;
		}
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}

	/**
	 * 是否邮箱
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email) {
		return regex(email, "\\w+@\\w+\\.[a-z]+(\\.[a-z]+)?");
	}
	
	/**
	 * 是否手机号码
	 * @param mobile
	 * @return
	 */
	public static boolean isMobile(String mobile) {
		return regex(mobile, "(\\+\\d+)?1[34578]\\d{9}$");
	}
	
	/**
	 * 是否身份证号
	 * @param idcard
	 * @return
	 */
	public static boolean isIdcard(String idcard) {
        if (StringUtils.isBlank(idcard)) {
            return false;
        }
        // 定义判别用户身份证号的正则表达式（15位或者18位，最后一位可以为字母）
        String regularExpression = "(^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|" +
                "(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}$)";
        //假设18位身份证号码:41000119910101123X  410001 19910101 123X
        //^开头
        //[1-9] 第一位1-9中的一个      4
        //\\d{5} 五位数字           10001（前六位省市县地区）
        //(18|19|20)                19（现阶段可能取值范围18xx-20xx年）
        //\\d{2}                    91（年份）
        //((0[1-9])|(10|11|12))     01（月份）
        //(([0-2][1-9])|10|20|30|31)01（日期）
        //\\d{3} 三位数字            123（第十七位奇数代表男，偶数代表女）
        //[0-9Xx] 0123456789Xx其中的一个 X（第十八位为校验值）
        //$结尾
 
        //假设15位身份证号码:410001910101123  410001 910101 123
        //^开头
        //[1-9] 第一位1-9中的一个      4
        //\\d{5} 五位数字           10001（前六位省市县地区）
        //\\d{2}                    91（年份）
        //((0[1-9])|(10|11|12))     01（月份）
        //(([0-2][1-9])|10|20|30|31)01（日期）
        //\\d{3} 三位数字            123（第十五位奇数代表男，偶数代表女），15位身份证不含X
        //$结尾
 
 
        boolean matches = idcard.matches(regularExpression);
 
        //判断第18位校验值
        if (matches) {
 
            if (idcard.length() == 18) {
                try {
                    char[] charArray = idcard.toCharArray();
                    //前十七位加权因子
                    int[] idCardWi = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
                    //这是除以11后，可能产生的11位余数对应的验证码
                    String[] idCardY = {"1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"};
                    int sum = 0;
                    for (int i = 0; i < idCardWi.length; i++) {
                        int current = Integer.parseInt(String.valueOf(charArray[i]));
                        int count = current * idCardWi[i];
                        sum += count;
                    }
                    char idCardLast = charArray[17];
                    int idCardMod = sum % 11;
                    if (idCardY[idCardMod].toUpperCase().equals(String.valueOf(idCardLast).toUpperCase())) {
                        return true;
                    } else {
                        return false;
                    }
 
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }
 
        }
        return matches;
    }
	
	/**
	 * 密码强度校验
	 * @param npwd
	 * @param title
	 * @return
	 */
	public static String checkPassword(String npwd, String title) {
		if(StringUtils.isBlank(npwd)) {
			return title+"不能为空";
		}
    	//至少8位
    	if(npwd.length() < 8){
    		return title+"长度不能小于8位";
    	}
    	//大写字母开头
    	if(!regex(npwd, "^[^0-9].+")) {
    		return title+"必须以非数字开头";
    	}
    	//必须包含大小写字母、数字、特殊字符至少三项
    	int cnt = 1; //大写字母开头已经包含
    	//包含小写字母
    	if(regex(npwd, "^[^0-9]+.*[a-z]+.*")) {
    		cnt++;
    	}
    	//包含数字
    	if(regex(npwd, "^[^0-9]+.*\\d+.*")) {
    		cnt++;
    	}
    	//包含特殊字符
    	if(regex(npwd, "^[^0-9]+.*\\W+.*")) {
    		cnt++;
    	}
    	
    	if(cnt < 3) {
    		return title+"强度不足";
    	}
    	
    	return null;
	}
	
}
