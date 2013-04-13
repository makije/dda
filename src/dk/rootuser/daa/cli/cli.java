package dk.rootuser.daa.cli;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import dk.rootuser.daa.parsers.DataCiteParser;

public class cli {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		DataCiteParser parser = null;
		
		try {
			parser = DataCiteParser.getInstance();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			System.exit(-1);
		} catch (SAXException e) {
			e.printStackTrace();
			System.exit(-2);
		}
		
		try {
			parser.parse(args[0]);
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
