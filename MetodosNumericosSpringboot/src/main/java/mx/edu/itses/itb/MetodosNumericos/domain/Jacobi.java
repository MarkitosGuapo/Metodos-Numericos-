/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.edu.itses.itb.MetodosNumericos.domain;

import java.util.ArrayList;
import lombok.Data;

/**
 *
 * @author mar_6
 */
@Data
public class Jacobi {
    
    private int MN; // Tamaño del sistema
    private ArrayList<Double> matrizA;
    private ArrayList<Double> vectorB;
    private ArrayList<Double> valoresIniciales; // X0, Y0, Z0
    private double tolerancia;
    private int maxIteraciones;
    
   
    private ArrayList<ArrayList<Double>> iteraciones; // Todas las iteraciones
    private ArrayList<Double> erroresRelativos; // Errores de cada iteración
    private ArrayList<Double> solucionFinal;
    private int iteracionesRealizadas;
    private boolean convergio;
    private String mensajeEstado;
    
 
     private double C1K;
    private double C2K;
    private double C3K;
    private double C4K;  
    private double C1Kmas1;
    private double C2Kmas1;
    private double C3Kmas1;
    private double C4Kmas1; 
    private double EaC1;
    private double EaC2;
    private double EaC3;
    private double EaC4;  
    
}
