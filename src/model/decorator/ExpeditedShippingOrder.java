package model.decorator;

import model.Order;

public class ExpeditedShippingOrder extends OrderDecorator {
    private double expeditedShippingCost = 15.00; // Fixed shipping price

    public ExpeditedShippingOrder(Order order) {
        super(order);
    }

    @Override
    public double calculateTotalPrice() {
        return super.calculateTotalPrice() + expeditedShippingCost;
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " (with expedited shipping)";
    }
}
