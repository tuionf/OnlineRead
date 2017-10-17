package com.example.tuionf.onlineread.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore.Files;
import android.provider.MediaStore.Files.FileColumns;
import android.text.TextUtils;
import android.util.Log;

import com.example.tuionf.onlineread.JianFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by tuion on 2017/10/9.
 */

public class FileUtils {
    public static ArrayList<JianFile> getSpecificTypeOfFile(Context context, String[] extension, Handler handler)
    {
        ArrayList<JianFile> fileLists = new ArrayList<>();
        //从外存中获取
        Uri fileUri=Files.getContentUri("external");
        //筛选列，这里只筛选了：文件路径和不含后缀的文件名
        String[] projection=new String[]{
                FileColumns.DATA,FileColumns.TITLE
        };
        //构造筛选语句
        String selection="";
        for(int i=0;i<extension.length;i++)
        {
            if(i!=0)
            {
                selection=selection+" OR ";
            }
            selection=selection+FileColumns.DATA+" LIKE '%"+extension[i]+"'";
        }
        //按时间递增顺序对结果进行排序;待会从后往前移动游标就可实现时间递减
        String sortOrder=FileColumns.DATE_MODIFIED;
        //获取内容解析器对象
        ContentResolver resolver=context.getContentResolver();
        //获取游标
        Cursor cursor=resolver.query(fileUri, projection, selection, null, sortOrder);
        if(cursor==null) {
            return null;
        }
        //游标从最后开始往前递减，以此实现时间递减顺序（最近访问的文件，优先显示）
        if(cursor.moveToLast())
        {
            do{

                JianFile jianFile = new JianFile();
                //输出文件的完整路径
                String data=cursor.getString(0);
                jianFile.setFilePath(data);
                jianFile.setFileName(getFileName(data));
                jianFile.setFileUpdateTime(getFileTime(data));
                fileLists.add(jianFile);
            }while(cursor.moveToPrevious());
        }
        cursor.close();

        handler.sendEmptyMessage(1);
        return fileLists;
    }

    private static String getFileTime(String path) {
        File file = new File(path);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd a K:mm");
        String ctime = formatter.format(file.lastModified());
        Log.e("time", "getFileTime: "+ctime );
        return ctime;
    }

    public static String getFileName(String path){

        if (!TextUtils.isEmpty(path)) {
            path = path.substring(path.lastIndexOf("/")+1);
        }

        Log.e("hhp", "getFileName: Name---"+path );
        return path;
    }


}
