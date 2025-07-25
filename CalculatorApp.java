import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class CalculatorApp extends Application {

    private TextField display = new TextField();
    private double num1 = 0;
    private String operator = "";

    @Override
    public void start(Stage stage) {
        display.setEditable(false);
        display.setStyle("-fx-font-size: 18px;");
        display.setPrefHeight(50);

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setHgap(10);
        grid.setVgap(10);

        String[] buttons = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", ".", "C", "=", 
            "+"  // '+' will be placed manually to ensure layout consistency
        };

        int row = 1;
        int col = 0;

        for (int i = 0; i < buttons.length; i++) {
            String text = buttons[i];
            Button btn = new Button(text);
            btn.setPrefSize(80, 80);
            btn.setStyle("-fx-font-size: 16px;");
            btn.setFocusTraversable(false);
            btn.setOnAction(e -> handleInput(text));

            // Place "+" button manually
            if (text.equals("+")) {
                grid.add(btn, 3, 5);
            } else {
                grid.add(btn, col, row);
                col++;
                if (col > 3) {
                    col = 0;
                    row++;
                }
            }
        }

        grid.add(display, 0, 0, 4, 1);

        Scene scene = new Scene(grid, 370, 500);
        stage.setTitle("JavaFX Calculator");
        stage.setScene(scene);
        stage.show();
    }

    private void handleInput(String text) {
        switch (text) {
            case "C":
                display.clear();
                num1 = 0;
                operator = "";
                break;
            case "=":
                calculateResult();
                break;
            case "+": case "-": case "*": case "/":
                if (!display.getText().isEmpty()) {
                    num1 = Double.parseDouble(display.getText());
                    operator = text;
                    display.clear();
                }
                break;
            case ".":
                if (!display.getText().contains(".")) {
                    display.appendText(".");
                }
                break;
            default:
                display.appendText(text);
        }
    }

    private void calculateResult() {
        try {
            double num2 = Double.parseDouble(display.getText());
            double result = 0;

            switch (operator) {
                case "+":
                    result = num1 + num2;
                    break;
                case "-":
                    result = num1 - num2;
                    break;
                case "*":
                    result = num1 * num2;
                    break;
                case "/":
                    if (num2 == 0) {
                        display.setText("Error");
                        return;
                    }
                    result = num1 / num2;
                    break;
                default:
                    display.setText("Error");
                    return;
            }

            // Display result
            if (result == (long) result) {
                display.setText(String.valueOf((long) result));
            } else {
                display.setText(String.valueOf(result));
            }

        } catch (NumberFormatException e) {
            display.setText("Error");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
