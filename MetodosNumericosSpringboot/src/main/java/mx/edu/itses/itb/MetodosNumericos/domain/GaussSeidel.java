package mx.edu.itses.itb.MetodosNumericos.domain;

import java.util.ArrayList;
import lombok.Data;

@Data
public class GaussSeidel {
    private int MN; 
    private ArrayList<Double> matrizA; 
    private ArrayList<Double> vectorB; 
    private ArrayList<Double> valoresIniciales; 
    private double tolerancia; 
    private int maxIteraciones; 
    
    // Resultados del proceso iterativo
    private ArrayList<ArrayList<Double>> iteraciones; 
    private ArrayList<Double> erroresRelativos; 
    private int iteracionesRealizadas; 
    private boolean convergio; 
    private String mensajeEstado; 
    private ArrayList<Double> solucionFinal; 
    
    // Variables para mostrar detalles de la última iteración
    private Double c1K; 
    private Double c2K; 
    private Double c3K; 
    private Double c4K; 
    
    //Suma de iteraciones 
    private Double c1Kmas1; // X1 en iteración k+1
    private Double c2Kmas1; // X2 en iteración k+1
    private Double c3Kmas1; // X3 en iteración k+1 
    private Double c4Kmas1; // X4 en iteración k+1 
    
    //Errores absolutos
    private Double eaC1; // Error absoluto de X1
    private Double eaC2; // Error absoluto de X2
    private Double eaC3; // Error absoluto de X3 
    private Double eaC4; // Error absoluto de X4 
    
  
}