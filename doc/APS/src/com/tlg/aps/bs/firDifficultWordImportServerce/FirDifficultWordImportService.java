
package com.tlg.aps.bs.firDifficultWordImportServerce;

import java.io.File;

import com.tlg.util.Result;
import com.tlg.util.UserInfo;

/*mantis：FIR0439，處理人員：BJ085，需求單編號：FIR0439 住火-APS中信代理投保難字匯入 */
public interface FirDifficultWordImportService {

	public Result importDifficultWord(File uploadFile, UserInfo userInfo) throws Exception;
	
}
