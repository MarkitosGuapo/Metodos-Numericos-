/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.edu.itses.itb.MetodosNumericos.services;

import java.util.ArrayList;
import java.util.Collections;
import lombok.extern.slf4j.Slf4j;
import mx.edu.itses.itb.MetodosNumericos.domain.Gauss;
import mx.edu.itses.itb.MetodosNumericos.domain.GaussJordan;
import mx.edu.itses.itb.MetodosNumericos.domain.GaussSeidel;
import mx.edu.itses.itb.MetodosNumericos.domain.Jacobi;
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

@Override
public GaussJordan AlgoritmoGaussJordan(GaussJordan modelGaussJordan) {
   vectorX.clear();
    switch (modelGaussJordan.getMN()) {
        case 2 -> {
            ArrayList<Double> A = modelGaussJordan.getMatrizA();
            ArrayList<Double> B = modelGaussJordan.getVectorB();
            
            // Crear matriz aumentada [A|B] de 2x3
            double[][] matrizAumentada = {
                {A.get(0), A.get(1), B.get(0)},
                {A.get(2), A.get(3), B.get(1)}
            };
            
            log.info("Matriz aumentada inicial:");
            imprimirMatriz(matrizAumentada, 2, 3);
            
            // Aplicar eliminación Gauss-Jordan
            matrizAumentada = eliminacionGaussJordan(matrizAumentada, 2);
            
            log.info("Matriz después de Gauss-Jordan (forma escalonada reducida):");
            imprimirMatriz(matrizAumentada, 2, 3);
            
            // Extraer solución directamente de la diagonal
            ArrayList<Double> solucion = extraerSolucion(matrizAumentada, 2);
            vectorX.addAll(solucion);
            
            log.info("Solución: X1 = " + vectorX.get(0) + ", X2 = " + vectorX.get(1));
        }
        case 3 -> {
            ArrayList<Double> A = modelGaussJordan.getMatrizA();
            ArrayList<Double> B = modelGaussJordan.getVectorB();
            
            // Crear matriz aumentada [A|B] de 3x4
            double[][] matrizAumentada = {
                {A.get(0), A.get(1), A.get(2), B.get(0)},
                {A.get(3), A.get(4), A.get(5), B.get(1)},
                {A.get(6), A.get(7), A.get(8), B.get(2)}
            };
            
            log.info("Matriz aumentada inicial:");
            imprimirMatriz(matrizAumentada, 3, 4);
            
            // Aplicar eliminación Gauss-Jordan
            matrizAumentada = eliminacionGaussJordan(matrizAumentada, 3);
            
            log.info("Matriz después de Gauss-Jordan (forma escalonada reducida):");
            imprimirMatriz(matrizAumentada, 3, 4);
            
            // Extraer solución directamente de la diagonal
            ArrayList<Double> solucion = extraerSolucion(matrizAumentada, 3);
            vectorX.addAll(solucion);
            
            log.info("Solución: X1 = " + vectorX.get(0) + ", X2 = " + vectorX.get(1) + ", X3 = " + vectorX.get(2));
        }
        case 4 -> {
            ArrayList<Double> A = modelGaussJordan.getMatrizA();
            ArrayList<Double> B = modelGaussJordan.getVectorB();
            
            // Crear matriz aumentada [A|B] de 4x5
            double[][] matrizAumentada = {
                {A.get(0),  A.get(1),  A.get(2),  A.get(3),  B.get(0)},
                {A.get(4),  A.get(5),  A.get(6),  A.get(7),  B.get(1)},
                {A.get(8),  A.get(9),  A.get(10), A.get(11), B.get(2)},
                {A.get(12), A.get(13), A.get(14), A.get(15), B.get(3)}
            };
            
            log.info("Matriz aumentada inicial 4x5:");
            imprimirMatriz(matrizAumentada, 4, 5);
            
            // Aplicar eliminación Gauss-Jordan
            matrizAumentada = eliminacionGaussJordan(matrizAumentada, 4);
            
            log.info("Matriz después de Gauss-Jordan (forma escalonada reducida):");
            imprimirMatriz(matrizAumentada, 4, 5);
            
            // Extraer solución directamente de la diagonal
            ArrayList<Double> solucion = extraerSolucion(matrizAumentada, 4);
            vectorX.addAll(solucion);
            
            log.info("Solución: X1 = " + vectorX.get(0) + ", X2 = " + vectorX.get(1) + 
                     ", X3 = " + vectorX.get(2) + ", X4 = " + vectorX.get(3));
        }
    }
    
    modelGaussJordan.setVectorX(vectorX);
    return modelGaussJordan;
}

// Método principal de eliminación Gauss-Jordan
private double[][] eliminacionGaussJordan(double[][] matriz, int n) {
    double[][] resultado = new double[n][n + 1];
    
    // Copiar matriz original
    for (int i = 0; i < n; i++) {
        System.arraycopy(matriz[i], 0, resultado[i], 0, n + 1);
    }
    
    //  Eliminación hacia adelante 
    log.info(" Eliminación hacia adelante");
    for (int k = 0; k < n; k++) {
        // Pivoteo parcial
        int maxRow = k;
        for (int i = k + 1; i < n; i++) {
            if (Math.abs(resultado[i][k]) > Math.abs(resultado[maxRow][k])) {
                maxRow = i;
            }
        }
        
        // Intercambiar filas 
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
        
        // Normalizar la fila del pivote
        double pivote = resultado[k][k];
        log.info("Normalizando fila " + k + " dividiendo por pivote: " + pivote);
        for (int j = 0; j < n + 1; j++) {
            resultado[k][j] /= pivote;
        }
        
        // Eliminación hacia adelante
        for (int i = k + 1; i < n; i++) {
            double factor = resultado[i][k];
            if (Math.abs(factor) > 1e-10) {
                log.info("Eliminando hacia adelante - fila " + i + ", factor: " + factor);
                for (int j = 0; j < n + 1; j++) {
                    resultado[i][j] -= factor * resultado[k][j];
                }
            }
        }
        
        log.info("Matriz después del paso " + (k + 1) + " hacia adelante:");
        imprimirMatriz(resultado, n, n + 1);
    }
    
    // Eliminación hacia atrás
    log.info("Eliminación hacia atras");
    for (int k = n - 1; k > 0; k--) {
        for (int i = k - 1; i >= 0; i--) {
            double factor = resultado[i][k];
            if (Math.abs(factor) > 1e-10) {
                log.info("Eliminando hacia atras - fila " + i + ", factor: " + factor);
                for (int j = 0; j < n + 1; j++) {
                    resultado[i][j] -= factor * resultado[k][j];
                }
            }
        }
        
        log.info("Matriz despues del paso " + k + " hacia atras:");
        imprimirMatriz(resultado, n, n + 1);
    }
    
    return resultado;
}

