package com.antikythera_mechanism.simulation;
import javafx.animation.Animation;
import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
public class AntikytheraMechanismRearView extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setResizable(false);
        AnchorPane root = new AnchorPane();
        root.setStyle("-fx-background-color: black;");
        for (int i = 0; i < 800; i += 50) {
            for (int j = 0; j < 725; j += 50) {
                Line line = new Line(i, 0, i, 725);
                line.setStroke(Color.rgb(96, 96, 96));
                root.getChildren().add(line);
                line = new Line(0, j, 800, j);
                line.setStroke(Color.rgb(96, 96, 96));
                root.getChildren().add(line);
            }
        }
        Group metonicDial = createDial(200, Color.rgb(0, 0, 255)); // Metonic dial
        AnchorPane.setTopAnchor(metonicDial, 50.0);
        AnchorPane.setLeftAnchor(metonicDial, 50.0);
        root.getChildren().add(metonicDial);
        animateDial(metonicDial);
        Group sarosDial = createDial(200, Color.rgb(210,105,30)); // Saros dial
        AnchorPane.setTopAnchor(sarosDial, 50.0);
        AnchorPane.setRightAnchor(sarosDial, 50.0);
        root.getChildren().add(sarosDial);
        animateDial(sarosDial);
        // Subdials
        Group gamesDial = createDial(100, Color.rgb(255, 255, 255));
        AnchorPane.setTopAnchor(gamesDial, 300.0);
        AnchorPane.setLeftAnchor(gamesDial, 100.0);
        root.getChildren().add(gamesDial);
        animateDial(gamesDial);
        Group callippicDial = createDial(100, Color.rgb(255, 255, 255)); // Callippic dial
        AnchorPane.setTopAnchor(callippicDial, 300.0);
        AnchorPane.setLeftAnchor(callippicDial, 300.0);
        root.getChildren().add(callippicDial);
        animateDial(callippicDial);
        Group exeligmosDial = createExeligmosDial(100); // Exeligmos dial
        AnchorPane.setTopAnchor(exeligmosDial, 300.0);
        AnchorPane.setLeftAnchor(exeligmosDial, 500.0);
        root.getChildren().add(exeligmosDial);
        animateDial(exeligmosDial);
        // Metonic dial
        Group metonicSpiral = createMetonicSpiral(200);
        AnchorPane.setTopAnchor(metonicSpiral, 50.0);
        AnchorPane.setLeftAnchor(metonicSpiral, 50.0);
        root.getChildren().add(metonicSpiral);
        // Saros dial
        Group sarosSpiral = createSarosSpiral(200);
        AnchorPane.setTopAnchor(sarosSpiral, 50.0);
        AnchorPane.setRightAnchor(sarosSpiral, 50.0);
        root.getChildren().add(sarosSpiral);
        Scene scene = new Scene(root, 800, 725);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Antikythera Mechanism - Rear View");
        primaryStage.show();
    }
    private Group createDial(double radius, Color color) {
        Group dial = new Group();
        Circle outerCircle = new Circle(radius);
        outerCircle.setFill(Color.TRANSPARENT);
        outerCircle.setStroke(color);
        outerCircle.setStrokeWidth(4);
        outerCircle.setStrokeType(javafx.scene.shape.StrokeType.OUTSIDE);
        dial.getChildren().add(outerCircle);
        Line needle = new Line(0, 0, 0, -radius);
        needle.setStroke(Color.LIGHTGRAY);
        needle.setStrokeWidth(2);
        needle.setStrokeLineCap(StrokeLineCap.ROUND);
        dial.getChildren().add(needle);
        for (int i = 0; i < 360; i += 30) {
            Text mark = new Text(getRomanNumeral(i / 30));
            mark.setFont(Font.font(18));
            mark.setFill(Color.WHITE);
            double angle = Math.toRadians(i);
            double x = radius * Math.cos(angle);
            double y = radius * Math.sin(angle);
            mark.setTranslateX(x + radius - 10);
            mark.setTranslateY(y + radius + 6);
            dial.getChildren().add(mark);
        }
        return dial;
    }
    private String getRomanNumeral(int number) {
        String[] romanNumerals = {"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X", "XI", "XII"};
        return romanNumerals[number % 12];
    }
    private void animateDial(Group dial) {
        RotateTransition rt = new RotateTransition(Duration.seconds(10), dial);
        rt.setByAngle(360);
        rt.setCycleCount(Animation.INDEFINITE);
        rt.play();
    }
    private Group createMetonicSpiral(double radius) {
        Group spiral = new Group();
        double centerX = radius;
        double centerY = radius;
        double numRotations = 5;
        double numMonths = 235;
        for (int i = 0; i <= numMonths; i++) {
            double angle = Math.toRadians(i * (360 / (numMonths * numRotations)));
            double x = centerX + (radius - 5) * Math.cos(angle);
            double y = centerY + (radius - 5) * Math.sin(angle);
            Line segment = new Line(centerX, centerY, x, y);
            segment.setStroke(Color.WHITE);
            segment.setStrokeWidth(2);
            segment.setStrokeLineCap(StrokeLineCap.ROUND);
            spiral.getChildren().add(segment);
        }
        Circle follower = new Circle(5, Color.RED);
        spiral.getChildren().add(follower);
        RotateTransition rt = new RotateTransition(Duration.seconds(10), spiral);
        rt.setByAngle(360 * numRotations);
        rt.setCycleCount(Animation.INDEFINITE);
        rt.play();
        return spiral;
    }
    private Group createSarosSpiral(double radius) {
        Group spiral = new Group();
        double centerX = radius;
        double centerY = radius;
        double numRotations = 1;
        double numMonths = 223;
        for (int i = 0; i <= numMonths; i++) {
            double angle = Math.toRadians(i * (360 / (numMonths * numRotations)));
            double x = centerX + (radius - 5) * Math.cos(angle);
            double y = centerY + (radius - 5) * Math.sin(angle);
            Line segment = new Line(centerX, centerY, x, y);
            segment.setStroke(Color.WHITE);
            segment.setStrokeWidth(2);
            segment.setStrokeLineCap(StrokeLineCap.ROUND);
            spiral.getChildren().add(segment);
            // Glyphs for lunar and solar eclipses
            if (i % 10 == 0) {
                Text glyph = new Text(x, y, "⦿");
                glyph.setFont(Font.font("Georgia", FontWeight.BOLD,17));
                glyph.setFill(Color.rgb(32,178,170));
                spiral.getChildren().add(glyph);
            }
        }
        // Cardinal point lines
        for (int i = 0; i < 4; i++) {
            double angle = Math.toRadians(i * 90);
            double x1 = centerX + (radius - 10) * Math.cos(angle);
            double y1 = centerY + (radius - 10) * Math.sin(angle);
            double x2 = centerX + (radius - 20) * Math.cos(angle);
            double y2 = centerY + (radius - 20) * Math.sin(angle);
            Line line = new Line(x1, y1, x2, y2);
            line.setStroke(Color.WHITE);
            line.setStrokeWidth(2);
            spiral.getChildren().add(line);
        }
        Circle follower = new Circle(5, Color.RED);
        spiral.getChildren().add(follower);
        RotateTransition rt = new RotateTransition(Duration.seconds(10), spiral);
        rt.setByAngle(360 * numRotations);
        rt.setCycleCount(Animation.INDEFINITE);
        rt.play();
        return spiral;
    }
    private Group createExeligmosDial(double radius) {
        Group dial = new Group();
        Circle outerCircle = new Circle(radius);
        outerCircle.setFill(Color.TRANSPARENT);
        outerCircle.setStroke(Color.rgb(255, 255, 255));
        outerCircle.setStrokeWidth(4);
        outerCircle.setStrokeType(javafx.scene.shape.StrokeType.OUTSIDE);
        dial.getChildren().add(outerCircle);
        Text blankLabel = new Text(radius - 10, radius, "o");
        blankLabel.setFont(Font.font(18));
        blankLabel.setFill(Color.WHITE);
        dial.getChildren().add(blankLabel);
        Text hLabel = new Text(radius - 20, radius + 10, "H");
        hLabel.setFont(Font.font(18));
        hLabel.setFill(Color.WHITE);
        dial.getChildren().add(hLabel);
        Text iLabel = new Text(radius + 10, radius - 10, "Iϛ");
        iLabel.setFont(Font.font(18));
        iLabel.setFill(Color.WHITE);
        dial.getChildren().add(iLabel);
        Circle follower = new Circle(10, Color.YELLOW);
        dial.getChildren().add(follower);
        RotateTransition rt = new RotateTransition(Duration.seconds(10), dial);
        rt.setByAngle(360);
        rt.setCycleCount(Animation.INDEFINITE);
        rt.play();
        return dial;
    }
    public static void main(String[] args) {
        launch(args);
    }
}
