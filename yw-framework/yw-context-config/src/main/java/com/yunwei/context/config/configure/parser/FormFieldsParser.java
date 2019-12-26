package com.yunwei.context.config.configure.parser;


import java.util.HashMap;
import java.util.Map;

import org.dom4j.Element;

import com.yunwei.context.config.configure.element.FormField;
import com.yunwei.context.config.configure.element.HTMLFragment;
import com.yunwei.context.config.configure.parse.formfield.CheckBoxFormFieldParser;
import com.yunwei.context.config.configure.parse.formfield.FormFieldParser;
import com.yunwei.context.config.configure.parse.formfield.InputFormFieldParser;
import com.yunwei.context.config.configure.parse.formfield.RadioFormFieldParser;
import com.yunwei.context.config.configure.parse.formfield.SelectFormFieldParser;


public class FormFieldsParser extends AbstractHTMLFragmentParser<FormField<?, ?>> {
    
    private Map<String, FormFieldParser<?>> formFieldParsers = new HashMap<String, FormFieldParser<?>>();
    
    public FormFieldsParser(ConfigureParser configureParser) {
        super(configureParser);
        init();
    }
    
    private void init() {
        addFormFieldParser(new InputFormFieldParser(configureParser));
        addFormFieldParser(new SelectFormFieldParser(configureParser));
        addFormFieldParser(new RadioFormFieldParser(configureParser));
        addFormFieldParser(new CheckBoxFormFieldParser(configureParser));
    }
    
    private void addFormFieldParser(FormFieldParser<?> parser) {
        formFieldParsers.put(parser.parseFor(), parser);
    }
    
    @Override
    public String parseFor() {
        return "validator";
    }
    
    @Override
    public FormField<?, ?> parseHTMLFragment(Element element, HTMLFragment<?, ?> contextFragment) {
        String type = element.attributeValue("type");
        if (null == type) {
            throw new IllegalArgumentException("type of validator is required");
        }
        
        FormFieldParser<?> parser = formFieldParsers.get(type);
        if (null == parser) {
            parser = formFieldParsers.get(FormFieldParser.COMMON_FORM_FIELD_TYPE);// 除了select/radio/checkbox以外的validator
        }
        FormField<?, ?> formField = parser.parseHTMLFragment(element, contextFragment);
        
        return formField;
    }
    
}
