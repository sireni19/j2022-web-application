package main.jdbc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Car {

    private int id;
    private String model;
    private int price;
    private String color;

    public Car(String model, int price, String color) {
        this.model = model;
        this.price = price;
        this.color = color;
    }
}
