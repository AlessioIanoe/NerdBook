/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */ 
package amm.nerdbook.classi;

/**
 *
 * @author ianuk
 */
public class Post {
    
public enum Type {
        TEXT, IMAGE, LINK
    };
    
    private int id;
    private Utente autore;
    private Utente user;
    private Gruppo group;
    private String content;
    private Type postType;
    private String urlAllegato;
    

    public Post() {
        id = 0;
        autore = null; 
        user = null; 
        group = null;
        content = "";
        postType = Type.TEXT;
        urlAllegato = "";
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
     * @return the user
     */
    public Utente getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(Utente user) {
        this.user = user;
    }

    /**
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return the postType
     */
    public Type getPostType() {
        return postType;
    }

    /**
     * @param postType the postType to set
     */
    public void setPostType(Type postType) {
        this.postType = postType;
    }
   
    /**
     * @return the urlAllegato
     */
    public String getUrlAllegato() {
        return urlAllegato;
    }

    /**
     * @param urlAllegato the urlAllegato to set
     */
    public void setUrlAllegato(String urlAllegato) {
        this.urlAllegato = urlAllegato;
    }

    /**
     * @return the group
     */
    public Gruppo getGroup() {
        return group;
    }

    /**
     * @param group the group to set
     */
    public void setGroup(Gruppo group) {
        this.group = group;
    }
    
    /**
     * @return the autore
     */
    public Utente getAutore() {
        return autore;
    }

    /**
     * @param autore the autore to set
     */
    public void setAutore(Utente autore) {
        this.autore = autore;
    }

    
    public void riconoscimentoPost()
    {
        if((this.urlAllegato.indexOf("jpg")>-1)||
        (this.urlAllegato.indexOf("jpeg")>-1)||
                (this.urlAllegato.indexOf("png")>-1)){
            this.setPostType(Type.IMAGE);}
        else{this.setPostType(Type.LINK);}
    }
    
}

