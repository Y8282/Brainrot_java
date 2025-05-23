from openai import OpenAI


class imageAI:

    def __init__(self, head, body, arms, legs, tail):
        self.prompt = f"""
        Generate a surreal, hybrid creature image description with the following components:
        - Head: {head}
        - Body: {body}
        - Arms: {arms}
        - Legs: {legs}
        - Tail: {tail}

        Include:
        • 유기적 재질감과 무기적 재질감의 극명한 대비
        • 극사실적 텍스처 디테일
        • 영화 같은 조명 (숲속 사이로 쏟아지는 골든아워 역광, 해변의 따뜻한 햇살과 파도 뒤 어스름한 그림자)  
        • 선명하고 대조적인 색채 (선명한 옐로우·그린·블루 대비, 메탈릭 실버와 내추럴 브라운 톤 조합)  
        • 역동적 구도 & 원근감 (저각도에서 3/4 뷰로 바라본 동작감, 'Body'와 'Legs'에 따라서 시선이 이끄는 ‘걷는’ 또는 ‘비행하는’ 또는 '수영하는' 자세 강조)  
        • 얕은 피사계 심도 (피사체 얼굴·몸통에 초점, 배경은 부드럽게 블러 처리)  
        • 환경 효과 (안개, 먼지, 물방울, 나뭇잎 흔들림 등으로 입체감 부여)  
        • 초고해상도(8K), 울트라 디테일 렌더링 느낌  
        """

    def callAI(self):
        client = OpenAI()
        response = client.images.generate(
            model="dall-e-3",
            prompt=self.prompt,
            size="1024x1024",
            quality="standard",
            n=1,
        )

        return response.data[0].url
