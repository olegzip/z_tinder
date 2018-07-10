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
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
    PrintWriter writer = resp.getWriter();
    String sender = "";
    String reciever = req.getParameter("reciever");
    String id = req.getParameter("id");
    Cookie[] cookies = req.getCookies();
    for (Cookie cookie : cookies) {
      if (cookie.getName().equals("user-id")) {
        sender = cookie.getValue();
        break;
      }
    }
    String text = req.getParameter("message");
    if(text!=null){
        messagesDao.saveMessage(sender, reciever, text);
    }

    Map<String, Object> variables = new HashMap<>();
    List<Message> messages = messagesDao.getAllMessages(sender, reciever);

    variables.put("id", id);
    variables.put("reciever", reciever);
    variables.put("messages", messages);

    FreemarkerHandler.processTemplate(writer, variables, "messages.html");
  }
}