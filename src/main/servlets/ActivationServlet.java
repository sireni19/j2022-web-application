package main.servlets;

import lombok.SneakyThrows;
import main.jdbc.dao.UserDAO;
import main.jdbc.impl.UserDAOImplementation;
import main.jdbc.model.User;
import main.util.EncDecUtils;
import main.util.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/activation")
public class ActivationServlet extends HttpServlet {

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token = req.getParameter("token").replace(" ","+");
        try {
            String email = EncDecUtils.decrypt(token);
//            String[] parameters = decryptedtoken.split("\\?");
//            String email = parameters[1];
            UserDAO dao = new UserDAOImplementation();
            User user = dao.getUserByEmail(email);
            if (user == null) {
                String template = IOUtils.readFileWithBuff
                        ("D:\\IDEAProjects\\j2022-web-application\\src\\main\\webapp\\templates\\email_activation.html").replace("???", "Email account isn`t valid!!!");
                resp.getWriter().println(template);
                return;
            } else {
                if(dao.activate(email)){
                    resp.getWriter().println("Activated");
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}