// Extraer solución de la matriz
private ArrayList<Double> extraerSolucion(double[][] matriz, int n) {
  ArrayList<Double> x = new ArrayList<>(Collections.nCopies(n, 0.0));
    
    for (int i = 0; i < n; i++) {
        double valor = matriz[i][n];
        // Redondear valores muy pequeños a cero
        if (Math.abs(valor) < 1e-10) {
            valor = 0.0;
        }
    
        valor = Math.round(valor * 1000000.0) / 1000000.0;
        
        x.set(i, valor);
        log.info("X" + (i + 1) + " = " + x.get(i));
    }
    
    return x;
}

    @Override
    public Jacobi AlgoritmoJacobi(Jacobi jacobi) {
        vectorX.clear();
    
    switch (jacobi.getMN()) {
        case 2 -> {
            return resolverJacobi2x2(jacobi);
        }
        case 3 -> {
            return resolverJacobi3x3(jacobi);
        }
        case 4 -> {
            return resolverJacobi4x4(jacobi); 
        }
        default -> {
            jacobi.setMensajeEstado("Tamaño de sistema no soportado");
            jacobi.setConvergio(false);
            return jacobi;
        }
    }
    }
    
 private Jacobi resolverJacobi2x2(Jacobi modelJacobi) {
    ArrayList<Double> A = modelJacobi.getMatrizA();
    ArrayList<Double> B = modelJacobi.getVectorB();
    ArrayList<Double> valoresIniciales = modelJacobi.getValoresIniciales();
    
    // CORRECCIÓN: Verificar que las listas no sean null
    if (A == null || B == null || valoresIniciales == null) {
        modelJacobi.setMensajeEstado("Error: Datos de entrada incompletos");
        modelJacobi.setConvergio(false);
        return modelJacobi;
    }
    
    // CORRECCIÓN: Verificar elementos diagonales no sean cero
    if (A.get(0) == 0 || A.get(3) == 0) {
        modelJacobi.setMensajeEstado("Error: Elementos diagonales no pueden ser cero");
        modelJacobi.setConvergio(false);
        return modelJacobi;
    }
    
    // Verificar convergencia diagonal dominante
    if (!esDiagonalDominante(A, 2)) {
        modelJacobi.setMensajeEstado("Advertencia: La matriz no es diagonalmente dominante. La convergencia no está garantizada.");
    }
    
    log.info("=== INICIANDO MÉTODO DE JACOBI 2x2 ===");
    log.info("Sistema: " + A.get(0) + "x + " + A.get(1) + "y = " + B.get(0));
    log.info("         " + A.get(2) + "x + " + A.get(3) + "y = " + B.get(1));
    log.info("Valores iniciales: x0 = " + valoresIniciales.get(0) + ", y0 = " + valoresIniciales.get(1));
    log.info("Tolerancia: " + modelJacobi.getTolerancia() + "%");
    
    double x_anterior = valoresIniciales.get(0);
    double y_anterior = valoresIniciales.get(1);
    
    ArrayList<ArrayList<Double>> todasIteraciones = new ArrayList<>();
    ArrayList<Double> erroresRelativos = new ArrayList<>();
    
    boolean convergio = false;
    int iteracion = 0;
    
    while (iteracion < modelJacobi.getMaxIteraciones() && !convergio) {
        iteracion++;
        
        // Calcular nuevos valores usando valores anteriores
        double x_nuevo = (B.get(0) - A.get(1) * y_anterior) / A.get(0);
        double y_nuevo = (B.get(1) - A.get(2) * x_anterior) / A.get(3);
        
        // Calcular errores relativos
        double error_x = (iteracion > 1) ? Funciones.ErrorRelativo(x_nuevo, x_anterior) : 100.0;
        double error_y = (iteracion > 1) ? Funciones.ErrorRelativo(y_nuevo, y_anterior) : 100.0;
        double error_max = Math.max(error_x, error_y);
        
        log.info("Iteración " + iteracion + ":");
        log.info("  x(" + iteracion + ") = (" + B.get(0) + " - " + A.get(1) + "*" + y_anterior + ") / " + A.get(0) + " = " + x_nuevo);
        log.info("  y(" + iteracion + ") = (" + B.get(1) + " - " + A.get(2) + "*" + x_anterior + ") / " + A.get(3) + " = " + y_nuevo);
        log.info("  Error x: " + error_x + "%, Error y: " + error_y + "%");
        
        // Guardar iteración
        ArrayList<Double> iteracionActual = new ArrayList<>();
        iteracionActual.add((double) iteracion);
        iteracionActual.add(x_nuevo);
        iteracionActual.add(y_nuevo);
        iteracionActual.add(error_x);
        iteracionActual.add(error_y);
        todasIteraciones.add(iteracionActual);
        erroresRelativos.add(error_max);
        
        // Verificar convergencia
        if (error_max < modelJacobi.getTolerancia()) {
            convergio = true;
            log.info("¡Convergencia alcanzada en la iteración " + iteracion + "!");
        }
        
        // Actualizar para siguiente iteración
        x_anterior = x_nuevo;
        y_anterior = y_nuevo;
        
        // Actualizar modelo para mostrar última iteración
        if (iteracion >= 2) {
            modelJacobi.setC1K(todasIteraciones.get(iteracion-2).get(1));
            modelJacobi.setC2K(todasIteraciones.get(iteracion-2).get(2));
            modelJacobi.setC1Kmas1(x_nuevo);
            modelJacobi.setC2Kmas1(y_nuevo);
            modelJacobi.setEaC1(error_x);
            modelJacobi.setEaC2(error_y);
        }
    }
    
    // Configurar resultados finales
    modelJacobi.setIteraciones(todasIteraciones);
    modelJacobi.setErroresRelativos(erroresRelativos);
    modelJacobi.setIteracionesRealizadas(iteracion);
    modelJacobi.setConvergio(convergio);
    
    ArrayList<Double> solucionFinal = new ArrayList<>();
    solucionFinal.add(x_anterior);
    solucionFinal.add(y_anterior);
    modelJacobi.setSolucionFinal(solucionFinal);
    
    if (convergio) {
        modelJacobi.setMensajeEstado("Convergencia exitosa en " + iteracion + " iteraciones");
    } else {
        modelJacobi.setMensajeEstado("No se alcanzó convergencia en " + iteracion + " iteraciones");
    }
    
    return modelJacobi;
}

