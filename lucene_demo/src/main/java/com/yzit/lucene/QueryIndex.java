package com.yzit.lucene;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class QueryIndex {
		
	
	@Test
	public void testTermQuery() throws Exception {
		Query query = new TermQuery(new Term("id","3"));
		//Query query=new TermQuery(new Term("name","王五"));
		
		doSearch(query);
	}
	
	@Test
	public void testQueryParser() throws ParseException, IOException{
		QueryParser parser = new QueryParser("name",new IKAnalyzer());//content表示搜索的域或者说字段
	      Query query = parser.parse("张三");//java表示被搜索的内容
	      doSearch(query);
	}
	
	
	//范围检索  error
		@Test
		public void testNumericRangeQuery() throws IOException{
			
			Query query = NumericRangeQuery.newIntRange("id", 1, 3, true,true);
			doSearch(query);
			System.out.println("--------------------------");
		}
		
		//多域查询
		@Test
		public void testMultiFieldQueryParser() throws ParseException, IOException{
			
			String[] fields  = {"id","name","price"};
			MultiFieldQueryParser multiFieldQueryParser = new MultiFieldQueryParser( fields , new IKAnalyzer());
			
			Query query = multiFieldQueryParser.parse("王五,1");
			
			doSearch(query);
			System.out.println("--------------------------");
		}
	
	public void doSearch(Query query) throws IOException{
		// 1、创建Directory
				String indexPath = "E:\\pictrue\\lucene\\index";// 索引库位置
				Directory directory = FSDirectory.open(new File(indexPath));// 在硬盘上生成Directory
				// 2、创建IndexReader
				  DirectoryReader reader = DirectoryReader.open(directory);
				// 3、根据IndexReader创建IndexSearcher
				  IndexSearcher searcher =  new IndexSearcher(reader);
				// 4、创建搜索的query
				  
				  
				
				// 5、根据Searcher返回TopDocs
				  TopDocs tds = searcher.search(query, 10000000);//查询10条记录
				// 6、根据TopDocs获取ScoreDoc
				  ScoreDoc[] sds = tds.scoreDocs;
				 
				// 7、根据Searcher和ScoreDoc获取搜索到的document对象
				  for(ScoreDoc sd:sds){
				       Document d = searcher.doc(sd.doc);
//				          8、根据document对象获取查询的字段值
				       /**  查询结果中content为空，是因为索引中没有存储content的内容，需要根据索引ID从原文件中获取content**/
				       System.out.println("id="+d.get("id"));
				       System.out.println("name="+d.get("name"));
				       System.out.println("price="+d.get("price"));
				   }
				// 8、根据document对象获取查询的字段值
				// 9、关闭reader
				  reader.close();
	}
}
