package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.List;

public class Controller {

    @FXML
    private Button checkButton;

    @FXML
    private Button newGameButton;

    @FXML
    private VBox colorsToChoose;

    @FXML
    private Circle chosenColor;

    @FXML
    private VBox mainCircles;

    @FXML
    private Label player1ScoreText;
    private int player1Score;

    @FXML
    private Label player2Label;

    @FXML
    private Label player2ScoreText;
    private int player2Score;


    @FXML
    private CheckBox duplicateBox;

    @FXML
    private ChoiceBox choiceBox;

    @FXML
    private Label turnText;

    private final String p1Turn = "First Player's turn";
    private final String p2Turn = "Second Player's turn";
    private final String computersTurn = "Computer's turn";
    private final String choice2Players = "Player vs. Player";
    private final String choiceComputerPlayer = "Player vs. Computer";
    private final String computerName = "Computer";
    private final String secondPlayersName = "Second Player";

    private String currentPlayer2Turn = computersTurn;

    private boolean isComputerPlaying = true;
    private boolean firstTurn = true;

    private ArrayList<ArrayList<Circle>> circles = new ArrayList<>();
    private ArrayList<ArrayList<Circle>> answerDots = new ArrayList<>();
    private Color currentColor = Color.BLUE;
    private int tries;

    private final int rowsAmmount = 10;
    private final int scoreMultiplier = 10;
    private final int scoreBase = 100;

    private int turnCounter = 0;
    private int maxTurn = 3;
    private final int slotSize = 4;

    private boolean allowDuplicates = false;


    private GameManager gameManager;

    @FXML
    public void initialize() {

        gameManager = new GameManager(slotSize);

        tries = 0;
        player1Score = 0;
        player2Score = 0;

        player2Label.setText(computerName+": ");

        choiceBox.setItems(FXCollections.observableArrayList(choiceComputerPlayer, choice2Players));
        choiceBox.setValue(choiceComputerPlayer);
        ObservableList HBoxes = colorsToChoose.getChildren();

        //choosing colors from the pallette
        for (Object node : HBoxes) {
            if (node instanceof HBox) {
                HBox hBox = (HBox) node;
                ObservableList colors = hBox.getChildren();
                for (Object object : colors) {
                    if (object instanceof Circle) {
                        Circle circle = (Circle) object;
                        Color color = (Color) circle.getFill();
                        circle.setOnMouseClicked(e -> {
                            currentColor = color;
                            chosenColor.setFill(color);
                        });
                    }

                }
            }
        }




        //setting action of all the circles and adding them to the list
        //also adding the answer dots to the list
        ObservableList potentialGuesses = mainCircles.getChildren();
        for (Object node : potentialGuesses) {
            if (node instanceof HBox) {
                HBox hBox = (HBox) node;
                ObservableList elements = hBox.getChildren();
                ArrayList<Circle> circleRow = new ArrayList<>();
                ArrayList<Circle> answerRow = new ArrayList<>();
                for (int i = 0; i < elements.size(); i++) {
                    if (elements.get(i) instanceof Circle) {
                        Circle circle = (Circle) elements.get(i);
                        circleRow.add(circle);
                        circle.setOnMouseClicked(e -> {
                            circle.setFill(currentColor);
                        });
                    }


                    if (elements.get(i) instanceof StackPane) {
                        StackPane stackPane = (StackPane) elements.get(i);
                        ObservableList temp = stackPane.getChildren();
                        for (Object obj : temp) {
                            if (obj instanceof HBox) {
                                HBox box = (HBox) obj;
                                ObservableList dots = box.getChildren();

                                for (Object dot : dots) {
                                    if (dot instanceof Circle) {
                                        Circle castedDot = (Circle) dot;

                                        answerRow.add(castedDot);
                                    }
                                }
                            }
                        }
                    }
                }
                circles.add(0, circleRow);
                answerDots.add(0, answerRow);

            }
        }

    }



    public boolean areColored(int index) {
        List<Circle> row = circles.get(index);
        for (Circle circle : row) {
            if (circle.getFill().equals(Color.GRAY)) {
                return false;
            }
        }
        return true;
    }

    public void disableRow(ArrayList<ArrayList<Circle>> circles, int index) {
        List<Circle> row = circles.get(index);
        for (Circle circle : row) {
            circle.setDisable(true);
        }

    }

    public void enableRow(ArrayList<ArrayList<Circle>> circles, int index) {
        List<Circle> row = circles.get(index);
        for (Circle circle : row) {
            circle.setDisable(false);
        }

    }

    public void colorRow(ArrayList<ArrayList<Circle>> circles, int index, Color color) {
        List<Circle> row = circles.get(index);
        for (Circle circle : row) {
            circle.setFill(color);
        }

    }

    public void setAnswerDots(Answer answer) {

        int blackDots = answer.getBlackDots();
        int whiteDots = answer.getWhiteDots();
        ArrayList<Circle> row = answerDots.get(tries);

        int counter = 0;
        while (blackDots > 0) {
            row.get(counter++).setFill(Color.BLACK);
            blackDots--;
        }

        while (whiteDots > 0) {
            row.get(counter++).setFill(Color.WHITE);
            whiteDots--;
        }

    }

