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
public class PostFactory {
   
    private static PostFactory singleton;

    public static PostFactory getInstance() {
        if (singleton == null) {
            singleton = new PostFactory();
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


    private PostFactory() {}

    public Post getPostById(int id) {
        try {
            Connection conn = DriverManager.getConnection(connectionString, "ammdb", "ammdb");
            
            String query = 
                      "select * from post "
                    + "join posttype on post.tipo = posttype.id_postType "
                    + "where id_post = ?";
            
            PreparedStatement stmt = conn.prepareStatement(query);
            
            stmt.setInt(1, id);
            
            ResultSet res = stmt.executeQuery();

            if (res.next()) {
                Post current = new Post();
                current = this.compilaPost(res);
                
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

    public List getPostByUtente(Utente usr) {

        List<Post> listaPost = new ArrayList<Post>();

        try {
            Connection conn = DriverManager.getConnection(connectionString, "ammdb", "ammdb");
            
            String query = 
                      "select * from post "
                    + "join posttype on post.tipo = posttype.id_postType "
                    + "where utenteDestinatario = ? "
                    + "or gruppoDestinatario in "
                    +   "(select gruppo from appartenenzaGruppo "
                    +   "where utente = ?) "
                    + "order by id_post desc";
            
            
            PreparedStatement stmt = conn.prepareStatement(query);
            
            stmt.setInt(1, usr.getId());
            stmt.setInt(2, usr.getId());
            
            ResultSet res = stmt.executeQuery();

            while (res.next()) {
                
                Post current = new Post();
                current = this.compilaPost(res);
                
                listaPost.add(current);
            }

            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return listaPost;
    }
    
    
    
    public List getPostByGruppo(Gruppo gr) {
        List<Post> listaPost = new ArrayList<Post>();

        try {

            Connection conn = DriverManager.getConnection(connectionString, "ammdb", "ammdb");
            
            String query = 
                      "select * from post "
                    + "join posttype on post.tipo = posttype.id_postType "
                    + "where gruppoDestinatario = ? order by id_post desc";
            
            PreparedStatement stmt = conn.prepareStatement(query);
            
            stmt.setInt(1, gr.getId());
            
            ResultSet res = stmt.executeQuery();

            while (res.next()) {
                
                Post current = new Post();
                current = this.compilaPost(res);
                
                listaPost.add(current);
            }

            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return listaPost;
    }
    
    public List getPostByAutore(Utente usr) {
        List<Post> listaPost = new ArrayList<Post>();

        try {
            Connection conn = DriverManager.getConnection(connectionString, "ammdb", "ammdb");
            
            String query = 
                      "select * from post "
                    + "join posttype on post.tipo = posttype.id_postType "
                    + "where autore = ? order by id_post desc";
            
            PreparedStatement stmt = conn.prepareStatement(query);
            
            stmt.setInt(1, usr.getId());
            
            ResultSet res = stmt.executeQuery();

            while (res.next()) {
                
                Post current = new Post();
                current = this.compilaPost(res);
                
                listaPost.add(current);
            }

            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return listaPost;
    }
    
    public void addNewPost(Post post){
        try {
            Connection conn = DriverManager.getConnection(connectionString, "ammdb", "ammdb");
            
            String query = 
                      "insert into post(id_post, autore, utenteDestinatario, "
                    + "gruppoDestinatario, tipo, content, urlAllegato) "
                    + "values	(default, ?, ?, ?, ?, ?, ?)";
            
            PreparedStatement stmt = conn.prepareStatement(query);
            
            stmt.setInt(1, post.getAutore().getId());
            if(post.getUser() != null)
            {
                stmt.setInt(2, post.getUser().getId());
                stmt.setNull(3, java.sql.Types.INTEGER);
            }else if(post.getGroup() != null)
            {
                stmt.setNull(2, java.sql.Types.INTEGER);
                stmt.setInt(3, post.getGroup().getId());
            }else
            {
                throw new SQLException("impossibile salvare il post");
            }
            stmt.setInt(4, this.postTypeFromEnum(post.getPostType()));
            stmt.setString(5, post.getContent());
            stmt.setString(6, post.getUrlAllegato());

            stmt.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    private Post compilaPost(ResultSet res) throws SQLException{
        UtenteFactory utenteFactory = UtenteFactory.getInstance();
        GruppoFactory gruppoFactory = GruppoFactory.getInstance();
        Post current = new Post();
        
        current.setId(res.getInt("id_post"));
        Utente autore = utenteFactory.getUtenteById(res.getInt("autore"));
        current.setAutore(autore);
        Utente utenteDestinatario = utenteFactory.getUtenteById(res.getInt("utenteDestinatario"));
        current.setUser(utenteDestinatario);
        Gruppo gruppoDestinatario = gruppoFactory.getGruppoById(res.getInt("gruppoDestinatario"));
        current.setGroup(gruppoDestinatario);
        current.setPostType(this.postTypeFromString(res.getString("nome")));
        current.setContent(res.getString("content"));
        current.setUrlAllegato(res.getString("urlAllegato"));
        
        return current;
    }
    
    private Post.Type postTypeFromString(String type){
        
        if(type.equals("link"))
        {
            return Post.Type.LINK;
        }else if(type.equals("image"))
        {
            return Post.Type.IMAGE;
        }
        return Post.Type.TEXT;
        
    }
    
    private int postTypeFromEnum(Post.Type type){
        
        switch (type) {
            case IMAGE:
                return 2;
            case LINK:
                return 3;
            default:
                return 1;
        }
    }
}