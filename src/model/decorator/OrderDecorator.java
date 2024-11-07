package model.decorator;

import model.Order;

public abstract class OrderDecorator implements Order {
    protected Order decoratedOrder;

    public OrderDecorator(Order order) {
        this.decoratedOrder = order;
    }

    @Override
    public double calculateTotalPrice() {
        return decoratedOrder.calculateTotalPrice();
    }

    @Override
    public String getDescription() {
        return decoratedOrder.getDescription();
    }
}
