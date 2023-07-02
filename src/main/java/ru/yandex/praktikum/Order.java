package ru.yandex.praktikum;

import java.util.List;

public class Order {
    private String _id;
    private List<String> ingredients;
    private String status;
    private String name;
    private String createdAt;
    private String updatedAt;
    private int number;

    public void set_id(String _id) {
        this._id = _id;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String get_id() {
        return _id;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public String getStatus() {
        return status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getName() {
        return name;
    }

    public int getNumber() {
        return number;
    }

    public Order() {
    }

    public Order(String _id, List<String> ingredients, String status, String name, String createdAt, String updatedAt, int number) {
        this._id = _id;
        this.ingredients = ingredients;
        this.status = status;
        this.name = name;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.number = number;
    }
}
