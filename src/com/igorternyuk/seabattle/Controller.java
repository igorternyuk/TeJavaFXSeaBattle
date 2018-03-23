package com.igorternyuk.seabattle;

import com.igorternyuk.seabattle.model.Game;
import com.igorternyuk.seabattle.model.GameState;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

import static com.igorternyuk.seabattle.Renderer.CELL_SIZE;

public class Controller implements Initializable{
    private final Game game = new Game();
    @FXML
    private Canvas canvas;
    private GraphicsContext gc;


    public void onBtnNewGameClicked(ActionEvent event) {
        this.game.prepareNewGame();
        renderAll();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.gc = this.canvas.getGraphicsContext2D();
        this.canvas.setOnMouseClicked(event -> {
            final int x = (int)(event.getX() / CELL_SIZE);
            final int y = (int)(event.getY() / CELL_SIZE);
            if(event.getButton().equals(MouseButton.PRIMARY)){
                if(this.game.getGameState().equals(GameState.HUMAN_SHIP_PLACEMENT)) {
                    this.game.tryToPlaceHorizontalShip(x - Game.FIELD_SIZE, y);
                    renderAll();
                } else if(this.game.getGameState().equals(GameState.HUMAN_TO_PLAY)){
                   // System.out.println("Shoots human x = " + x + " y = " + y);
                    this.game.shootsHuman(x, y);
                    renderAll();
                }
            } else if(event.getButton().equals(MouseButton.SECONDARY)){
                if(this.game.getGameState().equals(GameState.HUMAN_SHIP_PLACEMENT)) {
                    this.game.tryToPlaceVerticalShip(x - Game.FIELD_SIZE, y);
                    renderAll();
                }
            }
        });
        renderAll();
    }

    public void onBtnExitClicked(ActionEvent event) {
        final Alert alert = new Alert(Alert.AlertType.WARNING, "Dou you really want to exit?",
                ButtonType.YES, ButtonType.NO);
        alert.setTitle("Confirm exit, please");
        alert.showAndWait();
        if(alert.getResult() == ButtonType.YES){
            Platform.exit();
            System.exit(0);
        } else {
            alert.close();
        }

    }

    public void renderAll(){
        Renderer.renderField(this.gc, this.game);
        Renderer.renderHumanShots(this.gc, this.game);
        Renderer.renderComputerShots(this.gc, this.game);
        Renderer.renderHumanShips(this.gc, this.game);
        Renderer.renderComputerShips(this.gc, this.game);
        Renderer.renderGameStatus(this.gc, Color.RED, this.game);
    }
}
