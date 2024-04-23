DROP SCHEMA `ponchito`;

CREATE SCHEMA `ponchito`;

USE `ponchito`;

CREATE TABLE Ciudad (
    nombre CHAR(12),
    país CHAR(12),
    PRIMARY KEY (nombre, país)
);

CREATE TABLE LugarAvisitar (
    nombre CHAR(12),
    ciudad CHAR(12),
    país CHAR(12),
    dirección CHAR(20),
    descripción CHAR(40),
    precio INT,
    PRIMARY KEY (nombre, ciudad, país),
    FOREIGN KEY (ciudad, país) REFERENCES Ciudad(nombre, país)
);

CREATE TABLE Circuito (
    identificador CHAR(5),
    descripción CHAR(20),
    ciudadSalida CHAR(12),
    paísSalida CHAR(12),
    ciudadLlegada CHAR(12),
    paísLlegada CHAR(12),
    duración INT,
    precio INT,
    PRIMARY KEY (identificador),
    FOREIGN KEY (ciudadSalida, paísSalida) REFERENCES Ciudad(nombre, país),
    FOREIGN KEY (ciudadLlegada, paísLlegada) REFERENCES Ciudad(nombre, país)
);

CREATE TABLE FechaCircuito (
    identificador CHAR(5),
    fechaSalida DATE,
    nbPersonas INT,
    PRIMARY KEY (identificador, fechaSalida),
    FOREIGN KEY (identificador) REFERENCES Circuito(identificador)
);

CREATE TABLE Etapa (
    identificador CHAR(5),
    orden INT,
    nombreLugar CHAR(12),
    ciudad CHAR(12),
    país CHAR(12),
    duración INT,
    PRIMARY KEY (identificador, orden),
    FOREIGN KEY (nombreLugar, ciudad, país) REFERENCES LugarAvisitar(nombre, ciudad, país),
    FOREIGN KEY (identificador) REFERENCES Circuito(identificador)
);

CREATE TABLE Hotel (
    nombre CHAR(12),
    ciudad CHAR(12),
    país CHAR(12),
    dirección CHAR(20),
    numCuartos INT,
    precioCuarto INT,
    precioDesayuno INT,
    PRIMARY KEY (nombre, ciudad, país),
    FOREIGN KEY (ciudad, país) REFERENCES Ciudad(nombre, país)
);

INSERT INTO Ciudad (nombre, país) VALUES
('CDMX', 		'México'),
('Cancún', 		'México'),

('Madrid', 		'España'),
('Barcelona', 	'España'),

('Londres', 	'Inglaterra'),
('Edimburgo', 	'Escocia'),

('Nueva York', 	'EEUU'),
('Los Ángeles', 'EEUU'),

('París', 		'Francia'),

('Roma', 		'Italia'),
('Venecia', 	'Italia');

INSERT INTO LugarAvisitar (nombre, ciudad, país, dirección, descripción, precio) VALUES
('Zócalo', 		'CDMX', 		'México', 		'Plaza Constitución', 'Centro histórico CDMX', 0),
('Playa delfín','Cancún', 		'México', 		'Playa Carmen', 'Hermosa playa en Cancún', 0),

('Sinagoga', 	'Madrid', 		'España', 		'Calle Serrano', 'Importante lugar de culto', 10),
('Casa Lleó', 	'Barcelona',	'España', 		'Paseo Gracia', 'Edificio modernista', 10),

('Big Ben', 	'Londres', 		'Inglaterra',	'Bridge St', 'Icono de Londres', 20),
('Holyrood', 	'Edimburgo',	'Escocia', 		'Canongate', 'Residencia oficial', 20),

('La Libertad', 'Nueva York',	'EEUU',	 		'Liberty Island', 'Estatua de la Libertad', 25),
('Walk of Fame','Los Ángeles',	'EEUU', 		'Hollywood Blvd', 'Paseo de la fama', 25),

('Louvre', 		'París', 		'Francia', 		'Rue Rivoli', 'Museo más grande', 15),

('Coliseo', 	'Roma', 		'Italia', 		'Piazza Colosseo', 'Antiguo anfiteatro', 12),
('Palazzo Vecc','Roma', 		'Italia', 		'Piazza Signoria', 'Palacio renacentista', 12),
('San Marcos', 	'Venecia', 		'Italia', 		'Piazza San Marco', 'Iglesia bizantina', 12);

INSERT INTO Circuito (identificador, descripción, ciudadSalida, paísSalida, ciudadLlegada, paísLlegada, duración, precio) VALUES
('CIR00', 'Tour por México', 		'CDMX', 		'México', 		'Cancún', 		'México', 		7, 	800),
('CIR01', 'Tour por México',       	'Cancún',    	'México',       'CDMX',        	'México',       7,	800),

