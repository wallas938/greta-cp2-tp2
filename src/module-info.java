module banqueappFX {
	requires javafx.controls;
	requires javafx.fxml;

	opens fr.greta92.banqueappfx to javafx.graphics, javafx.fxml;
}
