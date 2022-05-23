package it.prova.manytomanyjpamaven.exception;

public class UtentiConRuoliAssociatiException extends Exception{
	private String message;
	
	public UtentiConRuoliAssociatiException(String message) {
		super(message);
	}
}
