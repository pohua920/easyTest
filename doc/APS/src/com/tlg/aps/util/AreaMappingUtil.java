package com.tlg.aps.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.tlg.aps.vo.AreaMappingSrcItem;

import au.com.bytecode.opencsv.CSVReader;



public class AreaMappingUtil {
	private static final Logger logger = Logger.getLogger(AreaMappingUtil.class);
	private static List<AreaMappingSrcItem> srcItems;
	private static final String AREA_MAPPING_SRC_LIST_FILE_NAME="postMapping.csv";
	
	public static String getAreaCode(String zipCode, String address){
		if( srcItems==null ){
			try{
				loadMappingSrcItemsFromCsv();
			}catch(Exception e){
				logger.error("Error loading "+AREA_MAPPING_SRC_LIST_FILE_NAME+" - "+e.getClass().getName()+":"+e.getMessage());
				return null;
			}
		}
		
		//match by zipCode
		if( StringUtils.isNotBlank(zipCode) ){
			for( AreaMappingSrcItem item:srcItems ){
				if( zipCode.trim().substring(0,3).equals(item.getZipCode().substring(0,3)) ){
					return item.getAreaCode();
				}
			}
		}
		
		//if not matched by zipCode, then match by address
		if( StringUtils.isNotBlank(address) ){
			for( AreaMappingSrcItem item:srcItems ){
				if( address.trim().substring(0, 3).equals(item.getDistrict().substring(0,3)) ){
					return item.getAreaCode();
				}
			}
		}
		
		//桃園縣升格桃園市
		if(  StringUtils.isNotBlank(address) && address.trim().startsWith("桃園縣") ){
			for( AreaMappingSrcItem item:srcItems ){
				if( "桃園市".equals(item.getDistrict().substring(0,3)) ){
					return item.getAreaCode();
				}
			}
		}
		
		//台北縣升格新北市
		if(  StringUtils.isNotBlank(address) && address.trim().startsWith("台北縣") ){
			for( AreaMappingSrcItem item:srcItems ){
				if( "新北市".equals(item.getDistrict().substring(0,3)) ){
					return item.getAreaCode();
				}
			}
		}
		
		return "";
	}
	public static String getZipCode(String address){
		if( srcItems==null ){
			try{
				loadMappingSrcItemsFromCsv();
			}catch(Exception e){
				logger.error("Error loading "+AREA_MAPPING_SRC_LIST_FILE_NAME+" - "+e.getClass().getName()+":"+e.getMessage());
				return null;
			}
		}
		
//		//match by zipCode
//		if( StringUtils.isNotBlank(zipCode) ){
//			for( AreaMappingSrcItem item:srcItems ){
//				if( zipCode.trim().substring(0,3).equals(item.getZipCode().substring(0,3)) ){
//					return item.getAreaCode();
//				}
//			}
//		}
		
		//if not matched by zipCode, then match by address
		if( StringUtils.isNotBlank(address) ){
			for( AreaMappingSrcItem item:srcItems ){
				
				if( address.equals(item.getDistrict()) ){
					logger.info("+++++loading "+item.getZipCode());
					return item.getZipCode();
				}
			}
		}
		
//		//桃園縣升格桃園市
//		if(  StringUtils.isNotBlank(address) && address.trim().startsWith("桃園縣") ){
//			for( AreaMappingSrcItem item:srcItems ){
//				if( "桃園市".equals(item.getDistrict().substring(0,3)) ){
//					return item.getZipCode();
//				}
//			}
//		}
//		
//		//台北縣升格新北市
//		if(  StringUtils.isNotBlank(address) && address.trim().startsWith("台北縣") ){
//			for( AreaMappingSrcItem item:srcItems ){
//				if( "新北市".equals(item.getDistrict().substring(0,3)) ){
//					return item.getZipCode();
//				}
//			}
//		}
//		
		return "Mapping錯誤";
	}
	public static String getDistrict(String zipCode){
		if( srcItems==null ){
			try{
				loadMappingSrcItemsFromCsv();
			}catch(Exception e){
				logger.error("Error loading "+AREA_MAPPING_SRC_LIST_FILE_NAME+" - "+e.getClass().getName()+":"+e.getMessage());
				return null;
			}
		}
		
		//match by zipCode
		if( StringUtils.isNotBlank(zipCode) ){
			for( AreaMappingSrcItem item:srcItems ){
				if( zipCode.trim().substring(0,3).equals(item.getZipCode().substring(0,3)) ){
					return item.getDistrict();
				}
			}
		}
		
//		//if not matched by zipCode, then match by address
//		if( StringUtils.isNotBlank(address) ){
//			for( AreaMappingSrcItem item:srcItems ){
//				if( address.trim().substring(0, 6).equals(item.getDistrict().substring(0,6)) ){
//					return item.getDistrict();
//				}
//			}
//		}
//		
//		//桃園縣升格桃園市
//		if(  StringUtils.isNotBlank(address) && address.trim().startsWith("桃園縣") ){
//			for( AreaMappingSrcItem item:srcItems ){
//				if( "桃園市".equals(item.getDistrict().substring(0,3)) ){
//					return item.getDistrict();
//				}
//			}
//		}
//		
//		//台北縣升格新北市
//		if(  StringUtils.isNotBlank(address) && address.trim().startsWith("台北縣") ){
//			for( AreaMappingSrcItem item:srcItems ){
//				if( "新北市".equals(item.getDistrict().substring(0,3)) ){
//					return item.getDistrict();
//				}
//			}
//		}
		
		return null;
	}
	protected static void loadMappingSrcItemsFromCsv() throws IOException{
		srcItems = new ArrayList<AreaMappingSrcItem>();
		
		logger.info("Loading AreaMappingSrcItems from "+ AREA_MAPPING_SRC_LIST_FILE_NAME+".... ");
		CSVReader reader =
				new CSVReader( new InputStreamReader(AreaMappingUtil.class.getClassLoader().getResourceAsStream(AREA_MAPPING_SRC_LIST_FILE_NAME),"UTF-8"));
		
		List<String[]> content = reader.readAll();
		logger.info("Found "+ (content.size()-1) +" records...");
		for( int i=0;i<content.size();i++ ){
			String[] row= content.get(i);
			AreaMappingSrcItem item = new AreaMappingSrcItem();
			item.setZipCode(StringUtils.defaultString(row[0],"").trim());
			item.setDistrict(StringUtils.defaultString(row[1],"").trim());
			item.setAreaCode(StringUtils.defaultString(row[2],"").trim());
			//logger.debug(StringUtils.defaultString(row[0],"")+"  "+StringUtils.defaultString(row[1],"")+" "+StringUtils.defaultString(row[2],""));
			srcItems.add(item);
		}
	}
	
