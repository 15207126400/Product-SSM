package com.yunwei.context.config.configure.parse.formfield;

import com.yunwei.context.config.configure.formfield.RadioFormField;
import com.yunwei.context.config.configure.parser.ConfigureParser;


public class RadioFormFieldParser extends SingleValidatorFormFieldParser<RadioFormField> {
    
    public RadioFormFieldParser(ConfigureParser configureParser) {
        super(configureParser);
    }
    
    @Override
    public String parseFor() {
        return "radio";
    }
    
    @Override
    protected RadioFormField createFormField() {
        return new RadioFormField();
    }
    
}
