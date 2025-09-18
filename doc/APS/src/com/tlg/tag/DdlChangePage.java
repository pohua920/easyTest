package com.tlg.tag;

import java.io.Writer;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.StrutsStatics;
import org.apache.struts2.components.Component;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.util.ValueStack;
import com.tlg.util.PageInfo;

public class DdlChangePage extends Component{

//	private int rowCount = 0;
//	private int currentPage = 1;
//	private int pageSize = 10;
//	private int pageCount = 0;
	private String textOnChange = "";
	private String selectOnChange = "";
	private String name = "";
	private String nameSpace = "";
	private String formId = "";
    
    public DdlChangePage(ValueStack stack) {
        super(stack);
    }

    
	@SuppressWarnings("unchecked")
	public boolean start(Writer writer) {
	    
		try{
			String pageInfoName = ActionContext.getContext().getActionInvocation().getAction().getClass().getSimpleName() + "PageInfo";
	        Map session = ActionContext.getContext().getSession();
	        if(session == null || session.get("userInfo") == null){
	        	return false;
	        }
	        if(session.get(pageInfoName) == null){
	        	return false;
	        }
	        PageInfo pageInfo = (PageInfo)session.get(pageInfoName);
	        
			ActionContext context = ActionContext.getContext();	        
			HttpServletRequest request = (HttpServletRequest)(context.get(StrutsStatics.HTTP_REQUEST));
			
			// 總頁數
			int pageCount = pageInfo.getRowCount() / pageInfo.getPageSize();
 			// System.out.print(rowCount +","+ pageSize+",");
			String result = "";
//			result += "<form id='pageForm'>";
			result += "<script>";
			result += "function changePageIndex(action,type,text){";
			result += "	   var pageCount = " + pageInfo.getRowCount() + ";";
			result += "    var re = /^[0-9]+$/;";
			result += "    var textValue = text.value;";
			result += "    var r = textValue.match(re);";
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
//			result += "    if('select' == type){";
//			result += "        var pageSize = document.getElementById('pageSize');";
//			result += "        thisForm.action=action + '?" + this.name + ".pageSize=' + pageSize.value;";
//			result += "    }";
//			result += "    if('text' == type){";
//			result += "        var currentPage = document.getElementById('currentPage');";
//			result += "        thisForm.action=action + '?" + this.name + ".currentPage=' + currentPage.value;";
//			result += "    }";
			result += "    thisForm.action=action;";
			result += "    thisForm.submit();";
			result += "}</script>";
			result += "<table width='100%'><tr><td align='left'>總共";
			result += pageInfo.getRowCount();
			result += "筆</td><td align='right'>";

			// 頁數
			if (pageCount <= 0) {
				pageCount = 1;
			} else if (pageInfo.getRowCount() % pageInfo.getPageSize() > 0) {
				pageCount = pageCount + 1;
			}

			int next = pageInfo.getCurrentPage() + 1;
			int pre = pageInfo.getCurrentPage() - 1;
			if (pre > 0) {
//				result += "<a href=\"javascript:__doPostBack('" + this.textOnChange + "','" + pre + "','')\">上一頁</a> ";
				result += "<a href = '"+ request.getContextPath() + this.nameSpace + "/" + this.textOnChange + ".action?" + this.name + ".currentPage=" + pre + "' >上一頁</a> ";
			} else {
				result += "上一頁 ";
			}

			if (next <= pageCount) {
//				result += "<a href=\"javascript:__doPostBack('"	+ this.textOnChange + "','" + next	+ "','')\">下一頁</a> 第";
//				result += "<a href = \"<s:url action='" + this.textOnChange + "'> <s:param name='next' value='" + next + "'/> </s:url>\">下一頁</a> 第";
				result += "<a href = '"+ request.getContextPath() + this.nameSpace +  "/" + this.textOnChange + ".action?" + this.name + ".currentPage=" + next + "' >下一頁</a> 第";
			} else {
				result += "下一頁 第";
			}
			result += "<input type='text' size='5' name='" + this.name + ".currentPage' id ='currentPage' ";
			result += "value='" + pageInfo.getCurrentPage() + "'";

			if (!this.textOnChange.trim().equals("")) {
//				result += "onChange=\"__doPostBack('" + this.textOnChange + "',this.value,'')\"";
				result += "onchange='changePageIndex(\"" + this.textOnChange + ".action\",\"text\",this)'";
			}
			result += "/>/" + pageCount + "頁 ";

			result += "<select name='" + this.name + ".pageSize' id='pageSize' onChange='changePageIndex(\"" + this.selectOnChange + ".action\",\"select\",this)'>";
			result += "<option value='10' ";
			if (pageInfo.getPageSize() == 10) {
				result += " selected ";
			}

			result += ">一頁十筆</option>";
			result += "<option value='20'";
			if (pageInfo.getPageSize() == 20) {
				result += " selected ";
			}
			result += ">一頁二十筆</option>";
			result += "<option value='50'";
			if (pageInfo.getPageSize() == 50) {
				result += " selected ";
			}
			result += ">一頁五十筆</option>";

			result += "<option value='100'";
			if (pageInfo.getPageSize() == 100) {
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
			writer.write(result);  
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return true;
    }

//	public int getRowCount() {
//		return rowCount;
//	}
//
//
//	public void setRowCount(int rowCount) {
//		this.rowCount = rowCount;
//	}
//
//
//	public int getCurrentPage() {
//		return currentPage;
//	}
//
//
//	public void setCurrentPage(int currentPage) {
//		this.currentPage = currentPage;
//	}
//
//
//	public int getPageSize() {
//		return pageSize;
//	}
//
//
//	public void setPageSize(int pageSize) {
//		this.pageSize = pageSize;
//	}
//
//
//	public int getPageCount() {
//		return pageCount;
//	}
//
//
//	public void setPageCount(int pageCount) {
//		this.pageCount = pageCount;
//	}


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