package it.polito.tdp.IndovinaNumero.model;

import java.security.InvalidParameterException;
import java.util.*;

public class Model {

	private int NMAX;
	private int TMAX;
	private int segreto;
	private int tentativiFatti;
	private boolean inGioco = false;
	
	private Set<Integer> tentativi;
	private ArrayList<Integer> bassi;
	private ArrayList<Integer> alti;
	
	public String assistita() {
		int min = 0;
    	int max = 0;
    	
    	Collections.sort(bassi);
    	Collections.sort(alti);
    	
    	if(alti.isEmpty())
    		max = NMAX;
    	else
    		max = alti.get(0);
    	
    	if(bassi.isEmpty())
    		min = 1;
    	else
    		min = bassi.get(bassi.size()-1);
    	
		return "" + min + ";" + max;
	}
	
	public void nuovaPartita(String difficolta) {
		//gestione difficoltà
		if(difficolta == null)
			throw new NullPointerException("Scegliere una difficoltà");
		if(difficolta.compareTo("Facile") == 0)
			NMAX = 50;
		if(difficolta.compareTo("Medio") == 0)
			NMAX = 100;
		if(difficolta.compareTo("Difficile") == 0)
			NMAX = 150;
		
		TMAX = (int) (Math.log(NMAX)/Math.log(2));
		//gestione inizio nuova partita
    	this.segreto = (int) (Math.random() * NMAX) + 1;
    	this.tentativiFatti = 0;
    	this.inGioco = true;
    	this.tentativi = new HashSet<Integer>();
    	this.alti = new ArrayList<Integer>();
    	this.bassi = new ArrayList<Integer>();
	}
	
	public int tentativo(int tentativo) {
		//controllo se la partita è in corso
		if(!inGioco && tentativo != this.segreto) {
			this.tentativiFatti++;
			throw new IllegalStateException("HAI PERSO! IL SEGRETO ERA " + this.segreto);
		}
		
		//controllo dell'input
		if(!this.tentativoValido(tentativo))
			throw new InvalidParameterException("Devi inserire un numero tra 1 e NMAX solo una volta");
		
		//il tentativo è valido
		this.tentativiFatti++;
		this.tentativi.add(tentativo);
		
		if(this.tentativiFatti == TMAX-1)
			this.inGioco = false;
		
		if(tentativo == this.segreto) {
			this.inGioco = false;
			return 0;
		}
		else if (tentativo < this.segreto) {
			bassi.add(tentativo);
			return -1;
		}
		else {
			alti.add(tentativo);
			return 1;
		}
	}
	
	private boolean tentativoValido(int tentativo) {
		if(tentativo < 1 || tentativo > NMAX)
			return false;
		if(this.tentativi.contains(tentativo))
			return false;
		
		return true;
	}
	
	public int getSegreto() {
		return segreto;
	}

	public int getTentativiFatti() {
		return tentativiFatti;
	}

	public boolean isInGioco() {
		return inGioco;
	}

	public int getNMAX() {
		return NMAX;
	}

	public int getTMAX() {
		return TMAX;
	}

}
