/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica4;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 *
 * @author Hector
 */
public class Operando extends Practica4{
    
    
    
    String[] Direccion(String Operando,String dir, int lin,String moddir,String codop,int operval){
        String[] Resultado = new String[] {"null", "null"};
        String  b=".err",Mdir="null", Res="null";
        int x=0,y=0,z=0; 
        boolean banRel=false;
        try{
        File f =new File(dir+b);
        FileWriter fw=new FileWriter(f,true);
        BufferedWriter error=new BufferedWriter(fw);
        
           // System.out.println("Codop antes: "+codop);
      if(Operando.matches("^\\%.*")||Operando.matches("^\\@.*")||Operando.matches("\\$.*")||Operando.matches("^\\#.*")||Operando.matches("^[0-9]+")||Operando.matches("^[a-zA-Z]+")||Operando.matches("^\\[.*")||Operando.matches("^\\-.*")||Operando.matches("^\\,.*")){
          /*   
          
        */  
         
          
          //System.out.println("Codop mod: "+codop);
         // System.out.println("A2: "+z);
          //Directo
          if(Operando.matches("^\\%.+")||Operando.matches("^\\@[0-7]+")||Operando.matches("\\$[0-9A-Fa-f]*")||Operando.matches("^[0-9]+")){
          //DIR
             int DIR=0;
              int tam=Operando.length();
              if(moddir.equals("DTV")){
                  Mdir=moddir;
              }else{
                  if(Operando.matches("^[0-9]*$")){
                  String dircad=Operando.substring(1,tam);
                  DIR=Integer.parseInt(dircad,16);  
                  if(DIR>=0||DIR<=255){
                      
                  Mdir="DIR";
                  
                  }
                  }
              }
              
              
              
              if(Operando.matches("^\\%.+")||Operando.matches("^\\@[0-7]+")||Operando.matches("\\$[0-9A-Fa-f]*")){
                  banRel=true;
              
              String dircad=Operando.substring(1,tam);
              DIR=Integer.parseInt(dircad,16);
              }
          if(Operando.matches("\\%.*")){
              
             // System.out.println("Binario "+Operando);
          }else{
              if(Operando.matches("\\@.*")){
                  
                  //System.out.println("Octal "+Operando);
              }else{
                  if(Operando.matches("\\$[0-9A-Fa-f]*"))
                  {
                      banRel=true;
                    //  System.out.println("Hexadecimal"+Operando);
                       String Hexcad=Operando.substring(1,tam);
                       int EXT=Integer.parseInt(Hexcad,16);
                       if(EXT>=256){
                           Mdir="EXT";
                           if(!Hexcad.matches("^0.*")){
                       x = EXT;  
                       y = ~x;   
                       z = y + 1;
                       Res=dectohex(z);
                      }
                       }
                  }else{
                      int op=Integer.parseInt(Operando);
                      if(op>=256){
                          if(moddir.equals("DTV")){
                              Mdir=moddir;
                          }
                          else{
                              Mdir="Ext";
                              
                          }
                      }
                    //  System.out.println("Decimal"+Operando);
                  }
                  
              }
              
          }
          
          }
           
          ///////////////////////////////////////////////////Indexados   IDX'S
          if(Operando.matches("^[-]*([0-9a-dA-D])*^,*([+|-])*([X|x|Y|y|sp|SP|pc|PC])*[+|-]*$")){
              banRel=true;
              String IDXcad=null;
              StringTokenizer IDX=new StringTokenizer(Operando,",");
              System.out.println("OperIdx: "+Operando);
              IDXcad =IDX.nextToken();
              if(IDXcad.matches("[a|A|b|B|d|D]")){
                  Mdir="IDX A";//Acumulador
              }
              
              if(IDXcad.matches("^[-]?[0-9]")){
                  banRel=true;
                  //contienen Decimales
                  int val=0;
                  val=Integer.parseInt(IDXcad);
                   // IDX 5Bits
                 if(val>=-16&&val<=15||Operando.matches(" ^,([+|-])*([X|x|Y|y|sp|SP|pc|PC])*[+|-]*$")){
                     
                     Mdir="IDX";
                 }
                 //IDX 9 Bits
                 if(val>=-256&&val<=-17||val>=16&&val<=255){
                     Mdir="IDX1";
                     
                 }
                 //IDX 16Bits
                 if(val<=-257&&val>=-32768||val>=256&&val<=65535){
                     Mdir="IDX2";
                 }
                 
              }else{
                  //Contiene Decimal
                  if(Operando.matches(",([+|-])*([X|x|Y|y|sp|SP|pc|PC])*[+|-]*$")){
                      Mdir="IDX";
                      
                  }
              }
              if(Mdir!="null"){
                  error.write("Linea: "+lin+" Error el Operando no cumple los requerimientos para Indexados contiene: "+Operando);
                  error.newLine();
              }
          }
          
          /////////////////////////////////////////////////////////////16 Bits Indirecto
          if(Operando.matches("^\\[[-0-9]*.*")){
              System.out.println("Operando[]: "+Operando);
              
          if(Operando.matches("\\[([0-9])*,([X|x|Y|y|sp|SP|pc|PC])*\\]")){
              
              Mdir="[IDX2]";
          }else{
              error.write("Linea: "+lin+" Error el Operando no cumple los requerimientos para [IDX2] contiene: "+Operando);
              error.newLine();
              
              
          }
          }
          ////////////////////////////////////////////////////////////Indexado Indirecto de Acumulador
          if(Operando.matches("^\\[([D|d]){1,1},")){
              
              
          if(Operando.matches("\\[([D|d])*,([X|x|Y|y|sp|SP|pc|PC])*\\]")){
              Mdir="[D,IDX]";
              
          }else{
              error.write("Linea: "+lin+" Error el Operando no cumple los requerimientos para [D,IDX] contiene: "+Operando);
              error.newLine();
          }
          }
          
          ////////////////////////////////////////////////////////////Inmediato IMM8, IMM16
          if(Operando.matches("^#.+")){
              
              int IMM=0;
              int tam=Operando.length();
              String immcad=Operando.substring(1,tam);
              //con base
              if(immcad.matches("^\\@.*")||immcad.matches("^\\%.*")||immcad.matches("^\\$.*"))
                {
               //empieza octal
               if(immcad.matches("^\\@.*")){     
               immcad=Operando.substring(2,tam);
               IMM =Integer.parseInt(immcad, 16);
              
              if(IMM<=255||-256<=IMM){
                  //System.out.println("Entro A imm8");
                  if(moddir.equals("INM")){
                  Mdir="IMM8";
                  //return Mdir;
                  if(IMM>=-256&&IMM<=-1){
                       x = IMM;  
                       y = ~x;   
                       z = y + 1;
                       Res=dectooct(z);
                      }
                  }
              }
              else if(IMM<=65535||-32768<=IMM){
                  //System.out.println("Entro A imm16");
                  if(moddir.equals("INM")){
                  Mdir="IMM16";
                  //return Mdir;
                  if(IMM>=-32768&&IMM<=-1){
                       x = IMM;  
                       y = ~x;   
                       z = y + 1;
                       Res=dectooct(z);
                      }
                  }
              }
                }//termina octal
               //entra binario
              if(immcad.matches("^\\%.*")){     
               immcad=Operando.substring(2,tam);
               IMM =Integer.parseInt(immcad, 16);
              
              if(IMM<=255||-256<=IMM){
                  //System.out.println("Entro A imm8");
                  if(moddir.equals("INM")){
                  Mdir="IMM8";
                  //return Mdir;
                  if(IMM>=-256&&IMM<=-1){
                       x = IMM;  
                       y = ~x;   
                       z = y + 1;
                       Res=dectobin(z);
                      }
                  }
              }
              else if(IMM<=65535||-32768<=IMM){
                  //System.out.println("Entro A imm16");
                  if(moddir.equals("INM")){
                  Mdir="IMM16";
                  //return Mdir;
                  if(IMM>=-32768&&IMM<=-1){
                       x = IMM;  
                       y = ~x;   
                       z = y + 1;
                       Res=dectobin(z);
                      }
                  }
              }
                }//termina binario
              //entra hexadecimal 
              if(immcad.matches("^\\$.*")){     
               immcad=Operando.substring(2,tam);
               IMM =Integer.parseInt(immcad, 16);//transforma de hexa a decimal para evaluar 
              //System.out.println("Hexadecimal"+IMM);
              if(IMM<=255||-256<=IMM){
                  //System.out.println("Entro A imm8");
                  if(moddir.equals("INM")){
                  Mdir="IMM8";
                  //return Mdir;
                  if(IMM>=-256&&IMM<=-1||immcad.matches("^0.*")){
                       x = IMM;  
                       y = ~x;   
                       z = y + 1;
                       Res=dectohex(z);
                      }
                  }
              }
              else if(IMM<=65535||-32768<=IMM){
                  //System.out.println("Entro A imm16");
                  if(moddir.equals("INM")){
                  Mdir="IMM16";
                  //return Mdir;
                  if(IMM>=-32768&&IMM<=-1||immcad.matches("^0.*")){
                       x = IMM;  
                       y = ~x;   
                       z = y + 1;
                       Res=dectohex(z);
                      }
                  }
              }
                }//termina hexadecimal
          }else{//sin base 
                  IMM =Integer.parseInt(immcad, 16);
                  if(IMM<=255||-256<=IMM){
                  //System.out.println("Entro A imm8");
                      if(moddir.equals("INM")){
                  Mdir="IMM8";
                  //return Mdir;
                  if(IMM>=-256&&IMM<=-1){
                       x = IMM;  
                       y = ~x;   
                       z = y + 1;
                       
                      }
                      }
                  }
                  else if(IMM<=65535||-32768<=IMM){
                   //System.out.println("Entro A imm16");
                      if(moddir.equals("INM")){
                  Mdir="IMM16";
                  //return Mdir;
                  if(IMM>=-32768&&IMM<=-1){
                       x = IMM;  
                       y = ~x;   
                       z = y + 1;
                      }
                      }
                  }
              }
          }
          
          /////////////////////////////////////////////////////////////////////Relativo REL8 & REL16
          if(Operando.matches("^[0-9a-zA-Z].*")&&banRel==false||Operando.matches("^\\@.*")&&banRel==false||Operando.matches("^\\%.*")&&banRel==false||Operando.matches("^\\$.*")&&banRel==false){
              int REL=0;
              int tam=Operando.length();
           //  System.out.println("moddir: "+moddir);
              if(Operando.matches("^\\@.*")||Operando.matches("^\\%.*")||Operando.matches("^\\$.*"))
                {
                    //Entra Octal
                    if(Operando.matches("^\\@.*")){
              String relcad=Operando.substring(1,tam);
              REL =Integer.parseInt(relcad, 16);
              if(REL<=255||REL>=-256){
                  if(codop.matches("^[lL].*")){
                  if(moddir.equals("REL")){
                      Mdir="REL16";
                      if(REL>=-256&&REL<=-1){
                       x = REL;  
                       y = ~x;   
                       z = y + 1;
                       Res=dectooct(z);
                      }
                  }
                  }else{
                    if(moddir.equals("REL")){
                      Mdir="REL8";
                      if(REL>=-256&&REL<=-1){
                       x = REL;  
                       y = ~x;   
                       z = y + 1;
                       Res=dectooct(z);
                      }
                   }
                  }
              }
              else if(codop.matches("^[lL].*")||REL<=65535||REL>=-32768){
                   if(moddir.equals("REL")){
                      Mdir="REL16";
                      if(REL>=-32768&&REL<=-1){
                       x = REL;  
                       y = ~x;   
                       z = y + 1;
                       Res=dectooct(z);
                      }
                  }
              }
                }//Termina Octal
                    
                  //Empieza Binario
                 if(Operando.matches("^\\%.*")){
              String relcad=Operando.substring(1,tam);
              REL =Integer.parseInt(relcad, 16);
              if(REL<=255||REL>=-256){
                  if(codop.matches("^[lL].*")){
                  if(moddir.equals("REL")){
                      Mdir="REL16";
                      if(REL>=-256&&REL<=-1){
                       x = REL;  
                       y = ~x;   
                       z = y + 1;
                       Res=dectobin(z);
                      }
                  }
                  }else{
                    if(moddir.equals("REL")){
                      Mdir="REL8";
                      if(REL>=-256&&REL<=-1){
                       x = REL;  
                       y = ~x;   
                       z = y + 1;
                       Res=dectobin(z);
                      }
                   }
                  }
              }
              else if(codop.matches("^[lL].*")||REL<=65535||REL>=-32768){
                   if(moddir.equals("REL")){
                      Mdir="REL16";
                      if(REL>=-32768&&REL<=-1){
                       x = REL;  
                       y = ~x;   
                       z = y + 1;
                       Res=dectobin(z);
                      }
                  }
              }
                }//Termina Binario
                 //Empieza Hexadecimal
                 if(Operando.matches("^\\$.*")){
              String relcad=Operando.substring(1,tam);
              REL =Integer.parseInt(relcad, 16);
              if(REL<=255||REL>=-256){
                  if(codop.matches("^[lL].*")){
                  if(moddir.equals("REL")){
                      Mdir="REL16";
                      if(REL>=-256&&REL<=-1||relcad.matches("^0.*")){
                       x = REL;  
                       y = ~x;   
                       z = y + 1;
                       Res=dectohex(z);
                      }
                  }
                  }else{
                    if(moddir.equals("REL")){
                      Mdir="REL8";
                      if(REL>=-256&&REL<=-1||relcad.matches("^0.*")){
                       x = REL;  
                       y = ~x;   
                       z = y + 1;
                       Res=dectohex(z);
                      }
                   }
                  }
              }
              else if(codop.matches("^[lL].*")||REL<=65535||REL>=-32768){
                   if(moddir.equals("REL")){
                      Mdir="REL16";
                      if(REL>=-32768&&REL<=-1||relcad.matches("^0.*")){
                       x = REL;  
                       y = ~x;   
                       z = y + 1;
                       Res=dectohex(z);
                       
                      }
                  }
              }
                }//Termina Hexadecimal
                }else{//sin base
                  if(Operando.matches("^[0-9].*")){
                  REL =Integer.parseInt(Operando, 16);
                  if(REL<=255||REL>=-256){
                    if(codop.matches("^[lL].*")){
                  if(moddir.equals("REL")){
                      Mdir="REL16";
                      
                  }
                  }else{
                        if(moddir.equals("REL")){
                      Mdir="REL8";
                       
                        }
                  }
              }
              else if(codop.matches("^[lL].*")||REL<=65535||REL>=-32768){
                   if(moddir.equals("REL")){
                      Mdir="REL16";
                      
                  }
              }
                  
              }else{
                     if(Operando.matches("^[a-zA-Z][a-zA-Z]*$")&&codop.matches("^[lL].*")){
                         if(moddir.equals("REL")){
                      Mdir="REL16";
                          }
                         }
                     else{
                         if(moddir.equals("REL")){
                      Mdir="REL8";
                          }
                     }
                  }
              }
                  
          }
          
          
          if(Mdir=="null"){
          error.write("Linea: "+lin+" Error no se encontro ningun modo de direccionamiento");
          error.newLine();
          }
         
      } 
       else{
          
          error.write("Linea: "+lin+" Error el modo de Direccionamiento no es valido");
          error.newLine();
          
      }
       
          error.close();
        
        }catch(Exception e){
            System.out.println("Hubo un problema en los modos de direccionamiento: "+e);
            
        }
        System.out.println("Mdir: "+Mdir+"Res op: "+Res);
        Resultado[0]=Mdir;
        Resultado[1]=Res;
        System.out.println("Mdir: "+Resultado[0]+"Res op: "+Resultado[1]);
      return Resultado;
    }
    
    public static int hextodec(String hexdecnum)
    {
             int decimal = Integer.parseInt(hexdecnum, 16);
             return decimal;
    }
    
    public static String dectohex(int dec)
    {
        String hex = Integer.toHexString(dec);
        return hex;
    }
    public String dectobin(int dec){
        
        
        String bin = Integer.toBinaryString(dec);
        
        return bin;
    }
    public String dectooct(int dec){
        
        String oct = Integer.toOctalString(dec);
        return oct;
    }
  
    
}
