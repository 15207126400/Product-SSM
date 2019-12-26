package com.yunwei.common.mvc.converter;

import org.apache.commons.lang3.ClassUtils;
import org.dom4j.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 功能说明: <br>
 * 系统版本: v1.0<br>
 * 开发人员: @author guanhui<br>
 * 开发时间: 2016年09月12日<br>
 */
public class XmlHttpMessageConverter implements HttpMessageConverter<Document> {

    private Logger logger = LoggerFactory.getLogger(getClass());

    public boolean canRead(Class<?> clazz, MediaType mediaType) {
        return (Document.class.equals(clazz));
    }

    public boolean canWrite(Class<?> clazz, MediaType mediaType) {
        boolean flag=ClassUtils.isAssignable(clazz,Document.class);
        return flag;
    }

    public List<MediaType> getSupportedMediaTypes() {
        List<MediaType> mediaTypes=new ArrayList<MediaType>();
        mediaTypes.add(new MediaType("application", "*+xml"));
        return mediaTypes;
    }

    public Document read(Class<? extends Document> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        return null;
    }

    public void write(Document document, MediaType contentType, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        MediaType mediaType=MediaType.parseMediaType("text/xml;charset=utf-8");
        outputMessage.getHeaders().setContentType(mediaType);
        String xmlContent=document.asXML();
        outputMessage.getBody().write(xmlContent.getBytes("UTF-8"));
        outputMessage.getBody().flush();
    }

}
