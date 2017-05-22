package com.github.xiaofei_dev.gank.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.github.xiaofei_dev.gank.R;
import com.github.xiaofei_dev.gank.ui.fragment.GankBaseFragment;
import com.github.xiaofei_dev.gank.ui.fragment.GankCategoryRandomFragment;
import com.github.xiaofei_dev.gank.ui.fragment.GankCategoryFragment;
import com.github.xiaofei_dev.gank.ui.fragment.GankDayFragment;
import com.github.xiaofei_dev.gank.ui.fragment.GankMeiZhiFragment;
import com.github.xiaofei_dev.gank.ui.fragment.GankMeizhiRandomFragment;
import com.github.xiaofei_dev.gank.ui.fragment.call_back_listener.ItemClickListener;
import com.github.xiaofei_dev.gank.ui.fragment.call_back_listener.MeiZhiItemClickListener;
import com.github.xiaofei_dev.gank.ui.view.RefreshView;
import com.github.xiaofei_dev.gank.ui.view.base.GankBaseView;
import com.github.xiaofei_dev.gank.util.ToastUtils;
import com.melnykov.fab.FloatingActionButton;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 将一个方法或已知不会被继承的类声明为 final 的不会带来性能的提升，但是会帮助编译器优化代码
 * 其他方法或类同因
 */
