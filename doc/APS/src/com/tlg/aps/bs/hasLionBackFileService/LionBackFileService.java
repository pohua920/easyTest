package com.tlg.aps.bs.hasLionBackFileService;

import java.util.Date;
import java.util.Map;

import com.tlg.prpins.entity.HasAgtBatchMain;
import com.tlg.prpins.entity.HasBatchLog;
import com.tlg.util.Result;

/** mantis：HAS0215，處理人員：CD094，需求單編號：HAS0215 TA雄獅回饋檔*/
public interface LionBackFileService {
	
	public Result insertHasBatchLog(Date excuteTime, String userId, String programId,String status, String remark, String batchNo) throws Exception;
	
	public void updateHasBatchLog(String status, String remark, String userId, HasBatchLog HasBatchLog) throws Exception;
	
	public void updateHasAgtBatch(HasAgtBatchMain main,Map<String,Object> params) throws Exception;
	
	public void updateHasAgtBatchMain(HasAgtBatchMain main, String remark, String userId) throws Exception;
}
