package com.sinosoft.claim.common.util;

import javax.servlet.http.HttpServletRequest;

import com.sinosoft.sysframework.common.util.StringUtils;

public class UIQueryAction {
	public UIQueryAction() {
	}

	/**
	 * 得到查询条件前缀标签
	 * @param httpServletRequest
	 * @param String[]：查询条件变量
	 * @return String[]：查询条件标签值
	 * @author 中科软
	 */
	public String[] getTags(HttpServletRequest httpServletRequest, String[] parameters) {
		String[] tags = null;// 查询条件前缀标签值
		int i = 0;
		if (parameters != null) {
			tags = new String[parameters.length];
			for (i = 0; i < parameters.length; i++) {
				tags[i] = httpServletRequest.getParameter(parameters[i] + "Tag");
			}
		}
		return tags;
	}

	/**
	 * 得到查询条件值
	 * @param httpServletRequest
	 * @param String[]：查询条件变量
	 * @return String[]：查询条件值
	 * @author 中科软
	 */
	public String[] getValues(HttpServletRequest httpServletRequest, String[] parameters) {
		String[] values = null;// 查询条件值
		int i = 0;
		if (parameters != null) {
			values = new String[parameters.length];
			for (i = 0; i < parameters.length; i++) {
				values[i] = StringUtils.rightTrim(StringUtils.leftTrim(httpServletRequest.getParameter(parameters[i])));
			}
		}
		return values;
	}

	/**
	 * 得到查询条件where语句
	 * @param httpServletRequest
	 * @param String[]：查询条件变量
	 * @param String[]：查询条件变量数据类型
	 * @param String[]：查询条件标签值
	 * @param String[]：查询条件变量值
	 * @return String 查询条件where语句
	 * @author 中科软
	 */
	public String getConditions(HttpServletRequest httpServletRequest, String[] parameters, String[] datatypes, String[] tags, String[] values) {
		String conditions = "1=1 ";// 查询条件where语句

		int i = 0;
		if (parameters != null) {
			for (i = 0; i < parameters.length; i++) {
				if (datatypes[i].equals("0")) {
					conditions += this.getCharConditions(parameters[i], tags[i], values[i]);
				} else if (datatypes[i].equals("1")) {
					conditions += this.getNumConditions(httpServletRequest, parameters[i], tags[i], values[i]);
				} else if (datatypes[i].equals("2")) {
					conditions += this.getDateConditions(httpServletRequest, parameters[i], tags[i], values[i]);
				}
			}
		}
		return conditions;
	}

	/**
	 * 得到字符型查询条件where语句
	 * @param String：查询条件变量
	 * @param String：查询条件标签值
	 * @param String：查询条件变量值
	 * @return String 查询条件where语句
	 * @author 中科软
	 */
	public String getCharConditions(String columns, String tag, String value) {
		String condition = ""; // 查询条件where语句

		if (value == null || value.equals("")) {
		} else {
			if (tag == null) {
				condition += " AND " + columns + " = '" + value + "' ";
			} else {
				if (tag.equals("=")) {
					condition += " AND " + columns + " = '" + value + "' ";
				} else if (tag.equals("*")) {
					condition += " AND " + columns + " LIKE '%" + value + "%' ";
				} else if (tag.equals("=*")) {
					condition += " AND " + columns + " LIKE '" + value + "%' ";
				} else if (tag.equals(">")) {
					condition += " AND " + columns + " > '" + value + "' ";
				} else if (tag.equals("<")) {
					condition += " AND " + columns + " < '" + value + "' ";
				} else if (tag.equals(">=")) {
					condition += " AND " + columns + " >= '" + value + "' ";
				} else if (tag.equals("<=")) {
					condition += " AND " + columns + " <= '" + value + "' ";
				} else {
					condition += " AND " + columns + " = '" + value + "' ";
				}
			}
		}
		return condition;
	}

