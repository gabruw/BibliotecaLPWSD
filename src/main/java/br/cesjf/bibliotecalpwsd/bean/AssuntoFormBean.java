package br.cesjf.bibliotecalpwsd.bean;

import br.cesjf.bibliotecalpwsd.dao.AssuntoDAO;
import br.cesjf.bibliotecalpwsd.model.Assunto;
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
public class AssuntoFormBean extends CommandFormBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private Assunto assunto;

	public AssuntoFormBean() {

	}

	public Assunto getAssunto() {
		return assunto;
	}

	public void setAssunto(Assunto assunto) {
		this.assunto = assunto;
	}

	@Override
	public void init() {
		if (Faces.isAjaxRequest()) {
			return;
		}
		
		if (id != null) {
			assunto = AssuntoDAO.getInstance().find(id);
		} else {
			assunto = Assunto.Builder.newInstance().build();
		}
	}

	@Override
	public void record(ActionEvent actionEvent) {
		AssuntoDAO.getInstance().persist(assunto);
		includeMessage();
	}

	@Override
	public void exclude(ActionEvent actionEvent) {
		AssuntoDAO.getInstance().remove(assunto.getId());
		removeMessage();
	}

	@Override
	public void clear() {
		assunto = Assunto.Builder.newInstance().build();
	}

	@Override
	public boolean isNew() {
		return assunto == null || assunto.getId() == null || assunto.getId() == 0;
	}
}