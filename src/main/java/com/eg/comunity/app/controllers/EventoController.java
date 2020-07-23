package com.eg.comunity.app.controllers;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.eg.comunity.app.models.entity.Evento;
import com.eg.comunity.app.models.service.IEventoService;
import com.eg.comunity.app.util.paginator.PageRender;

@Controller
public class EventoController {
	
	protected final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	private IEventoService eventoService;
	
	@RequestMapping(value = {"/", "/evento/eventos"}, method = RequestMethod.GET)
	public String listar(@RequestParam(name = "page", defaultValue = "0") int page, Model model,
			Authentication authentication,
			HttpServletRequest request) {	
		
		Pageable pageRequest = PageRequest.of(page, 5);

		Page<Evento> eventos = eventoService.findAll(pageRequest);

		PageRender<Evento> pageRender = new PageRender<Evento>("/eventos", eventos);
		model.addAttribute("titulo", "Listado de Eventos");
		model.addAttribute("eventos", eventos);
		model.addAttribute("page", pageRender);
		return "evento/eventos";
	}

}
