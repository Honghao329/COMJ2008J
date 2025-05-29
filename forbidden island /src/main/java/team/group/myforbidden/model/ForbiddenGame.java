package team.group.myforbidden.model;


import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import team.group.myforbidden.controller.GameScreenController;
import team.group.myforbidden.view.GameScreen;
import team.group.myforbidden.view.MenuScreen;
import team.group.myforbidden.view.SettingScreen;

import java.util.*;

public class ForbiddenGame {//游戏主逻辑，一个单例模式
    public static final int SCREEN_WIDTH = 1024;
    public static final int SCREEN_HEIGHT = 1024;//窗口宽高
    private static final ForbiddenGame instance = new ForbiddenGame();//逻辑类的唯一实体
    private static Stage stage;//游戏主窗口
    public static Player[] players = new Player[4];//用一个数组记录玩家的实体
    private static final Random RANDOM = new Random();//没用
    private static int playerNum;//玩家数
    private static int level;//游戏难度，水位等级
    private static List<PlayerType> shuffledList = new ArrayList<>();//洗牌数组被用于在主牌堆没牌时对其排队洗牌并添加，用list实现collection的shuffle方法
    private static Iterator<PlayerType> iterator = Collections.emptyIterator();//加强for循环进行迭代遍历时，如果需要对遍历对象进行修改可以作为中间记录变量
    private static Queue<Integer> floodCard = new LinkedList<>();
    private static Queue<Integer> usedFloodCard = new LinkedList<>();
    private static Queue<TreasureCard> treasureCard = new LinkedList<>();
    private static Queue<TreasureCard> usedTreasureCard = new LinkedList<>();//队列，洪水宝藏的牌堆和弃牌堆，用队列核心逻辑时保障牌堆连续性
    private static Map<String, Treasure> treasures = new HashMap<>();//哈希表，用于记录已收集的宝藏，key是与宝藏类型命名相同的string，value对应treasure
    //类的实体
    private static int actionRemain;//记录玩家剩余行动点

    private int  curPlayerIndex = 0;//记录行动玩家下标

    private ForbiddenGame() {
    }//主逻辑的私有构造方法不能被外部访问

    public static ForbiddenGame getInstance() {
        return instance;
    }//获取当前逻辑类的唯一实体对象


    public void init(Stage stage) {//游戏窗口初始化，在main调用
        AnchorPane root = new AnchorPane();
        Scene scene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT);

