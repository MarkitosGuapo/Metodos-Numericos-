package mx.edu.itses.marc.MetodosNumericos.services;

import static java.lang.Math.abs;
import org.mariuszgromada.math.mxparser.*;

public class Funciones {

    public static double Ecuacion(String f1, double x) {
        double f;
        Function funcion = new Function(f1);
        Expression evaluacion = new Expression("f(" + x + ")", funcion);
        f = evaluacion.calculate();
        return f;
    }
    
    public static double ErrorRelativo(double ValorNuevo, double ValorAnterior) {
        return abs((ValorNuevo - ValorAnterior) / ValorNuevo * 100);
    }

    // Implementación de la derivada numérica usando diferencias finitas
    public static double Derivada(String f1, double x) {
        double h = 1e-5;
        double f_xh = Ecuacion(f1, x + h);
        double f_x = Ecuacion(f1, x);
        return (f_xh - f_x) / h;
    }
}