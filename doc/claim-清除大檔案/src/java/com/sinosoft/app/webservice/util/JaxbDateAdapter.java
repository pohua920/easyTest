package com.sinosoft.app.webservice.util;

import java.text.DateFormat;
 import java.text.SimpleDateFormat;
 import java.util.Date;
 
import javax.xml.bind.annotation.adapters.XmlAdapter;

import com.sinosoft.claim.common.util.CommonUtils;
import com.sinosoft.sysframework.common.datatype.DateTime;

 
 public class JaxbDateAdapter extends XmlAdapter<String, Date> {
     static final String STANDARM_DATE_FORMAT = "yyyy-MM-dd";
 
     @Override
     public Date unmarshal(String v) throws Exception {
         if (v == null) {
             return null;
         }
         
         DateTime date = CommonUtils.getDateTimeInstance(v, DateTime.YEAR_TO_DAY);
         return date;
     }
 
     @Override
     public String marshal(Date v) throws Exception {
         DateFormat format = new SimpleDateFormat(STANDARM_DATE_FORMAT);
         return format.format(v);
     }
 }
