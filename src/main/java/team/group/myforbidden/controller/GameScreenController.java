package team.group.myforbidden.controller;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.effect.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
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
    @FXML private Button useSkill;//页面的fxml组件

    private static ImageView[] places;//记录所有岛屿
    private ImageView[] playerPawns;//玩家棋子
    private int shoreUpState = 0;//加固行动点
    public static int[] placeOfPlayer = new int[6];//玩家位置数组
    private Queue<TreasureCard> playerOneTreasureCard;//
    private Queue<TreasureCard> playerTwoTreasureCard;
    private Queue<TreasureCard> playerThreeTreasureCard;
    private Queue<TreasureCard> playerFourTreasureCard;//分别记录玩家手牌
    private ImageView[] playerOneHandImages;
    private ImageView[] playerTwoHandImages;
    private ImageView[] playerThreeHandImages;
    private ImageView[] playerFourHandImages;//玩家手牌位置的image
    private ArrayList<Integer> floodToDraw;//没用
    private int givePlayerIndex;//被给牌玩家的下标
    private int useEngineerSkill = 0;//工程师技能点
    private int usePilotSkill = 1;//飞行员技能点
    private int playerToMoveIndex = 0;//被导航元移动玩家的下标
    static Map<ImageView, String> imagePathMap = new HashMap<>();//岛屿的资源路径

    public static boolean checkSunk() {//判断是否沉没，宝藏岛屿和直升机岛屿是否沉没导致失败
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
    private void initialize() {//初始化游戏主界面
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

    private void setPlayerHands() {//设置玩家手牌
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

    private void drawPlayerHands() {//画出玩家手牌
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


    private void flood(ImageView imageView) {//洪水覆盖岛屿
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
    }//淹没岛屿

    private void shoreUp(ImageView imageView) {//加固
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


    private void drawFlood() {//画洪水效果
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

    private void drawNextTurnFlood() {//画出下一回合抽到洪水卡的洪水效果
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

    private void drawPlayers() {//画出玩家棋子头像
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
    void mouseClickedCapture(MouseEvent event) {//鼠标点击，把获取宝藏添加到宝藏堆并删除宝藏手牌
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
                }
            }
            System.out.println("You chose: " + selected);
        });
    }


    @FXML
    void mouseClickedGive(MouseEvent event) {//给牌
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
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Give Card Instruction");
        alert.setHeaderText(null);
        alert.setContentText("Please click the avatar of the player you want to give the card to first, then click the card you want to give.");
        alert.showAndWait();
    }

    @FXML
    void mouseClickedMove(MouseEvent event) {//移动
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

        int curPlayerIndex = ForbiddenGame.getInstance().getCurPlayerIndex();
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
            ImageView pawn = playerPawns[curPlayerIndex];

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
    }

    @FXML
    void mouseClickedShoreUp(MouseEvent event) {//加固
        if (ForbiddenGame.getActionRemain() == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Notice");
            alert.setHeaderText(null);
            alert.setContentText("You have no remaining action points!");
            alert.showAndWait();
            return;
        }
        boolean haveSand = false;
        Player player = ForbiddenGame.getInstance().getCurPlayer();
        for (TreasureCard treasureCard : player.getHandCards()) {
            if (treasureCard.getTreasureCardType() == TreasureCardType.SANDBAG) {
                haveSand = true;
            }
        }
        if (!haveSand) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No Sandbags Available");
            alert.setHeaderText(null);
            alert.setContentText("You do not have any Sandbags in your hand.");
            alert.showAndWait();
            return;
        }
        ForbiddenGame.getInstance().useAction();
        setActionText(String.valueOf(ForbiddenGame.getActionRemain()));

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
    void mouseClickedUseSkill(MouseEvent event) {//使用技能，根据玩家角色触发不同技能
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

    private void messengerSkill() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Card Giving Instruction");
        alert.setHeaderText("You may give a card to any player.");
        alert.setContentText("Please click the 'Give' button to proceed.");
        alert.showAndWait();
        ForbiddenGame.getInstance().useAction();
        setActionText(String.valueOf(ForbiddenGame.getActionRemain()));
        Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
        alert2.setTitle("Give Card Instruction");
        alert2.setHeaderText(null);
        alert2.setContentText("Please click the avatar of the player you want to give the card to first, then click the card you want to give.");
        alert2.showAndWait();
    }

    private void explorerSkill() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Hint");
        alert.setHeaderText(null);
        alert.setContentText("You can move or shore up diagonally.");
        alert.showAndWait();
        waitForNextMouseClick(anchorPane, point -> {
            System.out.println("Clicked at: " + point.getX() + ", " + point.getY());
            switch (ForbiddenGame.getInstance().getCurPlayerIndex()) {
                case 0 -> {
                    pawnOne.setLayoutX(point.getX()-10);
                    pawnOne.setLayoutY(point.getY()-20);
                }
                case 1 -> {
                    pawnTwo.setLayoutX(point.getX()-10);
                    pawnTwo.setLayoutY(point.getY()-20);
                }
                case 2 -> {
                    pawnThree.setLayoutX(point.getX()-10);
                    pawnThree.setLayoutY(point.getY()-20);
                }
                case 3 -> {
                    pawnFour.setLayoutX(point.getX()-10);
                    pawnFour.setLayoutY(point.getY()-20);
                }
            }
        });
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


    private void pilotSkill() {
        if (usePilotSkill == 1) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Move Instruction");
            alert.setHeaderText(null);
            alert.setContentText("You can move to any tile on the board.");
            alert.showAndWait();
            waitForNextMouseClick(anchorPane, point -> {
                System.out.println("Clicked at: " + point.getX() + ", " + point.getY());
                switch (ForbiddenGame.getInstance().getCurPlayerIndex()) {
                    case 0 -> {
                        pawnOne.setLayoutX(point.getX()-10);
                        pawnOne.setLayoutY(point.getY()-20);
                    }
                    case 1 -> {
                        pawnTwo.setLayoutX(point.getX()-10);
                        pawnTwo.setLayoutY(point.getY()-20);
                    }
                    case 2 -> {
                        pawnThree.setLayoutX(point.getX()-10);
                        pawnThree.setLayoutY(point.getY()-20);
                    }
                    case 3 -> {
                        pawnFour.setLayoutX(point.getX()-10);
                        pawnFour.setLayoutY(point.getY()-20);
                    }
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

    private void engineerSkill() {
        useEngineerSkill = 2;
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Drain Instruction");
        alert.setHeaderText("You may drain two flooded tiles.");
        alert.setContentText("Please click the islands you want to drain.");
        alert.showAndWait();
    }

    private void diverSkill() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Water Path Movement");
        alert.setHeaderText(null);
        alert.setContentText("You can travel through water paths. Please select your destination.");
        alert.showAndWait();
        waitForNextMouseClick(anchorPane, point -> {
            System.out.println("Clicked at: " + point.getX() + ", " + point.getY());
            switch (ForbiddenGame.getInstance().getCurPlayerIndex()) {
                case 0 -> {
                    pawnOne.setLayoutX(point.getX()-10);
                    pawnOne.setLayoutY(point.getY()-20);
                }
                case 1 -> {
                    pawnTwo.setLayoutX(point.getX()-10);
                    pawnTwo.setLayoutY(point.getY()-20);
                }
                case 2 -> {
                    pawnThree.setLayoutX(point.getX()-10);
                    pawnThree.setLayoutY(point.getY()-20);
                }
                case 3 -> {
                    pawnFour.setLayoutX(point.getX()-10);
                    pawnFour.setLayoutY(point.getY()-20);
                }
            }
        });
    }
//八个方法六个技能两个鼠标阻塞方法

    @FXML
    void mouseClickedNextTurn(MouseEvent event) {//进入下一回合
        usePilotSkill = 1;
        ForbiddenGame.getInstance().nextTurn();
        drawPlayerHands();
        drawNextTurnFlood();
        setActionText(String.valueOf(ForbiddenGame.getActionRemain()));
        setLevelText(String.valueOf(ForbiddenGame.getLevel()));
    }

    private void setActionText(String text) {//更新剩余行动点
        if (action != null) {
            action.setText(text);
        }
    }

    private void setLevelText(String text) {//更新等级
        if (level != null) {
            level.setText(text);
        }
    }


    @FXML
    void mouseClickedPlace0(MouseEvent event) {
        Queue<TreasureCard> newCards = new LinkedList<>();
        Player player = ForbiddenGame.getInstance().getCurPlayer();
        if (player.getType() == PlayerType.ENGINEER && (useEngineerSkill == 1 || useEngineerSkill == 2)) {
            shoreUp(place0);
            useEngineerSkill--;
            return;
        }
        for(TreasureCard treasureCard : ForbiddenGame.getInstance().getCurPlayer().getHandCards()) {
            if (treasureCard.getTreasureCardType() == TreasureCardType.SANDBAG) {
                if (shoreUpState == 1) {
                    shoreUp(place0);
                    shoreUpState--;
                }
            } else {
                newCards.add(treasureCard);
            }
        }
        player.setHandCards(newCards);
        setPlayerHands();
        drawPlayerHands();
    }

    @FXML
    void mouseClickedPlace1(MouseEvent event) {
        Queue<TreasureCard> newCards = new LinkedList<>();
        Player player = ForbiddenGame.getInstance().getCurPlayer();
        if (player.getType() == PlayerType.ENGINEER && (useEngineerSkill == 1 || useEngineerSkill == 2)) {
            shoreUp(place1);
            useEngineerSkill--;
            return;
        }
        for(TreasureCard treasureCard : ForbiddenGame.getInstance().getCurPlayer().getHandCards()) {
            if (treasureCard.getTreasureCardType() == TreasureCardType.SANDBAG) {
                if (shoreUpState == 1) {
                    shoreUp(place1);
                    shoreUpState--;
                }
            } else {
                newCards.add(treasureCard);
            }
        }
        player.setHandCards(newCards);
        setPlayerHands();
        drawPlayerHands();
    }

    @FXML
    void mouseClickedPlace2(MouseEvent event) {
        Queue<TreasureCard> newCards = new LinkedList<>();
        Player player = ForbiddenGame.getInstance().getCurPlayer();
        if (player.getType() == PlayerType.ENGINEER && (useEngineerSkill == 1 || useEngineerSkill == 2)) {
            shoreUp(place2);
            useEngineerSkill--;
            return;
        }
        for(TreasureCard treasureCard : ForbiddenGame.getInstance().getCurPlayer().getHandCards()) {
            if (treasureCard.getTreasureCardType() == TreasureCardType.SANDBAG) {
                if (shoreUpState == 1) {
                    shoreUp(place2);
                    shoreUpState--;
                }
            } else {
                newCards.add(treasureCard);
            }
        }
        player.setHandCards(newCards);
        setPlayerHands();
        drawPlayerHands();
    }

    @FXML
    void mouseClickedPlace3(MouseEvent event) {
        Queue<TreasureCard> newCards = new LinkedList<>();
        Player player = ForbiddenGame.getInstance().getCurPlayer();
        if (player.getType() == PlayerType.ENGINEER && (useEngineerSkill == 1 || useEngineerSkill == 2)) {
            shoreUp(place3);
            useEngineerSkill--;
            return;
        }
        for(TreasureCard treasureCard : ForbiddenGame.getInstance().getCurPlayer().getHandCards()) {
            if (treasureCard.getTreasureCardType() == TreasureCardType.SANDBAG) {
                if (shoreUpState == 1) {
                    shoreUp(place3);
                    shoreUpState--;
                }
            } else {
                newCards.add(treasureCard);
            }
        }
        player.setHandCards(newCards);
        setPlayerHands();
        drawPlayerHands();
    }

    @FXML
    void mouseClickedPlace4(MouseEvent event) {
        Queue<TreasureCard> newCards = new LinkedList<>();
        Player player = ForbiddenGame.getInstance().getCurPlayer();
        if (player.getType() == PlayerType.ENGINEER && (useEngineerSkill == 1 || useEngineerSkill == 2)) {
            shoreUp(place4);
            useEngineerSkill--;
            return;
        }
        for(TreasureCard treasureCard : ForbiddenGame.getInstance().getCurPlayer().getHandCards()) {
            if (treasureCard.getTreasureCardType() == TreasureCardType.SANDBAG) {
                if (shoreUpState == 1) {
                    shoreUp(place4);
                    shoreUpState--;
                }
            } else {
                newCards.add(treasureCard);
            }
        }
        player.setHandCards(newCards);
        setPlayerHands();
        drawPlayerHands();
    }

    @FXML
    void mouseClickedPlace5(MouseEvent event) {
        Queue<TreasureCard> newCards = new LinkedList<>();
        Player player = ForbiddenGame.getInstance().getCurPlayer();
        if (player.getType() == PlayerType.ENGINEER && (useEngineerSkill == 1 || useEngineerSkill == 2)) {
            shoreUp(place5);
            useEngineerSkill--;
            return;
        }
        for(TreasureCard treasureCard : ForbiddenGame.getInstance().getCurPlayer().getHandCards()) {
            if (treasureCard.getTreasureCardType() == TreasureCardType.SANDBAG) {
                if (shoreUpState == 1) {
                    shoreUp(place5);
                    shoreUpState--;
                }
            } else {
                newCards.add(treasureCard);
            }
        }
        player.setHandCards(newCards);
        setPlayerHands();
        drawPlayerHands();
    }

    @FXML
    void mouseClickedPlace6(MouseEvent event) {
        Queue<TreasureCard> newCards = new LinkedList<>();
        Player player = ForbiddenGame.getInstance().getCurPlayer();
        if (player.getType() == PlayerType.ENGINEER && (useEngineerSkill == 1 || useEngineerSkill == 2)) {
            shoreUp(place6);
            useEngineerSkill--;
            return;
        }
        for(TreasureCard treasureCard : ForbiddenGame.getInstance().getCurPlayer().getHandCards()) {
            if (treasureCard.getTreasureCardType() == TreasureCardType.SANDBAG) {
                if (shoreUpState == 1) {
                    shoreUp(place6);
                    shoreUpState--;
                }
            } else {
                newCards.add(treasureCard);
            }
        }
        player.setHandCards(newCards);
        setPlayerHands();
        drawPlayerHands();
    }

    @FXML
    void mouseClickedPlace7(MouseEvent event) {
        Queue<TreasureCard> newCards = new LinkedList<>();
        Player player = ForbiddenGame.getInstance().getCurPlayer();
        if (player.getType() == PlayerType.ENGINEER && (useEngineerSkill == 1 || useEngineerSkill == 2)) {
            shoreUp(place7);
            useEngineerSkill--;
            return;
        }
        for(TreasureCard treasureCard : ForbiddenGame.getInstance().getCurPlayer().getHandCards()) {
            if (treasureCard.getTreasureCardType() == TreasureCardType.SANDBAG) {
                if (shoreUpState == 1) {
                    shoreUp(place7);
                    shoreUpState--;
                }
            } else {
                newCards.add(treasureCard);
            }
        }
        player.setHandCards(newCards);
        setPlayerHands();
        drawPlayerHands();
    }

    @FXML
    void mouseClickedPlace8(MouseEvent event) {
        Queue<TreasureCard> newCards = new LinkedList<>();
        Player player = ForbiddenGame.getInstance().getCurPlayer();
        if (player.getType() == PlayerType.ENGINEER && (useEngineerSkill == 1 || useEngineerSkill == 2)) {
            shoreUp(place8);
            useEngineerSkill--;
            return;
        }
        for(TreasureCard treasureCard : ForbiddenGame.getInstance().getCurPlayer().getHandCards()) {
            if (treasureCard.getTreasureCardType() == TreasureCardType.SANDBAG) {
                if (shoreUpState == 1) {
                    shoreUp(place8);
                    shoreUpState--;
                }
            } else {
                newCards.add(treasureCard);
            }
        }
        player.setHandCards(newCards);
        setPlayerHands();
        drawPlayerHands();
    }

    @FXML
    void mouseClickedPlace9(MouseEvent event) {
        Queue<TreasureCard> newCards = new LinkedList<>();
        Player player = ForbiddenGame.getInstance().getCurPlayer();
        if (player.getType() == PlayerType.ENGINEER && (useEngineerSkill == 1 || useEngineerSkill == 2)) {
            shoreUp(place9);
            useEngineerSkill--;
            return;
        }
        for(TreasureCard treasureCard : ForbiddenGame.getInstance().getCurPlayer().getHandCards()) {
            if (treasureCard.getTreasureCardType() == TreasureCardType.SANDBAG) {
                if (shoreUpState == 1) {
                    shoreUp(place9);
                    shoreUpState--;
                }
            } else {
                newCards.add(treasureCard);
            }
        }
        player.setHandCards(newCards);
        setPlayerHands();
        drawPlayerHands();
    }

    @FXML
    void mouseClickedPlace10(MouseEvent event) {
        Queue<TreasureCard> newCards = new LinkedList<>();
        Player player = ForbiddenGame.getInstance().getCurPlayer();
        if (player.getType() == PlayerType.ENGINEER && (useEngineerSkill == 1 || useEngineerSkill == 2)) {
            shoreUp(place10);
            useEngineerSkill--;
            return;
        }
        for(TreasureCard treasureCard : ForbiddenGame.getInstance().getCurPlayer().getHandCards()) {
            if (treasureCard.getTreasureCardType() == TreasureCardType.SANDBAG) {
                if (shoreUpState == 1) {
                    shoreUp(place10);
                    shoreUpState--;
                }
            } else {
                newCards.add(treasureCard);
            }
        }
        player.setHandCards(newCards);
        setPlayerHands();
        drawPlayerHands();
    }

    @FXML
    void mouseClickedPlace11(MouseEvent event) {
        Queue<TreasureCard> newCards = new LinkedList<>();
        Player player = ForbiddenGame.getInstance().getCurPlayer();
        if (player.getType() == PlayerType.ENGINEER && (useEngineerSkill == 1 || useEngineerSkill == 2)) {
            shoreUp(place11);
            useEngineerSkill--;
            return;
        }
        for(TreasureCard treasureCard : ForbiddenGame.getInstance().getCurPlayer().getHandCards()) {
            if (treasureCard.getTreasureCardType() == TreasureCardType.SANDBAG) {
                if (shoreUpState == 1) {
                    shoreUp(place11);
                    shoreUpState--;
                }
            } else {
                newCards.add(treasureCard);
            }
        }
        player.setHandCards(newCards);
        setPlayerHands();
        drawPlayerHands();
    }

    @FXML
    void mouseClickedPlace12(MouseEvent event) {
        Queue<TreasureCard> newCards = new LinkedList<>();
        Player player = ForbiddenGame.getInstance().getCurPlayer();
        if (player.getType() == PlayerType.ENGINEER && (useEngineerSkill == 1 || useEngineerSkill == 2)) {
            shoreUp(place12);
            useEngineerSkill--;
            return;
        }
        for(TreasureCard treasureCard : ForbiddenGame.getInstance().getCurPlayer().getHandCards()) {
            if (treasureCard.getTreasureCardType() == TreasureCardType.SANDBAG) {
                if (shoreUpState == 1) {
                    shoreUp(place12);
                    shoreUpState--;
                }
            } else {
                newCards.add(treasureCard);
            }
        }
        player.setHandCards(newCards);
        setPlayerHands();
        drawPlayerHands();
    }

    @FXML
    void mouseClickedPlace13(MouseEvent event) {
        Queue<TreasureCard> newCards = new LinkedList<>();
        Player player = ForbiddenGame.getInstance().getCurPlayer();
        if (player.getType() == PlayerType.ENGINEER && (useEngineerSkill == 1 || useEngineerSkill == 2)) {
            shoreUp(place13);
            useEngineerSkill--;
            return;
        }
        for(TreasureCard treasureCard : ForbiddenGame.getInstance().getCurPlayer().getHandCards()) {
            if (treasureCard.getTreasureCardType() == TreasureCardType.SANDBAG) {
                if (shoreUpState == 1) {
                    shoreUp(place13);
                    shoreUpState--;
                }
            } else {
                newCards.add(treasureCard);
            }
        }
        player.setHandCards(newCards);
        setPlayerHands();
        drawPlayerHands();
    }

    @FXML
    void mouseClickedPlace14(MouseEvent event) {
        Queue<TreasureCard> newCards = new LinkedList<>();
        Player player = ForbiddenGame.getInstance().getCurPlayer();
        if (player.getType() == PlayerType.ENGINEER && (useEngineerSkill == 1 || useEngineerSkill == 2)) {
            shoreUp(place14);
            useEngineerSkill--;
            return;
        }
        for(TreasureCard treasureCard : ForbiddenGame.getInstance().getCurPlayer().getHandCards()) {
            if (treasureCard.getTreasureCardType() == TreasureCardType.SANDBAG) {
                if (shoreUpState == 1) {
                    shoreUp(place14);
                    shoreUpState--;
                }
            } else {
                newCards.add(treasureCard);
            }
        }
        player.setHandCards(newCards);
        setPlayerHands();
        drawPlayerHands();
    }

    @FXML
    void mouseClickedPlace15(MouseEvent event) {
        Queue<TreasureCard> newCards = new LinkedList<>();
        Player player = ForbiddenGame.getInstance().getCurPlayer();
        if (player.getType() == PlayerType.ENGINEER && (useEngineerSkill == 1 || useEngineerSkill == 2)) {
            shoreUp(place15);
            useEngineerSkill--;
            return;
        }
        for(TreasureCard treasureCard : ForbiddenGame.getInstance().getCurPlayer().getHandCards()) {
            if (treasureCard.getTreasureCardType() == TreasureCardType.SANDBAG) {
                if (shoreUpState == 1) {
                    shoreUp(place15);
                    shoreUpState--;
                }
            } else {
                newCards.add(treasureCard);
            }
        }
        player.setHandCards(newCards);
        setPlayerHands();
        drawPlayerHands();
    }

    @FXML
    void mouseClickedPlace16(MouseEvent event) {
        Queue<TreasureCard> newCards = new LinkedList<>();
        Player player = ForbiddenGame.getInstance().getCurPlayer();
        if (player.getType() == PlayerType.ENGINEER && (useEngineerSkill == 1 || useEngineerSkill == 2)) {
            shoreUp(place16);
            useEngineerSkill--;
            return;
        }
        for(TreasureCard treasureCard : ForbiddenGame.getInstance().getCurPlayer().getHandCards()) {
            if (treasureCard.getTreasureCardType() == TreasureCardType.SANDBAG) {
                if (shoreUpState == 1) {
                    shoreUp(place16);
                    shoreUpState--;
                }
            } else {
                newCards.add(treasureCard);
            }
        }
        player.setHandCards(newCards);
        setPlayerHands();
        drawPlayerHands();
    }

    @FXML
    void mouseClickedPlace17(MouseEvent event) {
        Queue<TreasureCard> newCards = new LinkedList<>();
        Player player = ForbiddenGame.getInstance().getCurPlayer();
        if (player.getType() == PlayerType.ENGINEER && (useEngineerSkill == 1 || useEngineerSkill == 2)) {
            shoreUp(place17);
            useEngineerSkill--;
            return;
        }
        for(TreasureCard treasureCard : ForbiddenGame.getInstance().getCurPlayer().getHandCards()) {
            if (treasureCard.getTreasureCardType() == TreasureCardType.SANDBAG) {
                if (shoreUpState == 1) {
                    shoreUp(place17);
                    shoreUpState--;
                }
            } else {
                newCards.add(treasureCard);
            }
        }
        player.setHandCards(newCards);
        setPlayerHands();
        drawPlayerHands();
    }

    @FXML
    void mouseClickedPlace18(MouseEvent event) {
        Queue<TreasureCard> newCards = new LinkedList<>();
        Player player = ForbiddenGame.getInstance().getCurPlayer();
        if (player.getType() == PlayerType.ENGINEER && (useEngineerSkill == 1 || useEngineerSkill == 2)) {
            shoreUp(place18);
            useEngineerSkill--;
            return;
        }
        for(TreasureCard treasureCard : ForbiddenGame.getInstance().getCurPlayer().getHandCards()) {
            if (treasureCard.getTreasureCardType() == TreasureCardType.SANDBAG) {
                if (shoreUpState == 1) {
                    shoreUp(place18);
                    shoreUpState--;
                }
            } else {
                newCards.add(treasureCard);
            }
        }
        player.setHandCards(newCards);
        setPlayerHands();
        drawPlayerHands();
    }

    @FXML
    void mouseClickedPlace19(MouseEvent event) {
        Queue<TreasureCard> newCards = new LinkedList<>();
        Player player = ForbiddenGame.getInstance().getCurPlayer();
        if (player.getType() == PlayerType.ENGINEER && (useEngineerSkill == 1 || useEngineerSkill == 2)) {
            shoreUp(place19);
            useEngineerSkill--;
            return;
        }
        for(TreasureCard treasureCard : ForbiddenGame.getInstance().getCurPlayer().getHandCards()) {
            if (treasureCard.getTreasureCardType() == TreasureCardType.SANDBAG) {
                if (shoreUpState == 1) {
                    shoreUp(place19);
                    shoreUpState--;
                }
            } else {
                newCards.add(treasureCard);
            }
        }
        player.setHandCards(newCards);
        setPlayerHands();
        drawPlayerHands();
    }

    @FXML
    void mouseClickedPlace20(MouseEvent event) {
        Queue<TreasureCard> newCards = new LinkedList<>();
        Player player = ForbiddenGame.getInstance().getCurPlayer();
        if (player.getType() == PlayerType.ENGINEER && (useEngineerSkill == 1 || useEngineerSkill == 2)) {
            shoreUp(place20);
            useEngineerSkill--;
            return;
        }
        for(TreasureCard treasureCard : ForbiddenGame.getInstance().getCurPlayer().getHandCards()) {
            if (treasureCard.getTreasureCardType() == TreasureCardType.SANDBAG) {
                if (shoreUpState == 1) {
                    shoreUp(place20);
                    shoreUpState--;
                }
            } else {
                newCards.add(treasureCard);
            }
        }
        player.setHandCards(newCards);
        setPlayerHands();
        drawPlayerHands();
    }

    @FXML
    void mouseClickedPlace21(MouseEvent event) {
        Queue<TreasureCard> newCards = new LinkedList<>();
        Player player = ForbiddenGame.getInstance().getCurPlayer();
        if (player.getType() == PlayerType.ENGINEER && (useEngineerSkill == 1 || useEngineerSkill == 2)) {
            shoreUp(place21);
            useEngineerSkill--;
            return;
        }
        for(TreasureCard treasureCard : ForbiddenGame.getInstance().getCurPlayer().getHandCards()) {
            if (treasureCard.getTreasureCardType() == TreasureCardType.SANDBAG) {
                if (shoreUpState == 1) {
                    shoreUp(place21);
                    shoreUpState--;
                }
            } else {
                newCards.add(treasureCard);
            }
        }
        player.setHandCards(newCards);
        setPlayerHands();
        drawPlayerHands();
    }

    @FXML
    void mouseClickedPlace22(MouseEvent event) {
        Queue<TreasureCard> newCards = new LinkedList<>();
        Player player = ForbiddenGame.getInstance().getCurPlayer();
        if (player.getType() == PlayerType.ENGINEER && (useEngineerSkill == 1 || useEngineerSkill == 2)) {
            shoreUp(place22);
            useEngineerSkill--;
            return;
        }
        for(TreasureCard treasureCard : ForbiddenGame.getInstance().getCurPlayer().getHandCards()) {
            if (treasureCard.getTreasureCardType() == TreasureCardType.SANDBAG) {
                if (shoreUpState == 1) {
                    shoreUp(place22);
                    shoreUpState--;
                }
            } else {
                newCards.add(treasureCard);
            }
        }
        player.setHandCards(newCards);
        setPlayerHands();
        drawPlayerHands();
    }

    @FXML
    void mouseClickedPlace23(MouseEvent event) {
        Queue<TreasureCard> newCards = new LinkedList<>();
        Player player = ForbiddenGame.getInstance().getCurPlayer();
        if (player.getType() == PlayerType.ENGINEER && (useEngineerSkill == 1 || useEngineerSkill == 2)) {
            shoreUp(place23);
            useEngineerSkill--;
            return;
        }
        for(TreasureCard treasureCard : ForbiddenGame.getInstance().getCurPlayer().getHandCards()) {
            if (treasureCard.getTreasureCardType() == TreasureCardType.SANDBAG) {
                if (shoreUpState == 1) {
                    shoreUp(place23);
                    shoreUpState--;
                    ForbiddenGame.getInstance().getUsedTreasureCard().add( new TreasureCard(TreasureCardType.SANDBAG));
                }
            } else {
                newCards.add(treasureCard);
            }
        }
        player.setHandCards(newCards);
        setPlayerHands();
        drawPlayerHands();
    }//鼠标点击每一个岛屿的操作，对于每个操作会更具手牌中的沙袋卡以及当前玩家角色进行判定，在最后更新玩家手牌以及岛屿

    @FXML
    void mouseClickedTopLeft(MouseEvent event) {
        playerToMoveIndex = 0;
        givePlayerIndex = 0;
    }

    @FXML
    void mouseClickedTopRight(MouseEvent event) {
        playerToMoveIndex = 1;
        givePlayerIndex = 1;
    }

    @FXML
    void mouseClickedDownLeft(MouseEvent event) {
        playerToMoveIndex = 2;
        givePlayerIndex = 2;
    }

    @FXML
    void mouseClickedDownRight(MouseEvent event) {
        playerToMoveIndex = 3;
        givePlayerIndex = 3;
    }//根据点击的玩家头像确定给牌对象以及让谁移动

    @FXML
    void mouseClicked11(MouseEvent event) {
        Player targetPlayer = ForbiddenGame.players[givePlayerIndex];
        targetPlayer.giveCard(playerOneTreasureCard.poll());
        ForbiddenGame.players[0].setHandCards(playerOneTreasureCard);
        setPlayerHands();
        drawPlayerHands();
    }

    @FXML
    void mouseClicked12(MouseEvent event) {
        Player targetPlayer = ForbiddenGame.players[givePlayerIndex];
        TreasureCard treasureCard = playerOneTreasureCard.poll();
        playerOneTreasureCard.offer(treasureCard);
        targetPlayer.giveCard(playerOneTreasureCard.poll());
        ForbiddenGame.players[0].setHandCards(playerOneTreasureCard);
        setPlayerHands();
        drawPlayerHands();
    }

    @FXML
    void mouseClicked13(MouseEvent event) {
        Player targetPlayer = ForbiddenGame.players[givePlayerIndex];
        for (int i = 0; i < 2; i++) {
            TreasureCard treasureCard = playerOneTreasureCard.poll();
            playerOneTreasureCard.offer(treasureCard);
        }
        targetPlayer.giveCard(playerOneTreasureCard.poll());
        ForbiddenGame.players[0].setHandCards(playerOneTreasureCard);
        setPlayerHands();
        drawPlayerHands();
    }

    @FXML
    void mouseClicked14(MouseEvent event) {
        Player targetPlayer = ForbiddenGame.players[givePlayerIndex];
        for (int i = 0; i < 3; i++) {
            TreasureCard treasureCard = playerOneTreasureCard.poll();
            playerOneTreasureCard.offer(treasureCard);
        }
        targetPlayer.giveCard(playerOneTreasureCard.poll());
        ForbiddenGame.players[0].setHandCards(playerOneTreasureCard);
        setPlayerHands();
        drawPlayerHands();
    }

    @FXML
    void mouseClicked15(MouseEvent event) {
        Player targetPlayer = ForbiddenGame.players[givePlayerIndex];
        for (int i = 0; i < 4; i++) {
            TreasureCard treasureCard = playerOneTreasureCard.poll();
            playerOneTreasureCard.offer(treasureCard);
        }
        targetPlayer.giveCard(playerOneTreasureCard.poll());
        ForbiddenGame.players[0].setHandCards(playerOneTreasureCard);
        setPlayerHands();
        drawPlayerHands();
    }

    @FXML
    void mouseClicked21(MouseEvent event) {
        Player targetPlayer = ForbiddenGame.players[givePlayerIndex];
        targetPlayer.giveCard(playerTwoTreasureCard.poll());
        ForbiddenGame.players[1].setHandCards(playerTwoTreasureCard);
        setPlayerHands();
        drawPlayerHands();
    }

    @FXML
    void mouseClicked22(MouseEvent event) {
        Player targetPlayer = ForbiddenGame.players[givePlayerIndex];
        TreasureCard treasureCard = playerTwoTreasureCard.poll();
        playerTwoTreasureCard.offer(treasureCard);
        targetPlayer.giveCard(playerTwoTreasureCard.poll());
        ForbiddenGame.players[1].setHandCards(playerTwoTreasureCard);
        setPlayerHands();
        drawPlayerHands();
    }

    @FXML
    void mouseClicked23(MouseEvent event) {
        Player targetPlayer = ForbiddenGame.players[givePlayerIndex];
        for (int i = 0; i < 2; i++) {
            TreasureCard treasureCard = playerTwoTreasureCard.poll();
            playerTwoTreasureCard.offer(treasureCard);
        }
        targetPlayer.giveCard(playerTwoTreasureCard.poll());
        ForbiddenGame.players[1].setHandCards(playerTwoTreasureCard);
        setPlayerHands();
        drawPlayerHands();
    }

    @FXML
    void mouseClicked24(MouseEvent event) {
        Player targetPlayer = ForbiddenGame.players[givePlayerIndex];
        for (int i = 0; i < 3; i++) {
            TreasureCard treasureCard = playerTwoTreasureCard.poll();
            playerTwoTreasureCard.offer(treasureCard);
        }
        targetPlayer.giveCard(playerTwoTreasureCard.poll());
        ForbiddenGame.players[1].setHandCards(playerTwoTreasureCard);
        setPlayerHands();
        drawPlayerHands();
    }

    @FXML
    void mouseClicked25(MouseEvent event) {
        Player targetPlayer = ForbiddenGame.players[givePlayerIndex];
        for (int i = 0; i < 4; i++) {
            TreasureCard treasureCard = playerTwoTreasureCard.poll();
            playerTwoTreasureCard.offer(treasureCard);
        }
        targetPlayer.giveCard(playerTwoTreasureCard.poll());
        ForbiddenGame.players[1].setHandCards(playerTwoTreasureCard);
        setPlayerHands();
        drawPlayerHands();
    }

    @FXML
    void mouseClicked31(MouseEvent event) {
        Player targetPlayer = ForbiddenGame.players[givePlayerIndex];
        targetPlayer.giveCard(playerThreeTreasureCard.poll());
        ForbiddenGame.players[2].setHandCards(playerThreeTreasureCard);
        setPlayerHands();
        drawPlayerHands();
    }

    @FXML
    void mouseClicked32(MouseEvent event) {
        Player targetPlayer = ForbiddenGame.players[givePlayerIndex];
        TreasureCard treasureCard = playerThreeTreasureCard.poll();
        playerThreeTreasureCard.offer(treasureCard);
        targetPlayer.giveCard(playerThreeTreasureCard.poll());
        ForbiddenGame.players[2].setHandCards(playerThreeTreasureCard);
        setPlayerHands();
        drawPlayerHands();
    }

    @FXML
    void mouseClicked33(MouseEvent event) {
        Player targetPlayer = ForbiddenGame.players[givePlayerIndex];
        for (int i = 0; i < 2; i++) {
            TreasureCard treasureCard = playerThreeTreasureCard.poll();
            playerThreeTreasureCard.offer(treasureCard);
        }
        targetPlayer.giveCard(playerThreeTreasureCard.poll());
        ForbiddenGame.players[2].setHandCards(playerThreeTreasureCard);
        setPlayerHands();
        drawPlayerHands();
    }

    @FXML
    void mouseClicked34(MouseEvent event) {
        Player targetPlayer = ForbiddenGame.players[givePlayerIndex];
        for (int i = 0; i < 3; i++) {
            TreasureCard treasureCard = playerThreeTreasureCard.poll();
            playerThreeTreasureCard.offer(treasureCard);
        }
        targetPlayer.giveCard(playerThreeTreasureCard.poll());
        ForbiddenGame.players[2].setHandCards(playerThreeTreasureCard);
        setPlayerHands();
        drawPlayerHands();
    }

    @FXML
    void mouseClicked35(MouseEvent event) {
        Player targetPlayer = ForbiddenGame.players[givePlayerIndex];
        for (int i = 0; i < 4; i++) {
            TreasureCard treasureCard = playerThreeTreasureCard.poll();
            playerThreeTreasureCard.offer(treasureCard);
        }
        targetPlayer.giveCard(playerThreeTreasureCard.poll());
        ForbiddenGame.players[2].setHandCards(playerThreeTreasureCard);
        setPlayerHands();
        drawPlayerHands();
    }

    @FXML
    void mouseClicked41(MouseEvent event) {
        Player targetPlayer = ForbiddenGame.players[givePlayerIndex];
        targetPlayer.giveCard(playerFourTreasureCard.poll());
        ForbiddenGame.players[3].setHandCards(playerFourTreasureCard);
        setPlayerHands();
        drawPlayerHands();
    }

    @FXML
    void mouseClicked42(MouseEvent event) {
        Player targetPlayer = ForbiddenGame.players[givePlayerIndex];
        TreasureCard treasureCard = playerFourTreasureCard.poll();
        playerFourTreasureCard.offer(treasureCard);
        targetPlayer.giveCard(playerFourTreasureCard.poll());
        ForbiddenGame.players[3].setHandCards(playerFourTreasureCard);
        setPlayerHands();
        drawPlayerHands();
    }

    @FXML
    void mouseClicked43(MouseEvent event) {
        Player targetPlayer = ForbiddenGame.players[givePlayerIndex];
        for (int i = 0; i < 2; i++) {
            TreasureCard treasureCard = playerFourTreasureCard.poll();
            playerFourTreasureCard.offer(treasureCard);
        }
        targetPlayer.giveCard(playerFourTreasureCard.poll());
        ForbiddenGame.players[3].setHandCards(playerFourTreasureCard);
        setPlayerHands();
        drawPlayerHands();
    }

    @FXML
    void mouseClicked44(MouseEvent event) {
        Player targetPlayer = ForbiddenGame.players[givePlayerIndex];
        for (int i = 0; i < 3; i++) {
            TreasureCard treasureCard = playerFourTreasureCard.poll();
            playerFourTreasureCard.offer(treasureCard);
        }
        targetPlayer.giveCard(playerFourTreasureCard.poll());
        ForbiddenGame.players[3].setHandCards(playerFourTreasureCard);
        setPlayerHands();
        drawPlayerHands();
    }

    @FXML
    void mouseClicked45(MouseEvent event) {
        Player targetPlayer = ForbiddenGame.players[givePlayerIndex];
        for (int i = 0; i < 4; i++) {
            TreasureCard treasureCard = playerFourTreasureCard.poll();
            playerFourTreasureCard.offer(treasureCard);
        }
        targetPlayer.giveCard(playerFourTreasureCard.poll());
        ForbiddenGame.players[3].setHandCards(playerFourTreasureCard);
        setPlayerHands();
        drawPlayerHands();
    }
}//根据点击的卡牌来确认给出的卡牌，并在最后对玩家手牌堆进行更新
