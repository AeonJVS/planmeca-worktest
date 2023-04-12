package PlanmecaWorktestGradle;
import java.io.*;
import java.lang.Math;
import java.util.*;

public class main {

	// removes noise from image
	public static List<Integer> denoiser(List<Integer> image, List<Integer> noise) {
		List<Integer> retVal = new ArrayList<>();
		for (int x = 0; x < image.size(); x++)
			retVal.add(image.get(x) - noise.get(x));
		return retVal;
	}
	
	// removes defective pixels from image utilising the immediate nearby pixels
	public static List<Integer> imputator(List<Integer> image, List<Integer> pixelMask) {
		List<Integer> retVal = new ArrayList<>();
		for (int x = 0; x < image.size(); x++) {
			if (pixelMask.get(x) == 0) {
				retVal.add(image.get(x));
			} else {
				int newPixel = 0;
				int column = x % 896;
				int row = column * 896;
				for (int i = Math.max(column - 1, 0); i < Math.min(column + 1, 896); i++) {
					for (int j = Math.max(row - 1, 0); j < Math.min(row + 1, 1088); j++) {
						if (j != row && i != column)
							newPixel += image.get(column * 896 + i);
					}
				}
				retVal.add(newPixel / 8);
			}
		}
		return retVal;
	}
	
	public static byte[] renderByteData(List<Integer> refinedPixels) {
		byte[] retVal = new byte[refinedPixels.size() * 2];
		
		for (int x = 0; x < refinedPixels.size(); x++) {
			
			int pixel = refinedPixels.get(x);
			
			// left side
			byte leftPart = (byte)((0xff00 & pixel) >> 8);
			retVal[x * 2] = leftPart;
			
			// right side
			byte rightPart = (byte)(0xff & pixel);
			retVal[x * 2 + 1] = rightPart;
		}
		return retVal;
	}
	
	public static void saveToFile(byte[] rawData, String saveFile) {
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(saveFile);
			fileOutputStream.write(rawData);
		    fileOutputStream.close();
		    System.out.println("Byte array successfully saved to file");
		} catch (IOException e) {
		    System.out.println("Error saving to file: " + e.getMessage());
		}
	}
	
	public static List<Integer> byteCruncher(String filename) {
		File rawFile = new File(filename);
		byte[] rawFileData = new byte[(int)rawFile.length()];
		
		try {
            FileInputStream fileInputStream = new FileInputStream(rawFile);
            fileInputStream.read(rawFileData);
            fileInputStream.close();
        } catch (Exception e) {
            System.out.println("Error reading raw image file: " + e.getMessage());
        }
		
		List<Integer> variable = new ArrayList<>();
		
		for (int x = 0; x < rawFileData.length / 2; x++) {
		    byte left = rawFileData[x * 2];
		    byte right = rawFileData[x * 2 + 1];
		    int correct = ((int)left << 8) | ((int)right & 0xFF);
		    variable.add(correct);
		}

		System.out.println("Original bytes: " + 
				String.format("\t%8s", Integer.toBinaryString(rawFileData[0])) + 
				String.format("%8s", Integer.toBinaryString(rawFileData[1])).replace(" ", "0")
		);
		System.out.println("Parsed bytes: \t" + Integer.toBinaryString(variable.get(0)));
		
		return variable;
	}
	
	public static void main(String[] args) {
		List<Integer> xRay = byteCruncher("./src/main/resources/1_xray_frame_896x1088.raw");
		List<Integer> noise = byteCruncher("./src/main/resources/2_offset_data_896x1088.raw");
		List<Integer> defects = byteCruncher("./src/main/resources/3_defect_map_896x1088.raw");
		
		List<Integer> denoisedImage = denoiser(xRay, noise);
		
		List<Integer> imputedImage = imputator(denoisedImage, defects);
		byte[] imputedImageInBytes = renderByteData(imputedImage);
		saveToFile(imputedImageInBytes, "imputedDenoise.raw");
	}
}
