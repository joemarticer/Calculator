package Calculator;

import java.math.RoundingMode;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class CalculatorController implements Initializable {
	Calculatorsub cs = new Calculatorsub();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println("Start Calculator");
	}

	@FXML
	public TextField texto;
	@FXML
	public TextField erg;
	@FXML
	public Pane popup;
	@FXML
	public Label pop;

	@FXML
	public void buttonClick(ActionEvent event) {
		Button button = (Button) event.getSource();
		texto.setText(texto.getText() + button.getText());
	}

	@FXML
	public void löschen(ActionEvent event) {
		texto.clear();
		erg.clear();
	}

	@FXML
	public void okClick(ActionEvent event) {
		popup.setVisible(false);
	}

	@FXML
	public void rechner(ActionEvent event) {
		try {
			NumberFormat format = DecimalFormat.getInstance();
			format.setRoundingMode(RoundingMode.FLOOR);
			format.setMinimumFractionDigits(0);
			erg.setText(format.format(cs.readString(texto.getText())));
		} catch (MathematischerFehlerException | EingabefehlerException e) {
			popup.setVisible(true);
			pop.setText(e.getMessage());
			popup.setStyle("-fx-background-color: #F5F5F5 ; -fx-border-style: solid; -fx-border-color: #039ED3");
		}
	}
}
