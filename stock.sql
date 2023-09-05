use control_de_stock;

create table producto(
id INT AUTO_INCREMENT,
nombre VARCHAR(50) NOT NULL,
descripcion varchar(25),
cantidad INT NOT NULL DEFAULT 0, 
PRIMARY KEY(id)
)Engine=InnoDB; #innodb para que la tabla acepte transacciones en una operacion

select * from producto;

insert into producto(nombre, descripcion, cantidad) 
values ('Mesa', 'mesa de 4 puestos', 10);
insert into producto(nombre, descripcion, cantidad) 
values ('Celular', 'samsung galaxy', 20);