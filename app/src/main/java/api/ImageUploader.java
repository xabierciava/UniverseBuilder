package api;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImageUploader {

    private ImageUploadCallback imageUploadCallback;

    public void uploadImage(String filePath) {
        // Map is used to multipart the file using okhttp3.RequestBody
        File file = new File(filePath);

        // Parsing any Media type file
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());

        ApiInterface getResponse = ServiceGenerator.createService(ApiInterface.class);
        Call<ServerResponse> call = getResponse.uploadFile(fileToUpload, filename);
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                if (response.isSuccessful()) {
                    if (imageUploadCallback != null) {
                        imageUploadCallback.onImageUploaded(response.body().getUrl());
                    }
                } else {
                    if (imageUploadCallback != null) {
                        imageUploadCallback.onImageUploadFailed();
                    }
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                if (imageUploadCallback != null) {
                    imageUploadCallback.onImageUploadFailed();
                }
            }
        });
    }

    public void setImageUploadCallback(ImageUploadCallback imageUploadCallback) {
        this.imageUploadCallback = imageUploadCallback;
    }

    public interface ImageUploadCallback {
        void onImageUploaded(String downloadUrl);

        void onImageUploadFailed();
    }

}
