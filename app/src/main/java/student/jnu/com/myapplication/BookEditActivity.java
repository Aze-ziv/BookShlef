package student.jnu.com.myapplication;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class BookEditActivity extends AppCompatActivity {
    String[] readingstate=new String[]{"已读","阅读中","未读"};

    private static final String TAG = "BookEditActivity";


    private boolean book_exists;

    ImageView book_pic_edit;
    EditText book_name;
    EditText author;
    EditText press;
    EditText press_time;
    EditText isbn;
    Book book;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar_edit) ;
        setSupportActionBar(toolbar);

        Intent intent=getIntent();
        book=(Book)intent.getSerializableExtra("book_item");
        book_exists = intent.getBooleanExtra("book_exists", false);



        //初始化控件
        book_pic_edit = (ImageView)findViewById(R.id.bookpic_edit);
        book_name = (EditText) findViewById(R.id.book_name_edit);
        author = (EditText)findViewById(R.id.book_author_edit);
        press = (EditText) findViewById(R.id.book_press_edit);
        press_time = (EditText)findViewById(R.id.book_presstime_edit);
        isbn=(EditText) findViewById(R.id.book_isbn_edit);
        //后面的也要设置为全局变量，但暂未用到，未更改，editor：张泽锋
        Spinner reading_state=(Spinner) findViewById(R.id.spinner_readingstate);
        Spinner bookshelf=(Spinner) findViewById(R.id.spinner_bookshelf_edit);
        EditText note_edit=(EditText)findViewById(R.id.book_note_edit);
        EditText book_source=(EditText) findViewById(R.id.book_source_edit);
        //TextView label=(TextView)findViewById();  //这里还不知道要不要设置成Spinner  后面处理

        //获取该图书信息
        book_pic_edit.setImageResource(book.getImageId());
        book_name.setText(book.getBookName());
        author.setText(book.getAuthor());
        press.setText(book.getPressName());
        press_time.setText(book.getPressTime());
        isbn.setText(book.getISBN());


        //加载阅读状态下拉框
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,readingstate);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner readingstate_spinner=(Spinner)findViewById(R.id.spinner_readingstate);
        readingstate_spinner.setAdapter(adapter);

        //设置阅读状态下拉框默认选中值
        SpinnerAdapter spinnerAdapter=readingstate_spinner.getAdapter();
        int k=spinnerAdapter.getCount();
        readingstate_spinner.setSelection(2,true);
        for(int i=0;i<k;i++){
            if(book.getReadingstate().equals(spinnerAdapter.getItem(i).toString())){
                readingstate_spinner.setSelection(i,true);
                break;
            }
        }




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_menu,menu);

        return true;

    }

//-------------------------------------------
//  editor:张泽锋
//-------------------------------------------
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.save:
                //判断这本书是否已经存在
//               book_exists = (book.getImageId() == R.drawable.img_nulledit &&
//                              book.getBookName() == null &&
//                              book.getISBN() == null &&
//                              book.getAuthor() == null &&
//                              book.getPressName() == null &&
//                              book.getPressTime() ==null) ? false : true;
                Log.d(TAG, "onOptionsItemSelected: book_exists is " + book_exists);
                book.setBookName(book_name.getText().toString());
                book.setISBN(isbn.getText().toString());
                book.setAuthor(author.getText().toString());
                book.setPressName(press.getText().toString());
                book.setPressTime(press_time.getText().toString());

                Log.d(TAG, "onOptionsItemSelected: book_img: " + book.getImageId());
                Log.d(TAG, "onOptionsItemSelected: book_name: " + book_name.getText().toString());
                Log.d(TAG, "onOptionsItemSelected: isbn: " + isbn.getText().toString());
                Log.d(TAG, "onOptionsItemSelected: author: " + author.getText().toString());
                Log.d(TAG, "onOptionsItemSelected: press: " + press.getText().toString());
                Log.d(TAG, "onOptionsItemSelected: press_time: " + press_time.getText().toString());
                Log.d(TAG, "onOptionsItemSelected: book:" + book);
                //若book里面的各属性为空，则判断是空书，应该返回去add（注意这里的判别有问题，edit后属性不为空，要修改）
                if(!book_exists){
                    Log.d(TAG, "onOptionsItemSelected: 返回数据");
                    Intent intent = new Intent();
                    intent.putExtra("book_exists", false);
                    intent.putExtra("book_new", book);
                    //还要传回被修改的项的position
                    BookEditActivity.this.setResult(RESULT_OK, intent);
                    Log.d(TAG, "onOptionsItemSelected: set ok");
                }
                //保存到数组
                Log.d(TAG, "onOptionsItemSelected: finish prev");
                finish();
                 //结束edit这个activity，否则从MainActivity按返回键是退回edit
                Log.d(TAG, "onOptionsItemSelected: book_afterfinish:" + book);
                Log.d(TAG, "onOptionsItemSelected: finish after");
                break;
        }

        return true;
    }
//-----------------------------------------
}
