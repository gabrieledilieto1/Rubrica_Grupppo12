/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.rubrica_gruppo12;

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
public class NumTelefonoTest {
    
    NumTelefono instance;
    
    public NumTelefonoTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    
    /**
     * Test of returnNum method, of class Email.
     */
    @Test
    public void testReturnNum() throws Exception {
        System.out.println("returnNum");
        instance = new NumTelefono();
        String c = "7200000000";
        instance.aggiungiNumTelefono(c);
        String exp = instance.returnNum(c);
        assertEquals(c, exp);
    }
    
    
    
    
    /**
     * Test of aggiungiNumTelefono method, of class NumTelefono.
     */
    @Test
    public void testAggiungiNumTelefono() throws Exception {
        System.out.println("aggiungiNumTelefono");
        instance = new NumTelefono();
        String c = "7200000000";
        instance.aggiungiNumTelefono(c);
        assertEquals(c, instance.returnNum(c));
    }

    /**
     * Test of modificaNumTelefono method, of class NumTelefono.
     */
    @Test
    public void testModificaNumTelefono() throws Exception {
        System.out.println("modificaNumTelefono");
        instance = new NumTelefono();
        String og = "8200000000";
        instance.aggiungiNumTelefono(og);
        String mod = "8200000001";
        instance.modificaNumTelefono(og, mod);
        if (instance.returnNum(og) != null){
            fail("errore modifica Mail");
        }    
        assertEquals(mod, instance.returnNum(mod));
    }

    /**
     * Test of toString method, of class NumTelefono.
     */
    @Test
    public void testToString() throws Exception {
        System.out.println("toString");
        instance = new NumTelefono();
        String c = "7200000000";
        instance.aggiungiNumTelefono(c);
        String expResult = "7200000000 ";
        String result = instance.toString();
        assertEquals(expResult, result);
    }
    
}
