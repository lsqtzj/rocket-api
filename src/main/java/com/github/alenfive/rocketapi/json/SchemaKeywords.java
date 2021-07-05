package com.github.alenfive.rocketapi.json;

public enum SchemaKeywords {

    SCHEMA("$schema"),
    ID("id"),
    TITLE("title"),
    NAME("name"),
    DESCRIPTION("description"),
    TYPE("type"),
    PROPERTIES("properties"),
    ITEMS("items"),
    REQUIRED("required"),
    MINIMUM("minimum"),
    MAXIMUM("maximum"),
    EXCLUSIVEMINIMUM("exclusiveMinimum"),
    EXCLUSIVEMAXIMUM("exclusiveMaximum"),
    MINLENGTH("minLength"),
    MAXLENGTH("maxLength"),
    DEFAULT("default"),
    MINITEMS("minItems"),
    UNIQUEITEMS("uniqueItems");

    private String keyword;

    /**
     * @param keyword
     */
    private SchemaKeywords(String keyword) {
        this.keyword = keyword;
    }

    public String getKeyword() {
        return keyword;
    }

    @Override
    public String toString() {
        return keyword;
    }

}