package it.unibo.unimanager.view;

import it.unibo.unimanager.controller.UniManagerController;
import java.awt.Dimension;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;

/** Finestra principale: una scheda per ciascuna area di gestione dell'ateneo. */
public final class MainFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private static final Dimension DIMENSIONE_MINIMA = new Dimension(900, 560);

    /**
     * @param controller il controller a cui la vista delega ogni operazione
     */
    public MainFrame(final UniManagerController controller) {
        super("UniManager - Gestione esami d'ateneo");

        final StudentiPanel studenti = new StudentiPanel(controller);
        final CorsiPanel corsi = new CorsiPanel(controller);
        final AppelliPanel appelli = new AppelliPanel(controller);
        final VotiPanel voti = new VotiPanel(controller);

        final JTabbedPane schede = new JTabbedPane();
        schede.addTab("Studenti", studenti);
        schede.addTab("Corsi", corsi);
        schede.addTab("Appelli", appelli);
        schede.addTab("Voti e medie", voti);
        add(schede);

        final List<Aggiornabile> pannelli = List.of(studenti, corsi, appelli, voti);
        controller.aggiungiOsservatore(() -> pannelli.forEach(Aggiornabile::aggiorna));

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(DIMENSIONE_MINIMA);
        setSize(DIMENSIONE_MINIMA);
        setLocationRelativeTo(null);
    }
}
