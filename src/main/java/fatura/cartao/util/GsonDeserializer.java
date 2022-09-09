package fatura.cartao.util;

import fatura.cartao.dto.EntradaJson;
import com.google.gson.Gson;

public class GsonDeserializer {
    public static EntradaJson getJson(String json) {
        return new Gson().fromJson(json, EntradaJson.class);
    }
}
