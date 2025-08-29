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
public class Secante {

    private double XImenos1;

    private double XI;
    private double XIMas1;
    private double FXImenos1;
    private double FXI;
    private double Ea;
    private double IteracionesMaximas;
    private String funcionStr;
    private double errorEsperado;
}
