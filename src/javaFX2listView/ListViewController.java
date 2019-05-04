package javaFX2listView;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.TreeSet;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class ListViewController {

	@FXML
	private ListView<String> lsv01;
	@FXML
	private Button btn01;
	@FXML
	private Button btn02;
	@FXML
	private ListView<String> lsv02;

	private ArrayList<String> cocktailNames = new ArrayList<>();
	private HashMap<String, String> cocktailHmap = new HashMap<>();

	@FXML
	void initialize() {

		// get Cocktail list
		{
			URL url = this.getClass().getResource("res/cocktail.csv");
			OpCsv csv = new OpCsv(url);

			TreeMap<Integer, String[]> map = csv.getCsv();
			Iterator<Integer> it = map.keySet().iterator();
			while (it.hasNext()) {
				int no = it.next();
				String[] words = map.get(no);
				String ename = words[0];
				String jname = words[1];
				if (cocktailHmap.containsKey(ename)) {
					String duplicateKey = ename + " ## duplicate ##";
					cocktailHmap.put(duplicateKey, jname + " (T_T)");
				}
				else {
					cocktailHmap.put(ename, jname);
				}
			}

			Iterator<String> itCocktail = (new TreeSet<>(cocktailHmap.keySet())).iterator(); // sort the key
			while (itCocktail.hasNext()) {
				cocktailNames.add(itCocktail.next());
			}
		}

		assert lsv01 != null : "fx:id=\"lsv01\" was not injected: check your FXML file 'ListView.fxml'.";
		assert btn01 != null : "fx:id=\"btn01\" was not injected: check your FXML file 'ListView.fxml'.";
		assert btn02 != null : "fx:id=\"btn02\" was not injected: check your FXML file 'ListView.fxml'.";
		assert lsv02 != null : "fx:id=\"lsv02\" was not injected: check your FXML file 'ListView.fxml'.";

		this.lsv01.getItems().setAll(cocktailNames);
		this.lsv01.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		this.lsv02.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		// sort
		java.util.Collections.sort(lsv01.getItems(), new java.util.Comparator<String>() {
			@Override
			public int compare(String s1, String s2) {
				return s1.compareTo(s2);
			}
		});
	}

	@FXML
	void btn01OnAction(ActionEvent event) {
		this.lsv01SelectToRight();
	}

	@FXML
	protected void lsv01DoubleClick(MouseEvent e) {
		if (e.getButton().equals(MouseButton.PRIMARY)) {
			if (e.getClickCount() == 2) {
				this.lsv01SelectToRight();
			}
		}
	}

	@FXML
	void btn02OnAction(ActionEvent event) {
		this.lsv02SelectToLeft();
	}

	@FXML
	protected void lsv02DoubleClick(MouseEvent e) {
		if (e.getButton().equals(MouseButton.PRIMARY)) {
			if (e.getClickCount() == 2) {
				this.lsv02SelectToLeft();
			}
		}
	}

	private void lsv01SelectToRight() {
		// bug fix
		// ObservableList<String> selected =
		// this.lsv01.getSelectionModel().getSelectedItems();
		ObservableList<String> selected = FXCollections.observableArrayList(this.lsv01.getSelectionModel()
				.getSelectedItems());
		this.lsv02.getItems().addAll(selected);
		this.lsv01.getItems().removeAll(selected);
		this.lsv01.getSelectionModel().clearSelection();
		this.lsv02.getSelectionModel().clearSelection();

		// sort
		java.util.Collections.sort(lsv02.getItems(), new java.util.Comparator<String>() {
			@Override
			public int compare(String s1, String s2) {
				return s1.compareTo(s2);
			}
		});
	}

	private void lsv02SelectToLeft() {
		// bug fix
		// ObservableList<String> selected =
		// this.lsv02.getSelectionModel().getSelectedItems();
		ObservableList<String> selected = FXCollections.observableArrayList(this.lsv02.getSelectionModel()
				.getSelectedItems());
		this.lsv01.getItems().addAll(selected);
		this.lsv02.getItems().removeAll(selected);
		this.lsv01.getSelectionModel().clearSelection();
		this.lsv02.getSelectionModel().clearSelection();

		// sort
		java.util.Collections.sort(lsv01.getItems(), new java.util.Comparator<String>() {
			@Override
			public int compare(String s1, String s2) {
				return s1.compareTo(s2);
			}
		});
	}

}
