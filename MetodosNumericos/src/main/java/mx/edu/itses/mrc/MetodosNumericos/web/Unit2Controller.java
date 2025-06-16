/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.edu.itses.mrc.MetodosNumericos.web;

import lombok.extern.slf4j.Slf4j;
import mx.edu.itses.mrc.MetodosNumericos.domain.Biseccion;
import mx.edu.itses.mrc.MetodosNumericos.services.Funciones;
import mx.edu.itses.mrc.MetodosNumericos.services.UnidadIIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author mar_6
 */
@Controller
@Slf4j
public class Unit2Controller {

    @Autowired
    private UnidadIIService bisectionservice;

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
        var solveBisection = bisectionservice.AlgoritmoBiseccion(bisection);

        
        
        model.addAttribute("solvebisection", solveBisection);
          
      //  log.info("Arreglo" + solveBisection);

        return "/unit2/bisection/solvebisection";
    }

}
