package com.antikythera_mechanism.simulation;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
public class MetonicDial extends Application {
    private static final int DIAL_RADIUS = 200;
    private static final int INDICATOR_RADIUS = 10;
    private static final double LUNAR_NEEDLE_LENGTH = 345;
    private static final int NUM_HOUR_LINES = 12;
    private static final int NUM_ROTATIONS = 5;
    private static final Map<Integer, String> corinthianMonths = new HashMap<>() {{
        put(0, "ΦΟΙΝΙΚΑΙΟΣ");    // PHOINIKAIOS
        put(29, "ΚΡΑΝΕΙΟΣ");     // KRANEIOS
        put(59, "ΛΑΝΟΤΡΟΠΙΟΣ");  // LANOTROPIOS
        put(89, "ΜΑΧΑΝΕΥΣ");     // MACHANEUS
        put(118, "ΔΩΔΕΚΑΤΕΥΣ");  // DODEKATEUS
        put(148, "ΕΥΚΛΕΙΟΣ");    // EUKLEIOS
        put(178, "ΑΡΤΕΜΙΣΙΟΣ");  // ARTEMISIOS
        put(207, "ΨΥΔΡΕΥΣ");     // PSYDREUS
        put(237, "ΓΑΜΕΙΛΙΟΣ");   // GAMEILIOS
        put(267, "ΑΓΡΙΑΝΙΟΣ");   // AGRANIOS
        put(296, "ΠΑΝΑΜΟΣ");     // PANAMOS
        put(326, "ΑΠΕΛΛΑΙΟΣ");   // APELLAIOS
    }};
    private static final String[] lunarPhases = {
            "NEW \nMOON",
            "WAXING \nCRESCENT",
            "FIRST \nQUARTER",
            "WAXING \nGIBBOUS",
            "FULL \nMOON",
            "WANING \nGIBBOUS",
            "LAST \nQUARTER",
            "WANING \nCRESCENT"
    };
    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
        Scene scene = new Scene(root, 2 * DIAL_RADIUS + 300, 2 * DIAL_RADIUS + 300, Color.BLACK); // Set background color to black
        primaryStage.setTitle("Metonic Dial");
        primaryStage.setScene(scene);
        Circle dial = new Circle(DIAL_RADIUS + 150, DIAL_RADIUS + 150, DIAL_RADIUS);
        dial.setFill(Color.BLACK);
        dial.setStroke(Color.WHITE);
        for (int i = 0; i < NUM_HOUR_LINES; i++) {
            double angle = Math.toRadians(360.0 / NUM_HOUR_LINES * i);
            double startX = DIAL_RADIUS + 150 + (DIAL_RADIUS - 20) * Math.cos(angle);
            double startY = DIAL_RADIUS + 150 + (DIAL_RADIUS - 20) * Math.sin(angle);
            double endX = DIAL_RADIUS + 150 + DIAL_RADIUS * Math.cos(angle);
            double endY = DIAL_RADIUS + 150 + DIAL_RADIUS * Math.sin(angle);
            Line hourLine = new Line(startX, startY, endX, endY);
            hourLine.setStroke(Color.WHITE);
            root.getChildren().add(hourLine);
        }
        Circle lunarIndicator = new Circle(DIAL_RADIUS + 150, DIAL_RADIUS + 150 - DIAL_RADIUS / 2, INDICATOR_RADIUS);
        lunarIndicator.setFill(Color.rgb(255, 215, 0));
        Line lunarNeedle = new Line();
        lunarNeedle.setStroke(Color.rgb(0, 206, 209));
        lunarNeedle.setStrokeWidth(2);
        Polyline pointer = createSpiralPointer();
        pointer.setStroke(Color.rgb(220, 20, 60));
        pointer.setStrokeWidth(2);
        Line needle = new Line();
        needle.setStroke(Color.rgb(220, 20, 60));
        needle.setStrokeWidth(2);
        Group lunarPhaseScale = createCircularScale(DIAL_RADIUS + 120, lunarPhases);
        Circle ringLunar = new Circle(DIAL_RADIUS + 150, DIAL_RADIUS + 150, DIAL_RADIUS + 100);
        ringLunar.setFill(null);
        ringLunar.setStroke(Color.WHITE);
        Circle additionalRing = new Circle(DIAL_RADIUS + 150, DIAL_RADIUS + 150, DIAL_RADIUS + 145);
        additionalRing.setFill(null);
        additionalRing.setStroke(Color.WHITE);
        Group corinthianScale = createCircularScale(DIAL_RADIUS + 70, corinthianMonths.values().toArray(new String[0]));
        Circle ringCorinthian = new Circle(DIAL_RADIUS + 150, DIAL_RADIUS + 150, DIAL_RADIUS + 60);
        ringCorinthian.setFill(null);
        ringCorinthian.setStroke(Color.WHITE);
        LocalTime currentTime = LocalTime.now();
        int currentHour = currentTime.getHour();
        pointer.setRotate(360.0 * currentHour / 12);
        double angle = 360.0 * currentHour / 12;
        updateNeedlePosition(needle, angle, DIAL_RADIUS - 50);
        LocalDate currentDate = LocalDate.now();
        LocalDate lastNewMoon = LocalDate.of(2000, 3, 24); // Last new moon before April 2024
        long daysSinceLastNewMoon = java.time.temporal.ChronoUnit.DAYS.between(lastNewMoon, currentDate);
        int lunarCycleDays = 29; // Average lunar cycle length in days
        int phaseIndex = (int) ((daysSinceLastNewMoon % lunarCycleDays) / (double) lunarCycleDays * 8);
        double lunarPhaseAngle = 360.0 / 8 * phaseIndex;
        updateNeedlePosition(lunarNeedle, lunarPhaseAngle, LUNAR_NEEDLE_LENGTH);
        lunarIndicator.setRotate(360.0 * currentHour / 12);
        Text timeText = new Text(currentTime.toString());
        timeText.setFont(Font.font("Courier New", FontWeight.BOLD, 14));
        timeText.setX(DIAL_RADIUS + 160 - timeText.getBoundsInLocal().getWidth() / 2);
        timeText.setY(DIAL_RADIUS + 150 + DIAL_RADIUS / 2 + 20);
        timeText.setFill(Color.WHITE);
        root.getChildren().addAll(dial, lunarIndicator, lunarNeedle, pointer, needle,
                ringLunar, additionalRing, lunarPhaseScale, ringCorinthian, corinthianScale, timeText);
        RotateTransition pointerRotation = new RotateTransition(Duration.seconds(10), pointer);
        pointerRotation.setByAngle(360.0);
        pointerRotation.setCycleCount(Animation.INDEFINITE);
        pointerRotation.setInterpolator(Interpolator.LINEAR);
        pointerRotation.play();
        RotateTransition needleUpdate = new RotateTransition(Duration.minutes(1), needle);
        needleUpdate.setByAngle(30); // Move by one hour
        needleUpdate.setCycleCount(Animation.INDEFINITE);
        needleUpdate.setInterpolator(Interpolator.LINEAR);
        needleUpdate.play();
        RotateTransition lunarNeedleUpdate = new RotateTransition(Duration.minutes(29.53 * 24 * 60), lunarNeedle);
        lunarNeedleUpdate.setByAngle(360.0 / lunarPhases.length);
        lunarNeedleUpdate.setCycleCount(Animation.INDEFINITE);
        lunarNeedleUpdate.setInterpolator(Interpolator.LINEAR);
        lunarNeedleUpdate.play();
        primaryStage.show();
    }
    private Polyline createSpiralPointer() {
        double pointerRadius = DIAL_RADIUS - 30;
        Polyline pointer = new Polyline();
        for (int i = 0; i <= NUM_ROTATIONS * 360; i += 5) {
            double angle = Math.toRadians(i);
            double x = DIAL_RADIUS + 150 + pointerRadius * Math.cos(angle) * (1 + 0.1 * i / 360);
            double y = DIAL_RADIUS + 150 + pointerRadius * Math.sin(angle) * (1 + 0.1 * i / 360);
            pointer.getPoints().addAll(x, y);
        }
        return pointer;
    }
    private void updateNeedlePosition(Line needle, double angle, double length) {
        double x = DIAL_RADIUS + 150 + (length) * Math.cos(Math.toRadians(angle));
        double y = DIAL_RADIUS + 150 + (length) * Math.sin(Math.toRadians(angle));
        needle.setStartX(DIAL_RADIUS + 150);
        needle.setStartY(DIAL_RADIUS + 150);
        needle.setEndX(x);
        needle.setEndY(y);
    }
    private Group createCircularScale(double scaleRadius, String[] scaleLabels) {
        Group scaleGroup = new Group();
        double scaleStartAngle = -90;
        double scaleEndAngle = 360.0 / scaleLabels.length;
        for (int i = 0; i < scaleLabels.length; i++) {
            Text labelText = new Text(scaleLabels[i]);
            labelText.setFill(Color.WHITE);
            labelText.setFont(Font.font("Georgia", FontWeight.BOLD, 14));
            double angle = Math.toRadians(scaleStartAngle + scaleEndAngle * i);
            double x = DIAL_RADIUS + 150 + scaleRadius * Math.cos(angle);
            double y = DIAL_RADIUS + 150 + scaleRadius * Math.sin(angle);
            labelText.setX(x - labelText.getBoundsInLocal().getWidth() / 2);
            labelText.setY(y + labelText.getBoundsInLocal().getHeight() / 34);
            labelText.setRotate(Math.toDegrees(angle) + 90);
            scaleGroup.getChildren().add(labelText);
        }
        return scaleGroup;
    }
    public static void main(String[] args) {
        launch(args);
    }
}
