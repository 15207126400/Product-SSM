package com.yunwei.context.config.configure.parse.formfield;

import com.yunwei.context.config.configure.formfield.CheckBoxFormField;
import com.yunwei.context.config.configure.parser.ConfigureParser;



public class CheckBoxFormFieldParser extends SingleValidatorFormFieldParser<CheckBoxFormField> {
    
    public CheckBoxFormFieldParser(ConfigureParser configureParser) {
        super(configureParser);
    }
    
    @Override
    public String parseFor() {
        return "checkbox";
    }
    
    @Override
    protected CheckBoxFormField createFormField() {
        return new CheckBoxFormField();
    }
    
}
