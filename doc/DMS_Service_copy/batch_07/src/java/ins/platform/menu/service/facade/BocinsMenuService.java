package ins.platform.menu.service.facade;

public interface BocinsMenuService {
	public String showMenu(Integer upperMenuId, String comCode,
			String userCode, String gradeCodes, String systemCode,
			String language, int powerType, String gradesIdString);
	public String showMenu(Integer upperMenuId, String comCode,
			String userCode, String gradeCodes, String systemCode,
			String riskCode,String language, int powerType, String gradesIdString);
}
