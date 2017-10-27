
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<header>
    <div id="navbar">
        <div id="title">NerdBook</div>

        <nav class="navbar navleft">                          
            <ul>
                <c:if test="${not empty utenteLoggato.password}">
                    <li><a class="nqui" href="bacheca.jsp">Bacheca</a></li>
                    <li><a class="nqui" href="profilo.jsp">Profilo</a></li>
                    </c:if>
            </ul>                
        </nav>
        <nav class="navbar navright">                        
            <ul>
                <c:if test="${not empty utenteLoggato.password}">

                    <li><img class="imgelemen" title="profilo" alt="immagine avatar"src="${utenteLoggato.getUrlFotoProfilo()}"><a class="nomeu" href="bacheca.html">
                            <c:out value="${utenteLoggato.getNomeCognome()}"/></a></li>
                        </c:if>

                <c:if test="${not empty utenteLoggato.password}">
                    <li><a class="logout" href="Login?logout=1">Logout</a></li>
                    </c:if>
                    <c:if test="${empty utenteLoggato.password}">
                    <li><a class="login" href="login.html">Login</a></li>
                    </c:if>
            </ul>   
        </nav>
    </div>
</header>
