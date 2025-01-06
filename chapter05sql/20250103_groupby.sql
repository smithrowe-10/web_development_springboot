-- users 에서 country 별 회원 수 구하기 쿼리

SELECT country, COUNT(distinct id) as uniqueUSerCnt
	from USERS
	group by COUNTRY
	;
-- ---------------------------> 뒤에 나오는 컬럼명을 기준으로 그룹화해서
-- country 를 표시하고 , COUNT() 적용한 컬럼도 표시해서 조회해줘.

SELECT * FROM users;

-- users에서 country가 Korea인 회원 중에서 마케팅 수신에 동의한 회원수를 구해 출력할 것
-- 표시 컬럼 uniqueUserCnt 두 컬럼으로 구성 (Group by - x)

SELECT COUNT(distinct ID) as uniqueUSerCnt
	from USERS
	WHERE COUNTRY = "Korea" and IS_MARKETING_AGREE = 1
	;

-- users에서 country별로 마케팅 수신 동의한 회원 수와 동의하지 않은 회원 수를 구해 출력
-- 표시 컬럼 country, uniqueUserCnt(Group by - o)

SELECT COUNTRY , IS_MARKETING_AGREE, COUNT(distinct id) as uniqueUSerCnt
	FROM users
	group by COUNTRY, IS_MARKETING_AGREE
	order by COUNTRY, uniqueUserCnt desc
	;
-- 			국가별로는 오름차순, uniqueUserCnt를 기준으로는 내립차순
--			select절 - from 테이블명 - group by절 - order by절 순서

group by에 두개 이상의 기준 컬럼을 추가하면 데이터가 여러 그룹으로 나뉨.
아르헨티나를 기준으로 했을 때 마케팅 수신 동의 여부가 0 인 회원수와 마케팅 수신 여부가 1인
회원수를 기준으로 나뉘어져 있음을 알 수 있음.

예를 들어서 위의 쿼리문의 경우, country를 기준으로 먼저 그룹화가 이루어지고,
그 후에 is_marketing_agree 를 기준으로 그룹화됐다.

그래서 group by에 여러 기준을 설정하면, 컬럼 순서에 따라 결과가 달라짐.
따라서 '중요한 기준을 앞에 배치' 해서 시각화와 그룹화에 대한 우선순위를 결정할 필요 없음.

-- GROUP BY 정리
-- 1) 주어진 컬럼을 기준으로 데이터를 그룹화아여 '집계함수'와 함께 사용
-- 2) GROUP BY 뒤에는 그룹화할 컬럼명을 입력. 그룹화한 컬럼에 집계 함수 적용하여
		-- 그룹별 계산을 수행.
-- 3) 형식 : GROUP BY 컬럼명1, 컬럼명2, ...
-- 4) GROUP BY에서 두개 이상의 기준 컬럼을 조건으로 추가하여 여러 그룹으로
-- 		분할 가능한데, 컬럼 순서에 따라 결과에 영향을 미치므로 우선순위를 생각할 필요 있음

-- users에서 국가(country) 내 도시(city) 별 회원수를 구하여 출력.
-- 단, 국가명은 알파뱃 순서대로 정렬, 같은 국가 내에서는 회원 수 기준으로 내림 차순 정렬.
-- 표시 컬럼 country, city, uniqueUserCnt(where절 - x)

SELECT country, city, COUNT(distinct id) as uniqueUserCnt
	from users
	group by country, city
	ORDER BY country ASC , uniqueUserCnt desc
	;


-- SUBSTR(컬럼명, 시작값, 글자개수)
-- users에서 월별(e.g. 2013-10) 가입 회원 수를 출력할 것.
-- 가입일시 컬럼 활용하고, 최신순으로 정렬할 것.

SELECT SUBSTR(CREATED_AT,1,7) as month, COUNT(distinct id) as uniqueUserCnt
	from users
	group by SUBSTR(CREATED_AT,1,7) 
	order by month DESC	
	;
	
-- 1. orderdetails에서 order_id별 주문 수량 quantity의 총합을 출력할 것.
	-- 주문 수량의 총합이 내림차순으로 정렬되도록 할 것(함수는 어제 수업확인)

SELECT ORDER_ID , SUM(QUANTITY) 
	FROM ORDERDETAILS
	group by ORDER_ID
	order by QUANTITY DESC 
	;

-- 2. orders에서 staff_id 별, user_id 별로 주문 건수를 출력할 것
	-- 단, staff_id 기준오름차순하고 주문건수 별 기준 내림차순

