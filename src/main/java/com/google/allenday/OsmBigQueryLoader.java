package com.google.allenday;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import org.akashihi.osm.parallelpbf.ParallelBinaryParser;
import org.akashihi.osm.parallelpbf.entity.*;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.zip.GZIPOutputStream;

/**
 * Load OSM records to BigQuery.
 *
 */
@SuppressWarnings({"PMD.UnusedPrivateMethod", "PMD.UnusedPrivateField"})
public class OsmBigQueryLoader {
    /**
     * TODO.
     * @param w outut destination
     * @param s to print
     * @throws IOException failed to write
     */
    public static void safePrintln(final BufferedWriter w, final String s) throws IOException {
        synchronized (w) {
            w.write(s + "\n");
        }
        //System.err.println(s);
    }
    /**
     * TODO.
     */
    private ObjectMapper mapper = new ObjectMapper();
    /**
     * TODO.
     */
    private Map<String, BufferedWriter> outputs = new HashMap<String, BufferedWriter>();

    /**
     * TODO.
     */
    private final StringBuilder output = new StringBuilder();
    /**
     * TODO.
     * @param header Header
     */
    void processHeader(@SuppressWarnings("unused") final Header header) {
        com.google.allenday.osm.domain.Header bean = new com.google.allenday.osm.domain.Header();

        bean.setRequiredFeatures(header.getRequiredFeatures());
        bean.setOptionalFeatures(header.getOptionalFeatures());
        bean.setwritingProgram(header.getWritingProgram());
        bean.setSource(header.getSource());

        try {
            String jsonInString = mapper.writeValueAsString(bean);
            safePrintln(outputs.get("header"), jsonInString);
        } catch (IOException e) {
            System.err.println("********** header");
            e.printStackTrace();
        }
    }
    /**
     * TODO.
     * @param bbox BoundBoxs
     */
    private void processBoundingBox(@SuppressWarnings("unused") final BoundBox bbox) {
        com.google.allenday.osm.domain.BoundingBox bean = new com.google.allenday.osm.domain.BoundingBox();
        bean.setTop(bbox.getTop());
        bean.setLeft(bbox.getLeft());
        bean.setBottom(bbox.getBotttom());
        bean.setRight(bbox.getRight());

        try {
            String jsonInString = mapper.writeValueAsString(bean);
            safePrintln(outputs.get("boundingbox"), jsonInString);
        } catch (IOException e) {
            System.err.println("********** boundingbox");
            e.printStackTrace();
        }
    }
    /**
     * TODO.
     * @param id Long
     */
    private void processChangesets(@SuppressWarnings("unused") final Long id) {
        //TODO seems that these don't exist as standalone objects, but are
        //TODO rather just Longs present in nodes/ways/relations
    }
    /**
     * TODO.
     * @param node Node
     */
    private void processNodes(@SuppressWarnings("unused") final Node node) {
        com.google.allenday.osm.domain.Node bean = new com.google.allenday.osm.domain.Node();

        bean.setId(node.getId());
        bean.setVersion(node.getInfo().getVersion());
        bean.setUsername(node.getInfo().getUsername());
        bean.setChangeset(node.getInfo().getChangeset());
        bean.setVisible(node.getInfo().isVisible());
        bean.setTimestamp(node.getInfo().getTimestamp());
        bean.setTags(node.getTags());
        bean.setLatitude(node.getLat());
        bean.setLongitude(node.getLon());

        try {
            String jsonInString = mapper.writeValueAsString(bean);
            safePrintln(outputs.get("node"), jsonInString);
        } catch (IOException e) {
            System.err.println("********** node");
            e.printStackTrace();
        }

    }
    /**
     * TODO.
     * @param way Way
     */
    private void processWays(@SuppressWarnings("unused") final Way way) {
        com.google.allenday.osm.domain.Way bean = new com.google.allenday.osm.domain.Way();

        bean.setId(way.getId());
        bean.setVersion(way.getInfo().getVersion());
        bean.setUsername(way.getInfo().getUsername());
        bean.setChangeset(way.getInfo().getChangeset());
        bean.setVisible(way.getInfo().isVisible());
        bean.setTimestamp(way.getInfo().getTimestamp());
        bean.setTags(way.getTags());
        bean.setNodes(way.getNodes());

        try {
            String jsonInString = mapper.writeValueAsString(bean);
            safePrintln(outputs.get("way"), jsonInString);
        } catch (IOException e) {
            System.err.println("********** way");
            e.printStackTrace();
        }
    }

