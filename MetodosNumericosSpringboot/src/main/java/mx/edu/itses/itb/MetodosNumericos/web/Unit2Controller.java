package mx.edu.itses.itb.MetodosNumericos.web;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.util.ArrayList;
import lombok.extern.slf4j.Slf4j;
import mx.edu.itses.itb.MetodosNumericos.domain.Biseccion;
import mx.edu.itses.itb.MetodosNumericos.domain.NewtonRaphson;
import mx.edu.itses.itb.MetodosNumericos.domain.PuntoFijo;
import mx.edu.itses.itb.MetodosNumericos.domain.ReglaFalsa;
import mx.edu.itses.itb.MetodosNumericos.domain.Secante;
import mx.edu.itses.itb.MetodosNumericos.domain.SecanteModificada;
import mx.edu.itses.itb.MetodosNumericos.services.UnidadIIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author mar_6
 */
@Controller
@Slf4j
public class Unit2Controller {

    @Autowired
    private UnidadIIService UnidadIIService;

    @GetMapping("unit2/formbisection")
    public String formBisection(Model model) {
        Biseccion bisection = new Biseccion();
        model.addAttribute("bisection", bisection);
        return "unit2/bisection/formbisection";
    }

    //recibe el objeto del formulario
    @PostMapping("unit2/solvebisection")
    public String solvebisection(Biseccion bisection, Model model) {
        // double valorFX = Funciones.Ecuacion(bisection.getFX(), bisection.getXL());
        //    log.info("Valor de fx:" + valorFX);
        var solveBisection = UnidadIIService.AlgoritmoBiseccion(bisection);
        model.addAttribute("solvebisection", solveBisection);
        //  log.info("Arreglo" + solveBisection);
        return "/unit2/bisection/solvebisection";
    }

    //  CAMBIAR REDIRECCIONAMIENTO DE RUTAS 
    @GetMapping("/unit2")
    public String index(Model model) {
        return "unit2/indexunidad2";
    }

    //Mapings de regla flasa y post
    @GetMapping("unit2/formreglafalsa")
    public String formReglaFalsa(Model model) {
        ReglaFalsa reglafalsa = new ReglaFalsa();
        model.addAttribute("reglafalsa", reglafalsa);
        return "unit2/reglafalsa/formreglafalsa";
    }

    @PostMapping("unit2/solvereglafalsa")
    public String solveReglaFalsa(ReglaFalsa reglafalsa, Model model) {
        var solveReglaFalsa = UnidadIIService.AlgoritmoDeReglaFalsa(reglafalsa);
        model.addAttribute("solvereglafalsa", solveReglaFalsa);
        return "/unit2/reglafalsa/solvereglafalsa";
    }


    
    //Punto Fijo
    
    @GetMapping("unit2/formpuntofijo")
    public String formPuntoFijo(Model model) {
        PuntoFijo punto = new PuntoFijo();
        model.addAttribute("puntofijo", punto);
        return "unit2/puntofijo/formpuntofijo";
    }


   @PostMapping("unit2/solvepuntofijo")
public String solvePuntoFijo(PuntoFijo puntofijo, Model model) {
    var solvePuntoFijo = UnidadIIService.AlgoritmoDePuntoFijo(
            puntofijo.getGXI(), puntofijo, puntofijo.getEa());
    model.addAttribute("solvepuntofijo", solvePuntoFijo);
    return "/unit2/puntofijo/solvepuntofijo";
}


//Newton-Raphson
@GetMapping("unit2/formnewtonraphson")
public String formNewtonRaphson(Model model) {
    NewtonRaphson newtonraphson = new NewtonRaphson();
    model.addAttribute("newtonraphson", newtonraphson);
    return "unit2/newtonraphson/formnewtonraphson";
}

@PostMapping("unit2/solvenewtonraphson")
public String solveNewtonRaphson(NewtonRaphson newtonraphson, Model model) {
    var solveNewtonRaphson = UnidadIIService.AlgoritmoNewtonRaphson(newtonraphson);
    model.addAttribute("solvenewtonraphson", solveNewtonRaphson);
    return "/unit2/newtonraphson/solvenewtonraphson";
}

//Secante
@GetMapping("unit2/formsecante")
public String formSecante(Model model) {
    Secante secante = new Secante();
    model.addAttribute("secante", secante);
    return "unit2/secante/formsecante";
}

@PostMapping("unit2/solvesecante")
public String solveSecante(Secante secante, Model model) {
    var solveSecante = UnidadIIService.AlgoritmoSecante(secante);
    model.addAttribute("solvesecante", solveSecante);
    return "/unit2/secante/solvesecante";
}

// Secante Modificada - Formulario
@GetMapping("unit2/formsecantemodificada")
public String formSecanteModificada(Model model) {
    SecanteModificada secantemodificada = new SecanteModificada();
  
    model.addAttribute("secantemodificada", secantemodificada);
    return "unit2/secantemodificada/formsecantemodificada";
}

// Secante Modificada - Resolver
@PostMapping("unit2/solvesecantemodificada")
public String solveSecanteModificada(SecanteModificada secantemodificada, Model model) {
 
    var solveSecanteModificada = UnidadIIService.AlgoritmoSecanteModificado(secantemodificada);
    model.addAttribute("solvesecantemodificada", solveSecanteModificada);
    
    log.info("Resultados obtenidos: " + solveSecanteModificada.size() + " iteraciones");
    return "/unit2/secantemodificada/solvesecantemodificada";
}







}