public final class MainActivity extends AppCompatActivity implements
        MeiZhiItemClickListener, ItemClickListener,RefreshView {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @BindView(R.id.search_view)
    MaterialSearchView searchView;

    @BindView(R.id.refresh)
    SwipeRefreshLayout mRefresh;

    FragmentManager fm;
    MenuItem randomNormal;

    private static final String TAG = "MainActivity";
    public static final String KEY_NIGHT_MODE = "night_mode";
    private long mPressedTime = 0;
    private int menuItemId = -1;
    private String title = "每日干货";
    private boolean isNight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        /*if (savedInstanceState != null) {
            title = savedInstanceState.getString("title", getString(R.string.news));
            menuItemId = savedInstanceState.getInt("menuItemId", -1);
        }
        fm = getSupportFragmentManager();

        //恢复保存的Fragment
        GankBaseFragment mGankBaseFragment = (GankBaseFragment) fm.findFragmentByTag(TAG);
        if (mGankBaseFragment == null) {
            mGankBaseFragment = new GankDayFragment();
            showRefresh();
        }*/
        if (savedInstanceState != null) {
            title = savedInstanceState.getString("title", getString(R.string.news));
            menuItemId = savedInstanceState.getInt("menuItemId", -1);
        }

        fm = getSupportFragmentManager();
        GankBaseFragment mGankBaseFragment;
        if(title.equals(getString(R.string.news))){
            mGankBaseFragment = new GankDayFragment();
        }else if(title.equals(getString(R.string.fu_li))){
            mGankBaseFragment = GankMeiZhiFragment.
                    newInstance(getResources().getString(R.string.fu_li));
        }else {
            mGankBaseFragment = GankCategoryFragment.newInstance(title);
        }

        showRefresh();

        fm.beginTransaction().replace(R.id.container, mGankBaseFragment, TAG).commit();
        initViews();
        //Log.d(TAG, "onCreate: ");
        //mGankBaseFragment = null;
        //toolbar.setTitle(getResources().getString(R.string.news));

//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                fragmentItem = (RecyclerView)findViewById(R.id.item_content);
//                fragmentItem.smoothScrollToPosition(0);
//                /*toolbar.setVisibility(View.VISIBLE);*/
//            }
//        });
//        //toolbar.setTitle(getResources().getString(R.string.news));
//
//        mGankCategoryFragment = GankCategoryFragment.
//                newInstance(getResources().getString(R.string.android));
//        transaction.replace(R.id.container,mGankCategoryFragment);
//        transaction.commit();
        }

        @Override
        public void showRefresh () {
            mRefresh.setRefreshing(true);
        }

        @Override
        public void hideRefresh () {
            mRefresh.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (mRefresh != null) {
                        mRefresh.setRefreshing(false);
                    }
                }
            }, 500);
        }

        @Override
        protected void onDestroy () {
            super.onDestroy();
            Log.d(TAG, "onDestroy: ");
            //mGankBaseFragment.setData(collectMyLoadedData());
        }

        @Override
        public void onSaveInstanceState (Bundle savedInstanceState){
            super.onSaveInstanceState(savedInstanceState);
            savedInstanceState.putString("title", toolbar.getTitle().toString());
            savedInstanceState.putInt("menuItemId", menuItemId);
            Log.d(TAG, "onSaveInstanceState: ");
        }

        @Override
        public boolean onCreateOptionsMenu (Menu menu){
            getMenuInflater().inflate(R.menu.tool_bar, menu);

            MenuItem item = menu.findItem(R.id.search);
            randomNormal = menu.findItem(R.id.random);
            searchView.setMenuItem(item);
            searchView.setSuggestions(getResources().getStringArray(R.array.query_suggestions));
            searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
//                GankSearchFragment gankSearchFragment = GankSearchFragment.newInstance(query);
//                fm.beginTransaction().replace(R.id.container, gankSearchFragment, TAG).commit();
//                showRefresh();
//                toolbar.setTitle(query);
//                searchView.closeSearch();
                    Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                    intent.putExtra(SearchActivity.ARGUMENT, query);
                    searchView.closeSearch();
                    startActivity(intent);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    //Do some magic
                    return false;
                }
            });

            return true;
        }

        @Override
        public boolean onOptionsItemSelected (MenuItem item){
            switch (item.getItemId()) {
                case android.R.id.home:
                    drawer.openDrawer(GravityCompat.START);
                    break;
                case R.id.random:
                    String toolbarTitle = toolbar.getTitle().toString();
                    String itemTitle = item.getTitle().toString();
                    String[] navItem = getResources().getStringArray(R.array.gank_item);
                    boolean isGankItem = Arrays.asList(navItem).contains(toolbarTitle);
                    //Log.d(TAG, "onOptionsItemSelected: " + "toolbarName " + toolbarTitle);
                    if (itemTitle.equals(getString(R.string.random))) {
                        if (toolbarTitle.equals("福利")) {
                            GankMeizhiRandomFragment gankMeizhiRandomFragment = GankMeizhiRandomFragment.
                                    newInstance(toolbarTitle);
                            fm.beginTransaction().replace(R.id.container, gankMeizhiRandomFragment, TAG).commit();
                            showRefresh();
                        } else if (isGankItem) {
                            GankCategoryRandomFragment gankCategoryRandomFragment = GankCategoryRandomFragment.
                                    newInstance(toolbarTitle);
                            fm.beginTransaction().replace(R.id.container, gankCategoryRandomFragment, TAG).commit();
                            showRefresh();
                        } else {
                            ToastUtils.showShort(R.string.nonsupport);
                            break;
                        }
                        item.setTitle(R.string.normal);
                    } else {
                        if (toolbarTitle.equals("福利")) {
                            GankMeiZhiFragment mGankMeiZhiFragment = GankMeiZhiFragment.
                                    newInstance(getResources().getString(R.string.fu_li));
                            fm.beginTransaction().replace(R.id.container, mGankMeiZhiFragment, TAG).commit();
                            showRefresh();
                        } else if (isGankItem) {
                            replaceFragment(toolbarTitle);
                        } else {
                            //ToastUtils.showShort(R.string.nonsupport);
                            break;
                        }
                        item.setTitle(R.string.random);
                    }
//                if (toolbarTitle.equals("福利")) {
//                    GankMeizhiRandomFragment gankMeizhiRandomFragment = GankMeizhiRandomFragment.
//                            newInstance(toolbarTitle);
//                    fm.beginTransaction().replace(R.id.container, gankMeizhiRandomFragment, TAG).commit();
//                    showRefresh();
//                } else if (!toolbarTitle.equals(getString(R.string.news))) {
//                    GankCategoryRandomFragment gankCategoryRandomFragment = GankCategoryRandomFragment.
//                            newInstance(toolbarTitle);
//                    fm.beginTransaction().replace(R.id.container, gankCategoryRandomFragment, TAG).commit();
//                    showRefresh();
//                } else {
//                    break;
//                }
                    break;
                case R.id.search:
                    //
                    break;
                default:
                    break;
            }
            return super.onOptionsItemSelected(item);
        }

    private void replaceFragment(String category) {
        GankCategoryFragment gankBaseCategoryFragment = GankCategoryFragment.newInstance(category);
        fm.beginTransaction().replace(R.id.container, gankBaseCategoryFragment, TAG).commit();
        showRefresh();
        toolbar.setTitle(category);
    }

