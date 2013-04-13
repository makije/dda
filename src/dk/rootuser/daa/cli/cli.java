package dk.rootuser.daa.cli;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import dk.rootuser.daa.parsers.DataCiteParser;
import dk.rootuser.daa.pojos.datacite.Resource;

public class cli {

	/**
	 * @param args contains the arguments 
	 */
	public static void main(String[] args) {

		if(args.length == 0) {
			System.out.println("Please provide some files to me");
			System.exit(-1);
		}
		
		ArrayList<Resource> resources = new ArrayList<Resource>();
		
		DataCiteParser parser = null;
		
		try {
			parser = DataCiteParser.getInstance();
			
			for(String s : args)
				resources.add(parser.parse(s));
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			System.exit(-2);
		} catch (SAXException e) {
			e.printStackTrace();
			System.exit(-3);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-4);
		}
		
		for(Resource r : resources) {
			System.out.println(r.getTitles().get(0).getTitle());
			System.out.println("\t" + r.getDescriptions().get(0).getDescription());
			System.out.println();
		}
		
	}

}
