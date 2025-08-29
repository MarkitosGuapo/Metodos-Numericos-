/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.edu.itses.itb.MetodosNumericos.domain;

import lombok.Data;

/**
 *
 * @author mar_6
 */
@Data
public class NewtonRaphson {
   private double XI;        // Valor actual de xi
    private double FX;        // Valor de f(xi)
    private double FPrima;    // Valor de f'(xi)
    private double XINext;    // Valor de xi+1
    private double Ea;        // Error aproximado
    private String funcionStr;        // La función como string
    private int iteracionesMaximas;   // Máximo número de iteraciones
    private double errorEsperado;     // Error deseado para convergencia
}