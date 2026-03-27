package com.sinosoft.claim.common.util;

import freemarker.template.Configuration;
import freemarker.template.Template;
import ins.framework.common.Page;
import ins.framework.utils.DataUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.CellView;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.struts2.StrutsStatics;
import org.hibernate.JDBCException;
import org.springframework.orm.hibernate3.HibernateJdbcException;

import com.opensymphony.xwork2.ActionContext;
import com.sinosoft.claim.common.ConstantCodes;
import com.sinosoft.claim.common.ConstantsCollection;
import com.sinosoft.claim.schema.model.PrpLpayObjectInfo;
import com.sinosoft.sysframework.common.datatype.DateTime;
import com.sinosoft.sysframework.common.datatype.PageRecord;

public class CommonUtils {
	/** 时间格式 */
	public static final String YEAR_TO_DAY = "YEAR_TO_DAY";
	/** 时间格式 */
	public static final String YEAR_TO_SECOND = "YEAR_TO_SECOND";
	/** 时间对象 yyyy-MM-dd */
	private static final DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	/** 时间对象 yyyy-MM-dd HH:mm:ss */
	private static final DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	/** 保留两位小数格式化字符串 */
	public static final DecimalFormat DoubleFormat = new DecimalFormat("#0.00");
	/** 取整格式化字符串 */
	public static final DecimalFormat IntegerFormat = new DecimalFormat("#0");
	/** 默认除法运算精度 */
	private static final int DEF_DIV_SCALE = 10;

	private CommonUtils() {// 防止实例化
	}

	/**
	 * 转换pageRecord
	 * @param pageRecord 结果集
	 * @return
	 */
	public static Page convert(PageRecord pageRecord) {
		int recordPerPage = pageRecord.getRowsPerPage();
		int pageNo = pageRecord.getPageNo();
		Page page = new Page((pageNo - 1) * recordPerPage, pageRecord.getCount(), recordPerPage, (List<?>) pageRecord.getResult());
		return page;
	}

