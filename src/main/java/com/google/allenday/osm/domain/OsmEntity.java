package com.google.allenday.osm.domain;

import org.apache.avro.reflect.Nullable;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Map;

/**
 * TODO.
 */
public abstract class OsmEntity {
    /**
     * TODO.
     */
    private Long id;

    /**
     * TODO.
     */
    @Nullable
    private Integer version;

    /**
     * TODO.
     */
    @Nullable
    private String username;

    /**
     * TODO.
     */
    @Nullable
    private Long changeset;

    /**
     * TODO.
     */
    private Boolean visible;

    /**
     * TODO.
     */
    @Nullable
    @JsonProperty("osm_timestamp")
    private Long timestamp;

    //TODO create this in a SQL "CREATE TABLE ... SELECT * ..." that builds destination table.
    //@Nullable
    //private String geometry;

    /**
     * TODO.
     */
    @JsonProperty("all_tags")
    private Map<String, String> tags;

    /**
     * TODO.
     * @return Long
     */
    public Long getId() {
        return id;
    }

    /**
     * TODO.
     * @param val to set
     */
    public void setId(final Long val) {
        this.id = val;
    }

    /**
     * TODO.
     * @return Integer
     */
    public Integer getVersion() {
        return version;
    }

    /**
     * TODO.
     * @param val to set
     */
    public void setVersion(final Integer val) {
        this.version = val;
    }

    /**
     * TODO.
     * @return String
     */
    public String getUsername() {
        return username;
    }

    /**
     * TODO.
     * @param val to set
     */
    public void setUsername(final String val) {
        this.username = val;
    }

    /**
     * TODO.
     * @return Long
     */
    public Long getChangeset() {
        return changeset;
    }

    /**
     * TODO.
     * @param val to set
     */
    public void setChangeset(final Long val) {
        this.changeset = val;
    }

    /**
     * TODO.
     * @return Boolean
     */
    public Boolean getVisible() {
        return visible;
    }

    /**
     * TODO.
     * @param val to set
     */
    public void setVisible(final Boolean val) {
        this.visible = val;
    }

    /**
     * TODO.
     * @return Long
     */
    public Long getTimestamp() {
        return timestamp;
    }

    /**
     * TODO.
     * @param val to set
     */
    public void setTimestamp(final Long val) {
        this.timestamp = val;
    }


    /**
     * TODO.
     * @return Map<String, String>
     */
    public Map<String, String> getTags() {
        return tags;
    }

    /**
     * TODO.
     * @param val to set
     */
    public void setTags(final Map<String, String> val) {
        this.tags = val;
    }

}
