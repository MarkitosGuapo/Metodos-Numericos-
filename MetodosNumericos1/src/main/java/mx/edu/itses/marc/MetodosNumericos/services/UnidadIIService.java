package mx.edu.itses.marc.MetodosNumericos.services;

import java.util.ArrayList;
import mx.edu.itses.marc.MetodosNumericos.domain.Biseccion;
import mx.edu.itses.marc.MetodosNumericos.domain.PuntoFijo;
import mx.edu.itses.marc.MetodosNumericos.domain.ReglaFalsa;

public interface UnidadIIService {
     public ArrayList<Biseccion> AlgoritmoBiseccion(Biseccion biseccion);
     public ArrayList<ReglaFalsa> AlgoritmoReglaFalsa(ReglaFalsa reglafalsa);
     public ArrayList<PuntoFijo> AlgoritmoDePuntoFijo(PuntoFijo puntofijo);
     
}
