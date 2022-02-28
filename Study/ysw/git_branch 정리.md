

[toc]

### 

## 1. 로컬 저장소에서

```bash
# 브랜치 생성
git branch <브랜치명>
# ex) git branch A

# head 포인터 이동
git switch <브랜치명>
# ex) git branch A # A 로 head가 이동

# 브랜치 생성 + 해당 브랜치로 head 이동 
git switch -c <브랜치명>


# 브랜치 병합 (커밋한 내용 == 병합할 내용 따라서, 병합 전 커밋 필수)
git merge <브랜치명> # 현재 head가 가리키는 브랜치에 입력한 브랜치 내용을 병합
# ex) 현재 head > master 상태에서 git merge A 하면 A의 커밋된 내용이 
# master 브랜치에 병합 된다.

# 만약 내용 충돌 발생시에는 수동으로 수정 해야 함

# 현재 브랜치 관련 조회 법
# 1. 현재 존재하는 모든 브랜치 조회
git branch
# 2. 현재 기준 브랜치 상황 체크 (현재 기준보다 더 나중 브랜치는 여기서 안 나옴)
git log 
git log --oneline

```



## 2. 원격 저장소 이용



기본 개념 

원격저장소(origin) 의 master 브랜치에는 최종본 or 완성단계에 가장 가까운 파일이어야 한다.

 따라서 로컬에서 원격저장소로 커밋할 떄 master 브랜치에 직접 push는 지양.

`git clone <저장소 주소>`  ||  `git pull`  (원격저장소 master브랜치 내용을 받는 것)

이후, 작업한 내용을 push하려면 **branch를 생성**하거나, 생**성되어있는 브랜치에 푸쉬

`git push origin <브랜치명>`

이후 이 브랜치를 직접 pull 받고 싶으면

`git pull <브랜치명>` 

ex) 만약 A라는 브랜치를 만들어 작업 했다면 원격 저장소로 push 할 때

`git push origin A` <= 이렇게 되면 원격 저장소에 master브랜치 외에 A브랜치가 생성 됨

만약  A 브랜치 내용을 직접 pull 받고 싶다면

`git pull A` 



이후 master 브랜치에 병합하려면 git 사이트에 머지 리퀘스트를 통해 병합 진행!



## 참고

참고사이트: https://backlog.com/git-tutorial/kr/

싸피 강의: Web Pjt 강의 중 git branch

