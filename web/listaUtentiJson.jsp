
<%@page contentType="application/json" pageEncoding="UTF-8"%>
<%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<json:array>
    <c:forEach var="utente" items="${listaUtenti}">
        <json:object>
            <json:property name="nome" value="${utente.getNome()}"/>
            <json:property name="cognome" value="${utente.getCognome()}"/>
            <json:property name="id" value="${utente.getId()}"/>
            <json:property name="email" value="${utente.getEmail()}"/>
        </json:object>
    </c:forEach>
</json:array>
