/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.edu.itses.itb.MetodosNumericos.services;

import java.util.ArrayList;
import java.util.Collections;
import lombok.extern.slf4j.Slf4j;
import mx.edu.itses.itb.MetodosNumericos.domain.Gauss;
import mx.edu.itses.itb.MetodosNumericos.domain.ReglaCramer;
import org.springframework.stereotype.Service;

/**
 *
 * @author mar_6
 */
@Service
@Slf4j
public class UnidadlllServicelmpl implements UnidadIIIService {

    ArrayList<Double> determinantes = new ArrayList<>();
    //vector de incognitas 
    ArrayList<Double> vectorX = new ArrayList<>();

    @Override
    public ReglaCramer AlgoritmoReglaCramer(ReglaCramer modelCramer) {
        //Tamaño de sistema lineal

        switch (modelCramer.getMN()) {
            case 2 -> {

                ArrayList<Double> A = modelCramer.getMatrizA();
                ArrayList<Double> B = modelCramer.getVectorB();

                double[][] MatrizA = {
                    {A.get(0), A.get(1),},
                    {A.get(2), A.get(3),}
                };

                determinantes.add(Det2(MatrizA));
                double DetA = Det2(MatrizA);
                log.info("Det A: " + DetA);

                double[][] MatrizX1 = {
                    {B.get(0), A.get(1),},
                    {B.get(1), A.get(3),}
                };

                determinantes.add(Det2(MatrizX1));
                log.info("Det X1: " + determinantes.get(1));

                //Calculamos determinante para X2
                double[][] MatrizX2 = {
                    {A.get(0), B.get(0),},
                    {A.get(2), B.get(1),}
                };

                determinantes.add(Det2(MatrizX2));
                log.info("Det X2: " + determinantes.get(2));
                //Resolviendo las variables 
                vectorX.add(determinantes.get(1) / determinantes.get(0));
                //parax2
                vectorX.add(determinantes.get(2) / determinantes.get(0));
            }
            case 3 -> {
                ArrayList<Double> A = modelCramer.getMatrizA();
                ArrayList<Double> B = modelCramer.getVectorB();
  //Creacion de Matriz principal de 3x3
                double[][] MatrizA = {
                    {A.get(0), A.get(1), A.get(2)},
                    {A.get(3), A.get(4), A.get(5)},
                    {A.get(6), A.get(7), A.get(8)}
                };

                determinantes.add(Det3(MatrizA));
                double DetA = Det3(MatrizA);
                log.info("Det A: " + DetA);
//Primer determinante 
                double[][] MatrizX1 = {
                    {B.get(0), A.get(1), A.get(2)},
                    {B.get(1), A.get(4), A.get(5)},
                    {B.get(2), A.get(7), A.get(8)}
                };

                determinantes.add(Det3(MatrizX1));
                log.info("Det X1: " + determinantes.get(1));
//Segundo determinandte 
                double[][] MatrizX2 = {
                    {A.get(0), B.get(0), A.get(2)},
                    {A.get(3), B.get(1), A.get(5)},
                    {A.get(6), B.get(2), A.get(8)}
                };

                determinantes.add(Det3(MatrizX2));
                log.info("Det X2: " + determinantes.get(2));
//Tercer determinante 
                double[][] MatrizX3 = {
                    {A.get(0), A.get(1), B.get(0)},
                    {A.get(3), A.get(4), B.get(1)},
                    {A.get(6), A.get(7), B.get(2)}
                };

                determinantes.add(Det3(MatrizX3));
                log.info("Det X3: " + determinantes.get(3));

                vectorX.add(determinantes.get(1) / determinantes.get(0)); // X1
                vectorX.add(determinantes.get(2) / determinantes.get(0)); // X2
                vectorX.add(determinantes.get(3) / determinantes.get(0)); // X3

            }
        }

        modelCramer.setVectorX(vectorX);
        modelCramer.setDeterminantes(determinantes);
        return modelCramer;
    }

    private double Det2(double[][] A) {
        return A[0][0] * A[1][1] - A[0][1] * A[1][0];
    }
    
    private double Det3(double[][] A) {
    return A[0][0] * (A[1][1] * A[2][2] - A[1][2] * A[2][1]) -
           A[0][1] * (A[1][0] * A[2][2] - A[1][2] * A[2][0]) +
           A[0][2] * (A[1][0] * A[2][1] - A[1][1] * A[2][0]);
}
    

    
  
    
    
