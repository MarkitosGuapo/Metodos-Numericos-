package mx.edu.itses.itb.MetodosNumericos.services;

import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import mx.edu.itses.itb.MetodosNumericos.domain.Biseccion;
import mx.edu.itses.itb.MetodosNumericos.domain.NewtonRaphson;
import mx.edu.itses.itb.MetodosNumericos.domain.PuntoFijo;
import mx.edu.itses.itb.MetodosNumericos.domain.ReglaFalsa;
import mx.edu.itses.itb.MetodosNumericos.domain.Secante;
import mx.edu.itses.itb.MetodosNumericos.domain.SecanteModificada;
import mx.edu.itses.itb.MetodosNumericos.services.Funciones;
import static mx.edu.itses.itb.MetodosNumericos.services.Funciones.Ecuacion;
import static mx.edu.itses.itb.MetodosNumericos.services.Funciones.ErrorRelativo;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UnidadIIServiceImpl implements UnidadIIService {

    // Bisección
    @Override
    public ArrayList<Biseccion> AlgoritmoBiseccion(Biseccion biseccion) {
        ArrayList<Biseccion> respuesta = new ArrayList<>();
        double XL, XU, XRa, XRn, FXL, FXU, FXR, Ea;

        XL = biseccion.getXL();
        XU = biseccion.getXU();
        XRa = 0;
        Ea = 100;
        
        // DEBUG: Mostrar valores recibidos
     
        log.info("FX: " + biseccion.getFX());
        log.info("XL: " + XL + ", XU: " + XU);
        log.info("Iteraciones Máximas: " + biseccion.getIteracionesMaximas());
        log.info("Error Esperado: " + biseccion.getEa());
        
        // Verificamos que en el intervalo definido haya un cambio de signo
        FXL = Funciones.Ecuacion(biseccion.getFX(), XL);
        FXU = Funciones.Ecuacion(biseccion.getFX(), XU);
        
        // DEBUG: Mostrar evaluaciones de función
        log.info("F(XL) = F(" + XL + ") = " + FXL);
        log.info("F(XU) = F(" + XU + ") = " + FXU);
        log.info("F(XL) * F(XU) = " + (FXL * FXU));
        
        if (FXL * FXU < 0) {
            log.info("HAY CAMBIO DE SIGNO - Iniciando iteraciones");
            for (int i = 1; i <= biseccion.getIteracionesMaximas(); i++) {
                XRn = (XL + XU) / 2;
                FXL = Funciones.Ecuacion(biseccion.getFX(), XL);
                FXU = Funciones.Ecuacion(biseccion.getFX(), XU);
                FXR = Funciones.Ecuacion(biseccion.getFX(), XRn);
                
                if (i != 1) {
                    Ea = Funciones.ErrorRelativo(XRn, XRa);
                }
                
                log.info("Iteración " + i + ": XL=" + XL + ", XU=" + XU + ", XR=" + XRn + ", Error=" + Ea);
                
                Biseccion renglon = new Biseccion();
                renglon.setXL(XL);
                renglon.setXU(XU);
                renglon.setXR(XRn);
                renglon.setFXL(FXL);
                renglon.setFXU(FXU);
                renglon.setFXR(FXR);
                renglon.setEa(Ea);
                
                respuesta.add(renglon);  // MOVER ANTES DE LOS CONDICIONALES
                
                if (FXL * FXR < 0) {
                    XU = XRn;
                } else if (FXL * FXR > 0) {
                    XL = XRn;
                } else if (FXL * FXR == 0) {
                    log.info("Raíz exacta encontrada en iteración " + i);
                    break;
                }
                
                XRa = XRn;
                
                if (Ea <= biseccion.getEa()) {
                    log.info("Convergencia alcanzada en iteración " + i);
                    break;
                }
            }
        } else {
            log.error("NO HAY CAMBIO DE SIGNO - Agregando objeto vacío");
            Biseccion renglon = new Biseccion();
            respuesta.add(renglon);
        }
        
        log.info("Respuesta final: " + respuesta.size() + " elementos");
        return respuesta;
    }

    // Regla Falsa
    @Override
    public ArrayList<ReglaFalsa> AlgoritmoDeReglaFalsa(ReglaFalsa falsa) {
        ArrayList<ReglaFalsa> respuesta = new ArrayList<>();
        
        double XL = falsa.getXL();
        double XU = falsa.getXU();
        double XRn = 0, XRa = 0, FXL, FXU, FXR, Ea = 100;
        
        // DEBUG
       
        log.info("FX: " + falsa.getFX());
        log.info("XL: " + XL + ", XU: " + XU);
        
        // Evaluar función en los extremos
        FXL = Funciones.Ecuacion(falsa.getFX(), XL);
        FXU = Funciones.Ecuacion(falsa.getFX(), XU);
        
        log.info("F(XL) = " + FXL + ", F(XU) = " + FXU);
        
        if (FXL * FXU >= 0) {
            
            ReglaFalsa renglon = new ReglaFalsa();
            renglon.setXL(XL);
            renglon.setXU(XU);
            renglon.setFXL(FXL);
            renglon.setFXU(FXU);
            renglon.setEa(-1); // Indicador de error
            respuesta.add(renglon);
            return respuesta;
        }
        
        for (int i = 0; i < falsa.getIteracionesMaximas(); i++) {
            XRn = XU - FXU * (XL - XU) / (FXL - FXU);
            FXR = Funciones.Ecuacion(falsa.getFX(), XRn);
            
            if (i > 0) {
                Ea = Funciones.ErrorRelativo(XRn, XRa);
            }
            
            log.info("Regla Falsa Iteración " + (i+1) + ": XR=" + XRn + ", Error=" + Ea);
            
            // Guardar iteración
            ReglaFalsa renglon = new ReglaFalsa();
            renglon.setXL(XL);
            renglon.setXU(XU);
            renglon.setXR(XRn);
            renglon.setFXL(FXL);
            renglon.setFXU(FXU);
            renglon.setFXR(FXR);
            renglon.setEa(Ea);
            respuesta.add(renglon);  
            
            // Verificar convergencia
            if (Math.abs(FXR) < 1e-10 || Ea <= falsa.getEa()) {
                break;
            }
            
         
            if (FXL * FXR < 0) {
                XU = XRn;
                FXU = FXR;
            } else if (FXL * FXR > 0) {
                XL = XRn;
                FXL = FXR;
            } else {
                break;
            }
            
            XRa = XRn;
            
            if (Ea <= falsa.getEa()) break;
        }
        
        return respuesta;
    }

    // Punto fijo
    @Override
    public ArrayList<PuntoFijo> AlgoritmoDePuntoFijo(String gx, PuntoFijo puntofijo, double tolerancia) {
        ArrayList<PuntoFijo> iteraciones = new ArrayList<>();

        // DEBUG
        
        log.info("g(x): " + gx);
        log.info("X0: " + puntofijo.getXL());
        log.info("Tolerancia: " + tolerancia);

        double xAnterior = puntofijo.getXL(); 
        double xNuevo;
        double error = 100; 
        int iteracion = 0;

        while (iteracion < puntofijo.getIteracionesMaximas() && error > tolerancia) {
            xNuevo = Funciones.Ecuacion(gx, xAnterior);
            
            log.info("Punto Fijo Iteración " + (iteracion+1) + ": g(" + xAnterior + ") = " + xNuevo);

            if (iteracion > 0) {
                error = Funciones.ErrorRelativo(xNuevo, xAnterior);
            }

            PuntoFijo paso = new PuntoFijo();
            paso.setXL(xAnterior);  
            paso.setXR(xNuevo);     
            paso.setGXI(gx);        
            paso.setEa(error);
            paso.setIteracionesMaximas(iteracion + 1);

            iteraciones.add(paso);

            xAnterior = xNuevo;
            iteracion++;
        }

        return iteraciones;
    }

    
    //NewtonRaphson

    @Override
    public ArrayList<NewtonRaphson> AlgoritmoNewtonRaphson(NewtonRaphson newton) {
    ArrayList<NewtonRaphson> respuesta = new ArrayList<>();
    
    double xi = newton.getXI(); // Valor inicial
    double xiNuevo;
    double fxi, fPrimaXi;
    double Ea = 100;
    
    // DEBUG
    log.info("Función: " + newton.getFuncionStr());
    log.info("X inicial: " + xi);
    log.info("Iteraciones Máximas: " + newton.getIteracionesMaximas());
    log.info("Error Esperado: " + newton.getErrorEsperado());
    
    for (int i = 1; i <= newton.getIteracionesMaximas(); i++) {
        // Evaluar función en xi
        fxi = Funciones.Ecuacion(newton.getFuncionStr(), xi);
        
        // Evaluar derivada en xi
        fPrimaXi = Funciones.Derivada(newton.getFuncionStr(), xi);
        
        log.info("Iteración " + i + ": f(" + xi + ") = " + fxi + ", f'(" + xi + ") = " + fPrimaXi);
        
        // Verificar que la derivada no sea cero
        if (Math.abs(fPrimaXi) < 1e-12) {
            log.error("La derivada es cero en x = " + xi + ". No se puede continuar.");
            // Agregar resultado con error
            NewtonRaphson renglon = new NewtonRaphson();
            renglon.setXI(xi);
            renglon.setFX(fxi);
            renglon.setFPrima(fPrimaXi);
            renglon.setEa(-1); // Indicador de error
            respuesta.add(renglon);
            break;
        }
        
        // Calcular nuevo valor usando la fórmula de Newton-Raphson
        xiNuevo = xi - (fxi / fPrimaXi);
        
        // Calcular error relativo (excepto en la primera iteración)
        if (i > 1) {
            Ea = Funciones.ErrorRelativo(xiNuevo, xi);
        }
        
        log.info("Newton-Raphson Iteración " + i + ": xi=" + xi + ", xi+1=" + xiNuevo + ", Error=" + Ea);
        
        // Crear objeto para esta iteración usando el modelo correcto
        NewtonRaphson renglon = new NewtonRaphson();
        renglon.setXI(xi);           // Xi actual
        renglon.setFX(fxi);          // f(Xi)
        renglon.setFPrima(fPrimaXi); // f'(Xi)
        renglon.setXINext(xiNuevo);  // Xi+1 nuevo
        renglon.setEa(Ea);           // Error relativo
        
        respuesta.add(renglon);
        
        // Verificar convergencia
        if (Math.abs(fxi) < 1e-10) {
            log.info("Raíz encontrada en iteración " + i + ": f(" + xi + ") ≈ 0");
            break;
        }
        
        if (Ea <= newton.getErrorEsperado() && i > 1) {
            log.info("Convergencia alcanzada en iteración " + i);
            break;
        }
        
        // Actualizar xi para la siguiente iteración
        xi = xiNuevo;
    }
    
    log.info("Newton-Raphson finalizado: " + respuesta.size() + " iteraciones");
    return respuesta;
    }

    @Override
    public ArrayList<Secante> AlgoritmoSecante(Secante secante) {
      ArrayList<Secante> respuesta = new ArrayList<>();
    
    double xImenos1 = secante.getXImenos1(); // x_{i-1}
    double xi = secante.getXI();             // x_i
    double xiMas1;                           // x_{i+1}
    double fxImenos1, fxi;
    double Ea = 100;
    
    // DEBUG
    /*
    log.info("Función: " + secante.getFuncionStr());
    log.info("X_{i-1}: " + xImenos1 + ", X_i: " + xi);
    log.info("Iteraciones Máximas: " + secante.getIteracionesMaximas());
    log.info("Error Esperado: " + secante.getErrorEsperado());
    */
    for (int i = 1; i <= (int)secante.getIteracionesMaximas(); i++) {
        // Evaluar función en ambos puntos
        fxImenos1 = Funciones.Ecuacion(secante.getFuncionStr(), xImenos1);
        fxi = Funciones.Ecuacion(secante.getFuncionStr(), xi);
        
     //   log.info("Iteración " + i + ": f(" + xImenos1 + ") = " + fxImenos1 + ", f(" + xi + ") = " + fxi);
        
        // Verificar que no haya división por cero
        if (Math.abs(fxi - fxImenos1) < 1e-12) {
            log.error("División por cero en iteración " + i + ". f(xi) - f(xi-1) ≈ 0");
            // Agregar resultado con error
            Secante renglon = new Secante();
            renglon.setXImenos1(xImenos1);
            renglon.setXI(xi);
            renglon.setFXImenos1(fxImenos1);
            renglon.setFXI(fxi);
            renglon.setEa(-1); // Indicador de error
            respuesta.add(renglon);
            break;
        }
        
        // Calcular nuevo valor usando la fórmula de la Secante
        // x_{i+1} = x_i - f(x_i) * (x_i - x_{i-1}) / (f(x_i) - f(x_{i-1}))
        xiMas1 = xi - fxi * (xi - xImenos1) / (fxi - fxImenos1);
        
        // Calcular error relativo (excepto en la primera iteración)
        if (i > 1) {
            Ea = Funciones.ErrorRelativo(xiMas1, xi);
        }
        
     //   log.info("Secante Iteración " + i + ": xi-1=" + xImenos1 + ", xi=" + xi + ", xi+1=" + xiMas1 + ", Error=" + Ea);
        
        // Crear objeto para esta iteración
        Secante renglon = new Secante();
        renglon.setXImenos1(xImenos1);  
        renglon.setXI(xi);          
        renglon.setXIMas1(xiMas1);     
        renglon.setFXImenos1(fxImenos1); 
        renglon.setFXI(fxi);           
        renglon.setEa(Ea);            
        
        respuesta.add(renglon);
        
        // Verificar convergencia por función
        if (Math.abs(fxi) < 1e-10) {
            log.info("Raíz encontrada en iteración " + i + ": f(" + xi + ") ≈ 0");
            break;
        }
        
        // Verificar convergencia por error
        if (Ea <= secante.getErrorEsperado() && i > 1) {
            log.info("Convergencia alcanzada en iteración " + i);
            break;
        }
        
        // Actualizar valores para la siguiente iteración
        xImenos1 = xi;
        xi = xiMas1;
    }
    
    log.info("Secante finalizado: " + respuesta.size() + " iteraciones");
    return respuesta;
    }

    @Override
    public ArrayList<SecanteModificada> AlgoritmoSecanteModificado(SecanteModificada secanteModificada) {
    ArrayList<SecanteModificada> respuesta = new ArrayList<>();
    
    double xi = secanteModificada.getXI();           // Valor inicial
    double xiNuevo;                                  // x_{i+1}
    double fxi, fxiMasSigma;                        // f(xi) y f(xi + σ)
    double sigma = secanteModificada.getSigma();     // Valor de perturbación
    double Ea = 100;                                 // Error inicial
    
    // DEBUG
    /*
    log.info("=== SECANTE MODIFICADA ===");
    log.info("Función: " + secanteModificada.getFuncionStr());
    log.info("X inicial: " + xi);
    log.info("Sigma (δ): " + sigma);
    log.info("Iteraciones Máximas: " + secanteModificada.getIteracionesMaximas());
    log.info("Error Esperado: " + secanteModificada.getErrorEsperado());
    */
    
    
    for (int i = 1; i <= secanteModificada.getIteracionesMaximas(); i++) {
        // Evaluar función en xi
        fxi = Funciones.Ecuacion(secanteModificada.getFuncionStr(), xi);
        
        // Evaluar función en xi + sigma
        fxiMasSigma = Funciones.Ecuacion(secanteModificada.getFuncionStr(), xi + sigma);
        
      
        
        // Verificar que no haya división por cero
        if (Math.abs(fxiMasSigma - fxi) < 1e-12) {
           
            // Agregar resultado con error
            SecanteModificada renglon = new SecanteModificada();
            renglon.setXI(xi);
            renglon.setFXI(fxi);
            renglon.setFXImasSigma(fxiMasSigma);
            renglon.setEa(-1); // Indicador de error
            respuesta.add(renglon);
            break;
        }
        
        // Calcular nuevo valor usando la fórmula de Secante Modificada
        // x_{i+1} = x_i - f(x_i) * σ / (f(x_i + σ) - f(x_i))
        xiNuevo = xi - (fxi * sigma) / (fxiMasSigma - fxi);
        
        // Calcular error relativo (excepto en la primera iteración)
        if (i > 1) {
            Ea = Funciones.ErrorRelativo(xiNuevo, xi);
        }
        
        log.info("Secante Modificada Iteración " + i + 
                ": xi=" + xi + ", xi+1=" + xiNuevo + ", Error=" + Ea);
        
        // Crear objeto para esta iteración
        SecanteModificada renglon = new SecanteModificada();
        renglon.setXI(xi);                    // xi actual
        renglon.setXImas1(xiNuevo);          // xi+1 nuevo
        renglon.setFXI(fxi);                 // f(xi)
        renglon.setFXImasSigma(fxiMasSigma); // f(xi + σ)
        renglon.setEa(Ea);                   // Error relativo
        
        respuesta.add(renglon);
        
        // Verificar convergencia por función
        if (Math.abs(fxi) < 1e-10) {
            log.info("Raíz encontrada en iteración " + i + ": f(" + xi + ") ≈ 0");
            break;
        }
        
        // Verificar convergencia por error
        if (Ea <= secanteModificada.getErrorEsperado() && i > 1) {
            log.info("Convergencia alcanzada en iteración " + i);
            break;
        }
        
        // Actualizar xi para la siguiente iteración
        xi = xiNuevo;
    }
    
    log.info("Secante Modificada finalizado: " + respuesta.size() + " iteraciones");
    return respuesta;
    }

    
 
}