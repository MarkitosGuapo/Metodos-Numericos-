
package mx.edu.itses.mrc.MetodosNumericos.domain;

import lombok.Data;

@Data //libreria para automatizar codigo
public class Biseccion {
    
    private String FX;  // Funcion a evaluar
    private double XL;
    private double XU;
    private double XR;
    private double FXL;
    private double FXU;
    private double FXR;
    private double Ea;
    private int IteracionesMaximas;
    
    
    
    
    
    
    
}
