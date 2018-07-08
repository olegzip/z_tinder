package util;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

public class FreemarkerHandler {
  public static void processTemplate(PrintWriter writer, Map<String, Object> variables, String templateName) throws IOException {
    Configuration cfg = new Configuration(Configuration.VERSION_2_3_28);
    cfg.setDirectoryForTemplateLoading(new File("./src/main/resources/static/html"));
    cfg.setDefaultEncoding("UTF-8");
    cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
    cfg.setLogTemplateExceptions(false);
    cfg.setWrapUncheckedExceptions(true);

    Template template = cfg.getTemplate(templateName);

    try {
      template.process(variables, writer);
    } catch (TemplateException e) {
      e.printStackTrace();
    }
  }
}