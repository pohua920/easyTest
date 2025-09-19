package com.tlg.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class ToJson {
	
	public static void main(String []args) throws Exception {
		DateFormat df = new SimpleDateFormat("yyyyMMddHH");
		String filePath = "D:\\temp\\20230531\\new259.txt";
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = br.readLine()) != null) {
				String id = line;
				System.out.println("id = " + id);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			System.exit(0);
		}
		

    }

}
