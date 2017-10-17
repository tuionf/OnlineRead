package com.example.tuionf.onlineread.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tuionf.onlineread.DividerItemDecoration;
import com.example.tuionf.onlineread.JianFile;
import com.example.tuionf.onlineread.JianFileAdapter;
import com.example.tuionf.onlineread.R;
import com.example.tuionf.onlineread.loadingDialog;
import com.example.tuionf.onlineread.utils.FileUtils;

import java.util.ArrayList;

/**
 * @author tuionf
 * @date 2017/10/16
 * @email 596019286@qq.com
 * @explain
 */

public class PageFragment extends Fragment {

    public static final String ARG_PAGE = "ARG_PAGE";
    private int mPage = 0;
    private RecyclerView type_file_rv;
    private JianFileAdapter mAdapter;
    private static Context mContext;
    private static ArrayList<JianFile> files = new ArrayList<>();
    private boolean isVisible;
    private static loadingDialog dialog = null;

    // 标志位，标志已经初始化完成，防止空指针的异常
    private boolean isViewCreated;
    private static final String TAG = "PageFragment";

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (isVisible && (1 == msg.what)){
                Log.e(TAG, "handleMessage: " );
                ((Activity)mContext).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.updateData(files);
                        mAdapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });
            }
        }
    };

    public static PageFragment newInstance(Context context,int page) {
        mContext = context;
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        PageFragment pageFragment = new PageFragment();
        pageFragment.setArguments(args);
        dialog = new loadingDialog(context);
        return pageFragment;
    }

    private void initFileData() {
        Log.e(TAG, "initFileData: ------------------------------------------" );
        dialog.show();
        Log.e("PageFragment", "initFileData: ===="+mPage );
        String [] types = new String[10];
        switch(mPage){
            case 0:
                types = new String[]{".docx",".doc",".docm",".dotx",".dotm",".dot"};
                break;
            case 1:
                types = new String[]{".xlsx",".xlsm",".xlsb",".xls",".xltx",".xltm",".xlt",".xlam",".xla"};
                break;
            case 2:
                types = new String[]{".pptx",".pptm",".ppt",".potx",".potm",".pot",".ppsx",".ppsm",".pps",".ppam"};
                break;
            case 3:
                types = new String[]{".pdf"};
                break;
            case 4:
                types = new String[]{".txt"};
                break;
            default:
                break;
        }
        files.clear();
        final String[] finalTypes = types;
        new Thread(new Runnable() {
            @Override
            public void run() {
                files = FileUtils.getSpecificTypeOfFile(mContext, finalTypes,mHandler);

            }
        }).start();


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
//        initFileData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        isViewCreated = true;
        View view = inflater.inflate(R.layout.fragment_page, container, false);
        TextView textView = (TextView) view.findViewById(R.id.test_tv);
        type_file_rv = (RecyclerView) view.findViewById(R.id.type_file_rv);
        textView.setText("Fragment #" + mPage);
        type_file_rv.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new JianFileAdapter(mContext,files,mPage);
        type_file_rv.setAdapter(mAdapter);
        type_file_rv.addItemDecoration(new DividerItemDecoration(mContext,DividerItemDecoration.VERTICAL_LIST));
        mAdapter.setOnRVItemClickLitener(new JianFileAdapter.OnRVItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(mContext, "PageFragment"+mPage+"**"+position, Toast.LENGTH_SHORT).show();
                Intent i = new Intent(mContext,FileActivity.class);
                i.putExtra("uri",files.get(position).getFilePath().toString());
                i.putExtra("type","1");
                startActivity(i);
            }

            @Override
            public void onItemLongClick(View view, int position) {
            }
        });

        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser && isViewCreated) {
            Log.e(TAG, "setUserVisibleHint:---------- " );
            isVisible = true;
            Log.e(TAG, "setUserVisibleHint: " );
            initFileData();
        }else {
            isVisible = false;
        }
    }


    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getUserVisibleHint()) {
            isVisible = true;
            initFileData();
        }
    }

}
