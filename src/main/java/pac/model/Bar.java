package pac.model;

import java.util.Map;
import java.util.TreeMap;

public class Bar {
    private Map<Double, Integer> sell;
    private Map<Double, Integer> buy;
    private double open;
    private double close;
    private double high;
    private double low;

    public Bar() {
        this.sell = new TreeMap<>();
        this.buy = new TreeMap<>();
    }

    public Map<Double, Integer> getBuy() {
        return buy;
    }

    public Map<Double, Integer> getSell() {
        return sell;
    }

    public void setBuy(Map<Double, Integer> buy) {
        this.buy = buy;
    }

    public void setSell(Map<Double, Integer> sell) {
        this.sell = sell;
    }

    public double getOpen() {
        return open;
    }

    public void setOpen(double open) {
        this.open = open;
    }

    public double getClose() {
        return close;
    }

    public void setClose(double close) {
        this.close = close;
    }

    public double getHigh() {
        return high;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public double getLow() {
        return low;
    }

    public void setLow(double low) {
        this.low = low;
    }
}
