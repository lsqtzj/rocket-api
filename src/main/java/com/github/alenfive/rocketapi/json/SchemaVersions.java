package com.github.alenfive.rocketapi.json;

public enum SchemaVersions {

    V3("http://json-schema.org/draft-03/schema#"),// 历史版本
    V4("http://json-schema.org/draft-04/schema#");// 历史版本

    private String version;

    /**
     * @param version
     */
    private SchemaVersions(String version) {
        this.version = version;
    }

    public String getVersion() {
        return version;
    }

    @Override
    public String toString() {
        return version;
    }

}
