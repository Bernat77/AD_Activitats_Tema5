/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proves;

import java.util.ArrayList;

/**
 *
 * @author windeveloper
 */
public class Dept {

    private ArrayList<Emp> empleats;
    String codi;
    String nom;
    String localitat;

    public Dept() {

    }

    public Dept(String codi, String nom, String localitat) {
        this.codi = codi;
        this.nom = nom;
        this.localitat = localitat;
    }

    public void setEmpleats(ArrayList<Emp> empleats) {
        this.empleats = empleats;
    }

    public ArrayList<Emp> getEmpleats() {
        return empleats;
    }
    
    
    
    
    

}
