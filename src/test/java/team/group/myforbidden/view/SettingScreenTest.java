package team.group.myforbidden.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.net.URL;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SettingScreenTest {

    @Test
    void testLoadSetsSceneRoot() throws Exception {
        // Arrange: mock Stage and Scene
        Stage mockStage = mock(Stage.class);
        Scene mockScene = mock(Scene.class);
        when(mockStage.getScene()).thenReturn(mockScene);

        // Prepare a concrete Parent instance
        Pane dummyRoot = new Pane();

        // Mock FXMLLoader.load to return dummyRoot
        try (MockedStatic<FXMLLoader> fxmlLoaderMock = mockStatic(FXMLLoader.class)) {
            fxmlLoaderMock.when(() -> FXMLLoader.load(any(URL.class))).thenReturn(dummyRoot);

            // Act
            SettingScreen.load(mockStage);
        }

        // Assert: verify setRoot was called with our dummyRoot
        verify(mockScene).setRoot(dummyRoot);
    }
}