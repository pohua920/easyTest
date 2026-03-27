package com.sinosoft.undwrt.common.util;

/**
 * <p>Title: UIQueryAction</p>
 * <p>Description: 查询语句生产公用类</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Sinosoft</p>
 * @author luyang
 * @version 1.0
 */
import javax.servlet.http.*;
import com.sinosoft.sysframework.common.util.StringUtils;
import com.sinosoft.sysframework.common.datatype.DateTime;

/**
 * The Class QueryAction.
 */
public class QueryAction
{
    
    /**
	 * Instantiates a new query action.
	 */
    public QueryAction()
    {
    }
    
    /**
	 * 得到查询条件前缀标签.
	 * 
	 * @param httpServletRequest
	 *            HttpServletRequest
	 * @param parameters
	 *            String[]
	 * @return String[]
	 */
    public String[] getTags(HttpServletRequest httpServletRequest, String[] parameters)
    {
        String[] tags = null; //查询条件前缀标签值
        int i = 0;
        if (parameters != null)
        {
            tags = new String[parameters.length];
            for (i = 0; i < parameters.length; i++)
            {
                tags[i] = httpServletRequest.getParameter(parameters[i] + "Tag");
            }
        }
        return tags;
    }
    
    /**
	 * 得到查询条件值.
	 * 
	 * @param httpServletRequest
	 *            HttpServletRequest
	 * @param parameters
	 *            String[]
	 * @return String[]
	 */
    public String[] getValues(HttpServletRequest httpServletRequest, String[] parameters)
    {
        String[] values = null; //查询条件值
        int i = 0;
        if (parameters != null)
        {
            values = new String[parameters.length];
            for (i = 0; i < parameters.length; i++)
            {
                
                values[i] = StringUtils.rightTrim(StringUtils.leftTrim(httpServletRequest.getParameter(parameters[i])));
                
            }
        }
        return values;
    }
    
    /**
	 * 得到查询条件where语句.
	 * 
	 * @param httpServletRequest
	 *            HttpServletRequest
	 * @param parameters
	 *            String[]
	 * @param datatypes
	 *            String[]
	 * @param tags
	 *            String[]
	 * @param values
	 *            String[]
	 * @param columns
	 *            String[]
	 * @return String
	 */
    public String getConditions(HttpServletRequest httpServletRequest, String[] parameters, String[] datatypes,
            String[] tags, String[] values, String[] columns)
    {
        String conditions = "1=1 "; //查询条件where语句
        int i = 0;
        if (columns == null || columns.length == 0)
        {
            columns = new String[parameters.length];
            for (i = 0; i < parameters.length; i++)
            {
                columns[i] = parameters[i];
            }
        }
        
        if (columns != null)
        {
            for (i = 0; i < columns.length; i++)
            {
                if (datatypes[i].equals("0"))
                {
                    conditions += this.getCharConditions(columns[i], tags[i], values[i]);
                }
                else if (datatypes[i].equals("1"))
                {
                    conditions += this.getNumConditions(httpServletRequest, columns[i], tags[i], values[i]);
                }
                else if (datatypes[i].equals("2"))
                {
                    conditions += this.getDateCharConditions(columns[i], tags[i], values[i]);
                }
            }
        }
        return conditions;
    }
    
    /**
	 * 得到字符型查询条件where语句.
	 * 
	 * @param columns
	 *            String
	 * @param tag
	 *            String
	 * @param value
	 *            String
	 * @return String
	 */
    public String getCharConditions(String columns, String tag, String value)
    {
        String condition = ""; //查询条件where语句
        
        if (value == null || value.equals(""))
        {}
        else
        {
            if (tag == null)
            {
                condition += " AND " + columns + " = '" + value + "' ";
            }
            else
            {
                if (tag.equals("="))
                {
                    condition += " AND " + columns + " = '" + value + "' ";
                }
                else if (tag.equals("*"))
                {
                    condition += " AND " + columns + " LIKE '%" + value + "%' ";
                }
                else if (tag.equals(">"))
                {
                    condition += " AND " + columns + " > '" + value + "' ";
                }
                else if (tag.equals("<"))
                {
                    condition += " AND " + columns + " < '" + value + "' ";
                }
                else if (tag.equals(">="))
                {
                    condition += " AND " + columns + " >= '" + value + "' ";
                }
                else if (tag.equals("<="))
                {
                    condition += " AND " + columns + " <= '" + value + "' ";
                }
                else
                {
                    condition += " AND " + columns + " = '" + value + "' ";
                }
            }
        }
        return condition;
    }
    
