package redeneural;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class PrincipalController implements Initializable{
	
	@FXML
    private Button btnTreinar;

	@FXML
	private TextField txtInput1;

	@FXML
	private TextField txtInput2;

	@FXML
	private TextField txtInput3;

	@FXML
	private TextField txtPeso1;

	@FXML
	private TextField txtPeso2;

	@FXML
	private TextField txtPeso3;

	@FXML
	private Label lblSaida;
	
    @FXML
    private TextField txtResultado;
    
    private static Map<Integer, String> personagens = new HashMap<Integer, String>();
    
    
    static {
    	personagens.put(0, "Compositor");
    	personagens.put(1, "Cientista");
    }

	public void treina() {

		int input1 = 0;
		int input2 = 0;
		int input3 = 0;
		
		int peso1 = 0;
		int peso2 = 0;
		int peso3 = 0;
		int saidaEsparada;
		int saidaCalculada;
		String resultadoEsperado = "";
		int valorErro = 0;
		

		if (txtInput1.getText() != null && !"".equals(txtInput1.getText())) {
			input1 = Integer.parseInt(txtInput1.getText());
		}

		if (txtInput2.getText() != null && !"".equals(txtInput2.getText())) {
			input2 = Integer.parseInt(txtInput2.getText());
		}

		if (txtInput3.getText() != null && !"".equals(txtInput3.getText())) {
			input3 = Integer.parseInt(txtInput3.getText());
		}
		
		if (txtPeso1.getText() != null && !"".equals(txtPeso1.getText())) {
			peso1 = Integer.parseInt(txtPeso1.getText());
		}

		if (txtPeso2.getText() != null && !"".equals(txtPeso2.getText())) {
			peso2 = Integer.parseInt(txtPeso2.getText());
		}

		if (txtPeso3.getText() != null && !"".equals(txtPeso3.getText())) {
			peso3 = Integer.parseInt(txtPeso3.getText());
		}
		
		if(txtResultado.getText() != null && !"".equals(txtResultado.getText())){
			resultadoEsperado = txtResultado.getText();
		}
		
		saidaEsparada = resultadoEsperado != null && !"".equals(resultadoEsperado) ? Integer.parseInt(resultadoEsperado) : null;
		
		int valorFuncao = calculaValorFuncao(input1, peso1, input2, peso2, input3, peso3);
		
		if(valorFuncao > 0 ){
			saidaCalculada = 1;
		}else{
			saidaCalculada = 0;
		}
		
		if(saidaCalculada == saidaEsparada){
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setHeaderText("Rede Neural treinada");
			valorErro = montaResposta(peso1, peso2, peso3, saidaEsparada, saidaCalculada, valorFuncao, alert, false);
			alert.showAndWait();
		} else {
			
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Erro");
			alert.setHeaderText("Rede Neural não treinada");
			valorErro = montaResposta(peso1, peso2, peso3, saidaEsparada, saidaCalculada, valorFuncao, alert, true);
			
			ButtonType btnSim = new ButtonType("Sim");
			ButtonType btnNao = new ButtonType("Não");
			
			alert.getButtonTypes().setAll(btnSim, btnNao);
			
			Optional<ButtonType> opcao = alert.showAndWait();
			
			if(opcao.get() == btnSim){
				peso1 = peso1 + 1 * valorErro * input1;
				txtPeso1.setText(String.valueOf(peso1));
				peso2 = peso2 + 1 * valorErro * input2;
				txtPeso2.setText(String.valueOf(peso2));
				peso3 = peso3 + 1 * valorErro * input3;
				txtPeso3.setText(String.valueOf(peso3));
				treina();
			}else {
				alert.close();
			}
			
			
		}
	}

	private int montaResposta(int peso1, int peso2, int peso3, int saidaEsparada, int saidaCalculada, int valorFuncao,
			Alert alert, boolean hasErro) {
		int valorErro;
		valorErro = saidaEsparada - saidaCalculada;
		
		StringBuilder informaPesos = new StringBuilder("");
		
		informaPesos.append("Peso 1: ").append(peso1).append("\n");
		informaPesos.append("Peso 2: ").append(peso2).append("\n");
		informaPesos.append("Peso 3: ").append(peso3).append("\n");
		informaPesos.append("Valor da função de ativação: ").append(valorFuncao).append("\n");
		informaPesos.append("Saida esperada: ").append(personagens.get(saidaEsparada)).append("\n");
		informaPesos.append("Saida calculada: ").append(personagens.get(saidaCalculada)).append("\n");
		informaPesos.append("Valor do erro: ").append(valorErro).append("\n");
		informaPesos.append("\n");
		if(hasErro){
			informaPesos.append("Deseja reajustar pesos?").append("\n");
		}
		
		alert.setContentText(informaPesos.toString());
		return valorErro;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		btnTreinar.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				treina();
			}
		});
		
		txtInput1.setText("1");
		txtPeso1.setText("0");
	}
	
	private int calculaValorFuncao(int input1, int peso1, int input2, int peso2, int input3, int peso3){
		
		int valor = (input1 * peso1) + (input2 * peso2) + (input3 * peso3); 
		
		return valor;
	}

}
