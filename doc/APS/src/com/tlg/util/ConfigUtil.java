package com.tlg.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigUtil {
	
	private Properties properties;
	private Properties configProperties;
	
	private static final Logger log = LoggerFactory.getLogger(ConfigUtil.class);

	
	public Properties loadProperties(String path) {
		Properties properties = new Properties();
	    InputStream is = ConfigUtil.class.getClassLoader().getResourceAsStream(path);
	    try {
	    	properties.load(is);
	    } catch (IOException e) {
	        // this is never happen
	        log.error(String.format("Load properties file [%s] on error", path), e);
	    }
		return properties;
	}
	
	public Properties loadConfigProperties() throws IOException {
		String path = (String)this.configProperties.get("propertyLocation");
		File initialFile = null;
		InputStream is = null;
		Properties properties = null;
		try {
			initialFile = new File(path);
			is = new FileInputStream(initialFile);
			properties = new Properties();
			properties.load(is);
		} catch (IOException e) {
			log.error(String.format("Load properties file [%s] on error", path),e);
		}finally{
			initialFile = null;
			if(is != null){
				is.close();
				is = null;
			}
		}
		return properties;
	}

	public String getString(String key) {
		Properties properties = null;
		try{
			properties = loadConfigProperties();
			System.out.println(properties.getProperty(key));
		    String value = System.getProperty(key);
		    return StringUtil.isSpace(value) ? properties.getProperty(key) : value;
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			properties = null;
		}
		return "";
	}
	
	public String getString(Properties properties, String key) throws Exception {
	    String value = System.getProperty(key);
	    return StringUtil.isSpace(value) ? properties.getProperty(key) : value;
	}
	
	public String getString(String key, String defaultValue) throws Exception {
		loadConfigProperties();
	    String value = this.properties.getProperty(key);
	    return StringUtil.isSpace(value) ? defaultValue : value;
	}

	public String getString(Properties properties, String key, String defaultValue) throws Exception {
	    String value = properties.getProperty(key);
	    return StringUtil.isSpace(value) ? defaultValue : value;
	}

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	public Properties getConfigProperties() {
		return configProperties;
	}

	public void setConfigProperties(Properties configProperties) {
		this.configProperties = configProperties;
	}


	
}
