package it.unibo.unimanager.view;

import java.awt.Component;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

/** Funzioni di supporto condivise dai pannelli della vista. */
final class UtiliGui {

    private UtiliGui() {
        // classe di utilità: non istanziabile
    }

    /**
     * Mostra un messaggio d'errore.
     *
     * @param genitore componente da cui centrare la finestra
     * @param messaggio testo da mostrare
     */
    static void errore(final Component genitore, final String messaggio) {
        JOptionPane.showMessageDialog(genitore, messaggio, "Operazione non riuscita", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Sostituisce il contenuto di una casella a discesa mantenendo, se possibile, la selezione.
     *
     * @param <T> tipo degli elementi
     * @param casella la casella da aggiornare
     * @param elementi i nuovi elementi
     */
    static <T> void riempi(final JComboBox<T> casella, final List<T> elementi) {
        final Object selezionato = casella.getSelectedItem();
        casella.removeAllItems();
        for (final T elemento : elementi) {
            casella.addItem(elemento);
        }
        if (selezionato != null && elementi.contains(selezionato)) {
            casella.setSelectedItem(selezionato);
        }
    }
}
