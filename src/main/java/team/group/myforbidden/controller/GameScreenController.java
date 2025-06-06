package team.group.myforbidden.controller;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import team.group.myforbidden.model.*;

import java.net.URL;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class GameScreenController {

    @FXML private AnchorPane anchorPane;
    @FXML private ImageView place0;
    @FXML private ImageView place1;
    @FXML private ImageView place2;
    @FXML private ImageView place3;
    @FXML private ImageView place4;
    @FXML private ImageView place5;
    @FXML private ImageView place6;
    @FXML private ImageView place7;
    @FXML private ImageView place8;
    @FXML private ImageView place9;
    @FXML private ImageView place10;
    @FXML private ImageView place11;
    @FXML private ImageView place12;
    @FXML private ImageView place13;
    @FXML private ImageView place14;
    @FXML private ImageView place15;
    @FXML private ImageView place16;
    @FXML private ImageView place17;
    @FXML private ImageView place18;
    @FXML private ImageView place19;
    @FXML private ImageView place20;
    @FXML private ImageView place21;
    @FXML private ImageView place22;
    @FXML private ImageView place23;
    @FXML private ImageView cornerBottomLeft;
    @FXML private ImageView cornerBottomRight;
    @FXML private ImageView cornerTopLeft;
    @FXML private ImageView cornerTopRight;
    @FXML private ImageView pawnFour;
    @FXML private ImageView pawnOne;
    @FXML private ImageView pawnThree;
    @FXML private ImageView pawnTwo;
    @FXML private Button give;
    @FXML private Button move;
    @FXML private Button capture;
    @FXML private Button shoreUp;
    @FXML private ImageView card11;
    @FXML private ImageView card12;
    @FXML private ImageView card13;
    @FXML private ImageView card14;
    @FXML private ImageView card15;
    @FXML private ImageView card21;
    @FXML private ImageView card22;
    @FXML private ImageView card23;
    @FXML private ImageView card24;
    @FXML private ImageView card25;
    @FXML private ImageView card31;
    @FXML private ImageView card32;
    @FXML private ImageView card33;
    @FXML private ImageView card34;
    @FXML private ImageView card35;
    @FXML private ImageView card41;
    @FXML private ImageView card42;
    @FXML private ImageView card43;
    @FXML private ImageView card44;
    @FXML private ImageView card45;
    @FXML private Label action;
    @FXML private Label level;
    @FXML private Button nextTurn;
    @FXML private Button useSkill;

    private static ImageView[] places;
    private ImageView[] playerPawns;
    private int shoreUpState = 0;
    public static int[] placeOfPlayer = new int[6];
    private Queue<TreasureCard> playerOneTreasureCard;
    private Queue<TreasureCard> playerTwoTreasureCard;
    private Queue<TreasureCard> playerThreeTreasureCard;
    private Queue<TreasureCard> playerFourTreasureCard;
    private ImageView[] playerOneHandImages;
    private ImageView[] playerTwoHandImages;
    private ImageView[] playerThreeHandImages;
    private ImageView[] playerFourHandImages;
    private int givePlayerIndex = -1;
    private int useEngineerSkill = 0;
    private int usePilotSkill = 1;
    private int playerToMoveIndex = 0;
    static Map<ImageView, String> imagePathMap = new HashMap<>();

    public static boolean checkSunk() {
        int fireSunk = 0;
        int windSunk = 0;
        int earthSunk = 0;
        int oceanSunk = 0;
        int helicopterSunk = 0;
        for (ImageView islandTile : places) {
            switch (imagePathMap.get(islandTile)) {
                case "/team/group/myforbidden/image/island/island_3.png",
                     "/team/group/myforbidden/image/island/island_4.png" -> {
                    if (islandTile.getOpacity() == 0) {
                        fireSunk++;
                    }
                }
                case "/team/group/myforbidden/image/island/island_12.png",
                     "/team/group/myforbidden/image/island/island_24.png" -> {
                    if (islandTile.getOpacity() == 0) {
                        windSunk++;
                    }
                }
                case "/team/group/myforbidden/image/island/island_19.png",
                     "/team/group/myforbidden/image/island/island_20.png" -> {
                    if (islandTile.getOpacity() == 0) {
                        earthSunk++;
                    }
                }
                case "/team/group/myforbidden/image/island/island_7.png",
                     "/team/group/myforbidden/image/island/island_21.png" -> {
                    if (islandTile.getOpacity() == 0) {
                        oceanSunk++;
                    }
                }
                case "/team/group/myforbidden/image/island/island_10.png" -> {
                    if (islandTile.getOpacity() == 0) {
                        helicopterSunk++;
                    }
                }
            }
        }
        if (fireSunk == 2 || windSunk == 2 || earthSunk == 2 || oceanSunk == 2 || helicopterSunk == 1) {
            return true;
        }
        return false;
    }

    @FXML
    private void initialize() {
        places = new ImageView[]{
                place0, place1, place2, place3, place4, place5,
                place6, place7, place8, place9, place10, place11,
                place12, place13, place14, place15, place16, place17,
                place18, place19, place20, place21, place22, place23
        };

        playerOneHandImages = new ImageView[]{
                card11, card12, card13, card14, card15
        };

        playerTwoHandImages = new ImageView[]{
                card21, card22, card23, card24, card25
        };

        playerThreeHandImages = new ImageView[]{
                card31, card32, card33, card34, card35
        };

        playerFourHandImages = new ImageView[]{
                card41, card42, card43, card44, card45
        };

        List<String> imagePaths = new ArrayList<>();
        for (int i = 1; i < 25; i++) {
            imagePaths.add("/team/group/myforbidden/image/island/island_" + i + ".png");
        }

        Collections.shuffle(imagePaths);

        for (int i = 0; i < places.length; i++) {
            if (places[i] != null) {
                String path = imagePaths.get(i);
                switch (path) {
                    case "/team/group/myforbidden/image/island/island_13.png" -> placeOfPlayer[0] = i;
                    case "/team/group/myforbidden/image/island/island_2.png" -> placeOfPlayer[1] = i;
                    case "/team/group/myforbidden/image/island/island_6.png" -> placeOfPlayer[2] = i;
                    case "/team/group/myforbidden/image/island/island_18.png" -> placeOfPlayer[3] = i;
                    case "/team/group/myforbidden/image/island/island_11.png" -> placeOfPlayer[4] = i;
                    case "/team/group/myforbidden/image/island/island_10.png" -> placeOfPlayer[5] = i;
                }

                URL url = getClass().getResource(path);
                if (url == null) {
                    System.err.println("Image resource not found: " + path);
                    continue;
                }

                Image image = new Image(url.toExternalForm()); // 支持 getUrl()
                places[i].setImage(image);
                imagePathMap.put(places[i], path);

            } else {
                System.err.println("Warning: place" + i + " is null");
            }
        }

        for (Player player : ForbiddenGame.players) {
            if (player == null) {
                break;
            }
            switch (player.getType()) {
                case DIVER -> player.setPlace(placeOfPlayer[0]);
                case ENGINEER -> player.setPlace(placeOfPlayer[1]);
                case EXPLORER -> player.setPlace(placeOfPlayer[2]);
                case MESSENGER -> player.setPlace(placeOfPlayer[3]);
                case NAVIGATOR -> player.setPlace(placeOfPlayer[4]);
                case PILOT -> player.setPlace(placeOfPlayer[5]);
            }
        }
        setPlayerHands();
        setActionText(String.valueOf(ForbiddenGame.getActionRemain()));
        setLevelText(String.valueOf(ForbiddenGame.getLevel()));
        drawPlayers();
        drawPlayerHands();
        drawFlood();
//        for (ImageView islandTile : places) {
//            System.out.println(imagePathMap.get(islandTile));
//        }
    }

    private void setPlayerHands() {
        for (int i = 0; i < 4; i++) {
            Player[] players = ForbiddenGame.players;
            if (players[i] == null) {
                break;
            }
            switch (i) {
                case 0 -> playerOneTreasureCard = players[i].getHandCards();
                case 1 -> playerTwoTreasureCard = players[i].getHandCards();
                case 2 -> playerThreeTreasureCard = players[i].getHandCards();
                case 3 -> playerFourTreasureCard = players[i].getHandCards();
            }
        }
    }

    private void drawPlayerHands() {
        // 玩家1
        for (int i = 0; i < playerOneHandImages.length; i++) {
            playerOneHandImages[i].setImage(null); // 清空旧图片
        }
        int index = 0;
        for (TreasureCard card : playerOneTreasureCard) {
            if (index >= playerOneHandImages.length) break;
            playerOneHandImages[index++].setImage(card.getImage());
        }

        // 玩家2
        for (int i = 0; i < playerTwoHandImages.length; i++) {
            playerTwoHandImages[i].setImage(null);
        }
        index = 0;
        for (TreasureCard card : playerTwoTreasureCard) {
            if (index >= playerTwoHandImages.length) break;
            playerTwoHandImages[index++].setImage(card.getImage());
        }

        // 玩家3
        for (int i = 0; i < playerThreeHandImages.length; i++) {
            playerThreeHandImages[i].setImage(null);
        }
        if (playerThreeTreasureCard != null) {
            index = 0;
            for (TreasureCard card : playerThreeTreasureCard) {
                if (index >= playerThreeHandImages.length) break;
                playerThreeHandImages[index++].setImage(card.getImage());
            }
        }

        // 玩家4
        for (int i = 0; i < playerFourHandImages.length; i++) {
            playerFourHandImages[i].setImage(null);
        }
        if (playerFourTreasureCard != null) {
            index = 0;
            for (TreasureCard card : playerFourTreasureCard) {
                if (index >= playerFourHandImages.length) break;
                playerFourHandImages[index++].setImage(card.getImage());
            }
        }
    }


    private void flood(ImageView imageView) {
        Boolean flooded = (Boolean) imageView.getProperties().get("flooded");
        if (Boolean.TRUE.equals(flooded)) {
            sunk(imageView);
            return;
        }
        double width = imageView.getFitWidth();
        double height = imageView.getFitHeight();

        Color deepBlue = Color.rgb(0, 0, 180, 0.5);

        ColorInput floodOverlay = new ColorInput(0, 0, width, height, deepBlue);

        Light.Distant light = new Light.Distant();
        light.setAzimuth(45); // 光源方向
        Lighting lighting = new Lighting();
        lighting.setLight(light);
        lighting.setSurfaceScale(5.0);

        Blend blend = new Blend();
        blend.setMode(BlendMode.SRC_OVER);
        blend.setBottomInput(lighting);
        blend.setTopInput(floodOverlay);

        imageView.setEffect(blend);
        imageView.getProperties().put("flooded", true);
    }

    private void sunk(ImageView imageView) {
        imageView.setOpacity(0);
    }

    private void shoreUp(ImageView imageView) {
        imageView.setEffect(null);              // 移除视觉特效
        imageView.setOpacity(1.0);              // 恢复不透明度
        imageView.setRotate(0);
        imageView.setScaleX(1.0);
        imageView.setScaleY(1.0);
        imageView.setTranslateX(0);
        imageView.setTranslateY(0);
        imageView.setBlendMode(null);           // 清除混合模式
        imageView.getProperties().put("flooded", false);  // ✅ 更新为未被淹状态
    }



    private static double getRelativeXToAnchor(ImageView imageView, AnchorPane anchorPane) {
        Bounds imageBoundsInScene = imageView.localToScene(imageView.getBoundsInLocal());
        Bounds anchorBoundsInScene = anchorPane.localToScene(anchorPane.getBoundsInLocal());
        return imageBoundsInScene.getMinX() - anchorBoundsInScene.getMinX();
    }

    private static double getRelativeYToAnchor(ImageView imageView, AnchorPane anchorPane) {
        Bounds imageBoundsInScene = imageView.localToScene(imageView.getBoundsInLocal());
        Bounds anchorBoundsInScene = anchorPane.localToScene(anchorPane.getBoundsInLocal());
        return imageBoundsInScene.getMinY() - anchorBoundsInScene.getMinY();
    }


    private void drawFlood() {
        for (int i = 0; i < 6; i++) {
            Integer index = ForbiddenGame.getInstance().getFloodCard().poll();
            if (index != null) {
                switch (index) {
                    case 0 -> flood(place0);
                    case 1 -> flood(place1);
                    case 2 -> flood(place2);
                    case 3 -> flood(place3);
                    case 4 -> flood(place4);
                    case 5 -> flood(place5);
                    case 6 -> flood(place6);
                    case 7 -> flood(place7);
                    case 8 -> flood(place8);
                    case 9 -> flood(place9);
                    case 10 -> flood(place10);
                    case 11 -> flood(place11);
                    case 12 -> flood(place12);
                    case 13 -> flood(place13);
                    case 14 -> flood(place14);
                    case 15 -> flood(place15);
                    case 16 -> flood(place16);
                    case 17 -> flood(place17);
                    case 18 -> flood(place18);
                    case 19 -> flood(place19);
                    case 20 -> flood(place20);
                    case 21 -> flood(place21);
                    case 22 -> flood(place22);
                    case 23 -> flood(place23);
                }
            }
            ForbiddenGame.getInstance().getUsedFloodCard().offer(index);
        }
    }

    private void drawNextTurnFlood() {
        for (int i = 0; i < ForbiddenGame.getLevel(); i++) {
            Integer index = ForbiddenGame.getInstance().getFloodCard().poll();
            if (index != null) {
                switch (index) {
                    case 0 -> flood(place0);
                    case 1 -> flood(place1);
                    case 2 -> flood(place2);
                    case 3 -> flood(place3);
                    case 4 -> flood(place4);
                    case 5 -> flood(place5);
                    case 6 -> flood(place6);
                    case 7 -> flood(place7);
                    case 8 -> flood(place8);
                    case 9 -> flood(place9);
                    case 10 -> flood(place10);
                    case 11 -> flood(place11);
                    case 12 -> flood(place12);
                    case 13 -> flood(place13);
                    case 14 -> flood(place14);
                    case 15 -> flood(place15);
                    case 16 -> flood(place16);
                    case 17 -> flood(place17);
                    case 18 -> flood(place18);
                    case 19 -> flood(place19);
                    case 20 -> flood(place20);
                    case 21 -> flood(place21);
                    case 22 -> flood(place22);
                    case 23 -> flood(place23);
                }
            }
            ForbiddenGame.getInstance().getUsedFloodCard().offer(index);
        }
    }

    private void drawPlayers() {
        ImageView[] playerCards = { cornerTopLeft, cornerTopRight, cornerBottomLeft, cornerBottomRight };
        playerPawns = new ImageView[]{pawnOne, pawnTwo, pawnThree, pawnFour};

        // 设置卡牌图像
        for (int i = 0; i < ForbiddenGame.players.length; i++) {
            if (ForbiddenGame.players[i] != null) {
                playerCards[i].setImage(ForbiddenGame.players[i].getCardImage());
            }
        }

        // 在布局完成后设置棋子位置
        Platform.runLater(() -> {
            double overallYOffset = 10; // 整体往下移10像素
            for (int i = 0; i < ForbiddenGame.players.length; i++) {
                if (ForbiddenGame.players[i] == null) {
                    break;
                }
                int placeIndex = ForbiddenGame.players[i].getPlace();
                if (placeIndex >= 0 && placeIndex < places.length) {
                    ImageView placeImageView = places[placeIndex];

                    // 设置棋子图像
                    playerPawns[i].setImage(ForbiddenGame.players[i].getPawnImage());

                    // 计算该 place 相对于 anchorPane 的位置
                    double x = getRelativeXToAnchor(placeImageView, anchorPane);
                    double y = getRelativeYToAnchor(placeImageView, anchorPane);

                    double centerOffsetX = placeImageView.getImage().getWidth() / 2.0 - playerPawns[i].getImage().getWidth() / 2.0;
                    double centerOffsetY = placeImageView.getImage().getHeight() / 2.0 - playerPawns[i].getImage().getHeight() / 2.0;

                    double marginX = 15;
                    double marginYTop = 15;
                    double marginYBottom = 30;

                    double offsetX = 0;
                    double offsetY = 0;

                    switch (i) {
                        case 0: // 左上
                            offsetX = centerOffsetX - marginX;
                            offsetY = centerOffsetY - marginYTop;
                            break;
                        case 1: // 右上
                            offsetX = centerOffsetX + marginX;
                            offsetY = centerOffsetY - marginYTop;
                            break;
                        case 2: // 左下
                            offsetX = centerOffsetX - marginX;
                            offsetY = centerOffsetY + marginYBottom;
                            break;
                        case 3: // 右下
                            offsetX = centerOffsetX + marginX;
                            offsetY = centerOffsetY + marginYBottom;
                            break;
                    }

                    playerPawns[i].setLayoutX(x + offsetX);
                    playerPawns[i].setLayoutY(y + offsetY + overallYOffset);
                }
            }
        });
    }

    @FXML
    void mouseClickedCapture(MouseEvent event) {
        if (ForbiddenGame.getActionRemain() == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Notice");
            alert.setHeaderText(null);
            alert.setContentText("You have no remaining action points!");
            alert.showAndWait();
            return;
        }
        ForbiddenGame.getInstance().useAction();
        setActionText(String.valueOf(ForbiddenGame.getActionRemain()));

        List<String> elements = Arrays.asList("Fire", "Wind", "Ocean", "Earth");

        ChoiceDialog<String> dialog = new ChoiceDialog<>(elements.get(0), elements);
        dialog.setTitle("Treasure Selection");
        dialog.setHeaderText("Choose a Treasure to Capture");
        dialog.setContentText("Available Treasures:");

        Optional<String> result = dialog.showAndWait();
        Player player = ForbiddenGame.getInstance().getCurPlayer();
        result.ifPresent(selected -> {
            switch (selected) {
                case "Fire" -> {
                    Queue<TreasureCard> newCards = new LinkedList<>();
                    for(TreasureCard treasureCard : player.getHandCards()) {
                        if (treasureCard.getTreasureCardType() != TreasureCardType.FIRE_CARD) {
                            newCards.add(treasureCard);
                        }
                        ForbiddenGame.getInstance().getUsedTreasureCard().add(new TreasureCard(TreasureCardType.FIRE_CARD));
                    }
                    player.setHandCards(newCards);
                    setPlayerHands();
                    drawPlayerHands();
                    ForbiddenGame.getInstance().add("fire", new Treasure(TreasureType.FIRE));
                    System.out.println("Get Fire");
                }
                case "Wind" -> {
                    Queue<TreasureCard> newCards = new LinkedList<>();
                    for(TreasureCard treasureCard : player.getHandCards()) {
                        if (treasureCard.getTreasureCardType() != TreasureCardType.WIND_CARD ) {
                            newCards.add(treasureCard);
                        }
                        ForbiddenGame.getInstance().getUsedTreasureCard().add(new TreasureCard(TreasureCardType.WIND_CARD));
                    }
                    player.setHandCards(newCards);
                    setPlayerHands();
                    drawPlayerHands();
                    ForbiddenGame.getInstance().add("wind", new Treasure(TreasureType.WIND));
                    System.out.println("Get Wind");
                }
                case "Ocean" -> {
                    Queue<TreasureCard> newCards = new LinkedList<>();
                    for(TreasureCard treasureCard : player.getHandCards()) {
                        if (treasureCard.getTreasureCardType() != TreasureCardType.OCEAN_CARD) {
                            newCards.add(treasureCard);
                        }
                        ForbiddenGame.getInstance().getUsedTreasureCard().add(new TreasureCard(TreasureCardType.OCEAN_CARD));
                    }
                    player.setHandCards(newCards);
                    setPlayerHands();
                    drawPlayerHands();
                    ForbiddenGame.getInstance().add("ocean", new Treasure(TreasureType.OCEAN));
                    System.out.println("Get Ocean");
                }
                case "Earth" -> {
                    Queue<TreasureCard> newCards = new LinkedList<>();
                    for(TreasureCard treasureCard : player.getHandCards()) {
                        if (treasureCard.getTreasureCardType() != TreasureCardType.EARTH_CARD) {
                            newCards.add(treasureCard);
                        }
                        ForbiddenGame.getInstance().getUsedTreasureCard().add(new TreasureCard(TreasureCardType.EARTH_CARD));
                    }
                    player.setHandCards(newCards);
                    setPlayerHands();
                    drawPlayerHands();
                    ForbiddenGame.getInstance().add("earth", new Treasure(TreasureType.EARTH));
                    System.out.println("Get Earth");
                }
            }
            System.out.println("You chose: " + selected);
        });
    }

    private boolean canGiveState = false;

    @FXML
    void mouseClickedGive(MouseEvent event) {
        alreadyChosePlayer = false;
        if (ForbiddenGame.getActionRemain() == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Notice");
            alert.setHeaderText(null);
            alert.setContentText("You have no remaining action points!");
            alert.showAndWait();
            return;
        }
        // 进入“给牌”流程，等待点击目标玩家头像再点击手牌
        canGiveState = true;
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Give Card Instruction");
        alert.setHeaderText(null);
        alert.setContentText("Please click the player avatar you want to give the card to, then click the card in your hand.");
        alert.showAndWait();
    }

    @FXML
    void mouseClickedMove(MouseEvent event) {
        // 如果没有行动点，直接提示
        if (ForbiddenGame.getActionRemain() == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Notice");
            alert.setHeaderText(null);
            alert.setContentText("You have no remaining action points!");
            alert.showAndWait();
            return;
        }

        // 当前玩家索引 & 对应的棋子
        int curPlayerIndex = ForbiddenGame.getInstance().getCurPlayerIndex();
        Player curPlayer = ForbiddenGame.getInstance().getCurPlayer();
        ImageView pawn = playerPawns[curPlayerIndex];

        // 1. 先弹窗让玩家选择方向
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle("Choose Move Direction");
        alert.setHeaderText("Please select a direction");

        ButtonType btnUp = new ButtonType("Up");
        ButtonType btnDown = new ButtonType("Down");
        ButtonType btnLeft = new ButtonType("Left");
        ButtonType btnRight = new ButtonType("Right");
        ButtonType btnCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(btnUp, btnDown, btnLeft, btnRight, btnCancel);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isEmpty() || result.get() == btnCancel) {
            return; // 用户取消
        }

        // 定义一个“索引→(row,col)” 的映射，和相反的“(row,col)→索引”映射。
        // 这个表必须和你的 FXML 中 GridPane 上各个 ImageView 的 row/col 布局一一对应。
        final int[][] indexGrid = {
                { -1, -1,  0,  1, -1, -1 },   // row=0
                { -1,  2,  3,  4,  5, -1 },   // row=1
                {  6,  7,  8,  9, 10, 11 },   // row=2
                { 12, 13, 14, 15, 16, 17 },   // row=3
                { -1, 18, 19, 20, 21, -1 },   // row=4
                { -1, -1, 22, 23, -1, -1 }    // row=5
        };

        // 先拿到当前玩家在 places 数组里的索引
        int currentIndex = curPlayer.getPlace();

        // 找到 currentIndex 对应的行列 (row, col)
        int currentRow = -1, currentCol = -1;
        outer:
        for (int r = 0; r < indexGrid.length; r++) {
            for (int c = 0; c < indexGrid[r].length; c++) {
                if (indexGrid[r][c] == currentIndex) {
                    currentRow = r;
                    currentCol = c;
                    break outer;
                }
            }
        }

        if (currentRow < 0) {
            // 理论上不可能，说明玩家的 place 尚未初始化好
            System.err.println("mouseClickedMove: 无法找到当前玩家 place 在 indexGrid 中的位置");
            return;
        }

        // 2. 根据玩家选择的方向，算出 newRow, newCol
        int newRow = currentRow, newCol = currentCol;
        if (result.get() == btnUp) {
            newRow = currentRow - 1;
        } else if (result.get() == btnDown) {
            newRow = currentRow + 1;
        } else if (result.get() == btnLeft) {
            newCol = currentCol - 1;
        } else if (result.get() == btnRight) {
            newCol = currentCol + 1;
        }

        // 3. 首先判定 newRow/newCol 是否越界
        if (newRow < 0 || newRow >= indexGrid.length || newCol < 0 || newCol >= indexGrid[0].length) {
            Alert invalid = new Alert(Alert.AlertType.WARNING);
            invalid.setTitle("Invalid Move");
            invalid.setHeaderText(null);
            invalid.setContentText("You cannot move outside the board.");
            invalid.showAndWait();
            return;
        }

        // 4. 再判定 indexGrid[newRow][newCol] 是否为 -1（表示该位置没有岛屿）
        int targetIndex = indexGrid[newRow][newCol];
        if (targetIndex < 0) {
            Alert invalid = new Alert(Alert.AlertType.WARNING);
            invalid.setTitle("Invalid Move");
            invalid.setHeaderText(null);
            invalid.setContentText("There is no island in that direction. Please choose another direction.");
            invalid.showAndWait();
            return;
        }

        // 5. 如果合法，真的“消耗一次行动点”并同步更新模型里玩家的 place
        ForbiddenGame.getInstance().useAction();
        setActionText(String.valueOf(ForbiddenGame.getActionRemain()));
        curPlayer.setPlace(targetIndex);

        // 6. 把 pawn 移到 places[targetIndex] 的中心
        ImageView targetTile = places[targetIndex];

        // 先算出 targetTile 在 anchorPane 本地坐标系下的左上角
        Bounds anchorBounds = anchorPane.localToScene(anchorPane.getBoundsInLocal());
        Bounds tileBounds   = targetTile.localToScene(targetTile.getBoundsInLocal());
        double tileRelX = tileBounds.getMinX() - anchorBounds.getMinX();
        double tileRelY = tileBounds.getMinY() - anchorBounds.getMinY();

        // 再算出 pawn 放在 tile 中心时的偏移
        double offsetX = (tileBounds.getWidth()  - pawn.getBoundsInLocal().getWidth())  / 2.0;
        double offsetY = (tileBounds.getHeight() - pawn.getBoundsInLocal().getHeight()) / 2.0;

        pawn.setLayoutX(tileRelX + offsetX);
        pawn.setLayoutY(tileRelY + offsetY);
    }



    private boolean isTileAdjacent(ImageView tileA, ImageView tileB) {
        if (tileA == null || tileB == null) return false;

        // 如果是同一个节点，算作“脚下”也视为相邻
        if (tileA == tileB) return true;

        Integer rowA = GridPane.getRowIndex(tileA);
        Integer colA = GridPane.getColumnIndex(tileA);
        Integer rowB = GridPane.getRowIndex(tileB);
        Integer colB = GridPane.getColumnIndex(tileB);

        if (rowA == null) rowA = 0;
        if (colA == null) colA = 0;
        if (rowB == null) rowB = 0;
        if (colB == null) colB = 0;

        boolean sameRowAndColAdjacent = rowA.equals(rowB) && Math.abs(colA - colB) == 1;
        boolean sameColAndRowAdjacent = colA.equals(colB) && Math.abs(rowA - rowB) == 1;

        return sameRowAndColAdjacent || sameColAndRowAdjacent;
    }

    @FXML
    void mouseClickedShoreUp(MouseEvent event) {
        if (ForbiddenGame.getActionRemain() == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Notice");
            alert.setHeaderText(null);
            alert.setContentText("You have no remaining action points!");
            alert.showAndWait();
            return;
        }

        shoreUpState = 1;
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Shore Up Instruction");
        alert.setHeaderText(null);
        alert.setContentText("Please click the island you want to shore up.");

        ButtonType btnOk = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        alert.getButtonTypes().setAll(btnOk);

        alert.showAndWait();
    }

    @FXML void mouseEnteredCapture(MouseEvent event) {capture.setOpacity(0.5);}
    @FXML void mouseEnteredGive(MouseEvent event) {give.setOpacity(0.5);}
    @FXML void mouseEnteredMove(MouseEvent event) {move.setOpacity(0.5);}
    @FXML void mouseEnteredShoreUp(MouseEvent event) {shoreUp.setOpacity(0.5);}
    @FXML void mouseExitedCapture(MouseEvent event) {capture.setOpacity(1);}
    @FXML void mouseExitedGive(MouseEvent event) {give.setOpacity(1);}
    @FXML void mouseExitedMove(MouseEvent event) {move.setOpacity(1);}
    @FXML void mouseExitedShoreUp(MouseEvent event) {shoreUp.setOpacity(1);}
    @FXML void mouseEnteredNextTurn(MouseEvent event) {nextTurn.setOpacity(0.5);}
    @FXML void mouseEnteredUseSkill(MouseEvent event) {useSkill.setOpacity(0.5);}
    @FXML void mouseExitedNextTurn(MouseEvent event) {nextTurn.setOpacity(1);}
    @FXML void mouseExitedUseSkill(MouseEvent event) {useSkill.setOpacity(1);}

    @FXML
    void mouseClickedUseSkill(MouseEvent event) {
        if (ForbiddenGame.getActionRemain() == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Notice");
            alert.setHeaderText(null);
            alert.setContentText("You have no remaining action points!");
            alert.showAndWait();
            return;
        }

        Player player = ForbiddenGame.getInstance().getCurPlayer();
        switch (player.getType()) {
            case DIVER -> diverSkill();
            case ENGINEER -> engineerSkill();
            case PILOT -> pilotSkill();
            case EXPLORER -> explorerSkill();
            case MESSENGER -> messengerSkill();
            case NAVIGATOR -> navigatorSkill(anchorPane);
        }
    }

    public void navigatorSkill(AnchorPane gamePane) {
        // 弹窗1：选择玩家
        Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
        alert1.setTitle("Move Other Player");
        alert1.setHeaderText(null);
        alert1.setContentText("You can move another player.\nPlease click the player's avatar you want to move.");
        alert1.showAndWait();

        // 等待点击玩家头像
        waitForNextClick(gamePane).thenRun(() -> {
            Platform.runLater(() -> {
                // 弹窗2：第一次移动
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                alert2.setTitle("First Movement");
                alert2.setHeaderText(null);
                alert2.setContentText("Please proceed with the first movement.");
                alert2.showAndWait();

                Alert alert = new Alert(Alert.AlertType.NONE);
                alert.setTitle("Choose Move Direction");
                alert.setHeaderText("Please select a direction");

                ButtonType btnUp = new ButtonType("Up");
                ButtonType btnDown = new ButtonType("Down");
                ButtonType btnLeft = new ButtonType("Left");
                ButtonType btnRight = new ButtonType("Right");
                ButtonType btnCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

                alert.getButtonTypes().setAll(btnUp, btnDown, btnLeft, btnRight, btnCancel);

                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent()) {
                    ButtonType chosen = result.get();
                    ImageView pawn = playerPawns[playerToMoveIndex];

                    if (chosen == btnUp) {
                        pawn.setTranslateY(pawn.getTranslateY() - 105);
                        System.out.println("Selected Up");
                    } else if (chosen == btnDown) {
                        pawn.setTranslateY(pawn.getTranslateY() + 105);
                        System.out.println("Selected Down");
                    } else if (chosen == btnLeft) {
                        pawn.setTranslateX(pawn.getTranslateX() - 108);
                        System.out.println("Selected Left");
                    } else if (chosen == btnRight) {
                        pawn.setTranslateX(pawn.getTranslateX() + 108);
                        System.out.println("Selected Right");
                    } else {
                        System.out.println("Selection cancelled");
                    }
                }
                // 等待第一次移动操作
                // 弹窗3：第二次移动
                Alert alert3 = new Alert(Alert.AlertType.INFORMATION);
                alert3.setTitle("Second Movement");
                alert3.setHeaderText(null);
                alert3.setContentText("Please proceed with the second movement.");
                alert3.showAndWait();

                Alert alert4 = new Alert(Alert.AlertType.NONE);
                alert4.setTitle("Choose Move Direction");
                alert4.setHeaderText("Please select a direction");
                ButtonType btnUp1 = new ButtonType("Up");
                ButtonType btnDown1 = new ButtonType("Down");
                ButtonType btnLeft1 = new ButtonType("Left");
                ButtonType btnRight1 = new ButtonType("Right");
                ButtonType btnCancel1 = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
                alert4.getButtonTypes().setAll(btnUp1, btnDown1, btnLeft1, btnRight1, btnCancel1);

                Optional<ButtonType> result1 = alert4.showAndWait();
                if (result1.isPresent()) {
                    ButtonType chosen = result1.get();
                    ImageView pawn = playerPawns[playerToMoveIndex];

                    if (chosen == btnUp1) {
                        pawn.setTranslateY(pawn.getTranslateY() - 105);
                        System.out.println("Selected Up");
                    } else if (chosen == btnDown1) {
                        pawn.setTranslateY(pawn.getTranslateY() + 105);
                        System.out.println("Selected Down");
                    } else if (chosen == btnLeft1) {
                        pawn.setTranslateX(pawn.getTranslateX() - 108);
                        System.out.println("Selected Left");
                    } else if (chosen == btnRight1) {
                        pawn.setTranslateX(pawn.getTranslateX() + 108);
                        System.out.println("Selected Right");
                    } else {
                        System.out.println("Selection cancelled");
                    }
                }
                // 等待第二次移动操作
                waitForNextClick(gamePane).thenRun(() -> {
                    Platform.runLater(() -> {
                        System.out.println("第二次移动完成！");
                    });
                });
            });
        });
    }

    private CompletableFuture<MouseEvent> waitForNextClick(Node node) {
        CompletableFuture<MouseEvent> future = new CompletableFuture<>();

        EventHandler<MouseEvent> handler = new EventHandler<>() {
            @Override
            public void handle(MouseEvent event) {
                node.removeEventHandler(MouseEvent.MOUSE_CLICKED, this);
                future.complete(event);
            }
        };

        node.addEventHandler(MouseEvent.MOUSE_CLICKED, handler);
        return future;
    }

    private boolean isMessenger = false;

    private void messengerSkill() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Card Giving Instruction");
        alert.setHeaderText("You may give a card to any player.");
        alert.setContentText("Please click the 'Give' button to proceed.");
        isMessenger = true;
        Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
        alert2.setTitle("Give Card Instruction");
        alert2.setHeaderText(null);
        alert2.setContentText("Please click the avatar of the player you want to give the card to first, then click the card you want to give.");
        alert2.showAndWait();
    }

    private void explorerSkill() {
        // Prompt the player to choose “Move” or “Shore Up”
        Alert choiceAlert = new Alert(Alert.AlertType.NONE);
        choiceAlert.setTitle("Explorer Skill");
        choiceAlert.setHeaderText("Choose Action");
        choiceAlert.setContentText("You may move to an adjacent tile (including diagonals) or shore up an adjacent tile (including diagonals).");

        ButtonType btnMove = new ButtonType("Move", ButtonBar.ButtonData.OK_DONE);
        ButtonType btnShoreUp = new ButtonType("Shore Up", ButtonBar.ButtonData.OK_DONE);
        ButtonType btnCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        choiceAlert.getButtonTypes().setAll(btnMove, btnShoreUp, btnCancel);
        Optional<ButtonType> choice = choiceAlert.showAndWait();
        if (choice.isEmpty() || choice.get() == btnCancel) {
            return; // User cancelled
        }

        boolean doMove = choice.get() == btnMove;
        // Wait for the player to click a target tile
        waitForNextMouseClick(anchorPane, point -> {
            double clickX = point.getX();
            double clickY = point.getY();

            // 1. Find which tile (if any) the click corresponds to
            ImageView targetTile = null;
            for (ImageView tile : places) {
                if (tile == null) continue;
                Bounds tileBounds = tile.localToScene(tile.getBoundsInLocal());
                if (tileBounds.contains(clickX, clickY)) {
                    targetTile = tile;
                    break;
                }
            }
            if (targetTile == null) {
                Alert invalid = new Alert(Alert.AlertType.WARNING);
                invalid.setTitle("Invalid Action");
                invalid.setHeaderText(null);
                invalid.setContentText("Please click on a valid island tile.");
                invalid.showAndWait();
                return;
            }

            // 2. Find the player’s current tile
            ImageView pawn = switch (ForbiddenGame.getInstance().getCurPlayerIndex()) {
                case 0 -> pawnOne;
                case 1 -> pawnTwo;
                case 2 -> pawnThree;
                case 3 -> pawnFour;
                default -> null;
            };
            if (pawn == null) {
                return;
            }

            ImageView currentTile = getUnderlyingTile(pawn);
            if (currentTile == null) {
                return;
            }

            // 3. Check adjacency (including diagonals)
            Integer rowA = GridPane.getRowIndex(currentTile), colA = GridPane.getColumnIndex(currentTile);
            Integer rowB = GridPane.getRowIndex(targetTile), colB = GridPane.getColumnIndex(targetTile);
            if (rowA == null) rowA = 0;
            if (colA == null) colA = 0;
            if (rowB == null) rowB = 0;
            if (colB == null) colB = 0;

            int rowDiff = Math.abs(rowA - rowB);
            int colDiff = Math.abs(colA - colB);
            boolean isAdjacent = (rowDiff <= 1 && colDiff <= 1) && !(rowDiff == 0 && colDiff == 0);

            if (!isAdjacent) {
                Alert invalidAdj = new Alert(Alert.AlertType.WARNING);
                invalidAdj.setTitle("Invalid Action");
                invalidAdj.setHeaderText(null);
                invalidAdj.setContentText("The selected tile is not adjacent to your current position (including diagonals).");
                invalidAdj.showAndWait();
                return;
            }

            // 4. Perform the chosen action
            if (doMove) {
                // Move pawn to the center of targetTile
                double tileRelX = getRelativeXToAnchor(targetTile, anchorPane);
                double tileRelY = getRelativeYToAnchor(targetTile, anchorPane);
                double offsetX = (targetTile.getImage().getWidth() - pawn.getImage().getWidth()) / 2.0;
                double offsetY = (targetTile.getImage().getHeight() - pawn.getImage().getHeight()) / 2.0;
                pawn.setLayoutX(tileRelX + offsetX);
                pawn.setLayoutY(tileRelY + offsetY);
                ForbiddenGame.getInstance().useAction();
                setActionText(String.valueOf(ForbiddenGame.getActionRemain()));
            } else {
                // Shore up the target tile
                shoreUp(targetTile);
                ForbiddenGame.getInstance().useAction();
                setActionText(String.valueOf(ForbiddenGame.getActionRemain()));
            }
        });
    }

    // Helper: wait for next mouse click on a Node
    private void waitForNextMouseClick(Node node, Consumer<Point2D> onClick) {
        EventHandler<MouseEvent> handler = new EventHandler<>() {
            @Override
            public void handle(MouseEvent event) {
                double x = event.getSceneX();
                double y = event.getSceneY();
                onClick.accept(new Point2D(x, y));
                node.removeEventHandler(MouseEvent.MOUSE_CLICKED, this);
            }
        };
        node.addEventHandler(MouseEvent.MOUSE_CLICKED, handler);
    }


    private void waitForNextMouseClick(AnchorPane anchorPane, Consumer<Point2D> onClick) {
        EventHandler<MouseEvent> handler = new EventHandler<>() {
            @Override
            public void handle(MouseEvent event) {
                double x = event.getSceneX();
                double y = event.getSceneY();
                onClick.accept(new Point2D(x, y));
                // 一次性使用，移除监听器
                anchorPane.removeEventHandler(MouseEvent.MOUSE_CLICKED, this);
            }
        };
        anchorPane.addEventHandler(MouseEvent.MOUSE_CLICKED, handler);
    }


    @FXML
    private void pilotSkill() {
        if (usePilotSkill == 1) {
            // 弹出提示，说明可以点击任意岛屿
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Move Instruction");
            alert.setHeaderText(null);
            alert.setContentText("You can move to any tile on the board. Please click on a valid island tile.");
            alert.showAndWait();

            // 等待下一次点击
            waitForNextMouseClick(anchorPane, point -> {
                double clickSceneX = point.getX();
                double clickSceneY = point.getY();
                log("PilotSkill: 用户点击坐标 (scene)：(" + clickSceneX + ", " + clickSceneY + ")");

                // 1. 找到点击位置对应的岛屿 tile
                ImageView clickedTile = null;
                for (ImageView tile : places) {
                    if (tile == null) continue;
                    Bounds tileBoundsInScene = tile.localToScene(tile.getBoundsInLocal());
                    if (tileBoundsInScene.contains(clickSceneX, clickSceneY)) {
                        clickedTile = tile;
                        break;
                    }
                }

                if (clickedTile != null) {
                    // 2. 找到对应玩家的 pawn，然后把其移动到该岛屿的中心位置
                    int curIndex = ForbiddenGame.getInstance().getCurPlayerIndex();
                    ImageView pawnToMove = null;
                    switch (curIndex) {
                        case 0 -> pawnToMove = pawnOne;
                        case 1 -> pawnToMove = pawnTwo;
                        case 2 -> pawnToMove = pawnThree;
                        case 3 -> pawnToMove = pawnFour;
                    }
                    if (pawnToMove == null) {
                        log("PilotSkill: 无法找到玩家 " + curIndex + " 对应的 pawn");
                        return;
                    }

                    // 计算 tile 相对于 anchorPane 的左上角坐标
                    double tileRelX = getRelativeXToAnchor(clickedTile, anchorPane);
                    double tileRelY = getRelativeYToAnchor(clickedTile, anchorPane);
                    // 计算 pawn 要定位到 tile 中心时的偏移
                    double offsetX = (clickedTile.getImage().getWidth() - pawnToMove.getImage().getWidth()) / 2.0;
                    double offsetY = (clickedTile.getImage().getHeight() - pawnToMove.getImage().getHeight()) / 2.0;

                    // 将 pawn 布局到 tile 中心
                    pawnToMove.setLayoutX(tileRelX + offsetX);
                    pawnToMove.setLayoutY(tileRelY + offsetY);

                    log("PilotSkill: 玩家 " + curIndex + " 的 pawn 移动到 Tile（index="
                            + Arrays.asList(places).indexOf(clickedTile) + "）中心");

                    // 标记本回合的 pilot 技能已经用过
                    usePilotSkill = 0;
                    ForbiddenGame.getInstance().useAction();
                    setActionText(String.valueOf(ForbiddenGame.getActionRemain()));
                } else {
                    // 用户点击的地方不属于任何 island tile
                    Alert invalidAlert = new Alert(Alert.AlertType.WARNING);
                    invalidAlert.setTitle("Invalid Move");
                    invalidAlert.setHeaderText(null);
                    invalidAlert.setContentText("Please click on a valid island tile.");
                    invalidAlert.showAndWait();
                    log("PilotSkill: 点击位置不在任何有效岛屿上，移动取消");
                }
            });
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Action Not Allowed");
            alert.setHeaderText(null);
            alert.setContentText("This cannot be used again this turn.");
            alert.showAndWait();
        }
    }

    // 日志辅助方法（调试用，可放在类里任意位置）
    private void log(String msg) {
        System.out.println("[DEBUG] " + msg);
    }

    private void engineerSkill() {
        ForbiddenGame.getInstance().useAction();
        setActionText(String.valueOf(ForbiddenGame.getActionRemain()));
        useEngineerSkill = 2;
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Drain Instruction");
        alert.setHeaderText("You may drain two flooded tiles.");
        alert.setContentText("Please click the islands you want to drain.");
        alert.showAndWait();
    }

    @FXML private GridPane gridPane;

    @FXML
    private void diverSkill() {
        // 1. 提示玩家使用潜水员技能
        Alert prompt = new Alert(Alert.AlertType.INFORMATION);
        prompt.setTitle("Diver Skill");
        prompt.setHeaderText(null);
        prompt.setContentText("You can travel through flooded tiles. Click on a dry island to land.");
        prompt.showAndWait();

        // 2. 等待玩家下一次点击
        waitForNextMouseClick(anchorPane, point -> {
            double clickSceneX = point.getX();
            double clickSceneY = point.getY();
            log("[DEBUG] DiverSkill: 用户点击坐标 (scene)=(" + clickSceneX + ", " + clickSceneY + ")");

            // —— 把 Scene 坐标转换到 GridPane 本地坐标系 ——
            Point2D localPt = gridPane.sceneToLocal(clickSceneX, clickSceneY);
            double xLocal = localPt.getX();
            double yLocal = localPt.getY();

            // 3. 取出 GridPane 渲染后的宽高、hgap、vgap
            double totalW = gridPane.getLayoutBounds().getWidth();
            double totalH = gridPane.getLayoutBounds().getHeight();
            double hgap   = gridPane.getHgap();
            double vgap   = gridPane.getVgap();
            int cols = 6, rows = 6;

            // 4. 判定点击是否在 GridPane 区域外
            if (xLocal < 0 || yLocal < 0 || xLocal > totalW || yLocal > totalH) {
                Alert invalid = new Alert(Alert.AlertType.WARNING);
                invalid.setTitle("Invalid Selection");
                invalid.setHeaderText(null);
                invalid.setContentText("Please click on a valid island tile.");
                invalid.showAndWait();
                log("[DEBUG] DiverSkill: 点击在 GridPane 外，操作取消");
                return;
            }

            // 5. 计算每个格子的“实际宽度/高度”
            double cellW = (totalW - (cols - 1) * hgap) / cols;
            double cellH = (totalH - (rows - 1) * vgap) / rows;

            // 6. 计算点击落在哪一列/哪一行
            int colIdx = (int) (xLocal / (cellW + hgap));
            int rowIdx = (int) (yLocal / (cellH + vgap));

            // 7. 判定是否落在格子之间的 gap
            double xInCell = xLocal - colIdx * (cellW + hgap);
            double yInCell = yLocal - rowIdx * (cellH + vgap);
            if (xInCell > cellW || yInCell > cellH) {
                Alert invalid = new Alert(Alert.AlertType.WARNING);
                invalid.setTitle("Invalid Selection");
                invalid.setHeaderText(null);
                invalid.setContentText("Please click on a valid island tile.");
                invalid.showAndWait();
                log("[DEBUG] DiverSkill: 点击落在了格子间隙 (row=" + rowIdx + ", col=" + colIdx + ")");
                return;
            }

            // 8. FXML 中 row/col → places[index] 映射表 (-1 表示该位置无岛屿)
            final int[][] indexGrid = {
                    { -1, -1,  0,  1, -1, -1 },   // row=0
                    { -1,  2,  3,  4,  5, -1 },   // row=1
                    {  6,  7,  8,  9, 10, 11 },   // row=2
                    { 12, 13, 14, 15, 16, 17 },   // row=3
                    { -1, 18, 19, 20, 21, -1 },   // row=4
                    { -1, -1, 22, 23, -1, -1 }    // row=5
            };

            // 9. 判定 rowIdx/colIdx 是否越界
            if (rowIdx < 0 || rowIdx >= rows || colIdx < 0 || colIdx >= cols) {
                Alert invalid = new Alert(Alert.AlertType.WARNING);
                invalid.setTitle("Invalid Selection");
                invalid.setHeaderText(null);
                invalid.setContentText("Please click on a valid island tile.");
                invalid.showAndWait();
                log("[DEBUG] DiverSkill: 计算出的行列越界 (row=" + rowIdx + ", col=" + colIdx + ")");
                return;
            }

            int targetIndex = indexGrid[rowIdx][colIdx];
            if (targetIndex < 0) {
                // 该行列对应无岛屿
                Alert invalid = new Alert(Alert.AlertType.WARNING);
                invalid.setTitle("Invalid Selection");
                invalid.setHeaderText(null);
                invalid.setContentText("Please click on a valid island tile.");
                invalid.showAndWait();
                log("[DEBUG] DiverSkill: 行列(row=" + rowIdx + ",col=" + colIdx + ") 对应无岛屿");
                return;
            }

            // 10. 拿到真正的 ImageView tile，再判断它是否被淹没
            ImageView tile = places[targetIndex];
            Boolean flooded = (Boolean) tile.getProperties().get("flooded");
            if (Boolean.TRUE.equals(flooded)) {
                Alert invalid = new Alert(Alert.AlertType.WARNING);
                invalid.setTitle("Invalid Landing");
                invalid.setHeaderText(null);
                invalid.setContentText("Cannot land on a flooded tile. Please choose a dry island.");
                invalid.showAndWait();
                log("[DEBUG] DiverSkill: 目标 Tile(index=" + targetIndex + ") 已被淹没");
                return;
            }

            // 11. 合法降落 → 把当前玩家 pawn 移到该 tile 的中心，并更新玩家 place
            int curIdx = ForbiddenGame.getInstance().getCurPlayerIndex();
            ImageView pawnToMove = switch (curIdx) {
                case 0 -> pawnOne;
                case 1 -> pawnTwo;
                case 2 -> pawnThree;
                case 3 -> pawnFour;
                default -> null;
            };
            if (pawnToMove == null) {
                log("[DEBUG] DiverSkill: 找不到玩家 " + curIdx + " 对应的 pawn");
                return;
            }

            // 11.1 先拿到 anchorPane 的 Scene 坐标范围
            Bounds anchorBounds = anchorPane.localToScene(anchorPane.getBoundsInLocal());
            // 11.2 再拿到 tile 在 Scene 坐标系中的 Bounds
            Bounds tileBounds   = tile.localToScene(tile.getBoundsInLocal());
            // 11.3 计算 tile 在 anchorPane 坐标系下的左上角
            double tileRelX = tileBounds.getMinX() - anchorBounds.getMinX();
            double tileRelY = tileBounds.getMinY() - anchorBounds.getMinY();
            // 11.4 计算 pawn 要放在 tile 中心时的偏移
            double offsetX = (tileBounds.getWidth()  - pawnToMove.getBoundsInLocal().getWidth())  / 2.0;
            double offsetY = (tileBounds.getHeight() - pawnToMove.getBoundsInLocal().getHeight()) / 2.0;

            pawnToMove.setLayoutX(tileRelX + offsetX);
            pawnToMove.setLayoutY(tileRelY + offsetY);

            // —— 这里就是新增的关键：更新玩家当前位置到 targetIndex ——
            Player curPlayer = ForbiddenGame.getInstance().getCurPlayer();
            curPlayer.setPlace(targetIndex);

            // 12. 消耗一次行动点并刷新右侧显示
            ForbiddenGame.getInstance().useAction();
            setActionText(String.valueOf(ForbiddenGame.getActionRemain()));
            log("[DEBUG] DiverSkill: 玩家 " + curIdx + " 的 pawn 移动到 Tile(index=" + targetIndex + ") 中心");
        });
    }




    private boolean isUnderlyingTileTransparent(ImageView pawn) {
        Bounds pawnBoundsInScene = pawn.localToScene(pawn.getBoundsInLocal());

        double pawnCenterX = pawnBoundsInScene.getMinX() + pawnBoundsInScene.getWidth() / 2.0;
        double pawnCenterY = pawnBoundsInScene.getMinY() + pawnBoundsInScene.getHeight() / 2.0;

        for (ImageView islandTile : places) {
            if (islandTile == null) continue;
            Bounds tileBoundsInScene = islandTile.localToScene(islandTile.getBoundsInLocal());
            if (tileBoundsInScene.contains(pawnCenterX, pawnCenterY)) {
                return islandTile.getOpacity() == 0.0;
            }
        }

        return false;
    }


    @FXML
    void mouseClickedNextTurn(MouseEvent event) {
       for (ImageView pawn : playerPawns) {
           boolean isTransparent = isUnderlyingTileTransparent(pawn);
           if (isTransparent) {
               Alert alert = new Alert(Alert.AlertType.ERROR);
               alert.setTitle("Game Over");
               alert.setHeaderText("Player Dead");
               alert.setContentText("You have died. Game over.");
               alert.showAndWait();
               ForbiddenGame.toMenu();
           } else {
           }
       }

        usePilotSkill = 1;
        ForbiddenGame.getInstance().nextTurn();
        drawPlayerHands();
        drawNextTurnFlood();
        setActionText(String.valueOf(ForbiddenGame.getActionRemain()));
        setLevelText(String.valueOf(ForbiddenGame.getLevel()));
    }

    private void setActionText(String text) {
        if (action != null) {
            action.setText(text);
        }
    }

    private void setLevelText(String text) {
        if (level != null) {
            level.setText(text);
        }
    }


    private ImageView getPawnTileByGameState() {
        ImageView pawn = playerPawns[ForbiddenGame.getInstance().getCurPlayerIndex()];
        return getUnderlyingTile(pawn);
    }

    private boolean isSandBag = false;

    @FXML
    void mouseClickedPlace0(MouseEvent event) {
        ImageView pawnTile = getPawnTileByGameState();
        if (isTileAdjacent(places[0], pawnTile)) {
            Player player = ForbiddenGame.getInstance().getCurPlayer();
            if (player.getType() == PlayerType.ENGINEER && (useEngineerSkill == 1 || useEngineerSkill == 2)) {
                shoreUp(place0);
                useEngineerSkill--;
                return;
            }
            if (shoreUpState == 1) {
                shoreUp(place0);
                if (!isSandBag) {
                    ForbiddenGame.getInstance().useAction();
                    setActionText(String.valueOf(ForbiddenGame.getActionRemain()));
                }
                isSandBag = false;
                shoreUpState--;
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Invalid Action");
            alert.setHeaderText(null);
            alert.setContentText("The selected island is not adjacent.");
            alert.showAndWait();
        }
    }

    @FXML
    void mouseClickedPlace1(MouseEvent event) {
        ImageView pawnTile = getPawnTileByGameState();
        if (isTileAdjacent(places[1], pawnTile)) {
            Player player = ForbiddenGame.getInstance().getCurPlayer();
            if (player.getType() == PlayerType.ENGINEER && (useEngineerSkill == 1 || useEngineerSkill == 2)) {
                shoreUp(place1);
                useEngineerSkill--;
                return;
            }
            if (shoreUpState == 1) {
                shoreUp(place1);
                if (!isSandBag) {
                    ForbiddenGame.getInstance().useAction();
                    setActionText(String.valueOf(ForbiddenGame.getActionRemain()));
                }
                isSandBag = false;
                shoreUpState--;
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Invalid Action");
            alert.setHeaderText(null);
            alert.setContentText("The selected island is not adjacent.");
            alert.showAndWait();
        }
    }

    @FXML
    void mouseClickedPlace2(MouseEvent event) {
        ImageView pawnTile = getPawnTileByGameState();
        if (isTileAdjacent(places[2], pawnTile)) {
            Player player = ForbiddenGame.getInstance().getCurPlayer();
            if (player.getType() == PlayerType.ENGINEER && (useEngineerSkill == 1 || useEngineerSkill == 2)) {
                shoreUp(place2);
                useEngineerSkill--;
                return;
            }
            if (shoreUpState == 1) {
                shoreUp(place2);
                if (!isSandBag) {
                    ForbiddenGame.getInstance().useAction();
                    setActionText(String.valueOf(ForbiddenGame.getActionRemain()));
                }
                isSandBag = false;
                shoreUpState--;
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Invalid Action");
            alert.setHeaderText(null);
            alert.setContentText("The selected island is not adjacent.");
            alert.showAndWait();
        }
    }

    @FXML
    void mouseClickedPlace3(MouseEvent event) {
        ImageView pawnTile = getPawnTileByGameState();
        if (isTileAdjacent(places[3], pawnTile)) {
            Player player = ForbiddenGame.getInstance().getCurPlayer();
            if (player.getType() == PlayerType.ENGINEER && (useEngineerSkill == 1 || useEngineerSkill == 2)) {
                shoreUp(place3);
                useEngineerSkill--;
                return;
            }
            if (shoreUpState == 1) {
                shoreUp(place3);
                if (!isSandBag) {
                    ForbiddenGame.getInstance().useAction();
                    setActionText(String.valueOf(ForbiddenGame.getActionRemain()));
                }
                isSandBag = false;
                shoreUpState--;
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Invalid Action");
            alert.setHeaderText(null);
            alert.setContentText("The selected island is not adjacent.");
            alert.showAndWait();
        }
    }

    @FXML
    void mouseClickedPlace4(MouseEvent event) {
        ImageView pawnTile = getPawnTileByGameState();
        if (isTileAdjacent(places[4], pawnTile)) {
            Player player = ForbiddenGame.getInstance().getCurPlayer();
            if (player.getType() == PlayerType.ENGINEER && (useEngineerSkill == 1 || useEngineerSkill == 2)) {
                shoreUp(place4);
                useEngineerSkill--;
                return;
            }
            if (shoreUpState == 1) {
                shoreUp(place4);
                if (!isSandBag) {
                    ForbiddenGame.getInstance().useAction();
                    setActionText(String.valueOf(ForbiddenGame.getActionRemain()));
                }
                isSandBag = false;
                shoreUpState--;
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Invalid Action");
            alert.setHeaderText(null);
            alert.setContentText("The selected island is not adjacent.");
            alert.showAndWait();
        }
    }

    @FXML
    void mouseClickedPlace5(MouseEvent event) {
        ImageView pawnTile = getPawnTileByGameState();
        if (isTileAdjacent(places[5], pawnTile)) {
            Player player = ForbiddenGame.getInstance().getCurPlayer();
            if (player.getType() == PlayerType.ENGINEER && (useEngineerSkill == 1 || useEngineerSkill == 2)) {
                shoreUp(place5);
                useEngineerSkill--;
                return;
            }
            if (shoreUpState == 1) {
                shoreUp(place5);
                if (!isSandBag) {
                    ForbiddenGame.getInstance().useAction();
                    setActionText(String.valueOf(ForbiddenGame.getActionRemain()));
                }
                isSandBag = false;
                shoreUpState--;
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Invalid Action");
            alert.setHeaderText(null);
            alert.setContentText("The selected island is not adjacent.");
            alert.showAndWait();
        }
    }

    @FXML
    void mouseClickedPlace6(MouseEvent event) {
        ImageView pawnTile = getPawnTileByGameState();
        if (isTileAdjacent(places[6], pawnTile)) {
            Player player = ForbiddenGame.getInstance().getCurPlayer();
            if (player.getType() == PlayerType.ENGINEER && (useEngineerSkill == 1 || useEngineerSkill == 2)) {
                shoreUp(place6);
                useEngineerSkill--;
                return;
            }
            if (shoreUpState == 1) {
                shoreUp(place6);
                if (!isSandBag) {
                    ForbiddenGame.getInstance().useAction();
                    setActionText(String.valueOf(ForbiddenGame.getActionRemain()));
                }
                isSandBag = false;
                shoreUpState--;
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Invalid Action");
            alert.setHeaderText(null);
            alert.setContentText("The selected island is not adjacent.");
            alert.showAndWait();
        }
    }

    @FXML
    void mouseClickedPlace7(MouseEvent event) {
        ImageView pawnTile = getPawnTileByGameState();
        if (isTileAdjacent(places[7], pawnTile)) {
            Player player = ForbiddenGame.getInstance().getCurPlayer();
            if (player.getType() == PlayerType.ENGINEER && (useEngineerSkill == 1 || useEngineerSkill == 2)) {
                shoreUp(place7);
                useEngineerSkill--;
                return;
            }
            if (shoreUpState == 1) {
                shoreUp(place7);
                if (!isSandBag) {
                    ForbiddenGame.getInstance().useAction();
                    setActionText(String.valueOf(ForbiddenGame.getActionRemain()));
                }
                isSandBag = false;
                shoreUpState--;
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Invalid Action");
            alert.setHeaderText(null);
            alert.setContentText("The selected island is not adjacent.");
            alert.showAndWait();
        }
    }

    @FXML
    void mouseClickedPlace8(MouseEvent event) {
        ImageView pawnTile = getPawnTileByGameState();
        if (isTileAdjacent(places[8], pawnTile)) {
            Player player = ForbiddenGame.getInstance().getCurPlayer();
            if (player.getType() == PlayerType.ENGINEER && (useEngineerSkill == 1 || useEngineerSkill == 2)) {
                shoreUp(place8);
                useEngineerSkill--;
                return;
            }
            if (shoreUpState == 1) {
                shoreUp(place8);
                if (!isSandBag) {
                    ForbiddenGame.getInstance().useAction();
                    setActionText(String.valueOf(ForbiddenGame.getActionRemain()));
                }
                isSandBag = false;
                shoreUpState--;
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Invalid Action");
            alert.setHeaderText(null);
            alert.setContentText("The selected island is not adjacent.");
            alert.showAndWait();
        }
    }

    @FXML
    void mouseClickedPlace9(MouseEvent event) {
        ImageView pawnTile = getPawnTileByGameState();
        if (isTileAdjacent(places[9], pawnTile)) {
            Player player = ForbiddenGame.getInstance().getCurPlayer();
            if (player.getType() == PlayerType.ENGINEER && (useEngineerSkill == 1 || useEngineerSkill == 2)) {
                shoreUp(place9);
                useEngineerSkill--;
                return;
            }
            if (shoreUpState == 1) {
                shoreUp(place9);
                if (!isSandBag) {
                    ForbiddenGame.getInstance().useAction();
                    setActionText(String.valueOf(ForbiddenGame.getActionRemain()));
                }
                isSandBag = false;
                shoreUpState--;
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Invalid Action");
            alert.setHeaderText(null);
            alert.setContentText("The selected island is not adjacent.");
            alert.showAndWait();
        }
    }

    @FXML
    void mouseClickedPlace10(MouseEvent event) {
        ImageView pawnTile = getPawnTileByGameState();
        if (isTileAdjacent(places[10], pawnTile)) {
            Player player = ForbiddenGame.getInstance().getCurPlayer();
            if (player.getType() == PlayerType.ENGINEER && (useEngineerSkill == 1 || useEngineerSkill == 2)) {
                shoreUp(place10);
                useEngineerSkill--;
                return;
            }
            if (shoreUpState == 1) {
                shoreUp(place10);
                if (!isSandBag) {
                    ForbiddenGame.getInstance().useAction();
                    setActionText(String.valueOf(ForbiddenGame.getActionRemain()));
                }
                isSandBag = false;
                shoreUpState--;
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Invalid Action");
            alert.setHeaderText(null);
            alert.setContentText("The selected island is not adjacent.");
            alert.showAndWait();
        }
    }

    @FXML
    void mouseClickedPlace11(MouseEvent event) {
        ImageView pawnTile = getPawnTileByGameState();
        if (isTileAdjacent(places[11], pawnTile)) {
            Player player = ForbiddenGame.getInstance().getCurPlayer();
            if (player.getType() == PlayerType.ENGINEER && (useEngineerSkill == 1 || useEngineerSkill == 2)) {
                shoreUp(place11);
                useEngineerSkill--;
                return;
            }
            if (shoreUpState == 1) {
                shoreUp(place11);
                if (!isSandBag) {
                    ForbiddenGame.getInstance().useAction();
                    setActionText(String.valueOf(ForbiddenGame.getActionRemain()));
                }
                isSandBag = false;
                shoreUpState--;
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Invalid Action");
            alert.setHeaderText(null);
            alert.setContentText("The selected island is not adjacent.");
            alert.showAndWait();
        }
    }

    @FXML
    void mouseClickedPlace12(MouseEvent event) {
        ImageView pawnTile = getPawnTileByGameState();
        if (isTileAdjacent(places[12], pawnTile)) {
            Player player = ForbiddenGame.getInstance().getCurPlayer();
            if (player.getType() == PlayerType.ENGINEER && (useEngineerSkill == 1 || useEngineerSkill == 2)) {
                shoreUp(place12);
                useEngineerSkill--;
                return;
            }
            if (shoreUpState == 1) {
                shoreUp(place12);
                if (!isSandBag) {
                    ForbiddenGame.getInstance().useAction();
                    setActionText(String.valueOf(ForbiddenGame.getActionRemain()));
                }
                isSandBag = false;
                shoreUpState--;
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Invalid Action");
            alert.setHeaderText(null);
            alert.setContentText("The selected island is not adjacent.");
            alert.showAndWait();
        }
    }

    @FXML
    void mouseClickedPlace13(MouseEvent event) {
        ImageView pawnTile = getPawnTileByGameState();
        if (isTileAdjacent(places[13], pawnTile)) {
            Player player = ForbiddenGame.getInstance().getCurPlayer();
            if (player.getType() == PlayerType.ENGINEER && (useEngineerSkill == 1 || useEngineerSkill == 2)) {
                shoreUp(place13);
                useEngineerSkill--;
                return;
            }
            if (shoreUpState == 1) {
                shoreUp(place13);
                if (!isSandBag) {
                    ForbiddenGame.getInstance().useAction();
                    setActionText(String.valueOf(ForbiddenGame.getActionRemain()));
                }
                isSandBag = false;
                shoreUpState--;
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Invalid Action");
            alert.setHeaderText(null);
            alert.setContentText("The selected island is not adjacent.");
            alert.showAndWait();
        }
    }

    @FXML
    void mouseClickedPlace14(MouseEvent event) {
        ImageView pawnTile = getPawnTileByGameState();
        if (isTileAdjacent(places[14], pawnTile)) {
            Player player = ForbiddenGame.getInstance().getCurPlayer();
            if (player.getType() == PlayerType.ENGINEER && (useEngineerSkill == 1 || useEngineerSkill == 2)) {
                shoreUp(place14);
                useEngineerSkill--;
                return;
            }
            if (shoreUpState == 1) {
                shoreUp(place14);
                if (!isSandBag) {
                    ForbiddenGame.getInstance().useAction();
                    setActionText(String.valueOf(ForbiddenGame.getActionRemain()));
                }
                isSandBag = false;
                shoreUpState--;
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Invalid Action");
            alert.setHeaderText(null);
            alert.setContentText("The selected island is not adjacent.");
            alert.showAndWait();
        }
    }

    @FXML
    void mouseClickedPlace15(MouseEvent event) {
        ImageView pawnTile = getPawnTileByGameState();
        if (isTileAdjacent(places[15], pawnTile)) {
            Player player = ForbiddenGame.getInstance().getCurPlayer();
            if (player.getType() == PlayerType.ENGINEER && (useEngineerSkill == 1 || useEngineerSkill == 2)) {
                shoreUp(place15);
                useEngineerSkill--;
                return;
            }
            if (shoreUpState == 1) {
                shoreUp(place15);
                if (!isSandBag) {
                    ForbiddenGame.getInstance().useAction();
                    setActionText(String.valueOf(ForbiddenGame.getActionRemain()));
                }
                isSandBag = false;
                shoreUpState--;
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Invalid Action");
            alert.setHeaderText(null);
            alert.setContentText("The selected island is not adjacent.");
            alert.showAndWait();
        }
    }

    @FXML
    void mouseClickedPlace16(MouseEvent event) {
        ImageView pawnTile = getPawnTileByGameState();
        if (isTileAdjacent(places[16], pawnTile)) {
            Player player = ForbiddenGame.getInstance().getCurPlayer();
            if (player.getType() == PlayerType.ENGINEER && (useEngineerSkill == 1 || useEngineerSkill == 2)) {
                shoreUp(place16);
                useEngineerSkill--;
                return;
            }
            if (shoreUpState == 1) {
                shoreUp(place16);
                if (!isSandBag) {
                    ForbiddenGame.getInstance().useAction();
                    setActionText(String.valueOf(ForbiddenGame.getActionRemain()));
                }
                isSandBag = false;
                shoreUpState--;
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Invalid Action");
            alert.setHeaderText(null);
            alert.setContentText("The selected island is not adjacent.");
            alert.showAndWait();
        }
    }

    @FXML
    void mouseClickedPlace17(MouseEvent event) {
        ImageView pawnTile = getPawnTileByGameState();
        if (isTileAdjacent(places[17], pawnTile)) {
            Player player = ForbiddenGame.getInstance().getCurPlayer();
            if (player.getType() == PlayerType.ENGINEER && (useEngineerSkill == 1 || useEngineerSkill == 2)) {
                shoreUp(place17);
                useEngineerSkill--;
                return;
            }
            if (shoreUpState == 1) {
                shoreUp(place17);
                if (!isSandBag) {
                    ForbiddenGame.getInstance().useAction();
                    setActionText(String.valueOf(ForbiddenGame.getActionRemain()));
                }
                isSandBag = false;
                shoreUpState--;
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Invalid Action");
            alert.setHeaderText(null);
            alert.setContentText("The selected island is not adjacent.");
            alert.showAndWait();
        }
    }

    @FXML
    void mouseClickedPlace18(MouseEvent event) {
        ImageView pawnTile = getPawnTileByGameState();
        if (isTileAdjacent(places[18], pawnTile)) {
            Player player = ForbiddenGame.getInstance().getCurPlayer();
            if (player.getType() == PlayerType.ENGINEER && (useEngineerSkill == 1 || useEngineerSkill == 2)) {
                shoreUp(place18);
                useEngineerSkill--;
                return;
            }
            if (shoreUpState == 1) {
                shoreUp(place18);
                if (!isSandBag) {
                    ForbiddenGame.getInstance().useAction();
                    setActionText(String.valueOf(ForbiddenGame.getActionRemain()));
                }
                isSandBag = false;
                shoreUpState--;
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Invalid Action");
            alert.setHeaderText(null);
            alert.setContentText("The selected island is not adjacent.");
            alert.showAndWait();
        }
    }

    @FXML
    void mouseClickedPlace19(MouseEvent event) {
        ImageView pawnTile = getPawnTileByGameState();
        if (isTileAdjacent(places[19], pawnTile)) {
            Player player = ForbiddenGame.getInstance().getCurPlayer();
            if (player.getType() == PlayerType.ENGINEER && (useEngineerSkill == 1 || useEngineerSkill == 2)) {
                shoreUp(place19);
                useEngineerSkill--;
                return;
            }
            if (shoreUpState == 1) {
                shoreUp(place19);
                if (!isSandBag) {
                    ForbiddenGame.getInstance().useAction();
                    setActionText(String.valueOf(ForbiddenGame.getActionRemain()));
                }
                isSandBag = false;
                shoreUpState--;
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Invalid Action");
            alert.setHeaderText(null);
            alert.setContentText("The selected island is not adjacent.");
            alert.showAndWait();
        }
    }

    @FXML
    void mouseClickedPlace20(MouseEvent event) {
        ImageView pawnTile = getPawnTileByGameState();
        if (isTileAdjacent(places[20], pawnTile)) {
            Player player = ForbiddenGame.getInstance().getCurPlayer();
            if (player.getType() == PlayerType.ENGINEER && (useEngineerSkill == 1 || useEngineerSkill == 2)) {
                shoreUp(place20);
                useEngineerSkill--;
                return;
            }
            if (shoreUpState == 1) {
                shoreUp(place20);
                if (!isSandBag) {
                    ForbiddenGame.getInstance().useAction();
                    setActionText(String.valueOf(ForbiddenGame.getActionRemain()));
                }
                isSandBag = false;
                shoreUpState--;
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Invalid Action");
            alert.setHeaderText(null);
            alert.setContentText("The selected island is not adjacent.");
            alert.showAndWait();
        }
    }

    @FXML
    void mouseClickedPlace21(MouseEvent event) {
        ImageView pawnTile = getPawnTileByGameState();
        if (isTileAdjacent(places[21], pawnTile)) {
            Player player = ForbiddenGame.getInstance().getCurPlayer();
            if (player.getType() == PlayerType.ENGINEER && (useEngineerSkill == 1 || useEngineerSkill == 2)) {
                shoreUp(place21);
                useEngineerSkill--;
                return;
            }
            if (shoreUpState == 1) {
                shoreUp(place21);
                if (!isSandBag) {
                    ForbiddenGame.getInstance().useAction();
                    setActionText(String.valueOf(ForbiddenGame.getActionRemain()));
                }
                isSandBag = false;
                shoreUpState--;
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Invalid Action");
            alert.setHeaderText(null);
            alert.setContentText("The selected island is not adjacent.");
            alert.showAndWait();
        }
    }

    @FXML
    void mouseClickedPlace22(MouseEvent event) {
        ImageView pawnTile = getPawnTileByGameState();
        if (isTileAdjacent(places[22], pawnTile)) {
            Player player = ForbiddenGame.getInstance().getCurPlayer();
            if (player.getType() == PlayerType.ENGINEER && (useEngineerSkill == 1 || useEngineerSkill == 2)) {
                shoreUp(place22);
                useEngineerSkill--;
                return;
            }
            if (shoreUpState == 1) {
                shoreUp(place22);
                if (!isSandBag) {
                    ForbiddenGame.getInstance().useAction();
                    setActionText(String.valueOf(ForbiddenGame.getActionRemain()));
                }
                isSandBag = false;
                shoreUpState--;
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Invalid Action");
            alert.setHeaderText(null);
            alert.setContentText("The selected island is not adjacent.");
            alert.showAndWait();
        }
    }

    @FXML
    void mouseClickedPlace23(MouseEvent event) {
        ImageView pawnTile = getPawnTileByGameState();
        if (isTileAdjacent(places[23], pawnTile)) {
            Player player = ForbiddenGame.getInstance().getCurPlayer();
            if (player.getType() == PlayerType.ENGINEER && (useEngineerSkill == 1 || useEngineerSkill == 2)) {
                shoreUp(place23);
                useEngineerSkill--;
                return;
            }
            if (shoreUpState == 1) {
                shoreUp(place23);
                if (!isSandBag) {
                    ForbiddenGame.getInstance().useAction();
                    setActionText(String.valueOf(ForbiddenGame.getActionRemain()));
                }
                isSandBag = false;
                shoreUpState--;
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Invalid Action");
            alert.setHeaderText(null);
            alert.setContentText("The selected island is not adjacent.");
            alert.showAndWait();
        }
    }

    private boolean alreadyChosePlayer = false;

    @FXML
    void mouseClickedTopLeft(MouseEvent event) {
        alreadyChosePlayer = true;
        if (isMessenger) {
            canGiveState = true;
            ForbiddenGame.getInstance().useAction();
            setActionText(String.valueOf(ForbiddenGame.getActionRemain()));
            givePlayerIndex = 0;
            targetPawn = pawnOne;
            return;
        }
        playerToMoveIndex = 0;
        givePlayerIndex = 0;
        if (canGiveState == true) {
            if (arePlayersOnSameTile(pawnOne, playerPawns[ForbiddenGame.getInstance().getCurPlayerIndex()])) {
                ForbiddenGame.getInstance().useAction();
                setActionText(String.valueOf(ForbiddenGame.getActionRemain()));
                givePlayerIndex = 0;
                targetPawn = pawnOne;
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Invalid Move");
                alert.setHeaderText("Players on Different Islands");
                alert.setContentText("The pawns are not on the same island.");
                alert.showAndWait();
                canGiveState = false;
            }
        }

    }

    @FXML
    void mouseClickedTopRight(MouseEvent event) {
        alreadyChosePlayer = true;
        if (isMessenger) {
            canGiveState = true;
            ForbiddenGame.getInstance().useAction();
            setActionText(String.valueOf(ForbiddenGame.getActionRemain()));
            givePlayerIndex = 1;
            targetPawn = pawnTwo;
            return;
        }
        playerToMoveIndex = 1;
        givePlayerIndex = 1;
        if (canGiveState == true) {
            if (arePlayersOnSameTile(pawnTwo, playerPawns[ForbiddenGame.getInstance().getCurPlayerIndex()])) {
                ForbiddenGame.getInstance().useAction();
                setActionText(String.valueOf(ForbiddenGame.getActionRemain()));
                givePlayerIndex = 1;
                targetPawn = pawnTwo;
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Invalid Move");
                alert.setHeaderText("Players on Different Islands");
                alert.setContentText("The pawns are not on the same island.");
                alert.showAndWait();
                canGiveState = false;
            }
        }
    }

    @FXML
    void mouseClickedDownLeft(MouseEvent event) {
        alreadyChosePlayer = true;
        if (isMessenger) {
            canGiveState = true;
            ForbiddenGame.getInstance().useAction();
            setActionText(String.valueOf(ForbiddenGame.getActionRemain()));
            givePlayerIndex = 2;
            targetPawn = pawnThree;
            return;
        }
        playerToMoveIndex = 2;
        givePlayerIndex = 2;
        if (canGiveState == true) {
            if (arePlayersOnSameTile(pawnThree, playerPawns[ForbiddenGame.getInstance().getCurPlayerIndex()])) {
                ForbiddenGame.getInstance().useAction();
                setActionText(String.valueOf(ForbiddenGame.getActionRemain()));
                givePlayerIndex = 2;
                targetPawn = pawnThree;
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Invalid Move");
                alert.setHeaderText("Players on Different Islands");
                alert.setContentText("The pawns are not on the same island.");
                alert.showAndWait();
                canGiveState = false;
            }
        }
    }

    @FXML
    void mouseClickedDownRight(MouseEvent event) {
        alreadyChosePlayer = true;
        if (isMessenger) {
            canGiveState = true;
            ForbiddenGame.getInstance().useAction();
            setActionText(String.valueOf(ForbiddenGame.getActionRemain()));
            givePlayerIndex = 3;
            targetPawn = pawnOne;
            return;
        }
        playerToMoveIndex = 3;
        givePlayerIndex = 3;
        if (canGiveState == true) {
            if (arePlayersOnSameTile(pawnFour, playerPawns[ForbiddenGame.getInstance().getCurPlayerIndex()])) {
                ForbiddenGame.getInstance().useAction();
                setActionText(String.valueOf(ForbiddenGame.getActionRemain()));
                givePlayerIndex = 3;
                targetPawn = pawnOne;
                isMessenger = false;
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Invalid Move");
                alert.setHeaderText("Players on Different Islands");
                alert.setContentText("The pawns are not on the same island.");
                alert.showAndWait();
                canGiveState = false;
            }
        }
    }

    private ImageView getUnderlyingTile(ImageView pawn) {
        if (pawn == null) return null;

        Bounds pawnBoundsInScene = pawn.localToScene(pawn.getBoundsInLocal());
        double centerX = pawnBoundsInScene.getMinX() + pawnBoundsInScene.getWidth() / 2.0;
        double centerY = pawnBoundsInScene.getMinY() + pawnBoundsInScene.getHeight() / 2.0;

        for (int i = 0; i < 24; i++) {
            ImageView tile = getPlaceByIndex(i);
            if (tile == null) continue;
            Bounds tileBoundsInScene = tile.localToScene(tile.getBoundsInLocal());
            if (tileBoundsInScene.contains(centerX, centerY)) {
                return tile;
            }
        }
        return null;
    }

    private ImageView getPlaceByIndex(int idx) {
        return switch (idx) {
            case 0  -> place0;
            case 1  -> place1;
            case 2  -> place2;
            case 3  -> place3;
            case 4  -> place4;
            case 5  -> place5;
            case 6  -> place6;
            case 7  -> place7;
            case 8  -> place8;
            case 9  -> place9;
            case 10 -> place10;
            case 11 -> place11;
            case 12 -> place12;
            case 13 -> place13;
            case 14 -> place14;
            case 15 -> place15;
            case 16 -> place16;
            case 17 -> place17;
            case 18 -> place18;
            case 19 -> place19;
            case 20 -> place20;
            case 21 -> place21;
            case 22 -> place22;
            case 23 -> place23;
            default -> null;
        };
    }


    public boolean arePlayersOnSameTile(ImageView pawnA, ImageView pawnB) {
        ImageView tileA = getUnderlyingTile(pawnA);
        ImageView tileB = getUnderlyingTile(pawnB);
        return tileA != null && tileA == tileB;
    }

    private ImageView targetPawn;

    private boolean isSandbagsCard(ImageView imageView) {
        if (imageView == null) return false;
        Image img = imageView.getImage();
        if (img == null) return false;

        Image reference = new Image(
                Objects.requireNonNull(
                        getClass().getResourceAsStream("/team/group/myforbidden/image/treasurecard/sandbags.png")
                )
        );

        if (img.getWidth() != reference.getWidth() || img.getHeight() != reference.getHeight()) {
            return false;
        }

        javafx.scene.image.PixelReader pr1 = img.getPixelReader();
        javafx.scene.image.PixelReader pr2 = reference.getPixelReader();
        if (pr1 == null || pr2 == null) return false;

        int x = (int) (img.getWidth() / 2);
        int y = (int) (img.getHeight() / 2);
        return pr1.getArgb(x, y) == pr2.getArgb(x, y);
    }


    private void useSandBag() {
        // 退出“给牌”流程
        if (canGiveState) {
            canGiveState = false;
        }

        // 标记：下一次点击要 shore up
        isSandBag = true;
        shoreUpState = 1;

        // —— 把那张 SANDBAG 卡从当前玩家手牌里摘掉，丢到弃牌堆 ——
        Player curPlayer = ForbiddenGame.getInstance().getCurPlayer();
        Queue<TreasureCard> hand = curPlayer.getHandCards();
        TreasureCard toRemove = null;
        for (TreasureCard card : hand) {
            if (card.getTreasureCardType() == TreasureCardType.SANDBAG) {
                toRemove = card;
                break;
            }
        }
        if (toRemove != null) {
            hand.remove(toRemove);
            ForbiddenGame.getInstance().getUsedTreasureCard().add(toRemove);
            curPlayer.setHandCards(hand);
            drawPlayerHands();
        } else {
            System.err.println("useSandBag(): 找不到对应的 TreasureCard");
        }

        // 弹窗让玩家点击想要 shore up 的岛屿
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Shore Up Instruction");
        alert.setHeaderText(null);
        alert.setContentText("Please click the island you want to shore up.");
        alert.getButtonTypes().setAll(new ButtonType("OK", ButtonBar.ButtonData.OK_DONE));
        alert.showAndWait();
    }


    @FXML
    void mouseClicked11(MouseEvent event) {
        if (isSandbagsCard(card11)) {
            System.out.println("SandbagsCard Clicked");
            useSandBag();
            return;
        }
        if (!alreadyChosePlayer) return;
        if (!canGiveState) return;
        Player targetPlayer = ForbiddenGame.players[givePlayerIndex];
        targetPlayer.giveCard(playerOneTreasureCard.poll());
        ForbiddenGame.players[0].setHandCards(playerOneTreasureCard);
        setPlayerHands();
        drawPlayerHands();
        canGiveState = false;
    }

    @FXML
    void mouseClicked12(MouseEvent event) {
        if (isSandbagsCard(card12)) {
            System.out.println("SandbagsCard Clicked");
            useSandBag();
            return;
        }
        if (!alreadyChosePlayer) return;
        if (!canGiveState) return;
        Player targetPlayer = ForbiddenGame.players[givePlayerIndex];
        TreasureCard treasureCard = playerOneTreasureCard.poll();
        playerOneTreasureCard.offer(treasureCard);
        targetPlayer.giveCard(playerOneTreasureCard.poll());
        ForbiddenGame.players[0].setHandCards(playerOneTreasureCard);
        setPlayerHands();
        drawPlayerHands();
        canGiveState = false;
    }

    @FXML
    void mouseClicked13(MouseEvent event) {
        if (isSandbagsCard(card13)) {
            System.out.println("SandbagsCard Clicked");
            useSandBag();
            return;
        }
        if (!alreadyChosePlayer) return;
        if (!canGiveState) return;
        Player targetPlayer = ForbiddenGame.players[givePlayerIndex];
        for (int i = 0; i < 2; i++) {
            TreasureCard treasureCard = playerOneTreasureCard.poll();
            playerOneTreasureCard.offer(treasureCard);
        }
        targetPlayer.giveCard(playerOneTreasureCard.poll());
        ForbiddenGame.players[0].setHandCards(playerOneTreasureCard);
        setPlayerHands();
        drawPlayerHands();
        canGiveState = false;
    }

    @FXML
    void mouseClicked14(MouseEvent event) {
        if (isSandbagsCard(card14)) {
            System.out.println("SandbagsCard Clicked");
            useSandBag();
            return;
        }
        if (!alreadyChosePlayer) return;
        if (!canGiveState) return;
        Player targetPlayer = ForbiddenGame.players[givePlayerIndex];
        for (int i = 0; i < 3; i++) {
            TreasureCard treasureCard = playerOneTreasureCard.poll();
            playerOneTreasureCard.offer(treasureCard);
        }
        targetPlayer.giveCard(playerOneTreasureCard.poll());
        ForbiddenGame.players[0].setHandCards(playerOneTreasureCard);
        setPlayerHands();
        drawPlayerHands();
        canGiveState = false;
    }

    @FXML
    void mouseClicked15(MouseEvent event) {
        if (isSandbagsCard(card15)) {
            System.out.println("SandbagsCard Clicked");
            useSandBag();
            return;
        }
        if (!alreadyChosePlayer) return;
        if (!canGiveState) return;
        Player targetPlayer = ForbiddenGame.players[givePlayerIndex];
        for (int i = 0; i < 4; i++) {
            TreasureCard treasureCard = playerOneTreasureCard.poll();
            playerOneTreasureCard.offer(treasureCard);
        }
        targetPlayer.giveCard(playerOneTreasureCard.poll());
        ForbiddenGame.players[0].setHandCards(playerOneTreasureCard);
        setPlayerHands();
        drawPlayerHands();
        canGiveState = false;
    }

    @FXML
    void mouseClicked21(MouseEvent event) {
        if (isSandbagsCard(card21)) {
            System.out.println("SandbagsCard Clicked");
            useSandBag();
            return;
        }
        if (!alreadyChosePlayer) return;
        if (!canGiveState) return;
        Player targetPlayer = ForbiddenGame.players[givePlayerIndex];
        targetPlayer.giveCard(playerTwoTreasureCard.poll());
        ForbiddenGame.players[1].setHandCards(playerTwoTreasureCard);
        setPlayerHands();
        drawPlayerHands();
        canGiveState = false;
    }

    @FXML
    void mouseClicked22(MouseEvent event) {
        if (isSandbagsCard(card22)) {
            System.out.println("SandbagsCard Clicked");
            useSandBag();
            return;
        }
        if (!alreadyChosePlayer) return;
        if (!canGiveState) return;
        Player targetPlayer = ForbiddenGame.players[givePlayerIndex];
        TreasureCard treasureCard = playerTwoTreasureCard.poll();
        playerTwoTreasureCard.offer(treasureCard);
        targetPlayer.giveCard(playerTwoTreasureCard.poll());
        ForbiddenGame.players[1].setHandCards(playerTwoTreasureCard);
        setPlayerHands();
        drawPlayerHands();
        canGiveState = false;
    }

    @FXML
    void mouseClicked23(MouseEvent event) {
        if (isSandbagsCard(card23)) {
            System.out.println("SandbagsCard Clicked");
            useSandBag();
            return;
        }
        if (!alreadyChosePlayer) return;
        if (!canGiveState) return;
        Player targetPlayer = ForbiddenGame.players[givePlayerIndex];
        for (int i = 0; i < 2; i++) {
            TreasureCard treasureCard = playerTwoTreasureCard.poll();
            playerTwoTreasureCard.offer(treasureCard);
        }
        targetPlayer.giveCard(playerTwoTreasureCard.poll());
        ForbiddenGame.players[1].setHandCards(playerTwoTreasureCard);
        setPlayerHands();
        drawPlayerHands();
        canGiveState = false;
    }

    @FXML
    void mouseClicked24(MouseEvent event) {
        if (isSandbagsCard(card24)) {
            System.out.println("SandbagsCard Clicked");
            useSandBag();
            return;
        }
        if (!alreadyChosePlayer) return;
        if (!canGiveState) return;
        Player targetPlayer = ForbiddenGame.players[givePlayerIndex];
        for (int i = 0; i < 3; i++) {
            TreasureCard treasureCard = playerTwoTreasureCard.poll();
            playerTwoTreasureCard.offer(treasureCard);
        }
        targetPlayer.giveCard(playerTwoTreasureCard.poll());
        ForbiddenGame.players[1].setHandCards(playerTwoTreasureCard);
        setPlayerHands();
        drawPlayerHands();
        canGiveState = false;
    }

    @FXML
    void mouseClicked25(MouseEvent event) {
        if (isSandbagsCard(card25)) {
            System.out.println("SandbagsCard Clicked");
            useSandBag();
            return;
        }
        if (!alreadyChosePlayer) return;
        if (!canGiveState) return;
        Player targetPlayer = ForbiddenGame.players[givePlayerIndex];
        for (int i = 0; i < 4; i++) {
            TreasureCard treasureCard = playerTwoTreasureCard.poll();
            playerTwoTreasureCard.offer(treasureCard);
        }
        targetPlayer.giveCard(playerTwoTreasureCard.poll());
        ForbiddenGame.players[1].setHandCards(playerTwoTreasureCard);
        setPlayerHands();
        drawPlayerHands();
        canGiveState = false;
    }

    @FXML
    void mouseClicked31(MouseEvent event) {
        if (isSandbagsCard(card31)) {
            System.out.println("SandbagsCard Clicked");
            useSandBag();
            return;
        }
        if (!alreadyChosePlayer) return;
        if (!canGiveState) return;
        Player targetPlayer = ForbiddenGame.players[givePlayerIndex];
        targetPlayer.giveCard(playerThreeTreasureCard.poll());
        ForbiddenGame.players[2].setHandCards(playerThreeTreasureCard);
        setPlayerHands();
        drawPlayerHands();
        canGiveState = false;
    }

    @FXML
    void mouseClicked32(MouseEvent event) {
        if (isSandbagsCard(card32)) {
            System.out.println("SandbagsCard Clicked");
            useSandBag();
            return;
        }
        if (!alreadyChosePlayer) return;
        if (!canGiveState) return;
        Player targetPlayer = ForbiddenGame.players[givePlayerIndex];
        TreasureCard treasureCard = playerThreeTreasureCard.poll();
        playerThreeTreasureCard.offer(treasureCard);
        targetPlayer.giveCard(playerThreeTreasureCard.poll());
        ForbiddenGame.players[2].setHandCards(playerThreeTreasureCard);
        setPlayerHands();
        drawPlayerHands();
        canGiveState = false;
    }

    @FXML
    void mouseClicked33(MouseEvent event) {
        if (isSandbagsCard(card33)) {
            System.out.println("SandbagsCard Clicked");
            useSandBag();
            return;
        }
        if (!alreadyChosePlayer) return;
        if (!canGiveState) return;
        Player targetPlayer = ForbiddenGame.players[givePlayerIndex];
        for (int i = 0; i < 2; i++) {
            TreasureCard treasureCard = playerThreeTreasureCard.poll();
            playerThreeTreasureCard.offer(treasureCard);
        }
        targetPlayer.giveCard(playerThreeTreasureCard.poll());
        ForbiddenGame.players[2].setHandCards(playerThreeTreasureCard);
        setPlayerHands();
        drawPlayerHands();
        canGiveState = false;
    }

    @FXML
    void mouseClicked34(MouseEvent event) {
        if (isSandbagsCard(card34)) {
            System.out.println("SandbagsCard Clicked");
            useSandBag();
            return;
        }
        if (!alreadyChosePlayer) return;
        if (!canGiveState) return;
        Player targetPlayer = ForbiddenGame.players[givePlayerIndex];
        for (int i = 0; i < 3; i++) {
            TreasureCard treasureCard = playerThreeTreasureCard.poll();
            playerThreeTreasureCard.offer(treasureCard);
        }
        targetPlayer.giveCard(playerThreeTreasureCard.poll());
        ForbiddenGame.players[2].setHandCards(playerThreeTreasureCard);
        setPlayerHands();
        drawPlayerHands();
        canGiveState = false;
    }

    @FXML
    void mouseClicked35(MouseEvent event) {
        if (isSandbagsCard(card35)) {
            System.out.println("SandbagsCard Clicked");
            useSandBag();
            return;
        }
        if (!alreadyChosePlayer) return;
        if (!canGiveState) return;
        Player targetPlayer = ForbiddenGame.players[givePlayerIndex];
        for (int i = 0; i < 4; i++) {
            TreasureCard treasureCard = playerThreeTreasureCard.poll();
            playerThreeTreasureCard.offer(treasureCard);
        }
        targetPlayer.giveCard(playerThreeTreasureCard.poll());
        ForbiddenGame.players[2].setHandCards(playerThreeTreasureCard);
        setPlayerHands();
        drawPlayerHands();
        canGiveState = false;
    }

    @FXML
    void mouseClicked41(MouseEvent event) {
        if (isSandbagsCard(card41)) {
            System.out.println("SandbagsCard Clicked");
            useSandBag();
            return;
        }
        if (!alreadyChosePlayer) return;
        if (!canGiveState) return;
        Player targetPlayer = ForbiddenGame.players[givePlayerIndex];
        targetPlayer.giveCard(playerFourTreasureCard.poll());
        ForbiddenGame.players[3].setHandCards(playerFourTreasureCard);
        setPlayerHands();
        drawPlayerHands();
        canGiveState = false;
    }

    @FXML
    void mouseClicked42(MouseEvent event) {
        if (isSandbagsCard(card42)) {
            System.out.println("SandbagsCard Clicked");
            useSandBag();
            return;
        }
        if (!alreadyChosePlayer) return;
        if (!canGiveState) return;
        Player targetPlayer = ForbiddenGame.players[givePlayerIndex];
        TreasureCard treasureCard = playerFourTreasureCard.poll();
        playerFourTreasureCard.offer(treasureCard);
        targetPlayer.giveCard(playerFourTreasureCard.poll());
        ForbiddenGame.players[3].setHandCards(playerFourTreasureCard);
        setPlayerHands();
        drawPlayerHands();
        canGiveState = false;
    }

    @FXML
    void mouseClicked43(MouseEvent event) {
        if (isSandbagsCard(card43)) {
            System.out.println("SandbagsCard Clicked");
            useSandBag();
            return;
        }
        if (!alreadyChosePlayer) return;
        if (!canGiveState) return;
        Player targetPlayer = ForbiddenGame.players[givePlayerIndex];
        for (int i = 0; i < 2; i++) {
            TreasureCard treasureCard = playerFourTreasureCard.poll();
            playerFourTreasureCard.offer(treasureCard);
        }
        targetPlayer.giveCard(playerFourTreasureCard.poll());
        ForbiddenGame.players[3].setHandCards(playerFourTreasureCard);
        setPlayerHands();
        drawPlayerHands();
        canGiveState = false;
    }

    @FXML
    void mouseClicked44(MouseEvent event) {
        if (isSandbagsCard(card44)) {
            System.out.println("SandbagsCard Clicked");
            useSandBag();
            return;
        }
        if (!alreadyChosePlayer) return;
        if (!canGiveState) return;
        Player targetPlayer = ForbiddenGame.players[givePlayerIndex];
        for (int i = 0; i < 3; i++) {
            TreasureCard treasureCard = playerFourTreasureCard.poll();
            playerFourTreasureCard.offer(treasureCard);
        }
        targetPlayer.giveCard(playerFourTreasureCard.poll());
        ForbiddenGame.players[3].setHandCards(playerFourTreasureCard);
        setPlayerHands();
        drawPlayerHands();
        canGiveState = false;
    }

    @FXML
    void mouseClicked45(MouseEvent event) {
        if (isSandbagsCard(card45)) {
            System.out.println("SandbagsCard Clicked");
            useSandBag();
            return;
        }
        if (!alreadyChosePlayer) return;
        if (!canGiveState) return;
        Player targetPlayer = ForbiddenGame.players[givePlayerIndex];
        for (int i = 0; i < 4; i++) {
            TreasureCard treasureCard = playerFourTreasureCard.poll();
            playerFourTreasureCard.offer(treasureCard);
        }
        targetPlayer.giveCard(playerFourTreasureCard.poll());
        ForbiddenGame.players[3].setHandCards(playerFourTreasureCard);
        setPlayerHands();
        drawPlayerHands();
        canGiveState = false;
    }

}
