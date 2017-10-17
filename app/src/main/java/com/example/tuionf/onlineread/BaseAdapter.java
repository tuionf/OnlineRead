package com.example.tuionf.onlineread;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
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

public class BaseAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<String> mList;
    private OnRVItemClickListener mOnItemClickLitener;
    private int flag;

    /*
    * flag 用于区分是否显示item布局中的图片
    * */
    public BaseAdapter(Context mContext, List<String> mList,int flag) {
        this.mContext = mContext;
        this.mList = mList;
        this.flag = flag;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (0 == flag) {
            view = LayoutInflater.from(mContext).inflate(R.layout.office_item,null);
        }else if (1 == flag){
            view = LayoutInflater.from(mContext).inflate(R.layout.fragment_item,null);
        }
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final MyViewHolder myViewHolder = (MyViewHolder) holder;
        myViewHolder.textView.setText(mList.get(position));

        if (mOnItemClickLitener != null) {
            myViewHolder.textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickLitener.onItemClick(myViewHolder.textView,position);
                }
            });

            myViewHolder.textView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mOnItemClickLitener.onItemLongClick(myViewHolder.textView, position);
                    return false;
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

    public void updateData(ArrayList<String> list){
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }
    public void addItem(int position){
        mList.add(position,"add");
        notifyItemInserted(position);   //不要使用notifyDataSetChanged()
    }

    public void removeItem(int position){
        mList.remove(position);
        notifyItemRemoved(position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView textView;
        ImageView img_item;
        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv_item);
            img_item = (ImageView) itemView.findViewById(R.id.img_item);
        }
    }

    public interface OnRVItemClickListener {
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }



    public void setOnRVItemClickLitener(OnRVItemClickListener mOnItemClickLitener){
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

}
