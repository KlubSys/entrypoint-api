package com.klub.entrypoint.api.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "klub_dir")
public class KlubDirEntity extends UuidAsIdEntity {

    private String name;
    //TODO add permission table and stats table

    @JoinColumn(name = "parent", nullable = true)
    @ManyToOne
    private KlubDirEntity parent;

    public KlubDirEntity() {
        super();
    }

    public KlubDirEntity(String id, String name, KlubDirEntity parent) {
        super(id);
        this.name = name;
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public KlubDirEntity getParent() {
        return parent;
    }

    public void setParent(KlubDirEntity parent) {
        this.parent = parent;
    }
}
