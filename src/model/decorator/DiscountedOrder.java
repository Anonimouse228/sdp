package model.decorator;

import model.Order;

public class DiscountedOrder extends OrderDecorator {
    private double discountPercentage;

    public DiscountedOrder(Order order, double discountPercentage) {
        super(order);
        this.discountPercentage = discountPercentage;
    }

    @Override
    public double calculateTotalPrice() {
        double price = super.calculateTotalPrice();
        return price - (price * discountPercentage / 100);
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " (with " + discountPercentage + "% discount)";
    }
}
