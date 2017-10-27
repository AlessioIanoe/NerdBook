<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<div id="sidebar">

    <div id="barraRicerca">
        <input id="searchField" type="text" placeholder="cerca utente"/>
        <button id="searchButton">Cerca</button>
    </div>
    <div id="persone">
        <h3>Persone</h3>
        <nav class="barralaterale">
            <div id="personeList">
                <ul id="personeLi">
                    <c:forEach var="usr" items="${utenti}"> 
                        <li><div class="botdiv"><a href="bacheca.html?user=0${usr.getId()}">${usr.getNomeCognome()}</a></div></li>
                            </c:forEach>
                </ul>
            </div>                
        </nav>

    </div>
    <div id="gruppi">
        <h3>Gruppi</h3>
        <nav class="barralaterale">                        
            <ul> 
                <c:forEach var="grp" items="${gruppi}">                
                    <li><div class="botdiv"><a href="bacheca.html?group=0${grp.getId()}">${grp.getNomeGruppo()}</a></div></li>                
                        </c:forEach>
            </ul>                
        </nav>
    </div>
</div>
