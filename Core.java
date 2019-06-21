import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Core extends Application {


    public static SlideShow ss;

    public int width = 1200;
    public int height = 500;


    Scene rootScene;

    Pane rootPane;

    Pane innerPane1;
    Pane innerPane2;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        ss = new SlideShow();
        ss.loadImages(width);

        primaryStage.setTitle("Slide Shows");
        primaryStage.setWidth(width);
        primaryStage.setHeight(height);

        rootPane = new Pane();

        innerPane1 = new Pane();
        innerPane2 = new Pane();

        for (int i = 0; i < ss.getIVs().size() - 1; i++) {
            innerPane1.getChildren().add(ss.getIVs().get(i));
        }
        innerPane2.getChildren().add(ss.getIVs().get(ss.getIVs().size() - 1));

        rootPane.getChildren().addAll(innerPane1, innerPane2);

        rootScene = new Scene(rootPane, width, height);

        primaryStage.setScene(rootScene);

        ss.addText("Learn how to write text on a SlideShow", innerPane1, innerPane2, width, height, 1, SlideShow.floatSelect.LEFT);
        ss.addText("THIS IS SOME OTHER TEXT ON SLIDESHOW", innerPane1, innerPane2, width, height, 2, SlideShow.floatSelect.CENTER);
        ss.addText("THIS IS SOME OTHER TEXT ON SLIDESHOW NUMBER 3", innerPane1, innerPane2, width, height, 3, SlideShow.floatSelect.CENTER);
        ss.addText("FINAL TEXT", innerPane1, innerPane2, width, height, 4, SlideShow.floatSelect.CENTER);

        ss.startAnimation(width, innerPane1, innerPane2, 4);

        primaryStage.show();
    }
}
