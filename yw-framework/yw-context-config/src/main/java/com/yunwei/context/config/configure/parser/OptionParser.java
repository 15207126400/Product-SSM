package com.yunwei.context.config.configure.parser;


import org.dom4j.Element;

import com.yunwei.context.config.configure.element.HTMLFragment;
import com.yunwei.context.config.configure.formfield.restriction.Option;
import com.yunwei.context.config.configure.formfield.restriction.RadioOption;


public class OptionParser extends AbstractHTMLFragmentParser<Option> {
    
    public OptionParser(ConfigureParser configureParser) {
        super(configureParser);
    }
    
    @Override
    public String parseFor() {
        return "option";
    }
    
    @Override
    public Option parseHTMLFragment(Element element, HTMLFragment<?, ?> contextFragment) {
        Element parentElement = element.getParent();
        String formFiledType = parentElement.attributeValue("type");
        
        Option option = null;
        if ("radio".equals(formFiledType) || "checkbox".equals(formFiledType)) {
            option = new RadioOption();
        } else {
            option = new Option();
        }
        
        String value = element.attributeValue("value");
        option.setValue(value);
        
        String label = element.attributeValue("label");
        if (null == label) {
            label = value;
        }
        option.setLabel(label);
        
        String description = element.attributeValue("description");
        if (null != description) {
            option.setDescription(description);
        }
        
        String related = element.attributeValue("related");
        if (null != related) {
            option.setRelated(related);
        }
        
        String display = element.attributeValue("display");
        if (null != display) {
            option.setDisplay(display);
        }
        
        return option;
    }
    
}
