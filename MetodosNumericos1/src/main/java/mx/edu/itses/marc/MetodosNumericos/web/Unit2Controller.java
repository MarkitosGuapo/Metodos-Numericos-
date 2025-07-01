package mx.edu.itses.marc.MetodosNumericos.web;

import lombok.extern.slf4j.Slf4j;
import mx.edu.itses.marc.MetodosNumericos.domain.Biseccion;
import mx.edu.itses.marc.MetodosNumericos.domain.NewRapson;
import mx.edu.itses.marc.MetodosNumericos.domain.ReglaFalsa;
import mx.edu.itses.marc.MetodosNumericos.services.Funciones;
import mx.edu.itses.marc.MetodosNumericos.services.UnidadIIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class Unit2Controller {

    @Autowired
    private UnidadIIService bisectionservice;
    @Autowired
    private UnidadIIService reglafalsaservice;
    @Autowired
    private UnidadIIService puntofijoservice;
    @Autowired
  private UnidadIIService newrapsonService;
    
    @GetMapping("unit2/formbisection")
    public String formBisecccion(Model model) {
        Biseccion bisection = new Biseccion();

        model.addAttribute("bisection", bisection);
        return "unit2/bisection/formbisection";
    }

    @GetMapping("unit2/formreglafalsa")
    public String formReglaFalsa(Model model) {
        ReglaFalsa reglafalsa = new ReglaFalsa();

        model.addAttribute("reglafalsa", reglafalsa);
        return "unit2/reglafalsa/formreglafalsa";
    }

    @PostMapping("/unit2/solvebisection")
    public String solvebisection(Biseccion bisection, Model model) {
        //  double valorFX=Funciones.Ecuacion(bisection.getFx(), bisection.getXl());
        //  log.info("Valor de Fx:"+valorFX);
        var solveBisection = bisectionservice.AlgoritmoBiseccion(bisection);

        log.info("Arreglo" + solveBisection);
        model.addAttribute("solveBisection", solveBisection);

        return "/unit2/bisection/solvebisection";
    }

    @PostMapping("/unit2/solvereglafalsa")
    public String solvereglafalsa(ReglaFalsa reglafalsa, Model model) {
        var solveReglaFalsa = reglafalsaservice.AlgoritmoReglaFalsa(reglafalsa);

        log.info("Arreglo" + solveReglaFalsa);
        model.addAttribute("solveReglaFalsa", solveReglaFalsa);

        return "/unit2/reglafalsa/solvereglafalsa";
    }
    
    @GetMapping("/unit2/formpuntofijo")
public String formPuntoFijo(Model model) {
    model.addAttribute("puntofijo", new mx.edu.itses.marc.MetodosNumericos.domain.PuntoFijo());
    return "unit2/puntofijo/formpuntofijo"; 
}

@PostMapping("/unit2/solvepuntofijo")
public String solvePuntoFijo(mx.edu.itses.marc.MetodosNumericos.domain.PuntoFijo puntofijo, Model model) {
    var solucion = puntofijoservice.AlgoritmoDePuntoFijo(puntofijo);
    model.addAttribute("solvePuntoFijo", solucion);
    return "unit2/puntofijo/solvepuntofijo"; 
}

  @GetMapping("/unit2/newRapson")
public String formNewRapson(Model model) {
    NewRapson newrapson = new NewRapson();
    model.addAttribute("newrapson", newrapson);
    return "unit2/newrapson/newrapson"; // minúsculas
}

@PostMapping("/unit2/solvenewrapson")
public String solvenewrapson(NewRapson newrapson, Model model) {
    var solvenewRapson = newrapsonService.AlgoritmoDeNewRpason(newrapson);
    log.info("Arreglo Newton-Raphson: " + solvenewRapson);
    model.addAttribute("solveNewRapson", solvenewRapson);
    return "unit2/newrapson/solvenewtonrapson"; 
}
    
    
 
    
    
    
    
}
