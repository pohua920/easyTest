package ins.platform.menu.service.facade;

public interface PiccMenuService {
	/**
	 * 执行生成PICC样式的树(用于显示） *
	 * 
	 * @param upperIdId
	 *            上级菜单ID 为0时表示顶级菜单
	 * @param comCode
	 *            机构代码
	 * @param userCode
	 *            用户代码
	 * @param gradeCodes
	 *            岗位代码列表
	 * @param systemCode
	 *            系统代码
	 * @param language
	 *            语言
	 * @return
	 */
	public String showMenu(Integer upperId, String comCode, String userCode,
			String gradeCodes, String systemCode, String language);
}
