package com.yzit.lucene;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class SearchIndex {
	
	public static void main(String[] args) throws Exception {
		//1.创建Directory
		String indexPath="E:\\pictrue\\lucene\\index";
		Directory directory=FSDirectory.open(new File(indexPath));
	
		//2.创建IndexReader
		DirectoryReader reader=DirectoryReader.open(directory);
		//3.根据IndexReader创建IndexSearcher
		IndexSearcher searcher = new IndexSearcher(reader);
	
		int maxDoc = reader.maxDoc();
		
		 for (int i = 0; i < maxDoc; i++){
             Document doc = searcher.doc(i);
             List<IndexableField> listField = doc.getFields();
             for ( int j = 0;j < listField.size(); j++){
                 IndexableField index = listField.get(j);
                 System.out.println(index.name()+":"+index.stringValue());
             }
         }
		
	/*	//4.创建搜索的query
		Query query = new TermQuery(new Term("address","北京"));
		
		//5.根据Searcher返回TopDocs
		TopDocs tds = searcher.search(query, 10);
		
		//6.根据TopDocs获取ScoreDoc
		ScoreDoc[] sds=tds.scoreDocs;
		
		//7.根据Searcher和ScoreDoc获取搜索到的document对象
		for(ScoreDoc sd:sds) {
			Document d= searcher.doc(sd.doc);
			
			//8根据document对象获取查询的字段值
			System.out.println("name------"+d.get("name"));
			System.out.println("address-----"+d.get("address"));
			System.out.println("content-------"+d.get("content"));
		}*/
		//关闭reader
		reader.close();
		
		
	
	
	
	
	}
}
