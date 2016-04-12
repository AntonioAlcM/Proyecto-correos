/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comprobarrefuerzo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Antonio
 */
public class Programa {
    ArrayList <Contrato> contratosRefuerzo;
    ArrayList <Contrato> contratosVacaciones;
    ArrayList <Contrato> contratosBaja;
    Calendar fecha = GregorianCalendar.getInstance();
    VentanaPrincipal ventanaPrincipal;

    Programa(VentanaPrincipal ventanaPrincipal){
        this.contratosRefuerzo = new ArrayList();
        this.contratosVacaciones = new ArrayList();
        this.contratosBaja = new ArrayList();
        this.ventanaPrincipal=ventanaPrincipal;
    }
    public boolean comprobarDiaSegunMes(int dia , int mes, int anho){
        boolean es_correcto=false;
        if(mes<8){
            if((mes%2!=0 && mes!=2) && dia<=31)
                es_correcto=true;
            else if((mes%2==0 && mes!=2) && dia<=30)
                es_correcto=true;
            else if(mes==2 && dia<=29 && ((anho % 4 == 0) && ((anho % 400 == 0)|| (anho % 100 != 0) )))
                es_correcto=true;
            else if (mes==2 && dia<=28 && ((anho % 4 != 0) && ((anho % 400 != 0)|| (anho % 100 == 0) )))
                es_correcto=true;
        }else
            if((mes%2!=0) && dia<=30)
                es_correcto=true;
            else if(mes%2==0  && dia<=31)
                es_correcto=true;
        return es_correcto;
    }
    public boolean sePuedeAnhadir(int diaComienzo,int diaFinalizacion,String mesComienzo,String mesFinalizacion,String anhoComienzo ,String anhoFinalizacion){
        boolean sePuede=false;
        if((!anhoComienzo.isEmpty() || !anhoFinalizacion.isEmpty()) && anhoComienzo.length()==4 && anhoFinalizacion.length()==4){
            if(this.obtenerNumeroMes(mesComienzo)==this.obtenerNumeroMes(mesFinalizacion)){
                    if(anhoComienzo.contentEquals(anhoFinalizacion) && Integer.parseInt(anhoFinalizacion)<fecha.get(Calendar.YEAR)
                            && diaComienzo<=diaFinalizacion ){
                        sePuede=true;
                    }else if(anhoComienzo.contentEquals(anhoFinalizacion) && Integer.parseInt(anhoFinalizacion)==fecha.get(Calendar.YEAR)){
                        if(this.obtenerNumeroMes(mesFinalizacion)<fecha.get(Calendar.MONTH)+1 && diaComienzo<=diaFinalizacion)
                                sePuede=true;
                        else if(this.obtenerNumeroMes(mesFinalizacion)==fecha.get(Calendar.MONTH)+1 && diaComienzo<=diaFinalizacion
                                && diaFinalizacion<=fecha.get(Calendar.DAY_OF_MONTH))
                                sePuede=true;
                    }else if(Integer.parseInt(anhoComienzo)<Integer.parseInt(anhoFinalizacion) && Integer.parseInt(anhoFinalizacion)<fecha.get(Calendar.YEAR)){
                        sePuede=true;
                    }else if(Integer.parseInt(anhoComienzo)<Integer.parseInt(anhoFinalizacion) && Integer.parseInt(anhoFinalizacion)==fecha.get(Calendar.YEAR))
                            if(this.obtenerNumeroMes(mesFinalizacion)<fecha.get(Calendar.MONTH)+1)
                                sePuede=true;
                            else if(this.obtenerNumeroMes(mesFinalizacion)==fecha.get(Calendar.MONTH)+1&& diaFinalizacion<=fecha.get(Calendar.DAY_OF_MONTH))
                                sePuede=true;
            }else if(this.obtenerNumeroMes(mesComienzo)<this.obtenerNumeroMes(mesFinalizacion)){
                    if(anhoComienzo.contentEquals(anhoFinalizacion) && Integer.parseInt(anhoFinalizacion)<fecha.get(Calendar.YEAR)){
                        sePuede=true;
                    }else if(anhoComienzo.contentEquals(anhoFinalizacion) && Integer.parseInt(anhoFinalizacion)==fecha.get(Calendar.YEAR)){
                        if(this.obtenerNumeroMes(mesFinalizacion)<fecha.get(Calendar.MONTH)+1 && diaComienzo<=diaFinalizacion)
                                sePuede=true;
                        else if(this.obtenerNumeroMes(mesFinalizacion)==fecha.get(Calendar.MONTH)+1 && diaFinalizacion<=fecha.get(Calendar.DAY_OF_MONTH))
                                sePuede=true;
                    }else if(Integer.parseInt(anhoComienzo)<Integer.parseInt(anhoFinalizacion) && Integer.parseInt(anhoFinalizacion)<fecha.get(Calendar.YEAR)){
                        sePuede=true;
                    }else if(Integer.parseInt(anhoComienzo)<Integer.parseInt(anhoFinalizacion) && Integer.parseInt(anhoFinalizacion)==fecha.get(Calendar.YEAR))
                            if(this.obtenerNumeroMes(mesFinalizacion)<fecha.get(Calendar.MONTH)+1)
                                sePuede=true;
                            else if(this.obtenerNumeroMes(mesFinalizacion)==fecha.get(Calendar.MONTH)+1&& diaFinalizacion<=fecha.get(Calendar.DAY_OF_MONTH))
                                sePuede=true;
            }else if(this.obtenerNumeroMes(mesComienzo)>this.obtenerNumeroMes(mesFinalizacion)){
                if(Integer.parseInt(anhoComienzo)<Integer.parseInt(anhoFinalizacion) && Integer.parseInt(anhoFinalizacion)<fecha.get(Calendar.YEAR)){
                        sePuede=true;
                }else if(Integer.parseInt(anhoComienzo)<Integer.parseInt(anhoFinalizacion) && Integer.parseInt(anhoFinalizacion)==fecha.get(Calendar.YEAR))
                         if(this.obtenerNumeroMes(mesFinalizacion)<fecha.get(Calendar.MONTH)+1)
                             sePuede=true;
                         else if(this.obtenerNumeroMes(mesFinalizacion)==fecha.get(Calendar.MONTH)+1&& diaFinalizacion<=fecha.get(Calendar.DAY_OF_MONTH))
                             sePuede=true;
            }
                       
        }       
        return sePuede;  
    }
    public Pair<Boolean,Integer> BuscarRepetidos(Contrato contrato){
       Pair<Boolean,Integer> contratoRepetido= new Pair(false,0);
       contratoRepetido=BuscarRepetidosContrato(contrato, this.contratosRefuerzo);
       if(contratoRepetido.getFirst()==true){
         contratoRepetido.setSecond(1);
       }
       if(contratoRepetido.getFirst()==false){
            contratoRepetido=BuscarRepetidosContrato(contrato, this.contratosVacaciones);
            if(contratoRepetido.getFirst()==true)
                contratoRepetido.setSecond(2);
       }
       if(contratoRepetido.getFirst()==false){
            contratoRepetido=BuscarRepetidosContrato( contrato, this.contratosBaja);
            if(contratoRepetido.getFirst()==true)
                contratoRepetido.setSecond(3);
       }
       return contratoRepetido;
   }
    public Pair<Boolean,Integer> BuscarRepetidosContrato(Contrato contrato, ArrayList<Contrato> contratos){
       Pair<Boolean,Integer> contratoRepetido= new Pair(false,0);
       boolean repetidos=false;
       for(int i=0; i<contratos.size() && repetidos==false ; i++){
                //El contrato es identico
               if((contratos.get(i).getDiaCominezo()==contrato.getDiaCominezo() && contratos.get(i).getDiaFinalizacion()==contrato.getDiaFinalizacion()
                       && this.obtenerNumeroMes(contratos.get(i).getMesComienzo())==this.obtenerNumeroMes(contrato.getMesComienzo()) && this.obtenerNumeroMes(contratos.get(i).getMesFinalizacion())==this.obtenerNumeroMes(contrato.getMesFinalizacion())
                       && contratos.get(i).getAnhoComienzo().contentEquals(contrato.getAnhoComienzo()) && contratos.get(i).getAnhoFinalizacion().contentEquals(contrato.getAnhoFinalizacion())) ){
                   repetidos=true;
                //El contrato coincide con el mes de finalización de otro contrato   
               }else if(this.obtenerNumeroMes(contratos.get(i).getMesFinalizacion())==this.obtenerNumeroMes(contrato.getMesComienzo()) && (contratos.get(i).getDiaFinalizacion()>=contrato.getDiaCominezo()) && 
                       contratos.get(i).getAnhoFinalizacion().contentEquals(contrato.getAnhoComienzo()) )
                    repetidos=true;
               //El contrato coincide con el  mes de comienzo de otro contrato  
               else if(this.obtenerNumeroMes(contratos.get(i).getMesComienzo())==this.obtenerNumeroMes(contrato.getMesFinalizacion()) && (contratos.get(i).getDiaCominezo()<=contrato.getDiaFinalizacion()) && 
                      contratos.get(i).getAnhoComienzo().contentEquals(contrato.getAnhoFinalizacion()) )
                    repetidos=true;
               else if((contratos.get(i).getAnhoComienzo().contentEquals(contrato.getAnhoComienzo()) 
                       && (Integer.parseInt(contratos.get(i).getAnhoFinalizacion())<=Integer.parseInt(contrato.getAnhoFinalizacion()) 
                       && contrato.getDiaCominezo()>=contratos.get(i).getDiaCominezo() &&  this.obtenerNumeroMes(contrato.getMesComienzo())>=this.obtenerNumeroMes(contratos.get(i).getMesComienzo()))
                       && contrato.getDiaFinalizacion()<=contratos.get(i).getDiaFinalizacion()
                       &&  this.obtenerNumeroMes(contrato.getMesFinalizacion())<=this.obtenerNumeroMes(contratos.get(i).getMesFinalizacion())))
                    repetidos=true;
       }
       contratoRepetido.setFirst(repetidos);
       return contratoRepetido;
   }
    public void Ordenar(){
        ordenarContratos(this.contratosRefuerzo);
        ordenarContratos(this.contratosVacaciones);
        ordenarContratos(this.contratosBaja);
        
   }
   private void ordenarContratos(ArrayList<Contrato> contratos){
       int izda, i;
        Contrato a_desplazar;
        for (izda=1; izda < contratos.size(); izda++){
            a_desplazar = contratos.get(izda);
            for (i=izda; i > 0 && es_menor(a_desplazar,contratos.get(i-1)); i--)
                contratos.set(i,contratos.get(i-1));
            contratos.set(i,a_desplazar);
        }
   }
   public int obtenerNumeroMes(String mes){
       int numero_mes=0;
       if (mes.contentEquals("Enero")){
           numero_mes=1;
       }else if (mes.contentEquals("Febrero")){
           numero_mes=2;
       }else if (mes.contentEquals("Marzo")){
           numero_mes=3;
       }else if (mes.contentEquals("Abril")){
           numero_mes=4;
       }else if (mes.contentEquals("Mayo")){
           numero_mes=5;
       }else if (mes.contentEquals("Junio")){
           numero_mes=6;
       }else if (mes.contentEquals("Julio")){
           numero_mes=7;
       }else if (mes.contentEquals("Agosto")){
           numero_mes=8;
       }else if (mes.contentEquals("Septiembre")){
           numero_mes=9;
       }else if (mes.contentEquals("Octubre")){
           numero_mes=10;
       }else if (mes.contentEquals("Noviembre")){
           numero_mes=11;
       }else if (mes.contentEquals("Diciembre")){
           numero_mes=12;
       }
       return numero_mes;
   }
   public String obtenerMes(int numero_mes){
       String mes=null;
       if (numero_mes==1){
           mes="Enero";
       }else if (numero_mes==2){
           mes="Febrero";
       }else if (numero_mes==3){
           mes="Marzo";
       }else if (numero_mes==4){
           mes="Abril";
       }else if (numero_mes==5){
           mes="Mayo";
       }else if (numero_mes==6){
           mes="Junio";
       }else if (numero_mes==7){
           mes="Julio";
       }else if (numero_mes==8){
           mes="Agosto";
       }else if (numero_mes==9){
           mes="Septiembre";
       }else if (numero_mes==10){
           mes="Octubre";
       }else if (numero_mes==11){
           mes="Noviembre";
       }else if (numero_mes==12){
           mes="Diciembre";
       }
       return mes;
   }

