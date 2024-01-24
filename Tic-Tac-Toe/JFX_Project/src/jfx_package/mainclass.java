package jfx_package;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class mainclass extends Application {

private static final int BOARD_SIZE = 3;
private static final int CELL_SIZE = 100;
private static final int WINDOW_WIDTH = BOARD_SIZE * CELL_SIZE;
private static final int WINDOW_HEIGHT = BOARD_SIZE * CELL_SIZE;


private Button[][] board = new Button[BOARD_SIZE][BOARD_SIZE];
private String currentPlayer = "X";

public static void main(String[] args) {
	launch(args);
}

@Override
public void start(Stage primaryStage) {
	GridPane root = new GridPane();
	Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

	for (int row = 0; row < BOARD_SIZE; row++) {
		for (int col = 0; col < BOARD_SIZE; col++) {
			final int finalRow = row;
			final int finalCol = col;
			Button button = new Button();
			button.setPrefSize(CELL_SIZE, CELL_SIZE);
			button.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					if (button.getText().isEmpty()) {
						button.setText(currentPlayer);
						if (checkWin(finalRow, finalCol)) {
							Alert alert = new Alert(Alert.AlertType.INFORMATION);
							alert.setTitle("Winner");
							alert.setHeaderText(null);
							alert.setContentText(currentPlayer + " wins!");
							alert.setOnHidden(evt -> resetGame()); // call resetGame() after alert is dismissed
							alert.showAndWait(); // waits for the alert to be closed before executing
							return;
						} else if (checkDraw()) {
							Alert alert = new Alert(Alert.AlertType.INFORMATION);
							alert.setTitle("Winner");
							alert.setHeaderText(null);
							alert.setContentText("Draw");
							alert.setOnHidden(evt -> resetGame()); // call resetGame() after alert is dismissed
							alert.showAndWait();// waits for the alert to be closed before executing
						} else {
							switchPlayer();
						}
					}
				}
			});
			board[row][col] = button;
			root.add(button, col, row);
		}
	}

	primaryStage.setTitle("Tic Tac Toe");
	primaryStage.setScene(scene);
	primaryStage.show();
	}

	private boolean checkWin(int row, int col) {
		// Check row
		for (int i = 0; i < BOARD_SIZE; i++) {
			if (!board[row][i].getText().equals(currentPlayer)) {
				break;
			}
			if (i == BOARD_SIZE - 1) {
				return true;
			}
		}

		// Check column
		for (int i = 0; i < BOARD_SIZE; i++) {
			if (!board[i][col].getText().equals(currentPlayer)) {
				break;
			}
			if (i == BOARD_SIZE - 1) {
				return true;
			}
		}

		// Check diagonal
		if (row == col) {
			for (int i = 0; i < BOARD_SIZE; i++) {
				if (!board[i][i].getText().equals(currentPlayer)) {
					break;
				}
				if (i == BOARD_SIZE - 1) {
					return true;
				}
			}
		}

		// Check anti-diagonal
		if (row + col == BOARD_SIZE - 1) {
			for (int i = 0; i < BOARD_SIZE; i++) {
				if (!board[i][BOARD_SIZE - 1 - i].getText().equals(currentPlayer)) {
					break;
				}
				if (i == BOARD_SIZE - 1) {
					return true;
				}
			}
		}

		return false;
	}

	private boolean checkDraw() {
		for (int row = 0; row < BOARD_SIZE; row++) {
			for (int col = 0; col < BOARD_SIZE; col++) {
				if (board[row][col].getText().isEmpty()) {
					return false;
				}
			}
		}
		return true;
	}

	private void switchPlayer() {
		if (currentPlayer.equals("X")) {
			currentPlayer = "O";
		} else {
			currentPlayer = "X";
		}
	}

	private void resetGame() {
		for (int row = 0; row < BOARD_SIZE; row++) {
			for (int col = 0; col < BOARD_SIZE; col++) {
				board[row][col].setText("");
			}
		}
		currentPlayer = "X";
	}

}