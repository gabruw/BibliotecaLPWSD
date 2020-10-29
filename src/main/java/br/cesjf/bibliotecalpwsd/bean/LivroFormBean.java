/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.bibliotecalpwsd.bean;

import br.cesjf.bibliotecalpwsd.dao.AssuntoDAO;
import br.cesjf.bibliotecalpwsd.dao.AutorDAO;
import br.cesjf.bibliotecalpwsd.dao.EditoraDAO;
import br.cesjf.bibliotecalpwsd.dao.LivroDAO;
import br.cesjf.bibliotecalpwsd.enums.MessageType;
import br.cesjf.bibliotecalpwsd.model.Editora;
import br.cesjf.bibliotecalpwsd.model.Livro;
import br.cesjf.bibliotecalpwsd.util.Message;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.omnifaces.cdi.ViewScoped;
import javax.inject.Named;
import org.omnifaces.util.Faces;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author dmeireles
 */
@Named
@ViewScoped
public class LivroFormBean extends CommandFormBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private UploadedFile uploadedFile;
	private List<Editora> editoras;
	private final String diretorio;
	private List assuntos;
	private List autores;
	private Livro livro;

	public LivroFormBean() {
		assuntos = AssuntoDAO.getInstance().getList();
		autores = AutorDAO.getInstance().getList();
		editoras = EditoraDAO.getInstance().getList();

		ExternalContext e = FacesContext.getCurrentInstance().getExternalContext();
		diretorio = e.getRealPath("resources\\arquivos");
	}

	public Livro getLivro() {
		return livro;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}

	public List getAssuntos() {
		Collections.sort(assuntos);
		return assuntos;
	}

	public void setAssuntos(List<?> assuntos) {
		this.assuntos = assuntos;
	}

	public List getAutores() {
		Collections.sort(autores);
		return autores;
	}

	public void setAutores(List<?> autores) {
		this.autores = autores;
	}

	public List<Editora> getEditoras() {
		Collections.sort(editoras);
		return editoras;
	}

	public void setEditoras(List<Editora> editoras) {
		this.editoras = editoras;
	}

	public String getCapa() {
		return diretorio + "\\" + livro.getArquivo();
	}

	public UploadedFile getUploadedFile() {
		return uploadedFile;
	}

	public void setUploadedFile(UploadedFile uploadedFile) {
		this.uploadedFile = uploadedFile;
		upload();
	}

	@Override
	public void init() {
		if (Faces.isAjaxRequest()) {
			return;
		}

		if (id != null) {
			livro = LivroDAO.getInstance().find(id);
		} else {
			livro = new Livro();
		}
	}

	@Override
	public void record(ActionEvent actionEvent) {
		upload();
		LivroDAO.getInstance().persist(livro);
		includeMessage();
	}

	@Override
	public void exclude(ActionEvent actionEvent) {
		delete(1);
		delete(2);
		LivroDAO.getInstance().remove(livro.getId());
		removeMessage();
	}

	@Override
	public void clear() {
		livro = new Livro();
	}

	@Override
	public boolean isNew() {
		return livro == null || livro.getId() == null || livro.getId() == 0;
	}

	public void upload() {
		if (uploadedFile != null) {
			File dir = new File(diretorio);

			if (!dir.exists()) {
				dir.mkdirs();
			}

			try {
				String name = new Timestamp(System.currentTimeMillis()).toString();
				name = name.replace("-", "").replace(".", "").replace(":", "").replace(" ", "");
				name = name + uploadedFile.getFileName();
				File file = new File(dir, name);
				OutputStream out = new FileOutputStream(file);
				out.write(uploadedFile.getContents());
				out.close();
				Message.logAndScreenMessage(MessageType.INFO,
						"O arquivo " + uploadedFile.getFileName() + " foi salvo!");
				if (uploadedFile.getFileName().toUpperCase().contains(".PDF")) {
					livro.setArquivo(name);
				} else {
					livro.setCapa(name);
				}
				uploadedFile = null;
			} catch (IOException e) {
				Message.logAndScreenMessage(MessageType.INFO,
						"Não foi possível salvar o arquivo " + uploadedFile.getFileName() + "!");
			}
		}
	}

	public void delete(int i) {
		File file;
		if (i == 1 && livro.getCapa() != null) {
			file = new File(diretorio + "\\" + livro.getCapa());
			file.delete();
		} else if (i == 2 && livro.getArquivo() != null) {
			file = new File(diretorio + "\\" + livro.getArquivo());
			file.delete();
		}

		Message.logAndScreenMessage(MessageType.INFO, "Arquivo apagado com sucesso");

		if (i == 1) {
			livro.setCapa(null);
		} else {
			livro.setArquivo(null);
		}

		LivroDAO.getInstance().persist(livro);
	}
}