package com.myy.common.usercenter.utils;

import cn.hutool.core.util.IdcardUtil;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.util.Random;

public class PwdUtil {
    /**
     * 加盐加密
     */
    public static String password(String password) {
        int i1 = new Random().nextInt(99999999);//生成第一个随机数
        int i2 = new Random().nextInt(99999999);//生成第二个随机数
        StringBuilder salt = new StringBuilder(i1 + String.valueOf(i2));
        if (salt.length() < 16) {
            salt.append("0".repeat(Math.max(0, 16 - salt.length())));
        }
        password = DigestUtils.md5DigestAsHex((password + salt).getBytes());
        char[] cs1 = password.toCharArray();
        char[] cs2 = salt.toString().toCharArray();
        char[] cs = new char[48];
        for (int i = 0; i < 48; i += 3) {
            cs[i] = cs1[i / 3 * 2];
            cs[i + 1] = cs2[i / 3];
            cs[i + 2] = cs1[i / 3 * 2 + 1];
        }
        return new String(cs);

    }

    /**
     * 验证密码是否错误
     */
    public static boolean verify(String password, String md5) {
        char[] cs = md5.toCharArray();
        char[] cs1 = new char[32];
        char[] cs2 = new char[16];
        for (int i = 0; i < 48; i += 3) {
            cs1[i / 3 * 2] = cs[i];
            cs1[i / 3 * 2 + 1] = cs[i + 2];
            cs2[i / 3] = cs[i + 1];
        }
        String salt = new String(cs2);
        String pwd = DigestUtils.md5DigestAsHex((password + salt).getBytes());
        return pwd.equals(new String(cs1));
    }

    /**
     * 判断密码是否过期，3个月过期
     */
    public static boolean passwordExpire(LocalDateTime passwordDate) {
        return passwordDate == null || passwordDate.plusMonths(3).isBefore(LocalDateTime.now());
    }

    /**
     * 校验密码格式
     */
    public static boolean verifyPassword(String password) {
        String regex = "^(?:(?!wanda|wonders).)(?![a-zA-Z]+$)(?![A-Z0-9]+$)(?![A-Z\\W_]+$)(?![a-z0-9]+$)(?![a-z\\W_]+$)(?![0-9\\W_]+$)[a-zA-Z0-9\\W_]{8,}$";
        return password.matches(regex);
    }

    //
    public static void main(String[] args) {
        System.out.println(IdcardUtil.getGenderByIdCard("310109195201290410"));
//        String password = "123456";
//        String pwd = password(password);
//        System.out.println(pwd);
//        System.out.println(verify(password, pwd));
//        System.out.println(verify("admin", pwd));
//        System.out.println(verifyPassword(password));
    }
}
