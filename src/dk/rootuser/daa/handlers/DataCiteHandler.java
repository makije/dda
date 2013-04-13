package dk.rootuser.daa.handlers;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import dk.rootuser.daa.handlerhelpers.DataCiteHandlerHelper;
import dk.rootuser.daa.pojos.datacite.Creator;
import dk.rootuser.daa.pojos.datacite.Date;
import dk.rootuser.daa.pojos.datacite.Identifier;
import dk.rootuser.daa.pojos.datacite.Resource;
import dk.rootuser.daa.pojos.datacite.ResourceType;
import dk.rootuser.daa.pojos.datacite.Subject;
import dk.rootuser.daa.pojos.datacite.Title;

public class DataCiteHandler extends DefaultHandler {

	private Resource resource = null;
	
	private Attributes attributes;
	
	private boolean isInResource = false;
	
	private boolean isInIdentifier = false;
	
	private boolean isInCreators = false;
	private boolean isInCreator = false;
	private boolean isInCreatorName = false;
	
	private boolean isIntitles = false;
	private boolean isIntitle = false;
	
	private boolean isInPublisher = false;
	
	private boolean isInPublicationYear = false;
	
	private boolean isInSubjects = false;
	private boolean isInSubject = false;
	
	private boolean isInDates = false;
	private boolean isInDate = false;
	
	private boolean isInLanguage = false;
	
	private boolean isInResourceType = false;
	
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
		} else if(tag.equals(DataCiteHandlerHelper.PUBLISHER_TAG)) {
			isInPublisher = true;
		} else if(tag.equals(DataCiteHandlerHelper.PUBLICATION_YEAR_TAG)) {
			isInPublicationYear = true;
		} else if(tag.equals(DataCiteHandlerHelper.SUBJECTS_TAG)) {
			isInSubjects = true;
			resource.setSubjects(new ArrayList<Subject>());
		} else if(tag.equals(DataCiteHandlerHelper.SUBJECT_TAG)) {
			isInSubject = true;
		} else if(tag.equals(DataCiteHandlerHelper.DATES_TAG)) {
			isInDates = true;
			resource.setDates(new ArrayList<Date>());
		} else if(tag.equals(DataCiteHandlerHelper.DATE_TAG)) {
			isInDate = true;
		} else if(tag.equals(DataCiteHandlerHelper.LANGUAGE_TAG)) {
			isInLanguage = true;
		} else if(tag.equals(DataCiteHandlerHelper.RESOURCE_TYPE_TAG)) {
			isInResourceType = true;
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
			} else if(isInPublisher) {
				resource.setPublisher(new String(ch, start, length));
			} else if(isInPublicationYear) {
				resource.setPublicationYear(new String(ch, start, length));
			} else if(isInSubjects) {
				if(isInSubject) {
					int subjectScheme;
					Subject s = new Subject();
					s.setSubject(new String(ch, start, length));
					if((subjectScheme = attributes.getIndex(DataCiteHandlerHelper.SUBJECT_SCHEME)) >= 0)
						s.setSubjectScheme(attributes.getValue(subjectScheme));
					resource.getSubjects().add(s);
				}
			} else if(isInDates) {
				if(isInDate) {
					int dateType;
					Date d = new Date();
					d.setDate(new String(ch, start, length));
					if((dateType = attributes.getIndex(DataCiteHandlerHelper.DATE_TYPE)) >= 0)
						d.setDateType(attributes.getValue(dateType));
					resource.getDates().add(d);
				}
			} else if(isInLanguage) {
				resource.setLanguage(new String(ch, start, length));
			} else if(isInResourceType) {
				int resourceType;
				ResourceType rt = new ResourceType();
				rt.setResourceType(new String(ch, start, length));
				if((resourceType = attributes.getIndex(DataCiteHandlerHelper.RESOURCE_TYPE_GENERAL)) >= 0)
					rt.setResourceTypeGeneral(attributes.getValue(resourceType));
				resource.setResourceType(rt);
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
		} else if(tag.equals(DataCiteHandlerHelper.PUBLISHER_TAG)) {
			isInPublisher = false;
		} else if(tag.equals(DataCiteHandlerHelper.PUBLICATION_YEAR_TAG)) {
			isInPublicationYear = false;
		} else if(tag.equals(DataCiteHandlerHelper.SUBJECTS_TAG)) {
			isInSubjects = false;
		} else if(tag.equals(DataCiteHandlerHelper.SUBJECT_TAG)) {
			isInSubject = false;
		} else if(tag.equals(DataCiteHandlerHelper.DATES_TAG)) {
			isInDates = false;
		} else if(tag.equals(DataCiteHandlerHelper.DATE_TAG)) {
			isInDate = false;
		} else if(tag.equals(DataCiteHandlerHelper.LANGUAGE_TAG)) {
			isInLanguage = false;
		} else if(tag.equals(DataCiteHandlerHelper.RESOURCE_TYPE_TAG)) {
			isInResourceType = false;
		}
	}
	
	public Resource getParsedResource() {
		return this.resource;
	}
}
