package com.yzit.lucene;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.yzit.entity.Product;

public class DB2Lucene {

	
	public static void main(String[] args) {
		//链接数据库，查询结果集合
				List<Product> productList = findProduct();
				//将结果集合转换为document
				List<Document> docList = convertDoucment(productList);
				
				//将document插入到索引库中		
				boolean flag = createIndex(docList);
				if(flag ){
					System.out.println("-----创建索引成功-----------");
				}else{
					System.out.println("-----创建索失败-----------");
				}
	}
	
	
	//链接数据库,查询结果集合
	public static List<Product> findProduct(){
		
		List<Product> productList = new ArrayList<Product>();
		Connection conn=null;
		
		String sql;
		String url="jdbc:mysql://localhost:3306/test8?"+
				 "user=root&password=root&useUnicode=true&characterEncoding=UTF8";
		 try {
	            Class.forName("com.mysql.jdbc.Driver");// 动态加载mysql驱动
	            // 一个Connection代表一个数据库连接
	            conn = DriverManager.getConnection(url);
	            // Statement里面带有很多方法，比如executeUpdate可以实现插入，更新和删除等
	            Statement stmt = conn.createStatement();
	            sql = "select p.id, p.name, p.price   from product p";
	            ResultSet rs = stmt.executeQuery(sql);// executeUpdate语句会返回一个受影响的行数，如果返回-1就没有成功
	            
	            Product product = null;
	            while(rs.next()){
	            	product  =new Product();
	            	product.setId(rs.getInt("id"));
	            	product.setName(rs.getString("name"));
	            	product.setPrice(rs.getDouble("price"));
	            	
	            	  productList.add(product);
	            }
	            
	        } catch (SQLException e) {
	            System.out.println("MySQL操作错误");
	            e.printStackTrace();
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	        
	        return productList;
		
        
	}
	
	
	//将结果集合转换为document
		public static List<Document>  convertDoucment(List<Product> productList){
			List<Document> docList = new ArrayList<Document>(productList.size());
			
			Document doc  = null;
			for( Product pro : productList){
				doc = new Document();
				doc.add(new StringField("id", pro.getId().toString(),Field.Store.YES));//store是否存储
//				doc.add(new StringField("name", pro.getName(),Field.Store.YES));
				doc.add(new TextField("name", pro.getName(), Field.Store.YES));
				doc.add(new StringField("price",pro.getPrice().toString(),Field.Store.YES));//textField内容会进行分词
				
				docList.add(doc);
			}
			return docList;
		}

	
	
		public  static  boolean createIndex(List<Document> docList) {
			
			try{
				// 1、创建Directory
				String indexPath = "E:\\pictrue\\lucene\\index";//索引库位置
				Directory directory = FSDirectory.open(new File(indexPath));//在硬盘上生成Directory
				// 2、创建IndexWriter
				IndexWriterConfig iwConfig = new IndexWriterConfig(Version.LUCENE_4_10_3, new IKAnalyzer());
				IndexWriter writer = new IndexWriter(directory, iwConfig);
				// 3、创建document对象
				// 4、为document添加field对象
				// 5、通过IndexWriter添加文档到索引中
				
				for(Document doc : docList){
					writer.addDocument(doc);	
				}
				// 6、使用完成后需要将writer进行关闭
				writer.close();
			}catch(Exception e){
				e.printStackTrace();
				return false;
			}
			return true;
		}
	
	
	
	
	
	
	
	
}
