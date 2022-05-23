package it.manytomanyjpamaven.dao;

import java.util.Date;
import java.util.List;

import it.manytomanyjpamaven.model.Ruolo;
import it.manytomanyjpamaven.model.StatoUtente;
import it.manytomanyjpamaven.model.Utente;

public interface UtenteDAO extends IBaseDAO<Utente> {
	
	public List<Utente> findAllByRuolo(Ruolo ruoloInput);
	
	public Utente findByIdFetchingRuoli(Long id);
	
	public List<Utente> findAllByDateOfJune2021(Date dataInput) throws Exception;
	
	public List<Utente> fcountAllUsersWithStatusAdmin(StatoUtente statoInput) throws Exception;

}
