package cpinotos.openapi.services.data;

import java.util.List;

public class PapiSearchResultVersion {
	private List<PapiSearchResultItem> items;

	public List<PapiSearchResultItem> getItems() {
		return items;
	}

	public void setItems(List<PapiSearchResultItem> items) {
		this.items = items;
	}

}
