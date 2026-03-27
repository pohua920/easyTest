package com.sinosoft.undwrt.common.web.view.taglib;

import java.io.IOException;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import com.sinosoft.sysframework.reference.AppConfig;
import com.sinosoft.sysframework.exceptionlog.UserException;
import com.sinosoft.function.insutil.dto.domain.PrpUserGradeDto;
import org.apache.struts.util.*;

/**
 * <p>
 * Title:菜单权限控制标签
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company:
 * </p>
 * .
 * 
 * @author LIUYANG
 * @version 1.0
 */
public class UIMenuControlTag extends TagSupport{
    
    /** 屬性The sinosoft href. */
    private String href        = "";
    
    /** 屬性The sinosoft onclick. */
    private String onclick     = "";
    
    /** 屬性The sinosoft taskcode. */
    private String taskcode    = "";
    
    /** 屬性The sinosoft level. */
    private String level       = "";
    
    /** 屬性The sinosoft key. */
    private String key         = "";
    
    /** 屬性The sinosoft keysub. */
    private String keysub      = "";
    
    /** 屬性The sinosoft str message. */
    private String strMessage= "";

    /** 屬性The sinosoft line separator. */
    private String LINE_SEPARATOR = System.getProperty("line.separator");
    
    /** 屬性The sinosoft results. */
    private StringBuffer results = null;

    /**
	 * Do start tag.
	 * 
	 * @return the int
	 * @throws JspException
	 *             the jsp exception
	 * @see javax.servlet.jsp.tagext.TagSupport#doStartTag()
	 */
    public int doStartTag() throws JspException
    {
        HttpServletRequest request   = (HttpServletRequest)pageContext.getRequest();
        HttpSession session          = (HttpSession)request.getSession(false);

        results = new StringBuffer();
        //判断session超时,超时则重新登陆
        if(session == null ||(String)session.getAttribute("myUserCode") == null ) {
            writeLine("<SCRIPT>");
            writeLine("alert('用户session超时，请重新登陆！');");
            writeLine("parent.parent.window.location='../index.jsp';");
            writeLine("</SCRIPT>");
            JspWriter writer = pageContext.getOut();
            try{
                writer.print(results.toString());
            }
            catch(IOException e){
                throw new JspException(e.toString());
            }
            // Evaluate the body of this tag
            return(SKIP_PAGE);
        }
        Collection arrUserGrade = (Collection)session.getAttribute("UserGrade");
        String usercode  = (String)session.getAttribute("myUserCode");
        String href      = this.getHref();
        String onclick   = this.getOnclick();
        String taskcode  = this.getTaskcode();
        String level     = this.getLevel();
        String key       = this.getKey();
        String keySub    = this.getKeySub();
        String value     = "";
        String target    = "fraInterface";
        String tdItemLink = "";
        String tdItemNoPermission = "<tr style='display:none'><td>";

        if(keySub==null)
            keySub="";
        strMessage=RequestUtils.message(pageContext,"org.apache.struts.action.MESSAGE","org.apache.struts.action.LOCALE",key,new Object[5]);

        if(onclick == null || onclick.trim().length()==0) {
            if(key.equals("prompt.menu.FXGL")||key.equals("prompt.menu.ZLGL")||key.equals("prompt.menu.SJZC"))
            {
                tdItemLink = "<tr><td ><img src='/undwrt/common/images/icon-1.gif' width='7' height='6'>";
            }
            else
            {
                //应该区分几几级菜单，来决定菜单的显示
                //临时处理1下,设计时应考虑菜单级别
                if(key.equals("prompt.menu.TaskAdd")||key.equals("prompt.menu.TaskMod"))
                {
                    tdItemLink = "<tr><td >&nbsp;&nbsp;&nbsp;&nbsp;<img src='/undwrt/common/images/icon-2.gif' width='7' height='6'>";
                }
                else
                {
                    tdItemLink = "<tr><td >&nbsp;&nbsp;<img src='/undwrt/common/images/icon-2.gif' width='7' height='6'>";
                }
             }
        }else {
            if(key.equals("prompt.menu.Task"))
            {
                 tdItemLink = "<tr><td class=itemLink  onclick='" + onclick + "'>&nbsp;&nbsp;<img src='/undwrt/common/images/icon-2.gif' width='7' height='7'>";
            }
            else
            {
               tdItemLink = "<tr><td class=itemLink  onclick='" + onclick + "'> <img src='/undwrt/common/images/icon-1.gif' width='7' height='7'>";
            }
            target = "_self";
        }

        try {
            Iterator it = arrUserGrade.iterator();
            while(it.hasNext()){
                PrpUserGradeDto prpUserGradeDto = (PrpUserGradeDto)it.next();
                if(prpUserGradeDto.getTaskCode().equals(taskcode)&&prpUserGradeDto.getCheckCode().equals(level))
                {
                   value = prpUserGradeDto.getValue();
                   break;
                }
            }
        }catch(Exception ex){
            throw new JspException(ex.toString());
        }

        if(href.equals("#")){
            if(value.equals("1")){
                writeLine( tdItemLink + "&nbsp;" +strMessage);
                pageContext.setAttribute("isHref","no");
            }
            else{
                writeLine( tdItemNoPermission );
                pageContext.setAttribute("isHref","no");
            }
        }
        else if( value.equals("1")){
            writeLine( tdItemLink + " <a class='menu' description ='"+keySub+strMessage+"' href='" + href + "' target='" + target + "'>" +strMessage);
            pageContext.setAttribute("isHref","yes");
        }else{
            writeLine( tdItemNoPermission );
            pageContext.setAttribute("isHref","no");
        }

        // Print this element to our output writer
        JspWriter writer = pageContext.getOut();
        try{
            writer.print(results.toString());
        }
        catch(IOException e){
            throw new JspException(e.toString());
        }
        // Evaluate the body of this tag
        return(EVAL_BODY_INCLUDE);
    }