   public Pair<Pair<Integer, Integer>,Integer> numeroMesesRefuerzo(){
       ArrayList<Contrato> mesesRefuerzo= new ArrayList();
       Pair<Pair<Integer, Integer>,Integer> tiempoTrabajado= new Pair(new Pair(0,0),0);
       Pair<Integer,Integer> totalFechaMesesDias= new Pair(0,0);
       Pair<Pair<Integer, Integer>,Integer>  aux= new Pair(0,0);
       Contrato contrato;
       int numero_dias=0, meses=0, anho=0;
       
       for(int i=0; i<this.contratosRefuerzo.size(); i++){
           if(Integer.parseInt(this.contratosRefuerzo.get(i).getAnhoComienzo())== fecha.get(Calendar.YEAR) 
                   || (Integer.parseInt(this.contratosRefuerzo.get(i).getAnhoFinalizacion())== fecha.get(Calendar.YEAR) )){
                if(this.obtenerNumeroMes(this.contratosRefuerzo.get(i).getMesComienzo())<  fecha.get(Calendar.MONTH)+1 
                        && this.obtenerNumeroMes(this.contratosRefuerzo.get(i).getMesFinalizacion())<fecha.get(Calendar.MONTH))
                   mesesRefuerzo.add(this.contratosRefuerzo.get(i));
                else if(this.obtenerNumeroMes(this.contratosRefuerzo.get(i).getMesFinalizacion())>=fecha.get(Calendar.MONTH)){
                    contrato=new Contrato(contratosRefuerzo.get(i).getDiaCominezo(),fecha.get(Calendar.DAY_OF_MONTH),contratosRefuerzo.get(i).getMesComienzo(),this.obtenerMes(fecha.get(Calendar.MONTH)+1), contratosRefuerzo.get(i).getAnhoComienzo(),Integer.toString(fecha.get(Calendar.YEAR)));
                    mesesRefuerzo.add(contrato);
               }
           }else if(Integer.parseInt(this.contratosRefuerzo.get(i).getAnhoComienzo())== fecha.get(Calendar.YEAR)-1){
               if(this.obtenerNumeroMes(this.contratosRefuerzo.get(i).getMesComienzo())> fecha.get(Calendar.MONTH)+1)
                   mesesRefuerzo.add(this.contratosRefuerzo.get(i));
               else if(this.obtenerNumeroMes(this.contratosRefuerzo.get(i).getMesFinalizacion())>fecha.get(Calendar.MONTH)+1){ 
                    if(this.contratosRefuerzo.get(i).getDiaCominezo()<fecha.get(Calendar.DAY_OF_MONTH)){
                        contrato=new Contrato(fecha.get(Calendar.DAY_OF_MONTH)+1,contratosRefuerzo.get(i).getDiaFinalizacion(),this.obtenerMes(fecha.get(Calendar.MONTH)+1),contratosRefuerzo.get(i).getMesFinalizacion(),contratosRefuerzo.get(i).getAnhoFinalizacion(),contratosRefuerzo.get(i).getAnhoFinalizacion());
                        mesesRefuerzo.add(contrato);
                    }else if(this.obtenerNumeroMes(this.contratosRefuerzo.get(i).getMesComienzo())==fecha.get(Calendar.MONTH)+1){
                        contrato=new Contrato(contratosRefuerzo.get(i).getDiaCominezo(),contratosRefuerzo.get(i).getDiaFinalizacion(),this.obtenerMes(fecha.get(Calendar.MONTH)+1),contratosRefuerzo.get(i).getMesFinalizacion(),contratosRefuerzo.get(i).getAnhoFinalizacion(),contratosRefuerzo.get(i).getAnhoFinalizacion());
                        mesesRefuerzo.add(contrato);
                    }
                }else if(this.obtenerNumeroMes(this.contratosRefuerzo.get(i).getMesFinalizacion())==fecha.get(Calendar.MONTH)+1
                        && contratosRefuerzo.get(i).getDiaFinalizacion()>fecha.get(Calendar.DAY_OF_MONTH)){
                    contrato=new Contrato(fecha.get(Calendar.DAY_OF_MONTH)+1,contratosRefuerzo.get(i).getDiaFinalizacion(),this.obtenerMes(fecha.get(Calendar.MONTH)+1),contratosRefuerzo.get(i).getMesFinalizacion(),contratosRefuerzo.get(i).getAnhoFinalizacion(),contratosRefuerzo.get(i).getAnhoFinalizacion());
                    mesesRefuerzo.add(contrato);
               }
           }
               
       }
       for(int j=0; j<mesesRefuerzo.size(); j++){
           aux=numeroDiasContrato(mesesRefuerzo.get(j));
           numero_dias+=aux.getFirst().getFirst();
           meses+=aux.getFirst().getSecond();
           anho+=aux.getSecond();
           if(ajustarMes(mesesRefuerzo.get(j))){
              numero_dias-=aux.getFirst().getFirst();
              meses+=1; 
           }
       }
       if(meses>12){
           anho+=meses/12;
           meses=meses%12;
       }
       if(numero_dias>=30){
           meses+=numero_dias/30;
           numero_dias-=30;
       }
       totalFechaMesesDias.setFirst(numero_dias);
       totalFechaMesesDias.setSecond(meses);
       tiempoTrabajado.setFirst(totalFechaMesesDias);
       tiempoTrabajado.setSecond(anho);
       return tiempoTrabajado;
   }
      public Pair<Pair<Integer, Integer>,Integer> numeroMesesRefuerzo(int dia_refuerzo, int mes_refuerzo, int anho_refuerzo){
       ArrayList<Contrato> mesesRefuerzo= new ArrayList();
       Pair<Pair<Integer, Integer>,Integer> tiempoTrabajado= new Pair(new Pair(0,0),0);
       Pair<Integer,Integer> totalFechaMesesDias= new Pair(0,0);
       Pair<Pair<Integer, Integer>,Integer>  aux= new Pair(0,0);
       Contrato contrato;
       int numero_dias=0, meses=0, anho=0;
       
       for(int i=0; i<this.contratosRefuerzo.size(); i++){
           if(Integer.parseInt(this.contratosRefuerzo.get(i).getAnhoComienzo())== anho_refuerzo
                   || (Integer.parseInt(this.contratosRefuerzo.get(i).getAnhoFinalizacion())== anho_refuerzo)){ 
                if(this.obtenerNumeroMes(this.contratosRefuerzo.get(i).getMesComienzo())<  mes_refuerzo 
                        && this.obtenerNumeroMes(this.contratosRefuerzo.get(i).getMesFinalizacion())< mes_refuerzo)
                   mesesRefuerzo.add(this.contratosRefuerzo.get(i));
                else if(this.obtenerNumeroMes(this.contratosRefuerzo.get(i).getMesFinalizacion())>=mes_refuerzo){
                    contrato=new Contrato(contratosRefuerzo.get(i).getDiaCominezo(),dia_refuerzo,contratosRefuerzo.get(i).getMesComienzo(),this.obtenerMes(mes_refuerzo), contratosRefuerzo.get(i).getAnhoComienzo(),Integer.toString(anho_refuerzo));
                    mesesRefuerzo.add(contrato);
               }
           }else if(Integer.parseInt(this.contratosRefuerzo.get(i).getAnhoComienzo())== anho_refuerzo-1){
               if(this.obtenerNumeroMes(this.contratosRefuerzo.get(i).getMesComienzo())> mes_refuerzo)
                   mesesRefuerzo.add(this.contratosRefuerzo.get(i));
               else if(this.obtenerNumeroMes(this.contratosRefuerzo.get(i).getMesFinalizacion())>mes_refuerzo){ 
                    if(this.contratosRefuerzo.get(i).getDiaCominezo()<dia_refuerzo){
                        contrato=new Contrato(dia_refuerzo+1,contratosRefuerzo.get(i).getDiaFinalizacion(),this.obtenerMes(mes_refuerzo),contratosRefuerzo.get(i).getMesFinalizacion(),contratosRefuerzo.get(i).getAnhoFinalizacion(),contratosRefuerzo.get(i).getAnhoFinalizacion());
                        mesesRefuerzo.add(contrato);
                    }else if(this.obtenerNumeroMes(this.contratosRefuerzo.get(i).getMesComienzo())==mes_refuerzo){
                        contrato=new Contrato(contratosRefuerzo.get(i).getDiaCominezo(),contratosRefuerzo.get(i).getDiaFinalizacion(),this.obtenerMes(mes_refuerzo),contratosRefuerzo.get(i).getMesFinalizacion(),contratosRefuerzo.get(i).getAnhoFinalizacion(),contratosRefuerzo.get(i).getAnhoFinalizacion());
                        mesesRefuerzo.add(contrato);
                    }
                }else if(this.obtenerNumeroMes(this.contratosRefuerzo.get(i).getMesFinalizacion())==mes_refuerzo
                        && contratosRefuerzo.get(i).getDiaFinalizacion()>dia_refuerzo){
                    contrato=new Contrato(mes_refuerzo,contratosRefuerzo.get(i).getDiaFinalizacion(),this.obtenerMes(mes_refuerzo),contratosRefuerzo.get(i).getMesFinalizacion(),contratosRefuerzo.get(i).getAnhoFinalizacion(),contratosRefuerzo.get(i).getAnhoFinalizacion());
                    mesesRefuerzo.add(contrato);
               }
           }
               
       }
       for(int j=0; j<mesesRefuerzo.size(); j++){
           aux=numeroDiasContrato(mesesRefuerzo.get(j));
           numero_dias+=aux.getFirst().getFirst();
           meses+=aux.getFirst().getSecond();
           anho+=aux.getSecond();
           if(ajustarMes(mesesRefuerzo.get(j))){
              numero_dias-=aux.getFirst().getFirst();
              meses+=1; 
           }
       }
       if(meses>12){
           anho+=meses/12;
           meses=meses%12;
       }
       if(numero_dias>=30){
           meses+=numero_dias/30;
           numero_dias-=30;
       }
       totalFechaMesesDias.setFirst(numero_dias);
       totalFechaMesesDias.setSecond(meses);
       tiempoTrabajado.setFirst(totalFechaMesesDias);
       tiempoTrabajado.setSecond(anho);
       return tiempoTrabajado;
   }
    public Pair<Pair<Integer, Integer>,Integer> tiempoTrabajoCorreos(){
       Pair<Pair<Integer, Integer>,Integer> tiempoTrabajado= new Pair(new Pair(0,0),0);
       Pair<Integer,Integer> totalFechaMesesDias= new Pair(0,0);
       Pair<Pair<Integer, Integer>,Integer>  aux= new Pair(0,0);
       int numero_dias=0, meses=0, anho=0;
       
       for(int i=0; i<this.contratosRefuerzo.size(); i++){
           aux=numeroDiasContrato(contratosRefuerzo.get(i));
           numero_dias+=aux.getFirst().getFirst();
           meses+=aux.getFirst().getSecond();
           anho+=aux.getSecond();
           if(ajustarMes(contratosRefuerzo.get(i))){
              numero_dias-=aux.getFirst().getFirst();
              meses+=1; 
           }
       }
       for(int i=0; i<this.contratosVacaciones.size(); i++){
           aux=numeroDiasContrato(contratosVacaciones.get(i));
           numero_dias+=aux.getFirst().getFirst();
           meses+=aux.getFirst().getSecond();
           anho+=aux.getSecond();
           if(ajustarMes(contratosVacaciones.get(i))){
              numero_dias-=aux.getFirst().getFirst();
              meses+=1; 
           }
       }
       for(int i=0; i<this.contratosBaja.size(); i++){
           aux=numeroDiasContrato(contratosBaja.get(i));
           numero_dias+=aux.getFirst().getFirst();
           meses+=aux.getFirst().getSecond();
           anho+=aux.getSecond();
           if(ajustarMes(contratosBaja.get(i))){
              numero_dias-=aux.getFirst().getFirst();
              meses+=1; 
           }   
       }
       if(meses>12){
           anho+=meses/12;
           meses=meses%12;
       }
       if(numero_dias>=30){
           meses+=numero_dias/30;
           numero_dias-=30;
       }
       totalFechaMesesDias.setFirst(numero_dias);
       totalFechaMesesDias.setSecond(meses);
       tiempoTrabajado.setFirst(totalFechaMesesDias);
       tiempoTrabajado.setSecond(anho);
       return tiempoTrabajado;
   }
    public boolean ajustarMes(Contrato contrato){
        boolean es_correcto=false;
        if(contrato.getMesComienzo().contentEquals(contrato.getMesFinalizacion()) && this.obtenerNumeroMes(contrato.getMesComienzo())<8){
            if((this.obtenerNumeroMes(contrato.getMesComienzo())%2!=0) && this.obtenerNumeroMes(contrato.getMesComienzo())!=2 && (contrato.getDiaCominezo()==1 && contrato.getDiaFinalizacion()==31))
                es_correcto=true;
            else if((this.obtenerNumeroMes(contrato.getMesComienzo())%2==0) && this.obtenerNumeroMes(contrato.getMesComienzo())!=2 && (contrato.getDiaCominezo()==1 && contrato.getDiaFinalizacion()==30))
                es_correcto=true;
            else if(this.obtenerNumeroMes(contrato.getMesComienzo())==2 && (contrato.getDiaCominezo()==1 && contrato.getDiaFinalizacion()==28) && (((Integer.parseInt(contrato.getAnhoComienzo())% 4 == 0)  && (Integer.parseInt(contrato.getAnhoComienzo())% 400 == 0) || (Integer.parseInt(contrato.getAnhoComienzo()) % 100 != 0))))
                es_correcto=true;
            else if (this.obtenerNumeroMes(contrato.getMesComienzo())==2 && (contrato.getDiaCominezo()==1 && contrato.getDiaFinalizacion()==29) && (((Integer.parseInt(contrato.getAnhoComienzo())% 4 == 0)  && (Integer.parseInt(contrato.getAnhoComienzo())% 400 != 0) || (Integer.parseInt(contrato.getAnhoComienzo()) % 100 == 0))))
                es_correcto=true;
        }else if(contrato.getMesComienzo().contentEquals(contrato.getMesFinalizacion())&& this.obtenerNumeroMes(contrato.getMesComienzo())>7)
            if((this.obtenerNumeroMes(contrato.getMesComienzo())%2!=0) && (contrato.getDiaCominezo()==1 && contrato.getDiaFinalizacion()==30))
                es_correcto=true;
            else if((this.obtenerNumeroMes(contrato.getMesComienzo())%2==0) && (contrato.getDiaCominezo()==1 && contrato.getDiaFinalizacion()==31))
                es_correcto=true;
        return es_correcto;
    }
   public Pair<Pair<Integer, Integer>,Integer> numeroDiasContrato(Contrato contrato){
       Pair<Pair<Integer, Integer>,Integer> totalFecha= new Pair(new Pair(0,0),0);
       Pair<Integer,Integer> totalFechaMesesDias= new Pair(0,0);
       int diasTipoMes=0;
       int diaInicio = contrato.getDiaCominezo();
       int diaFin= contrato.getDiaFinalizacion();
       int anioInicio=Integer.parseInt(contrato.getAnhoComienzo());
       int anioFin=Integer.parseInt(contrato.getAnhoFinalizacion());
       int mesInicio=obtenerNumeroMes(contrato.getMesComienzo());
       int mesFin=obtenerNumeroMes(contrato.getMesFinalizacion());
       int anios = 0;
       int mesesPorAnio = 0;
       int diasPorMes = 0;
       if (mesInicio == 2) {
		// Febrero
		if ((anioFin % 4 == 0) && ((anioFin % 4 == 0) && ((anioFin % 400 == 0) || (anioFin % 100 != 0)))) {
			// Bisiesto
			diasTipoMes = 29;
		} else {
			// No bisiesto
			diasTipoMes = 28;
		}
	} else if (mesInicio <= 7) {
		// De Enero a Julio los meses pares tienen 30 y los impares 31
		if (mesInicio % 2 == 0) {
			diasTipoMes = 30;
		} else {
			diasTipoMes = 31;
		}
	} else if (mesInicio > 7) {
		// De Julio a Diciembre los meses pares tienen 31 y los impares 30
		if (mesInicio % 2 == 0) {
			diasTipoMes = 31;
		} else {
			diasTipoMes = 30;
		}
	}
	//
	// Calculo de diferencia de año, mes y dia
	//
	if ((anioInicio > anioFin) || (anioInicio == anioFin && mesInicio > mesFin)
			|| (anioInicio == anioFin && mesInicio == mesFin && diaInicio > diaFin)) {
		// La fecha de inicio es posterior a la fecha fin
		
	} else {
		if (mesInicio <= mesFin) {
			anios = anioFin - anioInicio;
			if (diaInicio <= diaFin) {
				mesesPorAnio = mesFin - mesInicio;
                                if(diaFin==diasTipoMes+1 && mesFin!=7)
                                    diasPorMes = diaFin - diaInicio;
                                else    
                                    diasPorMes = diaFin - diaInicio+1;
			} else {
				if (mesFin == mesInicio) {
					anios = anios - 1;
				}
				mesesPorAnio = (mesFin - mesInicio - 1 + 12) % 12;
				if(diaFin==diasTipoMes+1 && mesFin!=7)
                                    diasPorMes = diaInicio - diaFin;
                                else    
                                    diasPorMes = diaInicio+1 - diaFin;
			}
		} else {
			anios = anioFin - anioInicio - 1;
			if (diaInicio > diaFin) {
				mesesPorAnio = mesFin - mesInicio - 1 + 12;
				if(diaFin==diasTipoMes+1 && mesFin!=7)
                                    diasPorMes = diaInicio - diaFin;
                                else    
                                    diasPorMes = diaInicio+1 - diaFin;
			} else {
				mesesPorAnio = mesFin - mesInicio + 12;
				if(diaFin==diasTipoMes+1 && mesFin!=7)
                                    diasPorMes = diaFin - diaInicio;
                                else    
                                    diasPorMes = diaFin - diaInicio+1;
			}
		}
	}
       totalFechaMesesDias.setFirst(diasPorMes);
       totalFechaMesesDias.setSecond(mesesPorAnio);
       totalFecha.setFirst(totalFechaMesesDias);
       totalFecha.setSecond(anios);
       return totalFecha;
   }
   
