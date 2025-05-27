# Api 사용법!

## 2025-05-27 갱신됨.

필요한 것!

- 카톡에 올린 .env
- python 3.11.4 버전 기준으로 작성 됨(다른 버전 호환여부 확인 안 됨)
- PostMan과 같이 post 요청 보낼 수 있는 프로그램 필요

## 구동법 Root 경로 기준 아래만 따라하자!

- cd Python(Ai)
- python -m venv env
- env\Scripts\activate
- pip install -r requirements.txt
- uvicorn main:app --reload
- http://127.0.0.1:8000/save-image 이 경로로 post 요청
- ex {
  "head": "악어",
  "body": "비행기",
  "arms": "비행기",
  "legs": "비행기",
  "tail": "비행기"
  } -> 여기 까지 했을 때 DB에 저장이 안 되면 둘중 하나
  .env에 있는 mariaDB 계정 정보가 자신과 안 맞거나
  테이블을 안 만들었거나 둘중 하나입니다~
