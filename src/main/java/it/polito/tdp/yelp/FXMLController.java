/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.yelp;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.yelp.model.Business;
import it.polito.tdp.yelp.model.LocaleDistanza;
import it.polito.tdp.yelp.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnDistante"
    private Button btnDistante; // Value injected by FXMLLoader

    @FXML // fx:id="btnCalcolaPercorso"
    private Button btnCalcolaPercorso; // Value injected by FXMLLoader

    @FXML // fx:id="txtX2"
    private TextField txtX2; // Value injected by FXMLLoader

    @FXML // fx:id="cmbCitta"
    private ComboBox<String> cmbCitta; // Value injected by FXMLLoader

    @FXML // fx:id="cmbB1"
    private ComboBox<Business> cmbB1; // Value injected by FXMLLoader

    @FXML // fx:id="cmbB2"
    private ComboBox<Business> cmbB2; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader
    
    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	this.cmbB1.getItems().clear();
    	this.cmbB2.getItems().clear();
    	String citta = this.cmbCitta.getValue();
    	if(citta != null) {
    		this.model.creaGrafo(citta);
    		txtResult.appendText("Grafo creato!\n");
    		txtResult.appendText("# VERTICI: " + this.model.getNVertici() + "\n");
    		txtResult.appendText("# ARCHI: " + this.model.getNArchi() + "\n");
    		this.cmbB1.getItems().addAll(this.model.getAllBusinesses());
    		this.cmbB2.getItems().addAll(this.model.getAllBusinesses());
    	} else {
    		txtResult.setText("Scegli una citta' dal menu' a tendina");
    		return;
    	}
    }

    @FXML
    void doCalcolaLocaleDistante(ActionEvent event) {
    	txtResult.clear();
    	Business locale = this.cmbB1.getValue();
    	if(locale != null) {
    		LocaleDistanza distante = this.model.getLocaleDistante(locale);
    		txtResult.appendText("LOCALE PIU' DISTANTE:\n");
    		txtResult.appendText(distante.toString());
    	} else {
    		txtResult.setText("Scegli un locale dal menu' a tendina");
    		return;
    	}
    	
    }

    @FXML
    void doCalcolaPercorso(ActionEvent event) {
    	txtResult.clear();
    	Business locale1 = this.cmbB1.getValue();
    	Business locale2 = this.cmbB2.getValue();
    	double soglia;
    	try {
    		soglia = Double.parseDouble(this.txtX2.getText());
    		if(locale1.equals(locale2)) {
    			txtResult.setText("Il locale2 deve essere diverso dal locale1!");
    			return;
    		} else {
	    		List<Business> percorso = this.model.calcolaPercorso(locale1, locale2, soglia);
	    		txtResult.appendText("PERCORSO DI LUNGHEZZA MAGGIORE:\n");
	    		for(Business b: percorso)
	    			txtResult.appendText(b.toString()+"\n");
	    		txtResult.appendText("DISTANZA PERCORSA: " + this.model.getKmPercorsi(percorso));
    		}
    	} catch(NumberFormatException nfe) {
    		txtResult.setText("Inserisci un numero, NON caratteri o stringhe");
    		return;
    	}
    }


    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnDistante != null : "fx:id=\"btnDistante\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCalcolaPercorso != null : "fx:id=\"btnCalcolaPercorso\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtX2 != null : "fx:id=\"txtX2\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbCitta != null : "fx:id=\"cmbCitta\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbB1 != null : "fx:id=\"cmbB1\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbB2 != null : "fx:id=\"cmbB2\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	
    	this.cmbCitta.getItems().addAll(this.model.getAllCities());
    }
}
