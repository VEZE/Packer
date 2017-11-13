package com.minecraft.packer.widget;

import android.app.Activity;


/**
 * Created by user on 9/7/2017.
 */

public class DownloadAdapter extends Activity {
      /*  private ProgressDialog pDialog;
        DialogUtils myDialog1= new DialogUtils();
      //  public ProgressDialog getMyDialog() {
      //  return myDialog1.showProgressDialog(this);
  //  }
    public ProgressDialog dismissMyDialog(){
        return myDialog1.dismiss();
    }

    public void CreateDownload(String url,Context context){
        new DownloadFileFromURL().execute(url);
        myDialog1.showProgressDialog(context);

    }

    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    *//**
     * Checks if the app has permission to write to device storage
     *
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * @param activity
     *//*
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }



    class DownloadFileFromURL extends AsyncTask<String, String, String> {

        *//**
         * Before starting background thread
         * *//*
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            System.out.println("Starting download");

           // getMyDialog();
            //pDialog = new ProgressDialog();
          //  pDialog.setMessage("Loading... Please wait...");
          //  pDialog.setIndeterminate(false);
          //  pDialog.setCancelable(false);
          //  pDialog.show();




        }





        *//**
         * Downloading file in background thread
         * *//*
        @Override
        protected String doInBackground(String... f_url) {
            int count;
            try {
                String root = Environment.getExternalStorageDirectory().toString();
                long delayInMillis = 2000;
                System.out.println("Downloading");
                URL url = new URL(f_url[0]);

                URLConnection conection = url.openConnection();
                conection.connect();
                // getting file length
                int lenghtOfFile = conection.getContentLength();

                // input stream to read file - with 8k buffer
                InputStream input = new BufferedInputStream(url.openStream(), 8192);

                // Output stream to write file

                OutputStream output = new FileOutputStream(root+"/Download/downloadedfile.jpg");
                byte data[] = new byte[1024];

                long total = 0;
                while ((count = input.read(data)) != -1) {
                    total += count;

                    // writing data to file
                    output.write(data, 0, count);

                }

                // flushing output
                output.flush();

                // closing streams
                output.close();
                input.close();

            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }

            return null;
        }



        *//**
         * After completing background task
         * **//*
        @Override
        protected void onPostExecute(String file_url) {
            System.out.println("Downloaded");

            dismissMyDialog();
        }

    }*/
}
