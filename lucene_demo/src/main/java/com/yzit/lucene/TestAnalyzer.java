package com.yzit.lucene;

import java.io.IOException;
import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;
public class TestAnalyzer {

	//ik分词器
	@Test
	public void IKAnalyzer() throws Exception {
		String txt="习近平是";
		Analyzer analyzer1=new IKAnalyzer();
		
		
		TokenStream tokenStream = analyzer1.tokenStream("content", new StringReader(txt));
		
		printKey(tokenStream);
	}
	
	 /**
     * 打印分词词
     * @param tokenstream
     * @throws IOException
     */
    public void printKey(TokenStream tokenstream) throws IOException{
        CharTermAttribute termAttribute = tokenstream.addAttribute(CharTermAttribute.class);// 为token设置属性类
        tokenstream.reset();// 重新设置
        while (tokenstream.incrementToken()) {// 遍历得到token
            System.out.print(new String(termAttribute.buffer(), 0,
                    termAttribute.length()) + "  ");
        }
    }
}
