package com.google.allenday.osm.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.avro.reflect.Nullable;
import org.apache.beam.repackaged.beam_sdks_java_core.com.google.common.base.Objects;
import org.apache.beam.sdk.coders.AvroCoder;
import org.apache.beam.sdk.coders.DefaultCoder;
import org.codehaus.jackson.annotate.JsonProperty;
import java.util.List;
import java.util.Map;

/**
 * TODO.
 */
@DefaultCoder(AvroCoder.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Way {

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
    private List<Long> nodes;

    /**
     * TODO.
     */
    @JsonProperty("all_tags")
    private Map<String, String> tags;

    /**
     * TODO.
     */
    public Way() {

    }

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
    public Long getchangeset() {
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
     * @return List<Long>
     */
    public List<Long> getNodes() {
        return nodes;
    }

    /**
     * TODO.
     * @param val to set
     */
    public void setNodes(final List<Long> val) {
        this.nodes = val;
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

    /**
     * TODO.
     * @param o
     * @return true if objects are equal
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Way way = (Way) o;
        return Objects.equal(id, way.id)
                &&
                Objects.equal(version, way.version)
                &&
                Objects.equal(username, way.username)
                &&
                Objects.equal(changeset, way.changeset)
                &&
                Objects.equal(visible, way.visible)
                &&
                Objects.equal(timestamp, way.timestamp)
                &&
                Objects.equal(nodes, way.nodes)
                &&
                Objects.equal(tags, way.tags);
    }

    /**
     * TODO.
     * @return hash of the object
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(id, version, username, changeset, visible, timestamp, nodes, tags);
    }

    /**
     * TODO.
     * @return serialization of the object.
     */
    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("id", id)
                .add("version", version)
                .add("username", username)
                .add("changeset", changeset)
                .add("visible", visible)
                .add("timestamp", timestamp)
                .add("nodes", nodes)
                .add("tags", tags)
                .toString();
    }
}
