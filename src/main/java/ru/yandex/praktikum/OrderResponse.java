package ru.yandex.praktikum;

import java.util.List;

public class OrderResponse {
    private boolean success;
    private List<Order> orders;

    public OrderResponse(boolean success) {
        this.success = success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public OrderResponse() {
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public OrderResponse(List<Order> orders) {
        this.orders = orders;
    }
}
