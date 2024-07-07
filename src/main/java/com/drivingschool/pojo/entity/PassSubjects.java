package com.drivingschool.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PassSubjects {
    private Long subjectId;
    private Long userId;
    private Integer subjectOne;
    private Integer subjectTwo;
    private Integer subjectThree;
    private Integer subjectFour;

}
