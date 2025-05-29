package team.group.myforbidden.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class SettingScreen {
    public static void load (Stage stage) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(SettingScreen.class.getResource("/team/group/myforbidden/fxml/settingscreen.fxml")));
            stage.getScene().setRoot(root);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
