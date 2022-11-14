package it.prova.gestionesocieta.service;

import java.util.List;

import it.prova.gestionesocieta.model.Dipendente;

public interface DipendenteService {
	
	public List<Dipendente> listAllDipendeti() ;

	public Dipendente caricaSingoloDipendente(Long id);

	public void aggiorna(Dipendente dipendenteInstance);

	public void inserisciNuovo(Dipendente dipendenteInstance);

	public void rimuovi(Dipendente dipendenteInstance);

}
