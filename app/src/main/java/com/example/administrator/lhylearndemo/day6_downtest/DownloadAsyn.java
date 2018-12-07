package com.example.administrator.lhylearndemo.day6_downtest;

import android.os.AsyncTask;
import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.example.administrator.lhylearndemo.day6_downtest.Contest.*;

public class DownloadAsyn extends AsyncTask<String,Integer,Integer> {

    private DownloadLinstener downloadLinstener;

    private boolean isPaused=false;
    private boolean isCancaled=false;
    private int lastProgress;

    public DownloadAsyn (DownloadLinstener downloadLinstener){
        this.downloadLinstener=downloadLinstener;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        int prg=values[0];
        if (prg>lastProgress){
            downloadLinstener.onProgess(prg);
            lastProgress=prg;
        }
    }

    @Override
    protected Integer doInBackground(String... strings) {
        long downFileSize=0;
        File downFile = null;
        InputStream inputStream = null;
        RandomAccessFile accessFile = null;
        try {
            String dwonUrl=strings[0];
            String downFileName=dwonUrl.substring(dwonUrl.lastIndexOf("/"));
            String directoy=Environment
                    .getExternalStoragePublicDirectory(
                            Environment.DIRECTORY_DOWNLOADS).getPath();
            downFile=new File(directoy+downFileName);
            if (downFile.exists()){
                downFileSize=downFile.length();
            }
            long contextDownSize=getContextFileSize(dwonUrl);       //下载文件总大小
            if (contextDownSize==0){
                return TYPE_FAILED;
            }else if (downFileSize==contextDownSize){
                return TYPE_SUCCESS;
            }

            OkHttpClient client=new OkHttpClient();
            Request request=new Request.Builder()
                    .url(dwonUrl)
                    .addHeader("header","size="+downFileSize)
                    .build();
            Response response=client.newCall(request).execute();
            if (response!=null){
                inputStream=response.body().byteStream();
                accessFile=new RandomAccessFile(downFile,"rw");
                accessFile.seek(downFileSize);
                byte [] bytes=new byte[1024];
                int total=0;
                int len;
                while ((len=inputStream.read(bytes))!=-1){
                    if (isCancaled){
                        return TYPE_CANCALED;
                    }else if (isPaused){
                        return TYPE_PAUSED;
                    }else {
                        total+=len;
                        accessFile.write(bytes,0,len);

                        //计算下载进度
                        int prguess=(int)((total+downFileSize)*100/contextDownSize);

                        publishProgress(prguess);
                    }
                }
                response.close();
                return TYPE_SUCCESS;
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if (inputStream!=null){
                    inputStream.close();
                }
                if (accessFile!=null){
                    accessFile.close();
                }
                if (isCancaled&&downFile!=null){
                    downFile.delete();
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return TYPE_FAILED;
    }

    private long getContextFileSize(String dwonUrl) throws IOException {
        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder()
                .url(dwonUrl)
                .build();
        Response response=client.newCall(request).execute();
        if (response!=null&&response.isSuccessful()){
            long size=response.body().contentLength();
            response.close();
            return size;
        }
        return 0;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        switch (integer){
            case TYPE_SUCCESS:
                downloadLinstener.onSuccess();
                break;
            case TYPE_FAILED:
                downloadLinstener.onFailed();
                break;
            case TYPE_PAUSED:
                downloadLinstener.onPaused();
                break;
            case TYPE_CANCALED:
                downloadLinstener.onCanceled();
                break;
        }
    }



    public void setPaused(boolean paused) {
        isPaused = paused;
    }

    public void setCancaled(boolean cancaled) {
        isCancaled = cancaled;
    }
}
