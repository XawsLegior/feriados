package com.estudo.esqueleto.uts;

import java.nio.charset.StandardCharsets;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.estudo.esqueleto.HelloController;

public class Regex {
    /* REMOVER LIXO DO REGEX */
    private static final String[] Lixo = {"holiday-month\">", "holiday-day\">", "holiday-name\" [\\-=\\w=\"\\/\\-> ]+\">", "</span", "</a"};

    public static String removerLixo(String dado) {
        for (String lixo : Lixo) {
            if (dado.contains(lixo) || Pattern.compile(lixo).matcher(dado).find()) {
                dado = dado.replaceAll(lixo, "");
            }
        }
        return dado;
    }

    // SALVAR MES
    public static void salvarDado(CharSequence result, HelloController self) {
        //Matcher dadosFeriados = Pattern.compile("holiday-month\">[\\D]+</span|holiday-day\">[\\w]+|holiday-name\" [\\w\\d=\" /\\->À-ú]+").matcher(result);
        Matcher dadosFeriados = Pattern.compile("holiday-month\">[\\D]+</span|holiday-day\">[\\w]+|holiday-name\" [\\D]+[\\w\\d\\s]+</a").matcher(result);
        ArrayList<String> dadosFeriadosTratados = new ArrayList<>();
        while (dadosFeriados.find()) {
            String dado = new String(removerLixo(dadosFeriados.group()).getBytes(), StandardCharsets.UTF_8);

            if(dadosFeriados.group().matches("holiday-month\">[\\D]+</span")){
                dadosFeriadosTratados.add("mes");
                dadosFeriadosTratados.add(dado);
            }
            else{
                dadosFeriadosTratados.add(dado);
            }
        }

        int index = 0, index_item_atual = 0, mes_index = 0;
        for(String dado: dadosFeriadosTratados){
            try {
                if (dado.equals("mes")) {
                    mes_index += 1;
                    Json.add(mes_index, "mes", dadosFeriadosTratados.get(index + 1));
                    index_item_atual = -1;
                }
                else if(index_item_atual == 1){
                    Json.add(mes_index, dadosFeriadosTratados.get(index), dadosFeriadosTratados.get(index + 1));
                    index_item_atual = -1;
                }
                index_item_atual+=1;
                index+=1;
            }
            catch (Exception ignored){}
        }
        /*for(String dado: dadosFeriadosTratados){
            if(dado.equals("mes")){
                Json.add(mes_index, "mes", dadosFeriadosTratados.get(index+1));
                try {
                    for (int x = 2; x < dadosFeriadosTratados.size() - 4; x += 2) {
                        String dia = dadosFeriadosTratados.get(index + x);
                        String feriado = dadosFeriadosTratados.get(index + x + 1);
                        Json.add(mes_index, dia, feriado);

                        if (dadosFeriadosTratados.get(index + x + 2).equals("mes")) {
                            break;
                        }
                    }
                }
                catch(Exception e){
                    Json.update(mes_index, dadosFeriadosTratados.get(index+1), dadosFeriadosTratados.get(index+2));
                    break;
                }
                mes_index+=1;
            }
            index+=1;
        }*/
    }
//
}
