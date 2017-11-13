package com.minecraft.packer.widget;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by user on 9/22/2017.
 */

public class DataAdapterAsync extends AsyncTask<String, String, String> {

    private String arg_assetDir;
    private String arg_destinationDir;
    private Context context;
    private File dest_dir;
    public DataAdapterAsync(String arg_assetDir, String arg_destinationDir, Context context) {
        this.arg_assetDir = arg_assetDir;
        this.arg_destinationDir = arg_destinationDir;
        this.context = context;
    }

    public DataAdapterAsync() {

    }

    @Override
    public String doInBackground(String... params)  {
        dest_dir=null;
        //String sd_path = context.getApplicationInfo().dataDir;
        String sd_path = Environment.getExternalStorageDirectory().toString();
        String dest_dir_path = sd_path + addLeadingSlash(arg_destinationDir);
         dest_dir = new File(dest_dir_path);

        try {
            createDir(dest_dir);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // AssetManager asset_manager = context.getAssets();
        AssetManager asset_manager = context.getResources().getAssets();
        String[] files = new String[0];
        try {
            files = asset_manager.list(arg_assetDir);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < files.length; i++) {

            String abs_asset_file_path = addTrailingSlash(arg_assetDir) + files[i];
            String sub_files[] = new String[0];
            try {
                sub_files = asset_manager.list(abs_asset_file_path);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (sub_files.length == 0) {
                // It is a file
                String dest_file_path = addTrailingSlash(dest_dir_path) + files[i];
                try {
                    copyAssetFile(abs_asset_file_path, dest_file_path, context, true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                // It is a sub directory
                new DataAdapterAsync(abs_asset_file_path, addTrailingSlash(arg_destinationDir) + files[i], context);
                doInBackground();
            }
        }

        return dest_dir_path;

    }


    public void copyAssetFile(String assetFilePath, String destinationFilePath, Context context,Boolean mod) throws IOException {
        if(mod==true) {
            InputStream bin = context.getAssets().open(assetFilePath);
            BufferedInputStream in = new BufferedInputStream(bin);
            OutputStream bon = new FileOutputStream(destinationFilePath);
            BufferedOutputStream out = new BufferedOutputStream(bon);

            byte[] buf = new byte[2048];
            int len;
            while ((len = in.read(buf,0,2048))>=0)
                out.write(buf, 0, len);
            in.close();
            out.flush();
            out.close();
        }
        if(mod==false){
            InputStream bin =new FileInputStream(assetFilePath);
            BufferedInputStream in =new BufferedInputStream(bin);
            OutputStream bon = new FileOutputStream(destinationFilePath);
            BufferedOutputStream out = new BufferedOutputStream(bon);

            byte buf[] = new byte[2048];
            int len;
            while ((len = in.read(buf,0,2048))>=0)
                out.write(buf, 0, len);
            in.close();
            out.flush();
            out.close();
        }

    }

    public String addTrailingSlash(String path) {
        if (path.charAt(path.length() - 1) != '/') {
            path += "/";
        }
        return path;
    }

    public String addLeadingSlash(String path) {
        if (path.charAt(0) != '/') {
            path = "/" + path;
        }
        return path;
    }

    public void createDir(File dir) throws IOException {
        if (dir.exists()) {
            if (!dir.isDirectory()) {
                throw new IOException("Can't create directory, a file is in the way");
            }
        } else {
            dir.mkdirs();
            if (!dir.isDirectory()) {
                throw new IOException("Unable to create directory");
            }
        }
    }



    @Override
    protected void onPostExecute(String result) {
        Log.i("PackIntegrator" , result);

    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
}


