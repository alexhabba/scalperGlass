package pac.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import pac.SecondaryController;

public class BarView extends SecondaryController {

    private ObservableList<Node> sell;
    private ObservableList<Node> buy;
    private ObservableList<Node> bar;


    public BarView(ObservableList<Node> buy, ObservableList<Node> sell, ObservableList<Node> bar) {
        this.buy = buy;
        this.sell = sell;
        this.bar = bar;
    }

    public ObservableList<Node> getBuy() {
        return buy;
    }

    public ObservableList<Node> getSell() {
        return sell;
    }

    public ObservableList<Node> getBar() {
        return bar;
    }
}
