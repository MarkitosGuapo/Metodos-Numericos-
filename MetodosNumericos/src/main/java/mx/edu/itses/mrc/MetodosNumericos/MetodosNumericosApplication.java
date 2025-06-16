package mx.edu.itses.mrc.MetodosNumericos;

import org.mariuszgromada.math.mxparser.License;
import org.mariuszgromada.math.mxparser.mXparser;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MetodosNumericosApplication {

	public static void main(String[] args) {
            
               boolean isCallSuccessful = License.iConfirmNonCommercialUse("Materia Metodos Numericos");
               String massage = License.getUseTypeConfirmationMessage();
               
                mXparser.consolePrintln("Mensaje:" + massage);
               
              SpringApplication.run(MetodosNumericosApplication.class, args);
              
	}

}
