package fatura.cartao.util;

import com.google.gson.Gson;
import fatura.cartao.dto.EntradaJson;

public class GsonDeserializer {
    private GsonDeserializer() {
    }

    public static EntradaJson getJson(String json) {
        return new Gson().fromJson(json, EntradaJson.class);
    }
}
