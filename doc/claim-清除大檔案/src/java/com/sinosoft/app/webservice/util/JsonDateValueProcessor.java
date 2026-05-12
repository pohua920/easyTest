package com.sinosoft.app.webservice.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;
/*
 * mantis：CLM0259、CLM9001，處理人員：DP0713，需求單編號：新核心-多元理賠收件平台建置案
 * 由理賠Copy過來的
 */

public class JsonDateValueProcessor implements JsonValueProcessor {

	private String format = "yyyy/MM/dd";

	 public JsonDateValueProcessor() {

	 }

	 public JsonDateValueProcessor(String format) {
	  this.format = format;
	 }

	 public Object processArrayValue(Object value, JsonConfig jsonConfig) {
	  return process(value, jsonConfig);
	 }

	 public Object processObjectValue(String key, Object value, JsonConfig jsonConfig) {
	  return process(value, jsonConfig);
	 }
	 
	 private Object process( Object value, JsonConfig jsonConfig ) {
	  if (value instanceof Date) {
	   String str = new SimpleDateFormat(format).format((Date) value);
	   return str;
	  }
	  return value == null ? null : value.toString();
	 }

	 public String getFormat() {
	  return format;
	 }

	 public void setFormat(String format) {
	  this.format = format;
	 }


}
