package com.example.passwordgenerator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

public class HelloController {
    @FXML
    private Rectangle symbolsRectangle;
    @FXML
    private Rectangle numbersRectangle;
    @FXML
    private Rectangle lowercaseRectangle;
    @FXML
    private Rectangle uppercaseRectangle;
    @FXML
    private Rectangle uncommonRectangle;
    @FXML
    private Rectangle genuineRectangle;
    @FXML
    private Rectangle strengthBarOne;
    @FXML
    private Rectangle strengthBarTwo;
    @FXML
    private Rectangle strengthBarThree;
    @FXML
    private CheckBox symbolsCheckBox;
    @FXML
    private CheckBox numbersCheckBox;
    @FXML
    private CheckBox lowercaseCheckBox;
    @FXML
    private CheckBox uppercaseCheckBox;
    @FXML
    private CheckBox genuineCheckBox;
    @FXML
    private CheckBox uncommonCheckBox;
    @FXML
    private Button generateButton;
    @FXML
    private Button checkButton;
    @FXML
    private TextField passwordBox;
    @FXML
    private ChoiceBox lengthSelectBox;

    ObservableList<Integer> passwordLengthOptions = FXCollections.observableArrayList(8,9,10,11,12,13,14,15,16);
    private final ArrayList<String> lowercaseNumbers = new ArrayList<String>(Arrays.asList("a","b","c","d","e","f","g","h","i","j","k","l","m",
            "n","o","p","q","r","s","t","u","v","w","x","y","z"));
    private final ArrayList<String> uppercaseNumbers = new ArrayList<String>(Arrays.asList("A","B","C","D","E","F","G","H","I","J","K","L","M",
            "N","O","P","Q","R","S","T","U","V","W","X","Y","Z"));
    private final ArrayList<String> numbersArray = new ArrayList<String>(Arrays.asList("0","1","2","3","4","5","6","7","8","9"));
    private final ArrayList<String> symbolsArray = new ArrayList<String>(Arrays.asList("!","@","#","$","%","*","_","-"));
    private final ArrayList<String> uncommonSymbols = new ArrayList<String>(Arrays.asList("^","&","(",")","?",":",";","<",">","+","/","|","[","]","{","}"));
    private final ArrayList<String> charactersArray = new ArrayList<String>();
    private String generatedPassword = "";
    private Random random = new Random();
    private int selectionCount = 0;
    @FXML
    private void initialize(){
        lengthSelectBox.setValue(12);
        lengthSelectBox.setItems(passwordLengthOptions);
    }

    @FXML
    private void generateButton(){
        setStrengthBarClear();
        selectionCount=0;
        generatedPassword="";
        int passwordLength = (int) lengthSelectBox.getValue();
        selectedOptionsToArray();
        if(genuineCheckBox.isSelected()){
            genuinePasswordBuilder(passwordLength);

        } else {
            normalPasswordBuilder(passwordLength);
        }
        passwordBox.setText(generatedPassword);
        strengthCalculator();
    }

    private void normalPasswordBuilder(int passwordLength) {
        while(passwordLength >= generatedPassword.length()){
            int currentChoice = random.nextInt(charactersArray.size());
            generatedPassword += charactersArray.get(currentChoice);
        }
    }

    private void genuinePasswordBuilder(int passwordLength) {
        while(passwordLength >= generatedPassword.length()){
            int currentChoice = random.nextInt(charactersArray.size());
            generatedPassword += charactersArray.get(currentChoice);
            charactersArray.remove(currentChoice);
        }
    }

    private void selectedOptionsToArray() {
        charactersArray.clear();
        if (symbolsCheckBox.isSelected()){
            charactersArray.addAll(symbolsArray);
            selectionCount++;
        }
        if (numbersCheckBox.isSelected()) {
            charactersArray.addAll(numbersArray);
            selectionCount++;
        }
        if (lowercaseCheckBox.isSelected()) {
            charactersArray.addAll(lowercaseNumbers);
            selectionCount++;
        }
        if (uppercaseCheckBox.isSelected()) {
            charactersArray.addAll(uppercaseNumbers);
            selectionCount++;
        }
        if (uncommonCheckBox.isSelected()) {
            charactersArray.addAll(uncommonSymbols);
            selectionCount++;
        }
    }

    @FXML
    private void checkButton(){
        selectionCount=0;
        setRectanglesClear();
        setStrengthBarClear();
        HashSet<String> genuineCheck = new HashSet<>();
        boolean genuinePassword = true;
        String currentPassword = passwordBox.getText();
        for(int currentCharacter =0; currentCharacter<currentPassword.length(); currentCharacter++){
            String currentValue = String.valueOf(currentPassword.charAt(currentCharacter));
            if(genuineCheck.contains(currentValue)) genuinePassword = false;
            genuineCheck.add(currentValue);
            checkRectangles(currentValue);
        }
        if(selectionCount>0 && genuinePassword ){
            genuineRectangle.setFill(Color.LIMEGREEN);
        }
        strengthCalculator();
    }

    private void setStrengthBarClear() {
        strengthBarOne.setFill(Color.web("#d7d7d7"));
        strengthBarTwo.setFill(Color.web("#d7d7d7"));
        strengthBarThree.setFill(Color.web("#d7d7d7"));
    }

    private void strengthCalculator() {
        System.out.println(selectionCount);
        if(selectionCount>0 && selectionCount<3){
            strengthBarOne.setFill(Color.RED);
        } else if (selectionCount>0 && selectionCount<5){
            strengthBarOne.setFill(Color.RED);
            strengthBarTwo.setFill(Color.YELLOW);
        } else if ( selectionCount>0 ) {
            strengthBarOne.setFill(Color.RED);
            strengthBarTwo.setFill(Color.YELLOW);
            strengthBarThree.setFill(Color.GREEN);
        }
    }

    private void checkRectangles(String currentValue) {
        if (lowercaseNumbers.contains(currentValue) && lowercaseRectangle.getFill() != Color.LIMEGREEN){
            lowercaseRectangle.setFill(Color.LIMEGREEN);
            selectionCount++;
        }
        else if (uppercaseNumbers.contains(currentValue) && uppercaseRectangle.getFill() != Color.LIMEGREEN){
            uppercaseRectangle.setFill(Color.LIMEGREEN) ;
            selectionCount++;
        }
        else if (uncommonSymbols.contains(currentValue) && uncommonRectangle.getFill() != Color.LIMEGREEN){
            uncommonRectangle.setFill(Color.LIMEGREEN);
            selectionCount++;
        }
        else if (symbolsArray.contains(currentValue) && symbolsRectangle.getFill() != Color.LIMEGREEN){
            symbolsRectangle.setFill(Color.LIMEGREEN);
            selectionCount++;
        }
        else if (numbersArray.contains(currentValue) && numbersRectangle.getFill() != Color.LIMEGREEN){
            numbersRectangle.setFill(Color.LIMEGREEN);
            selectionCount++;
        }
    }

    private void setRectanglesClear() {
        genuineRectangle.setFill(Color.WHITE);
        lowercaseRectangle.setFill(Color.WHITE);
        uppercaseRectangle.setFill(Color.WHITE) ;
        uncommonRectangle.setFill(Color.WHITE);
        symbolsRectangle.setFill(Color.WHITE);
        numbersRectangle.setFill(Color.WHITE);
    }
}
