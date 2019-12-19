# 구글 클라우드 정리

## 구글 클라우드 업로드 안내
https://cloud.google.com/community/tutorials/kotlin-springboot-container-engine

명령어 수정 
PROJECT_ID = spring-project-261615
> gcloud builds submit --tag=gcr.io/${PROJECT_ID}/demo:v1 .

kubectl expose deployment react --type=LoadBalancer --port 3000 --target-port 3000

### 클러스터 작동 법

> $ gcloud container clusters create demo-cluster --num-nodes=2 --zone=us-west1-b
> kubectl run demo --image=gcr.io/${PROJECT_ID}/demo:v1 --port 8080
> kubectl get pods

## 클러스터 링크
https://console.cloud.google.com/kubernetes/clusters/details/us-west1-b/demo-cluster?project=spring-project-261615&tab=details&persistent_volumes_tablesize=50&storage_class_tablesize=50&nodes_tablesize=50&node_pool_tablesize=10

## 클라우드 쉘 링크
https://ssh.cloud.google.com/cloudshell/editor?hl=ko#id=I0_1575997072895&_gfid=I0_1575997072895&parent=https:%2F%2Fconsole.cloud.google.com&pfname=&rpctoken=24819776

## Project의 권한관리
https://console.cloud.google.com/iam-admin/iam?folder=&hl=ko&organizationId=&project=spring-project-261615

## 리눅스 권한 막힐경우
https://stackoverflow.com/questions/47214040/google-cloud-folders-with-permission-denied

## 구글 클라우드 docker 이미지 업로드중 gradlew: Permission Denied 에러
RUN chmod +x gradlew 추가해서 수정 권한을 줍니다.
https://stackoverflow.com/questions/17668265/gradlew-permission-denied

## 도커 이미지 강제삭제
https://knight76.tistory.com/entry/docker-docker-%EC%9D%B4%EB%AF%B8%EC%A7%80-%EC%82%AD%EC%A0%9C

# 구글 클라우드 SQL 생성 그리고 연동

- 1. 메인에서 좌측 메뉴 > 저장소 SQL 탭 > Project 선택 원하는 설정에 맞게 DB 생성
- 2. 생성된 인스턴스ID 클릭
- 3. {이 인스턴스에 연결} 구역에서 {Cloud Shell을 사용해 연결} 클릭 해당 Postgres 를 명령어로 실행합니다.
- 4. 해당 DB에 허용된 IP만 접속할 수 있도록 설정해야 하므로 상단 탭에 연결 > 네트워크 추가 > localhost의 경우 해당 컴퓨터의 ip를 넣고 구글 클라우스 서비스일 경우 해당 스토리지 ip주소를 넣습니다.
- 5. Spring Boot > application.properties

~~~
#--------------------------------------------------
# DB
#--------------------------------------------------
spring.datasource.url=jdbc:postgresql://35.199.63.131:5432/postgres
spring.datasource.username=postgres
spring.datasource.password=cmd%*%&1591
~~~

- 6. {이 인스턴스에 연결} {공개 IP 주소} 추가하여 연결 확인을 합니다.

SQL 탭
https://console.cloud.google.com/sql/instances/spring-project-261615/overview?project=spring-project-261615
https://cloud.google.com/sql/docs/postgres/quickstart?hl=ko
https://medium.com/@jwlee98/gcp-gke-%EC%B0%A8%EA%B7%BC-%EC%B0%A8%EA%B7%BC-%EC%95%8C%EC%95%84%EB%B3%B4%EA%B8%B0-3%ED%83%84-gcp-%EC%84%9C%EB%B9%84%EC%8A%A4-%EC%97%B0%EA%B2%B0-%ED%95%B4%EB%B3%B4%EA%B8%B0-ae608b1b4338

- 7. 구글 클라우드 SQL 탭에서 연결하는 SERVER의 IP를 연결해주어야 spring boot 에서 정상 작동합니다.
SQL > 연결 > 연결 ip 추가 총 2개가 들어가는데 각각 
1.  kubectl get service 나오는 서버의 외부 IP를 복사후 저장합니다.
2.  VM 인스턴스의 외부 IP를 저장해야 합니다. https://console.cloud.google.com/compute/instances?project=spring-project-261615&folder=true&hl=ko&organizationId=true&instancessize=50 

## 구글 클라우드 file & 이미지파일 업로드 경로 설정

https://guitaryc.tistory.com/30

java file upload CODE 
https://cloud.google.com/java/getting-started/using-cloud-storage?hl=ko

구글 클라우드 버킷에 파일을 업로드 하려면 인증서 API JSON 정보가 필요합니다.
- 인증서 생성 및 다운로드 
- 구글 클라우드 네입게이션의 API Manager  선택  
- Create Credentials 
- service account key 
- service account name 입력  role project owner 선택
- create

https://stackoverflow.com/questions/52926765/spring-boot-image-upload-to-the-google-cloud-storage-bucket-is-not-working

# React 구글클라우드 업로드 방법
https://codechacha.com/ko/dockerizing-react-with-nginx/

# 구글 클라우드 container image 삭제 방법
https://stackoverflow.com/questions/48492708/gcloud-container-images-delete-issue

> gcloud container images list-tags gcr.io/spring-project-261615/demo
> gcloud container images delete gcr.io/spring-project-261615/demo@sha256:012ef29989ab --force-delete-tags

# 구글 클라우드 도메인 설정
https://antilibrary.org/1885
https://console.cloud.google.com/net-services/dns/zones/spring-project?project=spring-project-261615&cloudshell=true

# docker image 프로젝트 올리기 순서!
> gcloud builds submit --tag=gcr.io/spring-project-261615/spring:v1 .
> gcloud container clusters create spring-cluster --num-nodes=2 --zone=us-west1-b
> kubectl run spring --image=gcr.io/spring-project-261615/spring:v1 --port 8080
> kubectl expose deployment spring --type=LoadBalancer --port 80 --target-port 8080

# loopback 오류
> kubectl describe pod spring-c5b459757-mxcjb
> kubectl logs [id]

kubectl exec -it spring-c5b459757-mxcjb -- /bin/bash

kubectl exec spring-c5b459757-mxcjb -c spring reboot

kubectl delete service spring
kubectl delete pod spring-c5b459757-mxcjb

kubectl set image deployment/spring spring=gcr.io/spring-project-261615/spring:v1

# 복제수 감소
kubectl scale --replicas=0 deployment/spring
https://stackoverflow.com/questions/54821044/how-to-stop-pause-a-pod-in-kubernetes

# kubectl pod 삭제
https://www.howtodo.cloud/kubernetes/2019/05/16/kubernetes-use.html
kubectl delete deployment spring

gcloud builds submit --tag=gcr.io/spring-project-261615/spring:v2 .

# 구글클라우드 DNS 도메인설정
https://cloud.google.com/endpoints/docs/openapi/dev-portal-setup-custom-domain?hl=ko

gcloud builds submit --tag=gcr.io/spring-project-261615/spring:v2.1 .