package it.polito.tdp.yelp.model;

public class Arco {

	private String id1;
	private double lat1;
	private double longi1;
	
	private String id2;
	private double lat2;
	private double longi2;
	
	public Arco(String id1, double lat1, double longi1, String id2, double lat2, double longi2) {
		this.id1 = id1;
		this.lat1 = lat1;
		this.longi1 = longi1;
		this.id2 = id2;
		this.lat2 = lat2;
		this.longi2 = longi2;
	}

	public String getId1() {
		return id1;
	}

	public void setId1(String id1) {
		this.id1 = id1;
	}

	public double getLat1() {
		return lat1;
	}

	public void setLat1(double lat1) {
		this.lat1 = lat1;
	}

	public double getLongi1() {
		return longi1;
	}

	public void setLongi1(double longi1) {
		this.longi1 = longi1;
	}

	public String getId2() {
		return id2;
	}

	public void setId2(String id2) {
		this.id2 = id2;
	}

	public double getLat2() {
		return lat2;
	}

	public void setLat2(double lat2) {
		this.lat2 = lat2;
	}

	public double getLongi2() {
		return longi2;
	}

	public void setLongi2(double longi2) {
		this.longi2 = longi2;
	}
	
	
}
