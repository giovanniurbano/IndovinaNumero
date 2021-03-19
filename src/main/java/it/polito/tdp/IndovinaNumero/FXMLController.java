/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.IndovinaNumero;

import java.net.URL;
import java.security.InvalidParameterException;
import java.util.ResourceBundle;

import it.polito.tdp.IndovinaNumero.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class FXMLController {

	private Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;
    
    @FXML // fx:id="layoutTentativo"
    private HBox layoutTentativo; // Value injected by FXMLLoader
    @FXML
    private Label txtIntervallo;
    
    @FXML
    private CheckBox boxAssistita;
    
    @FXML
    private ProgressBar barTentativi;
    
    @FXML // fx:id="btnNuovaPartita"
    private Button btnNuovaPartita; // Value injected by FXMLLoader

    @FXML // fx:id="txtTentativi"
    private TextField txtTentativi; // Value injected by FXMLLoader

    @FXML // fx:id="txtTentativoUtente"
    private TextField txtTentativoUtente; // Value injected by FXMLLoader

    @FXML // fx:id="btnProva"
    private Button btnProva; // Value injected by FXMLLoader

    @FXML // fx:id="txtRisultato"
    private TextArea txtRisultato; // Value injected by FXMLLoader

    @FXML
    void doNuovaPartita(ActionEvent event) {
    	//inizio la partita
    	this.model.nuovaPartita();
    	
    	//gestione dell'interfaccia
    	this.txtTentativi.setText(Integer.toString(this.model.getTMAX()));
    	this.layoutTentativo.setDisable(false);
    	this.txtRisultato.setText("");
    	
    	txtTentativoUtente.setText("");
    	txtRisultato.setText("");
    	this.barTentativi.setProgress(0);
    	boxAssistita.setSelected(false);
    	txtIntervallo.setText("");
    }

    @FXML
    void doTentativo(ActionEvent event) {
    	//lettura input dell'utente
    	String ts = txtTentativoUtente.getText();
    	
    	int tentativo;
    	try {
    		tentativo = Integer.parseInt(ts);
    	}catch(NumberFormatException e) {
    		txtRisultato.setText("Devi inserire un numero!");
    		return;
    	}

    	this.txtTentativoUtente.setText("");
    	
    	int result;
    	try {
    		result = this.model.tentativo(tentativo);
    	}
    	catch (IllegalStateException ise) {
    		this.txtRisultato.setText(ise.getMessage());
    		this.barTentativi.setProgress(0);
    		this.txtTentativi.setText(Integer.toString(this.model.getTMAX()-this.model.getTentativiFatti()));
    		this.layoutTentativo.setDisable(true);
    		return;
    	}
		catch (InvalidParameterException ipe) {
			this.txtRisultato.setText(ipe.getMessage());
    		return;		
		}
    	
    	this.txtTentativi.setText(Integer.toString(this.model.getTMAX()-this.model.getTentativiFatti()));
    	this.barTentativi.setProgress(1-(double)(this.model.getTMAX()-this.model.getTentativiFatti())/this.model.getTMAX());
    	
    	if(result == 0) {
    		//HO INDOVINATO!
    		txtRisultato.setText("HAI VINTO CON " + this.model.getTentativiFatti() + " TENTATIVI");
    		this.barTentativi.setProgress(0);
    		this.layoutTentativo.setDisable(true);
    		return;
    	}
    	else if(result < 0) {
    		txtRisultato.setText("TENTATIVO TROPPO BASSO");
    		if(boxAssistita.isSelected())
    			this.doAssistita(event);
    	} 
    	else {
    		txtRisultato.setText("TENTATIVO TROPPO ALTO");
    		if(boxAssistita.isSelected())
    			this.doAssistita(event);
    	}
    	if(!boxAssistita.isSelected())
    		txtIntervallo.setText("");
    }
    
    @FXML
    void doAssistita(ActionEvent event) {
    	String estremi[] = model.assistita().split(";");
    	int min = Integer.parseInt(estremi[0]);
    	int max = Integer.parseInt(estremi[1]);
    	txtIntervallo.setText("Il segreto è compreso tra " + min + " e " + max);
    }


    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
    	 assert btnNuovaPartita != null : "fx:id=\"btnNuovaPartita\" was not injected: check your FXML file 'Scene.fxml'.";
         assert txtTentativi != null : "fx:id=\"txtTentativi\" was not injected: check your FXML file 'Scene.fxml'.";
         assert barTentativi != null : "fx:id=\"barTentativi\" was not injected: check your FXML file 'Scene.fxml'.";
         assert layoutTentativo != null : "fx:id=\"layoutTentativo\" was not injected: check your FXML file 'Scene.fxml'.";
         assert txtTentativoUtente != null : "fx:id=\"txtTentativoUtente\" was not injected: check your FXML file 'Scene.fxml'.";
         assert btnProva != null : "fx:id=\"btnProva\" was not injected: check your FXML file 'Scene.fxml'.";
         assert boxAssistita != null : "fx:id=\"boxAssistita\" was not injected: check your FXML file 'Scene.fxml'.";
         assert txtIntervallo != null : "fx:id=\"txtIntervallo\" was not injected: check your FXML file 'Scene.fxml'.";
         assert txtRisultato != null : "fx:id=\"txtRisultato\" was not injected: check your FXML file 'Scene.fxml'.";
    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
}

