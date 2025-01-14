package com.mycompany.rubrica_gruppo12;

import com.mycompany.exception.DuplicatiException;
import com.mycompany.exception.NomeECognomeMancanteException;
import com.mycompany.exception.NumeroTelefonoNonValidoException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author LELE
 */
public class PrimaryController implements Initializable {

    @FXML
    private TextField txtNome;
    @FXML
    private TextField txtCognome;
    @FXML
    private TextField txtNumTelefono;
    @FXML
    private TextField txtEmail;
    @FXML
    private Button btnAggiungi;
    @FXML
    private TextField txtCercaContatto;
    @FXML
    private Button btnCerca;
    @FXML
    private TableView<Contatto> tblContatti;
    @FXML
    private TableColumn<Contatto, String > clmCognome;
    @FXML
    private TableColumn<Contatto, String> clmNome;
    
    private FilteredList<Contatto> filteredContacts;
    private ObservableList<Contatto> contacts; 
    
    @FXML
    private TextField txtNumTelefono1;
    @FXML
    private TextField txtNome1;
    @FXML
    private TextField txtCognome1;
    @FXML
    private TextField txtEmail1;
    @FXML
    private TextField txtNumTelefono2;
    @FXML
    private TextField txtEmail2;
    @FXML
    private TextField txtNumTelefono3;
    @FXML
    private TextField txtEmail3;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        contacts = FXCollections.observableArrayList(); //istanzio una array list osservabile
        filteredContacts = new FilteredList<>(contacts, p->true);
        
        tblContatti.setItems(filteredContacts);

        clmNome.setCellValueFactory(s -> { return new SimpleStringProperty(s.getValue().getNome());});
        clmCognome.setCellValueFactory(s -> { return new SimpleStringProperty(s.getValue().getCognome());});
      // Assegna un listener per la selezione nella tabella
    tblContatti.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
        if (newSelection != null) {
            try {
                apriDettagliContatto(newSelection);
            } catch (IOException ex) {
                
            }
        }
    });
       controllaInput();
    }   
    
    
    
    private void controllaInput() {
    // DISABILITAZIONE DEL BOTTONE QUANDO I CAMPI OBBLIGATORI NON SONO INSERITI
    BooleanBinding nomeCognomeMancanti = Bindings.createBooleanBinding(
        () -> txtNome.getText().isEmpty() && txtCognome.getText().isEmpty(),
        txtNome.textProperty(),
        txtCognome.textProperty()
    );

    // DISABILITAZIONE DEL BOTTONE QUANDO IL CAMPO EMAIL CONTIENE PIÙ DI DUE VIRGOLE
    BooleanBinding troppeVirgoleEmail = Bindings.createBooleanBinding(
        () -> contaVirgole(txtEmail.getText()) >= 3,
        txtEmail.textProperty()
    );

    // DISABILITAZIONE DEL BOTTONE QUANDO IL CAMPO NUMERI DI TELEFONO CONTIENE PIÙ DI DUE VIRGOLE
    BooleanBinding troppeVirgoleNumeri = Bindings.createBooleanBinding(
        () -> contaVirgole(txtNumTelefono.getText()) >= 3,
        txtNumTelefono.textProperty()
    );

    // COMBINAZIONE DEI BINDING
    btnAggiungi.disableProperty().bind(
        nomeCognomeMancanti.or(troppeVirgoleEmail).or(troppeVirgoleNumeri)
    );
}

// Metodo per contare le virgole in una stringa
private int contaVirgole(String text) {
    if (text == null || text.isEmpty()) {
        return 0;
    }
    return text.length() - text.replace(",", "").length();
}

    @FXML
