package com.estudo.esqueleto;
import com.estudo.esqueleto.uts.Item;
import com.estudo.esqueleto.uts.Json;
import com.estudo.esqueleto.webscraping.scraping;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class HelloController {
    @FXML public Button btnProcurar;
    @FXML public TextField input;
    @FXML public TableView<Item> tableView;
    @FXML public TableColumn<Item, String> data;
    @FXML public TableColumn<Item, String> evento;

    /* CHECAR SE É UM ANO VÁLIDO */
    private boolean anoInvalido(Integer ano) throws InterruptedException {
        if(ano.toString().length() != 4){
            input.setText("");
            input.setPromptText("Insira um ano válido! Exemplo: 2022");
            btnProcurar.setDisable(false);
            return true;
        }
        return false;
    }

    /* BOTÃO PROCURAR */
    public void onProcurarAction() throws InterruptedException {
        Json.clear();
        btnProcurar.setDisable(true);
        try {
            Integer ano = Integer.valueOf(input.getText());
            if(anoInvalido(ano)){
                return;
            }
            tableView.getItems().clear();
            data.setCellValueFactory(new PropertyValueFactory<>("data"));
            evento.setCellValueFactory(new PropertyValueFactory<>("evento"));
            // chamar thread
            iniciarGet(ano);
        }
        catch(Exception e){
            anoInvalido(1);
        }
    }

    /* THREAD QUE VAI CHAMAR A CLASSE WEBSCRAPPING */
    public void iniciarGet(Integer ano) {
        String horaA = new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime());
        tableView.getItems().add(new Item(horaA, "Buscando feriados, aguarde..."));
        new Thread(()->{
            try {
                scraping.getAction(this, ano);
            } catch (IOException ignored) {}
        }).start();
    }

}