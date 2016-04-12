/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comprobarrefuerzo;

import java.io.Serializable;

/**
 *
 * @author Antonio
 */
public class Contrato implements Serializable{
    private int dia_comienzo;
    private int dia_finalizacion;
    private String mes_comienzo;
    private String mes_finalizacion;
    private String anho_comienzo;
    private String anho_finalizacion;
    
    Contrato() {
        this.dia_comienzo=0;
        this.dia_finalizacion=0;
        this.mes_comienzo=null;
        this.mes_finalizacion=null;
        this.anho_comienzo=null;
        this.anho_finalizacion=null;
    }
    
    public Contrato(int dia_comienzo,int dia_finalizacion, String mes_comienzo,String mes_finalizacion , String anho_comienzo,String anho_finalizacion){
        this.dia_comienzo=dia_comienzo;
        this.dia_finalizacion=dia_finalizacion;
        this.mes_comienzo=mes_comienzo;
        this.mes_finalizacion=mes_finalizacion;
        this.anho_comienzo=anho_comienzo;
        this.anho_finalizacion=anho_finalizacion;
    }

    public int getDiaCominezo(){
        return this.dia_comienzo;
    }
    public int getDiaFinalizacion(){
        return this.dia_finalizacion;
    }
    public String getMesComienzo(){
        return this.mes_comienzo;
    }
    public String getMesFinalizacion(){
        return this.mes_finalizacion;
    }
    public String getAnhoComienzo(){
        return this.anho_comienzo;
    }
    public String getAnhoFinalizacion(){
        return this.anho_finalizacion;
    }

}
