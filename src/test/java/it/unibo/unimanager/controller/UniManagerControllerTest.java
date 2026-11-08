package it.unibo.unimanager.controller;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import it.unibo.unimanager.model.Ateneo;
import   it.unibo.unimanager.model.Corso   ;
import it.unibo.unimanager.model.DominioException;
import it.unibo.unimanager.model.Studente;

    class UniManagerControllerTest{

     private UniManagerController controller;
        private Corso corso;

    @BeforeEach
    void preparaController (){
     controller = new UniManagerController(new Ateneo());
    corso = controller.aggiungiCorso("Programmazione a oggetti", 6);
    }
@Test
void creaUnAppelloLeggendoLaDataComeTesto() {
 assertEquals(LocalDate.of(2026, 7, 1),
controller.creaAppello(corso.getId(), " 2026-07-01 ", "Aula 2.1").getData());

    }
   @Test
     void unaDataNonLeggibileVieneRifiutata(){
    assertThrows(DominioException.class,()-> controller.creaAppello(corso.getId(), "1 luglio", "Aula"));
    assertThrows(DominioException.class, () -> controller.creaAppello(corso.getId(),null, "Aula"));
    assertEquals(0, controller.appelli().size());
       }

 @Test
    void  ilFlussoCompletoProduceLeMedieAttese(){
     final Studente studente = controller.aggiungiStudente("Mario", "Rossi", "0001");
    final int appello = controller.creaAppello(corso.getId(), "2026-07-01", "Aula").getId();
           controller.iscrivi(studente.getId(), appello);
     controller.registraVoto(studente.getId(), appello, 27,false);

    assertEquals(27.0, controller.mediaAritmetica(studente.getId()).getAsDouble(), 1e-9);
assertEquals(27.0, controller.mediaPonderata(studente.getId()).getAsDouble(), 1e-9);
    assertEquals(27.0, controller.mediaCorso(corso.getId()).getAsDouble(), 1e-9);
assertEquals(1, controller.voti().size());
     }


    @Test
    void gliOsservatoriRegistratiTramiteControllerVengonoNotificati()   {
           final int[] notifiche = {0};
    controller.aggiungiOsservatore(() -> notifiche[0]++);
          controller.aggiungiStudente("Anna", "Bianchi", "0002");
     assertEquals(1, notifiche[0]);
       }
   }
