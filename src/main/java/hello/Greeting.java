package hello;

import java.util.LinkedList;
import java.util.List;

public class Greeting {

	private final long id;
	private final String content;
	private List<String> listNome;

	public Greeting(long id, String content) {
		this.id = id;
		this.content = content;
		this.init();
	}

	private void init() {
		this.listNome = new LinkedList<String>();
		this.listNome.add("nome 1");
		this.listNome.add("nome 2");
		this.listNome.add("nome 3");
	}

	public long getId() {
		return id;
	}

	public String getContent() {
		return content;
	}

	public List<String> getListNome() {
		return listNome;
	}

	public void setListNome(List<String> listNome) {
		this.listNome = listNome;
	}

}