    public boolean es_menor(Contrato contrato1, Contrato contrato2){
       boolean es_menor=false;
                int primer_numero=Integer.parseInt(contrato1.getAnhoComienzo());
                int segundo_numero=Integer.parseInt(contrato2.getAnhoComienzo());
                if (primer_numero<segundo_numero){
                    es_menor=true;
                }else if(primer_numero==segundo_numero){
                    if(obtenerNumeroMes(contrato1.getMesComienzo()) < obtenerNumeroMes(contrato2.getMesComienzo())){
                        es_menor=true;
                    }else if (obtenerNumeroMes(contrato1.getMesComienzo())==obtenerNumeroMes(contrato2.getMesComienzo())){
                        if(contrato1.getDiaCominezo()<=contrato2.getDiaCominezo()){
                            es_menor=true;
                        }
                    }
                }
        return es_menor;   
    }
   public void reiniciar(){
        this.contratosRefuerzo.clear();
        this.contratosVacaciones.clear();
        this.contratosBaja.clear();
    }
   public void deshacer(){
        int ultimoRefuerzo=contratosRefuerzo.size()-1;
        int ultimoVacaciones=contratosVacaciones.size()-1;
        int ultimoBajas=contratosBaja.size()-1;
        if (ventanaPrincipal.tipoContratoSeleccionado()==1){
            this.contratosRefuerzo.remove(ultimoRefuerzo);
        }else if(ventanaPrincipal.tipoContratoSeleccionado()==2){
            this.contratosVacaciones.remove(ultimoVacaciones);
        }else if(ventanaPrincipal.tipoContratoSeleccionado()==3)
            this.contratosBaja.remove(ultimoBajas);
        imprimirDatosPaneles();
    }
   public void Eliminar(int tipoContrato, Contrato contrato){
            if(tipoContrato==1){
                eliminarContrato(this.contratosRefuerzo , contrato);
            }else if(tipoContrato==2){
                eliminarContrato(this.contratosVacaciones, contrato);
            }else if(tipoContrato==3){
                eliminarContrato(this.contratosBaja , contrato);
            }
   }
       
