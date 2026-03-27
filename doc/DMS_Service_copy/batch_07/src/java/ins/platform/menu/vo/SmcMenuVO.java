package ins.platform.menu.vo;

public class SmcMenuVO {
	// 菜单ID
	private Integer menuID;
	// 菜单中文名称
	private String menuCName;
	// 菜单上级
	private Integer upperID;
	// 菜单级别
	private Integer menuLevel;

	public Integer getMenuID() {
		return menuID;
	}

	public void setMenuID(Integer menuID) {
		this.menuID = menuID;
	}

	public String getMenuCName() {
		return menuCName;
	}

	public void setMenuCName(String menuCName) {
		this.menuCName = menuCName;
	}

	public Integer getUpperID() {
		return upperID;
	}

	public void setUpperID(Integer upperID) {
		this.upperID = upperID;
	}

	public Integer getMenuLevel() {
		return menuLevel;
	}

	public void setMenuLevel(Integer menuLevel) {
		this.menuLevel = menuLevel;
	}

}
