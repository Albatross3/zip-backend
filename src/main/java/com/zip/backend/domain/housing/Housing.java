package com.zip.backend.domain.housing;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

// field 가 많기 때문에 builder 패턴을 적극적으로 활용
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
public class Housing {

  @Id // id 값을 외부 API 를 통해 지정할 수 있음
  private Long HOUSE_MANAGE_NO;

  @Column
  private String IMAGE; // 크롤링 이후에 생성

  @Column
  private String HOUSE_NM; // 주택명

  @Column
  private String HOUSE_SECD_NM; // 주택구분코드명 ex) APT

  @Column
  private String HOUSE_DTL_SECD_NM; // 주택상세구분코드명 ex) 민영

  @Column
  private String SUBSCRPT_AREA_CODE_NM; // 공급지역

  @Column
  private String HSSPLY_ADRES; // 공급위치

  @Column
  private String TOT_SUPLY_HSHLDCO; // 공급규모

  @Column
  private String RCRIT_PBLANC_DE; // 모집 공고일

  @Column
  private String RCEPT_BGNDE; // 청약접수시작일

  @Column
  private String RCEPT_ENDDE; // 청약접수종료일

  @Column
  private String SPSPLY_RCEPT_BGNDE; // 특별공급 접수시작일

  @Column
  private String SPSPLY_RCEPT_ENDDE; // 특별공급 접수종료일

  @Column
  private String PRZWNER_PRESNATN_DE; // 당첨자 발표일

  @Column
  private String MVN_PREARNGE_YM; // 입주예정월

  // 크롤링 데이터로 image 지정
  public void setImage(String imageUrl) {
    IMAGE = imageUrl;
  }

  public Long getHOUSE_MANAGE_NO() {
    return HOUSE_MANAGE_NO;
  }
}
