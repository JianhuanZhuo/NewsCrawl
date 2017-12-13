package cn.keepfight.newsCrawl;

import org.junit.Test;

import java.sql.Timestamp;

public class IPPoolMakerTest {
//    @Test
//    public void validateIP() throws Exception {
////        IPPoolMaker.validateIP();
//
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("Y-MM-dd");
//        LocalDate x = LocalDate.of(2017, 1, 1);
//        for (int j = 0; j < 365; j++) {
//            x = x.plusDays(1);
//            final String date = x.format(formatter);
//            System.out.println(date);
//        }
//    }
//
//    @Test
//    public void pickIps() throws Exception {
////        IPPoolMaker.pickIps();
////        String url = dao.getUrl();
//        String url = "http://news.qq.com/a/20171128/013311.htm";
//        String fileName = "./article/" + url.replaceAll("/", "_").replaceAll(":", "-") + ".txt";
//        System.out.println(fileName);
//        FileUtils.write(new File(fileName), "fileName");
//
//    }
//
//    @Test
//    public void geresPage() throws Exception {
//        int page_max = 10;
//        int page_cur = 9;
//        IntStream.range(page_cur - 10, page_cur + 10)
//                .filter(x -> x <= page_max)
//                .filter(x -> x > 0)
//                .mapToObj(i -> new Pair<>(i, Math.abs(i - page_cur)))
//                .sorted(Comparator.comparing(Pair::getValue))
//                .limit(5)
//                .sorted(Comparator.comparing(Pair::getKey))
//                .map(p -> "&currentPage=" + p.getKey())
//                .forEach(System.out::println);
//    }
//
//    @Test
//    public void getList() throws Exception {
////        FileUtils.write(new File("./rawList/list.txt"), ArticleServices.listUrl().stream().collect(Collectors.joining("\n")))
////        ;
//        InputStream is1 = this.getClass().getClassLoader().getResourceAsStream("data/30wChinsesSeqDic_clean.dic");
//        InputStream is2 = this.getClass().getClassLoader().getResourceAsStream("data/words.dic");
//        try (BufferedReader bf1 = new BufferedReader(new InputStreamReader(is1));
//             BufferedReader bf2 = new BufferedReader(new InputStreamReader(is2));
//             BufferedWriter writer = new BufferedWriter(new FileWriter("words.dic"))) {
//            bf1.lines().map(line -> line.split(" ")[0]).forEach(s -> {
//                try {
//                    writer.write(s + "\n");
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            });
//            bf2.lines().map(line -> line.split(" ")[0]).forEach(s -> {
//                try {
//                    writer.write(s + "\n");
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            });
//        }
//    }

    @Test
    public void test(){
        System.out.println(Timestamp.valueOf("2017-12-08 21:15:48"));
    }
}