package dk.rootuser.daa.sorters;

import java.util.Comparator;

import dk.rootuser.daa.pojos.datacite.Resource;

/**
 * This sorter can sort a list of resources according to the first title kept in the resource
 * @author martin
 *
 */

public class TitleSorter implements Comparator<Resource>{

	private SortOrder order;
	
	public TitleSorter(SortOrder order) {
		this.order = order;
	}
	
	@Override
	public int compare(Resource r1, Resource r2) {
		if(order == SortOrder.ASC)
			return r1.getTitles().get(0).getTitle().compareTo(r2.getTitles().get(0).getTitle());
		else
			return -r1.getTitles().get(0).getTitle().compareTo(r2.getTitles().get(0).getTitle());
	}

}
