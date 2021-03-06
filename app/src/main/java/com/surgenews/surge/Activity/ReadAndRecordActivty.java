package com.surgenews.surge.Activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.Image;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.surgenews.surge.Controller.WebserviceClient;
import com.surgenews.surge.Controller.WebserviceInterface;
import com.surgenews.surge.Model.SignupResponse;
import com.surgenews.surge.Model.UploadResponse;
import com.surgenews.surge.R;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by anishhegde on 10/09/16.
 */
public class ReadAndRecordActivty extends AppCompatActivity implements View.OnClickListener{

    WebView mWebview;
    private ImageView mBtStart;
    //private Button mBtPause;
    private ImageView mBtStop;
    private MediaRecorder myAudioRecorder;
    private static final int REQUEST_CODE_PERMISSIONS = 0x1;
    private String mFileName;

    private RecordState mState = RecordState.Default;

    private enum RecordState{
        Default,
        Started,
        //Paused,
        Stopped
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.read_record_activity);
        String url = getIntent().getStringExtra("URL");
        setupButtons();

        mWebview = (WebView) findViewById(R.id.wv_news);
        mWebview.getSettings().setJavaScriptEnabled(true);
        mWebview.loadUrl(url);
        final Context mContext = this;
        mWebview.setWebViewClient(new AppWebViewClients(this));
        setUpRecorder();
    }

    public class AppWebViewClients extends WebViewClient {
        private ProgressDialog progressDialog;
        private Context mContext;

        public AppWebViewClients(Context context) {
            mContext = context;
            progressDialog = new ProgressDialog(mContext);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);
            if(progressDialog.isShowing()){
                progressDialog.dismiss();
            }
        }
    }

    void setupButtons(){
        mBtStart = (ImageView) findViewById(R.id.bt_record);
       // mBtPause = (Button) findViewById(R.id.bt_pause);
        mBtStop = (ImageView) findViewById(R.id.bt_stop);
        mBtStart.setOnClickListener(this);
       // mBtPause.setOnClickListener(this);
        mBtStop.setOnClickListener(this);
        setUpButtonVisibility();
    }

    void setUpButtonVisibility(){
        switch (mState){
            case Default:
               // mBtPause.setActivated(false);
                mBtStop.setActivated(false);
                mBtStart.setActivated(true);
                break;
            case Started:
               // mBtPause.setActivated(true);
                mBtStop.setActivated(false);
                mBtStart.setActivated(false);
                mBtStart.setBackgroundColor(Color.parseColor("#ff0000"));
                break;
            //case Paused:
                //mBtPause.setActivated(false);
                //mBtStop.setActivated(true);
                //mBtStart.setActivated(true);
                //break;
            case Stopped:
               // mBtPause.setActivated(false);
                mBtStop.setActivated(false);
                mBtStart.setActivated(false);
                mBtStart.setBackgroundColor(Color.parseColor("#ff0000ff"));
                break;
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.bt_record:
                tryStart();
                break;
           // case R.id.bt_pause:
             //   pause();
               // break;
            case R.id.bt_stop:
                stopRecord();
                break;
            default:
                break;
        }
    }

    void setUpRecorder(){
        myAudioRecorder=new MediaRecorder();
        myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
    }

    private String getNextFileName() {
        String path =  Environment.getExternalStorageDirectory()
                .getAbsolutePath()
                + File.separator
                + "Record_"
                + System.currentTimeMillis()
                + ".3gp";
        Log.d("path",path);
        mFileName = path;
        return path;
    }

    void startRecord(){
        myAudioRecorder.setOutputFile(getNextFileName());
        try {
            myAudioRecorder.prepare();
            myAudioRecorder.start();
            toast("Recording Started");

            mState = RecordState.Started;
            setUpButtonVisibility();
        }

        catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    void pause(){

    }

    void stopRecord(){
        myAudioRecorder.stop();
        myAudioRecorder.release();
        myAudioRecorder  = null;
        toast("Stopped Recording");

        mState = RecordState.Stopped;
        setUpButtonVisibility();

        WebserviceInterface webserviceInterface = WebserviceClient.getClient().create(WebserviceInterface.class);

        File file = new File(mFileName);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("filename", file.getName(), RequestBody.create(MediaType.parse("audio/*"), file));

        Call<UploadResponse> call = webserviceInterface.uploadAttachment(filePart);
        call.enqueue(new Callback<UploadResponse>() {
            @Override
            public void onResponse(Call<UploadResponse> call, Response<UploadResponse> response) {
                Toast.makeText(getApplicationContext(),"Uploaded",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<UploadResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Uploaded error",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void tryStart() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            final int checkAudio = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);
            final int checkStorage = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (checkAudio != PackageManager.PERMISSION_GRANTED || checkStorage != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECORD_AUDIO)) {
                    showNeedPermissionsMessage();
                } else if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    showNeedPermissionsMessage();
                } else {
                    requestPermissions(new String[]{
                                    Manifest.permission.RECORD_AUDIO,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            REQUEST_CODE_PERMISSIONS);
                }
            } else {
                startRecord();
            }
        } else {
            startRecord();
        }
    }

    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull final String[] permissions, @NonNull final int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_PERMISSIONS:
                boolean userAllowed = true;
                for (final int result : grantResults) {
                    userAllowed &= result == PackageManager.PERMISSION_GRANTED;
                }
                if (userAllowed) {
                    startRecord();
                } else {
                    /*
                     * Cannot show dialog from here
                     * https://code.google.com/p/android-developer-preview/issues/detail?id=2823
                     */
                    showNeedPermissionsMessage();
                }
                break;
            default:
                break;
        }
    }

    private void showNeedPermissionsMessage() {
        message(getString(R.string.error_no_permissions));
    }

    private void message(String message) {
        final View root = findViewById(R.id.root_layout_rra);
        if (root != null) {
            final Snackbar snackbar = Snackbar.make(root, message, Snackbar.LENGTH_INDEFINITE);
            snackbar.setAction(android.R.string.ok, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    snackbar.dismiss();
                }
            });
            snackbar.show();
        }
    }

    void toast(String msg){
        Toast.makeText(ReadAndRecordActivty.this, ""+ msg, Toast.LENGTH_SHORT).show();
    }

}
