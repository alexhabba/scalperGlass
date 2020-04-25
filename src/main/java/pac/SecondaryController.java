package pac;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import pac.model.Bar;
import pac.view.BarView;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class SecondaryController {
    @FXML
    public VBox boxOneBar;
    @FXML
    public VBox boxTwoBar;
    @FXML
    public VBox boxThreeBar;
    @FXML
    public VBox boxFourBar;
    @FXML
    public VBox boxFiveBar;
    @FXML
    private VBox boxPrice;
    @FXML
    private VBox boxGlass;
    @FXML
    private VBox boxBigBuy;
    @FXML
    private VBox boxBigSell;
    @FXML
    private VBox boxOneBuy;
    @FXML
    private VBox boxOneSell;
    @FXML
    private VBox boxTwoBuy;
    @FXML
    private VBox boxTwoSell;
    @FXML
    private VBox boxThreeBuy;
    @FXML
    private VBox boxThreeSell;
    @FXML
    private VBox boxFourBuy;
    @FXML
    private VBox boxFourSell;
    @FXML
    private VBox boxFiveBuy;
    @FXML
    private VBox boxFiveSell;
    @FXML
    private Label labelTime;

    private static double tempDouble;
    private List<String> listTick = new ArrayList<>();
    private List<String> listGlass = new ArrayList<>();
    private List<Bar> barList = new ArrayList<>();
    private List<BarView> barListView = new ArrayList<>();

    private static int countSameTimeMsk;
    private static String timeMsk = "";
    private static char timeMin;




    ObservableList<Node> listBoxPrice;
    ObservableList<Node> listBoxGlass;
    ObservableList<Node> listBoxBigBuy;
    ObservableList<Node> listBoxBigSell;
    ObservableList<Node> listBoxOneBuy;
    ObservableList<Node> listBoxOneSell;
    ObservableList<Node> listBoxTwoBuy;
    ObservableList<Node> listBoxTwoSell;
    ObservableList<Node> listBoxThreeBuy;
    ObservableList<Node> listBoxThreeSell;
    ObservableList<Node> listBoxFourBuy;
    ObservableList<Node> listBoxFourSell;
    ObservableList<Node> listBoxFiveBuy;
    ObservableList<Node> listBoxFiveSell;
    
    public void initialize() {

         listBoxPrice     = boxPrice.getChildren();
         listBoxGlass     = boxGlass.getChildren();
         listBoxBigBuy    = boxBigBuy.getChildren();
         listBoxBigSell   = boxBigSell.getChildren();
         listBoxOneBuy    = boxOneBuy.getChildren();
         listBoxOneSell   = boxOneSell.getChildren();
         listBoxTwoBuy    = boxTwoBuy.getChildren();
         listBoxTwoSell   = boxTwoSell.getChildren();
         listBoxThreeBuy  = boxThreeBuy.getChildren();
         listBoxThreeSell = boxThreeSell.getChildren();
         listBoxFourBuy   = boxFourBuy.getChildren();
         listBoxFourSell  = boxFourSell.getChildren();
         listBoxFiveBuy   = boxFiveBuy.getChildren();
         listBoxFiveSell  = boxFiveSell.getChildren();
        barListView.add(new BarView(listBoxFiveBuy, listBoxFiveSell));
        barListView.add(new BarView(listBoxFourBuy, listBoxFourSell));
        barListView.add(new BarView(listBoxThreeBuy, listBoxThreeSell));
        barListView.add(new BarView(listBoxTwoBuy, listBoxTwoSell));

        Timeline fiveSecondsWonder = new Timeline(new KeyFrame(Duration.millis(1), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                start();
                Date date = new Date();
                labelTime.setText(date.toString().split("\\s+")[3]);
            }
        }));
        fiveSecondsWonder.setCycleCount(Timeline.INDEFINITE);
        fiveSecondsWonder.play();

    }

    private void start() {
        Path pathTick = Paths.get("C:\\Program Files\\AMP MetaTrader 5\\MQL5\\Files\\Research\\tick.txt");
        Path pathGlass = Paths.get("C:\\Program Files\\AMP MetaTrader 5\\MQL5\\Files\\Research\\glass.txt");
        listTick  = readFile(listTick, pathTick);
        listGlass = readFile(listGlass, pathGlass);
        if (listGlass == null || listTick == null) return;
        printAll();

    }

    private void printAll () {
        String lastElement = listTick.get(listTick.size() - 1);
        if (lastElement.split("\\s+").length != 6) return;
            String tempCurrent = lastElement.split("\\s+")[5].substring(10);
        char tempTimeMin = lastElement.charAt(lastElement.length() - 19);

        if (tempTimeMin != timeMin) {
            getLastBar();
            clear();
            printBoxPrice(Double.parseDouble(listGlass.get(listGlass.size() / 2).split("\\s+")[0]) + 0.25 * 20, 0.25);
            timeMin = tempTimeMin;
            timeMsk = tempCurrent;
            countSameTimeMsk = getCountSameTimeMsk(timeMsk);
            printOneBar(listTick);
            printBoxGlass();
            printAllBars();
            return;
        }
        List<String> tempList = new ArrayList<>();
        for (int i = listTick.size() - 1; i >= 0; i--) {
            if (!timeMsk.equals(listTick.get(i).split("\\s+")[5].substring(10))) {
                tempList.add(listTick.get(i));
            }
            else if (countSameTimeMsk++ != getCountSameTimeMsk(timeMsk)) {
                tempList.add(listTick.get(i));
            } else break;
        }



        countSameTimeMsk = getCountSameTimeMsk(tempCurrent);
        timeMsk = lastElement.split("\\s+")[5].substring(10);
        printBoxGlass();
        printOneBar(tempList);
    }

    private void printAllBars() {
        for (int i = barList.size() - 1; i >= 0; i--) {
            Map<Double, Integer> mapBuy = barList.get(i).getBuy();
            ObservableList<Node> listOutBuy = barListView.get(i).getBuy();
            Map<Double, Integer> mapSell = barList.get(i).getSell();
            ObservableList<Node> listOutSell = barListView.get(i).getSell();

            printBarCenter(mapBuy, listOutBuy);
            printBarCenter(mapSell, listOutSell);
        }
    }

    private void getLastBar() {
        Bar lastBar = new Bar();
        lastBar.setBuy(getMapBar(listBoxOneBuy, "oneBuy"));
        lastBar.setSell(getMapBar(listBoxOneSell, "oneSell"));
        if (barList.size() == 4)
            barList.remove(0);
        barList.add(lastBar);
    }

    int getCountSameTimeMsk(String msk) {
        int count = 0;
        for (int i = listTick.size() - 1; i >= 0; i--) {
            String lastElementTime = listTick.get(i).split("\\s+")[5].substring(10);
            if (!lastElementTime.equals(msk))
                continue;
            else if (lastElementTime.equals(msk)) {
                count++;
                int z = i;
                if (--z >= 0)
                    if (!msk.equals(listTick.get(z).split("\\s+")[5].substring(10)))
                        break;
            }
        }
        return count;
    }

    private void printOneBar(List<String> list) {
        Map<Double, Integer> mapSell = new TreeMap<>();
        Map<Double, Integer> mapBuy = new TreeMap<>();
        list.forEach(x-> {
            String[] str = x.split("\\s+");
            double price = Double.parseDouble(str[1]);
            int volume = Integer.parseInt(str[0]);
            char type = str[2].charAt(0);
            if (type == 's') {
                if (mapSell.containsKey(price))
                    mapSell.put(price, mapSell.get(price) + volume);
                else
                    mapSell.put(price, volume);
            } else {
                if (mapBuy.containsKey(price))
                    mapBuy.put(price, mapBuy.get(price) + volume);
                else
                    mapBuy.put(price, volume);
            }
        });
//        oneBuy30
        listBoxPrice.stream().map(x-> (Label) x).forEach(x-> {
            double price = Double.parseDouble(x.getText());
            int id;
            if (mapBuy.containsKey(price)) {
                id = Integer.parseInt(x.getId().substring(5));
                Label label = (Label) listBoxOneBuy.get(id);
                int s = label.getText().equals("") ? 0 : Integer.parseInt(label.getText());
                s += mapBuy.get(price);
                label.setText(String.valueOf(s));
            }
            if (mapSell.containsKey(price)) {
                id = Integer.parseInt(x.getId().substring(5));
                Label label = (Label) listBoxOneSell.get(id);
                int s = label.getText().equals("") ? 0 : Integer.parseInt(label.getText());
                s += mapSell.get(price);
                label.setText(String.valueOf(s));
            }
        });

    }

    private List<String> readFile(List<String> list, Path path) {
        byte[] bytes;
        try {
            bytes = Files.readAllBytes(path);
            list = Arrays.asList(new String(bytes, StandardCharsets.UTF_16).split("\\n"));
        } catch (IOException e) {
//            System.out.println("failed read file");
            return null;
        }
        return list;
    }
    /**
     *   first parametr ....
     */
    private void printBarCenter(Map<Double, Integer> map, ObservableList<Node> listOut) {

        listBoxPrice.stream().map(x-> (Label) x).forEach(x-> {
            double price = Double.parseDouble(x.getText());
            int id;
            if (map.containsKey(price)) {

                id = Integer.parseInt(x.getId().replaceAll("price", ""));
                Label label = (Label) listOut.get(id);
                label.setText(String.valueOf(map.get(price)));
            }
        });
    }

    void clear() {
        listBoxPrice.stream().map(x-> (Label) x).forEach(x-> x.setText(""));
        listBoxGlass.stream().map(x-> (Label) x).forEach(x-> x.setText(""));
        listBoxBigBuy.stream().map(x-> (Label) x).forEach(x-> x.setText(""));
        listBoxBigSell.stream().map(x-> (Label) x).forEach(x-> x.setText(""));
        listBoxOneBuy.stream().map(x-> (Label) x).forEach(x-> x.setText(""));
        listBoxOneSell.stream().map(x-> (Label) x).forEach(x-> x.setText(""));
        listBoxTwoBuy.stream().map(x-> (Label) x).forEach(x-> x.setText(""));
        listBoxTwoSell.stream().map(x-> (Label) x).forEach(x-> x.setText(""));
        listBoxThreeBuy.stream().map(x-> (Label) x).forEach(x-> x.setText(""));
        listBoxThreeSell.stream().map(x-> (Label) x).forEach(x-> x.setText(""));
        listBoxFourBuy.stream().map(x-> (Label) x).forEach(x-> x.setText(""));
        listBoxFourSell.stream().map(x-> (Label) x).forEach(x-> x.setText(""));
        listBoxFiveBuy.stream().map(x-> (Label) x).forEach(x-> x.setText(""));
        listBoxFiveSell.stream().map(x-> (Label) x).forEach(x-> x.setText(""));
    }

    void printBoxPrice (double price, double step) {
        tempDouble = price;
        listBoxPrice.stream().map(x-> (Label) x).forEach(x-> {
            x.setText(String.valueOf(tempDouble));
            tempDouble -= step;
        });
    }
    /**
     *   This logic method: begin find price zero element from list file
     */
    void printBoxGlass () {


        Label labelGlass1 = (Label) listBoxGlass.get(0);
        Label labelGlass38 = (Label) listBoxGlass.get(39);
        if (!labelGlass1.getText().equals("") || !labelGlass38.getText().equals("")) {
            Map<Double, Integer> mapBuy = getMapBar(listBoxOneBuy, "oneBuy");
            Map<Double, Integer> mapSell = getMapBar(listBoxOneSell, "oneSell");
            clear();
            printBoxPrice(Double.parseDouble(listGlass.get(listGlass.size() / 2).split("\\s+")[0]) + 0.25 * 20, 0.25);
            printBarCenter(mapBuy, listBoxOneBuy);
            printBarCenter(mapSell, listBoxOneSell);
            printAllBars();
        }

        listBoxGlass.stream().map(x-> (Label) x).forEach(x-> {
            x.setText("");
            x.getStyleClass().removeAll("glassBuy", "glassSell");
        });
        String price = listGlass.get(0).split("\\s+")[0];
        StringBuilder id = new StringBuilder();
        listBoxPrice.stream().map(x-> (Label) x).forEach(x-> {
            if (Double.parseDouble(x.getText()) == Double.parseDouble(price))
                id.append(x.getId());
        });
        int tempId = 0;
        try {
            if (id.length() != 0)
            tempId = Integer.parseInt(id.toString().substring(5));
        } catch (Exception e) {
            System.out.printf("error  %s\n", id.toString());
            return;
        }
        int countListFile = 0;
        String volume;
        String priceGlass;
        for (int i = tempId; i < listBoxPrice.size(); i++) {
            if (listGlass.size() - 1 < countListFile) break;
            Label labelPrice = (Label) listBoxPrice.get(i);
            String[] str = listGlass.get(countListFile).split("\\s+");
            priceGlass  = str[0];
            volume = str[1];
            if (Double.parseDouble(labelPrice.getText()) == Double.parseDouble(priceGlass)) {
                Label labelGlass = (Label) listBoxGlass.get(i);
                labelGlass.setText(volume);
                if (str[2].equals("2")) {
                    labelGlass.getStyleClass().add("glassBuy");
                } else
                    labelGlass.getStyleClass().add("glassSell");
                countListFile++;
            }
        }
    }

    private Map<Double, Integer> getMapBar(ObservableList<Node> list, String str) {
        Map<Double, Integer> map = new TreeMap<>();
        list.stream().map(x-> (Label) x).forEach(x-> {
            if (!x.getText().equals("")) {
//                for (int i = 0; i < 10; i++) {
//                    System.out.println(x.getId().replaceAll(str, ""));
//                }
                int id = Integer.parseInt(x.getId().replaceAll(str, ""));
                Label label = (Label) listBoxPrice.get(id);
                double price = Double.parseDouble(label.getText());
                int volume = Integer.parseInt(x.getText());
                map.put(price, volume);
            }
        });
        return map;
    }

}

//            System.out.println(String.format("priceBox %f    priseGlass %f", Double.parseDouble(labelPrice.getText()),
//                    Double.parseDouble(priceGlass)));