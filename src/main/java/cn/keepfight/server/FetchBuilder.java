package cn.keepfight.server;

import cn.keepfight.utils.function.ConsumerCheck;
import cn.keepfight.utils.lang.Pair;

import java.util.ArrayList;
import java.util.List;

public class FetchBuilder<T> {
    String url = "";
    List<Pair<String, String>> params = new ArrayList<>(1);
    FetchServices.SUPPORT_METHODS method = FetchServices.SUPPORT_METHODS.GET;
    ConsumerCheck<T> processor = null;
}
