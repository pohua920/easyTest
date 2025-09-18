package com.tlg.prpins.util;

public class FirF01KindCodeUtil {
	
	public enum kindCode {Q_FB1, Q_B1, Q_BB, Q_PB, PL01, PL05, PL10, FB1, B1, B4, B5, B6, B7, BB, BD};
	

	public static String getName(String kindCode) {
		if("Q_FB1".equals(kindCode))
			return "鎮店保-商業火險";
		if("Q_B1".equals(kindCode))
			return "鎮店保-爆炸險";
		if("Q_BB".equals(kindCode))
			return "鎮店保-煙燻險";
		if("Q_PB".equals(kindCode))
			return "鎮店保-公共意外責任險";
		if("PL01".equals(kindCode))
			return "鎮店保-食物中毒責任";
		if("PL05".equals(kindCode))
			return "鎮店保-建築物承租人火災責任";
		if("PL10".equals(kindCode))
			return "鎮店保-廣告招牌責任";
		if("FB1".equals(kindCode))
			return "商業火災保險";
		if("B1".equals(kindCode))
			return "爆炸險";
		if("B4".equals(kindCode))
			return "航空器墜落、機動車輛碰撞險";
		if("B5".equals(kindCode))
			return "罷工、暴動、民眾騷擾、惡意破壞行為險";
		if("B6".equals(kindCode))
			return "自動消防裝置滲漏險";
		if("B7".equals(kindCode))
			return "竊盜險";
		if("BB".equals(kindCode))
			return "煙燻險";
		if("BD".equals(kindCode))
			return "水漬險";
		
		return "";
	}
	
	public static  void main (String args[]){
		System.out.println(FirF01KindCodeUtil.kindCode.Q_FB1);
		System.out.println(FirF01KindCodeUtil.getName(FirF01KindCodeUtil.kindCode.Q_FB1.toString()));
		
		
	}
}
