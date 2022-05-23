package it.manytomanyjpamaven.test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import it.manytomanyjpamaven.dao.EntityManagerUtil;
import it.manytomanyjpamaven.model.Ruolo;
import it.manytomanyjpamaven.model.StatoUtente;
import it.manytomanyjpamaven.model.Utente;
import it.manytomanyjpamaven.service.MyServiceFactory;
import it.manytomanyjpamaven.service.RuoloService;
import it.manytomanyjpamaven.service.UtenteService;

public class ManyToManyTest {

	public static void main(String[] args) {
		UtenteService utenteServiceInstance = MyServiceFactory.getUtenteServiceInstance();
		RuoloService ruoloServiceInstance = MyServiceFactory.getRuoloServiceInstance();

		// ora passo alle operazioni CRUD
		try {

			// inizializzo i ruoli sul db
			//initRuoli(ruoloServiceInstance);

			//System.out.println("In tabella Utente ci sono " + utenteServiceInstance.listAll().size() + " elementi.");

			//testInserisciNuovoUtente(utenteServiceInstance);
			//System.out.println("In tabella Utente ci sono " + utenteServiceInstance.listAll().size() + " elementi.");

			//testCollegaUtenteARuoloEsistente(ruoloServiceInstance, utenteServiceInstance);
			//System.out.println("In tabella Utente ci sono " + utenteServiceInstance.listAll().size() + " elementi.");

			//testModificaStatoUtente(utenteServiceInstance);
			//System.out.println("In tabella Utente ci sono " + utenteServiceInstance.listAll().size() + " elementi.");

			//testRimuoviRuoloDaUtente(ruoloServiceInstance, utenteServiceInstance);
			//System.out.println("In tabella Utente ci sono " + utenteServiceInstance.listAll().size() + " elementi.");
			
			//testListAll(ruoloServiceInstance);
			
			//testAggiorna(ruoloServiceInstance);
			
			//testRimuoviRuolo(ruoloServiceInstance);
			
			//testRimuoviUtente(utenteServiceInstance, ruoloServiceInstance);

			//testListaDiTutteLeDescrizioniDegliUtentiAssociati(ruoloServiceInstance);
			
			//testTrovaTuttiGliUtentiRegistratiAGiugno(utenteServiceInstance);
			
			
		} catch (Throwable e) {
			e.printStackTrace();
		} finally {
			// questa è necessaria per chiudere tutte le connessioni quindi rilasciare il
			// main
			EntityManagerUtil.shutdown();
		}

	}

	private static void initRuoli(RuoloService ruoloServiceInstance) throws Exception {
		if (ruoloServiceInstance.cercaPerDescrizioneECodice("Administrator", "ROLE_ADMIN") == null) {
			ruoloServiceInstance.inserisciNuovo(new Ruolo("Administrator", "ROLE_ADMIN"));
		}

		if (ruoloServiceInstance.cercaPerDescrizioneECodice("Classic User", "ROLE_CLASSIC_USER") == null) {
			ruoloServiceInstance.inserisciNuovo(new Ruolo("Classic User", "ROLE_CLASSIC_USER"));
		}
	}

	private static void testInserisciNuovoUtente(UtenteService utenteServiceInstance) throws Exception {
		System.out.println(".......testInserisciNuovoUtente inizio.............");

		Utente utenteNuovo = new Utente("pippo.rossi", "xxx", "pippo", "rossi", new Date());
		utenteServiceInstance.inserisciNuovo(utenteNuovo);
		if (utenteNuovo.getId() == null)
			throw new RuntimeException("testInserisciNuovoUtente fallito ");

		System.out.println(".......testInserisciNuovoUtente fine: PASSED.............");
	}

