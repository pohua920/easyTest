/**
* @author 周柳 E-mail:zhouliu@sinosoft.com
* @version 创建时间：2007-9-4 下午05:13:28
* @company 中科软科技股份有限公司
* @version 1.0
 */
package com.sinosoft.undwrt.common.util;
import ins.framework.utils.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The Class HqlRulesUtil.
 */
public final class HqlRulesUtil {
	
	/** 屬性The sinosoft hql. */
	private StringBuffer hql = new StringBuffer("");

	/**
	 * Instantiates a new hql rules util.
	 */
	public HqlRulesUtil() {

	}

	/**
	 * Adds the equal.
	 * 
	 * @param name
	 *            the name
	 * @param value
	 *            the value
	 * @return the hql rules util
	 */
	public HqlRulesUtil addEqual(String name, Long value) {
		if (value != null && !(value.equals(""))) {
			hql.append(" And " + name + "=" + value);
		}
		return this;
	}

	/**
	 * Adds the equal.
	 * 
	 * @param name
	 *            the name
	 * @param value
	 *            the value
	 * @return the hql rules util
	 */
	public HqlRulesUtil addEqual(String name, Integer value) {
		if (value != null && !(value.equals(""))) {
			hql.append(" And " + name + "=" + value);
		}
		return this;
	}

	/**
	 * Adds the equal.
	 * 
	 * @param name
	 *            the name
	 * @param value
	 *            the value
	 * @return the hql rules util
	 */
	public HqlRulesUtil addEqual(String name, String value) {
		if (value != null && !(value.equals(""))) {
			hql.append(" And " + name + "='" + value + "'");
		}
		return this;
	}
	
	/**
	 * Adds the not equal.
	 * 
	 * @param name
	 *            the name
	 * @param value
	 *            the value
	 * @return the hql rules util
	 */
	public HqlRulesUtil addNotEqual(String name, Long value) {
		if (value != null && !(value.equals(""))) {
			hql.append(" And " + name + "!=" + value);
		}
		return this;
	}

	/**
	 * Adds the not equal.
	 * 
	 * @param name
	 *            the name
	 * @param value
	 *            the value
	 * @return the hql rules util
	 */
	public HqlRulesUtil addNotEqual(String name, Integer value) {
		if (value != null && !(value.equals(""))) {
			hql.append(" And " + name + "!=" + value);
		}
		return this;
	}

	/**
	 * Adds the not equal.
	 * 
	 * @param name
	 *            the name
	 * @param value
	 *            the value
	 * @return the hql rules util
	 */
	public HqlRulesUtil addNotEqual(String name, String value) {
		if (value != null && !(value.equals(""))) {
			hql.append(" And " + name + "!='" + value + "'");
		}
		return this;
	}
	
	/**
	 * Adds the is null.
	 * 
	 * @param name
	 *            the name
	 * @return the hql rules util
	 */
	public HqlRulesUtil addIsNull(String name) {
			hql.append(" And " + name + " is null ");
		return this;
	}
	
	/**
	 * Adds the equal.
	 * 
	 * @param name
	 *            the name
	 * @param value
	 *            the value
	 * @param timeFlag
	 *            the time flag
	 * @return the hql rules util
	 */
	public HqlRulesUtil addEqual(String name, Date value,
			boolean timeFlag) {
		if (value != null) {
			if (timeFlag) {
				SimpleDateFormat bartDateFormat = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				hql.append(" And " + name + "='" + bartDateFormat.format(value)
						+ "'");
			} else {
				SimpleDateFormat bartDateFormat = new SimpleDateFormat(
						"yyyy-MM-dd");
				hql.append(" And " + name + "=to_date('"
						+ bartDateFormat.format(value) + "','yyyy-MM-dd')");
			}
		}
		return this;
	}

	/**
	 * Adds the between.
	 * 
	 * @param name
	 *            the name
	 * @param begin
	 *            the begin
	 * @param end
	 *            the end
	 * @return the hql rules util
	 */
	public HqlRulesUtil addBetween(String name, Long begin, Long end) {
		if (begin != null && end != null) {
			hql.append(" And " + name + " between " + begin + " And " + end);
		}
		return this;
	}

