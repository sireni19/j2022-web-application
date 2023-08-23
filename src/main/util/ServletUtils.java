package main.util;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ServletUtils {

    public static void forwarD(HttpServletRequest req, HttpServletResponse resp,String path) throws ServletException, IOException {
        RequestDispatcher dispatcher=req.getRequestDispatcher(path);
        dispatcher.forward(req,resp);
    }
    public static void includE(HttpServletRequest req, HttpServletResponse resp,String path,String message) throws ServletException, IOException {
        PrintWriter writer=resp.getWriter();
        RequestDispatcher dispatcher = req.getRequestDispatcher(path);
        writer.println(String.format("<h1>%s</h1>",message));
        dispatcher.include(req,resp);
    }
}
