package pac;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PrimaryController {

    @FXML
    private void openGlass() throws IOException {
        FXMLLoader glass = new FXMLLoader();
        glass.setLocation(getClass().getResource("glass.fxml"));
        glass.load();
        Parent root = glass.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("PRINT");
        stage.show();
    }
    @FXML
    public void openPrint() throws IOException {
        FXMLLoader glass = new FXMLLoader();
        glass.setLocation(getClass().getResource("print.fxml"));
        glass.load();
        Parent root = glass.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("PRINT");
        stage.show();
    }
}
