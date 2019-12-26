package com.yunwei.context.config.configure.parser;


import org.dom4j.Element;

import com.yunwei.context.config.configure.element.Block;
import com.yunwei.context.config.configure.element.Group;
import com.yunwei.context.config.configure.element.HTMLFragment;


public class GroupParser extends AbstractHTMLFragmentParser<Group<? extends Group<?, ?>, ? extends Block<?, ?>>> {
    
    public GroupParser(ConfigureParser configureParser) {
        super(configureParser);
    }
    
    @Override
    public String parseFor() {
        return "group";
    }
    
    @SuppressWarnings("rawtypes")
    @Override
    public Group parseHTMLFragment(Element element, HTMLFragment<?, ?> contextFragment) {
        Group group = new Group();
        group = parseForAbstractHTMLFragment(group, element);
        
        configureParser.parseChildElements(group, element);
        
        return group;
    }
    
}
