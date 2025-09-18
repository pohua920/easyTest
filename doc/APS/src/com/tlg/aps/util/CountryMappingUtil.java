/**
 * 
 */
package com.tlg.aps.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.tlg.aps.vo.CountryMappingSrcItem;

import au.com.bytecode.opencsv.CSVReader;

/**
 * @author bh061
 *
 */
public class CountryMappingUtil {
	private static final Logger logger = Logger.getLogger(CountryMappingUtil.class);
	private static List<CountryMappingSrcItem> srcItems;
	private static final String COUNTRY_MAPPING_SRC_LIST_FILE_NAME = "countryMapping.csv";

	public static String getCountryCode(String countryName) {
		// 讀取countryMapping csv檔案
		if (srcItems == null) {
			try {
				loadMappingSrcItemsFromCsv();
			} catch (Exception e) {
				logger.error("Error loading " + COUNTRY_MAPPING_SRC_LIST_FILE_NAME + " - " + e.getClass().getName() + ":" + e.getMessage());
				return null;
			}
		}

		// match by countryName
		if (StringUtils.isNotBlank(countryName)) {
			logger.info("arg countryName="+countryName);
			for (CountryMappingSrcItem item : srcItems) {
				if (countryName.trim().equals(item.getCountryName())){
					return item.getCountryCode();
				}
			}
		}
		
		return "";

	}

	protected static void loadMappingSrcItemsFromCsv() throws IOException {
		srcItems = new ArrayList<CountryMappingSrcItem>();

		logger.info("Loading CountryMappingSrcItem from " + COUNTRY_MAPPING_SRC_LIST_FILE_NAME + ".... ");
		CSVReader reader = new CSVReader(new InputStreamReader(CountryMappingUtil.class.getClassLoader()
				.getResourceAsStream(COUNTRY_MAPPING_SRC_LIST_FILE_NAME), "MS950")); //編碼改為MS950

		List<String[]> content = reader.readAll();
		logger.info("Found " + (content.size() - 1) + " records...");
		for (int i = 0; i < content.size(); i++) {
			String[] row = content.get(i);
			CountryMappingSrcItem item = new CountryMappingSrcItem();
			item.setCountryCode(StringUtils.defaultString(row[0], "").trim());
			item.setCountryName(StringUtils.defaultString(row[1], "").trim());

			srcItems.add(item);
//			logger.info("countryList="+item.getCountryCode() + "; "+item.getCountryName());
		}
	}
}
