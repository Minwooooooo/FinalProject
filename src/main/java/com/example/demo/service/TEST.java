package com.example.demo.service;

public class TEST {
    public static void main(String[] args) {
        String message=" !공지";
        String init=message.substring(0,1);
        System.out.println(message);
        if(init.equals("!")){
            System.out.println("true");
        }
        else System.out.println("false");
    }
}
