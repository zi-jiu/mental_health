package com.zj.demo.bo;

/**
 * @ClassName UserBO
 * @Author 字九
 * @Date 2021/3/26 10:42
 * @Description
 **/
public class UserBO {

    private String userId;
    private String FaceData;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFaceData() {
        return FaceData;
    }

    public void setFaceData(String faceData) {
        FaceData = faceData;
    }

    @Override
    public String toString() {
        return "UserBO{" +
                "userId='" + userId + '\'' +
                ", FaceData='" + FaceData + '\'' +
                '}';
    }
}