	/**
	 * Adds the between.
	 * 
	 * @param name
	 *            the name
	 * @param begin
	 *            the begin
	 * @param end
	 *            the end
	 * @param timeFlag
	 *            the time flag
	 * @return the hql rules util
	 */
	public HqlRulesUtil addBetween(String name, Date begin, Date end,
			boolean timeFlag) {
		if (begin != null && end != null) {
			if (timeFlag) {
				SimpleDateFormat bartDateFormat = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				hql.append(" And " + name + " between '"
						+ bartDateFormat.format(begin) + "' And '"
						+ bartDateFormat.format(end) + "'");
			} else {
				SimpleDateFormat bartDateFormat = new SimpleDateFormat(
						"yyyy-MM-dd");
				hql.append(" And " + name + " between to_date('"
						+ bartDateFormat.format(begin)
						+ "','yyyy-MM-dd') And to_date('"
						+ bartDateFormat.format(end) + "','yyyy-MM-dd')");
			}
		}
		return this;
	}

	/**
	 * Adds the between.
	 * 
	 * @param name
	 *            the name
	 * @param begin
	 *            the begin
	 * @param end
	 *            the end
	 * @return the hql rules util
	 */
	public HqlRulesUtil addBetween(String name, Integer begin, Integer end) {
		if (begin != null && end != null) {
			hql.append(" And " + name + " between " + begin + " And " + end);
		}
		return this;
	}

	/**
	 * Adds the greater equal.
	 * 
	 * @param name
	 *            the name
	 * @param value
	 *            the value
	 * @param timeFlag
	 *            the time flag
	 * @return the hql rules util
	 */
	public HqlRulesUtil addGreaterEqual(String name, Date value,
			boolean timeFlag) {
		if (value != null) {
			if (timeFlag) {
				SimpleDateFormat bartDateFormat = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				hql.append(" And " + name + ">='"
						+ bartDateFormat.format(value) + "'");
			} else {
				SimpleDateFormat bartDateFormat = new SimpleDateFormat(
						"yyyy-MM-dd");
				hql.append(" And " + name + ">=to_date('"
						+ bartDateFormat.format(value) + "','yyyy-MM-dd')");
			}
		}
		return this;
	}
	
	/**
	 * Adds the greater equal.
	 * 
	 * @param name
	 *            the name
	 * @param value
	 *            the value
	 * @return the hql rules util
	 */
	public HqlRulesUtil addGreaterEqual(String name, double value) {
		hql.append(" And " + name + ">="+ value + "");
		return this;
	}
	
	/**
	 * Adds the less than.
	 * 
	 * @param name
	 *            the name
	 * @param value
	 *            the value
	 * @return the hql rules util
	 */
	public HqlRulesUtil addLessThan(String name, double value) {
		hql.append(" And " + name + "<"+ value + "");
		return this;
	}

	/**
	 * Adds the greater than.
	 * 
	 * @param name
	 *            the name
	 * @param value
	 *            the value
	 * @return the hql rules util
	 */
	public HqlRulesUtil addGreaterThan(String name, double value) {
		hql.append(" And " + name + ">"+ value + "");
		return this;
	}
	
	/**
	 * Adds the less equal.
	 * 
	 * @param name
	 *            the name
	 * @param value
	 *            the value
	 * @return the hql rules util
	 */
	public HqlRulesUtil addLessEqual(String name, double value) {
		hql.append(" And " + name + "<="+ value + "");
		return this;
	}	
	
	/**
	 * Adds the greater than.
	 * 
	 * @param name
	 *            the name
	 * @param value
	 *            the value
	 * @param timeFlag
	 *            the time flag
	 * @return the hql rules util
	 */
	public HqlRulesUtil addGreaterThan(String name, Date value,
			boolean timeFlag) {
		if (value != null) {
			if (timeFlag) {
				SimpleDateFormat bartDateFormat = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				hql.append(" And " + name + ">'" + bartDateFormat.format(value)
						+ "'");
			} else {
				SimpleDateFormat bartDateFormat = new SimpleDateFormat(
						"yyyy-MM-dd");
				hql.append(" And " + name + ">to_date('"
						+ bartDateFormat.format(value) + "','yyyy-MM-dd')");
			}
		}
		return this;
	}

	/**
	 * Adds the less equal.
	 * 
	 * @param name
	 *            the name
	 * @param value
	 *            the value
	 * @param timeFlag
	 *            the time flag
	 * @return the hql rules util
	 */
	public HqlRulesUtil addLessEqual(String name, Date value,
			boolean timeFlag) {
		if (value != null) {
			if (timeFlag) {
				SimpleDateFormat bartDateFormat = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				hql.append(" And " + name + "<='"
						+ bartDateFormat.format(value) + "'");
			} else {
				SimpleDateFormat bartDateFormat = new SimpleDateFormat(
						"yyyy-MM-dd");
				hql.append(" And " + name + "<=to_date('"
						+ bartDateFormat.format(value) + "','yyyy-MM-dd')");
			}
		}
		return this;
	}

