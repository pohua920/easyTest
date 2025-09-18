package com.tlg.prpins.util;

public class PbKindCodeUtil {
	
	public enum kindCode {PB, PL01, PL02, PL10, PL22, PL03, PL44, PL05, PL42};
	

	public static String getName(String kindCode) {
		if("PB".equals(kindCode))
			return "公共意外責任保險";
		if("PL01".equals(kindCode))
			/* mantis：OTH0078，處理人員：BJ085，需求單編號：OTH0078 更名相關-APS、CWP、SYS系統中有台壽保產險、tlg、網域名稱更為新名稱 start */
			return "公共意外責任保險食品中毒責任附加條款";
		if("PL02".equals(kindCode))
			return "公共意外責任保險電梯責任附加條款";
		if("PL10".equals(kindCode))
			return "公共意外責任保險廣告招牌責任附加條款(甲型)";
		if("PL22".equals(kindCode))
			return "公共意外責任保險美容業責任除外不保附加條款";
		if("PL03".equals(kindCode))
			return "公共意外責任保險停車場責任附加條款";
		if("PL44".equals(kindCode))
			return "公共意外責任保險住院及身故慰問金費用附加條款";
		if("PL05".equals(kindCode))
			return "公共意外責任保險建築物承租人火災責任附加條款";
		if("PL42".equals(kindCode))
			return "公共意外責任保險活動事件工作人員責任附加條款";
		/* mantis：OTH0078，處理人員：BJ085，需求單編號：OTH0078 更名相關-APS、CWP、SYS系統中有台壽保產險、tlg、網域名稱更為新名稱 end */
		return "";
	}
	
	public static  void main (String args[]){
		System.out.println(PbKindCodeUtil.kindCode.PB);
		System.out.println(PbKindCodeUtil.getName(PbKindCodeUtil.kindCode.PB.toString()));
		
		
	}
}
