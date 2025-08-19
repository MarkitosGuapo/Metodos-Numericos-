package mx.edu.itses.itb.MetodosNumericos.services;

import java.util.ArrayList;
import mx.edu.itses.itb.MetodosNumericos.domain.Gauss;
import mx.edu.itses.itb.MetodosNumericos.domain.GaussJordan;
import mx.edu.itses.itb.MetodosNumericos.domain.ReglaCramer;

public interface UnidadIIIService {


    public ReglaCramer AlgoritmoReglaCramer(ReglaCramer modelCramer);
    public Gauss AlgoritmoGauss(Gauss modelGauss);
    public GaussJordan AlgoritmoGaussJordan(GaussJordan modelGaussJordan);
}
