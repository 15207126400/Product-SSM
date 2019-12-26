package com.yunwei.context.config.configure.parse.formfield;


import org.dom4j.Element;

import com.yunwei.context.config.configure.element.Config;
import com.yunwei.context.config.configure.element.FormField;
import com.yunwei.context.config.configure.element.Group;
import com.yunwei.context.config.configure.element.HTMLFragment;
import com.yunwei.context.config.configure.element.Property;
import com.yunwei.context.config.configure.parser.AbstractHTMLFragmentParser;
import com.yunwei.context.config.configure.parser.ConfigureParser;


public abstract class BaseFormFieldParser<G extends FormField<?, ?>> extends AbstractHTMLFragmentParser<G>
        implements FormFieldParser<G> {
    
    public BaseFormFieldParser(ConfigureParser configureParser) {
        super(configureParser);
    }
    
    protected G parseBasicInfoAndDealDefine(Element element, G oriFormField, HTMLFragment<?, ?> contextFragment) {
        G parsedFormFiled = parseForAbstractHTMLFragment(oriFormField, element);
        if (contextFragment instanceof Group || contextFragment instanceof Config) {
            parsedFormFiled.setDefine(true);
        } else if (contextFragment instanceof Property) {
            // 如果上面的解析中发生了ref，
            // 因为被引用元素如果在group或config中，可能是默认define，
            // 所以如果此处引用的元素上没有定义define，默认设置为非define
            if (parsedFormFiled != oriFormField) {
                String define = element.attributeValue("define");
                if (null == define) {
                    parsedFormFiled.setDefine(false);
                }
            }
        }
        return parsedFormFiled;
    }
    
}
