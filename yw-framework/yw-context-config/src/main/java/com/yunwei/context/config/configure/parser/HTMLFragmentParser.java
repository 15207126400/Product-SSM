package com.yunwei.context.config.configure.parser;


import org.dom4j.Element;

import com.yunwei.context.config.configure.element.HTMLFragment;


public interface HTMLFragmentParser<G extends HTMLFragment<?, ?>> {
    
    public String parseFor();
    
    public G parseHTMLFragment(Element element, HTMLFragment<?, ?> contextFragment);
    
}