('CIR10', 'Tour por España', 		'Madrid', 		'España', 		'Barcelona', 	'España', 		8, 	1000),
('CIR11', 'Tour por España',       	'Barcelona',  	'España',       'Madrid',      	'España',       8,  1000),

('CIR20', 'Tour por Reino Unido',	'Londres', 		'Inglaterra', 	'Edimburgo', 	'Escocia', 	9, 	1200),
('CIR21', 'Tour por Reino Unido',	'Edimburgo',	'Escocia',  	'Londres',     	'Inglaterra',  9,  1200),

('CIR30', 'Tour por EEUU',			'Nueva York', 	'EEUU', 		'Los Ángeles', 	'EEUU', 		10, 1000),
('CIR31', 'Tour por EEUU',         	'Los Ángeles', 	'EEUU',         'Nueva York',  	'EEUU',        10,  1000),

('CIR40', 'Tour por Europa', 		'París', 		'Francia', 		'Roma', 		'Italia', 		11, 1400),
('CIR41', 'Tour por Europa',       	'Roma',        	'Italia',       'París',       	'Francia',     11,  1400),

('CIR50', 'Tour por Italia', 		'Roma', 		'Italia', 		'Venecia', 		'Italia', 		12, 1600),
('CIR51', 'Tour por Italia',       	'Venecia',     	'Italia',       'Roma',        	'Italia',      12,  1600);


INSERT INTO FechaCircuito (identificador, fechaSalida, nbPersonas) VALUES
('CIR01', '2024-05-15', 20),
('CIR01', '2024-06-20', 15),
('CIR01', '2024-09-10', 15),
('CIR01', '2024-10-05', 10),
('CIR01', '2024-11-20', 20),
('CIR20', '2024-07-10', 10),
('CIR20', '2024-08-05', 25);

INSERT INTO Etapa (identificador, orden, nombreLugar, ciudad, país, duración) VALUES
('CIR01', 1, 'Zócalo', 			'CDMX', 		'México', 		1),
('CIR01', 2, 'Playa delfín', 	'Cancún', 		'México', 		2),

('CIR01', 3, 'Sinagoga', 		'Madrid', 		'España', 		2),
('CIR01', 4, 'Casa Lleó', 		'Barcelona',	'España', 		3),

('CIR01', 5, 'Big Ben',			'Londres', 		'Inglaterra', 	2),
('CIR01', 6, 'Holyrood', 		'Edimburgo',	'Escocia', 		2),

('CIR01', 7, 'La Libertad', 	'Nueva York',	'EEUU', 		2),
('CIR01', 8, 'Walk of Fame', 	'Los Ángeles',	'EEUU', 		3),

('CIR01', 9, 'Louvre', 			'París', 		'Francia', 		3),

('CIR01', 10, 'Coliseo', 		'Roma', 		'Italia', 		2),
('CIR01', 11, 'Palazzo Vecc', 	'Roma', 		'Italia', 		2),
('CIR01', 12, 'San Marcos', 	'Venecia', 		'Italia', 		1);

INSERT INTO Hotel (nombre, ciudad, país, dirección, numCuartos, precioCuarto, precioDesayuno) VALUES
('Hotel CDMX', 		'CDMX', 		'México', 		'Av. Insurgentes', 120, 90, 20),
('Hotel Cancún', 	'Cancún', 		'México', 		'Blvd. Kukulcán', 120, 90, 20),

('Hotel Madrid', 	'Madrid', 		'España', 		'Gran Vía, 29', 80, 70, 15),
('Hotel Barc', 		'Barcelona',	'España', 		'Psg. de Gràcia, 35', 80, 70, 15),

('Hotel Londr', 	'Londres', 		'Inglaterra', 	'Royal Scot, Euston', 100, 90, 18),
('Hotel Edimb', 	'Edimburgo',	'Escocia', 		'Royal Mile', 100, 90, 18),

('Hotel NY', 		'Nueva York',	'EEUU', 		'7th Ave, New York', 120, 100, 20),
('Hotel LA', 		'Los Ángeles', 	'EEUU', 		'Hollywood Blvd', 120, 100, 20),

('Hotel Paris', 	'París', 		'Francia', 		'Champs-Élysées', 100, 80, 15),

('Hotel Roma', 		'Roma', 		'Italia', 		'Via Cavour, 15', 80, 70, 12),
('Hotel Ven', 		'Venecia', 		'Italia', 		'Piazza San Marco', 80, 70, 12);