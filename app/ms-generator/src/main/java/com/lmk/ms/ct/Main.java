package com.lmk.ms.ct;

/**
 * 主类
 * @author zhudefu
 * @email laomake@hotmail.com
 */
public class Main {
    public static void main(String[] args) {
        MainFrame mainFrame = new MainFrame();

        try {
            mainFrame.init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