    /**
	 * 得到数字型查询条件where语句.
	 * 
	 * @param httpServletRequest
	 *            HttpServletRequest
	 * @param columns
	 *            String
	 * @param tag
	 *            String
	 * @param value
	 *            String
	 * @return String
	 */
    public String getNumConditions(HttpServletRequest httpServletRequest, String columns, String tag, String value)
    {
        String condition = ""; //查询条件where语句
        
        if (value == null || value.equals(""))
        {}
        else
        {
            if (tag == null)
            {
                condition += " AND " + columns + " = " + value + " ";
            }
            else
            {
                if (tag.equals("="))
                {
                    condition += " AND " + columns + " = " + value + " ";
                }
                else if (tag.equals(">"))
                {
                    condition += " AND " + columns + " > " + value + " ";
                }
                else if (tag.equals("<"))
                {
                    condition += " AND " + columns + " < " + value + " ";
                }
                else if (tag.equals(">="))
                {
                    condition += " AND " + columns + " >= " + value + " ";
                }
                else if (tag.equals("<="))
                {
                    condition += " AND " + columns + " <= " + value + " ";
                }
                else if (tag.equals(":"))
                {
                    if (httpServletRequest.getParameter(columns + "2") != null &&
                            (!httpServletRequest.getParameter(columns + "2").equals("")))
                    {
                        condition += " AND " + columns + " >= " + value + " AND " + columns + "<= " +
                        httpServletRequest.getParameter(columns + "2") + " ";
                    }
                    else
                    {
                        condition += " AND " + columns + " >= " + value + " ";
                    }
                }
                else
                {
                    condition += " AND " + columns + " = " + value + " ";
                }
            }
        }
        return condition;
    }
    
    /**
	 * 得到日期型查询条件where语句.
	 * 
	 * @param httpServletRequest
	 *            HttpServletRequest
	 * @param columns
	 *            String
	 * @param tag
	 *            String
	 * @param value
	 *            String
	 * @return String
	 */
    public String getDateConditions(HttpServletRequest httpServletRequest, String columns, String tag, String value)
    {
        String condition = ""; //查询条件where语句
        
        if (value == null || value.equals(""))
        {}
        else
        {
            if (tag == null)
            {
                condition += " AND " + columns + " = to_date('" + value + "','yyyy-mm-dd HH24:MI:SS') ";
            }
            else
            {
                if (tag.equals("="))
                {
                    condition += " AND " + columns + " = to_date('" + value + "','yyyy-mm-dd HH24:MI:SS') ";
                }
                else if (tag.equals(">"))
                {
                    condition += " AND " + columns + " > to_date('" + value + "','yyyy-mm-dd HH24:MI:SS') ";
                }
                else if (tag.equals("<"))
                {
                    condition += " AND " + columns + " < to_date('" + value + "','yyyy-mm-dd HH24:MI:SS') ";
                }
                else if (tag.equals(">="))
                {
                    condition += " AND " + columns + " >= to_date('" + value + "','yyyy-mm-dd HH24:MI:SS') ";
                }
                else if (tag.equals("<="))
                {
                    condition += " AND " + columns + " <= to_date('" + value + "','yyyy-mm-dd HH24:MI:SS') ";
                }
                else if (tag.equals(":"))
                {
                    if (httpServletRequest.getParameter(columns + "2") != null &&
                            (!httpServletRequest.getParameter(columns + "2").equals("")))
                    {
                        condition += " AND " + columns + " >= to_date('" + value + "','yyyy-mm-dd HH24:MI:SS') AND " + columns +
                        "<= to_date('" + httpServletRequest.getParameter(columns + "2") + "','yyyy-mm-dd HH24:MI:SS') ";
                    }
                    else
                    {
                        condition += " AND " + columns + " >= to_date('" + value + "','yyyy-mm-dd HH24:MI:SS') ";
                    }
                }
                else
                {
                    condition += " AND " + columns + " = to_date('" + value + "','yyyy-mm-dd HH24:MI:SS') ";
                }
            }
        }
        return condition;
    }
    