private void aggiungiContatto(javafx.event.ActionEvent event) throws NomeECognomeMancanteException, NumeroTelefonoNonValidoException, DuplicatiException {
    Email email = new Email();
    if (!txtEmail.getText().isEmpty()) {
        String[] emailArray = txtEmail.getText().split(","); // Email separate da virgola
        for (String mail : emailArray) {
            email.aggiungiEmail(mail.trim());
        }
    }

    NumTelefono numTelefono = new NumTelefono();
    if (!txtNumTelefono.getText().isEmpty()) {
        String[] numeriArray = txtNumTelefono.getText().split(","); // Numeri separati da virgola
        for (String numero : numeriArray) {
            numTelefono.aggiungiNumTelefono(numero.trim());
        }
    }

    String nome = txtNome.getText().isEmpty() ? "<vuoto>" : txtNome.getText();
    String cognome = txtCognome.getText().isEmpty() ? "<vuoto>" : txtCognome.getText();

    Contatto contatto = new Contatto(nome, cognome, email, numTelefono);
    contacts.add(contatto);
    contacts.sort(new ContattoCompare());
    tblContatti.refresh(); // Aggiorna la tabella
}
    @FXML
    private void cercaContatto(javafx.event.ActionEvent event) {
    String testoRicerca = txtCercaContatto.getText().toLowerCase();

    // Filtra i contatti in base al nome o cognome
    filteredContacts.setPredicate(contatto -> {
        if (testoRicerca == null || testoRicerca.isEmpty()) {
            return true; // Mostra tutti i contatti
        }
        // Filtra per nome o cognome
        return contatto.getNome().toLowerCase().contains(testoRicerca) || 
               contatto.getCognome().toLowerCase().contains(testoRicerca);
    });

    if (filteredContacts.isEmpty()) {
        System.out.println("Nessun contatto trovato.");
    } else {
        System.out.println("Contatti trovati: " + filteredContacts.size());
    }
}

    
    @FXML
    private void eliminaContatto(javafx.event.ActionEvent event) {
        
        //Ottieni lo studente selezionato
        Contatto selectedContatto = tblContatti.getSelectionModel().getSelectedItem();
        
        //Controllo se lo studente selezionato è presente
        if ( selectedContatto != null){
            //rimozione studente dalla collezione
            contacts.remove(selectedContatto);
        } else {
            System.out.println("Nessun Contatto Selezionato");
        }
    }

    @FXML
    private void importaRubrica(javafx.event.ActionEvent event) throws NumeroTelefonoNonValidoException, DuplicatiException, NomeECognomeMancanteException {
        
        contacts.removeAll(contacts);
        
        FileChooser fc = new FileChooser();
        File f = fc.showOpenDialog(new Stage());
        
        if(f == null) return; 
        
        try(Scanner scan = new Scanner(new BufferedReader(new FileReader(f)))){
          scan.useDelimiter("[;\n]");
          
          while(scan.hasNext()){
            // Legge i dati base del contatto  
            String nome = scan.next();
            String cognome = scan.next();
            String emailString = scan.next();
            String numeroString = scan.next();
            // Crea l'oggetto Email e aggiunge l'indirizzo letto
            Email email = new Email();
            for (String mail : emailString.split(",")) {
              email.aggiungiEmail(mail.trim());
            }
            // Crea l'oggetto NumTelefono e aggiunge il numero letto
             NumTelefono numero = new NumTelefono();
             for (String numeroTelefono : numeroString.split(",")) {
            numero.aggiungiNumTelefono(numeroTelefono.trim());
            }
              
            // Aggiunge il nuovo contatto alla lista
            contacts.add(new Contatto(nome, cognome, email, numero));}         
        } catch(IOException e){
            System.out.println("Errore! Impossibile caricare il file!"+ e.getMessage());
        }
    }
    
    
    @FXML
    private void esportaRubrica(javafx.event.ActionEvent event) {
        
        FileChooser fc = new FileChooser();
        
        File f = fc.showSaveDialog(txtNome.getParent().getScene().getWindow());
        
        if(f == null) return; 
        
        try( PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(f))) ) {
            for(Contatto con : contacts){
                  String emailString = String.join(",", con.getMail().getMail());
                  String numeroString = String.join(",", con.getNumeri().getNumeri());
                  pw.println(con.getNome() + ";" + con.getCognome() + ";" + emailString + ";" + numeroString);
                  System.out.println("File Esportato correttamente");
            }
        } catch (IOException e){
            System.err.println("Errore! Impossibile salvare il file!"+ e.getMessage());

        }
       
        
    }

    private void apriDettagliContatto(Contatto contatto) throws IOException{
         
     // Pulisce i campi per evitare dati residui
    txtNumTelefono1.clear();
    txtNumTelefono2.clear();
    txtNumTelefono3.clear();
    txtEmail1.clear();
    txtEmail2.clear();
    txtEmail3.clear();

    // Gestione dei numeri di telefono
    int telefonoIndex = 0;
    for (String numero : contatto.getNumeri().getNumeri()) {
        switch (telefonoIndex) {
            case 0:
                txtNumTelefono1.setText(numero);
                break;
            case 1:
                txtNumTelefono2.setText(numero);
                break;
            case 2:
                txtNumTelefono3.setText(numero);
                break;
            default:
                break; // Interrompe per valori oltre il necessario
        }
        telefonoIndex++;
        if (telefonoIndex >= 3) break; // Interrompe dopo aver riempito i campi
    }

    // Gestione delle email
    int emailIndex = 0;
    for (String email : contatto.getMail().getMail()) {
        switch (emailIndex) {
            case 0:
                txtEmail1.setText(email);
                break;
            case 1:
                txtEmail2.setText(email);
                break;
            case 2:
                txtEmail3.setText(email);
                break;
            default:
                break; // Interrompe per valori oltre il necessario
        }
        emailIndex++;
        if (emailIndex >= 3) break; // Interrompe dopo aver riempito i campi
    }

    // Nome e cognome
    txtNome1.setText(contatto.getNome());
    txtCognome1.setText(contatto.getCognome());
    }
    
    public void setContatti(ObservableList<Contatto> contatti) {
    if (contatti == null) {
        System.err.println("Errore: la lista dei contatti è null.");
        return;
    }
    this.contacts = contatti;

    // Reinizializza la FilteredList solo se necessario
    if (filteredContacts == null) {
        filteredContacts = new FilteredList<>(contacts, p -> true);
        tblContatti.setItems(filteredContacts); // Aggiorna la TableView
    } else {
        filteredContacts = new FilteredList<>(contacts, p -> true);
        }
    }
    
    // Metodo per aggiornare il contatto
    public void updateContatto(Contatto contattoAggiornato) {
        // Trova il contatto nella lista
        for (int i = 0; i < contacts.size(); i++) {
            Contatto contatto = contacts.get(i);
            if (contatto.getCognome().equals(contattoAggiornato.getCognome())) { // Supponiamo che ogni contatto abbia un ID unico
                // Sostituisci il contatto esistente con quello aggiornato
                contacts.set(i, contattoAggiornato);
                break;
            }
        }
        tblContatti.refresh();
    }

    @FXML
    private void modificaContatto(javafx.event.ActionEvent event) throws NomeECognomeMancanteException, NumeroTelefonoNonValidoException, DuplicatiException {
         // Recupera il contatto selezionato
    Contatto contattoSelezionato = tblContatti.getSelectionModel().getSelectedItem();
    if (contattoSelezionato == null) {
        System.out.println("Nessun contatto selezionato!");
        return;
    }

    // Recupera i dati modificati dai campi di testo
    String nuovoNome = txtNome1.getText();
    String nuovoCognome = txtCognome1.getText();
    String nuoviNumeri = String.join(",", txtNumTelefono1.getText(), txtNumTelefono2.getText(), txtNumTelefono3.getText());
    String nuoveEmail = String.join(",", txtEmail1.getText(), txtEmail2.getText(), txtEmail3.getText());

    // Aggiorna il contatto
    Email emailAggiornata = new Email();
    for (String mail : nuoveEmail.split(",")) {
        emailAggiornata.aggiungiEmail(mail.trim());
    }

    NumTelefono numeriAggiornati = new NumTelefono();
    for (String numero : nuoviNumeri.split(",")) {
        numeriAggiornati.aggiungiNumTelefono(numero.trim());
    }

    Contatto contattoAggiornato = new Contatto(nuovoNome, nuovoCognome, emailAggiornata, numeriAggiornati);

    // Aggiorna il contatto nella lista
    updateContatto(contattoAggiornato);
}
 
}
