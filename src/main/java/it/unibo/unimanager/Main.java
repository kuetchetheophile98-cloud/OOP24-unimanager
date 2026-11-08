    package it.unibo.unimanager;

       import javax.swing.SwingUtilities ;
     import javax.swing.UIManager;
    import javax.swing.UnsupportedLookAndFeelException;

        import it.unibo.unimanager.controller.UniManagerController;
       import   it.unibo.unimanager.model.Ateneo;
import  it.unibo.unimanager.view.MainFrame ;

/** Punto di ingresso dell'applicazione: collega modello, controller e vista. */
public final class Main {

    private Main() {
    }

    
       public static void main(final String[] args) {
           SwingUtilities.invokeLater(() -> {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()) ;
} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                    | UnsupportedLookAndFeelException e) {
                // si prosegue con l'aspetto predefinito di Swing
     }
final UniManagerController controller = new UniManagerController(new  Ateneo());
            new  MainFrame(controller).setVisible(true);
  }  );
     }
} 
