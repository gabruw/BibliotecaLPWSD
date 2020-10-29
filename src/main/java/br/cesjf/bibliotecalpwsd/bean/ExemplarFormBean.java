/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.bibliotecalpwsd.bean;

import br.cesjf.bibliotecalpwsd.dao.ExemplarDAO;
import br.cesjf.bibliotecalpwsd.dao.LivroDAO;
import br.cesjf.bibliotecalpwsd.model.Exemplar;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import javax.faces.event.ActionEvent;
import org.omnifaces.cdi.ViewScoped;
import javax.inject.Named;
import org.omnifaces.util.Faces;

/**
 *
 * @author dmeireles
 */
@Named
@ViewScoped
public class ExemplarFormBean extends CommandFormBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private Exemplar exemplar;
	private List livros;

	public ExemplarFormBean() {
		livros = LivroDAO.getInstance().getList();
	}

	public Exemplar getExemplar() {
		return exemplar;
	}

	public void setExemplar(Exemplar exemplar) {
		this.exemplar = exemplar;
	}

	public List getLivros() {
		Collections.sort(livros);
		return livros;
	}

	public void setLivros(List livros) {
		this.livros = livros;
	}

	@Override
	public void init() {
		if (Faces.isAjaxRequest()) {
			return;
		}
		if (id != null) {
			exemplar = ExemplarDAO.getInstance().find(id);
		} else {
			exemplar = new Exemplar();
		}
	}

	@Override
	public void record(ActionEvent actionEvent) {
		ExemplarDAO.getInstance().persist(exemplar);
		includeMessage();
	}

	@Override
	public void exclude(ActionEvent actionEvent) {
		ExemplarDAO.getInstance().remove(exemplar.getId());
		removeMessage();
	}

	@Override
	public void clear() {
		exemplar = new Exemplar();
	}

	@Override
	public boolean isNew() {
		return exemplar == null || exemplar.getId() == null || exemplar.getId() == 0;
	}
}