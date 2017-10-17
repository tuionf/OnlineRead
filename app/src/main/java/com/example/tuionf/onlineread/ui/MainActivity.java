package com.example.tuionf.onlineread.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tuionf.onlineread.BaseAdapter;
import com.example.tuionf.onlineread.DividerGridItemDecoration;
import com.example.tuionf.onlineread.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


	private TextView tv1,tv;
	private RecyclerView rv;
	private WebView webView;
	private ArrayList<String> files = new ArrayList<>();
	private ArrayList<String> mList = new ArrayList<>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		tv1 = (TextView) findViewById(R.id.tv1);
		tv = (TextView) findViewById(R.id.tv);
		rv = (RecyclerView) findViewById(R.id.rv);
		webView = (WebView) findViewById(R.id.webView);

		initData();
//		避免输入法界面弹出后遮挡输入光标的问题
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

		tv1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				openFileSys();
			}
		});

		rv.setLayoutManager(new GridLayoutManager(this,3));
		BaseAdapter mAdapter = new BaseAdapter(this,mList,0);
//        base_rv.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST));
//        base_rv.addItemDecoration(new DividerGridItemDecoration(this,10,R.color.colorPrimary));
		rv.addItemDecoration(new DividerGridItemDecoration(this));
		rv.setAdapter(mAdapter);
		mAdapter.setOnRVItemClickLitener(new BaseAdapter.OnRVItemClickListener() {
			@Override
			public void onItemClick(View view, int position) {
				Toast.makeText(MainActivity.this, "___"+position, Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(MainActivity.this,SelectActivity.class);
				intent.putExtra("index",position);
				startActivity(intent);
			}

			@Override
			public void onItemLongClick(View view, int position) {

			}
		});

		tv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.e("hhp", "onClick: "+"----------------" );
				setDocumentPath();
			}
		});
	}

	private void openFileSys() {
//		fileView.onStopDisplay();
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//		intent.setType("text/*");//设置类型，我这里是任意类型，任意后缀的可以这样写。
		intent.setType("*/*");
		intent.addCategory(Intent.CATEGORY_OPENABLE);
		startActivityForResult(intent,1);


//        Uri contentUri = getUriForFile(getContext(), "cn.teachcourse.fileprovider", newFile);
//        File imagePath = new File(Environment.getExternalStorageDirectory(), "/temp/"+System.currentTimeMillis());
//        FileProvider.getUriForFile(this,"com.example.tuionf.onlineread",imagePath);
	}

	private void initData() {
		mList.add("DOC");
		mList.add("EXCEl");
		mList.add("PPT");
		mList.add("PDF");
		mList.add("TXT");
//		mList.add("最近");
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK){
			if (requestCode == 1){
				Uri uri = data.getData();
                Intent i = new Intent(this,FileActivity.class);
				i.putExtra("uri",uri.toString());
                startActivity(i);
			}
		}
	}

	public void setDocumentPath() {
		webView.getSettings().setJavaScriptEnabled(true);
		webView.setWebChromeClient(new WebChromeClient());
//		webView.getSettings().setpl
		webView.loadUrl("https://docs.google.com/viewer?url=https://raw.githubusercontent.com/zhihu/zhihu-rxjava-meetup/master/slides/%E5%A6%82%E4%BD%95%E6%8A%8A%20RxJava%20%E5%BA%94%E7%94%A8%E5%88%B0%E7%9F%A5%E4%B9%8E%20-%20%E4%BD%95%E8%8B%A5%E6%98%95.pdf");
	}


}