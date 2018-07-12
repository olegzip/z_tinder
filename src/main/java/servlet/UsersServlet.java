package servlet;

import dao.UsersDao;
import entity.User;
import util.FreemarkerHandler;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class UsersServlet extends HttpServlet {
  private UsersDao usersDao;

  public UsersServlet(UsersDao usersDao) {
    this.usersDao = usersDao;
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    PrintWriter writer = resp.getWriter();

    Map<String, Object> variables = new HashMap<>();
    User user = usersDao.getNotLikedUser();

    if (user == null) {
      resp.sendRedirect("/liked");
      return;
    }

    variables.put("user", user);

    FreemarkerHandler.processTemplate(writer, variables, "users.html", this.getClass());
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    String userChoice = req.getParameter("choice");
    String name = req.getParameter("name");

    usersDao.saveLike(name, "yes".equals(userChoice));

    doGet(req, resp);
  }
}