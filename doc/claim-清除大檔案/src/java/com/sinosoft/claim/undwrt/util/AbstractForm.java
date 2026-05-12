package com.sinosoft.claim.undwrt.util;

import com.sinosoft.sysframework.common.datatype.PageRecord;
/**
 * 
 * @author 中科軟
 *
 */
public class AbstractForm {

	public AbstractForm() {
		pageNo = 1;
		rowsCount = 0;
		rowsPerPage = 10;
	}

	public AbstractForm(PageRecord pageRecord) {
		pageNo = pageRecord.getPageNo();
		rowsCount = pageRecord.getCount();
		rowsPerPage = pageRecord.getRowsPerPage();
	}

	public AbstractForm(PageRecord pageRecord, String taskCode) {
		this(pageRecord);
		this.taskCode = taskCode;
	}

	public AbstractForm(PageRecord pageRecord, String taskCode, String groupCode) {
		this(pageRecord, taskCode);
		this.groupCode = groupCode;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getAction() {
		return action;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setRowsCount(int rowsCount) {
		this.rowsCount = rowsCount;
	}

	public int getRowsCount() {
		return rowsCount;
	}

	public void setRowsPerPage(int rowsPerPage) {
		this.rowsPerPage = rowsPerPage;
	}

	public int getRowsPerPage() {
		return rowsPerPage;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCodeDescription() {
		return codeDescription;
	}

	public void setCodeDescription(String codeDescription) {
		this.codeDescription = codeDescription;
	}

	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	public String getTaskCode() {
		return taskCode;
	}

	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}
	/** */
	private String action;
	/**頁數*/
	private int pageNo;
	/**數據行數*/
	private int rowsCount;
	/**每頁數據行數*/
	private int rowsPerPage;
	/**類型*/
	private String type;
	/**代碼描述*/
	private String codeDescription;
	/**功能代碼*/
	private String taskCode;
	/**崗位代碼*/
	private String groupCode;
}