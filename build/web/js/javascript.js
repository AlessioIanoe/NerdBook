
function createElement(usr){
    var link = $("<a>")
        .attr("href", "bacheca.jsp?user=0" + usr.id)
        .html(usr.nome + " " + usr.cognome);

var userData= $("<li>")
        .append(link);
    
    
    return $("<li>")
        .append(userData);
}

function createError(){
 
    return $("<div>")
        .attr("id","invalidDataWarning")
        .html("utente non trovato");
}

function stateSuccess(data){
    var userListPage = $("#personeList");
    var userUl = $("<ul>").attr("id","personeLi");
    
    if(data.length === 0)
    {
        $(userListPage).empty();
        $(userListPage).append(createError());
    }else
    {
        $(userListPage).empty();
        $(userListPage).append(userUl);
        for(var instance in data){
            userUl.append(createElement(data[instance]));
        };
    }
    
}
function stateFailure(data, state){
    console.log(state);
}

 $(document).ready(function(){
    
    $("#searchField").on('keyup',function(){
        var wantedUser = $("#searchField")[0].value;
        
        $.ajax({
            url: "filter.json",
            data:{
                cmd:"search",
                nomeUtenteCercato: wantedUser
            },
            dataType:"json",
            success: function(data, state){
                stateSuccess(data)
            },
            error: function(data, state){
                stateFailure(data, state)
            }
        });
    })
 });

$(document).ready(function(){
    $("#searchButton").click(function(){
        
        var wantedUser = $("#searchField")[0].value;
        
        $.ajax({
            url: "filter.json",
            data:{
                cmd:"search",
                nomeUtenteCercato: wantedUser
            },
            dataType:"json",
            success: function(data, state){
                stateSuccess(data)
            },
            error: function(data, state){
                stateFailure(data, state)
            }
        });
    })
});
 



