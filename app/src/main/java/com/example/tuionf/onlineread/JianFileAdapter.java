package com.example.tuionf.onlineread;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tuionf
 * @date 2017/10/16
 * @email 596019286@qq.com
 * @explain
 */

public class JianFileAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<JianFile> mList;
    private OnRVItemClickListener mOnItemClickLitener;
    private int flag;

    /*
    * flag 用于区分是否显示item布局中的图片
    * */
    public JianFileAdapter(Context mContext, List<JianFile> mList, int flag) {
        this.mContext = mContext;
        this.mList = mList;
        this.flag = flag;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_item,null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        Log.e("hhp", "onBindViewHolder: " );
        final MyViewHolder myViewHolder = (MyViewHolder) holder;
        myViewHolder.textView.setText(mList.get(position).getFileName());
        setFileImg(myViewHolder.img_item);

        if (mOnItemClickLitener != null) {
            myViewHolder.textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickLitener.onItemClick(myViewHolder.textView,position);
                }
            });

            myViewHolder.img_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickLitener.onItemClick(myViewHolder.img_item,position);
                }
            });

            if (0 == flag){
                myViewHolder.img_item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnItemClickLitener.onItemClick(myViewHolder.textView,position);
                    }
                });
            }
        }

    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void updateData(ArrayList<JianFile> list){
        mList.clear();
        mList.addAll(list);
        Log.e("hhp", "updateData: ");
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView textView;
        ImageView img_item;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.fileName_tv);
            img_item = (ImageView) itemView.findViewById(R.id.fileType_iv);
        }
    }

    public interface OnRVItemClickListener {
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }



    public void setOnRVItemClickLitener(OnRVItemClickListener mOnItemClickLitener){
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    private void setFileImg(ImageView iv){
        switch(flag){
            case 0:
                iv.setBackgroundResource(R.mipmap.word);
                break;
            case 1:
                iv.setBackgroundResource(R.mipmap.excel);
                break;
            case 2:
                iv.setBackgroundResource(R.mipmap.ppt);
                break;
            case 3:
                iv.setBackgroundResource(R.mipmap.pdf);
                break;
            case 4:
                iv.setBackgroundResource(R.mipmap.txt);
                break;
            default:
                break;
        }

    }
}
