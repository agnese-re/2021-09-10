package it.polito.tdp.yelp.model;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.yelp.db.YelpDao;

public class Model {
	
	private YelpDao dao;
	
	private Graph<Business,DefaultWeightedEdge> grafo;
	
	private List<Business> soluzioneMigliore;
	
	public Model() {
		dao = new YelpDao();
	}
	
	public void creaGrafo(String citta) {
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		// aggiunta dei vertici
		Graphs.addAllVertices(this.grafo, this.dao.getBusinessCity(citta));
		
		// aggiunta degli archi
		for(Business b1: this.grafo.vertexSet())
			for(Business b2: this.grafo.vertexSet()) 
				if(!b1.equals(b2)) {
					// Arco arco = this.dao.getArco(b1.getBusinessId(), b2.getBusinessId());
					// public static double distance(LatLng point1, LatLng point2, LengthUnit unit)
					LatLng point1 = new LatLng(b1.getLatitude(), b1.getLongitude());
					LatLng point2 = new LatLng(b2.getLatitude(), b2.getLongitude());
					double peso = LatLngTool.distance(point1, point2, LengthUnit.KILOMETER);
					Graphs.addEdgeWithVertices(this.grafo, b1, b2, peso);
				}		
	}
	
	public List<Business> calcolaPercorso(Business b1, Business b2, double soglia) {
		this.soluzioneMigliore  = new ArrayList<Business>();
		
		List<Business> parziale = new ArrayList<Business>();
		parziale.add(b1);	// b1 deve essere incluso nel percorso
		cercaPercorso(parziale,b2,soglia);
		
		return soluzioneMigliore;
	}
	
	private void cercaPercorso(List<Business> parziale, Business b2, double soglia) {
		// casi terminali
		if(parziale.get(parziale.size()-1).equals(b2)) {
			if(parziale.size() > soluzioneMigliore.size())
				soluzioneMigliore = new ArrayList<Business>(parziale);
			return;
		}
		// algoritmo ricorsivo
		List<Business> vicini = Graphs.neighborListOf(this.grafo, parziale.get(parziale.size()-1));
		for(Business vicino: vicini) 
			if(!parziale.contains(vicino) && vicino.getStars() > soglia) {
				parziale.add(vicino);
				cercaPercorso(parziale,b2,soglia);
				parziale.remove(parziale.size()-1);		// BACKTRACKING
		}
		
	}

	public double getKmPercorsi(List<Business> percorso) {
		double kmPercorsi = 0.0;
		
		for(int indice = 0; indice < percorso.size()-1; indice++) {
			Business b1 = percorso.get(indice);
			Business b2 = percorso.get(indice+1);
			DefaultWeightedEdge e = this.grafo.getEdge(b1, b2);
			kmPercorsi = kmPercorsi + this.grafo.getEdgeWeight(e);
		}
		
		return kmPercorsi;
	}
	
	public int getNVertici() {
		return this.grafo.vertexSet().size();
	}
	
	public int getNArchi() {
		return this.grafo.edgeSet().size();
	}
	
	public List<Business> getAllBusinesses() {
		List<Business> result = new ArrayList<Business>(this.grafo.vertexSet());
		return result;
	}
	
	public LocaleDistanza getLocaleDistante(Business locale) {
		LocaleDistanza result = null;
		double pesoMassimo = 0.0;
		
		// Trovo il peso massimo
		for(Business vicino: Graphs.neighborListOf(this.grafo, locale)) {
			DefaultWeightedEdge e = this.grafo.getEdge(locale, vicino);
			if(this.grafo.getEdgeWeight(e) > pesoMassimo) 
				pesoMassimo = this.grafo.getEdgeWeight(e);
		}
		
		for(DefaultWeightedEdge e: this.grafo.edgeSet())
			if(this.grafo.getEdgeWeight(e) == pesoMassimo) {
				Business opposto = Graphs.getOppositeVertex(this.grafo, e, locale);
				result = new LocaleDistanza(opposto, this.grafo.getEdgeWeight(e));
			}
		return result;
	}
	
	public List<String> getAllCities() {
		return dao.getAllCities();
	}
}
