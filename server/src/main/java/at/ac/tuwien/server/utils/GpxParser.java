package at.ac.tuwien.server.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import at.ac.tuwien.server.domain.Location;


public class GpxParser extends DefaultHandler {
    private static final DateFormat TIME_FORMAT
            = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

    private Set<Location> track = new TreeSet<Location>();
    private StringBuffer buf = new StringBuffer();
    private double lat;
    private double lon;
    private double ele;
    private Date time;

    public static Set<Location> readTrack(InputStream in) throws IOException {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            factory.setValidating(true);
            SAXParser parser = factory.newSAXParser();
            GpxParser reader = new GpxParser();
            parser.parse(in, reader);
            return reader.getTrack();
        } catch (ParserConfigurationException e) {
            throw new IOException(e.getMessage());
        } catch (SAXException e) {
            throw new IOException(e.getMessage());
        }
    }

    public static Set<Location> readTrack(File file) throws IOException {
        InputStream in = new FileInputStream(file);
        try {
            return readTrack(in);
        } finally {
            in.close();
        }
    }

    @Override
    public void startElement(String uri, String localName, String qName,
            Attributes attributes) throws SAXException {
        buf.setLength(0);
        if (qName.equals("trkpt")) {
            lat = Double.parseDouble(attributes.getValue("lat"));
            lon = Double.parseDouble(attributes.getValue("lon"));
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        if (qName.equals("trkpt")) {
        	Location loc = new Location();
        	loc.setAltitude(ele);
        	loc.setLatitude(lat);
        	loc.setLongitude(lon);
        	loc.setTimestamp(time);
            track.add(loc);
        } else if (qName.equals("ele")) {
            ele = Double.parseDouble(buf.toString());
        } else if (qName.equals("time")) {
            try {
                time = TIME_FORMAT.parse(buf.toString());
            } catch (ParseException e) {
                throw new SAXException("Invalid time " + buf.toString());
            }
        }
    }

    @Override
    public void characters(char[] chars, int start, int length)
            throws SAXException {
        buf.append(chars, start, length);
    }

    private Set<Location> getTrack() {
        return track;
    }
}