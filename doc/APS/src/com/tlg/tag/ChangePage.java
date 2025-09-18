/*
 * Created on 2004/10/19
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.tlg.tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;


public class ChangePage extends TagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer rowCount = 0;
	private Integer currentPage = 1;
	private Integer pageSize = 10;
	private Integer pageCount = 0;
	private String textOnChange = "";
	private String selectOnChange = "";
	private String name = "";
	private String nameSpace = "";
	private String formId = "";


	public int doStartTag() {

		try {
			HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();
			
			JspWriter out = pageContext.getOut();
			// 總頁數
			int pageCount = this.rowCount / this.pageSize;
			// System.out.print(rowCount +","+ pageSize+",");
			String result = "";
//			result += "<form id='pageForm'>";
			result += "<script>";

			result += "function linkOnClick() {";
			result += "$('#cp').prop('readonly', true);";
			result += "$('#ps').prop('readonly', true);";
			result += "$('#prePage').prop('readonly', true);";
			result += "$('#nexPage').prop('readonly', true);";
			result += "}";
			
			result += "function changePageIndex(action,type,text){";
			result += "	   var pageCount = " + this.pageCount + ";";
			result += "    var re = /^[0-9]+$/;";
			result += "    var textValue = text.value;";
			result += "    var r = textValue.match(re);";
//			result += " var tmp = document.getElementsByName('pageInfo.currentPage');";
//			result += " alert(tmp);";
//			result += " alert(tmp.length);";
			result += "    if(r == null){";
			result += "        alert('請輸入數字!'); ";
			result += "        text.value='1'; ";
			result += "        return false;";
			result += "	   }";
			result += "    if('select' != type && textValue > pageCount){";
			result += "        text.value = pageCount;";
			result += "    }";
			result += "    if('select' != type && textValue <= 0){";
			result += "        text.value = 1;";
			result += "    }";
			result += "    var thisForm = document.getElementById('" + this.formId + "');";
//			result += "    var pageSize = document.getElementById('ps');";
//			result += "    if('select' == type){";
//			result += "        var pageSize = document.getElementById('ps');";
//			result += "        thisForm.action=action + '?" + this.name + ".pageSize=' + pageSize.value;";
//			result += "    }";
//			result += "    if('text' == type){";
//			result += "        var currentPage = document.getElementById('cp');";
//			result += "        thisForm.action=action + '?" + this.name + ".currentPage=' + currentPage.value;";
//			result += "    }";
			result += "    thisForm.action=action;";
			result += "$('#cp').prop('readonly', true);";
			result += "$('#ps').prop('readonly', true);";
			result += "$('#prePage').prop('readonly', true);";
			result += "$('#nexPage').prop('readonly', true);";
			result += "    thisForm.submit();";
			result += "}</script>";
			result += "<table width='100%' id='pageChgTlb' ><tr><td align='left'>總共";
			result += this.rowCount;
			result += "筆</td><td align='right'>";

			// 頁數
			if (pageCount <= 0) {
				pageCount = 1;
			} else if (rowCount % pageSize > 0) {
				pageCount = pageCount + 1;
			}

			int next = currentPage + 1;
			int pre = currentPage - 1;
			if (pre > 0) {
//				result += "<a href=\"javascript:__doPostBack('" + this.textOnChange + "','" + pre + "','')\">上一頁</a> ";
				result += "<a id='prePage' onClick='linkOnClick()' href = '"+ request.getContextPath() + this.nameSpace + "/" + this.textOnChange + ".action?" + this.name + ".currentPage=" + pre + "' >上一頁</a> ";
			} else {
				result += "上一頁 ";
			}

			if (next <= pageCount) {
//				result += "<a href=\"javascript:__doPostBack('"	+ this.textOnChange + "','" + next	+ "','')\">下一頁</a> 第";
//				result += "<a href = \"<s:url action='" + this.textOnChange + "'> <s:param name='next' value='" + next + "'/> </s:url>\">下一頁</a> 第";
				result += "<a id='nexPage' onClick='linkOnClick()' href = '"+ request.getContextPath() + this.nameSpace +  "/" + this.textOnChange + ".action?" + this.name + ".currentPage=" + next + "' >下一頁</a> 第";
			} else {
				result += "下一頁 第";
			}
			result += "<input type='text' size='5' name='" + this.name + ".currentPage' id ='cp' ";
			result += "value='" + this.currentPage + "'";

			if (!this.textOnChange.trim().equals("")) {
//				result += "onChange=\"__doPostBack('" + this.textOnChange + "',this.value,'')\"";
				result += "onchange='changePageIndex(\"" + this.textOnChange + ".action\",\"text\",this)'";
			}
			result += "/>/" + pageCount + "頁 ";

			result += "<select name='" + this.name + ".pageSize' id='ps' onChange='changePageIndex(\"" + this.selectOnChange + ".action\",\"select\",this)'>";
			result += "<option value='10' ";
			if (this.pageSize == 10) {
				result += " selected ";
			}

			result += ">一頁十筆</option>";
			result += "<option value='20'";
			if (this.pageSize == 20) {
				result += " selected ";
			}
			result += ">一頁二十筆</option>";
			result += "<option value='50'";
			if (this.pageSize == 50) {
				result += " selected ";
			}
			result += ">一頁五十筆</option>";

			result += "<option value='100'";
			if (this.pageSize == 100) {
				result += " selected ";
			}
			result += ">一頁一百筆</option></select>";

			/**
			 * 頁數的下拉選單 result+="<select name='"+ this.name +"' ";
			 * if(!this.textOnChange.trim().equals("")) {
			 * result+="textOnChange=\""+ this.textOnChange +"\""; }
			 * 
			 * result+=">";
			 * 
			 * for(int i=1;i<=pageCount;i++) {
			 * 
			 * if(currentPage==i) { select = " selected "; } else { select=""; }
			 * result+="<option value='"+i+"' "+ select +">"+i+"</option>"; }
			 * result+="</select>";
			 **/
			result += "</td></tr></table>";
//			result += "</form>";
			out.print(result);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.print(e);
		}
		return SKIP_BODY;
	}


	public Integer getRowCount() {
		return rowCount;
	}


	public void setRowCount(Integer rowCount) {
		this.rowCount = rowCount;
	}


	public Integer getCurrentPage() {
		return currentPage;
	}


	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}


	public Integer getPageSize() {
		return pageSize;
	}


	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}


	public Integer getPageCount() {
		return pageCount;
	}


	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}


	public String getTextOnChange() {
		return textOnChange;
	}


	public void setTextOnChange(String textOnChange) {
		this.textOnChange = textOnChange;
	}


	public String getSelectOnChange() {
		return selectOnChange;
	}


	public void setSelectOnChange(String selectOnChange) {
		this.selectOnChange = selectOnChange;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getNameSpace() {
		return nameSpace;
	}


	public void setNameSpace(String nameSpace) {
		this.nameSpace = nameSpace;
	}


	public String getFormId() {
		return formId;
	}


	public void setFormId(String formId) {
		this.formId = formId;
	}
	
	
	
	
}
