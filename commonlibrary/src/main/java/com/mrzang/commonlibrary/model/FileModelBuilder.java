package com.mrzang.commonlibrary.model;

/**
 * author: mr.zang
 * date：2019-05-31
 * description:
 */
public class FileModelBuilder {
    private String fileId;
    private String filePath;
    private String fileName;
    private long fileLength;
    private int fileType;
    //缩略图路径
    private String thumbnail;
    //拍摄时间
    private String shootDate;
    //维度
    private String latitude;
    //经度
    private String longitude;
    //附加的Tag
    private String Tag;
    private long videoTime;

    public FileModelBuilder() {
    }

    public static FileModelBuilder aMonitorFileModel() {
        return new FileModelBuilder();
    }

    public FileModelBuilder setFileId(String fileId) {
        this.fileId = fileId;
        return this;
    }

    public FileModelBuilder setFilePath(String filePath) {
        this.filePath = filePath;
        return this;
    }

    public FileModelBuilder setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public FileModelBuilder setFileLength(long fileLength) {
        this.fileLength = fileLength;
        return this;
    }

    public FileModelBuilder setFileType(int fileType) {
        this.fileType = fileType;
        return this;
    }

    public FileModelBuilder setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
        return this;
    }

    public FileModelBuilder setShootDate(String shootDate) {
        this.shootDate = shootDate;
        return this;
    }

    public FileModelBuilder setLatitude(String latitude) {
        this.latitude = latitude;
        return this;
    }

    public FileModelBuilder setLongitude(String longitude) {
        this.longitude = longitude;
        return this;
    }

    public FileModelBuilder setTag(String Tag) {
        this.Tag = Tag;
        return this;
    }

    public FileModelBuilder setVideoTime(long videoTime) {
        this.videoTime = videoTime;
        return this;
    }

    public FileModel build() {
        FileModel fileModel = new FileModel();
        fileModel.setFileId(fileId);
        fileModel.setFilePath(filePath);
        fileModel.setFileName(fileName);
        fileModel.setFileLength(fileLength);
        fileModel.setFileType(fileType);
        fileModel.setThumbnail(thumbnail);
        fileModel.setShootDate(shootDate);
        fileModel.setLatitude(latitude);
        fileModel.setLongitude(longitude);
        fileModel.setTag(Tag);
        fileModel.setVideoTime(videoTime);
        return fileModel;
    }
}
