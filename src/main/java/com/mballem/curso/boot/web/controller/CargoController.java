package com.mballem.curso.boot.web.controller;

import com.mballem.curso.boot.domain.Departamento;
import com.mballem.curso.boot.service.CargoService;
import com.mballem.curso.boot.service.DepartamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.mballem.curso.boot.domain.Cargo;

import java.util.List;

@Controller
@RequestMapping("/cargos")
public class CargoController {

	@Autowired
	private CargoService service;
	@Autowired
	private DepartamentoService departamentoService;


	@GetMapping("/cadastrar")
	public String cadastrar(Cargo cargo) {
		return "/cargo/cadastro";
	}
	
	@GetMapping("/listar")
	public String listar(ModelMap model) {

		model.addAttribute("cargos",service.buscarTodos());

		return "/cargo/lista";
	}


	@PostMapping("/salvar")
	public String salvar(Cargo cargo, RedirectAttributes attr){

		service.salvar(cargo);
		attr.addFlashAttribute("success","Cargo inserido com sucesso");
		return "redirect:/cargos/cadastrar";
	}

	@GetMapping("/editar/{id}")
	public String editar(@PathVariable("id") Long id, ModelMap model){

		model.addAttribute("cargo",service.buscarPorId(id));

		return "/cargo/cadastro";

	}

	@PostMapping("/editar")
	public String preEditar(Cargo cargo, RedirectAttributes attr){
		service.editar(cargo);
		attr.addFlashAttribute("success","Cargo alterado com sucesso");
		return "redirect:/cargos/cadastrar";

	}

	@GetMapping("excluir/{id}")
	public String excluir(@PathVariable("id") Long id, RedirectAttributes attr){
		if(!service.temFuncionarios(id)){
			service.excluir(id);
			attr.addFlashAttribute("success","Cargo removido com sucesso.");
			return "redirect:/cargos/listar";
		}else {
			attr.addFlashAttribute("fail","Existem funcionarios com esse cargo.");
			return "redirect:/cargos/listar";
		}

	}
	@ModelAttribute("departamentos")
	public List<Departamento> listaDeDepartamentos(){

		return departamentoService.buscarTodos();
	}
}
