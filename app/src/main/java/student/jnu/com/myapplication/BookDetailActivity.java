package student.jnu.com.myapplication;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class BookDetailActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toolbar toolbar_detail=(Toolbar)findViewById(R.id.toolbar_detail) ;
        setSupportActionBar(toolbar_detail);
        //获取书本对象
        Intent intent=getIntent();
        final Book book=(Book)intent.getSerializableExtra("book_item");

        //初始化控件
        ImageButton edit_button=(ImageButton)findViewById(R.id.btn_edit);
        ImageView book_image=(ImageView)findViewById(R.id.book_pic_detail);
        TextView book_name=(TextView)findViewById(R.id.book_name_detail);
        TextView author=(TextView)findViewById(R.id.author_detail);
        TextView press=(TextView)findViewById(R.id.press_detail);
        TextView press_time=(TextView)findViewById(R.id.press_time_detail);
        TextView isbn=(TextView)findViewById(R.id.isbn_detail);
        TextView reading_state=(TextView)findViewById(R.id.readingstate_detail);
        TextView bookshelf=(TextView)findViewById(R.id.bookshelf_detail);
        TextView label=(TextView)findViewById(R.id.label_datail);
        TextView book_source=(TextView)findViewById(R.id.book_source_detail);
        //获取该图书信息
//        Resources resources=getResources();
//        book_image.setImageDrawable(resources.getDrawable(book.getImageId()));
        book_image.setImageResource(book.getImageId());
        book_name.setText(book.getBookName());
        author.setText(book.getAuthor());
        press.setText(book.getPressName());
        press_time.setText(book.getPressTime());
        isbn.setText(book.getISBN());


//        //获取该图书信息
//        setBook(book);

        //编辑按钮
        edit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(BookDetailActivity.this,BookEditActivity.class);
                intent.putExtra("book_item",book);
                startActivity(intent);
                finish();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu,menu);
        return true;
    }

    //    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        switch (requestCode){
//            case 77:
//                if(resultCode==RESULT_OK){
//                    Book book_return=(Book)data.getSerializableExtra("return_item");
//                    setBook(book_return);
//
//                }
//
//        }
//    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.delete_book:
                //删除书籍的操作。。。。  从默认书架 和 book.getbookshelf 这两个书架中移除这本书
                //从所属标签book.getlabel中移除这本书
                // 然后保存数组
                Toast.makeText(this,"delete successfully!",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(BookDetailActivity.this,MainActivity.class);
                startActivity(intent);
                break;
        }
        return true;
    }
}
