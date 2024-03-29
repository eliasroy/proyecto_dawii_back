package com.proyecto.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.entidad.Sede;
import com.proyecto.service.SedeService;
import com.proyecto.util.Constantes;

@RestController
@RequestMapping("/url/sede")
@CrossOrigin(origins = "http://localhost:4200")
public class SedeController {
	
	//Autor : Hernan Cardenas Breña
	
	@Autowired
	private SedeService sedeService;

	@GetMapping
	@ResponseBody
	public ResponseEntity<List<Sede>> listaSede(){
		List<Sede> lista = sedeService.listaSede();
		return ResponseEntity.ok(lista);
	}

	@PostMapping
	@ResponseBody
	public  ResponseEntity<Map<String, Object>> insertaSede(@RequestBody Sede obj){
		Map<String, Object> salida = new HashMap<>();
		try {
			obj.setEstado(1);
			obj.setFechaRegistro(new Date());
			Sede objSalida = sedeService.insertaActualizaSede(obj);
			if (objSalida == null) {
				salida.put("mensaje", "No se registró, consulte con el administrador.");
			}else {
				salida.put("mensaje", "Se registró correctamente.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			salida.put("mensaje", "No se registró, consulte con el administrador.");
		}
		return ResponseEntity.ok(salida);
	}
	
	
	@GetMapping("/listaSedeConParametros")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> listaSedeNombreDireccionPaisEstado(
			@RequestParam(name = "nombre", required = false, defaultValue = "") String nombre,
			@RequestParam(name = "direccion", required = false, defaultValue = "") String direccion,
			@RequestParam(name = "idPais", required = false, defaultValue = "-1") int idPais,
			@RequestParam(name = "estado", required = true, defaultValue = "1") int estado) {
		Map<String, Object> salida = new HashMap<>();
		try {
			List<Sede> lista = sedeService.listaSedePorNombreDireccionPaisEstado("%"+nombre+"%", direccion, idPais, estado);
			if (CollectionUtils.isEmpty(lista)) {
				salida.put("mensaje", "No existen datos para mostrar");
			}else {
				salida.put("lista", lista);
				salida.put("mensaje", "Existen " + lista.size() + " elementos para mostrar");
			}
		} catch (Exception e) {
			e.printStackTrace();
			salida.put("mensaje", Constantes.MENSAJE_REG_ERROR);
		}
		return ResponseEntity.ok(salida);

	}
	
	
	/*===========================CRUD SEDE===================================*/
	
	@GetMapping("/listaSedePorNombreLike/{nom}")
	@ResponseBody
	public ResponseEntity<List<Sede>> listaDocentePorNombreLike(@PathVariable("nom") String nom) {
		List<Sede> lista  = null;
		try {
			if (nom.equals("todos")) {
				lista = sedeService.listaSedePorNombreLike("%");
			}else {
				lista = sedeService.listaSedePorNombreLike("%" + nom + "%");	
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok(lista);
	}
	
	@PostMapping("/registraSede")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> insertaDocente(@RequestBody Sede obj) {
		Map<String, Object> salida = new HashMap<>();
		try {
			obj.setIdSede(0);
			obj.setFechaRegistro(new Date());
			obj.setEstado(1);
			Sede objSalida =  sedeService.insertaActualizaSede(obj);
			if (objSalida == null) {
				salida.put("mensaje", Constantes.MENSAJE_REG_ERROR);
			} else {
				salida.put("mensaje", Constantes.MENSAJE_REG_EXITOSO);
			}
		} catch (Exception e) {
			e.printStackTrace();
			salida.put("mensaje", Constantes.MENSAJE_REG_ERROR);
		}
		return ResponseEntity.ok(salida);
	}

	@PutMapping("/actualizaSede")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> actualizaDocente(@RequestBody Sede obj) {
		Map<String, Object> salida = new HashMap<>();
		try {
			Sede objSalida =  sedeService.insertaActualizaSede(obj);
			if (objSalida == null) {
				salida.put("mensaje", Constantes.MENSAJE_ACT_ERROR);
			} else {
				salida.put("mensaje", Constantes.MENSAJE_ACT_EXITOSO);
			}
		} catch (Exception e) {
			e.printStackTrace();
			salida.put("mensaje", Constantes.MENSAJE_ACT_ERROR);
		}
		return ResponseEntity.ok(salida);
	}
	
	@DeleteMapping("/eliminaSede/{id}")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> eliminaSede(@PathVariable("id")int id) {
		Map<String, Object> salida = new HashMap<>();
		try {
			Optional<Sede> opt = sedeService.buscaSede(id);
			if (opt.isPresent()) {
				sedeService.eliminaSede(id);
				Optional<Sede> optSede = sedeService.buscaSede(id);
				if (optSede.isEmpty()) {
					salida.put("mensaje", Constantes.MENSAJE_ELI_EXITOSO);
				} else {
					salida.put("mensaje", Constantes.MENSAJE_ELI_ERROR);
				}
			}else {
				salida.put("mensaje", Constantes.MENSAJE_ELI_NO_EXISTE_ID);
			}
		} catch (Exception e) {
			e.printStackTrace();
			salida.put("mensaje", Constantes.MENSAJE_ELI_ERROR);
		}
		return ResponseEntity.ok(salida);
	}
	

}
