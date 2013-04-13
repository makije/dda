package dk.rootuser.daa.handlers;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import dk.rootuser.daa.handlerhelpers.DataCiteHandlerHelper;
import dk.rootuser.daa.pojos.datacite.Creator;
import dk.rootuser.daa.pojos.datacite.Identifier;
import dk.rootuser.daa.pojos.datacite.Resource;
import dk.rootuser.daa.pojos.datacite.Title;

public class DataCiteHandler extends DefaultHandler {

	private Resource resource = null;
	
	private Attributes attributes;
	
	private boolean isInResource = false;
	
	private boolean isInIdentifier = false;
	
	private boolean isInCreators = false;
	private boolean isInCreator = false;
	private boolean isInCreatorName = false;
	
	private boolean isIntitles;
	private boolean isIntitle;
	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
//		System.out.println("uri: " + uri + "\tlocalName: " + localName + "\tqName: " + qName);
//		
//		for(int i = 0; i < attributes.getLength(); i++) {
//			System.out.println("\tAttrib: " + i + "\turi: " + attributes.getURI(i) + "\tlocalName: " + attributes.getLocalName(i) + "\tqName: " + attributes.getQName(i));
//		}
		
		this.attributes = attributes; 
		
		String tag = (localName == null || localName.length() == 0 ? qName : localName);
		
		if(tag.equals(DataCiteHandlerHelper.RESOURCE_TAG)) {
			isInResource = true;
			resource = new Resource();
		} else if(tag.equals(DataCiteHandlerHelper.IDENTIFIER_TAG)) {
			isInIdentifier = true;
		} else if(tag.equals(DataCiteHandlerHelper.CREATORS_TAG)) {
			isInCreators = true;
			resource.setCreators(new ArrayList<Creator>());
		} else if(tag.equals(DataCiteHandlerHelper.CREATOR_TAG)) {
			isInCreator = true;
		} else if(tag.equals(DataCiteHandlerHelper.CREATOR_NAME_TAG)) {
			isInCreatorName = true;
		} else if(tag.equals(DataCiteHandlerHelper.TITLES_TAG)) {
			isIntitles = true;
			resource.setTitles(new ArrayList<Title>());
		} else if(tag.equals(DataCiteHandlerHelper.TITLE_TAG)) {
			isIntitle = true;
		}
		
	}
	
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		if(isInResource) {
			
			if(isInIdentifier) {
				Identifier i = new Identifier();
				i.setIdentifier(new String(ch, start, length));
				i.setIdentifierType(attributes.getValue(attributes.getIndex(DataCiteHandlerHelper.IDENTIFIER_TYPE)));
				
				resource.setIdentifier(i);
			} else if(isInCreators) {
				if(isInCreator) {
					if(isInCreatorName) {
						Creator c = new Creator();
						c.setName(new String(ch, start, length));
						resource.getCreators().add(c);
					}
				}
			} else if(isIntitles) {
				if(isIntitle) {
					int titleType;
					Title t = new Title();
					t.setTitle(new String(ch, start, length));
					if((titleType = attributes.getIndex(DataCiteHandlerHelper.TITLE_TYPE)) >= 0)
						t.setTitleType(attributes.getValue(titleType));
					resource.getTitles().add(t);
				}
			}
			
		}
		
		
//		for(int i = 0; i < attributes.getLength(); i++) {
//			System.out.println("\tAttrib: " + i + "\turi: " + attributes.getURI(i) + "\tlocalName: " + attributes.getLocalName(i) + "\tqName: " + attributes.getQName(i));
//		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		
		String tag = (localName == null || localName.length() == 0 ? qName : localName);
		
		if(tag.equals(DataCiteHandlerHelper.RESOURCE_TAG)) {
			isInResource = false;
		} else if(tag.equals(DataCiteHandlerHelper.IDENTIFIER_TAG)) {
			isInIdentifier = false;
		} else if(tag.equals(DataCiteHandlerHelper.CREATORS_TAG)) {
			isInCreators = false;
		} else if(tag.equals(DataCiteHandlerHelper.CREATOR_TAG)) {
			isInCreator = false;
		} else if(tag.equals(DataCiteHandlerHelper.CREATOR_NAME_TAG)) {
			isInCreatorName = false;
		} else if(tag.equals(DataCiteHandlerHelper.TITLES_TAG)) {
			isIntitles = false;
		} else if(tag.equals(DataCiteHandlerHelper.TITLE_TAG)) {
			isIntitle = false;
		}
	}
	
	public Resource getParsedResource() {
		return this.resource;
	}
}
