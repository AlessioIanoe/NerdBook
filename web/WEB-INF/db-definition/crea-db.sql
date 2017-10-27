



insert into postType(id_postType, nome)
values 	(default, 'text'), 
	(default, 'image'), 
	(default, 'link'); 
		
insert into gruppo(id_gruppo, nome, admin)
values 	(default, 'Gruppo2', 1),
	(default, 'Gruppo1', 4),
        (default, 'Eliminare', 5);
		
insert into post(id_post, autore, utenteDestinatario, gruppoDestinatario, tipo, content, urlAllegato)
values	(default, 1, 1, null, 1, 'Come va?', ''),
        (default, 1, 2, null, 2, '', 'img/Ok.jpg'),
        (default, 2, 1, null, 2, 'Check out this manzo', 'img/PaoloBrosio.jpeg'),
        (default, 3, 3, null, 2, '', 'img/111.jpg'),
        (default, 2, 2, null, 1, 'Ciao e buona giornata a tutti amici', ''),
        (default, 4, 4, null, 3, 'Mario!!!', 'https://www.youtube.com/watch?v=5qgOeXbFQ9U'),
        (default, 1, null, 2, 1, 'Quanto sale si mette per fare il pane?', ''),
        (default, 5, null, 3, 1, 'aaaaaaaaaaaaaa ','');
		
insert into amicizia(utente, amico)
values 	(1, 2),
        (1, 3),
        (1, 4),
        (2, 1),
        (2, 4),
        (3, 1),
        (3, 4),
        (4, 1),
        (4, 2),
        (4, 3),
        (5, 1),
        (1, 5);
       
		
insert into appartenenzaGruppo( utente, gruppo)
values 	(1, 1),
        (2, 1),
        (3, 1),
        (4, 1),
        (1, 2),
        (3, 2),
        (4, 2),
        (5, 3),
        (1, 3);
		
