/* Populate tables */
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('Andres', 'Guzman', 'profesor@bolsadeideas.com', '2017-08-01', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('John', 'Doe', 'john.doe@gmail.com', '2017-08-02', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('Linus', 'Torvalds', 'linus.torvalds@gmail.com', '2017-08-03', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('Jane', 'Doe', 'jane.doe@gmail.com', '2017-08-04', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('Rasmus', 'Lerdorf', 'rasmus.lerdorf@gmail.com', '2017-08-05', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('Erich', 'Gamma', 'erich.gamma@gmail.com', '2017-08-06', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('Richard', 'Helm', 'richard.helm@gmail.com', '2017-08-07', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('Ralph', 'Johnson', 'ralph.johnson@gmail.com', '2017-08-08', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('John', 'Vlissides', 'john.vlissides@gmail.com', '2017-08-09', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('James', 'Gosling', 'james.gosling@gmail.com', '2017-08-010', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('Bruce', 'Lee', 'bruce.lee@gmail.com', '2017-08-11', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('Johnny', 'Doe', 'johnny.doe@gmail.com', '2017-08-12', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('John', 'Roe', 'john.roe@gmail.com', '2017-08-13', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('Jane', 'Roe', 'jane.roe@gmail.com', '2017-08-14', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('Richard', 'Doe', 'richard.doe@gmail.com', '2017-08-15', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('Janie', 'Doe', 'janie.doe@gmail.com', '2017-08-16', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('Phillip', 'Webb', 'phillip.webb@gmail.com', '2017-08-17', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('Stephane', 'Nicoll', 'stephane.nicoll@gmail.com', '2017-08-18', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('Sam', 'Brannen', 'sam.brannen@gmail.com', '2017-08-19', '');  
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('Juergen', 'Hoeller', 'juergen.Hoeller@gmail.com', '2017-08-20', ''); 
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('Janie', 'Roe', 'janie.roe@gmail.com', '2017-08-21', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('John', 'Smith', 'john.smith@gmail.com', '2017-08-22', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('Joe', 'Bloggs', 'joe.bloggs@gmail.com', '2017-08-23', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('John', 'Stiles', 'john.stiles@gmail.com', '2017-08-24', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('Richard', 'Roe', 'stiles.roe@gmail.com', '2017-08-25', '');

/* Populate tabla productos */
INSERT INTO productos (nombre, precio, create_at) VALUES('Panasonic Pantalla LCD', 259990, NOW());
INSERT INTO productos (nombre, precio, create_at) VALUES('Sony Camara digital DSC-W320B', 123490, NOW());
INSERT INTO productos (nombre, precio, create_at) VALUES('Apple iPod shuffle', 1499990, NOW());
INSERT INTO productos (nombre, precio, create_at) VALUES('Sony Notebook Z110', 37990, NOW());
INSERT INTO productos (nombre, precio, create_at) VALUES('Hewlett Packard Multifuncional F2280', 69990, NOW());
INSERT INTO productos (nombre, precio, create_at) VALUES('Bianchi Bicicleta Aro 26', 69990, NOW());
INSERT INTO productos (nombre, precio, create_at) VALUES('Mica Comoda 5 Cajones', 299990, NOW());

/* Creamos algunas facturas */
INSERT INTO facturas (descripcion, observacion, cliente_id, create_at) VALUES('Factura equipos de oficina', null, 1, NOW());
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES(1, 1, 1);
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES(2, 1, 4);
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES(1, 1, 5);
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES(1, 1, 7);

INSERT INTO facturas (descripcion, observacion, cliente_id, create_at) VALUES('Factura Bicicleta', 'Alguna nota importante!', 1, NOW());
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES(3, 2, 6);


/* Creamos algunos usuarios con sus roles */
INSERT INTO users (username, password, enabled, nombre, apellido1, apellido2, email, fh_nacimiento) VALUES ('rafa','$2a$10$YxYo8dU5lp8gV5XVh.2RteH1AMy0NFuMzGQ0M0dQtgKRt25vGsQLe', 1, 'rafa', 'García', 'León', 'rafa@correo.es', '1983-06-16');
INSERT INTO users (username, password, enabled, nombre, apellido1, apellido2, email, fh_nacimiento) VALUES ('admin','$2a$10$YxYo8dU5lp8gV5XVh.2RteH1AMy0NFuMzGQ0M0dQtgKRt25vGsQLe',1, 'admin', 'admin', 'admin', 'admin@correo.es', '1983-06-16');
INSERT INTO users (username, password, enabled, nombre, apellido1, apellido2, email, fh_nacimiento) VALUES ('user','$2a$10$YxYo8dU5lp8gV5XVh.2RteH1AMy0NFuMzGQ0M0dQtgKRt25vGsQLe', 1, 'user', 'User', 'User', 'user@correo.es', '1983-06-16');
INSERT INTO users (username, password, enabled, nombre, apellido1, apellido2, email, fh_nacimiento) VALUES ('pepe','$2a$10$YxYo8dU5lp8gV5XVh.2RteH1AMy0NFuMzGQ0M0dQtgKRt25vGsQLe', 1, 'user2', 'User2', 'User2', 'user2@correo.es', '1983-06-16');
INSERT INTO users (username, password, enabled, nombre, apellido1, apellido2, email, fh_nacimiento) VALUES ('juan','$2a$10$YxYo8dU5lp8gV5XVh.2RteH1AMy0NFuMzGQ0M0dQtgKRt25vGsQLe', 1, 'user3', 'User3', 'User3', 'user3@correo.es', '1983-06-16');
INSERT INTO users (username, password, enabled, nombre, apellido1, apellido2, email, fh_nacimiento) VALUES ('rosa','$2a$10$YxYo8dU5lp8gV5XVh.2RteH1AMy0NFuMzGQ0M0dQtgKRt25vGsQLe', 1, 'user4', 'User4', 'User4', 'user4@correo.es', '1983-06-16');
INSERT INTO users (username, password, enabled, nombre, apellido1, apellido2, email, fh_nacimiento) VALUES ('luis','$2a$10$YxYo8dU5lp8gV5XVh.2RteH1AMy0NFuMzGQ0M0dQtgKRt25vGsQLe', 1, 'user5', 'User5', 'User5', 'user5@correo.es', '1983-06-16');

INSERT INTO authorities (user_id, authority) VALUES (1,'ROLE_USER');
INSERT INTO authorities (user_id, authority) VALUES (2,'ROLE_ADMIN');
INSERT INTO authorities (user_id, authority) VALUES (3,'ROLE_USER');
INSERT INTO authorities (user_id, authority) VALUES (4,'ROLE_USER');
INSERT INTO authorities (user_id, authority) VALUES (5,'ROLE_USER');
INSERT INTO authorities (user_id, authority) VALUES (6,'ROLE_USER');
INSERT INTO authorities (user_id, authority) VALUES (7,'ROLE_USER');

/* Cargamo la tabla de torneos*/
INSERT INTO torneos (nombre, descripcion, fecha, hora, lugar, precio, premio, foto, url) VALUES ('Quake Arena', 'Descripción del torneo de Quake Arena. Dicho juego se realizará al mejor de 50 rondas, con grupos de 10 y un máximo de 100 concursantes', '2020-02-20', '15:00', 'Asociación EG', 'Gratis', '50€ Primer equipo, 20€ Segundo equipo', 'quake.jpg', 'https://discord.gg/ByDAHZ3');
INSERT INTO torneos (nombre, descripcion, fecha, hora, lugar, precio, premio, foto, url) VALUES ('Unreal Tournament', 'Descripción del torneo de Unrel Tournament. Dicho juego se realizará al mejor de 50 rondas, con grupos de 10 y un máximo de 100 concursantes', '2020-04-16', '18:00', 'Online', 'Gratis', '50€ Primer equipo, 20€ Segundo equipo', 'unreal.jpg', 'https://discord.gg/ByDAHZ3');
INSERT INTO torneos (nombre, descripcion, fecha, hora, lugar, precio, premio, foto, url) VALUES ('Fornite', 'Descripción del torneo de Fornite. Dicho juego se realizará al mejor de 50 rondas, con grupos de 10 y un máximo de 100 concursantes', '2020-07-16', '20:00', 'Asociación EG', 'Gratis', '500€ Primer equipo, 20€ Segundo equipo', 'fornite.jpg', 'https://discord.gg/ByDAHZ3');
INSERT INTO torneos (nombre, descripcion, fecha, hora, lugar, precio, premio, foto, url) VALUES ('FIFA 2020', 'Descripción del torneo de Fifa 2020. Dicho juego se realizará al mejor de 50 rondas, con grupos de 10 y un máximo de 100 concursantes', '2020-07-16', '20:00', 'Asociación EG', 'Gratis', '500€ Primer equipo, 20€ Segundo equipo', 'fifa.jpg', 'https://discord.gg/ByDAHZ3');

/* Cargamos la tabla de eventos*/
INSERT INTO eventos (nombre, descripcion, fecha, hora, lugar, precio, foto, foto2, foto3, url, video) VALUES ('Titulo Evento',  'Descripción del evento, puede ser una cadena de caracteres de unas 50 o 60 palabras', '2020-01-01', '15:00', 'En la asociación', 'Gratis', '','','', 'www.marca.com', 'https://www.youtube.com/embed/tkQRkVyorVs');
INSERT INTO eventos (nombre, descripcion, fecha, hora, lugar, precio, foto, foto2, foto3, url, video) VALUES ('Titulo Evento2', 'Descripción del evento, puede ser una cadena de caracteres de unas 50 o 60 palabras', '2020-01-01', '15:00', 'Pabellón Alcarrachela', 'Gratis', 'juegoMesa1.jpg','juegoMesa2.jpg','juegoMesa3.jpg', 'www.marca.com.es', '');
INSERT INTO eventos (nombre, descripcion, fecha, hora, lugar, precio, foto, foto2, foto3, url, video) VALUES ('Titulo Evento3', 'Descripción del evento, puede ser una cadena de caracteres de unas 50 o 60 palabras', '2020-01-01', '15:00', 'Parque infantil', 'Gratis', '','','', 'www.marca.com', 'https://www.youtube.com/embed/0t5ZjeYdxxQ');
INSERT INTO eventos (nombre, descripcion, fecha, hora, lugar, precio, foto, foto2, foto3, url, video) VALUES ('Titulo Evento4', 'Descripción del evento, puede ser una cadena de caracteres de unas 50 o 60 palabras', '2020-01-01', '15:00', 'C/ José canalejas, 9', 'Gratis', 'juegoMesa2.jpg','juegoMesa3.jpg','juegoMesa1.jpg', 'www.marca.com', '');

/* Cargamos la tabla de configuracion*/
INSERT INTO configuracion (id, direccion, email, telefono, horario) values (1, 'Calle José Canalejas, 9, 41400 Écija, Sevilla', 'ecijagaming@gmail.com', '+34 666 666 666', 'Lunes-Domingo 9:30 - 22:00');



