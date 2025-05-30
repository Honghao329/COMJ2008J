package team.group.myforbidden.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.control.ScrollPane;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import javafx.embed.swing.SwingFXUtils;
import team.group.myforbidden.model.ForbiddenGame;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class SettingScreenController {//每个页面的交互逻辑处理

    @FXML private VBox pdfContainer;
    @FXML private ScrollPane scrollPane;
    @FXML private Button back;

    public void initialize() {//每个controller在加载fxml的初始化
        // 取消pdfContainer间距和padding，避免间隙
        pdfContainer.setSpacing(0);
        pdfContainer.setPadding(javafx.geometry.Insets.EMPTY);

        loadPDF("/team/group/myforbidden/docs/ForbiddenIslandTM-RULES.pdf");
    }

    private void loadPDF(String resourcePath) {//通过字节流的pdf文档倒入页面
        try (InputStream inputStream = getClass().getResourceAsStream(resourcePath)) {
            if (inputStream == null) {
                System.err.println("PDF Not Found: " + resourcePath);
                return;
            }

            try (PDDocument document = PDDocument.load(inputStream)) {
                PDFRenderer pdfRenderer = new PDFRenderer(document);
                int pageCount = document.getNumberOfPages();

                for (int page = 0; page < pageCount; page++) {
                    BufferedImage bufferedImage = pdfRenderer.renderImageWithDPI(page, 150);
                    Image fxImage = SwingFXUtils.toFXImage(bufferedImage, null);
                    ImageView imageView = new ImageView(fxImage);

                    imageView.setPreserveRatio(true);
                    imageView.setFitWidth(1024);  // 设置为Stage宽度，确保宽度统一
                    pdfContainer.getChildren().add(imageView);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML private void mouseClickedBack(MouseEvent event) {ForbiddenGame.toMenu();}//鼠标点击back回到主页面

    @FXML private void mouseEnteredBack(MouseEvent event) {back.setOpacity(0.5);}

    @FXML private void mouseExitedBack(MouseEvent event) {back.setOpacity(1);}
}
