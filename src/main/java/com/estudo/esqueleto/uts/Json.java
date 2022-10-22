package com.estudo.esqueleto.uts;

import java.util.HashMap;

public class Json {
    private static final HashMap<Object, HashMap<Object, HashMap<Object, Object>>> dados = new HashMap<>();
    private static final HashMap<Object, HashMap<Object, Object>> itens = new HashMap<>();

    private static Integer get_index() {
        return dados.size();
    }

    public static void clear(){
        dados.clear();
        itens.clear();
    }

    /* ADICIONAR NOVO JSON */
    public static void add(String subindex, String item) {
        itens.clear();
        HashMap<Object, Object> newitens = new HashMap<>();
        newitens.put(item, null);
        itens.put(subindex, newitens);
        dados.put(get_index(), new HashMap<>(itens));
    }
    /* ADICIONAR NOVO ITEM DENTRO DE UM JSON EXISTENTE */
    public static void add(Object index, String subindex, String item){
        itens.clear();
        HashMap<Object, Object> newitens = new HashMap<>();
        newitens.put(item, null);
        itens.put(subindex, newitens);
        if(dados.get(index) == null){
            dados.put(index, new HashMap<>(itens));
            return;
        }
        dados.get(index).put(subindex, newitens);
    }
    /* ADICIONAR NOVO ITEM COM TRÊS INDEXs*/
    // index{subindex{chave=valor}}
    public static void add(Object index, String subindex, String chave, String item){
        HashMap<Object, HashMap<Object, Object>> novos_itens = new HashMap<>();
        HashMap<Object, Object> thirdindex = new HashMap<>();
        thirdindex.put(chave, item);
        novos_itens.put(subindex, thirdindex);
        if(dados.get(index) == null || dados.get(index).get(subindex) == null){
            dados.put(index, novos_itens);
            return;
        }
        dados.get(index).get(subindex).put(chave, item);
    }

    /* PEGAR TODOS OS ITENS */
    public static HashMap<?, ?> get() {
        return dados;
    }

    /* PEGAR ITENS DO INDEX */
    /* RETORNO: {nome=exemplo, area=teste} */
    public static HashMap<Object, HashMap<Object, Object>> get(Object index) {
        return dados.get(index);
    }

    /* PEGAR ITEM DENTRO DE UM INDEX */
    /* RETORNO: exemplo */
    public static String get(Object index, String subindex) {
        return dados.get(index).get(subindex).keySet().toString().replace("]", "").replace("[", "");
    }

    /* PEGAR ITEM DENTRO DE UM JSON COM 3 INDEXS */
    public static Object get(Object index, String subindex, String thirdindex) {
        return dados.get(index).get(subindex).get(thirdindex);
    }

    /* REMOVER ITEM */
    public static void remove(Object remover){
        dados.remove(remover);
    }
    public static void remove(Object index, String remover){
        dados.get(index).remove(remover);
    }
    public static void remove(Object index, Object subindex, String remover){
        dados.get(index).get(subindex).remove(remover);
    }

    /* ATUALIZAR INDEX */
    // {replaced={nome=example}}
    public static HashMap<Object, HashMap<Object, HashMap<Object, Object>>> update(Object oldvalue, String newvalue){
        HashMap<Object, HashMap<Object, Object>> itens_atuais = dados.get(oldvalue);
        dados.remove(oldvalue);
        dados.put(newvalue, itens_atuais);
        return dados;
    }
    /* ATUALIZAR SUB INDEX NO INDEX */
    // {index={replaced=example}}
    public static void update_index(Object index, String oldvalue, String newvalue){
        if(dados.get(index).get(oldvalue) == null){
            return;
        }
        HashMap<Object, Object> item_atual = dados.get(index).get(oldvalue);
        dados.get(index).remove(oldvalue);
        dados.get(index).put(newvalue, item_atual);
    }
    /* ATUALIZAR ITEM */
    // {index={nome=replaced}}
    public static void update(Object index, String subindex, String newvalue){
        if(dados.get(index).get(subindex) == null){
            return;
        }
        HashMap<Object, Object> newitem = new HashMap<>();
        newitem.put(newvalue, null);
        dados.get(index).replace(subindex, newitem);
    }
    /* ATUALIZAR ITEM COM 3 INDEXs */
    // {index={subindex={thirdindex=replaced}}}
    public static void update(Object index, String subindex, Object thirdindex, String newvalue){
        if(dados.get(index).get(subindex).get(thirdindex) == null){
            return;
        }
        dados.get(index).get(subindex).put(thirdindex, newvalue);
    }
}
