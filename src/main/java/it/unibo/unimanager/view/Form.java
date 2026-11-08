package it.unibo.unimanager.view;


 import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import  javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
 import javax.swing.JLabel;
import javax.swing.JPanel ;

/** Pannello con titolo che dispone coppie etichetta/campo su righe successive. */
final class Form extends JPanel {

    private static final long serialVersionUID = 1L;
     private static final Insets MARGINI = new Insets(4, 6, 4, 6  )  ;

private int riga;

    
    Form(final String titolo) {
        super(new GridBagLayout());
        setBorder(BorderFactory.createTitledBorder(titolo));
    }

       void aggiungi(final String etichetta, final JComponent campo) {
            final GridBagConstraints vincoli = new GridBagConstraints();
              vincoli.gridy = riga++;
        vincoli.insets =       MARGINI;
        vincoli.anchor   = GridBagConstraints.WEST  ;
        vincoli.gridx = 0;
             add(new JLabel(etichetta), vincoli);
        vincoli.gridx = 1;
    vincoli.weightx = 1;
        vincoli.fill  = GridBagConstraints.HORIZONTAL   ;
        add(campo,    vincoli);
             }

    
  void aggiungi(final JButton pulsante) {
           final GridBagConstraints vincoli = new GridBagConstraints();
     vincoli.gridy =    riga++;
        vincoli.gridx   = 1;
          vincoli.insets = MARGINI;
        vincoli.anchor =    GridBagConstraints.EAST;
           add(pulsante, vincoli  ) ;

    }

}
