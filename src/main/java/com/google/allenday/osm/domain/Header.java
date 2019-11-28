package com.google.allenday.osm.domain;

import java.util.List;

/**
 * TODO.
 */
public class Header {
    /**
     * TODO.
     */
    private List<String> requiredFeatures;
    /**
     * TODO.
     */
    private List<String> optionalFeatures;
    /**
     * TODO.
     */
    private String writingProgram;
    /**
     * TODO.
     */
    private String source;

    /**
     * TODO.
     */
    public Header() {

    }

    /**
     * TODO.
     * @return List<String>
     */
    public List<String> getRequiredFeatures() {
        return requiredFeatures;
    }

    /**
     * TODO.
     * @param val to set
     */
    public void setRequiredFeatures(final List<String> val) {
        this.requiredFeatures = val;
    }

    /**
     * TODO.
     * @return Double
     */
    public List<String> getOptionalFeatures() {
        return optionalFeatures;
    }

    /**
     * TODO.
     * @param val to set
     */
    public void setOptionalFeatures(final List<String> val) {
        this.optionalFeatures = val;
    }

    /**
     * TODO.
     * @return String
     */
    public String getwritingProgram() {
        return writingProgram;
    }

    /**
     * TODO.
     * @param val to set
     */
    public void setwritingProgram(final String val) {
        this.writingProgram = val;
    }

    /**
     * TODO.
     * @return String
     */
    public String getSource() {
        return source;
    }

    /**
     * TODO.
     * @param val to set
     */
    public void setSource(final String val) {
        this.source = val;
    }


}