  @Override
public Gauss AlgoritmoGauss(Gauss modelGauss) {
    // Tamaño de sistema lineal
    vectorX.clear();
    switch (modelGauss.getMN()) {
        case 2 -> {
            ArrayList<Double> A = modelGauss.getMatrizA();
            ArrayList<Double> B = modelGauss.getVectorB();
            
            // Crear matriz aumentada [A|B] de 2x3
            double[][] matrizAumentada = {
                {A.get(0), A.get(1), B.get(0)},
                {A.get(2), A.get(3), B.get(1)}
            };
            
            log.info("Matriz aumentada inicial:");
            imprimirMatriz(matrizAumentada, 2, 3);
            
            // Aplicar eliminación gaussiana
            matrizAumentada = eliminacionGaussianaForward(matrizAumentada, 2);
            
            log.info("Matriz después de eliminación hacia adelante:");
            imprimirMatriz(matrizAumentada, 2, 3);
            
            // Sustitución hacia atrás
            ArrayList<Double> solucion = sustitucionHaciaAtras(matrizAumentada, 2);
            vectorX.addAll(solucion);
            
            log.info("Solución: X1 = " + vectorX.get(0) + ", X2 = " + vectorX.get(1));
        }
        case 3 -> {
            ArrayList<Double> A = modelGauss.getMatrizA();
            ArrayList<Double> B = modelGauss.getVectorB();
            
            // Crear matriz aumentada [A|B] de 3x4
            double[][] matrizAumentada = {
                {A.get(0), A.get(1), A.get(2), B.get(0)},
                {A.get(3), A.get(4), A.get(5), B.get(1)},
                {A.get(6), A.get(7), A.get(8), B.get(2)}
            };
            
            log.info("Matriz aumentada inicial:");
            imprimirMatriz(matrizAumentada, 3, 4);
            
            // Aplicar eliminación gaussiana
            matrizAumentada = eliminacionGaussianaForward(matrizAumentada, 3);
            
            log.info("Matriz después de eliminación hacia adelante:");
            imprimirMatriz(matrizAumentada, 3, 4);
            
            // Sustitución hacia atrás
            ArrayList<Double> solucion = sustitucionHaciaAtras(matrizAumentada, 3);
            vectorX.addAll(solucion);
            
            log.info("Solución: X1 = " + vectorX.get(0) + ", X2 = " + vectorX.get(1) + ", X3 = " + vectorX.get(2));
        }
        case 4 -> {
            ArrayList<Double> A = modelGauss.getMatrizA();
            ArrayList<Double> B = modelGauss.getVectorB();
            
            // Crear matriz aumentada [A|B] de 4x4
            
            double[][] matrizAumentada = {
                {A.get(0),  A.get(1),  A.get(2),  A.get(3),  B.get(0)},
                {A.get(4),  A.get(5),  A.get(6),  A.get(7),  B.get(1)},
                {A.get(8),  A.get(9),  A.get(10), A.get(11), B.get(2)},
                {A.get(12), A.get(13), A.get(14), A.get(15), B.get(3)}
            };
            
            log.info("Matriz aumentada inicial 4x4:");
            imprimirMatriz(matrizAumentada, 4, 5);
            
            // Aplicar eliminación gaussiana
            matrizAumentada = eliminacionGaussianaForward(matrizAumentada, 4);
            
            log.info("Matriz después de eliminación hacia adelante:");
            imprimirMatriz(matrizAumentada, 4, 5);
            
            // Sustitución hacia atrás
            ArrayList<Double> solucion = sustitucionHaciaAtras(matrizAumentada, 4);
            vectorX.addAll(solucion);
            
            log.info("Solución: X1 = " + vectorX.get(0) + ", X2 = " + vectorX.get(1) + 
                     ", X3 = " + vectorX.get(2) + ", X4 = " + vectorX.get(3));
        }
    }
    
    modelGauss.setVectorX(vectorX);
    return modelGauss;
}

// eliminación gaussiana hacia adelante
private double[][] eliminacionGaussianaForward(double[][] matriz, int n) {
    double[][] resultado = new double[n][n + 1];
    
    // Copiar matriz original
    for (int i = 0; i < n; i++) {
        System.arraycopy(matriz[i], 0, resultado[i], 0, n + 1);
    }
    
    // Eliminación hacia adelante
    for (int k = 0; k < n - 1; k++) {
        // Pivoteo parcial (opcional pero recomendado para estabilidad numérica)
        int maxRow = k;
        for (int i = k + 1; i < n; i++) {
            if (Math.abs(resultado[i][k]) > Math.abs(resultado[maxRow][k])) {
                maxRow = i;
            }
        }
        
        // Intercambiar filas si es necesario
        if (maxRow != k) {
            double[] temp = resultado[k];
            resultado[k] = resultado[maxRow];
            resultado[maxRow] = temp;
            log.info("Intercambiando fila " + k + " con fila " + maxRow);
        }
        
        // Verificar que el pivote no sea cero
        if (Math.abs(resultado[k][k]) < 1e-10) {
            log.error("Sistema sin solución única - pivote cero en posición [" + k + "][" + k + "]");
            throw new RuntimeException("Sistema sin solución única");
        }
        
        // Eliminación
        for (int i = k + 1; i < n; i++) {
            double factor = resultado[i][k] / resultado[k][k];
            log.info("Factor de eliminación para fila " + i + ": " + factor);
            
            for (int j = k; j < n + 1; j++) {
                resultado[i][j] -= factor * resultado[k][j];
            }
        }
    }
    
    return resultado;
}

//  sustitución hacia atrás
private ArrayList<Double> sustitucionHaciaAtras(double[][] matriz, int n) {
    ArrayList<Double> x = new ArrayList<>(Collections.nCopies(n, 0.0));
    
    // Resolver desde la última fila hacia arriba
    for (int i = n - 1; i >= 0; i--) {
        double suma = matriz[i][n]; // Término independiente
        
     
        for (int j = i + 1; j < n; j++) {
            suma -= matriz[i][j] * x.get(j);
        }
        
        // Dividir por el coeficiente diagonal
        x.set(i, suma / matriz[i][i]);
        log.info("X" + (i + 1) + " = " + x.get(i));
    }
    
    return x;
}

//  imprimir matriz
private void imprimirMatriz(double[][] matriz, int filas, int columnas) {
    for (int i = 0; i < filas; i++) {
        StringBuilder sb = new StringBuilder();
        for (int j = 0; j < columnas; j++) {
            sb.append(String.format("%8.4f ", matriz[i][j]));
            if (j == columnas - 2) sb.append("| "); 
        }
        log.info(sb.toString());
    }
}

    
    
    
    
    
}
