--------------------
# PROJECT DEVELOPMENT DOCUMENT
--------------------


# 목차
- [1. DOMAIN STORE DB COLUMN INSERT](#DOMAIN-STORE-DB-COLUMN-INSERT)

# DOMAIN STORE DB COLUMN INSERT

- SERVER

> src/java/domain/Store.java

TABLE COLUMN 추가를 위해서
Variable 추가

> src/java/dto/StoreDTO.java

DOMAIN 추가를 위해서
Variable 추가

> src/java/dto/UniversitySaveDTO.java

Client 에서 STORE 정보를 받아서 StoreDTO 전달해주기 위해서
Variable 추가

> src/java/projection/StorePublic.java

SERVER 에서 전달받은 DATA를 Client 에 선택된 DATA만 전달해 주기위해서
Variable 추가

> src/java/repository/StoreRepositoryImpl.java

~~~
public StorePublic findByStoreOne(Long id);
~~~

DB 에서 전달받은 DATA 를 StorePublic 추가해주기 위해서
메소드 Projections.constructor class 에 Variable 를 추가합니다.

> src/java/service/UniversityServiceImpl.java

~~~
public UniversityPublic saveOrUpdate(UniversitySaveDTO dto, StoreDTO storeDTO, Account accountData);
~~~

Client 에서 STORE 정보를 받아서 StoreDTO 저장하여 DB 에 INSERT 해주기 위해서 

~~~
storeDTO.setSto...(dto.getSto...());
~~~

식으로 Setter 해주어 DTO 에 값을 추가해 줍니다.

- Client

> src/mainPage/MainProvider.jsx

openModal() 메소드에 API DATA 를 받을 변수를 매개변수에 추가합니다.
closeModal() 메소드에는 API DATA 를 초기화 해주기 위해서 변수를 추가합니다.
openModal() 메소드로 전달받은 API DATA 를 this.state 저장하여 여러곳에 전달해줄 준비를 합니다.
API DATA 를 PugjjigProvider 전달해줍니다.

> src/service/KakaoMapService.jsx

API 에서 전달받은 DATA 를 Components 전달해주는 openModal() 메소드에 매개변수 값을 추가합니다.

> src/components/pugjjig/PugjjigProvider.jsx

MainProvider 에서 전달받은 props 값을 InsertModal 전달합니다.

> src/components/pugjjig/modal/InsertModal.jsx

InsertModal 에서 전달받은 props 값을 PugjjigWrite 전달합니다.

> src/components/pugjjig/form/PugjjigWrite.jsx

SERVER 전송되는 FORM 값에 onSubmit 변수 값을 추가합니다.