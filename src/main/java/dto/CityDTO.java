package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CityDTO {
    private Integer id;
    private String wikiDataId;
    private String type;
    private String city;
    private String name;
    private String country;
    private String countryCode;
    private String region;
    private String regionCode;
    private Long elevationMeters;
    private Float latitude;
    private Float longitude;
    private Long population;
    private String timezone;
    private Float distance;
    private Boolean deleted;
    private String placeType;
}
