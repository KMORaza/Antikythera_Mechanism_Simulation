package com.antikythera_mechanism.simulation;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
public class SarosDial extends Application {
    private static final double RADIUS = 300;
    private static final int CELLS = 223;
    private static final double CELL_ANGLE = 360.0 / CELLS;
    private static final double START_DATE_ANGLE = 118;
    private static final double SPEED = 0.05;
    private static final double SAROS_CYCLE_DAYS = 6585.333;
    private static final double YEARS_PER_CYCLE = 18 + 11.0 / 30.0;
    private static final double DAYS_PER_YEAR = SAROS_CYCLE_DAYS / YEARS_PER_CYCLE;
    private double angle = START_DATE_ANGLE;
    private double daysElapsed = 0;
    // Glyph abbreviations
    private static final String LUNAR_ECLIPSE = "Σ";  // ΣΕΛΗΝΗ (Selene)
    private static final String SOLAR_ECLIPSE = "Η";  // ΗΛΙΟΣ (Helios)
    private static final String DAY_HOUR = "H\\M";   // ΗΜΕΡΑΣ (Hemeras) and ωρα (hora)
    private static final String NIGHT = "N\\Y";      // ΝΥΚΤΟΣ (Nuktos)
    @Override
    public void start(Stage primaryStage) {
        Pane root = new Pane();
        root.setStyle("-fx-background-color: black;");
        Scene scene = new Scene(root, 700, 700);
        double centerX = scene.getWidth() / 2;
        double centerY = scene.getHeight() / 2;
        drawScale(root, centerX, scene.getHeight() - 50, SAROS_CYCLE_DAYS);
        Circle shadowIndicator = new Circle(0, 0, 5, Color.RED);
        root.getChildren().add(shadowIndicator);
        Text yearsText = new Text(20, 20, "Years: 0");
        Text daysText = new Text(20, 40, "Days: 0");
        root.getChildren().addAll(yearsText, daysText);
        drawSpiralPart(root, centerX, centerY, angle, daysElapsed);
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(20), e -> {
            daysElapsed += SPEED;
            angle += SPEED;
            root.getChildren().removeIf(node -> node instanceof Line || node instanceof Circle || node instanceof Text);
            drawSpiralPart(root, centerX, centerY, angle, daysElapsed);
            int years = (int) (daysElapsed / DAYS_PER_YEAR);
            int days = (int) (daysElapsed % DAYS_PER_YEAR);
            yearsText.setText("Years: " + years);
            daysText.setText("Days: " + days);
            yearsText.setFont(Font.font("Verdana", 12));
            yearsText.setFill(Color.WHITE);
            daysText.setFont(Font.font("Verdana", 12));
            daysText.setFill(Color.WHITE);
            double shadowRadius = RADIUS - (daysElapsed * RADIUS * 2 / SAROS_CYCLE_DAYS);
            double shadowX = centerX + shadowRadius * Math.cos(Math.toRadians(angle));
            double shadowY = centerY + shadowRadius * Math.sin(Math.toRadians(angle));
            Line shadowLine = new Line(centerX, centerY, shadowX, shadowY);
            shadowLine.setStroke(Color.RED);
            root.getChildren().add(shadowLine);
            shadowIndicator.setCenterX(shadowX);
            shadowIndicator.setCenterY(shadowY);
        }));
        timeline.setCycleCount(Animation.INDEFINITE); // Repeat indefinitely
        timeline.play();
        primaryStage.setTitle("Saros Dial");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    private void drawSpiralPart(Pane root, double centerX, double centerY, double startAngle, double daysElapsed) {
        for (int i = 0; i < CELLS; i++) {
            double currentRadius = RADIUS - (i * 1.5); // Decrease radius for each cell
            double endAngle = startAngle + i * CELL_ANGLE;
            double endX = centerX + currentRadius * Math.cos(Math.toRadians(endAngle));
            double endY = centerY + currentRadius * Math.sin(Math.toRadians(endAngle));
            Line line = new Line(centerX, centerY, endX, endY);
            line.setStroke(Color.WHITE);
            root.getChildren().add(line);
            if (i % 19 == 0) { // Every 19th cell
                String glyph = "";
                if (i % 5 == 0) {
                    glyph = LUNAR_ECLIPSE; // Lunar eclipse
                } else {
                    glyph = SOLAR_ECLIPSE; // Solar eclipse
                }
                Text text = new Text(endX, endY, glyph);
                text.setStroke(Color.rgb(64,224,208));
                text.setFont(Font.font("Georgia", 14));
                root.getChildren().add(text);
            }
            if (i % 51 == 0) { // Every 51st cell
                double innerRadius = RADIUS - 50;
                double innerEndX = centerX + innerRadius * Math.cos(Math.toRadians(endAngle));
                double innerEndY = centerY + innerRadius * Math.sin(Math.toRadians(endAngle));
                Line innerLine = new Line(centerX, centerY, innerEndX, innerEndY);
                innerLine.setStroke(Color.rgb(255,255,102)); // Change color for clarity
                root.getChildren().add(innerLine);
            }
            // day and night indicator
            if (i % 23 == 0) { // Every 23rd cell
                double indicatorRadius = RADIUS + 20;
                double indicatorX = centerX + indicatorRadius * Math.cos(Math.toRadians(endAngle));
                double indicatorY = centerY + indicatorRadius * Math.sin(Math.toRadians(endAngle));
                String indicatorText = i % 2 == 0 ? DAY_HOUR : NIGHT;
                Text indicator = new Text(indicatorX, indicatorY, indicatorText);
                indicator.setStroke(Color.WHITE);
                indicator.setFont(Font.font("Courier New", 16));
                root.getChildren().add(indicator);
            }
        }
    }
    private void drawScale(Pane root, double centerX, double y, double totalDays) {
        double startX = centerX - RADIUS;
        double endX = centerX + RADIUS;
        for (int i = 0; i <= YEARS_PER_CYCLE; i++) {
            double x = startX + (i * RADIUS * 2 / YEARS_PER_CYCLE);
            Line mark = new Line(x, y - 10, x, y + 10);
            root.getChildren().add(mark);
        }
        for (int i = 0; i < totalDays; i++) {
            double x = startX + (i * RADIUS * 2 / totalDays);
            if (i % 10 == 0) {
                Line mark = new Line(x, y - 5, x, y + 5);
                root.getChildren().add(mark);
            }
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}