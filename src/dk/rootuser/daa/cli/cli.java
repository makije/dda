package dk.rootuser.daa.cli;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import dk.rootuser.daa.parsers.DataCiteParser;
import dk.rootuser.daa.pojos.datacite.Creator;
import dk.rootuser.daa.pojos.datacite.Resource;
import dk.rootuser.daa.pojos.datacite.Title;

public class cli {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		ArrayList<Resource> resources = new ArrayList<Resource>();
		
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
			
			for(String s : args)
				resources.add(parser.parse(s));
			
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		int resourceNumber = 0;
		
		for(Resource r : resources) {
			System.out.println("Resource Number: " + resourceNumber);
			System.out.println("Identifier: " + r.getIdentifier().getIdentifier() + " Type: " + r.getIdentifier().getIdentifierType());
			System.out.println("Creators");
			for(Creator c : r.getCreators())
				System.out.println("\t" + c.getName());
			
			System.out.println("Titles");
			for(Title t : r.getTitles())
				System.out.println("\t\"" + t.getTitle() + "\"" + ( t.getTitleType() != null ? " (" + t.getTitleType() + ")" : "" ));
			
			System.out.println();
			resourceNumber++;
		}
		
	}

}
