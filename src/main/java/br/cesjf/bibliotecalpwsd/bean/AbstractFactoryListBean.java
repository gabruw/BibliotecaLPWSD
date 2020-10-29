package br.cesjf.bibliotecalpwsd.bean;

import javax.faces.event.ActionEvent;

public interface AbstractFactoryListBean {
	void buscarPorId(Long id);
	void novo(ActionEvent actionEvent);
	void record(ActionEvent actionEvent);
	void exclude(ActionEvent actionEvent);
}
