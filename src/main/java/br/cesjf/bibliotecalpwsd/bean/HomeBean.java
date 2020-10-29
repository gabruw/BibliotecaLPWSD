package br.cesjf.bibliotecalpwsd.bean;

import java.io.Serializable;

import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;

import br.cesjf.bibliotecalpwsd.dao.AssuntoDAO;
import br.cesjf.bibliotecalpwsd.dao.AutorDAO;
import br.cesjf.bibliotecalpwsd.dao.ExemplarDAO;
import br.cesjf.bibliotecalpwsd.dao.LivroDAO;

/**
 *
 * @author gabriel_marques
 */
@Named
@ViewScoped
public class HomeBean implements Serializable {
	private static final long serialVersionUID = 1L;

	public HomeBean() {

	}

	public int getAllAutorSize() {
		return AutorDAO.getInstance().getList().size();
	}

	public int getAllLivroSize() {
		return LivroDAO.getInstance().getList().size();
	}

	public int getAllAssuntoSize() {
		return AssuntoDAO.getInstance().getList().size();
	}

	public int getAllExemplarSize() {
		return ExemplarDAO.getInstance().getList().size();
	}
}
