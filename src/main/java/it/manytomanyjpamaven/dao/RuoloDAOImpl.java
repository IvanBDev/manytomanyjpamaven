package it.manytomanyjpamaven.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.mysql.cj.Query;

import it.manytomanyjpamaven.model.Ruolo;

public class RuoloDAOImpl implements RuoloDAO {

	private EntityManager entityManager;

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public List<Ruolo> list() throws Exception {
		// TODO Auto-generated method stub
		return entityManager.createQuery("FROM Ruolo", Ruolo.class).getResultList();
	}

	@Override
	public Ruolo get(Long id) throws Exception {
		return entityManager.find(Ruolo.class, id);
	}

	@Override
	public void update(Ruolo ruoloInstance) throws Exception {
		// TODO Auto-generated method stub
		if(ruoloInstance == null)
			throw new Exception("Valore in input non valido");
		
		entityManager.merge(ruoloInstance);
	}

	@Override
	public void insert(Ruolo ruoloInstance) throws Exception {
		if (ruoloInstance == null) {
			throw new Exception("Problema valore in input");
		}

		entityManager.persist(ruoloInstance);

	}

	@Override
	public void delete(Ruolo ruoloInstance) throws Exception {
		// TODO Auto-generated method stub
		if(ruoloInstance == null)
			throw new Exception("Valore input non valido");
		
		entityManager.remove(entityManager.merge(ruoloInstance));
	}

	@Override
	public Ruolo findByDescrizioneAndCodice(String descrizione, String codice) throws Exception {
		TypedQuery<Ruolo> query = entityManager
				.createQuery("select r from Ruolo r where r.descrizione=?1 and r.codice=?2", Ruolo.class)
				.setParameter(1, descrizione)
				.setParameter(2, codice);
		
		return query.getResultStream().findFirst().orElse(null);
	}

	@Override
	public List<String> findAllByDistinctDescriptionOfAssociateUsers() throws Exception {
		// TODO Auto-generated method stub
		Query query = (Query) entityManager.createNativeQuery("SELECT DISTINCT r.descrizione FROM ruolo r INNER JOIN utente_ruolo ur ON r.id = ur.ruolo_id JOIN utente u ON u.id = ur.utente_id;");
		
		return null;
	}

}