	/****
	 * 新老对象转换函数 （将sourObj对象转换为disObj对象）
	 * @param disObj 行对象
	 * @param sourObj 老对象
	 */
	public static Object convertObj(Object disObj, Object sourObj) {
		try {
			PropertyUtils.copyProperties(disObj, sourObj);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return disObj;
	}

	/****
	 * 新老对象转换函数 （将sourObj对象转换为disObj对象）
	 * @param disClz 新对象的类信息
	 * @param sourObj 老对象
	 */
	public static Object convertObj(Class<?> disClz, Object sourObj) {
		Object disObj = null;
		try {
			disObj = disClz.newInstance();
			PropertyUtils.copyProperties(disObj, sourObj);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return disObj;
	}

	/****
	 * 新老对象(集合)转换函数 （将sourcol中所有对象转换为disClz类型的对象）
	 * @param disClz 目标对象类型的Class
	 * @param sourcol 源数据的集合
	 * @return
	 */
	public static Collection<Object> convertObjs(Class<?> disClz, Collection<?> sourcol) {
		Collection<Object> discol = new ArrayList<Object>();
		try {
			if (sourcol != null && sourcol.size() > 0) {
				Object disObj = null;
				Object sourObj = null;
				for (Iterator<?> it = sourcol.iterator(); it.hasNext();) {
					sourObj = it.next();
					disObj = disClz.newInstance();
					PropertyUtils.copyProperties(disObj, sourObj);
					discol.add(disObj);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return discol;
	}

	/***
	 * 对yyyy-MM-dd HH:mm:ss或yyyy-MM-dd格式日期的转换
	 * @param dateType YEAR_TO_DAY/YEAR_TO_SECOND
	 * @param dateStr 时间
	 * @return
	 */
	public static Date toDate(String dateType, String dateStr) {
		if (!isEmpty(dateStr)) {
			try {
				if (YEAR_TO_DAY.equals(dateType)) {
					return ((DateFormat)format.clone()).parse(dateStr);
				} else if (YEAR_TO_SECOND.equals(dateType)) {
					return ((DateFormat)format1.clone()).parse(dateStr);
				}
			} catch (Exception e) {
			}
		}
		return null;
	}

	/***
	 * 按照yyyy-MM-dd格式转换日期
	 * @param date 时间
	 * @return
	 */
	public static String getYearToDayStr(Date date) {
		String dateStr = "";
		if (date != null) {
			dateStr = ((DateFormat)format.clone()).format(date);
		}
		return dateStr;
	}

	/***
	 * 按照yyyy-MM-dd HH:mm:ss格式日期的转换日期
	 * @param date 时间
	 * @return
	 */
	public static String getYearToSercondStr(Date date) {
		String dateStr = "";
		if (date != null) {
			dateStr = ((DateFormat)format1.clone()).format(date);
		}
		return dateStr;
	}

	/***
	 * 对yyyy-MM-dd HH:mm:ss格式日期的转换
	 * @param dateStr 时间
	 * @return
	 */
	public static Date toYearToSercondDate(String dateStr) {
		Date date = toDate(YEAR_TO_SECOND, dateStr);
		return date;
	}

	/***
	 * 对yyyy-MM-dd格式日期的转换
	 * @param dateStr 时间
	 * @return
	 */
	public static Date toYearToDayDate(String dateStr) {
		Date date = toDate(YEAR_TO_DAY, dateStr);
		return date;
	}

	/***
	 * 根据Date类型获取民国时间字符串
	 * @param date 原始日期
	 * @param format 格式化字符串
	 * @return
	 */
	public static String getMGDateStr(Date date, SimpleDateFormat format) {
		String strDate = "";
		try {
			if (date != null) {
				strDate = ((DateFormat)format.clone()).format(date);
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				int orgYear = calendar.get(Calendar.YEAR);
				calendar.add(Calendar.YEAR, -ConstantCodes.YEAROFFSET);// 日期减1911年
				int destYear = calendar.get(Calendar.YEAR);
				strDate = strDate.replaceFirst(String.valueOf(orgYear), ( destYear < 100 ? "0" : "" ) + String.valueOf(destYear));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strDate;
	}

	/***
	 * 根据Date类型获取民国时间
	 * @param date 原始日期
	 * @return
	 * @deprecated 因無法正確轉換西元潤年2月29日所以要拿掉
	 */
	public static Date getMGDate(Date date) {
		Date dateMG = null;
		if (date != null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.YEAR, -ConstantCodes.YEAROFFSET);// 日期减1911年
			dateMG = calendar.getTime();
		}
		return dateMG;
	}

	/**
	 * 判断字符串是否为空
	 * @param str 字符串
	 * @return
	 */
	public static boolean isEmpty(String str) {
		if (str == null || str.trim().length() == 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * 判断数组是否为空
	 * @param str 字符串
	 * @return
	 */
	public static boolean isEmpty(Object[] objs) {
		if (objs == null || objs.length == 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * 字符串NULL转换为空串
	 * @param str
	 * @return
	 */
	public static String nullToEmpty(String str) {
		if(isEmpty(str)) {
			return "";
		}
		return str;
	}

	/**
	 * 判断集合是否为空
	 * @param str 字符串
	 * @return
	 */
	public static boolean isEmpty(Collection<?> collection) {
		if (collection == null || collection.size() == 0) {
			return true;
		}
		return false;
	}

	/**
	 * dwr 调用异常处理入口
	 * @author 中科软
	 * @param systemException
	 * @throws Exception
	 */
	public static void process(Throwable systemException) throws Exception {
		if (systemException != null) {
			systemException.printStackTrace();
			if (systemException instanceof HibernateJdbcException) {
				processSQLException(((HibernateJdbcException) systemException).getSQLException());
			} else if (systemException instanceof JDBCException) {
				processSQLException(((JDBCException) systemException).getSQLException());
			}
			// 其他异常暂不处理，只打印异常堆栈信息
		}
	}

	/**
	 * 调用异常处理入口
	 * @param e 异常信息
	 * @return
	 */
	public static String getLocalizedMessage(Throwable e) {
		String message = e.getLocalizedMessage();
		if (DataUtils.emptyToNull(message) == null) {
			message = e.getMessage();
		}
		return DataUtils.dbNullToEmpty(message);
	}

	/***
	 * 处理SQLException
	 * @author 中科软
	 * @date Jun 20, 2013 4:30:17 PM
	 * @param e 异常信息
	 * @throws Exception
	 */
	private static void processSQLException(SQLException e) throws Exception {
		String errorContent = getLocalizedMessage(e);
		if (StringUtils.contains(errorContent, "ORA")) {
			int oraIndex = StringUtils.indexOf(errorContent, "ORA");
			int colonIndex = StringUtils.indexOf(errorContent, ":", oraIndex);
			colonIndex = (colonIndex == -1) ? errorContent.length() : colonIndex;
			String oraCode = errorContent.substring(oraIndex, colonIndex);
			String msg = ConstantsCollection.oraErrorMessage.get(oraCode);
			if (msg != null) {
				errorContent = msg;
			}
		}
		StringBuffer buffer = new StringBuffer();
		buffer.append(errorContent);
		throw new Exception("數據庫異常" + " \n " + buffer.toString());
	}

	/**
	 * 提供精确的加法运算。
	 * @param v1 被加数
	 * @param v2 加数
	 * @return 两个参数的和
	 */
	public static double add(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.add(b2).doubleValue();
	}

	/**
	 * 提供精确的减法运算。
	 * @param v1 被减数
	 * @param v2 减数
	 * @return 两个参数的差
	 */
	public static double sub(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.subtract(b2).doubleValue();
	}

	/**
	 * 提供精确的乘法运算。
	 * @param v1 被乘数
	 * @param v2 乘数
	 * @return 两个参数的积
	 */
	public static double mul(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.multiply(b2).doubleValue();
	}

	/**
	 * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到 小数点以后10位，以后的数字四舍五入。
	 * @param v1 被除数
	 * @param v2 除数
	 * @return 两个参数的商
	 */
	public static double div(double v1, double v2) {
		return div(v1, v2, DEF_DIV_SCALE);
	}

	/**
	 * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 定精度，以后的数字四舍五入。
	 * @param v1 被除数
	 * @param v2 除数
	 * @param scale 表示表示需要精确到小数点以后几位。
	 * @return 两个参数的商
	 */
	public static double div(double v1, double v2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 提供精确的小数位四舍五入处理。
	 * @param v 需要四舍五入的数字
	 * @param scale 小数点后保留几位
	 * @return 四舍五入后的结果
	 */
	public static double round(double v, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal b = new BigDecimal(Double.toString(v));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 比较两个double型数值是否相等
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static boolean isEquals(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		if (b1.compareTo(b2) == 0) {
			return true;
		} else {
			return false;
		}
	}

	/***
	 * 将Map中所有键的值读取到与bean对象中 1、Map中key与bean的属性对应，且必须存在；
	 * 2、Map中key的值类型必须与bean对应属性的类型可转换；
	 * 3、支持从属取值map的key如id.nodeNo对应bean的id属性所在对像的nodeNo属性（如swfLog的id的nodeNo）
	 * @param bean
	 * @param param
	 * @throws Exception
	 */
	public static void setProperty(Object bean, Map<String, Object> param) throws Exception {
		if (param != null && !param.isEmpty()) {
			for (Entry<String, Object> entry : param.entrySet()) {
				PropertyUtils.setProperty(bean, entry.getKey(), entry.getValue());
			}
		}
	}
	
	/***
	 * 根据值的集合与字段名，组织sql or
	 * @param str
	 * @param columName
	 * @return
	 */
	public static String getSqlOr(String[] str,String columName){
		if(str != null && str.length > 0){
			StringBuffer sb = new StringBuffer("");
			sb.append(" and ( ");
			for(int i = 0;i< str.length ; i++){
				if(i!=0){
					sb.append(" or ");
				}
				sb.append(columName + " = '"+str[i]+"' ");
			}
			sb.append(" ) ");
			return sb.toString();
		}
		return "";
	}
	/**
	 * 通过核损类型找到定损类型,如果是定损类型，返回本身类型
	 * @param nodeType
	 * @return
	 */
	public static String getCertainNodeType(String nodeType){
		String certainNodeTyype = nodeType;
		if("verif".equals(nodeType)){
			certainNodeTyype = "certa";
		}else if("veriw".equals(nodeType)){
			certainNodeTyype = "wound";
		}else if("propv".equals(nodeType)){
			certainNodeTyype = "propc";
		}
		return certainNodeTyype;
	}
	/**
	 * 通过定损类型找到核损类型，如果没有找到，返回本身
	 * @param nodeType
	 * @return
	 */
	public static String getVerifyNodeType(String nodeType){
		String certainNodeTyype = nodeType;
		if("certa".equals(nodeType)){
			certainNodeTyype = "verif";
		}else if("wound".equals(nodeType)){
			certainNodeTyype = "veriw";
		}else if("propc".equals(nodeType)){
			certainNodeTyype = "propv";
		}
		return certainNodeTyype;
	}
	
	/***
	 * 数据导出生成excel文件
	 * @param response 
	 * @param disPlayTitile excel文档名
	 * @param disPlayClumName excel行数据的表头
	 * @param disPlayField 需要显示的对象的属性，顺序由此决定
	 * @param result
	 */
	public static void exportExcel(HttpServletRequest request,HttpServletResponse response,String disPlayTitile,String[] disPlayClumName,String[] disPlayField,List<Object> result){
		WritableWorkbook wwb = null;
		try {
			OutputStream os = response.getOutputStream();
			response.setContentType("application/ms-excel");
			String fileName = "";
            String header =  request.getHeader("User-Agent").toUpperCase();
            if (header.contains("MSIE") || header.contains("TRIDENT") || header.contains("EDGE")) {//IE
                fileName = URLEncoder.encode(disPlayTitile+".xls", "utf-8");
                fileName = fileName.replace("+", "%20");    //IE下载文件名空格变+号问题
            } else {//非IE
                fileName = new String((disPlayTitile+".xls").getBytes(), "ISO8859-1");
            }
            response.setHeader("Content-Disposition", "attachment;filename=" +fileName);
//          response.setHeader("Content-Disposition", "attachment;filename=" + new String((disPlayTitile+".xls").getBytes(), "iso-8859-1"));

			wwb = Workbook.createWorkbook(os);
			WritableSheet sheet = wwb.createSheet(disPlayTitile, 0);//第一个工作表
			CellView cellView = new CellView();
			cellView.setSize(400);
			sheet.setRowView(0, cellView);
//          cellView.setSize(10000000); 
//			cellView.setAutosize(true); //设置自动大小 
//			sheet.setColumnView(6,cellView);
			Map<String,Integer> maxClumSize = new HashMap<String,Integer>();
			jxl.write.Label label = null;
			WritableCellFormat titleCellFormat = getTitleCellFormat();//表头格式
			if(disPlayClumName!=null && disPlayClumName.length > 0){
				for (int c = 0; c <= disPlayClumName.length - 1; c++) {
					label = new jxl.write.Label(c, 0, disPlayClumName[c], titleCellFormat);
					sheet.addCell(label);
					maxClumSize.put(String.valueOf(c), disPlayClumName[c].getBytes().length);
				}
			}
			WritableCellFormat contentCellFormat = getContentCellFormat();//表内容格式
			if (result != null && !result.isEmpty()) {
				Object obj = null;//list存储的对象
				String propertyName = null;//对象的属性名
				Object propertyValue = null;//对象的属性值
				String tempValue = "";
				for (int r = 1; r <= result.size(); r++) {
					obj = result.get(r - 1);
					for(int c=0;c<=disPlayField.length-1;c++){
						propertyName = disPlayField[c];
						propertyValue = PropertyUtils.getProperty(obj, propertyName);
						if(propertyValue!=null){
							tempValue = String.valueOf(propertyValue);
							if(tempValue.length()!=0){
								initMaxClumSize(maxClumSize,c,tempValue);//setAutosize不生效，暂时以此去列中的最长字符来调整列宽
							}
						}
						label = new jxl.write.Label(c, r, tempValue, contentCellFormat);
						sheet.addCell(label);
					}
				}
			}
			for(Entry<String,Integer> entry : maxClumSize.entrySet()){
				sheet.setColumnView(Integer.parseInt(entry.getKey()),(entry.getValue()+4));
			}
			wwb.write();
			wwb.close();
		} catch (WriteException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
	}

	/***
	 * 设置每列的最大字符长
	 * @param maxClumSize 
	 * @param c 当前列索引 0开始
	 * @param entryValue 当前列的值
	 */
	private static void initMaxClumSize(Map<String,Integer> maxClumSize,int c,String entryValue){
		int tempLength = entryValue.getBytes().length;
		String tempKey = String.valueOf(c);
		if(maxClumSize.get(tempKey) < tempLength){
			maxClumSize.put(tempKey, tempLength);
		}
	}
	
	/***
	 * 表头格式
	 * @return
	 * @throws Exception
	 */
	private static WritableCellFormat getTitleCellFormat() throws Exception{
		WritableFont wftitle = new WritableFont(WritableFont.ARIAL,10,WritableFont.BOLD,false,UnderlineStyle.NO_UNDERLINE,Colour.BLACK);
		WritableCellFormat format = new WritableCellFormat(wftitle);
		format.setAlignment(Alignment.CENTRE);// 对齐方式   
		format.setVerticalAlignment(VerticalAlignment.CENTRE);
		format.setBorder(Border.ALL, BorderLineStyle.THIN);// 边框   
		format.setBackground(Colour.GREY_25_PERCENT);// 背景色   
		return format;
	}
	
	/***
	 * 表内容格式
	 * @return
	 * @throws Exception
	 */
	private static WritableCellFormat getContentCellFormat() throws Exception{
		WritableFont wftitle = new WritableFont(WritableFont.ARIAL,10,WritableFont.NO_BOLD,false,UnderlineStyle.NO_UNDERLINE,Colour.BLACK);
		WritableCellFormat format = new WritableCellFormat(wftitle);
		format.setAlignment(Alignment.CENTRE);// 对齐方式   
		format.setVerticalAlignment(VerticalAlignment.CENTRE);
		format.setBorder(Border.ALL, BorderLineStyle.THIN);// 边框   
		return format;
	}
	
	/***
	 * 将Map中的key-value映射到目标bean与key同名的属性
	 * @param bean 目标bean
	 * @param fieldMap key-value键值对，UpperCase大写的bean属性-实际的属性名
	 * @param map 封装了与bean属性同名的合集
	 */
	public static void initMapToBean(Object bean, Map<String, String> fieldMap,Map<String, Object> map) {
		if(fieldMap == null || fieldMap.isEmpty()){//为空时从bean对象的class取
			Field[] fields = bean.getClass().getDeclaredFields();
			if(fields.length > 0){
				fieldMap = new HashMap<String, String>();
				for (Field f : fields) {
					fieldMap.put(f.getName().toUpperCase(), f.getName());
				}
			}
		}
		Object keyValue = null;
		String key = null;
		String propertyName = null;
		for (Entry<String, Object> entry : map.entrySet()) {
			try {
				keyValue = entry.getValue();
				key = entry.getKey().toUpperCase();//键值大写
				propertyName = fieldMap.get(key);//属性实际的属性名拼写
				if (keyValue != null && keyValue.toString().length() > 0 && propertyName!=null) {
					PropertyUtils.setProperty(bean, propertyName, keyValue);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	/***
	 * 根據機構生成一個流水號
	 * @param comCode
	 * @return
	 */
	public static String createFlowID(String comCode) {
		String random = String.valueOf(new Random().nextInt(100));
		random = com.sinosoft.sysframework.common.util.StringUtils.newString("0", 2 - random.length()) + random;
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmssSSS");
		return comCode + sdf.format(Calendar.getInstance().getTime()) + random;
	}
		/***
	 * 从数组中取出指定索引的值
	 * @param params
	 * @param index
	 * @return
	 */
	public static String getValue(String[] params,int index){
		if(params!=null && params.length > index){
			return params[index];
		}
		return "";
	}
	/***
	 * 将字符串数组用指定分隔符链接
	 * @param array 目标数组
	 * @param separator 分隔符
	 * @return
	 */
	public static String join(String[] array,String separator){
		if(array == null || array.length == 0){
			return "";
		}else{
			StringBuffer sb = new StringBuffer();
			for(String t : array){
				sb.append(separator).append(t);
			}
			return sb.substring(separator.length()).toString();
		}
	}

	/****
	 * 获取ClassPath下的文件
	 * @param path
	 * @return
	 */
	public static File getResourceFile(String path) {
		URL url = CommonUtils.class.getClassLoader().getResource(path);
		if (url != null) {
			File tempFile = new File(url.getFile());
			if (tempFile.isFile()) {
				return tempFile;
			}
		}
		return null;
	}
	/**
	 * 获取WebRoot下文件，该方法的调用必须在request的线程范围内。
	 * @param path
	 * @return
	 */
	public static File getWebRootFile(String path) {
		ActionContext ctx = ActionContext.getContext();
		ServletContext context = (ServletContext) ctx.get(StrutsStatics.SERVLET_CONTEXT);
		if(checkFile(path)){
			try {
				String filePath = context.getRealPath(path);
				File tempFile = new File(filePath);
				if (tempFile.isFile()) {
					return tempFile;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/***
	 * 获取Double值，如果为null返回0
	 * @param object
	 * @return
	 */
	public static Double getDouble(Object object) {
		Double _double = 0d;
		if (object != null) {
			_double = new Double(object.toString());
		}
		return _double;
	}
	
	/***
	 * 对文件名的校验，目录不得含有非法字符/\:*?"<>|等
	 * @param path
	 * @return
	 */
	public static boolean checkFile(String path){
		Pattern p = Pattern.compile("[^:\\*\\?\"<>|\\\\]+.[a-z|A-Z|0-9]+$");// 文件名不能含有的非法字符/\:*?"<>|
		Matcher m = p.matcher(path);
		return m.matches();
	}
	
	/***
	 * 获取赔付对象 账户讯息 总行d代号 + 分行代号 + 汇款账户 的全名
	 * @param prpLpayObjectInfo
	 * @return
	 */
	public static String getCustomBankFullName(PrpLpayObjectInfo prpLpayObjectInfo){
		String bankCode = prpLpayObjectInfo.getBankCode();
		String customBankCode = prpLpayObjectInfo.getCustomBankCode();
		String customBankFullName = "";
		if(!CommonUtils.isEmpty(bankCode)){
			customBankFullName += bankCode+" ";
		}
		if(!CommonUtils.isEmpty(customBankCode)){
			if(!CommonUtils.isEmpty(bankCode) && customBankCode.startsWith(bankCode)){
				customBankFullName += customBankCode.substring(bankCode.length())+" ";
			}else{
				customBankFullName += customBankCode+" ";
			}
		}
		if(!CommonUtils.isEmpty(prpLpayObjectInfo.getAccountCode())){
			customBankFullName += prpLpayObjectInfo.getAccountCode();
		}
		return customBankFullName;
	}
	
	/***
	 * 带前缀的主键
	 * @param prefix
	 * @return
	 */
	public static String createKey(String prefix) {
		int random = (int) (Math.random() * 10000.0D);
		StringBuilder fix = new StringBuilder(Integer.toString(random));
		int len = 4 - fix.length();
		for (int i = 0; i < len; i++) {
			fix.insert(0, '0');
		}
		return new StringBuilder(prefix).append(String.valueOf(System.currentTimeMillis())).append(fix).toString();
	}
	
	public static String getCondition(String sign, String paramName, String paramValue, List<Object> paramList, Class<?> c) {
		StringBuffer conditions = new StringBuffer(paramName);
		if ("=".equals(sign) && String.class == c) {
			conditions.append(" = ? ");
			paramList.add(paramValue);
		} else if ("=*".equals(sign) && String.class == c) {
			conditions.append(" LIKE ? ");
			paramList.add(paramValue + "%");
		} else if (Date.class == c && ("=".equals(sign) || ">".equals(sign) || "<".equals(sign) || ">=".equals(sign) || "<=".equals(sign))) {
			conditions.append(sign).append(" TO_DATE(? , 'yyyy-mm-dd') ");
			paramList.add(paramValue);
		} else if (Double.class == c) {
			conditions.append(sign).append(" ? ");
			paramList.add(Double.valueOf(paramValue));
		} else if (Integer.class == c) {
			conditions.append(sign).append(" ? ");
			paramList.add(Integer.valueOf(paramValue));
		}
		return conditions.toString();
	}
	
	/***
	 * Cell 設置excel單元格值
	 * @param cell 單元格
	 * @param value 值
	 * @param type 值類型 0 字符  ， 1 日期 ， 2 整形 ，3 真假
	 */
	public static void setCellValue(Cell cell , Object value , int type){
		if(value != null){
			switch(type){
			case 0:
				cell.setCellValue(value.toString());
				break;
			case 1:
				cell.setCellValue((Date)value);
				break;
			case 2:
				cell.setCellValue(Double.valueOf(value.toString()));
				break;
			case 3:
				cell.setCellValue(Boolean.valueOf(value.toString()));
				break;
			default:
				
			}
		}
	}
	/**
	 * 導出受害人收據明細資料為txt
	 * @author songxin
	 * @date 2017-05-10
	 * @param response
	 * @param list
	 * @param list1
	 */
	public static void exporTxtFile(HttpServletResponse response,List<?> list,List<?> list1){
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		BufferedOutputStream buff = null;
		ServletOutputStream outStr = null;
		try {
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition", "attachment;filename=" + new String(("InjuredPersonDeatil"+".txt").getBytes(), "GBK"));
			
			outStr = response.getOutputStream();
			buff = new BufferedOutputStream(outStr);
			StringBuffer write = new StringBuffer();
			String enter = "\r\n";
			Object[] object1 = null;
			String str = "";
			
			for(int i = 0;i<list.size();i++){
				Object[] object = (Object[])list.get(i);
				//判斷是否有追償
				for(int j = 0;j<list1.size();j++){
					object1 = (Object[])list1.get(j);
					String compensateNo1 = object[0].toString().substring(1, object[0].toString().length());//賠付單號
					String compensateNo2 = object1[0].toString().substring(1, object1[0].toString().length());//追償單號
					//判斷賠付和追償的單號，出首字母以外，都一致
					if(compensateNo1.equals(compensateNo2)){
						if(Integer.parseInt(object1[1].toString())>0){
							str = "+"+object1[1].toString();
							break;
						}else{
							str = object1[1].toString();
							break;
						}
					}
				}
				write.append(showMessage(object[0].toString().substring(1, object[0].toString().length()),16));//賠案書號(截取後面的16位)
				if(null != object[1]){
					write.append(showMessage(sdf.format((Date)object[1]),8));//出險日期				
				}else{
					write.append(showMessage(object[1],8));
				}
				if(null != object[2]){
					write.append(showMessage(sdf.format((Date)object[2]),8));//賠案受理日期
				}else{
					write.append(showMessage(object[2],8));//賠案受理日期
				}
				write.append(showMessage(object[3],1));//賠付／追償次數
				write.append(showMessage(object[4],1));//賠付代號
				if(null != object[5]){
					write.append(showMessage(sdf.format((Date)object[5]),8));//結案日期
				}else{
					write.append(showMessage(object[5],8));//結案日期
				}
				if(null != object[6]){
					write.append(showMessage(sdf.format((Date)object[6]),8));//賠付/追償日期
				}else{
					write.append(showMessage(object[6],8));//賠付/追償日期
				}
				
				write.append(showMessage(object[7],1));//涉及第29條追償事項
				write.append(showMessage(object[8],3));//本車肇事責任百分比
				//未含健保之醫療理賠金額
				if(Integer.parseInt(object[9].toString())>0){
					write.append("+"+showMessage(object[9]+str,12));
				}else{
					write.append(showMessage(object[9]+str,12));
				}
				
				write.append(showMessage(object[10],1));//受害人身分代號
				write.append(showMessage(object[11],10));//證照號碼
				write.append(showMessage(object[12],1));//受害人健保醫療就醫代號
				write.append(showMessage(object[13],1));//個別受害人醫療給付是否結案且待健保追償(返還)代號
				//健保醫療收據編號
				if(object[14].toString().length() == 1){
					write.append("000"+showMessage(object[14],1));
				}else if(object[14].toString().length() == 2){
					write.append("00"+showMessage(object[14],2));
				}else if(object[14].toString().length() == 3){
					write.append("0"+showMessage(object[14],3));
				}else{
					write.append(showMessage(object[14],4));
				}
				if(null != object[15] && !"".equals(object[15])){
					write.append(showMessage(sdf.format((Date)object[15]),8));//就診期間
				}else{
					write.append(showMessage(object[15],8));
				}
				write.append(showMessage(sdf.format(new Date()),8));//資料置送日期
				write.append(enter);
			}
			buff.write(write.toString().getBytes("UTF-8"));  
            buff.flush();  
            buff.close(); 
			
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {  
                buff.close();  
                outStr.close();  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
		}
		
	}
	
	/**
	 * 導出受害人資料匯總word檔
	 * @author songxin
	 * @date  2017-05-10
	 * @param response
	 * @param dataMap
	 * @param path
	 * @throws IOException 
	 */
	public static void exportWord(HttpServletResponse response,Map<?,?> dataMap,String path) throws IOException{
		
		Configuration configuration = new Configuration();
		configuration.setDefaultEncoding("utf-8");
		try {  
            configuration.setDirectoryForTemplateLoading(new File(path));  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
		Template t = configuration.getTemplate("collecttemplate.ftl");  
        File file = null;  
        InputStream fin = null;  
        ServletOutputStream out = null;  
		
		try{
			file = createDoc(dataMap,t);
			fin = new FileInputStream(file);
			response.setCharacterEncoding("utf-8");  
			response.setContentType("application/msword");
			// 设置浏览器以下载的方式处理该文件名  
            String fileName = "InjuredPersonAll.doc";  
            response.setHeader("Content-Disposition", "attachment;filename="  
                    .concat(String.valueOf(URLEncoder.encode(fileName, "UTF-8")))); 
            
            out = response.getOutputStream();  
            byte[] buffer = new byte[512];  // 缓冲区  
            int bytesToRead = -1;  
            // 通过循环将读入的Word文件的内容输出到浏览器中  
            while((bytesToRead = fin.read(buffer)) != -1) {  
                out.write(buffer, 0, bytesToRead);  
            }  
		}catch (Exception e) {
			
		}finally{
			if(fin != null) fin.close();  
            if(out != null) out.close();  
            if(file != null) file.delete(); // 删除临时文件  
		}
	}
	/**
	 * 限定長度，并佔位
	 * @date 2017-05-10
	 * @param o
	 * @param len
	 * @return
	 */
	public static String showMessage(Object o,int len){
		String string = "";
		StringBuffer sbf=new StringBuffer();
		if(o!=null){
			if(o.toString().length()<len){
				int length=len-o.toString().length();
				for(int i=0;i<length;i++){
					sbf.append(" ");
				}
			}
			string = o.toString()+sbf.toString();
			
		}else{
			for(int i=0;i<len;i++){
				sbf.append(" ");
			}
			string = sbf.toString();
		}
		return string;
	}
	
	private static File createDoc(Map<?, ?> dataMap, Template template) {  
        String name =  "InjuredPersonAll.doc";  
        File f = new File(name);  
        Template t = template;  
        try {  
            // 这个地方不能使用FileWriter因为需要指定编码类型否则生成的Word文档会因为有无法识别的编码而无法打开  
            Writer w = new OutputStreamWriter(new FileOutputStream(f), "utf-8");  
            t.process(dataMap, w);  
            w.close();  
        } catch (Exception ex) {  
            ex.printStackTrace();  
            throw new RuntimeException(ex);  
        }  
        return f;  
    }  
	/**
     * 獲取DateTime實例，字符串為空時返回null
     * @param dateString
     * @param type
     * @return
     */
    public static DateTime getDateTimeInstance(String dateString, int type){
        if(dateString!=null&&dateString.length()>0){
            return new DateTime(dateString,type);
        }else{
            return null;
        }
    }
}
