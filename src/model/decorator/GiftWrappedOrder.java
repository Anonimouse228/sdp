package model.decorator;

import model.Order;

public class GiftWrappedOrder extends OrderDecorator {
    private double wrapPrice = 5.00;

    public GiftWrappedOrder(Order order) {
        super(order);
    }

    @Override
    public double calculateTotalPrice() {
        return super.calculateTotalPrice() + wrapPrice;
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " (with gift wrapping)";
    }
}
