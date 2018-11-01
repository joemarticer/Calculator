package Calculator;

public class EingabefehlerException extends Exception {

	public EingabefehlerException() {
		super("Die Eingabe ist nicht korrekt!");
	}
}