package br.cesjf.bibliotecalpwsd.bean;

import javax.faces.event.ActionEvent;

public abstract class CommandFormBean {
	protected Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public abstract void init();
	
	public abstract void clear();

	public abstract boolean isNew();

	public abstract void record(ActionEvent actionEvent);

	public abstract void exclude(ActionEvent actionEvent);
	
	public void includeMessage() {
		System.out.println("Adicionado com sucesso!");
	}
	
	public void removeMessage() {
		System.out.println("Removido com sucesso!");
	}
}
