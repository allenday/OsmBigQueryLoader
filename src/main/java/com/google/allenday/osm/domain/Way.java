package com.google.allenday.osm.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.beam.repackaged.beam_sdks_java_core.com.google.common.base.Objects;
import org.apache.beam.sdk.coders.AvroCoder;
import org.apache.beam.sdk.coders.DefaultCoder;
import java.util.List;

/**
 * TODO.
 */
@DefaultCoder(AvroCoder.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Way extends OsmEntity {
    /**
     * TODO.
     */
    private List<Long> nodes;

    /**
     * TODO.
     */
    public Way() {

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
        return Objects.equal(getId(), way.getId())
                &&
                Objects.equal(getVersion(), way.getVersion())
                &&
                Objects.equal(getUsername(), way.getUsername())
                &&
                Objects.equal(getChangeset(), way.getChangeset())
                &&
                Objects.equal(getVisible(), way.getVisible())
                &&
                Objects.equal(getTimestamp(), way.getTimestamp())
                &&
                Objects.equal(getNodes(), way.getNodes())
                &&
                Objects.equal(getTags(), way.getTags());
    }

    /**
     * TODO.
     * @return hash of the object
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(
                getId(), getVersion(), getUsername(), getChangeset(),
                getVisible(), getTimestamp(), getNodes(), getTags());
    }

    /**
     * TODO.
     * @return serialization of the object.
     */
    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("id", getId())
                .add("version", getVersion())
                .add("username", getUsername())
                .add("changeset", getChangeset())
                .add("visible", getVisible())
                .add("timestamp", getTimestamp())
                .add("nodes", getNodes())
                .add("tags", getTags())
                .toString();
    }
}
