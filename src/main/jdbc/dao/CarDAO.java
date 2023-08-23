package main.jdbc.dao;

import main.jdbc.model.Car;

import java.util.List;

public interface CarDAO {

    List<Car> getAllCars();
    void deleteCarById(int id);
    Car getCarById(int id);
    void updateCar(Car upCar);

    void createCar(Car newCar);


}
