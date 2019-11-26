package com.google.allenday.osm.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.avro.reflect.Nullable;
import org.apache.beam.repackaged.beam_sdks_java_core.com.google.common.base.Objects;
import org.apache.beam.sdk.coders.AvroCoder;
import org.apache.beam.sdk.coders.DefaultCoder;
import org.codehaus.jackson.annotate.JsonProperty;
import java.util.List;
import java.util.Map;

@DefaultCoder(AvroCoder.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Way {

    private Long id;

    @Nullable
    private Integer version;

    @Nullable
    private String username;

    @Nullable
    private Long changeset;

    private Boolean visible;

    @Nullable
    @JsonProperty("osm_timestamp")
    private Long timestamp;

    //TODO create this in a SQL "CREATE TABLE ... SELECT * ..." that builds destination table.
    //@Nullable
    //private String geometry;

    private List<Long> nodes;

    @JsonProperty("all_tags")
    private Map<String,String> tags;

    public Way() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getchangeset() {
        return changeset;
    }

    public void setChangeset(Long changeset) {
        this.changeset = changeset;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public List<Long> getNodes() {
        return nodes;
    }

    public void setNodes(List<Long> nodes) {
        this.nodes = nodes;
    }

    public Map<String,String> getTags() {
        return tags;
    }

    public void setTags(Map<String,String> tags) {
        this.tags = tags;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Way way = (Way) o;
        return Objects.equal(id, way.id) &&
                Objects.equal(version, way.version) &&
                Objects.equal(username, way.username) &&
                Objects.equal(changeset, way.changeset) &&
                Objects.equal(visible, way.visible) &&
                Objects.equal(timestamp, way.timestamp) &&
                Objects.equal(nodes, way.nodes) &&
                Objects.equal(tags, way.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, version, username, changeset, visible, timestamp, nodes, tags);
    }

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
