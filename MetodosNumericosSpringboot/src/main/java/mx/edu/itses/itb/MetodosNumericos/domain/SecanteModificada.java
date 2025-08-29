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
public class SecanteModificada {
     private double XI;                    
    private double XImas1;              
    private double FXI;                  
    private double FXImasSigma;         
    private double Ea;                
    private double iteracionesMaximas;  
    private double errorEsperado;       
    private String funcionStr;           
    private double sigma;      
}
