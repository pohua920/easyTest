/*
 * @(#)WfLogQueryAction.java	Feb 19, 2013
 *
 * @Company < Technology Development Company LTD..>
 */
package com.sinosoft.claim.undwrt.web;

import java.sql.SQLException;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.sinosoft.claim.undwrt.util.CommonSessionUtil;
import com.sinosoft.claim.undwrt.util.WfLogQueryViewHelper;
import com.sinosoft.sysframework.common.datatype.PageRecord;
import com.sinosoft.sysframework.exceptionlog.UserException;

import ins.framework.web.Struts2Action;

/**
 * @Project <CL-Allocation tool>
 * @version <1.0>
 * @author 中科软
 * @description
 */
@SuppressWarnings("serial")
public class WfLogQueryAction extends Struts2Action {
	/**核赔数据收集*/
	private WfLogQueryViewHelper wfLogQueryViewHelper;

	/**
	 * 任务查询
	 * @return
	 * @throws UserException
	 * @throws Exception
	 */
	public String wfLogQuery() throws UserException, Exception {
		HttpServletRequest req = this.getRequest();
		HttpSession session = req.getSession(true);
		String forward = "";
		String handType = req.getParameter("HandType");
		String editType = req.getParameter("EditType");
		PageRecord pageRecord = null;
		int pageCount = 1;
		int rowsCount = 0;
		int pageNum = 1;
		String strPageNo = req.getParameter("pageNo");
		String strPageSize = req.getParameter("pageSize");
		String conditions = req.getParameter("conditions"); // 页面隐藏的查询条件
		if (req.getParameter("pageNo") == null) {
			strPageNo = "1";
		}
		if (req.getParameter("pageSize") == null) {
			strPageSize = "15";
		}
		int pageNo = Integer.parseInt(strPageNo);
		int pageSize = Integer.parseInt(strPageSize);

		if (handType == null || handType.equals("")) {
			handType = (String) session.getAttribute("HandType");
			editType = (String) session.getAttribute("EditType");
		}
		if (handType != null && editType != null) {
			CommonSessionUtil.setHandleSession(req, handType, editType);
		}
		Collection<?> collection = null;
		try {
			if (handType != null && !handType.equals("")) {
				String strWherePart = "";
				// 通过ViewHelper收集查询页面表单值，拼写SQL语句
				if (conditions == null || conditions.equals("")) {
					strWherePart = wfLogQueryViewHelper.getWherePart(req);
				} else { // 翻页时直接取页面上隐藏的查询条件
					strWherePart = conditions;
				}

				pageRecord = wfLogQueryViewHelper.findByConditions(strWherePart, pageNo, pageSize, true);
				collection = pageRecord.getResult();
				session.setAttribute("WflogListForm", collection);
				session.setAttribute("Conditions", strWherePart);
				pageCount = pageRecord.getTotalPageCount();
				rowsCount = pageRecord.getCount();
				pageNum = pageRecord.getPageNo();
				session.setAttribute("PageCount", String.valueOf(pageCount));
				session.setAttribute("RowsCount", String.valueOf(rowsCount));
				session.setAttribute("PageNum", String.valueOf(pageNum));
				if (handType.equals("11")) {
					forward = "HebaoSuccess";
				} else if (handType.equals("22")) {
					forward = "HepeiSuccess";
				}
			} else {
				forward = "failure";
				req.setAttribute("content", "不明確的任務類型！");
			}
		} catch (SQLException se) {
			se.printStackTrace();
			throw se;
		} catch (Exception ex) {
			throw ex;
		}
		return forward;
	}

	public WfLogQueryViewHelper getWfLogQueryViewHelper() {
		return wfLogQueryViewHelper;
	}

	public void setWfLogQueryViewHelper(WfLogQueryViewHelper wfLogQueryViewHelper) {
		this.wfLogQueryViewHelper = wfLogQueryViewHelper;
	}

}
