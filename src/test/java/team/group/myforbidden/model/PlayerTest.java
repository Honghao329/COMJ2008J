package team.group.myforbidden.model;

import javafx.embed.swing.JFXPanel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @BeforeAll
    static void initJFXToolkit() {
        // Initialize JavaFX toolkit for Image loading
        new JFXPanel();
    }

    @Test
    void testConstructorInitializesTypeAndHand() {
        for (PlayerType type : PlayerType.values()) {
            Player player = new Player(type);
            // Type should match
            assertEquals(type, player.getType(), "Player type should be set by constructor");
            // HandCards should start empty
            assertNotNull(player.getHandCards(), "Hand cards queue should not be null");
            assertTrue(player.getHandCards().isEmpty(), "Hand cards should be empty initially");
            // Alive should default to 1
            assertEquals(1, player.getAlive(), "Player should be alive (1) by default");
            // Place should be non-negative
            assertTrue(player.getPlace() >= 0, "Player place should be initialized to a non-negative position");
            // Images should be loaded
            assertNotNull(player.getCardImage(), "Card image should be initialized");
            assertNotNull(player.getPawnImage(), "Pawn image should be initialized");
        }
    }

    @Test
    void testGiveCardUnderLimitAddsCard() {
        Player player = new Player(PlayerType.EXPLORER);
        // Ensure hand is empty
        player.getHandCards().clear();
        int initialSize = player.getHandCards().size();

        TreasureCard card = new TreasureCard(TreasureCardType.FIRE_CARD);
        player.giveCard(card);

        assertEquals(initialSize + 1, player.getHandCards().size(), "giveCard should add a card when under limit");
        assertTrue(player.getHandCards().contains(card), "Hand cards should contain the newly given card");
    }

    @Test
    void testSetAndGetAlive() {
        Player player = new Player(PlayerType.PILOT);
        player.setAlive(0);
        assertEquals(0, player.getAlive(), "getAlive should return the value set by setAlive");

        player.setAlive(1);
        assertEquals(1, player.getAlive(), "getAlive should reflect changes made by setAlive");
    }

    @Test
    void testSetAndGetPlace() {
        Player player = new Player(PlayerType.MESSENGER);
        player.setPlace(42);
        assertEquals(42, player.getPlace(), "getPlace should return the value set by setPlace");
    }

    @Test
    void testSetHandCardsReplacesQueue() {
        Player player = new Player(PlayerType.NAVIGATOR);
        Queue<TreasureCard> newQueue = new LinkedList<>();
        newQueue.add(new TreasureCard(TreasureCardType.OCEAN_CARD));
        player.setHandCards(newQueue);
        assertSame(newQueue, player.getHandCards(), "setHandCards should replace the internal queue");
    }
}
