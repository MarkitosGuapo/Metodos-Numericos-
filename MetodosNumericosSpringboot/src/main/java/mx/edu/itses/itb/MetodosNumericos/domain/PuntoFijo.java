package mx.edu.itses.itb.MetodosNumericos.domain;
import lombok.Data;
@Data
public class PuntoFijo {
    private double XL;          
    private double XR;            
    private String GXI;           
    private double Ea;           
    private int IteracionesMaximas; 
}