package cn.keepfight.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("cn.keepfight")
public class RootApplicationContextConfig {

//   @Bean
//   public DataSource dataSource() {
//      EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
//      EmbeddedDatabase db = builder
//         .setType(EmbeddedDatabaseType.HSQL)
//         .addScript("db/sql/create-table.sql")
//         .addScript("db/sql/insert-data.sql")
//         .build();
//      return db;
//   }
  
//   @Bean
//   public NamedParameterJdbcTemplate getJdbcTemplate() {
//      return new NamedParameterJdbcTemplate(dataSource());
//   }
}
