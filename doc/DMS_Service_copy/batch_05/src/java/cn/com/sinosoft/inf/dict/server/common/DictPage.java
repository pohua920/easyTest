package cn.com.sinosoft.inf.dict.server.common;

import java.util.ArrayList;
import java.util.List;

public class DictPage {
	private int pageSize = 0;
	private int pageNo = 0;
	private Long pageCount ;
	private Long totalRecordCount;
	private List data = new ArrayList();
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	
	public Long getPageCount() {
		return pageCount;
	}
	public void setPageCount(Long pageCount) {
		this.pageCount = pageCount;
	}
	public Long getTotalRecordCount() {
		return totalRecordCount;
	}
	public void setTotalRecordCount(Long totalRecordCount) {
		this.totalRecordCount = totalRecordCount;
	}
	public List getData() {
		return data;
	}
	public void setData(List data) {
		this.data = data;
	}
}
