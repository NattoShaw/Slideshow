import javafx.animation.Animation;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SlideShow {

    public enum floatSelect {
        RIGHT,
        CENTER,
        LEFT
    }

    public ArrayList<Image> images;
    ArrayList<ImageView> ivs;
    ArrayList<TranslateTransition> tt;
    ArrayList<SequentialTransition> st;

    public SlideShow() {
        images = new ArrayList<>();
        ivs = new ArrayList<>();
        tt = new ArrayList<>();
        st = new ArrayList<>();
    }

    public void addText(String text, Pane innerPane1, Pane innerPane2, int width, int height, int slideNo, floatSelect floatSelect) {

        Text textPane = new Text((-width * (slideNo - 1)) + 50, (height / 2) + 20, text);
        Text duplicateText = new Text((-width * (getIVs().size() - 1)) + 50, (height / 2) + 20, text);

        textPane.setStyle("-fx-fill: #55fc9e; -fx-font-size: 30; -fx-font-weight: bold;");

        if (floatSelect.equals(SlideShow.floatSelect.CENTER)) {
            textPane.setX(((-width * (slideNo - 1)) + (width / 2)) - textPane.getBoundsInLocal().getWidth());
            duplicateText.setX((-width * (ivs.size() - 1)) + (width / 2) - duplicateText.getBoundsInLocal().getWidth());
        } else if (floatSelect.equals(SlideShow.floatSelect.LEFT)) {
            textPane.setX((-width * (slideNo - 1)) + 50);
            duplicateText.setX((-width * (ivs.size() - 1)) + 50);
        } else if (floatSelect.equals(SlideShow.floatSelect.RIGHT)) {
            textPane.setX((-width * (slideNo - 1)) + (width - 50));
            duplicateText.setX((-width * (ivs.size() - 1)) + (width - 50));
        }

        innerPane1.getChildren().add(textPane);


        if (slideNo == 1) {
            duplicateText.setStyle("-fx-fill: #55fc9e; -fx-font-size: 30; -fx-font-weight: bold;");
            innerPane2.getChildren().add(duplicateText);
        }
    }

    public void loadImages(int width) {
        try(Stream<Path> walk = Files.walk(Paths.get("C:\\Users\\IdeaProjects\\YTEPSLIDESHOW\\SlideShow"))) {
            List<String> result = walk.map(x -> x.toString()).filter(f -> f.endsWith(".jpg") || f.endsWith(".png")).collect(Collectors.toList());

            for (int i = 0; i < result.size(); i++) {
                File file = new File(result.get(i));
                Image tmpImg = new Image(String.valueOf(file.toURI().toURL()));

                images.add(tmpImg);

                ImageView tmpIV = new ImageView();
                tmpIV.setX(-width * i);

                tmpIV.setImage(tmpImg);
                tmpIV.setFitWidth(width);
                tmpIV.setPreserveRatio(true);
                tmpIV.setSmooth(true);
                tmpIV.setCache(true);

                ivs.add(tmpIV);

                if (i == result.size() - 1) {
                    ImageView finalIV = new ImageView();
                    finalIV.setX(-width * (i + 1));
                    finalIV.setImage(images.get(0));
                    finalIV.setFitWidth(width);
                    finalIV.setPreserveRatio(true);
                    finalIV.setSmooth(true);
                    finalIV.setCache(true);

                    ivs.add(finalIV);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startAnimation(int width, Pane innerPane1, Pane innerPane2, int duration) {
        if (tt.isEmpty()) {
            TranslateTransition tmpTT1;
            TranslateTransition tmpTT2;

            SequentialTransition stTMP = new SequentialTransition();
            SequentialTransition st2TMP = new SequentialTransition();

            for (int x = 0; x < images.size(); x++) {
                tmpTT1 = new TranslateTransition(Duration.seconds(duration), innerPane1);
                tmpTT1.setByX(width);
                tt.add(tmpTT1);
            }

            for (int x = 0; x < images.size(); x++) {
                tmpTT2 = new TranslateTransition(Duration.seconds(duration), innerPane2);
                tmpTT2.setByX(width);
                tt.add(tmpTT2);
            }

            for (int x = 0; x < (tt.size() / 2); x++) {
                stTMP.getChildren().addAll(new PauseTransition(Duration.seconds(4)), tt.get(x));
            }

            for (int x = (tt.size() / 2); x < tt.size(); x++) {
                st2TMP.getChildren().addAll(new PauseTransition(Duration.seconds(4)), tt.get(x));
            }

            st.add(st2TMP);
            st.add(stTMP);
        }

        for (SequentialTransition stCurr : st) {
            stCurr.setCycleCount(Animation.INDEFINITE);
            stCurr.play();
        }
    }

    public ArrayList<ImageView> getIVs() {
        return ivs;
    }
}
