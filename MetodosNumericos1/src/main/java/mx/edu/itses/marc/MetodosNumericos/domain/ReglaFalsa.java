package mx.edu.itses.marc.MetodosNumericos.domain;

import lombok.Data;

@Data
public class ReglaFalsa {
    
    private String FX; //Funcion a Evaluar
    private double XL;
    private double XU;
    private double XR;
    private double FXL;
    private double FXU;
    private double FXR;
    private double Ea;
    private int IteracionesMaximas;
}
