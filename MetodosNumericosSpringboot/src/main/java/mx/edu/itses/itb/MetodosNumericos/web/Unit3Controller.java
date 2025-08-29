package mx.edu.itses.itb.MetodosNumericos.web;

import java.util.ArrayList;
import lombok.extern.slf4j.Slf4j;
import mx.edu.itses.itb.MetodosNumericos.domain.Gauss;
import mx.edu.itses.itb.MetodosNumericos.domain.GaussJordan;
import mx.edu.itses.itb.MetodosNumericos.domain.GaussSeidel;
import mx.edu.itses.itb.MetodosNumericos.domain.Jacobi;
import mx.edu.itses.itb.MetodosNumericos.domain.ReglaCramer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import mx.edu.itses.itb.MetodosNumericos.services.UnidadIIIService;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
@Slf4j
public class Unit3Controller {
    @Autowired    
    private UnidadIIIService unidadIIIsrv;
    
    
    @GetMapping("/unit3")
    public String index(Model model) {
        return "unit3/index";
    }
    
    @GetMapping("/unit3/formcramer")
    public String formCramer(Model model) {
        ReglaCramer modelCramer = new ReglaCramer();
        model.addAttribute("modelCramer",modelCramer);
        return "unit3/cramer/formcramer";
    }
    
    @PostMapping("/unit3/solvecramer")
    public String solveCramer(ReglaCramer modelCramer,Errors errores,Model model) {
    //    log.info("OBJECTOS:" +  modelCramer.getMatrizA());
        var solveCramer = unidadIIIsrv.AlgoritmoReglaCramer(modelCramer);
      //  log.info("Solución: " + solveCramer.getVectorX());
          model.addAttribute("solveCramer",solveCramer);

        
        return "unit3/cramer/solvecramer";
    }
    

  //Eliminacion Gaussiana 
@GetMapping("/unit3/formgaussiana")
public String formGaussiana(Model model) {
    Gauss modelGauss = new Gauss();
    model.addAttribute("modelGauss", modelGauss); // ← Cambio aquí
    return "unit3/Gauss/formgaussiana";
}

@PostMapping("/unit3/solvegaussiana")
public String solveGaussiana(Gauss modelGauss, Errors errores, Model model) {
    var solveGaussiana = unidadIIIsrv.AlgoritmoGauss(modelGauss);
    model.addAttribute("solveGaussiana", solveGaussiana);
    return "unit3/Gauss/solvegaussiana";
}
    
