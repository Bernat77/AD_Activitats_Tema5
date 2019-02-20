/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proves;

/**
 *
 * @author windeveloper
 */
public class Emp {

    private String codi;
    private String codiDept;
    private String codiCap;
    private String cognom;
    private String ofici;
    private String dataAlta;
    private int salari;
    private int comissio;

    public Emp() {

    }

    public Emp(String codi) {
        this.codi = codi;
    }

    public String getCodiDept() {
        return codiDept;
    }

    public void setCodiDept(String codiDept) {
        this.codiDept = codiDept;
    }

    public String getCodiCap() {
        return codiCap;
    }

    public void setCodiCap(String codiCap) {
        this.codiCap = codiCap;
    }

    public String getCodi() {
        return codi;
    }

    public void setCodi(String codi) {
        this.codi = codi;
    }

    public String getCognom() {
        return cognom;
    }

    public void setCognom(String cognom) {
        this.cognom = cognom;
    }

    public String getOfici() {
        return ofici;
    }

    public void setOfici(String ofici) {
        this.ofici = ofici;
    }

    public String getDataAlta() {
        return dataAlta;
    }

    public void setDataAlta(String dataAlta) {
        this.dataAlta = dataAlta;
    }

    public int getSalari() {
        return salari;
    }

    public void setSalari(int salari) {
        this.salari = salari;
    }

    public int getComissio() {
        return comissio;
    }

    public void setComissio(int comissio) {
        this.comissio = comissio;
    }

}
