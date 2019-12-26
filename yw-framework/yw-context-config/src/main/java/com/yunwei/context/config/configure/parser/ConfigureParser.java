package com.yunwei.context.config.configure.parser;


import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yunwei.context.config.configure.element.Config;
import com.yunwei.context.config.configure.element.HTMLFragment;


public class ConfigureParser {
    
    private static final Logger LOG = LoggerFactory.getLogger("Config-Manager");
    
    private Map<Object, Object> dataStore;
    
    private Map<String, HTMLFragment<?, ?>> fullFragmentsMap = new HashMap<String, HTMLFragment<?, ?>>();
    
    private Map<String, HTMLFragmentParser<?>> generalParsers = new HashMap<String, HTMLFragmentParser<?>>();
    
    public ConfigureParser() {
        init();
    }
    
    private void init() {
        addGeneralParser(new GroupParser(this));
        addGeneralParser(new PropertyParser(this));
        addGeneralParser(new FormFieldsParser(this));
        addGeneralParser(new OptionParser(this));
    }
    
    private void addGeneralParser(HTMLFragmentParser<?> parser) {
        generalParsers.put(parser.parseFor(), parser);
    }
    
    public Config parserConfigureFromFullPath(String configFilePath) {
        try {
            SAXReader reader = new SAXReader();
            Document document = reader.read(new File(configFilePath));
            
            return parserConfigure(document);
        } catch (Exception e) {
            LOG.error("读取配置模板" + configFilePath + "出错", e);
        }
        return null;
    }
    
    public Config parserConfigure(InputStream inputStream) throws DocumentException {
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStream);
        
        return parserConfigure(document);
    }
    
    public Config parserConfigure(Document document) {
        Element root = document.getRootElement();
        
        Config config = parseConfigElement(root);
        
        parseChildElements(config, root);
        
        return config;
    }
    
    private Config parseConfigElement(Element configRoot) {
        Config config = new Config();
        return config;
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void parseChildElements(HTMLFragment contextFragment, Element contextElement) {
        List<Element> elements = contextElement.elements();
        for (Element element : elements) {
            HTMLFragment fragment = parseHTMLFragment(element, contextFragment);
            if (!fragment.isDefine()) {
                contextFragment.addFragment(fragment);
            }
        }
    }
    
    private HTMLFragment<?, ?> parseHTMLFragment(Element element, HTMLFragment<?, ?> contextFragment) {
        String nodeName = element.getName();
        HTMLFragmentParser<?> parser = generalParsers.get(nodeName);
        if (null == parser) {
            throw new IllegalArgumentException("illegal element: " + nodeName + ", no corresponding parser defined");
        }
        return parser.parseHTMLFragment(element, contextFragment);
    }
    
    public Map<Object, Object> getDataStore() {
        return dataStore;
    }
    
    public void setDataStore(Map<Object, Object> dataStore) {
        this.dataStore = dataStore;
    }
    
    public Map<String, HTMLFragment<?, ?>> getFullFragmentsMap() {
        return fullFragmentsMap;
    }
    
}
