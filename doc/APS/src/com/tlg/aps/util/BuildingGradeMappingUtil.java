package com.tlg.aps.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.tlg.aps.vo.BuildingGradeMappingSrcItem;

import au.com.bytecode.opencsv.CSVReader;



public class BuildingGradeMappingUtil {
	private static final Logger logger = Logger.getLogger(BuildingGradeMappingUtil.class);
	private static List<BuildingGradeMappingSrcItem> srcItems;
	private static final String MAPPING_SRC_LIST_FILE_NAME="BuildingGradeMapping.csv";
	
	public static String getlv(String accessCode){
		if( srcItems==null ){
			try{
				loadMappingSrcItemsFromCsv();
			}catch(Exception e){
				logger.error("Error loading "+MAPPING_SRC_LIST_FILE_NAME+" - "+e.getClass().getName()+":"+e.getMessage());
				return null;
			}
		}
		
		//match by zipCode
		if( StringUtils.isNotBlank(accessCode) ){
			for( BuildingGradeMappingSrcItem item:srcItems ){
//				logger.error("accessCode["+accessCode);
//				logger.error("item[ "+item);

				if( accessCode.equals(item.getAccessCode())){
					return item.getLv();
				}
			}
//			logger.error("Error loading "+accessCode);
		}
		
		
		return "建築等級比對錯誤";
	}
	
	public static String getlvDescription(String accessCode){
		if( srcItems==null ){
			try{
				loadMappingSrcItemsFromCsv();
			}catch(Exception e){
				logger.error("Error loading "+MAPPING_SRC_LIST_FILE_NAME+" - "+e.getClass().getName()+":"+e.getMessage());
				return null;
			}
		}
		
		//match by zipCode
		if( StringUtils.isNotBlank(accessCode) ){
			for( BuildingGradeMappingSrcItem item:srcItems ){
//				logger.error("accessCode["+accessCode);
//				logger.error("item[ "+item);

				if( accessCode.equals(item.getAccessCode())){
					return item.getLvDescription();
				}
			}
//			logger.error("Error loading "+accessCode);
		}
		
		
		return "建築等級比對錯誤";
	}

	protected static void loadMappingSrcItemsFromCsv() throws IOException{
		srcItems = new ArrayList<BuildingGradeMappingSrcItem>();
		
		logger.info("Loading TsbClerkMappingSrcItems from "+ MAPPING_SRC_LIST_FILE_NAME+".... ");
		CSVReader reader =
				new CSVReader( new InputStreamReader(BuildingGradeMappingUtil.class.getClassLoader().getResourceAsStream(MAPPING_SRC_LIST_FILE_NAME),"UTF-8"));
		
		List<String[]> content = reader.readAll();
		logger.info("Found "+ (content.size()-1) +" records...");
		for( int i=0;i<content.size();i++ ){
			String[] row= content.get(i);
			BuildingGradeMappingSrcItem item = new BuildingGradeMappingSrcItem();
			item.setAccessCode(StringUtils.defaultString(row[0],"").trim());
			item.setLv(StringUtils.defaultString(row[1],"").trim());
			item.setLvDescription(StringUtils.defaultString(row[2],"").trim());

			
			logger.debug(StringUtils.defaultString(row[0],"")+"  "+StringUtils.defaultString(row[1],"") + "  " +StringUtils.defaultString(row[2],"") );
			srcItems.add(item);
		}
	}
}
