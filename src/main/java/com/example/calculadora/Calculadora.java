package com.example.calculadora;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import javafx.stage.Stage;

public class Calculadora extends Application {

    private int control = 0;

    TextField etiOperador = new TextField();

    private TextField etiOperando1, etiOperando2;

    private Label op1Label, op2Label, op3Label;

    private Label l1;

    private EventHandler<ActionEvent> operatorHandler = e -> {
        String operando1 = etiOperando1.getText();
        String operando2 = etiOperando2.getText();
        String operador = etiOperador.getText();

        double resultado = 0.0;

        if (operando1.isEmpty() || operando2.isEmpty() || operador.isEmpty()) {
            l1.setText("Falta información para realizar la operación");
        } else {
            double num1 = Double.parseDouble(operando1);
            double num2 = Double.parseDouble(operando2);

            switch (operador) {
                case "+":
                    resultado = num1 + num2;
                    break;
                case "-":
                    resultado = num1 - num2;
                    break;
                case "*":
                    resultado = num1 * num2;
                    break;
                case "/":
                    if (num2 != 0) {
                        resultado = num1 / num2;
                    } else {
                        l1.setText("No se puede dividir entre cero");
                        return;
                    }
                    break;
            }

            etiOperando1.setText("");
            etiOperador.setText("");
            etiOperando2.setText("");
            l1.setText(Double.toString(resultado));
        }
    };

    private EventHandler<MouseEvent> buttonHandler = e -> {
        Button b = (Button) e.getSource();
        String buttonText = b.getText();
        String operando1 = etiOperando1.getText();
        String operando2 = etiOperando2.getText();
        String operador = etiOperador.getText();

        if (operador.isEmpty()) {
            operador = buttonText;
            etiOperador.setText(operador);
        } else if (operando1.isEmpty()) {
            operando1 = buttonText;
            etiOperando1.setText(operando1);
        } else if (operando2.isEmpty()) {
            operando2 = buttonText;
            etiOperando2.setText(operando2);
        } else {
            l1.setText("Operación completa, presione = para calcular");
        }
    };

    private EventHandler<ActionEvent> radioHandler = e -> {
        RadioButton rb = (RadioButton) e.getSource();
        etiOperador.setText(rb.getText());
    };

    private EventHandler<MouseEvent> resetHandler = e -> {
        etiOperando1.setText("");
        etiOperador.setText("");
        etiOperando2.setText("");
        l1.setText("");
    };

    private EventHandler<MouseEvent> resultHandler = e -> {
        operatorHandler.handle(null);
    };

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Calculadora");
        BorderPane borderPane = new BorderPane();


        //Parte Superior

        Image imagen = new Image("E:\\Escritorio\\UT11_Elisabet_Agulló\\calc.jpg");
        ImageView imageView = new ImageView(imagen);
        borderPane.setTop(imageView);

        imageView.setFitWidth(200);
        imageView.setFitHeight(200);
        BorderPane.setAlignment(imageView, Pos.CENTER);

        l1 = new Label();
        borderPane.setBottom(l1);
        BorderPane.setAlignment(l1, Pos.BOTTOM_CENTER);
        BorderPane.setMargin(l1, new Insets(20, 20, 20, 20));

        Label l2 = new Label();
        borderPane.setBottom(l2);
        BorderPane.setAlignment(l2, Pos.BOTTOM_CENTER);
        BorderPane.setMargin(l2, new Insets(20, 20, 20, 20));


        // Parte izquierda
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);

        RadioButton sumaRadioButton = new RadioButton("Suma");
        sumaRadioButton.setOnAction(radioHandler);
        vbox.getChildren().add(sumaRadioButton);
        VBox.setMargin(sumaRadioButton, new Insets(5, 0, 5, 0));

        RadioButton restaRadioButton = new RadioButton("Resta");
        restaRadioButton.setOnAction(radioHandler);
        vbox.getChildren().add(restaRadioButton);
        VBox.setMargin(restaRadioButton, new Insets(5, 0, 5, 0));


        RadioButton productoRadioButton = new RadioButton("Producto");
        productoRadioButton.setOnAction(radioHandler);
        vbox.getChildren().add(productoRadioButton);
        VBox.setMargin(productoRadioButton, new Insets(5, 0, 5, 0));


        RadioButton divisionRadioButton = new RadioButton("División");
        divisionRadioButton.setOnAction(radioHandler);
        vbox.getChildren().add(divisionRadioButton);
        VBox.setMargin(divisionRadioButton, new Insets(5, 0, 5, 0));


        borderPane.setLeft(vbox);


        // Parte Central


        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        // Crear botones del 1 al 9
        for (int i = 1; i <= 9; i++) {
            Button button = new Button(String.valueOf(i));
            button.setPrefSize(45, 40);
            button.addEventHandler(MouseEvent.MOUSE_CLICKED, buttonHandler);
            gridPane.add(button, (i - 1) % 3, (i - 1) / 3);
        }

       // Botón 0
        Button button0 = new Button("0");
        button0.setPrefSize(45, 40);
        button0.addEventHandler(MouseEvent.MOUSE_CLICKED, buttonHandler);
        gridPane.add(button0, 1, 3);

       // Botón de reset
        Button resetButton = new Button("Reset");
        resetButton.setPrefSize(80, 40);
        resetButton.addEventHandler(MouseEvent.MOUSE_CLICKED, resetHandler);
        gridPane.add(resetButton, 2, 3);

        borderPane.setCenter(gridPane);


        //Parte Derecha

        Button calcularButton = new Button("Resultado");
        calcularButton.setPrefSize(80, 40);
        calcularButton.addEventHandler(MouseEvent.MOUSE_CLICKED, resultHandler);

        borderPane.setRight(calcularButton);


        //Parte Inferior

        // Crear un HBox para mostrar las operaciones
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER);
        hbox.setSpacing(10);

        // Crear tres etiquetas para mostrar los operandos y el resultado
        op1Label = new Label("");
        op2Label = new Label("");
        op3Label = new Label("");


        // Agregar las etiquetas al HBox
        hbox.getChildren().addAll(op1Label, op2Label, op3Label);

        // Configurar el estilo del HBox
        hbox.setStyle("-fx-font-size: 18pt; -fx-padding: 10px;");

        // Crear un TitledPane con el título "Resultado"
        TitledPane resultadoPane = new TitledPane("Resultado", hbox);
        resultadoPane.setCollapsible(false);
        resultadoPane.setPadding(new Insets(10));

        // Agregar el TitledPane al BorderPane en la parte inferior
        borderPane.setBottom(resultadoPane);


        Scene scene = new Scene(borderPane, 450, 450);
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }
}
