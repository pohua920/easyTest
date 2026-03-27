package cn.com.sinosoft.inf.dict.xmlmsg.agentSYN;

import java.util.ArrayList;
import java.util.List;

import cn.com.sinosoft.dms.model.PrpDagentAll;
import cn.com.sinosoft.dms.model.PrpDagentExt;
import cn.com.sinosoft.dms.model.PrpDcontractManage;

public class AgentSYNObj {
	private static final long serialVersionUID = 1L;

	private PrpDagentAll prpDagent = new PrpDagentAll();
	private List<PrpDagentExt> prpDagentExtList = new ArrayList<PrpDagentExt>();
	private List<PrpDcontractManage> prpDcontractManageList = new ArrayList<PrpDcontractManage>();

	public List<PrpDagentExt> getPrpDagentExtList() {
		return prpDagentExtList;
	}

	public void setPrpDagentExtList(List<PrpDagentExt> prpDagentExtList) {
		this.prpDagentExtList = prpDagentExtList;
	}

	public List<PrpDcontractManage> getPrpDcontractManageList() {
		return prpDcontractManageList;
	}

	public void setPrpDcontractManageList(List<PrpDcontractManage> prpDcontractManageList) {
		this.prpDcontractManageList = prpDcontractManageList;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public PrpDagentAll getPrpDagent() {
		return prpDagent;
	}

	public void setPrpDagent(PrpDagentAll prpDagent) {
		this.prpDagent = prpDagent;
	}

}
