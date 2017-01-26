import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class UnZip {
	
	public static void main ( String[] args ) {
		
		final String OUTPUT_FOLDER = "C:/Users/saer6003/Documents/output";
        String FILE_PATH = "C:/Users/saer6003/Downloads/clipboard.js-master.zip";
  
        // Create Output folder if it does not exists
        File folder = new File(OUTPUT_FOLDER);
        if (!folder.exists()) {
            folder.mkdirs();
        }
  
        // Create buffer
        byte[] buffer = new byte[1024];
 
        ZipInputStream zipIs = null;
        try {
   
            // Create ZipInputStream read a file from path.
            zipIs = new ZipInputStream(new FileInputStream(FILE_PATH));
 
            ZipEntry entry = null;
       
            // Read ever Entry (From top to bottom until the end)
            while ((entry = zipIs.getNextEntry()) != null) {
                String entryName = entry.getName();
                String outFileName = OUTPUT_FOLDER + File.separator + entryName;
                System.out.println("Unzip: " + outFileName);
 
                if (entry.isDirectory()) {
       
                    // Make directories
                    new File(outFileName).mkdirs();
                } else {
             
                    // Create Stream to write file.
                    FileOutputStream fos = new FileOutputStream(outFileName);
 
                    int len;
          
                    // Read the data on the current entry.
                    while ((len = zipIs.read(buffer)) > 0) {
                        fos.write(buffer, 0, len);
                    }
 
                    fos.close();
                }
 
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                zipIs.close();
            } catch (Exception e) {
            }
        }
	}

}
