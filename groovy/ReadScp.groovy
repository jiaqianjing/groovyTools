import java.util.regex.Matcher

/**
 * Created by Administrator on 2016/9/29.
 */

def OUTPUT_BASEFOLDRE = "E:/oschina/littleTools/output/ret_dir";

def list = getFileList(new File("E:/oschina/littleTools/input/scp_train"));

def uidPattern = ~/v[0-9]{5,10}/ ;

for (String s : list) {
    println s;
    Matcher matcher = uidPattern.matcher(s);
    def String uid = "";
    while (matcher.find()) {
        uid = matcher.group(0);
    }

    FileInputStream fis = null;
    InputStreamReader isr = null;
    BufferedReader br = null;
    try {
        fis = new FileInputStream(new File(s));
        isr = new InputStreamReader(fis);
        br = new BufferedReader(isr);
        def String line;
        def arrys ;
        def count = 0;
        while ((line = br.readLine()) != null) {
            if(count == 101) break;
            if ("speex".equals(line)) continue;
            arrys = line.split("\t");
            def sid = arrys[0];
            def audioPath = arrys[1].split("\\[")[0];
            def startIndex = arrys[1].split("\\[")[1].split(",")[0];
            def offset = arrys[1].split("\\[")[1].split(",")[1].split("\\]")[0];

            println uid;
            println sid;
            println audioPath;
            println startIndex;
            println offset;

            // 写入操作  /${uid}/${sid}.data
            String path = OUTPUT_BASEFOLDRE + "/" + uid;
            String fout = path + "/" + sid + ".data";

            println("path:" + path);

            File outPath = new File(path);
            if (!outPath.exists() && !outPath.isDirectory()) {
               println("文件夹不存在，创建文件夹");
                mkDir(outPath);
            }

            def audio = new File(audioPath).readBytes()
            println "audio.length:"+audio.length

            OutputStream output = new FileOutputStream(new File(fout));

            output.write(audio, Integer.parseInt(startIndex), Integer.parseInt(offset)-Integer.parseInt(startIndex));
            output.close();

            new File(OUTPUT_BASEFOLDRE+"/"+uid+"/"+uid+".scp").write(getRandomSid(new Date().getTime())+"\t"+audioPath+"["+startIndex+","+"offset"+"]"+"\n");
            count++;
        }

    } catch (FileNotFoundException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }

}

/**
 * 去取传入的目录下的所有 uid.scp.tmp 文件
 * @param file
 * @return
 */
def getFileList(File file) {
    def pattern = ~/.*[0-9]\.scp\.tmp\u0024/
    List<String> result = new ArrayList<String>();
    if (!file.isDirectory()) {
        System.out.println(file.getAbsolutePath());
        result.add(file.getAbsolutePath());
    } else {
        File[] directoryList = file.listFiles(new FileFilter() {
            public boolean accept(File file1) {
                if (file1.isFile() && pattern.matcher(file1.getName()).matches()) {
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

def mkDir(File file){
    if (file.getParentFile().exists()) {
        file.mkdir();
    } else {
        mkDir(file.getParentFile());
        file.mkdir();
    }
}

def  getRandomSid(long timestamp) {

    def subjects = ["ath","iat","uup"] as String [];
    def locations = ["bj","gz","hf"] as String [];

    StringBuilder sb = new StringBuilder()
            .append(subjects[new Random().nextInt(3)])
            .append(String.format("%08d", new Random().nextInt(99999999)))
            .append("@")
            .append(locations[new Random().nextInt(3)])
            .append(String.format("%011x", timestamp))
            .append(String.format("%02x", new Random().nextInt(255)))
            .append(String.format("%02x", new Random().nextInt(255)))
            .append("000");

    return sb.toString();
}



