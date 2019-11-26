package com.google.allenday.osm.domain;

import com.google.allenday.osm.domain.Node;
import com.google.allenday.osm.domain.Way;

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
public class Relation {
}
