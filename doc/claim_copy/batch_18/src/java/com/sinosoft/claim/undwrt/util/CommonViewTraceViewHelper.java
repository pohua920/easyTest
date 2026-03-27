/*
 * @(#)CommonViewTraceViewHelper.java	Feb 20, 2013
 *
 * @Company < Technology Development Company LTD..>
 */
package com.sinosoft.claim.undwrt.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import com.sinosoft.undwrt.bl.facade.BLUwNotionFacade;
import com.sinosoft.undwrt.bl.facade.BLWfLogFacade;
import com.sinosoft.undwrt.dto.domain.UwNotionDto;
import com.sinosoft.undwrt.dto.domain.WfLogDto;

/**
 * @Project <CL-Allocation tool>
 * @version <1.0>
 * @author 中科软
 * @description
 */
public class CommonViewTraceViewHelper {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @desc 获取轨迹信息
	 * @param req HttpServletRequest
	 * @return Collection
	 * @throws Exception
	 * @author 中科软
	 * @version 1.0
	 */
	public Collection<WfLogDto> getTraceInfoList(HttpServletRequest req) throws Exception {
		Collection<WfLogDto> colTraceInfoList = null;
		Collection<UwNotionDto> colNotionList = new ArrayList<UwNotionDto>();
		WfLogDto wfLogDto = null;
		UwNotionDto uwNotionDto = null;
		String businessNo = req.getParameter("BusinessNo");
		String strNotionSQL = "";
		String handleText = "";

		String strSQL = " BusinessNo ='" + businessNo + "'" + " ORDER BY FlowId,LogNo";

		// 获取正常轨迹信息
		colTraceInfoList = new BLWfLogFacade().findByConditions(strSQL);
		for (Iterator<WfLogDto> iWfLog = colTraceInfoList.iterator(); iWfLog.hasNext();) {
			wfLogDto = (WfLogDto) iWfLog.next();
			handleText = "";
			strNotionSQL = " FlowId ='" + wfLogDto.getFlowID() + "'" + " AND LogNo ='" + wfLogDto.getLogNo() + "'" + " ORDER BY LineNo";
			colNotionList = new BLUwNotionFacade().findByConditions(strNotionSQL);
			for (Iterator<UwNotionDto> iNotion = colNotionList.iterator(); iNotion.hasNext();) {
				uwNotionDto = (UwNotionDto) iNotion.next();
				handleText += uwNotionDto.getHandleText();
				handleText += "\r\n";
			}
			wfLogDto.setHandleText(handleText);
		}
		return colTraceInfoList;

	}

}