// Método para sistema 3x3
private Jacobi resolverJacobi3x3(Jacobi modelJacobi) {
    ArrayList<Double> A = modelJacobi.getMatrizA();
    ArrayList<Double> B = modelJacobi.getVectorB();
    ArrayList<Double> valoresIniciales = modelJacobi.getValoresIniciales();
    
    // Verificar convergencia diagonal dominante
    if (!esDiagonalDominante(A, 3)) {
        modelJacobi.setMensajeEstado("Advertencia: La matriz no es diagonalmente dominante. La convergencia no está garantizada.");
    }
    
    log.info("=== INICIANDO MÉTODO DE JACOBI 3x3 ===");
    log.info("Valores iniciales: x0 = " + valoresIniciales.get(0) + ", y0 = " + valoresIniciales.get(1) + ", z0 = " + valoresIniciales.get(2));
    log.info("Tolerancia: " + modelJacobi.getTolerancia() + "%");
    
    // Ecuaciones de iteración:
    // x(k+1) = (b1 - a12*y(k) - a13*z(k)) / a11
    // y(k+1) = (b2 - a21*x(k) - a23*z(k)) / a22
    // z(k+1) = (b3 - a31*x(k) - a32*y(k)) / a33
    
    double x_anterior = valoresIniciales.get(0);
    double y_anterior = valoresIniciales.get(1);
    double z_anterior = valoresIniciales.get(2);
    
    ArrayList<ArrayList<Double>> todasIteraciones = new ArrayList<>();
    ArrayList<Double> erroresRelativos = new ArrayList<>();
    
    boolean convergio = false;
    int iteracion = 0;
    
    while (iteracion < modelJacobi.getMaxIteraciones() && !convergio) {
        iteracion++;
        
        // Calcular nuevos valores usando valores anteriores
        double x_nuevo = (B.get(0) - A.get(1) * y_anterior - A.get(2) * z_anterior) / A.get(0);
        double y_nuevo = (B.get(1) - A.get(3) * x_anterior - A.get(5) * z_anterior) / A.get(4);
        double z_nuevo = (B.get(2) - A.get(6) * x_anterior - A.get(7) * y_anterior) / A.get(8);
        
        // Calcular errores relativos
        double error_x = (iteracion > 1) ? Funciones.ErrorRelativo(x_nuevo, x_anterior) : 100.0;
        double error_y = (iteracion > 1) ? Funciones.ErrorRelativo(y_nuevo, y_anterior) : 100.0;
        double error_z = (iteracion > 1) ? Funciones.ErrorRelativo(z_nuevo, z_anterior) : 100.0;
        double error_max = Math.max(Math.max(error_x, error_y), error_z);
        
        log.info("Iteración " + iteracion + ":");
        log.info("  x(" + iteracion + ") = " + x_nuevo);
        log.info("  y(" + iteracion + ") = " + y_nuevo);
        log.info("  z(" + iteracion + ") = " + z_nuevo);
        log.info("  Errores: x=" + error_x + "%, y=" + error_y + "%, z=" + error_z + "%");
        
        // Guardar iteración
        ArrayList<Double> iteracionActual = new ArrayList<>();
        iteracionActual.add((double) iteracion);
        iteracionActual.add(x_nuevo);
        iteracionActual.add(y_nuevo);
        iteracionActual.add(z_nuevo);
        iteracionActual.add(error_x);
        iteracionActual.add(error_y);
        iteracionActual.add(error_z);
        todasIteraciones.add(iteracionActual);
        erroresRelativos.add(error_max);
        
        // Verificar convergencia
        if (error_max < modelJacobi.getTolerancia()) {
            convergio = true;
            log.info("¡Convergencia alcanzada en la iteración " + iteracion + "!");
        }
        
        // Actualizar para siguiente iteración
        x_anterior = x_nuevo;
        y_anterior = y_nuevo;
        z_anterior = z_nuevo;
        
        // Actualizar modelo para mostrar última iteración
        if (iteracion >= 2) {
            modelJacobi.setC1K(todasIteraciones.get(iteracion-2).get(1));
            modelJacobi.setC2K(todasIteraciones.get(iteracion-2).get(2));
            modelJacobi.setC3K(todasIteraciones.get(iteracion-2).get(3));
            modelJacobi.setC1Kmas1(x_nuevo);
            modelJacobi.setC2Kmas1(y_nuevo);
            modelJacobi.setC3Kmas1(z_nuevo);
            modelJacobi.setEaC1(error_x);
            modelJacobi.setEaC2(error_y);
            modelJacobi.setEaC3(error_z);
        }
    }
    
    // Configurar resultados finales
    modelJacobi.setIteraciones(todasIteraciones);
    modelJacobi.setErroresRelativos(erroresRelativos);
    modelJacobi.setIteracionesRealizadas(iteracion);
    modelJacobi.setConvergio(convergio);
    
    ArrayList<Double> solucionFinal = new ArrayList<>();
    solucionFinal.add(x_anterior);
    solucionFinal.add(y_anterior);
    solucionFinal.add(z_anterior);
    modelJacobi.setSolucionFinal(solucionFinal);
    
    if (convergio) {
        modelJacobi.setMensajeEstado("Convergencia exitosa en " + iteracion + " iteraciones");
    } else {
        modelJacobi.setMensajeEstado("No se alcanzó convergencia en " + iteracion + " iteraciones");
    }
    
    return modelJacobi;
}

