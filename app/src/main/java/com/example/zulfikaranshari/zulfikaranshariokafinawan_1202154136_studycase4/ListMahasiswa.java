package com.example.zulfikaranshari.zulfikaranshariokafinawan_1202154136_studycase4;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;

public class ListMahasiswa extends AppCompatActivity {


    public ListView mList;
    private Button mButton;
    private ProgressBar mBar;
    private ItemList itemList;
    private String[] mahasiswa = {
            "Dina", "Rian", "Gina", "Osas", "Reno", "Lina", "Roi", "Shifa"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_mahasiswa);

        mList = findViewById(R.id.listView);
        mButton = findViewById(R.id.btnAsync);
        mBar = findViewById(R.id.bar);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, new ArrayList<String>());

        mList.setAdapter(adapter);
    }


   //Memulai-AsyncTask
    public void startAsync(View view) {
        itemList = new ItemList();
        itemList.execute();
    }


    public class ItemList extends AsyncTask<Void, String, Void> {
        //Set-variable-untuk-counter-Adapter-dan-PrigressDialog
        private ArrayAdapter<String> mAdapter;
        private int mCounter = 1;
        ProgressDialog mDialog = new ProgressDialog(ListMahasiswa.this);

        //Set-progress-dialog
        @Override
        protected void onPreExecute() {
            mAdapter = (ArrayAdapter<String>) mList.getAdapter();
            mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mDialog.setTitle("Please wait while the data is being loaded");

            mDialog.setProgress(0);
            mDialog.show();
        }
        //Menampilkan-data-di-array
        @Override
        protected Void doInBackground(Void... params) {
            for (String data : mahasiswa){
                publishProgress(data);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            mAdapter.add(values[0]);

            //menghitung persen
            Integer status = (int) ((mCounter / (float) mahasiswa.length) * 100);
            mBar.setProgress(status);

            //set statu
            mDialog.setProgress(status);

          
            mDialog.setMessage(String.valueOf(status + "%"));
            mCounter++;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            //menyembunyikan -progress-dialog
            mBar.setVisibility(View.GONE);

            //mwnghapus-progress-dialog
            mDialog.dismiss();
            mList.setVisibility(View.VISIBLE);
        }
    }
}
