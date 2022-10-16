package order;

import java.util.ArrayList;

public class OrdersList {
    String success;

    String total;

    String totalToday;
    private ArrayList<Order> orders;

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getTotalToday() {
        return totalToday;
    }

    public void setTotalToday(String totalToday) {
        this.totalToday = totalToday;
    }
}