	 //
		/*
		 * 用郵遞區號置換掉 前面的地址行政區域
		 * Before 437,台中縣大甲鎮XXX 
		 * After 437,台中市大甲區 XXX
		 */
		public static String genAddressData(String zipCode, String inString){
			if( srcItems==null ){
				try{
					loadMappingSrcItemsFromCsv();
				}catch(Exception e){
					logger.error("Error loading "+AREA_MAPPING_SRC_LIST_FILE_NAME+" - "+e.getClass().getName()+":"+e.getMessage());
					return null;
				}
			}
			
			String str1 = inString.trim();
	        String str2 = "";                
	        String result = str1;		
	        String compareString = "";
	        int dist1 = 0;
			
			//match by zipCode
			if( StringUtils.isNotBlank(zipCode) ){
				for( AreaMappingSrcItem item:srcItems ){
					if( zipCode.trim().substring(0,3).equals(item.getZipCode().substring(0,3)) ){
						compareString = item.getDistrict();
						
			        	dist1 = genAdress(str1);
			        	
			        	if(dist1>=0){
			            	str2 = str1.substring(0, dist1+1);
			        	}else{
			        		str2 = str1.substring(0, compareString.length());
			        	}
			        	
				    	if(!compareString.equals(str2) ){
				    		result = str1.replace(str2, compareString);
				    	}
					}
				}
			}	
			
//			logger.info(zipCode+"------------------->inString = "+inString+", dist1 = "+dist1+", compareString = "+compareString+", str2 = "+str2+", result = "+result);		
			
			return result;
		}	
		
		
		/*
	     * 鄉鎮市區
	     * 街路
	    */
	    public static int genAdress(String inString) {
	    	int result = 0;
	    	
	    	if(inString.indexOf("市")>=0 && (inString.indexOf("新竹市")>=0||inString.indexOf("嘉義市")>=0)){
	    		result = inString.indexOf("市");
	    	}else if(inString.indexOf("南海")>=0 && inString.indexOf("沙")>=0){
	    		result = inString.indexOf("沙");
	    	}else if(inString.indexOf("南海島")>=0){
	    		result = inString.indexOf("島");    		
	    	}else if(inString.indexOf("南海島")>=0){
	    		result = inString.indexOf("島");
	    	}else if(inString.indexOf("區")>=0){
	    		result = inString.indexOf("區");     		
	    	}else if(inString.indexOf("鄉")>=0){
	    		result = inString.indexOf("鄉");    		
	    	}else if(inString.indexOf("鄉")>=0){
	    		result = inString.indexOf("鄉");    		
	    	}else if(inString.indexOf("鎮")>=0){
	    		result = inString.indexOf("鎮");       	
	    	}else if(inString.indexOf("市")>=0){
	    		result = inString.indexOf("市");
	    	} 
	    	
	    	return result;
	    }	
					
	
}
