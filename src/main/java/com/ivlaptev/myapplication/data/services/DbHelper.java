package com.ivlaptev.myapplication.data.services;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import androidx.annotation.Nullable;

import com.ivlaptev.myapplication.models.articles.ArticleCompact;
import com.ivlaptev.myapplication.models.articles.Author;
import com.ivlaptev.myapplication.models.articles.Category;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DbHelper extends SQLiteOpenHelper {
    private final String ARTICLES_TABLE = "articles";
    private final String ARTICLES_CATEGORY_TABLE = "articles_category";
    private final String ARTICLES_AUTHOR_TABLE = "articles_author";

    public DbHelper(Context context) {
        super(context, "Ckmirea.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + ARTICLES_CATEGORY_TABLE + "(id INTEGER primary key, title TEXT)");
        db.execSQL("create table " + ARTICLES_AUTHOR_TABLE + "(slug TEXT primary key, name TEXT," +
                " middlename TEXT, lastname TEXT)");
        db.execSQL("create table " + ARTICLES_TABLE + "(id INTEGER primary key, category INTEGER," +
                " author TEXT, image TEXT, preview_text TEXT, title TEXT, created_date TEXT, content TEXT," +
                "foreign key(author) references " + ARTICLES_AUTHOR_TABLE + "(slug)," +
                "foreign key(category) references " + ARTICLES_CATEGORY_TABLE + "(id))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists " + ARTICLES_CATEGORY_TABLE);
        db.execSQL("drop table if exists " + ARTICLES_AUTHOR_TABLE);
        db.execSQL("drop table if exists " + ARTICLES_TABLE);
    }

    public void insertArticles(List<ArticleCompact> articles) {
        SQLiteDatabase db = getWritableDatabase();
        for (ArticleCompact article :
                articles) {
            db.execSQL("insert or replace into " + ARTICLES_AUTHOR_TABLE + " values (\'"
                    + article.getAuthor().getSlug() + "\', \'" + article.getAuthor().getName() + "\', \'"
                    + article.getAuthor().getMiddlename() + "\', \'" + article.getAuthor().getLastname() + "\')");
            db.execSQL("insert or replace into " + ARTICLES_CATEGORY_TABLE + " values ("
                    + article.getCategory().getId() + ", \'" + article.getCategory().getTitle() + "\')");
            db.execSQL("insert or replace into " + ARTICLES_TABLE + " values ("
                    + article.getId() + ", " + article.getCategory().getId() + ", \'"
                    + article.getAuthor().getSlug() + "\', \'" + article.getImage() + "\', \'"
                    + article.getPreviewText() + "\', \'" + article.getTitle() + "\', \'" + article.getCreatedDate()
                    + "\', \'\')");
        }
    }

    public void updateArticle(ArticleCompact article) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("insert or replace into " + ARTICLES_AUTHOR_TABLE + " values (\'"
                + article.getAuthor().getSlug() + "\', \'" + article.getAuthor().getName() + "\', \'"
                + article.getAuthor().getMiddlename() + "\', \'" + article.getAuthor().getLastname() + "\')");
        db.execSQL("insert or replace into " + ARTICLES_CATEGORY_TABLE + " values ("
                + article.getCategory().getId() + ", \'" + article.getCategory().getTitle() + "\')");
        db.execSQL("insert or replace into " + ARTICLES_TABLE + " values ("
                + article.getId() + ", " + article.getCategory().getId() + ", \'"
                + article.getAuthor().getSlug() + "\', \'" + article.getImage() + "\', \'"
                + article.getPreviewText() + "\', \'" + article.getTitle() + "\', \'" + article.getCreatedDate()
                + "\', \'" + article.getContent() + "\')");
    }

    public List<ArticleCompact> getPopularArticles(@Nullable Integer count) {
        SQLiteDatabase db = getReadableDatabase();

        Map<String, Author> authors = getAuthors();
        Map<Integer, Category> categories = getCategories();

        List<ArticleCompact> articles = new ArrayList<>();

        String query = "select * from " + ARTICLES_TABLE;
        if (count != null) {
            query += " limit " + count.toString();
        }
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            ArticleCompact article = new ArticleCompact();
            article.setId(cursor.getInt(0));
            article.setTitle(cursor.getString(5));
            article.setPreviewText(cursor.getString(4));
            article.setImage(cursor.getString(3));
            article.setAuthor(authors.get(cursor.getString(2)));
            article.setCategory(categories.get(cursor.getInt(1)));
            article.setCreatedDate(cursor.getString(6));
            article.setContent(cursor.getString(7));

            articles.add(article);
        }

        return articles;
    }

    public ArticleCompact getArticleById(Integer id) {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("select * from " + ARTICLES_TABLE + " where id=" + id, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            ArticleCompact article = new ArticleCompact();
            article.setId(cursor.getInt(0));
            article.setTitle(cursor.getString(5));
            article.setPreviewText(cursor.getString(4));
            article.setImage(cursor.getString(3));
            article.setCreatedDate(cursor.getString(6));
            article.setContent(cursor.getString(7));

            return article;
        }

        return null;
    }

    public Map<String, Author> getAuthors() {
        SQLiteDatabase db = getReadableDatabase();
        Map<String, Author> authors = new HashMap<>();

        Cursor cursor = db.rawQuery("select * from " + ARTICLES_AUTHOR_TABLE, null);
        while (cursor.moveToNext()) {
            Author author = new Author();
            author.setSlug(cursor.getString(0));
            author.setName(cursor.getString(1));
            author.setMiddlename(cursor.getString(2));
            author.setLastname(cursor.getString(3));
            authors.put(author.getSlug(), author);
        }

        return authors;
    }

    public Map<Integer, Category> getCategories() {
        SQLiteDatabase db = getReadableDatabase();
        Map<Integer, Category> categories = new HashMap<>();

        Cursor cursor = db.rawQuery("select * from " + ARTICLES_CATEGORY_TABLE, null);
        while (cursor.moveToNext()) {
            Category category = new Category();
            category.setId(cursor.getInt(0));
            category.setTitle(cursor.getString(1));
            categories.put(category.getId(), category);
        }

        return categories;
    }
}
