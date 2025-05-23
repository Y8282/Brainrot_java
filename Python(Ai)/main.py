# requirements:
# pip install fastapi uvicorn requests PyMySQL

from fastapi import FastAPI, HTTPException
from pydantic import BaseModel, HttpUrl
import requests
import pymysql
import os
from dotenv import load_dotenv

from imageAI import imageAI

load_dotenv()


class ImageRequest(BaseModel):
    head: str
    body: str
    arms: str
    legs: str
    tail: str


# 환경변수로 DB 정보 설정
DB_HOST = os.getenv("DB_HOST")
DB_PORT = int(os.getenv("DB_PORT", 3306))
DB_USER = os.getenv("DB_USER")
DB_PASS = os.getenv("DB_PASS")
DB_NAME = os.getenv("DB_NAME")

# MariaDB 연결 설정
connection = pymysql.connect(
    host=DB_HOST,
    port=DB_PORT,
    user=DB_USER,
    password=DB_PASS,
    database=DB_NAME,
    charset="utf8mb4",
    cursorclass=pymysql.cursors.DictCursor,
)

app = FastAPI()


@app.post("/save-image")
async def save_image(request: ImageRequest):
    # Url 가져오기
    ai = imageAI(
        request.head,
        request.body,
        request.arms,
        request.legs,
        request.tail,
    )

    imageUrl = ai.callAI()

    # 이미지 가져오기
    try:
        resp = requests.get(imageUrl)
        resp.raise_for_status()
    except Exception as e:
        raise HTTPException(status_code=400, detail=f"이미지 조회 실패: {e}")
    img_bytes = resp.content

    # DB에 저장
    try:
        with connection.cursor() as cursor:
            sql = "INSERT INTO brainrot_image (image) VALUES (%s)"
            cursor.execute(sql, (img_bytes,))
            connection.commit()
            image_id = cursor.lastrowid
    except Exception as e:
        connection.rollback()
        raise HTTPException(status_code=500, detail=f"DB 저장 실패: {e}")

    return {"success": True, "id": image_id}


if __name__ == "__main__":
    import uvicorn

    uvicorn.run(app, host="0.0.0.0", port=8000)
