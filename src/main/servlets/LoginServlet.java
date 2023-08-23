package main.servlets;

import lombok.SneakyThrows;
import main.jdbc.dao.UserDAO;
import main.jdbc.impl.UserDAOImplementation;
import main.jdbc.model.User;
import main.util.EncDecUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import static main.util.ServletUtils.*;
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter writer = resp.getWriter();
        HttpSession session=req.getSession();
        Object sessionObject=null;
        if((sessionObject=session.getAttribute("USER"))!=null){
            System.out.println("Login http session id= "+session.getId());
            User user= (User) sessionObject;
            writer.println("<h1> Gutten tag, liebe "+user.getName()+"</h1>");
            writer.println("<br><a href='logout'>Logout</a>");
        }else{
            System.out.println(new Date() + " : " + req.getPathInfo() + " | " + req.getServletPath() + " | " + req.getContextPath());
            forwarD(req,resp,"login.html");
        }

    }

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher rd = req.getRequestDispatcher("login.html");
        resp.setContentType("text/html");
        PrintWriter writer = resp.getWriter();

        String email = req.getParameter("email");
        String password = req.getParameter("pwd");

        //check creds from DB
        UserDAO dao = new UserDAOImplementation();
        User userFromDB = dao.getUserByEmail(email.trim());
        if(userFromDB == null){
            //case : user not register yet
            writer.println("<h1>Please check your email or <a href='registry'> Sign Up </a></h1>");
            rd.include(req, resp);
        } else {
            //case : user presented in DB
            if(new EncDecUtils().decrypt(userFromDB.getPwd()).equals(password)){
                writer.println("<h1>Welcome back, dear " + userFromDB.getName() + "</h1>");
                HttpSession session=req.getSession();
                session.setAttribute("USER",userFromDB);
                System.out.println("Http session has been created with ID: "+session.getId());
                session.setMaxInactiveInterval(600);
            } else {
                writer.println("<h1>Please check your password. See <a href='reset'?email="+userFromDB.getEmail()+"'> Reset instruction </a></h1>");
                rd.include(req, resp);
            }
        }

    }
}
