package main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import main.service.ExchangeRatesService;
import main.service.GifService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/gg")
public class MainController {

    private ExchangeRatesService exchangeRatesService;
    private GifService gifService;
    @Value("${giphy.rich}")
    private String richTag;
    @Value("${giphy.broke}")
    private String brokeTag;
    @Value("${giphy.zero}")
    private String whatTag;
    @Value("${giphy.error}")
    private String errorTag;

    @Autowired
    public MainController(
            ExchangeRatesService exchangeRatesService,
            GifService gifService
    ) {
        this.exchangeRatesService = exchangeRatesService;
        this.gifService = gifService;
    }

    /**
     * Возвращает список доступных кодов валют.
     *
     * @return
     */
    @GetMapping("/getcodes")
    public List<String> getCharCodes() {
        return exchangeRatesService.getCharCodes();
    }

    /**
     * Получает гифку для отправки клиенту
     * исходя из резултата сравнения курса в openExchangeRatesService
     *
     * @param code
     * @return
     */
    @GetMapping("/getgif/{code}")
    public ResponseEntity<Map> resolveTag(@PathVariable String code) {
        int gifKey = exchangeRatesService.getKeyForTag(code);
        String gifTag = resolveTag(gifKey);
        return gifService.getGif(gifTag);
    }

    private String resolveTag(int gifKey) {
        switch (gifKey) {
            case 1:
                return this.richTag;
            case -1:
                return this.brokeTag;
            case 0:
                return this.whatTag;
            default:
                return this.errorTag;
        }
    }

}