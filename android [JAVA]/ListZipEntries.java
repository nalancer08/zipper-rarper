import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ListZipEntries {
	
	public static void main(String[] args) throws IOException {
		
		String path = "C:/Users/saer6003/Downloads/AutoCDAR.zip";
		
		ZipInputStream zipIs = null;
		
		zipIs = new ZipInputStream( new FileInputStream(path));
		ZipEntry entry = null;
		
		while ( ( entry = zipIs.getNextEntry() ) != null ){
			
			if ( entry.isDirectory() ) {
				System.out.print("Directory: ");
			} else {
				System.out.print("File: ");
			}
			System.out.print( entry.getName() + "\n");
		}
		
		zipIs.close();
	}
	
	
}
