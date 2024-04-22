package com.antikythera_mechanism.simulation;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
public class ExeligmosCycle extends Application {
    private final int SAROS_CYCLE_DAYS = 6585;
    private final int EXELIGMOS_CYCLE_DAYS = 19756;
    private final int DIAL_RADIUS = 140;
    private final int POINTER_LENGTH = 250;
    private int dialPosition = 0;
    private int dayCount = 0;
    @Override
    public void start(Stage primaryStage) {
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: black;");
        Circle dialCircle = new Circle(DIAL_RADIUS, Color.TRANSPARENT);
        dialCircle.setStroke(Color.WHITE);
        Line pointer = new Line(0, 0, 0, -POINTER_LENGTH);
        pointer.setStroke(Color.rgb(255,255,153));
        pointer.setStrokeWidth(2);
        Text dialPositionText = new Text("Dial Position: " + dialPosition);
        dialPositionText.setFont(Font.font("Verdana", 13));
        dialPositionText.setFill(Color.rgb(255,255,153));
        Text dayCountText = new Text("Day Count: " + dayCount);
        dayCountText.setFont(Font.font("Verdana", 13));
        dayCountText.setFill(Color.rgb(255,255,153));
        Text timeText = new Text("Time: " + calculateTime(dialPosition));
        timeText.setFont(Font.font("Verdana", 13));
        timeText.setFill(Color.rgb(255,255,153));
        VBox controls = new VBox(10);
        controls.setPadding(new Insets(10));
        controls.getChildren().addAll(dialPositionText, dayCountText, timeText);
        root.getChildren().addAll(dialCircle, pointer, controls);
        Scene scene = new Scene(root, 400, 400);
        primaryStage.setTitle("Exeligmos Cycle");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), e -> moveDial(1, pointer, dialPositionText, dayCountText, timeText))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
    private void moveDial(int hours, Line pointer, Text dialPositionText, Text dayCountText, Text timeText) {
        dialPosition += hours;
        dialPosition %= 24;
        dayCount += hours * SAROS_CYCLE_DAYS;
        dayCount %= EXELIGMOS_CYCLE_DAYS;
        dialPositionText.setText("Dial Position: " + dialPosition);
        dayCountText.setText("Day Count: " + dayCount);
        timeText.setText("Time: " + calculateTime(dialPosition));
        double angle = dialPosition * (360.0 / 24);
        pointer.setRotate(angle);
    }
    private String calculateTime(int dialPosition) {
        int hours = dialPosition + 1;
        return String.format("%02d HRS", hours);
    }
    public static void main(String[] args) {
        launch(args);
    }
}