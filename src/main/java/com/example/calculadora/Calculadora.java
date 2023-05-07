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
import javafx.scene.layout.*;


import javafx.stage.Stage;

public class Calculadora extends Application {

    private int estado = 0;

    TextField etiOperador = new TextField();

    private ToggleGroup operacionesToggleGroup = new ToggleGroup();


    private TextField etiOperando1, etiOperando2;

    private Label op1Label, op2Label, op3Label;

    private Label l1;


    private String operando1 = "";
    private String operando2 = "";
    private String operador = "";


    private final EventHandler<ActionEvent> operatorHandler = e -> {
        String operando1 = etiOperando1.getText();
        String operando2 = etiOperando2.getText();
        String operador = etiOperador.getText();

        System.out.println("Operando 1: " + operando1);
        System.out.println("Operando 2: " + operando2);
        System.out.println("Operador: " + operador);

        op1Label.setText(operando1);
        op2Label.setText(operador);
        op3Label.setText("");

        double resultado = 0;

        if (operando1.isEmpty() || operando2.isEmpty() || operador.isEmpty()) {
            l1.setText("Falta información para realizar la operación");
        } else {
            if (estado == 0) {
                // Se ha ingresado el primer número
                estado = 1;
            } else if (estado == 1) {
                // Se ha ingresado el operador
                estado = 2;
            } else if (estado == 2) {
                // Se ha ingresado el segundo número
                estado = 3;

                double num1 = Double.parseDouble(operando1);
                double num2 = Double.parseDouble(operando2);

                switch (operador) {
                    case "Suma":
                        resultado = num1 + num2;
                        break;
                    case "Resta":
                        resultado = num1 - num2;
                        break;
                    case "Producto":
                        resultado = num1 * num2;
                        break;
                    case "División":
                        if (num2 != 0) {
                            resultado = num1 / num2;
                        } else {
                            l1.setText("No se puede dividir entre cero");
                            estado = 0; // volver al estado inicial
                            return;
                        }
                        break;
                }

                op3Label.setText(Double.toString(resultado));

                System.out.println("Resultado: " + resultado);

                l1.setText(Double.toString(resultado));
            } else if (estado == 3) {
                // Se ha presionado el botón de resultado antes de ingresar un nuevo número u operador
                // volver al estado inicial
                estado = 0;
            }

            etiOperando1.setText("");
            etiOperador.setText("");
            etiOperando2.setText("");
        }
    };



    private final EventHandler<ActionEvent> numbersHandler = e -> {

        String digit = ((Button) e.getSource()).getText();
        if (estado == 0 || estado == 1) {
            estado = 1;
            if (operador == null) {
                operando1 += digit;
                etiOperando1.setText(operando1);
            } else {
                operando2 += digit;
                etiOperando2.setText(operando2);
            }
        } else if (estado == 2 || estado == 3) {
            estado = 3;
            if (operador == null) {
                operando1 = digit;
                etiOperando1.setText(operando1);
            } else {
                operando2 = digit;
                etiOperando2.setText(operando2);
            }
        }
    };



    private final EventHandler<ActionEvent> buttonHandler = e -> {
        Button b = (Button) e.getSource();
        String buttonText = b.getText();
        String operando1 = etiOperando1.getText();
        String operando2 = etiOperando2.getText();
        String operador = etiOperador.getText();

        if (operador.isEmpty()) {
            operador = buttonText;
            etiOperador.setText(operador);
            estado = 1; // Actualizar estado a 1 (operador ingresado)
        } else if (operando1.isEmpty()) {
            operando1 = buttonText;
            etiOperando1.setText(operando1);
        } else if (operando2.isEmpty()) {
            operando2 = buttonText;
            etiOperando2.setText(operando2);
            estado = 2; // Actualizar estado a 2 (segundo operando ingresado)
        } else {
            l1.setText("Operación completa, presione Resultado para calcular");
        }
    };


    private final EventHandler<ActionEvent> radioHandler = e -> {
        RadioButton rb = (RadioButton) e.getSource();
        etiOperador.setText(rb.getText());
    };

