package ins.platform.menu.service.spring;

import ins.framework.cache.CacheManager;
import ins.framework.dao.GenericDaoHibernate;
import ins.framework.power.PowerService;
import ins.platform.menu.model.SmcMenu;
import ins.platform.menu.service.facade.BocinsMenuService;
import ins.platform.menu.service.facade.MenuService;
import ins.platform.menu.service.facade.MinganMenuService;
import ins.platform.menu.service.facade.PiccMenuService;
import ins.platform.menu.vo.SmcMenuVO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

/**
 * PICC��ʽ�Ĳ˵������
 */
public class MenuServiceSpringImpl extends
		GenericDaoHibernate<SmcMenu, Integer> implements MenuService {

	protected final Logger logger = Logger
			.getLogger(MenuServiceSpringImpl.class);

	protected PowerService powerService;
	private MinganMenuService minganMenuService;
	private PiccMenuService piccMenuService;
	private BocinsMenuService bocinsMenuService;
	protected static Map<String, String> imageMap = new TreeMap<String, String>();

	private SmcMenu smcMenu;
	private static CacheManager cacheManager = CacheManager
			.getIntance("SmcMenu");

	public SmcMenu getInstance() {
		smcMenu = new SmcMenu();
		return smcMenu;
	}

	public PowerService getPowerService() {
		return powerService;
	}

	public void setPowerService(PowerService powerService) {
		this.powerService = powerService;
	}

	public MinganMenuService getMinganMenuService() {
		return minganMenuService;
	}

	public void setMinganMenuService(MinganMenuService minganMenuService) {
		this.minganMenuService = minganMenuService;
	}

	public PiccMenuService getPiccMenuService() {
		return piccMenuService;
	}

	public void setPiccMenuService(PiccMenuService piccMenuService) {
		this.piccMenuService = piccMenuService;
	}

	/**
	 * ִ�����PICC��ʽ����(������ʾ�� *
	 * 
	 * @param upperIdId
	 *            �ϼ��˵�ID Ϊ0ʱ��ʾ�����˵�
	 * @param comCode
	 *            �����
	 * @param userCode
	 *            �û�����
	 * @param gradeCodes
	 *            ��λ�����б�
	 * @param systemCode
	 *            ϵͳ����
	 * @param menuStyle
	 *            �˵�����
	 * @param language
	 *            ����
	 * @param powerType
	 *            Ȩ��ֵ 1-����Ȩ�� 2-����Ȩ��
	 * @param gradesIdString
	 *            ��λ�б�
	 * @return
	 */
	public String showMenu(Integer upperMenuId, String comCode,
			String userCode, String gradeCodes, String systemCode,
			String menuStyle, String language, int powerType,
			String gradesIdString) {
		comCode = "";
		gradeCodes = "";
		if (menuStyle == null || menuStyle.equalsIgnoreCase("picc")) {
			return piccMenuService.showMenu(upperMenuId, comCode, userCode,
					gradeCodes, systemCode, language);
		}
		if (menuStyle.equalsIgnoreCase("mingan")) {
			return minganMenuService.showMenu(upperMenuId, comCode, userCode,
					gradeCodes, systemCode, language);
		}
		if (menuStyle.equalsIgnoreCase("bocins")) {
			return bocinsMenuService
					.showMenu(upperMenuId, comCode, userCode, gradeCodes,
							systemCode, language, powerType, gradesIdString);
		}
		return piccMenuService.showMenu(upperMenuId, comCode, userCode,
				gradeCodes, systemCode, language);
	}

	public String showMenu(Integer upperMenuId, String comCode,
			String userCode, String gradeCodes, String systemCode,
			String riskCode, String menuStyle, String language, int powerType,
			String gradesIdString) {
		return bocinsMenuService.showMenu(upperMenuId, comCode, userCode,
				gradeCodes, systemCode, riskCode, language, powerType,
				gradesIdString);
	}

	public List<SmcMenuVO> findMenuVOList(String svrCode) {
		List<SmcMenuVO> SmcMenuVOList = new ArrayList<SmcMenuVO>(0);
		String hql = " select smcMenu from SmcMenu smcMenu where systemCode=? ";
		List<SmcMenu> smcMenuList = super.findByHql(hql, svrCode);
		for (SmcMenu smcMenu : smcMenuList) {
			SmcMenuVO smcMenuVO = new SmcMenuVO();
			smcMenuVO.setMenuID(smcMenu.getId());
			smcMenuVO.setMenuCName(smcMenu.getMenuCName());
			smcMenuVO.setUpperID(smcMenu.getUpperId());
			smcMenuVO.setMenuLevel(smcMenu.getMenuLevel());
			SmcMenuVOList.add(smcMenuVO);

		}
		return SmcMenuVOList;
	}

	// liyu 向菜单表中增加菜单
	public void insertMenu(SmcMenu smcMenu, String userCodeOper) {
		Date date = new Date(System.currentTimeMillis());
		// StringBuffer hql = new StringBuffer(
		// "select smcMenu from SmcMenu smcMenu where ID=? ");
		SmcMenu smcMenuTemp = super.findUnique("id", smcMenu.getUpperId());
		// 若为系统时，则从菜单表中取不数据，将其显示级别设计为一级;
		if (smcMenuTemp == null) {
			smcMenu.setMenuLevel(1);
		} else {
			smcMenu.setMenuLevel(smcMenuTemp.getMenuLevel() + 1);
		}
		// 处理创建人、创建时间、最后更新人、最后更新时间
		smcMenu.setCreatorCode(userCodeOper);
		smcMenu.setCreateTime(date);
		smcMenu.setUpdaterCode(userCodeOper);
		smcMenu.setUpdateTime(date);
		this.save(smcMenu);
	}

	// liyu 向菜单表中增加菜单
	public void updateMenu(SmcMenu smcMenu, String userCodeOper) {
		Date date = new Date(System.currentTimeMillis());
		smcMenu.setUpdaterCode(userCodeOper);
		smcMenu.setUpdateTime(date);
		super.update(smcMenu);
	}

	public void delMenu(int id,String svrCode) {
		StringBuffer hql1 = new StringBuffer(
				"select smcMenu from SmcMenu smcMenu where id=? and systemCode = ?");
		// List <SmcMenu> smcMenuList= new ArrayList();
		List<SmcMenu> smcMenuList = (List<SmcMenu>) super.findByHql(hql1
				.toString(), id,svrCode);

		StringBuffer hql2 = new StringBuffer(
				"select smcMenu from SmcMenu smcMenu where upperId=? and systemCode = ?");
		List<SmcMenu> smcMenuList2 = (List<SmcMenu>) super.findByHql(hql2
				.toString(), id,svrCode);
		for (SmcMenu smcMenu : smcMenuList2) {
			smcMenuList.add(smcMenu);
			StringBuffer hql3 = new StringBuffer(
					"select smcMenu from SmcMenu smcMenu where id=? and systemCode = ?");
			List<SmcMenu> smcMenuList3 = (List<SmcMenu>) super.findByHql(hql3
					.toString(), smcMenu.getId(),svrCode);
			for (SmcMenu smcMenu1 : smcMenuList3) {
				smcMenuList.add(smcMenu1);
				StringBuffer hql4 = new StringBuffer(
						"select smcMenu from SmcMenu smcMenu where id=? and systemCode = ?");
				List<SmcMenu> smcMenuList4 = (List<SmcMenu>) super.findByHql(
						hql4.toString(), smcMenu1.getId(),svrCode);
				for (SmcMenu smcMenu2 : smcMenuList4) {
					smcMenuList.add(smcMenu2);
				}
			}
		}
		this.deleteAll(smcMenuList);
	}

	// liyu 根据ID查找菜单
	public SmcMenu queryMenu(int id) {
		// TODO Auto-generated method stub
		SmcMenu smcMenuTemp = super.findUnique("id", id);
		return smcMenuTemp;
	}

	public BocinsMenuService getBocinsMenuService() {
		return bocinsMenuService;
	}

	public void setBocinsMenuService(BocinsMenuService bocinsMenuService) {
		this.bocinsMenuService = bocinsMenuService;
	}

}