<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
    <head>
        <title>Login</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="author" content="Alessio Ianoe">
        <link rel="stylesheet" type="text/css" href="style.css" media="screen">

    </head>
    
    <body>
        
            <div id="titlebar">
                NerdBook
            </div>
        
      <div id="pageDescLog">

        <!--FormIscrizione-->
      <div id="formIscrizione">
          
         
          <c:if test="${invalidData == true}">
                    <div id="invalidDataWarning">I dati inseriti non sono corretti</div>
          </c:if>
        <form action="Login" method="post">
            
                 <label id="labelemail" for="email"><b>Email</b></label>
                 <br>
                 <input  type="text" placeholder="email" name="email" id="email">
                 <br>
                <label id="labelpsw"><b>Password</b></label>
                <br>
                <input type="password" placeholder="Password" name="password" id="password">
                </br>
                <button  type='submit' class="bottoneLogin" value="Login">Login</button>
        </form>
           <c:if test="${invalidData == true}">
                    <div id="invalidDataWarning">I dati inseriti non sono corretti</div>
          </c:if>
                </div>

          
            </div>
               
        
       
    
    
    </body>
</html>
