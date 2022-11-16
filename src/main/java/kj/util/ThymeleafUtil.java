package kj.util;

import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.io.IOException;

public class ThymeleafUtil {

    private static final TemplateEngine engine;

    static {
        engine = new TemplateEngine();
        ClassLoaderTemplateResolver r = new ClassLoaderTemplateResolver();
        r.setCharacterEncoding("UTF-8");
        engine.setTemplateResolver(r);
    }

    private ThymeleafUtil() {}


    public static void process(String template, Context context, HttpServletResponse resp) {
        try {
            engine.process(template, context, resp.getWriter());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
