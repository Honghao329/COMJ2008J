package team.group.myforbidden.view;

import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import team.group.myforbidden.model.ForbiddenGame;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.mockStatic;

class MainTest {

    @Test
    void testStartCallsForbiddenGameInit() throws Exception {
        // Mock a JavaFX Stage to avoid toolkit initialization
        Stage mockStage = mock(Stage.class);
        // Mock the singleton instance and static getInstance()
        try (MockedStatic<ForbiddenGame> mockedStatic = mockStatic(ForbiddenGame.class)) {
            ForbiddenGame dummyGame = mock(ForbiddenGame.class);
            mockedStatic.when(ForbiddenGame::getInstance).thenReturn(dummyGame);

            // Call start, should delegate to ForbiddenGame.init without touching JavaFX
            new Main().start(mockStage);

            // Verify init(stage) was invoked
            verify(dummyGame).init(mockStage);
        }
    }
}