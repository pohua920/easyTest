/*
 * setCharacterEncodingFilter.java
 * 
 * Created on 2004�~9��16��, �U�� 1:48
 */

package com.tlg.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

public class SetCharacterEncodingFilter implements Filter {

  private String       encoding     = null;

  private FilterConfig filterConfig = null;

  public void init(FilterConfig filterConfig) {

    this.filterConfig = filterConfig;
    this.encoding = filterConfig.getInitParameter("encoding");

  }

  /**
   * 編碼的過濾
   * 
   * @param request,response,chain
   * @return 無
   */
  public void doFilter(
      ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		((HttpServletResponse) response).setHeader("Pragma","no-cache, no-store");
		((HttpServletResponse) response).setHeader("Cache-Control","no-cache");
		((HttpServletResponse) response).setDateHeader("Expires", 0);
	  
		String encoding = selectEncoding(request);
		request.setCharacterEncoding(encoding);
		response.setCharacterEncoding(encoding);
		chain.doFilter(request, response);

  }

  /**
   * 垃圾清除
   * 
   * @param 無
   * @return 無
   */
  public void destroy() {
    this.encoding = null;
  }

  /**
   * 參數設定
   * 
   * @param request
   * @return String
   */
  protected String selectEncoding(ServletRequest request) {
    return (this.encoding);
  }

}
