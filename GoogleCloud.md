# 구글 클라우드 정리

## 구글 클라우드 업로드 안내
https://cloud.google.com/community/tutorials/kotlin-springboot-container-engine

명령어 수정 
PROJECT_ID = spring-project-261615
> gcloud builds submit --tag=gcr.io/${PROJECT_ID}/demo:v1 .

> $ gcloud container clusters create demo-cluster --num-nodes=2 --zone=us-west1-b

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