	private static void testCollegaUtenteARuoloEsistente(RuoloService ruoloServiceInstance,
			UtenteService utenteServiceInstance) throws Exception {
		System.out.println(".......testCollegaUtenteARuoloEsistente inizio.............");

		Ruolo ruoloEsistenteSuDb = ruoloServiceInstance.cercaPerDescrizioneECodice("Classic User", "ROLE_CLASSIC_USER");
		if (ruoloEsistenteSuDb == null)
			throw new RuntimeException("testCollegaUtenteARuoloEsistente fallito: ruolo inesistente ");
		
		Date dataCreazione = null;
		dataCreazione =  new SimpleDateFormat("dd/MM/yyyy").parse("26/07/2021");

		// mi creo un utente inserendolo direttamente su db
		/*Utente utenteNuovo = new Utente("ivan.bendotti", "ib2002", "ivan", "bendotti", dataCreazione);
		utenteServiceInstance.inserisciNuovo(utenteNuovo);
		if (utenteNuovo.getId() == null)
			throw new RuntimeException("testInserisciNuovoUtente fallito: utente non inserito ");*/
		
		Utente utenteEsistente = utenteServiceInstance.listAll().get(3);

		utenteServiceInstance.aggiungiRuolo(utenteEsistente, ruoloEsistenteSuDb);
		// per fare il test ricarico interamente l'oggetto e la relazione
		Utente utenteReloaded = utenteServiceInstance.caricaUtenteSingoloConRuoli(utenteEsistente.getId());
		if (utenteReloaded.getRuoli().size() != 1)
			throw new RuntimeException("testInserisciNuovoUtente fallito: ruoli non aggiunti ");

		System.out.println(".......testCollegaUtenteARuoloEsistente fine: PASSED.............");
	}

	private static void testModificaStatoUtente(UtenteService utenteServiceInstance) throws Exception {
		System.out.println(".......testModificaStatoUtente inizio.............");
		
		Date dataCreazione = null;
		dataCreazione =  new SimpleDateFormat("dd/MM/yyyy").parse("12/07/2021");

		// mi creo un utente inserendolo direttamente su db
		Utente utenteNuovo = new Utente("gabriele99", "gb9922", "gabriele", "gravotta", dataCreazione);
		utenteServiceInstance.inserisciNuovo(utenteNuovo);
		if (utenteNuovo.getId() == null)
			throw new RuntimeException("testModificaStatoUtente fallito: utente non inserito ");

		// proviamo a passarlo nello stato ATTIVO ma salviamoci il vecchio stato
		StatoUtente vecchioStato = utenteNuovo.getStato();
		utenteNuovo.setStato(StatoUtente.ATTIVO);
		utenteServiceInstance.aggiorna(utenteNuovo);

		if (utenteNuovo.getStato().equals(vecchioStato))
			throw new RuntimeException("testModificaStatoUtente fallito: modifica non avvenuta correttamente ");

		System.out.println(".......testModificaStatoUtente fine: PASSED.............");
	}

	private static void testRimuoviRuoloDaUtente(RuoloService ruoloServiceInstance, UtenteService utenteServiceInstance)
			throws Exception {
		System.out.println(".......testRimuoviRuoloDaUtente inizio.............");

		// carico un ruolo e lo associo ad un nuovo utente
		Ruolo ruoloEsistenteSuDb = ruoloServiceInstance.cercaPerDescrizioneECodice("Administrator", "ROLE_ADMIN");
		if (ruoloEsistenteSuDb == null)
			throw new RuntimeException("testRimuoviRuoloDaUtente fallito: ruolo inesistente ");

		// mi creo un utente inserendolo direttamente su db
		Utente utenteNuovo = new Utente("aldo.manuzzi", "pwd@2", "aldo", "manuzzi", new Date());
		utenteServiceInstance.inserisciNuovo(utenteNuovo);
		if (utenteNuovo.getId() == null)
			throw new RuntimeException("testRimuoviRuoloDaUtente fallito: utente non inserito ");
		utenteServiceInstance.aggiungiRuolo(utenteNuovo, ruoloEsistenteSuDb);

		// ora ricarico il record e provo a disassociare il ruolo
		Utente utenteReloaded = utenteServiceInstance.caricaUtenteSingoloConRuoli(utenteNuovo.getId());
		boolean confermoRuoloPresente = false;
		for (Ruolo ruoloItem : utenteReloaded.getRuoli()) {
			if (ruoloItem.getCodice().equals(ruoloEsistenteSuDb.getCodice())) {
				confermoRuoloPresente = true;
				break;
			}
		}

		if (!confermoRuoloPresente)
			throw new RuntimeException("testRimuoviRuoloDaUtente fallito: utente e ruolo non associati ");

		// ora provo la rimozione vera e propria ma poi forzo il caricamento per fare un confronto 'pulito'
		utenteServiceInstance.rimuoviRuoloDaUtente(utenteReloaded.getId(), ruoloEsistenteSuDb.getId());
		utenteReloaded = utenteServiceInstance.caricaUtenteSingoloConRuoli(utenteNuovo.getId());
		if (!utenteReloaded.getRuoli().isEmpty())
			throw new RuntimeException("testRimuoviRuoloDaUtente fallito: ruolo ancora associato ");

		System.out.println(".......testRimuoviRuoloDaUtente fine: PASSED.............");
	}
	
