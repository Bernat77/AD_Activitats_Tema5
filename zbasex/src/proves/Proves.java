/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proves;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.basex.core.BaseXException;

/**
 *
 * @author windeveloper
 */
public class Proves {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Gestor gestor = null;
        try {
            
            gestor = new Gestor("empresa");

            Dept dept = gestor.getDeptAmbEmp("d200");
        
            gestor.replaceDept(dept,"d10");
            
            
 //   Dept dp = new Dept("d500", "departament", "Mallorca");
//            ArrayList<Emp> emple = new ArrayList();
//
//            Emp emp1 = new Emp();
//            emp1.setCodi("001");
//            emp1.setCodiCap("sanchez");
//            emp1.setCodiDept("d150");
//            emp1.setCognom("smith");
//            emp1.setComissio(0);
//            emp1.setDataAlta("1-3-99");
//            emp1.setOfici("Front-end");
//            emp1.setSalari(39844);
//
//            Emp emp2 = new Emp();
//            emp2.setCodi("002");
//            emp2.setCodiCap("smith");
//            emp2.setCodiDept("d150");
//            emp2.setCognom("sanchez");
//            emp2.setComissio(5);
//            emp2.setDataAlta("1-6-98");
//            emp2.setOfici("Back-end");
//            emp2.setSalari(3294);
//
//            emple.add(emp1);
//            emple.add(emp2);
//            dp.setEmpleats(emple);
        } catch (BaseXException ex) {
            System.out.println("Error de conexió: " + ex.getMessage());

        } catch (IOException ex) {

        }
    }
}
