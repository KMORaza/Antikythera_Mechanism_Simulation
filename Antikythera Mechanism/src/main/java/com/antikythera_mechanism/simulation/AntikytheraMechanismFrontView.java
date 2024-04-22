package com.antikythera_mechanism.simulation;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
public class AntikytheraMechanismFrontView extends Application {
    private static final int WINDOW_WIDTH = 900;
    private static final int WINDOW_HEIGHT = 900;
    private static final int CENTER_X = WINDOW_WIDTH / 2;
    private static final int CENTER_Y = WINDOW_HEIGHT / 2;
    private static final int SUN_RADIUS = 23;
    private static final int PLANET_RADIUS = 13;
    private static final int MOON_RADIUS = 6;
    private static final int ZODIAC_RADIUS = 342;
    private static final double ZODIAC_TEXT_OFFSET = 20;
    private double scale;
    // Orbital speeds of the planets in km/s
    private double[] ORBITAL_SPEED = {0, 47.87, 35.02, 29.78, 24.07, 13.07, 9.69, 6.81, 4.74};
    private double[] ORBITAL_ANGLE = new double[ORBITAL_SPEED.length];
    private int[] REVOLUTION_COUNTS = new int[ORBITAL_SPEED.length];
    private Color[] PLANET_APPEARANCE = {
            Color.WHITE,                          // Sun
            Color.rgb(224, 224, 224),   // Mercury
            Color.rgb(255, 153, 51),    // Venus
            Color.rgb(51, 255, 153),    // Earth
            Color.rgb(204, 0, 0),       // Mars
            Color.rgb(255, 229, 204),   // Jupiter
            Color.rgb(204, 102, 0),     // Saturn
            Color.rgb(0, 255, 255),     // Uranus
            Color.rgb(0, 0, 255)        // Neptune
    };
    // Greek inscriptions of the members of the zodiac
    private static final String[] ZODIAC_INSCRIPTIONS = {
            "ΚΡΙΟΣ",     // Aries
            "ΤΑΥΡΟΣ",    // Taurus
            "ΔΙΔΥΜΟΙ",   // Gemini
            "ΚΑΡΚΙΝΟΣ",  // Cancer
            "ΛΕΩΝ",      // Leo
            "ΠΑΡΘΕΝΟΣ",  // Virgo
            "ΧΗΛΑΙ",     // Libra
            "ΣΚΟΡΠΙΟΣ",  // Scorpio
            "ΤΟΞΟΤΗΣ",   // Sagittarius
            "ΑΙΓΟΚΕΡΩΣ", // Capricorn
            "ΥΔΡΟΧΟΟΣ",  // Aquarius
            "ΙΧΘΥΕΣ"     // Pisces
    };
    private static final String[] MONTH_NAMES = {
            "JANUARY",
            "FEBRUARY",
            "MARCH",
            "APRIL",
            "MAY",
            "JUNE",
            "JULY",
            "AUGUST",
            "SEPTEMBER",
            "OCTOBER",
            "NOVEMBER",
            "DECEMBER"
    };
    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
        Canvas canvas = new Canvas(WINDOW_WIDTH, WINDOW_HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        root.getChildren().add(canvas);
        primaryStage.setScene(new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT));
        primaryStage.show();
        primaryStage.setTitle("Antikythera Mechanism - Front View");
        scale = Math.min(WINDOW_WIDTH, WINDOW_HEIGHT) / 2 / 600.0;
        for (int i = 0; i < ORBITAL_ANGLE.length; i++) {
            ORBITAL_ANGLE[i] = 0;
            REVOLUTION_COUNTS[i] = 0;
        }
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                for (int i = 0; i < ORBITAL_ANGLE.length; i++) {
                    ORBITAL_ANGLE[i] += ORBITAL_SPEED[i] * 1000 / (3600 * 24 * 365);
                    if (ORBITAL_ANGLE[i] >= 1) {
                        REVOLUTION_COUNTS[i]++;
                        ORBITAL_ANGLE[i] -= 1;
                    }
                }
                draw(gc);
            }
        }.start();
    }
    private void draw(GraphicsContext gc) {
        gc.clearRect(0, 0, 500, 500);
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
        gc.setStroke(Color.GRAY);
        for (int x = 0; x <= WINDOW_WIDTH; x += 50) {
            gc.strokeLine(x, 0, x, WINDOW_HEIGHT);
        }
        for (int y = 0; y <= WINDOW_HEIGHT; y += 50) {
            gc.strokeLine(0, y, WINDOW_WIDTH, y);
        }
        drawZodiacDial(gc, CENTER_X, CENTER_Y, ZODIAC_RADIUS);
        drawPlanet(gc, ORBITAL_ANGLE[3], ZODIAC_RADIUS, Color.rgb(255,215,0), REVOLUTION_COUNTS[3]);
        double[] orbitRadii = {0, 50, 100, 150, 200, 250, 300, 350, 400};
        for (int i = 0; i < orbitRadii.length; i++) {
            drawOrbit(gc, CENTER_X, CENTER_Y, orbitRadii[i] * scale, Color.WHITE); // Draw orbit (changed color to white)
            if (i == 0) {
                drawSun(gc);
            } else {
                if (i == 3) { // Earth's orbit
                    drawMoon(gc, ORBITAL_ANGLE[i], orbitRadii[i] * scale, REVOLUTION_COUNTS[i]);
                }
                if (i == 4) { // Mars's orbit
                    drawPhobos(gc, ORBITAL_ANGLE[i], orbitRadii[i] * scale, REVOLUTION_COUNTS[i]);
                    drawDeimos(gc, ORBITAL_ANGLE[i], orbitRadii[i] * scale, REVOLUTION_COUNTS[i]);
                }
                if (i == 5) { // Jupiter's orbit
                    drawIo(gc, ORBITAL_ANGLE[i], orbitRadii[i] * scale, REVOLUTION_COUNTS[i]);
                    drawEuropa(gc, ORBITAL_ANGLE[i], orbitRadii[i] * scale, REVOLUTION_COUNTS[i]);
                    drawGanymede(gc, ORBITAL_ANGLE[i], orbitRadii[i] * scale, REVOLUTION_COUNTS[i]);
                    drawCallisto(gc, ORBITAL_ANGLE[i], orbitRadii[i] * scale, REVOLUTION_COUNTS[i]);
                }
                if (i == 6) { // Saturn's orbit
                    drawTitan(gc, ORBITAL_ANGLE[i], orbitRadii[i] * scale, REVOLUTION_COUNTS[i]);
                    drawEnceladus(gc, ORBITAL_ANGLE[i], orbitRadii[i] * scale, REVOLUTION_COUNTS[i]);
                    drawRhea(gc, ORBITAL_ANGLE[i], orbitRadii[i] * scale, REVOLUTION_COUNTS[i]);
                    drawDione(gc, ORBITAL_ANGLE[i], orbitRadii[i] * scale, REVOLUTION_COUNTS[i]);
                    drawIapetus(gc, ORBITAL_ANGLE[i], orbitRadii[i] * scale, REVOLUTION_COUNTS[i]);
                    drawJanusAndEpimetheus(gc, ORBITAL_ANGLE[i], orbitRadii[i] * scale, REVOLUTION_COUNTS[i]);
                }
                if (i == 7) { // Uranus's orbit
                    drawMiranda(gc, ORBITAL_ANGLE[i], orbitRadii[i] * scale, REVOLUTION_COUNTS[i]);
                    drawAriel(gc, ORBITAL_ANGLE[i], orbitRadii[i] * scale, REVOLUTION_COUNTS[i]);
                    drawUmbriel(gc, ORBITAL_ANGLE[i], orbitRadii[i] * scale, REVOLUTION_COUNTS[i]);
                    drawTitania(gc, ORBITAL_ANGLE[i], orbitRadii[i] * scale, REVOLUTION_COUNTS[i]);
                    drawOberon(gc, ORBITAL_ANGLE[i], orbitRadii[i] * scale, REVOLUTION_COUNTS[i]);
                }
                if (i == 8) { // Neptune's orbit
                    drawTriton(gc, ORBITAL_ANGLE[i], orbitRadii[i] * scale, REVOLUTION_COUNTS[i]);
                    drawProteus(gc, ORBITAL_ANGLE[i], orbitRadii[i] * scale, REVOLUTION_COUNTS[i]);
                    drawNereid(gc, ORBITAL_ANGLE[i], orbitRadii[i] * scale, REVOLUTION_COUNTS[i]);
                }
                drawPlanet(gc, ORBITAL_ANGLE[i], orbitRadii[i] * scale, PLANET_APPEARANCE[i], REVOLUTION_COUNTS[i]);
            }
        }
        double earthAngle = ORBITAL_ANGLE[3];
        double earthX = CENTER_X + orbitRadii[3] * scale * Math.cos(earthAngle * 2 * Math.PI);
        double earthY = CENTER_Y + orbitRadii[3] * scale * Math.sin(earthAngle * 2 * Math.PI);
        double calendarScaleX = CENTER_X + 373 * Math.cos(earthAngle * 2.5 * Math.PI);
        double calendarScaleY = CENTER_Y + 373 * Math.sin(earthAngle * 2.5 * Math.PI);
        gc.setLineWidth(2);
        gc.setStroke(Color.rgb(244,164,96));
        gc.strokeLine(earthX, earthY, calendarScaleX, calendarScaleY);
        drawSolarCalendar(gc, CENTER_X, CENTER_Y, 373);
        String pointerSymbol = "▲";
        gc.setFill(Color.rgb(244,164,96));
        gc.setFont(Font.font("Verdana", FontWeight.BOLD, 25));
        gc.fillText(pointerSymbol, calendarScaleX - 5, calendarScaleY + 5);
        drawLunarPhaseIndicator(gc, CENTER_X, CENTER_Y, 400);
        double lunarPhaseIndicatorX = CENTER_X + 420 * Math.cos(earthAngle * 3 * Math.PI);
        double lunarPhaseIndicatorY = CENTER_Y + 420 * Math.sin(earthAngle * 3 * Math.PI);
        gc.setStroke(Color.LIGHTGRAY);
        gc.setLineWidth(1);
        gc.strokeLine(earthX, earthY, calendarScaleX, calendarScaleY);
        gc.setStroke(Color.rgb(255,255,153));
        gc.setLineWidth(1);
        gc.strokeLine(earthX, earthY, lunarPhaseIndicatorX, lunarPhaseIndicatorY);
        double symbolSize = 10;
        gc.setFont(Font.font("Verdana", FontWeight.BOLD, 25));
        gc.setFill(Color.rgb(255,255,153));
        gc.fillText("◆", lunarPhaseIndicatorX - symbolSize / 2, lunarPhaseIndicatorY + symbolSize / 2);
    }
    private void drawOrbit(GraphicsContext gc, double centerX, double centerY, double radius, Color color) {
        gc.setStroke(color);
        gc.setFill(null);
        gc.strokeOval(centerX - radius, centerY - radius, 2 * radius, 2 * radius);
    }
    private void drawSun(GraphicsContext gc) {
        gc.setFill(Color.WHITE);
        gc.fillOval(CENTER_X - SUN_RADIUS / 2, CENTER_Y - SUN_RADIUS / 2, SUN_RADIUS, SUN_RADIUS);
    }
    private void drawPlanet(GraphicsContext gc, double angle, double radius, Color color, int revolutionCount) {
        double x = CENTER_X + radius * Math.cos(angle * 2 * Math.PI);
        double y = CENTER_Y + radius * Math.sin(angle * 2 * Math.PI);
        gc.setStroke(Color.FLORALWHITE);
        gc.setFill(color);
        gc.strokeOval(x - PLANET_RADIUS / 2, y - PLANET_RADIUS / 2, PLANET_RADIUS, PLANET_RADIUS);
        gc.fillOval(x - PLANET_RADIUS / 2, y - PLANET_RADIUS / 2, PLANET_RADIUS, PLANET_RADIUS);
        gc.setFont(Font.font("Consolas", 16));
        gc.strokeLine(CENTER_X, CENTER_Y, x, y);
        gc.setFill(Color.WHITE);
        gc.fillText("Rev:" + revolutionCount, x + PLANET_RADIUS, y);
    }
    private void drawMoon(GraphicsContext gc, double earthAngle, double earthRadius, int earthRevolutionCount) {
        // Assuming Moon's orbit radius is 30% of Earth's orbit radius
        double moonRadius = earthRadius * 0.3;
        double moonAngle = earthAngle * 13.3686; // Moon's orbit period is about 27.3 days, Earth's is 365.25 days
        double x = CENTER_X + earthRadius * Math.cos(earthAngle * 2 * Math.PI) +
                moonRadius * Math.cos(moonAngle * 2 * Math.PI);
        double y = CENTER_Y + earthRadius * Math.sin(earthAngle * 2 * Math.PI) +
                moonRadius * Math.sin(moonAngle * 2 * Math.PI);
        gc.setStroke(Color.LIGHTGRAY);
        gc.setFill(Color.LIGHTGRAY);
        gc.strokeOval(x - MOON_RADIUS / 2, y - MOON_RADIUS / 2, MOON_RADIUS, MOON_RADIUS);
        gc.fillOval(x - MOON_RADIUS / 2, y - MOON_RADIUS / 2, MOON_RADIUS, MOON_RADIUS);
        gc.strokeLine(CENTER_X + earthRadius * Math.cos(earthAngle * 2 * Math.PI),
                CENTER_Y + earthRadius * Math.sin(earthAngle * 2 * Math.PI), x, y);
    }
    private void drawPhobos(GraphicsContext gc, double marsAngle, double marsRadius, int marsRevolutionCount) {
        // Assuming Phobos's orbit radius is 40% of Mars's orbit radius
        double phobosRadius = marsRadius * 0.4;
        double phobosAngle = marsAngle * 0.3; // Phobos's orbit period is about 0.3 days, Mars's is about 687 days
        double x = CENTER_X + marsRadius * Math.cos(marsAngle * 2 * Math.PI) +
                phobosRadius * Math.cos(phobosAngle * 2 * Math.PI);
        double y = CENTER_Y + marsRadius * Math.sin(marsAngle * 2 * Math.PI) +
                phobosRadius * Math.sin(phobosAngle * 2 * Math.PI);
        gc.setStroke(Color.LIGHTGRAY);
        gc.setFill(Color.LIGHTGRAY);
        gc.strokeOval(x - MOON_RADIUS / 2, y - MOON_RADIUS / 2, MOON_RADIUS, MOON_RADIUS);
        gc.fillOval(x - MOON_RADIUS / 2, y - MOON_RADIUS / 2, MOON_RADIUS, MOON_RADIUS);
        gc.strokeLine(CENTER_X + marsRadius * Math.cos(marsAngle * 2 * Math.PI),
                CENTER_Y + marsRadius * Math.sin(marsAngle * 2 * Math.PI), x, y);
    }
    private void drawDeimos(GraphicsContext gc, double marsAngle, double marsRadius, int marsRevolutionCount) {
        // Assuming Deimos's orbit radius is 60% of Mars's orbit radius
        double deimosRadius = marsRadius * 0.6;
        double deimosAngle = marsAngle * 0.75; // Deimos's orbit period is about 0.75 days, Mars's is about 687 days
        double x = CENTER_X + marsRadius * Math.cos(marsAngle * 2 * Math.PI) +
                deimosRadius * Math.cos(deimosAngle * 2 * Math.PI);
        double y = CENTER_Y + marsRadius * Math.sin(marsAngle * 2 * Math.PI) +
                deimosRadius * Math.sin(deimosAngle * 2 * Math.PI);
        gc.setStroke(Color.LIGHTGRAY);
        gc.setFill(Color.LIGHTGRAY);
        gc.strokeOval(x - MOON_RADIUS / 2, y - MOON_RADIUS / 2, MOON_RADIUS, MOON_RADIUS);
        gc.fillOval(x - MOON_RADIUS / 2, y - MOON_RADIUS / 2, MOON_RADIUS, MOON_RADIUS);
        gc.strokeLine(CENTER_X + marsRadius * Math.cos(marsAngle * 2 * Math.PI),
                CENTER_Y + marsRadius * Math.sin(marsAngle * 2 * Math.PI), x, y);
    }
    private void drawIo(GraphicsContext gc, double jupiterAngle, double jupiterRadius, int jupiterRevolutionCount) {
        // Assuming Io's orbit radius is 20% of Jupiter's orbit radius
        double ioRadius = jupiterRadius * 0.2;
        double ioAngle = jupiterAngle * 0.0758; // Io's orbit period is about 1.77 days, Jupiter's is about 11.86 years
        double x = CENTER_X + jupiterRadius * Math.cos(jupiterAngle * 2 * Math.PI) +
                ioRadius * Math.cos(ioAngle * 2 * Math.PI);
        double y = CENTER_Y + jupiterRadius * Math.sin(jupiterAngle * 2 * Math.PI) +
                ioRadius * Math.sin(ioAngle * 2 * Math.PI);
        gc.setStroke(Color.LIGHTGRAY);
        gc.setFill(Color.LIGHTGRAY);
        gc.strokeOval(x - MOON_RADIUS / 2, y - MOON_RADIUS / 2, MOON_RADIUS, MOON_RADIUS);
        gc.fillOval(x - MOON_RADIUS / 2, y - MOON_RADIUS / 2, MOON_RADIUS, MOON_RADIUS);
        gc.strokeLine(CENTER_X + jupiterRadius * Math.cos(jupiterAngle * 2 * Math.PI),
                CENTER_Y + jupiterRadius * Math.sin(jupiterAngle * 2 * Math.PI), x, y);
    }
    private void drawEuropa(GraphicsContext gc, double jupiterAngle, double jupiterRadius, int jupiterRevolutionCount) {
        // Assuming Europa's orbit radius is 40% of Jupiter's orbit radius
        double europaRadius = jupiterRadius * 0.4;
        double europaAngle = jupiterAngle * 0.0997; // Europa's orbit period is about 3.55 days, Jupiter's is about 11.86 years
        double x = CENTER_X + jupiterRadius * Math.cos(jupiterAngle * 2 * Math.PI) +
                europaRadius * Math.cos(europaAngle * 2 * Math.PI);
        double y = CENTER_Y + jupiterRadius * Math.sin(jupiterAngle * 2 * Math.PI) +
                europaRadius * Math.sin(europaAngle * 2 * Math.PI);
        gc.setStroke(Color.LIGHTGRAY);
        gc.setFill(Color.LIGHTGRAY);
        gc.strokeOval(x - MOON_RADIUS / 2, y - MOON_RADIUS / 2, MOON_RADIUS, MOON_RADIUS);
        gc.fillOval(x - MOON_RADIUS / 2, y - MOON_RADIUS / 2, MOON_RADIUS, MOON_RADIUS);
        gc.strokeLine(CENTER_X + jupiterRadius * Math.cos(jupiterAngle * 2 * Math.PI),
                CENTER_Y + jupiterRadius * Math.sin(jupiterAngle * 2 * Math.PI), x, y);
    }
    private void drawGanymede(GraphicsContext gc, double jupiterAngle, double jupiterRadius, int jupiterRevolutionCount) {
        // Assuming Ganymede's orbit radius is 60% of Jupiter's orbit radius
        double ganymedeRadius = jupiterRadius * 0.6;
        double ganymedeAngle = jupiterAngle * 0.199; // Ganymede's orbit period is about 7.15 days, Jupiter's is about 11.86 years
        double x = CENTER_X + jupiterRadius * Math.cos(jupiterAngle * 2 * Math.PI) +
                ganymedeRadius * Math.cos(ganymedeAngle * 2 * Math.PI);
        double y = CENTER_Y + jupiterRadius * Math.sin(jupiterAngle * 2 * Math.PI) +
                ganymedeRadius * Math.sin(ganymedeAngle * 2 * Math.PI);
        gc.setStroke(Color.LIGHTGRAY);
        gc.setFill(Color.LIGHTGRAY);
        gc.strokeOval(x - MOON_RADIUS / 2, y - MOON_RADIUS / 2, MOON_RADIUS, MOON_RADIUS);
        gc.fillOval(x - MOON_RADIUS / 2, y - MOON_RADIUS / 2, MOON_RADIUS, MOON_RADIUS);
        gc.strokeLine(CENTER_X + jupiterRadius * Math.cos(jupiterAngle * 2 * Math.PI),
                CENTER_Y + jupiterRadius * Math.sin(jupiterAngle * 2 * Math.PI), x, y);
    }
    private void drawCallisto(GraphicsContext gc, double jupiterAngle, double jupiterRadius, int jupiterRevolutionCount) {
        // Assuming Callisto's orbit radius is 80% of Jupiter's orbit radius
        double callistoRadius = jupiterRadius * 0.8;
        double callistoAngle = jupiterAngle * 0.398; // Callisto's orbit period is about 16.69 days, Jupiter's is about 11.86 years
        double x = CENTER_X + jupiterRadius * Math.cos(jupiterAngle * 2 * Math.PI) +
                callistoRadius * Math.cos(callistoAngle * 2 * Math.PI);
        double y = CENTER_Y + jupiterRadius * Math.sin(jupiterAngle * 2 * Math.PI) +
                callistoRadius * Math.sin(callistoAngle * 2 * Math.PI);
        gc.setStroke(Color.LIGHTGRAY);
        gc.setFill(Color.LIGHTGRAY);
        gc.strokeOval(x - MOON_RADIUS / 2, y - MOON_RADIUS / 2, MOON_RADIUS, MOON_RADIUS);
        gc.fillOval(x - MOON_RADIUS / 2, y - MOON_RADIUS / 2, MOON_RADIUS, MOON_RADIUS);
        gc.strokeLine(CENTER_X + jupiterRadius * Math.cos(jupiterAngle * 2 * Math.PI),
                CENTER_Y + jupiterRadius * Math.sin(jupiterAngle * 2 * Math.PI), x, y);
    }
    private void drawTitan(GraphicsContext gc, double saturnAngle, double saturnRadius, int saturnRevolutionCount) {
        // Assuming Titan's orbit radius is 75% of Saturn's orbit radius
        double titanRadius = saturnRadius * 0.75;
        double titanAngle = saturnAngle * (16.0 / 29.5); // Titan's orbital period is approximately 16 days, Saturn's is approximately 29.5 years
        double x = CENTER_X + saturnRadius * Math.cos(saturnAngle * 2 * Math.PI) +
                titanRadius * Math.cos(titanAngle * 2 * Math.PI);
        double y = CENTER_Y + saturnRadius * Math.sin(saturnAngle * 2 * Math.PI) +
                titanRadius * Math.sin(titanAngle * 2 * Math.PI);
        gc.setStroke(Color.LIGHTGRAY);
        gc.setFill(Color.LIGHTGRAY);
        gc.strokeOval(x - MOON_RADIUS / 2, y - MOON_RADIUS / 2, MOON_RADIUS, MOON_RADIUS);
        gc.fillOval(x - MOON_RADIUS / 2, y - MOON_RADIUS / 2, MOON_RADIUS, MOON_RADIUS);
        gc.strokeLine(CENTER_X + saturnRadius * Math.cos(saturnAngle * 2 * Math.PI),
                CENTER_Y + saturnRadius * Math.sin(saturnAngle * 2 * Math.PI), x, y);
    }
    private void drawEnceladus(GraphicsContext gc, double saturnAngle, double saturnRadius, int saturnRevolutionCount) {
        // Assuming Enceladus's orbit radius is 40% of Saturn's orbit radius
        double enceladusRadius = saturnRadius * 0.4;
        double enceladusAngle = saturnAngle * 0.011; // Enceladus's orbit period is about 1.37 days, Saturn's is about 29.5 years
        double x = CENTER_X + saturnRadius * Math.cos(saturnAngle * 2 * Math.PI) +
                enceladusRadius * Math.cos(enceladusAngle * 2 * Math.PI);
        double y = CENTER_Y + saturnRadius * Math.sin(saturnAngle * 2 * Math.PI) +
                enceladusRadius * Math.sin(enceladusAngle * 2 * Math.PI);
        gc.setStroke(Color.LIGHTGRAY);
        gc.setFill(Color.LIGHTGRAY);
        gc.strokeOval(x - MOON_RADIUS / 2, y - MOON_RADIUS / 2, MOON_RADIUS, MOON_RADIUS);
        gc.fillOval(x - MOON_RADIUS / 2, y - MOON_RADIUS / 2, MOON_RADIUS, MOON_RADIUS);
        gc.strokeLine(CENTER_X + saturnRadius * Math.cos(saturnAngle * 2 * Math.PI),
                CENTER_Y + saturnRadius * Math.sin(saturnAngle * 2 * Math.PI), x, y);
    }
    private void drawRhea(GraphicsContext gc, double saturnAngle, double saturnRadius, int saturnRevolutionCount) {
        // Assuming Rhea's orbit radius is 60% of Saturn's orbit radius
        double rheaRadius = saturnRadius * 0.6;
        double rheaAngle = saturnAngle * (4.5 / 29.5); // Rhea's orbital period is approximately 4.5 days, Saturn's is approximately 29.5 years
        double x = CENTER_X + saturnRadius * Math.cos(saturnAngle * 2 * Math.PI) +
                rheaRadius * Math.cos(rheaAngle * 2 * Math.PI);
        double y = CENTER_Y + saturnRadius * Math.sin(saturnAngle * 2 * Math.PI) +
                rheaRadius * Math.sin(rheaAngle * 2 * Math.PI);
        gc.setStroke(Color.LIGHTGRAY);
        gc.setFill(Color.LIGHTGRAY);
        gc.strokeOval(x - MOON_RADIUS / 2, y - MOON_RADIUS / 2, MOON_RADIUS, MOON_RADIUS);
        gc.fillOval(x - MOON_RADIUS / 2, y - MOON_RADIUS / 2, MOON_RADIUS, MOON_RADIUS);
        gc.strokeLine(CENTER_X + saturnRadius * Math.cos(saturnAngle * 2 * Math.PI),
                CENTER_Y + saturnRadius * Math.sin(saturnAngle * 2 * Math.PI), x, y);
    }
    private void drawDione(GraphicsContext gc, double saturnAngle, double saturnRadius, int saturnRevolutionCount) {
        // Assuming Dione's orbit radius is 70% of Saturn's orbit radius
        double dioneRadius = saturnRadius * 0.7;
        double dioneAngle = saturnAngle * (2.7 / 29.5); // Dione's orbital period is approximately 2.7 days, Saturn's is approximately 29.5 years
        double x = CENTER_X + saturnRadius * Math.cos(saturnAngle * 2 * Math.PI) +
                dioneRadius * Math.cos(dioneAngle * 2 * Math.PI);
        double y = CENTER_Y + saturnRadius * Math.sin(saturnAngle * 2 * Math.PI) +
                dioneRadius * Math.sin(dioneAngle * 2 * Math.PI);
        gc.setStroke(Color.LIGHTGRAY);
        gc.setFill(Color.LIGHTGRAY);
        gc.strokeOval(x - MOON_RADIUS / 2, y - MOON_RADIUS / 2, MOON_RADIUS, MOON_RADIUS);
        gc.fillOval(x - MOON_RADIUS / 2, y - MOON_RADIUS / 2, MOON_RADIUS, MOON_RADIUS);
        gc.strokeLine(CENTER_X + saturnRadius * Math.cos(saturnAngle * 2 * Math.PI),
                CENTER_Y + saturnRadius * Math.sin(saturnAngle * 2 * Math.PI), x, y);
    }
    private void drawIapetus(GraphicsContext gc, double saturnAngle, double saturnRadius, int saturnRevolutionCount) {
        // Assuming Iapetus's orbit radius is 80% of Saturn's orbit radius
        double iapetusRadius = saturnRadius * 0.8;
        double iapetusAngle = saturnAngle * (79.3 / 29.5); // Iapetus's orbital period is approximately 79.3 days, Saturn's is approximately 29.5 years
        double x = CENTER_X + saturnRadius * Math.cos(saturnAngle * 2 * Math.PI) +
                iapetusRadius * Math.cos(iapetusAngle * 2 * Math.PI);
        double y = CENTER_Y + saturnRadius * Math.sin(saturnAngle * 2 * Math.PI) +
                iapetusRadius * Math.sin(iapetusAngle * 2 * Math.PI);
        gc.setStroke(Color.LIGHTGRAY);
        gc.setFill(Color.LIGHTGRAY);
        gc.strokeOval(x - MOON_RADIUS / 2, y - MOON_RADIUS / 2, MOON_RADIUS, MOON_RADIUS);
        gc.fillOval(x - MOON_RADIUS / 2, y - MOON_RADIUS / 2, MOON_RADIUS, MOON_RADIUS);
        gc.strokeLine(CENTER_X + saturnRadius * Math.cos(saturnAngle * 2 * Math.PI),
                CENTER_Y + saturnRadius * Math.sin(saturnAngle * 2 * Math.PI), x, y);
    }
    private void drawJanusAndEpimetheus(GraphicsContext gc, double saturnAngle, double saturnRadius, int saturnRevolutionCount) {
        // Assuming Janus and Epimetheus's orbit radius is 80% of Saturn's orbit radius
        double janusAndEpimetheusRadius = saturnRadius * 0.8;
        double janusAngle = saturnAngle * (0.695 / 0.601); // Janus's orbital period is approximately 0.695 days, Saturn's is approximately 29.5 years
        double epimetheusAngle = saturnAngle * (0.601 / 0.695); // Epimetheus's orbital period is approximately 0.601 days, Saturn's is approximately 29.5 years
        double janusX = CENTER_X + saturnRadius * Math.cos(saturnAngle * 2 * Math.PI) +
                janusAndEpimetheusRadius * Math.cos(janusAngle * 2 * Math.PI);
        double janusY = CENTER_Y + saturnRadius * Math.sin(saturnAngle * 2 * Math.PI) +
                janusAndEpimetheusRadius * Math.sin(janusAngle * 2 * Math.PI);
        double epimetheusX = CENTER_X + saturnRadius * Math.cos(saturnAngle * 2 * Math.PI) +
                janusAndEpimetheusRadius * Math.cos(epimetheusAngle * 2 * Math.PI);
        double epimetheusY = CENTER_Y + saturnRadius * Math.sin(saturnAngle * 2 * Math.PI) +
                janusAndEpimetheusRadius * Math.sin(epimetheusAngle * 2 * Math.PI);
        gc.setStroke(Color.LIGHTGRAY);
        gc.setFill(Color.LIGHTGRAY);
        gc.strokeOval(janusX - MOON_RADIUS / 2, janusY - MOON_RADIUS / 2, MOON_RADIUS, MOON_RADIUS);
        gc.fillOval(janusX - MOON_RADIUS / 2, janusY - MOON_RADIUS / 2, MOON_RADIUS, MOON_RADIUS);
        gc.strokeLine(CENTER_X + saturnRadius * Math.cos(saturnAngle * 2 * Math.PI),
                CENTER_Y + saturnRadius * Math.sin(saturnAngle * 2 * Math.PI), janusX, janusY);
        gc.strokeOval(epimetheusX - MOON_RADIUS / 2, epimetheusY - MOON_RADIUS / 2, MOON_RADIUS, MOON_RADIUS);
        gc.fillOval(epimetheusX - MOON_RADIUS / 2, epimetheusY - MOON_RADIUS / 2, MOON_RADIUS, MOON_RADIUS);
        gc.strokeLine(CENTER_X + saturnRadius * Math.cos(saturnAngle * 2 * Math.PI),
                CENTER_Y + saturnRadius * Math.sin(saturnAngle * 2 * Math.PI), epimetheusX, epimetheusY);
    }
    private void drawMiranda(GraphicsContext gc, double uranusAngle, double uranusRadius, int uranusRevolutionCount) {
        // Assuming Miranda's orbit radius is 90% of Uranus's orbit radius
        double mirandaRadius = uranusRadius * 0.9;
        double mirandaAngle = uranusAngle * (0.4135 / 84.02); // Miranda's orbital period is approximately 0.4135 days, Uranus's is approximately 84 years
        double x = CENTER_X + uranusRadius * Math.cos(uranusAngle * 2 * Math.PI) +
                mirandaRadius * Math.cos(mirandaAngle * 2 * Math.PI);
        double y = CENTER_Y + uranusRadius * Math.sin(uranusAngle * 2 * Math.PI) +
                mirandaRadius * Math.sin(mirandaAngle * 2 * Math.PI);
        gc.setStroke(Color.LIGHTGRAY);
        gc.setFill(Color.LIGHTGRAY);
        gc.strokeOval(x - MOON_RADIUS / 2, y - MOON_RADIUS / 2, MOON_RADIUS, MOON_RADIUS);
        gc.fillOval(x - MOON_RADIUS / 2, y - MOON_RADIUS / 2, MOON_RADIUS, MOON_RADIUS);
        gc.strokeLine(CENTER_X + uranusRadius * Math.cos(uranusAngle * 2 * Math.PI),
                CENTER_Y + uranusRadius * Math.sin(uranusAngle * 2 * Math.PI), x, y);
    }
    private void drawAriel(GraphicsContext gc, double uranusAngle, double uranusRadius, int uranusRevolutionCount) {
        // Assuming Ariel's orbit radius is 80% of Uranus's orbit radius
        double arielRadius = uranusRadius * 0.8;
        double arielAngle = uranusAngle * (2.52 / 84.02); // Ariel's orbital period is approximately 2.52 days, Uranus's is approximately 84 years
        double x = CENTER_X + uranusRadius * Math.cos(uranusAngle * 2 * Math.PI) +
                arielRadius * Math.cos(arielAngle * 2 * Math.PI);
        double y = CENTER_Y + uranusRadius * Math.sin(uranusAngle * 2 * Math.PI) +
                arielRadius * Math.sin(arielAngle * 2 * Math.PI);
        gc.setStroke(Color.LIGHTGRAY);
        gc.setFill(Color.LIGHTGRAY);
        gc.strokeOval(x - MOON_RADIUS / 2, y - MOON_RADIUS / 2, MOON_RADIUS, MOON_RADIUS);
        gc.fillOval(x - MOON_RADIUS / 2, y - MOON_RADIUS / 2, MOON_RADIUS, MOON_RADIUS);
        gc.strokeLine(CENTER_X + uranusRadius * Math.cos(uranusAngle * 2 * Math.PI),
                CENTER_Y + uranusRadius * Math.sin(uranusAngle * 2 * Math.PI), x, y);
    }
    private void drawUmbriel(GraphicsContext gc, double uranusAngle, double uranusRadius, int uranusRevolutionCount) {
        // Assuming Umbriel's orbit radius is 90% of Uranus's orbit radius
        double umbrielRadius = uranusRadius * 0.9;
        double umbrielAngle = uranusAngle * (4.14 / 84.02); // Umbriel's orbital period is approximately 4.14 days, Uranus's is approximately 84 years
        double x = CENTER_X + uranusRadius * Math.cos(uranusAngle * 2 * Math.PI) +
                umbrielRadius * Math.cos(umbrielAngle * 2 * Math.PI);
        double y = CENTER_Y + uranusRadius * Math.sin(uranusAngle * 2 * Math.PI) +
                umbrielRadius * Math.sin(umbrielAngle * 2 * Math.PI);
        gc.setStroke(Color.LIGHTGRAY);
        gc.setFill(Color.LIGHTGRAY);
        gc.strokeOval(x - MOON_RADIUS / 2, y - MOON_RADIUS / 2, MOON_RADIUS, MOON_RADIUS);
        gc.fillOval(x - MOON_RADIUS / 2, y - MOON_RADIUS / 2, MOON_RADIUS, MOON_RADIUS);
        gc.strokeLine(CENTER_X + uranusRadius * Math.cos(uranusAngle * 2 * Math.PI),
                CENTER_Y + uranusRadius * Math.sin(uranusAngle * 2 * Math.PI), x, y);
    }
    private void drawTitania(GraphicsContext gc, double uranusAngle, double uranusRadius, int uranusRevolutionCount) {
        // Assuming Titania's orbit radius is 100% of Uranus's orbit radius
        double titaniaRadius = uranusRadius;
        double titaniaAngle = uranusAngle * (8.71 / 84.02); // Titania's orbital period is approximately 8.71 days, Uranus's is approximately 84 years
        double x = CENTER_X + uranusRadius * Math.cos(uranusAngle * 2 * Math.PI) +
                titaniaRadius * Math.cos(titaniaAngle * 2 * Math.PI);
        double y = CENTER_Y + uranusRadius * Math.sin(uranusAngle * 2 * Math.PI) +
                titaniaRadius * Math.sin(titaniaAngle * 2 * Math.PI);
        gc.setStroke(Color.LIGHTGRAY);
        gc.setFill(Color.LIGHTGRAY);
        gc.strokeOval(x - MOON_RADIUS / 2, y - MOON_RADIUS / 2, MOON_RADIUS, MOON_RADIUS);
        gc.fillOval(x - MOON_RADIUS / 2, y - MOON_RADIUS / 2, MOON_RADIUS, MOON_RADIUS);
        gc.strokeLine(CENTER_X + uranusRadius * Math.cos(uranusAngle * 2 * Math.PI),
                CENTER_Y + uranusRadius * Math.sin(uranusAngle * 2 * Math.PI), x, y);
    }
    private void drawOberon(GraphicsContext gc, double uranusAngle, double uranusRadius, int uranusRevolutionCount) {
        // Assuming Oberon's orbit radius is 100% of Uranus's orbit radius
        double oberonRadius = uranusRadius;
        double oberonAngle = uranusAngle * (13.46 / 84.02); // Oberon's orbital period is approximately 13.46 days, Uranus's is approximately 84 years
        double x = CENTER_X + uranusRadius * Math.cos(uranusAngle * 2 * Math.PI) +
                oberonRadius * Math.cos(oberonAngle * 2 * Math.PI);
        double y = CENTER_Y + uranusRadius * Math.sin(uranusAngle * 2 * Math.PI) +
                oberonRadius * Math.sin(oberonAngle * 2 * Math.PI);
        gc.setStroke(Color.LIGHTGRAY);
        gc.setFill(Color.LIGHTGRAY);
        gc.strokeOval(x - MOON_RADIUS / 2, y - MOON_RADIUS / 2, MOON_RADIUS, MOON_RADIUS);
        gc.fillOval(x - MOON_RADIUS / 2, y - MOON_RADIUS / 2, MOON_RADIUS, MOON_RADIUS);
        gc.strokeLine(CENTER_X + uranusRadius * Math.cos(uranusAngle * 2 * Math.PI),
                CENTER_Y + uranusRadius * Math.sin(uranusAngle * 2 * Math.PI), x, y);
    }
    private void drawTriton(GraphicsContext gc, double neptuneAngle, double neptuneRadius, int neptuneRevolutionCount) {
        // Assuming Triton's orbit radius is 90% of Neptune's orbit radius
        double tritonRadius = neptuneRadius * 0.9;
        double tritonAngle = neptuneAngle * (5.877 / 164.8); // Triton's orbital period is approximately 5.877 days, Neptune's is approximately 164.8 years
        double x = CENTER_X + neptuneRadius * Math.cos(neptuneAngle * 2 * Math.PI) +
                tritonRadius * Math.cos(tritonAngle * 2 * Math.PI);
        double y = CENTER_Y + neptuneRadius * Math.sin(neptuneAngle * 2 * Math.PI) +
                tritonRadius * Math.sin(tritonAngle * 2 * Math.PI);
        gc.setStroke(Color.LIGHTGRAY);
        gc.setFill(Color.LIGHTGRAY);
        // Draw Triton
        gc.strokeOval(x - MOON_RADIUS / 2, y - MOON_RADIUS / 2, MOON_RADIUS, MOON_RADIUS);
        gc.fillOval(x - MOON_RADIUS / 2, y - MOON_RADIUS / 2, MOON_RADIUS, MOON_RADIUS);
        gc.strokeLine(CENTER_X + neptuneRadius * Math.cos(neptuneAngle * 2 * Math.PI),
                CENTER_Y + neptuneRadius * Math.sin(neptuneAngle * 2 * Math.PI), x, y);
    }
    private void drawProteus(GraphicsContext gc, double neptuneAngle, double neptuneRadius, int neptuneRevolutionCount) {
        // Assuming Proteus's orbit radius is 80% of Neptune's orbit radius
        double proteusRadius = neptuneRadius * 0.8;
        double proteusAngle = neptuneAngle * (1.122 / 5.875); // Proteus's orbital period is approximately 1.122 days, Neptune's is approximately 5.875 years
        double x = CENTER_X + neptuneRadius * Math.cos(neptuneAngle * 2 * Math.PI) +
                proteusRadius * Math.cos(proteusAngle * 2 * Math.PI);
        double y = CENTER_Y + neptuneRadius * Math.sin(neptuneAngle * 2 * Math.PI) +
                proteusRadius * Math.sin(proteusAngle * 2 * Math.PI);
        gc.setStroke(Color.LIGHTGRAY);
        gc.setFill(Color.LIGHTGRAY);
        gc.strokeOval(x - MOON_RADIUS / 2, y - MOON_RADIUS / 2, MOON_RADIUS, MOON_RADIUS);
        gc.fillOval(x - MOON_RADIUS / 2, y - MOON_RADIUS / 2, MOON_RADIUS, MOON_RADIUS);
        gc.strokeLine(CENTER_X + neptuneRadius * Math.cos(neptuneAngle * 2 * Math.PI),
                CENTER_Y + neptuneRadius * Math.sin(neptuneAngle * 2 * Math.PI), x, y);
    }
    private void drawNereid(GraphicsContext gc, double neptuneAngle, double neptuneRadius, int neptuneRevolutionCount) {
        // Assuming Nereid's orbit radius is 70% of Neptune's orbit radius
        double nereidRadius = neptuneRadius * 0.7;
        double nereidAngle = neptuneAngle * (360 / 360.13); // Nereid's orbital period is approximately 360 days, Neptune's is approximately 360.13 years
        double x = CENTER_X + neptuneRadius * Math.cos(neptuneAngle * 2 * Math.PI) +
                nereidRadius * Math.cos(nereidAngle * 2 * Math.PI);
        double y = CENTER_Y + neptuneRadius * Math.sin(neptuneAngle * 2 * Math.PI) +
                nereidRadius * Math.sin(nereidAngle * 2 * Math.PI);
        gc.setStroke(Color.LIGHTGRAY);
        gc.setFill(Color.LIGHTGRAY);
        gc.strokeOval(x - MOON_RADIUS / 2, y - MOON_RADIUS / 2, MOON_RADIUS, MOON_RADIUS);
        gc.fillOval(x - MOON_RADIUS / 2, y - MOON_RADIUS / 2, MOON_RADIUS, MOON_RADIUS);
        gc.strokeLine(CENTER_X + neptuneRadius * Math.cos(neptuneAngle * 2 * Math.PI),
                CENTER_Y + neptuneRadius * Math.sin(neptuneAngle * 2 * Math.PI), x, y);
    }
    private void drawZodiacDial(GraphicsContext gc, double centerX, double centerY, double radius) {
        int numInscriptions = ZODIAC_INSCRIPTIONS.length;
        double angleStep = 2 * Math.PI / numInscriptions;
        int numSubMarkings = 10;
        gc.setFont(Font.font("Georgia", 17));
        gc.setFill(Color.WHITE);
        for (int i = 0; i < numInscriptions; i++) {
            double angle = i * angleStep;
            double x = centerX + (radius - 40) * Math.cos(angle);
            double y = centerY + (radius - 40) * Math.sin(angle);
            gc.save();
            gc.translate(x, y);
            gc.rotate(Math.toDegrees(angle + Math.PI / 2));
            gc.fillText(ZODIAC_INSCRIPTIONS[i], -gc.getFont().getSize() * ZODIAC_INSCRIPTIONS[i].length() / 4, 0);
            gc.restore();
            double startX = centerX + (radius - 20) * Math.cos(angle);
            double startY = centerY + (radius - 20) * Math.sin(angle);
            double endX = centerX + (radius - 2) * Math.cos(angle);
            double endY = centerY + (radius - 2) * Math.sin(angle);
            gc.setStroke(Color.WHITE);
            gc.setLineWidth(4);
            gc.strokeLine(startX, startY, endX, endY);
            double subAngleStep = angleStep / (numSubMarkings + 1);
            for (int j = 1; j <= numSubMarkings; j++) {
                double subAngle = angle + j * subAngleStep;
                double subStartX = centerX + (radius - 17) * Math.cos(subAngle);
                double subStartY = centerY + (radius - 17) * Math.sin(subAngle);
                double subEndX = centerX + (radius - 1) * Math.cos(subAngle);
                double subEndY = centerY + (radius - 1) * Math.sin(subAngle);
                gc.setStroke(Color.rgb(0,206,209));
                gc.setLineWidth(2);
                gc.strokeLine(subStartX, subStartY, subEndX, subEndY);
            }
        }
        gc.setStroke(Color.rgb(0,206,209));
        gc.setLineWidth(1);
        gc.strokeOval(centerX - radius, centerY - radius, 2 * radius, 2 * radius);
    }
    private void drawSolarCalendar(GraphicsContext gc, double centerX, double centerY, double radius) {
        int numMonths = 12;
        double angleStep = 2 * Math.PI / numMonths;
        gc.setFont(Font.font("Georgia",17));
        gc.setFill(Color.WHITE);
        for (int i = 0; i < numMonths; i++) {
            double angle = i * angleStep;
            double x = centerX + (radius - 20) * Math.cos(angle);
            double y = centerY + (radius - 20) * Math.sin(angle);
            gc.save();
            gc.translate(x, y);
            gc.rotate(Math.toDegrees(angle + Math.PI / 2));
            gc.fillText(MONTH_NAMES[i], -gc.getFont().getSize() * MONTH_NAMES[i].length() / 4, 0);
            gc.restore();
        }
        gc.setStroke(Color.rgb(244,164,96));
        gc.setLineWidth(1);
        gc.strokeOval(centerX - radius, centerY - radius, 2 * radius, 2 * radius);
    }
    private void drawLunarPhaseIndicator(GraphicsContext gc, double centerX, double centerY, double radius) {
        String[] lunarPhases = {
                "NEW MOON",
                "WAXING CRESCENT",
                "FIRST QUARTER",
                "WAXING GIBBOUS",
                "FULL MOON",
                "WANING GIBBOUS",
                "LAST QUARTER",
                "WANING CRESCENT"
        };
        int numPhases = lunarPhases.length;
        double angleStep = 2 * Math.PI / numPhases;
        gc.setFont(Font.font("Georgia",17));
        gc.setFill(Color.WHITE);
        for (int i = 0; i < numPhases; i++) {
            double angle = i * angleStep;
            double x = centerX + (radius - 15) * Math.cos(angle);
            double y = centerY + (radius - 15) * Math.sin(angle);
            double startX = centerX + (radius + 5) * Math.cos(angle);
            double startY = centerY + (radius + 5) * Math.sin(angle);
            double endX = centerX + (radius + 20) * Math.cos(angle);
            double endY = centerY + (radius + 20) * Math.sin(angle);
            double subMarkingRadius = radius + 10;
            gc.setStroke(Color.WHITE);
            gc.setLineWidth(3);
            gc.strokeLine(startX, startY, endX, endY);
            for (int j = 0; j < 20; j++) {
                double subAngle = angle + (j + 1) * angleStep / 20;
                double subStartX = centerX + subMarkingRadius * Math.cos(subAngle);
                double subStartY = centerY + subMarkingRadius * Math.sin(subAngle);
                double subEndX = centerX + (subMarkingRadius + 10) * Math.cos(subAngle);
                double subEndY = centerY + (subMarkingRadius + 10) * Math.sin(subAngle);
                gc.setStroke(Color.rgb(255,255,153));
                gc.setLineWidth(1);
                gc.strokeLine(subStartX, subStartY, subEndX, subEndY);
            }
            gc.save();
            gc.translate(x, y);
            gc.rotate(Math.toDegrees(angle + Math.PI / 2));
            gc.fillText(lunarPhases[i], -gc.getFont().getSize() * lunarPhases[i].length() / 4, 0);
            gc.restore();
        }
        gc.setStroke(Color.rgb(255,255,153));
        gc.setLineWidth(1);
        gc.strokeOval(centerX - radius - 20, centerY - radius - 20, 2 * (radius + 20), 2 * (radius + 20));
    }
    public static void main(String[] args) {
        launch(args);
    }
}