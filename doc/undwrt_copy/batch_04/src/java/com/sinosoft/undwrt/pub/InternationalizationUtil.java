package com.sinosoft.undwrt.pub;

import java.util.Locale;
import java.util.ResourceBundle;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.LocaleProvider;
import com.opensymphony.xwork2.TextProvider;
import com.opensymphony.xwork2.TextProviderFactory;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * The Class InternationalizationUtil.
 */
public class InternationalizationUtil extends AbstractInterceptor implements LocaleProvider {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 986598366311114380L;
    
    /** 屬性TextProvider. */
    protected static TextProvider operateCodeProvider;

    // 初始化拦截器时，获取资源文件
    /**
	 * Inits the.
	 * 
	 * @see com.opensymphony.xwork2.interceptor.AbstractInterceptor#init()
	 */
    public void init() {
        operateCodeProvider = new TextProviderFactory().createInstance(ResourceBundle.getBundle("messages", getLocale()), this);
    }

    /**
	 * Intercept.
	 * 
	 * @param actionInvocation
	 *            the action invocation
	 * @return the string
	 * @throws Exception
	 *             the exception
	 * @see com.opensymphony.xwork2.interceptor.AbstractInterceptor#intercept(com.opensymphony.xwork2.ActionInvocation)
	 */
    @Override
    public String intercept(ActionInvocation actionInvocation) throws Exception {
        return null;
    }

    /**
	 * getLocale.
	 * 
	 * @return the locale
	 * @see com.opensymphony.xwork2.LocaleProvider#getLocale()
	 */
    @Override
    public Locale getLocale() {
        ActionContext ctx = ActionContext.getContext();
        if (ctx != null) {
            return ctx.getLocale();
        } else {
            return new Locale("zh", "CN");
        }
    }

    /**
	 * getText.
	 * 
	 * @param textName
	 *            the text name
	 * @return the text
	 */
    public String getText(String textName) {
        if (operateCodeProvider == null) {
            init();
        }
        String str = operateCodeProvider.getText(textName);// 获取配置文件中的值.
        return str;
    }

    /**
	 * getText.
	 * 
	 * @param textName
	 *            the text name
	 * @param list
	 *            the list
	 * @return the text
	 */
    public String getText(String textName, String[] list) {
        init();
        String str = this.operateCodeProvider.getText(textName, list);// 获取配置文件中的值.
        return str;

    }

}
