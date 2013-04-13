package dk.rootuser.daa.handlers;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import dk.rootuser.daa.pojos.datacite.Resource;

public class DataCiteHandler extends DefaultHandler {

	private Resource resource = null;
	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		System.out.println("uri: " + uri + "\tlocalName: " + localName + "\tqName: " + qName);
		
		for(int i = 0; i < attributes.getLength(); i++) {
			System.out.println("\tAttrib: " + i + "\turi: " + attributes.getURI(i) + "\tlocalName: " + attributes.getLocalName(i) + "\tqName: " + attributes.getQName(i));
		}
	}
	
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {

	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {

	}
	
	public Resource getParsedResource() {
		return this.resource;
	}
}
