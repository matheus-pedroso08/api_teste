package api_teste.ds.configs;

import org.springframework.context.annotation.Configuration; //importa a anotação de configuração do Spring
import org.springframework.web.servlet.config.annotation.CorsRegistry;//importa a classe responsavel por registrar as regras do Cors
import org.springframework.web.servlet.config.annotation.EnableWebMvc;//impota a anotação que habilita os recursos do spring web MVC
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;//importa a interface de customização do Sping MVC

@Configuration //indica que essa classe possui configurações de Beans que devem ser inicializados com Srping
@EnableWebMvc // importa e ativa o suporte basico as requisições e controladores web Mvc do spring

public class WebConfig implements WebMvcConfigurer { // Classe de fcinfinguração que implementa o contrato de customização do Spring
   
    @Override // Sobreescreve o método de mapeamento CORS padrão da interface WebMvcConfigurer
    public void addCorsMappings(CorsRegistry registry){//Método indicado pelo Spring para resgistrar as regras do CORS
    registry.addMapping("/**");//Libera qualquer rota da API(coring "/**") para aceitar chamadas externas 
}

}