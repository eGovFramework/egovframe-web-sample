## K-Pass(Kubernetes) 배포 설정
- web-sample 프로젝트를 kubernetes 환경에 배포하기 위한 설정파일
- 로컬PC에 minikube가 구성된 환경을 가정

---
### minikube 내 도커 이미지 로드
- 대상 이미지 --> egovframe-web:5.0.0
  - 대상 이미지 확인 명령어 ``` docker image ls ```
- minikube 내 이미지 로드
  - ``` minikube image load egovframe-web:5.0.0 ```

### deployment.yaml
- web-sample 이미지(egovframe-web:5.0.0)를 pod로 배포하기 위한 설정파일
- 위에서 로드한 이미지를 이용

### service.yaml
- web-sample pod에 연결하기위한 kubernetes(minikube)의 IP/PORT 설정
- nodePort(30000)을 web-sample 서비스에 이용

### minikube IP 확인
- 로컬에 구성된 kubernetes의 IP 확인
  - ```kubectl get nodes -o wide```
  - INTERNAL-IP 확인(예, 192.168.49.2)

### web-sample 배포 (pod 생성)
- deployment.yaml 파일의 경로에서(./k8s) 다음 명령어 실행
  - ```kubectl apply -f deployment.yaml```
- 확인 : ```kubectl get pod```

### 서비스 구성
- service.yaml 파일의 경로에서(./k8s) 다음 명령어 실행
  - ```kubectl apply -f service.yaml```
- 확인 : ```kubectl get svc```
- 전체 확인 : ```kubectl get all```

### 웹서비스 접속
- (위의 minikube IP 확인시 192.168.49.2 인 경우) 
  - http://192.168.49.2:30000/app/ 접속

### 참고사항
- Apple Silicon Mac에서는 minikube + docker driver 이용 시 NodePort로 연결이 안되는 현상 발생
  - minikube에서 제공하는 명령어를 통해서 로컬 tunnel을 구성 
    - ```minikube service egovframe-web```
  - 기타 port-forwarding을 구성하는 방법 등
