/**
 * @author 周柳 E-mail:zhouliu@sinosoft.com
 * @version 创建时间：2007-9-4 下午05:13:28
 * @company 中科软科技股份有限公司
 * @version 1.0
 */
package cn.com.sinosoft.saa.util;

import ins.framework.utils.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.com.sinosoft.ims.util.IConstants;
import cn.com.sinosoft.ims.util.ReadProperties;

public final class HqlRulesUtil {
	private StringBuffer hql = new StringBuffer("");

	public HqlRulesUtil() {

	}

	public HqlRulesUtil addEqual(String name, Long value) {
		if (value != null && !(value.equals(""))) {
			hql.append(" And " + name + "=" + value);
		}
		return this;
	}

	public HqlRulesUtil addEqual(String name, Integer value) {
		if (value != null && !(value.equals(""))) {
			hql.append(" And " + name + "=" + value);
		}
		return this;
	}

	public HqlRulesUtil addEqual(String name, String value) {
		if (value != null && !(value.equals(""))) {
			hql.append(" And " + name + "='" + value + "'");
		}
		return this;
	}

	public HqlRulesUtil addEqual(String name, Date value, boolean timeFlag) {
		if (value != null) {
			if (timeFlag) {
				SimpleDateFormat bartDateFormat = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				hql.append(" And " + name + "='" + bartDateFormat.format(value)
						+ "'");
			} else {
				SimpleDateFormat bartDateFormat = new SimpleDateFormat(
						"yyyy-MM-dd");
				String database = ReadProperties.getString("database");
				if (IConstants.DB_ORACLE.equals(database)) {
					hql.append(" And " + name + "=to_date('"
							+ bartDateFormat.format(value) + "','yyyy-MM-dd')");
				} else {
					hql.append(" And " + name + "=date('"
							+ bartDateFormat.format(value) + "')");
				}
			}
		}
		return this;
	}

	public HqlRulesUtil addBetween(String name, Long begin, Long end) {
		if (begin != null && end != null) {
			hql.append(" And " + name + " between " + begin + " And " + end);
		}
		return this;
	}

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
				String database = ReadProperties.getString("database");
				if (IConstants.DB_ORACLE.equals(database)) {
					hql.append(" And " + name + " between to_date('"
							+ bartDateFormat.format(begin)
							+ "','yyyy-MM-dd') And to_date('"
							+ bartDateFormat.format(end) + "','yyyy-MM-dd')");
				} else {
					hql.append(" And " + name + " between date('"
							+ bartDateFormat.format(begin) + "') And date('"
							+ bartDateFormat.format(end) + "')");
				}
			}
		}
		return this;
	}

	public HqlRulesUtil addBetween(String name, Integer begin, Integer end) {
		if (begin != null && end != null) {
			hql.append(" And " + name + " between " + begin + " And " + end);
		}
		return this;
	}

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
				String database = ReadProperties.getString("database");
				if (IConstants.DB_ORACLE.equals(database)) {
					hql.append(" And " + name + ">=to_date('"
							+ bartDateFormat.format(value) + "','yyyy-MM-dd')");
				} else {
					hql.append(" And " + name + ">=date('"
							+ bartDateFormat.format(value) + "')");
				}
			}
		}
		return this;
	}

	public HqlRulesUtil addGreaterThan(String name, Date value, boolean timeFlag) {
		if (value != null) {
			if (timeFlag) {
				SimpleDateFormat bartDateFormat = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				hql.append(" And " + name + ">'" + bartDateFormat.format(value)
						+ "'");
			} else {
				SimpleDateFormat bartDateFormat = new SimpleDateFormat(
						"yyyy-MM-dd");
				String database = ReadProperties.getString("database");
				if (IConstants.DB_ORACLE.equals(database)) {
					hql.append(" And " + name + ">to_date('"
							+ bartDateFormat.format(value) + "','yyyy-MM-dd')");
				} else {
					hql.append(" And " + name + ">date('"
							+ bartDateFormat.format(value) + "')");
				}
			}
		}
		return this;
	}

	public HqlRulesUtil addLessEqual(String name, Date value, boolean timeFlag) {
		if (value != null) {
			if (timeFlag) {
				SimpleDateFormat bartDateFormat = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				hql.append(" And " + name + "<='"
						+ bartDateFormat.format(value) + "'");
			} else {
				SimpleDateFormat bartDateFormat = new SimpleDateFormat(
						"yyyy-MM-dd");
				String database = ReadProperties.getString("database");
				if (IConstants.DB_ORACLE.equals(database)) {
					hql.append(" And " + name + "<=to_date('"
							+ bartDateFormat.format(value) + "','yyyy-MM-dd')");
				} else {
					hql.append(" And " + name + "<=date('"
							+ bartDateFormat.format(value) + "')");
				}
			}
		}
		return this;
	}

	public HqlRulesUtil addLessThan(String name, Date value, boolean timeFlag) {
		if (value != null) {
			if (timeFlag) {
				SimpleDateFormat bartDateFormat = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				hql.append(" And " + name + "<'" + bartDateFormat.format(value)
						+ "'");
			} else {
				SimpleDateFormat bartDateFormat = new SimpleDateFormat(
						"yyyy-MM-dd");
				String database = ReadProperties.getString("database");
				if (IConstants.DB_ORACLE.equals(database)) {
					hql.append(" And " + name + "<to_date('"
							+ bartDateFormat.format(value) + "','yyyy-MM-dd')");
				} else {
					hql.append(" And " + name + "<date('"
							+ bartDateFormat.format(value) + "')");
				}
			}
		}
		return this;
	}

	public HqlRulesUtil addLike(String name, String value) {
		if (value != null && !(value.equals(""))) {
			if (value.indexOf('*') >= 0 || value.indexOf('%') >= 0) { // zhhd：如果用户输入了匹配符，则按用户所输匹配
				value = StringUtils.replace(value, "*", "%");
				if (value.startsWith("%")) {
					value = value.substring(1);
				}
			} else {
				value = value + "%";
			}
			hql.append(" And " + name + " like '" + value + "'");

		}
		return this;
	}

	public HqlRulesUtil addIn(String name, String[] value) {
		if (value != null && value.length > 1) {
			StringBuffer inCondition = new StringBuffer("");
			for (int i = 0; i < value.length; i++) {
				if (i != value.length - 1) {
					inCondition.append("'" + value[i] + "',");
				} else {
					inCondition.append("'" + value[i] + "'");
				}
			}
			hql.append(" And " + name + " in (" + inCondition.toString() + ")");
		}
		return this;
	}

	public HqlRulesUtil add(String sql) {
		if (sql != null && !(sql.equals(""))) {
			hql.append(sql);
		}
		return this;
	}

	public String getHql() {

		String hqlString = hql.toString();
		if (hqlString != null && !(hqlString.equals(""))) {
			hqlString = hqlString.substring(4);
		}
		return hqlString;
	}

}
