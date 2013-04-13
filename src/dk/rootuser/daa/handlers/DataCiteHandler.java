package dk.rootuser.daa.handlers;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import dk.rootuser.daa.handlerhelpers.DataCiteHandlerHelper;
import dk.rootuser.daa.pojos.datacite.AlternateIdentifier;
import dk.rootuser.daa.pojos.datacite.Creator;
import dk.rootuser.daa.pojos.datacite.Date;
import dk.rootuser.daa.pojos.datacite.Description;
import dk.rootuser.daa.pojos.datacite.Format;
import dk.rootuser.daa.pojos.datacite.Identifier;
import dk.rootuser.daa.pojos.datacite.Resource;
import dk.rootuser.daa.pojos.datacite.ResourceType;
import dk.rootuser.daa.pojos.datacite.Size;
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
	
	private boolean isInAlternateIdentifiers = false;
	private boolean isInAlternateIdentifier = false;
	
	private boolean isInSizes = false;
	private boolean isInSize = false;
	
	private boolean isInFormats = false;
	private boolean isInFormat = false;
	
	private boolean isInVersion = false;
	
	private boolean isInRights = false;
	
	private boolean isInDescriptions = false;
	private boolean isInDescription = false;
	
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
		} else if(tag.equals(DataCiteHandlerHelper.ALTERNATEINDENTIFIERS_TAG)) {
			isInAlternateIdentifiers = true;
			resource.setAlternateIdentifiers(new ArrayList<AlternateIdentifier>());
		} else if(tag.equals(DataCiteHandlerHelper.ALTERNATEINDENTIFIER_TAG)) {
			isInAlternateIdentifier = true;
		} else if(tag.equals(DataCiteHandlerHelper.SIZES_TAG)) {
			isInSizes = true;
			resource.setSizes(new ArrayList<Size>());
		} else if(tag.equals(DataCiteHandlerHelper.SIZE_TAG)) {
			isInSize = true;
		} else if(tag.equals(DataCiteHandlerHelper.FORMATS_TAG)) {
			isInFormats = true;
			resource.setFormats(new ArrayList<Format>());
		} else if(tag.equals(DataCiteHandlerHelper.FORMAT_TAG)) {
			isInFormat = true;
		} else if(tag.equals(DataCiteHandlerHelper.VERSION_TAG)) {
			isInVersion = true;
		} else if(tag.equals(DataCiteHandlerHelper.RIGHTS_TAG)) {
			isInRights = true;
		} else if(tag.equals(DataCiteHandlerHelper.DESCRIPTIONS_TAG)) {
			isInDescriptions = true;
			resource.setDescriptions(new ArrayList<Description>());
		} else if(tag.equals(DataCiteHandlerHelper.DESCRIPTION_TAG)) {
			isInDescription = true;
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
			} else if(isInAlternateIdentifiers) {
				if(isInAlternateIdentifier) {
					int alternateIdentifierType;
					AlternateIdentifier a = new AlternateIdentifier();
					a.setAlternateIdentifier(new String(ch, start, length));
					if((alternateIdentifierType = attributes.getIndex(DataCiteHandlerHelper.ALTERNATEINDENTIFIER_TYPE)) >= 0)
						a.setAlternateIdentifierType(attributes.getValue(alternateIdentifierType));
					resource.getAlternateIdentifiers().add(a);
				}
			} else if(isInSizes) {
				if(isInSize) {
					Size s = new Size();
					s.setSize(new String(ch, start, length));
					resource.getSizes().add(s);
				}
			} else if(isInFormats) {
				if(isInFormat) {
					Format f = new Format();
					f.setFormat(new String(ch, start, length));
					resource.getFormats().add(f);
				}
			} else if(isInVersion) {
				resource.setVersion(new String(ch, start, length));
			} else if(isInRights) {
				resource.setRights(new String(ch, start, length));
			} else if(isInDescriptions) {
				if(isInDescription) {
					int descriptionsType;
					Description d = new Description();
					d.setDescription(new String(ch, start, length));
					if((descriptionsType = attributes.getIndex(DataCiteHandlerHelper.DESCRIPTION_TYPE)) >= 0)
						d.setDescriptionType(attributes.getValue(descriptionsType));
					resource.getDescriptions().add(d);
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
		} else if(tag.equals(DataCiteHandlerHelper.ALTERNATEINDENTIFIERS_TAG)) {
			isInAlternateIdentifiers = false;
		} else if(tag.equals(DataCiteHandlerHelper.ALTERNATEINDENTIFIER_TAG)) {
			isInAlternateIdentifier = false;
		} else if(tag.equals(DataCiteHandlerHelper.SIZES_TAG)) {
			isInSizes = false;
		} else if(tag.equals(DataCiteHandlerHelper.SIZE_TAG)) {
			isInSize = false;
		} else if(tag.equals(DataCiteHandlerHelper.FORMATS_TAG)) {
			isInFormats = false;
		} else if(tag.equals(DataCiteHandlerHelper.FORMAT_TAG)) {
			isInFormat = false;
		} else if(tag.equals(DataCiteHandlerHelper.VERSION_TAG)) {
			isInVersion = false;
		} else if(tag.equals(DataCiteHandlerHelper.RIGHTS_TAG)) {
			isInRights = false;
		} else if(tag.equals(DataCiteHandlerHelper.DESCRIPTIONS_TAG)) {
			isInDescriptions = false;
		} else if(tag.equals(DataCiteHandlerHelper.DESCRIPTION_TAG)) {
			isInDescription = false;
		}
	}
	
	public Resource getParsedResource() {
		return this.resource;
	}
}
