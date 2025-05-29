package team.group.myforbidden.view;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameScreenTest {

    @BeforeAll
    public static void setupJavaFX() {
        // Initialize JavaFX toolkit to avoid Platform errors
        new JFXPanel(); // Triggers JavaFX initialization
    }

    @Test
    public void testFXMLFileExists() {
        assertNotNull(
                GameScreen.class.getResource("/team/group/myforbidden/fxml/gamescreen.fxml"),
                "FXML file 'gamescreen.fxml' should exist in resources"
        );
    }

    @Test
    public void testLoadDoesNotThrow() throws InterruptedException {
        Platform.runLater(() -> {
            try {
                Stage mockStage = new Stage();
                mockStage.setScene(new Scene(new Pane())); // Dummy scene for testing
                GameScreen.load(mockStage);
            } catch (Exception e) {
                fail("Exception thrown during GameScreen.load(): " + e.getMessage());
            }
        });

        // Wait a bit for JavaFX thread to execute
        Thread.sleep(1000);
    }
}
