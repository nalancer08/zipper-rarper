package com.appbuilders.rarper;

import android.util.Log;

import com.github.junrar.Archive;
import com.github.junrar.exception.RarException;
import com.github.junrar.impl.FileVolumeManager;
import com.github.junrar.rarfile.FileHeader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by saer6003 on 26/01/2017.
 */

// Remember to add the mavenRepository
// -- mavenCentral()

// Remeber to add repository for gradle
// -- compile group: 'com.github.junrar', name: 'junrar', version: '0.7'

// Remeber to quit the lisence for JUNRAR
    /*
    * packagingOptions {
        exclude 'META-INF/NOTICE'
        exclude 'META-INF/LICENSE'
        exclude 'META-INF/DEPENDENCIES'
        exclude 'META-INF/LGPL2.1'
    }
    */

// Remeber to add the permission for android to write
// -- <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />

// Remember that right now the proccess dont generate the directory, you have to do it

public class Rarper {


    public Rarper() {

        String filename = "/storage/emulated/0/media/escuela.part1.rar";
        File f = new File(filename);
        Archive a = null;
        try {
            a = new Archive(new FileVolumeManager(f));
        } catch (RarException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (a != null) {
            a.getMainHeader().print();
            FileHeader fh = a.nextFileHeader();
            while (fh != null) {
                try {
                    File out = new File("/storage/emulated/0/media/esc/"
                            + fh.getFileNameString().trim());
                    Log.d("AB_DEV", out.getAbsolutePath());
                    FileOutputStream os = new FileOutputStream(out);
                    a.extractFile(fh, os);
                    os.close();
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (RarException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                fh = a.nextFileHeader();
            }
        }
    }

}
