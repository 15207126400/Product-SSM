package com.yunwei.context.config.configure.parse.formfield;


import org.dom4j.Element;

import com.yunwei.context.config.configure.element.FormField;
import com.yunwei.context.config.configure.element.HTMLFragment;
import com.yunwei.context.config.configure.element.Property;
import com.yunwei.context.config.configure.parser.ConfigureParser;


public abstract class SingleValidatorFormFieldParser<G extends FormField<?, ?>> extends BaseFormFieldParser<G> {
    
    public SingleValidatorFormFieldParser(ConfigureParser configureParser) {
        super(configureParser);
    }
    
    @Override
    public G parseHTMLFragment(Element element, HTMLFragment<?, ?> contextFragment) {
        if (contextFragment instanceof Property) {
            if (((Property) contextFragment).getFragments().size() > 1) {
                throw new IllegalArgumentException(
                        parseFor()
                                + " validator(select/radio/checkbox) should appear first and no more than once in one property");
            }
        }
        
        G formFiled = createFormField();
        G parsedFormFiled = parseBasicInfoAndDealDefine(element, formFiled, contextFragment);
        
        configureParser.parseChildElements(parsedFormFiled, element);
        
        return parsedFormFiled;
    }
    
    protected abstract G createFormField();
    
}