// Método auxiliar para verificar dominancia diagonal
private boolean esDiagonalDominante(ArrayList<Double> A, int n) {
    for (int i = 0; i < n; i++) {
        double diagonal = Math.abs(A.get(i * n + i));
        double sumaFilas = 0;
        for (int j = 0; j < n; j++) {
            if (i != j) {
                sumaFilas += Math.abs(A.get(i * n + j));
            }
        }
        if (diagonal <= sumaFilas) {
            return false;
        }
    }
    return true;
}

    // NUEVO MÉTODO PARA SISTEMA 4x4:
private Jacobi resolverJacobi4x4(Jacobi modelJacobi) {
    ArrayList<Double> A = modelJacobi.getMatrizA();
    ArrayList<Double> B = modelJacobi.getVectorB();
    ArrayList<Double> valoresIniciales = modelJacobi.getValoresIniciales();
    
    // Verificar convergencia diagonal dominante
    if (!esDiagonalDominante(A, 4)) {
        modelJacobi.setMensajeEstado("Advertencia: La matriz no es diagonalmente dominante. La convergencia no está garantizada.");
    }
    
    log.info("=== INICIANDO MÉTODO DE JACOBI 4x4 ===");
    log.info("Sistema 4x4:");
    log.info("Ecuación 1: " + A.get(0) + "x + " + A.get(1) + "y + " + A.get(2) + "z + " + A.get(3) + "w = " + B.get(0));
    log.info("Ecuación 2: " + A.get(4) + "x + " + A.get(5) + "y + " + A.get(6) + "z + " + A.get(7) + "w = " + B.get(1));
    log.info("Ecuación 3: " + A.get(8) + "x + " + A.get(9) + "y + " + A.get(10) + "z + " + A.get(11) + "w = " + B.get(2));
    log.info("Ecuación 4: " + A.get(12) + "x + " + A.get(13) + "y + " + A.get(14) + "z + " + A.get(15) + "w = " + B.get(3));
    log.info("Valores iniciales: x0=" + valoresIniciales.get(0) + ", y0=" + valoresIniciales.get(1) + 
             ", z0=" + valoresIniciales.get(2) + ", w0=" + valoresIniciales.get(3));
    log.info("Tolerancia: " + modelJacobi.getTolerancia() + "%");
    
    /* Ecuaciones de iteración para 4x4:
     * x(k+1) = (b1 - a12*y(k) - a13*z(k) - a14*w(k)) / a11
     * y(k+1) = (b2 - a21*x(k) - a23*z(k) - a24*w(k)) / a22
     * z(k+1) = (b3 - a31*x(k) - a32*y(k) - a34*w(k)) / a33
     * w(k+1) = (b4 - a41*x(k) - a42*y(k) - a43*z(k)) / a44
     */
    
    double x_anterior = valoresIniciales.get(0);
    double y_anterior = valoresIniciales.get(1);
    double z_anterior = valoresIniciales.get(2);
    double w_anterior = valoresIniciales.get(3);
    
    ArrayList<ArrayList<Double>> todasIteraciones = new ArrayList<>();
    ArrayList<Double> erroresRelativos = new ArrayList<>();
    
    boolean convergio = false;
    int iteracion = 0;
    
    while (iteracion < modelJacobi.getMaxIteraciones() && !convergio) {
        iteracion++;
        
        // Calcular nuevos valores usando valores anteriores
        double x_nuevo = (B.get(0) - A.get(1)*y_anterior - A.get(2)*z_anterior - A.get(3)*w_anterior) / A.get(0);
        double y_nuevo = (B.get(1) - A.get(4)*x_anterior - A.get(6)*z_anterior - A.get(7)*w_anterior) / A.get(5);
        double z_nuevo = (B.get(2) - A.get(8)*x_anterior - A.get(9)*y_anterior - A.get(11)*w_anterior) / A.get(10);
        double w_nuevo = (B.get(3) - A.get(12)*x_anterior - A.get(13)*y_anterior - A.get(14)*z_anterior) / A.get(15);
        
        // Calcular errores relativos
        double error_x = (iteracion > 1) ? Funciones.ErrorRelativo(x_nuevo, x_anterior) : 100.0;
        double error_y = (iteracion > 1) ? Funciones.ErrorRelativo(y_nuevo, y_anterior) : 100.0;
        double error_z = (iteracion > 1) ? Funciones.ErrorRelativo(z_nuevo, z_anterior) : 100.0;
        double error_w = (iteracion > 1) ? Funciones.ErrorRelativo(w_nuevo, w_anterior) : 100.0;
        double error_max = Math.max(Math.max(error_x, error_y), Math.max(error_z, error_w));
        
        log.info("Iteración " + iteracion + ":");
        log.info("  x(" + iteracion + ") = (" + B.get(0) + " - " + A.get(1) + "*" + y_anterior + 
                 " - " + A.get(2) + "*" + z_anterior + " - " + A.get(3) + "*" + w_anterior + ") / " + A.get(0) + " = " + x_nuevo);
        log.info("  y(" + iteracion + ") = (" + B.get(1) + " - " + A.get(4) + "*" + x_anterior + 
                 " - " + A.get(6) + "*" + z_anterior + " - " + A.get(7) + "*" + w_anterior + ") / " + A.get(5) + " = " + y_nuevo);
        log.info("  z(" + iteracion + ") = (" + B.get(2) + " - " + A.get(8) + "*" + x_anterior + 
                 " - " + A.get(9) + "*" + y_anterior + " - " + A.get(11) + "*" + w_anterior + ") / " + A.get(10) + " = " + z_nuevo);
        log.info("  w(" + iteracion + ") = (" + B.get(3) + " - " + A.get(12) + "*" + x_anterior + 
                 " - " + A.get(13) + "*" + y_anterior + " - " + A.get(14) + "*" + z_anterior + ") / " + A.get(15) + " = " + w_nuevo);
        log.info("  Errores: x=" + error_x + "%, y=" + error_y + "%, z=" + error_z + "%, w=" + error_w + "%");
        
        // Guardar iteración
        ArrayList<Double> iteracionActual = new ArrayList<>();
        iteracionActual.add((double) iteracion);
        iteracionActual.add(x_nuevo);
        iteracionActual.add(y_nuevo);
        iteracionActual.add(z_nuevo);
        iteracionActual.add(w_nuevo);
        iteracionActual.add(error_x);
        iteracionActual.add(error_y);
        iteracionActual.add(error_z);
        iteracionActual.add(error_w);
        todasIteraciones.add(iteracionActual);
        erroresRelativos.add(error_max);
        
        // Verificar convergencia
        if (error_max < modelJacobi.getTolerancia()) {
            convergio = true;
            log.info("¡Convergencia alcanzada en la iteración " + iteracion + "!");
        }
        
        // Actualizar para siguiente iteración
        x_anterior = x_nuevo;
        y_anterior = y_nuevo;
        z_anterior = z_nuevo;
        w_anterior = w_nuevo;
        
        // Actualizar modelo para mostrar última iteración
        if (iteracion >= 2) {
            modelJacobi.setC1K(todasIteraciones.get(iteracion-2).get(1));
            modelJacobi.setC2K(todasIteraciones.get(iteracion-2).get(2));
            modelJacobi.setC3K(todasIteraciones.get(iteracion-2).get(3));
            modelJacobi.setC4K(todasIteraciones.get(iteracion-2).get(4));
            modelJacobi.setC1Kmas1(x_nuevo);
            modelJacobi.setC2Kmas1(y_nuevo);
            modelJacobi.setC3Kmas1(z_nuevo);
            modelJacobi.setC4Kmas1(w_nuevo);
            modelJacobi.setEaC1(error_x);
            modelJacobi.setEaC2(error_y);
            modelJacobi.setEaC3(error_z);
            modelJacobi.setEaC4(error_w);
        }
    }
    
    // Configurar resultados finales
    modelJacobi.setIteraciones(todasIteraciones);
    modelJacobi.setErroresRelativos(erroresRelativos);
    modelJacobi.setIteracionesRealizadas(iteracion);
    modelJacobi.setConvergio(convergio);
    
    ArrayList<Double> solucionFinal = new ArrayList<>();
    solucionFinal.add(x_anterior);
    solucionFinal.add(y_anterior);
    solucionFinal.add(z_anterior);
    solucionFinal.add(w_anterior);
    modelJacobi.setSolucionFinal(solucionFinal);
    
    if (convergio) {
        modelJacobi.setMensajeEstado("Convergencia exitosa en " + iteracion + " iteraciones");
    } else {
        modelJacobi.setMensajeEstado("No se alcanzó convergencia en " + iteracion + " iteraciones. Máximo error: " + 
                                   String.format("%.6f", erroresRelativos.get(erroresRelativos.size()-1)) + "%");
    }
    
    log.info("=== RESULTADO FINAL 4x4 ===");
    log.info("X1 = " + solucionFinal.get(0));
    log.info("X2 = " + solucionFinal.get(1));
    log.info("X3 = " + solucionFinal.get(2));
    log.info("X4 = " + solucionFinal.get(3));
    log.info("Convergencia: " + convergio);
    log.info("Iteraciones: " + iteracion);
    
    return modelJacobi;
}
    @Override
