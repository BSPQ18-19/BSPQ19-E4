package carrenting.client.gui;

import java.io.IOException;
import java.util.Properties;
 
public class Language extends Properties{
 
    private static final long serialVersionUID = 1L;
 
    public Language(String language){
 
        //Modificar si quieres añadir mas idiomas
        //Cambia el nombre de los ficheros o añade los necesarios
        switch(language){
            case "Español":
                    getProperties("espanol.properties");
                    break;
            case "Inglés":
                    getProperties("ingles.properties");
                    break;
            case "Euskera":
            		getProperties("euskera.properties");
            default:
                    getProperties("ingles.properties");
        }
 
    }
 
    private void getProperties(String language) {
        try {
            this.load( getClass().getResourceAsStream(language) );
        } catch (IOException ex) {
 
        }
   }
}
