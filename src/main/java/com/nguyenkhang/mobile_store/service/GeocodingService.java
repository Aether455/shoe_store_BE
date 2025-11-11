package com.nguyenkhang.mobile_store.service;

import com.nguyenkhang.mobile_store.dto.Coordinates;
import com.nguyenkhang.mobile_store.dto.response.GeocodingResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;


@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GeocodingService {
    final RestTemplate restTemplate;

    static final String NOMINATIM_API_URL = "https://nominatim.openstreetmap.org/search";

    public Coordinates getCoordinates( String ward, String district, String province){
        String fullAddress = String.join(", ",ward,district, province, "VietNam");


        String url = UriComponentsBuilder.fromUriString(NOMINATIM_API_URL)
                .queryParam("q",fullAddress)
                .queryParam("format", "json")
                .queryParam("limit", 1)
                .build(false)
                .toUriString();


        HttpHeaders headers =new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("User-Agent","ProjectApp/1.0 (nguyenkhang31724@gmail.com)");
        HttpEntity<String> entity = new HttpEntity<>("nguyenkhang31724@gmail.com",headers);

        try{
            log.info("Call Geocoding Api: {}",url);

            ResponseEntity<GeocodingResponse[]> response= restTemplate.exchange(
                    url, HttpMethod.GET, entity, GeocodingResponse[].class
            );

            if (response.getStatusCode().is2xxSuccessful() && response.getBody()!=null && response.getBody().length>0){
                GeocodingResponse result = response.getBody()[0];

                double lat = Double.parseDouble(result.getLatitude());
                double lon = Double.parseDouble(result.getLongitude());

                log.info("Found coordinates: {}, {}", lat, lon);
                return Coordinates.builder()
                        .latitude(lat)
                        .longitude(lon)
                        .build();
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return null;
    }
}
