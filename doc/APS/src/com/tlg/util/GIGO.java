package com.tlg.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.PropertyUtilsBean;

public class GIGO {
  private static String[] abridge = { "", "Hf", "Tb", "Ddl", "Hd", "Sb", "Fb",
      "Rb", "Cb"                 };

  /**
   * 抓取source的attribute value,放入target裡有相同名稱的attribute value
   * 
   * @param target
   * @param source
   * @return fill成功回傳true, fill失敗回傳retuen false
   */
  public static boolean fill(Object target, Object source) throws Exception {
    Field souFie[] = source.getClass().getDeclaredFields();
    for(int i = 0; i < souFie.length; i++){
      Class[] arg1 = { souFie[i].getType() };
      char[] filedName = souFie[i].getName().toCharArray();
      filedName[0] = Character.toUpperCase(filedName[0]);
      try{
        Method tarMeth = null;
        Method souMeth = null;
        try{
          for(int j = 0; j < abridge.length; j++){
            tarMeth = target.getClass().getMethod(
                "set" + abridge[j] + String.valueOf(filedName), arg1);
            if(tarMeth != null) break;
          }

          for(int j = 0; j < abridge.length; j++){
            souMeth = source.getClass().getMethod(
                "get" + abridge[j] + String.valueOf(filedName), null);
            if(souMeth != null){
              Object[] arg3 = { "" };
              arg3[0] = souMeth.invoke(source, null);
              if(arg3[0] == null
                  && "java.lang.String".equals(arg1[0].getName()))
                arg3[0] = "";
              tarMeth.invoke(target, arg3);
              break;
            }
          }

        }catch (NoSuchMethodException e){
          continue;
        }

      }catch (Exception e){
        System.out.println("GIGO:fill:error");
        throw e;
      }
    }
    return true;
  }

  /**
   * 抓取rs的attribute value,放入target裡有相同名稱的attribute value
   * 
   * @param target
   * @param rs
   * @return fill成功回傳true, fill失敗回傳retuen false
   */
  public static boolean fill(Object target, ResultSet rs) throws Exception {
    Commen com = new Commen();
    Field tarFie[] = target.getClass().getDeclaredFields();

    for(int i = 0; i < tarFie.length; i++){
      Class[] arg1 = { tarFie[i].getType() };
      char[] filedName = tarFie[i].getName().toCharArray();
      filedName[0] = Character.toUpperCase(filedName[0]);
      try{
        Method tarMeth = null;

        tarMeth = target.getClass().getMethod(
            "set" + String.valueOf(filedName), arg1);

        if("java.lang.String".equals(arg1[0].getName())){
          String filedNameTemp = String.valueOf(filedName);
          Object[] arg3 = { null };
          for(int j = 1; j < abridge.length; j++){
            try{
              arg3[0] = com.adjustStrig(rs.getString(filedNameTemp));
              tarMeth.invoke(target, arg3);
              break;
            }catch (SQLException e){
              if(filedNameTemp.startsWith(abridge[j]))
                filedNameTemp = filedNameTemp.substring(
                    abridge[j].length(), filedNameTemp.length());
              continue;
            }
          }
          if(arg3[0] == null){
            rs.getString(String.valueOf(filedName));
          }
        }

        else
          if("int".equals(arg1[0].getName())){
            String filedNameTemp = String.valueOf(filedName);
            Object[] arg3 = { null };
            for(int j = 1; j < abridge.length; j++){
              try{
                arg3[0] = Integer.valueOf(String.valueOf(rs.getInt(String.valueOf(filedNameTemp))));
                tarMeth.invoke(target, arg3);
                break;
              }catch (SQLException e){
                if(filedNameTemp.startsWith(abridge[j]))
                  filedNameTemp = filedNameTemp.substring(
                      abridge[j].length(), filedNameTemp.length());
                continue;
              }
            }
            if(arg3[0] == null){
              rs.getInt(String.valueOf(filedName));
            }
          }else
            if("double".equals(arg1[0].getName())){
              String filedNameTemp = String.valueOf(filedName);
              Object[] arg3 = { null };
              for(int j = 1; j < abridge.length; j++){
                try{
                  arg3[0] = Double.valueOf(String.valueOf(rs.getDouble(String.valueOf(filedNameTemp))));
                  tarMeth.invoke(target, arg3);
                  break;
                }catch (SQLException e){
                  if(filedNameTemp.startsWith(abridge[j]))
                    filedNameTemp = filedNameTemp.substring(
                        abridge[j].length(), filedNameTemp.length());
                  continue;
                }
              }
              if(arg3[0] == null){
                rs.getDouble(String.valueOf(filedName));
              }
            }else
              if("long".equals(arg1[0].getName())){
                String filedNameTemp = String.valueOf(filedName);
                Object[] arg3 = { null };
                for(int j = 1; j < abridge.length; j++){
                  try{
                    arg3[0] = Long.valueOf(String.valueOf(rs.getLong(String.valueOf(filedNameTemp))));
                    tarMeth.invoke(target, arg3);
                    break;
                  }catch (SQLException e){
                    if(filedNameTemp.startsWith(abridge[j]))
                      filedNameTemp = filedNameTemp.substring(
                          abridge[j].length(), filedNameTemp.length());
                    continue;
                  }
                }
                if(arg3[0] == null){
                  rs.getDouble(String.valueOf(filedName));
                }
              }else
                if("java.util.Date".equals(arg1[0].getName())){
                  String filedNameTemp = String.valueOf(filedName);
                  Object[] arg3 = { null };
                  for(int j = 1; j < abridge.length; j++){
                    try{
                      arg3[0] = rs.getTimestamp(String.valueOf(filedNameTemp));
                      tarMeth.invoke(target, arg3);
                      break;
                    }catch (SQLException e){
                      if(filedNameTemp.startsWith(abridge[j]))
                        filedNameTemp = filedNameTemp.substring(
                            abridge[j].length(), filedNameTemp.length());
                      continue;
                    }
                  }
                  if(arg3[0] == null){
                    rs.getTimestamp(String.valueOf(filedName));
                  }
                }

      }catch (NoSuchMethodException e){
        continue;
      }catch (SQLException e){
        if("S0022".equals(e.getSQLState())){
          // S0022: Invalid column name ObjectName.(Sybase Code)
          // System.out.println("GIGO warning:" + e.getMessage());
        }else{
          System.out.println("GIGO:fill:error");
          throw e;
        }
      }catch (Exception e){
        System.out.println("GIGO:fill:error");
        throw e;
      }
    }
    return false;
  }

