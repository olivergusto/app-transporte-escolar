package com.example.androidqrcodescanner;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnScan;
    private  Button btnSMS;
    private TextView txtNome, txtResponsavel, txtEndereco, txtBairro, txtFone1, txtFone2, txtSituacao;
    private ImageView arquivo;

    private IntentIntegrator qrscan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnScan = findViewById(R.id.btnScan);
        txtNome = findViewById(R.id.edtNome);
        txtResponsavel = findViewById(R.id.edtResponsavel);
        txtEndereco = findViewById(R.id.edtEndereco);
        txtBairro = findViewById(R.id.edtBairro);
        txtFone1 = findViewById(R.id.edtFone1);
        txtFone2 = findViewById(R.id.edtFone2);
        txtSituacao = findViewById(R.id.edtSituacao);
//        arquivo = (ImageView) findViewById(R.id.imageView);


        qrscan = new IntentIntegrator(this);

        btnScan.setOnClickListener(this);

        btnSMS = (Button) findViewById(R.id.btnEnviarSms);
        btnSMS.setEnabled(false);






        }
    @Override
    public void onClick(View v) {
        qrscan.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {

            if (result.getContents() == null) {
                Toast.makeText(this, "Resultado não encontrado", Toast.LENGTH_SHORT).show();
            }else {

                try {
                    btnSMS = (Button) findViewById(R.id.btnEnviarSms);
                    btnSMS.setEnabled(true);

                    JSONObject obj = new JSONObject(result.getContents());

                    txtNome.setText(obj.getString("nome"));
                    txtResponsavel.setText(obj.getString("nome_responsavel"));
                    txtEndereco.setText(obj.getString("endereco"));
                    txtBairro.setText(obj.getString("bairro"));
                    txtFone1.setText(obj.getString("fone1"));
                    txtFone2.setText(obj.getString("fone2"));
                    txtSituacao.setText(obj.getString("situacao"));
//                    arquivo.setImageBitmap(BitmapFactory.decodeFile("arquivo"));
                }catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                }
            }
        }else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    public void EnviarSms(View v){



        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(txtFone1.getText().toString(), null, "Aluno(a) " + txtNome.getText().toString() + " " + "acabou de embarcar", null, null);

        Toast.makeText(this, "Sms Enviado", Toast.LENGTH_SHORT).show();

        btnSMS = (Button) findViewById(R.id.btnEnviarSms);
        btnSMS.setEnabled(false);



    }
}
