import java.io.File;
import java.io.PrintWriter;

import com.drew.metadata.Metadata;
import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Tag;


public class CR2parse {

	private static File file;
	private Metadata metadata;
	
	
	
	public CR2parse(File file) {
		CR2parse.file = file;
		try {
			this.metadata = ImageMetadataReader.readMetadata(file);
			//print(metadata);
		}
		catch (Exception e){
			System.out.print(e);
		}
	}



	private static void print(Metadata metadata){
        System.out.println("-------------------------------------");
        try {
        	PrintWriter writer = new PrintWriter(file.getParent().toString() +"/" + file.getName().toString() + ".txt","UTF-8");
	        for (Directory directory : metadata.getDirectories()) {
	        		for (Tag tag : directory.getTags()) {
	        			writer.println(tag);
		            }
	            if (directory.hasErrors()) {
	                for (String error : directory.getErrors()) {
	                    System.err.println("ERROR: " + error);
	                }
	            }
	        }
	        writer.close();
	        System.out.println(file.getParent().toString() + file.getName().toString() + ".txt");
        }
        catch (Exception e){
        	System.out.println(e);
        }
    }
	
	public String getLensType(){
		
		for (Directory directory : this.metadata.getDirectories()) {
			for (Tag tag : directory.getTags()) {
				if (tag.toString().indexOf("Lens Specification")!= -1) {
					return tag.toString();
				}
			}
		}
		
		return "No lens found ";// + file.getAbsolutePath().toString();
	}
	
	public String getISO(){
		
		for (Directory directory : this.metadata.getDirectories()) {
			for (Tag tag : directory.getTags()) {
				if (tag.toString().indexOf("ISO Speed Ratings")!= -1) {
					return tag.toString();
				}
			}
		}
		
		return "0";
	}
	
	public String getExpTime(){
		
		for (Directory directory : this.metadata.getDirectories()) {
			for (Tag tag : directory.getTags()) {
				if (tag.toString().indexOf("Exposure Time ")!= -1) {
					String temp = tag.toString().replace("[Exif SubIFD] Exposure Time - ", "");
					temp = temp.replace(" sec", "");
					return temp;
				}
			}
		}
		
		return "";
	}
	
	public String getExpType(){
		
		for (Directory directory : this.metadata.getDirectories()) {
			for (Tag tag : directory.getTags()) {
				if (tag.toString().indexOf("Exif SubIFD] Exposure Program")!= -1) {
					return tag.toString().replace("[Exif SubIFD] Exposure Program - ", "");
				}
			}
		}
		
		return "";
	}
	
	public String getMetering(){
		
		for (Directory directory : this.metadata.getDirectories()) {
			for (Tag tag : directory.getTags()) {
				if (tag.toString().indexOf("[Exif SubIFD] Metering Mode")!= -1) {
					return tag.toString().replace("[Exif SubIFD] Metering Mode - ", "");
				}
			}
		}
		
		return "";
	}
	
	public String getFocalLength(){
		
		for (Directory directory : this.metadata.getDirectories()) {
			for (Tag tag : directory.getTags()) {
				if (tag.toString().indexOf("[Exif SubIFD] Focal Length")!= -1) {
					return tag.toString();
				}
			}
		}
		
		return "Focal length not found";
	}
	
}