    /**
     * TODO.
     * @param rel Relation
     */
    private void processRelations(@SuppressWarnings("unused") final Relation rel) {
        com.google.allenday.osm.domain.Relation bean = new com.google.allenday.osm.domain.Relation();

        //TODO refactor this to use single list with a type field, apparently
        //TODO it's possible to collate heterogeneous types in a composite and order matters
        List<com.google.allenday.osm.domain.RelationMember> beans = new ArrayList<>();
        List<com.google.allenday.osm.domain.RelationMember> relations = new ArrayList<>();
        List<com.google.allenday.osm.domain.RelationMember> ways = new ArrayList<>();
        List<com.google.allenday.osm.domain.RelationMember> nodes = new ArrayList<>();

        List<RelationMember> members = rel.getMembers();
        for (RelationMember member  : members) {
            com.google.allenday.osm.domain.RelationMember rm = new com.google.allenday.osm.domain.RelationMember();

            rm.setId(member.getId());
            rm.setRole(member.getRole());
            rm.setType(member.getType().name());

            beans.add(rm);

            if (member.getType()  == RelationMember.Type.NODE) {
                nodes.add(rm);
            } else if (member.getType() == RelationMember.Type.WAY) {
                ways.add(rm);
            } else if (member.getType() == RelationMember.Type.RELATION) {
                relations.add(rm);
            }
        }

        bean.setId(rel.getId());
        bean.setVersion(rel.getInfo().getVersion());
        bean.setUsername(rel.getInfo().getUsername());
        bean.setChangeset(rel.getInfo().getChangeset());
        bean.setVisible(rel.getInfo().isVisible());
        bean.setTimestamp(rel.getInfo().getTimestamp());
        bean.setTags(rel.getTags());
        bean.setMembers(beans);
        bean.setRelations(relations);
        bean.setWays(ways);
        bean.setNodes(nodes);

        try {
            String jsonInString = mapper.writeValueAsString(bean);
            safePrintln(outputs.get("relation"), jsonInString);
        } catch (IOException e) {
            System.err.println("********** relation");
            e.printStackTrace();
        }
    }

    /**
     * TODO.
     */
    private void printOnCompletions() {
//        output.append("Node count: ");
//        output.append(nodesCounter.get());
//        output.append("\n");
//
//        output.append("Way count: ");
//        output.append(waysCounter.get());
//        output.append("\n");
//
//        output.append("Relations count: ");
//        output.append(relationsCounter.get());
//        output.append("\n");
//
//        output.append("Changesets count: ");
//        output.append(changesetsCounter.get());
//        output.append("\n");
//
//        System.err.println("Reading results:");
//        System.err.println(output);
    }

    /**
     * TODO.
     * @param filters Set<String>
     * @param threads int
     * @param inputStream InputStream
     * @throws IOException failed to write
     */
    private void execute(final Set<String> filters, final int threads, final InputStream inputStream)
            throws IOException {
        ParallelBinaryParser parser = new ParallelBinaryParser(inputStream, threads, threads, 0)
            .onComplete(this::printOnCompletions);

        Boolean all = filters.contains("all") ? true : false;

        if (filters.contains("header") || all) {
            FileOutputStream fos = new FileOutputStream("header.json.gz");
            GZIPOutputStream zos = new GZIPOutputStream(fos);
            OutputStreamWriter osw = new OutputStreamWriter(zos, StandardCharsets.UTF_8);
            BufferedWriter bw = new BufferedWriter(osw);
            outputs.put("header", bw);
            parser = parser.onHeader(this::processHeader);
        }
        if (filters.contains("boundingbox") || all) {
            FileOutputStream fos = new FileOutputStream("boundingbox.json.gz");
            GZIPOutputStream zos = new GZIPOutputStream(fos);
            OutputStreamWriter osw = new OutputStreamWriter(zos, StandardCharsets.UTF_8);
            BufferedWriter bw = new BufferedWriter(osw);
            outputs.put("boundingbox", bw);
            parser = parser.onBoundBox(this::processBoundingBox);
        }
        if (filters.contains("node") || all) {
            FileOutputStream fos = new FileOutputStream("node.json.gz");
            GZIPOutputStream zos = new GZIPOutputStream(fos);
            OutputStreamWriter osw = new OutputStreamWriter(zos, StandardCharsets.UTF_8);
            BufferedWriter bw = new BufferedWriter(osw);
            outputs.put("node", bw);
            parser = parser.onNode(this::processNodes);
        }
        if (filters.contains("way") || all) {
            FileOutputStream fos = new FileOutputStream("way.json.gz");
            GZIPOutputStream zos = new GZIPOutputStream(fos);
            OutputStreamWriter osw = new OutputStreamWriter(zos, StandardCharsets.UTF_8);
            BufferedWriter bw = new BufferedWriter(osw);
            outputs.put("way", bw);
            parser = parser.onWay(this::processWays);
        }
        if (filters.contains("relation") || all) {
            FileOutputStream fos = new FileOutputStream("relation.json.gz");
            GZIPOutputStream zos = new GZIPOutputStream(fos);
            OutputStreamWriter osw = new OutputStreamWriter(zos, StandardCharsets.UTF_8);
            BufferedWriter bw = new BufferedWriter(osw);
            outputs.put("relation", bw);
            parser = parser.onRelation(this::processRelations);
        }
        //TODO never happens
        //if (filters.contains("changeset") || all) {
        //}

        parser.parse();

        for (BufferedWriter bw : outputs.values()) {
            bw.close();
        }
    }

    /**
     * TODO.
     * @param args String[]
     * @throws IOException needs a valid file path
     */
    public static void main(final String[] args) throws IOException {
        Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        root.setLevel(Level.TRACE);


        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("sample.pbf");
        Integer threads = 1;
        Set<String> filters  = new HashSet<String>();

        for (int i =  0;  i < args.length; i++) {
            System.err.println("*** arg " + i + " = " +  args[i]);
        }
        if (args.length > 0) {
            inputStream = new FileInputStream(args[0]);
        }
        if  (args.length > 1) {
            for (String f : args[1].split(",")) {
                filters.add(f);
            }
        } else {
            filters.add("all");
        }
        if (args.length > 2) {
            threads = Integer.valueOf(args[2]);
        }
        new OsmBigQueryLoader().execute(filters, threads, inputStream);
    }
}
