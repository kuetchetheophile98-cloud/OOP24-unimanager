package it.unibo.unimanager.view;

/** Componente della vista che sa rileggere i propri dati dal controller. */
interface Aggiornabile {

    /** Ricarica tabelle e liste a partire dallo stato corrente del modello. */
    void aggiorna();
}
