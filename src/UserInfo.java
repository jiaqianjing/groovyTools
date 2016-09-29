/**
 * Created by Administrator on 2016/9/29.
 */
public class UserInfo {
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    private String uid;
    private String sid;
    private String audioPath;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getAudioPath() {
        return audioPath;
    }

    public void setAudioPath(String audioPath) {
        this.audioPath = audioPath;
    }

    public String getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(String startIndex) {
        this.startIndex = startIndex;
    }

    public String getOffset() {
        return offset;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "uid='" + uid + '\'' +
                ", sid='" + sid + '\'' +
                ", audioPath='" + audioPath + '\'' +
                ", startIndex='" + startIndex + '\'' +
                ", offset='" + offset + '\'' +
                '}';
    }

    public void setOffset(String offset) {
        this.offset = offset;
    }

    private String startIndex;
    private String offset;
}
