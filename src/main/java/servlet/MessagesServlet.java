package servlet;

import util.FreemarkerHandler;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessagesServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    PrintWriter writer = resp.getWriter();

    Map<String, Object> variables = new HashMap<>();
    List<String> messages = new ArrayList<>();
    messages.add("Hello");
    messages.add("Hi");
    messages.add("How are you?");
    variables.put("messages", messages);

    FreemarkerHandler.processTemplate(writer, variables, "messages.html");
  }
}