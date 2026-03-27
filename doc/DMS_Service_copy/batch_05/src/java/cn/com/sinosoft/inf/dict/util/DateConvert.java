package cn.com.sinosoft.inf.dict.util;

import java.text.ParseException;   
import java.text.SimpleDateFormat;   
import org.apache.commons.beanutils.Converter;   
  
/**  
 *  
 * @author lucas  
 */  
public class DateConvert implements Converter {   
  
    public Object convert(Class arg0, Object arg1) {   
        if(arg1 == null){
        	return null;
        }
    	String p = arg1.toString();   
        if(p== null || p.trim().length()==0){   
            return null;   
        }      
        try{   
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   
            return df.parse(p.trim());   
        }   
        catch(Exception e){   
            try {   
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");   
                return df.parse(p.trim());   
            } catch (ParseException ex) {   
                return null;   
            }   
        }   
           
    }   
  
}  
