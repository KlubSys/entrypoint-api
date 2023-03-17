package com.klub.entrypoint.api.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "klub_file")
public class KlubFileEntity extends UuidAsIdEntity {

    private Boolean isDir;
    private String filename;
    private String temporaryStorageFilename;
    private String fileType;

    private Integer level = 0;

    private Boolean uploaded = false;

    private String jobId;

    private Boolean jobScheduled = false;
    private String firstBlockGroupId;

    /**
     * Specify into how many data blocs the file was divided
     */
    private Integer dataBlocCount;

    @JoinColumn(name = "parent", nullable = true)
    @ManyToOne
    private KlubFileEntity parent;

    public KlubFileEntity() {
        super();
    }

    public KlubFileEntity(String id, Boolean isDir, String filename, String temporaryStorageFilename,
                          String fileType, Integer level, Boolean uploaded, String jobId,
                          Boolean jobScheduled, String firstBlockGroupId, Integer dataBlocCount, KlubFileEntity parent) {
        super(id);
        this.isDir = isDir;
        this.filename = filename;
        this.temporaryStorageFilename = temporaryStorageFilename;
        this.fileType = fileType;
        this.level = level;
        this.uploaded = uploaded;
        this.jobId = jobId;
        this.jobScheduled = jobScheduled;
        this.firstBlockGroupId = firstBlockGroupId;
        this.dataBlocCount = dataBlocCount;
        this.parent = parent;
    }

    public Boolean getDir() {
        return isDir;
    }

    public void setDir(Boolean dir) {
        isDir = dir;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public KlubFileEntity getParent() {
        return parent;
    }

    public void setParent(KlubFileEntity parent) {
        this.parent = parent;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Boolean getUploaded() {
        return uploaded;
    }

    public void setUploaded(Boolean uploaded) {
        this.uploaded = uploaded;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getFirstBlockGroupId() {
        return firstBlockGroupId;
    }

    public void setFirstBlockGroupId(String firstBlockGroupId) {
        this.firstBlockGroupId = firstBlockGroupId;
    }

    public Boolean getJobScheduled() {
        return jobScheduled;
    }

    public void setJobScheduled(Boolean jobScheduled) {
        this.jobScheduled = jobScheduled;
    }

    public String getTemporaryStorageFilename() {
        return temporaryStorageFilename;
    }

    public void setTemporaryStorageFilename(String temporaryStorageFilename) {
        this.temporaryStorageFilename = temporaryStorageFilename;
    }

    public Integer getDataBlocCount() {
        return dataBlocCount;
    }

    public void setDataBlocCount(Integer dataBlocCount) {
        this.dataBlocCount = dataBlocCount;
    }
}
