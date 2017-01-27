import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Zipper {
	
	private String path;
	
	public Zipper(String path) {
		
		ZipInputStream zipIs = null;
		
		this.setPath(path);
		
		try {
			zipIs = new ZipInputStream( new FileInputStream(path) );
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ZipEntry entry = null;
		
		try {
			while ( ( entry = zipIs.getNextEntry() ) != null ){
				
				if ( entry.isDirectory() ) {
					System.out.print("Directory: ");
				} else {
					System.out.print("File: ");
				}
				System.out.print( entry.getName() + "\n");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			zipIs.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void unZip(String destination) {
		
		
		// Create Output folder if it does not exists
        File folder = new File(destination);
        if (!folder.exists()) {
            folder.mkdirs();
        }
  
        // Create buffer
        byte[] buffer = new byte[1024];
 
        ZipInputStream zipIs = null;
        try {
   
            // Create ZipInputStream read a file from path.
            zipIs = new ZipInputStream(new FileInputStream(this.path));
 
            ZipEntry entry = null;
       
            // Read ever Entry (From top to bottom until the end)
            while ((entry = zipIs.getNextEntry()) != null) {
                String entryName = entry.getName();
                String outFileName = destination + File.separator + entryName;
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
	
	public void zip(String destination, String name) {
		
		File inputDir = new File(destination);
        File outputZipFile = new File(destination + name + ".zip");
		
		// Create parent directory for output file
        outputZipFile.getParentFile().mkdirs();
 
        String inputDirPath = inputDir.getAbsolutePath();
        byte[] buffer = new byte[1024];
 
        FileOutputStream fileOs = null;
        ZipOutputStream zipOs = null;
        try {
            List<File> allFiles = this.listChildFiles(inputDir);
            // Now zip files one by one
            // Create ZipOutputStream to write to the zip file
            fileOs = new FileOutputStream(outputZipFile);
            //
            zipOs = new ZipOutputStream(fileOs);
            for (File file : allFiles) {
                String filePath = file.getAbsolutePath();
 
                System.out.println("Zipping " + filePath);
                // For ZipEntry we need to keep only relative file path, so we
                // used substring on absolute path
                String entryName = filePath
                        .substring(inputDirPath.length() + 1);
 
                ZipEntry ze = new ZipEntry(entryName);
                // Put new entry into zip file.
                zipOs.putNextEntry(ze);
                // Read the file and write to ZipOutputStream
                FileInputStream fileIs = new FileInputStream(filePath);
 
                int len;
                while ((len = fileIs.read(buffer)) > 0) {
                    zipOs.write(buffer, 0, len);
                }
                fileIs.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeQuite(zipOs);
            closeQuite(fileOs);
        }
 
    }
 
    private void closeQuite(OutputStream out) {
        try {
            out.close();
        } catch (Exception e) {
        }
    }
 
    // This method returns the list of files, including the subfolder of the input folder.
    private List<File> listChildFiles(File dir) throws IOException {
        List<File> allFiles = new ArrayList<File>();
  
        // Children files
        File[] childFiles = dir.listFiles();
        for (File file : childFiles) {
            if (file.isFile()) {
                allFiles.add(file);
            } else {
                // recursively.
                List<File> files = this.listChildFiles(file);
                allFiles.addAll(files);
            }
        }
        return allFiles;
    }
	
	public void setPath(String path) {
		this.path = path;
	}
	
	public String getPath() {
		return this.path;
	}
	
}
