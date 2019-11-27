package com.google.allenday.osm.domain;

import org.apache.avro.reflect.Nullable;
import org.apache.beam.repackaged.beam_sdks_java_core.com.google.common.base.Objects;

/**
 * TODO.
 */
public class RelationMember {
    /**
     * TODO.
     */
    private String type;
    /**
     * TODO.
     */
    private Long id;
    /**
     * TODO.
     */
    @Nullable
    private String role;

    /**
     * TODO.
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * TODO.
     * @param val type
     */
    public void setType(final String val) {
        type = val;
    }


    /**
     * TODO.
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * TODO.
     * @param val id
     */
    public void setId(final Long val) {
        id = val;
    }


    /**
     * TODO.
     * @return role
     */
    public String getRole() {
        return role;
    }

    /**
     * TODO.
     * @param val role
     */
    public void setRole(final String val) {
        role = val;
    }

    /**
     * TODO.
     */
    public RelationMember() {

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
        RelationMember rm = (RelationMember) o;
        return Objects.equal(getType(), rm.getType())
                &&
                Objects.equal(getId(), rm.getId())
                &&
                Objects.equal(getRole(), rm.getRole());
    }

    /**
     * TODO.
     * @return hash of the object
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(getType(), getId(), getRole());
    }

    /**
     * TODO.
     * @return serialization of the object.
     */
    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("type", getType())
                .add("id", getId())
                .add("role", getRole())
                .toString();
    }
}
