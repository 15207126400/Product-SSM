package com.yunwei.context.config.configure.parse.formfield;


import org.dom4j.Element;

import com.yunwei.context.config.configure.element.HTMLFragment;
import com.yunwei.context.config.configure.element.Property;
import com.yunwei.context.config.configure.formfield.InputFormField;
import com.yunwei.context.config.configure.formfield.restriction.BasicValidator;
import com.yunwei.context.config.configure.parser.ConfigureParser;


public class InputFormFieldParser extends BaseFormFieldParser<InputFormField> {
    
    public InputFormFieldParser(ConfigureParser configureParser) {
        super(configureParser);
    }
    
    @Override
    public String parseFor() {
        return COMMON_FORM_FIELD_TYPE;// 除了select/radio/checkbox以外的validator
    }
    
    @Override
    public InputFormField parseHTMLFragment(Element element, HTMLFragment<?, ?> contextFragment) {
        InputFormField formFiled = null;
        if (contextFragment instanceof Property) {
            Property property = (Property) contextFragment;
            if (1 == property.getFragments().size()) {
                HTMLFragment<?, ?> existingFormFiled = property.getFragments().get(0);
                if (!(existingFormFiled instanceof InputFormField)) {
                    throw new IllegalArgumentException("select/radio/checkbox validator should not mix with others");
                } else {
                    // 只能有一个formField，先删除
                    property.getFragments().clear();
                    formFiled = (InputFormField) existingFormFiled;
                }
            } else {
                formFiled = new InputFormField();
            }
        }
        
        InputFormField parsedFormFiled = parseBasicInfoAndDealDefine(element, formFiled, contextFragment);
        
        BasicValidator basicValidator = createBasicValidator(element);
        formFiled.addFragment(basicValidator);
        
        return parsedFormFiled;
    }
    
    protected BasicValidator createBasicValidator(Element element) {
        BasicValidator basicValidator = new BasicValidator();
        
        String type = element.attributeValue("type");
        basicValidator.setType(type);
        
        String value = element.attributeValue("value");
        if (null == value) {
            value = "true";
        }
        basicValidator.setValue(value);
        
        return basicValidator;
    }
    
}
