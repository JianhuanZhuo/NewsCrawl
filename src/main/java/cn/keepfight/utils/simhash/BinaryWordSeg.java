package cn.keepfight.utils.simhash;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * @author zhangcheng
 *
 */
public class BinaryWordSeg implements IWordSeg {

    @Override
    public List<String> tokens(String doc) {
        List<String> binaryWords = new LinkedList<String>();
        for(int i = 0; i < doc.length() - 1; i += 1) {
            binaryWords.add(String.valueOf(doc.charAt(i)) + doc.charAt(i + 1));
        }
        return binaryWords;
    }

    @Override
    public List<String> tokens(String doc, Set<String> stopWords) {
        return null;
    }

}