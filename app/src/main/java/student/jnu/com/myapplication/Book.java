package student.jnu.com.myapplication;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.io.Serializable;

/**
 * Created by ASUS on 2019/4/3.
 */

public class Book implements Serializable{
    private int imageId;    //这里要改的
    private String bookName;
    private String ISBN;
    private String author;
    private String pressName;
    private String pressTime;
    private String readingstate="未读";
    private String bookshelf="默认书架";
    private String note="";
    private String label;
    private String bookresource="douban.com";

    public Book(int imageId,String bookName,String ISBN,String author,String pressName,String pressTime){
        this.imageId=imageId;
        this.bookName=bookName;
        this.ISBN=ISBN;
        this.author=author;
        this.pressName=pressName;
        this.pressTime=pressTime;

    }

    public int getImageId(){
        return imageId;
    }

    public String getBookName(){
        return bookName;
    }

    public String getISBN(){
        return ISBN;
    }

    public String getAuthor(){
        return author;
    }

    public String getPressName(){
        return pressName;
    }

    public String getPressTime(){
        return pressTime;
    }

    public String getReadingstate(){return readingstate;}

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPressName(String pressName) {
        this.pressName = pressName;
    }

    public void setPressTime(String pressTime) {
        this.pressTime = pressTime;
    }

    public int compareTo(@NonNull Book book) {
//        char firstLetter = getBookName().toLowerCase().charAt(0);
//        int fL = (int)firstLetter -(int)('0');
//        char L2 = book.getBookName().toLowerCase().charAt(0);
//        int f2 = (int)L2-(int)('0');
//        return f2-L2;
        return (int)(Long.parseLong(ISBN) - Long.parseLong(book.getISBN()));
    }

}
