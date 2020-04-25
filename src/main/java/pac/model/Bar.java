package pac.model;

import java.util.Map;
import java.util.TreeMap;

public class Bar {
    private Map<Double, Integer> sell;
    private Map<Double, Integer> buy;

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
}
