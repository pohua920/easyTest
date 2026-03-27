package com.sinosoft.undwrt.common.util;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.views.jsp.TagUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 民國日期顯示標籤庫.
 * 
 * @author sinosoft
 */
public class RCDateTag extends TagSupport {

	/** 民國年開始時的西元年. */
	private static final int R_C_START_YEAR = 1911;

	/** 頁面輸入域的名稱. */
	private String name;

	/** 日期格式. */
	private String format;

	/** 頁面輸入域的值. */
	private Date value;

	/**
	 * 頁面標籤調用執行方法.
	 * 
	 * @return the int
	 * @throws JspException
	 *             the jsp exception
	 */
	@Override
	public int doStartTag() throws JspException {
		try {
			if (value != null) {
				print(value);
				return SKIP_BODY;
			}

			// struts2的迭代过程默认为top
			if (StringUtils.isBlank(name)) {
				name = "top";
			}
			if (name != null) {
				Object obj = TagUtils.getStack(pageContext).findValue(name);
				if (obj != null && obj instanceof Date) {
					print((Date) obj);
				} else if (obj != null && obj instanceof String) {
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
					Date date = simpleDateFormat.parse((String) obj);
					print(date);
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.doStartTag();
	}

	/**
	 * 將民國日期寫回頁面.
	 * 
	 * @param date
	 *            日期類型
	 * @throws IOException
	 *             IO異常
	 */
	void print(Date date) throws IOException {
		String dataPrint = changeDateToString(date);
		String rcyear = getRCYear(date);
		pageContext.getOut().print(format(rcyear, dataPrint));
	}

	/**
	 * 格式化日期格式.
	 * 
	 * @param rcyear
	 *            the rcyear
	 * @param dataPrint
	 *            the data print
	 * @return the string
	 */
	String format(String rcyear, String dataPrint) {
		int yearPlace = format.lastIndexOf("y") + 1;
		return rcyear + dataPrint.substring(yearPlace);
	}

	/**
	 * 格式化日期.
	 * 
	 * @param date
	 *            日期格式
	 * @return 字符串格式日期
	 */
	String changeDateToString(Date date) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		return simpleDateFormat.format(date);
	}

	/**
	 * 日期轉換.
	 * 
	 * @param date
	 *            日期格式
	 * @return 民國日期
	 */
	String getRCYear(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int rcyear = calendar.get(Calendar.YEAR) - R_C_START_YEAR;
		return String.valueOf(rcyear);

	}

	/**
	 * 獲取頁面輸入域名稱.
	 * 
	 * @return the 頁面輸入域的名稱
	 */
	public String getName() {
		return name;
	}

	/**
	 * 設置頁面輸入域名稱.
	 * 
	 * @param name
	 *            the new 頁面輸入域的名稱
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 獲取日期格式.
	 * 
	 * @return the 日期格式
	 */
	public String getFormat() {
		return format;
	}

	/**
	 * 設置日期格式.
	 * 
	 * @param format
	 *            the new 日期格式
	 */
	public void setFormat(String format) {
		this.format = format;
	}

	/**
	 * 設置頁面輸入域值.
	 * 
	 * @param value_
	 *            the new 頁面輸入域的值
	 */
	public void setValue(Date value_) {
		this.value = value_;
	}

}
