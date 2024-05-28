import requests
import pymysql
import time
from lxml import etree
import re
import random

# 第一页0 第二页25 ... 225
conn = pymysql.connect(host="localhost", port=3306, user="root", password="qaz781212", database="moviedb")
furl = "https://movie.douban.com/top250?start=%d"

with conn.cursor() as cursor:
    query = "select movie_id from movie"
    cursor.execute(query)
    movie_ids = cursor.fetchall()
    movie_ids = [id[0] for id in movie_ids]

    for movie_id in movie_ids:
        sql = "update movie set image = %s where movie_id = %s"
        cursor.execute(sql, (f"{movie_id}.webp", str(movie_id)))
        conn.commit()

conn.close()