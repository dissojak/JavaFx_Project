module LocationVoitureProject {
	 	requires javafx.controls;
	    requires javafx.fxml;
		requires java.logging;
		requires java.sql;
		exports gestion.location.controller;
		
		opens gestion.location to javafx.graphics, javafx.fxml;
		opens gestion.location.controller to javafx.fxml;
		
	}
