
package mx.edu.itses.marc.MetodosNumericos.domain;

import lombok.Data;

@Data
public class PuntoFijo {
    private double XL;
    private double GX;
    private double FX;
    private double Ea;
    private int IteracionesMaximas;
    private double EaPermitido;
    private String FXstring;
    private String GXstring;

}
