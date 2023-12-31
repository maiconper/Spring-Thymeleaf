package com.mballem.curso.boot.web.controller;

import com.mballem.curso.boot.domain.Cargo;
import com.mballem.curso.boot.domain.Funcionario;
import com.mballem.curso.boot.domain.UF;
import com.mballem.curso.boot.service.CargoService;
import com.mballem.curso.boot.service.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/funcionarios")
public class FuncionarioController {

	@Autowired
	FuncionarioService funcionarioService;
	@Autowired
	CargoService cargoService;

	@GetMapping("/cadastrar")
	public String cadastrar(Funcionario funcionario) {
		return "/funcionario/cadastro";
	}
	
	@GetMapping("/listar")
	public String listar(ModelMap model) {
		model.addAttribute("funcionarios",funcionarioService.buscarTodos());
		return "/funcionario/lista"; 
	}

	@PostMapping("/salvar")
	public String salvar(Funcionario funcionario, RedirectAttributes attr){
		System.out.println("data:" + funcionario.getDataEntrada());
		funcionarioService.salvar(funcionario);
		attr.addFlashAttribute("success","Funcionario inserido com sucesso");
		return "redirect:/funcionarios/cadastrar";
	}

	@GetMapping("/editar/{id}")
	public String preEditar(@PathVariable("id") Long id, ModelMap model){

		model.addAttribute("funcionario",funcionarioService.buscarPorId(id));
		return "/funcionario/cadastro";
	}
	@PostMapping("/editar")
	public String editar(Funcionario funcionario, RedirectAttributes attr){
		funcionarioService.editar(funcionario);
		attr.addFlashAttribute("success","Funcionario editado com sucesso");
		return "redirect:/funcionarios/cadastrar";

	}

	@ModelAttribute("cargos")
	public List<Cargo> getCargos(){
		System.out.println(cargoService.buscarTodos().size());
		return cargoService.buscarTodos();
	}

	@ModelAttribute("ufs")
	public UF[] getUFs(){
		return UF.values();
	}
}
