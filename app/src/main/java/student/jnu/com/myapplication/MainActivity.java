package student.jnu.com.myapplication;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,SearchView.OnQueryTextListener{
    private static final String TAG = "MainActivity";
    private ArrayList<Book> bookList=new ArrayList<Book>();

    RecyclerView recyclerView_book;

    public BookAdapter bookAdapter;

    static Book bookNew;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //加载主list
        Log.d(TAG, "git change test");
        Log.d(TAG, "onCreate: create mainactivity");
        initBooks();
        recyclerView_book=(RecyclerView)findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView_book.setLayoutManager(layoutManager);
        bookAdapter=new BookAdapter(bookList,MainActivity.this);
        recyclerView_book.setAdapter(bookAdapter);

        //加载上面的toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //加载右下角的添加fab
        FloatingActionMenu fab=(FloatingActionMenu)findViewById(R.id.fab);
        fab.setClosedOnTouchOutside(true);
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

//-------------------------------------------
//  editor:张泽锋
//-------------------------------------------
        //fab里的添加button
        final com.github.clans.fab.FloatingActionButton fab_upload = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.fab_upload);
        fab_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_upload = new Intent(MainActivity.this, BookEditActivity.class);
                bookNew = new Book(R.drawable.img_nulledit, "bookName", "ISBN", "author", "pressName", "pressTime");
                intent_upload.putExtra("book_item", bookNew);
                intent_upload.putExtra("book_exists", false);
                startActivityForResult(intent_upload, 1);
            }
        });
//-------------------------------------------

        //加载上方书架选择的spinner
        String[] bookshelfType=new String[]{"默认书架","书架1","书架2"};
        SpinnerAdapter spinnerAdapter=ArrayAdapter.createFromResource(getApplicationContext(),R.array.category,R.layout.spinner_dropdown_item);
        Spinner mTopSpinner=new Spinner(getSupportActionBar().getThemedContext());
        mTopSpinner.setAdapter(spinnerAdapter);
        toolbar.addView(mTopSpinner,0);


        //加载左边的导航栏
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

//-------------------------------------------
//  editor:张泽锋
//-------------------------------------------
    //检测从edit活动返回的book是否已经存在，不存在则添加到booklist
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult: enter onactivityresult");
        Log.d(TAG, "bookNew: " + bookNew);
        boolean book_exists = data.getBooleanExtra("book_exists",false);
        switch (requestCode){
            case 1:
                if(resultCode == RESULT_OK){
                    Log.d(TAG, "onActivityResult: book_exists: " + !book_exists);
                    if(!book_exists) {
                        Log.d(TAG, "data: " + data);
                        Book book = (Book)data.getSerializableExtra("book_new");
                        Log.d(TAG, "book: " + book);
                        Log.d(TAG, "book_name: " + book.getBookName());
                        Log.d(TAG, "onActivityResult: booklist addprev size: " + bookList.size());
                        bookList.add(book);
                        Log.d(TAG, "onActivityResult: booklist addafter size: " + bookList.size());
                        Log.d(TAG, "onActivityResult: booklist add success");
                        bookAdapter.notifyDataSetChanged();
                        Log.d(TAG, "onActivityResult: bookAdapter notify success");
                    }else{
                        bookAdapter.notifyDataSetChanged();
                    }
                }
        }
    }

//-------------------------------------------

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        //加载SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
                searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.searchable.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    //对搜索内容进行匹配，返回一个匹配关键字成功 过滤后的一组书籍
    private List<Book> filter(List<Book>bookList,String text){
        List<Book>filter_bookList=new ArrayList<Book>();

        for (Book book:bookList){
            if (book.getBookName().contains(text) || book.getPressName().contains(text))
                //这里实现书名和出版社的搜索关键字
                filter_bookList.add(book);
        }
        return filter_bookList;
    }

    //初始化书列表
    private void initBooks(){
        Book book=new Book(R.drawable.book_pic,"六论自发性","9787520142625","詹姆斯·C·斯科特","社会科学文献出版社","2019-04");
        bookList.add(book);
        Book book_1=new Book(R.drawable.book1_pic,"C Primer Plus","9787115390592","Stephen Prata","人民邮电出版社","2016-01");
        bookList.add(book_1);
        Book book_2=new Book(R.drawable.book2_pic,"人工智能原理：一种现代的方法","9787302331094","罗素、诺维格","清华大学出版社","2013-11");
        bookList.add(book_2);
        Book book_3=new Book(R.drawable.book3_pic,"敏捷软件开发","9787302071976","Robert C·Martin","清华大学出版社","2003-09");
        bookList.add(book_3);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }


    //监听搜索框内容变化
    @Override
    public boolean onQueryTextChange(String newText) {
        //得到过滤后的一组书籍
        List<Book> filter_bookList=filter(bookList,newText);
        //将过滤后的结果set进adapter 显示
        bookAdapter.setFilter(filter_bookList);
        return true;

    }
}
