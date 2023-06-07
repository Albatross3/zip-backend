package com.zip.backend.external;

import com.zip.backend.domain.housing.Housing;
import com.zip.backend.domain.housing.HousingDetail;
import com.zip.backend.domain.housing.HousingDetailRepository;
import com.zip.backend.domain.housing.HousingRepository;
import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Map;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

// 딱 1번만 사용할 Component -> DB 에 적재되는 순간 필요없음
@Component
public class HousingInformationGetOnce {

  private final HousingRepository housingRepository;
  private final HousingDetailRepository housingDetailRepository;

  public HousingInformationGetOnce(HousingRepository housingRepository,
      HousingDetailRepository housingDetailRepository) {
    this.housingRepository = housingRepository;
    this.housingDetailRepository = housingDetailRepository;
  }

  private final String BASE_URL = "https://api.odcloud.kr/api";
  private final String HOUSING_INFORMATION_PATH = "/ApplyhomeInfoDetailSvc/v1/getAPTLttotPblancDetail";
  private final String SPECIFIC_HOUSING_INFORMATION_PATH = "/ApplyhomeInfoDetailSvc/v1/getAPTLttotPblancMdl";
  private final String SERVICE_KEY = "&serviceKey=qPFnYQeoJF7lXQSeolPofGHz/GC6GTFg/LKVtVrdZCamO3KXStAmdMib2G2Z3fGanXLULbxjmzfxeJVZrRQCLw==";


//  @PostConstruct
  public void createHousingData() {
    // 모집공고 5/3 까지 데이터만 가져오기
    String query = "?page=1&perPage=30&cond[RCRIT_PBLANC_DE::GTE]=2023-04-10&cond[RCRIT_PBLANC_DE::LTE]=2023-05-03";
    ResponseEntity<Map> resultMap = fetch(HOUSING_INFORMATION_PATH, query);

    ArrayList<Map> resultArrayListMap = (ArrayList<Map>) resultMap.getBody().get("data");

    for (Map<String, Object> map : resultArrayListMap) {
      String HOUSE_TYPE_CODE = (String) map.get("HOUSE_SECD");
      if(HOUSE_TYPE_CODE.equals("10")) continue; // 신혼 희망 타운 코드 : 10
      Housing housing = Housing.builder()
          .HOUSE_MANAGE_NO(Long.valueOf((Integer)map.get("HOUSE_MANAGE_NO"))) // Long.valueOf((Integer)map.get("HOUSE_MANAGE_NO"))
          .HOUSE_NM((String)map.get("HOUSE_NM"))
          .HOUSE_SECD_NM((String)map.get("HOUSE_SECD_NM"))
          .HOUSE_DTL_SECD_NM((String)map.get("HOUSE_DTL_SECD_NM"))
          .SUBSCRPT_AREA_CODE_NM((String)map.get("SUBSCRPT_AREA_CODE_NM"))
          .HSSPLY_ADRES((String)map.get("HSSPLY_ADRES"))
          .TOT_SUPLY_HSHLDCO(String.valueOf((Integer)map.get("TOT_SUPLY_HSHLDCO")))
          .RCRIT_PBLANC_DE((String)map.get("RCRIT_PBLANC_DE"))
          .RCEPT_BGNDE((String)map.get("RCEPT_BGNDE"))
          .RCEPT_ENDDE((String)map.get("RCEPT_ENDDE"))
          .SPSPLY_RCEPT_BGNDE((String)map.get("SPSPLY_RCEPT_BGNDE"))
          .SPSPLY_RCEPT_ENDDE((String)map.get("SPSPLY_RCEPT_ENDDE"))
          .PRZWNER_PRESNATN_DE((String)map.get("PRZWNER_PRESNATN_DE"))
          .MVN_PREARNGE_YM((String)map.get("MVN_PREARNGE_YM"))
          .build();
      housingRepository.save(housing);

      // 해당 APT 의 상세 정보 가져오기
      Long HOUSE_MANAGE_NUMBER = Long.valueOf((Integer) map.get("HOUSE_MANAGE_NO"));
      String detailQuery = "?page=1&perPage=10&cond[HOUSE_MANAGE_NO::EQ]=" + HOUSE_MANAGE_NUMBER;

      ResponseEntity<Map> tempResultMap = fetch(SPECIFIC_HOUSING_INFORMATION_PATH, detailQuery);

      ArrayList<Map> tempResultArrayListMap = (ArrayList<Map>) tempResultMap.getBody().get("data");
      for (Map<String, Object> tempMap : tempResultArrayListMap) {
        HousingDetail housingDetail = HousingDetail.builder()
            .housing(housing)
            .SUPLY_AR((String) tempMap.get("SUPLY_AR"))
            .LTTOT_TOP_AMOUNT((String) tempMap.get("LTTOT_TOP_AMOUNT"))
            .SUPLY_HSHLDCO((Integer) tempMap.get("SUPLY_HSHLDCO"))
            .SPSPLY_HSHLDCO((Integer)tempMap.get("SPSPLY_HSHLDCO"))
            .MNYCH_HSHLDCO((Integer)tempMap.get("MNYCH_HSHLDCO"))
            .NWWDS_HSHLDCO((Integer)tempMap.get("NWWDS_HSHLDCO"))
            .LFE_FRST_HSHLDCO((Integer)tempMap.get("LFE_FRST_HSHLDCO"))
            .OLD_PARNTS_SUPORT_HSHLDCO((Integer)tempMap.get("OLD_PARNTS_SUPORT_HSHLDCO"))
            .INSTT_RECOMEND_HSHLDCO((Integer)tempMap.get("INSTT_RECOMEND_HSHLDCO"))
            .TRANSR_INSTT_ENFSN_HSHLDCO((Integer)tempMap.get("TRANSR_INSTT_ENFSN_HSHLDCO"))
            .ETC_HSHLDCO((Integer)tempMap.get("ETC_HSHLDCO"))
            .build();

        housingDetailRepository.save(housingDetail);
      }

    }
  }

  public ResponseEntity<Map> fetch(String path, String query) {
    RestTemplate restTemplate = new RestTemplate();
    HttpEntity<?> entity = new HttpEntity<>(new HttpHeaders());
    return restTemplate.exchange(BASE_URL + path + query + SERVICE_KEY, HttpMethod.GET, entity, Map.class);
  }

}
