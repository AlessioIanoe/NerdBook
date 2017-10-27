<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="amm.nerdbook.classi.UtenteFactory"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>


<div class="confPost" class="post">
    <c:if test="${proprietario != null}">
        <c:set var="urlAction2" value="Bacheca?user=${proprietario.getId()}&click=2"/> 
    </c:if>
    <c:if test="${gruppo != null}">
        <c:set var="urlAction2" value="Bacheca?group=${gruppo.getId()}&click=2"/>
    </c:if>
    <form class="nuovopost" name="nuovopost" action="${urlAction2}" method="post"> 

        <c:if test="${!empty userd2}">
            <h3>Stai scrivendo nella bacheca di :</h3><h2> ${proprietario.getNomeCognome()}</h2></c:if>

        <c:if test="${!empty groupd2}">
            <h3>Stai scrivendo nel gruppo ${gruppo.getNomeGruppo()}</h3></c:if> 
            <h3>Stai pubblicando il seguente Post:</h3> 
        <c:if test="${!empty testo2}">
            <p>${testo2}</p>
        </c:if>
        <c:if test="${!empty allegato2}">
            <p><strong> url : </strong> ${allegato2}</p>
        </c:if>

        <input class="hidden" hidden type="text" name="testo" id="testo"
               value="${testo2}" />
        <input class="hidden" hidden type="text" name="allegato" id="allegato"
               value="${allegato2}" />
        <input class="hidden" hidden type="text" name="userp" id="userp"
               value="${userp2}" />
        <input class="hidden" hidden type="text" name="userd" id="userd"
               value="${userd2}" />
        <input class="hidden" hidden type="text" name="groupd" id="groupd"
               value="${groupd2}" />

        <button type="submit" name="thereIsPost" value="Confirmed">Posta</button>
    </form>
</div> 








