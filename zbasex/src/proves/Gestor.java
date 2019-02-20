/*
 * Programa: GestorDB.java
 * Objectiu: Programa que mostri els noms dels continents emmagatzemats en
 *           el document "mondial.xml" de la base de dades "mondial"
 * Autor...: Isidre Guix�
 */
package proves;

import org.basex.server.ClientSession;
import org.basex.core.BaseXException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.basex.server.LoginException;

public class Gestor {

    public ClientSession session;

    public Gestor(String bd) throws BaseXException, IOException {

        try {
            session = new ClientSession("localhost", 1984, "admin", "admin");

            try {
                session.execute("open " + bd);

            } catch (BaseXException | NullPointerException ex) {
                session.close();
                throw new BaseXException(new Exception("No s'ha trobat la base de dades"));

            }

        } catch (LoginException ex) {
            throw new BaseXException(new Exception("Usuari o contrasenya incorrects"));
        }

    }

    public Dept getDeptSenseEmp(String pk) {
        Dept departament = null;
        try {

            String[] query = session.query("for $x in doc(\"empresa\")//dept where $x/@codi = "
                    + "\"" + pk + "\" return concat(\"/\",$x/nom,\"-\",$x/localitat,\"/\")")
                    .execute().split("/");
            String localitat = query[1].split("-")[0];
            String nom = query[1].split("-")[1];
            System.out.println(nom);
            System.out.println(localitat);
            departament = new Dept(pk, nom, localitat);

        } catch (IOException | ArrayIndexOutOfBoundsException arrex) {
            System.out.println("No existeix el departament " + pk + " a la base de dades");
        } finally {
            return departament;
        }
    }

    public Dept getDeptAmbEmp(String pk) {
        Dept departament = null;
        ArrayList<Emp> empleatsList = new ArrayList();

        try {
            departament = getDeptSenseEmp(pk);

            if (departament != null) {

                String[] empleats = session.query("for $x in  doc(\"empresa\")//emp "
                        + "where $x/@dept = \"" + pk + "\" return concat "
                                + "(\"/\",$x/@codi,\",\",$x/@cap,\",\",$x/cognom,"
                                + "\",\",$x/ofici,\",\",$x/dataAlta,\",\",$x/salari,"
                                + "\",\",$x/comissio,\" \")").execute().split("/");
                if (empleats.length > 1) {
                    for (int i = 1; i < empleats.length; i++) {
                        String[] empleatarr = empleats[i].split(",");
                        Emp empleat = new Emp(empleatarr[0]);
                        empleat.setCodiCap(empleatarr[1]);
                        empleat.setCodiDept(pk);
                        empleat.setCognom(empleatarr[2]);
                        empleat.setOfici(empleatarr[3]);
                        empleat.setDataAlta(empleatarr[4]);
                        empleat.setSalari(Integer.parseInt(empleatarr[5]));
                        if (!empleatarr[6].trim().equals("")) {
                            empleat.setComissio(Integer.parseInt(empleatarr[6].trim()));
                        }
                        empleatsList.add(empleat);

                    }
                    for (Emp e : empleatsList) {
                        System.out.println("-----------");
                        System.out.println(e.getCodi());
                        System.out.println(e.getCodiCap());
                        System.out.println(e.getCodiDept());
                        System.out.println(e.getCognom());
                        System.out.println(e.getDataAlta());
                        System.out.println(e.getOfici());
                        System.out.println(e.getSalari());
                        System.out.println(e.getComissio());
                    }
                    departament.setEmpleats(empleatsList);
                }else{
                      System.out.println("El departament no té cap empleat");
                }
            }

        } catch (IOException ex) {

        }

        return departament;
    }

