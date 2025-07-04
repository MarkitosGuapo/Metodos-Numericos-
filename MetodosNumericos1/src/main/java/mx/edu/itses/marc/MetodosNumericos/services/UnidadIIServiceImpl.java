package mx.edu.itses.marc.MetodosNumericos.services;

import java.util.ArrayList;
import lombok.extern.slf4j.Slf4j;
import mx.edu.itses.marc.MetodosNumericos.domain.Biseccion;
import mx.edu.itses.marc.MetodosNumericos.domain.NewRapson;
import mx.edu.itses.marc.MetodosNumericos.domain.PuntoFijo;
import mx.edu.itses.marc.MetodosNumericos.domain.ReglaFalsa;
import mx.edu.itses.marc.MetodosNumericos.domain.Secante;
import mx.edu.itses.marc.MetodosNumericos.domain.SecanteModificada;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class UnidadIIServiceImpl implements UnidadIIService {

    @Override
    public ArrayList<Biseccion> AlgoritmoBiseccion(Biseccion biseccion){
        ArrayList<Biseccion> respuesta = new ArrayList<>();
        double XL, XU, XRa, XRn, FXL, FXU, FXR, Ea;

        XL = biseccion.getXL();
        XU = biseccion.getXU();
        XRa = 0;
        Ea = 100;
        // Verificamos que en el intervalo definido haya un cambio de signo
        FXL = Funciones.Ecuacion(biseccion.getFX(), XL);
        FXU = Funciones.Ecuacion(biseccion.getFX(), XU);
        if (FXL * FXU < 0) {
            for (int i = 1; i <= biseccion.getIteracionesMaximas(); i++) {
                XRn = (XL + XU) / 2;
                FXL = Funciones.Ecuacion(biseccion.getFX(), XL);
                FXU = Funciones.Ecuacion(biseccion.getFX(), XU);
                FXR = Funciones.Ecuacion(biseccion.getFX(), XRn);
                if (i != 1) {
                    Ea = Funciones.ErrorRelativo(XRn, XRa);
                }
                Biseccion renglon = new Biseccion();
                renglon.setXL(XL);
                renglon.setXU(XU);
                renglon.setXR(XRn);
                renglon.setFXL(FXL);
                renglon.setFXU(FXU);
                renglon.setFXR(FXR);
                renglon.setEa(Ea);
                if (FXL * FXR < 0) {
                    XU = XRn;
                } else if (FXL * FXR > 0) {
                    XL = XRn;
                } else if (FXL * FXR == 0) {
                    break;
                }
                XRa = XRn;
                respuesta.add(renglon);
                if (Ea <= biseccion.getEa()) {
                    break;
                }
            }
        } else {
            Biseccion renglon = new Biseccion();
           // renglon.setIntervaloInvalido(true);
            respuesta.add(renglon);
        }

        return respuesta;
    }

    @Override
        public ArrayList<ReglaFalsa> AlgoritmoReglaFalsa(ReglaFalsa reglafalsa){
        ArrayList<ReglaFalsa> respuesta = new ArrayList<>();
        double XL, XU, XRa, XRn, FXL, FXU, FXR, Ea;

        XL = reglafalsa.getXL();
        XU = reglafalsa.getXU();
        XRa = 0;
        Ea = 100;
        // Verificamos que en el intervalo definido haya un cambio de signo
        FXL = Funciones.Ecuacion(reglafalsa.getFX(), XL);
        FXU = Funciones.Ecuacion(reglafalsa.getFX(), XU);
        if (FXL * FXU < 0) {
            for (int i = 1; i <= reglafalsa.getIteracionesMaximas(); i++) {
                XRn = XU-(((FXU)*(XL-XU)) / (FXL-FXU));
                FXL = Funciones.Ecuacion(reglafalsa.getFX(), XL);
                FXU = Funciones.Ecuacion(reglafalsa.getFX(), XU);
                FXR = Funciones.Ecuacion(reglafalsa.getFX(), XRn);
                if (i != 1) {
                    Ea = Funciones.ErrorRelativo(XRn, XRa);
                }
                ReglaFalsa renglon = new ReglaFalsa();
                renglon.setXL(XL);
                renglon.setXU(XU);
                renglon.setXR(XRn);
                renglon.setFXL(FXL);
                renglon.setFXU(FXU);
                renglon.setFXR(FXR);
                renglon.setEa(Ea);
                if (FXL * FXR < 0) {
                    XU = XRn;
                } else if (FXL * FXR > 0) {
                    XL = XRn;
                } else if (FXL * FXR == 0) {
                    break;
                }
                XRa = XRn;
                respuesta.add(renglon);
                if (Ea <= reglafalsa.getEa()) {
                    break;
                }
            }
        } else {
            ReglaFalsa renglon = new ReglaFalsa();
           // renglon.setIntervaloInvalido(true);
            respuesta.add(renglon);
        }

        return respuesta;
    }

  

  
    @Override
    public ArrayList<PuntoFijo> AlgoritmoDePuntoFijo(PuntoFijo puntofijo) {
       ArrayList<PuntoFijo> respuesta = new ArrayList<>();
    double XL = puntofijo.getXL();
    double GX, FX, Ea = 100, XRa = 0;

    for (int i = 1; i <= puntofijo.getIteracionesMaximas(); i++) {
        GX = Funciones.Ecuacion(puntofijo.getGXstring(), XL); // g(x)
        FX = Funciones.Ecuacion(puntofijo.getFXstring(), GX); // f(x)

        if (i != 1) {
            Ea = Funciones.ErrorRelativo(GX, XRa);
        }

        PuntoFijo renglon = new PuntoFijo();
        renglon.setXL(XL);
        renglon.setGX(GX);
        renglon.setFX(FX);
        renglon.setEa(Ea);

        respuesta.add(renglon);

        if (Ea <= puntofijo.getEaPermitido()) {
            break;
        }

        XRa = GX;
        XL = GX;
    }

    return respuesta;
    }

    @Override
    public ArrayList<NewRapson> AlgoritmoDeNewRpason(NewRapson rapson) {
    ArrayList<NewRapson> respuesta = new ArrayList<>();
    double XI, XI1, FXI, DerivadaFXI, Ea;
    XI = rapson.getXI();
    Ea = 100;

    for (int i = 1; i <= rapson.getIteracionesMaximas(); i++) {
        FXI = Funciones.Ecuacion(rapson.getFX(), XI);
        DerivadaFXI = Funciones.Derivada(rapson.getFX(), XI); 

        if (DerivadaFXI == 0) {
            // Evitar división por cero
            break;
        }

        XI1 = XI - (FXI / DerivadaFXI);

        if (i != 1) {
            Ea = Funciones.ErrorRelativo(XI1, XI);
        }

        NewRapson renglon = new NewRapson();
        renglon.setFX(rapson.getFX());
        renglon.setXI(XI);
        renglon.setFXI(FXI);
        renglon.setDerivadaFXI(DerivadaFXI);
        renglon.setXI1(XI1);
        renglon.setEa(Ea);
        renglon.setIteracionesMaximas(rapson.getIteracionesMaximas());
        respuesta.add(renglon);

        if (Ea <= rapson.getEa()) {
            break;
        }

        XI = XI1;
    }

    return respuesta;
}

    @Override
    public ArrayList<SecanteModificada> AlgoritmoDeSecanteModificada(SecanteModificada secantemodificada) {
   ArrayList<SecanteModificada> respuesta = new ArrayList<>();
    double XL, X1, FX, FXSIGMA, Ea, XRa, sigma;

    XL = secantemodificada.getXL(); // Valor inicial
    XRa = 0;
    sigma = 0.01; // Diferencia pequeña σ
    Ea = 100;

    // Validar valores por defecto
    if (secantemodificada.getIteracionesMaximas() == 0) {
        secantemodificada.setIteracionesMaximas(50);
    }

    if (secantemodificada.getEa() == 0) {
        secantemodificada.setEa(0.001);
    }

    for (int i = 1; i <= secantemodificada.getIteracionesMaximas(); i++) {
    
        FX = Funciones.Ecuacion(secanteodificada.getFX(), XL);

      
        X1 = XL + sigma;
        FXSIGMA = Funciones.Ecuacion(secantemodificada.getFx(), X1);

    
        double denominador = FXSIGMA - FX;
        if (denominador == 0) {
            break; // Evita división por cero
        }

        // Calcular xi+1 usando secante modificada
        double XI1 = XL - (FX * sigma) / denominador;

        // Calcular error relativo
        if (i != 1) {
            Ea = Funciones.ErrorRelativo(XI1, XRa);
        }

        // Crear objeto con resultados de la iteración
        SecanteModificada renglon = new SecanteModificada();
        renglon.setXL(XL);
        renglon.setX1(X1);
        renglon.setFX(FX);
        renglon.setFXSIGMA(FXSIGMA);
        renglon.setEa(Ea);
        renglon.setIteracionesMaximas(secantemodificada.getIteracionesMaximas());
        renglon.setFx(secantemodificada.getFx());

        respuesta.add(renglon);

       
        if (Ea <= secantemodificada.getEa()) {
            break;
        }

        // Preparar siguiente iteración
        XRa = XI1;
        XL = XI1;
    }

    return respuesta;
    }
    
    @Override
public ArrayList<Secante> AlgoritmoDeSecante(Secante secante) {
    ArrayList<Secante> respuesta = new ArrayList<>();
    
    double x0 = secante.getX0();
    double x1 = secante.getX1();
    double Ea = 100;
    double Xr = 0;
    double XrAnt = 0;
    
    for (int i = 1; i <= secante.getIteracionesMaximas(); i++) {
        double fx0 = Funciones.Ecuacion(secante.getFx(), x0);
        double fx1 = Funciones.Ecuacion(secante.getFx(), x1);
        
        if ((fx1 - fx0) == 0) {
            break; // evita división por cero
        }
        
        Xr = x1 - fx1 * (x1 - x0) / (fx1 - fx0);
        
        if (i != 1) {
            Ea = Funciones.ErrorRelativo(Xr, XrAnt);
        }
        
        Secante renglon = new Secante();
        renglon.setX0(x0);
        renglon.setX1(x1);
        renglon.setFx(secante.getFx());
        renglon.setEa(Ea);
        
        respuesta.add(renglon);
        
        if (Ea <= secante.getEa()) {
            break;
        }
        
        x0 = x1;
        x1 = Xr;
        XrAnt = Xr;
    }
    
    return respuesta;
}

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
   
   
    






   

