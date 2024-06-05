module fournisseur {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
	requires java.desktop;
	requires javafx.graphics;
	  
    exports application.controller;

    // Ouvrir tous les packages pour permettre l'accès à reflection (peut être moins sécurisé)
    opens application;
    opens application.controller;
    opens application.model;
    opens application.view;

    // Autres directives
  
    
}