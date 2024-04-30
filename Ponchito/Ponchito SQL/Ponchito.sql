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

CREATE TABLE Hotel ( -- Se hospedan en cuartos individuales
    nombre CHAR(12),
    ciudad CHAR(12),
    país CHAR(12),
    dirección CHAR(20),
    numCuartos INT,
    precioCuarto INT,
    precioDesayuno INT,
    PRIMARY KEY (ciudad, país),  -- Solo tenemos convenio con un hotel en cada ciudad, se incluyen actividades (en hoteles) con viajes ponchito y descuentos en los mismos (como les llevamoms clientes seguido, nos hacen descuentoe) 
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

CREATE TABLE Etapa (
    identificador CHAR(5),
    orden INT,
    nombreLugar CHAR(12),
    ciudad CHAR(12),
    país CHAR(12),
    duración INT,
    precio INT,
    PRIMARY KEY (identificador, orden),
    FOREIGN KEY (nombreLugar, ciudad, país) REFERENCES LugarAvisitar(nombre, ciudad, país),
    FOREIGN KEY (identificador) REFERENCES Circuito(identificador)
);

CREATE TABLE FechaCircuito (
    identificador CHAR(5),
    fechaSalida DATE,
    nbPersonas INT,
    PRIMARY KEY (identificador, fechaSalida, nbpersonas),
    FOREIGN KEY (identificador) REFERENCES Circuito(identificador)
);



CREATE TABLE Cliente (
    idCliente INT AUTO_INCREMENT PRIMARY KEY, 
    nombre CHAR(50),
    apellidoPaterno CHAR(50),
    apellidoMaterno CHAR(50),
    fechaNacimiento DATE,
    tipo CHAR(10) CHECK (tipo IN ('compañía', 'grupo', 'individual')),
    agenciaEmpleado BOOLEAN,
    añoRegistro INT
);

CREATE TABLE Simulacion (
    numeroSimulacion INT AUTO_INCREMENT PRIMARY KEY,
    idCliente INT, 
    circuito CHAR(5),
    FOREIGN KEY (idCliente) REFERENCES Cliente(idCliente),
    FOREIGN KEY (circuito) REFERENCES Circuito(identificador)
);

CREATE TABLE Reservacion (
    numeroReservacion INT AUTO_INCREMENT PRIMARY KEY,
    idcliente INT,
    circuito CHAR(5),
    FOREIGN KEY (idcliente) REFERENCES Cliente(idCliente),
    FOREIGN KEY (circuito) REFERENCES Circuito(identificador)
);


-- Delete simulations after two days
CREATE EVENT delete_old_simulations
ON SCHEDULE EVERY 1 DAY
STARTS NOW()
DO
  DELETE FROM Simulacion
  WHERE created_at < NOW() - INTERVAL 2 DAY;




INSERT INTO Ciudad (nombre, país) VALUES
('CDMX', 'México'),
('Cancún', 'México'),
('Guadalajara', 'México'),
('Monterrey', 'México'),

('Nueva York', 'EEUU'),
('Los Ángeles', 'EEUU'),
('Chicago', 'EEUU'),
('Houston', 'EEUU'),

('Toronto', 'Canadá'),
('Montreal', 'Canadá'),
('Vancouver', 'Canadá'),
('Quebec', 'Canadá'),

('Madrid', 'España'),
('Barcelona', 'España'),
('Valencia', 'España'),
('Sevilla', 'España'),

('París', 'Francia'),
('Marsella', 'Francia'),
('Lyon', 'Francia'),
('Niza', 'Francia'),

('Roma', 'Italia'),
('Milán', 'Italia'),
('Nápoles', 'Italia'),
('Florencia', 'Italia'),

('Berlín', 'Alemania'),
('Múnich', 'Alemania'),
('Hamburgo', 'Alemania'),
('Fráncfort', 'Alemania'),

('Tokio', 'Japón'),
('Kioto', 'Japón'),
('Osaka', 'Japón'),
('Hiroshima', 'Japón'),

('Pekín', 'China'),
('Shanghái', 'China'),
('Hong Kong', 'China'),
('Cantón', 'China'),

('Bangkok', 'Tailandia'),
('Ratchaburi', 'Tailandia'),
('Ratchasima', 'Tailandia'),

('Buenos Aires', 'Argentina'),
('Córdoba', 'Argentina'),
('Rosario', 'Argentina'),
('Mendoza', 'Argentina'),

('Río', 'Brasil'),
('São Paulo', 'Brasil'),
('Brasilia', 'Brasil'),
('Salvador', 'Brasil'),

('Lima', 'Perú'),
('Cusco', 'Perú'),
('Arequipa', 'Perú'),
('Trujillo', 'Perú');

INSERT INTO LugarAvisitar (nombre, ciudad, país, dirección, descripción, precio) VALUES
('Bellas Artes', 'CDMX', 'México', 'Av. Juárez', 'Arte y cultura', 25),
('Playa Delfin', 'Cancún', 'México', 'Zona Hotelera', 'Hermosa playa', 0),
('Cabañas', 'Guadalajara', 'México', 'Calle Cabañas', 'Arquitectura histórica', 30),
('Fundidora', 'Monterrey', 'México', 'Av. Fundidora', 'Parque urbano', 15),

('Central Park', 'Nueva York', 'EEUU', 'Manhattan, NY', 'Parque urbano icónico', 0),
('Holly Blvd', 'Los Ángeles', 'EEUU', 'Hollywood Blvd', 'Paseo de la fama', 10),
('Millennium', 'Chicago', 'EEUU', '201 E Randolph St', 'Parque urbano con esculturas', 5),
('Space Center', 'Houston', 'EEUU', '1601 NASA Pkwy', 'Centro espacial', 20),

('CN Tower', 'Toronto', 'Canadá', '301 Front St W', 'Torre de observación', 15),
('Montreal', 'Montreal', 'Canadá', '1380 Rue Sherbrooke', 'Museo de bellas artes', 25),
('Stanley Park', 'Vancouver', 'Canadá', 'Stanley Park Dr', 'Parque urbano con vistas', 0),
('Old Quebec', 'Quebec', 'Canadá', 'Québec City', 'Ciudad vieja histórica', 10),

('Museo Prado', 'Madrid', 'España', 'Paseo del Prado', 'Museo de arte', 20),
('Sagrada', 'Barcelona', 'España', 'Carrer de Mallorca', 'Basilica famosa', 35),
('Ciudad Artes', 'Valencia', 'España', 'Av. del Professor', 'Complejo arquitectónico', 30),
('Alcázar', 'Sevilla', 'España', 'Patio de Banderas', 'Palacio fortificado', 15),

('Torre Eiffel', 'París', 'Francia', 'Champ de Mars', 'Icono de la ciudad', 20),
('Vieux Port', 'Marsella', 'Francia', 'Porte d''Aix', 'Puerto histórico', 10),
('Parque Tête', 'Lyon', 'Francia', 'Boulevard du 11', 'Parque urbano con zoológico', 5),
('Promenade', 'Niza', 'Francia', 'Nice', 'Paseo marítimo', 0),

('Coliseo', 'Roma', 'Italia', 'Piazza del Colosseo', 'Antiguo anfiteatro romano', 30),
('Última Cena', 'Milán', 'Italia', 'Piazza di Santa', 'Famoso fresco de Leonardo da Vinci', 25),
('Nápoles Subt', 'Nápoles', 'Italia', 'Piazza San Gaetano', 'Red de túneles históricos', 10),
('Uffizi', 'Florencia', 'Italia', 'Piazzale degli', 'Museo de arte renacentista', 20),

('Pérgamo', 'Berlín', 'Alemania', 'Bodestraße 1-3', 'Museo de antigüedades', 15),
('Hofbräuhaus', 'Múnich', 'Alemania', 'Platzl 9', 'Famoso beer hall', 10),
('Puerto', 'Hamburgo', 'Alemania', 'Bei den St. Pauli', 'Puerto marítimo', 5),
('Römerberg', 'Fráncfort', 'Alemania', '60311 Frankfurt', 'Plaza histórica', 0),

('Sensoji', 'Tokio', 'Japón', '2 Chome-3-1 Asakusa', 'Templo budista', 15),
('Kinkaku-ji', 'Kioto', 'Japón', '1 Kinkakujicho', 'Templo dorado', 20),
('Castillo', 'Osaka', 'Japón', '1-1 Osakajo', 'Castillo histórico', 10),
('Memorial', 'Hiroshima', 'Japón', '1-2 Nakajimacho', 'Monumento conmemorativo', 5),

('C, Prohibida', 'Pekín', 'China', '4 Jingshan Front St', 'Antigua residencia imperial', 25),
('Bund', 'Shanghái', 'China', 'Zhongshan East 1st', 'Paseo marítimo histórico', 20),
('Tan Buddha', 'Hong Kong', 'China', 'Ngong Ping Rd', 'Estatua de Buda gigante', 30),
('Banyan Trees', 'Cantón', 'China', '87 Liurong Rd', 'Antiguo templo budista', 15),

('Gran Palacio', 'Bangkok', 'Tailandia', 'Na Phra Lan Rd', 'Residencia real histórica', 30),
('Buda', 'Bangkok', 'Tailandia', 'Na Phra Lan Rd', 'Templo budista importante', 20),
('Damnoen', 'Ratchaburi', 'Tailandia', 'Saduak District', 'Mercado flotante tradicional', 25),
('Khao Yai', 'Ratchasima', 'Tailandia', 'Chong District', 'Reserva natural con vida silvestre', 10),

('Teatro Colón', 'Buenos Aires', 'Argentina', 'Cerrito 628', 'Importante teatro de ópera', 20),
('Manzana J', 'Córdoba', 'Argentina', 'Manzana Jesuítica', 'Conjunto arquitectónico histórico', 15),
('M Bandera', 'Rosario', 'Argentina', 'Sta Fe 580', 'Monumento nacional', 10),
('San Martín', 'Mendoza', 'Argentina', 'Parque San Martín', 'Parque urbano con vistas a la montaña', 5),

('Cristo', 'Río', 'Brasil', 'Parque da Tijuca', 'Estatua icónica en la cima de la montaña', 25),
('Museo São P', 'São Paulo', 'Brasil', 'Av. Paulista, 1578', 'Museo de arte contemporáneo', 20),
('Catedral', 'Brasilia', 'Brasil', 'Esplanada Ministério', 'Arquitectura moderna', 15),
('Pelourinho', 'Salvador', 'Brasil', 'Pelourinho, Salvador', 'Centro histórico', 10),

('Plaza Mayor', 'Lima', 'Perú', 'Cercado de Lima', 'Plaza principal', 20),
('Machu Picchu', 'Cusco', 'Perú', 'Cusco 08002', 'Sitio arqueológico inca', 50),
('Sta Catalina', 'Arequipa', 'Perú', 'Santa Catalina 301', 'Convento histórico', 15),
('Sol Luna', 'Trujillo', 'Perú', 'Huacas del Sol', 'Sitios arqueológicos mochica', 10);

INSERT INTO Hotel (nombre, ciudad, país, dirección, numCuartos, precioCuarto, precioDesayuno) VALUES
('H Quesadilla', 'CDMX', 'México', 'Av. Insurgentes', 120, 90, 20),
('H Pez', 'Cancún', 'México', 'Blvd. Kukulcán', 120, 90, 20),
('H Tequila', 'Guadalajara', 'México', 'Calle Principal', 100, 80, 15),
('H Silla', 'Monterrey', 'México', 'Av. Revolución', 100, 85, 18),

('H Broadway', 'Nueva York', 'EEUU', 'Broadway Ave', 200, 150, 25),
('H Hollywood', 'Los Ángeles', 'EEUU', 'Sunset Blvd', 180, 130, 20),
('H Windy', 'Chicago', 'EEUU', 'Michigan Ave', 150, 120, 22),
('H Space', 'Houston', 'EEUU', 'Main St', 130, 100, 18),

('H Maple', 'Toronto', 'Canadá', 'King St', 110, 90, 15),
('H Mont', 'Montreal', 'Canadá', 'Saint Catherine St', 100, 85, 20),
('H Lions', 'Vancouver', 'Canadá', 'Granville St', 120, 95, 18),
('H Vieux', 'Quebec', 'Canadá', 'Saint-Jean St', 90, 80, 15),

('H Gran Vía', 'Madrid', 'España', 'Gran Vía', 150, 110, 20),
('H Ramblas', 'Barcelona', 'España', 'Las Ramblas', 140, 100, 18),
('H Paella', 'Valencia', 'España', 'Plaza Mayor', 120, 90, 16),
('H Flamenco', 'Sevilla', 'España', 'Calle Sierpes', 110, 85, 18),

('H Eiffel', 'París', 'Francia', 'Champs-Élysées', 180, 140, 25),
('H Vieux Port', 'Marsella', 'Francia', 'Rue Paradis', 100, 80, 15),
('H Bellecour', 'Lyon', 'Francia', 'Place Bellecour', 120, 95, 18),
('H Promen', 'Niza', 'Francia', 'Promenade ', 110, 85, 16),

('H Coliseo', 'Roma', 'Italia', 'Via Veneto', 160, 120, 22),
('H Duomo', 'Milán', 'Italia', 'Corso Aires', 140, 100, 18),
('H Vesuvio', 'Nápoles', 'Italia', 'Via Toledo', 100, 80, 15),
('H Vecchio', 'Florencia', 'Italia', 'Piazza Repubblica', 120, 95, 18),

('H Brandeb', 'Berlín', 'Alemania', 'Unter den Linden', 150, 110, 20),
('H Marienpl', 'Múnich', 'Alemania', 'Marienplatz', 130, 95, 18),
('H Hamburgo', 'Hamburgo', 'Alemania', 'Reeperbahn', 120, 90, 16),
('H Römerberg', 'Fráncfort', 'Alemania', 'Zeil', 110, 85, 18),

('H Shibuya', 'Tokio', 'Japón', 'Ginza', 200, 150, 25),
('H Kinkakuji', 'Kioto', 'Japón', 'Gion District', 150, 120, 22),
('H Dotonbori', 'Osaka', 'Japón', 'Dotonbori', 130, 100, 18),
('HH', 'Hiroshima', 'Japón', 'Hondori', 110, 85, 18),

('H Pekín', 'Pekín', 'China', 'Wangfujing', 180, 140, 25),
('H El Bund', 'Shanghái', 'China', 'The Bund', 160, 120, 22),
('H Harbour', 'Hong Kong', 'China', 'Nathan Rd', 150, 110, 20),
('H Guangzhou', 'Cantón', 'China', 'Shangxiajiu', 130, 95, 18),

('H Wat Phra', 'Bangkok', 'Tailandia', 'Sukhumvit Rd', 140, 100, 18),
('H Saduak', 'Ratchaburi', 'Tailandia', 'Photharam', 80, 60, 12),
('H Phimai', 'Ratchasima', 'Tailandia', 'Ratchasima', 90, 70, 14),

('H Tango', 'Buenos Aires', 'Argentina', 'Corrientes', 120, 90, 16),
('H Chateau', 'Córdoba', 'Argentina', 'San Juan', 100, 80, 15),
('H Bandera', 'Rosario', 'Argentina', 'Oroño', 90, 70, 14),
('H Indep', 'Mendoza', 'Argentina', 'San Martín', 80, 60, 12),

('H Ipanema', 'Río', 'Brasil', 'Copacabana', 180, 140, 25),
('H Paulista', 'São Paulo', 'Brasil', 'Paulista Ave', 160, 120, 22),
('H Praça', 'Brasilia', 'Brasil', 'Eixo Monumental', 140, 100, 18),
('H Pelourinho', 'Salvador', 'Brasil', 'Pelourinho', 120, 90, 16),

('H Miraflores', 'Lima', 'Perú', 'Miraflores', 140, 100, 18),
('H Plaza', 'Cusco', 'Perú', 'Plaza de Armas', 120, 90, 16),
('H Armas', 'Arequipa', 'Perú', 'Plaza de Armas', 100, 80, 15),
('H Arepa', 'Trujillo', 'Perú', 'Plaza de Armas', 120, 70, 23);

INSERT INTO Circuito (identificador, descripción, ciudadSalida, paísSalida, ciudadLlegada, paísLlegada, duración, precio) VALUES
('CIR00', 'Tour por México', 'CDMX', 'México', 'Cancún', 'México', 7, 800),
('CIR01', 'Tour por México', 'Cancún', 'México', 'Guadalajara', 'México', 7, 800),
('CIR02', 'Tour por México', 'Guadalajara', 'México', 'Monterrey', 'México', 7, 800),
('CIR03', 'Tour por México', 'Monterrey', 'México', 'CDMX', 'México', 7, 800),

('CIR04', 'Tour por EEUU', 'Nueva York', 'EEUU', 'Los Ángeles', 'EEUU', 10, 1500),
('CIR05', 'Tour por EEUU', 'Los Ángeles', 'EEUU', 'Chicago', 'EEUU', 10, 1500),
('CIR06', 'Tour por EEUU', 'Chicago', 'EEUU', 'Houston', 'EEUU', 10, 1500),
('CIR07', 'Tour por EEUU', 'Houston', 'EEUU', 'Nueva York', 'EEUU', 10, 1500),

('CIR08', 'Tour por Canadá', 'Toronto', 'Canadá', 'Montreal', 'Canadá', 7, 1000),
('CIR09', 'Tour por Canadá', 'Montreal', 'Canadá', 'Vancouver', 'Canadá', 7, 1000),
('CIR10', 'Tour por Canadá', 'Vancouver', 'Canadá', 'Quebec', 'Canadá', 7, 1000),
('CIR11', 'Tour por Canadá', 'Quebec', 'Canadá', 'Toronto', 'Canadá', 7, 1000),

('CIR12', 'Tour por España', 'Madrid', 'España', 'Barcelona', 'España', 7, 1200),
('CIR13', 'Tour por España', 'Barcelona', 'España', 'Valencia', 'España', 7, 1200),
('CIR14', 'Tour por España', 'Valencia', 'España', 'Sevilla', 'España', 7, 1200),
('CIR15', 'Tour por España', 'Sevilla', 'España', 'Madrid', 'España', 7, 1200),

('CIR16', 'Tour por Francia', 'París', 'Francia', 'Marsella', 'Francia', 7, 1400),
('CIR17', 'Tour por Francia', 'Marsella', 'Francia', 'Lyon', 'Francia', 7, 1400),
('CIR18', 'Tour por Francia', 'Lyon', 'Francia', 'Niza', 'Francia', 7, 1400),
('CIR19', 'Tour por Francia', 'Niza', 'Francia', 'París', 'Francia', 7, 1400),

('CIR20', 'Tour por Italia', 'Roma', 'Italia', 'Milán', 'Italia', 7, 1300),
('CIR21', 'Tour por Italia', 'Milán', 'Italia', 'Nápoles', 'Italia', 7, 1300),
('CIR22', 'Tour por Italia', 'Nápoles', 'Italia', 'Florencia', 'Italia', 7, 1300),
('CIR23', 'Tour por Italia', 'Florencia', 'Italia', 'Roma', 'Italia', 7, 1300),

('CIR24', 'Tour por Alemania', 'Berlín', 'Alemania', 'Múnich', 'Alemania', 7, 1400),
('CIR25', 'Tour por Alemania', 'Múnich', 'Alemania', 'Hamburgo', 'Alemania', 7, 1400),
('CIR26', 'Tour por Alemania', 'Hamburgo', 'Alemania', 'Fráncfort', 'Alemania', 7, 1400),
('CIR27', 'Tour por Alemania', 'Fráncfort', 'Alemania', 'Berlín', 'Alemania', 7, 1400),

('CIR28', 'Tour por Japón', 'Tokio', 'Japón', 'Kioto', 'Japón', 7, 1500),
('CIR29', 'Tour por Japón', 'Kioto', 'Japón', 'Osaka', 'Japón', 7, 1500),
('CIR30', 'Tour por Japón', 'Osaka', 'Japón', 'Hiroshima', 'Japón', 7, 1500),
('CIR31', 'Tour por Japón', 'Hiroshima', 'Japón', 'Tokio', 'Japón', 7, 1500),

('CIR32', 'Tour por China', 'Pekín', 'China', 'Shanghái', 'China', 7, 1600),
('CIR33', 'Tour por China', 'Shanghái', 'China', 'Hong Kong', 'China', 7, 1600),
('CIR34', 'Tour por China', 'Hong Kong', 'China', 'Cantón', 'China', 7, 1600),
('CIR35', 'Tour por China', 'Cantón', 'China', 'Pekín', 'China', 7, 1600),

('CIR36', 'Tour por Tailandia', 'Bangkok', 'Tailandia', 'Ratchaburi', 'Tailandia', 7, 1300),
('CIR37', 'Tour por Tailandia', 'Ratchaburi', 'Tailandia', 'Ratchasima', 'Tailandia', 7, 1300),
('CIR38', 'Tour por Tailandia', 'Ratchasima', 'Tailandia', 'Bangkok', 'Tailandia', 7, 1300),

('CIR39', 'Tour por Argentina', 'Buenos Aires', 'Argentina', 'Córdoba', 'Argentina', 7, 1100),
('CIR40', 'Tour por Argentina', 'Córdoba', 'Argentina', 'Rosario', 'Argentina', 7, 1100),
('CIR41', 'Tour por Argentina', 'Rosario', 'Argentina', 'Mendoza', 'Argentina', 7, 1100),
('CIR42', 'Tour por Argentina', 'Mendoza', 'Argentina', 'Buenos Aires', 'Argentina', 7, 1100),

('CIR43', 'Tour por Brasil', 'Río', 'Brasil', 'São Paulo', 'Brasil', 7, 1200),
('CIR44', 'Tour por Brasil', 'São Paulo', 'Brasil', 'Brasilia', 'Brasil', 7, 1200),
('CIR45', 'Tour por Brasil', 'Brasilia', 'Brasil', 'Salvador', 'Brasil', 7, 1200),
('CIR46', 'Tour por Brasil', 'Salvador', 'Brasil', 'Río', 'Brasil', 7, 1200),

('CIR47', 'Tour por Perú', 'Lima', 'Perú', 'Cusco', 'Perú', 7, 1000),
('CIR48', 'Tour por Perú', 'Cusco', 'Perú', 'Arequipa', 'Perú', 7, 1000),
('CIR49', 'Tour por Perú', 'Arequipa', 'Perú', 'Trujillo', 'Perú', 7, 1000),
('CIR50', 'Tour por Perú', 'Trujillo', 'Perú', 'Lima', 'Perú', 7, 1000);


INSERT INTO FechaCircuito (identificador, fechaSalida, nbPersonas) VALUES
('CIR00', '2024-05-15', 20),
('CIR00', '2024-06-20', 15),
('CIR00', '2024-09-10', 15),
('CIR00', '2024-10-05', 10),
('CIR00', '2024-11-20', 20),
('CIR00', '2024-07-10', 10),
('CIR00', '2024-08-05', 25),

('CIR01', '2024-05-18', 18),
('CIR01', '2024-06-25', 22),
('CIR01', '2024-09-15', 16),
('CIR01', '2024-10-10', 20),
('CIR01', '2024-11-22', 23),
('CIR01', '2024-07-12', 19),
('CIR01', '2024-08-07', 21),

('CIR02', '2024-05-20', 22),
('CIR02', '2024-06-28', 20),
('CIR02', '2024-09-18', 19),
('CIR02', '2024-10-15', 17),
('CIR02', '2024-11-25', 24),
('CIR02', '2024-07-15', 18),
('CIR02', '2024-08-10', 23),

('CIR03', '2024-05-22', 20),
('CIR03', '2024-06-30', 18),
('CIR03', '2024-09-20', 22),
('CIR03', '2024-10-18', 16),
('CIR03', '2024-11-28', 21),
('CIR03', '2024-07-20', 19),
('CIR03', '2024-08-15', 23),

('CIR04', '2024-05-25', 18),
('CIR04', '2024-07-02', 20),
('CIR04', '2024-09-22', 23),
('CIR04', '2024-10-20', 19),
('CIR04', '2024-11-30', 22),
('CIR04', '2024-07-22', 21),
('CIR04', '2024-08-18', 17),

('CIR05', '2024-05-28', 21),
('CIR05', '2024-07-05', 19),
('CIR05', '2024-09-25', 22),
('CIR05', '2024-10-22', 20),
('CIR05', '2024-12-02', 23),
('CIR05', '2024-07-25', 18),
('CIR05', '2024-08-20', 24),

('CIR06', '2024-05-15', 20),
('CIR06', '2024-06-20', 15),
('CIR06', '2024-09-10', 15),
('CIR06', '2024-10-05', 10),
('CIR06', '2024-11-20', 20),
('CIR06', '2024-07-10', 10),
('CIR06', '2024-08-05', 25),

('CIR07', '2024-05-18', 18),
('CIR07', '2024-06-25', 22),
('CIR07', '2024-09-15', 16),
('CIR07', '2024-10-10', 20),
('CIR07', '2024-11-22', 23),
('CIR07', '2024-07-12', 19),
('CIR07', '2024-08-07', 21),

('CIR08', '2024-05-20', 22),
('CIR08', '2024-06-28', 20),
('CIR08', '2024-09-18', 19),
('CIR08', '2024-10-15', 17),
('CIR08', '2024-11-25', 24),
('CIR08', '2024-07-15', 18),
('CIR08', '2024-08-10', 23),

('CIR09', '2024-05-22', 20),
('CIR09', '2024-06-30', 18),
('CIR09', '2024-09-20', 22),
('CIR09', '2024-10-18', 16),
('CIR09', '2024-11-28', 21),
('CIR09', '2024-07-20', 19),
('CIR09', '2024-08-15', 23),

('CIR10', '2024-05-15', 20),
('CIR10', '2024-06-20', 15),
('CIR10', '2024-09-10', 15),
('CIR10', '2024-10-05', 10),
('CIR10', '2024-11-20', 20),
('CIR10', '2024-07-10', 10),
('CIR10', '2024-08-05', 25),

('CIR11', '2024-05-18', 18),
('CIR11', '2024-06-25', 22),
('CIR11', '2024-09-15', 16),
('CIR11', '2024-10-10', 20),
('CIR11', '2024-11-22', 23),
('CIR11', '2024-07-12', 19),
('CIR11', '2024-08-07', 21),

('CIR12', '2024-05-20', 22),
('CIR12', '2024-06-28', 20),
('CIR12', '2024-09-18', 19),
('CIR12', '2024-10-15', 17),
('CIR12', '2024-11-25', 24),
('CIR12', '2024-07-15', 18),
('CIR12', '2024-08-10', 23),

('CIR13', '2024-05-22', 20),
('CIR13', '2024-06-30', 18),
('CIR13', '2024-09-20', 22),
('CIR13', '2024-10-18', 16),
('CIR13', '2024-11-28', 21),
('CIR13', '2024-07-20', 19),
('CIR13', '2024-08-15', 23),

('CIR14', '2024-05-25', 18),
('CIR14', '2024-07-02', 20),
('CIR14', '2024-09-22', 23),
('CIR14', '2024-10-20', 19),
('CIR14', '2024-11-30', 22),
('CIR14', '2024-07-22', 21),
('CIR14', '2024-08-18', 17),

('CIR15', '2024-05-28', 21),
('CIR15', '2024-07-05', 19),
('CIR15', '2024-09-25', 22),
('CIR15', '2024-10-22', 20),
('CIR15', '2024-12-02', 23),
('CIR15', '2024-07-25', 18),
('CIR15', '2024-08-20', 24),

('CIR16', '2024-05-15', 20),
('CIR16', '2024-06-20', 15),
('CIR16', '2024-09-10', 15),
('CIR16', '2024-10-05', 10),
('CIR16', '2024-11-20', 20),
('CIR16', '2024-07-10', 10),
('CIR16', '2024-08-05', 25),

('CIR17', '2024-05-18', 18),
('CIR17', '2024-06-25', 22),
('CIR17', '2024-09-15', 16),
('CIR17', '2024-10-10', 20),
('CIR17', '2024-11-22', 23),
('CIR17', '2024-07-12', 19),
('CIR17', '2024-08-07', 21),

('CIR18', '2024-05-20', 22),
('CIR18', '2024-06-28', 20),
('CIR18', '2024-09-18', 19),
('CIR18', '2024-10-15', 17),
('CIR18', '2024-11-25', 24),
('CIR18', '2024-07-15', 18),
('CIR18', '2024-08-10', 23),

('CIR19', '2024-05-22', 20),
('CIR19', '2024-06-30', 18),
('CIR19', '2024-09-20', 22),
('CIR19', '2024-10-18', 16),
('CIR19', '2024-11-28', 21),
('CIR19', '2024-07-20', 19),
('CIR19', '2024-08-15', 23),

('CIR20', '2024-05-15', 20),
('CIR20', '2024-06-20', 15),
('CIR20', '2024-09-10', 15),
('CIR20', '2024-10-05', 10),
('CIR20', '2024-11-20', 20),
('CIR20', '2024-07-10', 10),
('CIR20', '2024-08-05', 25),

('CIR21', '2024-05-18', 18),
('CIR21', '2024-06-25', 22),
('CIR21', '2024-09-15', 16),
('CIR21', '2024-10-10', 20),
('CIR21', '2024-11-22', 23),
('CIR21', '2024-07-12', 19),
('CIR21', '2024-08-07', 21),

('CIR22', '2024-05-20', 22),
('CIR22', '2024-06-28', 20),
('CIR22', '2024-09-18', 19),
('CIR22', '2024-10-15', 17),
('CIR22', '2024-11-25', 24),
('CIR22', '2024-07-15', 18),
('CIR22', '2024-08-10', 23),

('CIR23', '2024-05-22', 20),
('CIR23', '2024-06-30', 18),
('CIR23', '2024-09-20', 22),
('CIR23', '2024-10-18', 16),
('CIR23', '2024-11-28', 21),
('CIR23', '2024-07-20', 19),
('CIR23', '2024-08-15', 23),

('CIR24', '2024-05-25', 18),
('CIR24', '2024-07-02', 20),
('CIR24', '2024-09-22', 23),
('CIR24', '2024-10-20', 19),
('CIR24', '2024-11-30', 22),
('CIR24', '2024-07-22', 21),
('CIR24', '2024-08-18', 17),

('CIR25', '2024-05-28', 21),
('CIR25', '2024-07-05', 19),
('CIR25', '2024-09-25', 22),
('CIR25', '2024-10-22', 20),
('CIR25', '2024-12-02', 23),
('CIR25', '2024-07-25', 18),
('CIR25', '2024-08-20', 24),

('CIR26', '2024-05-15', 20),
('CIR26', '2024-06-20', 15),
('CIR26', '2024-09-10', 15),
('CIR26', '2024-10-05', 10),
('CIR26', '2024-11-20', 20),
('CIR26', '2024-07-10', 10),
('CIR26', '2024-08-05', 25),

('CIR27', '2024-05-18', 18),
('CIR27', '2024-06-25', 22),
('CIR27', '2024-09-15', 16),
('CIR27', '2024-10-10', 20),
('CIR27', '2024-11-22', 23),
('CIR27', '2024-07-12', 19),
('CIR27', '2024-08-07', 21),

('CIR28', '2024-05-20', 22),
('CIR28', '2024-06-28', 20),
('CIR28', '2024-09-18', 19),
('CIR28', '2024-10-15', 17),
('CIR28', '2024-11-25', 24),
('CIR28', '2024-07-15', 18),
('CIR28', '2024-08-10', 23),

('CIR29', '2024-05-22', 20),
('CIR29', '2024-06-30', 18),
('CIR29', '2024-09-20', 22),
('CIR29', '2024-10-18', 16),
('CIR29', '2024-11-28', 21),
('CIR29', '2024-07-20', 19),
('CIR29', '2024-08-15', 23),

('CIR30', '2024-05-15', 20),
('CIR30', '2024-06-20', 15),
('CIR30', '2024-09-10', 15),
('CIR30', '2024-10-05', 10),
('CIR30', '2024-11-20', 20),
('CIR30', '2024-07-10', 10),
('CIR30', '2024-08-05', 25),

('CIR31', '2024-05-18', 18),
('CIR31', '2024-06-25', 22),
('CIR31', '2024-09-15', 16),
('CIR31', '2024-10-10', 20),
('CIR31', '2024-11-22', 23),
('CIR31', '2024-07-12', 19),
('CIR31', '2024-08-07', 21),

('CIR32', '2024-05-20', 22),
('CIR32', '2024-06-28', 20),
('CIR32', '2024-09-18', 19),
('CIR32', '2024-10-15', 17),
('CIR32', '2024-11-25', 24),
('CIR32', '2024-07-15', 18),
('CIR32', '2024-08-10', 23),

('CIR33', '2024-05-22', 20),
('CIR33', '2024-06-30', 18),
('CIR33', '2024-09-20', 22),
('CIR33', '2024-10-18', 16),
('CIR33', '2024-11-28', 21),
('CIR33', '2024-07-20', 19),
('CIR33', '2024-08-15', 23),

('CIR34', '2024-05-25', 18),
('CIR34', '2024-07-02', 20),
('CIR34', '2024-09-22', 23),
('CIR34', '2024-10-20', 19),
('CIR34', '2024-11-30', 22),
('CIR34', '2024-07-22', 21),
('CIR34', '2024-08-18', 17),

('CIR35', '2024-05-28', 21),
('CIR35', '2024-07-05', 19),
('CIR35', '2024-09-25', 22),
('CIR35', '2024-10-22', 20),
('CIR35', '2024-12-02', 23),
('CIR35', '2024-07-25', 18),
('CIR35', '2024-08-20', 24),

('CIR36', '2024-05-15', 20),
('CIR36', '2024-06-20', 15),
('CIR36', '2024-09-10', 15),
('CIR36', '2024-10-05', 10),
('CIR36', '2024-11-20', 20),
('CIR36', '2024-07-10', 10),
('CIR36', '2024-08-05', 25),

('CIR37', '2024-05-18', 18),
('CIR37', '2024-06-25', 22),
('CIR37', '2024-09-15', 16),
('CIR37', '2024-10-10', 20),
('CIR37', '2024-11-22', 23),
('CIR37', '2024-07-12', 19),
('CIR37', '2024-08-07', 21),

('CIR38', '2024-05-20', 22),
('CIR38', '2024-06-28', 20),
('CIR38', '2024-09-18', 19),
('CIR38', '2024-10-15', 17),
('CIR38', '2024-11-25', 24),
('CIR38', '2024-07-15', 18),
('CIR38', '2024-08-10', 23),

('CIR39', '2024-05-22', 20),
('CIR39', '2024-06-30', 18),
('CIR39', '2024-09-20', 22),
('CIR39', '2024-10-18', 16),
('CIR39', '2024-11-28', 21),
('CIR39', '2024-07-20', 19),
('CIR39', '2024-08-15', 23),

('CIR40', '2024-05-15', 20),
('CIR40', '2024-06-20', 15),
('CIR40', '2024-09-10', 15),
('CIR40', '2024-10-05', 10),
('CIR40', '2024-11-20', 20),
('CIR40', '2024-07-10', 10),
('CIR40', '2024-08-05', 25),

('CIR41', '2024-05-18', 18),
('CIR41', '2024-06-25', 22),
('CIR41', '2024-09-15', 16),
('CIR41', '2024-10-10', 20),
('CIR41', '2024-11-22', 23),
('CIR41', '2024-07-12', 19),
('CIR41', '2024-08-07', 21),

('CIR42', '2024-05-20', 22),
('CIR42', '2024-06-28', 20),
('CIR42', '2024-09-18', 19),
('CIR42', '2024-10-15', 17),
('CIR42', '2024-11-25', 24),
('CIR42', '2024-07-15', 18),
('CIR42', '2024-08-10', 23),

('CIR43', '2024-05-22', 20),
('CIR43', '2024-06-30', 18),
('CIR43', '2024-09-20', 22),
('CIR43', '2024-10-18', 16),
('CIR43', '2024-11-28', 21),
('CIR43', '2024-07-20', 19),
('CIR43', '2024-08-15', 23),

('CIR44', '2024-05-25', 18),
('CIR44', '2024-07-02', 20),
('CIR44', '2024-09-22', 23),
('CIR44', '2024-10-20', 19),
('CIR44', '2024-11-30', 22),
('CIR44', '2024-07-22', 21),
('CIR44', '2024-08-18', 17),

('CIR45', '2024-05-28', 21),
('CIR45', '2024-07-05', 19),
('CIR45', '2024-09-25', 22),
('CIR45', '2024-10-22', 20),
('CIR45', '2024-12-02', 23),
('CIR45', '2024-07-25', 18),
('CIR45', '2024-08-20', 24),

('CIR46', '2024-05-15', 20),
('CIR46', '2024-06-20', 15),
('CIR46', '2024-09-10', 15),
('CIR46', '2024-10-05', 10),
('CIR46', '2024-11-20', 20),
('CIR46', '2024-07-10', 10),
('CIR46', '2024-08-05', 25),

('CIR47', '2024-05-18', 18),
('CIR47', '2024-06-25', 22),
('CIR47', '2024-09-15', 16),
('CIR47', '2024-10-10', 20),
('CIR47', '2024-11-22', 23),
('CIR47', '2024-07-12', 19),
('CIR47', '2024-08-07', 21),

('CIR48', '2024-05-20', 22),
('CIR48', '2024-06-28', 20),
('CIR48', '2024-09-18', 19),
('CIR48', '2024-10-15', 17),
('CIR48', '2024-11-25', 24),
('CIR48', '2024-07-15', 18),
('CIR48', '2024-08-10', 23),

('CIR49', '2024-05-22', 20),
('CIR49', '2024-06-30', 18),
('CIR49', '2024-09-20', 22),
('CIR49', '2024-10-18', 16),
('CIR49', '2024-11-28', 21),
('CIR49', '2024-07-20', 19),
('CIR49', '2024-08-15', 23),

('CIR50', '2024-05-15', 20),
('CIR50', '2024-06-20', 15),
('CIR50', '2024-09-10', 15),
('CIR50', '2024-10-05', 10),
('CIR50', '2024-11-20', 20),
('CIR50', '2024-07-10', 10),
('CIR50', '2024-08-05', 25);

INSERT INTO Etapa (identificador, orden, nombreLugar, ciudad, país, duración) VALUES
('CIR01', 1, 'Bellas Artes', 'CDMX', 'México', 1),
('CIR01', 2, 'Playa delfin', 'Cancún', 'México',3),
('CIR01', 3, 'Cabañas', 'Guadalajara', 'México',1),
('CIR01', 4, 'Fundidora', 'Monterrey', 'México',1);