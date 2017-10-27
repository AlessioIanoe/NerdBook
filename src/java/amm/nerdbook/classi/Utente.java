/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amm.nerdbook.classi;
import java.util.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 *
 * @author ianuk
 */
public class Utente {

    private int id;
    private String nome;
    private String cognome;
    private Date dataNascita; 
    private String biografia; 
    private String password;
    private String urlFotoProfilo;
    private boolean superUtente;
    private String email;


    public Utente() {
        id = 0;
        nome = null;
        cognome = null;
        dataNascita = null;
        biografia = null;
        password = null;
        urlFotoProfilo = null;
        superUtente = false;
    }


    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the cognome
     */
    public String getCognome() {
        return cognome;
    }

    /**
     * @param cognome the cognome to set
     */
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    /**
     * @return the dataNascita
     */
    
    /**
     * @return the biografia
     */
    public String getBiografia() {
        return biografia;
    }

    /**
     * @param biografia the biografia to set
     */
    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the urlFotoProfilo
     */
    public String getUrlFotoProfilo() {
        return urlFotoProfilo;
    }

    /**
     * @param urlFotoProfilo the urlFotoProfilo to set
     */
    public void setUrlFotoProfilo(String urlFotoProfilo) {
        this.urlFotoProfilo = urlFotoProfilo;
    }
    
    

    /**
     * @return the dataNascita
     */
    public Date getDataNascita() {
        return dataNascita;
    }
    
    public String getDataNascitaString() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        
        return df.format(this.dataNascita);
    }

    /**
     * @param dataNascita the dataNascita to set
     */
    public void setDataNascita(Date dataNascita) {
        this.dataNascita = dataNascita;
    }
    
    public void setDataNascita(String dataNascita) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        
        this.dataNascita = df.parse(dataNascita);
    }

    /**
     * @return the superUtente
     */
    public boolean isSuperUtente() {
        return superUtente;
    }

    /**
     * @param superUtente the superUtente to set
     */
    public void setSuperUtente(boolean superUtente) {
        this.superUtente = superUtente;
    }

    
    public void setEmail(String email) {
        this.email= email;
    }
   
    public String getEmail(String email) 
    {
        return this.email=email;
    }
    
    public String getNomeCognome()
    {
        if(this.nome != null && this.cognome != null)
        {
            return this.nome + " " + this.cognome;
        }
        else
        {
            return this.getEmail(email);
        }
        
    }
}