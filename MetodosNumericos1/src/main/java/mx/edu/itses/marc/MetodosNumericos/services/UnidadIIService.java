package mx.edu.itses.marc.MetodosNumericos.services;

import java.util.ArrayList;
import mx.edu.itses.marc.MetodosNumericos.domain.Biseccion;
import mx.edu.itses.marc.MetodosNumericos.domain.NewRapson;
import mx.edu.itses.marc.MetodosNumericos.domain.PuntoFijo;
import mx.edu.itses.marc.MetodosNumericos.domain.ReglaFalsa;
import mx.edu.itses.marc.MetodosNumericos.domain.Secante;
import mx.edu.itses.marc.MetodosNumericos.domain.SecanteModificada;


public interface UnidadIIService {
    ArrayList<Biseccion> AlgoritmoBiseccion(Biseccion biseccion);
    ArrayList<ReglaFalsa> AlgoritmoReglaFalsa(ReglaFalsa reglafalsa);
    ArrayList<PuntoFijo> AlgoritmoDePuntoFijo(PuntoFijo puntofijo);
    ArrayList<NewRapson> AlgoritmoDeNewRpason(NewRapson rapson);
    ArrayList<SecanteModificada> AlgoritmoDeSecanteModificada(SecanteModificada secantemodificada);
   ArrayList<Secante> AlgoritmoDeSecante(Secante secante);
}