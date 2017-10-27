/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amm.nerdbook.classi;

import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 
 * @author ianuk
 */

public class GruppoFactory {
    //Pattern Design Singleton
    private static GruppoFactory singleton;

    public static GruppoFactory getInstance() {
        if (singleton == null) {
            singleton = new  GruppoFactory();
        }
        return singleton;
    }
    
    
    private String connectionString;
    
    public void setConnectionString(String s){
	this.connectionString = s;
    }
    
    public String getConnectionString(){
            return this.connectionString;
    }


    private GruppoFactory() {}

    public Gruppo getGruppoById(int id) {
        
        try {
            Connection conn = DriverManager.getConnection(connectionString, "ammdb", "ammdb");
            
            String query = 
                      "select * from gruppo "
                    + "where id_gruppo = ?";
            
            PreparedStatement stmt = conn.prepareStatement(query);
            
            stmt.setInt(1, id);
            
            ResultSet res = stmt.executeQuery();

            if (res.next()) {
                Gruppo current = new Gruppo();
                current = this.compilaGruppo(res);

                stmt.close();
                conn.close();
                return current;
            }

            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public List getGrouptByAdmin(Utente usr) {

        List<Gruppo> listaGruppo = new ArrayList<Gruppo>();

        try {
            
            Connection conn = DriverManager.getConnection(connectionString, "ammdb", "ammdb");
            
            String query = 
                      "select * from gruppo "
                    + "where admin = ?";
            
            PreparedStatement stmt = conn.prepareStatement(query);
            
            stmt.setInt(1, usr.getId());
            
            ResultSet res = stmt.executeQuery();

            while (res.next()) {
                Gruppo current = new Gruppo();
                current = this.compilaGruppo(res);

                listaGruppo.add(current);
            }

            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return listaGruppo;
    }
    
    public List getGrouptByMembro(Utente usr) {

        List<Gruppo> listaGruppo = new ArrayList<Gruppo>();

        try {
            Connection conn = DriverManager.getConnection(connectionString, "ammdb", "ammdb");
            
            String query = 
                      "select gruppo.id_gruppo, gruppo.nome, gruppo.admin "
                    + "from gruppo join appartenenzaGruppo "
                    + "on gruppo.id_gruppo = appartenenzaGruppo.gruppo "
                    + "where utente = ?";
            
            PreparedStatement stmt = conn.prepareStatement(query);
            
            stmt.setInt(1, usr.getId());
            
            ResultSet res = stmt.executeQuery();

            while (res.next()) {
                Gruppo current = new Gruppo();
                current = this.compilaGruppo(res);

                listaGruppo.add(current);
            }

            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return listaGruppo;
    }
    
    public List getListaGruppi() {
        List<Gruppo> listaGruppo = new ArrayList<Gruppo>();

        try {
            Connection conn = DriverManager.getConnection(connectionString, "ammdb", "ammdb");
            
            String query = 
                      "select * from gruppo order by nome";
            
            PreparedStatement stmt = conn.prepareStatement(query);
            
            ResultSet res = stmt.executeQuery();

            while (res.next()) {
                Gruppo current = new Gruppo();
                current = this.compilaGruppo(res);

                listaGruppo.add(current);
            }

            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return listaGruppo;
        
    }
    
    private Gruppo compilaGruppo(ResultSet res) throws SQLException{
        UtenteFactory utenteFactory = UtenteFactory.getInstance();
        Gruppo current = new Gruppo();        
        current.setId(res.getInt("id_gruppo"));
        current.setNomeGruppo(res.getString("nome"));
        Utente admin = utenteFactory.getUtenteById(res.getInt("admin"));
        current.setAdmin(admin);        
        return current;
    }
}
