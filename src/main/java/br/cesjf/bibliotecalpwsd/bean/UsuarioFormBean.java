/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.bibliotecalpwsd.bean;

import br.cesjf.bibliotecalpwsd.dao.UsuarioDAO;
import br.cesjf.bibliotecalpwsd.enums.UserType;
import br.cesjf.bibliotecalpwsd.model.Usuario;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
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
public class UsuarioFormBean extends CommandFormBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private Usuario usuario;
	
	Map<String, UserType> tipos;

	public UsuarioFormBean() {
		tipos = new HashMap<String, UserType>();

		for (UserType userType : UserType.values()) {
			tipos.put(userType.getUserType(), userType);
		}
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Map<String, UserType> getTipos() {
		return tipos;
	}

	public void setTipos(Map<String, UserType> tipos) {
		this.tipos = tipos;
	}

	@Override
	public void init() {
		if (Faces.isAjaxRequest()) {
			return;
		}

		if (id != null) {
			usuario = UsuarioDAO.getInstance().find(id);
		} else {
			usuario = Usuario.Builder.newInstance().build();
		}
	}

	@Override
	public void record(ActionEvent actionEvent) {
		UsuarioDAO.getInstance().persist(usuario);
		includeMessage();
	}

	@Override
	public void exclude(ActionEvent actionEvent) {
		UsuarioDAO.getInstance().remove(usuario.getId());
		removeMessage();
	}

	@Override
	public void clear() {
		usuario = Usuario.Builder.newInstance().build();
	}

	@Override
	public boolean isNew() {
		return usuario == null || usuario.getId() == null || usuario.getId() == 0;
	}
}