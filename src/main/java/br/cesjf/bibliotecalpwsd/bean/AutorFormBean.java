package br.cesjf.bibliotecalpwsd.bean;

import br.cesjf.bibliotecalpwsd.dao.AutorDAO;
import br.cesjf.bibliotecalpwsd.model.Autor;
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
public class AutorFormBean extends CommandFormBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private Autor autor;

    public AutorFormBean() {
    	
    }
    
    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }
    
    @Override
    public void init() {
        if(Faces.isAjaxRequest()){
           return;
        }
        
        if (id != null) {
            autor = AutorDAO.getInstance().find(id);
        } else {
            autor = Autor.Builder
                         .newInstance()
                         .build();
        }
    }
    
    @Override
    public void record(ActionEvent actionEvent) {
        AutorDAO.getInstance().persist(autor);
        includeMessage();
    }
    
    @Override
    public void exclude(ActionEvent actionEvent) {
        AutorDAO.getInstance().remove(autor.getId());
        removeMessage();
    }
    
	@Override
	public void clear() {
		autor = Autor.Builder.newInstance().build();
	}

	@Override
	public boolean isNew() {
		return autor == null || autor.getId() == null || autor.getId() == 0;
	}
}