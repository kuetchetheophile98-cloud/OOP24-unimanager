package it.unibo.unimanager.view;

import it.unibo.unimanager.controller.UniManagerController;
import it.unibo.unimanager.model.Appello;
import it.unibo.unimanager.model.Corso;
import it.unibo.unimanager.model.DominioException;
import it.unibo.unimanager.model.Studente;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

/** Scheda per creare appelli, iscrivere studenti e consultare gli appelli. */
final class AppelliPanel extends JPanel implements Aggiornabile {

    private static final long serialVersionUID = 1L;

    private final transient UniManagerController controller;
    private final JComboBox<Corso> corsi = new JComboBox<>();
    private final JTextField data = new JTextField(LocalDate.now().toString(), 10);
    private final JTextField luogo = new JTextField(15);
    private final JComboBox<Appello> appelli = new JComboBox<>();
    private final JComboBox<Studente> studenti = new JComboBox<>();
    private final TabellaDati tabella = new TabellaDati("ID", "Corso", "Data", "Luogo", "Iscritti");

    AppelliPanel(final UniManagerController controller) {
        super(new BorderLayout(8, 8));
        this.controller = controller;
        setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        final Form nuovo = new Form("Nuovo appello");
        nuovo.aggiungi("Corso", corsi);
        nuovo.aggiungi("Data (AAAA-MM-GG)", data);
        nuovo.aggiungi("Luogo", luogo);
        final JButton crea = new JButton("Crea appello");
        crea.addActionListener(e -> creaAppello());
        nuovo.aggiungi(crea);

        final Form iscrizione = new Form("Iscrizione a un appello");
        iscrizione.aggiungi("Appello", appelli);
        iscrizione.aggiungi("Studente", studenti);
        final JButton iscrivi = new JButton("Iscrivi studente");
        iscrivi.addActionListener(e -> iscriviStudente());
        iscrizione.aggiungi(iscrivi);

        final JPanel form = new JPanel(new GridLayout(1, 2, 8, 8));
        form.add(nuovo);
        form.add(iscrizione);

        add(form, BorderLayout.NORTH);
        add(new JScrollPane(tabella), BorderLayout.CENTER);
        aggiorna();
    }

    private void creaAppello() {
        final Corso corso = (Corso) corsi.getSelectedItem();
        if (corso == null) {
            UtiliGui.errore(this, "Crea prima almeno un corso.");
            return;
        }
        try {
            controller.creaAppello(corso.getId(), data.getText(), luogo.getText());
            luogo.setText("");
        } catch (DominioException ex) {
            UtiliGui.errore(this, ex.getMessage());
        }
    }

    private void iscriviStudente() {
        final Appello appello = (Appello) appelli.getSelectedItem();
        final Studente studente = (Studente) studenti.getSelectedItem();
        if (appello == null || studente == null) {
            UtiliGui.errore(this, "Servono almeno un appello e uno studente.");
            return;
        }
        try {
            controller.iscrivi(studente.getId(), appello.getId());
        } catch (DominioException ex) {
            UtiliGui.errore(this, ex.getMessage());
        }
    }

    @Override
    public void aggiorna() {
        UtiliGui.riempi(corsi, controller.corsi());
        UtiliGui.riempi(appelli, controller.appelli());
        UtiliGui.riempi(studenti, controller.studenti());
        final List<Object[]> righe = new ArrayList<>();
        for (final Appello a : controller.appelli()) {
            righe.add(new Object[] {
                a.getId(), a.getCorso().getNome(), a.getData(), a.getLuogo(), a.getIscritti().size()
            });
        }
        tabella.setRighe(righe);
    }
}
