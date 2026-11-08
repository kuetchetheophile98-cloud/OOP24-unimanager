 package it.unibo.unimanager.view;

   import java.awt.Component;
   import java.util.List;
 import java.util.Objects;

import javax.swing.JComboBox;
   import javax.swing.JOptionPane;  

/* Funzioni  di   supporto condivise dai  pannelli della vista. */
    final class UtiliGui {
private UtiliGui()  {

}
        

         static void errore(final Component genitore, final String messaggio) {
        JOptionPane.showMessageDialog(genitore, messaggio, "Operazione non riuscita", JOptionPane.ERROR_MESSAGE);
    }

    static <T> void riempi(final JComboBox<T> casella, final List<T> elementi) {
        final Object selezionato = casella.getSelectedItem();
               casella.removeAllItems() ;
        for (final  T elemento : elementi) {
                  casella.addItem(elemento);
     }
    
   if (selezionato != null && elementi.contains(selezionato)) {
            casella.setSelectedItem(selezionato);   

              } 
            }
        }