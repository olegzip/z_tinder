package servlet;

import dao.MessagesDao;
import entity.Message;
import util.FreemarkerHandler;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessagesServlet extends HttpServlet {

  private MessagesDao messagesDao;
  public MessagesServlet(MessagesDao messagesDao){
    this.messagesDao = messagesDao;
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
    doPost(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    PrintWriter writer = resp.getWriter();

    String from = "";
    String to = req.getParameter("to");
    Cookie[] cookies = req.getCookies();
    for (Cookie cookie : cookies) {
      if (cookie.getName().equals("user-id")) {
        from = cookie.getValue();
        break;
      }
    }

    String messageToSend = req.getParameter("message");
    if(messageToSend!=null){
        messagesDao.saveMessage(from, to, req.getParameter(messageToSend));
    }

    Map<String, Object> variables = new HashMap<>();
    List<Message> messages = messagesDao.getAllMessages(from, to);
    variables.put("messages", messages);

    FreemarkerHandler.processTemplate(writer, variables, "messages.html");
  }
}