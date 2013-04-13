package dk.rootuser.daa.pojos.datacite;

import java.util.List;

public class Resource {

	private Identifier identifier;
	private List<Creator> creators;
	private List<Title> titles;
	private String publisher;
	private String publicationYear;
	private List<Subject> subjects;
	private List<Contributer> contributers;
	private List<Date> dates;
	private String language;
	private ResourceType resourceType;
	private List<AlternateIdentifier> alternateIdentifiers;
	private List<RelatedIdentifiers> relatedIdentifiers;
	private List<Size> sizes;
	private List<Format> formats;
	private String version;
	private List<Right> rights;
	private List<Description> descriptions;
	
	public Identifier getIdentifier() {
		return identifier;
	}

	public void setIdentifier(Identifier identifier) {
		this.identifier = identifier;
	}

	public List<Creator> getCreators() {
		return creators;
	}

	public void setCreators(List<Creator> creators) {
		this.creators = creators;
	}

	public List<Title> getTitles() {
		return titles;
	}

	public void setTitles(List<Title> titles) {
		this.titles = titles;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getPublicationYear() {
		return publicationYear;
	}

	public void setPublicationYear(String publicationYear) {
		this.publicationYear = publicationYear;
	}

	public List<Subject> getSubjects() {
		return subjects;
	}

	public void setSubjects(List<Subject> subjects) {
		this.subjects = subjects;
	}

	public List<Contributer> getContributers() {
		return contributers;
	}

	public void setContributers(List<Contributer> contributers) {
		this.contributers = contributers;
	}

	public List<Date> getDates() {
		return dates;
	}

	public void setDates(List<Date> dates) {
		this.dates = dates;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public ResourceType getResourceType() {
		return resourceType;
	}

	public void setResourceType(ResourceType resourceType) {
		this.resourceType = resourceType;
	}

	public List<AlternateIdentifier> getAlternateIdentifiers() {
		return alternateIdentifiers;
	}

	public void setAlternateIdentifiers(
			List<AlternateIdentifier> alternateIdentifiers) {
		this.alternateIdentifiers = alternateIdentifiers;
	}

	public List<RelatedIdentifiers> getRelatedIdentifiers() {
		return relatedIdentifiers;
	}

	public void setRelatedIdentifiers(List<RelatedIdentifiers> relatedIdentifiers) {
		this.relatedIdentifiers = relatedIdentifiers;
	}

	public List<Size> getSizes() {
		return sizes;
	}

	public void setSizes(List<Size> sizes) {
		this.sizes = sizes;
	}

	public List<Format> getFormats() {
		return formats;
	}

	public void setFormats(List<Format> formats) {
		this.formats = formats;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public List<Right> getRights() {
		return rights;
	}

	public void setRights(List<Right> rights) {
		this.rights = rights;
	}

	public List<Description> getDescriptions() {
		return descriptions;
	}

	public void setDescriptions(List<Description> descriptions) {
		this.descriptions = descriptions;
	}

	public String getLastMetadataUpdate() {
		return lastMetadataUpdate;
	}

	public void setLastMetadataUpdate(String lastMetadataUpdate) {
		this.lastMetadataUpdate = lastMetadataUpdate;
	}

	public int getMetadataVersionNumber() {
		return metadataVersionNumber;
	}

	public void setMetadataVersionNumber(int metadataVersionNumber) {
		this.metadataVersionNumber = metadataVersionNumber;
	}

	private String lastMetadataUpdate;
	
	private int metadataVersionNumber;
	
}
