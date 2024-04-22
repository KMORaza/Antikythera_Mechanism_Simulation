package com.antikythera_mechanism.simulation;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
public class CallippicCycle extends Application {
    private int currentYear = 2024;
    private double animationSpeed = 1.0;
    private boolean isPaused = false;
    private Label synodicMonthsLabel = new Label("Synodic Months: " + calculateSynodicMonths(currentYear));
    private Label draconicMonthsLabel = new Label("Draconic Months: " + calculateDraconicMonths(currentYear));
    private Label eclipseYearsLabel = new Label("Eclipse Years: " + calculateEclipseYears(currentYear));
    private Label anomalisticMonthsLabel = new Label("Anomalistic Months: " + calculateAnomalisticMonths(currentYear));
    private Button startButton = new Button("▶"); // Start simulation
    private Button pauseButton = new Button("⏸"); // Pause simulation
    private Button resetButton = new Button("\uD83D\uDD04\n"); // Reset the simulation
    private Slider speedSlider = new Slider(0.1, 2.0, 1.0);
    private VBox resultBox = new VBox(10, synodicMonthsLabel, draconicMonthsLabel, eclipseYearsLabel, anomalisticMonthsLabel);
    @Override
    public void start(Stage primaryStage) {
        BorderPane borderPane = new BorderPane();
        VBox controlBox = new VBox(10);
        controlBox.setPadding(new Insets(10));
        HBox buttonBox = new HBox(10);
        buttonBox.getChildren().addAll(startButton, pauseButton, resetButton);
        speedSlider.setBlockIncrement(0.1);
        speedSlider.setShowTickMarks(true);
        speedSlider.setShowTickLabels(true);
        Label speedLabel = new Label("Speed:");
        speedLabel.setTextFill(Color.WHITE);
        HBox speedBox = new HBox(10, speedLabel, speedSlider);
        speedBox.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        controlBox.getChildren().addAll(buttonBox, speedBox);
        borderPane.setLeft(controlBox);
        AnimationTimer timer = new AnimationTimer() {
            private long lastUpdate = 0;
            @Override
            public void handle(long now) {
                if (now - lastUpdate >= 16_666_667 / animationSpeed) {
                    currentYear++;
                    updateLabels();
                    lastUpdate = now;
                }
            }
        };
        startButton.setOnAction(e -> {
            timer.start();
            isPaused = false;
        });
        pauseButton.setOnAction(e -> {
            timer.stop();
            isPaused = true;
        });
        resetButton.setOnAction(e -> {
            timer.stop();
            isPaused = true;
            currentYear = 2024;
            updateLabels();
        });
        speedSlider.valueProperty().addListener((obs, oldValue, newValue) -> {
            animationSpeed = newValue.doubleValue();
            if (!isPaused) {
                timer.stop();
                timer.start();
            }
        });
        resultBox.setPadding(new Insets(10));
        resultBox.setVisible(true);
        VBox mainLayout = new VBox(20, borderPane, resultBox);
        mainLayout.setPadding(new Insets(20));
        mainLayout.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        Scene scene = new Scene(mainLayout, 340, 280); // (width of window, height of window)
        scene.setFill(Color.BLACK);
        Font verdanaFont = Font.font("Verdana", 13);
        synodicMonthsLabel.setFont(verdanaFont);
        draconicMonthsLabel.setFont(verdanaFont);
        eclipseYearsLabel.setFont(verdanaFont);
        anomalisticMonthsLabel.setFont(verdanaFont);
        synodicMonthsLabel.setTextFill(Color.WHITE);
        draconicMonthsLabel.setTextFill(Color.WHITE);
        eclipseYearsLabel.setTextFill(Color.WHITE);
        anomalisticMonthsLabel.setTextFill(Color.WHITE);
        startButton.setStyle("-fx-background-color: #000000; -fx-text-fill: #FFFFFF; -fx-border-color: #FFFFFF;");
        pauseButton.setStyle("-fx-background-color: #000000; -fx-text-fill: #FFFFFF; -fx-border-color: #FFFFFF;");
        resetButton.setStyle("-fx-background-color: #000000; -fx-text-fill: #FFFFFF; -fx-border-color: #FFFFFF;");
        Font buttonFont = Font.font("Segoe UI Emoji", 23);
        startButton.setFont(buttonFont);
        pauseButton.setFont(buttonFont);
        resetButton.setFont(buttonFont);
        speedSlider.setStyle("-fx-control-inner-background: #000000; -fx-text-fill: #FFFFFF;");
        primaryStage.setTitle("Callippic Cycle");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
        updateLabels();
    }
    private int calculateSynodicMonths(int startYear) {
        return 940 * 29 + (startYear - 330) * 12; // 940 Synodic months
    }
    private double calculateDraconicMonths(int startYear) {
        return 1020.084 * 27.21222 + (startYear - 330) * 27.21222; // 1,020.084 Draconic months
    }
    private double calculateEclipseYears(int startYear) {
        return 80.084 * 346.620075 + (startYear - 330) * 346.620075; // 80.084 Eclipse years (160 Eclipse seasons)
    }
    private double calculateAnomalisticMonths(int startYear) {
        return 1007.410 * 27.55455 + (startYear - 330) * 27.55455; // 1,007.410 Anomalistic months
    }
    private void updateLabels() {
        synodicMonthsLabel.setText("Synodic Months: " + calculateSynodicMonths(currentYear));
        draconicMonthsLabel.setText("Draconic Months: " + calculateDraconicMonths(currentYear));
        eclipseYearsLabel.setText("Eclipse Years: " + calculateEclipseYears(currentYear));
        anomalisticMonthsLabel.setText("Anomalistic Months: " + calculateAnomalisticMonths(currentYear));
    }
    public static void main(String[] args) {
        launch(args);
    }
}