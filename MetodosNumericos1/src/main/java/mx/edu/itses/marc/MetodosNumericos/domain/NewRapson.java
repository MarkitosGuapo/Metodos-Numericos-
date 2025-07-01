/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.edu.itses.marc.MetodosNumericos.domain;

import lombok.Data;



@Data
public class NewRapson {
      private String FX; //Funcion a Evaluar
    private double XI;
    private double FXI;
    private double DerivadaFXI;
    private double XI1; //XI + 1
    private double Ea;
    private int IteracionesMaximas;
}
