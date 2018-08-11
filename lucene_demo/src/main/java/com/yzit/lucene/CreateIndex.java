package com.yzit.lucene;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class CreateIndex {

	public static void main(String[] args) throws Exception {
		//1.创建Directory
		String indexPath="E:\\pictrue\\lucene\\index";
		Directory directory=FSDirectory.open(new File(indexPath));
		
		//2.创建indexWriter
		IndexWriterConfig iwConfig=new IndexWriterConfig(Version.LUCENE_4_10_3,new StandardAnalyzer());
		IndexWriter writer = new IndexWriter(directory,iwConfig);
	
		//3.创建document对象
		Document document = null;
		
		//4.位document添加field对象
		
		document = new Document();
		document.add(new StringField("name","张三",Field.Store.YES));
		document.add(new StringField("address","北京",Field.Store.YES));
		document.add(new StringField("content","北京市昌平区东小口镇",Field.Store.YES));
	
		//5.通过IndexWriter添加文档到索引中去
		writer.addDocument(document);
		
		document = new Document();
		document.add(new StringField("name","李四",Field.Store.YES));
		document.add(new StringField("address","湖北",Field.Store.YES));
		document.add(new StringField("content","湖北省荆门市掇刀区",Field.Store.YES));
	
		//5.通过IndexWriter添加文档到索引中去
		writer.addDocument(document);
		
		
		document = new Document();
		document.add(new StringField("name","王五",Field.Store.YES));
		document.add(new StringField("address","北京",Field.Store.YES));
		document.add(new StringField("content","北京市海淀区",Field.Store.YES));
	
		//5.通过IndexWriter添加文档到索引中去
		writer.addDocument(document);
		
		writer.close();
	
	
	
	
	
	
	}
}
