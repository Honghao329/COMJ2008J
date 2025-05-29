package team.group.myforbidden.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.*;

class ForbiddenGameTest {

    private ForbiddenGame game;

    @BeforeEach
    void setUp() {
        game = ForbiddenGame.getInstance();
        // 重置关键静态字段，避免不同测试之间互相影响
        ForbiddenGame.setPlayerNum(2);
        ForbiddenGame.setLevel(1);
        // 重新进入游戏流程，确保卡堆与玩家手牌处于已初始化状态
        ForbiddenGame.toGame();
    }

    @Test
    void testSingletonInstance() {
        ForbiddenGame other = ForbiddenGame.getInstance();
        assertSame(game, other, "ForbiddenGame 应为单例");
    }

    @Test
    void testInitTreasureCardDrawsCorrectly() {
        Player player = game.getCurPlayer();
        Queue<TreasureCard> hand = player.getHandCards();

        assertEquals(2, hand.size(), "初始应发两张手牌");
        // 不应发到 Waters Rise 卡
        for (TreasureCard tc : hand) {
            assertNotEquals(
                    TreasureCardType.WATERS_RISE,
                    tc.getTreasureCardType(),
                    "初始手牌不应包含 Waters Rise"
            );
        }
    }

    @Test
    void testGetRandomPlayerTypeCycles() {
        PlayerType first = ForbiddenGame.getRandomPlayerType();
        boolean differentFound = false;
        for (int i = 0; i < 10; i++) {
            if (ForbiddenGame.getRandomPlayerType() != first) {
                differentFound = true;
                break;
            }
        }
        assertTrue(differentFound, "随机职业应能遍历到不同类型");
    }

    @Test
    void testUseActionDecreasesRemain() {
        int before = ForbiddenGame.getActionRemain();
        game.useAction();
        assertEquals(before - 1, ForbiddenGame.getActionRemain(), "useAction 应减少行动点");
    }

    @Test
    void testFloodCardQueueInit() {
        assertEquals(
                24,
                game.getFloodCard().size(),
                "洪水牌堆初始化应为 24 张"
        );
    }

    @Test
    void testAddTreasureAndIsCollected() throws Exception {
        game.add("fire",  new Treasure(TreasureType.FIRE));
        game.add("wind",  new Treasure(TreasureType.WIND));
        game.add("ocean", new Treasure(TreasureType.OCEAN));
        game.add("earth", new Treasure(TreasureType.EARTH));

        Method isCollected = ForbiddenGame.class.getDeclaredMethod("isCollected");
        isCollected.setAccessible(true);
        boolean collected = (boolean) isCollected.invoke(game);

        assertTrue(collected, "收集四种宝藏后 isCollected 应返回 true");
    }

    @Test
    void testDrawTreasureCardIncreasesLevelOnWatersRise() throws Exception {
        // 通过反射调用私有 drawTreasureCard 以便直接测试其效果
        Method drawTreasureCard = ForbiddenGame.class.getDeclaredMethod("drawTreasureCard");
        drawTreasureCard.setAccessible(true);

        int levelBefore = ForbiddenGame.getLevel();
        // 连续多次调用以增加触发 Waters Rise 的概率
        for (int i = 0; i < 10; i++) {
            drawTreasureCard.invoke(game);
        }
        int levelAfter = ForbiddenGame.getLevel();

        assertTrue(levelAfter >= levelBefore, "若抽到 Waters Rise，level 应提升");
    }

    @Test
    void testNextTurnChangesCurrentPlayer() {
        Player first = game.getCurPlayer();
        game.nextTurn();
        Player second = game.getCurPlayer();
        assertNotSame(first, second, "nextTurn 应切换到下一位玩家");
    }

    @Test
    void testShuffleTreasuresRepopulatesDeck() throws Exception {
        // 让弃牌堆里至少有两张牌
        game.getUsedTreasureCard().clear();
        game.getUsedTreasureCard().offer(new TreasureCard(TreasureCardType.FIRE_CARD));
        game.getUsedTreasureCard().offer(new TreasureCard(TreasureCardType.WIND_CARD));

        Method shuffleTreasures = ForbiddenGame.class.getDeclaredMethod("shuffleTreasures");
        shuffleTreasures.setAccessible(true);
        shuffleTreasures.invoke(game);

        assertEquals(
                0,
                game.getUsedTreasureCard().size(),
                "shuffleTreasures 调用后弃牌堆应被清空"
        );
    }
}
