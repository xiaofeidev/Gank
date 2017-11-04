package com.github.xiaofei_dev.gank.ui.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.github.xiaofei_dev.gank.R;
import com.github.xiaofei_dev.gank.greenDAO.VisitDao;
import com.github.xiaofei_dev.gank.greenDAO.bean.Collect;
import com.github.xiaofei_dev.gank.ui.view.RefreshView;
import com.github.xiaofei_dev.gank.util.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public final class SimpleWebActivity extends AppCompatActivity implements RefreshView {
    private String url;
    private String title;
    private String desc;
    private static final String TAG = "SimpleWebActivity";

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.web)
    WebView mWebView;
    @BindView(R.id.refresh)
    SwipeRefreshLayout mRefresh;

//    @BindView(R.id.title)
//    TextView mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        url = getIntent().getStringExtra("URL");
        title = getIntent().getStringExtra("TITLE");
        desc = getIntent().getStringExtra("DESC");
        setContentView(R.layout.activity_simple_web);
        ButterKnife.bind(this);
        initView();
//        ViewCompat.setTransitionName(getTitleViewInToolbar(mToolbar), TOOL_BAR_TITLE);
//        getTitleViewInToolbar(mToolbar).setTextColor(getResources().getColor(R.color.black));
//        ViewCompat.setTransitionName(mTitle, TOOL_BAR_TITLE);
//        mTitle.setText(title);
//        ActionBar actionBar = getSupportActionBar();
//        if(actionBar != null){
//            setTitle(title);
//            actionBar.setDisplayShowHomeEnabled(true);
//            actionBar.setDisplayHomeAsUpEnabled(true);
//        }
        //setTitle(title);//??
        //getTitleViewInToolbar(mToolbar).setText(title);//??
        //ViewCompat.setTransitionName(getTitleViewInToolbar(mToolbar), TOOL_BAR_TITLE);
    }
    public static Intent newIntent(Context context,String url,String title,String desc){
        Intent intent = new Intent(context,SimpleWebActivity.class);
        intent.putExtra("URL",url);
        intent.putExtra("TITLE",title);
        intent.putExtra("DESC",desc);
        return intent;
    }

    @Override
    public void showRefresh() {
        mRefresh.setRefreshing(true);
    }

    @Override
    public void hideRefresh() {
        mRefresh.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(mRefresh != null){
                    mRefresh.setRefreshing(false);
                }
            }
        },500);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_simple_web, menu);
        if (VisitDao.query(url).size() != 0){
            menu.findItem(R.id.collect).setIcon(R.drawable.ic_collection_true);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.collect:
                if (VisitDao.query(url).size() == 0){
                    Collect collect = new Collect();
                    collect.setUrl(url);
                    collect.setTitle(title);
                    collect.setDesc(desc);
                    VisitDao.insertCollections(collect);
                    item.setIcon(R.drawable.ic_collection_true);
                    ToastUtils.showShort(R.string.collect_done);
                }else {
                    VisitDao.deleteCollection(VisitDao.query(url).get(0));
                    item.setIcon(R.drawable.ic_collection_false);
                    ToastUtils.showShort(R.string.collect_delete);
                }
                break;

            case R.id.open:
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;
            case R.id.copy:
                ClipboardManager cmb = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData data = ClipData.newPlainText("gankUrl", url);
                cmb.setPrimaryClip(data);
                ToastUtils.showShort(R.string.url_copy_success);
                break;
            case R.id.share:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, title + "\n" + url);
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent, "分享到"));
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    private void initView(){
        setSupportActionBar(mToolbar);
        setTitle(title);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        showRefresh();

        mWebView.requestFocusFromTouch();//设置支持获取手势焦点
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);  //支持js
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        // 设置可以支持缩放
        webSettings.setSupportZoom(true);
        // 设置出现缩放工具
//        webSettings.setBuiltInZoomControls(true);
//        webSettings.setUseWideViewPort(true);//设置此属性，可任意比例缩放
//        webSettings.setLoadWithOverviewMode(true);
        //webSettings.setPluginsEnabled(true);  //支持插件
        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.setWebViewClient(new WebViewClient(){
            /**
             *desc：一般的正常加载无需覆盖此方法，使用默认回调就能正常加载网页
             */
//        @Override
//        public boolean shouldOverrideUrlLoading(WebView webView, String url) {
//            webView.loadUrl(url);
//            return true;
//        }
//
//        @Override
//        public boolean shouldOverrideUrlLoading(WebView webView, WebResourceRequest request) {
//            webView.loadUrl(request.getUrl().toString());
//            return true;
//        }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                showRefresh();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                hideRefresh();
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                hideRefresh();
            }
        });
        mWebView.loadUrl(url);

        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mWebView.reload();
            }
        });
    }

    /*private TextView getTitleViewInToolbar(Toolbar obj) {
        TextView textView = null;
        try {
            Field title = obj.getClass().getDeclaredField("mTitleTextView");
            title.setAccessible(true);
            textView = (TextView) title.get(obj);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return textView;
    }*/

//    private Intent getIntentInstance(Context context){
//        return new Intent(context,SimpleWebActivity.class);
//    }


}
