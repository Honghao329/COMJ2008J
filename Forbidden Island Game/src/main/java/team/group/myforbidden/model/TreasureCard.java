package team.group.myforbidden.model;

import javafx.scene.image.Image;

import java.util.Objects;

public class TreasureCard {
    private TreasureCardType treasureCardType;
    private Image image;
    public TreasureCard(TreasureCardType treasureCardType) {//字段1记录宝藏卡类型
        this.treasureCardType = treasureCardType;
        switch (treasureCardType) {
            case HELICOPTER_LIFT -> image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/team/group/myforbidden/image/treasurecard/helicopter.png")));
            case SANDBAG -> image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/team/group/myforbidden/image/treasurecard/sandbags.png")));
            case WATERS_RISE -> image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/team/group/myforbidden/image/treasurecard/watersrise.png")));
            case FIRE_CARD -> image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/team/group/myforbidden/image/treasurecard/fire.png")));
            case WIND_CARD -> image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/team/group/myforbidden/image/treasurecard/wind.png")));
            case EARTH_CARD -> image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/team/group/myforbidden/image/treasurecard/earth.png")));
            case OCEAN_CARD -> image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/team/group/myforbidden/image/treasurecard/ocean.png")));
        }
    }

    public TreasureCardType getTreasureCardType() {
        return treasureCardType;
    }

    public Image getImage() {
        return image;
    }//字段2对应图片资源
}
//初始化确认类型在方法内找对应图片