//    @Override
//    public void setGankDayInfo(GankDay gankDay) {
//        //GankDay.Results results = gankDay.getResults();
//        mTextView.setText(gankDay.toString());
//    }
//
//    @Override
//    public void showNetworkError() {
//        Toast.makeText(this,"network error",Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        //httpRequestPresenter.unsubscribe();
//    }


    @Override
    public void onBackPressed() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else {
            long mNowTime = System.currentTimeMillis();//记录本次按键时刻
            if ((mNowTime - mPressedTime) > 1000) {//比较两次按键时间差
                Toast.makeText(this, "再按一次退出应用", Toast.LENGTH_SHORT).show();
                mPressedTime = mNowTime;
            } else {
                //            退出程序
                //            finish();
                //            System.exit(0);
                super.onBackPressed();
            }
        }
    }

    private void initViews() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
        /*ViewCompat.setTransitionName(SimpleWebActivity.getTitleViewInToolbar(toolbar),
                SimpleWebActivity.TOOL_BAR_TITLE);*/
        //itemContent = (RecyclerView)findViewById(R.id.item_content);
        //fab.attachToRecyclerView(itemContent);
        setTitle(title);
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Fragment fragment = fm.findFragmentByTag(TAG);
                if (fragment != null && fragment instanceof GankBaseView) {
                    ((GankBaseView) fragment).firstGetDate();
                }

            }
        });


        if (menuItemId != -1) {
            navigationView.setCheckedItem(menuItemId);
        }
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.news:
                        GankDayFragment gankDayFragment = new GankDayFragment();
                        fm.beginTransaction().replace(R.id.container, gankDayFragment, TAG).commit();
                        showRefresh();
                        toolbar.setTitle(getResources().getString(R.string.news));
                        item.setChecked(true);
                        menuItemId = id;
                        randomNormal.setTitle(R.string.random);
                        break;
                    case R.id.android:
                        replaceFragment(getResources().getString(R.string.android));
                        item.setChecked(true);
                        menuItemId = id;
                        randomNormal.setTitle(R.string.random);
                        break;
                    case R.id.ios:
                        replaceFragment(getResources().getString(R.string.ios));
                        item.setChecked(true);
                        menuItemId = id;
                        randomNormal.setTitle(R.string.random);
                        break;
                    case R.id.app:
                        replaceFragment(getResources().getString(R.string.app));
                        item.setChecked(true);
                        menuItemId = id;
                        randomNormal.setTitle(R.string.random);
                        break;
                    case R.id.web:
                        replaceFragment(getResources().getString(R.string.web));
                        item.setChecked(true);
                        menuItemId = id;
                        randomNormal.setTitle(R.string.random);
                        break;
                    case R.id.expand:
                        replaceFragment(getResources().getString(R.string.expand));
                        item.setChecked(true);
                        menuItemId = id;
                        randomNormal.setTitle(R.string.random);
                        break;
                    case R.id.xiatuijian:
                        replaceFragment(getResources().getString(R.string.xia_tui_jian));
                        item.setChecked(true);
                        menuItemId = id;
                        randomNormal.setTitle(R.string.random);
                        break;
                    case R.id.fuli:
                        GankMeiZhiFragment mGankMeiZhiFragment = GankMeiZhiFragment.
                                newInstance(getResources().getString(R.string.fu_li));
                        fm.beginTransaction().replace(R.id.container, mGankMeiZhiFragment, TAG).commit();
                        showRefresh();
                        toolbar.setTitle(getResources().getString(R.string.fu_li));
                        item.setChecked(true);
                        menuItemId = id;
                        randomNormal.setTitle(R.string.random);
                        break;
                    case R.id.night:
                        GankBaseFragment fragment = (GankBaseFragment) fm.findFragmentByTag(TAG);
                        if (fragment instanceof GankCategoryRandomFragment | fragment instanceof GankMeizhiRandomFragment) {
                            ToastUtils.showShort(R.string.theme_hint);
                            break;
                        }
                        AlertDialog settingDialog = new AlertDialog.Builder(MainActivity.this,R.style.Theme_AppCompat_Dialog_MinWidth)
                                .setTitle(R.string.dialog_title)
                                .setMessage(R.string.dialog_message)
                                .setPositiveButton(getString(android.R.string.yes),
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.cancel();
                                                switchTheme();
                                            }
                                        })
                                .setNegativeButton(android.R.string.cancel,
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.cancel();

                                            }
                                        }).create();
                        settingDialog.show();
                        break;
                    case R.id.about:
//                        drawer.closeDrawers();
//                        new Thread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        //线程执行内容
//                                        try {
//                                            Thread.sleep(500);
//                                        } catch (Exception e) {
//                                            e.printStackTrace();
//                                        }
//                                        runOnUiThread(new Runnable() {
//                                            @Override
//                                            public void run() {
//                                                Intent intent = new Intent(MainActivity.this, AboutActivity.class);
//                                                startActivity(intent);
//                                            }
//                                        });
//                            }
//                        }).start();
                        Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                        startActivity(intent);
                        break;
                    default:
                        break;
                }
                drawer.closeDrawers();
                return true;
            }
        });
    }
//    public void startSimpleMeiZhiActivity(String url,View view){
//        Intent intent = new Intent(this,SimpleMeiZhiActivity.class);
//        intent.putExtra("URL",url);
//        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.
//                makeSceneTransitionAnimation(this, view, "simple transition name");
//        startActivity(intent, optionsCompat.toBundle());
//    }
    private void switchTheme(){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        if (!sp.getBoolean(KEY_NIGHT_MODE, false)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            sp.edit().putBoolean(KEY_NIGHT_MODE, true).apply();
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            sp.edit().putBoolean(KEY_NIGHT_MODE, false).apply();
        }
        getWindow().setWindowAnimations(R.style.WindowAnimationFadeInOut);
//        //drawer.closeDrawers();
//                        /*drawer.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                recreate();
//                            }
//                        }, 500);*/
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                //线程执行内容
//                try {
//                    Thread.sleep(500);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        recreate();
//                    }
//                });
//            }
//        }).start();
        recreate();
    }

    @Override
    public void onMeiZhiItemClick(String url, View view) {
        Intent intent = new Intent(this, SimpleMeiZhiActivity.class);
        intent.putExtra("URL", url);

//        Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();//
//        Bundle bundle = new Bundle();
//        bundle.putParcelable("BITMAP",bitmap);
//        intent.putExtra("BITMAP",bundle);

        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.
                makeSceneTransitionAnimation(this, view, "simple transition name");
        startActivity(intent, optionsCompat.toBundle());
    }

    @Override
    public void onItemClick(String url, String title) {
        Intent intent = new Intent(this, SimpleWebActivity.class);
        intent.putExtra("URL", url);
        intent.putExtra("TITLE", title);
//        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.
//                makeSceneTransitionAnimation(this, view, "title");
//        startActivity(intent, optionsCompat.toBundle());
        startActivity(intent);
    }
}

