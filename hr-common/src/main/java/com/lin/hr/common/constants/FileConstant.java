package com.lin.hr.common.constants;

/**
 * @author Lin_jd
 * @version V1.0.0
 * @since 2025/4/6 19:48
 **/
public class FileConstant {
    /**
     * 基础地址
     */
    public static final String FILE_FOLDER_FILE = "/file/";
    /**
     * 头像目录
     */
    public static final String FILE_FOLDER_AVATAR_NAME = "avatar/";
    /**
     * 图片后缀
     */
    public static final String IMAGE_SUFFIX = ".png";
    /**
     * cover后缀
     */
    public static final String COVER_IMAGE_SUFFIX = "_cover.png";
    /**
     * 常用图片后缀
     */
    public static final String[] IMAGE_SUFFIX_LIST = new String[]{".jpeg", ".jpg", ".png", ".gif", ".bmp", ".webp"};
    /**
     * 常用视频后缀
     */
    public static final String[] VIDEO_SUFFIX_LIST = new String[]{".mp4", ".avi", ".rmvb", ".mkv", ".mov"};
    /**
     * 文件大小单位
     */
    public static final Long FILE_SIZE_MB = 1024 * 1024L;
}
