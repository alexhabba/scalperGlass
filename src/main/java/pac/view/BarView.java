package pac.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import pac.SecondaryController;

public class BarView extends SecondaryController {

    private ObservableList<Node> sell;
    private ObservableList<Node> buy;


    public BarView(ObservableList<Node> buy, ObservableList<Node> sell) {
        this.buy = buy;
        this.sell = sell;
    }

    public ObservableList<Node> getBuy() {
        return buy;
    }

    public ObservableList<Node> getSell() {
        return sell;
    }
}