SELECT STAFF_ID , USER_ID , count(*)
	FROM ORDERS 
	group by STAFF_ID , USER_ID 
	order by STAFF_ID , COUNT(*) desc 
	;

-- 3. orders에서 월별로 주문한 회원 수 출력할 것(order_date 컬럼 이용, 최신순으로 정렬)

-- 1번 쿼리

SELECT SUBSTR(ORDER_DATE,1,7) , COUNT(distinct USER_ID) 
	FROM orders
	group by SUBSTR(ORDER_DATE,1,7) 
	order by SUBSTR(ORDER_DATE,1,7) DESC 
	;

-- 2번 쿼리

SELECT SUBSTR(ORDER_DATE,1,7) as month, COUNT(distinct USER_ID) as uniqueUser
	FROM orders
	group by SUBSTR(ORDER_DATE,1,7) 
	order by SUBSTR(ORDER_DATE,1,7) DESC 
	;

-- 마리아DB에서만 가능한 비표준 쿼리이므로 SQLD / P에서는 이렇게 출제되지않음 DB간 호환성을 염두에 두고있으면 1번으로 작성하는게 안전

-- users와 orders를 하나로 결합하여 출력합니다.(단 주문 정보가 있는 회원의 정보만 출력)

select *
	FROM users u inner join orders o on u.ID = o.USER_ID 
	;


-- 이상의 SQL문에 대한 해석
-- 기존에 from 다음에는 테이블 명 하나만 작성되었지만, 이제는 JOIN연산을 위한
-- 추가 문법이 적용됐음.
-- 회원 정보와 주문 정보를 하나로 결합하기 위해 users와 orders를 INNER JOIN(추후설명)으로
-- 묶고, '후속조건'으로 "주문정보가 있는 회원의 정보만 출력하기 위해" u.id=o.user_id를 적용함.

-- users PK인 id는 회원id에 해당합니다.
-- orders에 PK인 id는 주문 id에 해당하고, 2번째 컬럼인 user_id는 
-- orders에서는 PK는 아니지만 JOIN을 수행할 때 users와 합치는 조건이 됩니다.

-- 여러 테이블을 하나의 FROM에서 다룰 때에는 별칭을 사용 가능(ALIAS와는 다릅니다 -> AS는 컬럼명을 지정).
-- FROM users u 로 작성했을 때 이후에는 u 만 썻을 경우 users 테이블을 의미하게 됨.
-- 그래서 이후에 FROM 절에서 다수의 테이블 명을 기입하게 될 경우에 별칭을 통해서 정리하여
-- SQL문을 효율적으로 사용할 수 있게 됨

-- 이상의 문제에서의 조건은 '주문 정보가 있는 회원의 정보만 출력' 하는 것이므로, orders 내에
-- user_id가 일부 기준이 되어야 합니다.

-- 왜냐하면 users내에 있는 id는 1부터 끝까지 있으니까요.
-- users 테이블에는 회원 id가 id 컬럼에 기륵돼있고, orders 테이블에는 회원 id가 user_id로
-- 공통된 부분을 지정하는 컬럼이 존재하므로 둘을 연결시킬 수 있는데, 이때 사용하는 전치사가 "ON"

-- JOIN 적용 후 결과를 보기 좋게 정렬하도록 SQL 문 수정
-- 회원 id를 기준으로 오름차순하는 조건.
-- ORDER BY에서도 테이블 별칭으로 정렬할 컬럼을 지정 가능.

select *
	FROM users u inner join orders o on u.ID = o.USER_ID
	ORDER BY u.ID
	;

-- FROM에서 JOIN이 정렬된 후에 단일 테이블에 명령을 내리는 것처럼 쿼리를 작성 가능
-- -> 이미 JOIN을 통해 하나의 테이블로 구성된 것 처럼 간주되기 때문.

-- 복수의 테이블이 하나로 결합되기 위해서는 두 테이블 간에 공통된 부분이 존재해야 합니다.
-- RDBS에서는 이 부분을 키(Key)라고 합니다.
-- 키 값은 테이블에 반드시 1개 이상 존재하도록 설게되어 있고(여러분이 설계 해야 하고)
-- 테이블에서 개별 행을 유일하게 구분 짓습니다. 따라서 키 값은 컬럼 내에서 중복되지 않으며
-- 개별 행을 구분해야 하므로 null 값을 가질 수 없습니다(nullable = false로 Entity 클래스에서 지정했습니다.)

