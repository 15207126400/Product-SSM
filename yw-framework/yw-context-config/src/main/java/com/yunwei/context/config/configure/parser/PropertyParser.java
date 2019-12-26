package com.yunwei.context.config.configure.parser;


import org.dom4j.Element;

import com.yunwei.context.config.configure.element.HTMLFragment;
import com.yunwei.context.config.configure.element.Property;


public class PropertyParser extends AbstractHTMLFragmentParser<Property> {
    
    public PropertyParser(ConfigureParser configureParser) {
        super(configureParser);
    }
    
    @Override
    public String parseFor() {
        return "property";
    }
    
    @Override
    public Property parseHTMLFragment(Element element, HTMLFragment<?, ?> contextFragment) {
        Property property = new Property();
        property = parseForAbstractHTMLFragment(property, element);
        
        String defaultValue = element.attributeValue("default");
        if (null != defaultValue) {
            property.setDefaultValue(defaultValue);
        }
        
        String description = element.attributeValue("description");
        if (null != description) {
            property.setDescription(description);
        }
        
        property.setDataStore(configureParser.getDataStore());
        
        configureParser.parseChildElements(property, element);
        
        return property;
    }
    
}
