package com.google.allenday.osm.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.beam.repackaged.beam_sdks_java_core.com.google.common.base.Objects;
import org.apache.beam.sdk.coders.AvroCoder;
import org.apache.beam.sdk.coders.DefaultCoder;

/**
 * TODO.
 */
@DefaultCoder(AvroCoder.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Node extends OsmEntity {

    /**
     * TODO.
     */
    private Double latitude;

    /**
     * TODO.
     */
    private Double longitude;

    //TODO create this in a SQL "CREATE TABLE ... SELECT * ..." that builds destination table.
    //@Nullable
    //private String geometry;

    /**
     * TODO.
     */
    public Node() {

    }


    /**
     * TODO.
     * @return Double
     */
    public Double getLatitude() {
        return latitude;
    }

    /**
     * TODO.
     * @param val to set
     */
    public void setLatitude(final Double val) {
        this.latitude = val;
    }

    /**
     * TODO.
     * @return Double
     */
    public Double getLongitude() {
        return longitude;
    }

    /**
     * TODO.
     * @param val to set
     */
    public void setLongitude(final Double val) {
        this.longitude = val;
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
        Node node = (Node) o;
        return Objects.equal(getId(), node.getId())
                &&
                Objects.equal(getVersion(), node.getVersion())
                &&
                Objects.equal(getUsername(), node.getUsername())
                &&
                Objects.equal(getChangeset(), node.getChangeset())
                &&
                Objects.equal(getVisible(), node.getVisible())
                &&
                Objects.equal(getTimestamp(), node.getTimestamp())
                &&
                Objects.equal(getLatitude(), node.getLatitude())
                &&
                Objects.equal(getLongitude(), node.getLongitude())
                &&
                Objects.equal(getTags(), node.getTags());
    }

    /**
     * TODO.
     * @return hash of the object
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(getId(), getVersion(), getUsername(), getChangeset(),
                getVisible(), getTimestamp(), getLatitude(), getLongitude(), getTags());
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
                .add("latitude", getLatitude())
                .add("longitude", getLongitude())
                .add("tags", getTags())
                .toString();
    }
}
