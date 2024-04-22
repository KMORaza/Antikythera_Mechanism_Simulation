package com.antikythera_mechanism.simulation;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
public class OlympiadDial extends Application {
    private static final int WIDTH = 400;
    private static final int HEIGHT = 400;
    private static final int CENTER_X = WIDTH / 2;
    private static final int CENTER_Y = HEIGHT / 2;
    private static final int DIAL_RADIUS = 150;
    private static final int POINTER_LENGTH = 180;
    private static final int GAME_NAMES_RADIUS = DIAL_RADIUS + 70;
    private static final String[] YEARS = {"LΑ", "LΒ", "LΓ", "LΔ"};
    private double angle = 0;
    private double angleDelta = 1;
    @Override
    public void start(Stage primaryStage) {
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
                draw(gc);
            }
        };
        timer.start();
        StackPane root = new StackPane(canvas);
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        primaryStage.setTitle("Olympiad Dial");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    private void update() {
        angle += angleDelta;
    }
    private void draw(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, WIDTH, HEIGHT);
        gc.setFill(Color.WHITE);
        gc.fillOval(CENTER_X - DIAL_RADIUS, CENTER_Y - DIAL_RADIUS, DIAL_RADIUS * 2, DIAL_RADIUS * 2);
        for (int i = 0; i < 4; i++) {
            double startAngle = 90 - i * 90;
            double sectorAngle = 90;
            double sectorCenterAngle = startAngle - sectorAngle / 2;
            gc.setFill(getSectorColor(i));
            gc.fillArc(CENTER_X - DIAL_RADIUS, CENTER_Y - DIAL_RADIUS, DIAL_RADIUS * 2, DIAL_RADIUS * 2,
                    startAngle, -sectorAngle, javafx.scene.shape.ArcType.ROUND);
            double sectorCenterX = CENTER_X + Math.cos(Math.toRadians(sectorCenterAngle)) * (DIAL_RADIUS - 50);
            double sectorCenterY = CENTER_Y - Math.sin(Math.toRadians(sectorCenterAngle)) * (DIAL_RADIUS - 50);
            gc.setFill(Color.WHITE);
            gc.fillText(YEARS[i], sectorCenterX, sectorCenterY);
            double lineEndX = CENTER_X + Math.cos(Math.toRadians(startAngle)) * DIAL_RADIUS;
            double lineEndY = CENTER_Y - Math.sin(Math.toRadians(startAngle)) * DIAL_RADIUS;
            gc.setStroke(Color.WHITE);
            gc.setLineWidth(1);
            gc.strokeLine(CENTER_X, CENTER_Y, lineEndX, lineEndY);
        }
        drawGameName(gc, CENTER_X, CENTER_Y, GAME_NAMES_RADIUS);
        gc.setStroke(Color.WHITE);
        gc.setLineWidth(2);
        gc.strokeOval(CENTER_X - 180, CENTER_Y - 180, 180 * 2, 180 * 2);
        gc.setStroke(Color.rgb(255, 255, 153));
        gc.setLineWidth(3);
        double pointerAngle = angle % 360;
        double pointerX = CENTER_X + Math.cos(Math.toRadians(pointerAngle)) * POINTER_LENGTH;
        double pointerY = CENTER_Y - Math.sin(Math.toRadians(pointerAngle)) * POINTER_LENGTH;
        gc.strokeLine(CENTER_X, CENTER_Y, pointerX, pointerY);
    }
    private Color getSectorColor(int index) {
        switch (index) {
            case 0:
                return Color.BLACK;
            case 1:
                return Color.BLACK;
            case 2:
                return Color.BLACK;
            case 3:
                return Color.BLACK;
            default:
                return Color.WHITE;
        }
    }
    private void drawGameName(GraphicsContext gc, double centerX, double centerY, double radius) {
        String[] gameName = {
                "ΙΣΘΜΙΑ",  // ISHTMIA
                "ΟΛΥΜΠΙΑ", // OLYMPIA
                "ΝΕΜΕΑ",   // NEMEA
                "NAA",     // NAA
                "ΙΣΘΜΙΑ",  // ISTHMIA
                "ΠΥΘΙΑ",   // PYTHIA
                "ΝΕΜΕΑ",   // NEMEA
                "ΑΛΙΕΙΑ"   // HALEIIA
        };
        int numPhases = gameName.length;
        double angleStep = 2 * Math.PI / 8;
        gc.setFont(Font.font("Georgia", 14));
        gc.setFill(Color.WHITE);
        for (int i = 0; i < numPhases; i++) {
            double angle = i * angleStep - Math.PI / 2.60;
            double x = centerX + (radius - 60) * Math.cos(angle);
            double y = centerY + (radius - 60) * Math.sin(angle);
            gc.save();
            gc.translate(x, y);
            gc.rotate(Math.toDegrees(angle + Math.PI / 2));
            gc.fillText(gameName[i], -gc.getFont().getSize() * gameName[i].length() / 4, 0);
            gc.restore();
        }
        gc.setStroke(Color.WHITE);
        gc.setLineWidth(1);
        gc.strokeOval(centerX - radius + 70, centerY - radius + 70, 2 * (radius - 70), 2 * (radius - 70));
    }
    public static void main(String[] args) {
        launch(args);
    }
}