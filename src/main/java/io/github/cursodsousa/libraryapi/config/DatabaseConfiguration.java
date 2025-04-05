package io.github.cursodsousa.libraryapi.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfiguration {

    @Value("${spring.datasource.url}")
    String url;
    @Value("${spring.datasource.username}")
    String username;
    @Value("${spring.datasource.password}")
    String password;
    @Value("${spring.datasource.driver-class-name}")
    String driver;

    // Nao recomendado -> DataSource padrao, vamos usar o hikari abaixo, que o spring ja usa
    // mesmo que nao configuremos esse arquivo todo.
   // @Bean
//    public DataSource dataSource() {
//        DriverManagerDataSource ds = new DriverManagerDataSource();
//        ds.setUrl(url);
//        ds.setUsername(username);
//        ds.setPassword(password);
//        ds.setDriverClassName(driver);
//        return ds;
//    }

    @Bean
    public DataSource hikariDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);
        config.setDriverClassName(driver);

        config.setMaximumPoolSize(10); // Maxima quantidade de conexoes liberadas
        config.setMinimumIdle(1); // tamanho inicial do pool
        config.setPoolName("library-db-pool");
        config.setMaxLifetime(600000); // 600mil ms -> 10min no maximo a conexao, entao vai criar e matar outra.
        config.setConnectionTimeout(100000); // tentar uma conexao até esse tempo, se nao dar excecao
        config.setConnectionTestQuery("select 1"); // simples query de test -> retorna 1

        return new HikariDataSource(config);

    }
}