public GaussSeidel AlgoritmoGaussSeidel(GaussSeidel gaussSeidel) {
    vectorX.clear();
    
    switch (gaussSeidel.getMN()) {
        case 2 -> {
            return resolverGaussSeidel2x2(gaussSeidel);
        }
        case 3 -> {
            return resolverGaussSeidel3x3(gaussSeidel);
        }
        case 4 -> {
            return resolverGaussSeidel4x4(gaussSeidel);
        }
        default -> {
            gaussSeidel.setMensajeEstado("Tamaño de sistema no soportado");
            gaussSeidel.setConvergio(false);
            return gaussSeidel;
        }
    }
}

private GaussSeidel resolverGaussSeidel2x2(GaussSeidel modelGaussSeidel) {
    ArrayList<Double> A = modelGaussSeidel.getMatrizA();
    ArrayList<Double> B = modelGaussSeidel.getVectorB();
    ArrayList<Double> valoresIniciales = modelGaussSeidel.getValoresIniciales();
    
    // Verificar que las listas no sean null
    if (A == null || B == null || valoresIniciales == null) {
        modelGaussSeidel.setMensajeEstado("Error: Datos de entrada incompletos");
        modelGaussSeidel.setConvergio(false);
        return modelGaussSeidel;
    }
    
    // Verificar elementos diagonales no sean cero
    if (A.get(0) == 0 || A.get(3) == 0) {
        modelGaussSeidel.setMensajeEstado("Error: Elementos diagonales no pueden ser cero");
        modelGaussSeidel.setConvergio(false);
        return modelGaussSeidel;
    }
    
    // Verificar convergencia diagonal dominante
    if (!esDiagonalDominante(A, 2)) {
        modelGaussSeidel.setMensajeEstado("Advertencia: La matriz no es diagonalmente dominante. La convergencia no está garantizada.");
    }
    
    log.info("=== INICIANDO MÉTODO DE GAUSS-SEIDEL 2x2 ===");
    log.info("Sistema: " + A.get(0) + "x + " + A.get(1) + "y = " + B.get(0));
    log.info("         " + A.get(2) + "x + " + A.get(3) + "y = " + B.get(1));
    log.info("Valores iniciales: x0 = " + valoresIniciales.get(0) + ", y0 = " + valoresIniciales.get(1));
    log.info("Tolerancia: " + modelGaussSeidel.getTolerancia() + "%");
    
    double x = valoresIniciales.get(0);
    double y = valoresIniciales.get(1);
    
    ArrayList<ArrayList<Double>> todasIteraciones = new ArrayList<>();
    ArrayList<Double> erroresRelativos = new ArrayList<>();
    
    boolean convergio = false;
    int iteracion = 0;
    
    while (iteracion < modelGaussSeidel.getMaxIteraciones() && !convergio) {
        iteracion++;
        
        // Guardar valores anteriores para cálculo de error
        double x_anterior = x;
        double y_anterior = y;
        
        // GAUSS-SEIDEL: Usar valores actualizados inmediatamente
        // x(k+1) = (b1 - a12*y(k)) / a11
        x = (B.get(0) - A.get(1) * y) / A.get(0);
        
        // y(k+1) = (b2 - a21*x(k+1)) / a22  ← Nota: usa x recién calculado
        y = (B.get(1) - A.get(2) * x) / A.get(3);
        
        // Calcular errores relativos
        double error_x = (iteracion > 1) ? Funciones.ErrorRelativo(x, x_anterior) : 100.0;
        double error_y = (iteracion > 1) ? Funciones.ErrorRelativo(y, y_anterior) : 100.0;
        double error_max = Math.max(error_x, error_y);
        
        log.info("Iteración " + iteracion + ":");
        log.info("  x(" + iteracion + ") = (" + B.get(0) + " - " + A.get(1) + "*" + y_anterior + ") / " + A.get(0) + " = " + x);
        log.info("  y(" + iteracion + ") = (" + B.get(1) + " - " + A.get(2) + "*" + x + ") / " + A.get(3) + " = " + y);
        log.info("  Error x: " + error_x + "%, Error y: " + error_y + "%");
        
        // Guardar iteración
        ArrayList<Double> iteracionActual = new ArrayList<>();
        iteracionActual.add((double) iteracion);
        iteracionActual.add(x);
        iteracionActual.add(y);
        iteracionActual.add(error_x);
        iteracionActual.add(error_y);
        todasIteraciones.add(iteracionActual);
        erroresRelativos.add(error_max);
        
        // Verificar convergencia
        if (error_max < modelGaussSeidel.getTolerancia()) {
            convergio = true;
            log.info("¡Convergencia alcanzada en la iteración " + iteracion + "!");
        }
        
        // Actualizar modelo para mostrar última iteración
        if (iteracion >= 2) {
            modelGaussSeidel.setC1K(x_anterior);
            modelGaussSeidel.setC2K(y_anterior);
            modelGaussSeidel.setC1Kmas1(x);
            modelGaussSeidel.setC2Kmas1(y);
            modelGaussSeidel.setEaC1(error_x);
            modelGaussSeidel.setEaC2(error_y);
        }
    }
    
    // Configurar resultados finales
    modelGaussSeidel.setIteraciones(todasIteraciones);
    modelGaussSeidel.setErroresRelativos(erroresRelativos);
    modelGaussSeidel.setIteracionesRealizadas(iteracion);
    modelGaussSeidel.setConvergio(convergio);
    
    ArrayList<Double> solucionFinal = new ArrayList<>();
    solucionFinal.add(x);
    solucionFinal.add(y);
    modelGaussSeidel.setSolucionFinal(solucionFinal);
    
    if (convergio) {
        modelGaussSeidel.setMensajeEstado("Convergencia exitosa en " + iteracion + " iteraciones");
    } else {
        modelGaussSeidel.setMensajeEstado("No se alcanzó convergencia en " + iteracion + " iteraciones");
    }
    
    return modelGaussSeidel;
}

