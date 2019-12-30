package com.microservices.moviecatalog.model;

import java.util.List;


public class MovieCatalog {
 
	List<CatalogItem> catalogItems;

	public MovieCatalog() {
		super();
	}

	public MovieCatalog(List<CatalogItem> catalogItems) {
		super();
		this.catalogItems = catalogItems;
	}

	public List<CatalogItem> getCatalogItems() {
		return catalogItems;
	}

	public void setCatalogItems(List<CatalogItem> catalogItems) {
		this.catalogItems = catalogItems;
	}
	
	
	
}
