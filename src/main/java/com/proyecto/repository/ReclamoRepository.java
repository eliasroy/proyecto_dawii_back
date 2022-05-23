package com.proyecto.repository;



import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import com.proyecto.entidad.Reclamo;

public interface ReclamoRepository extends JpaRepository<Reclamo, Integer> {
	
/*@Query("select x from Reclamo x where (?1 is '' or x.descripcion like ?1) and (?2 is -1 or x.cliente.idCliente = ?2) and (?3 is -1 or x.tipoReclamo.idTipoReclamo = ?3)and (?4 is '' or x.fechaCompra like ?4) ")
public  List<Reclamo> ListaReclamoDescricionClienteTiporeclamoFecha(String descripcion ,int idCliente , int idTipoReclamo , Date fechaCompra);*/
	
@Query("select x from Reclamo x where (?1 is '' or x.descripcion like ?1) and (?2 is -1 or x.cliente.idCliente = ?2) and (?3 is -1 or x.tipoReclamo.idTipoReclamo = ?3)and (?4 is '' or x.fechaCompra like ?4) ")	
public  List<Reclamo> ListaReclamoDescricionClienteTiporeclamo(String descripcion ,int idCliente , int idTipoReclamo );
	
}
