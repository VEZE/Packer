package com.minecraft.packer.widget;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.io.FileUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by user on 9/14/2017.
 */

public class UnzipFileAsync extends AsyncTask<String, String, String> {

    private File inputZipFile;
    private File unzipDir;

    public UnzipFileAsync(File inputZipFile, File unzipDir) {
        this.inputZipFile = inputZipFile;
        this.unzipDir = unzipDir;
    }

    @Override
    protected String doInBackground(String... params) {

        Log.v("Decompress","Unzipping " + inputZipFile + " into " + unzipDir);
        ArchiveInputStream in = null;
        try {
            final InputStream is = new FileInputStream(inputZipFile);
            final BufferedInputStream bis = new BufferedInputStream(is);
            in = new ArchiveStreamFactory().createArchiveInputStream("zip", bis);
            ZipArchiveEntry entry;
            while( (entry = (ZipArchiveEntry) in.getNextEntry()) != null) {
                File currentFile = new File(unzipDir, entry.getName());
                if(entry.isDirectory()) {
                    FileUtils.forceMkdir(currentFile);
                } else {
                    //create parent dir if needed
                    File parent = currentFile.getParentFile();
                    if( ! parent.exists()) {
                        FileUtils.forceMkdir(parent);
                    }

                    OutputStream out = new FileOutputStream(currentFile);
                    BufferedOutputStream bout = new BufferedOutputStream(out);

                    IOUtils.copy(in, bout,1024);
                    bout.flush();
                    out.flush();
                    IOUtils.closeQuietly(out);
                    IOUtils.closeQuietly(bout);
                }
            }
            Log.v("Decompress","Unzipping file  " + unzipDir);

        } catch (Exception e) {
        } finally {
            IOUtils.closeQuietly(in);

        }



        return null;
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