private GaussSeidel resolverGaussSeidel3x3(GaussSeidel modelGaussSeidel) {
    ArrayList<Double> A = modelGaussSeidel.getMatrizA();
    ArrayList<Double> B = modelGaussSeidel.getVectorB();
    ArrayList<Double> valoresIniciales = modelGaussSeidel.getValoresIniciales();
    
    // Verificar convergencia diagonal dominante
    if (!esDiagonalDominante(A, 3)) {
        modelGaussSeidel.setMensajeEstado("Advertencia: La matriz no es diagonalmente dominante. La convergencia no está garantizada.");
    }
    
    log.info("=== INICIANDO MÉTODO DE GAUSS-SEIDEL 3x3 ===");
    log.info("Valores iniciales: x0 = " + valoresIniciales.get(0) + ", y0 = " + valoresIniciales.get(1) + ", z0 = " + valoresIniciales.get(2));
    log.info("Tolerancia: " + modelGaussSeidel.getTolerancia() + "%");
    
    double x = valoresIniciales.get(0);
    double y = valoresIniciales.get(1);
    double z = valoresIniciales.get(2);
    
    ArrayList<ArrayList<Double>> todasIteraciones = new ArrayList<>();
    ArrayList<Double> erroresRelativos = new ArrayList<>();
    
    boolean convergio = false;
    int iteracion = 0;
    
    while (iteracion < modelGaussSeidel.getMaxIteraciones() && !convergio) {
        iteracion++;
        
        // Guardar valores anteriores para cálculo de error
        double x_anterior = x;
        double y_anterior = y;
        double z_anterior = z;
        
        // GAUSS-SEIDEL: Usar valores actualizados inmediatamente
        // x(k+1) = (b1 - a12*y(k) - a13*z(k)) / a11
        x = (B.get(0) - A.get(1) * y - A.get(2) * z) / A.get(0);
        
        // y(k+1) = (b2 - a21*x(k+1) - a23*z(k)) / a22
        y = (B.get(1) - A.get(3) * x - A.get(5) * z) / A.get(4);
        
        // z(k+1) = (b3 - a31*x(k+1) - a32*y(k+1)) / a33
        z = (B.get(2) - A.get(6) * x - A.get(7) * y) / A.get(8);
        
        // Calcular errores relativos
        double error_x = (iteracion > 1) ? Funciones.ErrorRelativo(x, x_anterior) : 100.0;
        double error_y = (iteracion > 1) ? Funciones.ErrorRelativo(y, y_anterior) : 100.0;
        double error_z = (iteracion > 1) ? Funciones.ErrorRelativo(z, z_anterior) : 100.0;
        double error_max = Math.max(Math.max(error_x, error_y), error_z);
        
        log.info("Iteración " + iteracion + ":");
        log.info("  x(" + iteracion + ") = " + x);
        log.info("  y(" + iteracion + ") = " + y);
        log.info("  z(" + iteracion + ") = " + z);
        log.info("  Errores: x=" + error_x + "%, y=" + error_y + "%, z=" + error_z + "%");
        
        // Guardar iteración
        ArrayList<Double> iteracionActual = new ArrayList<>();
        iteracionActual.add((double) iteracion);
        iteracionActual.add(x);
        iteracionActual.add(y);
        iteracionActual.add(z);
        iteracionActual.add(error_x);
        iteracionActual.add(error_y);
        iteracionActual.add(error_z);
        todasIteraciones.add(iteracionActual);
        erroresRelativos.add(error_max);
        
        // Verificar convergencia
        if (error_max < modelGaussSeidel.getTolerancia()) {
            convergio = true;
            log.info("¡Convergencia alcanzada en la iteración " + iteracion + "!");
        }
        
        // Actualizar modelo para mostrar última iteración
        if (iteracion >= 2) {
            modelGaussSeidel.setC1K(x_anterior);
            modelGaussSeidel.setC2K(y_anterior);
            modelGaussSeidel.setC3K(z_anterior);
            modelGaussSeidel.setC1Kmas1(x);
            modelGaussSeidel.setC2Kmas1(y);
            modelGaussSeidel.setC3Kmas1(z);
            modelGaussSeidel.setEaC1(error_x);
            modelGaussSeidel.setEaC2(error_y);
            modelGaussSeidel.setEaC3(error_z);
        }
    }
    
    // Configurar resultados finales
    modelGaussSeidel.setIteraciones(todasIteraciones);
    modelGaussSeidel.setErroresRelativos(erroresRelativos);
    modelGaussSeidel.setIteracionesRealizadas(iteracion);
    modelGaussSeidel.setConvergio(convergio);
    
    ArrayList<Double> solucionFinal = new ArrayList<>();
    solucionFinal.add(x);
    solucionFinal.add(y);
    solucionFinal.add(z);
    modelGaussSeidel.setSolucionFinal(solucionFinal);
    
    if (convergio) {
        modelGaussSeidel.setMensajeEstado("Convergencia exitosa en " + iteracion + " iteraciones");
    } else {
        modelGaussSeidel.setMensajeEstado("No se alcanzó convergencia en " + iteracion + " iteraciones");
    }
    
    return modelGaussSeidel;
}

