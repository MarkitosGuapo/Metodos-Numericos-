package mx.edu.itses.itb.MetodosNumericos.web;

import lombok.extern.slf4j.Slf4j;
import mx.edu.itses.itb.MetodosNumericos.domain.DDNewton;
import mx.edu.itses.itb.MetodosNumericos.services.UnidadIVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
@Slf4j
public class Unit4Controller {
    
    @Autowired
    private UnidadIVService unidadIVsrv;
    
    @GetMapping("/unit4")
    public String index(Model model) {
        return "unit4/index";
    }
    
    @GetMapping("/unit4/formddnewton")
    public String formDDNewton(Model model) {
        DDNewton modelDDNewton = new DDNewton();
        model.addAttribute("modelDDNewton", modelDDNewton);
        return "unit4/ddnewton/formddnewton";
    }
    
    @PostMapping("/unit4/solveddnewton")
    public String solveDDNewton(@ModelAttribute DDNewton modelDDNewton, Errors errores, Model model) {
        try {
            log.info("=== DATOS RECIBIDOS EN CONTROLADOR DDNewton ===");
            log.info("Orden: " + modelDDNewton.getOrden());
            log.info("Puntos X: " + modelDDNewton.getPuntosX());
            log.info("Puntos Y: " + modelDDNewton.getPuntosY());
            log.info("Valor a interpolar: " + modelDDNewton.getValorInterpolacion());
            
            // Validación básica
            if (modelDDNewton.getPuntosX() == null || modelDDNewton.getPuntosY() == null) {
                model.addAttribute("error", "Datos incompletos. Por favor complete todos los campos.");
                return "unit4/ddnewton/formddnewton";
            }
            
            var solveDDNewton = unidadIVsrv.AlgoritmoDDNewton(modelDDNewton);
            model.addAttribute("solveDDNewton", solveDDNewton);
            
            log.info("=== RESULTADO PROCESADO DDNewton ===");
            log.info("Mensaje: " + solveDDNewton.getMensajeEstado());
            log.info("Valor interpolado: " + solveDDNewton.getValorInterpolado());
            
            return "unit4/ddnewton/solveddnewton";
            
        } catch (Exception e) {
            log.error("Error procesando DDNewton: ", e);
            model.addAttribute("error", "Error procesando la interpolación: " + e.getMessage());
            return "unit4/ddnewton/formddnewton";
        }
    }
}