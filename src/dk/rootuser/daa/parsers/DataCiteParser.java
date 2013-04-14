package dk.rootuser.daa.parsers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import dk.rootuser.daa.handlers.DataCiteHandler;
import dk.rootuser.daa.pojos.datacite.Resource;

/**
 * Wrapper class, so that we later don't have to think about SAXParsers etc.<br/>
 * Just feed it a filepath and it gives you the parsed resource
 * @author martin
 *
 */

public class DataCiteParser {

	private static DataCiteParser instance = null;
	
	private SAXParser parser;
	private DataCiteHandler handler;
	
	/**
	 * Private constructor to create our instance
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 */
	public DataCiteParser() throws ParserConfigurationException, SAXException {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		this.parser = factory.newSAXParser();
		this.handler = new DataCiteHandler();
	}
	
	/**
	 * Get an instance of this parser
	 * @return An instance
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 */
	public static DataCiteParser getInstance() throws ParserConfigurationException, SAXException {
		if(instance == null)
			instance = new DataCiteParser();
		
		return instance;
	}
	
	/**
	 * The method that actually takes care of the xml parsing
	 * @param filePath A path the to file to parse
	 * @return An instance of the parsed resource
	 * @throws SAXException
	 * @throws IOException
	 */
	public Resource parse(String filePath) throws SAXException, IOException {
		File xml = new File(filePath);
		
		if(!xml.exists())
			throw new FileNotFoundException("Couldn't find " + xml.getAbsoluteFile());
		
		parser.parse(xml, handler);
		
		return this.handler.getParsedResource();
	}
	
}
