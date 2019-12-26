package com.yunwei.context.config.configure.util;


import java.io.StringWriter;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yunwei.context.config.configure.element.HTMLFragment;

public class PrintUtil {
    
    private static final Logger LOG = LoggerFactory.getLogger("Config-Manager");
    
    private static final String UTF8 = "UTF-8";
    
    private static String vmBasePath;
    
    private static VelocityEngine engine = new VelocityEngine();
    static {
        init();
    }
    
    private static void init() {
        vmBasePath = Thread.currentThread().getContextClassLoader().getResource("/").getPath();
        vmBasePath += "/configure-vm/";
        
        engine.setProperty(Velocity.RESOURCE_LOADER, "file");
        engine.setProperty("class.resource.loader.class",
                "org.apache.velocity.runtime.resource.loader.FileResourceLoader");
        engine.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, "");
        engine.init();
    }
    
    public static String toString(String relativeVmPath, HTMLFragment<?, ?> fragment) {
        try {
            String vmPath = vmBasePath + relativeVmPath;
            Template template = engine.getTemplate(vmPath, UTF8);
            
            VelocityContext context = new VelocityContext();
            context.put("f", fragment);
            
            StringWriter writer = new StringWriter();
            
            template.merge(context, writer);
            
            return writer.toString();
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("merge template failed: " + relativeVmPath, e);
            return "";
        }
    }
    
    public static void setVmBasePath(String vmBasePath) {
        PrintUtil.vmBasePath = vmBasePath;
    }
    
}
