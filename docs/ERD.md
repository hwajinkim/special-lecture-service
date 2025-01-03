## 특강 신청 서비스 테이블 및 ERD 설계
---
### Entity 간 관계

1. Users ↔ Registrations
  * 일대다 관계 (1명의 사용자가 여러 특강에 신청가능)
2. Lectures ↔ Registrations
  * 일대다 관계 (1개의 특강에 여러 사용자가 신청가능)
---
### RDBMS 테이블 설계

#### user(사용자 테이블)
  
|필드명|데이터타입|제약조건|설명|
|------|---|---|---|
|user_id|long|PK|사용자 ID|
|username|varchar|NOT NULL|사용자 이름|
|created_at|datetime|DEFAULT CURRENT_TIMESTAMP|생성일자|
|updated_at|datetime|ON UPDATE CURRENT_TIMESTAMP|수정일자|

#### lecture(특강 테이블)      

|필드명|데이터타입|제약조건|설명|
|------|---|---|---|
|lecture_id|long|PK|특강 ID|
|lecture_name|varchar|NOT NULL|특강 이름|
|speaker|varchar|NOT NULL|강연자 이름|
|date|date|NOT NULL|특강 날짜(YYYY-MM-DD)|
|start_time|time|NOT NULL|특강 시작 시간|
|end_time|time|NOT NULL|특강 종료 시간|
|applicant_number|integer|NULL|특강 신청 인원|
|is_avaliable|varchar|NOT NULL DEFAULT ‘Y’|신청 가능 여부|
|created_at|datetime|DEFAULT CURRENT_TIMESTAMP|생성일자|
|updated_at|datetime|ON UPDATE CURRENT_TIMESTAMP|수정일자|

#### registration(특강 신청 테이블)  

|필드명|데이터타입|제약조건|설명|
|------|---|---|---|
|registration_id|long|PK|신청 ID|
|user_id|long|NOT NULL|사용자 ID|
|lecture_id|long|NOT NULL|특강 ID|
|lecture_name|varchar|NULL|특강 이름|
|speaker|varchar|NULL|강연자 이름|
|date|date|NULL|특강 날짜(YYYY-MM-DD)|
|start_time|time|NULL|특강 시작 시간|
|end_time|time|NULL|특강 종료 시간|
|status|varchar|DEFAULT 'COMPLETED’|신청 상태|
|applied_at|datetime|DEFAULT CURRENT_TIMESTAMP|신청일자|

### ERD 설계
![특강 ERD](https://github.com/user-attachments/assets/47f66d96-8aa9-494c-aeaa-cc5afd1f899a)
