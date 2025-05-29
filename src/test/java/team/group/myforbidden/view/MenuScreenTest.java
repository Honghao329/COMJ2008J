package team.group.myforbidden.view;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MenuScreenTest {

    @BeforeAll
    public static void setupJavaFX() {
        // Initialize JavaFX environment
        new JFXPanel();
    }

    @Test
    public void testFXMLFileExists() {
        assertNotNull(
                MenuScreen.class.getResource("/team/group/myforbidden/fxml/menuscreen.fxml"),
                "FXML file 'menuscreen.fxml' should exist in resources"
        );
    }

    @Test
    public void testLoadDoesNotThrow() throws InterruptedException {
        Platform.runLater(() -> {
            try {
                Stage mockStage = new Stage();
                mockStage.setScene(new Scene(new Pane()));
                MenuScreen.load(mockStage);
            } catch (Exception e) {
                fail("Exception thrown during MenuScreen.load(): " + e.getMessage());
            }
        });

        Thread.sleep(1000); // Wait for JavaFX thread
    }
}
