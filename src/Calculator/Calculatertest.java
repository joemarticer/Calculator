package Calculator;

import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Test;

public class Calculatertest {

	@Test
	public void values() throws MathematischerFehlerException, EingabefehlerException {
		FileReader fr;
		try {
			fr = new FileReader("Testbeispiele.txt");
			BufferedReader br = new BufferedReader(fr);
			String zeile = br.readLine();
			while (zeile != null) {
				System.out.println("Starte Programm mit: " + zeile);
				Calculatorsub cs = new Calculatorsub();

				Double ergebnis = cs.readString(zeile);
				System.out.println("Ergebnis: " + ergebnis);

				assertTrue(ergebnis == Double.parseDouble(br.readLine()));

				zeile = br.readLine();
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}