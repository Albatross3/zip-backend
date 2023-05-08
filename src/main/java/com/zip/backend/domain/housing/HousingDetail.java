package com.zip.backend.domain.housing;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
public class HousingDetail {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  @ManyToOne
  @JoinColumn()
  Housing housing;

  @Column
  String SUPLY_AR; // 공급면적

  @Column
  String LTTOT_TOP_AMOUNT; // 공급금액(분양최고금액) (단위:만원)

  @Column
  Integer SUPLY_HSHLDCO; // 일반공급 세대수

  @Column
  Integer SPSPLY_HSHLDCO; // 특별공급 세대수

  @Column
  Integer MNYCH_HSHLDCO; // 특별공급 - 다자녀가구 세대수

  @Column
  Integer NWWDS_HSHLDCO; // 특별공급 - 신혼부부 세대수

  @Column
  Integer LFE_FRST_HSHLDCO; // 특별공급 - 생애최초 세대수

  @Column
  Integer OLD_PARNTS_SUPORT_HSHLDCO; // 특별공급 - 노부모부양 세대수

  @Column
  Integer INSTT_RECOMEND_HSHLDCO; // 특별공급 - 기관추천 세대수

  @Column
  Integer TRANSR_INSTT_ENFSN_HSHLDCO; // 특별공급 - 이전기관 세대수

  @Column
  Integer ETC_HSHLDCO; // 특별공급 - 기타 세대수


}
