package com.tlg.aps.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.tlg.aps.vo.PhoneMappingSrcItem;

import au.com.bytecode.opencsv.CSVReader;

public class PhoneMappingUtil {
	private static final Logger logger = Logger.getLogger(PhoneMappingUtil.class);
	private static List<PhoneMappingSrcItem> srcItems;
	private static final String AREA_MAPPING_SRC_LIST_FILE_NAME = "phoneMapping.csv";

	public static PhoneMappingSrcItem getAreaCode(String areaCode) {
		if (srcItems == null) {
			try {
				loadMappingSrcItemsFromCsv();
			} catch (Exception e) {
				logger.error("Error loading " + AREA_MAPPING_SRC_LIST_FILE_NAME
						+ " - " + e.getClass().getName() + ":" + e.getMessage());
				return null;
			}
		}
		// match by areaCode
		if (StringUtils.isNotBlank(areaCode)) {
			for (PhoneMappingSrcItem item : srcItems) {
				if (areaCode.equals(item.getAreaCode())) {
					return item;
				}
			}
		}
		return null;
		
	}

	protected static void loadMappingSrcItemsFromCsv() throws IOException {
		srcItems = new ArrayList<PhoneMappingSrcItem>();

		logger.info("Loading AreaMappingSrcItems from "+ AREA_MAPPING_SRC_LIST_FILE_NAME + ".... ");
		CSVReader reader = new CSVReader(new InputStreamReader(PhoneMappingUtil.class.getClassLoader().getResourceAsStream(
						AREA_MAPPING_SRC_LIST_FILE_NAME), "UTF-8"));

		List<String[]> content = reader.readAll();
		logger.info("Found " + (content.size() - 1) + " records...");
		for (int i = 0; i < content.size(); i++) {
			String[] row = content.get(i);
			if(row.length < 5) continue;
			PhoneMappingSrcItem item = new PhoneMappingSrcItem();
			item.setAreCode(StringUtils.defaultString(row[0], "").trim());
			item.setTel(StringUtils.defaultString(row[1], "").trim());
			item.setExt(StringUtils.defaultString(row[2], "").trim());
			item.setFox(StringUtils.defaultString(row[3], "").trim());
			item.setAddress(StringUtils.defaultString(row[4], "").trim());
			logger.debug(StringUtils.defaultString(row[0], "") + "  " + StringUtils.defaultString(row[1], "") + " "
					+ StringUtils.defaultString(row[2], ""));
			srcItems.add(item);
		}
	}

}
