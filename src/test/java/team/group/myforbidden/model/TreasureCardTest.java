package team.group.myforbidden.model;

import javafx.embed.swing.JFXPanel;
import javafx.scene.image.Image;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TreasureCardTest {

    @BeforeAll
    static void initJFXToolkit() {
        // Initialize JavaFX toolkit for Image loading
        new JFXPanel();
    }

    @Test
    void testConstructorSetsTypeAndImage() {
        for (TreasureCardType type : TreasureCardType.values()) {
            TreasureCard card = new TreasureCard(type);
            // Verify type is set correctly
            assertEquals(type, card.getTreasureCardType(),
                    "TreasureCardType should match constructor argument");
            // Verify image is loaded
            Image img = card.getImage();
            assertNotNull(img, "Image should be loaded for type: " + type);
            // Optionally, verify basic image properties
            assertTrue(img.getWidth() > 0, "Image width should be positive");
            assertTrue(img.getHeight() > 0, "Image height should be positive");
        }
    }
}