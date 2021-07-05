package com.github.alenfive.rocketapi.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public enum ValueTypes {

    OBJECT("object"),
    ARRAY("array"),
    STRING("string"),
    NUMBER("number"),
    INTEGER("integer"),// JSON Schema
    BOOLEAN("boolean"),
    NULL("null");

    private String type;

    /**
     * @param type
     */
    private ValueTypes(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return type;
    }
    /**
     * 判断 Json 元素的类型
     *
     * @param jsonElement
     * @return
     */
    public static String getJsonValueType(JsonElement jsonElement) {
        if (jsonElement.isJsonObject()) {
            return OBJECT.toString();
        }
        if (jsonElement.isJsonArray()) {
            return ARRAY.toString();
        }
        if (jsonElement.isJsonPrimitive()) {
            JsonPrimitive asJsonPrimitive = jsonElement.getAsJsonPrimitive();
            if (asJsonPrimitive.isBoolean()) {
                return BOOLEAN.toString();
            }
            if (asJsonPrimitive.isNumber()) {
                try{
                    if(String.valueOf(asJsonPrimitive.getAsBigInteger()).equals(asJsonPrimitive.getAsString())){
                        return INTEGER.toString();
                    }
                }catch(Exception e){
                }
                return NUMBER.toString();
            }
            return STRING.toString();
        }
        return NULL.toString();
    }
}