    /**
	 * 獲取屬性日期 conditions.
	 * 
	 * @param parameter
	 *            the parameter
	 * @param tag
	 *            the tag
	 * @param value
	 *            the value
	 * @return 屬性日期 conditions的值
	 */
    public String getDateConditions(String parameter, String tag, String value)
    {
        String condition = ""; //查询条件where语句
        
        if (value == null || value.equals(""))
        {}
        else
        {
            if (tag == null)
            {
                condition += " AND " + parameter + " = to_date('" + value + "','yyyy-mm-dd HH24:MI:SS') ";
            }
            else
            {
                if (tag.equals("="))
                {
                    condition += " AND " + parameter + " = to_date('" + value + "','yyyy-mm-dd HH24:MI:SS') ";
                }
                else if (tag.equals(">"))
                {
                    condition += " AND " + parameter + " > to_date('" + value + "','yyyy-mm-dd HH24:MI:SS') ";
                }
                else if (tag.equals("<"))
                {
                    condition += " AND " + parameter + " < to_date('" + value + "','yyyy-mm-dd HH24:MI:SS') ";
                }
                else if (tag.equals(">="))
                {
                    condition += " AND " + parameter + " >= to_date('" + value + "','yyyy-mm-dd HH24:MI:SS') ";
                }
                else if (tag.equals("<="))
                {
                    condition += " AND " + parameter + " <= to_date('" + value + "','yyyy-mm-dd HH24:MI:SS') ";
                }
                else
                {
                    condition += " AND " + parameter + " = to_date('" + value + "','yyyy-mm-dd HH24:MI:SS') ";
                }
            }
        }
        return condition;
    }
    
    /**
	 * 獲取屬性日期 char conditions.
	 * 
	 * @param parameter
	 *            the parameter
	 * @param tag
	 *            the tag
	 * @param value
	 *            the value
	 * @return 屬性日期 char conditions的值
	 */
    public String getDateCharConditions(String parameter, String tag, String value)
    {
        String condition = ""; //查询条件where语句
        
        if (value == null || value.equals(""))
        {}
        else
        {
            if (tag == null)
            {
                condition += " AND " + parameter + " = '" + value + "'";
            }
            else
            {
                if (tag.equals("="))
                {
                    condition += " AND " + parameter + " = '" + value + "'";
                }
                else if (tag.equals(">"))
                {
                    condition += " AND " + parameter + " > '" + value + "'";
                }
                else if (tag.equals("<"))
                {
                    condition += " AND " + parameter + " < '" + value + "'";
                }
                else if (tag.equals(">="))
                {
                    condition += " AND " + parameter + " >= '" + value + "'";
                }
                else if (tag.equals("<="))
                {
                    condition += " AND " + parameter + " <= '" + value + "'";
                }
                else
                {
                    condition += " AND " + parameter + " = '" + value + "'";
                }
            }
        }
        return condition;
    }
    
    /**
	 * 计算日期相差小时.
	 * 
	 * @param startDate
	 *            String
	 * @param endDate
	 *            String
	 * @return int
	 * @author luyang
	 */
    public int intervalHour(String startDate, String endDate)
    {
        if (startDate == null || startDate.equals("") || startDate.length() < 13)
        {
            throw new IllegalArgumentException(startDate + "不合法！请输入标准日期格式YYYY-MM-DD HH！");
        }
        if (endDate == null || endDate.equals("") || endDate.length() < 13)
        {
            throw new IllegalArgumentException(endDate + "不合法！请输入标准日期格式YYYY-MM-DD HH！");
        }
        DateTime startDay = new DateTime(startDate);
        DateTime endDay = new DateTime(endDate);
        int diffDay = new DateTime().intervalDay(startDay, 1, endDay, 1);
        int startHour = Integer.parseInt(startDate.substring(11, 13));
        int endHour = Integer.parseInt(endDate.substring(11, 13));
        int diffHour = endHour - startHour;
        if (diffDay == 0)
        {
            if (diffHour < 0)
            {
                return -1;
            }
            else
            {
                return diffHour;
            }
        }
        if (diffDay > 0)
        {
            if (diffHour >= 0)
            {
                diffHour += diffDay * 24;
                return diffHour;
            }
            else
            {
                diffDay--;
                diffHour = 24 - (startHour - endHour);
                diffHour += diffDay * 24;
                return diffHour;
            }
        }
        else
        {
            return -1;
        }
    }
    
    /**
	 * 底层方法改造，取数组以逗号隔开的值做遍历20130905modify by wangjun.
	 * 
	 * @param column
	 *            the column
	 * @param value
	 *            the value
	 * @return 屬性the sinosoft char in conditions的值
	 */
    public String getCharInConditions(String column, String[] value)
    {
        StringBuffer conditions = new StringBuffer(200);
        if(value != null && value.length > 0)
        {
            conditions.append(" AND ").append(column).append(" in(");
            for(int i=0; i<value.length; i++)
            {
            	String[] s = value[i].split(",");
            	for (int j = 0; j < s.length; j++) {
            		conditions.append("'").append(s[j].trim()).append("'");	
            		if(j+1!=s.length)
            		{
            			conditions.append(", ");
            		}
				}
//                conditions.append("'").append(value[i]).append("'");
                if(i+1 != value.length)
                {
                    conditions.append(", ");
                }
            }
            conditions.append(")");
        }
        return conditions.toString();
    }
}
