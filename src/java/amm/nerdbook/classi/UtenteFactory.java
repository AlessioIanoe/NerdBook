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
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author ianuk
 */
public class UtenteFactory {

    //Pattern Design Singleton
    private static UtenteFactory singleton;

    public static UtenteFactory getInstance() {
        if (singleton == null) {
            singleton = new UtenteFactory();
        }
        return singleton;
    }

    private String connectionString;
    private Object java;

    public void setConnectionString(String s) {
        this.connectionString = s;
    }

    public String getConnectionString() {
        return this.connectionString;
    }

    private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

    private UtenteFactory() {
    }

    public Utente getUtenteById(int id) {
        try {
           
            Connection conn = DriverManager.getConnection(connectionString, "ammdb", "ammdb");

            String query
                    = "select * from utente "
                    + "where id_utente = ?";

            // Prepared Statement
            PreparedStatement stmt = conn.prepareStatement(query);

            // Associo i valori
            stmt.setInt(1, id);

            // Esecuzione query
            ResultSet res = stmt.executeQuery();

            // ciclo sulle righe 
            if (res.next()) {
                Utente current = new Utente();
                current = this.compilaUtente(res);

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

    public int getIdByEmailAndPassword(String email, String password) {
        try {
            Connection connessione = DriverManager.getConnection(connectionString, "ammdb", "ammdb");

            String query = "SELECT id_utente FROM utente" + " WHERE email = ? AND password = ?";

            PreparedStatement stmt = connessione.prepareStatement(query);

            stmt.setString(1, email);
            stmt.setString(2, password);

            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                int id = result.getInt("id_utente");

                stmt.close();
                connessione.close();
                return id;
            }

            stmt.close();
            connessione.close();
        } catch (SQLException e) {
            Logger.getLogger(UtenteFactory.class.getName()).log(Level.SEVERE, null, e);
        }

        return -1;
    }

    public List getListaUtenti() {

        List<Utente> userList = new ArrayList<Utente>();

        try {
            Connection conn = DriverManager.getConnection(connectionString, "ammdb", "ammdb");

            String query
                    = "select * from utente order by cognome, nome";

            PreparedStatement stmt = conn.prepareStatement(query);

            ResultSet res = stmt.executeQuery();

            while (res.next()) {

                Utente current = new Utente();
                current = this.compilaUtente(res);

                userList.add(current);
            }

            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userList;
    }
    
    public List getListaUtenti(String search) {

        List<Utente> userList = new ArrayList<Utente>();

        try {
            Connection conn = DriverManager.getConnection(connectionString, "ammdb", "ammdb");
            
            String query = 
                      "select * from utente "
                    + "where nome like =? or cognome like =? "
                    + "order by cognome, nome";
            
            PreparedStatement stmt = conn.prepareStatement(query);
            
            stmt.setString(1, "%" + search + "%");
            stmt.setString(2, "%" + search + "%");
            
            ResultSet res = stmt.executeQuery();

            while (res.next()) {
                
               Utente current = new Utente();
               current = this.compilaUtente(res);

                userList.add(current);
            }

            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        
        return userList;
    }

    
    public void deleteUser(Utente usr) {
        try {
            Connection conn = DriverManager.getConnection(connectionString, "ammdb", "ammdb");
            PreparedStatement cancellaPostStmt = null;
            PreparedStatement cancellaUtenteStmt = null;
            int idUser = usr.getId();

            String cancellaPostString
                    = "delete from post "
                    + "where utenteDestinatario = ? or autore = ?";

            String cancellaUtenteString
                    = "delete from utente "
                    + "where id_utente = ?";

            try {
                conn.setAutoCommit(false);
                cancellaPostStmt = conn.prepareStatement(cancellaPostString);
                cancellaUtenteStmt = conn.prepareStatement(cancellaUtenteString);

                cancellaPostStmt.setInt(1, idUser);
                cancellaPostStmt.setInt(2, idUser);
                cancellaPostStmt.executeUpdate();

                cancellaUtenteStmt.setInt(1, idUser);
                cancellaUtenteStmt.executeUpdate();

                conn.commit();

            } catch (SQLException e) {
                e.printStackTrace();
                if (conn != null) {
                    try {
                        System.err.print("Transaction is being rolled back");
                        conn.rollback();
                    } catch (SQLException excep) {
                        excep.printStackTrace();
                    }
                }
            } finally {
                if (cancellaPostStmt != null) {
                    cancellaPostStmt.close();
                }
                if (cancellaUtenteStmt != null) {
                    cancellaUtenteStmt.close();
                }
                conn.setAutoCommit(true);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Utente compilaUtente(ResultSet res) throws SQLException {
        java.sql.Date dbDate;
        Utente current = new Utente();

        current.setId(res.getInt("id_utente"));
        current.setNome(res.getString("nome"));
        current.setCognome(res.getString("cognome"));
        dbDate = res.getDate("dataNascita");
        current.setDataNascita(new java.util.Date(dbDate.getTime()));
        current.setBiografia(res.getString("biografia"));
        current.setPassword(res.getString("password"));
        current.setUrlFotoProfilo(res.getString("urlFotoProfilo"));
        current.setSuperUtente(res.getBoolean("superUtente"));
        current.setEmail(res.getString("email"));

        return current;
    }

    public void updateNome(String utente, int id) {
        try {
            Connection conn = DriverManager.getConnection(connectionString, "ammdb", "ammdb");

            String query
                    = "update utente SET nome = ? "
                    + "where id_utente = ?";

            // Prepared Statement
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, utente);
            stmt.setInt(2, id);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateCognome(String utente, int id) {
        try {
            Connection conn = DriverManager.getConnection(connectionString, "ammdb", "ammdb");

            String query
                    = "update utente SET cognome = ? "
                    + "where id_utente = ?";

            // Prepared Statement
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, utente);
            stmt.setInt(2, id);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateImgProfilo(String utente, int id) {
        try {
            Connection conn = DriverManager.getConnection(connectionString, "ammdb", "ammdb");

            String query
                    = "update utente SET  urlFotoProfilo = ? "
                    + "where id_utente = ?";

            // Prepared Statement
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, utente);
            stmt.setInt(2, id);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateBiografia(String utente, int id) {
        try {
            Connection conn = DriverManager.getConnection(connectionString, "ammdb", "ammdb");

            String query
                    = "update utente SET biografia = ? "
                    + "where id_utente = ?";

            // Prepared Statement
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, utente);
            stmt.setInt(2, id);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateDataNascita(String utente, int id) {
        try {
            Connection conn = DriverManager.getConnection(connectionString, "ammdb", "ammdb");

            String query
                    = "update utente SET dataNascita = ? "
                    + "where id_utente = ?";

            // Prepared Statement
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, utente);
            stmt.setInt(2, id);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateEmail(String utente, int id) {
        try {
            Connection conn = DriverManager.getConnection(connectionString, "ammdb", "ammdb");

            String query
                    = "update utente SET email = ? "
                    + "where id_utente = ?";

            // Prepared Statement
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, utente);
            stmt.setInt(2, id);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updatePassword(String utente, int id) {
        try {
            Connection conn = DriverManager.getConnection(connectionString, "ammdb", "ammdb");

            String query
                    = "update utente SET password = ? "
                    + "where id_utente = ?";

            // Prepared Statement
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, utente);
            stmt.setInt(2, id);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
