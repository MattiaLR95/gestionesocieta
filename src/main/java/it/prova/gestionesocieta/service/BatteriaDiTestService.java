package it.prova.gestionesocieta.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.prova.gestionesocieta.exception.RimuoviSocietaConDipendentiException;
import it.prova.gestionesocieta.model.Dipendente;
import it.prova.gestionesocieta.model.Societa;

@Service
public class BatteriaDiTestService {

	@Autowired
	private SocietaService societaService;

	@Autowired
	private DipendenteService dipendenteService;

	public void testInserisciSocieta() throws Exception {
		System.out.println("INIZIO TEST INSERISCI SOCIETA");

		Long nowInMillisecondi = new Date().getTime();
		Date nuovaData = new SimpleDateFormat("dd-MM-yyyy").parse("06-01-1995");
		Societa nuovaSocieta = new Societa("GoPro " + nowInMillisecondi, "Via " + nowInMillisecondi, nuovaData);
		societaService.inserisciNuovo(nuovaSocieta);
		if (societaService.listAllSocieta().size() != 1)
			throw new RuntimeException("Attenzione! Test Fallito");
		System.out.println("Test Completato");
	}

	public void testFindByExample() throws Exception {
		System.out.println("INIZIO TEST FIND BY EXAMPLE");

		Date nuovaData = new SimpleDateFormat("dd-MM-yyyy").parse("06-01-1995");
		Societa nuovaSocieta = new Societa("GoPro", "Via di casa mia", nuovaData);
		Societa societaExmaple = new Societa();

		societaService.inserisciNuovo(nuovaSocieta);
		if (societaService.findByExample(societaExmaple).size() != 2)
			throw new RuntimeException("Attenzione! Test Fallito");
		System.out.println("Test Completato");
	}

	public void testRimozioneSocieta() throws Exception {
		
		System.out.println("INIZIO TEST RIMOZIONE SOCIETA");

		Date nuovaData = new SimpleDateFormat("dd-MM-yyyy").parse("06-01-1995");
		Societa nuovaSocieta = new Societa("Marca", "Via della societa", nuovaData);
		Long idNuovaSocieta = nuovaSocieta.getId();
		societaService.inserisciNuovo(nuovaSocieta);
		societaService.rimuovi(nuovaSocieta);
		List<Societa> listaDatabase = societaService.listAllSocieta();
		for (Societa societaItem : listaDatabase) {
			if (idNuovaSocieta == societaItem.getId())
				throw new RuntimeException("Attenzione! Test Fallito");
		}
		System.out.println("Prima parte del test Completata");

		// Eliminiamo adesso una societa con dipendenti all'interno

		Societa societaConDipendente = new Societa("Societa", "Via di casa mia", nuovaData);
		societaService.inserisciNuovo(societaConDipendente);
		Dipendente nuovoDipendente = new Dipendente("Mattia","La Rocca",nuovaData,15000,societaConDipendente);
		dipendenteService.inserisciNuovo(nuovoDipendente);
		societaConDipendente.getDipendenti().add(nuovoDipendente);
		try {
			societaService.rimuovi(societaConDipendente);
		} catch (RimuoviSocietaConDipendentiException e) {
			e.printStackTrace();
		}
		System.out.println("TEST COMPLETATO");
	}
	
	public void testInserimentoDipendente () throws Exception{
		System.out.println("INIZIO TEST INSERIMENTO DIPENDENTE");
		
		Date nuovaData = new SimpleDateFormat("dd-MM-yyyy").parse("06-01-1995");
		Societa nuovaSocieta = new Societa("Marca", "Via della societa", nuovaData);
		societaService.inserisciNuovo(nuovaSocieta);
		
		Dipendente nuovoDipendente = new Dipendente("Mattia","La Rocca",nuovaData,15000,nuovaSocieta);
		dipendenteService.inserisciNuovo(nuovoDipendente);
		nuovaSocieta.getDipendenti().add(nuovoDipendente);
		
		if(nuovaSocieta.getDipendenti().size()!=1)
			throw new RuntimeException("Attenzione! Test Fallito");
		
		System.out.println("Test Completato");
	}
	
	public void testAggiornamentoDipendenti() throws Exception{
		System.out.println("INIZIO TEST AGGIORNAMENTO DIPENDENTE");
		
		Date nuovaData = new SimpleDateFormat("dd-MM-yyyy").parse("06-01-1995");
		Societa nuovaSocieta = new Societa("Marca", "Via della societa", nuovaData);
		societaService.inserisciNuovo(nuovaSocieta);
		
		Dipendente nuovoDipendente = new Dipendente("Mattia","La Rocca",nuovaData,15000,nuovaSocieta);
		dipendenteService.inserisciNuovo(nuovoDipendente);
		nuovaSocieta.getDipendenti().add(nuovoDipendente);
		
		String nomeDipendenteVecchio = nuovoDipendente.getNome();
		String nomeNuovo = "Carlo";
		nuovoDipendente.setNome(nomeNuovo);
		dipendenteService.aggiorna(nuovoDipendente);
		if(nomeDipendenteVecchio.equals(nuovoDipendente.getNome()))
			throw new RuntimeException("Attenzione! Test Fallito!");
		System.out.println("TEST COMPLETATO");
	}
	
	public void testSocietaConDipendentiConRalMaggioreDi() throws Exception{
		System.out.println("INIZIO TEST SOCIETA CON DIPENDENTI CON RAL MAGGIORE DI");
		
		Date nuovaData = new SimpleDateFormat("dd-MM-yyyy").parse("06-01-1995");
		Societa nuovaSocieta = new Societa("Marca", "Via della societa", nuovaData);
		societaService.inserisciNuovo(nuovaSocieta);
		
		Dipendente nuovoDipendente = new Dipendente("Mattia","La Rocca",nuovaData,40000,nuovaSocieta);
		dipendenteService.inserisciNuovo(nuovoDipendente);
		nuovaSocieta.getDipendenti().add(nuovoDipendente);
		if(societaService.trovaSocietaConDipendenteConRalCheParte(30000).size()!=1)
			throw new RuntimeException("Attenzione! TEST FALLITO");
		System.out.println("TEST COMPLETATO");
	}
	
	public void testDipendentePiuAnziano() throws Exception{
		System.out.println("INIZIO TEST DIPENDENTE PIU ANZIANO");
		
		Date nuovaData = new SimpleDateFormat("dd-MM-yyyy").parse("06-01-1989");
		Societa nuovaSocieta = new Societa("Marca", "Via della societa", nuovaData);
		societaService.inserisciNuovo(nuovaSocieta);
		
		Dipendente nuovoDipendente = new Dipendente("Mattia","La Rocca",nuovaData,40000,nuovaSocieta);
		Date nuovaData1 = new SimpleDateFormat("dd-MM-yyyy").parse("06-01-1998");
		Dipendente nuovoDipendente1 = new Dipendente("Mattia","La Rocca",nuovaData1,40000,nuovaSocieta);
		dipendenteService.inserisciNuovo(nuovoDipendente);
		dipendenteService.inserisciNuovo(nuovoDipendente1);
		nuovaSocieta.getDipendenti().add(nuovoDipendente);
		nuovaSocieta.getDipendenti().add(nuovoDipendente1);
		
		if(nuovoDipendente.getId()!=dipendenteService.dipendentePiuAnziano(new SimpleDateFormat("dd-MM-yyyy").parse("01-01-1990")).getId())
			throw new RuntimeException("ATTENZIONE! TEST FALLITO");
		System.out.println("TEST COMPLETATO");
	}
}
