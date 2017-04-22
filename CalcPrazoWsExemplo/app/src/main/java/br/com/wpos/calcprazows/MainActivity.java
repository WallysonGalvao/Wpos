package br.com.wpos.calcprazows;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

@SuppressWarnings("WrongConstant")
public class MainActivity extends AppCompatActivity {

    EditText txtCepOrigem;
    EditText txtCepDestino;
    Spinner spiTipoServico;
    TextView txvPrazo;
    TextView txvEntregaSabado;
    LinearLayout linhaPrazo;
    LinearLayout linhaEntregaSabado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Identifica os elementos de layout para manipulação
        txtCepOrigem = (EditText) findViewById(R.id.txtCepOrigem);
        txtCepDestino = (EditText) findViewById(R.id.txtCepDestino);
        spiTipoServico = (Spinner) findViewById(R.id.spiTipoServico);
        txvPrazo = (TextView) findViewById(R.id.txvPrazo);
        txvEntregaSabado = (TextView) findViewById(R.id.txvEntregaSabado);

        linhaPrazo = (LinearLayout) findViewById(R.id.linhaPrazo);
        linhaEntregaSabado = (LinearLayout) findViewById(R.id.linhaEntregaSabado);
    }

    public void calcularPrazo(View view) {

        // Informações de Conexão WebService
        final String NAMESPACE = "http://tempuri.org/";
        final String METHOD_NAME = "CalcPrazo";
        final String SOAP_ACTION = "http://tempuri.org/CalcPrazo";
        final String URL = "http://ws.correios.com.br/calculador/CalcPrecoPrazo.asmx?wsdl";

        // Montando a Requisição SOAP com as informações a serem enviadas
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

        // Transforma texto de serviço em código de serviço
        String nCdServico = "";
        switch (spiTipoServico.getSelectedItem().toString()) {
            case "SEDEX Varejo":
                nCdServico = "40010";
                break;
            case "SEDEX a Cobrar Varejo":
                nCdServico = "40045";
                break;
            case "SEDEX 10 Varejo":
                nCdServico = "40215";
                break;
            case "SEDEX Hoje Varejo":
                nCdServico = "40290";
                break;
            case "PAC Varejo":
                nCdServico = "41106";
                break;
        }

        // Adiciona informações à requisição
        request.addProperty("nCdServico", nCdServico);
        request.addProperty("sCepOrigem", txtCepOrigem.getText().toString());
        request.addProperty("sCepDestino", txtCepDestino.getText().toString());

        // Criando o Envelope de Envio
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

        /*
        * Por ser um .ASMX, o Servidor de WebService é feito no padrão .NET. Isso
        * deve ser informado ao KSOAP por se tratar de um padrão diferente
        */
        envelope.dotNet = true;

        // Adicionando a Requisição SOAP ao seu envelope
        envelope.setOutputSoapObject(request);

        // Colocar o envelope SOAP em um protocolo HTTP de transporte
        HttpTransportSE transport = new HttpTransportSE(URL);

        try {
            // Realizar a chamada, enviando as informações
            transport.call(SOAP_ACTION, envelope);
            SoapObject response = (SoapObject) envelope.bodyIn;

            SoapObject responseAnyType = (SoapObject) response.getProperty(0);
            SoapObject responseServicos = (SoapObject) responseAnyType.getProperty("Servicos");
            SoapObject responseCServico = (SoapObject) responseServicos.getProperty("cServico");

            // Exibe o prazo na tela
            //txvPrazo.setText(responseCServicos.getProperty("PrazoEntrega").toString() + " dias");

            txvPrazo.setText(getString(R.string.prazo_mensagem, responseCServico.getProperty("PrazoEntrega").toString()));

            // Verifica o "S" e o "N" retornado e exibe as palavras em tela
            if (responseCServico.getProperty("EntregaSabado").toString().equals("S")) {
                txvEntregaSabado.setText(R.string.sim);
            } else {
                txvEntregaSabado.setText(R.string.nao);
            }

            // Torna as linhas de resultado visíveis
            linhaPrazo.setVisibility(1);
            linhaEntregaSabado.setVisibility(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
