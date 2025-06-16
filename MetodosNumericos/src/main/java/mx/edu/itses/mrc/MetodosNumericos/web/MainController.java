/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.edu.itses.mrc.MetodosNumericos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author mar_6
 */

@Controller
@Slf4j
public class MainController {
     
    @GetMapping("/")
    
    public String inicio(Model model){
        System.out.println("Hola Guapo");
        int  i =1;
        log.info("mensaje de salida: {}", i);
        model.addAttribute( "Valori", i);
        return "index";
        
    }
    
    
    
    
    
}
