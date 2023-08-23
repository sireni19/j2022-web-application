package main.servlets;

import main.jdbc.model.User;
import main.util.ServletUtils;

import javax.mail.Session;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User sessionUser = ServletUtils.getUserFromSession(req);
        resp.setContentType("text/html");
        PrintWriter writer = resp.getWriter();
        if (sessionUser == null) {
            ServletUtils.returnHTML(resp).println("<h1> Who are you?</h1>"+
                    "Say your name to enter right <a href='logout'>THERE</a>");
        } else {
            ServletUtils.returnHTML(resp).println("<h1> Ah, is that you? Honey " + sessionUser.getName() + " welcome back</h1>"
            +"If you want to leave, click on <a href='logout'>THIS</a>");
        }
    }
}
