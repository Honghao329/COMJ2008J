package team.group.myforbidden.model;

import javafx.scene.control.ChoiceDialog;
import javafx.scene.image.Image;
import team.group.myforbidden.controller.GameScreenController;

import java.util.*;

public class Player {
    private PlayerType type;//对应头像和棋子资源
    private Image cardImage;
    private Image pawnImage;
    private Queue<TreasureCard> handCards;//采用队列数据结构记录对应手牌
    private int place;//记录所在岛屿
    private int alive;//记录存活状态

    public Player(PlayerType type) {//根据传入玩家类型参数去获取对应头像和棋子图片
        this.alive = 1;
        this.type = type;
        switch (type) {
            case DIVER -> {
                this.cardImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/team/group/myforbidden/image/playercard/diver.png")));
                this.pawnImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/team/group/myforbidden/image/player/diver.png")));
                this.place = GameScreenController.placeOfPlayer[0];
            }
            case ENGINEER -> {
                this.cardImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/team/group/myforbidden/image/playercard/engineer.png")));
                this.pawnImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/team/group/myforbidden/image/player/engineer.png")));
                this.place = GameScreenController.placeOfPlayer[1];
            }
            case EXPLORER -> {
                this.cardImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/team/group/myforbidden/image/playercard/explorer.png")));
                this.pawnImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/team/group/myforbidden/image/player/explorer.png")));
                this.place = GameScreenController.placeOfPlayer[2];
            }
            case MESSENGER -> {
                this.cardImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/team/group/myforbidden/image/playercard/messenger.png")));
                this.pawnImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/team/group/myforbidden/image/player/messenger.png")));
                this.place = GameScreenController.placeOfPlayer[3];
            }
            case NAVIGATOR -> {
                this.cardImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/team/group/myforbidden/image/playercard/navigator.png")));
                this.pawnImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/team/group/myforbidden/image/player/navigator.png")));
                this.place = GameScreenController.placeOfPlayer[4];
            }
            case PILOT -> {
                this.cardImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/team/group/myforbidden/image/playercard/pilot.png")));
                this.pawnImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/team/group/myforbidden/image/player/pilot.png")));
                this.place = GameScreenController.placeOfPlayer[5];
            }
        }
        this.handCards = new LinkedList<>();
    }

    public PlayerType getType() {
        return type;
    }

    public int getPlace() {
        return place;
    }

    public Image getCardImage() {
        return cardImage;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    public Image getPawnImage() {
        return pawnImage;
    }

    public Queue<TreasureCard> getHandCards() {
        return handCards;
    }

    public void setHandCards(Queue<TreasureCard> handCards) {
        this.handCards = handCards;
    }

    public void giveCard(TreasureCard card) {//给玩家的卡传给玩家，获取treasurecard
        if (this.handCards.size() == 5) {
            List<String> options = Arrays.asList(
                    "Card 1",
                    "Card 2",
                    "Card 3",
                    "Card 4",
                    "Card 5",
                    "Newly Drawn Card"
            );//弹窗询问玩家要丢弃的卡牌，插入插入到队尾，先进先出

            ChoiceDialog<String> dialog = new ChoiceDialog<>(options.get(0), options);
            dialog.setTitle("Discard a Card");
            dialog.setHeaderText("Your hand is full.");
            dialog.setContentText("Please select a card to discard:");

            Optional<String> result = dialog.showAndWait();

            result.ifPresent(selectedCard -> {
                System.out.println("Player chose to discard: " + selectedCard);
                // 执行丢弃逻辑，手牌大于5
                switch (selectedCard) {
                    case "Card 1" -> {
                        handCards.poll();
                        handCards.add(card);
                    }
                    case "Card 2" -> {
                        TreasureCard treasureCard = handCards.poll();
                        handCards.poll();
                        handCards.add(treasureCard);
                        handCards.add(card);
                    }
                    case "Card 3" -> {
                        TreasureCard treasureCard1 = handCards.poll();
                        TreasureCard treasureCard2 = handCards.poll();
                        handCards.poll();
                        handCards.add(treasureCard1);
                        handCards.add(treasureCard2);
                        handCards.add(card);
                    }
                    case "Card 4" -> {
                        TreasureCard treasureCard1 = handCards.poll();
                        TreasureCard treasureCard2 = handCards.poll();
                        TreasureCard treasureCard3 = handCards.poll();
                        handCards.poll();
                        handCards.add(treasureCard1);
                        handCards.add(treasureCard2);
                        handCards.add(treasureCard3);
                        handCards.add(card);
                    }
                    case "Card 5" -> {
                        TreasureCard treasureCard1 = handCards.poll();
                        TreasureCard treasureCard2 = handCards.poll();
                        TreasureCard treasureCard3 = handCards.poll();
                        TreasureCard treasureCard4 = handCards.poll();
                        handCards.poll();
                        handCards.add(treasureCard1);
                        handCards.add(treasureCard2);
                        handCards.add(treasureCard3);
                        handCards.add(treasureCard4);
                        handCards.add(card);
                    }
                    case "Newly Drawn Card" -> {
                        return;
                    }
                }
            });
        }
        handCards.add(card);
    }

    public int getAlive() {
        return alive;
    }

    public void setAlive(int alive) {
        this.alive = alive;
    }
}