    /**
	 * Render the end of the hyperlink.
	 * 
	 * @return int
	 * @throws JspException
	 *             if a JSP exception has occurred
	 */
    public int doEndTag() throws JspException{
        String isHref = (String)pageContext.getAttribute("isHref");
        results = new StringBuffer();
        if(isHref == null) {
        }else if(isHref.equals("yes")) {
            writeLine( "</a></td></tr>" );
        }else {
            writeLine( "</td></tr>" );
        }
        // Print this element to our output writer
        JspWriter writer = pageContext.getOut();
        try{
            writer.print(results.toString());
        }
        catch(IOException e){
            throw new JspException(e.toString());
        }
        return(EVAL_PAGE);
    }

    /** Release any acquired resources. */
    public void release(){
        super.release();
        this.href    = null;
        this.level   = null;
        this.keysub  = null;
    }

    /**
	 * 獲取屬性the sinosoft href.
	 * 
	 * @return 屬性the sinosoft href的值
	 */
    public String getHref(){
        return href;
    }

    /**
	 * 設置屬性the sinosoft href.
	 * 
	 * @param href
	 *            待設置的the sinosoft href的值
	 */
    public void setHref(String href){
        this.href = href;
    }

    /**
	 * 獲取屬性the sinosoft taskcode.
	 * 
	 * @return 屬性the sinosoft taskcode的值
	 */
    public String getTaskcode(){
        return taskcode;
    }

    /**
	 * 設置屬性the sinosoft taskcode.
	 * 
	 * @param taskcode
	 *            待設置的the sinosoft taskcode的值
	 */
    public void setTaskcode(String taskcode){
        this.taskcode = taskcode;
    }

    /**
	 * 獲取屬性the sinosoft level.
	 * 
	 * @return 屬性the sinosoft level的值
	 */
    public String getLevel(){
        return level;
    }

    /**
	 * 設置屬性the sinosoft level.
	 * 
	 * @param level
	 *            待設置的the sinosoft level的值
	 */
    public void setLevel(String level){
        this.level = level;
    }

    /**
	 * 獲取屬性the sinosoft onclick.
	 * 
	 * @return 屬性the sinosoft onclick的值
	 */
    public String getOnclick(){
        return onclick;
    }

    /**
	 * 設置屬性the sinosoft onclick.
	 * 
	 * @param onclick
	 *            待設置的the sinosoft onclick的值
	 */
    public void setOnclick(String onclick){
        this.onclick = onclick;
    }

    /**
	 * 獲取屬性the sinosoft key.
	 * 
	 * @return 屬性the sinosoft key的值
	 */
    public String getKey(){
        return key;
    }

    /**
	 * 設置屬性the sinosoft key.
	 * 
	 * @param key
	 *            待設置的the sinosoft key的值
	 */
    public void setKey(String key){
        this.key = key;
    }

    /**
	 * 獲取屬性the sinosoft key sub.
	 * 
	 * @return 屬性the sinosoft key sub的值
	 */
    public String getKeySub(){
        return keysub;
    }

    /**
	 * 設置屬性the sinosoft key sub.
	 * 
	 * @param keysub
	 *            待設置的the sinosoft key sub的值
	 */
    public void setKeySub(String keysub){
        this.keysub = keysub;
    }

    /**
	 * Write line.
	 * 
	 * @param value
	 *            the value
	 */
    private void writeLine(String value){
        results.append(value);
        results.append(LINE_SEPARATOR);
    }

    /**
	 * 獲取屬性the sinosoft group code.
	 * 
	 * @return 屬性the sinosoft group code的值
	 * @throws JspException
	 *             the jsp exception
	 */
    private String getGroupCode() throws JspException
    {
        String groupcode = "";
        try {
            groupcode    = AppConfig.get("sysconst.GROUPCODE");
        }
        catch(Exception ex){
            writeLine("<SCRIPT>");
            writeLine("alert('配置文件SysConstConfig.xml中没有配置GROUPCODE！');");
            writeLine("fraMenu.relogon();");
            writeLine("</SCRIPT>");
            JspWriter writer = pageContext.getOut();
            try{
                writer.print(results.toString());
            }
            catch(IOException e){
                throw new JspException(e.toString());
            }
            // Evaluate the body of this tag
        }
        return groupcode;
    }
}
