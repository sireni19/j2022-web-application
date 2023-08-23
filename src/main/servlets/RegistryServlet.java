package main.servlets;

import lombok.SneakyThrows;
import main.jdbc.dao.UserDAO;
import main.jdbc.impl.UserDAOImplementation;
import main.jdbc.model.User;
import main.util.EmailUtils;
import main.util.EncDecUtils;
import main.util.IOUtils;
import main.util.ServletUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import static main.util.ServletUtils.*;
@WebServlet("/registry")
public class RegistryServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        forwarD(req,resp,"registration.html");
    }

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String newName=req.getParameter("name");
        String newEmail=req.getParameter("email");
        String newPassword1=req.getParameter("pwd1");
        String newPassword2=req.getParameter("pwd1");

        if(!newPassword1.equals(newPassword2)){
            includE(req,resp,"registration.html","Please check passwords");
        }else {
            User newUser=new User(newName,newEmail,new EncDecUtils().encrypt(newPassword1));
            UserDAO DAO=new UserDAOImplementation();
            boolean isCreated=DAO.createUser(newUser);
            if(isCreated){
                String url="http://localhost:8080/j2022_web_application_war/activation?token="+(EncDecUtils.encrypt(newEmail));
//                String template= IOUtils.readFileWithBuff("D:\\IDEAProjects\\j2022-web-application\\src\\main\\webapp\\templates\\email_activation.html").replace("???",url) ЭТО НЕ РАБОТАЕТ;
                //template это email-activation.html только с a href=url
//                EmailUtils.sentEmail(newEmail.trim(),"Crazy Users App Activation",template) ЭТО НЕ РАБОТАЕТ;
                includE(req,resp,"registration.html","Successfully registration, please check your email for activation");
            }else {
                includE(req,resp,"registration.html","Oops! Some technical error. Try later");
            }
        }
    }
}
