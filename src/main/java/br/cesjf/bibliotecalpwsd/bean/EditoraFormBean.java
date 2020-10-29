/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.bibliotecalpwsd.bean;

import br.cesjf.bibliotecalpwsd.dao.EditoraDAO;
import br.cesjf.bibliotecalpwsd.model.Editora;
import java.io.Serializable;
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
public class EditoraFormBean extends CommandFormBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private Editora editora;

	public EditoraFormBean() {

	}

	public Editora getEditora() {
		return editora;
	}

	public void setEditora(Editora editora) {
		this.editora = editora;
	}

	@Override
	public void init() {
		if (Faces.isAjaxRequest()) {
			return;
		}

		if (id != null) {
			editora = EditoraDAO.getInstance().find(id);
		} else {
			editora = Editora.Builder.newInstance().build();
		}
	}

	@Override
	public void record(ActionEvent actionEvent) {
		EditoraDAO.getInstance().persist(editora);
		includeMessage();
	}

	@Override
	public void exclude(ActionEvent actionEvent) {
		EditoraDAO.getInstance().remove(editora.getId());
		removeMessage();
	}

	@Override
	public void clear() {
		editora = Editora.Builder.newInstance().build();
	}

	@Override
	public boolean isNew() {
		return editora == null || editora.getId() == null || editora.getId() == 0;
	}
}