package ru.rosbank.javaschool.web.model;



import lombok.Data;


@Data
public class OrderModel {

    private int id;
    boolean isPaid;

    public OrderModel(int id, boolean isPaid) {
        this.id = id;
        this.isPaid = isPaid;
    }

    public OrderModel() {
        id = 0;
        isPaid = false;
    }
}
