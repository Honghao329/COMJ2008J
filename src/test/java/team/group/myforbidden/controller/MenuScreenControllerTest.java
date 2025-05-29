package team.group.myforbidden.controller;

import javafx.embed.swing.JFXPanel;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.input.MouseEvent;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import org.mockito.MockedStatic;
import team.group.myforbidden.model.ForbiddenGame;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.github.stefanbirkner.systemlambda.SystemLambda.catchSystemExit;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MenuScreenControllerTest {

    @BeforeAll
    static void initJFX() {
        // Initialize JavaFX toolkit
        new JFXPanel();
    }

    private Button injectButton(MenuScreenController controller, String name) throws Exception {
        Button btn = new Button();
        Field field = MenuScreenController.class.getDeclaredField(name);
        field.setAccessible(true);
        field.set(controller, btn);
        return btn;
    }

    @Test
    void testMouseEnteredAndExitedOpacity() throws Exception {
        MenuScreenController controller = new MenuScreenController();
        // Test exit button
        Button exitBtn = injectButton(controller, "exit");
        controller.mouseEnteredExit(null);
        assertEquals(0.5, exitBtn.getOpacity(), 1e-6);
        controller.mouseExitedExit(null);
        assertEquals(1.0, exitBtn.getOpacity(), 1e-6);

        // Test learn button
        Button learnBtn = injectButton(controller, "learn");
        controller.mouseEnteredLearn(null);
        assertEquals(0.5, learnBtn.getOpacity(), 1e-6);
        controller.mouseExitedLearn(null);
        assertEquals(1.0, learnBtn.getOpacity(), 1e-6);

        // Test start button
        Button startBtn = injectButton(controller, "start");
        controller.mouseEnteredStart(null);
        assertEquals(0.5, startBtn.getOpacity(), 1e-6);
        controller.mouseExitedStart(null);
        assertEquals(1.0, startBtn.getOpacity(), 1e-6);
    }

    @Test
    void testMouseClickedLearnToPlayCallsToLearn() {
        MenuScreenController controller = new MenuScreenController();
        try (MockedStatic<ForbiddenGame> mocked = mockStatic(ForbiddenGame.class)) {
            controller.mouseClickedLearnToPlay(null);
            mocked.verify(ForbiddenGame::toLearn);
        }
    }

    @Test
    void testMouseClickedNewGame_CancelFirstDialog() {
        MenuScreenController controller = new MenuScreenController();
        // Mock both ChoiceDialog constructions to return empty
        try (MockedConstruction<ChoiceDialog> mockDialog = mockConstruction(ChoiceDialog.class,
                (mock, context) -> when(mock.showAndWait()).thenReturn(Optional.empty()));
             MockedStatic<ForbiddenGame> mockedGame = mockStatic(ForbiddenGame.class)) {
            controller.mouseClickedNewGame(null);
            // No calls should be made to ForbiddenGame
            mockedGame.verifyNoInteractions();
        }
    }

    @Test
    void testMouseClickedNewGame_UserProceeds() {
        MenuScreenController controller = new MenuScreenController();
        // Prepare player and difficulty options
        List<Integer> playerOptions = Arrays.asList(2, 3, 4);
        List<Integer> difficultyOptions = Arrays.asList(2, 3, 4, 5);

        try (MockedConstruction<ChoiceDialog> mockDialog = mockConstruction(ChoiceDialog.class,
                (mock, context) -> {
                    @SuppressWarnings("unchecked")
                    List<Integer> choices = (List<Integer>) context.arguments().get(1);
                    if (choices.size() == playerOptions.size()) {
                        when(mock.showAndWait()).thenReturn(Optional.of(3));
                    } else {
                        when(mock.showAndWait()).thenReturn(Optional.of(4));
                    }
                });
             MockedStatic<ForbiddenGame> mockedGame = mockStatic(ForbiddenGame.class)) {
            controller.mouseClickedNewGame(null);
            mockedGame.verify(() -> ForbiddenGame.setPlayerNum(3));
            mockedGame.verify(() -> ForbiddenGame.setLevel(4));
            mockedGame.verify(ForbiddenGame::toGame);
        }
    }
}
