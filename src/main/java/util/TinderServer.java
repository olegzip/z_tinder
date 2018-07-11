package util;

import dao.MessagesDao;
import dao.UsersDao;
import filter.LoginFilter;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import servlet.*;

public class TinderServer {
  public void start() throws Exception {
    Server server = new Server(8080);
    ServletContextHandler handler = new ServletContextHandler();

    UsersDao usersDao = new UsersDao();
    MessagesDao messagesDao = new MessagesDao();

    ServletHolder usersHolder = new ServletHolder(new UsersServlet(usersDao));
    handler.addServlet(usersHolder, "/users");

    ServletHolder likedHolder = new ServletHolder(new LikedServlet(usersDao));
    handler.addServlet(likedHolder, "/liked");

    ServletHolder messagesHolder = new ServletHolder(new MessagesServlet(messagesDao));
    handler.addServlet(messagesHolder, "/messages/*");

    ServletHolder loginHolder = new ServletHolder(new LoginServlet(usersDao));
    handler.addServlet(loginHolder, "/login");

    ServletHolder staticHolder = new ServletHolder(new StaticServlet());
    handler.addServlet(staticHolder, "/css/*");

    FilterHolder loginFilter = new FilterHolder(new LoginFilter());
    handler.addFilter(loginFilter, "/*", null);

    server.setHandler(handler);
    server.start();
    server.join();
  }
}
