package com.yunwei.context.config.configure.element;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yunwei.context.config.configure.util.PrintUtil;


public abstract class AbstractLeafHTMLFragment<S extends AbstractLeafHTMLFragment<?, ?>, C extends HTMLFragment<?, ?>>
        implements HTMLFragment<S, C> {
    
    protected final Logger LOG = LoggerFactory.getLogger("Config-Manager");
    
    protected String id;
    
    protected String name;
    
    protected String label;
    
    /**
     * 页面正常显示。
     */
    protected static final String DISPLAY_TRUE = "true";
    
    /**
     * 页面一般会以<code>style="display: none;"</code>的形式表现，同时其后代的<code>input</code>元素都会加上<code>disabled="disabled"</code>
     * 在提交的时候忽略。
     */
    protected static final String DISPLAY_FALSE = "false";
    
    /**
     * 页面一般会以<code>&lt;input type="hidden" /&gt;</code>的形式表现。
     */
    protected static final String DISPLAY_HIDE = "hide";
    
    /**
     * 显示方式。
     * @see #DISPLAY_TRUE
     * @see #DISPLAY_FALSE
     * @see #DISPLAY_HIDE
     */
    protected String display = DISPLAY_TRUE;
    
    /**
     * define为true表示页面完全没有该片段，只用于定义引用。
     */
    protected boolean define = false;
    
    public String generalDealDisplay() {
        if (DISPLAY_FALSE.equals(display) || DISPLAY_HIDE.equals(display)) {
            return " style='display: none;' ";
        } else {
            return "";
        }
    }
    
    public String genAttr(String attrName, String attrValue) {
        char quot = '\'';
        if (null == attrValue) {
            attrValue = "";
        } else {
            if (attrValue.contains("\'")) {
                quot = '"';
            }
        }
        return new StringBuilder(attrName.length() + attrValue.length() + 5).append(' ').append(attrName)
                .append('=').append(quot).append(attrValue).append(quot).append(' ').toString();
    }
    
    @Override
    public String toHTML() {
        return PrintUtil.toString(getRelativeVmPath(), this);
    }
    
    protected abstract String getRelativeVmPath();
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getLabel() {
        return label;
    }
    
    public void setLabel(String label) {
        this.label = label;
    }
    
    public String getDisplay() {
        return display;
    }
    
    public void setDisplay(String display) {
        if (StringUtils.isEmpty(display)) {
            display = DISPLAY_TRUE;
        } else if (!DISPLAY_TRUE.equalsIgnoreCase(display) && !DISPLAY_FALSE.equalsIgnoreCase(display)
                && !DISPLAY_HIDE.equalsIgnoreCase(display)) {
            throw new IllegalArgumentException("illegal value for display, must in <empty>, " + DISPLAY_TRUE + ", "
                    + DISPLAY_FALSE + " or " + DISPLAY_HIDE);
        }
        this.display = display;
    }
    
    public boolean isDefine() {
        return define;
    }
    
    public void setDefine(boolean define) {
        this.define = define;
    }
    
    @Override
    public void addFragment(C fragment) {
        throw new UnsupportedOperationException("this is a leaf fragment");
    }
    
    @SuppressWarnings("unchecked")
    public S clone() {
        try {
            return (S) super.clone();
        } catch (CloneNotSupportedException e) {
            // omit
            return null;
        }
    }
    
}
