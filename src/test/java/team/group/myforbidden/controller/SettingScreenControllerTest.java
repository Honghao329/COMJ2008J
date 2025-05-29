package team.group.myforbidden.controller;

import javafx.embed.swing.JFXPanel;
import javafx.scene.control.Button;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import team.group.myforbidden.model.ForbiddenGame;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mockStatic;

class SettingScreenControllerTest {

    @BeforeAll
    static void initToolkit() {
        // Initialize JavaFX toolkit for control instantiation
        new JFXPanel();
    }

    private void injectField(Object target, String fieldName, Object value) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }

    @Test
    void testMouseEnteredBackSetsOpacity() throws Exception {
        SettingScreenController controller = new SettingScreenController();
        // Inject a Button into the controller
        Button backButton = new Button();
        injectField(controller, "back", backButton);

        // Call handler
        controller.mouseEnteredBack(null);
        // Verify opacity set to 0.5
        assertEquals(0.5, backButton.getOpacity(), 1e-6);
    }

    @Test
    void testMouseExitedBackSetsOpacity() throws Exception {
        SettingScreenController controller = new SettingScreenController();
        // Inject a Button into the controller
        Button backButton = new Button();
        injectField(controller, "back", backButton);

        // Change opacity first
        backButton.setOpacity(0.5);
        controller.mouseExitedBack(null);
        // Verify opacity reset to 1.0
        assertEquals(1.0, backButton.getOpacity(), 1e-6);
    }

    @Test
    void testMouseClickedBackCallsToMenu() throws Exception {
        SettingScreenController controller = new SettingScreenController();
        // Use static mock for ForbiddenGame
        try (MockedStatic<ForbiddenGame> mockedGame = mockStatic(ForbiddenGame.class)) {
            // Call handler
            controller.mouseClickedBack(null);
            // Verify static method called
            mockedGame.verify(ForbiddenGame::toMenu);
        }
    }
}
