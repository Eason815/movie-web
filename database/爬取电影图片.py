import requests
import pymysql
import time
from lxml import etree
import re
import random

# 第一页0 第二页25 ... 225
conn = pymysql.connect(host="localhost", port=3306, user="root", password="123456", database="moviedb")
furl = "https://movie.douban.com/top250?start=%d"
headers = {
        "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36"
}

movie_id = 0

with conn.cursor() as cursor:
    query = "select movie_id from movie"
    cursor.execute(query)
    movie_ids = cursor.fetchall()
    movie_ids = [id[0] for id in movie_ids]



    for i in range(0, 10):
        url = furl % (i * 25)
        resp = requests.get(url, headers=headers)
        resp.encoding = resp.apparent_encoding
        tree = etree.HTML(resp.text)
        imgs = tree.xpath('//div[@class="pic"]//img/@src')

        for img in imgs:
            movie_id += 1
            if movie_id not in movie_ids:
                continue

            resp = requests.get(img, headers=headers)
            with open(f"image/{movie_id}.webp", "wb") as f:
                f.write(resp.content)

conn.close()