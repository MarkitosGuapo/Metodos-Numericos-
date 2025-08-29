package mx.edu.itses.itb.MetodosNumericos.domain;

import java.util.ArrayList;
import lombok.Data;

@Data
public class DDNewton {
    // Parámetros de entrada
    private int orden; // Orden del polinomio (1, 2, 3, 4)
    private ArrayList<Double> puntosX; // Puntos X conocidos
    private ArrayList<Double> puntosY; // Puntos Y conocidos (f(x))
    private double valorInterpolacion; // Valor de X donde queremos interpolar
    
    // Resultados del algoritmo
    private ArrayList<ArrayList<Double>> tablaDiferenciasDivididas; // Tabla completa de diferencias divididas
    private ArrayList<Double> coeficientes; // Coeficientes del polinomio de Newton
    private String polinomioNewton; // Expresión del polinomio en forma de Newton
    private String polinomioExpandido; // Expresión del polinomio expandido
    private double valorInterpolado; // Resultado de f(x) interpolado
    private String mensajeEstado; // Mensaje de estado del proceso
}