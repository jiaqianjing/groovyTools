import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileUtils {

	public static BufferedReader reader(String filePath) {
		FileInputStream fis;
		InputStreamReader isr;
		BufferedReader br = null;
		try {
			fis = new FileInputStream(new File(filePath));
			isr = new InputStreamReader(fis, "utf-8");
			br = new BufferedReader(isr);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return br;
	}
	public static BufferedWriter writer(String filePath) {
		FileOutputStream fos;
		OutputStreamWriter osw;
		BufferedWriter bw = null;
		try {
			fos = new FileOutputStream(new File(filePath));
			osw = new OutputStreamWriter(fos, "utf-8");
			bw = new BufferedWriter(osw);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return bw;
	}

	/**
	 * @param file
	 */
	public  static void mkDir(File file) {
		if (file.getParentFile().exists()) {
			file.mkdir();
		} else {
			mkDir(file.getParentFile());
			file.mkdir();
		}
	}

	public static byte[] toByteArray(String filename) throws IOException {

		File f = new File(filename);
		if(!f.exists()){
			throw new FileNotFoundException(filename);
		}

		ByteArrayOutputStream bos = new ByteArrayOutputStream((int)f.length());
		BufferedInputStream in = null;
		try{
			in = new BufferedInputStream(new FileInputStream(f));
			int buf_size = 1024;
			byte[] buffer = new byte[buf_size];
			int len = 0;
			while(-1 != (len = in.read(buffer,0,buf_size))){
				bos.write(buffer,0,len);
			}
			return bos.toByteArray();
		}catch (IOException e) {
			e.printStackTrace();
			throw e;
		}finally{
			try{
				in.close();
			}catch (IOException e) {
				e.printStackTrace();
			}
			bos.close();
		}
	}

    public static List<String> getFileList(File file) {
        Pattern pattern = Pattern.compile(".*[0-9]\\.scp\\.tmp$");
        List<String> result = new ArrayList<String>();
        if (!file.isDirectory()) {
            System.out.println(file.getAbsolutePath());
            result.add(file.getAbsolutePath());
        } else {
            File[] directoryList = file.listFiles(new FileFilter() {
                public boolean accept(File file) {
                    Matcher matcher =pattern.matcher(file.getName());
                    if (file.isFile() && matcher.matches()) {
                        return true;
                    } else {
                        return false;
                    }
                }
            });
            for (int i = 0; i < directoryList.length; i++) {
                result.add(directoryList[i].getPath());
            }
        }

        return result;
    }

}
