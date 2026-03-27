package com.sinosoft.undwrt.common.web.view.taglib;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * The Class UIImgTag.
 */
public class UIImgTag extends TagSupport{

    /** 屬性The sinosoft type. */
    private String type = "";

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
        String imgString ="";
        String type = this.getType();

        if (type.equals("must"))
          imgString = "markMustInput.gif";
        else if (type.equals("common"))
           imgString = "bgCommon.gif";
        else if (type.equals("expand"))
           imgString = "butExpand.gif";
        else if (type.equals("collapse"))
           imgString = "butCollapse.gif";

        imgString = "<img src=\"/undwrt/images/" + imgString + "\">";

        JspWriter writer = pageContext.getOut();
        try{
            writer.print(imgString);
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
        return(EVAL_PAGE);
    }

    /** Release any acquired resources. */
    public void release(){
        super.release();
        this.type = null;
    }

    /**
	 * 獲取屬性the sinosoft type.
	 * 
	 * @return 屬性the sinosoft type的值
	 */
    public String getType(){
        return type;
    }

    /**
	 * 設置屬性the sinosoft type.
	 * 
	 * @param type
	 *            待設置的the sinosoft type的值
	 */
    public void setType(String type){
        this.type = type;
    }
}