	public static void testListAll(RuoloService ruoloService) throws Exception{
		System.out.println(".......testListAll inizio: .............");
		
		if(ruoloService.listAll().size() == 0) 
			throw new RuntimeException("Non ci sono ruoli all'interno del DB");
		
		List<Ruolo> listaRuoli = ruoloService.listAll();
		for (Ruolo ruoloItem : listaRuoli) {
			System.out.println(ruoloItem.getId()+", "+ruoloItem.getDescrizione()+", "+ruoloItem.getCodice());
		}
		
		System.out.println(".......testListAll fine: PASSED.............");
	}
	
	public static void testAggiorna(RuoloService ruoloService) throws Exception{
		System.out.println(".......testAggiorna inizio: .............");
		
		if(ruoloService.listAll().size() == 0) 
			throw new RuntimeException("Non ci sono ruoli all'interno del DB");
		
		Long idRuoloDaModificare = ruoloService.listAll().get(2).getId();
		Ruolo ruoloAggiornato = new Ruolo(idRuoloDaModificare, "Administrator", "CICCIPUZZI");
		
		ruoloService.aggiorna(ruoloAggiornato);
		
		System.out.println(".......testAggiorna fine: PASSED .............");
	}
	
	public static void testRimuoviRuolo(RuoloService ruoloService) throws Exception{
		System.out.println(".......testAggiorna inizio: .............");
		
		if(ruoloService.listAll().size() == 0) 
			throw new RuntimeException("Non ci sono ruoli all'interno del DB");
		
		Long idRuoloDaEliminare = 3L;
		ruoloService.rimuovi(idRuoloDaEliminare);
		
		System.out.println(".......testAggiorna fine: PASSED.............");
	}
	
	public static void testRimuoviUtente(UtenteService utenteService, RuoloService ruoloService) throws Exception{
		System.out.println(".......testRimuoviUtente inizio: .............");
		
		if(utenteService.listAll().size() == 0)
			throw new RuntimeException("Non ci sono Utenti nel DB");
		
		if(ruoloService.listAll().size() == 0) 
			throw new RuntimeException("Non ci sono ruoli all'interno del DB");
		
		Long idUtenteDaEliminare = 2L;
		utenteService.rimuovi(idUtenteDaEliminare);
		
		System.out.println(".......testRimuoviUtente fine: PASSED.............");
	}
	
	public static void testListaDiTutteLeDescrizioniDegliUtentiAssociati(RuoloService ruoloService) throws Exception{
		System.out.println(".......testListaDiTutteLeDescrizioniDegliUtentiAssociati inizio: .............");
		
		if(ruoloService.listAll().size() == 0) 
			throw new RuntimeException("Non ci sono ruoli all'interno del DB");
		
		//List<String> listaRuoli = null;
		/*for (String ruoloItem : listaRuoli) {
			System.out.println(ruoloItem);
		}*/
		
		
		System.out.println(".......testListaDiTutteLeDescrizioniDegliUtentiAssociati fine: PASSED.............");
	}
	
	public static void testTrovaTuttiGliUtentiRegistratiAGiugno(UtenteService utenteService) throws Exception{
		System.out.println(".......testTrovaTuttiGliUtentiRegistratiAGiugno inizio: .............");
		
		if(utenteService.listAll().size() == 0)
			throw new RuntimeException("Non ci sono Utenti nel DB");
		
		List<Utente> listaUtente = utenteService.trovaTuttiGliUtentiRegistratiAGiugno();
		for (Utente utenteItem : listaUtente) {
			System.out.println(utenteItem.getId());
		}
		
		System.out.println(".......testTrovaTuttiGliUtentiRegistratiAGiugno fine: PASSED.............");
	}

}
