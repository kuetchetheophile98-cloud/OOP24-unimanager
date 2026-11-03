package it.unibo.unimanager.view;

import it.unibo.unimanager.controller.UniManagerController;
import it.unibo.unimanager.model.Corso;
import it.unibo.unimanager.model.DominioException;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

/** Scheda per inserire corsi e consultarne l'elenco. */
final class CorsiPanel extends JPanel implements Aggiornabile {

    private static final long serialVersionUID = 1L;
    private static final int CFU_PREDEFINITI = 6;

    private final transient UniManagerController controller;
    private final JTextField nome = new JTextField(20);
    private final JSpinner cfu = new JSpinner(
            new SpinnerNumberModel(CFU_PREDEFINITI, Corso.CFU_MIN, Corso.CFU_MAX, 1));
    private final TabellaDati tabella = new TabellaDati("ID", "Corso", "CFU");

    CorsiPanel(final UniManagerController controller) {
        super(new BorderLayout(8, 8));
        this.controller = controller;
        setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        final Form form = new Form("Nuovo corso");
        form.aggiungi("Nome", nome);
        form.aggiungi("CFU", cfu);
        final JButton aggiungi = new JButton("Aggiungi corso");
        aggiungi.addActionListener(e -> aggiungiCorso());
        form.aggiungi(aggiungi);

        add(form, BorderLayout.NORTH);
        add(new JScrollPane(tabella), BorderLayout.CENTER);
        aggiorna();
    }

    private void aggiungiCorso() {
        try {
            controller.aggiungiCorso(nome.getText(), (Integer) cfu.getValue());
            nome.setText("");
        } catch (DominioException ex) {
            UtiliGui.errore(this, ex.getMessage());
        }
    }

    @Override
    public void aggiorna() {
        final List<Object[]> righe = new ArrayList<>();
        for (final Corso c : controller.corsi()) {
            righe.add(new Object[] {c.getId(), c.getNome(), c.getCfu()});
        }
        tabella.setRighe(righe);
    }
}
