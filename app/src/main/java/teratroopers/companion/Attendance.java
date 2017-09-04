package teratroopers.companion;

import android.app.ProgressDialog;
import java.net.*;
import java.io.*;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Attendance extends AppCompatActivity {
public ProgressDialog dialog=null;
    public String uploadFilePath = "/storage/emulated/0/Download";
    public String uploadFileName = "mydata.txt";
   String upLoadServerUri = "http://192.168.0.3:8080/webapplication7/uploadservlet";
    int serverResponseCode=0;
    mydbhelper mydb;
    int sroll,eroll;
    Button disbutton;
    Button presbutton;
    Button absbutton;
    int present,absent;
    int total;
    String message;
    public Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        context=this;
        mydb =new mydbhelper(this);
        Bundle b = getIntent().getExtras();
        String cname = b.getString("name");
        //goToClass gtc=new goToClass(cname);
        getValues(cname);
        display();
        presentButton();
        absentButton();
    }

    public void getValues(String name) {

        disbutton = (Button) findViewById(R.id.rolldisplay);
        Cursor res = mydb.getcname(name);
        res.moveToNext();
        sroll = Integer.parseInt(res.getString(1));
        eroll = Integer.parseInt(res.getString(2));
    }
    public void display(){
        String number=Integer.toString(sroll);
        disbutton.setBackgroundColor(Color.BLUE);
        disbutton.setClickable(false);
        disbutton.setText(number);
    }

    //TODO store values in database and provide back button on snackbar
    //present button click upon attendance completion increases present count(check)

   public void presentButton(){
        total=(eroll-sroll)+1;
        presbutton=(Button)findViewById(R.id.present);
        presbutton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        present++;
                        message+=String.valueOf(sroll)+"=p\n";
                        if(sroll<eroll) {
                            sroll++;
                            display();
                        }
                        else{
                            disbutton.setText("Attendance Complete");
                            Snackbar.make(view,"Attendance Complete: \n"+present+"/"+total+"are present",Snackbar.LENGTH_LONG).show();
                        }

                    }
                }
        );
    }

    //TODO store values in database and provide back button on snackbar

    public void absentButton(){
        total=(eroll-sroll)+1;
        absbutton=(Button)findViewById(R.id.absent);
        absbutton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        absent++;
                        message+=String.valueOf(sroll)+"=a\n";
                        if(sroll<eroll) {
                            sroll++;
                            display();
                        }
                        else{
                            disbutton.setText("Attendance complete");
                            Snackbar.make(view,"Attendance Complete: \n"+present+"/"+total+" are present",Snackbar.LENGTH_LONG).show();
                        }

                    }
                }
        );
    }

        public void writeExternalStorage(View view)
        {
            String state;
            state= Environment.getExternalStorageState();
            if(Environment.MEDIA_MOUNTED.equals(state))
            {
                File Root=Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                File dir=new File(Root,"mydata.txt");
                String Message=message;
                try{
                    FileOutputStream fos=new FileOutputStream(dir);
                    fos.write(Message.getBytes());
                    fos.close();
                    Toast.makeText(context,"was written successfullu",Toast.LENGTH_LONG).show();
                }
                catch(Exception e)
                {
                    System.out.println(e);
                    Toast.makeText(context,dir.getAbsolutePath(),Toast.LENGTH_LONG).show();
                }

            }
            else if(Environment.MEDIA_MOUNTED_READ_ONLY.equals(state))
            {
                Toast.makeText(context,"sd read only found",Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(context,"sd card not found",Toast.LENGTH_LONG).show();
            }
        }
        public void fileupload(View view)
        {
            Button uploadButton;





            /****  File Path *****/


            uploadButton = (Button)findViewById(R.id.fileupload);



            /***** Php script path ******/

            uploadButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    dialog = ProgressDialog.show(context, "", "Uploading file...", true);

                    new Thread(new Runnable() {
                        public void run() {
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    //messageText.setText("uploading started.....");
                                }
                            });

                            uploadFile(uploadFilePath + "/" + uploadFileName);

                        }
                    }).start();
                }
            });
        }

    public int uploadFile(String sourceFileUri) {


        String fileName = sourceFileUri;

        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "***";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        File sourceFile = new File(sourceFileUri);

        if (!sourceFile.isFile()) {

            dialog.dismiss();

            Log.e("uploadFile", "Source File not exist :"
                    +uploadFilePath + "" + uploadFileName);

            runOnUiThread(new Runnable() {
                public void run() {

                }
            });

            return 0;

        }
        else
        {
            try {

                // open a URL connection to the Servlet
                FileInputStream fileInputStream = new FileInputStream(sourceFile);
                URL url = new URL(upLoadServerUri);

                // Open a HTTP  connection to  the URL
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true); // Allow Inputs
                conn.setDoOutput(true); // Allow Outputs
                conn.setUseCaches(false); // Don't use a Cached Copy
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                conn.setRequestProperty("uploaded_file", fileName);

                dos = new DataOutputStream(conn.getOutputStream());

                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=uploaded_file;filename="
                + fileName  + lineEnd);

                dos.writeBytes(lineEnd);

                // create a buffer of  maximum size
                bytesAvailable = fileInputStream.available();

                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                buffer = new byte[bufferSize];

                // read file and write it into form...
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                while (bytesRead > 0) {

                    dos.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                }

                // send multipart form data necesssary after file data...
                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                // Responses from the server (code and message)
                serverResponseCode = conn.getResponseCode();
                String serverResponseMessage = conn.getResponseMessage();

                Log.i("uploadFile", "HTTP Response is : "
                        + serverResponseMessage + ": " + serverResponseCode);

                if(serverResponseCode == 200){

                    runOnUiThread(new Runnable() {
                        public void run() {

                            String msg = "File Upload Completed.\n\n See uploaded file here : \n\n"
                                    +" http://www.androidexample.com/media/uploads/"
                                    +uploadFileName;

                            Toast.makeText(context, "File Upload Complete.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                //close the streams //
                fileInputStream.close();
                dos.flush();
                dos.close();

            } catch (MalformedURLException ex) {

                dialog.dismiss();
                ex.printStackTrace();

                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(context, "MalformedURLException",
                                Toast.LENGTH_SHORT).show();
                    }
                });

                Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
            } catch (Exception e) {
                final String name=e.toString();


                dialog.dismiss();
                e.printStackTrace();

                runOnUiThread(new Runnable() {
                    public void run() {

                        Toast.makeText(context,name,
                                Toast.LENGTH_SHORT).show();
                    }
                });
                Log.e("Upload ", "Exception : "
                        + e.getMessage(), e);
            }
            dialog.dismiss();
            return serverResponseCode;

        } // End else block
    }
}


