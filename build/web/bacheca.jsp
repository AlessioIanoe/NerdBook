
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>

<html>
    <head>
        <title>Nerdbook-Bacheca</title>
        <meta http-equiv = "content-type" content = "text/html; charset=utf-8" />
        <meta name="author" content="Alessio Ianoe">
        <link href="style.css" rel="stylesheet" type="text/css" media="screen" />
        <script src="js/jquery-3.2.1.min.js"></script>
       <script src="js/javascript.js"></script>
    </head>

    <body>
        <div id="page">

            <!--header-->
            <c:set var="title" value="Bacheca Personale" scope="request"/>
            <jsp:include page="header.jsp"/>

            <-!navbar-->
            <c:set var="page" value="Bacheca Personale" scope="request"/>
            <jsp:include page="sidebar.jsp"/>

            <div id="content">

                <c:if test="${proprietario != null}">
                    <div id="stato">
                        <img class="imgelemen" title="profilo" alt="immagine avatar"src="${proprietario.getUrlFotoProfilo()}">
                        <p><strong><c:out value="${proprietario.getNome()}"/> :</strong></p>
                        <p><c:out value="${proprietario.getBiografia()}"/></p>
                    </div>
                </c:if>
                <c:if test="${gruppo != null}">
                    <div id="stato">
                        <p><strong>${gruppo.getNomeGruppo()} :</strong></p>
                    </div>
                </c:if>

                <c:choose>
                    <c:when test="${empty newpost}">
                        <div id="divnuovopost" class="post">
                            <c:if test="${proprietario != null}">
                                <c:set var="urlAction" value="Bacheca?user=${proprietario.getId()}"/> 
                            </c:if>
                            <c:if test="${gruppo != null}">
                                <c:set var="urlAction" value="Bacheca?group=${gruppo.getId()}"/>
                            </c:if>
                            <c:if test="${!empty errorMessage}">
                                <div id="invalidDataWarning">${errorMessage}</div>
                            </c:if>

                            <form class="nuovopost" name="nuovopost" action="${urlAction}" method="post"> 
                                <div class="nuovopost">
                                    <div class="profilo"><img class="imgprofilo" title="profilo" alt="immagine profilo" src="${utenteLoggato.getUrlFotoProfilo()}"></div>
                                </div>

                                <textarea  name="testo" id="testo"></textarea>
                                <div class="nuovopost">
                                    <input type="text" name="allegato" id="allegato" placeholder="inserisci allegato">
                                </div>

                                <input class="hidden" type="text" hidden name="userp" id="userp"
                                       value="${utenteLoggato.getId()}" />

                                <input class="hidden" type="text" hidden name="userd" id="userd"
                                       value="${proprietario.getId()}" />

                                <input class="hidden" type="text" hidden name="groupd" id="groupd"
                                       value="${gruppo.getId()}" />

                                <button type="submit" name="thereIsPost" value="needConfirm">Crea post</button>
                            </form>
                        </div>

                    </c:when>
                    <c:when test="${newpost == 'true'}">
                        <div id="contentpost">
                            <c:set var="page" value="Bacheca Personale" scope="request"/>
                            <jsp:include page="newpost.jsp"/>
                        </div>       
                    </c:when>

                    <c:otherwise>
                        <c:if test="${!empty proprietario}">
                            <div id ="confermaPost">
                                <h3>Hai scritto sulla bacheca di <a href="bacheca.html?user=0${proprietario.getId()}">${proprietario.getNomeCognome()}</a></h3>
                            </div>
                        </c:if>

                        <c:if test="${!empty gruppo}">
                            <div id ="confermaPost">
                                <h3>Hai scritto sulla bacheca del gruppo <a href="bacheca.html?group=0${gruppo.getId()}">${gruppo.getNomeGruppo()}</a></h3>
                            </div>
                        </c:if>
                    </c:otherwise>
                </c:choose>

                <div id="contentpost">
                    <c:set var="page" value="Bacheca Personale" scope="request"/>
                    <jsp:include page="post.jsp"/>
                </div>
            </div>
        </div>
    </div>
</body>    
</html>


