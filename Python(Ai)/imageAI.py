from openai import OpenAI
import base64

italianInclude = """
        - 얕은 피사계 심도 (피사체 얼굴·몸통에 초점, 배경은 부드럽게 블러 처리) 
        - 유기적 재질감과 무기적 재질감의 극명한 대비
        - 극사실적 텍스처 디테일
        - 영화 같은 조명 (숲속 사이로 쏟아지는 골든아워 역광, 해변의 따뜻한 햇살과 파도 뒤 어스름한 그림자)  
        - 선명하고 대조적인 색채 
        - 환경 효과 (안개, 먼지, 물방울, 나뭇잎 흔들림 등으로 입체감 부여)  
        - 초고해상도(8K), 울트라 디테일 렌더링 느낌
        """


class imageAI:

    def __init__(self, head, body, arms, legs, tail, add, drs):
        self.prompt = f"""
        각 head, body, arms, legs, tail에 무조건 적힌대로 이루어지고 include 내용을 전부 
        충족하며 초현실적이고 하브리드적인 생물을 만들어야하며 생물이 이미지 밖으로 나가서 잘리면 안 돼:
        - Head: {head}
        - Body: {body}
        - Arms: {arms}
        - Legs: {legs}
        - Tail: {tail}

        Include: 
        - 역동적 구도 & 원근감 (저각도에서 3/4 뷰로 바라본 동작감)
        - {drs if drs else italianInclude}
        - {add}
        """

        print(self.prompt)

    def callAI(self):
        client = OpenAI()
        result = client.images.generate(model="gpt-image-1", prompt=self.prompt, n=1)

        image_base64 = result.data[0].b64_json
        image_bytes = base64.b64decode(image_base64)

        return image_bytes
