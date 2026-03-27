package com.sinosoft.undwrt.common.web.view.taglib;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/** Generate a URL-encoded hyperlink to the specified URI, with associated query parameters selecting a specified Plan. */
public class UICssTag extends TagSupport{
    /** The context-relative URI. */
    protected String page = null;
    /** The attribute name. */
    private String name = "cssStyle";
    
    /**
	 * Render the beginning of the hyperlink.
	 * 
	 * @return int
	 * @throws JspException
	 *             if a JSP exception has occurred
	 */
    public int doStartTag() throws JspException
    {
        HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();
        HttpSession session = request.getSession();
        String cssString = "<link rel=\"stylesheet\" type=\"text/css\"";
        cssString += " href=\"/undwrt/css/Standard.css\">";
        JspWriter writer = pageContext.getOut();
        try{
            writer.print(cssString);
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
        this.page = null;
        this.name = "cssStyle";
    }

    /**
	 * Gets the attribute name.
	 * 
	 * @return the attribute name
	 */
    public String getName(){
        return name;
    }

    /**
	 * Sets the attribute name.
	 * 
	 * @param name
	 *            the new attribute name
	 */
    public void setName(String name){
        this.name = name;
    }

    /**
	 * Gets the context-relative URI.
	 * 
	 * @return the context-relative URI
	 */
    public String getPage(){
        return page;
    }

    /**
	 * Sets the context-relative URI.
	 * 
	 * @param page
	 *            the new context-relative URI
	 */
    public void setPage(String page){
        this.page = page;
    }
}
