package com.jeff.demo;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.model.crf.CRFLexicalAnalyzer;
import com.hankcs.hanlp.seg.Dijkstra.DijkstraSegment;
import com.hankcs.hanlp.seg.NShort.NShortSegment;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.IndexTokenizer;
import com.hankcs.hanlp.tokenizer.NLPTokenizer;
import com.hankcs.hanlp.tokenizer.StandardTokenizer;

import java.util.List;

/**
 * @Auther: JeffSheng
 * @Date: 2019/10/14 17:45
 * @Description:
 *      HanLP几乎所有的功能都可以通过工具类HanLP快捷调用，当你想不起来调用方法时，只需键入HanLP.，IDE应当会给出提示，并展示HanLP完善的文档。
 *
 * 所有Demo都位于com.hankcs.demo下，比文档覆盖了更多细节，更新更及时，强烈建议运行一遍。
 */
public class DemoBaseSegment {

    public static void main(String[] args) throws Exception{
        /**
         * HanLP.segment(String)其实是对StandardTokenizer.segment的包装。
         */
        System.out.println(HanLP.segment("来首不完美女孩听听看"));
        System.out.println("----------------------------------------------------------------------------------");
        /**
         * 标准分词
         */
        List<Term> termList = StandardTokenizer.segment("来首不完美女孩听听看");
        System.out.println(termList);
        System.out.println("----------------------------------------------------------------------------------");

        /**
         * NLP分词
         */
        System.out.println(NLPTokenizer.segment("来首不完美女孩听听看"));
        // 注意观察下面两个“希望”的词性、两个“晚霞”的词性
        System.out.println(NLPTokenizer.analyze("我的希望是希望张晚霞的背影被晚霞映红").translateLabels());
        System.out.println(NLPTokenizer.analyze("支援臺灣正體香港繁體：微软公司於1975年由比爾·蓋茲和保羅·艾倫創立。"));
        System.out.println("----------------------------------------------------------------------------------");


        /**
         * 索引分词
         */
        List<Term> termList1 = IndexTokenizer.segment("来首不完美女孩听听看");
        for (Term term : termList1)
        {
            System.out.println(term + " [" + term.offset + ":" + (term.offset + term.word.length()) + "]");
        }
        System.out.println("----------------------------------------------------------------------------------");
        /**
         * N最短路分词算法
         */
        Segment nShortSegment = new NShortSegment().enableCustomDictionary(false).enablePlaceRecognize(true).enableOrganizationRecognize(true);
        Segment shortestSegment = new DijkstraSegment().enableCustomDictionary(false).enablePlaceRecognize(true).enableOrganizationRecognize(true);
        String[] testCase = new String[]{
                "来首不完美女孩听听看",
                "刘喜杰石国祥会见吴亚琴先进事迹报告团成员",
        };
        for (String sentence : testCase)
        {
            System.out.println("N-最短分词：" + nShortSegment.seg(sentence) + "\n最短路分词：" + shortestSegment.seg(sentence));
        }
        System.out.println("----------------------------------------------------------------------------------");

        /**
         * CRF分词
         */
        CRFLexicalAnalyzer analyzer = new CRFLexicalAnalyzer();
        String[] tests = new String[]{
                "商品和服务",
                "上海华安工业（集团）公司董事长谭旭光和秘书胡花蕊来到美国纽约现代艺术博物馆参观",
                // 支持繁体中文
                "微软公司於1975年由比爾·蓋茲和保羅·艾倫創立，18年啟動以智慧雲端、前端為導向的大改組。"
        };
        for (String sentence : tests)
        {
            System.out.println(analyzer.analyze(sentence));
        }

    }
}
