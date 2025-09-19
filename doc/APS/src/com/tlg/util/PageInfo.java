/*
 * Created on 2005/2/24
 * 
 * TODO To change the template for this generated file go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
package com.tlg.util;

import java.io.Serializable;
import java.util.Map;

@SuppressWarnings("serial")
public class PageInfo implements Serializable{

	private String keyword = "";

	private String sortBy = "";

	private String sortType;

	private int pageSize;

	private int currentPage;

	private int startRow;

	private int endRow;

	private int rowCount;

	private String id = "";

	private int pageCount;

	private Map<String, String> filter = null;

	public PageInfo() {
		sortType = "DESC";
		pageSize = 10;
		currentPage = 1;
		startRow = 1;
		endRow = startRow + pageSize - 1;
		pageCount = 1;
	}

	/**
	 * @return Returns the currentPage.
	 */
	public int getCurrentPage() {
		return currentPage;
	}

	/**
	 * @param currentPage
	 *            The currentPage to set.
	 */
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	/**
	 * @return Returns the endRow.
	 */
	public int getEndRow() {
		return endRow;
	}

	/**
	 * @param endRow
	 *            The endRow to set.
	 */
	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}

	/**
	 * @return Returns the filter.
	 */
	public Map getFilter() {
		
		//Map returnFilter = null;
		if(filter != null){
//			Set filKey = filter.keySet();
//			Iterator filIter = filKey.iterator();
//			
//			while(filIter.hasNext()){
//				// { "'", "<", ">", "(", ")", "[", "]", "=", "+", "-", "_", "~", "!", "@", "#", "$", "%", "^", "&", "*", "?", "/", "|" }
//				String[] checkChar = { "'", "<", ">", "(", ")", "[", "]", "=", "+", "_", "~", "@", "#", "$", "%", "^", "&","?", "|" };
//				String[] replaceChar = { "’", "＜", "＞", "（", "）", "［", "］", "＝", "＋", "＿", "～", "＠", "＃", "＄", "％", "︿", "＆", "？", "｜"};
//				
//				String keyStr = (String) filIter.next();
//				
//				String value = (String) filter.get(keyStr);
//
//				for (int i = 0; i < checkChar.length; i++) {
//					if (value != null && value.indexOf(checkChar[i]) != -1) {
//						value = value.replaceAll(checkChar[i], replaceChar[i]);
//						filter.put(keyStr, value);
//					}
//				}
//			}
			if(this.startRow > this.endRow){
				if(this.pageSize > 0){
					this.pageCount = this.rowCount / this.pageSize;
				}else{
					this.pageCount = 0;
				}
				this.startRow = this.pageCount * this.pageSize + 1;
				// 頁數
				if (this.pageCount <= 0) {
					this.pageCount = 1;
				} else if (this.rowCount % this.pageSize > 0) {
					this.pageCount = this.pageCount + 1;
				}
				this.currentPage = this.pageCount;
			}
			filter.put("startRow", String.valueOf(this.startRow));
			filter.put("endRow", String.valueOf(this.endRow));
		}
		return filter;
	}

	/**
	 * @param filter
	 *            The filter to set.
	 */
	public void setFilter(Map filter) {
		this.filter = filter;
	}

	/**
	 * @return Returns the id.
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            The id to set.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return Returns the keyword.
	 */
	public String getKeyword() {
		return keyword;
	}

	/**
	 * @param keyword
	 *            The keyword to set.
	 */
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	/**
	 * @return Returns the pageCount.
	 */
	public int getPageCount() {
		return pageCount;
	}

	/**
	 * @param pageCount
	 *            The pageCount to set.
	 */
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	/**
	 * @return Returns the pageSize.
	 */
	public int getPageSize() {
		if(this.pageSize == 0){
			this.pageSize = this.endRow;
		}
		return pageSize;
	}

	/**
	 * @param pageSize
	 *            The pageSize to set.
	 */
	public void setPageSize(int pageSize) {
//		this.currentPage = 1;
		this.pageSize = pageSize;
	}

	/**
	 * @return Returns the rowCount.
	 */
	public int getRowCount() {
		return rowCount;
	}

	/**
	 * @param rowCount
	 *            The rowCount to set.
	 */
	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	/**
	 * @return Returns the sortBy.
	 */
	public String getSortBy() {
		return sortBy;
	}

	/**
	 * @param sortBy
	 *            The sortBy to set.
	 */
	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	/**
	 * @return Returns the sortType.
	 */
	public String getSortType() {
		return sortType;
	}

	/**
	 * @param sortType
	 *            The sortType to set.
	 */
	public void setSortType(String sortType) {
		this.sortType = sortType;
	}

	/**
	 * @return Returns the startRow.
	 */
	public int getStartRow() {
		return startRow;
	}

	/**
	 * @param startRow
	 *            The startRow to set.
	 */
	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public String getChangeSortType() {
		if (this.sortType.equals("ASC")) {
			this.sortType = "DESC";
		} else {
			this.sortType = "ASC";
		}
		return sortType;
	}

	/**
	 * 換頁
	 * 
	 * @param 無
	 * @return 無
	 */
	public void doPage() {
		try{
			if (this.pageSize == 0) {
				this.pageCount = 1;
				this.startRow = 1;
				this.endRow = this.rowCount;
				return;
			}

			this.pageCount = this.rowCount / this.pageSize;
			// 頁數
			if (this.pageCount <= 0) {
				this.pageCount = 1;
			} else if (this.rowCount % this.pageSize > 0) {
				this.pageCount = this.pageCount + 1;
			}
			// 起始row
			if (this.currentPage - 1 > 0) {
				this.startRow = (this.currentPage - 1) * this.pageSize + 1;
			} else {
				this.startRow = 1;
			}
			// 結束row
			if (this.currentPage * this.pageSize <= rowCount) {
				this.endRow = this.currentPage * this.pageSize;
			} else {
				this.endRow = rowCount;
			}
			//
			if(this.currentPage > this.pageCount){
				this.currentPage = 1;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}

	}
}
