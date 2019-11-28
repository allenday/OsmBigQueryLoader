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
@SuppressWarnings("PMD.UnusedImports")
public class Relation extends OsmEntity {
    /**
     * TODO.
     */
    private List<RelationMember> members;

    /**
     * TODO.
     */
    //private List<RelationMember> relations;

    /**
     * TODO.
     */
    //private List<RelationMember> ways;

    /**
     * TODO.
     */
    //private List<RelationMember> nodes;

    /**
     * TODO.
     */
    public Relation() {

    }

    /**
     * TODO.
     * @return List<RelationMember>
     */
    public List<RelationMember> getMembers() {
        return members;
    }

    /**
     * TODO.
     * @param val to set
     */
    public void setMembers(final List<RelationMember> val) {
        this.members = val;
    }

    /**
     * TODO.
     * @return List<RelationMember>
     */
//    public List<RelationMember> getRelations() {
//        return relations;
//    }

    /**
     * TODO.
     * @param val to set
     */
//    public void setRelations(final List<RelationMember> val) {
//        this.relations = val;
//    }

    /**
     * TODO.
     * @return List<RelationMember>
     */
//    public List<RelationMember> getWays() {
//        return ways;
//    }

    /**
     * TODO.
     * @param val to set
     */
//    public void setWays(final List<RelationMember> val) {
//        this.ways = val;
//    }

    /**
     * TODO.
     * @return List<RelationMember>
     */
//    public List<RelationMember> getNodes() {
//        return nodes;
//    }

    /**
     * TODO.
     * @param val to set
     */
//    public void setNodes(final List<RelationMember> val) {
//        this.nodes = val;
//    }

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
        Relation rel = (Relation) o;
        return Objects.equal(getId(), rel.getId())
                &&
                Objects.equal(getVersion(), rel.getVersion())
                &&
                Objects.equal(getUsername(), rel.getUsername())
                &&
                Objects.equal(getChangeset(), rel.getChangeset())
                &&
                Objects.equal(getVisible(), rel.getVisible())
                &&
                Objects.equal(getTimestamp(), rel.getTimestamp())
                &&
                Objects.equal(getMembers(), rel.getMembers())
                &&
//                Objects.equal(getRelations(), rel.getRelations())
//                &&
//                Objects.equal(getWays(), rel.getWays())
//                &&
//                Objects.equal(getNodes(), rel.getNodes())
//                &&
                Objects.equal(getTags(), rel.getTags());
    }

    /**
     * TODO.
     * @return hash of the object
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(
                getId(), getVersion(), getUsername(), getChangeset(), getVisible(), getTimestamp(),
//                getRelations(), getWays(), getNodes(),
                getTags());
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
                .add("members", getMembers())
//                .add("relations", getRelations())
//                .add("ways", getWays())
//                .add("nodes", getNodes())
                .add("tags", getTags())
                .toString();
    }
}