-- cf) 키 값은 테이블 내에서 고유한 값을 가지므로 한 테이블에서 개수를 계산할 때 중복되진 않는다.
-- 하지만 여러테이블을 조인하면 '키 값도 중복될 수 있다.' 예를 들어 회원 아이디가 7인 사람이
-- 세번 주문했다면 회원정보(users)와 주문정보(orders)를 결합한 결과에는 u.id = 7인
-- 행에 3 개 있을 것이다. 이 경우 '한 번이라도 주문한 회원 수'를 중복 없이 구하려면 회원
-- id를 중복 제거한 뒤에 회원 수를 count 할 필요가 있다.

-- key의 구분
-- 1. 기본 키(Primary Key) : 하나의 테이블에서 가지는 고유한 값
-- 2. 외래 키(Foreign Key) : 다른 테이블에서 가지는 기존 테이블의 값

-- FK는 다른 테이블의 고유한 키 값인 PK를 참조한다.(orders에서 o.id가 FK에 해당,
-- users의 u.id가 PK에 해당해서 FK가 PK를 참조해서 조건을 합치시켜 JOIN함.)

-- 예를 들어서 PK값이 A, B, C만 있다면 PK 값도 이 값만 가질 수 있고, 또한 중복되지 않는다는 특징을 지니고 있음.
-- 하지만 FK의 경우에는 참조하고 있는 관계에 따라 참조 대상인 PK 값이 여러번 나타날 수 있습니다.
-- users 테이블에서는 id = 7인 값이 하나만 존재하지만
-- orders 테이블에서는 user_id가 PK가 아니기 때문에 3건 존재합니다
-- 이를 join 시켰을 때 u.id로 order by하더라도 합친 테이블 상에서 제 1 컬럼(즉 PK 컬럼)에
-- 동일 id가 여러 번 출력될 수 있습니다.

-- JOIN의 종류
-- 1. INNER JOIN
-- 	: 두 테이블의 키 값이 일치하는 행의 정보만 가지고 옵니다.
-- 
-- 


-- HAVING
-- group by를 이용해서 데이터를 그룹화하고, 해당 그룹별로 집계 연산을 수행하여,
-- 국가 별 회원수를 도출해낼수 있었습니다.(count())
-- 예를 들어서, 회원 수가 n명 이상인 국가의 회원 수만 보는 등의 조건을 걸려면 어떡해야 할까?

-- WHERE절을 이용하는 방법이 있긴 하지만 추가적인 개념에 대해서 학습할 예정입니다.
-- 언제나 WHERE을 쓰는 것이 용이하지 않다는 점 부터 짚고 넘어가서 HAVING 학습 할 예정

-- users에서 country가 Korea, USA, France인 회원 숫자를 도출할 것.

SELECT COUNTRY , COUNT(distinct id) 
	FROM users
	WHERE COUNTRY IN ("korea","Usa","France")
	group by COUNTRY 
	;

WHERE을 통해서 원하는 국가만 먼저 필터링하고, group By를 했습니다.
여기서 필터링 조건은 country 컬럼의 데이터 값에 해당합니다.

만약에 회원 수가 8명 이상인 국가의 회원 수만 필터링하려면 어떻게해야할까?

-- SELECT COUNTRY, COUNT(distinct id)
-- 	FROM users
-- 	WHERE COUNT(distinct ID) >= 8
-- 	; 오류 나는 사례

-- users에서 회원 수가 8명 이상인 country 별 회원 수 출력(회원 수 기준 내림 차순)

-- SELECT country, COUNT(distinct id)
-- 	from users
-- 	group by COUNTRY 
-- 	HAVING COUNT(distinct id) >= 8
-- 	ORDER BY count(distinct id) DESC 
-- 	;

SELECT country, COUNT(distinct id)
	from users
	group by COUNTRY 
	HAVING COUNT(distinct id) >= 8
	ORDER BY 2 DESC 			-- 두번째 컬럼을 DESC순으로 정리
	;

1. where에서 필터링을 시도할 때 오류가 발생하는 이유 :
	where은 group by보다 먼저 실행되기 때문에 그룹화를 진행하기 전에 행을 필터링 함.
	하지만 집계 함수로 계산된 값의 경우에는 그룹화 이후에 이루어지기 때문에
	순서상으로 group by 보다 뒤에있어야해서 WHERE 절 도입이 불가능 함.
	
2. 즉 group by 를 사용한 집계 값을 필터링 할때는 -> HAVING 을 사용

