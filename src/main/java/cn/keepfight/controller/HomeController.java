package cn.keepfight.controller;

import cn.keepfight.dao.ArticleDao;
import cn.keepfight.jdbc.ArticleServices;
import cn.keepfight.lucene.QueryFormDao;
import cn.keepfight.lucene.ResPage;
import cn.keepfight.lucene.SearchUtil;
import cn.keepfight.utils.NumericUtil;
import cn.keepfight.utils.lang.Pair;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class HomeController {

    @RequestMapping("")
    public String welcome(Model model) throws Exception {
        return "welcome";
    }

    @RequestMapping("/search")
    public String search(Model model, @ModelAttribute QueryFormDao query, HttpServletRequest request) throws Exception {
        System.out.println(query);
        long stamp = System.currentTimeMillis();
        if (query == null) {
            query = new QueryFormDao();
        }

        ResPage resPage = SearchUtil.decodeQuery(query);
        model.addAttribute("resPage", resPage);
        model.addAttribute("model", query);

        int page_max = (resPage.getTotal() - 1) / resPage.getPageCapacity() + 1;
        int page_cur = resPage.getCurrentPage();
        String params = request.getRequestURL().toString() + "?" + query.getQueryWithoutPage();
        List<Pair<Integer, String>> urls = IntStream.range(page_cur - 10, page_cur + 10)
                .filter(x -> x <= page_max)
                .filter(x -> x > 0)
                .mapToObj(i -> new Pair<>(i, Math.abs(i - page_cur)))
                .sorted(Comparator.comparing(Pair::getValue))
                .limit(5)
                .sorted(Comparator.comparing(Pair::getKey))
                .map(p -> new Pair<>(p.getKey(), params + "&currentPage=" + p.getKey()))
                .collect(Collectors.toList());
        model.addAttribute("prev", (page_cur - 1 < 1) ? "" : params + "&currentPage=" + (page_cur - 1));
        model.addAttribute("next", (page_cur + 1 > page_max) ? "" : params + "&currentPage=" + (page_cur + 1));
        model.addAttribute("urls", urls);
        model.addAttribute("query", query.getQueryWithoutPage());

        List<ArticleDao> hots = ArticleServices.mostHotArticles().stream()
                .map(article -> {
                    long cmts = NumericUtil.getOrDefault(article.getCmt_num())+1;
                    long pubs = System.currentTimeMillis() - article.getPubtime().getTime();
                    double score = Math.log(cmts) * 0.7 - Math.log(pubs/1000*60*60) * 0.3;
                    System.out.println("title:"+article.getTitle().substring(0, 10)+"-"+"pubs:"+pubs+" cmts: "+cmts+" score: "+score);
                    return new Pair<>(article, -score);
                })
                .sorted(Comparator.comparing(Pair::getValue))
                .limit(6)
                .map(Pair::getKey)
                .collect(Collectors.toList());
        model.addAttribute("hots", hots);

        model.addAttribute("cost", System.currentTimeMillis() - stamp);
        return "search";
    }

    @RequestMapping("/similar")
    public String similar(Model model, @RequestParam("id") String id) throws Exception {
        return "search";
    }

    @RequestMapping("*")
    public String nofound(Model model) {
        return "nofound";
    }
}
