package com.igorternyuk.seabattle;

import com.igorternyuk.seabattle.model.Cell;
import com.igorternyuk.seabattle.model.Game;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.awt.*;
import java.util.List;

import static com.igorternyuk.seabattle.model.Game.FIELD_SIZE;

/**
 * Created by igor on 23.03.18.
 */
public class Renderer {
    static final int CELL_SIZE = 40;
    private static final Font LARGE_FONT = new Font("Arial", 40);
    private static final Font SMALL_FONT = new Font("Arial", 30);

    static void renderField(final GraphicsContext gc, final Game game) {
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
        gc.setFill(Color.GRAY);
        gc.fillRect(0, 0, 2 * FIELD_SIZE * CELL_SIZE, FIELD_SIZE * CELL_SIZE);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        for(int y = 0; y <= FIELD_SIZE; ++y){
            gc.strokeLine(0,y * CELL_SIZE, 2 * FIELD_SIZE * CELL_SIZE, y * CELL_SIZE);
        }

        for(int x = 0; x <= 2 * FIELD_SIZE; ++x){
            gc.strokeLine(x * CELL_SIZE, 0, x * CELL_SIZE, FIELD_SIZE * CELL_SIZE);
        }

        gc.setStroke(Color.YELLOW);
        gc.setLineWidth(3);
        gc.strokeLine(FIELD_SIZE * CELL_SIZE, 0, FIELD_SIZE * CELL_SIZE, FIELD_SIZE * CELL_SIZE);
    }

    static void renderGameStatus(final GraphicsContext gc, final Color color, final Game game) {
        gc.setFill(color);
        gc.setFont(LARGE_FONT);
        gc.fillText(game.getGameState().toString(), 260, CELL_SIZE * FIELD_SIZE + 50);
    }


    static void renderHumanShots(final GraphicsContext gc, final Game game) {
        final List<Point> shots = game.getHumanPlayer().getShots();
        shots.forEach(shot -> {
            final Point pos = new Point(shot.x * CELL_SIZE, shot.y * CELL_SIZE);
            renderCell(gc, pos, Color.CYAN);
        });
    }

    static void renderComputerShots(final GraphicsContext gc, final Game game) {
        final List<Point> shots = game.getComputerPlayer().getShots();
        shots.forEach(shot -> {
            final Point pos = new Point(shot.x * CELL_SIZE + FIELD_SIZE * CELL_SIZE, shot.y * CELL_SIZE);
            renderCell(gc, pos, Color.CYAN);
        });
    }

    private static void renderCell(final GraphicsContext gc, final Point pos, final Color color) {
        gc.setFill(color);
        gc.fillRect(pos.x, pos.y, CELL_SIZE, CELL_SIZE);
    }

    static void renderHumanShips(final GraphicsContext gc, final Game game) {
        gc.setFill(Color.GREEN.darker());
        game.getHumanPlayer().getNavy().getShips().forEach(ship -> {
            final List<Cell> cells = ship.getCells();
            cells.forEach(cell -> {
                final Point pos = new Point(cell.getPosition().x * CELL_SIZE  + FIELD_SIZE * CELL_SIZE,
                        cell.getPosition().y * CELL_SIZE);
                renderCell(gc, pos, cell.isAlive() ? Color.GREEN.darker() : Color.RED);
            });
        });
    }

    public static void renderComputerShips(final GraphicsContext gc, final Game game){
        game.getComputerPlayer().getNavy().getShips().forEach(ship -> {
            final List<Cell> cells = ship.getCells();
            cells.stream().filter(Cell::isDestroyed).forEach(cell -> {
                final Point pos = new Point(cell.getPosition().x * CELL_SIZE, cell.getPosition().y * CELL_SIZE);
                renderCell(gc, pos, cell.isAlive() ? Color.ALICEBLUE : Color.RED);
            });
        });
    }

    public static void renderPlayersHealth(final GraphicsContext gc, final Game game) {
        gc.setFont(SMALL_FONT);
        gc.setFill(Color.BLUEVIOLET);
        gc.fillText(String.format("Your health: %8.2f%" + "%", game.getHumanPlayer().getNavy().calculateHealthPercentage()),
                10, FIELD_SIZE * CELL_SIZE + 100);
        gc.setFill(Color.MAGENTA);
        gc.fillText(String.format("Computer's health: %8.2f%" + "%",
                game.getComputerPlayer().getNavy().calculateHealthPercentage()), FIELD_SIZE * CELL_SIZE + 5,
                FIELD_SIZE * CELL_SIZE + 100);

    }
}
