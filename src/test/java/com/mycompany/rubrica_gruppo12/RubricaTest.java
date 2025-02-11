/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.rubrica_gruppo12;

import java.util.HashSet;
import java.util.TreeSet;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author LELE
 */
public class RubricaTest {
    Rubrica instance;    
    
    public RubricaTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp()  throws Exception {
    instance = new Rubrica();
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getElenco method, of class Rubrica.
     */
    @Test
    public void testGetElenco() throws Exception{
        System.out.println("getElenco");
        Contatto c2 = new Contatto("Luca","De Iuliis",null,null); 
        instance.aggiungiContatto(c2);
        TreeSet<Contatto> result = instance.getElenco();
        instance.toString();
        if (!result.contains(c2)){
            fail("errore GetElenco");
            }
    }

    /**
     * Test of aggiungiContatto method, of class Rubrica.
     */
    @Test
    public void testAggiungiContatto() throws Exception {
        System.out.println("aggiungiContatto");
        Contatto c1 = new Contatto("Luca","De Iuliis",null,null); 
        instance.aggiungiContatto(c1);
        if (instance.getContatto(c1) == null){
        fail("errore aggiungi contatto");
        }
    }

    /**
     * Test of rimuoviContatto method, of class Rubrica.
     */
    @Test
    public void testRimuoviContatto() throws Exception{
        System.out.println("rimuoviContatto");
        Contatto c1 = new Contatto("Luca","De Iuliis",null,null); 
        instance.aggiungiContatto(c1);
        instance.rimuoviContatto(c1);
        if (instance.getContatto(c1) != null){
        fail("errore aggiungi contatto");
        }
    }

    /**
     * Test of Ricerca method, of class Rubrica.
     */
    @Test
    public void testRicerca() throws Exception{
        System.out.println("Ricerca");   
        HashSet<Contatto> filtro = new HashSet();  
        Contatto c1 = new Contatto("Luca","De Iuliis",null,null);         
        Contatto c2 = new Contatto("Gabriele","Di Lieto",null,null);         
        instance.aggiungiContatto(c1);        
        instance.aggiungiContatto(c2);        
        String cerca = "Luca";
        instance.Ricerca(cerca,filtro);
        HashSet<Contatto> result = new HashSet();
        result.add(c1);
        assertEquals(result, filtro);
    }

    /**
     * Test of esporta method, of class Rubrica.
     * Testa sia esporta che importa
     */
    @Test
    public void testEsporta() throws Exception {
        System.out.println("esporta");
        Contatto c1 = new Contatto("Luca","De Iuliis",null,null);
        instance.aggiungiContatto(c1);         
        String filePath = null;
        if(filePath != null){
            instance.esporta(filePath);
            instance.importa(filePath);
            if(instance.getContatto(c1) == null){
               fail ("Errore Esporta/Importa");
            }
        }                        
    }

    /**
     * Test of toString method, of class Rubrica.
     */
    @Test
    public void testToString() throws Exception{
        System.out.println("toString");
        String expResult = "Contatto: [nome=Luca, cognome=De Iuliis, mail=null, numeri=null]\n";
        Contatto c1 = new Contatto("Luca","De Iuliis",null,null);                 
        instance.aggiungiContatto(c1);           
        String result = instance.toString();
        assertEquals(expResult, result);
    }
    
}
