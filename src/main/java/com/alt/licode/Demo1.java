package com.alt.licode;

import com.sun.deploy.util.ArrayUtil;

public class Demo1 {


    public static void main(String[] args) {
        //第一种声明方式
        int[] a = new int[]{1, 21, 12, 2, 1, 23, 12, 15, 34, 24};
        //第二种
        int[] b = {213, 12};
        //第三种数组声明方式
        int[] c = new int[3];
        bubbleSort(a);
    }


    private static void bubbleSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }

            }
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }
    }
}
