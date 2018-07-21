package com.springboot.controller;

import java.util.Random;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
           Random r = new Random();
           for(int i = 0 ;i<9;i++){
        	   
        	   System.out.println(r.nextInt(3));
           }
           
	}

}
