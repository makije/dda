package dk.rootuser.daa.ui.cli;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import dk.rootuser.daa.parsers.DataCiteParser;
import dk.rootuser.daa.pojos.datacite.Resource;
import dk.rootuser.daa.sorters.SortOrder;
import dk.rootuser.daa.sorters.TitleSorter;

/**
 * CLI<br/>
 * This class is one way to interact with the data files<br/>
 * If you are in to this sort of thing
 * @author martin
 *
 */

public class cli {

	private static ArrayList<String> filesToParse = new ArrayList<String>();
	private static SortOrder titleSortOrder = SortOrder.NONE;
	
	private static long parsingStarted = 0;
	private static long parsingTook = 0;
	private static long sortingStarted = 0;
	private static long sortingTook = 0;
	
	public static void main(String[] args) {

		if(args.length == 0) {
			System.out.println("-h for help");
			System.exit(-1);
		}
		
		if(args.length == 1 && args[0].equalsIgnoreCase("-h")) {
			System.out.println("This program can parse any number of datacite metadata files, as long as your memory permits it\n");
			System.out.println("-h\t\t\tShows this help");
			System.out.println("title.<SortType>\tSortType can be of asc or desc");
			System.out.println("\nUsage example: java -jar dda.jar xml1.xml xml2.xml title.asc");
			System.exit(0);
		}
		
		ArrayList<Resource> resources = new ArrayList<Resource>();
		
		DataCiteParser parser = null;
		
		try {
			parser = DataCiteParser.getInstance();
			
			for(String s : args) {
				if(s.contains(".xml")) {
					filesToParse.add(s);
				} else {
					if(s.equalsIgnoreCase("title.asc"))
						titleSortOrder = SortOrder.ASC;
					else if(s.equalsIgnoreCase("title.desc"))
						titleSortOrder = SortOrder.DESC;
				}
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			System.exit(-2);
		} catch (SAXException e) {
			e.printStackTrace();
			System.exit(-3);
		}
		
		if(filesToParse.size() == 0) {
			System.out.println("Please provide me with some files");
			System.exit(0);
		}
		
		System.out.println("you gave me " + filesToParse.size() + " files to crunch");
		if(titleSortOrder != SortOrder.NONE)
			System.out.println("Sorting the titles " + (titleSortOrder == SortOrder.ASC ? "ascending" : "descending"));
		else
			System.out.println("No sorting is being applied");
		System.out.println("Press enter to start");
		
		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		parsingStarted = System.currentTimeMillis();
		
		for(String s : filesToParse)
			try {
				resources.add(parser.parse(s));
			} catch (SAXException e) {
				e.printStackTrace();
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		
		parsingTook = System.currentTimeMillis() - parsingStarted;
		
		if(titleSortOrder != SortOrder.NONE) {
			sortingStarted = System.currentTimeMillis();
			TitleSorter titleSorter = new TitleSorter(titleSortOrder);
			Collections.sort(resources, titleSorter);
			sortingTook = System.currentTimeMillis() - sortingStarted;
		}
		
		for(Resource r : resources) {
			System.out.println(r.getTitles().get(0).getTitle());
			System.out.println("\t" + r.getDescriptions().get(0).getDescription());
			System.out.println();
		}
		
		System.out.println("\nIt took me " + parsingTook + "ms to parse" + (titleSortOrder != SortOrder.NONE ? " and sorting was " + sortingTook + "ms" : ""));
	}

}
