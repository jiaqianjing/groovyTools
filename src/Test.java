import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/9/29.
 */
public class Test {

    static final String OUTPUT_BASEFOLDRE = "E:/oschina/littleTools/output/ret_dir";

    public static void main(String[] args) {
        List<String> list = FileUtils.getFileList(new File("E:/oschina/littleTools/input/scp_train"));
        Map<String, UserInfo> map = new HashMap<String, UserInfo>();

        String reg = "v[0-9]{5,10}";
        Pattern pattern = Pattern.compile(reg);

        for (String s : list) {
            System.out.println(s);
            UserInfo userInfo = new UserInfo();
            Matcher matcher = pattern.matcher(s);
            while (matcher.find()) {
                userInfo.setUid(matcher.group(0));
            }

            FileInputStream fis = null;
            InputStreamReader isr = null;
            BufferedReader br = null;
            try {
                fis = new FileInputStream(new File(s));
                isr = new InputStreamReader(fis);
                br = new BufferedReader(isr);
                String line = "";
                String[] arrys = null;
                while ((line = br.readLine()) != null) {
                    if ("speex".equals(line)) continue;
                    arrys = line.split("\t");
                    userInfo.setSid(arrys[0]);
                    userInfo.setAudioPath(arrys[1].split("\\[")[0]);
                    userInfo.setStartIndex(arrys[1].split("\\[")[1].split(",")[0]);
                    userInfo.setOffset(arrys[1].split("\\[")[1].split(",")[1].split("\\]")[0]);

                    System.out.println(userInfo);

                    // 写入操作  /${uid}/${sid}.data
                    String path = OUTPUT_BASEFOLDRE + "/" + userInfo.getUid();
                    String fout = path + "/" + userInfo.getSid() + ".data";
                    System.out.println("path:" + path);
                    File outPath = new File(path);
                    if (!outPath.exists() && !outPath.isDirectory()) {
                        System.out.println("文件夹不存在，创建文件夹");
                        FileUtils.mkDir(outPath);
                    }

                    byte[] audio = FileUtils.toByteArray(userInfo.getAudioPath());

                    OutputStream output = new FileOutputStream(new File(fout));

                    output.write(audio, Integer.parseInt(userInfo.getStartIndex()), Integer.parseInt(userInfo.getOffset())-Integer.parseInt(userInfo.getStartIndex()));
                    output.close();
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    public static void testMyGroovy() {
        try {
            GroovyCommonUtil.invokeMethod("readscp.groovy", "test");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