    public void insertDept(Dept departament) {
        if (getDeptSenseEmp(departament.codi) == null) {
            try {
                session.query("insert node <dept codi=\"" + departament.codi + "\"><nom>" 
                        + departament.nom + "</nom><localitat>" + departament.localitat 
                        + "</localitat></dept>into doc(\"empresa\")//departaments").execute();
                System.out.println("Departament afegit");
                if (departament.getEmpleats() != null) {
                    String[] empleats = session.query("for $x in doc(\"empresa\")//emp return concat"
                            + "(\"/\",$x/@codi,\",\",$x/@dept)").execute().split("/");
                    ArrayList<Emp> listEmpleats = departament.getEmpleats();
                    int[] founded = new int[listEmpleats.size()];
                    for (int i = 0; i < departament.getEmpleats().size(); i++) {
                        for (int j = 1; j < empleats.length; j++) {
                            String codiEmpleat = empleats[j].split(",")[0].trim();
                            String codiDepartament = empleats[j].split(",")[1].trim();
                            if (listEmpleats.get(i).getCodi().trim().equals(codiEmpleat)) {
                                founded[i] = 1;
                                if (listEmpleats.get(i).getCodiDept().trim().equals(codiDepartament)) {
                                    founded[i] = 2;
                                }
                            }
                        }
                    }

                    for (int i = 0; i < founded.length; i++) {
                        if (founded[i] == 0) {
                            Emp temp = listEmpleats.get(i);
                            session.query("insert node <emp codi=\"" + temp.getCodi() 
                                    + "\" dept=\"" + departament.codi + "\" cap=\"" 
                                    + temp.getCodiCap() + "\"><cognom>" + temp.getCognom() 
                                    + "</cognom><ofici>" + temp.getOfici() + "</ofici><dataAlta>" 
                                    + temp.getDataAlta() + "</dataAlta><salari>" 
                                    + String.valueOf(temp.getSalari()) + "</salari><comissio>" 
                                    + String.valueOf(temp.getComissio()) + "</comissio></emp> "
                                            + "into doc(\"empresa\")//empleats").execute();
                            System.out.println("Afegit empleat:" + temp.getCodi());
                        } else if (founded[i] == 1) {
                            Emp temp = listEmpleats.get(i);
                            session.query("replace value of node doc(\"empresa\")//emp[@codi='" + 
                                    temp.getCodi() + "']/@dept with \"" + departament.codi + "\"").execute();
                            System.out.println("Empleat: " + temp.getCodi() 
                                    + " Canviat a departament " + departament.codi);
                        }
                    }
                } else {
                    System.out.println("El departament no té cap empleat");
                }
            } catch (IOException ex) {

            }

        } else {
            System.out.println("Aquest departament ja està inserit");
        }

    }

    public void deleteDept(String departament, String nouDepartament) {
        if (!departament.equals(nouDepartament)) {
            if (getDeptSenseEmp(departament) != null && getDeptSenseEmp(nouDepartament) != null) {
                try {
                    String[] empleats = session.query(" for $x in doc(\"empresa\")//emp where $x/@dept=\"" 
                            + departament + "\" return concat(\"/\",$x/@codi)").execute().split("/");

                    for (int i = 1; i < empleats.length; i++) {
                        String empleatCodi = empleats[i].trim();
                        session.query("replace value of node doc(\"empresa\")//emp[@codi='" 
                                + empleatCodi + "']/@dept with \"" + nouDepartament + "\"").execute();
                        System.out.println("Empleat:" + empleatCodi + "Canviat a departament " + nouDepartament);

                    }
                    session.query("delete node doc('empresa')//dept[@codi=\"" + departament + "\"]").execute();
                    System.out.println("Departament eliminat");

                } catch (IOException ex) {
                }
            }
        }else{
            System.out.println("Els codis introduits son iguals");
        }
    }

    public void replaceDept(Dept nouDepartament, String remplac) {

        if (getDeptSenseEmp(nouDepartament.codi) != null) {
            if (getDeptSenseEmp(remplac) != null) {
                try {
                    session.query("replace value of node doc(\"empresa\")//dept[@codi='" + nouDepartament.codi + "']/@codi with \"" + nouDepartament.codi + "\"").execute();

                    //                   String[] empleats = session.query(" for $x in doc(\"empresa\")//emp where $x/@dept=\"" + nouDepartament.codi + "\" return concat(\"/\",$x/@codi)").execute().split("/");
                    //hay que cambiar remplac por nouDepartament, por tanto hay que borrar remplac
                    deleteDept(remplac, nouDepartament.codi);
//                    for (int i = 1; i < empleats.length; i++) {
//                        String empleatCodi = empleats[i].trim();
//                        session.query("replace value of node doc(\"empresa\")//emp[@codi='" + empleatCodi + "']/@dept with \"" + remplac + "\"").execute();
//                        System.out.println("Empleat:" + empleatCodi + "Canviat a nouDepartament " + remplac);
//                    }
                } catch (IOException ex) {

                }
            } else {
                System.out.println("El codi del departament: " + remplac + " no existeix");
            }
        } else {
            System.out.println("El departament " + nouDepartament.codi + " no existeix");

        }
    }

    public void tancarSessio() {
        try {
            if (session != null) {
                session.execute("close");
                session.close();
            }
        } catch (Exception ex) {
            new BaseXException(new Exception("problema tancant la base de dades"));
        }
    }
}
