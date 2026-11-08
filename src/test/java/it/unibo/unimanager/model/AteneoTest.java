package it.unibo.unimanager.model;

  import static org.junit.jupiter.api.Assertions.assertEquals;
   import static org.junit.jupiter.api.Assertions.assertFalse;
      import static org.junit.jupiter.api.Assertions.assertThrows;
     import static org.junit.jupiter.api.Assertions.assertTrue;

    import java.time.LocalDate;
  import java.util.OptionalDouble;
     import org.junit.jupiter.api.BeforeEach;
   import org.junit.jupiter.api.Test;

  class AteneoTest {

    private static final LocalDate DATA = LocalDate.of(2026, 6, 15);
    private static final double TOLLERANZA = 1e-9;

      private Ateneo ateneo;
    private Studente mario;
      private Studente anna;
    private Corso oop;
      private Corso  analisi;
    private Appello appelloOop;
       private Appello   appelloAnalisi;

       @BeforeEach
        void preparaAteneo() {
 ateneo = new Ateneo();
  mario = ateneo.aggiungiStudente("Mario", "Rossi", "0001");
            anna = ateneo.aggiungiStudente("Anna", "Bianchi", "0002");
        oop=    ateneo.aggiungiCorso("Programmazione a    oggetti", 6);
        analisi = ateneo.aggiungiCorso("Analisi", 12);
              appelloOop = ateneo.creaAppello(oop.getId(  ), DATA, "Aula 2.1");
    appelloAnalisi = ateneo.creaAppello(analisi.getId(), DATA, "Aula 3.4");
            for (final Studente s : new Studente[] {mario, anna}) {
     ateneo.iscriviStudente(s.getId(), appelloOop.getId());
             ateneo.iscriviStudente(s.getId(), appelloAnalisi.getId());
}
     }

        @Test
    void gliIdentificativiSonoProgressivi() {
     assertEquals(1, mario.getId());
assertEquals(2, anna.getId());
          assertEquals(2, ateneo.getStudenti().size());
      }

    @Test
           void matricolaDuplicataVieneRifiutata() {
 assertThrows(MatricolaDuplicataException.class,
               () -> ateneo.aggiungiStudente("Luca", "Verdi", "0001"));
  assertEquals(2, ateneo.getStudenti().size());
         }



    @Test
              void laMatricolaRifiutataNonConsumaUnIdentificativo() {
 assertThrows(MatricolaDuplicataException.class,
                () -> ateneo.aggiungiStudente("Luca", "Verdi", "0001"));
 assertEquals(3, ateneo.aggiungiStudente("Luca", "Verdi", "0003").getId());
        }

    @Test
 void datiVuotiVengonoRifiutati() {
     assertThrows(  DominioException.class, () -> ateneo.aggiungiStudente ("  ", "Verdi", "0009"));
 assertThrows(DominioException.class, ()    -> ateneo.aggiungiCorso("", 6));
        assertThrows(DominioException.class, () -> ateneo.aggiungiCorso("Fisica", 0));
 assertThrows(DominioException.class, ()    -> ateneo.creaAppello(oop.getId(), DATA, " "));
    }
 @Test
    void    creareUnAppelloPerUnCorsoInesistenteFallisce()  {
assertThrows(EntitaNonTrovataException.class, () -> ateneo.creaAppello(99, DATA, "Aula"));
    }

       @Test
 void    iscrizioneDoppiaVieneRifiutata()  {
assertThrows(  IscrizioneNonValidaException.class,
    () ->   ateneo.iscriviStudente(mario.getId(), appelloOop.getId()));
  assertEquals(2, appelloOop.getIscritti().size());
        }

    @Test
    void registraUnVotoValido() {
        final Voto voto = ateneo.registraVoto(mario.getId(), appelloOop.getId(), 28, false);
        assertEquals(28, voto.getValore());
        assertEquals("28", voto.getEtichetta());
        assertEquals(1, ateneo.getVoti().size());
    }

    @Test
    void ilVotoDeveEssereTra18E30() {
        assertThrows(VotoNonValidoException.class,
                () -> ateneo.registraVoto(mario.getId(), appelloOop.getId(), 17, false));
        assertThrows(VotoNonValidoException.class,
                () -> ateneo.registraVoto(mario.getId(), appelloOop.getId(), 31, false));
        assertTrue(ateneo.getVoti().isEmpty());
    }

    @Test
    void laLodeSiPuoDareSoloConTrenta() {
        assertThrows(VotoNonValidoException.class,
                () -> ateneo.registraVoto(mario.getId(), appelloOop.getId(), 29, true));
           final Voto voto = ateneo.registraVoto(mario.getId(), appelloOop.getId(), 30, true);
      assertTrue(voto.isLode());
         assertEquals("30L", voto.getEtichetta());
}

    @Test
    void ilVotoRichiedeLIscrizioneAllAppello() {
          final Studente luca = ateneo.aggiungiStudente("Luca", "Verdi", "0003");
     assertThrows(IscrizioneNonValidaException.class,
                () -> ateneo.registraVoto(luca.getId(), appelloOop.getId(), 25, false));
    }

    @Test
    void nonSiPuoRegistrareDueVotiPerLoStessoAppello() {
        ateneo.registraVoto(mario.getId(), appelloOop.getId(), 25, false);
     assertThrows(VotoDuplicatoException.class,
                 () -> ateneo.registraVoto(mario.getId(), appelloOop.getId(), 27, false));
        assertEquals(1, ateneo.getVoti().size());
    }

    @Test
    void studenteOAppelloInesistentiVengonoSegnalati() {
        assertThrows(EntitaNonTrovataException.class,
                () -> ateneo.registraVoto(99, appelloOop.getId(), 25, false));
        assertThrows(EntitaNonTrovataException.class,
                () -> ateneo.registraVoto(mario.getId(), 99, 25, false));
    }

    @Test
    void mediaAritmeticaDelloStudente() {
        ateneo.registraVoto(mario.getId(), appelloOop.getId(), 30, false);
        ateneo.registraVoto(mario.getId(), appelloAnalisi.getId(), 24, false);
        final OptionalDouble media = ateneo.mediaStudente(mario.getId(), new MediaAritmetica());
        assertEquals(27.0, media.getAsDouble(), TOLLERANZA);
    }

    @Test
    void mediaPonderataDelloStudentePesaISuoiCfu() {
              ateneo.registraVoto(mario.getId(), appelloOop.getId(), 30, false);
        ateneo.registraVoto(mario.getId(), appelloAnalisi.getId(), 24, false);
        // (30 * 6 + 24 * 12) / (6 + 12) = 26
                  final OptionalDouble media = ateneo.mediaStudente(mario.getId(),           new MediaPonderata());
       
                  assertEquals(26.0, media.getAsDouble(), TOLLERANZA);
    }


    @Test
    void laLodeVaIntesaComeTrentaNellaMedia() {
  ateneo.registraVoto(mario.getId(), appelloOop.getId(), 30, true);
ateneo.registraVoto(mario.getId(),    appelloAnalisi.getId(), 28, false);
    assertEquals(29.0, ateneo.mediaStudente(mario.getId(), new MediaAritmetica()).getAsDouble(), TOLLERANZA);
  


}

 @Test
            void senzaVotiLaMediaDelloStudenteEVuota()    {
    assertFalse(ateneo.mediaStudente(mario.getId(), new MediaAritmetica()).isPresent());
 assertFalse(ateneo.mediaStudente(mario.getId(), new MediaPonderata()).isPresent());
    }

        @Test
void   laMediaDiUnoStudenteInesistenteFallisce() {
   assertThrows(EntitaNonTrovataException.class,
                 () -> ateneo.mediaStudente(99, new MediaAritmetica()));
    }

          @Test
    void mediaDelCorsoSuTuttiGliStudenti() {
    ateneo.registraVoto(mario.getId(), appelloOop.getId(), 24, false);
    ateneo.registraVoto(anna.getId(), appelloOop.getId(), 30, false);
    ateneo.registraVoto(anna.getId(), appelloAnalisi.getId(),  18, false);
     assertEquals(27.0, ateneo.mediaCorso(oop.getId()).getAsDouble(),   TOLLERANZA);
     assertEquals(18.0, ateneo.mediaCorso(analisi.getId()).getAsDouble() , TOLLERANZA);
         }

         @Test
    void senzaVotiLaMediaDelCorsoEVuota() {
     assertFalse(ateneo.mediaCorso(oop.getId()).isPresent());
 assertThrows(EntitaNonTrovataException.class, (  ) -> ateneo.mediaCorso(99));
    }

    @Test
    void leListeRestituiteNonSonoModificabili() {
 assertThrows(UnsupportedOperationException.class, () -> ateneo.getStudenti().clear());
        assertThrows(UnsupportedOperationException.class, () -> ateneo.getVoti().clear());
     assertThrows(UnsupportedOperationException.class ,
         ()-> appelloOop.getIscritti().clear());
    }

    @Test
    void gliOsservatoriVengonoNotificatiSoloDopoUnaModificaRiuscita() {
      final int[] notifiche = {0};
        ateneo.aggiungiOsservatore(() ->notifiche[0]++);
       ateneo.aggiungiCorso    ("Fisica", 9);
        assertEquals(1, notifiche[0]);
     assertThrows(DominioException.class, () -> ateneo.aggiungiCorso("", 9));
        assertEquals(1, notifiche[0]);
      }
    }
