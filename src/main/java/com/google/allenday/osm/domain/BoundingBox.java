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
public class BoundingBox {
    /**
     * TODO.
     */
    private Double top;
    /**
     * TODO.
     */
    private Double left;
    /**
     * TODO.
     */
    private Double bottom;
    /**
     * TODO.
     */
    private Double right;

    /**
     * TODO.
     */
    public BoundingBox() {

    }

    /**
     * TODO.
     * @return Double
     */
    public Double getTop() {
        return top;
    }

    /**
     * TODO.
     * @param val to set
     */
    public void setTop(final Double val) {
        this.top = val;
    }

    /**
     * TODO.
     * @return Double
     */
    public Double getLeft() {
        return left;
    }

    /**
     * TODO.
     * @param val to set
     */
    public void setLeft(final Double val) {
        this.left = val;
    }

    /**
     * TODO.
     * @return Double
     */
    public Double getBottom() {
        return bottom;
    }

    /**
     * TODO.
     * @param val to set
     */
    public void setBottom(final Double val) {
        this.bottom = val;
    }

    /**
     * TODO.
     * @return Double
     */
    public Double getRight() {
        return right;
    }

    /**
     * TODO.
     * @param val to set
     */
    public void setRight(final Double val) {
        this.right = val;
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
        BoundingBox bb = (BoundingBox) o;
        return Objects.equal(getTop(), bb.getTop())
                &&
                Objects.equal(getLeft(), bb.getLeft())
                &&
                Objects.equal(getBottom(), bb.getBottom())
                &&
                Objects.equal(getRight(), bb.getRight());
    }

    /**
     * TODO.
     * @return hash of the object
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(getTop(), getLeft(), getBottom(), getRight());
    }

    /**
     * TODO.
     * @return serialization of the object.
     */
    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("top", getTop())
                .add("left", getLeft())
                .add("bottom", getBottom())
                .add("right", getRight())
                .toString();
    }
}
