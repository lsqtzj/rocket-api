package com.github.alenfive.rocketapi.json;

import com.google.gson.*;

import java.util.Map;

public class JsonSchema {
    public String fromString(String string){
        JsonParser jsonParser = new JsonParser();
        JsonElement jsonElement = jsonParser.parse(string);

        GsonBuilder gsonBuilder = new GsonBuilder();

        //gsonBuilder.setPrettyPrinting();

        gsonBuilder.disableHtmlEscaping();
        Gson gson = gsonBuilder.create();
        JsonObject jsonSchemaElement = makeSchema(jsonElement, null, true, null);
        return gson.toJson(jsonSchemaElement);
    }
    /**
     * 生成 Json Schema 对象
     *
     * @param element
     * @param name
     * @param isfirstlevel
     * @param required
     * @return
     */
    private JsonObject makeSchema(JsonElement element, String name, boolean isfirstlevel, JsonArray required) {
        JsonObject jsonObject = new JsonObject();
        if (isfirstlevel) {
            jsonObject.addProperty(SchemaKeywords.SCHEMA.toString(), SchemaVersions.V4.getVersion());
        }
        String elementType = ValueTypes.getJsonValueType(element);
        jsonObject.addProperty(SchemaKeywords.TYPE.toString(), elementType);
        JsonArray newRequired = new JsonArray();
        if (elementType.equals(ValueTypes.OBJECT.toString()) && !element.getAsJsonObject().entrySet().isEmpty()) {
            JsonObject propertiesObject = new JsonObject();
            for (Map.Entry<String, JsonElement> propertyElemement : element.getAsJsonObject().entrySet()) {
                propertiesObject.add(propertyElemement.getKey(), makeSchema(propertyElemement.getValue(), propertyElemement.getKey(), false, newRequired));
            }
            jsonObject.add(SchemaKeywords.PROPERTIES.toString(), propertiesObject);
        } else if (elementType.equals(ValueTypes.ARRAY.toString()) && element.getAsJsonArray().size() > 0) {
            JsonArray jsonArray = element.getAsJsonArray();
            jsonObject.add(SchemaKeywords.ITEMS.toString(), makeSchema(jsonArray.get(0), "0", false,  new JsonArray()));
        }
        return jsonObject;
    }

}