    public Guess getCurrentGuess() {

        Guess guess = new Guess();
        ArrayList<Circle> row = circles.get(tries);
        for (int i = 0; i < row.size(); i++) {
            guess.set(i, (Color) row.get(i).getFill());
        }

        return guess;

    }

    @FXML
    public void check() {

        System.out.println(gameManager.getSecretGuess());

        if (areColored(tries)) {
            disableRow(circles, tries);
            Answer answer = gameManager.getAnswer(getCurrentGuess());
            System.out.println(answer);
            System.out.println();
            setAnswerDots(answer);
            if (answer.isWinning()) {
                win();
            } else if (tries >= rowsAmmount - 1) {

                loose();
            } else {
                tries++;
                enableRow(circles, tries);
                colorRow(circles, tries, Color.GRAY);

            }
        }
    }

    @FXML
    public void newGame() {
        startGame();
    }

    public void win() {
        int scoreObtained = scoreBase + (rowsAmmount - tries) * scoreMultiplier;
        if (firstTurn) {
            player1Score += scoreObtained;
            player1ScoreText.setText(Integer.toString(player1Score));
        } else {
            player2Score += scoreObtained;
            player2ScoreText.setText(Integer.toString(player2Score));
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Congratulations!");
        alert.setHeaderText(null);
        alert.setContentText("You found the solution and earned " + scoreObtained + " points.");

        alert.showAndWait();

        prepare();
        changeTurn();
    }

    public void loose() {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("You failed.");
        alert.setHeaderText(null);
        alert.setContentText("Don't give up.");

        alert.showAndWait();

        prepare();
        changeTurn();
    }

    public void prepare() {

        gameManager.shuffle(allowDuplicates);
        tries = 0;
        currentColor = Color.BLUE;
        chosenColor.setFill(currentColor);

        for (int i = 1; i < circles.size(); i++) {
            disableRow(circles, i);
            colorRow(circles, i, Color.SILVER);
        }
        enableRow(circles, 0);
        colorRow(circles, 0, Color.GRAY);

        for (int i = 0; i < answerDots.size(); i++) {
            colorRow(answerDots, i, Color.SILVER);
        }
    }

    public void startGame() {

        if(choiceBox.getValue().equals(choiceComputerPlayer)) {

            player2Label.setText(computerName + ":");
            isComputerPlaying = true;
            currentPlayer2Turn = computersTurn;

        } else if(choiceBox.getValue().equals(choice2Players)) {
            player2Label.setText(secondPlayersName + ":");
            isComputerPlaying = false;
            currentPlayer2Turn = p2Turn;
        }

        if(duplicateBox.isSelected()) {
            allowDuplicates = true;
        } else if(!duplicateBox.isSelected()) {
            allowDuplicates = false;
        }

        player1ScoreText.setText("0");
        player2ScoreText.setText("0");
        firstTurn = true;
        turnText.setText(p1Turn);
        prepare();
    }

    public void gameOver() {
        startGame();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        int score = 0;
        String player = "First Player";

        if (player1Score == player2Score) {

            alert.setTitle(null);
            alert.setHeaderText("Draw");
            alert.setContentText("Nobody wins this time.");
            alert.showAndWait();

        } else {

            if (player1Score > player2Score) {
                player = "First Player";
                score = player1Score;
            }

            if(player1Score < player2Score) {
                player = "Second Player";
                score = player2Score;
            }

            alert.setTitle(null);
            alert.setHeaderText(player + " wins!");
            alert.setContentText("Score: " + score);
            alert.showAndWait();
        }



    }

    public void changeTurn() {
        firstTurn = !firstTurn;
        if (firstTurn) {
            turnCounter++;
            turnText.setText(p1Turn);
            if (turnCounter >= maxTurn) {
                gameOver();
            }
        } else {
            turnText.setText(currentPlayer2Turn);
            if(isComputerPlaying) {
                simulateComputer();
            }
        }

    }

    public void setGuess(int index, Guess guess) {
        Color[] colors = guess.getColors();
        for(int i = 0; i < slotSize; i++) {
            circles.get(index).get(i).setFill(colors[i]);
        }
    }

    public void simulateComputer() {

        boolean win = false;
        gameManager.startAlgorithm();
        Guess guess = gameManager.getGuess(null);
        setGuess(tries, guess);
        Answer answer = gameManager.getAnswer(guess);
        System.out.println("tutaj " + tries);
        System.out.println("Secret Guess:" + gameManager.getSecretGuess());
         do {
             tries++;
             System.out.println("tries " + tries);

             if(answer.getBlackDots() == slotSize) {
                 win = true;
             }

             System.out.println("next Guess: " + guess.toString());
             guess = gameManager.getGuess(answer);

             setGuess(tries, guess);
             answer = gameManager.getAnswer(guess);

         } while( !win && tries < rowsAmmount );
    }


}
