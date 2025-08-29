/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package mx.edu.itses.itb.MetodosNumericos.services;
import java.util.ArrayList;
import mx.edu.itses.itb.MetodosNumericos.domain.Biseccion;
import mx.edu.itses.itb.MetodosNumericos.domain.NewtonRaphson;
import mx.edu.itses.itb.MetodosNumericos.domain.PuntoFijo;
import mx.edu.itses.itb.MetodosNumericos.domain.ReglaFalsa;
import mx.edu.itses.itb.MetodosNumericos.domain.Secante;
import mx.edu.itses.itb.MetodosNumericos.domain.SecanteModificada;


public interface UnidadIIService {
        public ArrayList<Biseccion> AlgoritmoBiseccion(Biseccion biseccion);
        public ArrayList<ReglaFalsa>AlgoritmoDeReglaFalsa(ReglaFalsa falsa);
        public ArrayList<PuntoFijo>AlgoritmoDePuntoFijo(String gx, PuntoFijo puntofijo, double tolerancia);
        public ArrayList<NewtonRaphson>AlgoritmoNewtonRaphson(NewtonRaphson newton);
        public ArrayList<Secante>AlgoritmoSecante(Secante secante);
         ArrayList<SecanteModificada> AlgoritmoSecanteModificado(SecanteModificada secanteModificada);
}
