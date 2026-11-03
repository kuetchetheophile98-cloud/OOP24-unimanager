package it.unibo.unimanager.view;

import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/** Tabella a sola lettura, riempita con righe di valori già pronti per la visualizzazione. */
final class TabellaDati extends JTable {

    private static final long serialVersionUID = 1L;

    private final DefaultTableModel modello;

    /**
     * @param colonne intestazioni delle colonne
     */
    TabellaDati(final String... colonne) {
        super(new DefaultTableModel(colonne, 0) {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(final int riga, final int colonna) {
                return false;
            }
        });
        this.modello = (DefaultTableModel) getModel();
        setFillsViewportHeight(true);
        setRowHeight(24);
    }

    /**
     * Sostituisce tutte le righe della tabella.
     *
     * @param righe le nuove righe, una per elemento
     */
    void setRighe(final List<Object[]> righe) {
        modello.setRowCount(0);
        for (final Object[] riga : righe) {
            modello.addRow(riga);
        }
    }
}
