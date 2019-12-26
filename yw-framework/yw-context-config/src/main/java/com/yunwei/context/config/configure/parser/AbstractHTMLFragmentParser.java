package com.yunwei.context.config.configure.parser;


import org.dom4j.Element;

import com.yunwei.context.config.configure.element.AbstractHTMLFragment;
import com.yunwei.context.config.configure.element.HTMLFragment;


public abstract class AbstractHTMLFragmentParser<G extends HTMLFragment<?, ?>> implements HTMLFragmentParser<G> {
    
    protected ConfigureParser configureParser;
    
    public AbstractHTMLFragmentParser(ConfigureParser configureParser) {
        this.configureParser = configureParser;
    }
    
    @SuppressWarnings("unchecked")
    protected <T extends AbstractHTMLFragment<?, ?>> T parseForAbstractHTMLFragment(T fragment, Element element) {
        String id = element.attributeValue("id");
        String name = element.attributeValue("name");
        if (null == id) {
            if (null == name) {
                //throw new IllegalArgumentException("id or name is required");
            } else {
                id = name;
            }
        }
        fragment.setId(id);
        
        String ref = element.attributeValue("ref");
        if (null != ref) {
            HTMLFragment<?, ?> refFragment = configureParser.getFullFragmentsMap().get(ref);
            if (null == refFragment) {
                throw new IllegalArgumentException("no ref element found: " + ref + ", at element: " + id);
            } else {
                fragment = (T) refFragment.clone();
                // 类型错误自己抛异常
                fragment.setId(id);
            }
        }
        
        if (null != name) {
            fragment.setName(name);
        }
        
        String label = element.attributeValue("label");
        if (null != label) {
            fragment.setLabel(label);
        }
        
        String display = element.attributeValue("display");
        if (null != display) {
            fragment.setDisplay(display);
        }
        
        String define = element.attributeValue("define");
        if (null != define) {
            boolean isDefine = Boolean.parseBoolean(define);
            fragment.setDefine(isDefine);
        }
        
        if (null != id) {
            configureParser.getFullFragmentsMap().put(id, fragment);
        }
        return fragment;
    }
    
}
