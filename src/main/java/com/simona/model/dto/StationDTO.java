package com.simona.model.dto;

import com.simona.model.LongLat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class StationDTO implements Comparable {

    private Integer id;
    private List<ControlPointDTO> controlPoints;
    private Integer sys_id;
    private StationRserviceDTO rservice;
    private String rserviceName;
    private Integer rsrvc_sys_id;
    private Integer entrp_id;
    private Integer entrp_sys_id;

    private Double latitude;
    private Double longitude;

    private String region;
    private String city;
    private String address;
    private String nick_name;
    private String nick_name_index;
    private Integer status;
    private String perm_number;
    private Date perm_date_from;
    private Date perm_date_to;
    private String perm_remark;

    private LocalDateTime updated = null;

    @Override
    public int compareTo(Object o) {
        StationDTO stationDTO = (StationDTO)o;
        return Integer.compare(id, stationDTO.getId());
    }


    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + latitude.hashCode();
        result = 31 * result + longitude.hashCode();
        result = 31 * result + id;
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof StationDTO)) {
            return false;
        }

        StationDTO stationDTO = (StationDTO) o;

        int result1 = Double.compare(latitude, stationDTO.getLatitude());
        int result2 = Double.compare(longitude, stationDTO.getLongitude());

        return result1 == 0 && result2 == 0 && id.equals(stationDTO.getId());
    }
}
