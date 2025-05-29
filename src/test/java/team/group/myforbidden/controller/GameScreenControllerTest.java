package team.group.myforbidden.controller;

import javafx.embed.swing.JFXPanel;
import javafx.scene.control.Label;
import javafx.scene.effect.Effect;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import team.group.myforbidden.model.ForbiddenGame;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GameScreenControllerTest {

    @BeforeAll
    static void initToolkit() {
        // Initialize JavaFX toolkit
        new JFXPanel();
    }

    @Test
    void testCheckSunkReturnsTrueWhenThresholdReached() throws Exception {
        // Setup two ImageViews representing fire sunk
        ImageView iv1 = new ImageView();
        ImageView iv2 = new ImageView();
        iv1.setOpacity(0);
        iv2.setOpacity(0);

        // Inject into static places array and imagePathMap
        ImageView[] places = {iv1, iv2};
        Field placesField = GameScreenController.class.getDeclaredField("places");
        placesField.setAccessible(true);
        placesField.set(null, places);

        Map<ImageView,String> map = new HashMap<>();
        map.put(iv1, "/team/group/myforbidden/image/island/island_3.png");
        map.put(iv2, "/team/group/myforbidden/image/island/island_4.png");
        Field mapField = GameScreenController.class.getDeclaredField("imagePathMap");
        mapField.setAccessible(true);
        mapField.set(null, map);

        // Should detect fireSunk == 2 => true
        assertTrue(GameScreenController.checkSunk());
    }

    @Test
    void testCheckSunkReturnsFalseWhenNotThreshold() throws Exception {
        ImageView iv = new ImageView();
        iv.setOpacity(1);
        ImageView[] places = {iv};
        Field placesField = GameScreenController.class.getDeclaredField("places");
        placesField.setAccessible(true);
        placesField.set(null, places);

        Map<ImageView,String> map = new HashMap<>();
        map.put(iv, "/team/group/myforbidden/image/island/island_3.png");
        Field mapField = GameScreenController.class.getDeclaredField("imagePathMap");
        mapField.setAccessible(true);
        mapField.set(null, map);

        assertFalse(GameScreenController.checkSunk());
    }

    @Test
    void testFloodAndSunkAndShoreUp() throws Exception {
        GameScreenController controller = new GameScreenController();
        ImageView iv = new ImageView();
        iv.setFitWidth(100);
        iv.setFitHeight(50);

        // Test flood sets effect and flooded property
        Method floodM = controller.getClass().getDeclaredMethod("flood", ImageView.class);
        floodM.setAccessible(true);
        floodM.invoke(controller, iv);
        Object flooded = iv.getProperties().get("flooded");
        assertTrue(Boolean.TRUE.equals(flooded), "flooded property should be true");
        Effect eff = iv.getEffect();
        assertNotNull(eff, "Effect should be applied on flood");

        // Second flood should sink (opacity 0)
        Method floodM1 = controller.getClass().getDeclaredMethod("flood", ImageView.class);
        floodM1.setAccessible(true);
        floodM1.invoke(controller, iv);
        assertEquals(0.0, iv.getOpacity(), 1e-6, "Opacity should be 0 after sinking");

        // Shore up should reset visuals
        Method shoreM = controller.getClass().getDeclaredMethod("shoreUp", ImageView.class);
        shoreM.setAccessible(true);
        shoreM.invoke(controller, iv);
        assertEquals(1.0, iv.getOpacity(), 1e-6, "Opacity should reset to 1 on shoreUp");
        assertNull(iv.getEffect(), "Effect should be cleared on shoreUp");
        assertFalse(Boolean.TRUE.equals(iv.getProperties().get("flooded")), "flooded property should be false after shoreUp");
    }

    @Test
    void testSetActionAndLevelText() throws Exception {
        GameScreenController controller = new GameScreenController();
        // Inject labels
        Label actionLabel = new Label();
        Label levelLabel = new Label();
        Field actionField = GameScreenController.class.getDeclaredField("action");
        actionField.setAccessible(true);
        actionField.set(controller, actionLabel);
        Field levelField = GameScreenController.class.getDeclaredField("level");
        levelField.setAccessible(true);
        levelField.set(controller, levelLabel);

        // Invoke setActionText
        Method setActionM = controller.getClass().getDeclaredMethod("setActionText", String.class);
        setActionM.setAccessible(true);
        setActionM.invoke(controller, "5");
        assertEquals("5", actionLabel.getText());

        // Invoke setLevelText
        Method setLevelM = controller.getClass().getDeclaredMethod("setLevelText", String.class);
        setLevelM.setAccessible(true);
        setLevelM.invoke(controller, "2");
        assertEquals("2", levelLabel.getText());
    }
}
