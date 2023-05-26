package com.example.exchanger;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    ChoiceBox<String> to;
    @FXML
    ChoiceBox<String> from;
    @FXML
    Button convert;
    @FXML
    TextField input;
    @FXML
    Label result;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        addProperties(to);
        addProperties(from);
        from.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue)
                -> Convert.from = Convert.getCode(newValue));
        to.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue)
                -> Convert.to = Convert.getCode(newValue));
    }

    void addProperties(ChoiceBox<String> cb) {
        cb.setItems(FXCollections.observableArrayList(
                "AED(아랍에미리트)", "AUD(오스트레일리아)", "BHD(바레인)", "BND(브루나이)", "CAD(캐나다)",
                "CHF(스위스)", "CNH(중국)", "DKK(덴마크)", "EUR(유로)", "GBP(영국 파운드)", "HKD(홍콩 달러)",
                "IDR(인도네시아)", "JPY(일본)", "KRW(한국)", "KWD(쿠웨이트)", "MYR(말레이시아)", "NOK(노르웨이)",
                "NZD(뉴질랜드)", "SAR(사우디아라비아)", "SEK(스웨인)", "SGD(싱가포르)", "THB(태국)", "USD(미국)"
        ));
    }

    public void onClicked() throws IOException {
        String s = input.getText();
        Convert.value = Double.parseDouble(s);
        result.setText(String.format("%.4f", Convert.KRWToResult()));
    }
}