	/**
	 * Adds the less than.
	 * 
	 * @param name
	 *            the name
	 * @param value
	 *            the value
	 * @param timeFlag
	 *            the time flag
	 * @return the hql rules util
	 */
	public HqlRulesUtil addLessThan(String name, Date value,
			boolean timeFlag) {
		if (value != null) {
			if (timeFlag) {
				SimpleDateFormat bartDateFormat = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				hql.append(" And " + name + "<'" + bartDateFormat.format(value)
						+ "'");
			} else {
				SimpleDateFormat bartDateFormat = new SimpleDateFormat(
						"yyyy-MM-dd");
				hql.append(" And " + name + "<to_date('"
						+ bartDateFormat.format(value) + "','yyyy-MM-dd')");
			}
		}
		return this;
	}

	/**
	 * Adds the like.
	 * 
	 * @param name
	 *            the name
	 * @param value
	 *            the value
	 * @return the hql rules util
	 */
	public HqlRulesUtil addLike(String name, String value) {
		if (value != null && !(value.equals(""))) {
			if (value.indexOf('*') >= 0 || value.indexOf('%') >= 0) { // zhhd：如果用户输入了匹配符，则按用户所输匹配
				value = StringUtils.replace(value, "*", "%");
				if(value.startsWith("%")){
					value=value.substring(1);
				}
			} else {
				value =  value + "%";
			}
			hql.append(" And " + name + " like '" + value + "'");

		}
		return this;
	}
	
	/**
	 * Adds the not like.
	 * 
	 * @param name
	 *            the name
	 * @param value
	 *            the value
	 * @return the hql rules util
	 */
	public HqlRulesUtil addNotLike(String name, String value) {
		if (value != null && !(value.equals(""))) {
			if (value.indexOf('*') >= 0 || value.indexOf('%') >= 0) { // zhhd：如果用户输入了匹配符，则按用户所输匹配
				value = StringUtils.replace(value, "*", "%");
				if(value.startsWith("%")){
					value=value.substring(1);
				}
			} else {
				value =  value + "%";
			}
			hql.append(" And " + name + " not like '" + value + "'");

		}
		return this;
	}	
	
	/**
	 * Adds the in.
	 * 
	 * @param name
	 *            the name
	 * @param value
	 *            the value
	 * @return the hql rules util
	 */
	public HqlRulesUtil addIn(String name ,String[] value){
		if (value!=null && value.length>1) {
			StringBuffer inCondition = new StringBuffer("");
			for (int i = 0; i < value.length; i++) {
				if (i != value.length-1) {
					inCondition.append("'"+value[i] + "',");
				} else {
					inCondition.append("'"+value[i]+"'");
				}
			}
			hql.append(" And " + name + " in ("+inCondition.toString()+")");
		}
		return this;
	}
	
	/**
	 * Adds the in.
	 * 
	 * @param name
	 *            the name
	 * @param value
	 *            the value
	 * @return the hql rules util
	 */
	public HqlRulesUtil addIn(String name ,String value){
		String[] valueList = value.split(",");
		if (valueList!=null && valueList.length>0) {
			StringBuffer inCondition = new StringBuffer("");
			for (int i = 0; i < valueList.length; i++) {
				if (i != valueList.length-1) {
					inCondition.append("'"+valueList[i] + "',");
				} else {
					inCondition.append("'"+valueList[i]+"'");
				}
			}
			hql.append(" And " + name + " in ("+inCondition.toString()+")");
		}
		return this;
	}	

	/**
	 * Adds the.
	 * 
	 * @param sql
	 *            the sql
	 * @return the hql rules util
	 */
	public HqlRulesUtil add(String sql) {
		if (sql != null && !(sql.equals(""))) {
			hql.append(sql);
		}
		return this;
	}

	/**
	 * 獲取屬性the sinosoft hql.
	 * 
	 * @return 屬性the sinosoft hql的值
	 */
	public String getHql() {

		String hqlString = hql.toString();
		if (hqlString != null && !(hqlString.equals(""))) {
			hqlString = hqlString.substring(4);
		}
		return hqlString;
	}

}
