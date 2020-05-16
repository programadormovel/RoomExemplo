package br.com.domtecpro.roomexemplo;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import static androidx.core.content.FileProvider.getUriForFile;

public class NovoColaboradorActivity extends AppCompatActivity {

    //Declaração de constantes
    private static final int REQUEST_TAKE_PHOTO = 1;
    private static final int PERMISSAO_REQUEST = 2;
    private static final int PEGA_FOTO = 3;
    //Declaração de objetos criados nas telas activity e content
    private AppCompatImageView imageView;
    private AppCompatTextView textView;
    private String currentPhotoPath;
    private Bitmap bitmap;
    private Uri photoURI;

    private AppCompatEditText edtNome, edtCargo, edtCpf, edtEndereco, edtEmail;

    //public static final String EXTRA_REPLY = "com.example.android.wordlistsql.REPLY";
    public static final String EXTRA_REPLY = "br.com.domtecpro.roomexemplo.REPLY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_palavra);

        edtNome = findViewById(R.id.edtNome);
        edtCargo = findViewById(R.id.edtCargo);
        edtCpf = findViewById(R.id.edtCpf);
        edtEndereco = findViewById(R.id.edtEndereco);
        edtEmail = findViewById(R.id.edtEmail);

        permissoesAcesso();

        final Button button = findViewById(R.id.btnSalvarColab);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(edtNome.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    byte[] imagemEnviada = stream.toByteArray();

                    Colaborador colaborador = new Colaborador(
                            0, edtNome.getText().toString(),
                            edtCargo.getText().toString(),
                            edtCpf.getText().toString(),
                            edtEndereco.getText().toString(),
                            edtEmail.getText().toString(),
                            edtNome.getText()
                                    .toString().toLowerCase().trim()
                                    + ".jpg", imagemEnviada);

                    //String word = mEditPalavraView.getText().toString();
                    //replyIntent.putExtra(EXTRA_REPLY, word);
                    replyIntent.putExtra(EXTRA_REPLY, (Parcelable) colaborador);
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });

        //Botão para editar foto do colaborador
        findViewById(R.id.editFotoColab)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Snackbar.make(v, "Acessando Galeria!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        // Acesso à Galeria
                        acessarGaleria();
                    }
                });

        //Botão para acionamento da câmera
        findViewById(R.id.capturaFotoColab)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Snackbar.make(v, "Capturando Imagem!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        //Chamada do método tirarFoto()
                        tirarFoto();
                    }
                });
    }

    private void permissoesAcesso(){
        //Condicional para controle de permissões
        // Verifica se há permissão para leitura de arquivos
        if (ContextCompat.checkSelfPermission(getBaseContext(),
                android.Manifest.permission.READ_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSAO_REQUEST);
            }
        }

        //Verifica se há permissões para escrita de arquivos
        if (ContextCompat.checkSelfPermission(getBaseContext(),
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSAO_REQUEST);
            }
        }

        //Verifica se há permissões para escrita de arquivos
        if (ContextCompat.checkSelfPermission(getBaseContext(),
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.CAMERA)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.CAMERA}, PERMISSAO_REQUEST);
            }
        }
    }
    private void tirarFoto() {
        Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (it.resolveActivity(getPackageManager()) != null) {

            File photoFile = null;
            try {
                permissoesAcesso();
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            if (photoFile != null) {

                photoURI = getUriForFile(getBaseContext(),
                        getBaseContext()
                                .getApplicationContext().getPackageName()
                                + ".fileprovider", photoFile);

                it.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

                startActivityForResult(it, REQUEST_TAKE_PHOTO);
            }
        }
    }

    //Método para inclusão de imagem na galeria
    private void galleryAddPic() {
        //File f = new File(currentPhotoPath);
        sendBroadcast(
                new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                        photoURI));
    }

    //Método para acessar a galeria
    private void acessarGaleria() {
        Intent intentPegaFoto = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(intentPegaFoto, PEGA_FOTO);
    }

    //Método para definição das dimensões da imagem
    private void setPic() {
        // Obtendo as dimensões da imagem para a View
        int targetW = imageView.getWidth();
        int targetH = imageView.getHeight();

        // Obter as dimensões do Bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(currentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;
        // Determina como diminuir a escala da imagem
        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

        // Decodifica o arquivo de imagem para o Bitmap que preencherá a View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        // Cria o bitmap da imagem capturada
        bitmap = BitmapFactory.decodeFile(currentPhotoPath, bmOptions);

        // Apresenta a imagem na tela
        imageView.setImageBitmap(bitmap);
    }

    // Método que retorna o resultado da chamada da câmera pela Intent
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imageView = findViewById(R.id.fotoColab);
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            // Chamada do método de adição da imagem na galeria
            galleryAddPic();
            // Definição das dimensões da imagem
            setPic();
        } else if (requestCode == PEGA_FOTO && resultCode == RESULT_OK) {
            //Captura caminho da imagem selecionada
            Uri imagemSelecionada = data.getData();

            // declara um stream (seguimento de dados) para ler a imagem
            // recuperada do SD Card
            InputStream inputStream = null;

            // recuperando a sequencia de entrada, baseada no caminho (uri)
            // da imagem
            try {
                inputStream = getContentResolver().openInputStream(imagemSelecionada);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            // recuperando um bitmap do stream
            bitmap = BitmapFactory.decodeStream(inputStream);
            // Vínculo do objeto ImageView
            imageView = (AppCompatImageView) findViewById(R.id.fotoColab);
            // Reduz imagem e configura apresentação
            Bitmap bitmapReduzido = Bitmap.createScaledBitmap(bitmap, 1080, 1080, true);
            imageView.setImageBitmap(bitmapReduzido);
            imageView.setScaleType(AppCompatImageView.ScaleType.FIT_XY);
        }
    }

    private File createImageFile() throws IOException {
        textView = findViewById(R.id.textView);

        // Criando o nome da imagem
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";

        // CÓDIGO ANTIGO, PARA VERSÕES ANTERIORES AO ANDROIDX
        /* Define a galeria como caminho da imagem para armazenamento
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  *//* prefix *//*
                ".jpg",    *//* suffix *//*
                storageDir      *//* directory *//*
        );
        */

        // PÓS ANDROIDX
        // Define a galeria como caminho da imagem para armazenamento
        File imagePath = new File(getBaseContext().getExternalFilesDir(null),
                "images");
        File image = new File(imagePath, "default_image.jpg");

        // Salva um arquivo: caminho para utilização com ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        // Apresenta caminho salvo na tela
        textView.setText(currentPhotoPath);

        return image;
    }
}
