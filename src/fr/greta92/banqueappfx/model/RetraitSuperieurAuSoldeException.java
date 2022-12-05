package fr.greta92.banqueappfx.model;

public class RetraitSuperieurAuSoldeException extends RuntimeException {

	public RetraitSuperieurAuSoldeException() {
		super("Retrait supérieur au Solde");
	}
}
