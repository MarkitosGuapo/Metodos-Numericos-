package mx.edu.itses.itb.MetodosNumericos.services;

import java.util.ArrayList;
import lombok.extern.slf4j.Slf4j;
import mx.edu.itses.itb.MetodosNumericos.domain.DDNewton;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UnidadIVServiceImpl implements UnidadIVService {

    @Override
    public DDNewton AlgoritmoDDNewton(DDNewton modelDDNewton) {
        try {
            log.info("=== INICIANDO ALGORITMO DIFERENCIAS DIVIDIDAS DE NEWTON ===");
            log.info("Orden del polinomio: " + modelDDNewton.getOrden());
            log.info("Puntos X: " + modelDDNewton.getPuntosX());
            log.info("Puntos Y: " + modelDDNewton.getPuntosY());
            log.info("Valor a interpolar: " + modelDDNewton.getValorInterpolacion());

            // Validar datos de entrada
            if (!validarDatos(modelDDNewton)) {
                modelDDNewton.setMensajeEstado("Error: Datos de entrada inválidos");
                return modelDDNewton;
            }

            int n = modelDDNewton.getOrden() + 1; // Número de puntos necesarios
            ArrayList<Double> x = modelDDNewton.getPuntosX();
            ArrayList<Double> y = modelDDNewton.getPuntosY();

            // Crear tabla de diferencias divididas
            ArrayList<ArrayList<Double>> tabla = construirTablaDiferenciasDivididas(x, y, n);
            modelDDNewton.setTablaDiferenciasDivididas(tabla);

            // Extraer coeficientes (primera fila de la tabla)
            ArrayList<Double> coeficientes = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                coeficientes.add(tabla.get(0).get(i));
            }
            modelDDNewton.setCoeficientes(coeficientes);

            // Construir polinomio en forma de Newton
            String polinomioNewton = construirPolinomioNewton(coeficientes, x, n);
            modelDDNewton.setPolinomioNewton(polinomioNewton);

            // Evaluar el polinomio en el valor de interpolación
            double valorInterpolado = evaluarPolinomioNewton(coeficientes, x, modelDDNewton.getValorInterpolacion(), n);
            modelDDNewton.setValorInterpolado(valorInterpolado);

            modelDDNewton.setMensajeEstado("Interpolación exitosa");
            
            log.info("Polinomio de Newton: " + polinomioNewton);
            log.info("Valor interpolado f(" + modelDDNewton.getValorInterpolacion() + ") = " + valorInterpolado);

        } catch (Exception e) {
            log.error("Error en algoritmo DDNewton: ", e);
            modelDDNewton.setMensajeEstado("Error procesando interpolación: " + e.getMessage());
        }

        return modelDDNewton;
    }

    /**
     * Validar que los datos de entrada sean correctos
     */
    private boolean validarDatos(DDNewton model) {
        if (model.getPuntosX() == null || model.getPuntosY() == null) {
            log.error("Puntos X o Y son null");
            return false;
        }

        int n = model.getOrden() + 1;
        if (model.getPuntosX().size() != n || model.getPuntosY().size() != n) {
            log.error("Número incorrecto de puntos. Se esperan " + n + " puntos para orden " + model.getOrden());
            return false;
        }

        // Verificar que no hay valores X repetidos
        ArrayList<Double> x = model.getPuntosX();
        for (int i = 0; i < x.size(); i++) {
            for (int j = i + 1; j < x.size(); j++) {
                if (Math.abs(x.get(i) - x.get(j)) < 1e-10) {
                    log.error("Valores X duplicados encontrados: " + x.get(i));
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Construir la tabla completa de diferencias divididas
     */
    private ArrayList<ArrayList<Double>> construirTablaDiferenciasDivididas(
            ArrayList<Double> x, ArrayList<Double> y, int n) {
        
        ArrayList<ArrayList<Double>> tabla = new ArrayList<>();
        
        // Inicializar tabla con valores Y en la primera columna
        for (int i = 0; i < n; i++) {
            ArrayList<Double> fila = new ArrayList<>();
            fila.add(y.get(i)); // f[xi]
            tabla.add(fila);
        }

        log.info("=== CONSTRUCCIÓN DE TABLA DIFERENCIAS DIVIDIDAS ===");
        log.info("Columna 0 (f[xi]): " + y);

        // Calcular diferencias divididas de orden superior
        for (int j = 1; j < n; j++) {
            for (int i = 0; i < n - j; i++) {
                double numerador = tabla.get(i + 1).get(j - 1) - tabla.get(i).get(j - 1);
                double denominador = x.get(i + j) - x.get(i);
                double diferencia = numerador / denominador;
                tabla.get(i).add(diferencia);
                
                log.info("f[x" + i + ",x" + (i + j) + "] = (" + 
                        tabla.get(i + 1).get(j - 1) + " - " + tabla.get(i).get(j - 1) + 
                        ") / (" + x.get(i + j) + " - " + x.get(i) + ") = " + diferencia);
            }
        }

        return tabla;
    }

    /**
     * Construir la expresión del polinomio en forma de Newton
     */
    private String construirPolinomioNewton(ArrayList<Double> coeficientes, 
            ArrayList<Double> x, int n) {
        
        StringBuilder polinomio = new StringBuilder();
        
        // Término constante
        polinomio.append(String.format("%.6f", coeficientes.get(0)));
        
        // Términos de orden superior
        for (int i = 1; i < n; i++) {
            double coef = coeficientes.get(i);
            
            if (coef >= 0 && i > 0) {
                polinomio.append(" + ");
            } else if (coef < 0) {
                polinomio.append(" - ");
                coef = Math.abs(coef);
            }
            
            polinomio.append(String.format("%.6f", coef));
            
            // Agregar productos (x - xi)
            for (int j = 0; j < i; j++) {
                if (x.get(j) >= 0) {
                    polinomio.append("(x - ").append(String.format("%.3f", x.get(j))).append(")");
                } else {
                    polinomio.append("(x + ").append(String.format("%.3f", Math.abs(x.get(j)))).append(")");
                }
            }
        }
        
        return polinomio.toString();
    }

    /**
     * Evaluar el polinomio de Newton en un punto específico
     */
    private double evaluarPolinomioNewton(ArrayList<Double> coeficientes, 
            ArrayList<Double> x, double valorX, int n) {
        
        double resultado = coeficientes.get(0); // Término constante
        
        log.info("=== EVALUACIÓN DEL POLINOMIO ===");
        log.info("P(" + valorX + ") = " + coeficientes.get(0));
        
        // Evaluar términos de orden superior
        for (int i = 1; i < n; i++) {
            double producto = 1.0;
            
            // Calcular producto (x - x0)(x - x1)...(x - xi-1)
            for (int j = 0; j < i; j++) {
                producto *= (valorX - x.get(j));
            }
            
            double termino = coeficientes.get(i) * producto;
            resultado += termino;
            
            log.info("Término " + i + ": " + coeficientes.get(i) + " * " + producto + " = " + termino);
            log.info("Suma acumulada: " + resultado);
        }
        
        return resultado;
    }
}