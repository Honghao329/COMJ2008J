package team.group.myforbidden.view;


import javafx.application.Application;
import javafx.stage.Stage;
import team.group.myforbidden.model.ForbiddenGame;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        ForbiddenGame.getInstance().init(primaryStage);//初始化游戏主窗口
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

}
