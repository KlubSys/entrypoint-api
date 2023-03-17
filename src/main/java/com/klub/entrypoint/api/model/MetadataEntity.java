package com.klub.entrypoint.api.model;


import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class MetadataEntity extends UuidAsIdEntity {

    @Column(name = "object", nullable = false)
    private String key;

    @Column(name = "content", nullable = false)
    private String value;

    /**
     * Will help to deserialize the content value
     */
    @Column(name = "value_type", nullable = false)
    private String valueType;

    public MetadataEntity() {
        super();
    }

    public MetadataEntity(String id, String key, String value, String valueType) {
        super(id);
        this.key = key;
        this.value = value;
        this.valueType = valueType;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValueType() {
        return valueType;
    }

    public void setValueType(String valueType) {
        this.valueType = valueType;
    }
}
