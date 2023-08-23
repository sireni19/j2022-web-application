package main.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

public class AboutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter pw = resp.getWriter();
        // Формирование HTML-кода страницы "О нас"
        pw.println("<html><head><title>About us</title></head><body>");
        pw.println("<h1>О нас</h1>");
        pw.println("<p>We are the company which works with program equipment.</p>");
        pw.println("<p>Server time "+new Date()+"</p>");
        pw.println("</body></html>");
    }
}

