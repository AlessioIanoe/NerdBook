
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="amm.nerdbook.classi.UtenteFactory"%>
<%@page import="amm.nerdbook.*"%>

<!DOCTYPE html>

<c:forEach var="post" items="${posts}">
    <div class="newpost">

        <div class="profilo">
            <img class="imgprofilo" title="profilo" alt="immagine profilo" src="${post.getAutore().getUrlFotoProfilo()}">
            <div class="nameprofilo"><a href="bacheca.html?user=0${post.getAutore().getId()}">${post.getAutore().getNomeCognome() }</a></div>
        </div>
        <div class="testopost"><p>${post.getContent()}</p></div>
        <c:if test="${post.postType == 'LINK'}"><div class="link_post"><a href="${post.getUrlAllegato()}">${post.getUrlAllegato()}</a></div></c:if>
        <c:if test="${post.postType == 'IMAGE'}"><div class="img_post"> <img alt="immagine casuale" src="${post.getUrlAllegato()}"> </div></c:if>
        </div>
</c:forEach>
