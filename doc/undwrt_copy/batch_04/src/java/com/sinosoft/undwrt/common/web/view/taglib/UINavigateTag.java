package com.sinosoft.undwrt.common.web.view.taglib;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import com.sinosoft.sysframework.web.view.AbstractForm;

/**
 * 生成导航控制条.
 */
public class UINavigateTag extends TagSupport
{
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = UINavigateTag.class.hashCode();
    /** The context-relative URI. */
    protected String page = null;
    /** The key of the session-scope bean we look for. */
    private String name = "formBean";
    
    /** 屬性The sinosoft object name. */
    private String objectName = "";
    
    /** 屬性The sinosoft line separator. */
    private String LINE_SEPARATOR = System.getProperty("line.separator");
    
    /** 屬性The sinosoft results. */
    private StringBuffer results = new StringBuffer(64000);
    
    /**
	 * Render the beginning of the hyperlink.
	 * 
	 * @return int
	 * @throws JspException
	 *             if a JSP exception has occurred
	 */
    public int doStartTag() throws JspException
    {
        // Generate the URL to be encoded
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        String formName = getName();
        String objectName = getObjectName();
        AbstractForm abstractForm = (AbstractForm) request.getAttribute(objectName);
        int rowsCount = abstractForm.getRowsCount();
        int firstRow = 0;
        int lastRow = 0;
        int currentPage = 0;
        int pagesCount = 0;
        int tempCount = rowsCount;
        if (rowsCount > 0) {
            currentPage = abstractForm.getPageNo();
            if (currentPage == 0) {
                currentPage = 1;
            }
        }
        while (tempCount > 0) {
            tempCount = tempCount - abstractForm.getRowsPerPage();
            pagesCount++;
        }
        if (currentPage > 0) {
            firstRow = abstractForm.getRowsPerPage() * (currentPage - 1) + 1;
        }
        if (currentPage < pagesCount) {
            lastRow = firstRow + abstractForm.getRowsPerPage() - 1;
        } else {
            lastRow = abstractForm.getRowsCount();
        }
        results.setLength(0);
        writeLine("    <table width=\"100%\" border=0 cellspacing=0 cellpadding=0>");
        writeLine("        <tr >");
        writeLine("          <td width=\"33%\" class=\"page\">&nbsp;共" + rowsCount + "條，列出" + firstRow + "條到第"
                + lastRow + "條</td>");
        writeLine("          <td width=\"34%\" align=\"center\" class=\"page\">");
        writeLine("             <input type=hidden name=pagesCount value=" + pagesCount + ">");
        writeLine("             <img src='../images/btnFirstPage.gif' align=middle style='cursor:hand' border=0 alt='首頁' onclick=\"return locate(1);\">&nbsp;");
        writeLine("                  　　　 ");
        writeLine("             <img src='../images/btnUp.gif' align=middle style='cursor:hand' border=0 alt='上頁' onclick=\"return locate("
                + (currentPage - 1) + ")\">&nbsp;");
        writeLine("                  　　　 ");
        writeLine("             <img src='../images/btnNext.gif' align='middle' style='cursor:hand' border=0 alt='下頁' onclick=\"return locate("
                + (currentPage + 1) + ")\">&nbsp;");
        writeLine("                  　　　 ");
        writeLine("             <img src='../images/btnLastPage.gif' align=middle style='cursor:hand' border=0 alt='末頁' onclick=\"return locate("
                + pagesCount + ")\">");
        writeLine("          </td>");
        writeLine("          <td width=\"33%\" align=\"right\" class=\"page\">");
        writeLine("             共" + pagesCount + "頁，列出第" + currentPage + "頁&nbsp; 轉到");
        writeLine("             <input type='text' name=newPageNo class='smallGo'>頁");
        writeLine("             <img src='../images/btnGo.gif' align='middle' style='cursor:hand' border='0' alt='轉到' onclick=\"return goPage()\">&nbsp;");
        writeLine("          </td>");
        writeLine("        </tr>");
        writeLine("   </table>");
        writeLine("");
        writeLine("<script language=\"javascript\">");
        writeLine("    function locate(pageNo){");
        writeLine("        if(pageNo<1){");
        writeLine("            alert(\"已到第一頁\");");
        writeLine("            return false;");
        writeLine("        }");
        writeLine("        if(pageNo>parseInt(" + formName + ".pagesCount.value)){");
        writeLine("            alert(\"已到最後一頁\");");
        writeLine("            return false;");
        writeLine("        }");
        writeLine("        if(pageNo==" + currentPage + "){");
        writeLine("            return false;");
        writeLine("        }");
        writeLine("");
        writeLine("        " + formName + ".pageNo.value=pageNo;");
        writeLine("        " + formName + ".submit();");
        writeLine("        return true;");
        writeLine("    }");
        writeLine("    function goPage(){");
        writeLine("        var pageNo=parseInt(" + formName + ".newPageNo.value);");
        writeLine("        if(isNaN(pageNo)){");
        writeLine("            pageNo=1;");
        writeLine("        }");
        writeLine("        if(pageNo>parseInt(" + formName + ".pagesCount.value)){");
        writeLine("            alert(\"無法轉到第\" + pageNo + \"頁\");");
        writeLine("            return false;");
        writeLine("        }");
        writeLine("        return locate(pageNo);");
        writeLine("    }");
        writeLine("</script>");
        // Print this element to our output writer
        JspWriter writer = pageContext.getOut();
        try {
            writer.print(results.toString());
        } catch (IOException e) {
            throw new JspException(e.toString());
        }
        // Evaluate the body of this tag
        return (EVAL_BODY_INCLUDE);
    }
    
    /**
	 * Render the end of the hyperlink.
	 * 
	 * @return int
	 * @throws JspException
	 *             if a JSP exception has occurred
	 */
    public int doEndTag() throws JspException {
        return (EVAL_PAGE);
    }
    /** Release any acquired resources. */
    public void release() {
        super.release();
        this.page = null;
        this.name = "formBean";
    }
    
    /**
	 * Gets the key of the session-scope bean we look for.
	 * 
	 * @return the key of the session-scope bean we look for
	 */
    public String getName() {
        return name;
    }
    
    /**
	 * 獲取屬性the sinosoft object name.
	 * 
	 * @return 屬性the sinosoft object name的值
	 */
    public String getObjectName() {
        return objectName;
    }
    
    /**
	 * 設置屬性the sinosoft object name.
	 * 
	 * @param objectName
	 *            待設置的the sinosoft object name的值
	 */
    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }
    
    /**
	 * Sets the key of the session-scope bean we look for.
	 * 
	 * @param name
	 *            the new key of the session-scope bean we look for
	 */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
	 * Gets the context-relative URI.
	 * 
	 * @return the context-relative URI
	 */
    public String getPage() {
        return page;
    }
    
    /**
	 * Sets the context-relative URI.
	 * 
	 * @param page
	 *            the new context-relative URI
	 */
    public void setPage(String page) {
        this.page = page;
    }
    
    /**
	 * Write line.
	 * 
	 * @param value
	 *            the value
	 */
    private void writeLine(String value) {
        results.append(value);
        results.append(LINE_SEPARATOR);
    }
}