   @GetMapping("/unit3/formgaussjordan")
public String formGaussJordan(Model model) {
    GaussJordan modelGaussJordan = new GaussJordan();
    model.addAttribute("modelGaussJordan", modelGaussJordan);
    return "unit3/GaussJordan/formgaussjordan";
}

@PostMapping("/unit3/solvegaussjordan")
public String solveGaussJordan(GaussJordan modelGaussJordan, Errors errores, Model model) {
    var solveGaussJordan = unidadIIIsrv.AlgoritmoGaussJordan(modelGaussJordan);
    model.addAttribute("solveGaussJordan", solveGaussJordan);
    return "unit3/GaussJordan/solvegaussjordan";
}
  
@GetMapping("/unit3/formjacobi")
public String formJacobi(Model model) {
    Jacobi modelJacobi = new Jacobi();
    // CORRECCIÓN: Establecer valores por defecto
    modelJacobi.setTolerancia(1.0);
    modelJacobi.setMaxIteraciones(50);
    model.addAttribute("modelJacobi", modelJacobi);
    return "unit3/jacobi/formjacobi";
}

@PostMapping("/unit3/solvejacobi")
public String solveJacobi(@ModelAttribute Jacobi modelJacobi, Errors errores, Model model) {
    try {
        log.info("=== DATOS RECIBIDOS EN CONTROLADOR ===");
        log.info("MN=" + modelJacobi.getMN());
        log.info("Tolerancia=" + modelJacobi.getTolerancia());
        log.info("Max Iteraciones=" + modelJacobi.getMaxIteraciones());
        log.info("Matriz A: " + modelJacobi.getMatrizA());
        log.info("Vector B: " + modelJacobi.getVectorB());
        log.info("Valores iniciales: " + modelJacobi.getValoresIniciales());
        
        // CORRECCIÓN: Validación básica
        if (modelJacobi.getMatrizA() == null || modelJacobi.getVectorB() == null || 
            modelJacobi.getValoresIniciales() == null) {
            model.addAttribute("error", "Datos incompletos. Por favor complete todos los campos.");
            return "unit3/jacobi/formjacobi";
        }
        
        var solveJacobi = unidadIIIsrv.AlgoritmoJacobi(modelJacobi);
        model.addAttribute("solveJacobi", solveJacobi);
        
        log.info("=== RESULTADO PROCESADO ===");
        log.info("Convergio: " + solveJacobi.isConvergio());
        log.info("Iteraciones realizadas: " + solveJacobi.getIteracionesRealizadas());
        log.info("Mensaje: " + solveJacobi.getMensajeEstado());
        
        return "unit3/jacobi/solvejacobi";
        
    } catch (Exception e) {
        log.error("Error procesando Jacobi: ", e);
        model.addAttribute("error", "Error procesando el sistema: " + e.getMessage());
        return "unit3/jacobi/formjacobi";
    }
}

@GetMapping("/unit3/formgaussseidel")
public String formGaussSeidel(Model model) {
    GaussSeidel modelGaussSeidel = new GaussSeidel();
    // Establecer valores por defecto
    modelGaussSeidel.setTolerancia(1.0);
    modelGaussSeidel.setMaxIteraciones(50);
    model.addAttribute("modelGaussSeidel", modelGaussSeidel);
    return "unit3/gaussseidel/formgaussseidel"; // ← ERROR AQUÍ
}

@PostMapping("/unit3/solvegaussseidel")
public String solveGaussSeidel(@ModelAttribute GaussSeidel modelGaussSeidel, Errors errores, Model model) {
    try {
        log.info("=== DATOS RECIBIDOS EN CONTROLADOR GAUSS-SEIDEL ===");
        log.info("MN=" + modelGaussSeidel.getMN());
        log.info("Tolerancia=" + modelGaussSeidel.getTolerancia());
        log.info("Max Iteraciones=" + modelGaussSeidel.getMaxIteraciones());
        log.info("Matriz A: " + modelGaussSeidel.getMatrizA());
        log.info("Vector B: " + modelGaussSeidel.getVectorB());
        log.info("Valores iniciales: " + modelGaussSeidel.getValoresIniciales());
        
        // Validación básica
        if (modelGaussSeidel.getMatrizA() == null || modelGaussSeidel.getVectorB() == null || 
            modelGaussSeidel.getValoresIniciales() == null) {
            model.addAttribute("error", "Datos incompletos. Por favor complete todos los campos.");
            return "unit3/gaussseidel/formgaussseidel";
        }
        
        var solveGaussSeidel = unidadIIIsrv.AlgoritmoGaussSeidel(modelGaussSeidel);
        model.addAttribute("solveGaussSeidel", solveGaussSeidel);
        
        log.info("=== RESULTADO PROCESADO GAUSS-SEIDEL ===");
        log.info("Convergio: " + solveGaussSeidel.isConvergio());
        log.info("Iteraciones realizadas: " + solveGaussSeidel.getIteracionesRealizadas());
        log.info("Mensaje: " + solveGaussSeidel.getMensajeEstado());
        
        return "unit3/gaussseidel/solvegaussseidel";
        
    } catch (Exception e) {
        log.error("Error procesando Gauss-Seidel: ", e);
        model.addAttribute("error", "Error procesando el sistema: " + e.getMessage());
        return "unit3/gaussseidel/solvegaussseidel";
    }
}





}

