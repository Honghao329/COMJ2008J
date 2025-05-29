package team.group.myforbidden.model;

import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class ForbiddenGameTest {

    @BeforeEach
    @SuppressWarnings("unchecked")
    void setUp() throws Exception {
        // Reset static fields of ForbiddenGame
        // Reset public static players array
        ForbiddenGame.players = new Player[4];
        // Reset playerNum and level
        ForbiddenGame.setPlayerNum(0);
        ForbiddenGame.setLevel(0);

        Class<ForbiddenGame> clazz = ForbiddenGame.class;
        // Reset flood and used flood queues
        Field floodField = clazz.getDeclaredField("floodCard");
        floodField.setAccessible(true);
        floodField.set(null, new LinkedList<Integer>());

        Field usedFloodField = clazz.getDeclaredField("usedFloodCard");
        usedFloodField.setAccessible(true);
        usedFloodField.set(null, new LinkedList<Integer>());

        // Reset treasure and used treasure queues
        Field treasureField = clazz.getDeclaredField("treasureCard");
        treasureField.setAccessible(true);
        treasureField.set(null, new LinkedList<TreasureCard>());

        Field usedTreasureField = clazz.getDeclaredField("usedTreasureCard");
        usedTreasureField.setAccessible(true);
        usedTreasureField.set(null, new LinkedList<TreasureCard>());

        // Reset shuffledList and iterator
        Field shuffledListField = clazz.getDeclaredField("shuffledList");
        shuffledListField.setAccessible(true);
        shuffledListField.set(null, new ArrayList<PlayerType>());

        Field iteratorField = clazz.getDeclaredField("iterator");
        iteratorField.setAccessible(true);
        iteratorField.set(null, Collections.emptyIterator());

        // Reset treasures map
        Field treasuresField = clazz.getDeclaredField("treasures");
        treasuresField.setAccessible(true);
        treasuresField.set(null, new HashMap<String, Treasure>());

        // Reset current player index
        Field curIndexField = clazz.getDeclaredField("curPlayerIndex");
        curIndexField.setAccessible(true);
        curIndexField.set(ForbiddenGame.getInstance(), 0);

        // Reset actionRemain
        Field actionField = clazz.getDeclaredField("actionRemain");
        actionField.setAccessible(true);
        actionField.setInt(null, 0);

        // Reset JavaFX stage
        Field stageField = clazz.getDeclaredField("stage");
        stageField.setAccessible(true);
        stageField.set(null, (Stage) null);
    }

    @Test
    void testSingleton() {
        ForbiddenGame first = ForbiddenGame.getInstance();
        ForbiddenGame second = ForbiddenGame.getInstance();
        assertSame(first, second, "getInstance should return the same instance");
    }

    @Test
    void testConstants() {
        assertEquals(1024, ForbiddenGame.SCREEN_WIDTH, "SCREEN_WIDTH should be 1024");
        assertEquals(1024, ForbiddenGame.SCREEN_HEIGHT, "SCREEN_HEIGHT should be 1024");
    }

    @Test
    void testInitialState() {
        ForbiddenGame game = ForbiddenGame.getInstance();
        // Before initialization
        assertEquals(0, ForbiddenGame.getActionRemain(), "Initial actionRemain should be 0");
        assertTrue(game.getFloodCard().isEmpty(), "floodCard should start empty");
        assertTrue(game.getUsedFloodCard().isEmpty(), "usedFloodCard should start empty");
        assertEquals(0, game.getCurPlayerIndex(), "Initial curPlayerIndex should be 0");
    }

    @Test
    void testInitFloodCard() throws Exception {
        Method initFlood = ForbiddenGame.class.getDeclaredMethod("initFloodCard");
        initFlood.setAccessible(true);
        initFlood.invoke(null);

        Queue<Integer> flood = ForbiddenGame.getInstance().getFloodCard();
        assertEquals(24, flood.size(), "Flood deck should contain 24 cards");
        // Check it contains all numbers 0..23
        List<Integer> expected = IntStream.range(0, 24).boxed().toList();
        assertTrue(flood.containsAll(expected), "Flood deck should contain all card indices 0-23");
    }

    @Test
    void testInitTreasureCardComposition() throws Exception {
        Method initTreasure = ForbiddenGame.class.getDeclaredMethod("initTreasureCard");
        initTreasure.setAccessible(true);
        initTreasure.invoke(null);

        // Access private static treasureCard queue
        Field treasureField = ForbiddenGame.class.getDeclaredField("treasureCard");
        treasureField.setAccessible(true);
        @SuppressWarnings("unchecked")
        Queue<TreasureCard> deck = (Queue<TreasureCard>) treasureField.get(null);

        assertEquals(28, deck.size(), "Treasure deck should contain 28 cards");

        // Count each type
        Map<TreasureCardType, Long> counts = deck.stream()
                .collect(Collectors.groupingBy(TreasureCard::getTreasureCardType, Collectors.counting()));

        assertEquals(3L, counts.getOrDefault(TreasureCardType.HELICOPTER_LIFT, 0L));
        assertEquals(3L, counts.getOrDefault(TreasureCardType.WATERS_RISE, 0L));
        assertEquals(2L, counts.getOrDefault(TreasureCardType.SANDBAG, 0L));
        assertEquals(5L, counts.getOrDefault(TreasureCardType.FIRE_CARD, 0L));
        assertEquals(5L, counts.getOrDefault(TreasureCardType.WIND_CARD, 0L));
        assertEquals(5L, counts.getOrDefault(TreasureCardType.OCEAN_CARD, 0L));
        assertEquals(5L, counts.getOrDefault(TreasureCardType.EARTH_CARD, 0L));
    }

    @Test
    void testInitPlayersDealsTwoCards() throws Exception {
        // Set player number and initialize game
        ForbiddenGame.setPlayerNum(2);
        Method initGame = ForbiddenGame.class.getDeclaredMethod("initGame");
        initGame.setAccessible(true);
        initGame.invoke(null);

        // Check that two players were created with 2 cards each
        assertNotNull(ForbiddenGame.players[0], "Player 0 should be initialized");
        assertNotNull(ForbiddenGame.players[1], "Player 1 should be initialized");
        assertEquals(2, ForbiddenGame.players[0].getHandCards().size(), "Player 0 should have 2 cards");
        assertEquals(2, ForbiddenGame.players[1].getHandCards().size(), "Player 1 should have 2 cards");
        // Other array slots should remain null
        assertNull(ForbiddenGame.players[2], "Player slot 2 should be null");
        assertNull(ForbiddenGame.players[3], "Player slot 3 should be null");

        // Check actionRemain was reset
        assertEquals(3, ForbiddenGame.getActionRemain(), "actionRemain should be reset to 3");
    }

    @Test
    void testUseActionDecrements() throws Exception {
        ForbiddenGame.setPlayerNum(1);
        Method initGame = ForbiddenGame.class.getDeclaredMethod("initGame");
        initGame.setAccessible(true);
        initGame.invoke(null);

        ForbiddenGame game = ForbiddenGame.getInstance();
        assertEquals(3, ForbiddenGame.getActionRemain(), "actionRemain should start at 3 after init");
        game.useAction();
        assertEquals(2, ForbiddenGame.getActionRemain(), "useAction should decrement actionRemain by 1");
    }

    @Test
    void testAddAndIsCollected() throws Exception {
        ForbiddenGame game = ForbiddenGame.getInstance();
        Method isCollected = ForbiddenGame.class.getDeclaredMethod("isCollected");
        isCollected.setAccessible(true);

        // Initially false
        assertFalse((boolean) isCollected.invoke(game), "isCollected should be false without treasures");
        // Add all required keys
        game.add("fire", null);
        game.add("wind", null);
        game.add("ocean", null);
        game.add("earth", null);
        assertTrue((boolean) isCollected.invoke(game), "isCollected should be true when all treasures are present");
    }

    @Test
    void testSetAndGetLevel() {
        ForbiddenGame.setLevel(5);
        assertEquals(5, ForbiddenGame.getLevel(), "getLevel should return the value set by setLevel");
    }

    @Test
    void testGetRandomPlayerType() {
        // Should return a valid enum value
        PlayerType random = ForbiddenGame.getRandomPlayerType();
        assertNotNull(random, "getRandomPlayerType should not return null");
        assertTrue(Arrays.asList(PlayerType.values()).contains(random), "Returned value should be one of PlayerType");
    }
}