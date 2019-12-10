package com.example.client.helper;

import com.google.gson.Gson;

public class JsonParsingHelper {

    public static String ParseToJson(Object obj){
        Gson gson = new Gson();
        String json = gson.toJson(obj);
        return json;

    }

}
