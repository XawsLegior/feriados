package com.estudo.esqueleto.webscraping;

import com.estudo.esqueleto.HelloController;
import com.estudo.esqueleto.uts.Item;
import com.estudo.esqueleto.uts.Json;
import com.estudo.esqueleto.uts.Regex;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class scraping {
    private static HelloController parent;

    public static void getAction(HelloController self, Integer ano) throws IOException {
        parent = self;
        URL url = new URL("https://www.calendarr.com/brasil/feriados-" + ano);
        HttpURLConnection request = (HttpURLConnection) url.openConnection();
        request.setRequestMethod("GET");
        int response = request.getResponseCode();
        if (response == 200) {
            tratarCodigoFonte(request);
        }
        self.btnProcurar.setDisable(false);
    }

    /* APLICAR REGEX NO CÓDIGO FONTE RETORNADO
    * CHAMAR FUNÇÃO QUE VAI APLICAR REGEX NOS VALORES RETORNADOS (DATAS E FERIADOS)
    */

    private static void tratarCodigoFonte(HttpURLConnection request) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String line;
        StringBuilder source = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            source.append(line);
        }
        capturarDatas(source);
    }

    /* APLICAR REGEX NOS VALORES RETORNADOS
    * DATAS E FERIADOS
    */
    private static void capturarDatas(StringBuilder source) {
        /* APLICAR REGEX DE BUSCA DO BLOCO DA DIV QUE CONTEM AS INFORMAÇÕES */
        Pattern pattern = Pattern.compile("(<div class=\"col-xs-12 col-sm-8 mt20 year-holiday-list[\\w\\d\\n <>\"À-ÿ/=\\-]+).*<div class=\"vertical-menu\">");
        Matcher blocoDiv = pattern.matcher(source);
        StringBuilder result = new StringBuilder();
        while (blocoDiv.find()) {
            result.append(blocoDiv.group());
        }
        /* CAPTURAR DADOS */
        Regex.salvarDado(result, parent);

        /* LISTAR EM ORDEM */
        parent.tableView.getItems().clear();
        int index = 1;
        String feriado = "";
        int dataSize = 0, feriadoSize = 0;

        while(Json.get(index) != null){
            HashMap<Object, HashMap<Object, Object>> dados = Json.get(index);
            for(Object key: dados.keySet()){
                if(key.equals("mes")){
                    continue;
                }
                if(Pattern.compile("[0-9]*").matcher(key.toString()).find()){
                    feriado = Json.get(index, key.toString());
                    if(feriado.length()*3 > feriadoSize){
                        feriadoSize = feriado.length() * 3;
                    }

                    String data = key + " de " + Json.get(index, "mes");
                    if(data.length()*3 > dataSize){
                        dataSize = data.length() * 3;
                    }
                    parent.tableView.getItems().add(new Item(data, feriado));
                }

            }
            index+=1;
        }

        parent.data.setPrefWidth(parent.data.getPrefWidth() + dataSize);
    }

}