    private final EventHandler<ActionEvent> resetHandler = e -> {

        op1Label.setText("");
        op2Label.setText("");
        op3Label.setText("");

        estado = 0;
    };


    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Calculadora");
        BorderPane borderPane = new BorderPane();



        etiOperando1 = new TextField();

        etiOperando2 = new TextField();



        //Parte Superior

        Image imagen = new Image("E:\\Escritorio\\UT11_Elisabet_Agulló\\calc.jpg");
        ImageView imageView = new ImageView(imagen);
        borderPane.setTop(imageView);

        imageView.setFitWidth(120);
        imageView.setFitHeight(120);
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
        sumaRadioButton.setToggleGroup(operacionesToggleGroup);
        sumaRadioButton.setOnAction(radioHandler);
        vbox.getChildren().add(sumaRadioButton);
        VBox.setMargin(sumaRadioButton, new Insets(5, 0, 5, 0));

        RadioButton restaRadioButton = new RadioButton("Resta");
        restaRadioButton.setToggleGroup(operacionesToggleGroup);
        restaRadioButton.setOnAction(radioHandler);
        vbox.getChildren().add(restaRadioButton);
        VBox.setMargin(restaRadioButton, new Insets(5, 0, 5, 0));


        RadioButton productoRadioButton = new RadioButton("Producto");
        productoRadioButton.setOnAction(radioHandler);
        productoRadioButton.setToggleGroup(operacionesToggleGroup);
        vbox.getChildren().add(productoRadioButton);
        VBox.setMargin(productoRadioButton, new Insets(5, 0, 5, 0));


        RadioButton divisionRadioButton = new RadioButton("División");
        divisionRadioButton.setToggleGroup(operacionesToggleGroup);
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
            button.addEventHandler(ActionEvent.ACTION, buttonHandler);
            button.addEventHandler(ActionEvent.ACTION, operatorHandler);
            gridPane.add(button, (i - 1) % 3, (i - 1) / 3);
        }

        // Botón 0
        Button button0 = new Button("0");
        button0.setPrefSize(45, 40);
        button0.addEventHandler(ActionEvent.ACTION, buttonHandler);
        button0.addEventHandler(ActionEvent.ACTION, operatorHandler);
        gridPane.add(button0, 1, 3);


        // Botón de reset
        Button resetButton = new Button("Reset");
        resetButton.setPrefSize(80, 40);
        resetButton.setOnAction(resetHandler);
        gridPane.add(resetButton, 2, 3);
        borderPane.setCenter(gridPane);


        //Parte Derecha


        Button btnResultado = new Button("Resultado");
        btnResultado.setOnAction(operatorHandler);
        btnResultado.setPrefSize(80, 40);
        borderPane.setRight(btnResultado);
        gridPane.add(btnResultado, 3, 3);



        //Parte Inferior

        // Crear un HBox para mostrar las operaciones
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER);
        hbox.setSpacing(10);

        // Crear tres etiquetas para mostrar los operandos y el resultado
        op1Label = new Label("");
        op2Label = new Label("");
        op3Label = new Label("");

        // Configurar el estilo de las etiquetas
        op1Label.setStyle("-fx-font-size: 18pt;");
        op2Label.setStyle("-fx-font-size: 18pt;");
        op3Label.setStyle("-fx-font-size: 18pt;");

        // Agregar las etiquetas al HBox
        hbox.getChildren().addAll(op1Label, op2Label, op3Label);

        // Configurar el estilo del HBox
        hbox.setStyle("-fx-padding: 10px;");

        // Crear un TitledPane con el título "Resultado"
        TitledPane resultadoPane = new TitledPane("Resultado", hbox);
        resultadoPane.setCollapsible(false);
        resultadoPane.setPadding(new Insets(10));

        // Agregar el TitledPane al BorderPane en la parte inferior
        borderPane.setBottom(resultadoPane);

        // Actualizar el contenido de las etiquetas con los valores de los operandos
        op1Label.setText(operando1);
        op2Label.setText(operador);
        op3Label.setText(operando2);




        Scene scene = new Scene(borderPane, 450, 450);
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }
}