  /**
   * 將bean屬性放入到Map的Key中，而bean屬性的值，則放入Value中
   * created by jason, Chiang in 2007.04.03
   *
   * @param Map map, 儲存bean資料的map物件; Object source, 來源bean
   */  
  public static void fill(Map map, Object source) throws Exception {
    if(map==null)
        map = new HashMap();
    
    Class sourceClass = source.getClass();
    Field fieldAry[] = sourceClass.getDeclaredFields();
    for(int i=0; i< fieldAry.length;i++){
      String lowerFieldName = fieldAry[i].getName();
      char[] fieldName = fieldAry[i].getName().toCharArray();
      fieldName[0] = Character.toUpperCase(fieldName[0]);      
      String fieldType = fieldAry[i].getType().getName();
      String fieldNameStr = String.valueOf(fieldName);

      try{
        if(fieldAry[i].getType().isPrimitive() || fieldType.equalsIgnoreCase("java.lang.String")
       		|| fieldType.equalsIgnoreCase("java.math.BigDecimal")
            || fieldType.equalsIgnoreCase("java.util.Date")
            || fieldType.equalsIgnoreCase("java.lang.Integer")
            || fieldType.equalsIgnoreCase("java.lang.Float")
            || fieldType.equalsIgnoreCase("java.lang.Boolean")
            || fieldType.equalsIgnoreCase("java.lang.Character")
            || fieldType.equalsIgnoreCase("java.lang.Long")
            || fieldType.equalsIgnoreCase("java.lang.Byte")){
          Method getMethod = sourceClass.getMethod("get"+fieldNameStr, null);
  
          Object returnObj = getMethod.invoke(source, null);
          if(returnObj!=null)
            map.put(lowerFieldName, returnObj.toString());
          else
            map.put(lowerFieldName, "");
        }
      }catch (NoSuchMethodException e){
        continue;
      }catch (Exception e){
        System.out.println("GIGO:fill:error");
        throw e;
      }

    }
  }  
  
  private static ConvertUtilsBean convertUtils = new ConvertUtilsBean();
  private static DateConverter dateConverter = new DateConverter();
  
  static{
    convertUtils.register(dateConverter, Date.class);
  }
  
  public static void fill(Object entity, Map source) throws Exception {
  	if(entity==null || source ==null)
  		throw new Exception("entity or source is NULL");
  	
  		Set keySet = source.keySet();
  		Iterator it = keySet.iterator();
	    BeanUtilsBean beanUtils = new BeanUtilsBean(convertUtils, new PropertyUtilsBean());
	    while(it.hasNext()){
	    	String name = (String)it.next();
	    	beanUtils.setProperty(entity, name, source.get(name));
	    }
  }
}