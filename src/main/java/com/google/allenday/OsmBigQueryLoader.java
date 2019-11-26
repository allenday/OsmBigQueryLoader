package com.google.allenday;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import org.akashihi.osm.parallelpbf.ParallelBinaryParser;
import org.akashihi.osm.parallelpbf.entity.*;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Load OSM records to BigQuery.
 *
 */
@SuppressWarnings("PMD.UnusedPrivateMethod")
public class OsmBigQueryLoader {

    /**
     * TODO.
     */
    private ObjectMapper mapper = new ObjectMapper();

    /**
     * TODO.
     */
    private final StringBuilder output = new StringBuilder();
    /**
     * TODO.
     */
    private AtomicLong nodesCounter = new AtomicLong();
    /**
     * TODO.
     */
    private AtomicLong waysCounter = new AtomicLong();
    /**
     * TODO.
     */
    private AtomicLong relationsCounter = new AtomicLong();
    /**
     * TODO.
     */
    private AtomicLong changesetsCounter = new AtomicLong();
    /**
     * TODO.
     * @param header Header
     */
    void processHeader(@SuppressWarnings("unused") final Header header) {
        synchronized (output) {
            output.append(header);
            output.append("\n");
        }
    }
    /**
     * TODO.
     * @param bbox BoundBoxs
     */
    private void processBoundingBox(@SuppressWarnings("unused") final BoundBox bbox) {
        synchronized (output) {
            output.append(bbox);
            output.append("\n");
        }
    }
    /**
     * TODO.
     * @param id Long
     */
    private void processChangesets(@SuppressWarnings("unused") final Long id) {
        changesetsCounter.incrementAndGet();
    }


    /**
     * TODO.
     * @param node Node
     */
    private void processNodes(@SuppressWarnings("unused") final Node node) {
        nodesCounter.incrementAndGet();


    }

    /**
     * TODO.
     * @param way Way
     */
    private void processWays(@SuppressWarnings("unused") final Way way) {
        waysCounter.incrementAndGet();
        com.google.allenday.osm.domain.Way bean = new com.google.allenday.osm.domain.Way();

        bean.setId(way.getId());
        bean.setVersion(way.getInfo().getVersion());
        bean.setUsername(way.getInfo().getUsername());
        bean.setChangeset(way.getInfo().getChangeset());
        bean.setVisible(way.getInfo().isVisible());
        bean.setTimestamp(way.getInfo().getTimestamp());
        bean.setNodes(way.getNodes());
        bean.setTags(way.getTags());

        try {
            //jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(bean);
            String jsonInString = mapper.writeValueAsString(bean);
            System.err.println(jsonInString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * TODO.
     * @param relation Relation
     */
    private void processRelations(@SuppressWarnings("unused") final Relation relation) {
        relationsCounter.incrementAndGet();
    }

    /**
     * TODO.
     */
    private void printOnCompletions() {
        output.append("Node count: ");
        output.append(nodesCounter.get());
        output.append("\n");

        output.append("Way count: ");
        output.append(waysCounter.get());
        output.append("\n");

        output.append("Relations count: ");
        output.append(relationsCounter.get());
        output.append("\n");

        output.append("Changesets count: ");
        output.append(changesetsCounter.get());
        output.append("\n");

        System.out.println("Reading results:");
        System.out.println(output);
    }

    /**
     * TODO.
     * @param filters Set<String>
     * @param threads int
     * @param inputStream InputStream
     */
    private void execute(final Set<String> filters, final int threads, final InputStream inputStream) {
        ParallelBinaryParser parser = new ParallelBinaryParser(inputStream, threads)
            .onComplete(this::printOnCompletions);

        Boolean all = filters.contains("all") ? true : false;

        if (filters.contains("header") || all) {
            parser = parser.onHeader(this::processHeader);
        }
        if (filters.contains("boundbox") || all) {
            parser = parser.onBoundBox(this::processBoundingBox);
        }
        if (filters.contains("node") || all) {
            parser = parser.onNode(this::processNodes);
        }
        if (filters.contains("way") || all) {
            parser = parser.onWay(this::processWays);
        }
        if (filters.contains("relation") || all) {
            parser = parser.onRelation(this::processRelations);
        }
        if (filters.contains("changeset") || all) {
            parser = parser.onChangeset(this::processChangesets);
        }

        parser.parse();
    }

    /**
     * TODO.
     * @param args String[]
     * @throws FileNotFoundException needs a valid file path
     */
    public static void main(final String[] args) throws FileNotFoundException {
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
