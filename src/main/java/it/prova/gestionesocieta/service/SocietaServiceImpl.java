package it.prova.gestionesocieta.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.aop.ThrowsAdvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.lang3.StringUtils;

import it.prova.gestionesocieta.exception.RimuoviSocietaConDipendentiException;
import it.prova.gestionesocieta.model.Societa;
import it.prova.gestionesocieta.repository.SocietaRepository;

@Service
public class SocietaServiceImpl implements SocietaService {

	@Autowired
	private SocietaRepository societaRepository;
	@PersistenceContext
	private EntityManager entityManager;

	@Transactional(readOnly = true)
	@Override
	public List<Societa> listAllSocieta() {
		return (List<Societa>) societaRepository.findAll();
	}

	@Transactional(readOnly = true)
	@Override
	public Societa caricaSingolaSocieta(Long id) {
		return societaRepository.findById(id).orElse(null);

	}

	@Transactional
	@Override
	public void aggiorna(Societa societaInstance) {
		societaRepository.save(societaInstance);
	}

	@Transactional
	@Override
	public void inserisciNuovo(Societa societaInstance) {
		societaRepository.save(societaInstance);
	}

	@Transactional
	@Override
	public void rimuovi(Societa societaInstance) {
		if(societaInstance.getDipendenti()!= null)
			throw new RimuoviSocietaConDipendentiException("Attenzione! Dipendenti nella societa");
		societaRepository.delete(societaInstance);
	}

	@Transactional(readOnly = true)
	@Override
	public List<Societa> findByExample(Societa example) {
		String query = "select s from Societa s where s.id = s.id ";

		if (StringUtils.isNotEmpty(example.getRagioneSociale()))
			query += " and s.ragioneSociale like '%" + example.getRagioneSociale() + "%' ";
		if (StringUtils.isNotEmpty(example.getIndirizzo()))
			query += " and s.indirizzo like '%" + example.getIndirizzo() + "%' ";
		if (example.getDataFondazione() != null && !example.getDataFondazione().equals(null))
			query += " and s.dataFondazione >= '" + example.getDataFondazione().toInstant() + "'";

		return entityManager.createQuery(query, Societa.class).getResultList();
	}

}
