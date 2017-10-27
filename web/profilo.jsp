
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>


<!DOCTYPE html>
<html>
    <head>
        <title>Nerdbook-Profilo</title>
        <meta http-equiv = "content-type" content = "text/html; charset=utf-8" />
        <meta name="author" content="Alessio Ianoe">
        <link href="style.css" rel="stylesheet" type="text/css" media="screen" />
        <script src="js/jquery-3.2.1.min.js"></script>
        <script src="js/javascript.js"></script>
    </head>

    <body>
        <div id="page">
            <!--header -->
            <c:set var="title" value="Bacheca Personale" scope="request"/>
            <jsp:include page="header.jsp"/>

            <!--Barra di navigazione tra le pagine del sito-->
            <c:set var="page" value="Bacheca Personale" scope="request"/>
            <jsp:include page="sidebar.jsp"/>


            <div id="content">
                <div class="form">
                    <c:choose>
                        <c:when test="${click == 1}">
                            <div id="formProfilo">

                                <div class="datiProfilo">
                                    <h3>Dati inseriti correttamente</h3>

                                    <c:if test="${!empty newNome}">
                                        <p><strong>nome:</strong> ${newNome}</p>
                                    </c:if>
                                    <c:if test="${!empty newCognome}">
                                        <p><strong>cognome:</strong> ${newCognome}</p>
                                    </c:if>
                                    <c:if test="${!empty newDataNascita}">
                                        <p><strong>data di nascita:</strong> ${newDataNascita}</p>
                                    </c:if>
                                    <c:if test="${!empty newImg}">
                                        <p><strong>immagine del profilo:</strong> ${newImg}</p>
                                    </c:if>
                                    <c:if test="${!empty newBio}">
                                        <p><strong>Biografia:</strong> ${newBio}</p>
                                    </c:if>
                                    <c:if test="${!empty newUsername}">
                                        <p><strong>E-mail</strong> ${newEmail}</p>
                                    </c:if>
                                    <c:if test="${!empty newPassword}">
                                        <p><strong>password:</strong> ${newPassword}</p>
                                    </c:if>
                                </div>

                            </div>
                        </c:when>
                        <c:otherwise>
                            <div id="formProfilo">

                                <c:if test="${click == 2}">
                                    <div id="invalidDataWarning">password non valida</div>
                                </c:if>
                                <div class="immaginiprofilo">
                                    <img class="profilo" alt="foto profilo" src="${utenteLoggato.getUrlFotoProfilo()}"/>
                                </div>
                                <div class="areaform">
                                    <form action="Profilo" method="post">
                                        <c:choose>
                                            <c:when test="${click == 3}">
                                                <p class="center"><strong>Sei sicuro di voler cancellare il profilo?</strong></p>

                                                <button type="submit" name="cancellaProfilo" value="deleteOk">Cancella Profilo</button>
                                            </c:when>

                                            <c:otherwise>
                                                <div class="textform">
                                                    <label for="username"><b>Nome</b></label> 
                                                    <input type="text" id="name" name="name" placeholder="Nome" 
                                                           value="${utenteLoggato.getNome()}">
                                                </div>
                                                <div class="textform">
                                                    <label for="lastname"><b>Cognome</b></label> 
                                                    <input type="text" id="surname" name="surname" placeholder="Cognome"
                                                           value="${utenteLoggato.getCognome()}">
                                                </div>

                                                <div class="textform">
                                                    <label for="img"><b>Immagine</b></label> 
                                                    <input type="url" name="img" id="img" placeholder="inserisci immagine"                                 
                                                           value="${utenteLoggato.getUrlFotoProfilo()}">
                                                </div>

                                                <div class="textform">

                                                    <label for="bday"><b>Data di nascista</b></label> 
                                                    <input type="date" name="data" id="data" placeholder="inserisci la tua data di nascita"                          
                                                           value="${utenteLoggato.getDataNascitaString()}">
                                                </div>

                                                <div class="textform">
                                                    <label for="email"><b>Biografia</b></label> 
                                                    <input type="text" name="email" id="frase" placeholder="inserisci la tua biografia"
                                                           value="${utenteLoggato.getBiografia()}">
                                                </div>

                                                <div class="textform">
                                                    <label for="email"><b>Email</b></label>
                                                    <input type="email" name="email" id="email"
                                                           value="${utenteLoggato.getEmail()}" />
                                                </div>

                                                <div class="textform">
                                                    <label for="primapsw"><b>Password</b></label> 
                                                    <input type="password" id="pswd" name="pswd" placeholder="inserisci password"
                                                           value="${utenteLoggato.getPassword()}">
                                                </div>

                                                <div class="textform">
                                                    <label for="verifica"><b>Conferma password</b></label> 
                                                    <input type="password" id="confpswd"  name="confpswd" placeholder="inserisci nuovamente password"
                                                           value="${utenteLoggato.getPassword()}">     
                                                </div>

                                                <button type="submit" name="modificaProfilo" value="needConfirm">Modifica</button>

                                                <button type="submit" name="cancellaProfilo" value="deleteProfile">Cancella Profilo</button>
                                            </c:otherwise>
                                        </c:choose>


                                    </form>

                                </div>

                            </c:otherwise>
                        </c:choose>

                    </div>
                </div>
            </div>
        </div>
    </body>
</html>


