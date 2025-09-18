package com.tlg.tag;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.tlg.cmn.entity.CmnpBranch;
import com.tlg.cmn.entity.CmnpDepartment;
import com.tlg.cmn.service.CmnpBranchService;
import com.tlg.cmn.service.CmnpDepartmentService;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;


/**
 * 
 * @author 4555
 *
 */
@SuppressWarnings("unchecked")
public class OrgDropDownList extends TagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 *類型
	 */
	private String type = "";
	
	/**
	 *起始階層
	 */
	private String startLevel = "";
	
	/**
	 *結束階層
	 */
	private String endLevel = "";

	/**
	 * Html的文字標籤名稱
	 */
	private String targetLabel = "";
	/**
	 * Html的名稱屬性(對應Html的name)，
	 */
	private String targetName = "";
	/**
	 * Html的名稱屬性(對應Html的id)，
	 */
	private String targetId = "";

	private ArrayList<CmnpBranch> cmnpBranchCol = null;
	
	private Map<String,CmnpBranch> cmnpBranchMap = null;
	
	private ArrayList<CmnpDepartment> cmnpDepartmentCol = null;
	
	private Map<String,CmnpDepartment> cmnpDepartmentMap = null;
	
	private Map<String,Collection<CmnpDepartment>> childMap = null;
	
	private Map<String,Collection> levelMap = new HashMap<String,Collection>();
	
	private int totalLevel = 0;

	public int doStartTag() {

		try {
			JspWriter out = pageContext.getOut();
//			String result = dropDownType(this.type);
			
			if(StringUtil.isSpace(this.startLevel)){
				this.startLevel = "1";
			}
			if(cmnpBranchCol == null){
				cmnpBranchCol = new ArrayList<CmnpBranch>();
			}
			if(cmnpBranchMap == null){
				cmnpBranchMap = new HashMap<String,CmnpBranch>();
			}
			if(cmnpDepartmentCol == null){
				cmnpDepartmentCol = new ArrayList<CmnpDepartment>();
			}
			if(cmnpDepartmentMap == null){
				cmnpDepartmentMap = new HashMap<String,CmnpDepartment>();
			}
			if(childMap == null){
				childMap = new HashMap<String,Collection<CmnpDepartment>>();
			}
			
			prepaData();
			
			if(cmnpBranchCol.size() > 0 ){
				for(int i = 0 ; i < cmnpBranchCol.size() ; i++){
					CmnpBranch cmnpBranch = (CmnpBranch) cmnpBranchCol.get(i);
					int level = 1; 
//					if(this.levelMap == null){
//						this.levelMap = new HashMap<String,Collection>();
//					}
//					ArrayList list = null;
//					if(levelMap.containsKey(String.valueOf(level))){
//						list = (ArrayList)levelMap.get(String.valueOf(level));
//					}else{
//						list = new ArrayList();
//					}
//					list.add(cmnpBranch);
					
					addToLevelMap(cmnpBranch,level);
					
//					this.levelMap.put(String.valueOf(level), list);
					//由公司開始找下一層,第一層就是branch
					for(int j = 0 ; j < cmnpDepartmentCol.size() ; j++){
						CmnpDepartment cmnpDepartment = (CmnpDepartment)cmnpDepartmentCol.get(j);
						level = 2;
						if(level > totalLevel){
							totalLevel = level;
						}
						if(cmnpBranch.getBranch().equals(cmnpDepartment.getBranch())){
							//第二層
							if("".equals(StringUtil.nullToSpace(cmnpDepartment.getpDepartment()))){
								ArrayList list = null;
								if(childMap.containsKey(cmnpBranch.getBranch())){
									list = (ArrayList)childMap.get(cmnpBranch.getBranch());
								}else{
									list = new ArrayList();
								}
								list.add(cmnpDepartment);
								this.childMap.put(cmnpBranch.getBranch(), list);
								addToLevelMap(cmnpDepartment,level);
								//可能是第三層或是第四層...
								findLevelLoop(cmnpDepartment);
							}else{
								//可能是第三層或是第四層...
								findLevelLoop(cmnpDepartment);
							}

						}
					}
					
//					//找出階層
//					findLevelLoop(cmnpDepartment);
					
				}
//				String str = buildList();
//				for(int i = 1 ; i <= this.totalLevel ; i++){
//					System.out.println("i = " + i);
//					Collection col = levelMap.get(String.valueOf(i));
//					if(i == 1){
//						Iterator levelIt = col.iterator();
//						while(levelIt.hasNext()){
//							CmnpBranch cmnpBranch = (CmnpBranch)levelIt.next();
//							System.out.println(">>> Branch = " + cmnpBranch.getBranch());
//						}
//					}else{
//						Iterator levelIt = col.iterator();
//						while(levelIt.hasNext()){
//							CmnpDepartment cmnpDepartment = (CmnpDepartment)levelIt.next();
//							System.out.println(">>> Department = " + cmnpDepartment.getDepartment());
//						}
//					}
//				}
//
//				
				out.print(buildList());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			this.levelMap = null;
			this.cmnpDepartmentCol = null;
			this.cmnpBranchCol = null;
			this.cmnpBranchMap = null;
			this.cmnpDepartmentMap = null;
			this.childMap = null;
			this.startLevel = "";
			this.targetLabel = "";
		}
		return SKIP_BODY;

	}
	

	@SuppressWarnings("unchecked")
	private void prepaData() {
		Map filter = null;
		try {
			ServletContext servletContext = pageContext.getSession().getServletContext();
			WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			CmnpDepartmentService cmnpDepartmentService = (CmnpDepartmentService) context.getBean("cmnpDepartmentService");
			filter = new HashMap();
			filter.put("departmentGt", "30");
			filter.put("sortBy", "PRT_SORT ,DEPARTMENT");
			filter.put("sortType", "ASC");
			Result result = cmnpDepartmentService.findCmnpDepartmentByParams(filter);
			if (result.getResObject() != null) {
				Collection<CmnpDepartment> col = (ArrayList<CmnpDepartment>) result.getResObject();
				Iterator<CmnpDepartment> it = col.iterator();
				while (it.hasNext()) {
					CmnpDepartment cmnpDepartment = (CmnpDepartment) it.next();
					cmnpDepartmentCol.add(cmnpDepartment);
					cmnpDepartmentMap.put(cmnpDepartment.getDepartment(), cmnpDepartment);
//					String psnmOrg = cmnpDepartment.getpDepartment();
//					String iunitno = cmnpDepartment.getDepartment();
//					if(psnmOrg.equals(iunitno)){
//						rootList.add(iunitno);
//					}
				}
			}
			CmnpBranchService cmnpBranchService = (CmnpBranchService) context.getBean("cmnpBranchService");
			filter.clear();
			filter.put("branchGt", "30");
			filter.put("sortBy", "BRANCH");
			filter.put("sortType", "ASC");
			result = cmnpBranchService.findCmnpBranchByParams(filter);
			if (result.getResObject() != null) {
				Collection<CmnpBranch> col = (ArrayList<CmnpBranch>) result.getResObject();
				Iterator<CmnpBranch> it = col.iterator();
				while (it.hasNext()) {
					CmnpBranch cmnpBranch = (CmnpBranch) it.next();
					cmnpBranchCol.add(cmnpBranch);
					cmnpBranchMap.put(cmnpBranch.getBranch(), cmnpBranch);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			filter = null;
		}
	}
	

	/**
	 * 依據階層放入置Map
	 * @param element
	 * @param level
	 * @throws Exception
	 */
	private void findLevelLoop(CmnpDepartment element)throws Exception{
		ArrayList<CmnpDepartment> myChild = (ArrayList<CmnpDepartment>)findChild(element);
		childMap.put(element.getDepartment(), myChild);
		ArrayList<CmnpDepartment> list = null;
		if(this.levelMap == null){
			this.levelMap = new HashMap<String,Collection>();
		}
		int level = findLevel(element,2);
		if(this.totalLevel < level){
			this.totalLevel = level;
		}
		if(levelMap.containsKey(String.valueOf(level))){
			list = (ArrayList<CmnpDepartment>)levelMap.get(String.valueOf(level));
		}else{
			list = new ArrayList<CmnpDepartment>();
		}
		
		list.add(element);
		levelMap.put(String.valueOf(level), list);
		
//		if(myChild != null && myChild.size() > 0){
//			int childLevel = level + 1;
//			Iterator<CmnpDepartment> it = myChild.iterator();
//			while (it.hasNext()){
//				CmnpDepartment cmnpDepartment = (CmnpDepartment) it.next();
//				if(element.getDepartment().equals(cmnpDepartment.getDepartment())){
//					continue;
//				}
//				findLevelLoop(cmnpDepartment);
//			}
//		}
	}
	/**
	 * 找出節點下的子節點
	 * @param node
	 * @return
	 * @throws Exception
	 */
	private Collection<CmnpDepartment> findChild(CmnpDepartment node) throws Exception {
		Collection<CmnpDepartment> childCol = new ArrayList<CmnpDepartment>();
		Iterator<CmnpDepartment> it = cmnpDepartmentCol.iterator();
		while (it.hasNext()){
			CmnpDepartment cmnpDepartment = (CmnpDepartment) it.next();
			if(!node.getBranch().equals(cmnpDepartment.getBranch())){
				continue;
			}
			if(node.getDepartment().equals(cmnpDepartment.getpDepartment())){
				childCol.add(cmnpDepartment);
			}
		}
		return childCol;
	}
	
	private int findLevel(CmnpDepartment node,int level) throws Exception {
		
		String parentDepartment = node.getpDepartment();
		if(!this.cmnpDepartmentMap.containsKey(parentDepartment)){
			return level;
		}
		
//		System.out.println(">>. " + node.getDepartment());
//		System.out.println(">>. " + node.getDepartmentName());
		
		level++;
		CmnpDepartment parentNode = (CmnpDepartment)this.cmnpDepartmentMap.get(parentDepartment);
		return findLevel(parentNode,level);
	}
	
//	private String createHtml(){
//		String resultStr = "";
//		try{
//			if(StringUtil.isSpace(this.targetLabel) || StringUtil.isSpace(this.targetName) || StringUtil.isSpace(this.targetId)){
//				return "";
//			}
////			String labelArray[] = targetLabel.split(",");
//			String nameArray[] = targetName.split(",");
//			String idArray[] = targetId.split(",");
//			
//			if(idArray.length != nameArray.length){
//				return "";
//			}
//			resultStr = buildHtm();
//			
//		}catch (Exception e) {
//			e.printStackTrace();
//		}finally{
//			
//		}
//		return resultStr;
//	}
	
	private String buildList(){
		String str = "<tr><td width=\"200px\" align=\"right\">" + this.targetLabel + "</td><td width=\"285px\">";
		str = str + "<SELECT name='" + this.targetName + "' id='" + this.targetId + "' ><option value='' >請選擇</option>";
		try{
//			this.endLevel = String.valueOf(this.totalLevel);
			
			//產生下拉
			if("".equals(this.startLevel)){
				this.startLevel = "1";
			}
			if("".equals(this.endLevel)){
				this.endLevel = String.valueOf(this.totalLevel);
			}
			if(Integer.parseInt(this.endLevel) < Integer.parseInt(startLevel)){
				this.startLevel = "1";
			}
			if(Integer.parseInt(this.endLevel) > this.totalLevel){
				this.endLevel = String.valueOf(this.totalLevel);
			}
			ArrayList col = (ArrayList)this.levelMap.get(String.valueOf(this.startLevel));
			//由起始層開始往下展
			
			Iterator it = col.iterator();
			while(it.hasNext()){
				
				int currentLevel = Integer.parseInt(this.startLevel);
				String branch = "";
				String deptId = "";
				String desc = "";
				if("1".equals(this.startLevel)){
					CmnpBranch cmnpBranch =(CmnpBranch)it.next();
					branch = cmnpBranch.getBranch();
					deptId = "";
					desc = cmnpBranch.getBranchName();
					str = str + "<option value='' >" + desc + "</option>";
					//依據id開始找子目錄
					str = str + nextLevel(branch,currentLevel);
				}else{
					CmnpDepartment cmnpDepartment =(CmnpDepartment)it.next();
					branch = cmnpDepartment.getBranch();
					deptId = cmnpDepartment.getDepartment();
					desc = cmnpDepartment.getDepartmentNameFull();

					if(str.indexOf(deptId) != -1){
						continue;
					}
					str = str + "<option value=" + branch + "," + deptId + ">" + deptId + " - " + desc + "</option>";
					//依據id開始找子目錄
					str = str + nextLevel(deptId,currentLevel);
				}
				
			}
			str = str + "</SELECT></td>";
//			System.out.println("str = " + str);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return str;
	}
	
	private String nextLevel(String parentId,int currentLevel) throws Exception{
		
		String str = "";
		
		if(!this.childMap.containsKey(parentId)){
			return str;
		}
		currentLevel++;
		if(currentLevel > Integer.parseInt(this.endLevel)){
			return str;
		}
		
		if(str.indexOf(parentId) != -1){
			return str;
		}
		String space = "";
		for(int i = 0 ; i < currentLevel ; i++){
			space = space + "　";
		}

		Collection<CmnpDepartment> col = (ArrayList<CmnpDepartment>)this.childMap.get(parentId);
		Iterator it = col.iterator();
		while(it.hasNext()){
			CmnpDepartment cmnpDepartment = (CmnpDepartment)it.next();
			String branch = cmnpDepartment.getBranch();
			String deptId = cmnpDepartment.getDepartment();
			String desc = cmnpDepartment.getDepartmentNameFull();
			
			str = str + "<option value=" + branch + "," + deptId + ">" + space + deptId + " - " + desc + "</option>";
			str = str + nextLevel(deptId,currentLevel);
		}
		return str;
	}
	
//	private String buildHtm(){
//		String idArray[] = targetId.split(",");
//		//組資料
//		String dataScript = "<script type=\"text/javascript\">";
//		//jQuery設定
//		String script = "<script type=\"text/javascript\">";
//		//jQuery語法
//		script += "jQuery(document).ready(function(){";
//		String html = "";
//		try{
//			//產生jQuery Method
//			for(int i = 0 ; i < idArray.length - 1 ; i++ ){
//				int layer = i + Integer.parseInt(startLevel);
//				
//				String nextId = this.targetId.split(",")[i + 1];
//				script += "jQuery(\"#" + nextId + "\").cascade(\"#" + this.targetId.split(",")[i] + "\",{";
//				script += "list: " + "list" + layer + ",";
//				script += "template: commonTemplate,";
//				script += "match: commonMatch";
//				script += "});";
//			}
//			//
//			for(int i = 0 ; i < idArray.length ; i++ ){
//				int layer = i + Integer.parseInt(startLevel);
//				html += "&nbsp;" + this.targetName.split(",")[i] + "<select name=\"" + this.targetName.split(",")[i] + "" + layer + " \" size=\"1\" id=\"" + this.targetId.split(",")[i] + "\" >";
//				html += "<option value=\"\" >請選擇</option>";
//				if(i == 0){
//					Collection<CmnpDepartment> col = (ArrayList<CmnpDepartment>)this.levelMap.get(String.valueOf(layer));
//					Iterator<CmnpDepartment> it = col.iterator();
//					while(it.hasNext()){
//						CmnpDepartment avo = (CmnpDepartment)it.next();
//						html += "<option value=\""+ avo.getDepartment() + "\" >" + avo.getDepartmentNameFull() + "</option>";
//					}
//				}
//				html += "</select>";
//				
//				int count = 0;
//				if(!this.levelMap.containsKey(String.valueOf(layer))){
//					continue;
//				}
//				//依據階層組資料
//				Collection<CmnpDepartment> col = (ArrayList<CmnpDepartment>)this.levelMap.get(String.valueOf(layer));
//				Iterator<CmnpDepartment> it = col.iterator();
//				while(it.hasNext()){
//					CmnpDepartment avo = (CmnpDepartment)it.next();
//					if(count == 0){
//						dataScript += "var list" + layer + " = [";
//						count++;
//					}
//					Collection<CmnpDepartment> childCol = (ArrayList<CmnpDepartment>)this.childMap.get(avo.getDepartment());
//					Iterator<CmnpDepartment> childIt = childCol.iterator();
//					while(childIt.hasNext()){
//						CmnpDepartment childAvo = (CmnpDepartment)childIt.next();
//						dataScript += "{'When':'" + avo.getDepartment() + "','Value':'" + childAvo.getDepartment() + "','Text':'" + childAvo.getDepartmentNameFull() +"'}";
//						if(childIt.hasNext()){
//							dataScript += ",";
//						}
//						if(it.hasNext() && !childIt.hasNext()){
//							dataScript += ",";
//						}
//					}
//					if(!it.hasNext()){
//						dataScript += "];";
//					}
//				}
//			}
//
//
//		}catch(Exception e){
//			e.printStackTrace();
//		}finally{
//			script += "});";
//			script += "</script>";
//			
//			dataScript +="function commonTemplate(item) {";
//			dataScript +="return \"<option value='\" + item.Value + \"'>\" + item.Text + \"</option>\";";
//			dataScript +="};";
//			dataScript +="function commonTemplate2(item) {";
//			dataScript +="return \"<option value='\" + item.Value + \"'>***\" + item.Text + \"***</option>\"; ";
//			dataScript +="};";
//			dataScript +="function commonMatch(selectedValue) {";
//			dataScript +="return this.When == selectedValue; ";
//			dataScript +="};";
//			
//			dataScript += "</script>";
//		}
//		
//		return dataScript + html + script;
//	}

	private void addToLevelMap(Object obj,int level){
		if(this.levelMap == null){
			this.levelMap = new HashMap<String,Collection>();
		}
		ArrayList list = null;
		if(levelMap.containsKey(String.valueOf(level))){
			list = (ArrayList)levelMap.get(String.valueOf(level));
		}else{
			list = new ArrayList();
		}
		list.add(obj);
		levelMap.put(String.valueOf(level), list);
	}
	
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTargetLabel() {
		return targetLabel;
	}

	public void setTargetLabel(String targetLabel) {
		this.targetLabel = targetLabel;
	}

	public String getTargetName() {
		return targetName;
	}

	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}

	public String getTargetId() {
		return targetId;
	}

	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}

	public String getStartLevel() {
		return startLevel;
	}

	public void setStartLevel(String startLevel) {
		this.startLevel = startLevel;
	}

	public String getEndLevel() {
		return endLevel;
	}


	public void setEndLevel(String endLevel) {
		this.endLevel = endLevel;
	}
	
}
