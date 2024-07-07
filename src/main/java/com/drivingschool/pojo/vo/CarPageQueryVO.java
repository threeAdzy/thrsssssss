package com.drivingschool.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarPageQueryVO implements Serializable {
    private Long carId;
    private String carNumber;
    private String carImg;
    private Integer carState;
//    private Long coachId;
    private String name;//教练姓名
    private String coachImg;

}
