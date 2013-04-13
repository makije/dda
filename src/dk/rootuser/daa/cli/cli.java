package dk.rootuser.daa.cli;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import dk.rootuser.daa.parsers.DataCiteParser;
import dk.rootuser.daa.pojos.datacite.AlternateIdentifier;
import dk.rootuser.daa.pojos.datacite.Creator;
import dk.rootuser.daa.pojos.datacite.Date;
import dk.rootuser.daa.pojos.datacite.Description;
import dk.rootuser.daa.pojos.datacite.Format;
import dk.rootuser.daa.pojos.datacite.Resource;
import dk.rootuser.daa.pojos.datacite.Size;
import dk.rootuser.daa.pojos.datacite.Subject;
import dk.rootuser.daa.pojos.datacite.Title;

public class cli {

	/**
	 * @param args
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
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			System.exit(-2);
		} catch (SAXException e) {
			e.printStackTrace();
			System.exit(-3);
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
			
			if(r.getCreators() != null) {
				System.out.println("Creators");
				for(Creator c : r.getCreators())
					System.out.println("\t" + c.getName());
			}
			
			if(r.getTitles() != null) {
				System.out.println("Titles");
				for(Title t : r.getTitles())
					System.out.println("\t\"" + t.getTitle() + "\"" + ( t.getTitleType() != null ? " (Type: " + t.getTitleType() + ")" : "" ));
			}
			
			System.out.println("Publisher: " + r.getPublisher());
			System.out.println("Publication year: " + r.getPublicationYear());
			
			if(r.getSubjects() != null) {
				System.out.println("Subjects");
				for(Subject s : r.getSubjects())
					System.out.println("\t\"" + s.getSubject() + "\"" + ( s.getSubjectScheme() != null ? " (Scheme: " + s.getSubjectScheme() + ")" : "" ));
			}
			
			if(r.getDates() != null) {
				System.out.println("Dates");
				for(Date d : r.getDates())
					System.out.println("\t" + d.getDate() + ( d.getDateType() != null ? " (DateType: " + d.getDateType() + ")" : "" ));
			}
			
			System.out.println("Language: " + r.getLanguage());
			
			if(r.getResourceType() != null) {
				System.out.println("ResourceType: " + r.getResourceType().getResourceType() + (r.getResourceType().getResourceTypeGeneral() != null ? " (ResourceTypeGeneral: " + r.getResourceType().getResourceTypeGeneral() + ")" : "" ));
			}
			
			if(r.getAlternateIdentifiers() != null) {
				System.out.println("AlternateIdentifiers");
				for(AlternateIdentifier a : r.getAlternateIdentifiers())
					System.out.println("\t" + a.getAlternateIdentifier() + (a.getAlternateIdentifierType() != null ? " (Type: " + a.getAlternateIdentifierType() + ")" : "" ));
			}
			
			if(r.getSizes() != null) {
				System.out.println("Sizes");
				for(Size s : r.getSizes())
					System.out.println("\t" + s.getSize());
			}
			
			if(r.getFormats() != null) {
				System.out.println("Formats");
				for(Format f : r.getFormats())
					System.out.println("\t" + f.getFormat());
			}
			
			System.out.println("Version: " + r.getVersion());
			
			System.out.println("Rights: " + r.getRights());
			
			if(r.getDescriptions() != null) {
				System.out.println("Description");
				for(Description d : r.getDescriptions())
					System.out.println("\t" + d.getDescription() + (d.getDescriptionType() != null ? " (Type: " + d.getDescriptionType() + ")" : "" ));
			}
			
			System.out.println();
			resourceNumber++;
		}
		
	}

}
