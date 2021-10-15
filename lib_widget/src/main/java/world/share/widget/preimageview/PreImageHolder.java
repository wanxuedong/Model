package world.share.widget.preimageview;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

//用于存储图片的部分信息
public class PreImageHolder implements Serializable, Parcelable {

    private String name;       //图片的名字
    protected String path;       //图片的路径
    private long size;         //图片的大小
    private int width;         //图片的宽度
    private int height;        //图片的高度
    private String mimeType;   //图片的类型
    private long addTime;      //图片的创建时间

    /** 图片的路径和创建时间相同就认为是同一张图片 */
    @Override
    public boolean equals(Object o) {
        if (o instanceof PreImageHolder) {
            PreImageHolder item = (PreImageHolder) o;
            return this.path.equalsIgnoreCase(item.path) && this.addTime == item.addTime;
        }

        return super.equals(o);
    }

    public String getPath(){
        return path;
    }

    public String getName(){
        return name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.path);
        dest.writeLong(this.size);
        dest.writeInt(this.width);
        dest.writeInt(this.height);
        dest.writeString(this.mimeType);
        dest.writeLong(this.addTime);
    }

    public PreImageHolder() {
    }

    protected PreImageHolder(Parcel in) {
        this.name = in.readString();
        this.path = in.readString();
        this.size = in.readLong();
        this.width = in.readInt();
        this.height = in.readInt();
        this.mimeType = in.readString();
        this.addTime = in.readLong();
    }

    public static final Creator<PreImageHolder> CREATOR = new Creator<PreImageHolder>() {
        @Override
        public PreImageHolder createFromParcel(Parcel source) {
            return new PreImageHolder(source);
        }

        @Override
        public PreImageHolder[] newArray(int size) {
            return new PreImageHolder[size];
        }
    };
}
