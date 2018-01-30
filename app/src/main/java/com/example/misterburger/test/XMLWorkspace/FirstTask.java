package com.example.misterburger.test.XMLWorkspace;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.misterburger.test.RealmModule.RealmImpl;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FirstTask {
    private String API_BASE_URL = "http://ainsoft.pro";
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private Context context;
    private RealmImpl realm;


    public FirstTask(Context context, RealmImpl realm) {
        this.context = context;
        this.realm = realm;
    }

    private void getProducts() {

        final Retrofit retrofit = new Retrofit.Builder().
                baseUrl(API_BASE_URL).
                build();

        ProductAPI downloadService = retrofit.create(ProductAPI.class);

        Call<ResponseBody> call = downloadService.downloadFileWithFixedUrl();

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Log.d("TAG", "server contacted and has file");

                    boolean writtenToDisk = writeResponseBodyToDisk(response.body());

                    Log.d("TAG", "file download was a success? " + writtenToDisk);
                } else {
                    Log.d("TAG", "server contact failed");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("TAG", "error");
            }
        });
    }

    private boolean writeResponseBodyToDisk(ResponseBody body) {
        try {
            File futureStudioIconFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsoluteFile(), "test.xml");

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(futureStudioIconFile);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;

                    parseXml(getStringFromXml(futureStudioIconFile));

                    Log.d("TAG", "file download: " + fileSizeDownloaded + " of " + fileSize);


                }
                outputStream.flush();

                return true;
            } catch (IOException e) {
                Log.d("TAG", e.toString());
                Toast toast = Toast.makeText(context,
                        "Download failed", Toast.LENGTH_SHORT);
                toast.show();
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }

    public void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
            verifyStoragePermissions(activity);
        } else {
            getProducts();
        }
    }

    private void parseXml(String xml) {
        if (xml != null) {
            try {

                DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                InputSource is = new InputSource();
                is.setCharacterStream(new StringReader(xml));
                Document doc = db.parse(is);
                NodeList nodes = doc.getElementsByTagName("product");

                for (int i = 0; i < nodes.getLength(); i++) {
                    Element element = (Element) nodes.item(i);
                    NodeList name = element.getElementsByTagName("name");
                    NodeList id = element.getElementsByTagName("id");
                    NodeList price = element.getElementsByTagName("price");
                    Element nameLine = (Element) name.item(0);
                    Element idLine = (Element) id.item(0);
                    Element priceLine = (Element) price.item(0);
                    Product product = new Product();
                    product.setId(Integer.parseInt(getCharacterDataFromElement(idLine)));
                    product.setName(getCharacterDataFromElement(nameLine));
                    product.setPrice(Double.parseDouble(getCharacterDataFromElement(priceLine)));
                    if(product.getId() != 0){
                        realm.saveProduct(context, product);
                        Log.d("TAG", String.valueOf(product.getId()));
                        Log.d("TAG", String.valueOf(product.getPrice()));
                    }
                }
                Toast toast = Toast.makeText(context,
                        "Done", Toast.LENGTH_SHORT);
                toast.show();

            } catch (Exception e) {
                Log.v("TAG", e.toString());
            }
        } else {
            Toast toast = Toast.makeText(context,
                    "Smtn wrong with xml", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private String getStringFromXml(File file) {
        try {
            InputStream is = new FileInputStream(file.getPath());
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new InputSource(is));
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer t = tf.newTransformer();
            StringWriter sw = new StringWriter();
            t.transform(new DOMSource(doc), new StreamResult(sw));
            return String.valueOf(sw);
        } catch (Exception e) {
            Log.v("TEG", e.toString());
            return null;
        }
    }

    private static String getCharacterDataFromElement(Element e) {
        Node child = e.getFirstChild();
        if (child instanceof CharacterData) {
            CharacterData cd = (CharacterData) child;
            return cd.getData();
        }
        return "";
    }
}


