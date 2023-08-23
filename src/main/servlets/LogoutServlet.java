package main.servlets;

import main.jdbc.model.User;
import main.util.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User sessionUser=ServletUtils.getUserFromSession(req);
        if(sessionUser!=null){
            System.out.println("Session "+req.getSession().getId()+" has been closed");
            req.getSession().invalidate();
            resp.setContentType("text/html");
            resp.getWriter().println("<h1>Logout was successfully.</h1> <br> <a href='login'>Login again</a> ");
        }else {
            ServletUtils.forwarD(req,resp,"login.html");
        }


    }
}
