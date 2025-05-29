package team.group.myforbidden.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.input.MouseEvent;
import team.group.myforbidden.model.ForbiddenGame;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class MenuScreenController {

    @FXML private Button exit;
    @FXML private Button learn;
    @FXML private Button start;

    @FXML void mouseClickedExit(MouseEvent event) {
        System.exit(0);
    }//点击退出终止整个进程
    @FXML void mouseClickedLearnToPlay(MouseEvent event) {
        ForbiddenGame.toLearn();
    }//加载学习文档
    @FXML
    void mouseClickedNewGame(MouseEvent event) {//选择开始新游戏，传给主逻辑并加载
        // 选择玩家人数
        List<Integer> playerOptions = Arrays.asList(2, 3, 4);
        ChoiceDialog<Integer> playerDialog = new ChoiceDialog<>(2, playerOptions);
        playerDialog.setTitle("New Game Setup");
        playerDialog.setHeaderText("Select Number of Players");
        playerDialog.setContentText("Players:");

        Optional<Integer> playerResult = playerDialog.showAndWait();
        if (playerResult.isEmpty()) return; // 用户取消

        int playerCount = playerResult.get();

        // 选择游戏难度
        List<Integer> difficultyOptions = Arrays.asList(2, 3, 4, 5);
        ChoiceDialog<Integer> difficultyDialog = new ChoiceDialog<>(2, difficultyOptions);
        difficultyDialog.setTitle("New Game Setup");
        difficultyDialog.setHeaderText("Select Game Difficulty");
        difficultyDialog.setContentText("Difficulty (2=Easy, 5=Hard):");

        Optional<Integer> difficultyResult = difficultyDialog.showAndWait();
        if (difficultyResult.isEmpty()) return; // 用户取消

        int difficulty = difficultyResult.get();

        ForbiddenGame.setPlayerNum(playerCount);
        ForbiddenGame.setLevel(difficulty);

        // 启动游戏
        ForbiddenGame.toGame();
    }

    @FXML void mouseEnteredExit(MouseEvent event) {
        exit.setOpacity(0.5);
    }
    @FXML void mouseEnteredLearn(MouseEvent event) {
        learn.setOpacity(0.5);
    }
    @FXML void mouseEnteredStart(MouseEvent event) {
        start.setOpacity(0.5);
    }
    @FXML void mouseExitedExit(MouseEvent event) {
        exit.setOpacity(1);
    }
    @FXML void mouseExitedLearn(MouseEvent event) {
        learn.setOpacity(1);
    }
    @FXML void mouseExitedStart(MouseEvent event) {
        start.setOpacity(1);
    }
}
