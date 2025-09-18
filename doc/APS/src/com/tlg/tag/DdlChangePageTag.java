/*
 * Created on 2004/10/19
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.tlg.tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ComponentTagSupport;

import com.opensymphony.xwork2.util.ValueStack;

@SuppressWarnings("serial")
public class DdlChangePageTag extends ComponentTagSupport  {
	
	
	//private int rowCount = 0;
	//private int currentPage = 1;
	//private int pageSize = 10;
	//private int pageCount = 0;
	private String textOnChange = "";
	private String selectOnChange = "";
	private String name = "";
	private String nameSpace = "";
	private String formId = "";

    public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        return new DdlChangePage(stack);
    }

    protected void populateParams() {   
//    	((DdlChangePage) getComponent()).setRowCount(rowCount);
//    	((DdlChangePage) getComponent()).setCurrentPage(currentPage);
//    	((DdlChangePage) getComponent()).setPageSize(pageSize);
//    	((DdlChangePage) getComponent()).setPageCount(pageCount);
    	((DdlChangePage) getComponent()).setTextOnChange(textOnChange);
    	((DdlChangePage) getComponent()).setSelectOnChange(selectOnChange);
    	((DdlChangePage) getComponent()).setName(name);
    	((DdlChangePage) getComponent()).setNameSpace(nameSpace);
    	((DdlChangePage) getComponent()).setFormId(formId);
    }

//	public int getRowCount() {
//		return rowCount;
//	}
//
//	public void setRowCount(int rowCount) {
//		this.rowCount = rowCount;
//	}
//
//	public int getCurrentPage() {
//		return currentPage;
//	}
//
//	public void setCurrentPage(int currentPage) {
//		this.currentPage = currentPage;
//	}
//
//	public int getPageSize() {
//		return pageSize;
//	}
//
//	public void setPageSize(int pageSize) {
//		this.pageSize = pageSize;
//	}
//
//	public int getPageCount() {
//		return pageCount;
//	}
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
