package mx.edu.itses.marc.MetodosNumericos.domain;

import lombok.Data;

@Data
public class Secante {
    private double X0;            
    private double X1;               
    private String fx;            
    private double Ea;             
    private int IteracionesMaximas; 
}
