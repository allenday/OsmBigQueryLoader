package com.google.allenday.osm.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.beam.sdk.coders.AvroCoder;
import org.apache.beam.sdk.coders.DefaultCoder;

/**
 * TODO.
 */
@DefaultCoder(AvroCoder.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Node {
}
