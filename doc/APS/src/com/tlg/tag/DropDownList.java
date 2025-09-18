/*
 * Created on 2005/3/4
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.tlg.tag;

import java.sql.ResultSet;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * @author Nancy.Su
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class DropDownList extends TagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ResultSet dataSource = null;
	private String textColumnName1 = "";
	private String textColumnName2 = "";
	private String valueColumnName = "";
	private String name = "";
	private String defaultValue = "";
	private String onChange = "";
	private String defaultOption = "N";
	private String disabled = "false";

	/**
	 * @return Returns the dataSource.
	 */
	public ResultSet getDataSource() {
		return dataSource;
	}

	/**
	 * @param dataSource
	 *            The dataSource to set.
	 */
	public void setDataSource(ResultSet dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * @return Returns the defaultOption.
	 */
	public String getDefaultOption() {
		return defaultOption;
	}

	/**
	 * @param defaultOption
	 *            The defaultOption to set.
	 */
	public void setDefaultOption(String defaultOption) {
		this.defaultOption = defaultOption;
	}

	/**
	 * @return Returns the defaultValue.
	 */
	public String getDefaultValue() {
		return defaultValue;
	}

	/**
	 * @param defaultValue
	 *            The defaultValue to set.
	 */
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	/**
	 * @return Returns the disabled.
	 */
	public String getDisabled() {
		return disabled;
	}

	/**
	 * @param disabled
	 *            The disabled to set.
	 */
	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}

	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return Returns the onChange.
	 */
	public String getOnChange() {
		return onChange;
	}

	/**
	 * @param onChange
	 *            The onChange to set.
	 */
	public void setOnChange(String onChange) {
		this.onChange = onChange;
	}

	/**
	 * @return Returns the textColumnName1.
	 */
	public String getTextColumnName1() {
		return textColumnName1;
	}

	/**
	 * @param textColumnName1
	 *            The textColumnName1 to set.
	 */
	public void setTextColumnName1(String textColumnName1) {
		this.textColumnName1 = textColumnName1;
	}

	/**
	 * @return Returns the textColumnName2.
	 */
	public String getTextColumnName2() {
		return textColumnName2;
	}

	/**
	 * @param textColumnName2
	 *            The textColumnName2 to set.
	 */
	public void setTextColumnName2(String textColumnName2) {
		this.textColumnName2 = textColumnName2;
	}

	/**
	 * @return Returns the valueColumnName.
	 */
	public String getValueColumnName() {
		return valueColumnName;
	}

	/**
	 * @param valueColumnName
	 *            The valueColumnName to set.
	 */
	public void setValueColumnName(String valueColumnName) {
		this.valueColumnName = valueColumnName;
	}

	public int doStartTag() {

		try {

			if (dataSource != null && dataSource.next()) {
				JspWriter out = pageContext.getOut();

				String result = "";
				result = "<select name='" + this.name + "' ";

				if (dataSource == null) {
					result += " >";
					result += "<option value='*'>請選擇</option></select>";
				} else {

					dataSource.beforeFirst();
					boolean havaData = dataSource.next();
					dataSource.beforeFirst();
					if (!this.onChange.trim().equals("")) {
						result += "onChange=\"" + this.onChange + "\"";
					}
					// disabled
					if (this.disabled.equals("true")) {
						result += " disabled ";
					}
					result += ">";
					if (this.defaultOption.equals("Y") || !havaData) {
						result += "<option value='*'>請選擇</option>";

					}
					if (havaData) {

						while (dataSource.next()) {
							String show1 = dataSource.getString(
									this.textColumnName1).toString().trim();
							String show2 = "";
							if (!this.textColumnName2.equals("")) {
								show2 = dataSource.getString(
										this.textColumnName2).toString().trim();
							}
							String myValue = dataSource.getString(
									this.valueColumnName).toString().trim();

							result += "<option value='" + myValue + "'";
							if (myValue != null && this.defaultValue != null) {
								if (!this.defaultValue.equals("")
										&& myValue.trim().equals(
												this.defaultValue)) {
									result += " selected ";
								}
							}
							result += ">" + show1;
							if (!show2.equals("")) {
								result += "-" + show2;
							}
							result += "</option>";

						}

						result += "</select>";
						out.print(result);
					}
				}
			}
		} catch (Exception e) {
			System.out.print(e);
		}
		return SKIP_BODY;

	}

}
