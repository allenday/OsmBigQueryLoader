package com.google.allenday;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import org.akashihi.osm.parallelpbf.ParallelBinaryParser;
import org.akashihi.osm.parallelpbf.entity.*;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
//import java.util.concurrent.atomic.AtomicLong;

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
//        System.err.println(s);
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
     */
    //private AtomicLong nodesCounter = new AtomicLong();
    /**
     * TODO.
     */
    //private AtomicLong waysCounter = new AtomicLong();
    /**
     * TODO.
     */
    //private AtomicLong relationsCounter = new AtomicLong();
    /**
     * TODO.
     */
    //private AtomicLong changesetsCounter = new AtomicLong();
    /**
     * TODO.
     * @param header Header
     */
    void processHeader(@SuppressWarnings("unused") final Header header) {
//        synchronized (output) {
//            output.append(header);
//            output.append("\n");
//        }
    }
    /**
     * TODO.
     * @param bbox BoundBoxs
     */
    private void processBoundingBox(@SuppressWarnings("unused") final BoundBox bbox) {
//        synchronized (output) {
//            output.append(bbox);
//            output.append("\n");
//        }
    }
    /**
     * TODO.
     * @param id Long
     */
    private void processChangesets(@SuppressWarnings("unused") final Long id) {
//        changesetsCounter.incrementAndGet();
    }


    /**
     * TODO.
     * @param node Node
     */
    private void processNodes(@SuppressWarnings("unused") final Node node) {
//        nodesCounter.incrementAndGet();
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
            e.printStackTrace();
        }

    }

    /**
     * TODO.
     * @param way Way
     */
    private void processWays(@SuppressWarnings("unused") final Way way) {
//        waysCounter.incrementAndGet();
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
            //jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(bean);
            String jsonInString = mapper.writeValueAsString(bean);
            safePrintln(outputs.get("way"), jsonInString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * TODO.
     * @param rel Relation
     */
    private void processRelations(@SuppressWarnings("unused") final Relation rel) {
//        relationsCounter.incrementAndGet();
        com.google.allenday.osm.domain.Relation bean = new com.google.allenday.osm.domain.Relation();

        List<Map<Long, String>> relations = new ArrayList<Map<Long, String>>();
        List<Map<Long, String>> ways = new ArrayList<Map<Long, String>>();
        List<Map<Long, String>> nodes = new ArrayList<Map<Long, String>>();

        List<RelationMember> members = rel.getMembers();
        for (RelationMember member  : members) {
            Map<Long, String> kv = new HashMap<Long, String>();
            kv.put(member.getId(), member.getRole());
            if (member.getType()  == RelationMember.Type.NODE) {
                nodes.add(kv);
            } else if (member.getType() == RelationMember.Type.WAY) {
                ways.add(kv);
            } else if (member.getType() == RelationMember.Type.RELATION) {
                relations.add(kv);
            }
        }

        bean.setId(rel.getId());
        bean.setVersion(rel.getInfo().getVersion());
        bean.setUsername(rel.getInfo().getUsername());
        bean.setChangeset(rel.getInfo().getChangeset());
        bean.setVisible(rel.getInfo().isVisible());
        bean.setTimestamp(rel.getInfo().getTimestamp());
        bean.setTags(rel.getTags());
        bean.setRelations(relations);
        bean.setWays(ways);
        bean.setNodes(nodes);

        try {
            //jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(bean);
            String jsonInString = mapper.writeValueAsString(bean);
            safePrintln(outputs.get("relation"), jsonInString);
        } catch (IOException e) {
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
        ParallelBinaryParser parser = new ParallelBinaryParser(inputStream, threads)
            .onComplete(this::printOnCompletions);

        Boolean all = filters.contains("all") ? true : false;

        if (filters.contains("header") || all) {
            BufferedWriter bw = Files.newBufferedWriter(Paths.get("header.json"));
            outputs.put("header", bw);
            parser = parser.onHeader(this::processHeader);
        }
        if (filters.contains("boundbox") || all) {
            BufferedWriter bw = Files.newBufferedWriter(Paths.get("boundbox.json"));
            outputs.put("boundbox", bw);
            parser = parser.onBoundBox(this::processBoundingBox);
        }
        if (filters.contains("node") || all) {
            BufferedWriter bw = Files.newBufferedWriter(Paths.get("node.json"));
            outputs.put("node", bw);
            parser = parser.onNode(this::processNodes);
        }
        if (filters.contains("way") || all) {
            BufferedWriter bw = Files.newBufferedWriter(Paths.get("way.json"));
            outputs.put("way", bw);
            parser = parser.onWay(this::processWays);
        }
        if (filters.contains("relation") || all) {
            BufferedWriter bw = Files.newBufferedWriter(Paths.get("relation.json"));
            outputs.put("relation", bw);
            parser = parser.onRelation(this::processRelations);
        }
        if (filters.contains("changeset") || all) {
            BufferedWriter bw = Files.newBufferedWriter(Paths.get("changeset.json"));
            outputs.put("changeset", bw);
            parser = parser.onChangeset(this::processChangesets);
        }

        parser.parse();
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
