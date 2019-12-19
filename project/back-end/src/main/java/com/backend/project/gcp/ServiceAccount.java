package com.backend.project.gcp;

import com.google.gson.Gson;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class ServiceAccount
{
    private String type;
    private String project_id;
    private String private_key_id;
    private String private_key;
    private String client_email;
    private String client_id;
    private String auth_uri;
    private String token_uri;
    private String auth_provider_x509_cert_url;
    private String client_x509_cert_url;

    public String createJson()
    {
        ServiceAccount serviceAccount = new ServiceAccount();
        serviceAccount.setType("service_account");
        serviceAccount.setProject_id("spring-project-261615");
        serviceAccount.setPrivate_key_id("869b8500405823f34bb4746b290e45cbaa516174");
        serviceAccount.setPrivate_key("-----BEGIN PRIVATE KEY-----\nMIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCcbvZ94SUtyU49\nGHY6csL7hgdOkxgTEY+K4skvBDT/Iy80wEQ647pm8xGX4sJK9L8p+mIG6/kYQfYG\n+SmWZPgSM6S4tzW7Gc4yrLrIcXpUsPL132O2O2hVxEe2RKgcpIIez+EDTUCvziwj\nRvJwmuEyGjKy9jJa5KRjPmsx2+Pa6QEFgjpbv4/jEGn7vFFruOORZlUqyF89kD9B\n44kuNSuUrxOOfP/o9y77NsOHJv3aSUYBWdc/tKGGCKbw2x0JaHNM1VN8DinpCgNI\n8u8D1/9eZSEcJufYccVol7dibIVE591j0Dr57WtTMpUsroRquJe3M8YTBMQZ44VP\n7fj15IgtAgMBAAECggEAF8/sRY0YE7go6LPoi00c0Ry90AAkEgMCTqnm5flF9/Br\nJyR9Plmb7j/h1bT2zmIjF0i90z/oUwD3amhTJEmKWXrzAAsjaq+q65vPcuOTM4Eb\n+sFedEbmwud14xDgZjvEctxB7Da+OgSwQ05xaSKqVO4X+ZXwZFWZkGHX/2bxcEj6\nkvu7DTfSx7D40/eTAuqNuwDNW0t/zmopcsR+mYFOrwKQZw+NImgNaiFjCME4W9wv\nI85wwPoo+yong/W3b72NcIs0jBx9E88brTg6TmxyIQPDWpqJTXnNZxjcdJX4pbYz\n6KOal1ji9i5Tpo/HmFzrRkPWHbdS23Bv3LkxkJ/zAQKBgQDTVWVhqk7M615XaGnF\n6F4aRn6W9sMqnsO68zHj8G/eNqbwMvVZWh8IK2Bd7FTm7WcyQRaNxoEddMMxhVvk\nDSpfQqbW740m8MVxvRVzTPMqXY+ZShjCx8o285jtBD5MRFdl/+9hcQxzaIIOnexM\nyzL+dlxurw9LZJgbxnmEgy2sqQKBgQC9fxdannrSK/LuhcNG4aV/ChIGJ+LXktdm\nnmZ5J5n7CWtPP5x7mgCZTyZUGZUWy4JVcecy5/wJXv8uBsTvWbpaTHl+NW7qU7iS\ndjWQ5OlVlOd79Jk7pZ4KBSfCBDM4f0Jp3tx0qQU++NJaFseaKPO/WyQ2tYaZR2Di\nHdq8DHCN5QKBgD3eOCnZY+xV026fYKfRmQr0LEchwo0NoZtvauGiF/3j3gdfw7f+\nzCrmF/YnI8vfIGDz8RhcTpxC/InY0sf2mrr8lj9CLf6CaiscAdWZZ261qT5lFM6h\nEq/3rINmxJ4rO7LetEPAhraal8iTrwPkUO9x0ymMElwt/OHUvupuRMbRAoGAYZ7x\nhwuRtmhRtDNQ0Fk6iuHDO0yc/fV15jXAELYoCz1k/OSGVt6VZGV0F2s+dQnOUPpm\n4sDEBIfd6fYuyidta0tuY2hfhXQv57PiEIz878vQHPl7X1EIw5y4SKklQfu9WREo\nfSj9vN0ucJ6/oVlriK8JTu3hM2GkNZivsKSv70kCgYEAt5JIq6INuRd1v4jem+D5\n7MVzPSIlM0Zlls7cy3h4tcus9JXnGAlHz/AP730BrFKKs9nyUmE/xZq8vcjfCmAj\nnFd9GwzRRUp9awRTnDbJPTDSRq5WYW2FqFQetB9+2cbYzvABf2E8go/kSco5iJIn\ntuUQalvqlKLIv7Y4UedpxPI=\n-----END PRIVATE KEY-----\n");
        serviceAccount.setClient_email("1078706248583-compute@developer.gserviceaccount.com");
        serviceAccount.setClient_id("104414470191063439450");
        serviceAccount.setAuth_uri("https://accounts.google.com/o/oauth2/auth");
        serviceAccount.setToken_uri("https://oauth2.googleapis.com/token");
        serviceAccount.setAuth_provider_x509_cert_url("https://www.googleapis.com/oauth2/v1/certs");
        serviceAccount.setClient_x509_cert_url("https://www.googleapis.com/robot/v1/metadata/x509/1078706248583-compute%40developer.gserviceaccount.com");

        Gson gson = new Gson();
        String json = gson.toJson(serviceAccount);

        return json;
    }
}
