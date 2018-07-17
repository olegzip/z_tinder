package servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class StaticServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String url = req.getPathInfo();
        if (url!=null) {
            Path in = Paths.get("src/main/resources/static/html/css", url);
            Files.copy(in, resp.getOutputStream());
        } else {
            resp.getWriter().print("you should pass the file name after slash");
        }
    }
}