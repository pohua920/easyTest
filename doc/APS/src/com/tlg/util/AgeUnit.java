package com.tlg.util;

/**
 * 
 * @author neil
 *
 */
public enum AgeUnit {
	NULL("不分"), DAY("天"), MONTH("月"), YEAR("年");
	
	private AgeUnit(String name){
		this.name = name;
	}
	
	private String name;
	
	public String getName(){
		
		return name;
	}
}
