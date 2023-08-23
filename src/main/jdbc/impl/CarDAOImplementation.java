package main.jdbc.impl;

import main.jdbc.DBUtils;
import main.jdbc.dao.CarDAO;
import main.jdbc.model.Car;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarDAOImplementation implements CarDAO {
    @Override
    public List<Car> getAllCars() {

        try (Connection connection = DBUtils.getConnection()) {
            List<Car> cars = new ArrayList();
            PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM web_users_db.cars");
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                Car car = new Car();
                car.setId(resultSet.getInt("id"));
                car.setModel(resultSet.getString("model"));
                car.setPrice(resultSet.getInt("price"));
                car.setColor(resultSet.getString("color"));
                cars.add(car);
            }
            return cars;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void deleteCarById(int id) {
        try (Connection connection = DBUtils.getConnection()) {
            PreparedStatement pstmt = connection.prepareStatement("DELETE from web_users_db.cars where id=?");
            pstmt.setInt(1, id);
            int counter = pstmt.executeUpdate();
            if (counter == 1) {
                System.out.println("Car with id " + id + " was deleted successfully");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Car getCarById(int id) {
        Car car = null;
        try (Connection connection = DBUtils.getConnection()) {
            car = new Car();
            String sql = "SELECT * FROM web_users_db.cars where id=?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1,id);
            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                car.setId(resultSet.getInt("id"));
                car.setModel(resultSet.getString("model"));
                car.setPrice(resultSet.getInt("price"));
                car.setColor(resultSet.getString("color"));
                return car;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return car;
    }

    @Override
    public void updateCar(Car upCar) {
        String sql = "UPDATE cars SET model = ?, price = ?, color = ? WHERE cars.id = ? ";
        try (Connection connection = DBUtils.getConnection()) {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, upCar.getModel());
            pstmt.setInt(2, upCar.getPrice());
            pstmt.setString(3, upCar.getColor());
            pstmt.setInt(4, upCar.getId());
            int counter = pstmt.executeUpdate();
            if (counter == 1) {
                System.out.println("Car with id " + upCar.getId() + " was updated successfully");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void createCar(Car newCar) {
        try (Connection connection = DBUtils.getConnection()) {
            String sql="INSERT INTO cars (model, price, color) VALUES  (?, ?, ?)";
            PreparedStatement pstmt=connection.prepareStatement(sql);
            pstmt.setString(1, newCar.getModel());
            pstmt.setInt(2, newCar.getPrice());
            pstmt.setString(3, newCar.getColor());
            int counter=pstmt.executeUpdate();
            if(counter==1){
                System.out.println("Car was created");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
