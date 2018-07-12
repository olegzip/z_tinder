package servlet;

import dao.UsersDao;
import entity.User;
import util.FreemarkerHandler;
import util.SetLikesToNull;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

public class LoginServlet extends HttpServlet {

  //Сброс лайков при каждом запуске:
  static{
    SetLikesToNull.setLikesToNull();
  }

  private UsersDao usersDao;

  public LoginServlet(UsersDao usersDao) {
    this.usersDao = usersDao;
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    PrintWriter writer = resp.getWriter();

    FreemarkerHandler.processTemplate(writer, new HashMap<>(), "login.html", this.getClass());
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    String login = req.getParameter("login");
    String password = req.getParameter("password");

    User user = usersDao.getUserByLoginAndPassword(login, password);

    if (user == null) {
      resp.getWriter().write("Wrong credentials");
    } else {
      Cookie cookie = new Cookie("user-id", user.getName());
      resp.addCookie(cookie);
      resp.sendRedirect("/users");
    }
  }
}