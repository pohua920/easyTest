package com.tlg.util.xmlAdapter;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class DateAdapter extends XmlAdapter<String, Date> {

    private SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat dateFormat3 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private SimpleDateFormat dateFormat4 = new SimpleDateFormat("yyyy/MM/dd");

    @Override
    public String marshal(Date v) throws Exception {
        return dateFormat1.format(v);
    }

    @Override
    public Date unmarshal(String v) throws Exception {
    	Date date = null;
    	if(v.indexOf("-") != -1){
    		if(v.length() == 10){
    			date = dateFormat2.parse(v);
    		}else{
    			date = dateFormat1.parse(v);
    		}
    	}
    	
    	if(v.indexOf("/") != -1){
    		if(v.length() == 10){
    			date = dateFormat4.parse(v);
    		}else{
    			date = dateFormat3.parse(v);
    		}
    	}
    	return date;
    }

}