	/**
	 * 得到数字型查询条件where语句
	 * @param httpServletRequest
	 * @param String：查询条件变量
	 * @param String：查询条件标签值
	 * @param String：查询条件变量值
	 * @return String 查询条件where语句
	 * @author 中科软
	 */
	public String getNumConditions(HttpServletRequest httpServletRequest, String parameter, String tag, String value) {
		String condition = "";// 查询条件where语句

		if (value == null || value.equals("")) {
		} else {
			if (tag == null) {
				condition += " AND " + parameter + " = " + value + " ";
			} else {
				if (tag.equals("=")) {
					condition += " AND " + parameter + " = " + value + " ";
				} else if (tag.equals(">")) {
					condition += " AND " + parameter + " > " + value + " ";
				} else if (tag.equals("<")) {
					condition += " AND " + parameter + " < " + value + " ";
				} else if (tag.equals(">=")) {
					condition += " AND " + parameter + " >= " + value + " ";
				} else if (tag.equals("<=")) {
					condition += " AND " + parameter + " <= " + value + " ";
				} else if (tag.equals(":")) {
					int index = value.indexOf(':');

					if (index > -1) {
						String strValueStart = value.substring(0, index);
						String strValueEnd = value.substring(index + 1);
						condition += " AND " + parameter + " >= " + strValueStart + " AND " + parameter + "<= " + strValueEnd + " ";
					} else {
						condition += " AND " + parameter + " >= " + value + " ";
					}
				} else {
					condition += " AND " + parameter + " = " + value + " ";
				}
			}
		}
		return condition;
	}

	/**
	 * 得到日期型查询条件where语句
	 * @param httpServletRequest
	 * @param String：查询条件变量
	 * @param String：查询条件标签值
	 * @param String：查询条件变量值
	 * @return String 查询条件where语句
	 * @author 中科软
	 */
	public String getDateConditions(HttpServletRequest httpServletRequest, String parameter, String tag, String value) {
		String condition = "";// 查询条件where语句

		if (value == null || value.equals("")) {
		} else {
			if (tag == null) {
				condition += " AND " + parameter + " = to_date('" + value + "','yyyy-mm-dd HH24:MI:SS') ";
			} else {
				if (tag.equals("=")) {
					condition += " AND " + parameter + " = to_date('" + value + "','yyyy-mm-dd HH24:MI:SS') ";
				} else if (tag.equals(">")) {
					condition += " AND " + parameter + " > to_date('" + value + "','yyyy-mm-dd HH24:MI:SS') ";
				} else if (tag.equals("<")) {
					condition += " AND " + parameter + " < to_date('" + value + "','yyyy-mm-dd HH24:MI:SS') ";
				} else if (tag.equals(">=")) {
					condition += " AND " + parameter + " >= to_date('" + value + "','yyyy-mm-dd HH24:MI:SS') ";
				} else if (tag.equals("<=")) {
					condition += " AND " + parameter + " <= to_date('" + value + "','yyyy-mm-dd HH24:MI:SS') ";
				} else if (tag.equals(":")) {
					int index = value.indexOf(':');

					if (index > -1) {
						String strValueStart = value.substring(0, index);
						String strValueEnd = value.substring(index + 1);
						condition += " AND " + parameter + " >= to_date('" + strValueStart + "','yyyy-mm-dd HH24:MI:SS') AND " + parameter + "<= to_date('" + strValueEnd + "','yyyy-mm-dd HH24:MI:SS') ";
					} else {
						condition += " AND " + parameter + " >= to_date('" + value + "','yyyy-mm-dd HH24:MI:SS') ";
					}
				} else {
					condition += " AND " + parameter + " = to_date('" + value + "','yyyy-mm-dd HH24:MI:SS') ";
				}
			}
		}
		return condition;
	}

	/**
	 * 拼装where条件的in sql语句 格式 and column in('','')
	 * @param column 列名
	 * @param value 列名的值
	 * @return and column in('','')
	 */
	public String getCharInConditions(String column, String[] value) {
		StringBuffer conditions = new StringBuffer(200);
		if (value != null && value.length > 0) {
			conditions.append(" AND ").append(column).append(" in(");
			for (int i = 0; i < value.length; i++) {
				conditions.append("'").append(value[i]).append("'");
				if (i + 1 != value.length) {
					conditions.append(", ");
				}
			}
			conditions.append(")");
		}
		return conditions.toString();
	}
}