   public void eliminarContrato(ArrayList <Contrato> contratos ,Contrato contrato){
       for(int i=0; i<contratos.size(); i++){
               if((contratos.get(i).getDiaCominezo()==contrato.getDiaCominezo() && contratos.get(i).getDiaFinalizacion()==contrato.getDiaFinalizacion()
                       && this.obtenerNumeroMes(contratos.get(i).getMesComienzo())==this.obtenerNumeroMes(contrato.getMesComienzo()) && this.obtenerNumeroMes(contratos.get(i).getMesFinalizacion())==this.obtenerNumeroMes(contrato.getMesFinalizacion()) 
                       && contratos.get(i).getAnhoComienzo().contentEquals(contrato.getAnhoComienzo()) && contrato.getAnhoFinalizacion().contentEquals(contrato.getAnhoFinalizacion()))){
                       contratos.remove(i);
               }      
       }
   }
   public void guardaDatos(int numeroTipoContrato, File fichero) throws IOException{ 
            if(numeroTipoContrato==1){
                ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(fichero , true));
                for(int i=0; i<this.contratosRefuerzo.size(); i++){
                    oos.writeObject(contratosRefuerzo.get(i));
                }
                oos.close();
            }else if(numeroTipoContrato==2){
                ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(fichero, true));
                for(int i=0; i<this.contratosVacaciones.size(); i++){
                    oos.writeUnshared(contratosVacaciones.get(i));
                }
                oos.close();
            }else{
                
                ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(fichero, true));
                for(int i=0; i<this.contratosBaja.size(); i++){
                    oos.writeObject(contratosBaja.get(i));
                }
                oos.close();
            }
   }  
   public void leeDatos(File ficheroRefuerzo, File ficheroVacaciones, File ficheroBajas) throws ClassNotFoundException, IOException{
        if(ficheroRefuerzo.length()!=0){
            ObjectInputStream oisRefuerzo = new ObjectInputStream(new FileInputStream(ficheroRefuerzo));
            this.leerDatosArchivo(oisRefuerzo, this.contratosRefuerzo);
        }
        if(ficheroVacaciones.length()!=0){
            ObjectInputStream oisVacaciones = new ObjectInputStream(new FileInputStream(ficheroVacaciones));
            this.leerDatosArchivo(oisVacaciones, this.contratosVacaciones);
        }
        if(ficheroBajas.length()!=0){   
            ObjectInputStream oisBajas= new ObjectInputStream(new FileInputStream(ficheroBajas));
            this.leerDatosArchivo(oisBajas, this.contratosBaja);
        }                  
   }
   public void leerDatosArchivo(ObjectInputStream ois,  ArrayList<Contrato> contratos) throws ClassNotFoundException, IOException{
        try{
            Object aux = ois.readObject();
            contratos.add((Contrato)aux);
            while(aux!=null){    
                aux=ois.readObject();
                contratos.add((Contrato)aux);
            }
        }catch(IOException io){
            System.out.print(" ");
        }finally{
            ois.close();
        } 
   }
   public void setContrato(int tipoContrato, Contrato contrato, File fichero){
       if(tipoContrato==1){
           this.contratosRefuerzo.add(contrato);
           try {
                guardaDatos(tipoContrato, fichero);
           } catch (IOException ex) {
                Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
           }
       }else if(tipoContrato==2){
           this.contratosVacaciones.add(contrato);
           try {
               guardaDatos(tipoContrato, fichero);
           } catch (IOException ex) {
               Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
           }
       }else if(tipoContrato==3){
           this.contratosBaja.add(contrato);
           try {
                guardaDatos(tipoContrato, fichero);
           } catch (IOException ex) {
                Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
           }
       }
   }
    
    public void imprimirDatosPaneles(){
        ventanaPrincipal.getPanelContratosRefuerzo().setText(null);
            for(int i=0; i<this.contratosRefuerzo.size(); i++){ 
                ventanaPrincipal.getPanelContratosRefuerzo().append("Fecha de comienzo: " + this.contratosRefuerzo.get(i).getDiaCominezo() + " / " + this.contratosRefuerzo.get(i).getMesComienzo() + " / " + this.contratosRefuerzo.get(i).getAnhoComienzo() + "\n" +
                        "Fecha de finalización: " + this.contratosRefuerzo.get(i).getDiaFinalizacion() + " / " + this.contratosRefuerzo.get(i).getMesFinalizacion() + " / " + this.contratosRefuerzo.get(i).getAnhoFinalizacion() + "\n" +
                        "\n");                
            }
        ventanaPrincipal.getPanelContratosVacaciones().setText(null);
            for(int i=0; i<this.contratosVacaciones.size(); i++){ 
                this.ventanaPrincipal.getPanelContratosVacaciones().append("Fecha de comienzo: " + this.contratosVacaciones.get(i).getDiaCominezo() + " / " + this.contratosVacaciones.get(i).getMesComienzo() + " / " + this.contratosVacaciones.get(i).getAnhoComienzo() + "\n" +
                        "Fecha de finalización " + this.contratosVacaciones.get(i).getDiaFinalizacion() + " / " + this.contratosVacaciones.get(i).getMesFinalizacion() + " / " + this.contratosVacaciones.get(i).getAnhoFinalizacion() + "\n" +
                        "\n");                
            }
        ventanaPrincipal.getPanelContratosBajas().setText(null);
            for(int i=0; i<this.contratosBaja.size(); i++){ 
                this.ventanaPrincipal.getPanelContratosBajas().append("Fecha de comienzo: " + this.contratosBaja.get(i).getDiaCominezo() + " / " + this.contratosBaja.get(i).getMesComienzo() + " / " + this.contratosBaja.get(i).getAnhoComienzo() + "\n" +
                        "Fecha de finalización: " + this.contratosBaja.get(i).getDiaFinalizacion() + " / " + this.contratosBaja.get(i).getMesFinalizacion() + " / " + this.contratosBaja.get(i).getAnhoFinalizacion() + "\n" +
                        "\n");                
            }
    }
    public void imprimirTiempoTrabajado(){
            Pair<Pair<Integer,Integer>,Integer> tiempoTrabajado= new Pair();
            tiempoTrabajado=tiempoTrabajoCorreos();
            ventanaPrincipal.getPanelTiempotrabajado().setText(null);
            ventanaPrincipal.getPanelTiempotrabajado().append("Tienes "+  tiempoTrabajado.getFirst().getFirst() + " dias y " + tiempoTrabajado.getFirst().getSecond() + " meses y "+ tiempoTrabajado.getSecond() +" años\n");
    }
}
