package ViewModel;

import Model.IModel;
import Model.MyModel;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyEvent;

import java.util.Observable;
import java.util.Observer;

public class MyViewModel extends Observable implements Observer {
    private static MyViewModel ViewModelInstance; //singleton
    private IModel model;

    //viewModel is observer of model
    private MyViewModel() {
        model = MyModel.getInstance();
        model.assignObserver(this);
    }

    public static MyViewModel getInstance() {
        if (ViewModelInstance == null){
            ViewModelInstance = new MyViewModel();
        }
        return ViewModelInstance;
    }


    public int getPlayerRow() {
        return model.getPlayerRow();
    }

    public int getPlayerCol() {
        return model.getPlayerCol();
    }

    public Maze getMaze() {
        return model.getMaze();
    }

    @Override
    public void update(Observable o, Object arg) { //create maze or move character
        String action = arg.toString();
        //In all cases we'll pass the same message we received from the Model to the View.
        setChanged(); // let know the view we done
        notifyObservers(action);
    }

    public void generateMaze(int row, int col) {
        model.generateMaze(row, col);
    }

    public void movePlayer(KeyEvent keyEvent){ //we get from the model the user UP/DOWM.. and the Model knows 1 2 3...
        int direction = -1;
        switch (keyEvent.getCode()){
            //up, down, left, right
            //todo:  check answer on forum - add to help section the valid keys (add keys button?)
            case CONTROL -> direction = -2;// ignore
            case NUMPAD8, UP -> direction = 8;
            case NUMPAD2, DOWN -> direction = 2;
            case NUMPAD4, LEFT -> direction = 4;
            case NUMPAD6, RIGHT -> direction = 6;
            //diagonals
            case NUMPAD7 -> direction = 7;
            case NUMPAD9 -> direction = 9;
            case NUMPAD1 -> direction = 1;
            case NUMPAD3 -> direction = 3;
            default -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Invalid Key, press only 1,2,3,4,6,7,8,9 to move");
                alert.show();
            }

        }
        if (direction > 0)
            model.UpdatePlayerPosition(direction);
    }

    public void restartPlayer(){
        model.UpdatePlayerPosition(0);
    }

    public void solveMaze(){
        model.solveMaze();
    }

    public Solution getSolution (){
        return model.getSolution();
    }

    public void exitGame(){model.shutDownServers();}

    public void initGameServers(){model.initServers();}

    public void setLoadedMaze(Maze loadedMaze) {
        model.setLoadedMaze(loadedMaze);
    }

}