-- orders에서 담당 직원별 주문 건수와 주문 회원수를 계산하고, 주문건수가 10건이상이면서
-- 주문회원수가 40명 이하인 데이터만 출력(단, 주문건수기준으로 내림차순 정렬할 것.
-- staff_id, user_id, id 컬럼 이용)

SELECT staff_id, COUNT(distinct id), COUNT(distinct user_id) 
	from ORDERS
	group by STAFF_ID
	HAVING COUNT(distinct id) <= 40
	order by COUNT(distinct id) DESC 

-- HAVING 정리
-- 순서상 GROUP BY 뒤쪽에 위치하며, GROUP BY와 집계함수로 그룹별로 집계한 값을
-- 필터링 할 때 사용
	
-- WHERE과 동일하게 필터링 기능을 수행하지만, 적용 영역의 차이 때문에 주의할 필요 있음.
-- WHERE은 FROM에서 불러온 데이터를 직접 필터링하는 반면에,
-- HAVING은 'GROUP BY가 실행된 이후의 집계 함수 값'을 필터링함
-- 따라서 HAVING은 GROUP BY가 SELECT문 내에 없다면 사용할 수가 없음.
	
-- SELECT문의 실행순서 ★★★★★
SELECT 컬럼명			-5
from 테이블명			-1
WHERE 조건1			-2
GROUP BY 컬럼명		-3
HAVING 조건2			-4
ORDER BY 컬럼명		-6

-- 1. 컴퓨터는 가장 먼저 FROM을 읽어 테이터가 저장된 위치에 접근하고, 테이블의 존재 유무를 따지고
-- 테이블을 확인하는 작업을 실행하고,
-- 2. WHERE을 실행하여 가져올 테이터의 범위 확인
-- 3. 데이터베이스에서 가져올 범위가 결정된 데이터에 한하여 집계 연산을 적용할 수 있게
-- 	그룹으로 데이터를 나눈다.
-- 4. HAVING은 바로 그 다음 실행되면서 이미 group by를 통해 집계 연산 적용이 가능한 상태이기
-- 	때문에 4의 단계에서 집계 연산을 수행함.
-- 5. 이후 SELECT를 통해 특정 컬럼, 혹은 집계 함수 적용 컬럼을 조회하여 값을 보여주는데,
-- 6. order BY를 통해 특정 컬럼 및 연산 결과를 통한 오름차순 및 내림차순으로 나열함.

-- 연습 문제
-- 1. orders에서 회원별 주문 건수 추출(단 주문건수가 7건 이상인 회원의 정보만 추출,
-- 주문 건수 기준으로 내림차순 정렬, user_id와 주문 아이디(id)컬럼 활용)

SELECT USER_ID , COUNT(distinct id) 
	FROM ORDERS
	group by USER_ID 
	HAVING COUNT(distinct id) >= 7
	order BY COUNT(distinct id) DESC 
	;

-- 2. users에서 country별 city 수와 국가별 회원(id) 수를 추출.
-- 	단, 도시수가 5개 이상이고 회원수가 3명이상인 정보만 추출하고,
-- 	도시 수, 회원 수 기준으로 모두 내림차순 할것
	
SELECT COUNTRY , COUNT(distinct CITY), COUNT(distinct id) 
	FROM USERS
	group by COUNTRY
	HAVING COUNT(distinct CITY)>=5 and COUNT(distinct id)>=3
	order by COUNT(distinct CITY)DESC , COUNT(distinct id) DESC 
	
-- 3. users에서 USA, BRAZIL, KOREA, ARGENTINA, MEXICO에 거주중인 회원수를
-- 	국가별로 추출(단, 회원수가 5명 이상인 국가만 추출하고, 회원수 기준으로 내림차순)

SELECT COUNTRY , COUNT(distinct ID) 
	FROM users
	WHERE COUNTRY IN ("USA","BRAZIL","KOREA","ARGENTINA","MEXICO")
	GROUP BY COUNTRY 
	HAVING COUNT(distinct ID)>=5
	ORDER BY COUNT(distinct ID)desc
	;

-- SQL 실무 상황에서의 GROUP BY & HAVING
-- 1. 2025-01-03에 음식 분류별(한식, 중식, 분식, ...) 주문 건수 집계
-- SELECT 음식분류, COUNT(distinct 주문아이디) as 주문건수
-- 	from 주문정보
-- 	WHERE 주문시간(월) = "2025-01"
-- 	GROUP BY 음식분류
-- 	ORDER BY 주문건수 DESC 
-- 	;