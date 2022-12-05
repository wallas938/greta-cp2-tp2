package fr.greta92.banqueappfx.model;

public class SoldeInsuffisantException extends RuntimeException {
	public SoldeInsuffisantException() {
		super("Solde insuffisant");
	}

}
