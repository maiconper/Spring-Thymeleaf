package com.mballem.curso.boot.web.controller;

import com.mballem.curso.boot.domain.Departamento;
import com.mballem.curso.boot.service.DepartamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/departamentos")
public class DepartamentoController {


	@Autowired
	private DepartamentoService departamentoService;

	@GetMapping("/cadastrar")
	public String cadastrar(Departamento departamento) {
		return "/departamento/cadastro";
	}
	
	@GetMapping("/listar")
	public String listar(ModelMap model) {

		model.addAttribute("departamentos",departamentoService.buscarTodos());

		return "/departamento/lista";
	}

	@GetMapping("/editar/{id}")
	public String preEditar(@PathVariable("id") Long id, ModelMap model){

		model.addAttribute("departamento",departamentoService.buscarPorId(id));
		return "/departamento/cadastro";

	}

	@PostMapping("/editar")
	public String preEditar(Departamento departamento, RedirectAttributes attr){

		departamentoService.editar(departamento);
		attr.addFlashAttribute("success","Departamento editado com sucesso.");
		return "redirect:/departamentos/cadastrar";

	}

	@PostMapping("/salvar")
	public String salvar(Departamento departamento, RedirectAttributes attr){
		departamentoService.salvar(departamento);
		attr.addFlashAttribute("success","Departamento salvo com sucesso.");
		return "redirect:/departamentos/cadastrar";
	}

	@GetMapping("/excluir/{id}")
	public String excluir(@PathVariable("id") Long id, ModelMap model){
		if(departamentoService.departamentoTemCargos(id)){
			model.addAttribute("fail","Departamento contem funcionarios.");
		} else{
			departamentoService.excluir(id);
			model.addAttribute("success","Departamento removido.");
		}
		return listar(model);
	}

}