        stage.setTitle("Forbidden Island");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setWidth(SCREEN_WIDTH);
        stage.setHeight(SCREEN_HEIGHT);
        ForbiddenGame.stage = stage;
        toMenu();
        stage.show();
    }

    public static void toMenu() {
        MenuScreen.load(stage);
    }//调用对应的load

    public static void toGame() {
        initGame();//先对游戏参数进行初始化
        GameScreen.load(stage);
    }

    public static void toLearn() {
        SettingScreen.load(stage);
    }

    private static void initGame() {
        actionRemain = 3;
        initTreasureCard();
        initPlayers();
        initFloodCard();
    }//初始化

    private static void initTreasureCard() {//创建所有需要的宝藏牌对象洗牌后加入到对应牌堆
        List<TreasureCard> orderTreasureCard = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            orderTreasureCard.add(new TreasureCard(TreasureCardType.HELICOPTER_LIFT));
            orderTreasureCard.add(new TreasureCard(TreasureCardType.WATERS_RISE));
        }
        for (int i = 0; i < 2; i++) {
            orderTreasureCard.add(new TreasureCard(TreasureCardType.SANDBAG));
        }
        for (int i = 0; i < 5; i++) {
            orderTreasureCard.add(new TreasureCard(TreasureCardType.FIRE_CARD));
            orderTreasureCard.add(new TreasureCard(TreasureCardType.WIND_CARD));
            orderTreasureCard.add(new TreasureCard(TreasureCardType.OCEAN_CARD));
            orderTreasureCard.add(new TreasureCard(TreasureCardType.EARTH_CARD));
        }
        Collections.shuffle(orderTreasureCard);
        treasureCard.addAll(orderTreasureCard);
    }

    private static void initFloodCard() {//同理洪水牌
        List<Integer> orderFloodCard = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            orderFloodCard.add(i);
        }
        Collections.shuffle(orderFloodCard);
        floodCard.addAll(orderFloodCard);
    }

    private static void initPlayers() {//对player初始化根据玩家数把玩家添加到player数组里
        for (int i = 0; i < playerNum; i++) {
            players[i] = new Player(getRandomPlayerType());
            TreasureCard card1 = treasureCard.poll();
            TreasureCard card2 = treasureCard.poll();
            while (true) {
                assert card1 != null;
                if (!(card1.getTreasureCardType() == TreasureCardType.WATERS_RISE)) break;
                usedTreasureCard.offer(card1);
                card1 = treasureCard.poll();
            }
            while (true) {
                assert card2 != null;
                if (!(card2.getTreasureCardType() == TreasureCardType.WATERS_RISE)) break;
                usedTreasureCard.offer(card2);
                card2 = treasureCard.poll();
            }
            players[i].getHandCards().add(card1);
            players[i].getHandCards().add(card2);
        }
    }

    public static PlayerType getRandomPlayerType() {//随机获取一种角色类型
        if (!iterator.hasNext()) {
            // 如果已经取完一轮，重新打乱列表
            shuffledList = new ArrayList<>(Arrays.asList(PlayerType.values()));
            Collections.shuffle(shuffledList);
            iterator = shuffledList.iterator();
        }
        return iterator.next();
    }

    public int getCurPlayerIndex() {
        return curPlayerIndex;
    }

    public Queue<Integer> getFloodCard() {
        return floodCard;
    }

    public Queue<Integer> getUsedFloodCard() {
        return usedFloodCard;
    }

    private boolean isCollected() {//用于判定是否集齐四个宝藏
        if (treasures.containsKey("fire") && treasures.containsKey("wind") &&
            treasures.containsKey("ocean") && treasures.containsKey("earth")) {
            return true;
        }
        return false;
    }

    public void add(String name, Treasure treasure) {
        treasures.put(name, treasure);
    }//用于往哈希表中添加宝藏

    public static void setPlayerNum(int playerNum) {
        ForbiddenGame.playerNum = playerNum;
    }

    public static void setLevel(int level) {
        ForbiddenGame.level = level;
    }//初始化玩家数和游戏难度

    private void shuffleTreasures() {//洗牌
        List<TreasureCard> treasureCards = new ArrayList<>();
        treasureCards.addAll(usedTreasureCard);
        Collections.shuffle(treasureCards);
        usedTreasureCard.clear();
        treasureCard.addAll(treasureCards);
    }

    private void drawTreasureCard() {//为当前玩家抽取宝藏牌
        Player player = players[curPlayerIndex];
        TreasureCard card1 = treasureCard.poll();
        if (card1 == null) {
            shuffleTreasures();
            card1 = treasureCard.poll();
        }
        TreasureCard card2 = treasureCard.poll();
        if (card2 == null) {
            shuffleTreasures();
            card2 = treasureCard.poll();
        }
        if(card1.getTreasureCardType() == TreasureCardType.WATERS_RISE && card2.getTreasureCardType() != TreasureCardType.WATERS_RISE) {
            level++;
            List<Integer> orderFloodCard = new ArrayList<>();
            orderFloodCard.addAll(floodCard);
            orderFloodCard.addAll(usedFloodCard);
            Collections.shuffle(orderFloodCard);
            floodCard = new LinkedList<>(orderFloodCard);
            usedTreasureCard.offer(card1);
            player.giveCard(card2);
        } else if (card1.getTreasureCardType() != TreasureCardType.WATERS_RISE && card2.getTreasureCardType() == TreasureCardType.WATERS_RISE) {
            level++;
            List<Integer> orderFloodCard = new ArrayList<>();
            orderFloodCard.addAll(floodCard);
            orderFloodCard.addAll(usedFloodCard);
            Collections.shuffle(orderFloodCard);
            floodCard = new LinkedList<>(orderFloodCard);
            usedTreasureCard.offer(card2);
            player.giveCard(card1);
        } else if (card1.getTreasureCardType() == TreasureCardType.WATERS_RISE && card2.getTreasureCardType() == TreasureCardType.WATERS_RISE) {
            level++;
            level++;
            List<Integer> orderFloodCard = new ArrayList<>();
            orderFloodCard.addAll(floodCard);
            orderFloodCard.addAll(usedFloodCard);
            Collections.shuffle(orderFloodCard);
            floodCard = new LinkedList<>(orderFloodCard);
            usedTreasureCard.offer(card1);
            usedTreasureCard.offer(card2);
        } else {
            player.giveCard(card1);
            player.giveCard(card2);
        }
    }

    public void nextTurn() {//加载下一个回合，每次进入下一个判定游戏成功还是失败
        if (checkLose()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Game Over");
            alert.setHeaderText(null);
            alert.setContentText("Game over! You have failed.\nReturning to the main menu...");
            alert.showAndWait();
            toMenu();
        }
        if (checkWin()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Game Over");
            alert.setHeaderText(null);
            alert.setContentText("Congratulations! You win the game!\nReturning to the main menu...");
            alert.showAndWait();
            toMenu();
        }
        actionRemain = 3;
        drawTreasureCard();
        curPlayerIndex = (curPlayerIndex + 1) % playerNum;
    }

    private boolean haveHelicopter() {//判定任意玩家是否有直升机卡
        for (Player player : players) {
            for (TreasureCard treasureCard : player.getHandCards()) {
                if (treasureCard.getTreasureCardType() == TreasureCardType.HELICOPTER_LIFT) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkLose() {//判定是否失败
        for (Player player : players) {
            if (player.getAlive() == 0) {
                return true;
            }
        }
        if (GameScreenController.checkSunk()) {
            return true;
        }
        if (level > 5) {
            return true;
        }
        return false;
    }

    private boolean checkWin() {
        return isCollected() && haveHelicopter();
    }//成功

    public static int getLevel() {
        return level;
    }

    public static int getActionRemain() {
        return actionRemain;
    }

    public void useAction() {
        actionRemain--;
    }//消耗一个行动点

    public Player getCurPlayer() {
        return players[curPlayerIndex];
    }

    public Queue<TreasureCard> getUsedTreasureCard() {
        return usedTreasureCard;
    }

}
