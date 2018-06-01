import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class PanelInfo {
	private File file;
	private Mode mode;

	private String originalFileContent;
	private String fileContentBuffer;
	private boolean dirty;
	
	private ArrayList <String> fileContentList;
	
	private FileReader fr;
	private FileWriter fw;
	private BufferedReader br;
	private BufferedWriter bw;
	
	
	
	public PanelInfo() {
		file = null;
		mode = Mode.VIEW;
		
		originalFileContent = null;
		fileContentBuffer = null;
		dirty = false;
		
		fileContentList = null;
		
		fr = null;
		fw = null;
 		br = null;
 		bw = null;
	}
	
	/**
	 * opens the file in the corresponding file path and sets the file of PanelInfo.
	 * @return true if success, false if fail
	 */
	public boolean load(String filePath) {
		if (file != null) {
			// PanelView���� �갡 return false�ϸ�, save ���� �ϰ� �ٽ� �õ��ϰ� �ؾ� ��
			if (dirty) {
				System.out.println("The file has been modified. Please save the file before opening another one.");
				return false;
			}

			closeFile();
		}

		try {
			fr = new FileReader(filePath);
			br = new BufferedReader(fr);

			originalFileContent = new String();
			String s = null;

			while ((s = br.readLine()) != null) {
				originalFileContent += s;
				originalFileContent += "\r\n";
			}

			file = new File(filePath);

			
			System.out.println(getFilePath());

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			if (br != null)	try { br.close(); } catch (IOException e) {}
			if (fr != null)	try { fr.close(); } catch (IOException e) {}
		}

		System.out.println(this.fileContentBuffer);
		
		fileContentBuffer = new String(originalFileContent);
		dirty = false;
		return true;
	}

	/**
	 * saves the file
	 * @return true if success, false if failure.
	 */
	public boolean save() {
		if (file == null) {
			System.out.println("No file exists.");
			return false;
		}
		String filePath = getFilePath();
		
		return this.saveAs(filePath);
	}

	/**
	 * save the file  w/ different file name
	 * @param newFilePath
	 * @return true if success, false if failure.
	 */
	public boolean saveAs(String newFilePath) {
		try {
			file = new File(newFilePath);

			fw = new FileWriter(newFilePath);
			bw = new BufferedWriter(fw);

			bw.write(fileContentBuffer);
			
			System.out.println("Saved");
		
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Failed to save file.");
			return false;
		} finally {
			if (bw != null)
				try { bw.close(); } catch (IOException e) {}
			if (fw != null)
				try { fw.close(); } catch (IOException e) {}
		}
		
		dirty = false;
		originalFileContent = new String(fileContentBuffer);
		return true;
	}
	

	/**
	 * operation when user closes the file
	 */
	public void closeFile() {
		file = null;
		mode = Mode.VIEW;
		
		originalFileContent = null;
		fileContentBuffer = null;
		dirty = false;
		
		fileContentList = null;
	}
	
	/**
	 * Checks if a file is open in the panel in order to save it before opening
	 * another file
	 * @return true if file is open, else return false
	 */
	public boolean fileIsOpen() {
		return file != null;
	}
	
	/* Getter & Setter */
	public String getFilePath() {
		if (file == null)
			return "";
		else 
			return file.getPath();
	}
	
	public String getFileName() {
		if (file == null)
			return "";
		else
			return file.getName();
	}
	
	/**
	 * @return current mode of text editor
	 */
	public Mode getMode() {
		return this.mode;
	}
	
	public void setMode(Mode mode) {
		this.mode = mode;
	}
	
	public String getOriginalFileContent() {
		return this.originalFileContent;
	}
	
	public void setFileContentBuffer(String fileContent) {
		fileContentBuffer = new String(fileContent);
	}
	
	public boolean isUpdated() {
		return dirty;
	}
	
	public void setUpdated(boolean flag) {
		this.dirty = flag;
	}
	
	public ArrayList<String> getFileContentBufferList() {
		return this.fileContentList;
	}
	
	@SuppressWarnings("unchecked")
	public void setFileContentList(ArrayList<String> fromMerge) {
		this.fileContentList = (ArrayList<String>)fromMerge.clone();
	}
	
	/* Private Functionss */
	private void enterCompareMode() {
		String[] fcArray = fileContentBuffer.split("\r\n");
		
		this.fileContentList = new ArrayList<String>(Arrays.asList(fcArray));
	}

	private void exitCompareMode() {
		fileContentBuffer = new String();
		
		for (int i = 0; i< fileContentList.size(); i++) {
			fileContentBuffer += this.fileContentList.get(i);
			fileContentBuffer += "\r\n";
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Test PanelInfo.java");
	}

}