private GaussSeidel resolverGaussSeidel4x4(GaussSeidel modelGaussSeidel) {
    ArrayList<Double> A = modelGaussSeidel.getMatrizA();
    ArrayList<Double> B = modelGaussSeidel.getVectorB();
    ArrayList<Double> valoresIniciales = modelGaussSeidel.getValoresIniciales();
    
    // Verificar convergencia diagonal dominante
    if (!esDiagonalDominante(A, 4)) {
        modelGaussSeidel.setMensajeEstado("Advertencia: La matriz no es diagonalmente dominante. La convergencia no está garantizada.");
    }
    
    log.info("=== INICIANDO MÉTODO DE GAUSS-SEIDEL 4x4 ===");
    log.info("Valores iniciales: x0=" + valoresIniciales.get(0) + ", y0=" + valoresIniciales.get(1) + 
             ", z0=" + valoresIniciales.get(2) + ", w0=" + valoresIniciales.get(3));
    log.info("Tolerancia: " + modelGaussSeidel.getTolerancia() + "%");
    
    double x = valoresIniciales.get(0);
    double y = valoresIniciales.get(1);
    double z = valoresIniciales.get(2);
    double w = valoresIniciales.get(3);
    
    ArrayList<ArrayList<Double>> todasIteraciones = new ArrayList<>();
    ArrayList<Double> erroresRelativos = new ArrayList<>();
    
    boolean convergio = false;
    int iteracion = 0;
    
    while (iteracion < modelGaussSeidel.getMaxIteraciones() && !convergio) {
        iteracion++;
        
        // Guardar valores anteriores para cálculo de error
        double x_anterior = x;
        double y_anterior = y;
        double z_anterior = z;
        double w_anterior = w;
        
        // GAUSS-SEIDEL: Usar valores actualizados inmediatamente
        // x(k+1) = (b1 - a12*y(k) - a13*z(k) - a14*w(k)) / a11
        x = (B.get(0) - A.get(1)*y - A.get(2)*z - A.get(3)*w) / A.get(0);
        
        // y(k+1) = (b2 - a21*x(k+1) - a23*z(k) - a24*w(k)) / a22
        y = (B.get(1) - A.get(4)*x - A.get(6)*z - A.get(7)*w) / A.get(5);
        
        // z(k+1) = (b3 - a31*x(k+1) - a32*y(k+1) - a34*w(k)) / a33
        z = (B.get(2) - A.get(8)*x - A.get(9)*y - A.get(11)*w) / A.get(10);
        
        // w(k+1) = (b4 - a41*x(k+1) - a42*y(k+1) - a43*z(k+1)) / a44
        w = (B.get(3) - A.get(12)*x - A.get(13)*y - A.get(14)*z) / A.get(15);
        
        // Calcular errores relativos
        double error_x = (iteracion > 1) ? Funciones.ErrorRelativo(x, x_anterior) : 100.0;
        double error_y = (iteracion > 1) ? Funciones.ErrorRelativo(y, y_anterior) : 100.0;
        double error_z = (iteracion > 1) ? Funciones.ErrorRelativo(z, z_anterior) : 100.0;
        double error_w = (iteracion > 1) ? Funciones.ErrorRelativo(w, w_anterior) : 100.0;
        double error_max = Math.max(Math.max(error_x, error_y), Math.max(error_z, error_w));
        
        log.info("Iteración " + iteracion + ":");
        log.info("  x(" + iteracion + ") = " + x);
        log.info("  y(" + iteracion + ") = " + y);
        log.info("  z(" + iteracion + ") = " + z);
        log.info("  w(" + iteracion + ") = " + w);
        log.info("  Errores: x=" + error_x + "%, y=" + error_y + "%, z=" + error_z + "%, w=" + error_w + "%");
        
        // Guardar iteración
        ArrayList<Double> iteracionActual = new ArrayList<>();
        iteracionActual.add((double) iteracion);
        iteracionActual.add(x);
        iteracionActual.add(y);
        iteracionActual.add(z);
        iteracionActual.add(w);
        iteracionActual.add(error_x);
        iteracionActual.add(error_y);
        iteracionActual.add(error_z);
        iteracionActual.add(error_w);
        todasIteraciones.add(iteracionActual);
        erroresRelativos.add(error_max);
        
        // Verificar convergencia
        if (error_max < modelGaussSeidel.getTolerancia()) {
            convergio = true;
            log.info("¡Convergencia alcanzada en la iteración " + iteracion + "!");
        }
        
        // Actualizar modelo para mostrar última iteración
        if (iteracion >= 2) {
            modelGaussSeidel.setC1K(x_anterior);
            modelGaussSeidel.setC2K(y_anterior);
            modelGaussSeidel.setC3K(z_anterior);
            modelGaussSeidel.setC4K(w_anterior);
            modelGaussSeidel.setC1Kmas1(x);
            modelGaussSeidel.setC2Kmas1(y);
            modelGaussSeidel.setC3Kmas1(z);
            modelGaussSeidel.setC4Kmas1(w);
            modelGaussSeidel.setEaC1(error_x);
            modelGaussSeidel.setEaC2(error_y);
            modelGaussSeidel.setEaC3(error_z);
            modelGaussSeidel.setEaC4(error_w);
        }
    }
    
    // Configurar resultados finales
    modelGaussSeidel.setIteraciones(todasIteraciones);
    modelGaussSeidel.setErroresRelativos(erroresRelativos);
    modelGaussSeidel.setIteracionesRealizadas(iteracion);
    modelGaussSeidel.setConvergio(convergio);
    
    ArrayList<Double> solucionFinal = new ArrayList<>();
    solucionFinal.add(x);
    solucionFinal.add(y);
    solucionFinal.add(z);
    solucionFinal.add(w);
    modelGaussSeidel.setSolucionFinal(solucionFinal);
    
    if (convergio) {
        modelGaussSeidel.setMensajeEstado("Convergencia exitosa en " + iteracion + " iteraciones");
    } else {
        modelGaussSeidel.setMensajeEstado("No se alcanzó convergencia en " + iteracion + " iteraciones. Máximo error: " + 
                                       String.format("%.6f", erroresRelativos.get(erroresRelativos.size()-1)) + "%");
    }
    
    log.info("=== RESULTADO FINAL 4x4 ===");
    log.info("X1 = " + solucionFinal.get(0));
    log.info("X2 = " + solucionFinal.get(1));
    log.info("X3 = " + solucionFinal.get(2));
    log.info("X4 = " + solucionFinal.get(3));
    log.info("Convergencia: " + convergio);
    log.info("Iteraciones: " + iteracion);
    
    return modelGaussSeidel;
}
}
