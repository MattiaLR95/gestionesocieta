package it.prova.gestionesocieta.service;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.gestionesocieta.model.Dipendente;
import it.prova.gestionesocieta.repository.DipendenteRepository;

@Service
public class DipendenteServiceImpl implements DipendenteService {

	@Autowired
	private DipendenteRepository dipendenteRepository;
	@PersistenceContext
	private EntityManager entityManager;

	@Transactional(readOnly = true)
	@Override
	public List<Dipendente> listAllDipendeti() {
		return (List<Dipendente>) dipendenteRepository.findAll();
	}

	@Transactional(readOnly = true)
	@Override
	public Dipendente caricaSingoloDipendente(Long id) {
		return dipendenteRepository.findById(id).orElse(null);
	}

	@Transactional
	@Override
	public void aggiorna(Dipendente dipendenteInstance) {
		dipendenteRepository.save(dipendenteInstance);
	}

	@Transactional
	@Override
	public void inserisciNuovo(Dipendente dipendenteInstance) {
		dipendenteRepository.save(dipendenteInstance);
	}

	@Transactional
	@Override
	public void rimuovi(Dipendente dipendenteInstance) {
		dipendenteRepository.delete(dipendenteInstance);
	}

	@Override
	public Dipendente dipendentePiuAnziano(Date date) {
		return dipendenteRepository.findTopBySocieta_DataFondazioneBeforeOrderByDataAssunzioneAsc(date);
	}

}
