package com.nateshao;

/**
 * Hello world!
 */
public class App {
    /**
     * 99乘法表
     * 思路：
     * <p>
     * 1. 第一层从1到9遍历
     * 2. 第二层1到9遍历
     * 3. 比较判断。有两种情况。当 i == j 比如1 * 1 = 1 。否则就不是 i ！= j 输出
     *
     * @param args
     */
//    public static void main(String[] args) {
//        for (int i = 1; i < 10; i++) {
//            for (int j = 1; j <= i; j++) {
//                if (j == i) {
//                    System.out.println(j + " * " + i + " = " + (j * i) + "   ");
//                } else {
//                    System.out.print(j + " * " + i + " = " + (j * i) + "   ");
//                }
//            }
//        }
//    }
    public static void main(String[] args) {
        for (int i = 1; i <= 9; i++) {
            for (int j = 1; j <= i; j++) {
                System.out.print(j + "×" + i + "=" + i * j + "\t");// \t 跳到下一个TAB位置
            }
            System.out.println();
        }
    }
}
