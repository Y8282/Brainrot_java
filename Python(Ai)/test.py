from openai import OpenAI
import base64

client = OpenAI(
    api_key="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"
)

prompt = """
A children's book drawing of a veterinarian using a stethoscope to 
listen to the heartbeat of a baby otter.
"""

result = client.images.generate(model="gpt-image-1", prompt=prompt)

image_base64 = result.data[0].b64_json
image_bytes = base64.b64decode(image_base64)

# Save the image to a file
with open("otter.png", "wb") as f:
    f.write(image_bytes)
