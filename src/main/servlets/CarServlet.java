package main.servlets;

import main.jdbc.dao.CarDAO;
import main.jdbc.impl.CarDAOImplementation;
import main.jdbc.model.Car;
import main.util.HTMLTableBuilder;
import main.util.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/cars")
public class CarServlet extends HttpServlet {
    CarDAO DAO = new CarDAOImplementation();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        String action = req.getParameter("action");
        if (action != null) {
            if (action.equals("delete")) {
                int idToDelete = Integer.parseInt(req.getParameter("id"));
                DAO.deleteCarById(idToDelete);
            } else if (action.equals("update")) {
                int idToUpdate = Integer.parseInt(req.getParameter("id"));
                Car updatableCar = DAO.getCarById(idToUpdate);
                String updateFrom = IOUtils.readFileWithBuff("D:\\IDEAProjects\\j2022-web-application\\src\\main\\webapp\\car-update.html");
                updateFrom = updateFrom.replace("0?", updatableCar.getId() + "");
                updateFrom = updateFrom.replace("1?", updatableCar.getModel());
                updateFrom = updateFrom.replace("2?", updatableCar.getPrice() + "");
                updateFrom = updateFrom.replace("3?", updatableCar.getColor());
                resp.getWriter().println(updateFrom);
                return;
            } else if (action.equals("create")) {
                String createFrom = IOUtils.readFileWithBuff("D:\\IDEAProjects\\j2022-web-application\\src\\main\\webapp\\car-create.html");
                resp.getWriter().println(createFrom);
                return;
            }
        }

        List<Car> cars = DAO.getAllCars();
        HTMLTableBuilder builder = new HTMLTableBuilder("CARS", true, cars.size(), 7, 1, 1, 1);
        builder.addTableHeader("Id", "Model", "Price", "Color", "DELETE", "UPDATE", "<a href='/j2022_web_application_war/cars?action=create'>CREATE</a>");
        for (Car c : cars) {
            builder.addRowValues(c.getId(), c.getModel(), c.getPrice(), c.getColor(), "<a href='/j2022_web_application_war/cars?action=delete&id=" + c.getId() + "'>DELETE</a>",
                    "<a href='/j2022_web_application_war/cars?action=update&id=" + c.getId() + "'>UPDATE</a>", "");
        }
        String table = builder.build();
        resp.getWriter().println(table);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        String parameter = req.getParameter("form");
        System.out.println("parameter: "+parameter);
        if (parameter != null) {
            if (parameter.equals("upd")) {
                System.out.println(parameter);
                Car updatableCar = new Car();
                updatableCar.setId(Integer.parseInt(req.getParameter("id")));
                updatableCar.setModel(req.getParameter("model"));
                updatableCar.setPrice(Integer.parseInt(req.getParameter("price")));
                updatableCar.setColor(req.getParameter("color"));
                DAO.updateCar(updatableCar);
                doGet(req, resp);
            } else if (parameter.equals("add")) {
                System.out.println(parameter);
                Car newCar = new Car();
                newCar.setModel(req.getParameter("model"));
                newCar.setPrice(Integer.parseInt(req.getParameter("price")));
                newCar.setColor(req.getParameter("color"));
                System.out.println(newCar);
                DAO.createCar(newCar);
                doGet(req, resp);
            }
        }
    }


}
